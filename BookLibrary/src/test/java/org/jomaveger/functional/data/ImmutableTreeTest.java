package org.jomaveger.functional.data;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import java.util.function.Function;

public class ImmutableTreeTest {

	@Test
	public void testInsert() {
		ImmutableTree<Integer> tree = ImmutableTree.<Integer>empty().insert(4)
										.insert(2).insert(6).insert(1)
										.insert(3).insert(7).insert(5);
		assertEquals("(T (T (T E 1 E) 2 (T E 3 E)) 4 (T (T E 5 E) 6 (T E 7 E)))", tree.toString());
	}
	
	@Test
	public void testMemberTrue() {
		ImmutableTree<Integer> tree = ImmutableTree.<Integer>empty().insert(4)
										.insert(2).insert(6)
										.insert(1).insert(3)
										.insert(7).insert(5);
		assertTrue(tree.member(3));
	}

	@Test
	public void testMemberFalse() {
		ImmutableTree<Integer> tree = ImmutableTree.<Integer>empty().insert(4)
										.insert(2).insert(6).insert(1)
										.insert(3).insert(7).insert(5);
		assertFalse(tree.member(8));
	}
	
	@Test
	public void testTree() throws Exception {
		ImmutableTree<Integer> tree = ImmutableTree.tree(4, 2, 6, 1, 3, 7, 5);
		assertEquals("(T (T (T E 1 E) 2 (T E 3 E)) 4 (T (T E 5 E) 6 (T E 7 E)))", tree.toString());
	}
	
	@Test
	public void testSize() throws Exception {
		ImmutableTree<Integer> tree = ImmutableTree.tree(4, 2, 6, 1, 3, 7, 5);
		assertEquals(7, tree.size());
	}

	@Test
	public void testHeight() throws Exception {
		ImmutableTree<Integer> tree = ImmutableTree.tree(4, 2, 6, 1, 3, 7, 5);
		assertEquals(2, tree.height());
	}

	@Test
	public void testSize2() throws Exception {
		ImmutableTree<Integer> tree = ImmutableTree.tree(1, 2, 3, 4, 5, 6, 7);
		assertEquals(7, tree.size());
	}

	@Test
	public void testHeight2() throws Exception {
		ImmutableTree<Integer> tree = ImmutableTree.tree(1, 2, 3, 4, 5, 6, 7);
		assertEquals(6, tree.height());
	}
	
	@Test
	public void testMax() throws Exception {
		ImmutableTree<Integer> tree = ImmutableTree.tree(4, 2, 6, 1, 3, 7, 5);
		assertTrue(tree.max().map(i -> i.equals(7)).getOrElse(false));
	}

	@Test
	public void testMin() throws Exception {
		ImmutableTree<Integer> tree = ImmutableTree.tree(4, 2, 6, 1, 3, 7, 5);
		assertTrue(tree.min().map(i -> i.equals(1)).getOrElse(false));
	}

	@Test
	public void testMax2() throws Exception {
		ImmutableTree<Integer> tree = ImmutableTree.tree(1, 2, 3, 4, 5, 6, 7);
		assertTrue(tree.max().map(i -> i.equals(7)).getOrElse(false));
	}

	@Test
	public void testMin2() throws Exception {
		ImmutableTree<Integer> tree = ImmutableTree.tree(1, 2, 3, 4, 5, 6, 7);
		assertTrue(tree.min().map(i -> i.equals(1)).getOrElse(false));
	}

	@Test
	public void testRemoveLower() {
		ImmutableTree<Integer> tree = ImmutableTree.tree(4, 2, 1, 3, 6, 5, 7);
		assertEquals("(T (T E 1 (T E 3 E)) 4 (T (T E 5 E) 6 (T E 7 E)))", tree.remove(2).toString());
	}

	@Test
	public void testRemoveEquals() {
		ImmutableTree<Integer> tree = ImmutableTree.tree(4, 2, 1, 3, 6, 5, 7);
		assertEquals("(T (T E 1 E) 2 (T E 3 (T (T E 5 E) 6 (T E 7 E))))", tree.remove(4).toString());
	}

