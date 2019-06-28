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
package org.evosuite.testcase.execution;

import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Driver;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

import org.evosuite.Properties;
import org.evosuite.TestGenerationContext;
import org.evosuite.Stats;
import org.evosuite.ga.stoppingconditions.MaxStatementsStoppingCondition;
import org.evosuite.ga.stoppingconditions.MaxTestsStoppingCondition;
import org.evosuite.rmi.ClientServices;
import org.evosuite.rmi.service.ClientState;
import org.evosuite.runtime.LoopCounter;
import org.evosuite.runtime.sandbox.PermissionStatistics;
import org.evosuite.runtime.sandbox.Sandbox;
import org.evosuite.runtime.util.JOptionPaneInputs;
import org.evosuite.runtime.util.SystemInUtil;
import org.evosuite.selenium.graph.GraphParser;
import org.evosuite.selenium.state.ResetAppState;
import org.evosuite.selenium.utils.CheckCondition;
import org.evosuite.selenium.utils.PageObjectTestCaseMinimizer;
import org.evosuite.selenium.utils.TestCaseLogger;
import org.evosuite.setup.TestCluster;
import org.evosuite.testcase.TestCase;
import org.evosuite.testcase.execution.reset.ClassReInitializer;
import org.evosuite.testcase.statements.MethodStatement;
import org.evosuite.testcase.statements.Statement;
import org.evosuite.testcase.statements.numeric.LongPrimitiveStatement;
import org.evosuite.testcase.variable.VariableReference;
import org.evosuite.testcase.variable.VariableReferenceImpl;
import org.evosuite.utils.generic.GenericClass;
import org.evosuite.utils.generic.GenericMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * The test case executor manages thread creation/deletion to execute a test
 * case
 * </p>
 * 
 * <p>
 * WARNING: never give "privileged" rights in MSecurityManager to any of the
 * threads generated here
 * </p>
 * 
 * @author Gordon Fraser
 */
public class TestCaseExecutor implements ThreadFactory {

	/**
	 * Used to identify the threads spawn by the SUT
	 */
	public static final String TEST_EXECUTION_THREAD_GROUP = "Test_Execution_Group";

	/**
	 * Name used to define the threads spawn by this factory
	 */
	public static final String TEST_EXECUTION_THREAD = "TEST_EXECUTION_THREAD";

	private static final Logger logger = LoggerFactory.getLogger(TestCaseExecutor.class);

	private static final PrintStream systemOut = System.out;
	private static final PrintStream systemErr = System.err;

	private static TestCaseExecutor instance = null;

	private ExecutorService executor;

	private Thread currentThread = null;

	private ThreadGroup threadGroup = null;

	// private static ExecutorService executor =
	// Executors.newCachedThreadPool();

	private Set<ExecutionObserver> observers;

	private final Set<Thread> stalledThreads = new HashSet<Thread>();

	/** Constant <code>timeExecuted=0</code> */
	public static long timeExecuted = 0;

	/** Constant <code>testsExecuted=0</code> */
	public static int testsExecuted = 0;

	/**
	 * Used when we spawn a new thread to give a unique name
	 */
	public volatile int threadCounter;

	private final static List<String> customExceptions = Arrays.asList(Properties.CUSTOM_EXCEPTIONS);

	static {
		PermissionStatistics.getInstance().setThreadGroupToMonitor(TEST_EXECUTION_THREAD_GROUP);
	}

	/**
	 * <p>
	 * Getter for the field <code>instance</code>.
	 * </p>
	 * 
	 * @return a {@link org.evosuite.testcase.execution.TestCaseExecutor}
	 *         object.
	 */
	public static synchronized TestCaseExecutor getInstance() {
		if (instance == null)
			instance = new TestCaseExecutor();

		return instance;
	}

	private static Collection<Throwable> filterThrownExceptions(Collection<Throwable> thrownExceptions){
		return thrownExceptions.stream()
				.filter(throwable -> {
					String throwableNameWithoutMock = throwable.getClass().getSimpleName().replace("Mock", "");
					if(!customExceptions.contains(throwableNameWithoutMock)){
						return true;
					}else{
						return false;
					}
				}).collect(Collectors.toList());
	}

