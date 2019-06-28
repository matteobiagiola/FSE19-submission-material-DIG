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
package org.evosuite.testcase.mutation;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.evosuite.Properties;
import org.evosuite.ga.ConstructionFailedException;
import org.evosuite.selenium.graph.GraphParser;
import org.evosuite.selenium.graph.Importer;
import org.evosuite.selenium.graph.PathGenerator;
import org.evosuite.setup.TestCluster;
import org.evosuite.testcase.ConstraintHelper;
import org.evosuite.testcase.ConstraintVerifier;
import org.evosuite.testcase.TestCase;
import org.evosuite.testcase.TestFactory;
import org.evosuite.testcase.statements.FunctionalMockStatement;
import org.evosuite.testcase.statements.MethodStatement;
import org.evosuite.testcase.statements.PrimitiveStatement;
import org.evosuite.testcase.statements.Statement;
import org.evosuite.testcase.variable.NullReference;
import org.evosuite.testcase.variable.VariableReference;
import org.evosuite.utils.Randomness;
import org.evosuite.utils.generic.GenericAccessibleObject;
import org.evosuite.utils.generic.GenericMethod;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.AllDirectedPaths;
import org.jgrapht.alg.ConnectivityInspector;
import org.jgrapht.graph.DirectedPseudograph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RandomInsertion implements InsertionStrategy {

	private static final Logger logger = LoggerFactory.getLogger(RandomInsertion.class);
	
	@Override
	public int insertStatement(TestCase test, int lastPosition) {
		double r = Randomness.nextDouble();
		int oldSize = test.size();

		/*
			TODO: if allow inserting a UUT method in the middle of a test,
			 we need to handle case of not breaking any initialzing bounded variable
		 */
		int max = lastPosition;
		if (max == test.size())
			max += 1;

		if (max <= 0)
			max = 1;

		int position = 0;

		assert Properties.INSERTION_UUT + Properties.INSERTION_ENVIRONMENT + Properties.INSERTION_PARAMETER == 1.0;

		boolean insertUUT = Properties.INSERTION_UUT > 0 &&
				r <= Properties.INSERTION_UUT && TestCluster.getInstance().getNumTestCalls() > 0 ;

		boolean insertEnv = !insertUUT && Properties.INSERTION_ENVIRONMENT > 0 &&
				r > Properties.INSERTION_UUT && r <= Properties.INSERTION_UUT+Properties.INSERTION_ENVIRONMENT &&
				TestCluster.getInstance().getNumOfEnvironmentCalls() > 0;

		boolean insertParam = !insertUUT && !insertEnv;

		boolean success = false;
		if (insertUUT) {
			// Insert a call to the UUT at the end
			position = test.size();
			success = TestFactory.getInstance().insertRandomCall(test, position);
		} else if (insertEnv) {
			/*
				Insert a call to the environment. As such call is likely to depend on many constraints,
				we do not specify here the position of where it ll happen.
			 */
			position = TestFactory.getInstance().insertRandomCallOnEnvironment(test,lastPosition);
			success = (position >= 0);
		} else if (insertParam){
			// Insert a call to a parameter

			VariableReference var = selectRandomVariableForCall(test, lastPosition);
			if (var != null) {
				int lastUsage = var.getStPosition();

				for (VariableReference usage : test.getReferences(var)) {
					if (usage.getStPosition() > lastUsage)
						lastUsage = usage.getStPosition();
				}

				int boundPosition = ConstraintHelper.getLastPositionOfBounded(var, test);
				if(boundPosition >= 0 ){
					// if bounded variable, cannot add methods before its initialization
					position = boundPosition + 1;
				} else {

					if (lastUsage > var.getStPosition() + 1) {
						position = Randomness.nextInt(var.getStPosition(), // call has to be after the object is created
								lastUsage) + 1;
					} else {
						position = lastUsage;
					}
				}

				if(logger.isDebugEnabled()) {
					logger.debug("Inserting call at position " + position + ", chosen var: "
							+ var.getName() + ", distance: " + var.getDistance() + ", class: "
							+ var.getClassName());
				}

				success = TestFactory.getInstance().insertRandomCallOnObjectAt(test, var, position);
			}

			if (!success && TestCluster.getInstance().getNumTestCalls() > 0) {
				logger.debug("Adding new call on UUT because var was null");
				//Why was it different from UUT insertion? ie, in random position instead of last
				//position = Randomness.nextInt(max);
				position = test.size();
				success = TestFactory.getInstance().insertRandomCall(test, position);
			}
		}

		//this can happen if insertion had side effect of adding further previous statements in the test,
		//eg to handle input parameters
		if (test.size() - oldSize > 1) {
			position += (test.size() - oldSize - 1);
		}

		if (success) {
			assert ConstraintVerifier.verifyTest(test);
			assert ! ConstraintVerifier.hasAnyOnlyForAssertionMethod(test);

			return position;
		} else {
			return -1;
		}
	}
	
	public List<Integer> insertRandomPath(TestCase test, int lastPosition) throws IllegalStateException{
		int oldSize = test.size();
		
		List<Integer> positions = new ArrayList<Integer>();
		
			//TODO: if allow inserting a UUT method in the middle of a test,
			//we need to handle case of not breaking any initialzing bounded variable
		
		//unuseful in my opinion
		int max = lastPosition;
		if (max == test.size())
			max += 1;

		if (max <= 0)
			max = 1;

		int position = 0;
		
		//we want that only calls belonging to unit under test are inserted
		boolean insertUUT = true;

		boolean success = false;
		if (insertUUT) {
			// Insert a call to the UUT at the end
			//take the first method statement starting from the end of the test case
			/*
			 * with the deletion we can have this cases:
			 * 1) the last statement, method call with parameters, is deleted and the declaration of a parameter is left in the last position. 
			 * Deletion removes parameters and all the statements related to those parameters;
			 * 2) the deletion removed all the statements except the constructor that it the last statement
			 * */
			Statement lastStatement = null;
			for(int i = test.size() - 1; i > 0; i--){
				if(test.hasStatement(i) && test.getStatement(i) instanceof MethodStatement){
					lastStatement = test.getStatement(i);
					break;
				}
			}
			try 
			{
				String lastEdgeTargetNode;
				if(lastStatement == null){
					logger.debug("@@@no method statement in the test case: choosing starting node " + Properties.START_NODE + " as the starting point for the random path insertion");
					lastEdgeTargetNode = Properties.START_NODE;
				}else{
					MethodStatement method = (MethodStatement) lastStatement;
					String lastEdge = GraphParser.fromMethodToEdge(method);
					//create a random path between last edge target node and edge to insert source node
					lastEdgeTargetNode = GraphParser.getEdgeTarget(lastEdge);
				}
				GenericAccessibleObject<?> object = TestCluster.getInstance().getRandomNonConstructorTestCall();
				if(object == null){
					//return an empty non null list: there are no objects available to add a method sequence
					//probably all the interesting methods are covered
					return positions;
				}
				GenericMethod randomChoice = (GenericMethod) object;
				String edgeToCover = GraphParser.fromMethodToEdge(randomChoice);
				logger.debug("@@@edge to cover: " + edgeToCover);
				
				String startNode = lastEdgeTargetNode;
				//subgraph extraction
				/*DirectedPseudograph<String,String> graphCopy = (DirectedPseudograph<String, String>) Importer.graph.clone();
				ConnectivityInspector<String, String> conn = new ConnectivityInspector<String, String>(graphCopy);
				String sourceNodeEdgeToCover = GraphParser.getEdgeSource(edgeToCover);
				String targetNodeEdgeToCover = GraphParser.getEdgeTarget(edgeToCover);
				Set<String> nodeSet = new LinkedHashSet<String>(graphCopy.vertexSet());
				//remove all the nodes from the graph from which the source node of the edge to cover is not reachable
				for(String node: nodeSet){
					if(!conn.pathExists(node, sourceNodeEdgeToCover) && !node.equals(targetNodeEdgeToCover)){
						graphCopy.removeVertex(node);
					}
				}*/
				
				int length = Randomness.nextInt(1,Properties.CHROMOSOME_LENGTH);
				
				int maxSteps = length;
				List<String> edges = PathGenerator.generateRandomPathWithTarget(Importer.graph, startNode, edgeToCover, false, maxSteps);
				logger.debug("@@@random path generated size: " + edges.size());
				String lastEdge = edges.get(edges.size() - 1);
				int previousLength = edges.size();
				if(!lastEdge.equals(edgeToCover)){
					//search shortest path directly on the graph not in the subgraph given that it is not a random path
					//and so it is not biased by the not relevant nodes (out of the subgraph)
					PathGenerator.shortestPath(Importer.graph, lastEdge, edgeToCover, edges);
					int newLength = edges.size();
					logger.debug("@@@apply shortest path of length " + (newLength - previousLength) + " to arrive from " + lastEdge + " to " + edgeToCover);
				}
				
				for(int i = 0; i < edges.size(); i++){
					success = false;
					oldSize = test.size();
					position = test.size();
					String methodName = GraphParser.getMethodName(edges.get(i));
					logger.debug("@@@methodName: " + methodName + " edge: " + edges.get(i));
					GenericAccessibleObject<?> objectCall = GraphParser.findRightImmutableMethodCall(methodName);
					GenericMethod methodCall = (GenericMethod) objectCall;
					logger.debug("@@@objectCall: " + objectCall);
					if(objectCall == null){
						throw new IllegalStateException(this.getClass().getName() + " insertRandomPath: there is no edge " + methodName + " in the graph");
					}
					success = TestFactory.getInstance().insertGivenMethodCall(test, methodCall, position);
					//this can happen if insertion had side effect of adding further previous statements in the test,
					//eg to handle input parameters
					if (test.size() - oldSize > 1) {
						logger.debug("@@@handle param insertion: testSize " + test.size() + " oldSize: " + oldSize);
						position += (test.size() - oldSize - 1);
					}
					positions.add(position);
					if (success) {
						assert ConstraintVerifier.verifyTest(test);
						assert ! ConstraintVerifier.hasAnyOnlyForAssertionMethod(test);
					}else{
						throw new IllegalStateException(this.getClass().getName() + " insertRandomPath failed");
					}	
				}
			} catch (ConstructionFailedException e) {
				// TODO Auto-generated catch block
				logger.warn("@@@Construction of random non constructor test call failed");
				positions.clear();
				return positions;
			}
			
		}
		return positions;
		
	}
	
	private VariableReference selectRandomVariableForCall(TestCase test, int position) {
		if (test.isEmpty() || position == 0)
			return null;

		List<VariableReference> allVariables = test.getObjects(position);
		Set<VariableReference> candidateVariables = new LinkedHashSet<>();

		for(VariableReference var : allVariables) {

			if (!(var instanceof NullReference) &&
					!var.isVoid() &&
					!(test.getStatement(var.getStPosition()) instanceof PrimitiveStatement) &&
					!var.isPrimitive() &&
					test.hasReferences(var) &&
					/* Note: this check has been added only recently,
						to avoid having added calls to UUT in the middle of the test
					 */
					/*
					   Commented this out again, as it would mean that methods of the SUT class
					   that are declared in a superclass would not be inserted at all, but now
					   this may break some constraints.
					 */
//					!var.getVariableClass().equals(Properties.getTargetClass()) &&
					//do not directly call methods on mock objects
					! (test.getStatement(var.getStPosition()) instanceof FunctionalMockStatement) ){

				candidateVariables.add(var);
			}
		}

		if(candidateVariables.isEmpty()) {
			return null;
		} else {
			VariableReference choice = Randomness.choice(candidateVariables);
			return choice;
		}
	}

}
