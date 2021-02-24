/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package binaryTree;

import exceptions.ElementNotFoundException;
import exceptions.NullElementValueException;
import interfaces.BinaryTreeADT;
import java.util.Iterator;
import list.UnorderedArrayList;

/**
 * 
 *  Nome:João Carlos Abreu Lopes 
    Número:8190221
    Turma:T1
 */
public abstract class ArrayBinaryTree<T> implements BinaryTreeADT<T>{
    public T[] tree;
    protected int count;
    private final int CAPACITY = 5;

    /**
     * Creates an empty binary tree.
     */
    public ArrayBinaryTree() {
        this.count = 0;
        this.tree = (T[]) new Object[CAPACITY];
    }

    /**
     * Creates a binary tree with the specified element as its root.
     *
     * @param element the element which will become the root of the new tree
     */
    public ArrayBinaryTree(T element) {
        this.count = 1;
        this.tree = (T[]) new Object[CAPACITY];
        this.tree[0] = element;
    }
    
    protected void expandCapacity(){
        T[] temp=(T[])new Object[this.tree.length*2+2];
        
        for(int i=0;i<this.tree.length;i++){
            temp[i]=this.tree[i];
        }
        
        this.tree=temp;
    }
    
    @Override
    public T getRoot() {
        return this.tree[0];
    }

    @Override
    public boolean isEmpty() {
        return (this.tree[0]==null);
    }

    @Override
    public int size() {
        return this.count;
    }

    @Override
    public boolean contains(T targetElement) {
        Iterator<T> it=this.iteratorInOrder();
        while(it.hasNext()){
            if(it.next().equals(targetElement))
                return true;
        }
        return false;
    }

    /**
     * Returns a reference to the specified target element if it is found in
     * this binary tree. Throws a NoSuchElementException if the specified target
     * element is not found in the binary tree.
     *
     * @param targetElement the element being sought in the tree
     * @return T if the element is in the tree
     * @throws ElementNotFoundException if an element not found exception occurs
     */
    @Override
    public T find(T targetElement) throws ElementNotFoundException {
        T temp = null;
        boolean found = false;

        for (int ct = 0; ct < count && !found; ct++) {
            if (targetElement.equals(tree[ct])) {
                found = true;
                temp = tree[ct];
            }
        }
        if (!found) {
            throw new ElementNotFoundException("Element not found!");
        }
        return temp;
    }

    @Override
    public Iterator<T> iteratorPreOrder() {
       UnorderedArrayList<T> tempList=new UnorderedArrayList<>();
       preOrder(0,tempList);
       return tempList.iterator();
    }
    
    /**
     * Performs a recursive inorder traversal.
     *
     * @param root the node used in the traversal
     * @param temp the temporary list used in the traversal
     */
    protected void preOrder(int root, UnorderedArrayList<T> temp) {
        if (root < tree.length) {
            if (tree[root] != null) {
                try {
                    temp.addToRear(this.tree[root]);
                } catch (NullElementValueException ex) {}
                preOrder(root * 2 + 1, temp);
                preOrder((root+1)*2,temp);
            }
        }
    }
    
    @Override
    public Iterator<T> iteratorInOrder() {
        UnorderedArrayList<T> temp=new UnorderedArrayList<>();
        inOrder(0,temp);
        return temp.iterator();
    }

    protected void inOrder(int root,UnorderedArrayList<T> temp){
        if (root < tree.length) {
            if (this.tree[root] != null) {
                inOrder(root * 2 + 1, temp);
                try {
                    temp.addToRear(tree[root]);
                } catch (NullElementValueException ex) {}
                inOrder((root + 1) * 2, temp);
            }
        }
    }
            
    @Override
    public Iterator<T> iteratorPostOrder() {
         UnorderedArrayList<T> temp=new UnorderedArrayList<>();
         postOrder(0,temp);
         return temp.iterator();
    }

    protected void postOrder(int root, UnorderedArrayList<T> temp) {
        if (root < tree.length) {
            if (tree[root] != null) {
                postOrder(root * 2 + 1, temp);
                postOrder((root + 1) * 2, temp);
                try {
                    temp.addToRear(tree[root]);
                } catch (NullElementValueException ex) {}
            }
        }
    }

    @Override
    public Iterator<T> iteratorLevelOrder() {
        UnorderedArrayList<T> temp=new UnorderedArrayList<>();
        for(int i=0;i<this.tree.length;i++){
             if(this.tree[i]!=null)
                try {
                    temp.addToRear(this.tree[i]);
             } catch (NullElementValueException ex) {}
        }
        return temp.iterator();     
    }
}
