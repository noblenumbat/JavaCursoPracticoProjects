package org.jomaveger.bookexamples.chapter6;

import java.math.BigInteger;

import java.util.function.Function;

import org.jomaveger.functional.control.TailCall;

public final class FRecursion {
	
	public FRecursion() {		
	}	
	
	public final Function<Integer, Integer> factorial = 
			n -> n == 0 ? 1 : n * this.factorial.apply(n - 1);
			
	public static final Function<Integer, Integer> staticFactorial =
					n -> n == 0 ? 1 : n * FRecursion.staticFactorial.apply(n - 1);


	public static BigInteger tfact(Integer n) {
		return FRecursion.tacuFact(BigInteger.ONE, n).eval();
	}
	
	public static TailCall<BigInteger> tacuFact(BigInteger a, Integer n) {
		if (n == 0) {
			return TailCall.ret(a);
		} else {
			return TailCall.sus(() -> tacuFact(a.multiply(BigInteger.valueOf(n)), n - 1));
		}
	}
	
	public static BigInteger tfib(int x) {
		return tacuFib(BigInteger.ONE, BigInteger.ZERO, BigInteger.valueOf(x)).eval();
	}

	public static TailCall<BigInteger> tacuFib(BigInteger acc1, BigInteger acc2, BigInteger x) {
		if (x.equals(BigInteger.ZERO)) {
			return TailCall.ret(BigInteger.ZERO);
		} else if (x.equals(BigInteger.ONE)) {
			return TailCall.ret(acc1.add(acc2));
		} else {
			return TailCall.sus(() -> tacuFib(acc2, acc1.add(acc2), x.subtract(BigInteger.ONE)));
		}
	}
}