	@Test
	public void testRemoveHigher() {
		ImmutableTree<Integer> tree = ImmutableTree.tree(4, 2, 1, 3, 6, 5, 7);
		assertEquals("(T (T (T E 1 E) 2 (T E 3 E)) 4 (T E 5 (T E 7 E)))", tree.remove(6).toString());
	}

	@Test
	public void testRemoveEmpty() {
		ImmutableTree<Integer> tree = ImmutableTree.tree();
		assertEquals("E", tree.remove(6).toString());
	}
	
	ImmutableTree<Integer> tree1 = ImmutableList.of(3, 1, 5, 0, 2, 4, 6, 7).foldLeft(ImmutableTree.empty(), (t, a) -> t.insert(a));
	ImmutableTree<Integer> tree2 = ImmutableList.of(5, 3, 7, 1, 4, 6, 8, -2).foldLeft(ImmutableTree.empty(), (t, a) -> t.insert(a));
	ImmutableTree<Integer> tree3 = ImmutableList.of(3, 7, 1, 4, 6, 8, -1, 9).foldLeft(ImmutableTree.empty(), (t, a) -> t.insert(a));

	@Test
	public void testMerge1() {
		assertEquals("(T (T (T (T E -2 E) 0 E) 1 (T E 2 E)) 3 (T (T E 4 E) 5 (T E 6 (T E 7 (T E 8 E)))))",
				tree1.merge(tree2).toString());
		assertEquals("(T (T (T (T E -2 (T E 0 E)) 1 (T E 2 E)) 3 (T E 4 E)) 5 (T (T E 6 E) 7 (T E 8 E)))",
				tree2.merge(tree1).toString());
	}

	@Test
	public void testMerge2() {
		assertEquals("(T (T (T (T E -1 E) 0 E) 1 (T E 2 E)) 3 (T (T E 4 E) 5 (T E 6 (T E 7 (T E 8 (T E 9 E))))))",
				tree1.merge(tree3).toString());
		assertEquals("(T (T (T E -1 (T E 0 E)) 1 (T E 2 E)) 3 (T (T E 4 (T (T E 5 E) 6 E)) 7 (T E 8 (T E 9 E))))",
				tree3.merge(tree1).toString());
	}
	
	@Test
	public void testFoldInOrder_inOrderLeft() {
		Function<String, Function<Integer, Function<String, String>>> f = s1 -> i -> s2 -> s1 + i + s2;
		ImmutableTree<Integer> tree = ImmutableTree.tree(4, 2, 1, 3, 6, 5, 7);
		assertEquals("1234567", tree.foldInOrder("", f));
	}

	@Test
	public void testFoldPreOrder_preOrderLeft() {
		Function<Integer, Function<String, Function<String, String>>> f = i -> s1 -> s2 -> i + s1 + s2;
		ImmutableTree<Integer> tree = ImmutableTree.tree(4, 2, 1, 3, 6, 5, 7);
		assertEquals("4213657", tree.foldPreOrder("", f));
	}

	@Test
	public void testFoldPostOrder_postOrderLeft() {
		Function<String, Function<String, Function<Integer, String>>> f = s1 -> s2 -> i -> s1 + s2 + i;
		ImmutableTree<Integer> tree = ImmutableTree.tree(4, 2, 1, 3, 6, 5, 7);
		assertEquals("1325764", tree.foldPostOrder("", f));
	}
	
	@Test
	public void testMergeLeftEmpty() {
		ImmutableTree<Integer> tree = ImmutableTree.tree(6, 7, 5, 9, 8);
		ImmutableTree<Integer> result = ImmutableTree.tree(ImmutableTree.empty(), 4, tree);
		assertEquals("(T E 4 (T (T E 5 E) 6 (T E 7 (T (T E 8 E) 9 E))))", result.toString());
	}

	@Test
	public void testMergeLeftEmptyNok() {
		ImmutableTree<Integer> tree = ImmutableTree.tree(4, 6, 7, 5, 2, 1, 0);
		ImmutableTree<Integer> result = ImmutableTree.tree(ImmutableTree.empty(), 4, tree);
		assertEquals("(T (T (T (T E 0 E) 1 E) 2 E) 4 (T (T E 5 E) 6 (T E 7 E)))", result.toString());
	}

