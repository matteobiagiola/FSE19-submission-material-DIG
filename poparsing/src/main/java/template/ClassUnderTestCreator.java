package template;

import com.google.common.collect.ImmutableList;
import org.apache.log4j.Logger;
import parsing.MethodProcessor;
import spoon.reflect.code.*;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtField;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.reference.CtFieldReference;
import spoon.reflect.reference.CtTypeReference;
import utils.MyProperties;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.*;

public class ClassUnderTestCreator {

    private final static Logger logger = Logger.getLogger(ClassUnderTestCreator.class);
    private static List<CtClass<?>> parsedPOsCopy;
    private static List<CtClass<?>> parsedParametricPOsCopy;
    private static List<String> poNames;
    private static List<String> parametricPONames;


    public ClassUnderTestCreator(List<CtClass<?>> parsedPOs){
        parsedPOsCopy = ImmutableList.copyOf(parsedPOs);
        poNames = parsedPOsCopy.stream().map(parsedPO -> parsedPO.getSimpleName()).collect(Collectors.toList());
    }

    public ClassUnderTestCreator(List<CtClass<?>> parsedPOs, List<CtClass<?>> parsedParametricPOs){
        parsedPOsCopy = ImmutableList.copyOf(parsedPOs);
        parsedParametricPOsCopy = ImmutableList.copyOf(parsedParametricPOs);
        poNames = parsedPOsCopy.stream().map(parsedPO -> parsedPO.getSimpleName()).collect(Collectors.toList());
        parametricPONames = parsedParametricPOsCopy.stream().map(parsedParametricPO -> parsedParametricPO.getSimpleName()).collect(Collectors.toList());
    }

    private void removeHiddenFilesFromDirectory(File spoonDirectory){
        checkArgument(spoonDirectory.isDirectory(), "Directory " + spoonDirectory.getAbsolutePath() + " is not a directory");
        Arrays.stream(spoonDirectory.listFiles()).forEach(file -> {
            if(file.getPath().contains(".DS_Store")){
                checkState(file.delete(), "Failed to remove file " + file.getAbsolutePath());
            }
        });
    }


    public void createCUT(){
        Map<String,Object> context = new HashMap<String,Object>();
        File directoryTempFiles = new File(MyProperties.instantiated_template_temp_path);
        if(!directoryTempFiles.exists()){
            checkState(directoryTempFiles.mkdir(), "Failed to create file " + directoryTempFiles.getAbsolutePath());
        }
        List<String> methodNames = this.createCUTMethods();
        boolean isMethod = false;
        context.put("packageName", MyProperties.package_name_cut);
        context.put("projectName", this.capitalize(MyProperties.project_name));
        context.put("className", MyProperties.class_under_test_name);
        context.put("startingPageObjectQualifiedName", MyProperties.starting_page_object_qualified_name);
        context.put("parsedPOs", parsedPOsCopy);
        context.put("methodNames", methodNames);
        context.put("exceptions", MyProperties.exceptions);
        context.put("quitBrowserToReset", MyProperties.quit_browser_to_reset);
        TemplatingEngine templatingEngine = new TemplatingEngine(MyProperties.template_class_under_test_name, isMethod);
        boolean isCUT = true;
        templatingEngine.instantiateTemplate(MyProperties.class_under_test_name, context, isCUT);
        this.removeTempFiles(directoryTempFiles);
        this.copyCutInTargetProject();
    }

    private void removeTempFiles(File directoryTempFiles){
        Arrays.stream(directoryTempFiles.listFiles()).forEach(file -> {
            checkState(file.delete(), "Failed to delete file " + file.getPath());
        });
    }

