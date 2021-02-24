/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package binaryTree;


/**
 * 
 *  Nome:João Carlos Abreu Lopes 
    Número:8190221
    Turma:T1
 */

/**
 * BinaryTreeNode represents a node in a binary tree with a left and
 * right child.
 */
public class BinaryTreeNode<T> {
    protected T element;
    public BinaryTreeNode<T> left;
    public BinaryTreeNode<T> right;

    /**
     * Creates a new tree node with the specified data.
     *
     * @param element the element that will become a part of the new tree node
     */
    public BinaryTreeNode(T element) {
        this.element = element;
        this.left = null;
        this.right = null;
    }

    /**
     * Returns the number of non-null children of this node. This method may be
     * able to be written more efficiently.
     *
     * @return the integer number of non-null children of this node
     */
    public int numChildren(){
        int children=0;
        
        if (left != null) {
            children = 1 + left.numChildren();
        }

        if (right != null) {
            children = children + 1 + right.numChildren();
        }
        return children;
    }

    public T getElement() {
        return element;
    }

    public void setElement(T element) {
        this.element = element;
    }

    public BinaryTreeNode<T> getLeft() {
        return left;
    }

    public void setLeft(BinaryTreeNode<T> left) {
        this.left = left;
    }

    public BinaryTreeNode<T> getRight() {
        return right;
    }

    public void setRight(BinaryTreeNode<T> right) {
        this.right = right;
    }
 
}
