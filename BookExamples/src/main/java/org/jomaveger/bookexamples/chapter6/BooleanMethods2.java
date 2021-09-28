package org.jomaveger.bookexamples.chapter6;

public class BooleanMethods2 {
	
	public static void main(String[] args) {
		System.out.println(getFirst() || getSecond());
		System.out.println(or(getFirst(), getSecond()));
	}

	public static boolean getFirst() {
		return true;
	}

	public static boolean getSecond() {
		throw new IllegalStateException();
	}

	public static boolean or(boolean a, boolean b) {
		return a ? true : b ? true : false;
	}

	public static boolean and(boolean a, boolean b) {
		return a ? b ? true : false : false;
	}
}