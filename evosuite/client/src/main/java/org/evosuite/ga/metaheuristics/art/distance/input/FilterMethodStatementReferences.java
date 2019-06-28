package org.evosuite.ga.metaheuristics.art.distance.input;

import org.evosuite.Properties;
import org.evosuite.testcase.TestCase;
import org.evosuite.testcase.statements.ConstructorStatement;
import org.evosuite.testcase.statements.FieldStatement;
import org.evosuite.testcase.statements.MethodStatement;
import org.evosuite.testcase.statements.PrimitiveStatement;
import org.evosuite.testcase.statements.Statement;
import org.evosuite.testcase.variable.VariableReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Predicate;

public class FilterMethodStatementReferences implements Predicate<VariableReference> {

    private TestCase testCase;
    private static final Logger logger = LoggerFactory.getLogger(FilterMethodStatementReferences.class);

    public FilterMethodStatementReferences(TestCase testCase){
        this.testCase = testCase;
    }

    /*
    * What does constitute a parameter?
    * 1. PrimitiveStatement
    * 2. ConstructorStatement
    * */
    @Override
    public boolean test(VariableReference variableReference) {
        int stmtPosition = variableReference.getStPosition();
        logger.debug("VariableReference type name: " + variableReference.getType().getTypeName());
        if(testCase.hasStatement(stmtPosition)){
            Statement parameterStmt = testCase.getStatement(stmtPosition);
            if(parameterStmt instanceof PrimitiveStatement){
                return true;
            }else if(parameterStmt instanceof ConstructorStatement){
                //it must not be the constructor of the test case: the first statement in the test case
                if(stmtPosition == 0) return false;
                return true;
                /*ConstructorStatement constructorStatement = (ConstructorStatement) parameterStmt;
                String declaringClassName = constructorStatement.getDeclaringClassName();
                if(declaringClassName.contains(Properties.CUSTOM_PARAMETER_PACKAGE_NAME)){
                    return true;
                }
                return false;*/
            }else if(parameterStmt instanceof MethodStatement && variableReference.getType().getTypeName().equals("void")){
                return false;
            }else if(parameterStmt instanceof FieldStatement){
                //FieldStatement fieldStatement = (FieldStatement) parameterStmt;
                //logger.debug("FieldStatement: " + fieldStatement.getCode());
                return true;
            }
            else{
                //logger.warn("FilterMethodReferences. Unhandled unknown parameter statement reference " + parameterStmt + " " + parameterStmt.getClass().getName());
                throw new IllegalStateException("FilterMethodReferences. Unhandled unknown parameter statement reference " + parameterStmt.getCode() + " class " + parameterStmt.getClass().getName());
            }
        }else{
            throw new IllegalArgumentException("FilterMethodReferences. Test case ID " + testCase.getID() + " does not have a statement in position " + stmtPosition);
        }
    }
}
