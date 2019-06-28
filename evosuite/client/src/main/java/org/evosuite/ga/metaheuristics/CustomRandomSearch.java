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
 * 
 */
package org.evosuite.ga.metaheuristics;

import org.evosuite.ProgressMonitor;
import org.evosuite.Properties;
import org.evosuite.coverage.FitnessFunctions;
import org.evosuite.coverage.branch.OnlyFalseBranchCoverageSuiteFitness;
import org.evosuite.ga.Chromosome;
import org.evosuite.ga.ChromosomeFactory;
import org.evosuite.ga.FitnessFunction;
import org.evosuite.ga.metaheuristics.mosa.MOSA;
import org.evosuite.testcase.TestChromosome;
import org.evosuite.testcase.TestFitnessFunction;
import org.evosuite.testsuite.TestSuiteChromosome;
import org.evosuite.testsuite.TestSuiteFitnessFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * RandomSearch class.
 * </p>
 * 
 * @author Gordon Fraser
 */
public class CustomRandomSearch<T extends Chromosome> extends GeneticAlgorithm<T> {

	private static final Logger logger = LoggerFactory.getLogger(CustomRandomSearch.class);

	private static final long serialVersionUID = -7685015421245920459L;

	/** Map used to store the covered test goals (keys of the map) and the corresponding covering test cases (values of the map) **/
	protected Map<FitnessFunction<T>, T> archive = new  HashMap<FitnessFunction<T>, T>();

	/** Boolean vector to indicate whether each test goal is covered or not. **/
	protected Set<FitnessFunction<T>> uncoveredGoals = new HashSet<FitnessFunction<T>>();

	/**
	 * keep track of overall suite fitness and coverage
	 */
	protected TestSuiteFitnessFunction suiteFitness;

	/**
	 * <p>
	 * Constructor for CustomRandomSearch.
	 * </p>
	 *
	 * @param factory
	 *            a {@link ChromosomeFactory} object.
	 */
	public CustomRandomSearch(ChromosomeFactory<T> factory) {
		super(factory);
		if(org.evosuite.Properties.CRITERION.length == 1){
			suiteFitness = FitnessFunctions.getFitnessFunction(org.evosuite.Properties.CRITERION[0]);
			logger.info("SuiteFitness CustomRandomSearch: " + suiteFitness.getClass());
		}else{
			String criteria = Arrays.stream(org.evosuite.Properties.CRITERION)
					.map(String::valueOf).collect(Collectors.joining(":"));
			throw new IllegalStateException(this.getClass() + " constructor: CustomRandomSearch supports " +
					"only one search criterion. Found " + Properties.CRITERION.length + " - "
					+ criteria);
		}
	}


	/** {@inheritDoc} */
	@Override
	protected void evolve() {
		T newChromosome = chromosomeFactory.getChromosome();
		this.calculateFitness(newChromosome);
		currentIteration++;
	}

	/**
	 * This method computes the fitness scores only for the uncovered goals
	 * @param c chromosome
	 */
	private void calculateFitness(T c) {
		for (FitnessFunction<T> fitnessFunction : this.fitnessFunctions) {
			double fitness = fitnessFunction.getFitness(c);
			if (fitness == 0.0) {
				updateArchive(c, fitnessFunction);
			}
		}
		notifyEvaluation(c);
	}

	/**
	 * This method computes the fitness scores for all (covered and uncovered) goals
	 * @param c chromosome
	 */
	private void completeCalculateFitness(T c) {
		for (FitnessFunction<T> fitnessFunction : fitnessFunctions) {
			fitnessFunction.getFitness(c);
			//notifyEvaluation(c);
		}
	}

	/**
	 * Notify all search listeners of fitness evaluation
	 *
	 * @param chromosome
	 *            a {@link Chromosome} object.
	 */
	@Override
	protected void notifyEvaluation(Chromosome chromosome) {
		for (SearchListener listener : listeners) {
			if (listener instanceof ProgressMonitor) {
				continue;
			}
			listener.fitnessEvaluation(chromosome);
		}
	}

	/** {@inheritDoc} */
	@Override
	public void initializePopulation() {
		logger.info("executing initializePopulation function");

		notifySearchStarted();
		currentIteration = 0;

		// Create a random parent population P0
		generateInitialPopulation(1);
		// Determine fitness
		calculateFitness();
		this.notifyIteration();
	}

	/** {@inheritDoc} */
	@Override
	public void generateSolution() {
		logger.info("executing generateSolution function");

		// keep track of covered goals
		for (FitnessFunction<T> goal : fitnessFunctions) {
			uncoveredGoals.add(goal);
		}

		//initialize population
		if (population.isEmpty())
			initializePopulation();

		while (!isFinished() && this.getNumberOfCoveredGoals()<this.fitnessFunctions.size()) {
			evolve();
			notifyIteration();
		}

		completeCalculateFitness();
		notifySearchFinished();
	}

