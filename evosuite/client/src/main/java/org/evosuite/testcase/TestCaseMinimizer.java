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
package org.evosuite.testcase;

import org.evosuite.Properties;
import org.evosuite.TimeController;
import org.evosuite.ga.ConstructionFailedException;
import org.evosuite.ga.FitnessFunction;
import org.evosuite.ga.SecondaryObjective;
import org.evosuite.selenium.utils.CheckCondition;
import org.evosuite.selenium.utils.PageObjectTestCaseMinimizer;
import org.evosuite.testcase.execution.ExecutionResult;
import org.evosuite.testcase.execution.TestCaseExecutor;
import org.evosuite.testcase.statements.MethodStatement;
import org.evosuite.testcase.statements.Statement;
import org.evosuite.testcase.variable.VariableReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Remove all statements from a test case that do not contribute to the fitness
 * 
 * @author Gordon Fraser
 */
public class TestCaseMinimizer {

	private static final Logger logger = LoggerFactory.getLogger(TestCaseMinimizer.class);

	private final TestFitnessFunction fitnessFunction;

	/**
	 * Constructor
	 * 
	 * @param fitnessFunction
	 *            Fitness function with which to measure whether a statement is
	 *            necessary
	 */
	public TestCaseMinimizer(TestFitnessFunction fitnessFunction) {
		this.fitnessFunction = fitnessFunction;
	}

	/**
	 * Remove all unreferenced variables
	 * 
	 * @param t
	 *            The test case
	 * @return True if something was deleted
	 */
	public static boolean removeUnusedVariables(TestCase t) {
		List<Integer> to_delete = new ArrayList<Integer>();
		boolean has_deleted = false;

		int num = 0;
		for (Statement s : t) {
			VariableReference var = s.getReturnValue();
			if (!t.hasReferences(var)) {
				to_delete.add(num);
				has_deleted = true;
			}
			num++;
		}
		Collections.sort(to_delete, Collections.reverseOrder());
		for (Integer position : to_delete) {
			t.remove(position);
		}

		return has_deleted;
	}

	private static boolean isWorse(FitnessFunction<TestChromosome> fitness,
	        TestChromosome oldChromosome, TestChromosome newChromosome) {
		if (fitness.isMaximizationFunction()) {
			if (oldChromosome.getFitness(fitness) > fitness.getFitness(newChromosome))
				return true;
		} else {
			if (fitness.getFitness(newChromosome) > oldChromosome.getFitness(fitness))
				return true;
		}

		for (SecondaryObjective objective : TestChromosome.getSecondaryObjectives()) {
			if (objective.compareChromosomes(oldChromosome, newChromosome) < 0)
				return true;
		}

		return false;
	}
	
    private boolean isTimeoutReached() {
        return !TimeController.getInstance().isThereStillTimeInThisPhase();
    }
    
    /**
	 * @param c
	 *            a {@link org.evosuite.testcase.TestChromosome} object.
	 */
	public TestChromosome minimizeSelenium(TestChromosome c, TestChromosome notCloned, int idTestCaseBeforeCloning) {
		if (!Properties.MINIMIZE) {
			return c;
		}
		logger.info("Minimizing selenium test case: \n {}", c.getTestCase().toCode());

		double fitness = fitnessFunction.getFitness(c);

		if(fitness != 0.0){
			logger.error("minimizeSelenium - goal " + fitnessFunction + " is covered by test case with Id " + idTestCaseBeforeCloning + "? "
					+ notCloned.getTestCase().isGoalCovered(fitnessFunction) + ". The same goal is covered by cloned test case? "
					+ c.getTestCase().isGoalCovered(fitnessFunction));
			logger.error("minimizeSelenium - fitness value of goal " + fitnessFunction + " given test case with Id "
					+ idTestCaseBeforeCloning + " before minimization must be 0.0, found: " + fitness + ". Return not minimized test case.");
			throw new IllegalStateException("minimizeSelenium - fitness must be 0.0");
		}

//		CheckCondition.checkState(fitness == 0.0, "minimizeSelenium - fitness value of goal " + fitnessFunction + " given test case with Id "
//				+ idTestCaseBeforeCloning + " before minimization must be 0.0, found: " + fitness + ".");

		if (isTimeoutReached()) {
			return c;
		}

		assert ConstraintVerifier.verifyTest(c);
		
		if (isTimeoutReached()) {
			logger.debug("Timeout reached after verifying test");
			return c;
		}

		PageObjectTestCaseMinimizer pageObjectTestCaseMinimizer = new PageObjectTestCaseMinimizer();
		TestChromosome testChromosomeMinimized = pageObjectTestCaseMinimizer.minimizeIndividual(c);

		// needed in order not to filter custom exceptions (see property CUSTOM_EXCEPTIONS) when the test case is executed
		// in fact for timing reasons the minimized test case may still throw custom exceptions; if it does and the test case
		// doesn't fail after a max number of attempts it is fine; otherwise there is a page object bug
		testChromosomeMinimized.getTestCase().setMinimized(true);

		logger.info("Minimized selenium test case: \n {}", testChromosomeMinimized.getTestCase().toCode());

		logger.info("Executing minimized selenium test case to get its fitness");
		double fitnessAfterMinimization = fitnessFunction.getFitness(testChromosomeMinimized);

		if(fitnessAfterMinimization != fitness){
			logger.error("minimizeSelenium - Minimization bug: minimization modified " + fitnessFunction
					+ " fitness from " + fitness + " to " + fitnessAfterMinimization
					+ ". Test case before: \n" + c.getTestCase().toCode()
					+ "\n Test case after: \n" + testChromosomeMinimized.getTestCase().toCode());
//			throw new IllegalStateException("Minimization must not modify the fitness");
			// test case is not minimized -> minimization failed. When this test case is executed will throw
			// custom exceptions. Therefore these exceptions have to be filtered when the test case is executed.
			// The test case will be printed with try catches if a statement throws exceptions
			c.getTestCase().setMinimizedFailed(true);
			return c;
		}

		//TODO: add back this check
//		assert  (fitnessFunction.isMaximizationFunction() ?
//				fitnessFunction.getFitness(c) >= fitness : fitnessFunction.getFitness(c) <= fitness)
//				:
//				"Minimization worsened " + fitnessFunction.getClass().getName()+" fitness from "+fitness+
//						" to "+fitnessFunction.getFitness(c)+" on test "+c.getTestCase().toCode();


//		if (Properties.MINIMIZE_VALUES) {
//			logger.info("Minimizing values of test case");
//			ValueMinimizer minimizer = new ValueMinimizer();
//			minimizer.minimize(c, fitnessFunction);
//		}

		assert ConstraintVerifier.verifyTest(c);

		return testChromosomeMinimized;

	}
	