    private List<String> createCUTMethods(){
        boolean isMethod = true;
        List<String> poMethodNames = new ArrayList<String>();
        boolean isCUT = false;
        parsedPOsCopy.stream()
                .forEach(parsedPO -> {
                    List<CtMethod<?>> navigationalMethods = parsedPO.getMethods().stream()
                            .filter(MethodProcessor.isNavigationalMethod.apply(poNames))
                            .collect(Collectors.toList());;
                    poMethodNames.addAll(navigationalMethods.stream()
                            .map(navigationalMethod -> navigationalMethod.getSimpleName())
                            .collect(Collectors.toList()));
                    navigationalMethods.stream().forEach(navigationalMethod -> {
                        Map<String,Object> context = new HashMap<String,Object>();
                        String methodName = navigationalMethod.getSimpleName();
                        String methodParameters = navigationalMethod.getParameters().stream()
                                .map(ctParameter -> ctParameter.toString())
                                .collect(Collectors.joining(","));
                        List<CtStatement> transformedStatements = navigationalMethod.getBody().getStatements().stream()
                                .map(sourceCodeTransformation.apply(navigationalMethod.getSimpleName(), parsedPO))
                                .collect(Collectors.toList());
                        navigationalMethod.getBody().setStatements(transformedStatements);
                        //logger.debug("CtMethod: " + navigationalMethod);
                        context.put("poName", parsedPO.getSimpleName());
                        context.put("methodParameters", methodParameters);
                        context.put("pageObject", parsedPO.getQualifiedName());
                        context.put("methodName", methodName);
                        context.put("exceptions", MyProperties.exceptions);
                        CtBlock methodBody = navigationalMethod.getBody();
                        String methodBodyWithoutBraces = removeEnclosingCurlyBracesFromMethodBody(methodBody);
                        context.put("methodBody", methodBodyWithoutBraces);
                        context.put("exceptions", MyProperties.exceptions);
                        TemplatingEngine templatingEngine = new TemplatingEngine(MyProperties.template_class_under_test_method_name, isMethod);
                        templatingEngine.instantiateTemplate(methodName, context, isCUT);
                    });

                });
        return poMethodNames;
    }

    private static BiFunction<String, CtClass, Function<CtStatement, CtStatement>> sourceCodeTransformation = (methodName, parsedPO) -> {
        return ctStatement -> {
            if(ctStatement instanceof CtIf){
                CtIf ctIf = (CtIf) ctStatement;
                return transformIfStatement(ctIf, methodName, parsedPO);
            } //CtInvocation -> navigational method without preconditions
            else if(ctStatement instanceof CtInvocation<?>){
                CtInvocation<?> ctInvocation = (CtInvocation<?>) ctStatement;
                return transformInvocationStatement(ctInvocation, methodName, parsedPO);
            } //CtReturn -> navigational method without preconditions
            else if(ctStatement instanceof CtReturn){
                CtReturn ctReturn = (CtReturn) ctStatement;
                return transformReturnStatement(ctReturn, methodName, parsedPO);
            }
            else if(ctStatement instanceof CtLocalVariable){
                CtLocalVariable ctLocalVariable = (CtLocalVariable) ctStatement;
                return transformLocalVariable(ctLocalVariable, methodName, parsedPO);
            }
            else{
                logger.warn("sourceCodeTransformation. Unhandled unknown statement " + ctStatement + " "
                        + ctStatement.getClass() + debugInfo(methodName,parsedPO) + " at line "
                        + ctStatement.getPosition().getLine());
            }
            return ctStatement;
        };
    };

    private static CtLocalVariable transformLocalVariable(CtLocalVariable ctLocalVariable, String methodName, CtClass<?> parsedPO){
        CtLocalVariable copyLocalVariable = ctLocalVariable.clone();
        //TODO: analyze the other part of the assignment
        CtExpression assignment = copyLocalVariable.getAssignment();
        if(assignment instanceof CtInvocation){
            CtInvocation ctInvocation = (CtInvocation) assignment;
            CtExpression newAssignment = transformInvocationStatement(ctInvocation, methodName, parsedPO);
            copyLocalVariable.setAssignment(newAssignment);
            return copyLocalVariable;
        }else{
            logger.warn("transformLocalVariable. Unhandled unknown local variable assignment " + assignment + " "
                    + assignment.getClass() + debugInfo(methodName,parsedPO) + " at line " + assignment.getPosition().getLine());
        }
        return copyLocalVariable;
    }


