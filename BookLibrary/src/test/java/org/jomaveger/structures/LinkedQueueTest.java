package org.jomaveger.structures;

import static org.junit.jupiter.api.Assertions.*;

import org.jomaveger.lang.dbc.exceptions.ContractViolationException;
import org.junit.jupiter.api.*;

public class LinkedQueueTest {

	private IQueue<Integer> q;

	@BeforeEach
    public void setUp() {
		q = new LinkedQueue<>();
    }
    
    @AfterEach
    public void tearDown() {
        q = null;
    }
    
    @Test
    public void testDefaultConstructorEnsuresQueueIsEmpty() {
        assertEquals((Integer)0, q.size());
    }
    
    @Test
    public void testEnqueuePrecondition() {
    	Assertions.assertThrows(ContractViolationException.class, () -> {
    		q.enqueue(null);
		});
    }
    
    @Test
    public void testEnqueuePostcondition1() {
    	Integer siete = 7;
        q.enqueue(siete);
        
        assertEquals(siete, q.front());
    }
    
    @Test
    public void testEnqueuePostcondition2() {
    	Integer siete = 7;
    	Integer tres = 3;
    	Integer cinco = 5;
    	q.enqueue(siete);
    	q.enqueue(tres);
    	q.enqueue(cinco);
    	
    	Integer oldSize = q.size();
    	
    	Integer nueve = 9;
    	q.enqueue(nueve);
    	
    	assertEquals((Integer)(1 + oldSize), q.size());
    }
    
    @Test
    public void testDequeuePrecondition() {
    	Assertions.assertThrows(ContractViolationException.class, () -> {
    		q.dequeue();
		});        
    }

    @Test
    public void testDequeuePostcondition() {
    	Integer siete = 7;
    	Integer tres = 3;
    	Integer cinco = 5;
        q.enqueue(siete);
        q.enqueue(tres);
        q.enqueue(cinco);
        Integer oldSize = q.size();
        q.dequeue();
        
        assertEquals((Integer)(oldSize - 1), q.size());
    }
    
    @Test
    public void testFrontPrecondition() {
    	Assertions.assertThrows(ContractViolationException.class, () -> {
    		q.front();
		});
    }
    
    @Test
    public void testFrontPostcondition() {
    	Integer siete = 7;
    	Integer tres = 3;
    	Integer cinco = 5;
        q.enqueue(siete);
        q.enqueue(tres);
        q.enqueue(cinco);
        Integer oldSize = q.size();
        Integer peek = q.front();
        
        assertEquals(siete, peek);
        assertEquals((Integer)(oldSize), q.size());
    }
    
    @Test
    public void testSizePostcondition() {
    	Integer siete = 7;
    	Integer tres = 3;
    	Integer cinco = 5;
    	
    	assertTrue(q.size() >= 0);
    	
        q.enqueue(siete);
        q.enqueue(tres);
        q.enqueue(cinco);
        
        assertTrue(q.size() >= 0);
       
        q.dequeue();
        q.dequeue();
        
        assertTrue(q.size() >= 0);
        
        Integer nueve = 9;
        q.enqueue(nueve);
        q.dequeue();
        q.dequeue();
        assertTrue(q.size() >= 0);
    }
    
    @Test
    public void testDeepCopyPostcondition() {
    	Integer siete = 7;
    	Integer tres = 3;
    	Integer cinco = 5;
        q.enqueue(siete);
        q.enqueue(tres);
        q.enqueue(cinco);
        
        IQueue<Integer> clone = q.deepCopy();
        
        assertTrue(q.equals(clone));
    }
}
