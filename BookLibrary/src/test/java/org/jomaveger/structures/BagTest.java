package org.jomaveger.structures;

import static org.junit.jupiter.api.Assertions.*;

import org.jomaveger.lang.dbc.exceptions.ContractViolationException;
import org.junit.jupiter.api.*;

public class BagTest {

    private IBag<String> bag;
    String siete = "siete";
    String tres = "tres";
    String cinco = "cinco";
    String nueve = "nueve";
    String uno = "uno";
    String dos = "dos";
    String cuatro = "cuatro";
    String seis = "seis";
    String ocho = "ocho";
    String cero = "cero";

    @BeforeEach
    public void setUp() {
        bag = new Bag<>();
    }

    @AfterEach
    public void tearDown() {
        bag = null;
    }

    @Test
    public void testDefaultConstructorEnsuresBagIsEmpty() {
        assertEquals((Integer)0, bag.cardinal());
        assertTrue(bag.isEmpty());
        assertEquals((Integer)0, bag.cardinalDistinct());
    }

    @Test
    public void testClearPostcondition() {
        bag.add(cero);
        bag.add(uno);
        bag.add(tres);
        bag.clear();
        assertEquals((Integer)0, bag.cardinal());
        assertTrue(bag.isEmpty());
    }

    @Test
    public void testAddPrecondition() {
    	Assertions.assertThrows(ContractViolationException.class, () -> {
    		bag.add(null);
		});
    }

    @Test
    public void testAddPostcondition() {
        bag.add(siete);
        bag.add(tres);
        bag.add(cinco);
        Integer oldCardinal = bag.cardinal();
        Integer oldMultiplicity = bag.multiplicity(siete);
        Integer oldCardinalDistinct = bag.cardinalDistinct();

        bag.add(siete);
        assertTrue(!bag.isEmpty());
        assertEquals((Integer) (oldCardinal + 1), bag.cardinal());
        assertEquals((Integer) (oldMultiplicity + 1), bag.multiplicity(siete));
        assertEquals((Integer) (oldCardinalDistinct), bag.cardinalDistinct());
    }

    @Test
    public void testMultiplicityPrecondition() {
    	Assertions.assertThrows(ContractViolationException.class, () -> {
    		bag.multiplicity(null);
		});
    }

    @Test
    public void testMultiplicityPostcondition() {
        bag.add(siete);
        bag.add(tres);
        bag.add(cinco);
        bag.add(siete);
        bag.add(siete);
        Integer multiplicity = bag.multiplicity(siete);

        assertTrue(multiplicity >= 0);
        assertTrue(multiplicity == 3);
        assertTrue(multiplicity <= bag.cardinal());
        assertTrue(multiplicity <= bag.cardinalDistinct());
    }

    @Test
    public void testDeletePrecondition() {
    	Assertions.assertThrows(ContractViolationException.class, () -> {
    		bag.delete(null);
		});
    }

    @Test
    public void testDeletePostcondition() {
        bag.add(siete);
        bag.add(tres);
        bag.add(cinco);
        bag.add(siete);
        bag.add(siete);
        Integer oldCardinal = bag.cardinal();
        Integer oldMultiplicity = bag.multiplicity(siete);
        Integer oldCardinalDistinct = bag.cardinalDistinct();

        bag.delete(siete);
        assertTrue(!bag.isEmpty());
        assertEquals((Integer) (oldCardinal - 1), bag.cardinal());
        assertEquals((Integer) (oldMultiplicity - 1), bag.multiplicity(siete));
        assertEquals((Integer) (oldCardinalDistinct), bag.cardinalDistinct());
    }

    @Test
    public void testRemovePrecondition() {
    	Assertions.assertThrows(ContractViolationException.class, () -> {
    		bag.remove(null);
		});
    }

    @Test
    public void testRemovePostcondition() {
        bag.add(siete);
        bag.add(tres);
        bag.add(cinco);
        bag.add(siete);
        bag.add(siete);
        Integer oldCardinal = bag.cardinal();
        Integer oldMultiplicity = bag.multiplicity(siete);
        Integer oldCardinalDistinct = bag.cardinalDistinct();

        bag.remove(siete);
        assertTrue(!bag.isEmpty());
        assertEquals((Integer) (oldCardinal - oldMultiplicity), bag.cardinal());
        assertEquals((Integer) 0, bag.multiplicity(siete));
        assertEquals((Integer) (oldCardinalDistinct - 1), bag.cardinalDistinct());
    }

    @Test
    public void testUnionPrecondition() {
    	Assertions.assertThrows(ContractViolationException.class, () -> {
    		bag.union(null);
		});
    }

    @Test
    public void testUnionPostcondition() {
        bag.add(siete);
        bag.add(tres);
        bag.add(cinco);
        bag.add(siete);
        Integer oldCardinal = bag.cardinal();
        Integer oldCardinalDistinct = bag.cardinalDistinct();

        IBag<String> other = new Bag<>();
        other.add(siete);
        other.add(tres);
        other.add(cinco);
        other.add(nueve);
        other.add(cinco);
        other.add(siete);
        other.add(ocho);
        other.add(cinco);

        bag.union(other);
        assertTrue(bag.cardinal() == oldCardinal + other.cardinal());
        assertTrue(bag.cardinalDistinct() <= oldCardinalDistinct + other.cardinalDistinct());
    }

    @Test
    public void testIntersectionPrecondition() {
    	Assertions.assertThrows(ContractViolationException.class, () -> {
    		bag.intersection(null);
		});
    }

    @Test
    public void testIntersectionPostcondition() {
        bag.add(siete);
        bag.add(tres);
        bag.add(cinco);
        bag.add(siete);
        Integer oldCardinal = bag.cardinal();
        Integer oldCardinalDistinct = bag.cardinalDistinct();

        IBag<String> other = new Bag<>();
        other.add(siete);
        other.add(tres);
        other.add(cinco);
        other.add(nueve);
        other.add(cinco);
        other.add(siete);
        other.add(ocho);
        other.add(cinco);

        bag.intersection(other);
        assertTrue(bag.cardinal() < oldCardinal + other.cardinal());
        assertTrue(bag.cardinalDistinct() < oldCardinalDistinct + other.cardinalDistinct());
    }

    @Test
    public void testDifferencePrecondition() {
    	Assertions.assertThrows(ContractViolationException.class, () -> {
    		bag.difference(null);
		});
    }

    @Test
    public void testDifferencePostcondition() {
        bag.add(siete);
        bag.add(tres);
        bag.add(cinco);
        bag.add(siete);
        Integer oldCardinal = bag.cardinal();
        Integer oldCardinalDistinct = bag.cardinalDistinct();

        IBag<String> other = new Bag<>();
        other.add(siete);
        other.add(tres);
        other.add(cinco);
        other.add(nueve);
        other.add(cinco);
        other.add(siete);
        other.add(ocho);
        other.add(cinco);

        bag.difference(other);
        assertTrue(bag.cardinal() <= oldCardinal + other.cardinal());
        assertTrue(bag.cardinalDistinct() <= oldCardinalDistinct + other.cardinalDistinct());
    }

    @Test
    public void testDeepCopyPostcondition() {
        bag.add(siete);
        bag.add(tres);
        bag.add(cinco);
        bag.add(nueve);
        bag.add(siete);
        bag.add(siete);

        IBag<String> clone = bag.deepCopy();

        assertTrue(bag.equals(clone));
    }
}