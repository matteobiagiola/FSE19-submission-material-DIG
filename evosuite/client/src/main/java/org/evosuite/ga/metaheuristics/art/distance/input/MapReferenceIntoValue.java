package org.evosuite.ga.metaheuristics.art.distance.input;

import org.evosuite.Properties;
import org.evosuite.ga.metaheuristics.art.distance.input.parsing.CustomClassDetails;
import org.evosuite.ga.metaheuristics.art.distance.input.parsing.CustomEnumDetails;
import org.evosuite.ga.metaheuristics.art.utils.PrintObjectCollection;
import org.evosuite.testcase.TestCase;
import org.evosuite.testcase.statements.*;
import org.evosuite.testcase.statements.numeric.IntPrimitiveStatement;
import org.evosuite.testcase.variable.VariableReference;
import org.evosuite.utils.generic.GenericField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class MapReferenceIntoValue implements Function<VariableReference, String> {

    private TestCase testCase;
    private MethodStatement methodStatement;
    private int loopCounter;
    private List<String> methodParameterTypes;
    private static final Logger logger = LoggerFactory.getLogger(MapReferenceIntoValue.class);

    public MapReferenceIntoValue(TestCase testCase, MethodStatement methodStatement){
        this.testCase = testCase;
        this.methodStatement = methodStatement;
        this.loopCounter = 0;
        this.methodParameterTypes = this.getMethodParameterTypes();
        //logger.debug("MethodParameterTypes: " + this.methodParameterTypes);
    }

    @Override
    public String apply(VariableReference variableReference) {
        //logger.debug("Variable reference getType: " + variableReference.getType().getTypeName());
        String parameterTypes = Arrays.stream(methodStatement.getAccessibleObject().getParameterTypes()).map(new Function<Type, String>() {
            @Override
            public String apply(Type type) {
                return "[" + type.getTypeName() + "]";
            }
        }).collect(Collectors.joining(","));
        //logger.debug("Parameter types method statement: " + parameterTypes);
        Statement parameterStmt = this.getStatementFromReference(variableReference);
        String value = this.extractValueFromParameter(parameterStmt);
        String type = this.extractTypeFromParameter(parameterStmt);
        this.loopCounter++;
        //logger.debug("Value and type: " + "(" + value + "[" + type + "])");
        return "(" + value + "[" + type + "])";
    }

    private String extractTypeFromParameter(Statement parameterStmt){
        //logger.debug("Analyzing parameter statement for type computation: " + parameterStmt.getCode());
        if(parameterStmt instanceof PrimitiveStatement){
            PrimitiveStatement primitiveStatement = (PrimitiveStatement) parameterStmt;
            PrimitiveValue primitiveValue = new PrimitiveValue(primitiveStatement.getValue());
            if(primitiveValue.isPrimitive()){
                if(primitiveStatement instanceof IntPrimitiveStatement
                        || primitiveStatement instanceof StringPrimitiveStatement){
                    return primitiveStatement.getReturnType().getTypeName();
                }else{
                    throw new UnsupportedOperationException(this.getClass().getName() + ": found primitive statement " + primitiveStatement + " " + primitiveStatement.getClass().getName() + ". Not supported yet");
                }
            }else if(primitiveStatement instanceof EnumPrimitiveStatement){
                EnumPrimitiveStatement enumPrimitiveStatement = (EnumPrimitiveStatement) primitiveStatement;
                String enumName = enumPrimitiveStatement.getEnumClass().getSimpleName();
                CustomEnumDetails customEnumDetails = ParsedCustomEnum.getInstance().get(enumName);

                if(customEnumDetails.isImplementingCustomInterface()){
                    throw new UnsupportedOperationException(this.getClass().getName() + ": enum class " + enumPrimitiveStatement + " implements interface " + Properties.CUSTOM_INTERFACE_NAME + ". Not supported yet.");
                }

                String enumType = customEnumDetails.getType();
                return this.validateEnumParameterType(enumType, customEnumDetails);
            }else{
                throw new UnsupportedOperationException(this.getClass().getName() + ": found primitive statement " + primitiveStatement + " " + primitiveStatement.getClass().getName() + ". Not a primitive statement.");
            }
        }else if(parameterStmt instanceof ConstructorStatement){
            ConstructorStatement constructorStatement = (ConstructorStatement) parameterStmt;
            String qualifiedClassName = constructorStatement.getDeclaringClassName(); //package_name.class_name
            String simpleClassName = qualifiedClassName.split("\\.")[1]; //class_name
            CustomClassDetails customClassDetails = ParsedCustomClasses.getInstance().get(simpleClassName);

            //ex. Id, Category for categorical numbers (names of custom classes)
            // or CUSTOM_INTERFACE_NAME for custom classes the implement the CUSTOM_INTERFACE_NAME interface like a Quantity concept for example
            if(customClassDetails.isImplementingCustomInterface()){
                //logger.debug("Implement custom interface: " + Properties.CUSTOM_INTERFACE_NAME);
                return this.validateParameterType(Properties.CUSTOM_INTERFACE_NAME);
            }
            return this.validateParameterType(simpleClassName);
        }else if(parameterStmt instanceof FieldStatement){
            FieldStatement fieldStatement = (FieldStatement) parameterStmt;
            String qualifiedTypeName = fieldStatement.getField().getFieldType().getTypeName();
            String simpleTypeName = qualifiedTypeName.split("\\.")[1];
            CustomEnumDetails customEnumDetails = ParsedCustomEnum.getInstance().get(simpleTypeName);
            if(customEnumDetails == null) throw new IllegalStateException("MapReferenceIntoValue extractValueFromParameter: could not find a custom enum from field type name " + simpleTypeName + " of field statement " + fieldStatement.getCode());
            if(customEnumDetails.isImplementingCustomInterface()){
                throw new UnsupportedOperationException(this.getClass().getName() + ": enum class " + fieldStatement + " implements interface " + Properties.CUSTOM_INTERFACE_NAME + ". Not supported yet.");
            }

            String enumType = customEnumDetails.getType();
            return this.validateEnumParameterType(enumType, customEnumDetails);
        }
        else{
            throw new IllegalStateException(this.getClass().getName() + ": statement type must be either PrimitiveStatement, ConstructorStatement or FieldStatement. Type found " + parameterStmt.getClass().getName());
        }
    }

    private String extractValueFromParameter(Statement parameterStmt){
        //logger.debug("Analyzing parameter statement for value computation: " + parameterStmt.getCode());
        if(parameterStmt instanceof PrimitiveStatement){
            PrimitiveStatement primitiveStatement = (PrimitiveStatement) parameterStmt;
            //logger.debug("Primitive statement value: " + primitiveStatement.getValue());
            PrimitiveValue primitiveValue = new PrimitiveValue(primitiveStatement.getValue());
            if(primitiveValue.isPrimitive()){
                if(primitiveStatement instanceof IntPrimitiveStatement
                        || primitiveStatement instanceof StringPrimitiveStatement){
                    return primitiveStatement.getValue().toString();
                }
                else{
                    throw new UnsupportedOperationException(this.getClass().getName() + ": found primitive statement " + primitiveStatement + " '" + primitiveStatement.getClass().getName() + ". Not supported yet");
                }
            }else if(primitiveStatement instanceof EnumPrimitiveStatement){
                EnumPrimitiveStatement enumPrimitiveStatement = (EnumPrimitiveStatement) primitiveStatement;
                String enumName = enumPrimitiveStatement.getEnumClass().getSimpleName();
                //logger.debug("Enum name: " + enumName);
                CustomEnumDetails customEnumDetails = ParsedCustomEnum.getInstance().get(enumName);
                /*if(!(enumPrimitiveStatement.getValue() instanceof String)){
                    throw new IllegalStateException(this.getClass().getName() + ": enum value " + enumPrimitiveStatement.getValue() + " is not a string -> " + enumPrimitiveStatement.getValue().getClass());
                }*/
                String enumConstantName = enumPrimitiveStatement.getValue().toString();
                String value = customEnumDetails.getValue(enumConstantName);
                return value;
            }
            else{
                throw new UnsupportedOperationException(this.getClass().getName() + ": found primitive statement " + primitiveStatement.getValue() + ". Not a primitive value.");
            }
        }else if(parameterStmt instanceof ConstructorStatement){
            ConstructorStatement constructorStatement = (ConstructorStatement) parameterStmt;
            List<VariableReference> constructorReferences = constructorStatement.getParameterReferences();
            PrintObjectCollection.print(constructorReferences,this.getClass(),"Constructor statement parameter references");
            /*Find the closest reference with respect to the stmt position of this constructor statement.
            * Maybe EvoSuite already handles it (it gives the reference to the last position in which the variable, parameter of
            * this constructor statement, was defined) but I cannot assume it.*/
            VariableReference closestVariableReference = constructorReferences.stream().sorted(new Comparator<VariableReference>() {
                @Override
                public int compare(VariableReference o1, VariableReference o2) {
                    int stmtPosition1 = getStatementPositionFromReference(o1);
                    int stmtPosition2 = getStatementPositionFromReference(o2);
                    /* Descending ordering */
                    if(stmtPosition1 < stmtPosition2) return 1;
                    else return -1;
                }
            }).findFirst().get();
            Statement closestParameterStatement = this.getStatementFromReference(closestVariableReference);
            String parameterValue = this.extractValueFromParameter(closestParameterStatement);
            String parameterValueWithoutParenthesis = this.extractNumberFromString(parameterValue);

            try{
                Integer.parseInt(parameterValueWithoutParenthesis);
            }catch(NumberFormatException ex){
                ex.printStackTrace();
                throw new NumberFormatException(this.getClass().getName() + " parameter of a custom class constructor must be a number! Other formats are not yet supported. ");
            }

            //modify the integer value passed as a parameter to the constructor according to the constructor logic
            String qualifiedClassName = constructorStatement.getDeclaringClassName(); //package_name.class_name
            String simpleClassName = qualifiedClassName.split("\\.")[1]; //class_name

            //PrintObjectCollection.print(ParsedCustomClasses.keys(),this.getClass(),"Key set of parsed custom classes");
            CustomClassDetails customClassDetails = ParsedCustomClasses.getInstance().get(simpleClassName);
            int lowerValue = customClassDetails.getLowerValue();
            int upperValue = customClassDetails.getUpperValue();

            CustomClassLogic customClassLogic = new CustomClassLogic(lowerValue,upperValue);
            String realParameterValue = customClassLogic.map(parameterValueWithoutParenthesis);

            return realParameterValue;
        }else if(parameterStmt instanceof FieldStatement){
            FieldStatement fieldStatement = (FieldStatement) parameterStmt;
            String qualifiedTypeName = fieldStatement.getField().getFieldType().getTypeName();
            String simpleTypeName = qualifiedTypeName.split("\\.")[1];
            CustomEnumDetails customEnumDetails = ParsedCustomEnum.getInstance().get(simpleTypeName);
            if(customEnumDetails == null) throw new IllegalStateException("MapReferenceIntoValue extractValueFromParameter: could not find a custom enum from field type name " + simpleTypeName + " of field statement " + fieldStatement.getCode());
            GenericField field = fieldStatement.getField();
            String enumConstantName = field.getName();
            String value = customEnumDetails.getValue(enumConstantName);
            //logger.debug("Value fieldStatement: " + value);
            return value;
        }
        else{
            throw new IllegalStateException(this.getClass().getName() + " statement type must be either PrimitiveStatement, ConstructorStatement or FieldStatement. Type found " + parameterStmt.getClass().getName());
        }
    }


    /*private String extractValueFromParameter(Statement parameterStmt){
        logger.debug("Analyzing parameter statement: " + parameterStmt.getCode());
        if(parameterStmt instanceof PrimitiveStatement){
            PrimitiveStatement primitiveStatement = (PrimitiveStatement) parameterStmt;
            logger.debug("Primitive statement value: " + primitiveStatement.getValue());
            //PrintObjectCollection.print(primitiveStatement.getUniqueVariableReferences(),this.getClass(),"Primitive statement variable references");
            PrimitiveValue primitiveValue = new PrimitiveValue(primitiveStatement.getValue());
            if(primitiveValue.isPrimitive()){
                String toReturn = "(" + primitiveStatement.getValue() + "[" + primitiveStatement.getReturnType().getTypeName() + "])";
                return toReturn;
            }else{
                throw new IllegalStateException("ExtractValueFromParameter MapReferenceIntoValue: " + primitiveStatement.getValue() + " is not a primitive value");
            }
        }else if(parameterStmt instanceof ConstructorStatement){
            ConstructorStatement constructorStatement = (ConstructorStatement) parameterStmt;
            List<VariableReference> constructorReferences = constructorStatement.getParameterReferences();
            PrintObjectCollection.print(constructorReferences,this.getClass(),"Constructor statement parameter references");
            //Find the closest reference with respect to the stmt position of this constructor statement.
            //Maybe EvoSuite already handles it (it gives the reference to the last position in which the variable, parameter of
            //this constructor statement, was defined) but I cannot assume it.
            VariableReference closestVariableReference = constructorReferences.stream().sorted(new Comparator<VariableReference>() {
                @Override
                public int compare(VariableReference o1, VariableReference o2) {
                    int stmtPosition1 = getStatementPositionFromReference(o1);
                    int stmtPosition2 = getStatementPositionFromReference(o2);
                    //Descending ordering
                    if(stmtPosition1 < stmtPosition2) return 1;
                    else return -1;
                }
            }).findFirst().get();
            Statement closestParameterStatement = this.getStatementFromReference(closestVariableReference);
            String parameterValue = this.extractValueFromParameter(closestParameterStatement);
            String parameterValueWithoutParenthesis = this.extractNumber(parameterValue);

            try{
                Integer.parseInt(parameterValueWithoutParenthesis);
            }catch(NumberFormatException ex){
                ex.printStackTrace();
                throw new NumberFormatException(this.getClass().getName() + " parameter of a custom class constructor must be a number! Other formats are not yet supported. ");
            }

            //modify the integer value passed as a parameter to the constructor according to the constructor logic
            String qualifiedClassName = constructorStatement.getDeclaringClassName(); //package_name.class_name
            String simpleClassName = qualifiedClassName.split("\\.")[1]; //class_name

            //PrintObjectCollection.print(ParsedCustomClasses.keys(),this.getClass(),"Key set of parsed custom classes");
            CustomClassDetails customClassDetails = ParsedCustomClasses.getInstance().get(simpleClassName);
            int lowerValue = customClassDetails.getLowerValue();
            int upperValue = customClassDetails.getUpperValue();

            CustomClassLogic customClassLogic = new CustomClassLogic(lowerValue,upperValue);
            String realParameterValue = customClassLogic.map(parameterValueWithoutParenthesis);


            //ex. Id, Category for categorical numbers (names of custom classes)
            // or CUSTOM_INTERFACE_NAME for custom classes the implement the CUSTOM_INTERFACE_NAME interface like a Quantity concept for example
            if(customClassDetails.isImplementingCustomInterface()){
                logger.debug("Real parameter value: " + realParameterValue + " type: " + Properties.CUSTOM_INTERFACE_NAME);
                return "(" + realParameterValue + "[" + Properties.CUSTOM_INTERFACE_NAME + "])";
            }

            String constructorParameterType = simpleClassName;
            if(this.isParameterCorrectType(constructorParameterType)){
                logger.debug("Parameter type position " + this.loopCounter + " of the method " + this.methodStatement.getMethodName() + " is a custom class " + constructorParameterType);
            }else{
                logger.debug("Parameter type position " + this.loopCounter + " of the method " + this.methodStatement.getMethodName()
                        + " is NOT a custom class. It is a " + this.methodParameterTypes.get(this.loopCounter));
                constructorParameterType = this.methodParameterTypes.get(this.loopCounter);
            }

            logger.debug("Real parameter value: " + realParameterValue + " type: " + constructorParameterType);

            return "(" + realParameterValue + "[" + constructorParameterType + "])";
        }else{
            throw new IllegalStateException(this.getClass().getName() + " statement type must be either PrimitiveStatement or ConstructorStatement. Type found " + parameterStmt.getClass().getName());
        }
    }*/

    private String validateEnumParameterType(String parameterType, CustomEnumDetails customEnumDetails){
        String realMethodParameterType = this.methodParameterTypes.get(this.loopCounter);
        if(parameterType.equals(realMethodParameterType)) {
            //logger.debug("Parameter type position " + this.loopCounter + " of the method " + this.methodStatement.getMethodName() + " is a custom enum " + parameterType);
            return parameterType;
        }else if(customEnumDetails.getImpementedInterfaces().contains(realMethodParameterType)) {
            //logger.debug("Parameter type position " + this.loopCounter + " of the method " + this.methodStatement.getMethodName() + " is a custom enum " + parameterType + " because it implements real method parameter type " + realMethodParameterType);
            return parameterType;
        }else{
            /*logger.debug("Parameter type position " + this.loopCounter + " of the method " + this.methodStatement.getMethodName()
                    + " is NOT a custom enum. It is a " + this.methodParameterTypes.get(this.loopCounter));*/
            return this.methodParameterTypes.get(this.loopCounter);
        }
    }


    private String validateParameterType(String parameterType){
        if(this.isParameterCorrectType(parameterType)){
            //logger.debug("Parameter type position " + this.loopCounter + " of the method " + this.methodStatement.getMethodName() + " is a custom class " + parameterType);
            return parameterType;
        }else{
            /*logger.debug("Parameter type position " + this.loopCounter + " of the method " + this.methodStatement.getMethodName()
                    + " is NOT a custom class. It is a " + this.methodParameterTypes.get(this.loopCounter));*/
            return this.methodParameterTypes.get(this.loopCounter);
        }
    }

    private List<String> getMethodParameterTypes(){
        return Arrays.stream(this.methodStatement.getAccessibleObject().getParameterTypes()).map(new Function<Type, String>() {
            @Override
            public String apply(Type type) {
                if(type.getTypeName().contains(".")){//it is not a primitive value
                    String[] packageStructure = type.getTypeName().split("\\.");
                    return packageStructure[packageStructure.length - 1]; //only stores the simple name (without package names, i.e. the last element)
                }
                return type.getTypeName();// it is a primitive value
            }
        }).collect(Collectors.toList());
    }

    private boolean isParameterCorrectType(String constructorParameterType){
        String realMethodParameterType = this.methodParameterTypes.get(this.loopCounter);
        //logger.debug("isParameterCorrectType. ConstructorParameterType: " + constructorParameterType + "; RealMethodParameterType: " + realMethodParameterType);
        if(constructorParameterType.equals(realMethodParameterType)) return true;
        return false;
    }

    private String extractNumberFromString(String stringWithParenthesis){
        StringBuffer buffer = new StringBuffer();
        Pattern pattern = Pattern.compile("(-?[0-9]+)");
        Matcher matcher = pattern.matcher(stringWithParenthesis);
        if(matcher.find()){
            buffer.append(matcher.group(1));
        }else{
            throw new NumberFormatException(this.getClass().getName() + " parameter of a custom class constructor must be a number! Other formats are not yet supported. ");
        }
        return buffer.toString();
    }

    //not used for the moment -> not tested if it works properly
    /*private String extractNumberType(String stringWithParenthesis){
        StringBuffer buffer = new StringBuffer();
        Pattern pattern = Pattern.compile("([a-zA-Z]+)");
        Matcher matcher = pattern.matcher(stringWithParenthesis);
        if(matcher.find()){
            buffer.append(matcher.group(1));
        }
        return buffer.toString();
    }*/

    private int getStatementPositionFromReference(VariableReference variableReference){
        return variableReference.getStPosition();
    }

    private Statement getStatementFromReference(VariableReference variableReference){
        int stmtPosition = variableReference.getStPosition();
        Statement parameterStmt = testCase.getStatement(stmtPosition);
        return parameterStmt;
    }

}
