package graph;

import org.apache.log4j.Logger;
import org.jgrapht.alg.ConnectivityInspector;
import org.jgrapht.graph.DirectedPseudograph;
import spoon.reflect.code.*;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.reference.CtTypeReference;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

public class GraphBuilder {

    private final static Logger logger = Logger.getLogger(GraphBuilder.class);
    private List<CtClass<?>> parsedPOs;
    private List<String> poNames;
    private Map<String, CtClass<?>> mapPONamesCtClasses;

    public GraphBuilder(List<CtClass<?>> parsedPOs){
        this.parsedPOs = parsedPOs;
        this.mapPONamesCtClasses = this.parsedPOs.stream().collect(Collectors.toMap(parsedPO -> parsedPO.getSimpleName(), Function.identity()));
    }

    public DirectedPseudograph<CtClass<?>, Connection> buildGraph(){
        DirectedPseudograph<CtClass<?>, Connection> graphWithNodes = this.addNodesToGraph();
        DirectedPseudograph<CtClass<?>, Connection> graphWithNodesAndEdges = this.addEdgesToGraph(graphWithNodes);
        this.checkConnectivity(graphWithNodesAndEdges);
        return graphWithNodesAndEdges;
    }

    private DirectedPseudograph<CtClass<?>, Connection> addNodesToGraph(){
        DirectedPseudograph<CtClass<?>, Connection> graphWithNodes = new DirectedPseudograph<CtClass<?>, Connection>(Connection.class);
        this.parsedPOs.stream().forEach(parsedPO -> {
            graphWithNodes.addVertex(parsedPO);
        });
        return graphWithNodes;
    }

    private DirectedPseudograph<CtClass<?>, Connection> addEdgesToGraph(DirectedPseudograph<CtClass<?>, Connection> graphWithNodes){
        DirectedPseudograph<CtClass<?>, Connection> clonedGraph = (DirectedPseudograph<CtClass<?>, Connection>) graphWithNodes.clone();
        this.parsedPOs.stream().forEach(parsedPO -> {
            logger.debug("PO: " + parsedPO.getSimpleName());
            Set<CtMethod<?>> poMethods = parsedPO.getMethods();
            poMethods.stream().forEach(poMethod -> {
                logger.debug("Method: " + poMethod.getSimpleName());
                List<CtReturn> returnStatements = poMethod.getElements(ctReturn -> true);
                if(returnStatements.isEmpty()){
                    throw new IllegalStateException("Method: " + poMethod.getSimpleName() + " of page object: " + parsedPO.getSimpleName() + " must have a return statement");
                }
                returnStatements.stream().forEach(ctReturn -> {
                    //logger.debug("Return statement: " + ctReturn);
                    CtExpression returnExpression = ctReturn.getReturnedExpression();
                    if(returnExpression instanceof CtConstructorCall){
                        CtConstructorCall ctConstructorCall = (CtConstructorCall) returnExpression;
                        String targetPOName = ctConstructorCall.getType().getSimpleName();
                        if(this.mapPONamesCtClasses.get(targetPOName) != null){
                            CtClass<?> sourceNode = parsedPO;
                            CtClass<?> targetNode = this.mapPONamesCtClasses.get(targetPOName);
                            checkNotNull(targetNode);
                            this.addConnectionToGraph(clonedGraph,sourceNode,targetNode,poMethod);
                        }
                    }else if(returnExpression instanceof CtThisAccess){
                        CtClass<?> sourceNode = parsedPO;
                        CtClass<?> targetNode = sourceNode;
                        this.addConnectionToGraph(clonedGraph,sourceNode,targetNode,poMethod);
                    }else if(returnExpression instanceof CtInvocation){
                        CtInvocation ctInvocation = (CtInvocation) returnExpression;
                        List<CtTypeReference> typeCasts = ctInvocation.getTypeCasts();
                        checkState(typeCasts.size() == 1, "Return expression must have exactly one cast type!");
                        CtTypeReference ctTypeReference = typeCasts.get(0);
                        String returnTypeName = ctTypeReference.getSimpleName();
                        CtClass<?> sourceNode = parsedPO;
                        CtClass<?> targetNode = this.mapPONamesCtClasses.get(returnTypeName);
                        checkNotNull(targetNode);
                        this.addConnectionToGraph(clonedGraph,sourceNode,targetNode,poMethod);
                    }
                    else{
                        logger.warn("addEdgesToGraph. Unhandled unknown return expression " + returnExpression + " " + returnExpression.getClass());
                    }
                });
            });
        });
        return clonedGraph;
    }

    private void addConnectionToGraph(DirectedPseudograph<CtClass<?>, Connection> graph,
                                      CtClass<?> sourceNode, CtClass<?> targetNode, CtMethod<?> poMethod){
        Connection connection = new Connection(poMethod, sourceNode, targetNode);
        logger.debug("Adding connection: " + connection.toString());
        graph.addEdge(sourceNode,targetNode,connection);
    }

    /*
    * In the applications that I've seen so far from each node it is possible to reach any other
    * node in the graph. If that is not possible it means that the POs are not written properly. It
    * is a check to verify that.
    * */
    private void checkConnectivity(DirectedPseudograph<CtClass<?>, Connection> graph){
        Set<CtClass<?>> vertexes = graph.vertexSet();
        ConnectivityInspector connectivityInspector = new ConnectivityInspector(graph);
        vertexes.forEach(sourceVertex -> {
            vertexes.forEach(targetVertex -> {
                if(connectivityInspector.pathExists(sourceVertex, targetVertex)){
                    logger.warn(sourceVertex.getSimpleName() + " -> " + targetVertex.getSimpleName() + " path exists.");
                }else{
                    throw new IllegalStateException("There exists no path from " + sourceVertex.getSimpleName() + " to " + targetVertex.getSimpleName() + ". Check your page objects.");
                }
                checkState(connectivityInspector.pathExists(sourceVertex, targetVertex),
                        "There exists no path from " + sourceVertex.getSimpleName() + " to " + targetVertex.getSimpleName() + ". Check your page objects.");
            });
        });
    }

}
