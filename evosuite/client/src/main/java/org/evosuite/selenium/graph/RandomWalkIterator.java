/* (C) Copyright 2016-2017, by Assaf Mizrachi and Contributors.
*
* JGraphT : a free Java graph-theory library
*
* This program and the accompanying materials are dual-licensed under
* either
*
* (a) the terms of the GNU Lesser General Public License version 2.1
* as published by the Free Software Foundation, or (at your option) any
* later version.
*
* or (per the licensee's choosing)
*
* (b) the terms of the Eclipse Public License v1.0 as published by
* the Eclipse Foundation.
*/

package org.evosuite.selenium.graph;

import java.util.*;

import org.evosuite.selenium.graph.event.*;
import org.jgrapht.Graph;
import org.jgrapht.Graphs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
* 
* A <a href="https://en.wikipedia.org/wiki/Random_walk#Random_walk_on_graphs">random-walk</a>
* iterator for a directed and an undirected graph. At each step selected a randomly (uniformly
* distributed) edge out of the current vertex edges (in case of directed graph - from the outgoing
* edges), and follows it to the next vertex.
* 
* In case a weighted walk is desired (and in case the graph is weighted), edges are selected with
* probability respective to its weight (out of the total weight of the edges).
* 
* Walk can be bounded by number of steps (default {@code Long#MAX_VALUE} . When the bound is
* reached the iterator is considered exhausted. Calling {@code next()} on exhausted iterator will
* throw {@code NoSuchElementException}.
* 
* In case a sink (i.e. no edges) vertex is reached, iterator will return null and consecutive calls
* to {@code next()} will throw {@code NoSuchElementException}.
* 
* For this iterator to work correctly the graph must not be modified during iteration. Currently
* there are no means to ensure that, nor to fail-fast. The results of such modifications are
* undefined.
* 
* @author Assaf Mizrachi
*
* @param <V> vertex type
* @param <E> edge type
*/
public class RandomWalkIterator<V, E>
   extends AbstractGraphIterator<V, E>
{

   private V currentVertex;
   private V vertexToCover;
   private E edgeToTraverse;
   private E currentEdge;
   private final Graph<V, E> graph;
   private final boolean isWeighted;
   private boolean sinkReached;
   private long maxSteps;
   private Random random;

   protected static final Logger logger = LoggerFactory.getLogger(RandomWalkIterator.class);

   /**
    * Creates a new iterator for the specified graph. Iteration will start at arbitrary vertex.
    * Walk is un-weighted and bounded by {@code Long#MAX_VALUE} steps.
    *
    * @param graph the graph to be iterated.
    *
    * @throws IllegalArgumentException if <code>graph==null</code> or does not contain
    *         <code>startVertex</code>
    */
   public RandomWalkIterator(Graph<V, E> graph)
   {
       this(graph, null);
   }

   /**
    * Creates a new iterator for the specified graph. Iteration will start at the specified start
    * vertex. If the specified start vertex is <code>
    * null</code>, Iteration will start at an arbitrary graph vertex. Walk is un-weighted and
    * bounded by {@code Long#MAX_VALUE} steps.
    *
    * @param graph the graph to be iterated.
    * @param startVertex the vertex iteration to be started.
    *
    * @throws IllegalArgumentException if <code>graph==null</code> or does not contain
    *         <code>startVertex</code>
    */
   public RandomWalkIterator(Graph<V, E> graph, V startVertex)
   {
       this(graph, startVertex, true);
   }

   /**
    * Creates a new iterator for the specified graph. Iteration will start at the specified start
    * vertex. If the specified start vertex is <code>
    * null</code>, Iteration will start at an arbitrary graph vertex. Walk is bounded by
    * {@code Long#MAX_VALUE} steps.
    *
    * @param graph the graph to be iterated.
    * @param startVertex the vertex iteration to be started.
    * @param isWeighted set to <code>true</code> if a weighted walk is desired.
    *
    * @throws IllegalArgumentException if <code>graph==null</code> or does not contain
    *         <code>startVertex</code>
    */
   public RandomWalkIterator(Graph<V, E> graph, V startVertex, boolean isWeighted)
   {
       this(graph, startVertex, isWeighted, Long.MAX_VALUE);
   }

   /**
    * Creates a new iterator for the specified graph. Iteration will start at the specified start
    * vertex. If the specified start vertex is <code>
    * null</code>, Iteration will start at an arbitrary graph vertex. Walk is bounded by the
    * provided number steps.
    *
    * @param graph the graph to be iterated.
    * @param startVertex the vertex iteration to be started.
    * @param isWeighted set to <code>true</code> if a weighted walk is desired.
    * @param maxSteps number of steps before walk is exhausted.
    *
    * @throws IllegalArgumentException if <code>graph==null</code> or does not contain
    *         <code>startVertex</code>
    */
   public RandomWalkIterator(Graph<V, E> graph, V startVertex, boolean isWeighted, long maxSteps)
   {
       if (graph == null) {
           throw new IllegalArgumentException("graph must not be null");
       }
       // do not cross components.
       setCrossComponentTraversal(false);
       this.graph = graph;
       this.isWeighted = isWeighted;
       this.maxSteps = maxSteps;
       this.specifics = createGraphSpecifics(graph);
       // select a random start vertex in case not provided.
       if (startVertex == null) {
           if (graph.vertexSet().size() > 0) {
               currentVertex = graph.vertexSet().iterator().next();
           }
       } else if (graph.containsVertex(startVertex)) {
           currentVertex = startVertex;
       } else {
           throw new IllegalArgumentException("graph must contain the start vertex " + startVertex);
       }
       this.sinkReached = false;
       this.random = new Random();
   }
   
   /**
    * Creates a new iterator for the specified graph. Iteration will start at the specified start
    * vertex. If the specified start vertex is <code>
    * null</code>, Iteration will start at an arbitrary graph vertex. Walk is bounded by the
    * provided number steps. If the walk reaches the end vertex the search it stops.
    *
    * @param graph the graph to be iterated.
    * @param startVertex the vertex iteration to be started.
    * @param endVertex the vertex iteration to be ended.
    * @param isWeighted set to <code>true</code> if a weighted walk is desired.
    * @param maxSteps number of steps before walk is exhausted.
    *
    * @throws IllegalArgumentException if <code>graph==null</code> or does not contain
    *         <code>startVertex</code>
    */
   public RandomWalkIterator(Graph<V, E> graph, V startVertex, E edgeToTraverse, boolean isWeighted, long maxSteps)
   {
	   this(graph, startVertex, isWeighted, maxSteps);
       if(edgeToTraverse == null || edgeToTraverse.toString().isEmpty()){
           throw new IllegalStateException("edge to traverse must not be null nor empty");
       }
	   if(graph.containsEdge(edgeToTraverse)){
		   this.edgeToTraverse = edgeToTraverse;
	   }else{
		   throw new IllegalArgumentException("graph must contain the edge to traverse " + edgeToTraverse);
	   }
   }
   
   //V and E may potentially be the same -> the same constructor of above with V instead of E throws an error
   public RandomWalkIterator(Graph<V, E> graph, V startVertex, V vertexToCover, long maxSteps)
   {
	   this(graph, startVertex, false, maxSteps);
       if(vertexToCover == null){
           throw new IllegalStateException("vertex to cover must not be null");
       }
	   if(graph.containsVertex(vertexToCover)){
		   this.vertexToCover = vertexToCover;
	   }else{
		   throw new IllegalArgumentException("graph must contain the vertex to cover " + vertexToCover);
	   }
   }
   
   /**
    * Check if this walk is exhausted. Calling {@link #next()} on exhausted iterator will throw
    * {@link NoSuchElementException}.
    * 
    * @return <code>true</code>if this iterator is exhausted, <code>false</code> otherwise.
    */
   protected boolean isExhausted()
   {
       return maxSteps == 0;
   }

   /**
    * Update data structures every time we see a vertex.
    *
    * @param vertex the vertex encountered
    * @param edge the edge via which the vertex was encountered, or null if the vertex is a
    *        starting point
    */
   protected void encounterVertex(V vertex, E edge)
   {
       maxSteps--;
   }

   /**
    * @see java.util.Iterator#hasNext()
    */
   @Override
   public boolean hasNext()
   {
	   if(this.edgeToTraverse != null){
		   return !this.edgeToTraverse.equals(this.currentEdge) && !isExhausted() && !sinkReached;
	   }
	   else if(this.vertexToCover != null){
		   return !this.vertexToCover.equals(this.currentVertex) && !isExhausted() && !sinkReached;
	   }
	   else{
		   return currentVertex != null && !isExhausted() && !sinkReached;
	   }
   }

   /**
    * @see java.util.Iterator#next()
    */
   @Override
   public V next()
   {

       if (!hasNext()) {
           throw new NoSuchElementException();
       }

       Set<? extends E> potentialEdges = specifics.edgesOf(currentVertex);

       // randomly select an edge from the set of potential edges.
       E nextEdge = drawEdge(potentialEdges);
       if (nextEdge != null) {
           V nextVertex;
           this.currentEdge = nextEdge;
           nextVertex = Graphs.getOppositeVertex(graph, nextEdge, currentVertex);
           encounterVertex(nextVertex, nextEdge);
           fireEdgeTraversed(createEdgeTraversalEvent(nextEdge));
           fireVertexTraversed(createVertexTraversalEvent(nextVertex));
           currentVertex = nextVertex;
           return nextVertex;
       } else {
           sinkReached = true;
           return currentVertex;
       }
   }

   /**
    * Randomly draws an edges out of the provided set. In case of un-weighted walk, edge will be
    * selected with uniform distribution across all outgoing edges. In case of a weighted walk,
    * edge will be selected with probability respective to its weight across all outgoing edges.
    * 
    * @param edges the set to select the edge from
    * @return the drawn edges or null if set is empty.
    */
   private E drawEdge(Set<? extends E> edges)
   {
       if (edges.isEmpty()) {
           return null;
       }

       int drawn = 0;
       List<E> list = new ArrayList<E>(edges);
       if (isWeighted) {
		   Iterator<E> safeIter = list.iterator();
           double border = random.nextDouble() * getTotalWeight(list);
           double d = 0;
           drawn = -1;
           do {
               d += graph.getEdgeWeight(safeIter.next());
               drawn++;
           } while (d < border);  
       } else {
           drawn = random.nextInt(list.size());
       }
       E edge = list.get(drawn);
       return edge;
   }

   private EdgeTraversalEvent<E> createEdgeTraversalEvent(E edge)
   {
       if (isReuseEvents()) {
           reusableEdgeEvent.setEdge(edge);

           return reusableEdgeEvent;
       } else {
           return new EdgeTraversalEvent<E>(this, edge);
       }
   }

   private VertexTraversalEvent<V> createVertexTraversalEvent(V vertex)
   {
       if (isReuseEvents()) {
           reusableVertexEvent.setVertex(vertex);

           return reusableVertexEvent;
       } else {
           return new VertexTraversalEvent<V>(this, vertex);
       }
   }

   private double getTotalWeight(Collection<E> edges)
   {
       double total = 0;
       for (E e : edges) {
           total += graph.getEdgeWeight(e);
       }
       return total;
   }
}
