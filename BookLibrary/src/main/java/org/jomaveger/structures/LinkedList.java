package org.jomaveger.structures;

import java.io.Serializable;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.jomaveger.lang.DeepCloneable;
import org.jomaveger.lang.dbc.Contract;

public class LinkedList<T> implements IList<T>, Serializable {

    private Integer size;
    private Integer modCount;
    private DoublyLinkedNode<T> head;
    private DoublyLinkedNode<T> tail;

    public LinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
        this.modCount = 0;
        
        Contract.ensure(isEmpty());
        Contract.invariant(checkInvariant());
    }

    @Override
    public void addFirst(final T elem) {
    	Contract.invariant(checkInvariant());
    	Contract.require(elem != null);
    	int oldSize = size();
    	
        this.head = new DoublyLinkedNode<T>(elem, this.head, null);
        if (this.tail == null)
            this.tail = this.head;
        this.size++;
        this.modCount++;
        
        Contract.ensure((getFirst() == elem) && (size() == (oldSize + 1)));
        Contract.invariant(checkInvariant());
    }

    @Override
    public void addLast(final T elem) {
    	Contract.invariant(checkInvariant());
    	Contract.require(elem != null);
    	int oldSize = size();
    	
        this.tail = new DoublyLinkedNode<T>(elem, null, this.tail);
        if (this.head == null)
            this.head = this.tail;
        this.size++;
        this.modCount++;
        
        Contract.ensure((getLast() == elem) && (size() == (oldSize + 1)));
        Contract.invariant(checkInvariant());
    }

    @Override
    public T getFirst() {
    	Contract.invariant(checkInvariant());
    	Contract.require(!isEmpty());
    	int oldSize = size();
    	
    	T elem = this.head.getElem();
    	
    	Contract.ensure((elem == get(0)) && (size() == oldSize));
    	Contract.invariant(checkInvariant());
        return elem;
    }

    @Override
    public T getLast() {
    	Contract.invariant(checkInvariant());
    	Contract.require(!isEmpty());
    	int oldSize = size();
    	
    	T elem = this.tail.getElem();
    	
    	Contract.ensure((elem == get(size() - 1)) && (size() == oldSize));
    	Contract.invariant(checkInvariant());
        return elem;
    }

    @Override
    public void removeFirst() {
    	Contract.invariant(checkInvariant());
    	Contract.require(!isEmpty());
    	int oldSize = size();
    	
        DoublyLinkedNode<T> temp = this.head;
        this.head = this.head.getNext();
        if (this.head != null) {
            this.head.setPrevious(null);
        } else {
            this.tail = null;
        }
        temp.setNext(null);
        this.size--;
        this.modCount++;
        
        Contract.ensure(size() == oldSize - 1);
    	Contract.invariant(checkInvariant());
    }

    @Override
    public void removeLast() {
    	Contract.invariant(checkInvariant());
    	Contract.require(!isEmpty());
    	int oldSize = size();
    	
        DoublyLinkedNode<T> temp = this.tail;
        this.tail = this.tail.getPrevious();
        if (this.tail == null) {
            this.head = null;
        } else {
            this.tail.setNext(null);
        }
        this.size--;
        this.modCount++;
        
        Contract.ensure(size() == oldSize - 1);
    	Contract.invariant(checkInvariant());
    }

    @Override
    public Boolean contains(final T elem) {
    	Contract.invariant(checkInvariant());
    	Contract.require(elem != null);
    	int oldSize = size();
    	
        DoublyLinkedNode<T> finger = this.head;
        while ((finger != null) && (!finger.getElem().equals(elem))) {
            finger = finger.getNext();
        }
        boolean condition = finger != null;
        
        Contract.ensure(size() == oldSize);
    	Contract.invariant(checkInvariant());
        return condition;
    }

    @Override
    public void remove(final T elem) {
    	Contract.invariant(checkInvariant());
    	Contract.require(elem != null);
    	int oldSize = size();
    	
    	DoublyLinkedNode<T> finger = this.head;
        while (finger != null && !finger.getElem().equals(elem)) {
            finger = finger.getNext();
        }
        if (finger != null) {
            if (finger.getPrevious() != null) {
                finger.getPrevious().setNext(finger.getNext());
            } else {
                this.head = finger.getNext();
            }
            if (finger.getNext() != null) {
                finger.getNext().setPrevious(finger.getPrevious());
            } else {
                this.tail = finger.getPrevious();
            }
            this.size--;
            this.modCount++;
        }
        
        Contract.ensure(!contains(elem) || size() == (oldSize - 1));
    	Contract.invariant(checkInvariant());
    }

    @Override
    public void clear() {
    	Contract.invariant(checkInvariant());
    	
        this.head = null;
        this.tail = null;
        this.size = 0;
        this.modCount++;
        
        Contract.ensure(isEmpty());
    	Contract.invariant(checkInvariant());
    }

    @Override
    public T get(Integer index) {
    	Contract.invariant(checkInvariant());
    	Contract.require(!isEmpty() && (index >= 0) && (index <= (size() - 1)));
    	int oldSize = size();
    	
        DoublyLinkedNode<T> finger = this.head;
        while (index > 0) {
            finger = finger.getNext();
            index--;
        }
        T elem = finger.getElem();
        
        Contract.ensure((elem != null) && (size() == oldSize));
    	Contract.invariant(checkInvariant());
        return elem;
    }

    @Override
    public void set(Integer index, final T elem) {
    	Contract.invariant(checkInvariant());
    	Contract.require(!isEmpty() && (elem != null) && (index >= 0) && (index <= (size() - 1)));
    	int oldSize = size();
    	
        DoublyLinkedNode<T> finger = this.head;
        while (index > 0) {
            finger = finger.getNext();
            index--;
        }
        finger.setElem(elem);
        
        Contract.ensure(size() == oldSize);
    	Contract.invariant(checkInvariant());
    }

    @Override
    public void add(Integer index, final T elem) {
    	Contract.invariant(checkInvariant());
    	Contract.require((elem != null) && (index >= 0) && (index <= size()));
    	int oldSize = size();
    	
        if (index == 0)
            this.addFirst(elem);
        else if (index == this.size())
            this.addLast(elem);
        else {
            DoublyLinkedNode<T> before = null;
            DoublyLinkedNode<T> after = this.head;

            while (index > 0) {
                before = after;
                after = after.getNext();
                index--;
            }

            DoublyLinkedNode<T> current =
                    new DoublyLinkedNode<T>(elem, after, before);
            this.size++;
            this.modCount++;

            before.setNext(current);
            after.setPrevious(current);
        }
        
        Contract.ensure((size() == (oldSize + 1)) && (contains(elem) == true));
    	Contract.invariant(checkInvariant());
    }

    @Override
    public void remove(Integer index) {
    	Contract.invariant(checkInvariant());
    	Contract.require(!isEmpty() && (index >= 0) && (index <= (size() - 1)));
    	int oldSize = size();

        if (index == 0)
            this.removeFirst();
        else if (index == this.size() - 1)
            this.removeLast();
        else {
            DoublyLinkedNode<T> previous = null;
            DoublyLinkedNode<T> finger = this.head;

            while (index > 0) {
                previous = finger;
                finger = finger.getNext();
                index--;
            }

            previous.setNext(finger.getNext());
            finger.getNext().setPrevious(previous);
            this.size--;
            this.modCount++;
        }
        
        Contract.ensure(size() == (oldSize - 1));
    	Contract.invariant(checkInvariant());
    }

    @Override
    public Integer indexOf(final T elem) {
    	Contract.invariant(checkInvariant());
    	Contract.require(elem != null);
    	int oldSize = size();
    	
        Integer i = 0;
        DoublyLinkedNode<T> finger = head;
        Integer index = 0;

        while (finger != null
                && !finger.getElem().equals(elem)) {
            finger = finger.getNext();
            i++;
        }

        if (finger == null) {
            index = -1;
        } else {
            index = i;
        }
        
        Contract.ensure((size() == oldSize) && ((index == -1) || (contains(elem))));
    	Contract.invariant(checkInvariant());
        return index;
    }

    @Override
    public Integer lastIndexOf(final T elem) {
    	Contract.invariant(checkInvariant());
    	Contract.require(elem != null);
    	int oldSize = size();
    	
        Integer i = this.size() - 1;
        DoublyLinkedNode<T> finger = this.tail;
        Integer index = 0;

        while (finger != null &&
                !finger.getElem().equals(elem)) {
            finger = finger.getPrevious();
            i--;
        }

        if (finger == null) {
            index = -1;
        } else {
            index = i;
        }
        
        Contract.ensure((size() == oldSize) && ((index == -1) || (contains(elem))
        		&& ((index == -1) || (index >= indexOf(elem)))));
    	Contract.invariant(checkInvariant());
        return index;
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
    public IList<T> deepCopy() {
    	Contract.invariant(checkInvariant());
    	IList<T> deepCopy;
        try {
        	deepCopy = DeepCloneable.deepCopy(this);
        } catch (Exception e) {
        	deepCopy = new LinkedList<>();
        }
        Contract.ensure(deepCopy.equals(this) || deepCopy.isEmpty());
        Contract.invariant(checkInvariant());
        return deepCopy;
    }

    @Override
    public Iterator<T> iterator() {
        return new LinkedListIterator();
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append(this.getClass().getName() + "[");

        for (T elem: this) {
            string.append(elem).append(", ");
        }

        string.append("]");
        return string.toString();
    }

    @Override
    public boolean equals(Object otherObject) {
        if (this == otherObject) return true;

        if (otherObject == null
                || this.getClass() != otherObject.getClass())
            return false;

        LinkedList<T> that = (LinkedList<T>) otherObject;

        if (!Objects.equals(this.size, that.size))
            return false;

        DoublyLinkedNode<T> tempOther = that.head;

        for (T elem: this) {

            if (!Objects.equals(elem, tempOther.getElem()))
                return false;

            tempOther = tempOther.getNext();
        }

        return true;
    }

    @Override
    public int hashCode() {
        Object[] array = new Object[this.size + 1];
        array[0] = this.size;

        Integer i = 1;
        for (T elem: this) {
            array[i] = elem;
            i++;
        }

        return Objects.hash(array);
    }
    
    private boolean checkInvariant() {
        if (this.size < 0) {
            return false;
        }
        if (this.size == 0) {
            if (this.head != null) return false;
            if (this.tail != null) return false;
        }
        else if (this.size == 1) {
            if (this.head == null)     	return false;
            if (this.head != this.tail) return false;
        }
        else {
            if (this.head == null)      		 return false;
            if (this.tail == null) 				 return false;
            if (this.head.getPrevious() != null) return false;
            if (this.tail.getNext() != null) 	 return false;
        }

        int numberOfNodes = 0;
        for (DoublyLinkedNode<T> x = this.head; x != null && numberOfNodes <= this.size; x = x.getNext()) {
            numberOfNodes++;
        }
        if (numberOfNodes != this.size) return false;

        return true;
    }
    
    private class LinkedListIterator implements Iterator<T> {

        private DoublyLinkedNode<T> current;
        private DoublyLinkedNode<T> lastVisited;
        private T lastElementReturned;
        private Integer expectedModCount;
        private Integer index;

        public LinkedListIterator() {
            this.current = LinkedList.this.head;
            this.lastVisited = null;
            this.expectedModCount = LinkedList.this.modCount;
            this.index = 0;
        }

        @Override
        public boolean hasNext() {
            if (this.expectedModCount != LinkedList.this.modCount)
                throw new ConcurrentModificationException();

            return this.index < LinkedList.this.size();
        }

        @Override
        public T next() {
            if (!this.hasNext())
                throw new NoSuchElementException();

            this.lastElementReturned = this.current.getElem();
            this.lastVisited = this.current;
            this.current = this.current.getNext();
            this.index++;
            return this.lastElementReturned;
        }

        @Override
        public void remove() {
            if (this.expectedModCount != LinkedList.this.modCount)
                throw new ConcurrentModificationException();
            if (this.lastVisited == null)
                throw new IllegalStateException();

            LinkedList.this.remove(this.lastElementReturned);
            this.lastVisited = null;
            this.index--;
            this.expectedModCount++;
        }
    }
}