	/**
	 * Central minimization function. Loop and try to remove until all
	 * statements have been checked.
	 * 
	 * @param c
	 *            a {@link org.evosuite.testcase.TestChromosome} object.
	 */
	public void minimize(TestChromosome c) {
		if (!Properties.MINIMIZE) {
			return;
		}
		logger.info("Minimizing test case: " + c.test.toCode());


		double fitness = fitnessFunction.getFitness(c);
		if (isTimeoutReached()) {
			return;
		}

		logger.debug("Start fitness values: {}", fitness);

		//logger.debug("#### TestCaseNotMinimized : {}", c.getTestCase().toCode());
		
		assert ConstraintVerifier.verifyTest(c);
		
		if (isTimeoutReached()) {
			logger.debug("Timeout reached after verifying test");
			return;
		}

		boolean changed = true;

		while (changed) {
			changed = false;

			for (int i = c.test.size() - 1; i >= 0; i--) {
				if (isTimeoutReached()) {
					logger.debug("Timeout reached before minimizing statement {}", c.test.getStatement(i).getCode());
					return;
				}

				logger.debug("Deleting statement {}", c.test.getStatement(i).getCode());
				TestChromosome copy = (TestChromosome) c.clone();
				boolean modified;
				try {
					modified = TestFactory.getInstance().deleteStatementGracefully(c.test, i);
				} catch (ConstructionFailedException e) {
					modified = false;
				}

				if(!modified){
					c.setChanged(false);
					c.test = copy.test;
					logger.debug("Deleting failed");
					continue;
				}

				c.setChanged(true);

				if (isTimeoutReached()) {
					logger.debug("Keeping original version due to timeout");
					restoreTestCase(c, copy);
					return;
				}

				if (!isWorse(fitnessFunction, copy, c)) {
					logger.debug("Keeping shorter version");
					changed = true;
					break;
				} else {
					logger.debug("Keeping original version");
					restoreTestCase(c, copy);
				}
			}
		}


		//TODO: add back this check
		assert  (fitnessFunction.isMaximizationFunction() ?
				fitnessFunction.getFitness(c) >= fitness : fitnessFunction.getFitness(c) <= fitness)
				:
				"Minimization worsened " + fitnessFunction.getClass().getName()+" fitness from "+fitness+
						" to "+fitnessFunction.getFitness(c)+" on test "+c.getTestCase().toCode();


		if (Properties.MINIMIZE_VALUES) {
			logger.info("Minimizing values of test case");
			ValueMinimizer minimizer = new ValueMinimizer();
			minimizer.minimize(c, fitnessFunction);
		}

		assert ConstraintVerifier.verifyTest(c);

		if (logger.isDebugEnabled()) {
			logger.debug("Minimized test case: ");
			logger.debug(c.test.toCode());
		}
	}
	
	private void executeTestCase(TestChromosome c){
		//execute test c
	}

	private static void restoreTestCase(TestChromosome c, TestChromosome copy) {
		c.test = copy.test;
		c.copyCachedResults(copy);
		//c.setFitness(copy.getFitness());
		c.setFitnessValues(copy.getFitnessValues());
		c.setPreviousFitnessValues(copy.getPreviousFitnessValues());
		c.setChanged(false);
	}

}
