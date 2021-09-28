package org.jomaveger.bookexamples.chapter4;

import static org.junit.jupiter.api.Assertions.*;

import org.jomaveger.lang.dbc.exceptions.ContractViolationException;
import org.junit.jupiter.api.*;

import java.math.BigInteger;

public class RecursionTest {

	@Test
	public void testFactorialRecursive0() {
		assertEquals(BigInteger.valueOf(1), Recursion.factorialRecursive(0));
	}
	
	@Test
	public void testFactorialRecursiveMinus1() {
		Assertions.assertThrows(ContractViolationException.class, () -> {
			Recursion.factorialRecursive(-1);
		});
	}

	@Test
	public void testFactorialRecursive5() {
		assertEquals(BigInteger.valueOf(120), Recursion.factorialRecursive(5));
	}
	
	@Test
	public void testGcd() {
		assertEquals(Integer.valueOf(6), Recursion.gcd(12, 18));
	}
	
	@Test
	public void testGcdNegative() {
		Assertions.assertThrows(ContractViolationException.class, () -> {
			Recursion.gcd(-1, 3);
		});
	}
	
	@Test
	public void testFibonacci() {
		assertEquals(BigInteger.valueOf(55), Recursion.fibonacci(10));
	}
	
	@Test
	public void testFibonacciNegative() {
		Assertions.assertThrows(ContractViolationException.class, () -> {
			Recursion.fibonacci(-1);
		});
	}
	
	@Test
	public void testBinarySearch1() {
		int[] array = {5, 7, 9, 13, 15};
		SearchResult result = Recursion.binarySearch(array, 5);
		assertEquals(0, result.index);
		assertEquals(true, result.found);
	}
	
	@Test
	public void testBinarySearchPreconditionError() {
		Assertions.assertThrows(ContractViolationException.class, () -> {
			int[] array = {5, 7, 25, 13, 15};
			Recursion.binarySearch(array, 5);
		});
	}
	
	@Test
	public void testBinarySearch2() {
		int[] array = {5, 7, 9, 13, 15};
		SearchResult result = Recursion.binarySearch(array, 15);
		assertEquals(4, result.index);
		assertEquals(true, result.found);
	}
	
	@Test
	public void testBinarySearch3() {
		int[] array = {5, 7, 9, 13, 15};
		SearchResult result = Recursion.binarySearch(array, 8);
		assertEquals(2, result.index);
		assertEquals(false, result.found);
	}
}
