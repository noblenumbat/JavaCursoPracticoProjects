package org.jomaveger.bookexamples.chapter2;

public class Counter {

    private Integer count;

    public Counter() {
        this.count = 0;
    }

    public Integer currentCount() {
        return this.count;
    }

    public void incrementCount() {
        this.count = this.count + 1;
    }

    public void reset() {
        this.count = 0;
    }
}
