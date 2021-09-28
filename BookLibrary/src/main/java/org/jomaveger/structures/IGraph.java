package org.jomaveger.structures;

public interface IGraph<T> {

	boolean isDirected();
	
	boolean isWeighted();
	
	void addVertex(Vertex<T> v);
	
	void addEdge(Edge<T> e);
	
	void removeVertex(Vertex<T> v);
	
	void removeEdge(Edge<T> e);
	
	boolean hasVertex(Vertex<T> v);
	
	boolean hasEdge(Edge<T> e);
	
	Double getWeight(Edge<T> e);
	
	IList<Edge<T>> getAdj(Vertex<T> v);
	
	boolean isEmpty();
	
	int numVertex();
	
	int numEdges();
	
	IGraph<T> deepCopy();
}
