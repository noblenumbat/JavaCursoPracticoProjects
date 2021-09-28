package org.jomaveger.structures;

import java.io.Serializable;
import java.util.Objects;

public class Entry<T> implements Serializable {
	
	private int     degree = 0;
    private boolean isMarked = false;

    private Entry<T> next;
    private Entry<T> prev;

    private Entry<T> parent;

    private Entry<T> child;

    private T      value;
    private double priority;
    
    public Entry(T elem, double mPriority) {
        next = prev = this;
        value = elem;
        priority = mPriority;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public double getPriority() {
        return priority;
    }
    
    public void setPriority(double mPriority) {
		this.priority = mPriority;
	}

	public int getDegree() {
		return degree;
	}

	public void setDegree(int mDegree) {
		this.degree = mDegree;
	}

	public boolean isIsMarked() {
		return isMarked;
	}

	public void setIsMarked(boolean mIsMarked) {
		this.isMarked = mIsMarked;
	}

	public Entry<T> getNext() {
		return next;
	}

	public void setNext(Entry<T> mNext) {
		this.next = mNext;
	}

	public Entry<T> getPrev() {
		return prev;
	}

	public void setPrev(Entry<T> mPrev) {
		this.prev = mPrev;
	}

	public Entry<T> getParent() {
		return parent;
	}

	public void setParent(Entry<T> mParent) {
		this.parent = mParent;
	}

	public Entry<T> getChild() {
		return child;
	}

	public void setChild(Entry<T> mChild) {
		this.child = mChild;
	}
	
	@Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append(this.getClass().getName() + "[");
        string.append(this.value + ", " + this.priority);
        string.append("]");
        return string.toString();
    }

    @Override
    public boolean equals(Object otherObject) {
        if (this == otherObject) return true;

        if (otherObject == null
                || this.getClass() != otherObject.getClass())
            return false;

        Entry<T> that = (Entry<T>) otherObject;

        return Objects.equals(this.value, that.value) &&
                Objects.equals(this.priority, that.priority);
    }

    @Override
    public int hashCode() {
        Object[] array = new Object[2];
        array[0] = this.value;
        array[1] = this.priority;

        return Objects.hash(array);
    }
}
