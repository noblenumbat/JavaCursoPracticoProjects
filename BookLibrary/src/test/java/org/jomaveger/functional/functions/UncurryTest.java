package org.jomaveger.functional.functions;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import java.util.function.BiFunction;
import java.util.function.Function;

import org.jomaveger.functional.tuples.*;

public class UncurryTest {

	@Test
	public void testFunctionsUncurry2f() {
		Function<Integer, Function<Integer, Integer>> curriedSum = x -> y -> x + y;
		BiFunction<Integer, Integer, Integer> increment = Uncurry.uncurry2f(curriedSum);
	    assertEquals((Integer) 5, increment.apply(4, 1));
	}
	
	@Test
	public void testFunctionsUncurry2t() {
		Function<Double, Function<Double, Double>> curriedTransformer = conversionRate -> value -> conversionRate * value;
		Function<Tuple2<Double, Double>, Double> transform = Uncurry.uncurry2t(curriedTransformer);
	    assertEquals((Double) 16.09, transform.apply(Tuple2.from(1.609, 10.0)));
	}
}
