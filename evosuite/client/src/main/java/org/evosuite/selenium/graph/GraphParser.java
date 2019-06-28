package org.evosuite.selenium.graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.evosuite.setup.TestCluster;
import org.evosuite.testcase.statements.MethodStatement;
import org.evosuite.utils.generic.GenericAccessibleObject;
import org.evosuite.utils.generic.GenericMethod;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DirectedPseudograph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GraphParser {

	private static final Logger logger = LoggerFactory.getLogger(GraphParser.class);

	/* -(\w*)- source*/
	/* (\w*) method*/
	/* -(\w*)\s target*/
	//edge syntax = methodName-sourceVertex-targetVertex hyphen
	public static String getMethodName(String edge){
		StringBuffer method = new StringBuffer();
		Pattern pattern = Pattern.compile("(\\w*)");
		Matcher matcher = pattern.matcher(edge);
		if(matcher.find()){
			method.append(matcher.group(1)); //take text between delimiters
		}
		logger.debug("getMethodName: edge " + edge + " method " + method.toString());
		return method.toString();
		/*int hyphenIndex = edge.indexOf("-");
		String methodName = edge.substring(0, hyphenIndex);
		return methodName;*/
	}
	
	public static String getEdgeSource(String edge){
		StringBuffer sourceNode = new StringBuffer();
		Pattern pattern = Pattern.compile("-(\\w*)-");
		Matcher matcher = pattern.matcher(edge);
		if(matcher.find()){
			sourceNode.append(matcher.group(1)); //take text between delimiters
		}
		logger.debug("getEdgeSource: edge " + edge + " sourceNode " + sourceNode.toString());
		return sourceNode.toString();
		/*int firstHyphenIndex = edge.indexOf("-");
		int secondHyphenIndex = edge.indexOf("-", firstHyphenIndex + 1);
		String sourceNode = edge.substring(firstHyphenIndex + 1, secondHyphenIndex);
		return sourceNode;*/
	}
	
	public static String getEdgeTarget(String edge){
		StringBuffer targetNode = new StringBuffer();
		Pattern pattern = Pattern.compile("-(\\w*)$");
		Matcher matcher = pattern.matcher(edge);
		if(matcher.find()){
			targetNode.append(matcher.group(1)); //take text between delimiters
		}
		logger.debug("getEdgeTarget: edge " + edge + " targetNode " + targetNode.toString());
		return targetNode.toString();

		/*int firstHyphenIndex = edge.indexOf("-");
		int secondHyphenIndex = edge.indexOf("-", firstHyphenIndex + 1);
		String targetNode = edge.substring(secondHyphenIndex + 1, edge.length());
		return targetNode;*/
	}
	
	public static String fromMethodToEdge(MethodStatement method){
		String result = "";
		DirectedPseudograph<String,String> graph = Importer.graph;
		Set<String> edges = graph.edgeSet();
		for(String edge: edges){
			if(edge.contains(method.getMethodName())){
				result = edge;
			}
		}
		return result;
	}

	/**
	 * @return empty string if method is not an edge of the graph; edge of the graph otherwise
	 * with this syntax: edgeName-sourceNode-targetNode
	* */
	public static String fromMethodToEdge(GenericMethod method){
		String result = "";
		DirectedPseudograph<String,String> graph = Importer.graph;
		Set<String> edges = graph.edgeSet();
		for(String edge: edges){
			if(edge.contains(method.getName())){
				result = edge;
			}
		}
		return result;
	}
	
	public static GenericAccessibleObject<?> findRightImmutableMethodCall(String methodName){
		GenericAccessibleObject<?> call = null;
		for(GenericAccessibleObject<?> currentCall: TestCluster.getInstance().getImmutableTestCalls()){
			if(currentCall.isMethod()){
				GenericMethod method = (GenericMethod) currentCall;
				//System.out.println("@@@method in immutable list: " + method.getName());
				if(method.getName().equals(methodName)){
					call = currentCall;
					return call;
				}
			}
		}
		return null;
	}
	
	public static GenericAccessibleObject<?> findRightMethodCall(String methodName){
		GenericAccessibleObject<?> call = null;
		for(GenericAccessibleObject<?> currentCall: TestCluster.getInstance().getTestCalls()){
			if(currentCall.isMethod()){
				GenericMethod method = (GenericMethod) currentCall;
				//System.out.println("@@@method in mutable list: " + method.getName());
				if(method.getName().equals(methodName)){
					call = currentCall;
					return call;
				}
			}
		}
		return null;
	}
	
	//check if a call can be used
	public static boolean isValid(GenericAccessibleObject<?> call){
		DirectedPseudograph<String,String> graph = Importer.graph;
		if(call instanceof GenericMethod){
			GenericMethod method = (GenericMethod) call;
			String edge = fromMethodToEdge(method);
			if(graph.containsEdge(edge)){
				return true;
			}
			System.out.println("@@@cannot use " + method + " call");
			return false;
		}
		else{
			System.out.println("@@@cannot use " + call + " call");
			return false;
		}
	}
	
	public static List<String> getNodesFromEdges(List<String> edgeList, GraphPath<String,String> graphPath){
        if (edgeList.isEmpty()) {
            String startVertex = graphPath.getStartVertex();
            if (startVertex != null && startVertex.equals(graphPath.getEndVertex())) {
                return Collections.singletonList(startVertex);
            } else {
                return Collections.emptyList();
            }
        }

        Graph<String, String> g = graphPath.getGraph();
        List<String> list = new ArrayList<String>();
        String v = graphPath.getStartVertex();
        list.add(v);
        for (String e : edgeList) {
            v = Graphs.getOppositeVertex(g, e, v);
            list.add(v);
        }
        return list;
	}
}
