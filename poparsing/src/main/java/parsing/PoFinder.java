package parsing;

import org.apache.log4j.Logger;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtField;
import spoon.reflect.declaration.CtMethod;
import utils.MyProperties;

import java.io.File;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;


public class PoFinder {
    
    private final static Logger logger = Logger.getLogger(PoFinder.class);

    public PoFinder(){
    }
    
    private List<File> getClassFiles(File file){
        List<File> result = new ArrayList<File>();
        if(file.isDirectory()){
            for (File child: file.listFiles()){
                result.addAll(this.getClassFiles(child));
            }
        }else{
            logger.debug("Getting class: " + file.getAbsolutePath());
            result.add(file);
        }
        return result;
    }

    private static Function<String, Function<Optional<CtClass<?>>, CtClass<?>>> getCtClassFromOptionalCtClass = errorMessage -> {
        return optional -> {
            if(optional.isPresent()) return optional.get();
            else throw new IllegalStateException(errorMessage);
        };
    };

    private static Predicate<Optional<?>> isEmptyOptional = o -> {
        if(o.isPresent()){
            return true;
        }
        return false;
    };


    public Map<String, List<CtClass<?>>> getPoParsedRepresentations(File file){
        List<File> classFiles = this.getClassFiles(file);
        List<CtClass<?>> parsedClasses = classFiles.stream()
                .map(file1 -> {
                    PoParser poParser = new PoParser(file1);
                    Optional<CtClass<?>> optionalCtClass = poParser.getCtRepresentationOfClass(); // optional because some of them may not be classes
                    return optionalCtClass;
                })
                .filter(isEmptyOptional)
                .map(getCtClassFromOptionalCtClass.apply(this.getClass().getName() + ": file " + file.getAbsolutePath() + " is not a class"))
                .collect(Collectors.toList());

        /*List<CtClass<?>> parsedParametricComponents = parsedClasses.stream()
                .filter(PoParser.isPO.apply("ParametricPageComponent"))
                .collect(Collectors.toList());*/

        /*Map<String, CtClass<?>> mapNameToParsedParametricComponents = parsedParametricComponents.stream()
                .collect(Collectors.toMap(parsedParametricComponent -> parsedParametricComponent.getSimpleName(), Function.identity()));*/

        List<CtClass<?>> parsedPOs = parsedClasses.stream()
                .filter(PoParser.isPO.apply("PageObject"))
                .collect(Collectors.toList());

        /*List<CtClass<?>> parsedPOsWithMethodComponents = parsedPOs.stream()
                .map(parsedPO -> {
                    Set<CtMethod<?>> poMethods = parsedPO.getMethods();
                    List<CtField> parametricComponentFields = parsedPO.getFields().stream()
                            .filter(ctField -> {
                                String fieldTypeName = ctField.getType().getSimpleName();
                                return mapNameToParsedParametricComponents.keySet().contains(fieldTypeName);
                            })
                            .collect(Collectors.toList());
                    parametricComponentFields.stream()
                            .forEach(ctField -> {
                                String fieldTypeName = ctField.getType().getSimpleName();
                                CtClass<?> parametricComponent = mapNameToParsedParametricComponents.get(fieldTypeName).clone();
                                String parametricComponentName = parametricComponent.getSimpleName();
                                Set<CtMethod<?>> parametricComponentMethods = parametricComponent.getMethods();
                                Set<CtMethod<?>> parametricComponentMethodsRenamed = parametricComponentMethods.stream()
                                        .map(MethodProcessor.methodRenaming.apply(parametricComponentName + MyProperties.page_component_static_label))
                                        .collect(Collectors.toSet());
                                poMethods.addAll(parametricComponentMethodsRenamed);
                                parsedPO.setMethods(poMethods);
                            });
                    return parsedPO;
                })
                .collect(Collectors.toList());*/

        Map<String, List<CtClass<?>>> mapOfLists = new LinkedHashMap<String, List<CtClass<?>>>();
        //mapOfLists.put(MyProperties.page_object_interface_name, parsedPOsWithMethodComponents);
        //mapOfLists.put(MyProperties.parametric_page_object_component_interface_name, parsedParametricComponents);
        mapOfLists.put(MyProperties.page_object_interface_name, parsedPOs);
        return mapOfLists;
    }

}
