package com.example.myapplication;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Red black tree which helps the tokenizer to search faster
 * The main methods in this class are insert() and listToRedBlackTree() to change String input into a tree,
 * and inOrderTraversal() to traverse the input tokens(i.e. nodes in the tree).
 * @author Qifeng Zheng
 */
public class RedBlackTree {
    private Node root;

    public RedBlackTree() {
        root = null;
    }

    private void insert(String value) {
        Node newNode = new Node(value);
        if (root == null) {
            root = newNode;
        } else {
            insertRecursively(root, newNode);
        }
        fixViolation(newNode);
    }

    // Helper method for insertion
    private void insertRecursively(Node root, Node newNode) {
        if (root.value.compareTo(newNode.value) > 0) {
            if (root.left == null) {
                root.left = newNode;
                newNode.parent = root;
            } else {
                insertRecursively(root.left, newNode);
            }
        } else {
            if (root.right == null) {
                root.right = newNode;
                newNode.parent = root;
            } else {
                insertRecursively(root.right, newNode);
            }
        }
    }

    // Fix violation after insertion
    private void fixViolation(Node newNode) {
        Node parent = null;
        Node grandParent = null;
        while (newNode != root && newNode.isRed && newNode.parent.isRed) {
            parent = newNode.parent;
            grandParent = parent.parent;
            // Case 1: Parent is left child of Grandparent
            if (parent == grandParent.left) {
                Node uncle = grandParent.right;
                if (uncle != null && uncle.isRed) {
                    grandParent.isRed = true;
                    parent.isRed = false;
                    uncle.isRed = false;
                    newNode = grandParent;
                } else {
                    if (newNode == parent.right) {
                        leftRotate(parent);
                        newNode = parent;
                        parent = newNode.parent;
                    }
                    rightRotate(grandParent);
                    boolean tempColor = parent.isRed;
                    parent.isRed = grandParent.isRed;
                    grandParent.isRed = tempColor;
                    newNode = parent;
                }
            } else { // Case 2: Parent is right child of Grandparent
                Node uncle = grandParent.left;
                if (uncle != null && uncle.isRed) {
                    grandParent.isRed = true;
                    parent.isRed = false;
                    uncle.isRed = false;
                    newNode = grandParent;
                } else {
                    if (newNode == parent.left) {
                        newNode = parent;
                        rightRotate(newNode);
                        parent = newNode.parent;
                    }
                    leftRotate(grandParent);
                    boolean tempColor = parent.isRed;
                    parent.isRed = grandParent.isRed;
                    grandParent.isRed = tempColor;
                    newNode = parent;
                }
            }
        }
        root.isRed = false;
    }

    // Left rotation
    private void leftRotate(Node x) {
        Node y = x.right;
        x.right = y.left;
        if (y.left != null)
            y.left.parent = x;
        y.parent = x.parent;
        if (x.parent == null)
            root = y;
        else if (x == x.parent.left)
            x.parent.left = y;
        else
            x.parent.right = y;
        y.left = x;
        x.parent = y;
    }

    // Right rotation
    private void rightRotate(Node y) {
        Node x = y.left;
        y.left = x.right;
        if (x.right != null)
            x.right.parent = y;
        x.parent = y.parent;
        if (y.parent == null)
            root = x;
        else if (y == y.parent.right)
            y.parent.right = x;
        else
            y.parent.left = x;
        x.right = y;
        y.parent = x;
    }

    public String inOrderTraversal(int n) {
        return inOrderHelper(root, n, new AtomicInteger(0));
    }

    private String inOrderHelper(Node node, int n, AtomicInteger count) {
        if (node == null)
            return null;

        // In-order traversal to get the n-th word
        String left = inOrderHelper(node.left, n, count);
        if (left != null)
            return left;

        if (count.get() == n)
            return node.value;

        count.incrementAndGet();

        return inOrderHelper(node.right, n, count);
    }

    // Method to convert string list to Red-Black Tree
    public static RedBlackTree listToRedBlackTree(String[] stringList) {
        RedBlackTree rbTree = new RedBlackTree();
        for (String str : stringList) {
            rbTree.insert(str);
        }
        return rbTree;
    }
}
