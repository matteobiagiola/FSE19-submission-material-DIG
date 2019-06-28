package org.evosuite.selenium.graph;

import java.util.Map;

import org.jgrapht.ext.EdgeProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EdgesProvider implements EdgeProvider<String,String>{

	private static final Logger logger = LoggerFactory.getLogger(EdgesProvider.class);

	public String buildEdge(String from, String to, String label, Map<String, String> attributes) {
		// TODO Auto-generated method stub
		int indexParenthesis = label.indexOf("(");
		String labelWithoutParenthesis = label.substring(0, indexParenthesis);
		String edge = labelWithoutParenthesis + "-" + from + "-" + to;
		//logger.debug("Build edge: from " + from + " to " + to + " label " + label + ". Final edge: " + edge);
		return edge;
	}

}