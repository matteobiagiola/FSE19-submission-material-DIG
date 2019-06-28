package main;

import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import spoon.Launcher;
import spoon.SpoonAPI;
import spoon.reflect.code.CtAssignment;
import spoon.reflect.code.CtBlock;
import spoon.reflect.code.CtConstructorCall;
import spoon.reflect.code.CtFieldWrite;
import spoon.reflect.code.CtIf;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.code.CtStatement;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.ModifierKind;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class LineInstrumenter {

    private boolean instrumentCutForTestSuiteRun;
    private boolean instrumentCutForEvosuiteRun;

    public LineInstrumenter(boolean instrumentCutForTestSuiteRun, boolean instrumentCutForEvosuiteRun){
        this.instrumentCutForTestSuiteRun = instrumentCutForTestSuiteRun;
        this.instrumentCutForEvosuiteRun = instrumentCutForEvosuiteRun;
        if(instrumentCutForTestSuiteRun && instrumentCutForEvosuiteRun){
            throw new IllegalArgumentException(this.getClass().getSimpleName() + " conditions must not be true at the same time");
        }
    }

    /**
     * @return list of lines already sorted
     * */
    public List<Integer> instrumentLinesForModelTransitions(){
        SpoonAPI spoon = new Launcher();
        spoon.getEnvironment().setNoClasspath(true);
        spoon.addInputResource(new File(System.getProperty("user.home") + "/"
                + MyProperties.getInstance().getProperty("sourceCodeCutToInstrumentPath")).getAbsolutePath());
        spoon.buildModel();
        List<CtClass<?>> ctClasses = spoon.getModel().getElements(ctClass -> true);
        if(ctClasses.isEmpty()){
            throw new IllegalStateException("No class found in file "
                    + new File(System.getProperty("user.home") + "/"
                    + MyProperties.getInstance().getProperty("sourceCodeCutToInstrumentPath"))
                        .getAbsolutePath());
        }
        CtClass ctClass = ctClasses.get(0);
        Set<CtMethod<?>> methods = ctClass.getMethods();
        // ignoring private methods
        methods = methods.stream().filter(ctMethod -> !ctMethod.hasModifier(ModifierKind.PRIVATE)).collect(Collectors.toSet());
        List<CtMethod<?>> methodsInList = new ArrayList<>(methods);
        Comparator<CtMethod> methodsComparator = (ctMethod1, ctMethod2) -> {
          return Integer.compare(ctMethod1.getPosition().getLine(), ctMethod2.getPosition().getLine());
        };
        List<Integer> linesToCover = new ArrayList<>();
        Set<CtMethod<?>> newMethods = methodsInList.stream().sorted(methodsComparator).map(ctMethod -> {
            System.out.println("Method: " + ctMethod.getSimpleName());
            CtBlock ctBlock = ctMethod.getBody();
            List<CtStatement> newStatements = ctBlock.getStatements().stream().map(ctStatement -> {
                CtStatement modifiedStatement = this.instrumentLines(ctStatement, linesToCover, ctMethod);
                return modifiedStatement;
            }).collect(Collectors.toList());
            ctBlock.setStatements(newStatements);
            ctMethod.setBody(ctBlock);
            return ctMethod;
        }).collect(Collectors.toSet());
        ctClass.setMethods(newMethods);
        if(this.instrumentCutForTestSuiteRun){
            String ctClassWithCoverageField = this.addCoverageMapField(ctClass.toString(), this.numberOfTargets);
            this.writeInstrumentedClassToFile(ctClassWithCoverageField);
        }else if(this.instrumentCutForEvosuiteRun){
            this.writeInstrumentedClassToFile(ctClass.toString());
        }
        linesToCover.sort(Integer::compareTo);
        return linesToCover;
    }

    private String addCoverageMapField(String ctClassInstrumented, int numberOfTargets){
        JavaClassSource javaClass = Roaster.parse(JavaClassSource.class, ctClassInstrumented);
        javaClass.addImport("java.util.HashMap");
        javaClass.addImport("java.util.LinkedHashMap");
        javaClass.addField("public static HashMap<String, Integer> coverageMap = createMap(" + numberOfTargets + ");");
        javaClass.addMethod("public static HashMap<String, Integer> createMap(int numberOfTargets){ \n" +
                " HashMap<String, Integer> coverageMap = new LinkedHashMap<>(); \n"
                + "for (int i = 0; i < numberOfTargets; i++) { \n"
                + "coverageMap.put(\"targetId\" + i, 0); \n }"
                + "return coverageMap; \n }");
        return javaClass.toString();
    }

    private void writeInstrumentedClassToFile(String ctClassInstrumented){
        try {
            Writer writer = new PrintWriter(System.getProperty("user.home") + "/"
                    + MyProperties.getInstance().getProperty("sourceCodeCutToInstrumentPath"));
            writer.write(ctClassInstrumented);
            writer.flush();
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private CtStatement instrumentLines(CtStatement statementOfMethodBody, List<Integer> linesToCover, CtMethod ctMethod){
        CtStatement copyStatementOfMethodBody = statementOfMethodBody.clone();
        if(copyStatementOfMethodBody instanceof CtIf){
            CtIf ctIf = (CtIf) copyStatementOfMethodBody;
            CtIf ctIfModified = this.handleIfStatement(ctIf, linesToCover, ctMethod);
            return ctIfModified;
        }else{
            throw new IllegalStateException("instrumentLines: statement body is not an If statement -> " + copyStatementOfMethodBody.toString() + " " + copyStatementOfMethodBody.getClass());
        }
    }

    private CtIf handleIfStatement(CtIf ctIf, List<Integer> linesToCover, CtMethod ctMethod){
        CtIf copyCtIf = ctIf.clone();
        CtStatement modifiedThenStatement = this.handleThenStatement(copyCtIf.getThenStatement(), linesToCover, ctMethod);
        copyCtIf.setThenStatement(modifiedThenStatement);
        if(copyCtIf.getElseStatement() != null){
            CtStatement modifiedElseStatement = this.handleElseStatement(copyCtIf.getElseStatement(), linesToCover, ctMethod);
            copyCtIf.setElseStatement(modifiedElseStatement);
        }
        return copyCtIf;
    }

    private CtStatement handleThenStatement(CtStatement thenStatement, List<Integer> linesToCover, CtMethod ctMethod){
        CtStatement copyThenStatement = thenStatement.clone();
        if(copyThenStatement instanceof CtBlock){
            CtBlock ctBlock = (CtBlock) copyThenStatement;
            return handleBlockStatement(ctBlock, linesToCover, ctMethod);
        }else if(copyThenStatement instanceof CtIf){
            CtIf ctIf = (CtIf) copyThenStatement;
            return handleIfStatement(ctIf, linesToCover, ctMethod);
        }else{
            System.out.println("handleThenStatement: then statement not handled -> " + thenStatement.toString() + " - " +  thenStatement.getClass());
            return copyThenStatement;
        }
    }

    private CtStatement handleElseStatement(CtStatement elseStatement, List<Integer> linesToCover, CtMethod ctMethod){
        CtStatement copyElseStatement = elseStatement.clone();
        return this.handleThenStatement(copyElseStatement, linesToCover, ctMethod);
    }

    private int numberOfTargets = 0;

    private CtBlock handleBlockStatement(CtBlock ctBlock, List<Integer> linesToCover, CtMethod ctMethod){
        CtBlock copyCtBlock = ctBlock.clone();
        List<CtStatement> newStatements = new ArrayList<>();
        List<CtStatement> oldStatements = copyCtBlock.getStatements();
        for(CtStatement oldStatement: oldStatements){ // it is a for loop in case it is needed to add an instrumentation statement
            if(oldStatement instanceof CtIf){
                CtIf ctIf = (CtIf) oldStatement;
                newStatements.add(this.handleIfStatement(ctIf, linesToCover, ctMethod));
            }else if(oldStatement instanceof CtAssignment){
                CtAssignment ctAssignment = (CtAssignment) oldStatement;
                if(ctAssignment.getAssigned() instanceof CtFieldWrite
                        && (ctAssignment.getAssignment() instanceof CtConstructorCall || ctAssignment.getAssignment() instanceof CtInvocation) ){
                    // this.currentPage -> CtFieldWrite; new PO(driver) -> CtConstructorCall or po.poMethod() that returns a PO -> CtInvocation
                    CtFieldWrite ctFieldWrite = (CtFieldWrite) ctAssignment.getAssigned();
                    if(ctFieldWrite.toString().equals("this.currentPage")){
                        if(this.instrumentCutForEvosuiteRun){
                            CtStatement statement = ctAssignment.getFactory()
                                    .createCodeSnippetStatement("PageObjectLogging.getInstance()." +
                                            "writeCoveredGoal(\"" + ctMethod.getSimpleName() + " - "
                                            + (ctFieldWrite.getPosition().getLine() + this.numberOfTargets) + "\")");
                            linesToCover.add(ctFieldWrite.getPosition().getLine() + this.numberOfTargets);
                            this.numberOfTargets++;
                            newStatements.add(statement);
                        }else if(this.instrumentCutForTestSuiteRun){
                            CtStatement statement = ctAssignment.getFactory()
                                    .createCodeSnippetStatement("coverageMap.put(\"targetId" + this.numberOfTargets + "\", 1)");
                            linesToCover.add(ctFieldWrite.getPosition().getLine() + this.numberOfTargets);
                            this.numberOfTargets++;
                            newStatements.add(statement);
                        }
                        else{
                            linesToCover.add(ctFieldWrite.getPosition().getLine());
                        }
                        newStatements.add(ctAssignment);
                    }else{
                        System.out.println("handleBlockStatement: assignment not handled -> assigned "
                                + ctAssignment.getAssigned() + " - " + ctAssignment.getAssigned().getClass() + "; "
                                + ctAssignment.getAssignment() + " - " + ctAssignment.getAssignment().getClass());
                        newStatements.add(ctAssignment);
                    }
                }else{
                    System.out.println("handleBlockStatement: assignment not handled -> assigned "
                            + ctAssignment.getAssigned() + " - " + ctAssignment.getAssigned().getClass() + "; "
                            + ctAssignment.getAssignment() + " - " + ctAssignment.getAssignment().getClass());
                    newStatements.add(ctAssignment);
                }
            }
            else{
                System.out.println("handleBlockStatement: statement not handled -> " + oldStatement.toString() + " - " + oldStatement.getClass());
                newStatements.add(oldStatement);
            }
        }
        copyCtBlock.setStatements(newStatements);
        return copyCtBlock;
    }

}
