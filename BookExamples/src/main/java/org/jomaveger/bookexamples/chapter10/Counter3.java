package org.jomaveger.bookexamples.chapter10;

public class Counter3 {
    
    private long value = 0;
    private final Object lock = new Object(); 

    public long getValue() {
        synchronized(lock) {
            return value;   
        }
    }

    public long increment() {
        synchronized(lock) {
            return ++value;    
        }
    }
}
