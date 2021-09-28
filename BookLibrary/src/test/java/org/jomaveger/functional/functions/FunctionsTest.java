package org.jomaveger.functional.functions;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import java.util.function.Function;

public class FunctionsTest {
	
	@Test
	public void testFunctionsCompose() {
	    Function<Integer, Integer> triple = x -> x * 3;
	    Function<Integer, Integer> square = x -> x * x;
	    assertEquals((Integer)12,
	        Functions.<Integer, Integer, Integer>compose().apply(triple).apply(square).apply(2));
	}

	@Test
	public void testFunctionsAndThen() {
	    Function<Integer, Integer> triple = x -> x * 3;
	    Function<Integer, Integer> square = x -> x * x;
	    assertEquals((Integer)36,
	    		Functions.<Integer, Integer, Integer>andThen().apply(triple).apply(square).apply(2));
	}
}
