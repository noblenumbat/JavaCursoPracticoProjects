package org.jomaveger.functional.functions;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import java.util.function.BiFunction;
import java.util.function.Function;

import org.jomaveger.functional.tuples.*;

public class CurryTest {

	@Test
	public void testFunctionsCurry2f() {
		BiFunction<Integer, Integer, Integer> sum2 = (x, y) -> x + y;
		Function<Integer, Function<Integer, Integer>> curriedSum = Curry.curry2f(sum2);
		Function<Integer, Integer> increment = curriedSum.apply(1);
	    assertEquals((Integer) 5, increment.apply(4));
	}
	
	@Test
	public void testFunctionsCurry2t() {
		Function<Tuple2<Double, Double>, Double> transformer = p -> p._1 * p._2;
		Function<Double, Function<Double, Double>> curriedTransformer = Curry.curry2t(transformer);
		Function<Double, Double> transform = curriedTransformer.apply(1.609);
	    assertEquals((Double) 16.09, transform.apply(10.0));
	}

}
