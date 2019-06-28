package org.evosuite.ga.metaheuristics.art.distance.input.parsing;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.Optional;

public class ClassVisitor extends VoidVisitorAdapter<CustomClassDetails> {


    @Override
    public void visit(ClassOrInterfaceDeclaration clazz, CustomClassDetails customClassDetails){

        if(!this.isToBeProcessed(clazz)) return;
        customClassDetails.setClazz(clazz);
    }

    private boolean isToBeProcessed(ClassOrInterfaceDeclaration clazz){
        if(clazz.isInterface()) return false;
        return true;
    }
}
