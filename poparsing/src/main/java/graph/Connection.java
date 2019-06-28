package graph;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtParameter;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class Connection {

    private final CtMethod<?> method;
    private final CtClass<?> sourceNode;
    private final CtClass<?> targetNode;

    public Connection(CtMethod<?> method, CtClass<?> sourceNode, CtClass<?> targetNode){
        checkNotNull(method);
        checkNotNull(sourceNode);
        checkNotNull(targetNode);
        this.method = method;
        this.sourceNode = sourceNode;
        this.targetNode = targetNode;
    }

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        builder.append(this.method.getSimpleName());
        builder.append(" -> [");
        builder.append(sourceNode.getSimpleName() + "," + targetNode.getSimpleName() + "]");
        return builder.toString();
    }

    public CtMethod<?> getMethod() {
        return method;
    }

    public CtClass<?> getSourceNode() {
        return sourceNode;
    }

    public CtClass<?> getTargetNode() {
        return targetNode;
    }
}