	/**
	 * Execute a test case
	 * 
	 * @param test
	 *            The test case to execute
	 * @return Result of the execution
	 */
	public static ExecutionResult runTest(TestCase test) {

		ExecutionResult result = new ExecutionResult(test, null);

		try {
			TestCaseExecutor executor = getInstance();
			logger.info("Executing test");

			if(Properties.DEBUG_TEST_CASE){
				TestCaseLogger.getInstance().log(test);
			}

			if(Properties.ADD_SLEEP_STATEMENTS_IN_TEST_CASE){
				long sleepValue = 0;
				List<Integer> methodCallPositionsOriginalTestCase = getMethodCallPositions(test);
				TestCase testCaseWithSleeps = insertSleepStmtsInTestCase(test, sleepValue, methodCallPositionsOriginalTestCase);
				logger.info("Executing test. Id: " + test.getID() + " (Client state: " + ClientServices.getInstance().getClientNode().getCurrentStateName() + ")");
				result = executor.executeSelenium(testCaseWithSleeps);
				long executionTime = result.getExecutionTime();
				Collection<Throwable> thrownExceptions = result.getAllThrownExceptions();
				if(Properties.CUT_EXCEPTIONS){
					if(!test.isMinimized() || test.hasMinimizationFailed()){
						thrownExceptions = filterThrownExceptions(thrownExceptions);
					}
				}
				if(thrownExceptions.size() > 0) {
					logger.info("Test with Id " + test.getID() + " failed");
				}else{
					logger.info("Test with Id " + test.getID() + " succeeded");
				}
				int maxAttempts = 0;
				TestCase newTestCaseWithSleeps = null;
				//TODO: minimize test case before re-executed, that could save much time. Problem: modifying original test case with info on exceptions thrown from last executed test case (line 215)
//                if(Properties.CUT_EXCEPTIONS && thrownExceptions.size() > 0){
//                    PageObjectTestCaseMinimizer pageObjectTestCaseMinimizer = new PageObjectTestCaseMinimizer();
//                    TestCase testCaseMinimized = pageObjectTestCaseMinimizer.minimizeTestCase(test);
//                    // get method call positions for minimized test case
//                    testCaseWithSleeps = insertSleepStmtsInTestCase(testCaseMinimized, sleepValue, methodCallPositionsOriginalTestCase);
//                }
				while (thrownExceptions.size() > 0 && maxAttempts < Properties.MAX_ATTEMPTS_EXECUTION_TEST_CASE){
					sleepValue = 150 * (maxAttempts + 1); // increment 150 ms each attempt
					newTestCaseWithSleeps = changeValueOfSleep(testCaseWithSleeps, sleepValue);
					logger.info("Going to re-execute test with Id " + test.getID() + " using sleep timeout " + sleepValue);

					result = executor.executeSelenium(newTestCaseWithSleeps);

					logger.info("Previous execution time [ms]: " + executionTime + "; current execution time [ms]: "
							+ result.getExecutionTime() + ". Current execution time should be more or less "
							+ methodCallPositionsOriginalTestCase.size() * sleepValue + " units bigger than the previous execution time." +
							" The difference between the two is " + (result.getExecutionTime() - executionTime));

					if(executionTime >= result.getExecutionTime()){
						logger.warn("Previous execution time [ms] " + executionTime
								+ " is bigger than current execution time [ms] " + result.getExecutionTime()
								+ ". It may be a problem!");
					}

					executionTime = result.getExecutionTime();

					MaxStatementsStoppingCondition.statementsExecuted(result.getExecutedStatements());

					thrownExceptions = result.getAllThrownExceptions();

					if(Properties.CUT_EXCEPTIONS){
						if(!test.isMinimized() || test.hasMinimizationFailed()){
							thrownExceptions = filterThrownExceptions(thrownExceptions);
						}
					}

					maxAttempts++;
				}

				if(Properties.CUT_EXCEPTIONS){
					TestCase lastTestCaseExecuted;
					if(newTestCaseWithSleeps != null){
						lastTestCaseExecuted = newTestCaseWithSleeps;
					}else{
						lastTestCaseExecuted = testCaseWithSleeps;
					}

					Map<Integer, Throwable> lastExecutedTestCaseMapOfThrownExceptions = result.getCopyOfExceptionMapping();
					Map<Integer, Throwable> originalTestCaseMapOfThrownExceptions = new LinkedHashMap<>();

					logger.debug("runTest: exceptions thrown at positions " + result.getPositionsWhereExceptionsWereThrown()
							+ " in last executed test case \n" + lastTestCaseExecuted.toCode(lastExecutedTestCaseMapOfThrownExceptions));

					List<Integer> methodCallPositionsLastTestCaseExecuted = getMethodCallPositions(lastTestCaseExecuted);
					CheckCondition.checkState(methodCallPositionsLastTestCaseExecuted.size() == methodCallPositionsOriginalTestCase.size(),
							"runTest: method calls of last executed test " +
									"case (with sleeps) are different from method calls of original test case: "
									+ methodCallPositionsLastTestCaseExecuted.size() + " vs " + methodCallPositionsOriginalTestCase.size());
					for (int i = 0; i < methodCallPositionsOriginalTestCase.size(); i++) {
						int indexOriginalTestCase = methodCallPositionsOriginalTestCase.get(i);
						int indexLastExecutedTestCase = methodCallPositionsLastTestCaseExecuted.get(i);
						CheckCondition.checkState(test.hasStatement(indexOriginalTestCase),
								"runTest: test case " + test.toCode() + " has no statement at index " + indexOriginalTestCase);
						Statement originalStatement = test.getStatement(indexOriginalTestCase);
						CheckCondition.checkState(originalStatement instanceof MethodStatement,
								"runTest: statement " + originalStatement + " is not a method statement");
						MethodStatement originalMethodStatement = (MethodStatement) originalStatement;
						CheckCondition.checkState(lastTestCaseExecuted.hasStatement(indexLastExecutedTestCase),
								"runTest: test case " + lastTestCaseExecuted.toCode() + " has no statement at index " + indexLastExecutedTestCase);
						Statement lastExecutedStatement = lastTestCaseExecuted.getStatement(indexLastExecutedTestCase);
						CheckCondition.checkState(lastExecutedStatement instanceof MethodStatement,
								"runTest: statement " + lastExecutedStatement + " is not a method statement");
						MethodStatement lastExecutedMethodStatement = (MethodStatement) lastExecutedStatement;
						CheckCondition.checkState(originalMethodStatement.getMethodName().equals(lastExecutedMethodStatement.getMethodName())
								,"runTest: two method statements must be equal. Found original: "
										+ originalMethodStatement.getMethodName() + "; last executed: " + lastExecutedMethodStatement.getMethodName());
						if(result.isThereAnExceptionAtPosition((indexLastExecutedTestCase))){
							// an exception was thrown at position 'indexLastExecutedTestCase'
							// map this exception in the original test case at position 'indexOriginalTestCase'
							Throwable throwable = result.getExceptionThrownAtPosition(indexLastExecutedTestCase);
							CheckCondition.checkState(throwable != null, "runTest: no exception was found at index "
									+ indexLastExecutedTestCase + " in test case " + lastTestCaseExecuted.toCode());
							originalTestCaseMapOfThrownExceptions.put(indexOriginalTestCase, throwable);
						}
					}
					result.setThrownExceptions(originalTestCaseMapOfThrownExceptions);

					logger.debug("runTest: exceptions thrown at positions " + result.getPositionsWhereExceptionsWereThrown()
							+ " in original test case \n" + test.toCode(originalTestCaseMapOfThrownExceptions));

					if(test.isMinimized()){
						test.setMinimized(false);
					}
				}


				if(maxAttempts == Properties.MAX_ATTEMPTS_EXECUTION_TEST_CASE){
                    String exceptions = thrownExceptions.stream()
                            .map(throwable -> {
                                String toPrint = "Message: " + throwable.getMessage() + ". Cause: " + throwable.getCause() + ".";
//						    String stackTrace = Arrays.stream(throwab le.getStackTrace())
//                                    .map(stackTraceElement -> "\tat " + stackTraceElement)
//                                    .collect(Collectors.joining("\n"));
//							return toPrint + "\n" + stackTrace;
                                return toPrint;
                            })
                            .collect(Collectors.joining(":::"));
					logger.info("runTest: giving up re-execution of test case with Id "
							+ test.getID() +  " after " + Properties.MAX_ATTEMPTS_EXECUTION_TEST_CASE
							+ " attempts. Exceptions: " + exceptions);

					if(Properties.CUT_EXCEPTIONS){
						logger.info("Test case that failed: \n" + test.toCode(result.getCopyOfExceptionMapping()));
					}else{
						logger.info("Test case that failed: \n" + test.toCode());
					}

					throw new IllegalStateException("Test generation failed: page object bug.");
				}
			}else{

				result = executor.execute(test);

				if(Properties.DEBUG_TEST_CASE){
					TestCaseLogger.getInstance().logSpace(2);
				}

				MaxStatementsStoppingCondition.statementsExecuted(result.getExecutedStatements());
			}


		} catch (Exception e) {
			logger.error("TG: Exception caught: ", e);
			throw new Error(e);
		}

		return result;
	}

