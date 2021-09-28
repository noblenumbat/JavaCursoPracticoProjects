package org.jomaveger.bookexamples.chapter6;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TripleTest {

	@Test
	public void testTriple() {
		assertEquals((Integer) 6, new Triple().apply(2));
	}

}