	@Test
	public void testMergeRightEmpty() {
		ImmutableTree<Integer> tree = ImmutableTree.tree(4, 6, 7, 5, 2, 1, 0);
		ImmutableTree<Integer> result = ImmutableTree.tree(tree, 4, ImmutableTree.<Integer>empty());
		assertEquals("(T (T (T (T E 0 E) 1 E) 2 E) 4 (T (T E 5 E) 6 (T E 7 E)))", result.toString());
	}

	@Test
	public void testMergeNok() {
		ImmutableTree<Integer> tree1 = ImmutableTree.tree(4, 6, 7, 5, 2);
		ImmutableTree<Integer> tree2 = ImmutableTree.tree(7, 5, 2, 1, 0);
		ImmutableTree<Integer> result = ImmutableTree.tree(tree1, 4, tree2);
		assertEquals("(T (T (T (T E 0 E) 1 E) 2 E) 4 (T (T E 5 E) 6 (T E 7 E)))", result.toString());
	}

	@Test
	public void testMergeEmpty() {
		ImmutableTree<Integer> result = ImmutableTree.tree(ImmutableTree.<Integer>empty(), 4, ImmutableTree.<Integer>empty());
		assertEquals("(T E 4 E)", result.toString());
	}

	@Test
	public void testMerge() {
		ImmutableTree<Integer> tree1 = ImmutableTree.tree(2, 1, 3);
		ImmutableTree<Integer> tree2 = ImmutableTree.tree(6, 5, 7);
		ImmutableTree<Integer> result = ImmutableTree.tree(tree1, 4, tree2);
		assertEquals("(T (T (T E 1 E) 2 (T E 3 E)) 4 (T (T E 5 E) 6 (T E 7 E)))", result.toString());
		assertEquals("[4, 2, 1, 3, 6, 5, 7, NIL]", 
			result.foldPreOrder(ImmutableList.<Integer>empty(), i -> l1 -> l2 -> ImmutableList.of(i).append(l1).append(l2)).toString());
	}

	@Test
	public void testMergeInverseOrder() {
		ImmutableTree<Integer> tree1 = ImmutableTree.tree(2, 1, 3);
		ImmutableTree<Integer> tree2 = ImmutableTree.tree(6, 5, 7);
		ImmutableTree<Integer> result = ImmutableTree.tree(tree2, 4, tree1);
		assertEquals("(T (T (T E 1 E) 2 (T E 3 E)) 4 (T (T E 5 E) 6 (T E 7 E)))", result.toString());
		assertEquals("[4, 2, 1, 3, 6, 5, 7, NIL]", 
			result.foldPreOrder(ImmutableList.<Integer>empty(), i -> l1 -> l2 -> ImmutableList.of(i).append(l1).append(l2)).toString());
	}

	@Test
	public void testTreeFold1() {
		ImmutableList<Integer> list = ImmutableList.of(4, 6, 7, 5, 2, 1, 3);
		ImmutableTree<Integer> tree0 = ImmutableTree.tree(list);
		ImmutableTree<Integer> tree1 = tree0.foldInOrder(ImmutableTree.<Integer>empty(), t1 -> i -> t2 -> ImmutableTree.tree(t1, i, t2));
		assertEquals(tree0.toString(), tree1.toString());
		ImmutableTree<Integer> tree2 = tree0.foldPostOrder(ImmutableTree.<Integer>empty(), t1 -> t2 -> i -> ImmutableTree.tree(t1, i, t2));
		assertEquals(tree0.toString(), tree2.toString());
		ImmutableTree<Integer> tree3 = tree0.foldPreOrder(ImmutableTree.<Integer>empty(), i -> t1 -> t2 -> ImmutableTree.tree(t1, i, t2));
		assertEquals(tree0.toString(), tree3.toString());
	}
	
