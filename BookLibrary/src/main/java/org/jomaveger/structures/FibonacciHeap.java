package org.jomaveger.structures;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.jomaveger.lang.DeepCloneable;
import org.jomaveger.lang.dbc.Contract;

public class FibonacciHeap<T> implements IPriorityQueue<T>, Serializable {
	
	private Entry<T> min = null;
	
	private int size = 0;
	
	public FibonacciHeap() {
		Contract.ensure(isEmpty());
        Contract.invariant(checkInvariant());
	}

	@Override
	public Entry<T> enqueue(T value, double priority) {
		Contract.require(value != null && checkPriority(priority));
		
		Entry<T> result = new Entry<T>(value, priority);

        min = FibonacciHeap.mergeLists(min, result);

        ++size;

        return result;
	}

	private static <T> Entry<T> mergeLists(Entry<T> one, Entry<T> two) {
		if (one == null && two == null) {
            return null;
        }
        else if (one != null && two == null) {
            return one;
        }
        else if (one == null && two != null) {
            return two;
        }
        else {
        	Entry<T> oneNext = one.getNext();
            one.setNext(two.getNext());
            one.getNext().setPrev(one);
            two.setNext(oneNext);
            two.getNext().setPrev(two);

            return (Double.compare(one.getPriority(), two.getPriority()) < 0)? one : two;            
        }
	}

	private boolean checkPriority(double priority) {
		if (Double.isNaN(priority)) return false;
		else return true;
	}

	@Override
	public Entry<T> min() {
		Contract.invariant(checkInvariant());
    	Contract.require(!isEmpty());
    	
    	Entry<T> m = min;
    	
    	Contract.ensure(m != null);
    	Contract.invariant(checkInvariant());
    	return m;
	}

	@Override
	public boolean isEmpty() {
		Contract.invariant(checkInvariant());
		boolean isEmpty = (min == null);
		
		Contract.ensure(isEmpty == (min == null));
    	Contract.invariant(checkInvariant());
        return isEmpty;
	}

	@Override
	public int size() {
		Contract.invariant(checkInvariant());
    	
        int size = this.size;
        
        Contract.ensure(this.size >= 0);
        Contract.invariant(checkInvariant());
        return size;
	}
	
	public static <T> FibonacciHeap<T> merge(FibonacciHeap<T> one, FibonacciHeap<T> two) {
		Contract.require(one != null && two != null);
        FibonacciHeap<T> result = new FibonacciHeap<T>();

        result.min = mergeLists(one.min, two.min);

        result.size = one.size + two.size;

        one.size = two.size = 0;
        one.min  = null;
        two.min  = null;

        return result;
    }

	@Override
	public Entry<T> dequeueMin() {
		Contract.invariant(checkInvariant());
    	Contract.require(!isEmpty());
    	
    	--size;

        Entry<T> minElem = min;
        
        if (min.getNext() == min) {
            min = null;
        }
        else {
            min.getPrev().setNext(min.getNext());
            min.getNext().setPrev(min.getPrev());
            min = min.getNext();
        }
        
        if (minElem.getChild() != null) {
            Entry<?> curr = minElem.getChild();
            do {
            	
                curr.setParent(null);
                curr = curr.getNext();
                
            } while (curr != minElem.getChild());
        }
        
        min = mergeLists(min, minElem.getChild());

        if (min == null) return minElem;
        
        List<Entry<T>> treeTable = new ArrayList<Entry<T>>();
        List<Entry<T>> toVisit = new ArrayList<Entry<T>>();
        
        for (Entry<T> curr = min; toVisit.isEmpty() || toVisit.get(0) != curr; curr = curr.getNext())
            toVisit.add(curr);
        
        for (Entry<T> curr: toVisit) {
            
            while (true) {
            
                while (curr.getDegree() >= treeTable.size())
                    treeTable.add(null);

                if (treeTable.get(curr.getDegree()) == null) {
                    treeTable.set(curr.getDegree(), curr);
                    break;
                }

                Entry<T> other = treeTable.get(curr.getDegree());
                treeTable.set(curr.getDegree(), null);

                Entry<T> min = (other.getPriority() < curr.getPriority())? other : curr;
                Entry<T> max = (other.getPriority() < curr.getPriority())? curr  : other;

                max.getNext().setPrev(max.getPrev());
                max.getPrev().setNext(max.getNext());

                max.setNext(max); max.setPrev(max);
                min.setChild(mergeLists(min.getChild(), max));
                
                max.setParent(min);

                max.setIsMarked(false);

                min.setDegree(min.getDegree() + 1);

                curr = min;
            }

            if (Double.compare(curr.getPriority(), min.getPriority()) <= 0) min = curr;
        }
    	
    	Contract.ensure(minElem != null);
    	Contract.invariant(checkInvariant());
    	return minElem;
	}

	@Override
	public void decreaseKey(Entry<T> entry, double newPriority) {
		Contract.invariant(checkInvariant());
		int oldSize = size();
		Contract.require(entry != null && checkPriority(newPriority) && checkNewPriority(entry, newPriority));
		
        decreaseKeyUnchecked(entry, newPriority);
        Contract.ensure(size() == oldSize);
        Contract.invariant(checkInvariant());
	}

	private void decreaseKeyUnchecked(Entry<T> entry, double newPriority) {
		entry.setPriority(newPriority);
		
        if (entry.getParent() != null && (Double.compare(entry.getPriority(), entry.getParent().getPriority()) <= 0))
            cutNode(entry);

        if (Double.compare(entry.getPriority(), min.getPriority()) <= 0)
            min = entry;
	}

	private void cutNode(Entry<T> entry) {
        entry.setIsMarked(false);

        if (entry.getParent() == null) return;

        if (entry.getNext() != entry) {
            entry.getNext().setPrev(entry.getPrev());
            entry.getPrev().setNext(entry.getNext());
        }

        if (entry.getParent().getChild() == entry) {
        	
            if (entry.getNext() != entry) {
                entry.getParent().setChild(entry.getNext());
            }
            else {
                entry.getParent().setChild(null);
            }
        }

        entry.getParent().setDegree(entry.getParent().getDegree() - 1);

        entry.setPrev(entry); entry.setNext(entry);
        min = mergeLists(min, entry);

        if (entry.getParent().isIsMarked())
            cutNode(entry.getParent());
        else
            entry.getParent().setIsMarked(true);
        
        entry.setParent(null);
	}

	private boolean checkNewPriority(Entry<T> entry, double newPriority) {
        if (Double.compare(newPriority, entry.getPriority()) > 0) return false;
        else return true;
	}

	@Override
	public void delete(Entry<T> entry) {
		Contract.require(entry != null);
		Contract.invariant(checkInvariant());
		int oldSize = size();
		
        decreaseKeyUnchecked(entry, Double.NEGATIVE_INFINITY);

        dequeueMin();
        
        Contract.ensure(size() == oldSize - 1);
        Contract.invariant(checkInvariant());
	}

	@Override
	public IPriorityQueue<T> deepCopy() {
		Contract.invariant(checkInvariant());
		IPriorityQueue<T> deepCopy;
        try {
        	deepCopy = DeepCloneable.deepCopy(this);
        } catch (Exception e) {
        	deepCopy = new FibonacciHeap<>();
        }
        Contract.ensure(deepCopy.equals(this) || deepCopy.isEmpty());
        Contract.invariant(checkInvariant());
        return deepCopy;
	}

	private boolean checkInvariant() {
		return size >= 0;
	}
}
