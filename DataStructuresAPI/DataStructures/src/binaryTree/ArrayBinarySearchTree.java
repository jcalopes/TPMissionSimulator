/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package binaryTree;

import binaryTree.ArrayBinaryTree;
import exceptions.ElementNotFoundException;
import exceptions.InvalidOperationException;
import interfaces.BinarySearchTreeADT;

/**
 *
 * Nome:João Carlos Abreu Lopes Número:8190221 Turma:T1
 */
/**
 * ArrayBinarySearchTree implements a binary search tree using an array.
 *
 */
public class ArrayBinarySearchTree<T> extends ArrayBinaryTree<T> implements BinarySearchTreeADT<T> {

    protected int height;
    protected int maxIndex;

    /**
     * Creates an empty binary search tree.
     */
    public ArrayBinarySearchTree() {
        super();
    }

    /**
     * Creates a binary search with the specified element as its root
     *
     * @param element the element that will become the root of the new tree
     */
    public ArrayBinarySearchTree(T element) {
        super(element);
    }

    /**
     * Adds the specified object to this binary search tree in the appropriate
     * position according to its key value. Note that equal elements are added
     * to the right. Also note that the index of the left child of the current
     * index can be found by doubling the current index and adding 1. Finding
     * the index of the right child can be calculated by doubling the current
     * index and adding 2.
     *
     * @param element the element to be added to the search tree
     */
    @Override
    public void addElement(T element) {
        if (tree.length < maxIndex * 2 + 3) {
            expandCapacity();
        }
        Comparable<T> tempelement = (Comparable<T>) element;
        if (isEmpty()) {
            tree[0] = element;
            maxIndex = 0;
        } else {

            boolean added = false;
            int currentIndex = 0;
            while (!added) {
                if (tempelement.compareTo((tree[currentIndex])) < 0) {
                    if (tree[currentIndex * 2 + 1] == null) {
                        tree[currentIndex * 2 + 1] = element;
                        added = true;
                        if (currentIndex * 2 + 1 > maxIndex) {
                            maxIndex = currentIndex * 2 + 1;
                        }
                    } else {
                        currentIndex = currentIndex * 2 + 1;
                    }
                } else {
                    if (tree[currentIndex * 2 + 2] == null) {
                        tree[currentIndex * 2 + 2] = element;
                        added = true;
                        if (currentIndex * 2 + 2 > maxIndex) {
                            maxIndex = currentIndex * 2 + 2;
                        }
                    } else {
                        currentIndex = currentIndex * 2 + 2;
                    }
                }
            }
        }
        height = (int) (Math.log(maxIndex + 1) / Math.log(2)) + 1;
        count++;
    }
    
    private int findIndex(Comparable<T> element,int n) throws ElementNotFoundException{
        int result=0;
        if(n>maxIndex){
            throw new ElementNotFoundException("Element not found!");
        }
        if(tree[n]==null){
            throw new ElementNotFoundException("The element not found!");
        }
        
        if(element.compareTo(tree[n])<0){
            result=findIndex(element,n*2+1);
        }
        else if(element.compareTo(tree[n])>0){
            result=findIndex(element,n*2+2);
        }
        else{
            result=n;
        }
        return result;
    }
    
    @Override
    public T removeElement(T targetElement) throws ElementNotFoundException {
        Comparable<T> target=(Comparable)targetElement;
        int pos=findIndex(target,0);
        replace(pos);
        count--;
        return tree[pos];
    }

