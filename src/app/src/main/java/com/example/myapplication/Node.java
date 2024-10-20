package com.example.myapplication;

/**
 * Node used for constructing red-black tree
 * The value of each node is the String of each token.
 * @author Qifeng Zheng
 */
public class Node {
    String value;
    Node left, right, parent;
    boolean isRed; // true if red, false if black

    public Node(String value) {
        this.value = value;
        left = right = parent = null;
        isRed = true; // By default, newly inserted nodes are red
    }
}