    private static CtStatement transformIfStatement(CtIf ctIf, String methodName, CtClass<?> parsedPO){

        CtIf copyCtIf = ctIf.clone();

        //change "this" in "page" in preconditions
        CtExpression precondition = copyCtIf.getCondition();
        CtExpression modifiedPrecondition = handlePrecondition(precondition, methodName, parsedPO);
        copyCtIf.setCondition(modifiedPrecondition);

        //change "this" in "page" in then statement
        CtStatement thenStatement = copyCtIf.getThenStatement();
        CtStatement modifiedThenStatement = handleThenStatement(thenStatement, methodName, parsedPO);
        copyCtIf.setThenStatement(modifiedThenStatement);

        //change "else" statement from throwing "IllegalArgumentException" in "return"
        CtStatement elseStatement = copyCtIf.getElseStatement();
        CtStatement modifiedElseStatement = handleElseStatement(elseStatement, methodName, parsedPO);
        copyCtIf.setElseStatement(modifiedElseStatement);

        return copyCtIf;
    }

    private static CtExpression handlePrecondition(CtExpression precondition, String methodName, CtClass<?> parsedPO){
        CtExpression<?> copyPrecondition = precondition.clone();
        if(copyPrecondition instanceof CtInvocation){
            CtInvocation ctInvocation = (CtInvocation) copyPrecondition;
            return transformInvocationStatement(ctInvocation, methodName, parsedPO);
        } else if(copyPrecondition instanceof CtBinaryOperator){
            CtBinaryOperator ctBinaryOperator = (CtBinaryOperator) precondition;
            CtExpression leftHandOperand = ctBinaryOperator.getLeftHandOperand();
            CtExpression rightHandOperand = ctBinaryOperator.getRightHandOperand();
            CtExpression modifiedLeftHandOperand = handlePrecondition(leftHandOperand, methodName, parsedPO);
            CtExpression modifiedRightHandOperand = handlePrecondition(rightHandOperand, methodName, parsedPO);
            ctBinaryOperator.setLeftHandOperand(modifiedLeftHandOperand);
            ctBinaryOperator.setRightHandOperand(modifiedRightHandOperand);
            return ctBinaryOperator;
        } else if(copyPrecondition instanceof CtUnaryOperator){
            CtUnaryOperator ctUnaryOperator = (CtUnaryOperator) copyPrecondition;
            //logger.debug("Method " + methodName + " of parsed PO " + parsedPO.getSimpleName() + " unary operator: " + ctUnaryOperator + " operand " + ctUnaryOperator.getOperand() + " " + ctUnaryOperator.getOperand().getClass());
            CtExpression operand = ctUnaryOperator.getOperand();
            CtExpression modifiedOperand = handlePrecondition(operand, methodName, parsedPO);
            ctUnaryOperator.setOperand(modifiedOperand);
            return ctUnaryOperator;
        } else if(copyPrecondition instanceof CtFieldRead) {
            CtFieldRead ctFieldRead = (CtFieldRead) copyPrecondition;
            return transformFieldReadStatement(ctFieldRead, methodName, parsedPO);
        }
        else{
            logger.warn("handlePrecondition. Not handled unknown expression: " + precondition + " "
                    + precondition.getClass() + debugInfo(methodName,parsedPO) + " at line "
                    + precondition.getPosition().getLine());
        }
        return copyPrecondition;
    }

    private static CtStatement handleThenStatement(CtStatement thenStatement, String methodName, CtClass<?> parsedPO){
        CtStatement copyThenStatement = thenStatement.clone();
        CtBlock ctBlock = (CtBlock) copyThenStatement;
        List<CtStatement> modifiedStatements = ctBlock.getStatements().stream()
                .map(ctStatement -> {
                    if(ctStatement instanceof CtInvocation){ //modify this keyword in then statement
                        CtInvocation ctInvocation = (CtInvocation) ctStatement;
                        return transformInvocationStatement(ctInvocation, methodName, parsedPO);
                    }else if(ctStatement instanceof CtReturn){ //modify return statement in then statement
                        CtReturn ctReturn = (CtReturn) ctStatement;
                        return transformReturnStatement(ctReturn, methodName, parsedPO);
                    }else if(ctStatement instanceof CtIf){ //modify if statement in then statement
                        CtIf ctIf = (CtIf) ctStatement;
                        return transformIfStatement(ctIf, methodName, parsedPO);
                    }else if(ctStatement instanceof CtLocalVariable){
                        CtLocalVariable ctLocalVariable = (CtLocalVariable) ctStatement;
                        return transformLocalVariable(ctLocalVariable, methodName, parsedPO);
                    }else{
                        logger.warn("handleThenStatement. Not handled unknown statement: " + ctStatement + " "
                                + ctStatement.getClass() + debugInfo(methodName,parsedPO) + " at line "
                                + ctStatement.getPosition().getLine());
                    }
                    return ctStatement;
                })
                .collect(Collectors.toList());
        ctBlock.setStatements(modifiedStatements);
        return ctBlock;
    }