    /**
     * Used by function removeElement in order to remove a node root and
     * reorganize the binary tree.
     *
     * @param node
     */
    private void replace(int node) throws ElementNotFoundException {
        int parent=node;
        int current;
        //Nó só tem ramo à direita
        if (tree[node * 2 + 1] == null && tree[node * 2 + 2] != null) {
            current = node * 2 + 2;
            tree[parent] = tree[current];
            tree[current] = null;
        } //Nó só tem ramo à esquerda
        else if (tree[node * 2 + 2] == null && tree[node * 2 + 1] != null) {
            current = node * 2 + 1;
            tree[parent] = tree[current];
            tree[current] = null;
        } //Nó folha
        else if (tree[node * 2 + 2] == null && tree[node * 2 + 1] == null) {
            tree[node] = null;
        } //Nó interno
        else {
            current = node * 2 + 2;
            while (current * 2 + 1 <= maxIndex && tree[current * 2 + 1] != null) {
                parent = current;
                current = current * 2 + 1;
            }
            if (node * 2 + 2 == current) {
                tree[parent] = tree[current];
                if (current * 2 + 2 <= maxIndex) {
                    tree[current] = tree[current * 2 + 2];
                    tree[current * 2 + 2] = null;
                }
                else tree[current]=null;
            } else {
                tree[node] = tree[current];
                if (current * 2 + 2 <= maxIndex) {
                    tree[parent * 2 + 1] = tree[current * 2 + 2];
                    tree[current * 2 + 2] = null;
                } else {
                    tree[current] = null;
                }
            }
        }
        while (tree[maxIndex] == null) {
            maxIndex--;
        }
    }

    @Override
    public void removeAllOccurrences(T targetElement) throws ElementNotFoundException {
        this.removeElement(targetElement);
        boolean hasElement = true;
        while (hasElement) {
            try {
                this.removeElement(targetElement);
            } catch (ElementNotFoundException ex) {
                hasElement = false;
            }
        }
    }

    @Override
    public T removeMin() throws InvalidOperationException {
        if (this.tree[0] == null) {
            throw new InvalidOperationException("The list is empty");
        }
        T result = null;
        if (this.tree[0 * 2 + 1] == null) {
            result = tree[0];
            this.tree[0] = this.tree[0 * 2 + 2];
            this.tree[0 * 2 + 2] = null;
            return result;
        }
        int current = 0;
        while (current * 2 + 1 <= maxIndex && tree[current * 2 + 1] != null) {
            current = current * 2 + 1;
        }
        result = tree[current];
        if (current * 2 + 2 <= maxIndex && tree[current * 2 + 2] != null) {
            tree[current] = tree[current * 2 + 2];
            tree[current * 2 + 2] = null;
        } else {
            tree[current] = null;
        }
        while (tree[maxIndex] == null) {
            maxIndex--;
        }
        count--;
        return result;
    }

    @Override
    public T removeMax() throws InvalidOperationException {
        if (this.tree[0] == null) {
            throw new InvalidOperationException("The list is empty");
        }
        T result = null;
        if (this.tree[0 * 2 + 2] == null) {
            result = tree[0];
            this.tree[0] = this.tree[0 * 2 + 1];
            this.tree[0 * 2 + 1] = null;
            return result;
        }
        int current = 0;
        while (current * 2 + 2 <= maxIndex && tree[current * 2 + 2] != null) {
            current = current * 2 + 2;
        }
        result = tree[current];
        if (current * 2 + 1 <= maxIndex && tree[current * 2 + 1] != null) {
            tree[current] = tree[current * 2 + 1];
            tree[current * 2 + 1] = null;
        } else {
            tree[current] = null;
        }
        while (tree[maxIndex] == null) {
            maxIndex--;
        }
        count--;
        return result;
    }

    @Override
    public T findMin() throws ElementNotFoundException {
        if (tree[0] == null) {
            throw new ElementNotFoundException("The tree is empty!");
        }
        int current = 0;
        while (current * 2 + 1 <= maxIndex && tree[current * 2 + 1] != null) {
            current = current * 2 + 1;
        }
        return tree[current];

    }

    @Override
    public T findMax() throws ElementNotFoundException {
        if (tree[0] == null) {
            throw new ElementNotFoundException("The tree is empty!");
        }
        int current = 0;
        while (current * 2 + 2 <= maxIndex && tree[current * 2 + 2] != null) {
            current = current * 2 + 2;
        }
        return tree[current];
    }

}
