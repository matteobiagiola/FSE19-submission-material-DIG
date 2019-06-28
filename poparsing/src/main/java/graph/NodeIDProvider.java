package graph;

import org.jgrapht.io.ComponentNameProvider;
import spoon.reflect.declaration.CtClass;


public class NodeIDProvider implements ComponentNameProvider<CtClass<?>> {

    @Override
    public String getName(CtClass<?> ctClass) {
        return ctClass.getSimpleName();
    }
}
