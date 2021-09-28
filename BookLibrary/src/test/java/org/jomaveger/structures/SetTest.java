package org.jomaveger.structures;

import static org.junit.jupiter.api.Assertions.*;

import org.jomaveger.lang.dbc.exceptions.ContractViolationException;
import org.junit.jupiter.api.*;

public class SetTest {

	private ISet<Integer> set;
	Integer siete = 7;
	Integer tres = 3;
	Integer cinco = 5;
	Integer nueve = 9;
	Integer uno = 1;
	Integer dos = 2;
	Integer cuatro = 4;
	Integer seis = 6;
	Integer ocho = 8;
	Integer cero = 0;

	@BeforeEach
    public void setUp() {
		set = new Set<>();
    }
    
    @AfterEach
    public void tearDown() {
        set = null;
    }
    
    @Test
    public void testDefaultConstructorEnsuresSetIsEmpty() {
        assertEquals((Integer)0, set.cardinal());
        assertTrue(set.isEmpty());
    }
    
    @Test
    public void testmakeUnitSetPrecondition() {
    	Assertions.assertThrows(ContractViolationException.class, () -> {
    		set.makeUnitSet(null);
		});
    }
    
    @Test
    public void testmakeUnitSetPostcondition() {
        set.makeUnitSet(siete);
        assertTrue(!set.isEmpty());
        assertEquals((Integer)1, set.cardinal());
        assertTrue(set.contains(siete));
    }
    
    @Test
    public void testClearPostcondition() {
        set.clear();
        assertEquals((Integer)0, set.cardinal());
        assertTrue(set.isEmpty());
    }
    
    @Test
    public void testAddPrecondition() {
    	Assertions.assertThrows(ContractViolationException.class, () -> {
    		set.add(null);
		});
    }
    
    @Test
    public void testAddPostcondition() {
    	set.add(siete);
        set.add(tres);
        set.add(cinco);
        Integer oldCardinal = set.cardinal();
        
        set.add(cinco);
        set.add(nueve);
        assertTrue(!set.isEmpty());
        assertEquals((Integer) (oldCardinal + 1), set.cardinal());
        assertTrue(set.contains(nueve));
    }
    
    @Test
    public void testContainsPrecondition() {
    	Assertions.assertThrows(ContractViolationException.class, () -> {
    		set.contains(null);
		});
    }
    
    @Test
    public void testContainsPostcondition() {
    	set.add(siete);
        set.add(tres);
        set.add(cinco);
        Integer oldCardinal = set.cardinal();
        
        assertTrue(set.contains(siete));
        assertEquals((Integer) (oldCardinal), set.cardinal());
    }
    
    @Test
    public void testRemovePrecondition() {
    	Assertions.assertThrows(ContractViolationException.class, () -> {
    		set.remove(null);
		});
    }
    
    @Test
    public void testRemovePostcondition() {
    	set.add(siete);
        set.add(tres);
        set.add(cinco);
        Integer oldCardinal = set.cardinal();
        
        set.remove(tres);
        assertTrue(!set.contains(tres));
        assertEquals((Integer) (oldCardinal - 1), set.cardinal());
    }
    
    @Test
    public void testIsSubsetPrecondition() {
    	Assertions.assertThrows(ContractViolationException.class, () -> {
    		set.isSubset(null);
		});
    }
    
    @Test
    public void testIsSubsetPostcondition() {
    	set.add(siete);
        set.add(tres);
        set.add(cinco);
        set.add(nueve);
        Integer oldCardinal = set.cardinal();
        
        ISet<Integer> other = new Set<>();
        other.add(siete);
        other.add(tres);
        other.add(cinco);
        other.add(nueve);
        other.add(uno);
        other.add(cuatro);
        other.add(ocho);
        other.add(seis);
        
        assertTrue(set.isSubset(other));
        assertEquals((Integer) (oldCardinal), set.cardinal());
    }
    
    @Test
    public void testUnionPrecondition() {
    	Assertions.assertThrows(ContractViolationException.class, () -> {
    		set.union(null);
		});
    }
    
    @Test
    public void testUnionPostcondition() {
    	set.add(siete);
        set.add(tres);
        set.add(cinco);
        set.add(nueve);
        Integer oldCardinal = set.cardinal();
        
        ISet<Integer> other = new Set<>();
        other.add(siete);
        other.add(tres);
        other.add(cinco);
        other.add(nueve);
        other.add(uno);
        other.add(cuatro);
        other.add(ocho);
        other.add(seis);
        
        set.union(other);
        assertEquals((Integer) (8), set.cardinal());
        assertTrue(set.cardinal() <= oldCardinal + other.cardinal());
        assertTrue(set.contains(tres));
        assertTrue(set.contains(siete));
        assertTrue(set.contains(cinco));
        assertTrue(set.contains(nueve));
        assertTrue(set.contains(uno));
        assertTrue(set.contains(cuatro));
        assertTrue(set.contains(ocho));
        assertTrue(set.contains(seis));
    }
    
    @Test
    public void testIntersectionPrecondition() {
    	Assertions.assertThrows(ContractViolationException.class, () -> {
    		set.intersection(null);
		});
    }
    
    @Test
    public void testIntersectionPostcondition() {
    	set.add(siete);
        set.add(tres);
        set.add(cinco);
        set.add(nueve);
        Integer oldCardinal = set.cardinal();
        
        ISet<Integer> other = new Set<>();
        other.add(siete);
        other.add(tres);
        other.add(cinco);
        other.add(nueve);
        other.add(uno);
        other.add(cuatro);
        other.add(ocho);
        other.add(seis);
        
        set.intersection(other);
        assertEquals((Integer) (4), set.cardinal());
        assertTrue(set.cardinal() <= oldCardinal + other.cardinal());
        assertTrue(set.contains(tres));
        assertTrue(set.contains(siete));
        assertTrue(set.contains(cinco));
        assertTrue(set.contains(nueve));
    }
    
    @Test
    public void testDifferencePrecondition() {
    	Assertions.assertThrows(ContractViolationException.class, () -> {
    		set.difference(null);
		});
    }
    
    @Test
    public void testDifferencePostcondition() {
    	set.add(siete);
        set.add(tres);
        set.add(cinco);
        set.add(nueve);
        set.add(cero);
        Integer oldCardinal = set.cardinal();
        
        ISet<Integer> other = new Set<>();
        other.add(siete);
        other.add(tres);
        other.add(cinco);
        other.add(nueve);
        other.add(uno);
        other.add(cuatro);
        other.add(ocho);
        other.add(seis);
        
        set.difference(other);
        assertEquals((Integer) (1), set.cardinal());
        assertTrue(set.cardinal() <= oldCardinal);
        assertTrue(set.contains(cero));
        assertTrue(!set.contains(siete));
        assertTrue(!set.contains(cinco));
        assertTrue(!set.contains(nueve));
        assertTrue(!set.contains(tres));
    }
    
    @Test
    public void testDeepCopyPostcondition() {
    	set.add(siete);
        set.add(tres);
        set.add(cinco);
        set.add(nueve);
        
        ISet<Integer> clone = set.deepCopy();
        
        assertTrue(set.equals(clone));
    }
}
