package graph;

import org.jgrapht.graph.DirectedPseudograph;
import org.jgrapht.io.DOTExporter;
import org.jgrapht.io.IntegerComponentNameProvider;
import spoon.reflect.declaration.CtClass;

import java.io.*;

public class GraphExporter {

    private final DirectedPseudograph<CtClass<?>, Connection> graph;
    private final EdgeLabelProvider edgeLabelProvider;
    private final NodeIDProvider nodeIDProvider;

    public GraphExporter(DirectedPseudograph<CtClass<?>, Connection> graph){
        this.graph = graph;
        this.edgeLabelProvider = new EdgeLabelProvider();
        this.nodeIDProvider = new NodeIDProvider();
    }

    public void export(String graphDOTFileName){
        DOTExporter<CtClass<?>, Connection> dotExporter = new DOTExporter<CtClass<?>, Connection>(nodeIDProvider, null, edgeLabelProvider);
        try {
            Writer writer = new PrintWriter(graphDOTFileName + ".txt", "UTF-8");
            dotExporter.exportGraph(this.graph, writer);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
