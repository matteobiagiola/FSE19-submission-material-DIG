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
package org.evosuite.testcase.factories;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.evosuite.Properties;
import org.evosuite.ga.ChromosomeFactory;
import org.evosuite.ga.ConstructionFailedException;
import org.evosuite.selenium.graph.GraphIterator;
import org.evosuite.selenium.graph.GraphParser;
import org.evosuite.selenium.graph.Importer;
import org.evosuite.selenium.graph.PathGenerator;
import org.evosuite.selenium.graph.PathListener;
import org.evosuite.selenium.graph.RandomWalkIterator;
import org.evosuite.setup.TestCluster;
import org.evosuite.testcase.DefaultTestCase;
import org.evosuite.testcase.TestCase;
import org.evosuite.testcase.TestChromosome;
import org.evosuite.testcase.TestFactory;
import org.evosuite.testcase.execution.ExecutionTracer;
import org.evosuite.testcase.statements.ConstructorStatement;
import org.evosuite.testcase.variable.VariableReference;
import org.evosuite.utils.generic.GenericAccessibleObject;
import org.evosuite.utils.generic.GenericConstructor;
import org.evosuite.utils.generic.GenericMethod;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DirectedPseudograph;
import org.evosuite.utils.Randomness;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * AllMethodsTestChromosomeFactory class.
 * </p>
 * 
 * @author Gordon Fraser
 */
public class AllMethodsTestChromosomeFactory implements ChromosomeFactory<TestChromosome> {

	private static final long serialVersionUID = -420224349882780856L;

	/** Constant <code>logger</code> */
	protected static final Logger logger = LoggerFactory.getLogger(AllMethodsTestChromosomeFactory.class);

	/** Methods we have already seen */
	private static Set<GenericAccessibleObject<?>> attemptedMethods = new LinkedHashSet<GenericAccessibleObject<?>>();

	/** Methods we have not already seen */
	private static Set<GenericAccessibleObject<?>> remainingMethods = new LinkedHashSet<GenericAccessibleObject<?>>();

	/** Methods we have to cover */
	private static List<GenericAccessibleObject<?>> allMethods = new LinkedList<GenericAccessibleObject<?>>();
	
	/** Methods we have to cover */
	private static List<GenericAccessibleObject<?>> allMethodsPathSequence = new LinkedList<GenericAccessibleObject<?>>();
	
	private static GenericConstructor constructor;
	
	private boolean considerRemainingMethodCalls = false;
	
	

	/**
	 * Create a list of all methods
	 */
	public AllMethodsTestChromosomeFactory() {
		allMethods.addAll(TestCluster.getInstance().getTestCalls());
		if(Properties.SELENIUM_WHOLE){
			String graphPath = System.getProperty("user.home") + "/" + Properties.GRAPH_PATH;
			logger.debug("@@@graphPath: " + graphPath);
			// import and set graph in a property of class Importer
			Importer.importGraphDotFile(graphPath);
			// allMethodsPathSequence.addAll(TestCluster.getInstance().getTestCalls());
			GenericAccessibleObject<?> constructorCall = null;
			for(GenericAccessibleObject<?> call: allMethods){
				if (call.isMethod()) {
					GenericMethod method = (GenericMethod) call;
					logger.info("####method: " + method.getName());
				} else if (call.isConstructor()) {
					constructorCall = call;
					constructor = (GenericConstructor) call;
					logger.info("####constructor: " + constructor.getName());
				}
			}
			allMethods.remove(constructorCall);
		}
		Randomness.shuffle(allMethods);
		reset();
	}

	/**
	 * Create a random individual
	 * 
	 * @param size
	 */
	private TestCase getRandomTestCase(int size) {
		boolean tracerEnabled = ExecutionTracer.isEnabled();
		if (tracerEnabled)
			ExecutionTracer.disable();

		TestCase test = getNewTestCase();
		int num = 0;

		// Choose a random length in 0 - size
		int length = Randomness.nextInt(size);
		while (length == 0)
			length = Randomness.nextInt(size);

		// Then add random stuff
		while (test.size() < length && num < Properties.MAX_ATTEMPTS) {
			// Select an uncovered method and add it

			if (remainingMethods.size() == 0) {
				reset();
			}
			GenericAccessibleObject<?> call = Randomness.choice(remainingMethods);
			attemptedMethods.add(call);
			remainingMethods.remove(call);

			try {
				TestFactory testFactory = TestFactory.getInstance();
				if (call.isMethod()) {
					testFactory.addMethod(test, (GenericMethod) call, test.size(), 0);
				}
				else if (call.isConstructor()) {
					testFactory.addConstructor(test, (GenericConstructor) call,
					                           test.size(), 0);
				} else {
					assert (false) : "Found test call that is neither method nor constructor";
				}
			} catch (ConstructionFailedException e) {
			}
			num++;
		}
		if (logger.isDebugEnabled())
			logger.debug("Randomized test case:" + test.toCode());

		if (tracerEnabled)
			ExecutionTracer.enable();

		return test;
	}
	
