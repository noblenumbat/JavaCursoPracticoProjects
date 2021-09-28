package org.jomaveger.structures;

import java.io.Serializable;
import java.util.Objects;
import org.jomaveger.lang.DeepCloneable;
import org.jomaveger.lang.dbc.Contract;

public class Bag<T> implements IBag<T>, Serializable {

    private ITable<T, Integer> bag;

    public Bag() {
        this.bag = new LinkedTable<>();
        
        Contract.ensure(isEmpty() && (cardinal() == 0) && cardinalDistinct() == 0);
        Contract.invariant(checkInvariant());
    }

    @Override
    public void add(final T elem) {
    	Contract.invariant(checkInvariant());
    	Contract.require(elem != null);
    	int oldCardinal = cardinal();
    	int oldCardinalDistinct = cardinalDistinct();
    	int oldMultiplicity = multiplicity(elem);
    	
        if (this.bag.contains(elem)) {
            Integer actualMultiplicity = this.bag.get(elem);
            this.bag.set(elem, ++actualMultiplicity);
        } else {
            this.bag.set(elem, 1);
        }
        
        Contract.ensure(!isEmpty() && cardinal() == oldCardinal + 1
        		&& multiplicity(elem) == oldMultiplicity + 1
        		&& (!(multiplicity(elem) == 1)
        				|| cardinalDistinct() == oldCardinalDistinct + 1));
        Contract.invariant(checkInvariant());
    }

    @Override
    public Integer multiplicity(T elem) {
    	Contract.invariant(checkInvariant());
    	Contract.require(elem != null);
    	
        Integer multiplicity;
        if (this.bag.contains(elem)) {
            multiplicity = this.bag.get(elem);
        } else {
            multiplicity = 0;
        }
        
        Contract.ensure(multiplicity >= 0 && multiplicity <= cardinal() && multiplicity <= cardinalDistinct());
        Contract.invariant(checkInvariant());
        return multiplicity;
    }

    @Override
    public void delete(T elem) {
    	Contract.invariant(checkInvariant());
    	Contract.require(elem != null);
    	int oldCardinal = cardinal();
    	int oldCardinalDistinct = cardinalDistinct();
    	int oldMultiplicity = multiplicity(elem);

        if (this.bag.contains(elem)) {
            Integer actualMultiplicity = this.bag.get(elem);
            if (actualMultiplicity > 1) {
                this.bag.set(elem, --actualMultiplicity);
            } else {
                this.bag.remove(elem);
            }
        }
        
        Contract.ensure(cardinal() == oldCardinal - 1
        		&& multiplicity(elem) == oldMultiplicity - 1
        		&& (!(multiplicity(elem) == 0)
        				|| cardinalDistinct() == oldCardinalDistinct - 1));
        Contract.invariant(checkInvariant());
    }

    @Override
    public void remove(T elem) {
    	Contract.invariant(checkInvariant());
    	Contract.require(elem != null);
    	int oldCardinal = cardinal();
    	int oldCardinalDistinct = cardinalDistinct();
    	int oldMultiplicity = multiplicity(elem);
    	
        this.bag.remove(elem);
        
        Contract.ensure(cardinal() == oldCardinal - oldMultiplicity
        		&& multiplicity(elem) == 0
        		&& cardinalDistinct() == oldCardinalDistinct - 1);
        Contract.invariant(checkInvariant());
    }

    @Override
    public void clear() {
    	Contract.invariant(checkInvariant());
    	
        this.bag.clear();
        
        Contract.ensure(isEmpty());
        Contract.invariant(checkInvariant());
    }

    @Override
    public void union(IBag<T> other) {
    	Contract.invariant(checkInvariant());
    	Contract.require(other != null);
    	int oldCardinal = cardinal();
    	int oldCardinalDistinct = cardinalDistinct();
    	int oldOtherCardinal = other.cardinal();
    	int oldOtherCardinalDistinct = other.cardinalDistinct();

        Bag<T> otherBag = (Bag<T>) other;
        IList<T> list = otherBag.bag.keyList();
        for (T elem : list) {
            if (this.bag.contains(elem)) {
                Integer actualMultiplicity = this.bag.get(elem);
                Integer otherMultiplicity = otherBag.bag.get(elem);
                this.bag.set(elem, actualMultiplicity + otherMultiplicity);
            } else {
                this.bag.set(elem, otherBag.bag.get(elem));
            }
        }
        
        Contract.ensure(cardinal() == oldCardinal + oldOtherCardinal
        		&& cardinalDistinct() <= oldCardinalDistinct + oldOtherCardinalDistinct);
        Contract.invariant(checkInvariant());
    }

