package org.jomaveger.structures;

import java.io.Serializable;
import java.util.Objects;

import org.jomaveger.lang.dbc.Contract;

public class Vertex<T> implements Serializable {

	private String name;
	private T value;
	
	public Vertex(String name, T value) {
		Contract.require(name != null && value != null);
		this.name = name;
		this.value = value;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return String.format("(%s,%s)", name, value);
	}
	
	@Override
	public boolean equals(Object otherObject) {
		if (this == otherObject) return true;

        if (otherObject == null
                || this.getClass() != otherObject.getClass())
            return false;

        Vertex<T> that = (Vertex<T>) otherObject;
        
        if (!Objects.equals(this.name, that.name))
            return false;

        return Objects.equals(this.value, that.value);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + name.hashCode();
		result = prime * result + value.hashCode();
		return result;
	}
}