	/**
	 * Create a random individual with one constructor for each test case
	 * 
	 * @param size
	 */
	private TestCase getRandomTestCaseOneConstructor(int size) {
		boolean tracerEnabled = ExecutionTracer.isEnabled();
		if (tracerEnabled)
			ExecutionTracer.disable();

		TestCase test = getNewTestCase();
		int num = 0;

		// Choose a random length in 0 - size
		int length = Randomness.nextInt(size);
		while (length == 0)
			length = Randomness.nextInt(size);

		boolean firstMethodToAdd = true;
		// Then add random stuff
		while (test.size() < length && num < Properties.MAX_ATTEMPTS) {
			// Select an uncovered method and add it

			if (remainingMethods.size() == 0) {
				reset();
			}
			GenericAccessibleObject<?> call = Randomness.choice(remainingMethods);
			attemptedMethods.add(call);
			remainingMethods.remove(call);

			try {
				TestFactory testFactory = TestFactory.getInstance();

				//MODIFIED
				if (call.isMethod() && firstMethodToAdd) {
					testFactory.addMethod(test, (GenericMethod) call, test.size(), 0);
					firstMethodToAdd = false;
				}
				else if(call.isMethod())
				{
					GenericMethod method = (GenericMethod) call;
					ConstructorStatement cStmt = (ConstructorStatement) test.getStatement(0);
					VariableReference cStmtVar = cStmt.getReturnValue();
					testFactory.addMethodFor(test, cStmtVar, method, test.size());
				}
			} catch (ConstructionFailedException e) {
			}
			num++;
		}
		if (logger.isDebugEnabled())
			logger.debug("@@@@Randomized test case:" + test.toCode());

		if (tracerEnabled)
			ExecutionTracer.enable();

		return test;
	}
	
