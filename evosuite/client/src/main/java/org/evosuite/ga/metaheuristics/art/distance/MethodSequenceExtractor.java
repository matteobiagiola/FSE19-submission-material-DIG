package org.evosuite.ga.metaheuristics.art.distance;

import org.evosuite.Properties;
import org.evosuite.ga.metaheuristics.art.distance.input.*;
import org.evosuite.ga.metaheuristics.art.sequencealignment.MethodCallSequence;
import org.evosuite.ga.metaheuristics.art.sequencealignment.MethodSequence;
import org.evosuite.testcase.TestCase;
import org.evosuite.testcase.statements.MethodStatement;
import org.evosuite.testcase.variable.VariableReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MethodSequenceExtractor {

    private final TestCase testCase;
    private final String separator;
    private static final Logger logger = LoggerFactory.getLogger(MethodSequenceExtractor.class);

    public MethodSequenceExtractor(TestCase testCase, String separator){
        this.testCase = testCase;
        this.separator = separator;
    }

    public MethodSequence extractMethodSequence(){
        logger.debug("ExtractMethodSequence - test case id: " + testCase.getID() + " " +  this.testCase.toCode());
        MethodSequence sequence = null;
        TestVisitorForMethodStatements testVisitorForMethodStatements = new TestVisitorForMethodStatements();
        testCase.accept(testVisitorForMethodStatements);
        List<MethodStatement> methodStatements = testVisitorForMethodStatements.getMethodStatements();

        String methodSequence = methodStatements.stream().map(new Function<MethodStatement, String>() {
            @Override
            public String apply(MethodStatement methodStatement) {
                return methodStatement.getMethodName();
            }
        }).collect(Collectors.joining(this.separator));

        if(Properties.INPUT_DISTANCE){
            String parameterSequence = methodStatements.stream().map(new Function<MethodStatement, String>() {
                @Override
                public String apply(MethodStatement methodStatement) {
                    logger.debug("Analyzing method statement: " + methodStatement.getMethodName());
                    List<VariableReference> references = methodStatement.getUniqueVariableReferences();
                    //logger.debug("Unique variable references: " + references);
                    //logger.debug("Variable references: " + methodStatement.getVariableReferences());
                    //logger.debug("Parameter references: " + methodStatement.getParameterReferences());
                    FilterMethodStatementReferences filterMethodStatementReferences = new FilterMethodStatementReferences(testCase);
                    MapReferenceIntoValue mapReferenceIntoValue = new MapReferenceIntoValue(testCase,methodStatement);
                    String parameterStatement = references.stream()
                            .filter(filterMethodStatementReferences)
                            .map(mapReferenceIntoValue)
                            .collect(Collectors.joining(UsefulConstants.methodParameterIntraSeparator));
                    if(parameterStatement != null && !parameterStatement.isEmpty()){
                        return parameterStatement;
                    }else{
                        return UsefulConstants.methodWithNoParameter;
                    }
                }
            }).collect(Collectors.joining(UsefulConstants.methodParameterInterSeparator));
            logger.debug("ParameterSequence: " + parameterSequence);
            sequence = new MethodCallSequence(this.separator,methodSequence,parameterSequence);
        }else{
            sequence = new MethodCallSequence(this.separator,methodSequence);
        }
        return sequence;
    }

}
