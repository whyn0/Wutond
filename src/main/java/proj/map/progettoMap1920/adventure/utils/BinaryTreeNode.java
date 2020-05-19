/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proj.map.progettoMap1920.adventure.utils;

import java.util.logging.Logger;

/**
 *
 * @author whyno
 */
public class BinaryTreeNode<T> {
    private final BinaryTreeNode<T> firstChild;
    private final BinaryTreeNode<T> secondChild;
    private final T value;
    public BinaryTreeNode(BinaryTreeNode<T> firstChild, BinaryTreeNode<T> secondChild,T value) {
        this.firstChild = firstChild;
        this.secondChild = secondChild;
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public BinaryTreeNode<T> getFirstChild() {
        return firstChild;
    }

    public BinaryTreeNode<T> getSecondChild() {
        return secondChild;
    }
    
    
}
