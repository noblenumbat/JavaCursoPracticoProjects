package org.jomaveger.bookexamples.chapter6;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigInteger;

import org.junit.jupiter.api.Test;

public class MemFibonacciTest {

	@Test
	public void testFibonacciMemoizer() {
		assertEquals(BigInteger.valueOf(55), MemFibonacci.fibonacci(10));
	}

}
