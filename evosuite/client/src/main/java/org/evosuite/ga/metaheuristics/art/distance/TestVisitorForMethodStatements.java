package org.evosuite.ga.metaheuristics.art.distance;

import org.evosuite.testcase.TestCase;
import org.evosuite.testcase.TestCodeVisitor;
import org.evosuite.testcase.TestVisitor;
import org.evosuite.testcase.statements.*;

import java.util.ArrayList;
import java.util.List;

public class TestVisitorForMethodStatements extends TestVisitor {

    List<MethodStatement> methodStatements;

    public TestVisitorForMethodStatements(){
        this.methodStatements = new ArrayList<MethodStatement>();
    }

    @Override
    public void visitTestCase(TestCase test) {

    }

    @Override
    public void visitPrimitiveStatement(PrimitiveStatement<?> statement) {

    }

    @Override
    public void visitFieldStatement(FieldStatement statement) {

    }

    @Override
    public void visitMethodStatement(MethodStatement statement) {
        methodStatements.add(statement);
    }

    @Override
    public void visitConstructorStatement(ConstructorStatement statement) {

    }

    @Override
    public void visitArrayStatement(ArrayStatement statement) {

    }

    @Override
    public void visitAssignmentStatement(AssignmentStatement statement) {

    }

    @Override
    public void visitNullStatement(NullStatement statement) {

    }

    @Override
    public void visitPrimitiveExpression(PrimitiveExpression primitiveExpression) {

    }

    @Override
    public void visitFunctionalMockStatement(FunctionalMockStatement functionalMockStatement) {

    }

    public List<MethodStatement> getMethodStatements(){
        if(this.methodStatements.isEmpty()){
            throw new IllegalStateException(this.getClass().getName() + " getMethodStatements: call this " +
                    "getter after visiting a test case with this TestVisitor. Use the method testCase.accept(TestVisitor thisVisitor)");
        }
        return this.methodStatements;
    }
}
