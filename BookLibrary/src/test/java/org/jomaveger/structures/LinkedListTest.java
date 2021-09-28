package org.jomaveger.structures;

import static org.junit.jupiter.api.Assertions.*;

import org.jomaveger.lang.dbc.exceptions.ContractViolationException;
import org.junit.jupiter.api.*;

public class LinkedListTest {

	private IList<Integer> list;
	Integer siete = 7;
	Integer tres = 3;
	Integer cinco = 5;
	Integer nueve = 9;

	@BeforeEach
    public void setUp() {
		list = new LinkedList<>();
    }
    
    @AfterEach
    public void tearDown() {
        list = null;
    }
    
    @Test
    public void testDefaultConstructorEnsuresListIsEmpty() {
        assertEquals((Integer)0, list.size());
    }
    
    @Test
    public void testAddFirstPrecondition() {
    	Assertions.assertThrows(ContractViolationException.class, () -> {
    		list.addFirst(null);
		});
    }
    
    @Test
    public void testAddFirstPostcondition() {
        list.addFirst(siete);
        list.addFirst(tres);
        list.addFirst(cinco);
        Integer oldSize = list.size();
        
        list.addFirst(nueve);
        assertEquals(list.getFirst(), nueve);
        assertEquals((Integer)(1 + oldSize), list.size());
    }
    
    @Test
    public void testAddLastPrecondition() {
    	Assertions.assertThrows(ContractViolationException.class, () -> {
    		list.addLast(null);
		});
    }
    
    @Test
    public void testAddLastPostcondition() {
        list.addLast(siete);
        list.addLast(tres);
        list.addLast(cinco);
        Integer oldSize = list.size();
        
        list.addLast(nueve);
        assertEquals(list.getLast(), nueve);
        assertEquals((Integer)(1 + oldSize), list.size());
    }
    
    @Test
    public void testGetFirstPrecondition() {
    	Assertions.assertThrows(ContractViolationException.class, () -> {
    		list.getFirst();
		});
    }
    
    @Test
    public void testGetFirstPostcondition() {
    	list.addLast(siete);
        list.addLast(tres);
        list.addLast(cinco);
        Integer oldSize = list.size();
        
        assertEquals(list.get(0), list.getFirst());
        assertEquals((Integer)(oldSize), list.size());
    }
    
    @Test
    public void testGetLastPrecondition() {
    	Assertions.assertThrows(ContractViolationException.class, () -> {
    		list.getLast();
		});
    }
    
    @Test
    public void testGetLastPostcondition() {
    	list.addLast(siete);
        list.addLast(tres);
        list.addLast(cinco);
        Integer oldSize = list.size();
        
        assertEquals(list.get(list.size() - 1), list.getLast());
        assertEquals((Integer)(oldSize), list.size());
    }
    
    @Test
    public void testRemoveFirstPrecondition() {
    	Assertions.assertThrows(ContractViolationException.class, () -> {
    		list.removeFirst();
		});
    }
    
    @Test
    public void testRemoveFirstPostcondition() {
    	list.addLast(siete);
        list.addLast(tres);
        list.addLast(cinco);
        Integer oldSize = list.size();
        
        list.removeFirst();
        assertEquals((Integer)(oldSize - 1), list.size());
    }
    
    @Test
    public void testRemoveLastPrecondition() {
    	Assertions.assertThrows(ContractViolationException.class, () -> {
    		list.removeLast();
		});
    }
    
    @Test
    public void testRemoveLastPostcondition() {
    	list.addLast(siete);
        list.addLast(tres);
        list.addLast(cinco);
        Integer oldSize = list.size();
        
        list.removeLast();
        assertEquals((Integer)(oldSize - 1), list.size());
    }
    
    @Test
    public void testContainsPrecondition() {
    	Assertions.assertThrows(ContractViolationException.class, () -> {
    		list.contains(null);
		});
    }
    
    @Test
    public void testContainsPostcondition() {
    	list.addLast(siete);
        list.addLast(tres);
        list.addLast(cinco);
        list.addLast(siete);
        Integer oldSize = list.size();
        
        assertTrue(list.contains(siete) && list.indexOf(siete) >= 0);
        assertEquals((Integer)(oldSize), list.size());
    }
    
    @Test
    public void testRemovePrecondition() {
    	Assertions.assertThrows(ContractViolationException.class, () -> {
    		IList<String> slist = new LinkedList<>();
            slist.remove((String)null);
		});
    }
    
    @Test
    public void testRemovePostcondition() {
    	IList<String> slist = new LinkedList<>();
    	slist.addLast("Han");
    	slist.addLast("Luke");
    	slist.addLast("Leia");
        Integer oldSize = slist.size();
        
        slist.remove("Luke");
        assertEquals((Integer)(oldSize - 1), slist.size());
    }
    
    @Test
    public void testClearPostcondition() {
    	list.addLast(siete);
        list.addLast(tres);
        list.addLast(cinco);
        list.addLast(siete);
        list.clear();
        
        assertTrue(list.isEmpty());
    }
    
    @Test
    public void testGetPrecondition() {
    	Assertions.assertThrows(ContractViolationException.class, () -> {
    		list.get(0);
		});
    }
    
    @Test
    public void testGetPostcondition() {
    	list.addLast(siete);
        list.addLast(tres);
        list.addLast(cinco);
        list.addLast(siete);
        Integer oldSize = list.size();
        
        assertTrue(list.get(3) != null);
        assertEquals((Integer)(oldSize), list.size());
        assertEquals(siete, list.get(3));
    }
    
