package org.jomaveger.bookexamples.chapter2;

public class OuterClass4 {
    int outerVariable = 100;
    private static int staticOuterVariable = 200;
 
    public static class StaticMemberClass {
        int innerVariable = 20;
         
        int getSum(int parameter) {
            return innerVariable + staticOuterVariable + parameter;
        }       
    }
     
    public static void main(String[] args) {
        OuterClass4 outer = new OuterClass4();
        StaticMemberClass inner = new StaticMemberClass(); 
        System.out.println(inner.getSum(3));
        outer.run();
    }
     
    void run() {
        StaticMemberClass localInner = new StaticMemberClass();
        System.out.println(localInner.getSum(5));
    }
}