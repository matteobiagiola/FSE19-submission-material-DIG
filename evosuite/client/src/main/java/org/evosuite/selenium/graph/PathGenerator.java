package org.evosuite.selenium.graph;

import java.util.List;

import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DirectedPseudograph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PathGenerator {

	private static final Logger logger = LoggerFactory.getLogger(PathGenerator.class);

	/*the procedure stops when it reaches a node without outgoing edges
	 * if start node and target node are the same a limit has to be set otherwise the iterator never stops.
	 * */
	public static List<String> generateRandomPath(DirectedPseudograph<String,String> graph, String startNodeGraph, boolean isWeighted, int maxSteps){
		
		GraphIterator<String, String> iterator = new RandomWalkIterator<String, String>(graph, startNodeGraph, isWeighted, maxSteps);
		PathListener pathListener = new PathListener();
		iterator.addTraversalListener(pathListener);
		while(iterator.hasNext()){
			iterator.next();
		}
		List<String> edges = pathListener.getEdges();
		return edges;
	}
	
	/**
	 * @param target must be an edge of the graph
	 * */
	public static List<String> generateRandomPathWithTarget(DirectedPseudograph<String,String> graph, String startNodeGraph, String target,boolean isWeighted, int maxSteps){	
		GraphIterator<String, String> iterator = new RandomWalkIterator<String, String>(graph, startNodeGraph, target, isWeighted, maxSteps);
		PathListener pathListener = new PathListener();
		iterator.addTraversalListener(pathListener);
		while(iterator.hasNext()){
			iterator.next();
		}
		List<String> edges = pathListener.getEdges();
		return edges;
	}
	
	/**
	 * @param target must be a vertex of the graph
	 * */
	public static List<String> generateRandomPathWithTarget(DirectedPseudograph<String,String> graph, String startNodeGraph, String target,int maxSteps){	
		GraphIterator<String, String> iterator = new RandomWalkIterator<String, String>(graph, startNodeGraph, target, maxSteps);
		PathListener pathListener = new PathListener();
		iterator.addTraversalListener(pathListener);
		while(iterator.hasNext()){
			iterator.next();
		}
		List<String> edges = pathListener.getEdges();
		return edges;
	}
	
	public static void shortestPath(DirectedPseudograph<String,String> graph, String lastEdge, String edgeToAdd, List<String> edges){
		if(edges.size() == 0){
			throw new IllegalStateException("It was not possible to add the edge " + edgeToAdd + " because its starting node is not reachable from any node of the graph");
		}
		logger.debug("shortestPath. LastEdge: " + lastEdge);
		logger.debug("shortestPath. EdgeToAdd: " + edgeToAdd);
		String startNode = GraphParser.getEdgeTarget(lastEdge);
		logger.debug("shortestPath. startNode: " + startNode);
		String targetNode = GraphParser.getEdgeSource(edgeToAdd);
		logger.debug("shortestPath. targetNode: " + targetNode);
		List<String> shortestPath = DijkstraShortestPath.findPathBetween(graph, startNode, targetNode);
		if(shortestPath != null){
			shortestPath.add(edgeToAdd);
			for(String edge: shortestPath){
				edges.add(edge);
			}
		}else{
			throw new UnsupportedOperationException("shortestPath: shortestPath is null, not able to find a path from " + startNode + "->" + targetNode);
			//the node "targetNode" in which we can attach the edgeToCover is not reachable from the node "startNode"
			//cut the path sequence ("edges") one edge at a time and verify if the "targetNode" is reachable from the
			//new "startNode"
			/*edges.remove(edges.size() - 1);
			lastEdge = edges.get(edges.size() - 1);
			shortestPath(graph, lastEdge, edgeToAdd, edges);*/
		}
	}
	
	public static void shortestPathToVertex(DirectedPseudograph<String,String> graph, String lastEdge, String vertexToAdd, List<String> edges){
		if(edges.size() == 0){
			throw new IllegalStateException("It was not possible to add the vertex " + vertexToAdd + " because it is not reachable from any node of the graph");
		}
		String startNode = GraphParser.getEdgeTarget(lastEdge);
		String targetNode = vertexToAdd;
		List<String> shortestPath = DijkstraShortestPath.findPathBetween(graph, startNode, targetNode);
		if(shortestPath != null){
			for(String edge: shortestPath){
				edges.add(edge);
			}
		}else{
			throw new UnsupportedOperationException("shortestPath: shortestPath is null");
			//the node "targetNode" in which we can attach the edgeToCover is not reachable from the node "startNode"
			//cut the path sequence ("edges") one edge at a time and verify it the "targetNode" is reachable from the
			//new "startNode"
			/*edges.remove(edges.size() - 1);
			lastEdge = edges.get(edges.size() - 1);
			shortestPath(graph, lastEdge, vertexToAdd, edges);*/
		}
	}
}

