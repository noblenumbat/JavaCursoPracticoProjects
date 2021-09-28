package org.jomaveger.bookexamples.chapter4;

import static org.junit.jupiter.api.Assertions.*;

import org.jomaveger.lang.dbc.exceptions.ContractViolationException;
import org.junit.jupiter.api.*;

public class LoopTest {

	@Test
	public void testIsSortedTrue() {
		int[] array = {5, 7, 9, 13, 15};
		assertTrue(Loop.isSorted(array));
	}
	
	@Test
	public void testIsSortedFalse() {
		int[] array = {5, 7, 25, 13, 15};
		assertFalse(Loop.isSorted(array));
	}

	@Test
	public void testIsSortedPreconditionError() {
		Assertions.assertThrows(ContractViolationException.class, () -> {
			int[] array = null;
			Loop.isSorted(array);
		});
	}
	
	@Test
	public void testBinarySearch1() {
		int[] array = {5, 7, 9, 13, 15};
		assertEquals(0, Loop.binarySearch(array, 5));
	}
	
	@Test
	public void testBinarySearchPreconditionError() {
		Assertions.assertThrows(ContractViolationException.class, () -> {
			int[] array = {5, 7, 25, 13, 15};
			Loop.binarySearch(array, 5);
		});
	}
	
	@Test
	public void testBinarySearch2() {
		int[] array = {5, 7, 9, 13, 15};
		assertEquals(4, Loop.binarySearch(array, 15));
	}
	
	@Test
	public void testBinarySearch3() {
		int[] array = {5, 7, 9, 13, 15};
		assertEquals(1, Loop.binarySearch(array, 8));
	}
}