    private static CtStatement handleElseStatement(CtStatement elseStatement, String methodName, CtClass<?> parsedPO){
        CtStatement copyElseStatement = elseStatement.clone();
        CtBlock ctBlock = (CtBlock) copyElseStatement;
        List<CtStatement> modifiedBlockStatements = ctBlock.getStatements().stream()
                .map(ctStatement -> {
                    if(ctStatement instanceof CtIf){
                        CtIf ctIf = (CtIf) ctStatement;
                        return transformIfStatement(ctIf, methodName, parsedPO);
                    }else if(ctStatement instanceof CtThrow){
                        if(MyProperties.exceptions){
                            CtThrow ctThrow = (CtThrow) ctStatement;
                            CtExpression thrownExpression = ctThrow.getThrownExpression();
                            if(thrownExpression instanceof CtConstructorCall){
                                CtConstructorCall ctConstructorCall = (CtConstructorCall) thrownExpression;
                                List<CtExpression> oldArguments = ctConstructorCall.getArguments();
                                List<CtExpression> modifiedArguments = oldArguments.stream()
                                        .map(ctExpression -> transformExpression(ctExpression, methodName, parsedPO))
                                        .collect(Collectors.toList());
                                ctConstructorCall.setArguments(modifiedArguments);
                                CtConstructorCall newCtConstructorCall = ctConstructorCall.getFactory().createConstructorCall();
                                newCtConstructorCall.setArguments(ctConstructorCall.getArguments());
                                newCtConstructorCall.setType(ctConstructorCall.getFactory().createTypeReference().setSimpleName("NotTheRightInputValuesException"));
                                ctThrow.setThrownExpression(newCtConstructorCall);
                                return ctThrow;
                            }else{
                                throw new IllegalStateException("handleElseStatement: thrown expression is not an instance of CtConstructorCall; found "
                                        + thrownExpression + " " + thrownExpression.getClass() + debugInfo(methodName, parsedPO)
                                        + " at line " + thrownExpression.getPosition().getLine());
                            }
                        }else{
                            CtCodeSnippetStatement returnStatementOfVoidMethod = copyElseStatement.getFactory().createCodeSnippetStatement("return");
                            return returnStatementOfVoidMethod;
                        }
                    }else if(ctStatement instanceof CtInvocation){
                        CtInvocation ctInvocation = (CtInvocation) ctStatement;
                        return transformInvocationStatement(ctInvocation, methodName, parsedPO);
                    }else if(ctStatement instanceof CtReturn){
                        CtReturn ctReturn = (CtReturn) ctStatement;
                        return transformReturnStatement(ctReturn, methodName, parsedPO);
                    }else{
                        logger.warn("handleElseStatement. Not handled unknown statement " + ctStatement + " "
                                + ctStatement.getClass() + debugInfo(methodName,parsedPO) + " at line " + ctStatement.getPosition().getLine());
                    }
                    return ctStatement;
                })
                .collect(Collectors.toList());
        ctBlock.setStatements(modifiedBlockStatements);
        return ctBlock;
    }

    private static CtExpression transformExpression(CtExpression ctExpression, String methodName, CtClass<?> parsedPO){
        CtExpression copyExpression = ctExpression.clone();
        CtExpression pageExpression = getPageExpression(copyExpression, methodName, Optional.empty(), parsedPO);
        return pageExpression;
    }

    /*private static CtExpression transformTargetedExpression(CtTargetedExpression ctTargetedExpression){
        CtTargetedExpression copy = ctTargetedExpression.clone();
        CtExpression<?> target = copy.getTarget();
        CtExpression<?> pageExpression = getPageExpression(target, Optional.empty());
        ctTargetedExpression.setTarget(pageExpression);
        return ctTargetedExpression;
    }*/

