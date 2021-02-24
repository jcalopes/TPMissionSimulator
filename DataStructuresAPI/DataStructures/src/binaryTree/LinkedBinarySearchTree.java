/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package binaryTree;

import exceptions.ElementNotFoundException;
import exceptions.InvalidOperationException;
import interfaces.BinarySearchTreeADT;

/**
 * 
 *  Nome:João Carlos Abreu Lopes 
    Número:8190221
    Turma:T1
 */
public class LinkedBinarySearchTree<T> extends LinkedBinaryTree<T> implements BinarySearchTreeADT<T>{

    /**
     * Creates an empty binary search tree.
     */
    public LinkedBinarySearchTree(){
        super();   
    }

    /**
     * Creates a binary search with the specified element as its root.
     *
     * @param element the element that will be the root of the new binary search
     * tree
     */
    public LinkedBinarySearchTree(T element) {
        super(element);
    }

    private BinaryTreeNode<T> add(BinaryTreeNode<T> root, BinaryTreeNode<T> target) {
        if (root == null) {
            return target;
        }

        if (((Comparable) target.element).compareTo(root.element) < 0) {
            root.left = add(root.left, target);
        } else {
            root.right = add(root.right, target);
        }

        return root;
//        if (((Comparable) target.element).compareTo(root.element) < 0) {
//            root.left = (root.left == null) ? add(null, target) : add(root.left, target); 
//        }
//        else{
//            root.right=(root.right==null) ? add(null,target):add(root.right,target);
//        }
    }
    
    /**
     * Adds the specified object to the binary search tree in the appropriate
     * position according to its key value. Note that equal elements are added
     * to the right.
     *
     * @param element the element to be added to the binary search tree
     */
    @Override
    public void addElement(T element) throws ClassCastException{
        Comparable <T> check=(Comparable<T>) element;
        
        BinaryTreeNode<T> node=new BinaryTreeNode<>(element);
       if(this.root==null){
           this.root=node;
       }
       else{
           this.root=add(this.root,node);  
       }
       this.count++;
    }
    
    /**
     * Removes the first element that matches the specified target element from
     * the binary search tree and returns a reference to it. Throws a
     * ElementNotFoundException if the specified target element is not found in
     * the binary search tree.
     *
     * @param targetElement the element being sought in the binary search tree
     * @throws ElementNotFoundException if an element not found exception occurs
     */
    @Override
    public T removeElement(T targetElement)
            throws ElementNotFoundException {
        T result = null;
        if (!isEmpty()) {
            if (((Comparable) targetElement).equals(root.element)) {
                result = root.element;
                root = replacement(root);
                count--;
            } else {
                BinaryTreeNode<T> current, parent = root;
                boolean found = false;
                if (((Comparable) targetElement).compareTo(root.element) < 0) {
                    current = root.left;
                } else {
                    current = root.right;
                }
                while (current != null && !found) {
                    if (targetElement.equals(current.element)) {
                        found = true;
                        count--;
                        result = current.element;

                        if (current == parent.left) {
                            parent.left = replacement(current);
                        } else {
                            parent.right = replacement(current);
                        }
                    } else {
                        parent = current;

                        if (((Comparable) targetElement).compareTo(current.element) < 0) {
                            current = current.left;
                        } else {
                            current = current.right;
                        }
                    }
                } //while

                if (!found) {
                    throw new ElementNotFoundException("binary search tree");
                }
            }
        } // end outer if
        return result;
    }

    /**
 * Returns a reference to a node that will replace the one
 * specified for removal. In the case where the removed node has
 * two children, the inorder successor is used as its replacement.
 *
 * @param node the node to be removeed
 * @return a reference to the replacing node
 */
 protected BinaryTreeNode<T> replacement (BinaryTreeNode<T> node)
 {
        BinaryTreeNode<T> result = null;
        if ((node.left == null) && (node.right == null)) {
            result = null;
        } else if ((node.left != null) && (node.right == null)) {
            result = node.left;
        } else if ((node.left == null) && (node.right != null)) {
            result = node.right;
        } else {
            BinaryTreeNode<T> current = node.right;
            BinaryTreeNode<T> parent = node;
            while (current.left != null) {
                parent = current;
                current = current.left;
            }
            if (node.right == current) {
                current.left = node.left;
            } else {
                parent.left = current.right;
                current.right = node.right;
                current.left = node.left;
            }
            result = current;
        }
        return result;
    }
    @Override
    public void removeAllOccurrences(T targetElement)throws ElementNotFoundException {
        this.removeElement(targetElement);
        boolean elements=true;
        
        while(elements){
            try{
                this.removeElement(targetElement);
            }catch(ElementNotFoundException ex){
                elements=false;
            }
        }
    }

    @Override
    public T removeMin() throws InvalidOperationException{
        if(this.count==0) throw new InvalidOperationException("The tree is empty!");
        BinaryTreeNode<T> parent=this.root;
        
        if(this.root.getLeft()==null){
            this.root=this.root.getRight();
            return parent.getElement();
        } 
           
        BinaryTreeNode<T> current=this.root.left;
        while(current.getLeft()!=null){
            parent=current;
            current=current.getLeft();
        }
        parent.setLeft(current.getRight());
        count--;
        return current.getElement();     
    }

    @Override
    public T removeMax() throws InvalidOperationException{
        if(this.count == 0)throw new InvalidOperationException("The tree is empty!");
        BinaryTreeNode<T> parent=this.root;
        
        if(this.root.right==null){
            this.root=this.root.getLeft();
            return parent.element;
        }
        
        BinaryTreeNode<T> current=this.root.getRight();
        while(current.getRight()!=null){
            parent=current;
            current=current.getRight();
        }
        
        parent.setRight(current.getLeft());
        count--;
        return current.getElement();
    }

    @Override
    public T findMin()throws ElementNotFoundException {
       if(this.count == 0)throw new ElementNotFoundException("The tree is empty!");
       
       if(this.root.left==null)
           return this.root.element;
       
       BinaryTreeNode<T> current=this.root.getLeft();
       
       while(current.getLeft()!=null){
           current=current.getLeft();
       }
       
       return current.element;     
    }

    @Override
    public T findMax() throws ElementNotFoundException {
       if(this.count == 0)throw new ElementNotFoundException("The tree is empty!");
       
       if(this.root.right==null)
           return this.root.element;
       
       BinaryTreeNode<T> current=this.root.getRight();
       
       while(current.getRight()!=null){
           current=current.getRight();
       }
       
       return current.element;     
    }
}
