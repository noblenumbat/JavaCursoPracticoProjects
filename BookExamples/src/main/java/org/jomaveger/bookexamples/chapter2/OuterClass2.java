package org.jomaveger.bookexamples.chapter2;

public class OuterClass2 {
    private int outerVariable = 10000;
    private static int staticOuterVariable = 2000;
     
    public static void main(String[] args) {
        OuterClass2 outer = new OuterClass2();
        System.out.println(outer.run());
    }
     
    Object run() {
        int localVariable = 666;
        final int finalLocalVariable = 300;
         
        class LocalClass {
            int innerVariable = 40;
             
            int getSum(int parameter) {
                return outerVariable + staticOuterVariable + localVariable +
                       finalLocalVariable + innerVariable + parameter;
            }       
        }
        LocalClass local = new LocalClass();
        System.out.println(local.getSum(5));
        return local;
    }
}