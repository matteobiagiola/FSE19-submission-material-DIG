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
package org.evosuite.strategy;

import org.evosuite.Properties;
import org.evosuite.coverage.TestFitnessFactory;
import org.evosuite.coverage.archive.ArchiveTestChromosomeFactory;
import org.evosuite.ga.ChromosomeFactory;
import org.evosuite.ga.FitnessFunction;
import org.evosuite.ga.metaheuristics.GeneticAlgorithm;
import org.evosuite.ga.stoppingconditions.MaxStatementsStoppingCondition;
import org.evosuite.result.TestGenerationResultBuilder;
import org.evosuite.rmi.ClientServices;
import org.evosuite.rmi.service.ClientState;
import org.evosuite.selenium.utils.TestCaseStatistics;
import org.evosuite.statistics.RuntimeVariable;
import org.evosuite.statistics.StatisticsSender;
import org.evosuite.testcase.TestChromosome;
import org.evosuite.testcase.TestFitnessFunction;
import org.evosuite.testcase.execution.ExecutionTracer;
import org.evosuite.testcase.factories.AllMethodsTestChromosomeFactory;
import org.evosuite.testcase.factories.JUnitTestCarvedChromosomeFactory;
import org.evosuite.testcase.factories.RandomLengthTestFactory;
import org.evosuite.testsuite.TestSuiteChromosome;
import org.evosuite.testsuite.TestSuiteFitnessFunction;
import org.evosuite.utils.ArrayUtil;
import org.evosuite.utils.LoggingUtils;
import org.evosuite.utils.Randomness;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Iteratively generate random tests. If adding the random test
 * leads to improved fitness, keep it, otherwise drop it again.
 *
 */
public class AdaptiveRandomTestStrategy extends TestGenerationStrategy {

	private static final Logger logger = LoggerFactory.getLogger(AdaptiveRandomTestStrategy.class);
	
