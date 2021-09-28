package org.jomaveger.functional.data;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.jomaveger.functional.tuples.Tuple2;

public class ImmutableListTest {

	@Test
    public void testEmpty() {
        ImmutableList<Integer> list = ImmutableList.empty();
        assertFalse(list.head().isSuccess());
        assertFalse(list.tail().isSuccess());
        assertEquals(list, list);
        assertEquals(list, ImmutableList.<Integer>empty());
        assertNotEquals(list, ImmutableList.cons(0, list));
    }
	
	@Test
    public void testCons() {
		ImmutableList<Integer> list = ImmutableList.of(3, 2, 1);
		ImmutableList<Integer> listP = ImmutableList.cons(0, list);
        assertEquals(list.length() + 1, listP.length());
        assertEquals((Integer)0, listP.head().getOrElse(1));
        assertEquals(list, listP.tail().getOrElse(ImmutableList.empty()));
        assertEquals(listP, list.cons(0));
    }

	@Test
    public void testToArray() {
		ImmutableList<Integer> list = ImmutableList.of(0, 1, 2);
		Integer[] array = new Integer[3];
		array[0] = 0; array[1] = 1; array[2] = 2;
		Integer[] arr = list.toArray(Integer.class);
		assertEquals(array.length, arr.length);
        for (int i = 0; i < array.length; i++) {
            assertEquals(array[i], arr[i]);
        }
    }
	
	@Test
    public void testIndex() {
        ImmutableList<Integer> l = ImmutableList.of(0, 1, 2, 3, 4);
        assertTrue(l.index(0).getOrElse(1) == 0);
        assertFalse(l.index(2).getOrElse(1) == 0);
        assertTrue(l.index(5).isEmpty());
        assertTrue(l.index(-1).isFailure());
        assertTrue(ImmutableList.<Integer>empty().index(1).isEmpty());
    }
	
	@Test
    public void testFromArray() {
		ImmutableList<Integer> list = ImmutableList.of(0, 1, 2, 3, 4);
        Integer[] array = list.toArray(Integer.class);
        ImmutableList<Integer> list1 = ImmutableList.of(array);
        assertEquals(list, list1);
    }
	
	@Test
    public void testFromList() {
		ImmutableList<Integer> list = ImmutableList.of(0, 1, 2, 3, 4);
		List<Integer> arrList = new ArrayList<>();
        list.forEach(arrList::add);
        ImmutableList<Integer> listP = ImmutableList.from(arrList);
        assertEquals(list, listP);
    }
	
	@Test
    public void testMap() {
		ImmutableList<Integer> list = ImmutableList.of(5, 6, 7, 8);
		Integer[] addArray = new Integer[list.length()];
        for (int i = 0; i < addArray.length; i++) {
            addArray[i] = list.index(i).getOrElse(-1) + 1;
        }
        assertEquals(list.map(x -> x + 1), ImmutableList.of(addArray));
    }
	
	@Test
    public void testFlatMap() {
		ImmutableList<Integer> list = ImmutableList.of(0, 1, 2, 3, 4, 5, 6, 7, 8);
		Integer[] dups = new Integer[list.length() * 2];
        for (int i = 0; i < dups.length / 2; i++) {
            dups[i * 2] = list.index(i).getOrElse(-1);
            dups[i * 2 + 1] = list.index(i).getOrElse(-1);
        }
        assertEquals(ImmutableList.of(dups), list.flatMap(x -> ImmutableList.of(x, x)));
    }
	
	@Test
    public void testInit() {
		ImmutableList<Integer> list = ImmutableList.of(0, 1, 2, 3, 4, 5, 6, 7, 8);
		ImmutableList<Integer> init = ImmutableList.of(0, 1, 2, 3, 4, 5, 6, 7);
		assertEquals(init, list.init().getOrElse(ImmutableList.empty()));
    }
	
	@Test
    public void testReverse() {
        ImmutableList<Integer> l = ImmutableList.empty();
        for (int i = 0; i < 10000; i++) {
            l = l.cons(i);
        }
        l = l.reverse();
        for (int i = 0; i < 10000; i++) {
            assertEquals((Integer)i, l.head().getOrElse(-1));
            l = l.tail().getOrElse(ImmutableList.empty());
        }
        assertTrue(l.equals(ImmutableList.empty()));
    }
	
	@Test
    public void testSpan() {
		ImmutableList<Integer> list = ImmutableList.of(5, 10, 15);
		int lengthA = 1; int lengthB = 2;
		Tuple2<ImmutableList<Integer>, ImmutableList<Integer>> s = list.span(i -> i < 10);
		assertEquals(s._1.length(), lengthA);
        assertEquals(s._2.length(), lengthB);
    }
	
