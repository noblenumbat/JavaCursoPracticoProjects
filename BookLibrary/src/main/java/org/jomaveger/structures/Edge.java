package org.jomaveger.structures;

import java.io.Serializable;

import org.jomaveger.lang.dbc.Contract;

public class Edge<T> implements Comparable<Edge<T>>, Serializable {
	
	private Vertex<T> orig;
	private Vertex<T> dest;
	private double weight;
	
	private int tiebreaker;
    private static int nextTiebreaker = 0;
	
	public Edge(Vertex<T> v, Vertex<T> w) {
		this(v, w, Double.NaN);
	}
	
	public Edge(Vertex<T> v, Vertex<T> w, double weight) {
		Contract.require(v != null && w != null);
		this.orig = v;
		this.dest = w;
		this.weight = weight;
		
		if (!Double.isNaN(weight))
			tiebreaker = nextTiebreaker++;
	}

	public Vertex<T> getOrig() {
		return orig;
	}

	public void setOrig(Vertex<T> orig) {
		this.orig = orig;
	}

	public Vertex<T> getDest() {
		return dest;
	}

	public void setDest(Vertex<T> dest) {
		this.dest = dest;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	@Override
	public int compareTo(Edge<T> that) {
		int res = Double.compare(this.weight, that.weight);
		
		if (res == 0)
			res = tiebreaker - that.tiebreaker;
		
		return res;
	}
	
	@Override
	public String toString() {
		StringBuilder string = new StringBuilder();
        string.append("[");

        string.append(orig).append("---").append(dest);
        
        if (!Double.isNaN(weight))
        	string.append(String.format(",%f",weight));

        string.append("]");
        return string.toString();
	}
}