	private TestCaseExecutor() {
		executor = Executors.newSingleThreadExecutor(this);
		newObservers();
	}

	public static class TimeoutExceeded extends RuntimeException {
		private static final long serialVersionUID = -5314228165430676893L;
	}

	/**
	 * <p>
	 * setup
	 * </p>
	 */
	public void setup() {
		// start own thread
	}

	/**
	 * <p>
	 * pullDown
	 * </p>
	 */
	public static void pullDown() {
		if (instance != null) {
			if (instance.executor != null) {
				instance.executor.shutdownNow();
				instance.executor = null;
			}
		}
	}

	/**
	 * <p>
	 * initExecutor
	 * </p>
	 */
	public static void initExecutor() {
		if (instance != null) {
			if (instance.executor == null) {
				logger.info("TestCaseExecutor instance is non-null, but its actual executor is null");
				instance.executor = Executors.newSingleThreadExecutor(instance);
			} else {
				instance.executor = Executors.newSingleThreadExecutor(instance);
			}
		}
	}

	/**
	 * <p>
	 * addObserver
	 * </p>
	 * 
	 * @param observer
	 *            a {@link org.evosuite.testcase.execution.ExecutionObserver}
	 *            object.
	 */
	public void addObserver(ExecutionObserver observer) {
		if (!observers.contains(observer)) {
			logger.debug("Adding observer {}",observer.getClass());
			observers.add(observer);
		}
		// FIXXME: Find proper solution for this
		// for (ExecutionObserver o : observers)
		// if (o.getClass().equals(observer.getClass()))
		// return;

	}

