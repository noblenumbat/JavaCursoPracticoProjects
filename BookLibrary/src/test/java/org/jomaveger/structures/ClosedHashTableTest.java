package org.jomaveger.structures;

import static org.junit.jupiter.api.Assertions.*;

import org.jomaveger.lang.dbc.exceptions.ContractViolationException;
import org.junit.jupiter.api.*;

public class ClosedHashTableTest {

    private ITable<String, Integer> table;

    @BeforeEach
    public void setUp() {
       table = new ClosedHashTable<>();
    }

    @AfterEach
    public void tearDown() {
        table = null;
    }

    @Test
    public void testDefaultConstructorEnsuresTableIsEmpty() {
        assertEquals((Integer)0, table.size());
        assertTrue(table.isEmpty());
    }

    @Test
    public void testSetPrecondition1() {
    	Assertions.assertThrows(ContractViolationException.class, () -> {
    		table.set(null, 100);
		});
    }

    @Test
    public void testSetPrecondition2() {
    	Assertions.assertThrows(ContractViolationException.class, () -> {
    		table.set("Luke", null);
		});
    }

    @Test
    public void testSetPostcondition() {
        table.set("Luke", 100);
        table.set("Han", 200);
        table.set("Leia", 300);
        Integer oldSize = table.size();
        IList<String> oldKeyList = table.keyList();

        table.set("Han", 400);
        assertTrue(!table.isEmpty());
        assertEquals(oldSize, table.size());
        assertEquals(oldKeyList, table.keyList());

        table.set("Yoda", 500);
        assertTrue(!table.isEmpty());
        assertEquals((Integer) (oldSize + 1), table.size());
        assertEquals((Integer) (oldKeyList.size() + 1), table.keyList().size());
    }

    @Test
    public void testGetPrecondition() {
    	Assertions.assertThrows(ContractViolationException.class, () -> {
    		table.get(null);
		});
    }

    @Test
    public void testGetPostcondition() {
        table.set("Luke", 100);
        table.set("Han", 200);
        table.set("Leia", 300);
        Integer oldSize = table.size();
        IList<String> oldKeyList = table.keyList();

        Integer value = table.get("Han");
        assertEquals((Integer) 200, value);
        assertEquals(oldSize, table.size());
        assertEquals(oldKeyList, table.keyList());
        assertTrue(table.contains("Han"));

        Integer val = table.get("Yoda");
        assertTrue(val == null);
        assertEquals(oldSize, table.size());
        assertEquals(oldKeyList, table.keyList());
        assertTrue(!table.contains("Yoda"));
    }

    @Test
    public void testRemovePrecondition() {
    	Assertions.assertThrows(ContractViolationException.class, () -> {
    		table.remove(null);
		});
    }

    @Test
    public void testRemovePostcondition() {
        table.set("Luke", 100);
        table.set("Han", 200);
        table.set("Leia", 300);
        Integer oldSize = table.size();
        IList<String> oldKeyList = table.keyList();

        assertTrue(table.contains("Han"));
        table.remove("Han");
        assertEquals((Integer) (oldSize - 1), table.size());
        assertEquals((Integer) (oldKeyList.size() - 1), table.keyList().size());

        oldSize = table.size();
        oldKeyList = table.keyList();
        assertTrue(!table.contains("Yoda"));
        table.remove("Yoda");
        assertEquals(oldSize, table.size());
        assertEquals(oldKeyList, table.keyList());
    }

    @Test
    public void testClearPostcondition() {
        table.set("Luke", 100);
        table.set("Han", 200);
        table.set("Leia", 300);

        table.clear();
        assertTrue(table.isEmpty());
    }

    @Test
    public void testContainsPrecondition() {
    	Assertions.assertThrows(ContractViolationException.class, () -> {
    		table.contains(null);
		});
    }

    @Test
    public void testContainsPostcondition() {
        table.set("Luke", 100);
        table.set("Han", 200);
        table.set("Leia", 300);
        Integer oldSize = table.size();
        IList<String> oldKeyList = table.keyList();

        Boolean contains = table.contains("Han");
        assertTrue(contains);
        assertEquals(oldSize, table.size());
        assertEquals(oldKeyList, table.keyList());
    }

    @Test
    public void testDeepCopyPostcondition() {
        table.set("Luke", 100);
        table.set("Han", 200);
        table.set("Leia", 300);

        ITable<String, Integer> clone = table.deepCopy();

        assertTrue(table.equals(clone));
    }

    @Test
    public void testKeyListPostcondition() {
        table.set("Luke", 100);
        table.set("Han", 200);
        table.set("Leia", 300);

        IList<String> keyList = table.keyList();

        assertTrue(keyList != null);
        assertTrue(keyList.size() >= 0);
        assertTrue(keyList.size() == 3);
    }
}