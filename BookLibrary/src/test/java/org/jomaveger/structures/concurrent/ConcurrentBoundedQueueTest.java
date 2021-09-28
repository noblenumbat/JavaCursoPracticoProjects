package org.jomaveger.structures.concurrent;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

public class ConcurrentBoundedQueueTest {
    
    private ConcurrentBoundedQueue<Integer> bq;
    
    @BeforeEach
    public void setUp() {
	bq = new ConcurrentBoundedQueue<>(10);
    }
    
    @AfterEach
    public void tearDown() {
        bq = null;
    }
    
    @Test
    public void testIsEmptyWhenConstructed() {
        assertTrue(bq.isEmpty());
        assertFalse(bq.isFull());
    }

    @Test
    public void testIsFullAfterEnqueue() {
        for (int i = 0; i < 10; i++) {
            bq.enqueue(i);
        }
        assertTrue(bq.isFull());
        assertFalse(bq.isEmpty());
        assertEquals((Integer)10, bq.size());
        assertEquals((Integer)0, bq.front());
    }
}
