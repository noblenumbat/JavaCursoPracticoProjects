package org.jomaveger.bookexamples.chapter2;

public class App1 implements A {

    @Override
    public void doSth() {
        System.out.println("inside App1");
    }

    public static void main(String[] args) {
        new App1().doSth();
    }
}