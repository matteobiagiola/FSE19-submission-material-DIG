package org.evosuite.selenium.graph;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import org.jgrapht.ext.DOTImporter;
import org.jgrapht.ext.ImportException;
import org.jgrapht.graph.DirectedPseudograph;


public class Importer {
	
	public static DirectedPseudograph<String,String> graph = new DirectedPseudograph<String, String>(String.class);
	
	public static void importGraphDotFile(String fileName){
		NodesProvider nodesProvider = new NodesProvider();
		EdgesProvider edgesProvider = new EdgesProvider();
		DOTImporter<String, String> importer = new DOTImporter<String, String>(nodesProvider, edgesProvider);
		try {
			BufferedReader in = new BufferedReader(new FileReader(fileName));
			importer.importGraph(graph, in);
		} catch (FileNotFoundException | ImportException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
