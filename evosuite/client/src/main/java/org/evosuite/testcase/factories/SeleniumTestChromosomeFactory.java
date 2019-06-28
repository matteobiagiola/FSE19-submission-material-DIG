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

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.evosuite.Properties;
import org.evosuite.ga.ChromosomeFactory;
import org.evosuite.ga.ConstructionFailedException;
import org.evosuite.setup.TestCluster;
import org.evosuite.testcase.DefaultTestCase;
import org.evosuite.testcase.TestCase;
import org.evosuite.testcase.TestChromosome;
import org.evosuite.testcase.TestFactory;
import org.evosuite.testcase.execution.ExecutionTracer;
import org.evosuite.testcase.statements.ConstructorStatement;
import org.evosuite.testcase.statements.MethodStatement;
import org.evosuite.testcase.statements.Statement;
import org.evosuite.testcase.variable.VariableReference;
import org.evosuite.utils.generic.GenericAccessibleObject;
import org.evosuite.utils.generic.GenericConstructor;
import org.evosuite.utils.generic.GenericMethod;
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
public class SeleniumTestChromosomeFactory implements ChromosomeFactory<TestChromosome> {

	private static final long serialVersionUID = -420224349882780856L;

	/** Constant <code>logger</code> */
	protected static final Logger logger = LoggerFactory.getLogger(SeleniumTestChromosomeFactory.class);

	/** Methods we have already seen */
	private static Set<GenericAccessibleObject<?>> attemptedMethods = new LinkedHashSet<GenericAccessibleObject<?>>();

	/** Methods we have not already seen */
	private static Set<GenericAccessibleObject<?>> remainingMethods = new LinkedHashSet<GenericAccessibleObject<?>>();

	/** Methods we have to cover */
	private static List<GenericAccessibleObject<?>> allMethods = new LinkedList<GenericAccessibleObject<?>>();

	/**
	 * Create a list of all methods
	 */
	public SeleniumTestChromosomeFactory() {
		allMethods.addAll(TestCluster.getInstance().getTestCalls());
		reset();
	}

	/**
	 * Create a random individual
	 * 
	 * @param size
	 */
	private TestCase getRandomTestCase(TestChromosome c, int size) {
		
		boolean tracerEnabled = ExecutionTracer.isEnabled();
		if (tracerEnabled)
			ExecutionTracer.disable();

		TestCase test = getNewTestCase();
		int num = 0;
		boolean canReuseExistingVariables = false;
		TestFactory testFactory = TestFactory.getInstance();

		while (test.size() < size && num < Properties.MAX_ATTEMPTS) {
			
			logger.debug("@@@@while loop test case size: {} , loop index: {}", test.size(), num);
			GenericMethod method = null;

			for(Object obj : remainingMethods){
				GenericAccessibleObject<?> call = (GenericAccessibleObject<?>) obj;
				logger.debug("@@@@call: {}", call.toString());
				if (call.isMethod()) {
					logger.debug("@@@@call is method: {}", call.toString());
					method = (GenericMethod) call;
					break;
				}
			}
			
			if(method != null){
				try 
				{
					//testFactory.addMethod(test, method, test.size(), 0, canReuseExistingVariables);
					if(num == 0) {
						logger.debug("@@@@addMethod call");
						testFactory.addMethod(test, method, test.size(), 0, canReuseExistingVariables);
					}
					else
					{
						logger.debug("@@@@addMethodFor call");
						ConstructorStatement cStmt = (ConstructorStatement) test.getStatement(0);
						logger.debug("@@@@cStmt: " + cStmt.getCode());
						VariableReference cStmtVar = cStmt.getReturnValue();
						logger.debug("@@@@cStmtVar: " + cStmtVar);
						testFactory.addMethodFor(test, cStmtVar, method, test.size(), canReuseExistingVariables);
					}
				} catch (ConstructionFailedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			num++;
		}
		
		if (logger.isDebugEnabled())
			logger.debug("@@@@Randomized test case final:" + test.toCode());
		
		/*
		 * getting method call statements and moving them at the end of the test case
		 * for crossover and mutation operators to work correctly 
		 * */
		/*TestCase newTest = getNewTestCase();
		
		List<MethodStatement> methodStmts = new ArrayList<MethodStatement>();
		List<Statement> stmts = new ArrayList<Statement>();
		for(int i = 0; i < test.size(); i++){
			if(test.getStatement(i) instanceof MethodStatement){
				MethodStatement methodStmt = (MethodStatement) test.getStatement(i);
				methodStmts.add(methodStmt);
			}
			else{
				newTest.addStatement(test.getStatement(i));
				stmts.add(test.getStatement(i));
			}
		}
		
		for(int i = 0; i < methodStmts.size(); i++){
			stmts.add(methodStmts.get(i));
			newTest.addStatement(methodStmts.get(i));
		}*/
		
		/*for(int i = test.size(); i >= 0; i--){
			test.remove(i);
		}
		
		test.addStatements(stmts);*/
		
		//logger.debug("@@@@Test case after method call movements: " + newTest.toCode());
		//logger.debug("@@@@Statements newTest: " + ((DefaultTestCase) newTest).statements.size() + " oldTest: " + ((DefaultTestCase) test).statements.size());
		//logger.debug("@@@@Access last statement oldTest: " + test.getStatement(test.size() - 1).toString() + " newTest: " + newTest.getStatement(newTest.size() - 1));
		

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
		//# of params x # of chromosome parts + 1 constructor call + # of chromosome parts (number of method calls, one for each chromosome part)
		int chromosomeSize = (Properties.GENOTYPE_LENGTH * Properties.CHROMOSOME_PARTS) + 1 + Properties.CHROMOSOME_PARTS;
		c.setTestCase(getRandomTestCase(c, chromosomeSize));
		
		return c;
	}

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
}
