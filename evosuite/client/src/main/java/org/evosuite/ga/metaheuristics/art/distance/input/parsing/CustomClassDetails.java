package org.evosuite.ga.metaheuristics.art.distance.input.parsing;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.IntegerLiteralExpr;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import org.evosuite.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CustomClassDetails {

    private ClassOrInterfaceDeclaration clazz;
    private static final Logger logger = LoggerFactory.getLogger(CustomClassDetails.class);

    public CustomClassDetails(){
    }

    public CustomClassDetails(ClassOrInterfaceDeclaration clazz){
        this.clazz = clazz;
    }

    public void setClazz(ClassOrInterfaceDeclaration clazz) {
        this.clazz = clazz;
    }

    /*clazz field not initialized*/
    public boolean isEmpty(){
        if(this.clazz == null) return true;
        return false;
    }

    public boolean isImplementingCustomInterface(){
        return Parser.implementInterface(clazz, Properties.CUSTOM_INTERFACE_NAME);
    }

    public String getName(){
        return this.clazz.getNameAsString();
    }

    public int getUpperValue(){
        Optional<FieldDeclaration> optionalUpperField = clazz.getFieldByName("upper");
        if(optionalUpperField.isPresent()){
            FieldDeclaration upperField = optionalUpperField.get();
            return this.extractValueFromFieldDeclaration(upperField);
        }else{
            throw new IllegalStateException(this.getClass().getName() + ": upper field is not present in custom class " + clazz.getNameAsString());
        }
    }

    public int getLowerValue(){
        Optional<FieldDeclaration> optionalLowerField = clazz.getFieldByName("lower");
        if(optionalLowerField.isPresent()){
            FieldDeclaration lowerField = optionalLowerField.get();
            return this.extractValueFromFieldDeclaration(lowerField);
        }else{
            throw new IllegalStateException(this.getClass().getName() + ": lower field is not present in custom class " + clazz.getNameAsString());
        }
    }

    public String getValuePropertyType(){
        Optional<FieldDeclaration> optionalValueField = clazz.getFieldByName("value");
        if(optionalValueField.isPresent()){
            FieldDeclaration valueField = optionalValueField.get();
            return this.extractTypeFromFieldDeclaration(valueField);
        }else{
            throw new IllegalStateException(this.getClass().getName() + ": value field is not present in custom class " + clazz.getNameAsString());
        }
    }

    private String extractTypeFromFieldDeclaration(FieldDeclaration field){
        /*In our case, at the moment, there is only one variable*/
        if(field.getVariables().size() > 1){
            throw new IllegalStateException(this.getClass().getName() + ": " + field.toString() + " field declaration must have only one variable. Multiple variables are not supported yet");
        }
        VariableDeclarator variableDeclarator = field.getVariables().get(0);
        return variableDeclarator.getType().asString();
    }

    private int extractValueFromFieldDeclaration(FieldDeclaration field){
        /*In our case, at the moment, there is only one variable*/
        if(field.getVariables().size() > 1){
            throw new IllegalStateException(this.getClass().getName() + ": " + field.toString() + " field declaration must have only one variable. Multiple variables are not supported yet");
        }
        VariableDeclarator variableDeclarator = field.getVariables().get(0);
        Optional<Expression> optionalExpression = variableDeclarator.getInitializer();
        Expression expression = optionalExpression.get();
        IntegerLiteralExpr integerLiteralExpr = expression.asIntegerLiteralExpr();
        int value = integerLiteralExpr.asInt();
        return value;
    }

}