    @Test
    public void testSetPrecondition1() {
    	Assertions.assertThrows(ContractViolationException.class, () -> {
    		list.set(0, siete);
		});
    }
    
    @Test
    public void testSetPrecondition2() {
    	Assertions.assertThrows(ContractViolationException.class, () -> {
    		list.set(0, null);
		});
    }
    
    @Test
    public void testSetPrecondition3() {
    	Assertions.assertThrows(ContractViolationException.class, () -> {
    		list.set(-1, tres);
		});
    	
    }
    
    @Test
    public void testSetPrecondition4() {
    	Assertions.assertThrows(ContractViolationException.class, () -> {
    		list.addLast(siete);
            list.addLast(tres);
            list.addLast(cinco);
            list.addLast(siete);
        	list.set(4, nueve);
		});
    }
    
    @Test
    public void testSetPostcondition() {
    	list.addLast(siete);
        list.addLast(tres);
        list.addLast(cinco);
        list.addLast(siete);
        Integer oldSize = list.size();
        
        list.set(2, nueve);
        assertEquals(nueve, list.get(2));
        assertEquals((Integer)(oldSize), list.size());
    }
    
    @Test
    public void testAddPrecondition1() {
    	Assertions.assertThrows(ContractViolationException.class, () -> {
    		list.add(0, null);
		});
    }
    
    @Test
    public void testAddPrecondition2() {
    	Assertions.assertThrows(ContractViolationException.class, () -> {
    		list.add(-1, tres);
		});
    }
    
    @Test
    public void testAddPrecondition3() {
    	Assertions.assertThrows(ContractViolationException.class, () -> {
    		list.addLast(siete);
            list.addLast(tres);
            list.addLast(cinco);
            list.addLast(siete);
        	list.add(5, nueve);
		});
    }
    
    @Test
    public void testAddPostcondition() {
    	list.addLast(siete);
        list.addLast(tres);
        list.addLast(cinco);
        list.addLast(siete);
        Integer oldSize = list.size();
        
        list.add(2, nueve);
        assertEquals(nueve, list.get(2));
        assertEquals((Integer)(oldSize + 1), list.size());
        assertTrue(list.contains(nueve));
    }
    
    @Test
    public void testRemovePrecondition1() {
    	Assertions.assertThrows(ContractViolationException.class, () -> {
    		IList<String> slist = new LinkedList<>();
            slist.remove((Integer)0);
		});
    }
    
    @Test
    public void testRemovePrecondition2() {
    	Assertions.assertThrows(ContractViolationException.class, () -> {
    		IList<String> slist = new LinkedList<>();
            slist.remove((Integer)(-1));
		});
    }
    
    @Test
    public void testRemovePrecondition3() {
    	Assertions.assertThrows(ContractViolationException.class, () -> {
    		IList<String> slist = new LinkedList<>();
        	slist.addLast("Han");
        	slist.addLast("Luke");
        	slist.addLast("Leia");
        	slist.remove((Integer)3);
		});
    }
    
    @Test
    public void testRemoveWithIndexPostcondition() {
    	IList<String> slist = new LinkedList<>();
    	slist.addLast("Han");
    	slist.addLast("Luke");
    	slist.addLast("Leia");
        Integer oldSize = slist.size();
        
        slist.remove(1);
        assertEquals((Integer)(oldSize - 1), slist.size());
    }
    
    @Test
    public void testIndexOfPrecondition() {
    	Assertions.assertThrows(ContractViolationException.class, () -> {
    		list.indexOf(null);
		});
    }
    
    @Test
    public void testIndexOfPostcondition1() {
    	list.addLast(siete);
        list.addLast(tres);
        list.addLast(cinco);
        list.addLast(siete);
        Integer oldSize = list.size();
        
        assertEquals((Integer)(oldSize), list.size());
        assertEquals((Integer)(-1), list.indexOf(nueve));
    }
    
    @Test
    public void testIndexOfPostcondition2() {
    	list.addLast(siete);
        list.addLast(tres);
        list.addLast(cinco);
        list.addLast(siete);
        Integer oldSize = list.size();
        
        assertEquals((Integer)(oldSize), list.size());
        assertEquals((Integer)(0), list.indexOf(siete));
        assertTrue(list.contains(siete));
    }
    
    @Test
    public void testLastIndexOfPrecondition() {
    	Assertions.assertThrows(ContractViolationException.class, () -> {
    		list.lastIndexOf(null);
		});
    }
    
    @Test
    public void testLastIndexOfPostcondition1() {
    	list.addLast(siete);
        list.addLast(tres);
        list.addLast(cinco);
        list.addLast(siete);
        Integer oldSize = list.size();
        
        assertEquals((Integer)(oldSize), list.size());
        assertEquals((Integer)(-1), list.lastIndexOf(nueve));
    }
    
    @Test
    public void testLastIndexOfPostcondition2() {
    	list.addLast(siete);
        list.addLast(tres);
        list.addLast(cinco);
        list.addLast(siete);
        Integer oldSize = list.size();
        
        assertEquals((Integer)(oldSize), list.size());
        assertEquals((Integer)(3), list.lastIndexOf(siete));
        assertTrue(list.contains(siete));
        assertTrue(list.lastIndexOf(siete) >= list.indexOf(siete));
    }
    
    @Test
    public void testDeepCopyPostcondition() {
    	list.addLast(siete);
        list.addLast(tres);
        list.addLast(cinco);
        list.addLast(siete);
        
        IList<Integer> clone = list.deepCopy();
        
        assertTrue(list.equals(clone));
    }
}
