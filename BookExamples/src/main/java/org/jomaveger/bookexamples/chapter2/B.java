package org.jomaveger.bookexamples.chapter2;

public interface B {
    default void doSth(){
        System.out.println("inside B");
    }
}
