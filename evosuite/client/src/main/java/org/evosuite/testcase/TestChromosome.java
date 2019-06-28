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
import org.evosuite.coverage.mutation.Mutation;
import org.evosuite.coverage.mutation.MutationExecutionResult;
import org.evosuite.ga.Chromosome;
import org.evosuite.ga.ConstructionFailedException;
import org.evosuite.ga.SecondaryObjective;
import org.evosuite.ga.localsearch.LocalSearchObjective;
import org.evosuite.ga.operators.mutation.MutationHistory;
import org.evosuite.runtime.util.AtMostOnceLogger;
import org.evosuite.selenium.graph.GraphParser;
import org.evosuite.selenium.graph.Importer;
import org.evosuite.selenium.graph.PathGenerator;
import org.evosuite.setup.TestCluster;
import org.evosuite.symbolic.BranchCondition;
import org.evosuite.symbolic.ConcolicExecution;
import org.evosuite.symbolic.ConcolicMutation;
import org.evosuite.testcase.execution.ExecutionResult;
import org.evosuite.testcase.localsearch.TestCaseLocalSearch;
import org.evosuite.testcase.statements.ConstructorStatement;
import org.evosuite.testcase.statements.EntityWithParametersStatement;
import org.evosuite.testcase.statements.FunctionalMockStatement;
import org.evosuite.testcase.statements.MethodStatement;
import org.evosuite.testcase.statements.PrimitiveStatement;
import org.evosuite.testcase.statements.Statement;
import org.evosuite.testcase.variable.VariableReference;
import org.evosuite.testsuite.CurrentChromosomeTracker;
import org.evosuite.testsuite.TestSuiteFitnessFunction;
import org.evosuite.utils.Randomness;
import org.evosuite.utils.generic.GenericAccessibleObject;
import org.evosuite.utils.generic.GenericClass;
import org.evosuite.utils.generic.GenericMethod;
import org.jgrapht.alg.ConnectivityInspector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Chromosome representation of test cases
 *
 * @author Gordon Fraser
 */
public class TestChromosome extends ExecutableChromosome {

	private static final long serialVersionUID = 7532366007973252782L;

	private static final Logger logger = LoggerFactory.getLogger(TestChromosome.class);

	/** The test case encoded in this chromosome */
	protected TestCase test = new DefaultTestCase();

	/** To keep track of what has changed since last fitness evaluation */
	protected MutationHistory<TestMutationHistoryEntry> mutationHistory = new MutationHistory<TestMutationHistoryEntry>();

	/** Secondary objectives used during ranking */
	private static final List<SecondaryObjective<?>> secondaryObjectives = new ArrayList<SecondaryObjective<?>>();
	
	private SeleniumGenotype genotype;
	
	public TestChromosome(){
		this.setGenotype(new SeleniumGenotype(Properties.GENOTYPE_LENGTH));
	}

	/**
	 * <p>
	 * setTestCase
	 * </p>
	 *
	 * @param testCase
	 *            a {@link org.evosuite.testcase.TestCase} object.
	 */
	public void setTestCase(TestCase testCase) {
		test = testCase;
		clearCachedResults();
		clearCachedMutationResults();
		setChanged(true);
	}

	/**
	 * <p>
	 * getTestCase
	 * </p>
	 *
	 * @return a {@link org.evosuite.testcase.TestCase} object.
	 */
	public TestCase getTestCase() {
		return test;
	}

	/** {@inheritDoc} */
	@Override
	public void setLastExecutionResult(ExecutionResult lastExecutionResult) {
	    if (lastExecutionResult == null)
	        return ;
		assert lastExecutionResult.test.equals(this.test);
		this.lastExecutionResult = lastExecutionResult;
	}

	/** {@inheritDoc} */
	@Override
	public void setChanged(boolean changed) {
		super.setChanged(changed);
		if (changed) {
			clearCachedResults();
		}
		CurrentChromosomeTracker.getInstance().changed(this);
	}

	/**
	 * {@inheritDoc}
	 *
	 * Create a deep copy of the chromosome
	 */
	@Override
	public Chromosome clone() {
		TestChromosome c = new TestChromosome();
		c.test = test.clone();
		c.setFitnessValues(getFitnessValues());
		c.setPreviousFitnessValues(getPreviousFitnessValues());
		c.copyCachedResults(this);
		c.setChanged(isChanged());
		c.setLocalSearchApplied(hasLocalSearchBeenApplied());
		if (Properties.LOCAL_SEARCH_SELECTIVE) {
			for (TestMutationHistoryEntry mutation : mutationHistory) {
				if(test.contains(mutation.getStatement()))
					c.mutationHistory.addMutationEntry(mutation.clone(c.getTestCase()));
			}
		}
		// c.mutationHistory.set(mutationHistory);

		return c;
	}

