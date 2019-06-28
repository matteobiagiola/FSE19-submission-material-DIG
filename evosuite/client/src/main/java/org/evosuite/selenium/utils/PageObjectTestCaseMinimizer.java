package org.evosuite.selenium.utils;

import org.evosuite.ga.ConstructionFailedException;
import org.evosuite.testcase.TestCase;
import org.evosuite.testcase.TestCaseMinimizer;
import org.evosuite.testcase.TestChromosome;
import org.evosuite.testcase.TestFactory;
import org.evosuite.testcase.execution.ExecutionResult;
import org.evosuite.testcase.statements.ConstructorStatement;
import org.evosuite.testcase.statements.EntityWithParametersStatement;
import org.evosuite.testcase.statements.FieldStatement;
import org.evosuite.testcase.statements.MethodStatement;
import org.evosuite.testcase.statements.PrimitiveStatement;
import org.evosuite.testcase.statements.Statement;
import org.evosuite.testcase.variable.VariableReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class PageObjectTestCaseMinimizer {

    private static final Logger logger = LoggerFactory.getLogger(PageObjectTestCaseMinimizer.class);

    public TestCase minimizeTestCase(TestCase testCase, ExecutionResult executionResult){
        TestCase executedTestCase = testCase.clone();
        logger.debug("minimizeTestCase: executedTestCase -> " + executedTestCase.toCode());
//        List<Integer> positionsOfMethodsWithThrownExceptions = this.getPositionsOfMethodsWithThrownExceptions(executedTestCase);
        List<Integer> positionsOfMethodsWithThrownExceptions = new ArrayList<>(executionResult.getPositionsWhereExceptionsWereThrown())
                .stream()
                .sorted(Comparator.comparingInt(Integer::valueOf)) // sort list according to numeric values since set from executionResult is not sorted
                .collect(Collectors.toList());
        boolean isChanged = false;
        if(positionsOfMethodsWithThrownExceptions.size() > 0){
            isChanged = true;
            for (int i = positionsOfMethodsWithThrownExceptions.size() - 1; i >= 0; i--) {
                CheckCondition.checkState(executedTestCase.hasStatement(positionsOfMethodsWithThrownExceptions.get(i)),
                        "minimizeTestCase: executedTestCase has no statement in position "
                                + positionsOfMethodsWithThrownExceptions.get(i));
                CheckCondition.checkState(executedTestCase.getStatement(positionsOfMethodsWithThrownExceptions.get(i)) instanceof  MethodStatement, "" +
                        "minimizeTestCase: executedTestCase is supposed to have a method statement in position " + positionsOfMethodsWithThrownExceptions.get(i)
                        + ", found " + executedTestCase.getStatement(positionsOfMethodsWithThrownExceptions.get(i)).getCode() + " "
                        + executedTestCase.getStatement(positionsOfMethodsWithThrownExceptions.get(i)).getClass());
                logger.debug("removing statement at position " + positionsOfMethodsWithThrownExceptions.get(i)
                        + " " + executedTestCase.getStatement(positionsOfMethodsWithThrownExceptions.get(i)).getCode());
                executedTestCase.remove(positionsOfMethodsWithThrownExceptions.get(i));
            }
            positionsOfMethodsWithThrownExceptions = this.getPositionsOfMethodsWithThrownExceptions(executedTestCase);
            CheckCondition.checkState(positionsOfMethodsWithThrownExceptions.size() == 0, "Bug in test case minimization");
            executedTestCase = this.deleteUnreferencedParameters(executedTestCase);
        }else{
            logger.debug("minimizeTestCase: no thrown exceptions in test case with Id " + executedTestCase.getID());
        }
        if(isChanged){
            logger.debug("minimizeTestCase: executedTestCase after minimization " + executedTestCase.toCode());
        }
        return executedTestCase;
    }

    public TestChromosome minimizeIndividual(TestChromosome individual){
        TestChromosome clone = (TestChromosome) individual.clone();
        TestCase executedTestCase = clone.getTestCase();
        TestCase executedTestCaseMinimized = this.minimizeTestCase(executedTestCase, clone.getLastExecutionResult());
        clone.setTestCase(executedTestCaseMinimized); // implies clear cache results so the test case is going to be re-executed if 'getFitness' method is called
        return clone;
    }

    private List<Integer> getPositionsOfMethodsWithThrownExceptions(TestCase testCase){
        List<Integer> positions = new ArrayList<>();
        int stmtPosition = 0;
        while(testCase.hasStatement(stmtPosition)){
            Statement statement = testCase.getStatement(stmtPosition);
            if(statement instanceof MethodStatement){
                MethodStatement methodStatement = (MethodStatement) statement;
                if(methodStatement.isExceptionThrownBoolean()){
                    positions.add(stmtPosition);
                }
            }
            stmtPosition++;
        }
        return positions;
    }

    /**
     * some parameters declaration of deleted methods might be still there
     * after the deletion of the interesting methods because ES when deletes a method statement
     * doesn't know beforehand if the parameters used by that method are used by another
     * method call placed before the current method to delete.
     * delete them if they are not referenced by other method statements
     */
    private TestCase deleteUnreferencedParameters(TestCase testCase) {
        TestCase clone = testCase.clone();
        TestFactory testFactory = TestFactory.getInstance();
        List<Integer> paramsToDelete = new ArrayList<>();
        logger.debug("analyzing not referenced statements after deletion of method statements");
        //delete constructor statements (custom classes for parameters extracted from PageObject methods preconditions)
        //that are not referenced anymore
        logger.debug("analyzing not referenced constructor statements of custom classes for parameters on test case \n" + clone.toCode());
        //it starts from 1 because it does not include the constructor statement of the class under test
        for(int i = 1; i < clone.size(); i++){
            if(clone.getStatement(i) instanceof ConstructorStatement){
                logger.debug("analyzing constructor statement: " + clone.getStatement(i).getCode());
                Set<Integer> positions = testFactory.getReferencePositions(clone, i);
                boolean canRemove = true;
                for(Integer pos: positions){
                    if(clone.getStatement(pos) instanceof MethodStatement){
                        logger.debug("cannot remove constructor statement " + clone.getStatement(i).getCode() + " because it has a reference to " + clone.getStatement(pos).getCode());
                        canRemove = false;
                        break;
                    }
                }
                if(canRemove){
                    logger.debug("removing constructor statement: " + clone.getStatement(i).getCode() + " because is not referenced");
                    paramsToDelete.add(i);
                }
            }else{
                logger.debug("not a constructor statement " + clone.getStatement(i).getCode() + " " + clone.getStatement(i).getClass());
            }
        }
        Collections.sort(paramsToDelete, Collections.reverseOrder());
        for(Integer integer: paramsToDelete){
            clone.remove(integer);
//            testFactory.deleteStatementGracefully(clone, integer);
        }
        paramsToDelete.clear();
        logger.debug("analyzing not referenced primitive/field statements on test case \n" + clone.toCode());
        //delete primitive statements that are not referenced anymore (some primitive statements might depend on just removed constructor statements)
        for(int i = 1; i < clone.size(); i++){
            if(clone.getStatement(i) instanceof PrimitiveStatement){ // include also EnumPrimitiveStatement that extends PrimitiveStatement
                logger.debug("analyzing primitive statement: " + clone.getStatement(i).getCode());
                Set<Integer> positions = testFactory.getReferencePositions(clone, i);
                boolean canRemove = true;
                for(Integer pos: positions){
                    //EntityWithParametersStatement is extended by both MethodStatement and ConstructorStatement
                    if(clone.getStatement(pos) instanceof EntityWithParametersStatement){
                        logger.debug("cannot remove primitive statement " + clone.getStatement(i).getCode() + " because it has a reference to " + clone.getStatement(pos).getCode());
                        canRemove = false;
                        break;
                    }
                }
                if(canRemove){
                    logger.debug("removing primitive statement: " + clone.getStatement(i).getCode() + " because is not referenced");
                    paramsToDelete.add(i);
                }
            }else if(clone.getStatement(i) instanceof FieldStatement){
                logger.debug("analyzing field statement: " + clone.getStatement(i).getCode());
                Set<Integer> positions = testFactory.getReferencePositions(clone, i);
                boolean canRemove = true;
                for(Integer pos: positions){
                    //EntityWithParametersStatement is extended by both MethodStatement and ConstructorStatement
                    if(clone.getStatement(pos) instanceof EntityWithParametersStatement){
                        logger.debug("cannot remove primitive statement " + clone.getStatement(i).getCode() + " because it has a reference to " + clone.getStatement(pos).getCode());
                        canRemove = false;
                        break;
                    }
                }
                if(canRemove){
                    logger.debug("removing field statement: " + clone.getStatement(i).getCode() + " because is not referenced");
                    paramsToDelete.add(i);
                }
            }
            else{
                if(!(clone.getStatement(i) instanceof MethodStatement) && !(clone.getStatement(i) instanceof ConstructorStatement)){
                    throw new IllegalStateException("not a primitive/field statement " + clone.getStatement(i).getCode() + " " + clone.getStatement(i).getClass());
                }
            }
        }
        Collections.sort(paramsToDelete, Collections.reverseOrder());
        for(Integer integer: paramsToDelete){
            clone.remove(integer);
//            testFactory.deleteStatementGracefully(clone, integer);
        }
        paramsToDelete.clear();
        return clone;
    }
}