	@Override
	public TestSuiteChromosome generateTests(){
		// Set up search algorithm
		PropertiesSuiteGAFactory algorithmFactory = new PropertiesSuiteGAFactory();

		Properties.ALGORITHM = Properties.Algorithm.ADAPTIVE;
		GeneticAlgorithm<TestSuiteChromosome> algorithm = algorithmFactory.getSearchAlgorithm();

		ChromosomeFactory factory = new AllMethodsTestChromosomeFactory();
		algorithm.setChromosomeFactory(factory);

		if(Properties.SERIALIZE_GA || Properties.CLIENT_ON_THREAD)
			TestGenerationResultBuilder.getInstance().setGeneticAlgorithm(algorithm);

		long startTime = System.currentTimeMillis() / 1000;

		// What's the search target
		List<TestFitnessFactory<? extends TestFitnessFunction>> goalFactories = getFitnessFactories();
		List<TestFitnessFunction> fitnessFunctions = new ArrayList<TestFitnessFunction>();
		for (TestFitnessFactory<? extends TestFitnessFunction> goalFactory : goalFactories) {
			fitnessFunctions.addAll(goalFactory.getCoverageGoals());
		}
		algorithm.addFitnessFunctions((List)fitnessFunctions);

		// if (Properties.SHOW_PROGRESS && !logger.isInfoEnabled())
		algorithm.addListener(progressMonitor); // FIXME progressMonitor may cause
		// client hang if EvoSuite is
		// executed with -prefix!

//		List<TestFitnessFunction> goals = getGoals(true);

		LoggingUtils.getEvoLogger().info("* Total number of test goals for Adaptive: {}", fitnessFunctions.size());

//		ga.setChromosomeFactory(getChromosomeFactory(fitnessFunctions.get(0))); // FIXME: just one fitness function?

//		if (Properties.SHOW_PROGRESS && !logger.isInfoEnabled())
//			ga.addListener(progressMonitor); // FIXME progressMonitor may cause

		if (ArrayUtil.contains(Properties.CRITERION, Properties.Criterion.DEFUSE) ||
				ArrayUtil.contains(Properties.CRITERION, Properties.Criterion.ALLDEFS) ||
				ArrayUtil.contains(Properties.CRITERION, Properties.Criterion.STATEMENT) ||
				ArrayUtil.contains(Properties.CRITERION, Properties.Criterion.RHO) ||
				ArrayUtil.contains(Properties.CRITERION, Properties.Criterion.BRANCH) ||
				ArrayUtil.contains(Properties.CRITERION, Properties.Criterion.AMBIGUITY))
			ExecutionTracer.enableTraceCalls();

		algorithm.resetStoppingConditions();

		ClientServices.getInstance().getClientNode().trackOutputVariable(RuntimeVariable.Total_Goals, fitnessFunctions.size());
		TestSuiteChromosome testSuite = null;

		if (!(Properties.STOP_ZERO && fitnessFunctions.isEmpty()) || ArrayUtil.contains(Properties.CRITERION, Properties.Criterion.EXCEPTION)) {
			// Perform search
			LoggingUtils.getEvoLogger().info("* Using seed {}", Randomness.getSeed());
			LoggingUtils.getEvoLogger().info("* Starting evolution");
			ClientServices.getInstance().getClientNode().changeState(ClientState.SEARCH);

			algorithm.generateSolution();
			List<TestSuiteChromosome> bestSuites = (List<TestSuiteChromosome>) algorithm.getBestIndividuals();
			if (bestSuites.isEmpty()) {
				LoggingUtils.getEvoLogger().warn("Could not find any suitable chromosome");
				return new TestSuiteChromosome();
			}else{
				testSuite = bestSuites.get(0);
			}
		} else {
			zeroFitness.setFinished();
			testSuite = new TestSuiteChromosome();
			for (FitnessFunction<?> ff : testSuite.getFitnessValues().keySet()) {
				testSuite.setCoverage(ff, 1.0);
			}
		}

		long endTime = System.currentTimeMillis() / 1000;

//		goals = getGoals(false); //recalculated now after the search, eg to handle exception fitness
//        ClientServices.getInstance().getClientNode().trackOutputVariable(RuntimeVariable.Total_Goals, goals.size());

		// Newline after progress bar
		if (Properties.SHOW_PROGRESS)
			LoggingUtils.getEvoLogger().info("");

		String text = " statements, best individual has fitness: ";
		LoggingUtils.getEvoLogger().info("* Search finished after "
				+ (endTime - startTime)
				+ "s and "
				+ algorithm.getAge()
				+ " generations, "
				+ MaxStatementsStoppingCondition.getNumExecutedStatements()
				+ text
				+ testSuite.getFitness());
		// Search is finished, send statistics
		sendExecutionStatistics();

		List<Long> distanceComputationTimes = TestCaseStatistics.getInstance().getDistanceComputationTimes();
		List<Long> testExecutionTimes = TestCaseStatistics.getInstance().getTestExecutionTimes();

		logger.info("Statistics adaptive random search: ");
		logger.info("Statistics adaptive random search - Already executed test cases size: " + TestCaseStatistics.getInstance().getAlreadyExecutedTestCasesSize());
		logger.info("Statistics adaptive random search - Distance time vector: " + distanceComputationTimes + ".");
		logger.info("Statistics adaptive random search - Average time to compute distance: "
				+ (distanceComputationTimes.stream().mapToLong(Long::longValue).sum() / distanceComputationTimes.size()) + "ms.");
		logger.info("Statistics adaptive random search - Test execution time: " + testExecutionTimes + ".");
		logger.info("Statistics adaptive random search - Average time to execute a test case: "
				+ (testExecutionTimes.stream().mapToLong(Long::longValue).sum() / testExecutionTimes.size()) + "ms.");

		return testSuite;
	}

