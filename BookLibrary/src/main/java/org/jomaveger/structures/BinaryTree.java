package org.jomaveger.structures;

import java.io.Serializable;
import java.util.Objects;
import org.jomaveger.lang.DeepCloneable;
import org.jomaveger.lang.dbc.Contract;

public class BinaryTree<T> implements IBinaryTree<T>, Serializable {

    private BinaryNode<T> root;

    private IList<T> preorder;
    private IList<T> inorder;
    private IList<T> postorder;
    private IList<T> levelorder;

    public BinaryTree() {
        this.root = null;
        
        Contract.ensure(isEmpty());
    }

    public BinaryTree(final T rootItem) {
    	Contract.require(rootItem != null);
    	
        this.root = new BinaryNode<>(rootItem, null, null);
        
        Contract.ensure(!isEmpty() && root.getElem() == rootItem);
    }

    @Override
    public BinaryNode<T> getRoot() {
        BinaryNode<T> root = this.root;
        
        Contract.ensure(!isEmpty() || root == null);
        return root;
    }

    @Override
    public BinaryNode<T> getLeftChild() {
    	Contract.require(!isEmpty());
        return this.root.getLeft();
    }

    @Override
    public BinaryNode<T> getRightChild() {
    	Contract.require(!isEmpty());
        return this.root.getRight();
    }

    @Override
    public Boolean isEmpty() {
        Boolean condition = (this.root == null);
        
        Contract.ensure(condition == (size() == 0));
        return condition;
    }

    @Override
    public void makeEmpty() {
        this.root = null;
        Contract.ensure(isEmpty());
    }

    @Override
    public Boolean isBalanced() {
        Boolean isBalanced = this.recIsBalanced(this.root);
        return isBalanced;
    }

    private Boolean recIsBalanced(BinaryNode<T> node) {
        Integer lh;
        Integer rh;

        if (node == null)
            return Boolean.TRUE;

        lh = recHeight(node.getLeft());
        rh = recHeight(node.getRight());

        if (Math.abs(lh - rh) <= 1
                && recIsBalanced(node.getLeft())
                && recIsBalanced(node.getRight()))
            return Boolean.TRUE;

        return Boolean.FALSE;
    }

    @Override
    public Integer height() {
        Integer result = this.recHeight(this.root);
        Contract.ensure(result >= 0);
        return result;
    }

    private Integer recHeight(BinaryNode<T> node) {
        if (node == null) {
           return 0;
        } else {
           return 1 + Math.max(this.recHeight(node.getLeft()), this.recHeight(node.getRight()));
        }
    }

    @Override
    public Integer size() {
        Integer result = this.recSize(this.root);
        Contract.ensure(result >= 0);
        return result;
    }

    private Integer recSize(BinaryNode<T> node) {
        if (node == null) {
           return 0;
        } else {
            return 1 + this.recSize(node.getLeft()) + this.recSize(node.getRight());
        }
    }

    @Override
    public Integer leaves() {
        Integer result = this.recLeaves(this.root);
        Contract.ensure(result >= 0);
        return result;
    }

    private Integer recLeaves(BinaryNode<T> node) {
        if (node == null) {
            return 0;
        } else if (node.getLeft() == null && node.getRight() == null) {
            return 1;
        } else {
           return this.recLeaves(node.getLeft()) + this.recLeaves(node.getRight());
        }
    }

    @Override
    public IList<T> preorder() {
        this.preorder = new LinkedList<>();
        if (this.isBalanced()) {
           this.preorder = this.recPreorder(this.root);
        } else {
           this.preorder = this.itPreorder(this.root);
        }
        Contract.ensure(this.preorder.size() >= 0);
        return this.preorder;
    }

    private IList<T> recPreorder(BinaryNode<T> node) {
        if (node == null) {
            return this.preorder;
        } else {
            this.preorder.addLast(node.getElem());
            this.recPreorder(node.getLeft());
            this.recPreorder(node.getRight());
        }

        return this.preorder;
    }

    private IList<T> itPreorder(BinaryNode<T> node) {
        if (node == null) {
            return this.preorder;
        } else {
            IStack<BinaryNode<T>> stack = new LinkedStack<>();
            stack.push(node);

           while (!stack.isEmpty()) {

                BinaryNode<T> curr = stack.peek();
                stack.pop();

                this.preorder.addLast(curr.getElem());

                if (curr.getRight() != null) {
                    stack.push(curr.getRight());
                }

                if (curr.getLeft() != null) {
                    stack.push(curr.getLeft());
                }
            }

            return this.preorder;
        }
    }


