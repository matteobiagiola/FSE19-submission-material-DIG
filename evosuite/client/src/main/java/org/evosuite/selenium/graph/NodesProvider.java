package org.evosuite.selenium.graph;

import java.util.Map;

import org.jgrapht.ext.VertexProvider;

public class NodesProvider implements VertexProvider<String>{

	public String buildVertex(String label, Map<String, String> attributes) {
		// TODO Auto-generated method stub
		return label;
	}

}