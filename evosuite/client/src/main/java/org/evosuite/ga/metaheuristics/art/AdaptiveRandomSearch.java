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
package org.evosuite.ga.metaheuristics.art;

import org.evosuite.ProgressMonitor;
import org.evosuite.Properties;
import org.evosuite.coverage.FitnessFunctions;
import org.evosuite.ga.Chromosome;
import org.evosuite.ga.ChromosomeFactory;
import org.evosuite.ga.FitnessFunction;
import org.evosuite.ga.metaheuristics.GeneticAlgorithm;
import org.evosuite.ga.metaheuristics.SearchListener;
import org.evosuite.ga.metaheuristics.art.distance.DistanceComputation;
import org.evosuite.ga.metaheuristics.art.distance.input.parsing.Parser;
import org.evosuite.ga.metaheuristics.art.utils.CircularFifoQueue;
import org.evosuite.ga.metaheuristics.art.utils.PrintObjectCollection;
import org.evosuite.selenium.graph.GraphParser;
import org.evosuite.selenium.utils.CheckCondition;
import org.evosuite.selenium.utils.PageObjectTestCaseMinimizer;
import org.evosuite.selenium.utils.TestCaseStatistics;
import org.evosuite.testcase.TestCase;
import org.evosuite.testcase.TestChromosome;
import org.evosuite.testcase.TestFitnessFunction;
import org.evosuite.testcase.statements.MethodStatement;
import org.evosuite.testcase.statements.Statement;
import org.evosuite.testsuite.TestSuiteChromosome;
import org.evosuite.testsuite.TestSuiteFitnessFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * <p>
 * RandomSearch class.
 * </p>
 * 
 * @author Gordon Fraser
 */
public class AdaptiveRandomSearch<T extends Chromosome> extends GeneticAlgorithm<T> {

	private static final Logger logger = LoggerFactory.getLogger(AdaptiveRandomSearch.class);

	/** Map used to store the covered test goals (keys of the map) and the corresponding covering test cases (values of the map) **/
	protected Map<FitnessFunction<T>, T> archive = new  HashMap<FitnessFunction<T>, T>();

	private List<T> alreadyExecutedTestCases;

	/**
	 * keep track of overall suite fitness and coverage
	 */
	protected TestSuiteFitnessFunction suiteFitness;

	/**
	 * Boolean vector to indicate whether each test goal is covered or not.
	 */
	protected Set<FitnessFunction<T>> uncoveredGoals = new HashSet<FitnessFunction<T>>();

	/**
	 * <p>
	 * Constructor for RandomSearch.
	 * </p>
	 *
	 * @param factory
	 *            a {@link ChromosomeFactory} object.
	 */
	public AdaptiveRandomSearch(ChromosomeFactory<T> factory) {
		super(factory);
		if(Properties.CRITERION.length == 1){
			suiteFitness = FitnessFunctions.getFitnessFunction(Properties.CRITERION[0]);
			logger.info("SuiteFitness AdaptiveRandomSearch: " + suiteFitness.getClass());
			if(Properties.QUEUE_ART){
			    this.alreadyExecutedTestCases = new CircularFifoQueue<>(Properties.QUEUE_CAPACITY);
            }else{
			    this.alreadyExecutedTestCases = new ArrayList<>();
            }
		}else{
			String criteria = Arrays.stream(Properties.CRITERION)
					.map(String::valueOf).collect(Collectors.joining(":"));
			throw new IllegalStateException(this.getClass() + " constructor: AdaptiveRandomSearch supports " +
					"only one search criterion. Found " + Properties.CRITERION.length + " - "
					+ criteria);
		}
	}

	private static final long serialVersionUID = -7685015421245920459L;