	/*public TestSuiteChromosome generateTests() {
		LoggingUtils.getEvoLogger().info("* Using adaptive random test generation");

		List<TestSuiteFitnessFunction> fitnessFunctions = getFitnessFunctions();

		TestSuiteChromosome suite = new TestSuiteChromosome();
		for (TestSuiteFitnessFunction fitnessFunction : fitnessFunctions)
			suite.addFitness( fitnessFunction);

		List<TestFitnessFactory<? extends TestFitnessFunction>> goalFactories = getFitnessFactories();
		List<TestFitnessFunction> goals = new ArrayList<TestFitnessFunction>();
		LoggingUtils.getEvoLogger().info("* Total number of test goals: ");
		for (TestFitnessFactory<? extends TestFitnessFunction> goalFactory : goalFactories) {
			goals.addAll(goalFactory.getCoverageGoals());
			LoggingUtils.getEvoLogger().info("  - " + goalFactory.getClass().getSimpleName().replace("CoverageFactory", "")
					+ " " + goalFactory.getCoverageGoals().size());
		}
		ClientServices.getInstance().getClientNode().trackOutputVariable(RuntimeVariable.Total_Goals,
				goals.size());

		if(!canGenerateTestsForSUT()) {
			LoggingUtils.getEvoLogger().info("* Found no testable methods in the target class "
					+ Properties.TARGET_CLASS);
			return new TestSuiteChromosome();
		}
		ChromosomeFactory<TestChromosome> factory = getChromosomeFactory();

		StoppingCondition stoppingCondition = getStoppingCondition();
		for (FitnessFunction<?> fitness_function : fitnessFunctions)
			((TestSuiteFitnessFunction)fitness_function).getFitness(suite);
		ClientServices.getInstance().getClientNode().changeState(ClientState.SEARCH);

		List<TestChromosome> alreadyExecutedTestCases = new ArrayList<TestChromosome>();
		TestChromosome testChromosome = factory.getChromosome();
		this.executeTest(testChromosome,suite,fitnessFunctions,alreadyExecutedTestCases);

		//TODO set as a java property in the near future
		int k = 30;
		while (!isFinished(suite, stoppingCondition)){
			int iteration = 0;
			List<TestChromosome> candidates = new ArrayList<TestChromosome>();
			for (int i = 0; i < k; i++) {
				TestChromosome candidate = factory.getChromosome();
				candidates.add(candidate);
			}
			DistanceComputation distanceComputation = new DistanceComputation(candidates,alreadyExecutedTestCases,iteration);
			TestChromosome testCaseToExecute = distanceComputation.getTestCaseToExecute();
			logger.debug("Going to execute test case: " + testCaseToExecute.getTestCase().getID());
			this.executeTest(testCaseToExecute,suite,fitnessFunctions,alreadyExecutedTestCases);
			iteration++;
		}

		//while (!isFinished(suite, stoppingCondition)) {
			//TestChromosome test = factory.getChromosome();
			//TestSuiteChromosome clone = suite.clone();
			//clone.addTest(test);
			//for (FitnessFunction<?> fitness_function : fitnessFunctions) {
				//((TestSuiteFitnessFunction)fitness_function).getFitness(clone);
				//logger.debug("Old fitness: {}, new fitness: {}", suite.getFitness(),
						//clone.getFitness());
			//}
			//if (clone.compareTo(suite) < 0) {
				//suite = clone;
				//StatisticsSender.executedAndThenSendIndividualToMaster(clone);
			//}
		//}

		//statistics.searchFinished(suiteGA);
		LoggingUtils.getEvoLogger().info("* Search Budget:");
		LoggingUtils.getEvoLogger().info("\t- " + stoppingCondition.toString());
		
		// In the GA, these statistics are sent via the SearchListener when notified about the GA completing
		// Search is finished, send statistics
		sendExecutionStatistics();

		// TODO: Check this: Fitness_Evaluations = getNumExecutedTests?
		ClientServices.getInstance().getClientNode().trackOutputVariable(RuntimeVariable.Fitness_Evaluations, MaxTestsStoppingCondition.getNumExecutedTests());

		return suite;	
	}*/

	private void executeTest(TestChromosome test,
							 TestSuiteChromosome suite,
							 List<TestSuiteFitnessFunction> fitnessFunctions,
							 List<TestChromosome> alreadyExecutedTestCases){
		TestSuiteChromosome clone = suite.clone();
		clone.addTest(test);
		for (FitnessFunction<?> fitness_function : fitnessFunctions) {
			((TestSuiteFitnessFunction)fitness_function).getFitness(clone);
			logger.debug("Old fitness: {}, new fitness: {}", suite.getFitness(),
					clone.getFitness());
		}
		if (clone.compareTo(suite) < 0) {
			suite = clone;
			StatisticsSender.executedAndThenSendIndividualToMaster(clone);
		}
		alreadyExecutedTestCases.add(test);
	}


	protected ChromosomeFactory<TestChromosome> getChromosomeFactory() {
		switch (Properties.TEST_FACTORY) {
		case ALLMETHODS:
			return new AllMethodsTestChromosomeFactory();
		case RANDOM:
			return new RandomLengthTestFactory();
		case ARCHIVE:
			return new ArchiveTestChromosomeFactory();
		case JUNIT:
			return new JUnitTestCarvedChromosomeFactory(
					new RandomLengthTestFactory());
		default:
			throw new RuntimeException("Unsupported test factory: "
					+ Properties.TEST_FACTORY);
		}
		
	}
	

	
}
