package org.jomaveger.bookexamples.chapter2;

public interface C extends A {
    default void doSth(){
        System.out.println("inside C");
    }
}