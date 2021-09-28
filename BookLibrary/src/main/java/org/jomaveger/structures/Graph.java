package org.jomaveger.structures;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Objects;

import org.jomaveger.lang.DeepCloneable;
import org.jomaveger.lang.dbc.Contract;

public class Graph<T> implements IGraph<T>, Serializable {
	
	protected IList<Vertex<T>> vertex;
	protected IList<IList<Edge<T>>> edges;
	private boolean isDirected;
	private boolean isWeighted;
	
	public Graph() {
		this(false, false);
	}
	
	public Graph(boolean isDirected, boolean isWeighted) {
		this.vertex = new LinkedList<>();
		this.edges = new LinkedList<>();
		this.isDirected = isDirected;
		this.isWeighted = isWeighted;
		
		Contract.ensure(isEmpty());
        Contract.invariant(checkInvariant());
	}
	
	protected int search(Vertex<T> v) {
		return vertex.indexOf(v);
	}

	@Override
	public boolean isDirected() {
		Contract.invariant(checkInvariant());
		boolean resul = this.isDirected;
		Contract.invariant(checkInvariant());
		return resul;
	}

	@Override
	public boolean isWeighted() {
		Contract.invariant(checkInvariant());
		boolean resul = this.isWeighted;
		Contract.invariant(checkInvariant());
		return resul;
	}

	@Override
	public void addVertex(Vertex<T> v) {
		Contract.invariant(checkInvariant());
		Contract.require(v != null);
		int index = this.search(v);
		if (index == -1) {
			vertex.addLast(v);
			edges.addLast(new LinkedList<>());
		}
		Contract.ensure(vertex.contains(v) && !vertex.isEmpty());
		Contract.invariant(checkInvariant());
	}

	@Override
	public void addEdge(Edge<T> e) {
		Contract.invariant(checkInvariant());
		Contract.require(e != null && hasVertex(e.getOrig()) && hasVertex(e.getDest())
				&& (!isWeighted() || !Double.isNaN(e.getWeight())));
		int vIndex = this.search(e.getOrig());
		int wIndex = this.search(e.getDest());
		IList<Edge<T>> eg = edges.get(vIndex);
		int eIndex = eg.indexOf(e);
		if (eIndex == -1) {
			eg.addLast(e);
			if (!isDirected()) {
				edges.get(wIndex).addLast(new Edge<>(e.getDest(), e.getOrig(), e.getWeight()));
			}
		} else {
			if (isWeighted()) {
				eg.get(eIndex).setWeight(e.getWeight());
			}
		}
		Contract.ensure(edges.get(vIndex).contains(e) && !edges.get(vIndex).isEmpty());
		Contract.invariant(checkInvariant());
	}

	@Override
	public void removeVertex(Vertex<T> v) {
		Contract.invariant(checkInvariant());
		Contract.require(v != null);
		int vIndex = this.search(v);
		if (vIndex != -1) {
			vertex.remove(vIndex);
			edges.remove(vIndex);
			for (IList<Edge<T>> iList : edges) {
				for (Iterator<Edge<T>> iterator = iList.iterator(); iterator.hasNext();) {
					Edge<T> elem = iterator.next();
					if (elem.getDest().equals(v)) {
						iterator.remove();
					}
				}
			}
		}
		Contract.ensure(!vertex.contains(v));
		Contract.invariant(checkInvariant());
	}

	@Override
	public void removeEdge(Edge<T> e) {
		Contract.invariant(checkInvariant());
		Contract.require(e != null && hasEdge(e));
		int vIndex = this.search(e.getOrig());
		int wIndex = this.search(e.getDest());
		IList<Edge<T>> vw = edges.get(vIndex);
		int vwIndex = vw.indexOf(e);
		vw.remove(vwIndex);
		if (!isDirected()) {
			IList<Edge<T>> wv = edges.get(wIndex);
			int wvIndex = wv.indexOf(new Edge<>(e.getDest(), e.getOrig(), e.getWeight()));
			wv.remove(wvIndex);
		}
		Contract.ensure(!hasEdge(e));
		Contract.invariant(checkInvariant());
	}