    private static CtFieldRead transformFieldReadStatement(CtFieldRead ctFieldRead, String methodName, CtClass<?> parsedPO){
       CtFieldRead copyFieldRead = ctFieldRead.clone();
       CtExpression target = copyFieldRead.getTarget();
       checkNotNull(target, "Please refer to the variable " + ctFieldRead + " as this." + ctFieldRead
               + " in navigational methods of PO " + parsedPO.getSimpleName() + " at line " + target.getPosition().getLine());
       CtExpression modifiedTargetExpression = transformExpression(target, methodName, parsedPO);
       copyFieldRead.setTarget(modifiedTargetExpression);
       return copyFieldRead;
    }

    private static CtInvocation transformInvocationStatement(CtInvocation ctInvocation, String methodName, CtClass<?> parsedPO){
        CtInvocation copyCtInvocation = ctInvocation.clone();
        List<CtExpression> arguments = copyCtInvocation.getArguments();
        List<CtExpression> modifiedArguments = arguments.stream()
                .map(ctExpression -> transformExpression(ctExpression, methodName, parsedPO))
                .collect(Collectors.toList());
        copyCtInvocation.setArguments(modifiedArguments);
        CtExpression targetExpression = copyCtInvocation.getTarget();
        CtExpression modifiedTargetExpression = transformExpression(targetExpression, methodName, parsedPO);
        copyCtInvocation.setTarget(modifiedTargetExpression);
        return copyCtInvocation;
    }

    private static CtExpression<?> getPageExpression(CtExpression<?> targetInvocation, String methodName, Optional<String> poComponentName,
                                                     CtClass<?> parsedPO){
//        Set<String> parsedPOFields =  parsedPO.getAllFields().stream()
//                .map(ctFieldReference -> ctFieldReference.getQualifiedName().replace("#", "."))
//                .collect(Collectors.toSet());
        if(targetInvocation instanceof CtThisAccess){
            String invocationExpression = "page";
            //if(isPageComponent(methodName)) invocationExpression += "." + getPageComponentName(methodName, parsedPO);
            if(poComponentName.isPresent()) invocationExpression += "." + poComponentName.get();
            CtCodeSnippetExpression expression = targetInvocation.getFactory().createCodeSnippetExpression(invocationExpression);
            return expression;
        }else if (targetInvocation instanceof CtFieldRead){
            CtFieldRead<?> ctFieldRead = (CtFieldRead<?>) targetInvocation;
            if(ctFieldRead.getTarget() instanceof CtThisAccess){
                return getPageExpression(ctFieldRead.getTarget(), methodName, Optional.of(ctFieldRead.getVariable().getSimpleName()), parsedPO);
            }else if(ctFieldRead.getTarget() instanceof CtVariableRead){
                String invocationExpression = ctFieldRead.toString();
                CtCodeSnippetExpression expression = targetInvocation.getFactory().createCodeSnippetExpression(invocationExpression);
                return expression;
            }else if(ctFieldRead.getTarget() instanceof CtTypeAccess){
                String targetName = ctFieldRead.getTarget().getType().getSimpleName();
                if(targetName.equals(parsedPO.getSimpleName())){
                    throw new UnsupportedOperationException("Please use the keyword this to access variables inside of "
                            + parsedPO.getSimpleName() + " PO at line " + ctFieldRead.getPosition().getLine() +
                            ". Static variables and private variables not supported yet. "
                            + ctFieldRead + ": target -> " + ctFieldRead.getTarget() + " " + ctFieldRead.getTarget().getClass());
                }else{
                    return ctFieldRead;
                }
                //take fields of current PO
                /*Collection<CtFieldReference<?>> ctFieldReferences = parsedPO.getDeclaredFields();
                ctFieldReferences.stream().forEach(ctFieldReference -> {
                    logger.warn(ctFieldReference.toString());
                });*/
            }
            else{
                throw new UnsupportedOperationException("Unknown type of field read statement: " + ctFieldRead
                        + ": target -> " + ctFieldRead.getTarget() + " " + ctFieldRead.getTarget().getClass()
                        + ". PO " + parsedPO.getSimpleName() + " at line " + ctFieldRead.getPosition().getLine() + ".");
            }
        }else if(targetInvocation instanceof CtInvocation){
            CtInvocation<?> ctInvocation = (CtInvocation<?>) targetInvocation;
            return transformInvocationStatement(ctInvocation, methodName, parsedPO);
        }else if(targetInvocation instanceof CtBinaryOperator){
            CtBinaryOperator ctBinaryOperator = (CtBinaryOperator) targetInvocation;
            return transformCtBinaryOperator(ctBinaryOperator, methodName, parsedPO);
        }else{
            logger.warn("getPageExpression. Unhandled unknown expression " + targetInvocation + " "
                    + targetInvocation.getClass() + debugInfo(methodName,parsedPO) + " at line "
                    + targetInvocation.getPosition().getLine());
            return targetInvocation;
        }
    }

