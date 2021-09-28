package org.jomaveger.bookexamples.chapter4;

import java.math.BigInteger;

import org.jomaveger.lang.dbc.Contract;

public final class Recursion {
	
	public Recursion() {		
	}
	
	public static BigInteger factorialRecursive(Integer x) {
		Contract.require(x >= 0);
		if (x == 0) {
			return BigInteger.ONE;
		} else {
			return factorialRecursive(x - 1).multiply(BigInteger.valueOf(x));
		}
	}
	
	public static Integer gcd(Integer x, Integer y) {
		Contract.require(x > 0 && y > 0);
		Integer m = 1;
		if (x == y) {
			m = x;
		}
		else if (x > y) {
			m = gcd(x - y, y);
		}
		else if (x < y) {
			m = gcd(x, y - x); 
		}
		return m;
	}
	
	public static BigInteger fibonacci(Integer x) {
		Contract.require(x >= 0);
		if (x == 0 || x == 1) {
			return BigInteger.valueOf(x);
		}
		return fibonacci(x - 1).add(fibonacci(x - 2));
	}
	
	public static SearchResult binarySearch(int[] v, int e) {
		Contract.require(Loop.isSorted(v));
		
		SearchResult result = gBinarySearch(v, e, 0, v.length);
		
		Contract.ensure((!result.found || (result.index >= 0 && (result.index <= v.length - 1) && (e == v[result.index])))				
				&& (result.found || (result.index >= 0 && (result.index <= v.length) && checkOrdering(v, e, result.index))));
		
		return result;
	}
	
	private static boolean checkOrdering(int[] v, int e, int index) {
		boolean ordering = true;
		int j = 0;
		while (j <= index - 1 && ordering) {
			ordering = v[j] < e;
			j++;
		}
		j = index;
		ordering = true;
		while (j < v.length && ordering) {
			ordering = e < v[j];
			j++;
		}
		return ordering;
	}

	public static SearchResult gBinarySearch(int[] v, int e, int c, int f) {
		SearchResult result = new SearchResult();
		
		if (c > f) {
			result.found = false;
			result.index = c;
		}
		if (c <= f) {
			int m = (c + f) / 2;
			if (e < v[m]) return gBinarySearch(v, e, c, m - 1);
			if (e == v[m]) {
				result.found = true;
				result.index = m;
			}
			if (e > v[m]) return gBinarySearch(v, e, m + 1, f);
		}
			
		return result;
	}
	
	public static Integer gcdIter(Integer x, Integer y) {
		Integer m = 1;
		while (x != y) {
			if (x > y) {
				x = x - y;
			} else if (x < y) {
				y = y - x;
			}
		}
		m = x;
		return m;
	}

	public static BigInteger acuFact(BigInteger a, Integer n) {
		BigInteger r = BigInteger.ONE; 
		if (n == 0) {
			r = a;
		} else if (n > 0) {
			r = acuFact(a.multiply(BigInteger.valueOf(n)), n - 1);
		}
		return r;
	}
	
	public static BigInteger fact(Integer n) {
		return Recursion.acuFact(BigInteger.ONE, n);
	}
	
	public static BigInteger factIter(Integer n) {
		 BigInteger r;
		 BigInteger a = BigInteger.ONE;
		 
		 while (n > 0) {
			 a = a.multiply(BigInteger.valueOf(n));
			 n = n - 1;
		 }
		 r = a;
		 return r;
	}
	
	public static BigInteger acuFib(BigInteger acc1, BigInteger acc2, BigInteger x) {
		if (x.equals(BigInteger.ZERO)) {
			return BigInteger.ZERO;
		} else if (x.equals(BigInteger.ONE)) {
			return acc1.add(acc2);
		} else {
			return acuFib(acc2, acc1.add(acc2), x.subtract(BigInteger.ONE));
		}
	}
	
	public static BigInteger fib(Integer x) {
		return acuFib(BigInteger.ONE, BigInteger.ZERO, BigInteger.valueOf(x));
	}
}
