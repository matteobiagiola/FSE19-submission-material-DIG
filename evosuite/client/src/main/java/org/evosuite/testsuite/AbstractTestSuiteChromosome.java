/**
 * Copyright (C) 2010-2016 Gordon Fraser, Andrea Arcuri and EvoSuite
 * contributors
 *
 * This file is part of EvoSuite.
 *
 * EvoSuite is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3.0 of the License, or
 * (at your option) any later version.
 *
 * EvoSuite is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with EvoSuite. If not, see <http://www.gnu.org/licenses/>.
 */
/**
 * Copyright (C) 2011,2012 Gordon Fraser, Andrea Arcuri and EvoSuite
 * contributors
 *
 * This file is part of EvoSuite.
 *
 * EvoSuite is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Public License as published by the Free Software Foundation,
 * either version 3 of the License, or (at your option) any later version.
 *
 * EvoSuite is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Public License for more details.
 *
 * You should have received a copy of the GNU Public License along with
 * EvoSuite. If not, see <http://www.gnu.org/licenses/>.
 *
 * @author Gordon Fraser
 */
package org.evosuite.testsuite;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.evosuite.Properties;
import org.evosuite.ga.Chromosome;
import org.evosuite.ga.ChromosomeFactory;
import org.evosuite.ga.ConstructionFailedException;
import org.evosuite.ga.localsearch.LocalSearchObjective;
import org.evosuite.regression.RegressionTestChromosomeFactory;
import org.evosuite.testcase.DefaultTestCase;
import org.evosuite.testcase.ExecutableChromosome;
import org.evosuite.testcase.TestChromosome;
import org.evosuite.testcase.execution.ExecutionResult;
import org.evosuite.testcase.factories.AllMethodsTestChromosomeFactory;
import org.evosuite.testcase.factories.RandomLengthTestFactory;
import org.evosuite.testcase.statements.MethodStatement;
import org.evosuite.testcase.statements.Statement;
import org.evosuite.utils.Randomness;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractTestSuiteChromosome<T extends ExecutableChromosome> extends Chromosome {


	private static final long serialVersionUID = 1L;

	private static final Logger logger = LoggerFactory.getLogger(AbstractTestSuiteChromosome.class);
	
	protected List<T> tests = new ArrayList<T>();
	protected ChromosomeFactory<T> testChromosomeFactory;

	/**
	 * only used for testing/debugging
	 */
	protected AbstractTestSuiteChromosome(){
		super();
	}
	
	
	/**
	 * <p>Constructor for AbstractTestSuiteChromosome.</p>
	 *
	 * @param testChromosomeFactory a {@link org.evosuite.ga.ChromosomeFactory} object.
	 */
	protected AbstractTestSuiteChromosome(ChromosomeFactory<T> testChromosomeFactory) {
		this.testChromosomeFactory = testChromosomeFactory;
	}

	/**
	 * <p>Getter for the field <code>testChromosomeFactory</code>.</p>
	 *
	 * @return a {@link org.evosuite.ga.ChromosomeFactory} object.
	 */
	public ChromosomeFactory<T> getTestChromosomeFactory() {
		return testChromosomeFactory;
	}

	/**
	 * Creates a deep copy of source.
	 *
	 * @param source a {@link org.evosuite.testsuite.AbstractTestSuiteChromosome} object.
	 */
	@SuppressWarnings("unchecked")
	protected AbstractTestSuiteChromosome(AbstractTestSuiteChromosome<T> source) {
		this(source.testChromosomeFactory);

		for (T test : source.tests) {
			addTest((T) test.clone());
		}

		//this.setFitness(source.getFitness());
		this.setFitnessValues(source.getFitnessValues());
		this.setPreviousFitnessValues(source.getPreviousFitnessValues());
		this.setChanged(source.isChanged());
		this.setCoverageValues(source.getCoverageValues());
        this.setNumsOfCoveredGoals(source.getNumsOfCoveredGoals());
        this.setNumsOfNotCoveredGoals(source.getNumsNotCoveredGoals());
	}

	/**
	 * <p>addTest</p>
	 *
	 * @param test a T object.
	 */
	public void addTest(T test) {
		tests.add(test);
		this.setChanged(true);
	}
	
	public void deleteTest(T test) {
		boolean changed = tests.remove(test);
		if(changed)
			this.setChanged(true);
	}

	/**
	 * <p>addTests</p>
	 *
	 * @param tests a {@link java.util.Collection} object.
	 */
	public void addTests(Collection<T> tests) {
		for (T test : tests) {
			this.tests.add(test);
		}
		if (!tests.isEmpty())
			this.setChanged(true);
	}

	/**
	 * <p>addUnmodifiableTest</p>
	 *
	 * @param test a T object.
	 */
	public void addUnmodifiableTest(T test) {
		tests.add(test);
		this.setChanged(true);
	}

	/**
	 * {@inheritDoc}
	 *
	 * Keep up to position1, append copy of other from position2 on
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void crossOver(Chromosome other, int position1, int position2)
	        throws ConstructionFailedException {
		if (!(other instanceof AbstractTestSuiteChromosome<?>)) {
			throw new IllegalArgumentException(
			        "AbstractTestSuiteChromosome.crossOver() called with parameter of unsupported type "
			                + other.getClass());
		}

		AbstractTestSuiteChromosome<T> chromosome = (AbstractTestSuiteChromosome<T>) other;
		
		if(Properties.TEST_CASE_CROSSOVER){
			if(Randomness.nextDouble() <= Properties.TEST_CASE_CROSSOVER_RATE){
				//test case level crossover
				logger.debug("@@@test case level crossover: ");
				int min_size = Math.min(tests.size(), chromosome.size());
				for (int i = 0; i < min_size; i++) {
					T test = tests.get(i);
					T otherTest = chromosome.tests.get(i);
					//instead of probability of applying crossover at test case level (see mutate method in this class) we choose to apply it
					//only when the two statements are method statements
					TestChromosome testChromosome = (TestChromosome) test;
					TestChromosome otherTestChromosome = (TestChromosome) otherTest;
					if(testChromosome.getTestCase().hasStatement(position1) && otherTestChromosome.getTestCase().hasStatement(position2)){
						Statement statement = testChromosome.getTestCase().getStatement(position1);
						Statement otherStatement = otherTestChromosome.getTestCase().getStatement(position2);
						if(statement instanceof MethodStatement && otherStatement instanceof MethodStatement){
							logger.debug("@@@cut point this chromosome: " + statement + " position: " + position1);
							logger.debug("@@@cut point other chromosome: " + otherStatement + " position: " + position2);
							test.crossOver(otherTest, position1, position2);
						}
					}
				}
			}else{
				//test suite level crossover
				logger.debug("@@@test suite level crossover");
				while (tests.size() > position1) {
					tests.remove(position1);
				}

				for (int num = position2; num < other.size(); num++) {
					T otherTest =  chromosome.tests.get(num);
					T clonedTest = (T) otherTest.clone();
					tests.add(clonedTest);
				}
			}
			
			this.setChanged(true);
		}else{
			while (tests.size() > position1) {
				tests.remove(position1);
			}

			for (int num = position2; num < other.size(); num++) {
				T otherTest =  chromosome.tests.get(num);
				T clonedTest = (T) otherTest.clone();
				tests.add(clonedTest);
			}
			
			this.setChanged(true);
		}
		
	}

	/** {@inheritDoc} */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;

		if (!(obj instanceof TestSuiteChromosome))
			return false;

		TestSuiteChromosome other = (TestSuiteChromosome) obj;
		if (other.size() != size())
			return false;

		for (int i = 0; i < size(); i++) {
			if (!tests.get(i).equals(other.tests.get(i)))
				return false;
		}

		return true;
	}

	/** {@inheritDoc} */
	@Override
	public int hashCode() {
		return tests.hashCode();
	}

	/**
	 * {@inheritDoc}
	 *
	 * Apply mutation on test suite level
	 */
	@Override
	public void mutate() {
		boolean changed = false;

		// Mutate existing test cases
		for (int i = 0; i < tests.size(); i++) {
			T test = tests.get(i);
			if (Randomness.nextDouble() < 1.0 / tests.size()) {
				test.mutate();
				if(test.isChanged())
					changed = true;
			}
		}

		// Add new test cases
		final double ALPHA = Properties.P_TEST_INSERTION; //0.1;

		for (int count = 1; Randomness.nextDouble() <= Math.pow(ALPHA, count)
		        && size() < Properties.MAX_SIZE; count++) {
			T test = testChromosomeFactory.getChromosome();
			addTest(test);
			logger.debug("Adding new test case");
			changed = true;
			/*if(Properties.SELENIUM_WHOLE){
				AllMethodsTestChromosomeFactory methodsChromosomeFactory = (AllMethodsTestChromosomeFactory) testChromosomeFactory;
				boolean considerRemainingMethodCalls = true;
				TestChromosome testChromosome = methodsChromosomeFactory.getChromosome(true);
				T test = (T) testChromosome;
				addTest(test);
				logger.debug("@@@Adding new test case");
				changed = true;
			}
			else{
				T test = testChromosomeFactory.getChromosome();
				addTest(test);
				logger.debug("Adding new test case");
				changed = true;
			}*/
			
		}
		
		Iterator<T> testIterator = tests.iterator();
		while(testIterator.hasNext()) {
			T test = testIterator.next();
			if(test.size() == 0)
				testIterator.remove();
		}
		

		if (changed) {
			this.setChanged(true);
		}
	}

	/**
	 * <p>totalLengthOfTestCases</p>
	 *
	 * @return Sum of the lengths of the test cases
	 */
	public int totalLengthOfTestCases() {
		int length = 0;
		for (T test : tests)
			length += test.size();
		return length;
	}

	/** {@inheritDoc} */
	@Override
	public int size() {
		return tests.size();
	}

	/** {@inheritDoc} */
	@Override
	public abstract boolean localSearch(LocalSearchObjective<? extends Chromosome> objective);

	/** {@inheritDoc} */
	@Override
	public abstract Chromosome clone();

	/**
	 * <p>getTestChromosome</p>
	 *
	 * @param index a int.
	 * @return a T object.
	 */
	public T getTestChromosome(int index) {
		return tests.get(index);
	}

	/**
	 * <p>getTestChromosomes</p>
	 *
	 * @return a {@link java.util.List} object.
	 */
	public List<T> getTestChromosomes() {
		return tests;
	}

	public List<ExecutionResult> getLastExecutionResults() {
		return tests.stream().map(t -> t.getLastExecutionResult()).collect(Collectors.toList());
	}

	/**
	 * <p>setTestChromosome</p>
	 *
	 * @param index a int.
	 * @param test a T object.
	 */
	public void setTestChromosome(int index, T test) {
		tests.set(index, test);
		this.setChanged(true);
	}
}