    private static CtBinaryOperator transformCtBinaryOperator(CtBinaryOperator ctBinaryOperator, String methodName, CtClass<?> parsedPO){
        CtBinaryOperator copyCtBinaryOperator = ctBinaryOperator.clone();
        CtExpression leftOperandExpression = ctBinaryOperator.getLeftHandOperand();
        CtExpression rightOperandExpression = ctBinaryOperator.getRightHandOperand();
        copyCtBinaryOperator.setLeftHandOperand(transformExpression(leftOperandExpression, methodName, parsedPO));
        copyCtBinaryOperator.setRightHandOperand(transformExpression(rightOperandExpression, methodName, parsedPO));
        return copyCtBinaryOperator;
    }

    private static CtStatement transformReturnStatement(CtReturn ctReturn, String methodName, CtClass<?> parsedPO){
        CtReturn copyCtReturn = ctReturn.clone();
        CtExpression<?> returnedExpression = copyCtReturn.getReturnedExpression();
        CtTypeReference ctTypeReference = returnedExpression.getType();
        if(returnedExpression instanceof CtThisAccess){
            List<CtMethod<?>> methodsByName = parsedPO.getMethodsByName(methodName);
            checkState(methodsByName.size() == 1, "Method " + methodName
                    + " is not present or it is present more than once in PO " + parsedPO.getSimpleName());
            CtMethod<?> ctMethod = methodsByName.get(0);
            List<CtFieldRead> fieldReads = ctMethod.getElements(ctFieldRead -> true);
            //take the last field read whose variable is a field of the current parsedPO
            Optional<CtFieldRead> ctFieldReadOptional = fieldReads.stream()
                    .filter(ctFieldRead -> {
                        String variableName = ctFieldRead.getVariable().getSimpleName();
                        Optional<CtField<?>> ctFieldOptional = parsedPO.getFields().stream().filter(ctField -> ctField.getSimpleName().equals(variableName)).findFirst();
                        if(ctFieldOptional.isPresent()) return true;
                        return false;
                    })
                    .findFirst();
            if(ctFieldReadOptional.isPresent()){
                //checkState(ctFieldReadOptional.isPresent(), "Couldn't get any component in method " + methodName + " of PO " + parsedPO.getSimpleName());
                CtFieldRead ctFieldRead = ctFieldReadOptional.get();
                String lastComponentUsedName = ctFieldRead.getVariable().getSimpleName();
                CtCodeSnippetExpression ctCodeSnippetExpression = returnedExpression.getFactory().createCodeSnippetExpression("new " + ctTypeReference + "(page." + lastComponentUsedName + ".getDriver())");
                copyCtReturn.setReturnedExpression(ctCodeSnippetExpression);
            }else if(returnedExpression.toString().equals("this")){
                CtCodeSnippetExpression ctCodeSnippetExpression = returnedExpression.getFactory().createCodeSnippetExpression("new " + ctTypeReference + "(page.getDriver())");
                copyCtReturn.setReturnedExpression(ctCodeSnippetExpression);
            }else{
                throw new IllegalStateException("transformReturnStatement: unknown return expression " + returnedExpression);
            }
        }else if(returnedExpression instanceof CtConstructorCall){
            CtConstructorCall<?> ctConstructorCall = (CtConstructorCall<?>) returnedExpression;
            List arguments = ctConstructorCall.getArguments().stream()
                    .map(ctExpression -> {
                        if(ctExpression instanceof CtInvocation){
                            CtInvocation ctInvocation = (CtInvocation) ctExpression;
                            return transformInvocationStatement(ctInvocation, methodName, parsedPO);
                        }else if(ctExpression instanceof CtFieldRead){
                            CtFieldRead ctFieldRead = (CtFieldRead) ctExpression;
                            return transformFieldReadStatement(ctFieldRead, methodName, parsedPO);
                        }
                        else{
                            logger.warn("transformReturnStatement. Unhandled return expression in arguments of constructor call "
                                    + ctExpression + " " + ctExpression.getClass() + debugInfo(methodName, parsedPO)
                                    + " at line " + ctExpression.getPosition().getLine());
                            return ctExpression;
                        }
                    })
                    .collect(Collectors.toList());
            ctConstructorCall.setArguments(arguments);
            copyCtReturn.setReturnedExpression(ctConstructorCall);
        }else if(returnedExpression instanceof CtInvocation){
            CtInvocation ctInvocation = (CtInvocation) returnedExpression;
            CtInvocation modifiedCtInvocation = transformInvocationStatement(ctInvocation, methodName, parsedPO);
            String targetInvocationName = modifiedCtInvocation.getTarget().toString();
            String executableInvocationName = modifiedCtInvocation.getExecutable().getSimpleName();
            List<CtExpression> argumentsExecutableInvocationList = modifiedCtInvocation.getArguments();
            String argumentsExecutableInvocationString = argumentsExecutableInvocationList.stream()
                    .map(ctExpression -> ctExpression.toString())
                    .collect(Collectors.joining(","));
            CtCodeSnippetExpression ctCodeSnippetExpression = returnedExpression.getFactory().createCodeSnippetExpression(targetInvocationName
                    + "." + executableInvocationName + "(" + argumentsExecutableInvocationString + ")");
            copyCtReturn.setReturnedExpression(ctCodeSnippetExpression);
        }
        else{
            logger.warn("transformReturnStatement. Unhandled return expression " + returnedExpression + " "
                    + returnedExpression.getClass() + debugInfo(methodName, parsedPO) + " at line "
                    + returnedExpression.getPosition().getLine());
        }
        CtCodeSnippetStatement ctCodeSnippetStatement = copyCtReturn.getFactory().createCodeSnippetStatement("this.currentPage = " + copyCtReturn.getReturnedExpression());
        return ctCodeSnippetStatement;
    }