	/**
	 * Create a random individual with one constructor for each test case which respects graph sequences
	 * 
	 * @param size
	 */
	private TestCase getRandomTestPathSequence(int size, DirectedPseudograph<String,String> graph) {
		boolean tracerEnabled = ExecutionTracer.isEnabled();
		if (tracerEnabled)
			ExecutionTracer.disable();
		
		// Choose a random length in 1- size
		int length = Randomness.nextInt(1,size);
				
		String startNodeGraph = Properties.START_NODE;
		int maxSteps = length;
		boolean isWeighted = false;
		//List<String> edges = PathGenerator.generateRandomPath(graph, startNodeGraph, isWeighted, maxSteps);
		
		/*GenericAccessibleObject<?> genericCall;
		if(this.considerRemainingMethodCalls){
			try {
				genericCall = Randomness.choice(TestCluster.getInstance().getRandomNonConstructorTestCall());
			} catch (ConstructionFailedException e) {
				genericCall = null;
				e.printStackTrace();
			}
		}else{
			//pick one method at a time in order that all the methods have the same probability of being picked
			if (remainingMethods.size() == 0) {
				reset();
			}
			genericCall = Randomness.choice(remainingMethods);
			attemptedMethods.add(genericCall);
			remainingMethods.remove(genericCall);
		}
		if(genericCall == null){
			throw new IllegalStateException(this.getClass().getName() + ": a method to insert in the random path sequence was not found");
		}*/
		
		if (remainingMethods.size() == 0) {
			reset();
		}
		
		GenericAccessibleObject<?> genericCall = Randomness.choice(remainingMethods);
		
		attemptedMethods.add(genericCall);
		remainingMethods.remove(genericCall);
		
		GenericMethod methodToAdd = (GenericMethod) genericCall;
		String edgeToAdd = GraphParser.fromMethodToEdge(methodToAdd);
		logger.info("MethodToAdd: " + methodToAdd.getMethod().getName() + ", edge to add " + edgeToAdd);
		List<String> edges = PathGenerator.generateRandomPathWithTarget(graph, startNodeGraph, edgeToAdd, isWeighted, maxSteps);
		//add edgeToAdd if it was not added by random walk procedure because it reached the maxSteps
		//maxSteps set in order to avoid very long test cases
		String lastEdge = edges.get(edges.size() - 1);
		int previousLength = edges.size();
		logger.debug("@@@edge to add: " + edgeToAdd);
		if(!lastEdge.equals(edgeToAdd)){
			//select the shortest path to arrive to the same start node of the edge to add
			//the procedure modifies the "edges" array 
			PathGenerator.shortestPath(graph, lastEdge, edgeToAdd, edges);
			int newLength = edges.size();
			logger.debug("@@@apply shortest path of length " + (newLength - previousLength) + " to arrive from " + lastEdge + " to " + edgeToAdd);
			/*String startNode = GraphParser.getEdgeTarget(lastEdge);
			String targetNode = GraphParser.getEdgeSource(edgeToAdd);
			logger.debug("@@@start node: " + startNode + " targetNode: " + targetNode);
			List<String> shortestPath = DijkstraShortestPath.findPathBetween(graph, startNode, targetNode);
			shortestPath.add(edgeToAdd);
			for(String edge: shortestPath){
				edges.add(edge);
			}*/
		}
		
		/*String edgeToTraverse = "selectQuantityShoppingCartPage-ShoppingCartPage-ShoppingCartPage";
		List<String> fakeEdges = PathGenerator.generateRandomPathWithTarget(graph, startNodeGraph, edgeToTraverse, isWeighted, 5);
		for(String edge: fakeEdges){
			logger.debug("@@@fakeEdge: " + edge);
		}*/
		
		//logger.debug("@@@length: " + length + " random walk length: " + edges.size());
		//should be equal
		//assert length == edges.size();
		/*logger.debug("@@@final set of edges to add");
		for(int i = 0; i < edges.size(); i++){
			logger.debug("@@@edge(" + i + ")" + edges.get(i));
		}*/

		TestCase test = getNewTestCase();
		int num = 0;

		boolean firstMethodToAdd = true;
		int i = 0;
		// Then add random stuff
		while (i < edges.size() && num < Properties.MAX_ATTEMPTS) {
			// Select an uncovered method and add it
			
			//edge syntax = methodName-sourceVertex-targetVertex
			String edge = edges.get(i);
			String methodName = GraphParser.getMethodName(edge);
			//now immutable test call: check new random test case at test suite level if it calls the factory again
			//if that is the case it should be better to generate a random test case which covers the remain test calls
			//yet uncovered
			//logger.debug("@@@methodName chose: " + methodName);
			
			GenericAccessibleObject<?> call = GraphParser.findRightImmutableMethodCall(methodName);
			
			if(call != null){
				try {
					TestFactory testFactory = TestFactory.getInstance();
					if (call.isMethod() && firstMethodToAdd) {
						testFactory.addMethod(test, (GenericMethod) call, test.size(), 0);
						firstMethodToAdd = false;
					}
					else if(call.isMethod())
					{
						GenericMethod method = (GenericMethod) call;
						ConstructorStatement cStmt = (ConstructorStatement) test.getStatement(0);
						VariableReference cStmtVar = cStmt.getReturnValue();
						testFactory.addMethodFor(test, cStmtVar, method, test.size());
					}
				} catch (ConstructionFailedException e) {
				}
			}
			num++;
			i++;
		}
		if (logger.isDebugEnabled())
			logger.debug("@@@@Randomized path sequence test case:" + test.toCode());

		if (tracerEnabled)
			ExecutionTracer.enable();

		return test;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * Generate a random chromosome
	 */
	@Override
	public TestChromosome getChromosome() {
		TestChromosome c = new TestChromosome();
		if(Properties.SELENIUM_WHOLE){
			if (Randomness.nextDouble() <= Properties.PATH_SEQUENCE_RATE){
				c.setTestCase(getRandomTestPathSequence(Properties.CHROMOSOME_LENGTH, Importer.graph));
			}
			else{
				c.setTestCase(getRandomTestCaseOneConstructor(Properties.CHROMOSOME_LENGTH));
			}
		}else{
			c.setTestCase(getRandomTestCase(Properties.CHROMOSOME_LENGTH));
		}
		return c;
	}
	
	//with a certain probability a new test case can be added to the test suite during the evolution (see mutate() method in AbstractTestSuiteChromosome)
	//in that case we should consider remaining branches (in methods) that are not covered yet rather than immutable method call
	/*public TestChromosome getChromosome(boolean considerRemainingMethodCalls){
		this.considerRemainingMethodCalls = considerRemainingMethodCalls;
		return getChromosome();
	}*/

	/**
	 * Provided so that subtypes of this factory type can modify the returned
	 * TestCase
	 * 
	 * @return a {@link org.evosuite.testcase.TestCase} object.
	 */
	protected TestCase getNewTestCase() {
		return new DefaultTestCase();
	}

	/**
	 * How many methods do we still need to cover?
	 * 
	 * @return a int.
	 */
	public int getNumUncoveredMethods() {
		return remainingMethods.size();
	}

	/**
	 * Forget which calls we have already attempted
	 */
	public void reset() {
		remainingMethods.addAll(allMethods);
		attemptedMethods.clear();
	}
	
	/*private GenericAccessibleObject<?> findRightMethodCall(String methodName){
		GenericAccessibleObject<?> call = null;
		for(GenericAccessibleObject<?> currentCall: allMethodsPathSequence){
			if(currentCall.isMethod()){
				GenericMethod method = (GenericMethod) currentCall;
				//logger.debug("@@@method in list: " + method.getName());
				if(method.getName().equals(methodName)){
					call = currentCall;
					return call;
				}
			}
		}
		return null;
	}*/
}