	/* (non-Javadoc)
	 * @see org.evosuite.ga.GeneticAlgorithm#evolve()
	 */
	/** {@inheritDoc} */
	@Override
	protected void evolve() {
		int k = Properties.ART_ALGORITHM_NUM_CANDIDATES;
		List<T> candidates = new ArrayList<T>();
		for (int i = 0; i < k; i++) {
			T candidate = chromosomeFactory.getChromosome();
			if(!(candidate instanceof TestChromosome)){
				throw new IllegalStateException(this.getClass().getName() + " evolve: adaptive random testing solution " +
						"currently supported only for evolution of test cases");
			}
			candidates.add(candidate);
		}


		long startDistanceTime = System.nanoTime();
		//logger.debug("Start distance time computation");
		DistanceComputation<T> distanceComputation = new DistanceComputation<>(candidates,alreadyExecutedTestCases,currentIteration);
		T testCaseToExecute = distanceComputation.getTestCaseToExecute();
		long timeElapsedDistanceInNs = (System.nanoTime() - startDistanceTime);
		long timeElapsedDistanceInMs = TimeUnit.NANOSECONDS.toMillis(timeElapsedDistanceInNs);
		TestCaseStatistics.getInstance().storeDistanceComputationTime(timeElapsedDistanceInMs);
		//logger.debug("Time to compute distance: " + timeElapsedDistanceInMs);

		long startExecutionTime = System.nanoTime();
		//logger.debug("Start execute time computation");
		this.calculateFitness(testCaseToExecute);
		long timeElapsedExecutionInNs = (System.nanoTime() - startExecutionTime);
		long timeElapsedExecutionInMs = TimeUnit.NANOSECONDS.toMillis(timeElapsedExecutionInNs);
		TestCaseStatistics.getInstance().storeTestExecutionTime(timeElapsedExecutionInMs);
		//logger.debug("Time to execute a test case: " + timeElapsedExecutionInMs);

		if(Properties.CUT_EXCEPTIONS){
			PageObjectTestCaseMinimizer pageObjectTestCaseMinimizer = new PageObjectTestCaseMinimizer();
			CheckCondition.checkState(testCaseToExecute instanceof TestChromosome,
					" evolve: required TestChromosome, found " + testCaseToExecute.getClass());
			TestChromosome minimizedIndividual = pageObjectTestCaseMinimizer.minimizeIndividual((TestChromosome) testCaseToExecute);
			List<Integer> methodCallPositionsAfterMinimization = this.getMethodCallPositions(minimizedIndividual.getTestCase());
			logger.debug("evolve: test case minimized " + minimizedIndividual);
			if(methodCallPositionsAfterMinimization.size() == 0){
				logger.warn("evolve: minimized individual has no method calls. It will not be included in the already executed test cases list.");
			}else{
				this.alreadyExecutedTestCases.add((T) minimizedIndividual);
			}
		}else{
			this.alreadyExecutedTestCases.add(testCaseToExecute);
		}

		//replace old solution with the new one
//		population.set(0,testCaseToExecute);

		//update covered goals with the ones covered by the last solution
//		updateCoveredGoals(population);

		//update archive based on all solutions (all executed test cases)
//		updateArchive(this.alreadyExecutedTestCases);

		currentIteration++;

		logger.debug("Generation=" + currentIteration + " Archive size=" + archive.size());
	}

	private List<Integer> getMethodCallPositions(TestCase tc){
		List<Integer> methodCallPositions = new ArrayList<>();
		int stmtPosition = 0;
		while(tc.hasStatement(stmtPosition)){
			Statement statement = tc.getStatement(stmtPosition);
			if(statement instanceof MethodStatement) {
				MethodStatement methodStatement = (MethodStatement) statement;
				String graphEdge = GraphParser.fromMethodToEdge(methodStatement);
				if (!graphEdge.isEmpty()) {
					logger.debug("getMethodCallPositions: adding method statement " + methodStatement);
					methodCallPositions.add(stmtPosition);
				}
			}
			stmtPosition++;
		}
		return methodCallPositions;
	}


