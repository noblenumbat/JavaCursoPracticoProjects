package org.jomaveger.bookexamples.chapter2;

public class OuterClass {
    private int outerVariable = 100;
 
    public class InnerClass {
        int innerVariable = 20;
         
        int getSum(int parameter) {
            return innerVariable +  outerVariable + parameter;
        }       
    }
     
    public static void main(String[] args) {
        OuterClass outer = new OuterClass();
        InnerClass inner = outer.new InnerClass(); 
        System.out.println(inner.getSum(3));
        outer.run();
    }
     
    void run() {
        InnerClass localInner = new InnerClass();
        System.out.println(localInner.getSum(5));
    }
}
