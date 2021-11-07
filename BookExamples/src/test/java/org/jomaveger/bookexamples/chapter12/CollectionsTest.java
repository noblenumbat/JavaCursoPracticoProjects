package org.jomaveger.bookexamples.chapter12;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class CollectionsTest {

	@Test
    public void testSynchronizedCollection() throws InterruptedException {
		Collection<Integer> syncCollection = Collections.synchronizedCollection(new ArrayList<>());
	    Runnable listOperations = () -> {
	        syncCollection.addAll(Arrays.asList(1, 2, 3, 4, 5, 6));
	    };
	     
	    Thread thread1 = new Thread(listOperations);
	    Thread thread2 = new Thread(listOperations);
	    thread1.start();
	    thread2.start();
	    thread1.join();
	    thread2.join();
	     
	    assertEquals(12, syncCollection.size());
    }
	
	@Test
    public void testSynchronizedList() throws InterruptedException {
	    List<String> syncCollection = Collections.synchronizedList(Arrays.asList("a", "b", "c"));
	    List<String> uppercasedCollection = new ArrayList<>();
	         
	    Runnable listOperations = () -> {
	        synchronized (syncCollection) {
	            syncCollection.forEach((e) -> {
	                uppercasedCollection.add(e.toUpperCase());
	            });
	        }
	    };
	    
	    Thread thread1 = new Thread(listOperations);
	    Thread thread2 = new Thread(listOperations);
	    thread1.start();
	    thread2.start();
	    thread1.join();
	    thread2.join();
	    
	    assertEquals("A", uppercasedCollection.get(0));	    
    }
	
	@Test
    public void testSynchronizedMap() throws InterruptedException {
        Map<Integer, String> syncMap = Collections.synchronizedMap(new HashMap<>());

        Runnable mapOperations = () -> {
            syncMap.put(1, "one");
            syncMap.put(2, "two");
            syncMap.put(3, "three");
            
        };
        Thread thread1 = new Thread(mapOperations);
        Thread thread2 = new Thread(mapOperations);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        
        assertEquals(3, syncMap.size());
        
        Set<Integer> s = syncMap.keySet();
        Integer sum = 0;
        synchronized (syncMap) {  // Synchronizing on m, not s!
            Iterator<Integer> i = s.iterator();
            while (i.hasNext())
                sum = sum + i.next();
        }
        
        assertEquals(6, sum.intValue());
    }
	
	@Test
    public void testSynchronizedSet() throws InterruptedException {
        Set<Integer> syncSet = Collections.synchronizedSet(new HashSet<>());
        
        Runnable setOperations = () -> {syncSet.addAll(Arrays.asList(1, 2, 3, 4, 5, 6));};
        Thread thread1 = new Thread(setOperations);
        Thread thread2 = new Thread(setOperations);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        
        assertEquals(6, syncSet.size());
        
        Integer sum = 0;
        synchronized (syncSet) {
            Iterator<Integer> i = syncSet.iterator();
            while (i.hasNext())
                sum = sum + i.next();
        }
        
        assertEquals(21, sum.intValue());
    }
	
	@Test
    public void testConcurrentMap() {
		ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
		 
		map.put("First", 10);
		map.put("Second", 20);
		map.put("Third", 30);
		map.put("Fourth", 40);
		 
		Iterator<String> iterator = map.keySet().iterator();
		Integer sum = 0;
		
		while (iterator.hasNext()) {
		    String key = iterator.next();
		    sum = sum + map.get(key);
		    map.put("Fifth", 50);
		}
        
        assertEquals(150, sum.intValue());
    }
    
    @Test
    public void giveRangeOfLongs_whenSummedInParallel_shouldBeEqualToExpectedTotal()
            throws InterruptedException, ExecutionException {

        long firstNum = 1;
        long lastNum = 1_000_000;

        List<Long> aList = LongStream.rangeClosed(firstNum, lastNum).boxed()
                .collect(Collectors.toList());

        ForkJoinPool customThreadPool = new ForkJoinPool(
                Runtime.getRuntime().availableProcessors() -1);
        long actualTotal = customThreadPool.submit(
                () -> aList.parallelStream().reduce(0L, Long::sum)).get();

        assertEquals((lastNum + firstNum) * lastNum / 2, actualTotal);
    }
}