    private static String removeEnclosingCurlyBracesFromMethodBody(CtBlock methodBody){
        CtBlock methodBodyClone = methodBody.clone();
        String bodyWithBraces = methodBodyClone.toString();
        int openBraceIndex = bodyWithBraces.indexOf("{");
        int closeBraceIndex = bodyWithBraces.lastIndexOf("}");
        String bodyWithoutBraces = bodyWithBraces.substring(openBraceIndex + 2, closeBraceIndex - 1);
        return bodyWithoutBraces;
    }

    /*private static boolean isPageComponent(String methodName){
        return methodName.contains(MyProperties.page_component_static_label);
    }*/

    /*private static String getPageComponentName(String methodName, CtClass<?> parsedPO){
        Optional<String> optionalPageComponentName = parametricPONames.stream().filter(parametricPOName -> methodName.contains(parametricPOName)).findAny();
        if(optionalPageComponentName.isPresent()){
            String pageComponentName = optionalPageComponentName.get();
            Optional<CtField<?>> ctFieldOptional = parsedPO.getFields().stream().filter(ctField -> ctField.getType().getSimpleName().equals(pageComponentName)).findAny();
            if(ctFieldOptional.isPresent()){
                String variableName = ctFieldOptional.get().getSimpleName();
                return variableName;
            }else{
                throw new IllegalStateException("getPageComponentName: there is no field in " + parsedPO.getSimpleName() + " with type " + pageComponentName);
            }
        }else{
            throw new IllegalStateException("getPageComponentName: methodName " + methodName + " is not a method of a page object component");
        }
    }*/

    private static String debugInfo(String methodName, CtClass<?> parsedPO){
        return " in method " + methodName + " of PO " + parsedPO.getSimpleName();
    }

    private void copyCutInTargetProject(){
        //The file (second parameter) is located in a directory "resources" relative to the current working directory and is UTF-8 encoded.
        Path sourcePath = FileSystems.getDefault().getPath(MyProperties.instantiated_template_path, MyProperties.class_under_test_name + ".java");
        Path targetPath = FileSystems.getDefault().getPath(MyProperties.path_to_project + "/" + MyProperties.project_name
                + "/" + MyProperties.java_package_structure + "/" + MyProperties.package_name_cut, MyProperties.class_under_test_name + ".java");
        try {
            Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String capitalize(String line) {
        return Character.toUpperCase(line.charAt(0)) + line.substring(1);
    }
}
