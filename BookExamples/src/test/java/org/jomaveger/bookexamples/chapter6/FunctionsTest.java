package org.jomaveger.bookexamples.chapter6;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import java.util.function.Function;

import org.jomaveger.functional.tuples.*;

public class FunctionsTest {

	@Test
    public void testFunctionsTreble() {
        assertEquals((Integer) 6, Functions.treble.apply(2));
    }
	
	@Test
    public void testFunctionsCompose() {
		Function<Integer, Integer> suma3 = t -> t + 3;
		Function<Integer, Integer> beforeFunction = Functions.treble.compose(suma3);
				
        assertEquals((Integer) 15, beforeFunction.apply(2));
    }
	
	@Test
    public void testFunctionsAndThen() {
		Function<Integer, Integer> afterFunction = Functions.treble.andThen(t -> t + 3);
				
        assertEquals((Integer) 9, afterFunction.apply(2));
    }
	
	@Test
	public void testFunctionsIdentity() {
	    assertEquals((Integer)9, Function.identity().apply(9));
	}
	
	@Test
    public void testFunctionsConverter() {
        assertEquals((Double) 16.09, Functions.converter.apply(1.609, 10.0));
    }
	
	@Test
    public void testFunctionsTransformer() {
        assertEquals((Double) 16.09, Functions.transformer.apply(Tuple2.from(1.609, 10.0)));
    }
	
	@Test
    public void testFunctionsSum3() {
        assertEquals((Integer) 12, Functions.sum3.apply(7, 2, 3));
    }
	
	@Test
    public void testFunctionsSum3t() {
        assertEquals((Integer) 12, Functions.sum3t.apply(Tuple3.from(7, 2, 3)));
    }
	
	@Test
	public void testFunctionsSum2c() {
	    assertEquals((Integer) 3, Functions.sum2c.apply(1).apply(2));
	}
	
	@Test
	public void testFunctionsSum3c() {
	    assertEquals((Integer) 9, Functions.sum3c.apply(2).apply(3).apply(4));
	}
}
