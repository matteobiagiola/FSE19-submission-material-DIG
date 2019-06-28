package parsing;

import com.google.common.collect.ImmutableList;
import org.apache.log4j.Logger;
import spoon.reflect.code.*;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.reference.CtTypeReference;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkState;

public class MethodProcessor {

    private List<CtClass<?>> parsedPOs;
    private List<String> poNames;
    private final static Logger logger = Logger.getLogger(MethodProcessor.class);
    private static final Set<Class<?>> WRAPPER_TYPES = getWrapperTypes();

    public MethodProcessor(List<CtClass<?>> parsedPOs){
        this.parsedPOs = ImmutableList.copyOf(parsedPOs);
        this.poNames = this.parsedPOs.stream().map(parsedPO -> parsedPO.getSimpleName()).collect(Collectors.toList());
    }

    public List<CtClass<?>> renameMethodsOfPO(){
        List<CtClass<?>> modifiedParsedPOs = this.parsedPOs.stream()
                .map(parsedPO -> {
                    String parsedPOName = parsedPO.getSimpleName();
                    Set<CtMethod<?>> navigationalMethods = parsedPO.getMethods().stream()
                            .filter(isNavigationalMethod.apply(this.poNames))
                            .collect(Collectors.toSet());
                    Set<CtMethod<?>> renamedMethods = navigationalMethods.stream()
                            .map(poMethod -> {
                                poMethod.setSimpleName(poMethod.getSimpleName() + parsedPOName);
                                return poMethod;
                            })
                            .collect(Collectors.toSet());
                    parsedPO.setMethods(renamedMethods);
                    return parsedPO;
                })
                .collect(Collectors.toList());
        return modifiedParsedPOs;
    }

    public static Function<String, Function<CtMethod<?>, CtMethod<?>>> methodRenaming = nameToAppend -> {
        return ctMethod -> ctMethod.setSimpleName(ctMethod.getSimpleName() + nameToAppend);
    };

    public static Function<List<String>, Predicate<CtMethod<?>>> isNavigationalMethod = poNames -> {
      return ctMethod -> {
          List<CtReturn> returnStatements = ctMethod.getBody().getElements(ctReturn -> true);
          Optional<CtReturn> returnPO = returnStatements.stream()
                  .filter(ctReturn -> {
                      CtExpression ctExpression = ctReturn.getReturnedExpression();
                      if(ctExpression instanceof CtConstructorCall){
                          CtConstructorCall ctConstructorCall = (CtConstructorCall) ctExpression;
                          String returnTypeName = ctConstructorCall.getType().getSimpleName();
                          return poNames.contains(returnTypeName);
                      }else if(ctExpression instanceof CtInvocation){
                          CtInvocation ctInvocation = (CtInvocation) ctExpression;
                          List<CtTypeReference> typeCasts = ctInvocation.getTypeCasts();
                          if(typeCasts.isEmpty()){
                              String returnTypeName = ctInvocation.getType().toString();
                              return poNames.contains(returnTypeName);
                          }else{
                              checkState(typeCasts.size() == 1, "Return expression " + ctReturn +  " must have exactly one cast type: found " + typeCasts.size() + ", " + typeCasts);
                              CtTypeReference ctTypeReference = typeCasts.get(0);
                              String returnTypeName = ctTypeReference.getSimpleName();
                              return poNames.contains(returnTypeName);
                          }
                      }else if(ctExpression instanceof CtThisAccess){
                          CtThisAccess ctThisAccess = (CtThisAccess) ctExpression;
                          CtExpression targetExpression = ctThisAccess.getTarget();
                          String returnClassQualifiedName = targetExpression.toString();
                          String[] packageNames = returnClassQualifiedName.split("\\.");
                          String returnClassSimpleName = packageNames[packageNames.length - 1];
                          return poNames.contains(returnClassSimpleName);
                      }else{
                          logger.warn("Method: " + ctMethod.getSimpleName() + " is NOT a navigational method.");
                          return false;
                      }
                  })
                  .findFirst();
          if(returnPO.isPresent()) {
              return true;
          }
          return false;
      };
    };

    /*public static Predicate<CtMethod<?>> isNavigationalMethod = ctMethod -> {
        logger.debug("Is method: " + ctMethod.getSimpleName() + " a navigational method?");
        List<CtReturn> returnStatements = ctMethod.getBody().getElements(ctReturn -> true);
        Optional<CtReturn> returnPO = returnStatements.stream()
                .filter(ctReturn -> {
                    String returnType = ctReturn.getReturnedExpression().getType().getSimpleName();
                    logger.debug("Return type: " + returnType);
                    return isWrapperType(returnType.getClass());
                })
                .findFirst();
        if(returnPO.isPresent()) {
            logger.debug("YES");
            return true;
        }
        logger.debug("NO");
        return false;
    };*/

    public static boolean isWrapperType(Class<?> clazz)
    {
        return WRAPPER_TYPES.contains(clazz);
    }

    private static Set<Class<?>> getWrapperTypes()
    {
        Set<Class<?>> ret = new HashSet<Class<?>>();
        ret.add(Boolean.class);
        ret.add(Character.class);
        ret.add(Byte.class);
        ret.add(Short.class);
        ret.add(Integer.class);
        ret.add(Long.class);
        ret.add(Float.class);
        ret.add(Double.class);
        ret.add(Void.class);
        return ret;
    }
}
