package org.jomaveger.bookexamples.chapter6;

import java.math.BigInteger;

import java.util.function.Function;

import org.jomaveger.functional.control.Memoizer;

public class MemFactorial {

	private static final Function<Integer, BigInteger> CACHED = 
			Memoizer.memoize(MemFactorial::uncached);
	 
    public static BigInteger factorial(Integer n) {
        return CACHED.apply(n);
    }
 
    private static BigInteger uncached(Integer x) {
    	if (x == 0) {
			return BigInteger.ONE;
		} else {
			return factorial(x - 1).multiply(BigInteger.valueOf(x));
		}
    }
}
