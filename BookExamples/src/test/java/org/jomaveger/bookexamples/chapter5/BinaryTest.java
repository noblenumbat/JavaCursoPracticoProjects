package org.jomaveger.bookexamples.chapter5;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

public class BinaryTest {

	@Test
	public void testBin() {
		assertEquals(1010, Binary.binary(10));
	}

	@Test
	public void testItBin() {
		assertEquals(1010, Binary.itBinary(10));
	}
}
