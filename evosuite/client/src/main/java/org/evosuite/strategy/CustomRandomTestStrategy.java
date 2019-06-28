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
import org.evosuite.statistics.RuntimeVariable;
import org.evosuite.testcase.TestChromosome;
import org.evosuite.testcase.TestFitnessFunction;
import org.evosuite.testcase.execution.ExecutionTracer;
import org.evosuite.testcase.factories.AllMethodsTestChromosomeFactory;
import org.evosuite.testcase.factories.JUnitTestCarvedChromosomeFactory;
import org.evosuite.testcase.factories.RandomLengthTestFactory;
import org.evosuite.testsuite.TestSuiteChromosome;
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
 * @author gordon
 *
 */
public class CustomRandomTestStrategy extends TestGenerationStrategy {

	private static final Logger logger = LoggerFactory.getLogger(CustomRandomTestStrategy.class);

	@Override
	public TestSuiteChromosome generateTests() {
		// Set up search algorithm
		PropertiesSuiteGAFactory algorithmFactory = new PropertiesSuiteGAFactory();

		Properties.ALGORITHM = Properties.Algorithm.CUSTOM_RANDOM;
		GeneticAlgorithm<TestSuiteChromosome> algorithm = algorithmFactory.getSearchAlgorithm();

		// Override chromosome factory
		// TODO handle this better by introducing generics

		//change: using AllMethodsTestChromosomeFactory
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

		LoggingUtils.getEvoLogger().info("* Total number of test goals for RANDOM: {}", fitnessFunctions.size());

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

		return testSuite;
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