    @Override
    public void intersection(IBag<T> other) {
    	Contract.invariant(checkInvariant());
    	Contract.require(other != null);
    	int oldCardinal = cardinal();
    	int oldCardinalDistinct = cardinalDistinct();
    	int oldOtherCardinal = other.cardinal();
    	int oldOtherCardinalDistinct = other.cardinalDistinct();
    	
        Bag<T> temp = new Bag<>();
        Bag<T> otherBag = (Bag<T>) other;
        IList<T> list = otherBag.bag.keyList();
        for (T elem : list) {
            if (this.bag.contains(elem)) {
                Integer thisMultiplicity = this.bag.get(elem);
                Integer otherMultiplicity = otherBag.bag.get(elem);
                Integer minMultiplicity = Math.min(thisMultiplicity, otherMultiplicity);
                temp.bag.set(elem, minMultiplicity);
            }
        }

        this.clear();
        IList<T> tempList = temp.bag.keyList();
        for (T elem : tempList) {
            this.bag.set(elem, temp.bag.get(elem));
        }
        
        Contract.ensure(cardinal() < oldCardinal + oldOtherCardinal
        		&& cardinalDistinct() < oldCardinalDistinct + oldOtherCardinalDistinct);
        Contract.invariant(checkInvariant());
    }

    @Override
    public void difference(IBag<T> other) {
    	Contract.invariant(checkInvariant());
    	Contract.require(other != null);
    	int oldCardinal = cardinal();
    	int oldCardinalDistinct = cardinalDistinct();
    	int oldOtherCardinal = other.cardinal();
    	int oldOtherCardinalDistinct = other.cardinalDistinct();
    	
        Bag<T> temp = new Bag<>();
        Bag<T> otherBag = (Bag<T>) other;
        IList<T> list = this.bag.keyList();
        for (T elem : list) {
            if (otherBag.bag.contains(elem)) {
                Integer thisMultiplicity = this.bag.get(elem);
                Integer otherMultiplicity = otherBag.bag.get(elem);
                if (thisMultiplicity > otherMultiplicity) {
                    temp.bag.set(elem, thisMultiplicity - otherMultiplicity);
                }
            } else {
                temp.bag.set(elem, this.bag.get(elem));
            }
        }

        this.clear();
        IList<T> tempList = temp.bag.keyList();
        for (T elem : tempList) {
            this.bag.set(elem, temp.bag.get(elem));
        }
        
        Contract.ensure(cardinal() <= oldCardinal + oldOtherCardinal
        		&& cardinalDistinct() <= oldCardinalDistinct + oldOtherCardinalDistinct);
        Contract.invariant(checkInvariant());
    }

    @Override
    public Boolean isEmpty() {
    	Contract.invariant(checkInvariant());
    	
        Boolean condition = this.bag.isEmpty();
        
        Contract.ensure(condition == (cardinal() == 0));
        Contract.invariant(checkInvariant());
        return condition;
    }

    @Override
    public Integer cardinal() {
    	Contract.invariant(checkInvariant());
    	
        Integer cardinal = 0;
        IList<T> list = this.bag.keyList();
        for (T elem : list) {
            cardinal += this.bag.get(elem);
        }
        
        Contract.ensure(cardinal >= 0);
        Contract.invariant(checkInvariant());
        return cardinal;
    }

    @Override
    public Integer cardinalDistinct() {
    	Contract.invariant(checkInvariant());
    	
        Integer cardinal = 0;
        IList<T> list = this.bag.keyList();
        cardinal = list.size();
        
        Contract.ensure(cardinal <= cardinal());
        Contract.invariant(checkInvariant());
        return cardinal;
    }

    @Override
    public IBag<T> deepCopy() {
    	Contract.invariant(checkInvariant());
        IBag<T> deepCopy;
        try {
            deepCopy = DeepCloneable.deepCopy(this);
        } catch (Exception e) {
            deepCopy = new Bag<>();
        }
        Contract.ensure(deepCopy.equals(this) || deepCopy.isEmpty());
        Contract.invariant(checkInvariant());
        return deepCopy;
    }

    private boolean checkInvariant() {
        return this.bag != null;
    }

    @Override
    public boolean equals(Object otherObject) {
        if (this == otherObject) return true;

        if (otherObject == null
                || this.getClass() != otherObject.getClass())
            return false;

        Bag<T> that = (Bag<T>) otherObject;
        return Objects.equals(this.bag, that.bag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.bag);
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append(this.getClass().getName() + "[");
        string.append(this.bag.toString());
        string.append("]");
        return string.toString();
    }
}