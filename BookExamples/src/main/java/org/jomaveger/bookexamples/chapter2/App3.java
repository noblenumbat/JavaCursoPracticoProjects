package org.jomaveger.bookexamples.chapter2;

public class App3 implements B, A {

    @Override
    public void doSth() {
        B.super.doSth();
    }

    public static void main(String[] args) {
        new App3().doSth();
    }
}