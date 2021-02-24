/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package avlBinarySearchTree;

/**
 *
 * Nome:João Carlos Abreu Lopes Número:8190221 Turma:T1
 */
public class BinaryTreeAVLNode<T> {
    protected T element;
    protected BinaryTreeAVLNode<T> left;
    protected BinaryTreeAVLNode<T> right;
    protected int height; 

    /**
     * Creates a new tree node with the specified data.
     *
     * @param element the element that will become a part of the new tree node
     */
    public BinaryTreeAVLNode(T element) {
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

    public BinaryTreeAVLNode<T> getLeft() {
        return left;
    }

    public void setLeft(BinaryTreeAVLNode<T> left) {
        this.left = left;
    }

    public BinaryTreeAVLNode<T> getRight() {
        return right;
    }

    public void setRight(BinaryTreeAVLNode<T> right) {
        this.right = right;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
 

    
}
