package org.jomaveger.structures;

public interface IPriorityQueue<T> {
	
	Entry<T> enqueue(T value, double priority);
	
	Entry<T> min();
	
	boolean isEmpty();
	
	int size();
	
	Entry<T> dequeueMin();
	
	void decreaseKey(Entry<T> entry, double newPriority);
	
	void delete(Entry<T> entry);
	
	IPriorityQueue<T> deepCopy();
}
