package org.jomaveger.bookexamples.chapter6;

import java.util.function.BiFunction;
import java.util.function.Function;

import org.jomaveger.functional.functions.TriFunction;
import org.jomaveger.functional.tuples.*;

public interface Functions {

	Function<Integer, Integer> treble = new Function<Integer, Integer>() {
		@Override
		public Integer apply(Integer x) {
			return x * 3;
		}
	};
	
	Function<Integer, Integer> triple = x -> x * 3;
	
	Function<Integer, Integer> square = x -> x * 2;
	
	BiFunction<Double, Double, Double> converter = (conversionRate, value) -> conversionRate * value;
	
	Function<Tuple2<Double, Double>, Double> transformer = p -> p._1 * p._2;
	
	TriFunction<Integer, Integer, Integer, Integer> sum3 = (a, b, c) -> a + b + c;
	
	Function<Tuple3<Integer, Integer, Integer>, Integer> sum3t = t -> t._1 + t._2 + t._3;
	
	BiFunction<Integer, Integer, Integer> sum2 = (x, y) -> x + y;
	
	Function<Integer, Function<Integer, Integer>> sum2c = x -> y -> x + y;
	
	Function<Integer, Function<Integer, Function<Integer, Integer>>> sum3c =
		    x -> y -> z -> x + y + z;
}
