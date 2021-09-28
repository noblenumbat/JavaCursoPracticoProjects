package org.jomaveger.bookexamples.chapter6;

import java.math.BigInteger;

import java.util.function.Function;

import org.jomaveger.functional.control.Memoizer;

public class MemFibonacci {

	private static final Function<Integer, BigInteger> CACHED = 
			Memoizer.memoize(MemFibonacci::uncached);
	 
    public static BigInteger fibonacci(Integer n) {
        return CACHED.apply(n);
    }
 
    private static BigInteger uncached(Integer x) {
    	if (x == 0 || x == 1) {
			return BigInteger.valueOf(x);
		}
		return fibonacci(x - 1).add(fibonacci(x - 2));
    }
}
