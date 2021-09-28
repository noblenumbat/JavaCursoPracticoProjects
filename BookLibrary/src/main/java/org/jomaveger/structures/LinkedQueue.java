package org.jomaveger.structures;

import java.io.Serializable;
import java.util.Objects;

import org.jomaveger.lang.DeepCloneable;
import org.jomaveger.lang.dbc.Contract;

public class LinkedQueue<T> implements IQueue<T>, Serializable {

    private LinkedNode<T> first;
    private LinkedNode<T> last;
    private Integer size;

    public LinkedQueue() {
        this.first = null;
        this.last = null;
        this.size = 0;
        
        Contract.ensure(isEmpty());
        Contract.invariant(checkInvariant());
    }

    @Override
    public void enqueue(final T elem) {
    	Contract.invariant(checkInvariant());
    	Contract.require(elem != null);
    	int oldSize = size();
    	
        LinkedNode<T> oldLast = this.last;
        this.last = new LinkedNode<>(elem);
        if (this.size == 0)
            this.first = this.last;
        else
            oldLast.setNext(this.last);
        this.size++;
        
        Contract.ensure((!(size() == 1) || (front() == elem)) && (size() == (oldSize + 1)));
        Contract.invariant(checkInvariant());
    }

    @Override
    public void dequeue() {
    	Contract.invariant(checkInvariant());
    	Contract.require(size > 0);
    	int oldSize = size();
    	
        this.first = this.first.getNext();
        this.size--;
        if (this.size == 0)
            this.last = null;
        
        Contract.ensure(size() == (oldSize - 1));
        Contract.invariant(checkInvariant());
    }

    @Override
    public T front() {
    	Contract.invariant(checkInvariant());
    	Contract.require(size > 0);
    	int oldSize = size();
    	
    	T elem = this.first.getElem();
    	
    	Contract.ensure(size() == oldSize);
    	Contract.invariant(checkInvariant());
        return elem;
    }

    @Override
    public Boolean isEmpty() {
    	Contract.invariant(checkInvariant());
    	
    	Boolean condition = this.size == 0;
    	
    	Contract.ensure(condition == (this.size == 0));
    	Contract.invariant(checkInvariant());
        return condition;
    }

    @Override
    public Integer size() {
    	Contract.invariant(checkInvariant());
    	
        Integer size = this.size;
        
        Contract.ensure(this.size >= 0);
        Contract.invariant(checkInvariant());
        return size;
    }

    @Override
    public IQueue<T> deepCopy() {
    	Contract.invariant(checkInvariant());
    	IQueue<T> deepCopy;
        try {
        	deepCopy = DeepCloneable.deepCopy(this);
        } catch (Exception e) {
        	deepCopy = new LinkedQueue<>();
        }
        Contract.ensure(deepCopy.equals(this) || deepCopy.isEmpty());
        Contract.invariant(checkInvariant());
        return deepCopy;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append(this.getClass().getName() + "[");

        LinkedNode<T> temp = this.first;
        while (temp != null) {
            string.append(temp.getElem()).append(", ");
            temp = temp.getNext();
        }

        string.append("]");
        return string.toString();
    }

    @Override
    public boolean equals(Object otherObject) {
        if (this == otherObject) return true;

        if (otherObject == null || this.getClass() != otherObject.getClass())
            return false;

        LinkedQueue<T> that = (LinkedQueue<T>) otherObject;

        if (!Objects.equals(this.size, that.size)) return false;

        LinkedNode<T> tempThis = this.first;
        LinkedNode<T> tempThat = that.first;
        while (tempThis != null) {
            if (!Objects.equals(tempThis.getElem(), tempThat.getElem()))
                return false;
            tempThis = tempThis.getNext();
            tempThat = tempThat.getNext();
        }
        return true;
    }

    @Override
    public int hashCode() {
        Object[] array = new Object[this.size + 1];
        array[0] = this.size;

        LinkedNode<T> temp = this.first;
        Integer i = 1;
        while (temp != null && i < array.length) {
            array[i] = temp.getElem();
            i++;
            temp = temp.getNext();
        }

        return Objects.hash(array);
    }
    
    private boolean checkInvariant() {
    	if (this.size < 0) {
            return false;
        }
        else if (this.size == 0) {
            if (this.first != null) return false;
            if (this.last  != null) return false;
        }
        else if (this.size == 1) {
            if (this.first == null || this.last == null) return false;
            if (this.first != this.last)                 return false;
            if (this.first.getNext() != null)            return false;
        }
        else {
            if (this.first == null || this.last == null) return false;
            if (this.first == this.last)      			 return false;
            if (this.first.getNext() == null) 			 return false;
            if (this.last.getNext() != null) 			 return false;

            int numberOfNodes = 0;
            for (LinkedNode<T> x = this.first; x != null && numberOfNodes <= this.size; x = x.getNext()) {
                numberOfNodes++;
            }
            if (numberOfNodes != this.size) return false;

            LinkedNode<T> lastNode = this.first;
            while (lastNode.getNext() != null) {
                lastNode = lastNode.getNext();
            }
            if (this.last != lastNode) return false;
        }

        return true;
    }
}