	/* (non-Javadoc)
	 * @see org.evosuite.ga.GeneticAlgorithm#initializePopulation()
	 */
	/** {@inheritDoc} */
	@Override
	public void initializePopulation() {
		logger.info("executing initializePopulation function");

		notifySearchStarted();
		currentIteration = 0;

		if(Properties.CUT_EXCEPTIONS){
			this.createAndMinimizeFirstIndividual();
		}else{
			// Create a random parent population P0
			generateInitialPopulation(1);
			// Determine fitness
			calculateFitness();
			this.alreadyExecutedTestCases.add(population.get(0));
		}

		this.notifyIteration();
	}

	private void createAndMinimizeFirstIndividual(){
		// Create a random parent population P0
		generateInitialPopulation(1);
		// Determine fitness
		calculateFitness();
		PageObjectTestCaseMinimizer pageObjectTestCaseMinimizer = new PageObjectTestCaseMinimizer();
		CheckCondition.checkState(population.get(0) instanceof TestChromosome,
				" createAndMinimizeFirstIndividual: required TestChromosome, found " + population.get(0).getClass());
		TestChromosome minimizedIndividual = pageObjectTestCaseMinimizer.minimizeIndividual((TestChromosome) population.get(0));
		List<Integer> methodCallPositionsAfterMinimization = this.getMethodCallPositions(minimizedIndividual.getTestCase());
		logger.debug("createAndMinimizeFirstIndividual: test case minimized " + minimizedIndividual);
		if(methodCallPositionsAfterMinimization.size() == 0){
			clearPopulation();
			this.createAndMinimizeFirstIndividual();
		}else{
			this.alreadyExecutedTestCases.add((T) minimizedIndividual);
		}
	}

	/* (non-Javadoc)
	 * @see org.evosuite.ga.GeneticAlgorithm#generateSolution()
	 */
	/** {@inheritDoc} */
	@Override
	public void generateSolution() {
		logger.info("executing generateSolution function");

		// keep track of covered goals
		for (FitnessFunction<T> goal : fitnessFunctions) {
			uncoveredGoals.add(goal);
		}

		if(Properties.INPUT_DISTANCE){
			logger.info("Input distance enabled");
			String javaProjectDirectoryStructure = "src/main/java";
			String sourceCodeDirectory = System.getProperty("user.home")
					+ "/" + Properties.SOURCE_CODE_SYSTEM_PATH
					+ "/" + javaProjectDirectoryStructure;
			Parser parser = new Parser(sourceCodeDirectory);
			parser.parseClasses();
		}

		notifySearchStarted();
		if (population.isEmpty())
			initializePopulation();

		//update archive with covered goals (the ones that, for each goal, has fitness 0.0)
//		updateCoveredGoals(population);
		// update archive with goals covered so far (during initialization)
//		updateArchive(population);

		while (!isFinished() && this.getNumberOfCoveredGoals() < this.fitnessFunctions.size()) {
			evolve();
			this.notifyIteration();
		}

		TestCaseStatistics.getInstance().setAlreadyExecutedTestCasesSize(this.alreadyExecutedTestCases.size());

		//updateBestIndividualFromArchive();
		completeCalculateFitness();
		notifySearchFinished();
	}

	/*ADDED ---------------------------------------------------------------------------------------------------*/

	private void printArchive(){
		List<T> listArchive = this.getArchive();
		PrintObjectCollection.print(listArchive,this.getClass(),"Archive adaptive random testing");
	}

	/**
	 * Notify all search listeners of fitness evaluation
	 *
	 * @param chromosome
	 *            a {@link org.evosuite.ga.Chromosome} object.
	 */
	@Override
	protected void notifyEvaluation(Chromosome chromosome) {
		for (SearchListener listener : listeners) {
			if (listener instanceof ProgressMonitor)
				continue;
			listener.fitnessEvaluation(chromosome);
		}
	}

