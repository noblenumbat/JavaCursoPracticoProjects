package org.jomaveger.bookexamples.chapter2;

public interface A {
    default void doSth(){
        System.out.println("inside A");
    }
}
