package org.jomaveger.structures;

import java.io.Serializable;
import java.util.Objects;

public class Link<T> implements Serializable {

	private T parent;
    private int rank = 0;

    public Link(T parent) {
        this.parent = parent;
    }

	public T getParent() {
		return parent;
	}

	public void setParent(T parent) {
		this.parent = parent;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}
	
	@Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append(this.getClass().getName() + "[");
        string.append(this.parent + ", " + this.rank);
        string.append("]");
        return string.toString();
    }

    @Override
    public boolean equals(Object otherObject) {
        if (this == otherObject) return true;

        if (otherObject == null
                || this.getClass() != otherObject.getClass())
            return false;

        Link<T> that = (Link<T>) otherObject;

        return Objects.equals(this.parent, that.parent) &&
                Objects.equals(this.rank, that.rank);
    }

    @Override
    public int hashCode() {
        Object[] array = new Object[2];
        array[0] = this.parent;
        array[1] = this.rank;

        return Objects.hash(array);
    }
}