	/* (non-Javadoc)
	 * @see org.evosuite.testcase.ExecutableChromosome#copyCachedResults(org.evosuite.testcase.ExecutableChromosome)
	 */
	/** {@inheritDoc} */
	@Override
	public void copyCachedResults(ExecutableChromosome other) {
		if (test == null)
			throw new RuntimeException("Test is null!");

		if (other.lastExecutionResult != null) {
			this.lastExecutionResult = other.lastExecutionResult.clone();
			this.lastExecutionResult.setTest(this.test);
		}

		if (other.lastMutationResult != null) {
			for (Mutation mutation : other.lastMutationResult.keySet()) {
				MutationExecutionResult copy = other.lastMutationResult.get(mutation); //.clone();
				//copy.test = test;
				this.lastMutationResult.put(mutation, copy);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * Single point cross over
	 */
	@Override
	public void crossOver(Chromosome other, int position1, int position2)
	        throws ConstructionFailedException {
		logger.debug("Crossover starting");
		TestChromosome offspring = new TestChromosome();
		TestChromosome otherTestChromosome = (TestChromosome) other;
		TestFactory testFactory = TestFactory.getInstance();
		
		if(Properties.SELENIUM){
			//add constructor statement
			offspring.test.addStatement(test.getStatement(0).clone(offspring.test));
			logger.debug("@@@@offspring first statement added: {}", offspring.test.toCode());
			
			int j = 0;
			int k = 0;
			while(j < Properties.CHROMOSOME_PARTS)
			{
				for (int i = 1 + k; i < position1 + 1 + k; i++) {
					logger.debug("@@@@offspring add statement current parent: {}, i: {}, j: {}, k: {}", test.getStatement(i).getCode(), i, j, k);
					offspring.test.addStatement(test.getStatement(i).clone(offspring.test));
				}
				
				logger.debug("@@@@offspring after first parent contrib: {}", offspring.test.toCode());
				
				//+2 is to include also the method call statement: in this case we take the one from the other parent but it doesn't matter
				for (int i = position2 + 1 + k; i < Properties.GENOTYPE_LENGTH + 2 + k; i++) {
					logger.debug("@@@@offspring add statement other parent: {}", otherTestChromosome.getTestCase().getStatement(i).getCode());
					offspring.test.addStatement(otherTestChromosome.getTestCase().getStatement(i).clone(offspring.test));
				}
				
				
				//+1 is to skip the insertion of the method call statement that has been already included in the previous for loop
				k = k + Properties.GENOTYPE_LENGTH + 1;;
				logger.debug("@@@@offspring after other parent contribution: {}", offspring.test.toCode());
				j++;
			}
			
			logger.debug("@@@@offspring final: {}", offspring.test.toCode());
			setChanged(true);
		}
		else if(Properties.TEST_CASE_CROSSOVER){
			/*
			 * test case crossover: given head and tail 
			 * (two different method calls, taken from different chromosomes, head is the last
			 * method call of headChromosome while tail is the first method call of tailChromosome), 
			 * if head is not reachable from tail, 
			 * try to remove head and/or tail, 
			 * and repeat until reachability is guaranteed; 
			 * at this point bind head and tail via random walk
			 * */
			logger.debug("@@@before crossover this chromosome: " + test.toCode());
			logger.debug("@@@before crossover other chromosome: " + otherTestChromosome.test.toCode());
			//check reachability
			ConnectivityInspector<String,String> inspector = new ConnectivityInspector<String,String>(Importer.graph);
			//ASSUMPTION: we know that head and tail are method statements
			MethodStatement headMethod = (MethodStatement) test.getStatement(position1);
			MethodStatement tailMethod = (MethodStatement) otherTestChromosome.test.getStatement(position2);
			String headEdge = GraphParser.fromMethodToEdge(headMethod);
			logger.debug("Head edge: " + headEdge);
			String tailEdge = GraphParser.fromMethodToEdge(tailMethod);
			logger.debug("Tail edge: " + tailEdge);
			String targetNodeHead = GraphParser.getEdgeTarget(headEdge);
			logger.debug("Target node head: " + targetNodeHead);
			String sourceNodeTail = GraphParser.getEdgeSource(tailEdge);
			logger.debug("Source node tail: " + sourceNodeTail);
			if(inspector.pathExists(targetNodeHead, sourceNodeTail)){
				//tail is reachable from head
				logger.debug("@@@tail source node {} is reachable from head target node {}",sourceNodeTail,targetNodeHead);
				TestCase copy = test.clone();
				//cut all the statements of the test case after position1
				logger.debug("@@@chopping this chromosome");
				for(int i = copy.size() - 1; i > position1; i--){
					copy.remove(i);
				}
				logger.debug("@@@this chromosome after chopping: " + copy.toCode());
				if(targetNodeHead.equals(sourceNodeTail)){
					//no additional path needed to bind the head target node && tail source node
					logger.debug("@@@tail source node is equal to head target node");
					for (int i = 0; i < position1; i++) {
						offspring.test.addStatement(test.getStatement(i).clone(offspring.test));
					}
					for (int i = position2; i < other.size(); i++) {
						//addMethodFor -> see AllMethodsFactory (only one constructor admissible)
						testFactory.appendStatement(offspring.test,
						                            ((TestChromosome) other).test.getStatement(i));
					}
					if (!Properties.CHECK_MAX_LENGTH
					        || offspring.test.size() <= Properties.CHROMOSOME_LENGTH) {
						test = offspring.test;
					}
				}else{
					//an additional path (random walk) is needed to bind the head target node && tail source node
					logger.debug("@@@tail source node is different from head target node: connecting them via random walk -> from head target node {} to tail source node {} ",targetNodeHead,sourceNodeTail);
					//insert random path to connect head and tail, in this chromosome (head)
					this.insertRandomPath(copy, targetNodeHead, sourceNodeTail);
					//before adding path copy.size() = position1
					int newPosition1 = copy.size();
					for (int i = 0; i < newPosition1; i++) {
						offspring.test.addStatement(copy.getStatement(i).clone(offspring.test));
					}
					for (int i = position2; i < other.size(); i++) {
						testFactory.appendStatement(offspring.test,
						                            ((TestChromosome) other).test.getStatement(i));
					}
					if (!Properties.CHECK_MAX_LENGTH
					        || offspring.test.size() <= Properties.CHROMOSOME_LENGTH) {
						test = offspring.test;
					}
				}
			}else{
				throw new UnsupportedOperationException(this.getClass().getName() + ": test case crossover. Test case crossover when tail is not reachable from head, it has to be tested.");
				/*//tail is not reachable from head
				//TODO TO TEST before benchmarking applications
				logger.debug("@@@tail source node {} is not reachable from head target node {}: cutting either tail or head until they (tail source node and head target node) are reachable",sourceNodeTail,targetNodeHead);
				TestCase copy = test.clone();
				TestCase otherCopy = otherTestChromosome.test.clone();
				//why do we chop both chromosomes in this spot?
				logger.debug("@@@chopping this chromosome");
				//copy.chop(position1);
				for(int i = copy.size() - 1; i > position1; i--){
					copy.remove(i);
				}
				logger.debug("@@@chopping other chromosome");
				for(int i = position2 - 1; i > 0; i--){
					otherCopy.remove(i);
				}
				boolean headModified = false;
				boolean tailModified = false;
				do{
					if(Randomness.nextDouble() <= 0.5){
						//delete method statement before head in this chromosome
						logger.debug("@@@cutting head");
						headModified = true;
						Statement stmtToRemove = copy.getStatement(position1);
						if(stmtToRemove instanceof MethodStatement){
							copy.remove(position1);
							position1--;
						}else{
							//search first available method statement
							for(int i = position1 - 1; i > 0; i--){
								Statement currentStmt = copy.getStatement(i);
								if(currentStmt instanceof MethodStatement){
									stmtToRemove = currentStmt;
									position1 = i;
								}
								if(i == 0){
									logger.warn("@@@reached beginning of test case during attempt of binding head and tail in crossover operator: crossover operator failed");
									return;
								}
							}
							copy.remove(position1);
							position1--;
						}
						headMethod = (MethodStatement) copy.getStatement(position1);
						headEdge = GraphParser.fromMethodToEdge(headMethod);
						targetNodeHead = GraphParser.getEdgeTarget(headEdge);
					}else{
						//delete method statement after tail in other chromosome
						logger.debug("@@@cutting tail");
						tailModified = true;
						Statement stmtToRemove = otherCopy.getStatement(position2);
						if(stmtToRemove instanceof MethodStatement){
							otherCopy.remove(position2);
							position2++;
						}else{
							//search first available method statement
							for(int i = position2; i < otherCopy.size(); i++){
								Statement currentStmt = otherCopy.getStatement(i);
								if(currentStmt instanceof MethodStatement){
									stmtToRemove = currentStmt;
									position1 = i;
								}
								if(i == otherCopy.size()){
									logger.warn("@@@reached end of test case during attempt of binding head and tail in crossover operator: crossover operator failed");
									return;
								}
							}
							copy.remove(position2);
							position2++;
						}
						tailMethod = (MethodStatement) otherCopy.getStatement(position2);
						tailEdge = GraphParser.fromMethodToEdge(tailMethod);
						sourceNodeTail = GraphParser.getEdgeSource(tailEdge);
					}
				}while(!inspector.pathExists(targetNodeHead, sourceNodeTail));
				
				if(headModified){
					this.deleteUnreferencedParameters(copy, testFactory, false);
				}
				if(tailModified){
					this.deleteUnreferencedParameters(otherCopy, testFactory, false);
				}
				
				if(targetNodeHead.equals(sourceNodeTail)){
					//no additional path needed to bind the head target node && tail source node
					logger.debug("@@@tail source node {} and head target node {} are equal AFTER CUTTING",sourceNodeTail,targetNodeHead);
					for (int i = 0; i < position1; i++) {
						offspring.test.addStatement(copy.getStatement(i).clone(offspring.test));
					}
					for (int i = position2; i < other.size(); i++) {
						testFactory.appendStatement(offspring.test, otherCopy.getStatement(i));
					}
					if (!Properties.CHECK_MAX_LENGTH
					        || offspring.test.size() <= Properties.CHROMOSOME_LENGTH) {
						test = offspring.test;
					}
				}else{
					//an additional path (random walk) is needed to bind the head target node && tail source node
					//insert random path to connect head and tail, in this chromosome
					logger.debug("@@@tail source node is different from head target node AFTER CUTTING: connecting them via random walk -> from head target node {} to tail source node {} ",targetNodeHead,sourceNodeTail);
					this.insertRandomPath(copy, targetNodeHead, sourceNodeTail);
					//before adding path copy.size() = position1
					int newPosition1 = copy.size();
					for (int i = 0; i < newPosition1; i++) {
						offspring.test.addStatement(copy.getStatement(i).clone(offspring.test));
					}
					for (int i = position2; i < other.size(); i++) {
						testFactory.appendStatement(offspring.test, otherCopy.getStatement(i));
					}
					if (!Properties.CHECK_MAX_LENGTH
					        || offspring.test.size() <= Properties.CHROMOSOME_LENGTH) {
						test = offspring.test;
					}
				}*/
			}
			logger.debug("@@@after crossover chromosome: " + test.toCode());
		}
		else
		{
			for (int i = 0; i < position1; i++) {
				offspring.test.addStatement(test.getStatement(i).clone(offspring.test));
			}
			for (int i = position2; i < other.size(); i++) {
				testFactory.appendStatement(offspring.test,
				                            ((TestChromosome) other).test.getStatement(i));
			}
			if (!Properties.CHECK_MAX_LENGTH
			        || offspring.test.size() <= Properties.CHROMOSOME_LENGTH) {
				test = offspring.test;
			}

			setChanged(true);
		}
		
		
	}

	/**
	 * {@inheritDoc}
	 *
	 * Two chromosomes are equal if their tests are equal
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TestChromosome other = (TestChromosome) obj;
		if (test == null) {
			if (other.test != null)
				return false;
		} else if (!test.equals(other.test))
			return false;
		return true;
	}

	/** {@inheritDoc} */
	@Override
	public int hashCode() {
		return test.hashCode();
	}

	public MutationHistory<TestMutationHistoryEntry> getMutationHistory() {
		return mutationHistory;
	}

	public void clearMutationHistory() {
		mutationHistory.clear();
	}

	public boolean hasRelevantMutations() {

		if (mutationHistory.isEmpty()) {
			logger.info("Mutation history is empty");
			return false;
		}

		// Only apply local search up to the point where an exception was thrown
		int lastPosition = test.size() - 1;
		if (lastExecutionResult != null && !isChanged()) {
			Integer lastPos = lastExecutionResult.getFirstPositionOfThrownException();
			if (lastPos != null)
				lastPosition = lastPos.intValue();
		}

		for (TestMutationHistoryEntry mutation : mutationHistory) {
			logger.info("Considering: " + mutation.getMutationType());

			if (mutation.getMutationType() != TestMutationHistoryEntry.TestMutation.DELETION
			        && mutation.getStatement().getPosition() <= lastPosition) {
				if (Properties.LOCAL_SEARCH_SELECTIVE_PRIMITIVES) {
					if (!(mutation.getStatement() instanceof PrimitiveStatement<?>))
						continue;
				}
				final Class<?> targetClass = Properties.getTargetClassAndDontInitialise();

				if (!test.hasReferences(mutation.getStatement().getReturnValue())
				        && !mutation.getStatement().getReturnClass().equals(targetClass)) {
					continue;
				}

				int newPosition = -1;
				for (int i = 0; i <= lastPosition; i++) {
					if (test.getStatement(i) == mutation.getStatement()) {
						newPosition = i;
						break;
					}
				}

				// Couldn't find statement, may have been deleted in other mutation?
				assert (newPosition >= 0);
				if (newPosition < 0) {
					continue;
				}

				return true;
			}
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see org.evosuite.ga.Chromosome#localSearch()
	 */
	/** {@inheritDoc} */
	@SuppressWarnings("unchecked")
	@Override
	public boolean localSearch(LocalSearchObjective<? extends Chromosome> objective) {
		TestCaseLocalSearch localSearch = TestCaseLocalSearch.selectTestCaseLocalSearch();
		return localSearch.doSearch(this,
		                            (LocalSearchObjective<TestChromosome>) objective);
	}

	/**
	 * {@inheritDoc}
	 *
	 * Each statement is mutated with probability 1/l
	 */
	@Override
	public void mutate() {
		boolean changed = false;
		mutationHistory.clear();
		
		logger.debug("@@@@mutation: {}", this.getClass().getName());
		logger.debug("@@@@test case size: {}", test.size());
		logger.debug("@@@@before mutation : {}", test.toCode());
		
		
		if(mockChange()){
			changed = true;
		}

		int lastPosition = getLastMutatableStatement();
		if(Properties.CHOP_MAX_LENGTH && size() >= Properties.CHROMOSOME_LENGTH) {
			test.chop(lastPosition + 1);
		}

		// Delete
		if (Randomness.nextDouble() <= Properties.P_TEST_DELETE) {
			logger.debug("Mutation: delete");
			if(mutationDelete())
				changed = true;
		}

		// Change
		if (Randomness.nextDouble() <= Properties.P_TEST_CHANGE) {
			logger.debug("Mutation: change");
			if(Properties.SELENIUM){
				if (mutationSeleniumChange())
					changed = true;
			}else{
				if (mutationChange())
					changed = true;
			}
		}

		// Insert
		if (Randomness.nextDouble() <= Properties.P_TEST_INSERT) {
			logger.debug("Mutation: insert");
			if (mutationInsert())
				changed = true;
		}

		if (changed) {
			setChanged(true);
			test.clearCoveredGoals();
		}
		for (Statement s : test) {
			s.isValid();
		}

		// be sure that mutation did not break any constraint.
		// if it happens, it means a bug in EvoSuite
		assert ConstraintVerifier.verifyTest(test);
		assert ! ConstraintVerifier.hasAnyOnlyForAssertionMethod(test);
		
		logger.debug("@@@@after mutation : {}", test.toCode());
	}


	private boolean mockChange()  {

		/*
			Be sure to update the mocked values if there has been any change in
			behavior in the last execution.

			Note: mock "expansion" cannot be done after a test has been mutated and executed,
			as the expansion itself might have side effects. Therefore, it has to be done
			before a test is evaluated.
		 */

		boolean changed = false;

		for(int i=0; i<test.size(); i++){
			Statement st = test.getStatement(i);
			if(! (st instanceof FunctionalMockStatement)){
				continue;
			}

			FunctionalMockStatement fms = (FunctionalMockStatement) st;
			if(! fms.doesNeedToUpdateInputs()){
				continue;
			}

			int preLength = test.size();

			try {
				List<Type> missing = fms.updateMockedMethods();
				int pos = st.getPosition();
				logger.debug("Generating parameters for mock call");
				List<VariableReference> refs = TestFactory.getInstance().satisfyParameters(test, null, missing, pos, 0, true, false,true);
				fms.addMissingInputs(refs);
			} catch (Exception e){
				//shouldn't really happen because, in the worst case, we could create mocks for missing parameters
				String msg = "Functional mock problem: "+e.toString();
				AtMostOnceLogger.warn(logger, msg);
				fms.fillWithNullRefs();
				return changed;
			}
			changed = true;

			int increase = test.size() - preLength;
			i += increase;
		}

		return changed;
	}

	private int getLastMutatableStatement() {
		ExecutionResult result = getLastExecutionResult();
		if (result != null && !result.noThrownExceptions()) {
			int pos = result.getFirstPositionOfThrownException();
			// It may happen that pos > size() after statements have been deleted
			if (pos >= test.size())
				return test.size() - 1;
			else
				return pos;
		} else {
			return test.size() - 1;
		}
	}

	/**
	 * Each statement is deleted with probability 1/length
	 *
	 * @return
	 */
	private boolean mutationDelete() {
		
		if(test.isEmpty()){
			return false; //nothing to delete
		}
		
		if(Properties.SELENIUM_WHOLE){
			if(test.size() <= Properties.TEST_MIN_SIZE){
				logger.debug("@@@do not delete any statement from the test case because is too short");
				return false; //we don't want test cases too short when there are few methods left uncovered
			}
		}

		boolean changed = false;
		int lastMutableStatement = getLastMutatableStatement();
		double pl = 1d / (lastMutableStatement + 1);
		TestFactory testFactory = TestFactory.getInstance();

		for (int num = lastMutableStatement; num >= 0; num--) {

			if(num >= test.size()){
				continue; //in case the delete remove more than one statement
			}

			// Each statement is deleted with probability 1/l
			if (Randomness.nextDouble() <= pl) {
				if(Properties.CUSTOM_MUTATION_DELETE){
					
					Statement statement = test.getStatement(num);
					logger.debug("@@@analyzing statement: " + statement.toString() + " position " + num);
					if(statement instanceof ConstructorStatement){
						logger.debug("@@@do not delete statement " + statement.toString() + " because it is a constructor in the test case");
						continue;
					}
					
					//do not delete statements that destroy the sequence of methods
					/*if(statement instanceof MethodStatement && !canDelete(test, num)){
						logger.debug("@@@do not delete statement " + statement.toString() + " position " + num + " because it destroys the sequence");
						continue;
					}*/
					
					//do not delete statements that are not method statements
					if(!(statement instanceof MethodStatement)){
						logger.debug("@@@do not delete statement " + statement.toString() + " position " + num + " because it is not a method statement");
						continue;
					}
				}
				changed |= deleteStatement(testFactory, num);

				if(changed){
					assert ConstraintVerifier.verifyTest(test);
				}
			}
		}

		if(changed){
			assert ConstraintVerifier.verifyTest(test);
		}

		return changed;
	}
	
	private boolean canDelete(TestCase test, int num){
		MethodStatement methodToDelete = (MethodStatement) test.getStatement(num);
		MethodStatement previousMethod = null;
		MethodStatement nextMethod = null;
		//search previous MethodStatement in the test case: > 1 in order to do not touch ConstructorStatement that is in the first position of the test case
		for(int i = num; i > 1; i--){
			if(test.hasStatement(i) && test.getStatement(i) instanceof MethodStatement){
				previousMethod = (MethodStatement) test.getStatement(i);
				break;
			}
		}
		//search next MethodStatement in the test case
		for(int i = num; i < test.size(); i++){
			if(test.hasStatement(i) && test.getStatement(i) instanceof MethodStatement){
				nextMethod = (MethodStatement) test.getStatement(i);
				break;
			}
		}
		String edgeToDelete = GraphParser.fromMethodToEdge(methodToDelete);
		String sourceNodeToDelete = GraphParser.getEdgeSource(edgeToDelete);
		String targetNodeToDelete = GraphParser.getEdgeTarget(edgeToDelete);
		if(previousMethod != null && nextMethod != null){
			//current statement is inside other two statements
			if(sourceNodeToDelete.equals(targetNodeToDelete)){
				return true;
			}
		}
		else if(previousMethod != null && nextMethod == null){
			//current statement is the last statement of the test case
			return true;
		}
		else if(previousMethod == null && nextMethod != null){
			//this statement is the method after the constructor
			if(sourceNodeToDelete.equals(targetNodeToDelete)){
				return true;
			}
		}
		return false;
	}

	protected boolean deleteStatement(TestFactory testFactory, int num) {

		try {

            TestCase copy = test.clone();
            
            boolean modified = false;
            
            if(Properties.CUSTOM_MUTATION_DELETE){
            	//statement to delete is for sure a method statement
                Statement statement = copy.getStatement(num);
                if(!(statement instanceof MethodStatement)){
                	return false;
                }
                MethodStatement methodStatement = (MethodStatement) statement;
                String startingEdge = GraphParser.fromMethodToEdge(methodStatement);
                //starting from the source node of this edge delete all the edges
                //until we reach an edge with the same target node of the starting source node
                //In this way the method sequence that reveals a path is not destroyed and we
                //have a bloat control mechanism to balance the mutation insertion operator
                //Example: x = selected
                //m1 (0-1) -> m2 (1-2) -> m3 x (2 x -7) -> m4 (7-3) -> m5 (3-2 x ) -> m6 (2-10)
                //m1 (0-1) -> m2 (1-2) -> m6 (2-10)
                String startingNode = GraphParser.getEdgeSource(startingEdge);
                List<Integer> methodsToDeletePositions = new ArrayList<Integer>();
                methodsToDeletePositions.add(num);
                for(int i = num + 1; i < copy.size(); i++){
                	if(copy.hasStatement(i) && copy.getStatement(i) instanceof MethodStatement){
                		MethodStatement methodToDelete = (MethodStatement) copy.getStatement(i);
                		String currentEdge = GraphParser.fromMethodToEdge(methodToDelete);
                		String currentTargetNode = GraphParser.getEdgeTarget(currentEdge);
                		methodsToDeletePositions.add(i);
                		if(currentTargetNode.equals(startingNode)){
                			break;
                		}
                	}
                }
                
                Collections.sort(methodsToDeletePositions, Collections.reverseOrder());
                MethodStatement lastMethodToDelete = (MethodStatement) copy.getStatement(methodsToDeletePositions.get(0));
                String lastEdgeToDelete = GraphParser.fromMethodToEdge(lastMethodToDelete);
                String lastNodeTarget = GraphParser.getEdgeTarget(lastEdgeToDelete);
                if(methodsToDeletePositions.get(0) == copy.size() - 1){
                	lastNodeTarget = "end of the test case";
                }else{
                	//should be equal
                	assert lastNodeTarget.equals(startingNode);
                }
                //do not permit that the delete operator deletes all method statements except the first (constructor of CUT)
                if(methodsToDeletePositions.get(0) == copy.size() - 1){
                	//get position of the first method statement of the test case
                	int positionOfFirstMethodStatement = 0;
                	for(int i = 1; i < copy.size(); i++){
                		if(copy.hasStatement(i) && copy.getStatement(i) instanceof MethodStatement){
                			positionOfFirstMethodStatement = i;
                			break;
                		}
                	}
                	if(num == positionOfFirstMethodStatement){
                		logger.debug("@@@it is not possible to delete all the method statements of the test case. Test case size: " + copy.size() + ", last statement to delete: " + copy.getStatement(methodsToDeletePositions.get(0)) + ", first method statement is: " + copy.getStatement(num));
                    	return false;
                	}
                }
                logger.debug("@@@deleting statements from " 
                		+ num + ": " + copy.getStatement(num).getCode() + " source node: " + startingNode + ", until " + 
                		+ methodsToDeletePositions.get(0) + ": " + copy.getStatement(methodsToDeletePositions.get(0)).getCode() + " target node: " + lastNodeTarget
                		+ ", test case size: " + copy.size() + ", last position to delete: " + methodsToDeletePositions.get(0));
                for(Integer integer: methodsToDeletePositions){
                	mutationHistory.addMutationEntry(new TestMutationHistoryEntry(
        					TestMutationHistoryEntry.TestMutation.DELETION));
                	modified |= testFactory.deleteStatementGracefully(copy, integer);
                }
                
                boolean traceMutationHistory = true;
                this.deleteUnreferencedParameters(copy, testFactory, traceMutationHistory);
                
                /*//some parameters declaration of deleted methods might be still there 
                //after the deletion of the interesting methods because ES when deletes a method statement
                //doesn't know beforehand if the parameters used by that method are used by another
                //method call placed before the current method to delete 
                //delete them if they are not referenced by other method statements
                List<Integer> paramsToDelete = new ArrayList<Integer>();
                logger.debug("@@@analyzing not referenced statements after deletion of method statements");
                //delete constructor statements (custom classes for parameters extracted from PageObject methods preconditions)
                //that are not referenced anymore
                logger.debug("@@@analyzing not referenced constructor statements of custom classes for parameters");
                //it starts from 1 because it does not include the constructor statement of the class under test
                for(int i = 1; i < copy.size(); i++){
                	if(copy.getStatement(i) instanceof ConstructorStatement){
                		Set<Integer> positions = testFactory.getReferencePositions(copy, i);
                		boolean canRemove = true;
                		for(Integer pos: positions){
                			if(copy.getStatement(pos) instanceof MethodStatement){
                				canRemove = false;
                				break;
                			}
                		}
                		if(canRemove){
                			logger.debug("@@@removing constructor statement: " + copy.getStatement(i).getCode() + " because is not referenced");
                			paramsToDelete.add(i);
                		}
                	}
                }
                Collections.sort(paramsToDelete, Collections.reverseOrder());
                for(Integer integer: paramsToDelete){
                	mutationHistory.addMutationEntry(new TestMutationHistoryEntry(
        					TestMutationHistoryEntry.TestMutation.DELETION));
                	modified |= testFactory.deleteStatementGracefully(copy, integer);
                }
                paramsToDelete.clear();
                logger.debug("@@@analyzing not referenced primitive statements");
                //delete primitive statements that are not referenced anymore (some primitive statements might depend on just removed constructor statements)
                for(int i = 1; i < copy.size(); i++){
                	if(copy.getStatement(i) instanceof PrimitiveStatement){
                		Set<Integer> positions = testFactory.getReferencePositions(copy, i);
                		boolean canRemove = true;
                		for(Integer pos: positions){
                			//EntityWithParametersStatement: both MethodStatements and ConstructorStatement
                			if(copy.getStatement(pos) instanceof EntityWithParametersStatement){
                				canRemove = false;
                				break;
                			}
                		}
                		if(canRemove){
                			logger.debug("@@@removing primitive statement: " + copy.getStatement(i).getCode() + " because is not referenced");
                			paramsToDelete.add(i);
                		}
                	}
                }
                Collections.sort(paramsToDelete, Collections.reverseOrder());
                for(Integer integer: paramsToDelete){
                	mutationHistory.addMutationEntry(new TestMutationHistoryEntry(
        					TestMutationHistoryEntry.TestMutation.DELETION));
                	modified |= testFactory.deleteStatementGracefully(copy, integer);
                }
                paramsToDelete.clear();*/
            }
            else{
            	mutationHistory.addMutationEntry(new TestMutationHistoryEntry(
    					TestMutationHistoryEntry.TestMutation.DELETION));
                modified = testFactory.deleteStatementGracefully(copy, num);
            }
            
            test = copy;
           	return modified;

        } catch (ConstructionFailedException e) {
            logger.warn("Deletion of statement failed: " + test.getStatement(num).getCode());
            logger.warn(test.toCode());
			return false; //modifications were on copy
        }
	}
	
	/**
	 * @param target must be a node of the graph
	 * */
	private void insertRandomPath(TestCase test, String startVertex, String target){
		int maxSteps = Randomness.nextInt(1,Properties.CHROMOSOME_LENGTH);
		List<String> edges = PathGenerator.generateRandomPathWithTarget(Importer.graph, startVertex, target, maxSteps);
		logger.debug("@@@random path generated size: " + edges.size());
		String lastEdge = edges.get(edges.size() - 1);
		logger.debug("insertRandomPath: lastEdge " + lastEdge);
		String lastNode = GraphParser.getEdgeTarget(lastEdge);
		logger.debug("insertRandomPath: lastNode " + lastNode);
		int previousLength = edges.size();
		if(!lastNode.equals(target)){
			//search shortest path directly on the graph not in the subgraph given that it is not a random path
			//and so it is not biased by the not relevant nodes (out of the subgraph)
			PathGenerator.shortestPathToVertex(Importer.graph, lastEdge, target, edges);
			int newLength = edges.size();
			logger.debug("@@@apply shortest path of length " + (newLength - previousLength) + " to arrive from " + lastNode + " to " + target);
		}
		boolean success = false;
		int oldSize;
		int position;
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
			if (success) {
				assert ConstraintVerifier.verifyTest(test);
				assert ! ConstraintVerifier.hasAnyOnlyForAssertionMethod(test);
			}else{
				throw new IllegalStateException(this.getClass().getName() + " crossover failed in inserting random path between head and tail");
			}	
		}
	}
	
	private boolean deleteUnreferencedParameters(TestCase copy, TestFactory testFactory, boolean traceMutationHistory) throws ConstructionFailedException{
		//some parameters declaration of deleted methods might be still there 
        //after the deletion of the interesting methods because ES when deletes a method statement
        //doesn't know beforehand if the parameters used by that method are used by another
        //method call placed before the current method to delete.
        //delete them if they are not referenced by other method statements
		boolean modified = false;
        List<Integer> paramsToDelete = new ArrayList<Integer>();
        logger.debug("@@@analyzing not referenced statements after deletion of method statements");
        //delete constructor statements (custom classes for parameters extracted from PageObject methods preconditions)
        //that are not referenced anymore
        logger.debug("@@@analyzing not referenced constructor statements of custom classes for parameters");
        //it starts from 1 because it does not include the constructor statement of the class under test
        for(int i = 1; i < copy.size(); i++){
        	if(copy.getStatement(i) instanceof ConstructorStatement){
        		Set<Integer> positions = testFactory.getReferencePositions(copy, i);
        		boolean canRemove = true;
        		for(Integer pos: positions){
        			if(copy.getStatement(pos) instanceof MethodStatement){
        				canRemove = false;
        				break;
        			}
        		}
        		if(canRemove){
        			logger.debug("@@@removing constructor statement: " + copy.getStatement(i).getCode() + " because is not referenced");
        			paramsToDelete.add(i);
        		}
        	}
        }
        Collections.sort(paramsToDelete, Collections.reverseOrder());
        for(Integer integer: paramsToDelete){
        	if(traceMutationHistory){
        		mutationHistory.addMutationEntry(new TestMutationHistoryEntry(
    					TestMutationHistoryEntry.TestMutation.DELETION));
        	}
        	modified |= testFactory.deleteStatementGracefully(copy, integer);
        }
        paramsToDelete.clear();
        logger.debug("@@@analyzing not referenced primitive statements");
        //delete primitive statements that are not referenced anymore (some primitive statements might depend on just removed constructor statements)
        for(int i = 1; i < copy.size(); i++){
        	if(copy.getStatement(i) instanceof PrimitiveStatement){
        		Set<Integer> positions = testFactory.getReferencePositions(copy, i);
        		boolean canRemove = true;
        		for(Integer pos: positions){
        			//EntityWithParametersStatement: both MethodStatements and ConstructorStatement
        			if(copy.getStatement(pos) instanceof EntityWithParametersStatement){
        				canRemove = false;
        				break;
        			}
        		}
        		if(canRemove){
        			logger.debug("@@@removing primitive statement: " + copy.getStatement(i).getCode() + " because is not referenced");
        			paramsToDelete.add(i);
        		}
        	}
        }
        Collections.sort(paramsToDelete, Collections.reverseOrder());
        for(Integer integer: paramsToDelete){
        	if(traceMutationHistory){
        		mutationHistory.addMutationEntry(new TestMutationHistoryEntry(
    					TestMutationHistoryEntry.TestMutation.DELETION));
        	}
        	modified |= testFactory.deleteStatementGracefully(copy, integer);
        }
        paramsToDelete.clear();
        return modified;
	}
	
	/**
	 * Each statement is replaced with probability 1/length
	 *
	 * @return
	 */
	private boolean mutationChange() {
		boolean changed = false;
		int lastMutatableStatement = getLastMutatableStatement();
		double pl = 1d / (lastMutatableStatement + 1); 
		TestFactory testFactory = TestFactory.getInstance();

		if (Randomness.nextDouble() < Properties.CONCOLIC_MUTATION) {
			try {
				changed = mutationConcolic();
			} catch (Exception exc) {
				logger.warn("Encountered exception when trying to use concolic mutation: {}", exc.getMessage());
				logger.debug("Detailed exception trace: ", exc);
			}
		}

		if (!changed) {
			for (int position = 0; position < test.size(); position++) {
				if (Randomness.nextDouble() <= pl) {
					assert (test.isValid());

					Statement statement = test.getStatement(position);
					
					if(Properties.CUSTOM_MUTATION_CHANGE){
						//do not change the constructor of the class under test 
						if(position == 0 && statement instanceof ConstructorStatement){
							continue;
						}
					}

					if(statement.isReflectionStatement())
						continue;

					int oldDistance = statement.getReturnValue().getDistance();
					
					logger.debug("@@@@changing statement: {}", statement.getCode());

					//constraints are handled directly in the statement mutations
					if (statement.mutate(test, testFactory)) {
						
						logger.debug("@@@@statement {} changed", statement.getCode());
						
						changed = true;
						mutationHistory.addMutationEntry(new TestMutationHistoryEntry(
						        TestMutationHistoryEntry.TestMutation.CHANGE, statement));
						assert (test.isValid());
						assert ConstraintVerifier.verifyTest(test);

					} else if (!statement.isAssignmentStatement() &&
							ConstraintVerifier.canDelete(test,position) && !Properties.SELENIUM_WHOLE) {
						//for the moment disable this behaviour: find out what is the aim of it 
						
						//if a statement should not be deleted, then it cannot be either replaced by another one

						int pos = statement.getPosition();
						if (testFactory.changeRandomCall(test, statement)) {
							changed = true;
							mutationHistory.addMutationEntry(new TestMutationHistoryEntry(
							        TestMutationHistoryEntry.TestMutation.CHANGE,
							        test.getStatement(pos)));
							assert ConstraintVerifier.verifyTest(test);
						}
						assert (test.isValid());
					}

					statement.getReturnValue().setDistance(oldDistance);
					position = statement.getPosition(); // Might have changed due to mutation
				}
			}
		}

		if(changed){
			assert ConstraintVerifier.verifyTest(test);
		}

		return changed;
	}

	/**
	 * Each statement is replaced with probability 1/length
	 *
	 * @return
	 */
	private boolean mutationSeleniumChange() {
		boolean changed = false;
		//int lastMutatableStatement = getLastMutatableStatement();
		//double pl = 1d / (lastMutatableStatement + 1);
		double pl = (double)1.0/Properties.GENOTYPE_LENGTH; 
		TestFactory testFactory = TestFactory.getInstance();

		if (!changed) {
			/*
			 * the first statement of the chromosome is the constructor of the class under test
			 * mutate statements according to the genotypes
			 * */
			int j = 0;
			int k = 0;
			while(j < Properties.CHROMOSOME_PARTS)
			{
				for (int position = 1 + k; position < Properties.GENOTYPE_LENGTH + 1 + k; position++) {
					if (Randomness.nextDouble() <= pl) {
						assert (test.isValid());
						
						logger.debug("@@@@changing statement position: {}, k: {}, j: {}", position, k , j);
						
						Statement statement = test.getStatement(position);
						
						logger.debug("@@@@statement: {}", statement.getCode());

						if(statement.isReflectionStatement())
							continue;

						int oldDistance = statement.getReturnValue().getDistance();
						

						//constraints are handled directly in the statement mutations
						if (statement.mutate(test, testFactory)) {
							
							logger.debug("@@@@statement {} changed", statement.getCode());
							
							changed = true;
							mutationHistory.addMutationEntry(new TestMutationHistoryEntry(
							        TestMutationHistoryEntry.TestMutation.CHANGE, statement));
							//assert (test.isValid());
							//assert ConstraintVerifier.verifyTest(test);

						} /*else if (!statement.isAssignmentStatement() &&
								ConstraintVerifier.canDelete(test,position)) {
							//if a statement should not be deleted, then it cannot be either replaced by another one

							logger.debug("@@@@isAssignmentStatement && canDelete");
							
							int pos = statement.getPosition();
							if (testFactory.changeRandomCall(test, statement)) {
								changed = true;
								mutationHistory.addMutationEntry(new TestMutationHistoryEntry(
								        TestMutationHistoryEntry.TestMutation.CHANGE,
								        test.getStatement(pos)));
								assert ConstraintVerifier.verifyTest(test);
							}
							assert (test.isValid());
						}*/

						statement.getReturnValue().setDistance(oldDistance);
						//position = statement.getPosition(); // Might have changed due to mutation
					}
				}
				
				/*int nextBeginning = k + Properties.GENOTYPE_LENGTH;
				if(Properties.CHROMOSOME_PARTS > 1){
					//we don't want to mutate method call statement that is in the middle of two chromosomes of the same individual
					//+1 is to skip the mutation of the method call statement
					nextBeginning = k + Properties.GENOTYPE_LENGTH + 1;
				}*/
				
				//we don't want to mutate method call statement that is in the middle of two chromosomes of the same individual
				//+1 is to skip the mutation of the method call statement
				k = k + Properties.GENOTYPE_LENGTH + 1;
				j++;
			}
		}

		/*if(changed){
			assert ConstraintVerifier.verifyTest(test);
		}*/

		return changed;
	}

	/**
	 * With exponentially decreasing probability, insert statements at random
	 * position
	 *
	 * @return
	 */
	public boolean mutationInsert() {
		boolean changed = false;
		final double ALPHA = Properties.P_STATEMENT_INSERTION; //0.5;
		int count = 0;
		TestFactory testFactory = TestFactory.getInstance();

		while (Randomness.nextDouble() <= Math.pow(ALPHA, count)
		        && (!Properties.CHECK_MAX_LENGTH || size() < Properties.CHROMOSOME_LENGTH)) {

			count++;
			if(Properties.CUSTOM_MUTATION_INSERT){
				if(test.isEmpty()) {
					logger.debug("@@@test case is empty: insertion cannot be performed");
					return false;
				}
				List<Integer> positions = testFactory.insertRandomPath(test, getLastMutatableStatement());
				if(positions != null)
				{
					for(int i = 0; i < positions.size(); i++){
						if (positions.get(i) >= 0 && positions.get(i) < test.size()) {
							changed = true;
							mutationHistory.addMutationEntry(new TestMutationHistoryEntry(
							        TestMutationHistoryEntry.TestMutation.INSERTION,
							        test.getStatement(positions.get(i))));
						}
					}
				}
			}else{
				// Insert at position as during initialization (i.e., using helper sequences)
				int position = testFactory.insertRandomStatement(test, getLastMutatableStatement());

				if (position >= 0 && position < test.size()) {
					changed = true;
					mutationHistory.addMutationEntry(new TestMutationHistoryEntry(
					        TestMutationHistoryEntry.TestMutation.INSERTION,
					        test.getStatement(position)));
				}

			}
		}
		return changed;
	}

	/**
	 * Collect path constraints and negate one of them to derive new integer
	 * inputs
	 *
	 * @return
	 */
	private boolean mutationConcolic() {
		logger.info("Applying DSE mutation");
		// concolicExecution = new ConcolicExecution();

		// Apply DSE to gather constraints
		List<BranchCondition> branches = ConcolicExecution.getSymbolicPath(this);
		logger.debug("Conditions: " + branches);
		if (branches.isEmpty())
			return false;

		boolean mutated = false;
		List<BranchCondition> targetBranches = new ArrayList<BranchCondition>();
		for (BranchCondition branch : branches) {
			if (TestCluster.isTargetClassName(branch.getClassName()))
				targetBranches.add(branch);
		}
		// Select random branch
		BranchCondition branch = null;
		if (targetBranches.isEmpty())
			branch = Randomness.choice(branches);
		else
			branch = Randomness.choice(targetBranches);

		logger.debug("Trying to negate branch " + branch.getInstructionIndex()
		        + " - have " + targetBranches.size() + "/" + branches.size()
		        + " target branches");

		// Try to solve negated constraint
		TestCase newTest = ConcolicMutation.negateCondition(branches, branch, test);

		// If successful, add resulting test to test suite
		if (newTest != null) {
			logger.debug("CONCOLIC: Created new test");
			// logger.info(newTest.toCode());
			// logger.info("Old test");
			// logger.info(test.toCode());
			this.test = newTest;
			this.setChanged(true);
			this.lastExecutionResult = null;
		} else {
			logger.debug("CONCOLIC: Did not create new test");
		}

		return mutated;
	}

	/**
	 * {@inheritDoc}
	 *
	 * The size of a chromosome is the length of its test case
	 */
	@Override
	public int size() {
		return test.size();
	}

	/** {@inheritDoc} */
	@Override
	public int compareTo(Chromosome o) {
		int result = super.compareTo(o);
		if (result != 0) {
			return result;
		}
		// make this deliberately not 0
		// because then ordering of results will be random
		// among tests of equal fitness
		if (o instanceof TestChromosome) {
			return test.toCode().compareTo(((TestChromosome) o).test.toCode());
		}
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return test.toCode();
	}

	/**
	 * <p>
	 * hasException
	 * </p>
	 *
	 * @return a boolean.
	 */
	public boolean hasException() {
		return lastExecutionResult == null ? false
		        : !lastExecutionResult.noThrownExceptions();
	}


	/* (non-Javadoc)
	 * @see org.evosuite.ga.Chromosome#applyDSE()
	 */
	/** {@inheritDoc} */
	/*
	@Override
	public boolean applyDSE(GeneticAlgorithm<?> ga) {
		// TODO Auto-generated method stub
		return false;
	}
	*/

	/** {@inheritDoc} */
	@Override
	public ExecutionResult executeForFitnessFunction(
	        TestSuiteFitnessFunction testSuiteFitnessFunction) {
		return testSuiteFitnessFunction.runTest(this.test);
	}

	@Override
	@SuppressWarnings("unchecked")
	public  <T extends Chromosome> int compareSecondaryObjective(T o) {
		int objective = 0;
		int c = 0;

		while (c == 0 && objective < secondaryObjectives.size()) {

			SecondaryObjective<T> so = (SecondaryObjective<T>) secondaryObjectives.get(objective++);
			if (so == null)
				break;
			c = so.compareChromosomes((T) this, o);
		}
		return c;
	}
	/**
	 * Add an additional secondary objective to the end of the list of
	 * objectives
	 *
	 * @param objective
	 *            a {@link org.evosuite.ga.SecondaryObjective} object.
	 */
	public static void addSecondaryObjective(SecondaryObjective<?> objective) {
		secondaryObjectives.add(objective);
	}

	public static void ShuffleSecondaryObjective() {
		Collections.shuffle(secondaryObjectives);
	}

	public static void reverseSecondaryObjective() {
		Collections.reverse(secondaryObjectives);
	}

	/**
	 * Remove secondary objective from list, if it is there
	 *
	 * @param objective
	 *            a {@link org.evosuite.ga.SecondaryObjective} object.
	 */
	public static void removeSecondaryObjective(SecondaryObjective<?> objective) {
		secondaryObjectives.remove(objective);
	}

	/**
	 * <p>
	 * Getter for the field <code>secondaryObjectives</code>.
	 * </p>
	 *
	 * @return a {@link java.util.List} object.
	 */
	public static List<SecondaryObjective<?>> getSecondaryObjectives() {
		return secondaryObjectives;
	}

	public SeleniumGenotype getGenotype() {
		return genotype;
	}

	public void setGenotype(SeleniumGenotype genotype) {
		this.genotype = genotype;
	}

}
