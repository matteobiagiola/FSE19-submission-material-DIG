package org.evosuite.ga.metaheuristics.art.distance.input.parsing;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.EnumConstantDeclaration;
import com.github.javaparser.ast.body.EnumDeclaration;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.StringLiteralExpr;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import org.evosuite.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CustomEnumDetails {

    private EnumDeclaration enumDeclaration;
    private static final Logger logger = LoggerFactory.getLogger(CustomEnumDetails.class);

    public CustomEnumDetails(){
    }

    public CustomEnumDetails(EnumDeclaration enumDeclaration){
        this.enumDeclaration = enumDeclaration;
    }


    public boolean isImplementingCustomInterface(){
        NodeList<ClassOrInterfaceType> implementedTypes = enumDeclaration.getImplementedTypes();
        List<ClassOrInterfaceType> implementedTypesFiltered = implementedTypes.stream().filter(new Predicate<ClassOrInterfaceType>() {
            @Override
            public boolean test(ClassOrInterfaceType classOrInterfaceType) {
                if(classOrInterfaceType.getName().asString().equals(Properties.CUSTOM_INTERFACE_NAME)) {
                    return true;
                }
                return false;
            }
        }).collect(Collectors.toList());
        if(!implementedTypesFiltered.isEmpty()){
            return true;
        }
        return false;
    }

    public String getName(){
        return this.enumDeclaration.getNameAsString();
    }

    public String getValue(String enumConstantName){
        NodeList<EnumConstantDeclaration> enumEntries = enumDeclaration.getEntries();
        Optional<String> stringOptional = enumEntries.stream()
                .filter(enumConstantDeclaration -> enumConstantDeclaration.getName().asString().equals(enumConstantName))
                .map(enumConstantDeclaration -> {
                    NodeList<Expression> enumArguments = enumConstantDeclaration.getArguments();
                    if(enumArguments.size() > 1 || enumArguments.size() == 0) throw new UnsupportedOperationException("getValue in customEnumDetails: each enum in the list must have only one argument right now.");
                    Expression enumExpression = enumArguments.get(0);
                    if(enumExpression instanceof StringLiteralExpr){
                        StringLiteralExpr stringLiteralExpr = (StringLiteralExpr) enumExpression;
                        return stringLiteralExpr.getValue();
                    }else{
                        throw new UnsupportedOperationException("getValue in customEnumDetails: " + enumExpression + " " + enumExpression.getClass() + " not supported yet. Supported only strings.");
                    }
                })
                .findAny();
        if(stringOptional.isPresent()){
            return stringOptional.get();
        }
        throw new IllegalStateException("getValue in customEnumDetails could not find any value");
    }

    public String getType(){
        return this.getName();
    }

    public List<String> getImpementedInterfaces(){
        NodeList<ClassOrInterfaceType> implementedTypes = enumDeclaration.getImplementedTypes();
        List<String> implementedInterfaceNames = implementedTypes.stream().map(classOrInterfaceType -> classOrInterfaceType.getName().asString()).collect(Collectors.toList());
        //logger.debug("Implemented interfaces of enum " + this.getName() + ": " + implementedInterfaceNames);
        return implementedInterfaceNames;
    }
}
