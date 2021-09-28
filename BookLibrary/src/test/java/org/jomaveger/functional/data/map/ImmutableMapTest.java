package org.jomaveger.functional.data.map;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import org.jomaveger.functional.control.Result;
import org.jomaveger.functional.tuples.Tuple2;

public class ImmutableMapTest {

	@Test
	public void testAddContainsRemoveGet() {
		ImmutableMap<String, Integer> jedis = ImmutableMap.empty();
		jedis = jedis.add("Han", 100).add("Leia", 200).add("Luke", 300)
				.add("Anakin", 400).add("Aa", 2260).add("BB", 2260);
		
		assertTrue(jedis.contains("Han"));
		assertFalse(jedis.contains("Yoda"));
		
		jedis = jedis.remove("Aa");
		assertTrue(jedis.contains("BB"));
		assertFalse(jedis.contains("Aa"));
		
		Result<Tuple2<String, Integer>> result = jedis.get("Luke");
		Tuple2<String, Integer> defaultT = new Tuple2<>("", 0);
		assertEquals("Luke", result.getOrElse(defaultT)._1);
		assertEquals((Integer)300, result.getOrElse(defaultT)._2);
	}

}
