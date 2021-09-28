package org.jomaveger.structures;

import java.io.Serializable;
import java.util.Objects;

import org.jomaveger.lang.DeepCloneable;
import org.jomaveger.lang.dbc.Contract;

public class Set<T> implements ISet<T>, Serializable {

    private IList<T> set;

    public Set() {
        this.set = new LinkedList<>();
        
        Contract.ensure(isEmpty() && (cardinal() == 0));
        Contract.invariant(checkInvariant());
    }

    @Override
    public void makeUnitSet(final T elem) {
    	Contract.invariant(checkInvariant());
    	Contract.require(elem != null);
    	
        this.clear();
        this.add(elem);
        
        Contract.ensure(!isEmpty() && (cardinal() == 1) && (contains(elem) == true));
        Contract.invariant(checkInvariant());
    }

    @Override
    public void clear() {
    	Contract.invariant(checkInvariant());
    	
        this.set.clear();
        
        Contract.ensure(isEmpty() && (cardinal() == 0));
        Contract.invariant(checkInvariant());
    }

    @Override
    public void add(final T elem) {
    	Contract.invariant(checkInvariant());
    	Contract.require(elem != null);
    	boolean oldContains = contains(elem);
    	int oldCardinal = cardinal();
    	
        if (! this.set.contains(elem)) {
            this.set.addLast(elem);
        }
        
        Contract.ensure(!isEmpty() && (contains(elem) == true) && (oldContains || cardinal() == oldCardinal + 1));
        Contract.invariant(checkInvariant());
    }

    @Override
    public Boolean contains(final T elem) {
    	Contract.invariant(checkInvariant());
    	Contract.require(elem != null);
    	int oldCardinal = cardinal();
    	
    	Boolean contains = this.set.contains(elem);
    	
    	Contract.ensure(cardinal() == oldCardinal);
        Contract.invariant(checkInvariant());
        return contains;
    }

    @Override
    public void remove(final T elem) {
    	Contract.invariant(checkInvariant());
    	Contract.require(elem != null);
    	int oldCardinal = cardinal();
    	
        this.set.remove(elem);
        
        Contract.ensure(!contains(elem) || (cardinal() == oldCardinal - 1));
        Contract.invariant(checkInvariant());
    }

    @Override
    public Boolean isSubset(final ISet<T> other) {
    	Contract.invariant(checkInvariant());
    	Contract.require(other != null);
    	int oldCardinal = cardinal();
    	
    	Boolean isSubset = Boolean.TRUE;
        for (Integer i = 0; i < this.set.size(); i++) {
            if (! other.contains(this.set.get(i))) {
            	isSubset = Boolean.FALSE;
            }
        }
        
        Contract.ensure(cardinal() == oldCardinal);
        Contract.invariant(checkInvariant());
        return isSubset;
    }

    @Override
    public void union(final ISet<T> other) {
    	Contract.invariant(checkInvariant());
    	Contract.require(other != null);
    	int oldCardinal = cardinal();
    	int oldOtherCardinal = other.cardinal();
    	
        Set<T> otherSet = (Set<T>) other;
        for (Integer i = 0; i < otherSet.set.size(); i++) {
            if (! this.set.contains(otherSet.set.get(i))) {
                this.set.addLast(otherSet.set.get(i));
            }
        }
        
        Contract.ensure(cardinal() <= oldCardinal + oldOtherCardinal);
        Contract.invariant(checkInvariant());
    }

    @Override
    public void intersection(final ISet<T> other) {
    	Contract.invariant(checkInvariant());
    	Contract.require(other != null);
    	int oldCardinal = cardinal();
    	int oldOtherCardinal = other.cardinal();
    	
        ISet<T> temp = new Set<T>();
        for (T elem : this.set) {
            if (other.contains(elem)) {
                temp.add(elem);
            }
        }
        this.clear();
        Set<T> tempSet = (Set<T>) temp;
        for (Integer i = 0; i < tempSet.set.size(); i++) {
            this.add(tempSet.set.get(i));
        }
        
        Contract.ensure(cardinal() <= oldCardinal + oldOtherCardinal);
        Contract.invariant(checkInvariant());
    }

    @Override
    public void difference(final ISet<T> other) {
    	Contract.invariant(checkInvariant());
    	Contract.require(other != null);
    	int oldCardinal = cardinal();
    	
        Set<T> otherSet = (Set<T>) other;
        for (Integer i = 0; i < otherSet.set.size(); i++) {
            this.remove(otherSet.set.get(i));
        }
        
        Contract.ensure(cardinal() <= oldCardinal);
        Contract.invariant(checkInvariant());
    }

    @Override
    public Boolean isEmpty() {
    	Contract.invariant(checkInvariant());
    	
        Boolean condition = this.set.isEmpty();
        
        Contract.ensure(condition == (cardinal() == 0));
    	Contract.invariant(checkInvariant());
    	
        return condition;
    }

    @Override
    public Integer cardinal() {
    	Contract.invariant(checkInvariant());
    	
        Integer size = this.set.size();
        
        Contract.ensure(size >= 0);
        Contract.invariant(checkInvariant());
        return size;
    }

    @Override
    public ISet<T> deepCopy() {
    	Contract.invariant(checkInvariant());
    	ISet<T> deepCopy;
        try {
        	deepCopy = DeepCloneable.deepCopy(this);
        } catch (Exception e) {
        	deepCopy = new Set<>();
        }
        Contract.ensure(deepCopy.equals(this) || deepCopy.isEmpty());
        Contract.invariant(checkInvariant());
        return deepCopy;
    }
    
    private boolean checkInvariant() {
    	return this.set != null;
    }

    @Override
    public boolean equals(Object otherObject) {
        if (this == otherObject) return true;

        if (otherObject == null
                || this.getClass() != otherObject.getClass())
            return false;

        Set<T> that = (Set<T>) otherObject;
        return Objects.equals(this.set, that.set);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.set);
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append(this.getClass().getName() + "[");
        string.append(this.set.toString());
        string.append("]");
        return string.toString();
    }
}
