package org.evosuite.ga.metaheuristics.art.distance.input.parsing;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.EnumDeclaration;
import com.github.javaparser.ast.nodeTypes.NodeWithImplements;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import org.evosuite.Properties;
import org.evosuite.ga.metaheuristics.art.distance.input.ParsedCustomClasses;
import org.evosuite.ga.metaheuristics.art.distance.input.ParsedCustomEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Parser {

    private String sourceCodeDirectoryName;
    private static final Logger logger = LoggerFactory.getLogger(Parser.class);

    public Parser(String sourceCodeDirectoryName) {
        this.sourceCodeDirectoryName = sourceCodeDirectoryName;
        File sourceCodeDirectoryFile = new File(sourceCodeDirectoryName);
        if(!sourceCodeDirectoryFile.exists()) {
            throw new IllegalArgumentException("Source code directory does not exist. Path: "
                    + sourceCodeDirectoryName);
        }

        if(sourceCodeDirectoryFile.exists() && !sourceCodeDirectoryFile.isDirectory()) {
            throw new IllegalArgumentException("Source code directory is not a directory. Path: "
                    + sourceCodeDirectoryName);
        }

        if(sourceCodeDirectoryFile.exists() && sourceCodeDirectoryFile.isDirectory()
                && sourceCodeDirectoryFile.list() == null) {
            throw new IllegalArgumentException("Source code directory does not contain any file. Path: "
                    + sourceCodeDirectoryName);
        }
    }

    /**
     * as a side effect builds a key value store with custom class name and custom class details
     * */
    public void parseClasses(){
        File sourceCodeDirectory = new File(sourceCodeDirectoryName);
        List<File> javaFiles = this.getListOfJavaFiles(sourceCodeDirectory);
        javaFiles.forEach(javaFile -> {
            CompilationUnit compilationUnit = null;
            try {
                compilationUnit = JavaParser.parse(javaFile);
                String compilationUnitName = compilationUnit.getTypes().get(0).getNameAsString();
                Optional<ClassOrInterfaceDeclaration> classOrInterfaceDeclarationOptional = compilationUnit.getClassByName(compilationUnitName);
                Optional<EnumDeclaration> enumDeclarationOptional = compilationUnit.getEnumByName(compilationUnitName);
                if(classOrInterfaceDeclarationOptional.isPresent()){
                    ClassOrInterfaceDeclaration classOrInterfaceDeclaration = classOrInterfaceDeclarationOptional.get();
                    if(implementInterface(classOrInterfaceDeclaration, Properties.TEST_DATA_INTERFACE)){
                        //it is a custom class
                        CustomClassDetails customClassDetails = new CustomClassDetails(classOrInterfaceDeclaration);
                        ParsedCustomClasses.getInstance().put(customClassDetails.getName(), customClassDetails);
                    }
                }else if(enumDeclarationOptional.isPresent()){
                    EnumDeclaration enumDeclaration = enumDeclarationOptional.get();
                    if(implementInterface(enumDeclaration, Properties.TEST_DATA_INTERFACE)){
                        //it is a custom enum
                        CustomEnumDetails customEnumDetails = new CustomEnumDetails(enumDeclaration);
                        ParsedCustomEnum.getInstance().put(customEnumDetails.getName(), customEnumDetails);
                    }
                }else{
                    logger.warn("No custom class nor custom enum for compilation unit name: " + compilationUnitName);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    private List<File> getListOfJavaFiles(File sourceCodeDirectory){
        List<File> list = new ArrayList<File>();
        for(File file: sourceCodeDirectory.listFiles()){
            if(file.isDirectory()){
                list.addAll(getListOfJavaFiles(file));
            }else if(file.getAbsolutePath().contains(".java")){
                list.add(file);
            }
        }
        return list;
    }

    public static boolean implementInterface(NodeWithImplements nodeWithImplements, String interfaceName){
        NodeList<ClassOrInterfaceType> implementedTypes = nodeWithImplements.getImplementedTypes();
        List<ClassOrInterfaceType> implementedTypesFiltered = implementedTypes.stream().filter(new Predicate<ClassOrInterfaceType>() {
            @Override
            public boolean test(ClassOrInterfaceType classOrInterfaceType) {
                if(classOrInterfaceType.getName().asString().equals(interfaceName)) {
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



}
