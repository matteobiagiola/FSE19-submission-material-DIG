package org.evosuite.selenium.graph;

import java.util.ArrayList;
import java.util.List;

import org.evosuite.selenium.graph.event.EdgeTraversalEvent;
import org.evosuite.selenium.graph.event.TraversalListenerAdapter;
import org.jgrapht.graph.DirectedPseudograph;


public class PathListener extends TraversalListenerAdapter<String, String>{
	
	List<String> edges = new ArrayList<String>();
	
	public PathListener() 
	{
	}
	
	public PathListener(DirectedPseudograph<String, String> g, String startNode) 
	{
	}
	
	/*@Override
	public void vertexTraversed(VertexTraversalEvent<String> e) 
	{	
		String vertex = e.getVertex();
		//System.out.println("Node added: " + vertex.getTypes().get(0).getName());
		//System.out.println("+ Listener vertex traversed: " + vertex.getTypes().get(0).getName());
	}
	
	@Override
	public void vertexFinished(VertexTraversalEvent<String> e)
	{
		String vertex = e.getVertex();
		//System.out.println("+ Vertex finished: " + vertex.getTypes().get(0).getName());
	}*/
	
	@Override
	public void edgeTraversed(EdgeTraversalEvent<String> e)
	{
		String edge = e.getEdge();
		this.edges.add(edge);
	}
	
	public List<String> getEdges(){
		return this.edges;
	}

}
