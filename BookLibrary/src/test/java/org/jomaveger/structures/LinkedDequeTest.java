package org.jomaveger.structures;

import static org.junit.jupiter.api.Assertions.*;

import org.jomaveger.lang.dbc.exceptions.ContractViolationException;
import org.junit.jupiter.api.*;

public class LinkedDequeTest {

    private IDequeue<Integer> d;
    Integer siete = 7;
    Integer tres = 3;
    Integer cinco = 5;
    Integer nueve = 9;

    @BeforeEach
    public void setUp() {
        d = new LinkedDequeue<>();
    }

    @AfterEach
    public void tearDown() {
        d = null;
    }

    @Test
    public void testDefaultConstructorEnsuresDequeueIsEmpty() {
        assertEquals((Integer)0, d.size());
    }

    @Test
    public void testAddFirstPrecondition() {
    	Assertions.assertThrows(ContractViolationException.class, () -> {
    		d.addFirst(null);
		});
    }

    @Test
    public void testAddFirstPostcondition() {
        d.addFirst(siete);
        d.addFirst(tres);
        d.addFirst(cinco);
        Integer oldSize = d.size();

        d.addFirst(nueve);
        assertEquals(d.getFirst(), nueve);
        assertEquals((Integer)(1 + oldSize), d.size());
    }

    @Test
    public void testAddLastPrecondition() {
    	Assertions.assertThrows(ContractViolationException.class, () -> {
    		d.addLast(null);
		});        
    }

    @Test
    public void testAddLastPostcondition() {
        d.addLast(siete);
        d.addLast(tres);
        d.addLast(cinco);
        Integer oldSize = d.size();

        d.addLast(nueve);
        assertEquals(d.getLast(), nueve);
        assertEquals((Integer)(1 + oldSize), d.size());
    }

    @Test
    public void testGetFirstPrecondition() {
    	Assertions.assertThrows(ContractViolationException.class, () -> {
    		d.getFirst();
		});
    }

    @Test
    public void testGetFirstPostcondition() {
        d.addLast(siete);
        d.addLast(tres);
        d.addLast(cinco);
        Integer oldSize = d.size();

        assertEquals((Integer)(oldSize), d.size());
    }

    @Test
    public void testGetLastPrecondition() {
    	Assertions.assertThrows(ContractViolationException.class, () -> {
    		d.getLast();
		});
    }

    @Test
    public void testGetLastPostcondition() {
        d.addLast(siete);
        d.addLast(tres);
        d.addLast(cinco);
        Integer oldSize = d.size();

        assertEquals((Integer)(oldSize), d.size());
    }

    @Test
    public void testRemoveFirstPrecondition() {
    	Assertions.assertThrows(ContractViolationException.class, () -> {
    		d.removeFirst();
		});    	
    }

    @Test
    public void testRemoveFirstPostcondition() {
        d.addLast(siete);
        d.addLast(tres);
        d.addLast(cinco);
        Integer oldSize = d.size();

        d.removeFirst();
        assertEquals((Integer)(oldSize - 1), d.size());
    }

    @Test
    public void testRemoveLastPrecondition() {
    	Assertions.assertThrows(ContractViolationException.class, () -> {
    		d.removeLast();
		});
    }

    @Test
    public void testRemoveLastPostcondition() {
        d.addLast(siete);
        d.addLast(tres);
        d.addLast(cinco);
        Integer oldSize = d.size();

        d.removeLast();
        assertEquals((Integer)(oldSize - 1), d.size());
    }

    @Test
    public void testContainsPrecondition() {
    	Assertions.assertThrows(ContractViolationException.class, () -> {
    		d.contains(null);
		});
    }

    @Test
    public void testContainsPostcondition() {
        d.addLast(siete);
        d.addLast(tres);
        d.addLast(cinco);
        d.addLast(siete);
        Integer oldSize = d.size();

        assertEquals((Integer)(oldSize), d.size());
    }

    @Test
    public void testDeepCopyPostcondition() {
        d.addLast(siete);
        d.addLast(tres);
        d.addLast(cinco);
        d.addLast(siete);

        IDequeue<Integer> clone = d.deepCopy();

        assertTrue(d.equals(clone));
    }
}