	@Test
    public void testZipWith() {
		ImmutableList<Integer> list = ImmutableList.of(0, 1, 2, 3, 4, 5, 6, 7, 8);
		ImmutableList<Integer> integers = list.zipWith((a, b) -> 0, ImmutableList.<Integer>empty());
        assertEquals(0, integers.length());
        integers = list.zipWith((a, b) -> a + b, list);
        list.foldLeft(integers, (l, integer) -> {
            assertEquals(integer * 2, (int) l.head().getOrElse(3));
            return l.tail().getOrElse(ImmutableList.<Integer>empty());
        });
        if (list.isNotEmpty()) {
            ImmutableList<Integer> a = list.zipWith((x, y) -> x + y, list.tail().getOrElse(ImmutableList.<Integer>empty()));
            assertEquals(a.length(), list.length() - 1);
        }
    }
	
	@Test
    public void testCount() {
		ImmutableList<Integer> list = ImmutableList.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17);
        int count = list.count(i -> i > 15);
        assertEquals(2, count);
    }

    @Test
    public void testFilter() {
    	ImmutableList<Integer> list = ImmutableList.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17);
        ImmutableList<Integer> integers = list.filter(i -> i > 7);
        assertEquals(10, integers.length());
    }

    @Test
    public void testRemoveAll() {
    	ImmutableList<Integer> list = ImmutableList.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17);
        ImmutableList<Integer> integers = list.removeAll(i -> i <= 15);
        assertEquals(2, integers.length());
    }

    @Test
    public void testTakeDrop() {
    	ImmutableList<Integer> list = ImmutableList.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17);
        assertEquals(3, list.drop(15).length());
        assertEquals(15, list.take(15).length());
    }
    
    @Test
    public void testExists() {
        ImmutableList<Integer> list = ImmutableList.empty();
        assertFalse(list.contains(0));
    }

    @Test
    public void testEvery() {
        ImmutableList<Integer> list = ImmutableList.of(5, 10, 15, 20);
        assertTrue(list.every(integer -> integer % 5 == 0));
        list = list.cons(1);
        assertFalse(list.every(integer -> integer % 5 == 0));
        assertTrue(ImmutableList.empty().every(x -> false));
    }
    
    @Test
    public void testFoldRight() {
    	ImmutableList<Integer> integers = ImmutableList.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17);
    	int total = 0;
        for (int i : integers) {
            total += i;
        }
        assertEquals((Integer) total, integers.foldRight(0, (a, b) -> a + b));
    }
    
    @Test
    public void testContains() {
        String a = new String("Luke");
        String b = new String("Han");
        String c = new String("Leia");
        String d = new String("");
        ImmutableList<String> l = ImmutableList.of(a, b, c);
        assertTrue(l.contains(a));
        assertTrue(l.contains(b));
        assertTrue(l.contains(c));
        assertFalse(l.contains(d));
    }
    
    @Test
    public void testFindIndex() {
        assertTrue(!ImmutableList.empty().findIndex(i -> true).isSuccess());
        assertTrue(!ImmutableList.of(1).findIndex(i -> i == 0).isSuccess());
        assertTrue(ImmutableList.of(1).findIndex(i -> i == 1).getOrElse(3) == 0);
        assertTrue(ImmutableList.of(0, 1).findIndex(i -> i == 1).getOrElse(3) == 1);
        assertTrue(ImmutableList.of(0, 1, 1).findIndex(i -> i == 1).getOrElse(3) == 1);
        assertTrue(!ImmutableList.of(0, 1).findIndex(i -> i == 2).isSuccess());
    }
    
    @Test
    public void testStream() {
        ImmutableList<Integer> list = ImmutableList.of(1, 2, 3, 4, 5);
        List<Integer> mutableList = list.stream().collect(Collectors.toList());
        assertEquals(list, ImmutableList.from(mutableList));
        mutableList.sort((int1, int2) -> int2 - int1); // reversed

        assertEquals(list.reverse(), ImmutableList.from(mutableList));
        assertEquals(mutableList, list.stream().sorted((int1, int2) -> int2 - int1).collect(Collectors.toList()));
    }

    @Test
    public void testCollector() {
        ImmutableList<Integer> list = ImmutableList.of(1, 2, 3, 4, 5);
        assertEquals(list, StreamSupport.stream(list.spliterator(), false).map(x -> x + 1).map(x -> x - 1).collect(ImmutableList.collector()));
    }
}
