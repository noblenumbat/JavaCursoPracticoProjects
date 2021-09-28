package org.jomaveger.bookexamples.chapter6;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigInteger;

import org.junit.jupiter.api.Test;

public class MemFactorialTest {

	@Test
	public void testFactorialMemoizer() {
		assertEquals(BigInteger.valueOf(120), MemFactorial.factorial(5));
	}

}
