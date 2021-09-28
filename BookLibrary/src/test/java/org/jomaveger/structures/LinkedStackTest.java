package org.jomaveger.structures;

import static org.junit.jupiter.api.Assertions.*;

import org.jomaveger.lang.dbc.exceptions.ContractViolationException;
import org.junit.jupiter.api.*;

public class LinkedStackTest {
	
	private IStack<Integer> stack;

	@BeforeEach
    public void setUp() {
		stack = new LinkedStack<>();
    }
    
    @AfterEach
    public void tearDown() {
        stack = null;
    }
    
    @Test
    public void testDefaultConstructorEnsuresStackIsEmpty() {
        assertEquals((Integer)0, stack.size());
    }
    
    @Test
    public void testPushPrecondition() {
    	Assertions.assertThrows(ContractViolationException.class, () -> {
    		stack.push(null);
		});
    }
    
    @Test
    public void testPushPostcondition1() {
    	Integer siete = 7;
        stack.push(siete);
        
        assertEquals(siete, stack.peek());
    }
    
    @Test
    public void testPushPostcondition2() {
    	Integer siete = 7;
        stack.push(siete);
        
        assertTrue(stack.size() > 0);
    }
    
    @Test
    public void testPushPostcondition3() {
    	Integer oldSize = stack.size();
    	Integer siete = 7;
        stack.push(siete);
        
        assertEquals((Integer)(1 + oldSize), stack.size());
    }
    
    @Test
    public void testPopPrecondition() {
    	Assertions.assertThrows(ContractViolationException.class, () -> {
    		stack.pop();
		});
    }

    @Test
    public void testPopPostcondition() {
    	Integer siete = 7;
    	Integer tres = 3;
    	Integer cinco = 5;
        stack.push(siete);
        stack.push(tres);
        stack.push(cinco);
        Integer oldSize = stack.size();
        stack.pop();
        
        assertEquals((Integer)(oldSize - 1), stack.size());
    }
    
    @Test
    public void testPeekPrecondition() {
    	Assertions.assertThrows(ContractViolationException.class, () -> {
    		stack.peek();
		});
    }
    
    @Test
    public void testPeekPostcondition() {
    	Integer siete = 7;
    	Integer tres = 3;
    	Integer cinco = 5;
        stack.push(siete);
        stack.push(tres);
        stack.push(cinco);
        Integer oldSize = stack.size();
        Integer peek = stack.peek();
        
        assertEquals(cinco, peek);
        assertEquals((Integer)(oldSize), stack.size());
    }
    
    @Test
    public void testDeepCopyPostcondition() {
    	Integer siete = 7;
    	Integer tres = 3;
    	Integer cinco = 5;
        stack.push(siete);
        stack.push(tres);
        stack.push(cinco);
        
        IStack<Integer> clone = stack.deepCopy();
        
        assertTrue(stack.equals(clone));
    }
}
