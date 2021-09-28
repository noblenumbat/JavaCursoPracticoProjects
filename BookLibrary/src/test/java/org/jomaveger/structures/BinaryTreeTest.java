package org.jomaveger.structures;

import static org.junit.jupiter.api.Assertions.*;

import org.jomaveger.lang.dbc.exceptions.ContractViolationException;
import org.junit.jupiter.api.*;

public class BinaryTreeTest {

    private IBinaryTree<Integer> tree;
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

    private IBinaryTree<Integer> makeTree() {
       IBinaryTree<Integer> tree = new BinaryTree<>(1);

        BinaryNode<Integer> left = new BinaryNode<>(2, new BinaryNode<>(4, null, null), new BinaryNode<>(5, null, null));

        tree.getRoot().setRight(new BinaryNode<>(3, null, null));
        tree.getRoot().setLeft(left);
        return tree;
    }

    @BeforeEach
    public void setUp() {
       tree = new BinaryTree<>();
    }

    @AfterEach
    public void tearDown() {
        tree = null;
    }

    @Test
    public void testDefaultConstructorEnsuresTreeIsEmpty() {
        assertTrue(tree.isEmpty());
    }

    @Test
    public void testConstructorPrecondition() {
    	Assertions.assertThrows(ContractViolationException.class, () -> {
    		tree = new BinaryTree<>(null);
		});
    }

    @Test
    public void testConstructorPostcondition() {
        tree = new BinaryTree<>(tres);
        assertTrue(!tree.isEmpty());
        assertTrue(tree.getRoot().getElem() == tres);
    }

    @Test
    public void testGetRootPostcondition() {
        tree = new BinaryTree<>(tres);
        BinaryNode<Integer> root = tree.getRoot();
        assertTrue(!tree.isEmpty() || root == null);
        assertTrue(root != null);
    }

    @Test
    public void testGetLeftChildPrecondition() {
    	Assertions.assertThrows(ContractViolationException.class, () -> {
    		tree.getLeftChild();
		});
    }

    @Test
    public void testGetRightChildPrecondition() {
    	Assertions.assertThrows(ContractViolationException.class, () -> {
    		tree.getRightChild();
		});
    }

    @Test
    public void testGetLeftChildPostcondition() {
        tree = makeTree();
        BinaryNode<Integer> leftChild = tree.getLeftChild();
        assertTrue(leftChild != null);
        assertTrue(leftChild.getElem() == 2);
    }

    @Test
    public void testGetRightChildPostcondition() {
        tree = makeTree();
        BinaryNode<Integer> rightChild = tree.getRightChild();
        assertTrue(rightChild != null);
        assertTrue(rightChild.getElem() == 3);
    }

    @Test
    public void testIsEmptyPostcondition() {
        tree = makeTree();
        assertTrue(!tree.isEmpty());
    }

    @Test
    public void testMakeEmptyPostcondition() {
        tree = makeTree();
        assertTrue(!tree.isEmpty());
        tree.makeEmpty();
        assertTrue(tree.isEmpty());
    }

    @Test
    public void testIsBalancedTrue() {
        tree = makeTree();
        Boolean isBalanced = tree.isBalanced();
        assertTrue(isBalanced);
    }

    @Test
    public void testIsBalancedFalse() {
        tree = makeAnotherTree();
        Boolean isBalanced = tree.isBalanced();
        assertTrue(!isBalanced);
    }

    @Test
    public void testHeightPostcondition() {
        tree = makeTree();
        Integer height = tree.height();
        assertTrue(height >= 0);
        assertTrue(height == 3);
    }

    @Test
    public void testSizePostcondition() {
        tree = makeTree();
        Integer size = tree.size();
        assertTrue(size >= 0);
        assertTrue(size == 5);
    }

    @Test
    public void testLeavesPostcondition() {
        tree = makeTree();
        Integer leaves = tree.leaves();
        assertTrue(leaves >= 0);
        assertTrue(leaves == 3);
    }

    @Test
    public void testPreOrderRecPostcondition() {
        tree = makeTree();
        IList<Integer> list = tree.preorder();
        assertTrue(list.size() >= 0);
        String result =
            list.toString().replaceFirst("org.jomaveger.structures.LinkedList", "");
        assertEquals("[1, 2, 4, 5, 3, ]", result);
    }

    @Test
    public void testInOrderRecPostcondition() {
        tree = makeTree();
        IList<Integer> list = tree.inorder();
        assertTrue(list.size() >= 0);
        String result =
            list.toString().replaceFirst("org.jomaveger.structures.LinkedList", "");
        assertEquals("[4, 2, 5, 1, 3, ]", result);
    }

    @Test
    public void testPostOrderRecPostcondition() {
        tree = makeTree();
        IList<Integer> list = tree.postorder();
        assertTrue(list.size() >= 0);
        String result =
            list.toString().replaceFirst("org.jomaveger.structures.LinkedList", "");
        assertEquals("[4, 5, 2, 3, 1, ]", result);
    }

    @Test
    public void testPreOrderItPostcondition() {
        tree = makeAnotherTree();
        IList<Integer> list = tree.preorder();
        assertTrue(list.size() >= 0);
        String result =
            list.toString().replaceFirst("org.jomaveger.structures.LinkedList", "");
        assertEquals("[1, 2, 4, 7, 5, 3, ]", result);
    }

    @Test
    public void testInOrderItPostcondition() {
        tree = makeAnotherTree();
        IList<Integer> list = tree.inorder();
        assertTrue(list.size() >= 0);
        String result =
            list.toString().replaceFirst("org.jomaveger.structures.LinkedList", "");
        assertEquals("[4, 7, 2, 5, 1, 3, ]", result);
    }

    @Test
    public void testPostOrderItPostcondition() {
        tree = makeAnotherTree();
        IList<Integer> list = tree.postorder();
        assertTrue(list.size() >= 0);
        String result =
            list.toString().replaceFirst("org.jomaveger.structures.LinkedList", "");
        assertEquals("[7, 4, 5, 2, 3, 1, ]", result);
    }

    @Test
    public void testLevelOrderPostcondition() {
        tree = makeTree();
        IList<Integer> list = tree.levelorder();
        assertTrue(list.size() >= 0);
        String result =
            list.toString().replaceFirst("org.jomaveger.structures.LinkedList", "");
        assertEquals("[1, 2, 3, 4, 5, ]", result);
    }

    @Test
    public void testDeepCopyPostcondition() {
        tree = makeTree();

        IBinaryTree<Integer> clone = tree.deepCopy();

        assertTrue(tree.equals(clone));
    }

    private IBinaryTree<Integer> makeAnotherTree() {
        IBinaryTree<Integer> tree = new BinaryTree<>(1);

        BinaryNode<Integer> left = new BinaryNode<>(2, new BinaryNode<>(4, null, new BinaryNode<>(7, null, null)), new BinaryNode<>(5, null, null));

        tree.getRoot().setRight(new BinaryNode<>(3, null, null));
        tree.getRoot().setLeft(left);
        return tree;
    }
}