	/**
	 * <p>
	 * removeObserver
	 * </p>
	 * 
	 * @param observer
	 *            a {@link org.evosuite.testcase.execution.ExecutionObserver}
	 *            object.
	 */
	public void removeObserver(ExecutionObserver observer) {
		if (observers.contains(observer)) {
			logger.debug("Removing observer");
			observers.remove(observer);
		}
	}

	/**
	 * <p>
	 * newObservers
	 * </p>
	 */
	public void newObservers() {
		observers = new LinkedHashSet<>();
	}

	public Set<ExecutionObserver> getExecutionObservers() {
		return new LinkedHashSet<ExecutionObserver>(observers);
	}

	private void resetObservers() {
		for (ExecutionObserver observer : observers) {
			observer.clear();
		}
	}

    /**
     * Execute a test case on a new scope: it is the same method that is below but I need it to make
     * sure that if add_sleep_statements_in_test_case is true a test case is executed with runTest static
     * method of this class.
     *
     * @param tc
     *            a {@link org.evosuite.testcase.TestCase} object.
     * @return a {@link org.evosuite.testcase.execution.ExecutionResult} object.
     */
    public ExecutionResult executeSelenium(TestCase tc) {
        if(Properties.RESET_STATE && !Properties.QUIT_BROWSER_TO_RESET){
            logger.info("Reset application state");
            //ResetAppState.reset();
            ResetAppState.resetViaReflection();
        }
        logger.debug("TestCaseExecutor: run test case");

        Stats.getInstance().updateNumberOfTestsCaseExecuted();

        ExecutionResult result = execute(tc, Properties.TIMEOUT);

        return result;
    }