	/**
	 * Calculate fitness for all individuals
	 */
	protected void calculateFitness() {
		logger.debug("Calculating fitness for " + population.size() + " individuals");

		Iterator<T> iterator = population.iterator();
		while (iterator.hasNext()) {
			T c = iterator.next();
			if (isFinished()) {
				if (c.isChanged())
					iterator.remove();
			} else {
				calculateFitness(c);
			}
		}
	}

	protected void completeCalculateFitness() {
		logger.debug("Calculating fitness for " + population.size() + " individuals");
		Set<T> arch = new HashSet<T>();
		arch.addAll(archive.values());
		Iterator<T> iterator = arch.iterator();
		while (iterator.hasNext()) {
			T c = iterator.next();
			completeCalculateFitness(c);
		}
	}

	/** This method is used to print the number of test goals covered by the test cases stored in the current archive **/
	private int getNumberOfCoveredGoals() {
		int n_covered_goals = this.archive.keySet().size();
		logger.debug("# Covered Goals = " + n_covered_goals);
		return n_covered_goals;
	}

	/** This method return the test goals covered by the test cases stored in the current archive **/
	private Set<FitnessFunction<T>> getCoveredGoals() {
		return this.archive.keySet();
	}


	/**
	 * This method update the archive by adding test cases that cover new test goals, or replacing the
	 * old tests if the new ones are smaller (at the same level of coverage).
	 *
	 * @param solutionSet is the list of Chromosomes (population)
	 */
	private void updateArchive(T solution, FitnessFunction<T> covered) {
		// the next two lines are needed since that coverage information are used
		// during EvoSuite post-processing
		TestChromosome tch = (TestChromosome) solution;
		tch.getTestCase().getCoveredGoals().add((TestFitnessFunction) covered);

		// store the test cases that are optimal for the test goal in the
		// archive
		if (archive.containsKey(covered)){
			int bestSize = this.archive.get(covered).size();
			int size = solution.size();
			if (size < bestSize)
				this.archive.put(covered, solution);
		} else {
			archive.put(covered, solution);
			this.uncoveredGoals.remove(covered);
		}
	}

	protected List<T> getArchive() {
		Set<T> set = new HashSet<T>();
		set.addAll(archive.values());
		List<T> arch = new ArrayList<T>();
		arch.addAll(set);
		return arch;
	}

	protected List<T> getFinalTestSuite() {
		// trivial case where there are no branches to cover or the archive is empty
		if (this.getNumberOfCoveredGoals()==0) {
			return getArchive();
		}
		if (archive.size() == 0)
			if (population.size() > 0) {
				ArrayList<T> list = new ArrayList<T>();
				list.add(population.get(population.size() - 1));
				return list;
			} else
				return getArchive();
		return getArchive();
	}


	public TestSuiteChromosome getSuiteBestIndividual() {
		TestSuiteChromosome best = new TestSuiteChromosome();
		//Chromosome best = new TestSuiteChromosome();
		for (T test : getArchive()) {
			best.addTest((TestChromosome) test);
		}
		// compute overall fitness and coverage
		double coverage = ((double) this.getNumberOfCoveredGoals()) / ((double) this.fitnessFunctions.size());
		best.setCoverage(suiteFitness, coverage);
		best.setFitness(suiteFitness,  this.fitnessFunctions.size() - this.getNumberOfCoveredGoals());
		//suiteFitness.getFitness(best);
		return best;
	}

	/**
	 * This method is used by the Progress Monitor at the and of each generation to show the total coverage reached by the algorithm.
	 * Since the Progress Monitor need a "Suite", this method artificially creates a "SuiteChromosome" (see {@link MOSA#suiteFitness})
	 * as the union of all test cases stored in {@link MOSA#archive}.
	 *
	 * The coverage score of the "SuiteChromosome" is given by the percentage of test goals covered (goals in {@link MOSA#archive})
	 * onto the total number of goals <code> this.fitnessFunctions</code> (see {@link GeneticAlgorithm}).
	 *
	 * @return "SuiteChromosome" directly consumable by the Progress Monitor.
	 */
	@Override
	public T getBestIndividual() {
		TestSuiteChromosome best = new TestSuiteChromosome();
		//Chromosome best = new TestSuiteChromosome();
		for (T test : getArchive()) {
			best.addTest((TestChromosome) test);
		}
		// compute overall fitness and coverage
		double coverage = ((double) this.getNumberOfCoveredGoals()) / ((double) this.fitnessFunctions.size());
		best.setCoverage(suiteFitness, coverage);
		best.setFitness(suiteFitness,  this.fitnessFunctions.size() - this.getNumberOfCoveredGoals());
		//suiteFitness.getFitness(best);
		return (T)best;
	}

	@Override
	public List<T> getBestIndividuals() {
		//get final test suite (i.e., non dominated solutions in Archive)
		TestSuiteChromosome bestTestCases = new TestSuiteChromosome();
		for (T test : getFinalTestSuite()) {
			bestTestCases.addTest((TestChromosome) test);
		}
		// compute overall fitness and coverage
		suiteFitness.getFitness(bestTestCases);

		List<T> bests = new ArrayList<T>();
		bests.add((T) bestTestCases);
		return bests;
	}

}