    @Override
    public IList<T> inorder() {
        this.inorder = new LinkedList<>();
        if (this.isBalanced()) {
           this.inorder = this.recInorder(this.root);
        } else {
            this.inorder = this.itInorder(this.root);
        }
        Contract.ensure(this.inorder.size() >= 0);
        return this.inorder;
    }

    private IList<T> itInorder(BinaryNode<T> node) {
       if (node == null) {
           return this.inorder;
       } else {
           IStack<BinaryNode<T>> stack = new LinkedStack<>();
           BinaryNode<T> curr = node;

           while (!stack.isEmpty() || curr != null) {

                if (curr != null) {
                   stack.push(curr);
                   curr = curr.getLeft();
                }
                else {
                    curr = stack.peek();
                    stack.pop();

                    this.inorder.addLast(curr.getElem());

                    curr = curr.getRight();
                }
            }

            return this.inorder;
       }
    }

    private IList<T> recInorder(BinaryNode<T> node) {
        if (node == null) {
           return this.inorder;
        } else {
            this.recInorder(node.getLeft());
            this.inorder.addLast(node.getElem());
            this.recInorder(node.getRight());
        }

        return this.inorder;
    }

    @Override
    public IList<T> postorder() {
        this.postorder = new LinkedList<>();
        if (this.isBalanced()) {
           this.postorder = this.recPostorder(this.root);
        } else {
            this.postorder = this.itPostorder(this.root);
        }
        Contract.ensure(this.postorder.size() >= 0);
        return this.postorder;
    }

    private IList<T> itPostorder(BinaryNode<T> node) {
        if (node == null) {
           return this.postorder;
        } else {
            IStack<BinaryNode<T>> stack = new LinkedStack<>();
            stack.push(node);

            while (!stack.isEmpty()) {

                BinaryNode<T> curr = stack.peek();
                stack.pop();

                this.postorder.addFirst(curr.getElem());

                if (curr.getLeft() != null) {
                    stack.push(curr.getLeft());
                }

                if (curr.getRight() != null) {
                    stack.push(curr.getRight());
                }
            }

            return this.postorder;
       }
    }

    private IList<T> recPostorder(BinaryNode<T> node) {
        if (node == null) {
            return this.postorder;
        } else {
            this.recPostorder(node.getLeft());
            this.recPostorder(node.getRight());
            this.postorder.addLast(node.getElem());
        }

        return postorder;
    }

    @Override
    public IList<T> levelorder() {
       this.levelorder = new LinkedList<>();
       BinaryNode<T> next, child;

       if (!this.isEmpty()) {
           IQueue<BinaryNode<T>> q = new LinkedQueue<>();
           q.enqueue(this.root);
           while (!q.isEmpty()) {
                next = q.front();
                q.dequeue();
                this.levelorder.addLast(next.getElem());
                child = next.getLeft();
                if (child != null) {
                    q.enqueue(child);
                }
                child = next.getRight();
                if (child != null) {
                   q.enqueue(child);
                }
            }
        }
        Contract.ensure(this.levelorder.size() >= 0);
        return this.levelorder;
    }

    @Override
    public IBinaryTree<T> deepCopy() {
        IBinaryTree<T> deepCopy;
        try {
            deepCopy = DeepCloneable.deepCopy(this);
        } catch (Exception e) {
            deepCopy = new BinaryTree<>();
        }
        Contract.ensure(deepCopy.equals(this) || deepCopy.isEmpty());
        return deepCopy;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append(this.getClass().getName() + "[");

        for (T elem: this.levelorder()) {
            string.append(elem).append(", ");
        }

        string.append("]");
        return string.toString();
    }

    @Override
    public boolean equals(Object otherObject) {
        if (this == otherObject) return true;

        if (otherObject == null
                || this.getClass() != otherObject.getClass())
            return false;

        BinaryTree<T> that = (BinaryTree<T>) otherObject;

        if (!Objects.equals(this.size(), that.size()))
            return false;

        return Objects.equals(this.levelorder(), that.levelorder());
    }

    @Override
    public int hashCode() {
        Object[] array = new Object[this.size() + 1];
        array[0] = this.size();

        Integer i = 1;
        for (T elem: this.levelorder()) {
            array[i] = elem;
            i++;
        }

        return Objects.hash(array);
    }
}