	/**
	 * Execute a test case on a new scope
	 * 
	 * @param tc
	 *            a {@link org.evosuite.testcase.TestCase} object.
	 * @return a {@link org.evosuite.testcase.execution.ExecutionResult} object.
	 */
	public ExecutionResult execute(TestCase tc) {

	    if(Properties.ADD_SLEEP_STATEMENTS_IN_TEST_CASE){
	        throw new UnsupportedOperationException("execute - this method is not supported when add_sleep_statements_in_test_case property is true; please use runTest");
        }

		if(Properties.RESET_STATE && !Properties.QUIT_BROWSER_TO_RESET){
			logger.debug("Reset application state");
			//ResetAppState.reset();
			ResetAppState.resetViaReflection();
		}
		logger.debug("TestCaseExecutor: run test case");

		Stats.getInstance().updateNumberOfTestsCaseExecuted();

		ExecutionResult result = execute(tc, Properties.TIMEOUT);

		return result;
	}

	private static List<Integer> getMethodCallPositions(TestCase tc){
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

	private static TestCase insertSleepStmtsInTestCase(TestCase tc, long sleepValue, List<Integer> methodCallPositions){
		TestCase clone = tc.clone();
		List<Integer> methodCallPositionsCopy = new ArrayList<>(methodCallPositions);
		LongPrimitiveStatement sleepAmountStatement = new LongPrimitiveStatement(clone, sleepValue);
		clone.addStatement(sleepAmountStatement.clone(clone), 1); //first statement (index 0) is the constructor

		if(methodCallPositionsCopy.size() > 0){
			try {
				Class threadClass = Class.forName("java.lang.Thread");
				Method sleepReflectionMethod = threadClass.getDeclaredMethod("sleep", long.class);
				GenericMethod sleepGenericMethod = new GenericMethod(sleepReflectionMethod, Thread.class);
				VariableReference calleeVarReference = new VariableReferenceImpl(clone, new GenericClass(Thread.class));
				List<VariableReference> parameters = new ArrayList<>();
				Statement secondStatementOfTestCase = clone.getStatement(1);
				if(secondStatementOfTestCase instanceof LongPrimitiveStatement){
					LongPrimitiveStatement sleepAmountStatementInTestCase = (LongPrimitiveStatement) secondStatementOfTestCase;
					VariableReference sleepAmountStatementSelfReference = sleepAmountStatementInTestCase.getReturnValue();
					parameters.add(sleepAmountStatementSelfReference);
					MethodStatement sleepMethodStatement = new MethodStatement(clone, sleepGenericMethod, calleeVarReference, parameters);
					clone.addStatement(sleepMethodStatement.clone(clone)); // add sleep statement at the end
					int methodCallPositionsSize = methodCallPositionsCopy.size();
					for (int i = methodCallPositionsSize - 1; i >= 1; i--) {
						int indexToPlaceSleepStatement = methodCallPositionsCopy.get(i) + 1;
						clone.addStatement(sleepMethodStatement.clone(clone), indexToPlaceSleepStatement);
					}
					clone.addStatement(sleepMethodStatement.clone(clone), 2); // add sleep statement after sleep value initialization
				}else{
					throw new IllegalStateException("insertSleepStmtsInTestCase: " +
							"second statement of test case is not the sleep amount value. Found "
							+ secondStatementOfTestCase.toString() + " " + secondStatementOfTestCase.getClass());
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
		}else{
			logger.warn("Test case with Id " + tc.getID() + " has no method call. Test case: " + tc.toCode());
		}
		return clone;
	}

	//TODO: it needs fixing since it adds two statements, ex: 'long long0 = 150L; long long0 = 150L'...other statements.
	//TODO: it works but it is not correct.
	private static TestCase changeValueOfSleep(TestCase testCaseWithSleeps, long newSleepValue){
		TestCase clone = testCaseWithSleeps.clone();
		// if it is a test case with sleep stmts the 2nd stmt must be the sleep amount variable declaration
		if(clone.hasStatement(1)){
			Statement statement = clone.getStatement(1);
			if(statement instanceof LongPrimitiveStatement){
				LongPrimitiveStatement sleepAmountStatement = (LongPrimitiveStatement) statement;
				sleepAmountStatement.setValue(newSleepValue);
				clone.addStatement(sleepAmountStatement, 1);
			}else{
				throw new IllegalStateException("changeValueOfSleep: test case with " +
						"sleeps must have the 2nd statement a LongPrimitiveStatement found " +
						statement + " " + statement.getClass());
			}
		}else{
			throw new IllegalStateException("changeValueOfSleep: test case with sleeps must have more than two statements");
		}
		return clone;
	}

	/**
	 * Execute a test case on a new scope
	 * 
	 * @param tc
	 *            a {@link org.evosuite.testcase.TestCase} object.
	 * @return a {@link org.evosuite.testcase.execution.ExecutionResult} object.
	 */
	public ExecutionResult execute(TestCase tc, int timeout) {
		Scope scope = new Scope();

		ExecutionResult result = execute(tc, scope, timeout);

		if (Properties.RESET_STATIC_FIELDS) {
			ClassReInitializer.getInstance().reInitializeClassesAfterTestExecution(tc, result);
		}
		
		return result;
	}

	/**
	 * Execute a test case on an existing scope
	 * 
	 * @param tc
	 *            a {@link org.evosuite.testcase.TestCase} object.
	 * @param scope
	 *            a {@link org.evosuite.testcase.execution.Scope} object.
	 * @return a {@link org.evosuite.testcase.execution.ExecutionResult} object.
	 */
	@SuppressWarnings("deprecation")
	private ExecutionResult execute(TestCase tc, Scope scope, int timeout) {
		ExecutionTracer.getExecutionTracer().clear();

		// TODO: Re-insert!
		resetObservers();
		ExecutionObserver.setCurrentTest(tc);
		MaxTestsStoppingCondition.testExecuted();

		long startTime = System.currentTimeMillis();

		TimeoutHandler<ExecutionResult> handler = new TimeoutHandler<ExecutionResult>();

		// #TODO steenbuck could be nicer (TestRunnable should be an interface
		TestRunnable callable = new TestRunnable(tc, scope, observers);
		callable.storeCurrentThreads();

		/*
		 * FIXME: the sequence of "catch" with calls to "result.set" should be
		 * re-factored, as these things should be (already) handled in
		 * TestRunnable.call. If not, it should be explained, as it is not
		 * necessarily obvious why some checks are done here, and others in
		 * TestRunnable
		 */

		try {
			// ExecutionResult result = task.get(timeout,
			// TimeUnit.MILLISECONDS);

			ExecutionResult result = null;

			// important to call it before setting up the sandbox
			SystemInUtil.getInstance().initForTestCase();
			JOptionPaneInputs.getInstance().initForTestCase();

			Sandbox.goingToExecuteSUTCode();
			TestGenerationContext.getInstance().goingToExecuteSUTCode();
			try {
				result = handler.execute(callable, executor, timeout, Properties.CPU_TIMEOUT);
			} finally {
				Sandbox.doneWithExecutingSUTCode();
				TestGenerationContext.getInstance().doneWithExecutingSUTCode();
			}

			PermissionStatistics.getInstance().countThreads(threadGroup.activeCount());
			result.setSecurityException(PermissionStatistics.getInstance().getAndResetExceptionInfo());
			/*
			 * TODO: this will need proper care when we ll start to handle
			 * threads in the search.
			 */
			callable.killAndJoinClientThreads();

			/*
			 * TODO: we might want to initialize the ExecutionResult here, once
			 * we waited for all SUT threads to finish
			 */

			long endTime = System.currentTimeMillis();
			timeExecuted += endTime - startTime;
			testsExecuted++;
			return result;
		} catch (ThreadDeath t) {
			logger.warn("Caught ThreadDeath during test execution");
			ExecutionResult result = new ExecutionResult(tc, null);
			result.setThrownExceptions(callable.getExceptionsThrown());
			result.setTrace(ExecutionTracer.getExecutionTracer().getTrace());
			ExecutionTracer.getExecutionTracer().clear();
			return result;

		} catch (InterruptedException e1) {
			logger.info("InterruptedException");
			ExecutionResult result = new ExecutionResult(tc, null);
			result.setThrownExceptions(callable.getExceptionsThrown());
			result.setTrace(ExecutionTracer.getExecutionTracer().getTrace());
			ExecutionTracer.getExecutionTracer().clear();
			return result;
		} catch (ExecutionException e1) {
			/*
			 * An ExecutionException at this point, is most likely an error in
			 * evosuite. As exceptions from the tested code are caught before
			 * this.
			 */
			System.setOut(systemOut);
			System.setErr(systemErr);

			logger.error("ExecutionException (this is likely a serious error in the framework)", e1);
			ExecutionResult result = new ExecutionResult(tc, null);
			result.setThrownExceptions(callable.getExceptionsThrown());
			result.setTrace(ExecutionTracer.getExecutionTracer().getTrace());
			ExecutionTracer.getExecutionTracer().clear();
			if (e1.getCause() instanceof Error) { // an error was thrown
													// somewhere in evosuite
													// code
				throw (Error) e1.getCause();
			} else if (e1.getCause() instanceof RuntimeException) {
				throw (RuntimeException) e1.getCause();
			}
			return result; // FIXME: is this reachable?
		} catch (TimeoutException e1) {
			// System.setOut(systemOut);
			// System.setErr(systemErr);

			if (Properties.LOG_TIMEOUT) {
				logger.warn("Timeout occurred for " + Properties.TARGET_CLASS);
			}
			logger.info("TimeoutException, need to stop runner", e1);
			ExecutionTracer.setKillSwitch(true);
			try {
				handler.getLastTask().get(Properties.SHUTDOWN_TIMEOUT, TimeUnit.MILLISECONDS);
			} catch (InterruptedException e2) {
			} catch (ExecutionException e2) {
			} catch (TimeoutException e2) {
			}
			// task.cancel(true);

			if (!callable.isRunFinished()) {
				logger.info("Cancelling thread:");
				for (StackTraceElement elem : currentThread.getStackTrace()) {
					logger.info(elem.toString());
				}
				logger.info(tc.toCode());
				boolean loopCounter = LoopCounter.getInstance().isActivated();
				while (isInStaticInit()) {
					// LoopCounter and killswitch check the stacktrace often
					// and that is costly - to speed things up we deactivate it
					// until we're outside the static constructor
					LoopCounter.getInstance().setActive(false);
					ExecutionTracer.setKillSwitch(false);
					logger.info("Run still not finished, but awaiting for static initializer to finish.");

					try {
						executor.awaitTermination(Properties.SHUTDOWN_TIMEOUT, TimeUnit.MILLISECONDS);
					} catch (InterruptedException e) {
						logger.info("Interrupted");
						e.printStackTrace();
					}
				}
				LoopCounter.getInstance().setActive(loopCounter);
				ExecutionTracer.setKillSwitch(true);

				if (!callable.isRunFinished()) {
					handler.getLastTask().cancel(true);
					logger.info("Run not finished, waiting...");
					try {
						executor.awaitTermination(Properties.SHUTDOWN_TIMEOUT, TimeUnit.MILLISECONDS);
					} catch (InterruptedException e) {
						logger.info("Interrupted");
						e.printStackTrace();
					}
				}

				if (!callable.isRunFinished()) {
					logger.info("Run still not finished, replacing executor.");
					try {
						executor.shutdownNow();
						if (currentThread.isAlive()) {
							logger.info("Thread survived - unsafe operation.");
							for (StackTraceElement element : currentThread.getStackTrace()) {
								logger.info(element.toString());
							}
							logger.info("Killing thread:");
							for (StackTraceElement elem : currentThread.getStackTrace()) {
								logger.info(elem.toString());
							}
							currentThread.stop();
						}
					} catch (ThreadDeath t) {
						logger.info("ThreadDeath.");
					} catch (Throwable t) {
						logger.info("Throwable: " + t);
					}
					ExecutionTracer.disable();
					executor = Executors.newSingleThreadExecutor(this);
				}
			} else {
				logger.info("Run is finished - " + currentThread.isAlive() + ": " + getNumStalledThreads());

			}
			ExecutionTracer.disable();

			// TODO: If this is true, is this problematic?
			if (Sandbox.isOnAndExecutingSUTCode()) {
				Sandbox.doneWithExecutingSUTCode();
				TestGenerationContext.getInstance().doneWithExecutingSUTCode();
			}

			ExecutionResult result = new ExecutionResult(tc, null);
			result.setThrownExceptions(callable.getExceptionsThrown());
			result.reportNewThrownException(tc.size(), new TestCaseExecutor.TimeoutExceeded());
			result.setTrace(ExecutionTracer.getExecutionTracer().getTrace());
			ExecutionTracer.getExecutionTracer().clear();
			ExecutionTracer.setKillSwitch(false);
			ExecutionTracer.enable();
			System.setOut(systemOut);
			System.setErr(systemErr);

			return result;
		} finally {
			if (threadGroup != null)
				PermissionStatistics.getInstance().countThreads(threadGroup.activeCount());
			TestCluster.getInstance().handleRuntimeAccesses(tc);
		}
	}

	private boolean isInStaticInit() {
		for (StackTraceElement elem : currentThread.getStackTrace()) {
			if (elem.getMethodName().equals("<clinit>"))
				return true;
			if (elem.getMethodName().equals("loadClass") && elem.getClassName()
					.equals(org.evosuite.instrumentation.InstrumentingClassLoader.class.getCanonicalName()))
				return true;
			// CFontManager is responsible for loading fonts
			// which can take seconds
			if (elem.getClassName().equals("sun.font.CFontManager"))
				return true;
		}
		return false;
	}

	/**
	 * <p>
	 * getNumStalledThreads
	 * </p>
	 * 
	 * @return a int.
	 */
	public int getNumStalledThreads() {
		Iterator<Thread> iterator = stalledThreads.iterator();
		while (iterator.hasNext()) {
			Thread t = iterator.next();
			if (!t.isAlive()) {
				iterator.remove();
			}
		}
		return stalledThreads.size();
	}

	/** {@inheritDoc} */
	@Override
	public Thread newThread(Runnable r) {
		if (currentThread != null && currentThread.isAlive()) {
			currentThread.setPriority(Thread.MIN_PRIORITY);
			stalledThreads.add(currentThread);
			logger.info("Current number of stalled threads: " + getNumStalledThreads());
		} else {
			logger.info("No stalled threads");
		}

		if (threadGroup != null) {
			PermissionStatistics.getInstance().countThreads(threadGroup.activeCount());
		}
		threadGroup = new ThreadGroup(TEST_EXECUTION_THREAD_GROUP);
		currentThread = new Thread(threadGroup, r);
		currentThread.setName(TEST_EXECUTION_THREAD + "_" + threadCounter);
		threadCounter++;
		currentThread.setContextClassLoader(TestGenerationContext.getInstance().getClassLoaderForSUT());
		ExecutionTracer.setThread(currentThread);
		return currentThread;
	}

	public void setExecutionObservers(Set<ExecutionObserver> observers) {
		this.observers = observers;
	}

}
