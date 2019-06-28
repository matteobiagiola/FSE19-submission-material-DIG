package parsing;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

import org.apache.log4j.Logger;
import spoon.Launcher;
import spoon.SpoonAPI;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.factory.Factory;
import spoon.reflect.reference.CtTypeReference;

public class PoParser {

    final static Logger logger = Logger.getLogger(PoParser.class);
    private final Factory factory;
    private SpoonAPI spoon;

    public PoParser(File classFile){
        SpoonAPI spoon = new Launcher();
        spoon.getEnvironment().setNoClasspath(true);
        spoon.addInputResource(classFile.getAbsolutePath());
        spoon.buildModel();
        this.spoon = spoon;
        this.factory = spoon.getFactory();
    }

    /**
     * @implNote if the parsed class is a PO, it transforms it according to rules
     * defined by the creation of the CUT.
     * */
    public Optional<CtClass<?>> getCtRepresentationOfClass(){
        Optional<CtClass<?>> ctClassOptional;
        List<CtClass<?>> ctClasses = this.spoon.getModel().getElements(ctClass -> true);
        if(ctClasses.isEmpty()){
            ctClassOptional = Optional.empty();
        }else{
            CtClass<?> ctClass = ctClasses.get(0);
            ctClassOptional = Optional.of(ctClass);
        }
        return ctClassOptional;
    }

    public static Function<String, Predicate<CtClass<?>>> isPO = interfaceName -> {
        return ctClass -> {
            Set<CtTypeReference<?>> interfaces = ctClass.getSuperInterfaces();
            if(!interfaces.isEmpty()){
                Optional<CtTypeReference<?>> optionalPoInterface = interfaces.stream().filter(ctTypeReference -> {
                    if(ctTypeReference.getSimpleName().equals(interfaceName)) return true;
                    return false;
                }).findAny();
                if(optionalPoInterface.isPresent()) return true;
                return false;
            }
            return false;
        };
    };




}

