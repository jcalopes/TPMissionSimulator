
package binaryTree;

import queue.LinkedQueue;
import exceptions.ElementNotFoundException;
import exceptions.InvalidOperationException;
import exceptions.NullElementValueException;
import interfaces.BinaryTreeADT;
import java.util.Iterator;
import linkedListSentinela.UnorderedLinkedList;
import list.UnorderedArrayList;

/**
 * 
 */
public abstract class LinkedBinaryTree<T> implements BinaryTreeADT<T>{
    protected int count;
    public BinaryTreeNode<T> root;
    
    public LinkedBinaryTree(){
        this.count=0;
        this.root=null;
    }
    
    public LinkedBinaryTree(T element){
        this.count=1;
        this.root=new BinaryTreeNode<>(element);
    }
    
     public LinkedBinaryTree(BinaryTreeNode node){
        this.count=1;
        this.root=node;
    }
    
    @Override
    public T getRoot() {
        return root.element;
    }

    @Override
    public boolean isEmpty() {
         return (this.count==0);
    }

    @Override
    public int size() {
        return this.count;
    }

    @Override
    public boolean contains(T targetElement) {
         Iterator<T> data=this.iteratorInOrder();
         while(data.hasNext()){
             if(data.next().equals(targetElement)){
                 return true;
             }
         }
         return false;
    }

    @Override
    public Iterator<T> iteratorInOrder() {
        UnorderedArrayList temp=new UnorderedArrayList<>();
        this.inOrder(this.root,temp);
        return temp.iterator();
    }
    
    protected void inOrder(BinaryTreeNode<T> root,UnorderedArrayList<T> temp){
        if(root!=null){
            inOrder(root.left,temp);
            try {
                temp.addToRear(root.element);
            } catch (NullElementValueException ex) {}
            inOrder(root.right,temp);
        }        
    }

    @Override
    public Iterator<T> iteratorPostOrder() {
        UnorderedArrayList<T> temp=new UnorderedArrayList<>();
        postOrder(this.root,temp);
        return temp.iterator();
    }
    
    protected void postOrder(BinaryTreeNode<T> root,UnorderedArrayList<T> temp){
        if(root!=null){
            postOrder(root.left,temp);
            postOrder(root.right,temp);
            try {
                temp.addToRear(root.element);
            } catch (NullElementValueException ex) {}
        }
    }

    @Override
    public Iterator<T> iteratorLevelOrder(){
        LinkedQueue<BinaryTreeNode<T>> nodes=new LinkedQueue<>();
        UnorderedLinkedList<T> results=new UnorderedLinkedList<>();
        try {
            nodes.enqueue(this.root);
        } catch (NullElementValueException ex) {
            return results.iterator();
        }
        while(!nodes.isEmpty()){
            BinaryTreeNode<T> current=null;
            try {
                current=nodes.dequeue();
            } catch (InvalidOperationException ex) {
                System.out.println(ex);
            }
            if(current!=null){
                try {                 
                    results.addToRear(current.element);
                } catch (NullElementValueException ex) {
                    System.out.println(ex);
                }
            try {               
               if(current.left!=null) nodes.enqueue(current.left);
               if(current.right!=null) nodes.enqueue(current.right);  
            } catch (NullElementValueException ex) {
                System.out.println(ex);
            }
           }             
        }
        return results.iterator();
    }
      
    /**
     * Returns a reference to the specified target element if it is found in
     * this binary tree. Throws a NoSuchElementException if the specified target
     * element is not found in the binary tree.
     *
     * @param targetElement the element being sought in this tree
     * @return a reference to the specified target
     * @throws ElementNotFoundException if an element not found exception occurs
     */
    @Override
    public T find(T targetElement) throws ElementNotFoundException {
        BinaryTreeNode<T> current = findAgain(targetElement, root);

        if (current == null) {
            throw new ElementNotFoundException("The element was not found!");
        }

        return (current.element);
    }

    /**
     * Returns a reference to the specified target element if it is found in
     * this binary tree.
     *
     * @param targetElement the element being sought in this tree
     * @param next the element to begin searching from
     */
    private BinaryTreeNode<T> findAgain(T targetElement,
            BinaryTreeNode<T> next) {
        if (next == null) {
            return null;
        }

        if (next.element.equals(targetElement)) {
            return next;
        }

        BinaryTreeNode<T> temp = findAgain(targetElement, next.left);

        if (temp == null) {
            temp = findAgain(targetElement, next.right);
        }

        return temp;
    }

    @Override
    public Iterator<T> iteratorPreOrder() {
       UnorderedArrayList<T> temp=new UnorderedArrayList<>();
       PreOrder(this.root,temp);
       return temp.iterator();
    }
    
    protected void PreOrder(BinaryTreeNode<T> root,UnorderedArrayList<T> temp){
        if(root!=null){
            try {
                temp.addToRear(root.element);
            } catch (NullElementValueException ex) {}
            PreOrder(root.left,temp);
            PreOrder(root.right,temp);
        }
    }
}