	@Test
	public void testMap() {
		ImmutableTree<Integer> tree = ImmutableTree.tree(4, 2, 1, 3, 6, 5, 7);
		ImmutableTree<Integer> result = tree.map(x -> x + 2);
		assertEquals("(T (T (T E 3 E) 4 (T E 5 E)) 6 (T (T E 7 E) 8 (T E 9 E)))", result.toString());
	}

	@Test
	public void testMap2() {
		ImmutableTree<Integer> tree = ImmutableTree.tree(-4, 2, -1, 3, 6, -5, 7);
		ImmutableTree<Integer> result = tree.map(x -> x * x);
		assertEquals("(T (T (T E 1 E) 4 (T E 9 E)) 16 (T E 25 (T E 36 (T E 49 E))))", result.toString());
	}
	
	@Test
	public void testRotateLeft() {
		ImmutableTree<Integer> tree = ImmutableTree.tree(4, 6, 7, 5, 2, 1, 3);
		assertEquals("(T (T (T E 1 E) 2 (T E 3 E)) 4 (T (T E 5 E) 6 (T E 7 E)))", tree.toString());
		ImmutableTree<Integer> tree1 = tree.rotateLeft();
		assertEquals("(T (T (T (T E 1 E) 2 (T E 3 E)) 4 (T E 5 E)) 6 (T E 7 E))", tree1.toString());
		ImmutableTree<Integer> tree2 = tree1.rotateLeft();
		assertEquals("(T (T (T (T (T E 1 E) 2 (T E 3 E)) 4 (T E 5 E)) 6 E) 7 E)", tree2.toString());
		ImmutableTree<Integer> tree3 = tree2.rotateLeft();
		assertEquals("(T (T (T (T (T E 1 E) 2 (T E 3 E)) 4 (T E 5 E)) 6 E) 7 E)", tree3.toString());
	}

	@Test
	public void testRotateRight() {
		ImmutableTree<Integer> tree = ImmutableTree.tree(7, 6, 5, 4, 3, 2, 1);
		ImmutableTree<Integer> tree1 = tree.rotateRight();
		assertEquals("(T (T (T (T (T (T E 1 E) 2 E) 3 E) 4 E) 5 E) 6 (T E 7 E))", tree1.toString());
		ImmutableTree<Integer> tree2 = tree1.rotateRight();
		assertEquals("(T (T (T (T (T E 1 E) 2 E) 3 E) 4 E) 5 (T E 6 (T E 7 E)))", tree2.toString());
		ImmutableTree<Integer> tree3 = tree2.rotateRight();
		assertEquals("(T (T (T (T E 1 E) 2 E) 3 E) 4 (T E 5 (T E 6 (T E 7 E))))", tree3.toString());
		ImmutableTree<Integer> tree4 = tree3.rotateRight();
		assertEquals("(T (T (T E 1 E) 2 E) 3 (T E 4 (T E 5 (T E 6 (T E 7 E)))))", tree4.toString());
		ImmutableTree<Integer> tree5 = tree4.rotateRight();
		assertEquals("(T (T E 1 E) 2 (T E 3 (T E 4 (T E 5 (T E 6 (T E 7 E))))))", tree5.toString());
		ImmutableTree<Integer> tree6 = tree5.rotateRight();
		assertEquals("(T E 1 (T E 2 (T E 3 (T E 4 (T E 5 (T E 6 (T E 7 E)))))))", tree6.toString());
		ImmutableTree<Integer> tree7 = tree6.rotateRight();
		assertEquals("(T E 1 (T E 2 (T E 3 (T E 4 (T E 5 (T E 6 (T E 7 E)))))))", tree7.toString());
	}
	
	@Test
	public void testToListInOrderRight() {
		ImmutableTree<Integer> tree2 = ImmutableTree.tree(4, 6, 7, 5, 2, 1, 3);
		assertEquals("[7, 6, 5, 4, 3, 2, 1, NIL]", tree2.toListInOrderRight().toString());
	}
	
	@Test
	public void testToListInOrder() {
		ImmutableTree<Integer> tree2 = ImmutableTree.tree(4, 6, 7, 5, 2, 1, 3);
		assertEquals("[1, 2, 3, 4, 5, 6, 7, NIL]", tree2.toListInOrder().toString());
	}
}
