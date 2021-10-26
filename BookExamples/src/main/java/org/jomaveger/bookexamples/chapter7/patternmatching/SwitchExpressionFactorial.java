package org.jomaveger.bookexamples.chapter7.patternmatching;

public class SwitchExpressionFactorial {

	public static void main(String[] args) {
		System.out.println("Factorial of 3 is " + factorial(3));
		System.out.println("Factorial of 5 is " + factorial(5));
    }
	
	public static int factorial(int n) {
		return switch (n) {
		case 0, 1 -> 1;
		case 2 -> 2;
		default -> factorial(n - 1) * n;
		};
	}
}