	/**
	 * This method computes the fitness scores only for the uncovered goals
	 * @param c chromosome
	 */
	private void calculateFitness(T c) {
		for (FitnessFunction<T> fitnessFunction : this.fitnessFunctions) {
			// compute the fitness function only for uncovered goals
			// while for branch coverage this leads to slight improvements
			// for mutation coverage this save much time
			double value = fitnessFunction.getFitness(c);
			if (value == 0.0) {
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
	 * @param solutionSet is the list of Chromosomes (poulation)
	 */
//	private void updateArchive(List<T> solutionSet) {
//		// store the test cases that are optimal for the test goal in the
//		// archive
//		for (FitnessFunction<T> entry : this.getCoveredGoals()){
//			double bestSize = this.archive.get(entry).size();
//			for (T solution : solutionSet) {
//				double value = entry.getFitness(solution);
//				double size = solution.size();
//				if (value == 0.0 && size < bestSize) {
//					this.archive.put(entry, solution);
//					bestSize = size;
//				}
//			}
//		}
//		//this.printArchive();
//		logger.debug("UpdateArchive function covered goals size: " + this.getCoveredGoals().size());
//		//PrintObjectCollection.print(this.getCoveredGoals(),this.getClass(),"Covered goals archive");
//		this.uncoveredGoals.removeAll(this.getCoveredGoals());
//	}

	/**
	 * This method update the archive by adding test cases that cover new test goals, or replacing the
	 * old tests if the new ones are smaller (at the same level of coverage).
	 */
	private void updateArchive(T solution, FitnessFunction<T> covered) {
		// the next two lines are needed since that coverage information are used
		// during EvoSuite post-processing
		TestChromosome tch = (TestChromosome) solution;
		tch.getTestCase().addCoveredGoal((TestFitnessFunction) covered);

		// store the test cases that are optimal for the test goal in the
		// archive
		if (archive.containsKey(covered)) {
			TestChromosome existingSolution = (TestChromosome) this.archive.get(covered);
			// if the new solution is better (based on secondary criterion), then the archive must be updated
			if (solution.compareSecondaryObjective(existingSolution) < 0) {
				this.archive.put(covered, solution);
			}
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

	/**
	 * This method is used by the Progress Monitor at the and of each generation to show the total coverage reached by the algorithm.
	 * Since the Progress Monitor need a "Suite", this method artificially creates a "SuiteChromosome" (see {@link AdaptiveRandomSearch#suiteFitness})
	 * as the union of all test cases stored in {@link AdaptiveRandomSearch#archive}.
	 *
	 * The coverage score of the "SuiteChromosome" is given by the percentage of test goals covered (goals in {@link AdaptiveRandomSearch#archive})
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

//	private void updateCoveredGoals(List<T> solutionSet){
////		logger.info("Update covered goals function");
//		Map<FitnessFunction<T>, T> newCoveredGoals = new HashMap<FitnessFunction<T>, T>();
//		for (FitnessFunction<T> entry : this.uncoveredGoals) {
////			logger.info("Goal " + entry.toString() + " is covered?");
//			double best_size = Double.MAX_VALUE;
//			double minimumValues = Double.MAX_VALUE;
//			T best = null;
//			for (T solution : solutionSet) {
//				double value = solution.getFitness(entry);
//				double size = solution.size();
////				logger.info("Solution, fitness value: " + value + " size: " + size);
//				if (value < minimumValues || (value == minimumValues && size < best_size)) {
//					minimumValues = value;
//					best_size = size;
//					best = solution;
//				}
//			}
////			logger.info("MinimiumValues: " + minimumValues);
//			if (minimumValues == 0.0){
////				logger.info("Goal " + entry.toString() + " IS covered");
//				newCoveredGoals.put(entry, best);
//			}else{
////				logger.info("Goal " + entry.toString() + " is NOT covered");
//			}
//		}
//		this.archive.putAll(newCoveredGoals);
//	}

}