	@Override
	public boolean hasVertex(Vertex<T> v) {
		Contract.invariant(checkInvariant());
		Contract.require(v != null);
		boolean resul = (this.search(v) != -1);
		Contract.ensure(resul == (this.search(v) != -1));
		Contract.invariant(checkInvariant());
		return resul;
	}

	@Override
	public boolean hasEdge(Edge<T> e) {
		Contract.invariant(checkInvariant());
		Contract.require(e != null && hasVertex(e.getOrig()) && hasVertex(e.getDest()));
		int vIndex = this.search(e.getOrig());
		IList<Edge<T>> eg = edges.get(vIndex);
		int eIndex = eg.indexOf(e);
		boolean resul = (eIndex != -1);
		Contract.ensure(resul == (eIndex != -1));
		Contract.invariant(checkInvariant());
		return resul;
	}

	@Override
	public Double getWeight(Edge<T> e) {
		Contract.invariant(checkInvariant());
		Contract.require(e != null && hasEdge(e) && isWeighted());
		int vIndex = this.search(e.getOrig());
		IList<Edge<T>> eg = edges.get(vIndex);
		int eIndex = eg.indexOf(e);
		double w = eg.get(eIndex).getWeight();
		Contract.invariant(checkInvariant());
		return w;
	}

	@Override
	public IList<Edge<T>> getAdj(Vertex<T> v) {
		Contract.invariant(checkInvariant());
		Contract.require(v != null && hasVertex(v));
		int vIndex = this.search(v);
		IList<Edge<T>> adj = edges.get(vIndex);
		Contract.ensure(adj.size() >= 0);
		Contract.invariant(checkInvariant());
		return adj;
	}

	@Override
	public boolean isEmpty() {
		Contract.invariant(checkInvariant());
		boolean resul = this.vertex.size() == 0;
		Contract.ensure(resul == (this.vertex.size() == 0));
		Contract.invariant(checkInvariant());
		return resul;
	}

	@Override
	public int numVertex() {
		Contract.invariant(checkInvariant());
		int size = vertex.size();
		Contract.invariant(checkInvariant());
		return size;
	}

	@Override
	public int numEdges() {
		Contract.invariant(checkInvariant());
		int numEdges = 0;
		for (IList<Edge<T>> iList : edges) {
			numEdges += iList.size();
		}
		if (!isDirected()) {
			numEdges = numEdges / 2;
		}
		Contract.ensure(numEdges >= 0);
		Contract.invariant(checkInvariant());
		return numEdges;
	}

	@Override
	public IGraph<T> deepCopy() {
		Contract.invariant(checkInvariant());
		IGraph<T> deepCopy;
        try {
            deepCopy = DeepCloneable.deepCopy(this);
        } catch (Exception e) {
            deepCopy = new Graph<>();
        }
        Contract.ensure(deepCopy.equals(this) || deepCopy.isEmpty());
        Contract.invariant(checkInvariant());
        return deepCopy;
	}

	private boolean checkInvariant() {
		return this.vertex.size() >= 0;
	}
	
	@Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append(this.getClass().getName() + "[");

        for (IList<Edge<T>> iList : edges) {
			string.append(iList);
		}

        string.append("]");
        return string.toString();
    }

    @Override
    public boolean equals(Object otherObject) {
        if (this == otherObject) return true;

        if (otherObject == null || this.getClass() != otherObject.getClass())
            return false;

        Graph<T> that = (Graph<T>) otherObject;
        
        if (!Objects.equals(this.vertex, that.vertex)) return false;
        
        for (int i = 0; i < this.edges.size(); i++) {
        	if (!Objects.equals(this.edges.get(i), that.edges.get(i))) return false;
			
		}

        return true;
    }

    @Override
    public int hashCode() {
    	final int prime = 31;
		int result = 1;
		result = prime * result + vertex.hashCode();
		result = prime * result + edges.hashCode();
		return result;
    }
}
