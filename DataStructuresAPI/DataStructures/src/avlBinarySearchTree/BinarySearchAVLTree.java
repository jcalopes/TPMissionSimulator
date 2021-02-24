/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package avlBinarySearchTree;

import stack.ArrayStack;
import exceptions.ElementNotFoundException;
import exceptions.InvalidOperationException;
import exceptions.NullElementValueException;
import interfaces.BinarySearchTreeADT;
import interfaces.QueueADT;
import interfaces.StackADT;
import interfaces.UnorderedListADT;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import list.UnorderedArrayList;
import queue.CircularArrayQueue;

/**
 * 
 *  Nome:João Carlos Abreu Lopes 
    Número:8190221
    Turma:T1
 */
public class BinarySearchAVLTree<T> implements BinarySearchTreeADT<T>  {
    private BinaryTreeAVLNode<T> tree;
    private int count;
    
    public BinarySearchAVLTree(T element){
        this.count=1;
        this.tree=new BinaryTreeAVLNode(element);
        this.tree.setHeight(0);
    }
    
    public BinarySearchAVLTree(){
        this.count=0;
    }
  
    private int calcHeight(BinaryTreeAVLNode<T> target){
        if(target.numChildren()==0)
            return 0;
        else if(target.left==null){
            return target.right.height+1;
        }
        else if(target.right==null){
            return target.left.height+1;
        }
        else{
            return Math.max(target.left.height, target.right.height)+1;
        }
    }
    
    private int balancingFactor(BinaryTreeAVLNode<T> target){
        if(target.right==null){
            return (0-(target.left.height+1));
        }
        else if(target.left==null){
            return(target.right.height+1);
        }
        else {return (target.right.height+1)-(target.left.height+1);}
    }
    
    private BinaryTreeAVLNode<T> moveLeft(BinaryTreeAVLNode<T> root){
        BinaryTreeAVLNode<T> oldRoot=root;
        BinaryTreeAVLNode<T> newRoot=root.right;
        
        root=newRoot;
        oldRoot.right=newRoot.left;
        root.left=oldRoot;
        
        oldRoot.height=calcHeight(oldRoot);
        newRoot.height=calcHeight(newRoot);
        return root;
    }

    private BinaryTreeAVLNode<T> moveRight(BinaryTreeAVLNode<T> root) {
        BinaryTreeAVLNode<T> oldRoot = root;
        BinaryTreeAVLNode<T> newRoot = root.left;

        root = newRoot;
        oldRoot.left = newRoot.right;
        root.right = oldRoot;

        oldRoot.height = calcHeight(oldRoot);
        newRoot.height = calcHeight(newRoot);
        return root;
    }

    private BinaryTreeAVLNode<T> balanceNode(BinaryTreeAVLNode<T> target) {
        target.height = calcHeight(target);
        if (target.height == 0) {
            return target;
        }
        int balancingFactor = balancingFactor(target);
        if (balancingFactor(target) == -2) {
            if (balancingFactor(target.left) <= 0) {
                target = moveRight(target);
            } else {
                target.left = moveLeft(target.left);
                target = moveRight(target);
            }
        } else if (balancingFactor == 2) {
            if (balancingFactor(target.right) >= 0) {
                target = moveLeft(target);
            } else {
                target.right = moveRight(target.right);
                target = moveLeft(target);
            }
        }
        return target;
    }
    
    private BinaryTreeAVLNode<T> add(BinaryTreeAVLNode<T> target,BinaryTreeAVLNode<T> current){
        if(current==null){
            return target;
        }
        if(((Comparable)target.element).compareTo(current.element)<0){
            current.left=(current.left!=null) ? add(target,current.left): add(target,null);          
        }
        else{
             current.right=(current.right!=null) ? add(target,current.right): add(target,null);
        }
        current=this.balanceNode(current);
        return current;
    }
    
    @Override
    public void addElement(T element) throws ClassCastException,InvalidOperationException { 
        if(element==null)throw new InvalidOperationException("The element is null!");
       try{
           Comparable<T> target=(Comparable)element;
       }
       catch(ClassCastException ex){
           throw new ClassCastException("The element is not comparable!");
       }
        
        BinaryTreeAVLNode<T> node=new BinaryTreeAVLNode<>(element);
        node.height=0;
        if(this.isEmpty()){
            this.tree=node;
        }
        else{
             this.tree=add(node,tree);
        }
        this.count++;
    }

    private BinaryTreeAVLNode<T> replacement(ArrayStack<BinaryTreeAVLNode<T>> temp,BinaryTreeAVLNode<T> node){
        try {
            temp.pop();
        } catch (InvalidOperationException ex) {}
        if (node.left == null && node.right == null) {
            return null;
        } else if (node.left == null && node.right != null) {
            return node.right;
        }
        else if(node.left!=null && node.right==null){
            return node.left;
        }
        else{
            BinaryTreeAVLNode<T> parent=node;
            BinaryTreeAVLNode<T> current=node.right;
            temp.push(current);
            while(current.left!=null){
                parent=current;
                current=current.left;
                temp.push(current);
            }
            if(node.right==current){
                current.left=node.left;
                return current;
            }
            else{
                parent.left=current.right;
                current.left=node.left;
                current.right=node.right;
                return current;
            }
        }    
    }
    
    private BinaryTreeAVLNode<T> balanceTree(BinaryTreeAVLNode<T> current,BinaryTreeAVLNode<T> target){     
        if (((Comparable) current.element).compareTo(target.element) < 0) {
            current.right = balanceTree(current.right, target);
        } else if (((Comparable) current.element).compareTo(target.element) > 0) {
            current.left = balanceTree(current.left, target);
        } else {
            current=balanceNode(current);
        }
        return current;
    }
    
    private BinaryTreeAVLNode<T> remove(ArrayStack<BinaryTreeAVLNode<T>> temp,BinaryTreeAVLNode<T> current, T target){
        temp.push(current);
        if (((Comparable) current.element).compareTo(target) < 0) {
            current.right = remove(temp, current.right, target);
        } else if (((Comparable) current.element).compareTo(target) > 0) {
            current.left = remove(temp, current.left, target);
        } else {
            current = replacement(temp, current);
        }
        return current;
    }

    @Override
    public T removeElement(T targetElement) throws ElementNotFoundException, InvalidOperationException {
        if (tree == null ) {
            throw new ElementNotFoundException("Element not found!");
        }
        if(targetElement==null){
            throw new InvalidOperationException("The element is null!");
        }
        try{
            Comparable<T> check=(Comparable<T>)targetElement;
        }catch(ClassCastException ex){
            throw new ClassCastException("The element is not comparable!");
        }
      
        if(!this.contains(targetElement)){
            throw new ElementNotFoundException("Element not found!");
        }
        
        ArrayStack<BinaryTreeAVLNode<T>> listNodes = new ArrayStack<>();
        tree = remove(listNodes, tree, targetElement);
        try {
            while (!listNodes.isEmpty()) {
                BinaryTreeAVLNode<T> currentNode=listNodes.pop();
                tree = balanceTree(tree, currentNode);
            }
        } catch (InvalidOperationException ex) {}
        count--;
        return targetElement;
    }

    @Override
    public void removeAllOccurrences(T targetElement) throws ElementNotFoundException, InvalidOperationException {
        this.removeElement(targetElement);
        boolean hasElement=true;
        
        try{
            while(hasElement){
                this.removeElement(targetElement);}
        }
        catch(ElementNotFoundException ex){
            hasElement=false;
        }     
    }
    
    
    @Override
    public T removeMin() throws InvalidOperationException {
      if(this.tree==null){
          throw new InvalidOperationException("The binary tree is empty!");
      }
      
      T removed=null;
      if(this.tree.getLeft()==null){
            removed=this.tree.getElement();
            this.tree=this.tree.getRight();
      }
      else{
          StackADT<BinaryTreeAVLNode<T>> stack=new ArrayStack<>();
          BinaryTreeAVLNode<T> parent=null;
          BinaryTreeAVLNode<T> current=this.tree;
          stack.push(current);
          
          while(current.getLeft()!=null){
              stack.push(current.getLeft());
              parent=current;
              current=current.getLeft();
          }
          removed=current.getElement();
          parent.setLeft(current.getRight());
          stack.pop();
          
          while(!stack.isEmpty()){
              BinaryTreeAVLNode<T> currentNode=stack.pop();
              this.tree=this.balanceTree(this.tree,currentNode);
          }
      }
      this.count--;
      return removed;
    }
    
    @Override
    public T removeMax() throws InvalidOperationException {
         if(this.tree==null){
          throw new InvalidOperationException("The binary tree is empty!");
      }
      
      T removed=null;
      if(this.tree.getRight()==null){
            removed=this.tree.getElement();
            this.tree=this.tree.getLeft();
      }
      else{
          StackADT<BinaryTreeAVLNode<T>> stack=new ArrayStack<>();
          BinaryTreeAVLNode<T> parent=null;
          BinaryTreeAVLNode<T> current=this.tree;
          stack.push(current);
          
          while(current.getRight()!=null){
              stack.push(current.getRight());
              parent=current;
              current=current.getRight();
          }
          removed=current.getElement();
          parent.setRight(current.getLeft());
          stack.pop();
          
          while(!stack.isEmpty()){
              BinaryTreeAVLNode<T> currentNode=stack.pop();
              this.tree=this.balanceTree(this.tree,currentNode);
          }
      }
      this.count--;
      return removed;
    }

    @Override
    public T findMin() throws ElementNotFoundException {
       if(this.tree==null)throw new ElementNotFoundException("The tree is empty!");
       BinaryTreeAVLNode<T> current=this.tree;
       while(current.left!=null){
           current=current.left;
       }
       return current.element;
    }

    @Override
    public T findMax() throws ElementNotFoundException {
       if(this.tree==null)throw new ElementNotFoundException("The tree is empty!");
       BinaryTreeAVLNode<T> current=this.tree;
       while(current.right!=null){
           current=current.right;
       }
       return current.element;
    }

    @Override
    public T getRoot() {
        return tree.element;
    }

    @Override
    public boolean isEmpty() {
        return(this.tree==null);
    }

    private int countElements(BinaryTreeAVLNode<T> root){
        int total=0;
        if(root!=null){
            total=countElements(root.left)+1+countElements(root.right);
        }
        return total;
    }
    
    @Override
    public int size() {
         return countElements(this.tree);
    }

    @Override
    public boolean contains(T targetElement) {
        Iterator<T> it=this.iteratorInOrder();
        while(it.hasNext()){
            if(((Comparable)it.next()).compareTo(targetElement)==0){
                return true;
            }
        }
        return false;
    }

    @Override
    public T find(T targetElement) throws ElementNotFoundException {
        Iterator<T> it=this.iteratorInOrder();
        while(it.hasNext()){
            T current=it.next();
            if(((Comparable)current).compareTo(targetElement)==0){
                return current;
            }
        }
        throw new ElementNotFoundException("Element not found!");
    }

    private void preOrder(UnorderedArrayList<T> temp,BinaryTreeAVLNode<T> current){
        if(current!=null){
            try {
                temp.addToRear(current.element);
            } catch (NullElementValueException ex) {}
            preOrder(temp,current.left);
            preOrder(temp,current.right);
        }
    }
    @Override
    public Iterator<T> iteratorPreOrder() {
        UnorderedArrayList<T> data=new UnorderedArrayList<>();
        preOrder(data,this.tree);
        return data.iterator();
    }

    private void inOrder(UnorderedArrayList<T> temp,BinaryTreeAVLNode<T> current){
        if(current!=null){
            inOrder(temp,current.left);
            try {
                temp.addToRear(current.element);
            } catch (NullElementValueException ex) {}
            inOrder(temp,current.right);
        }
    }
    @Override
    public Iterator<T> iteratorInOrder() {
        UnorderedArrayList<T> data=new UnorderedArrayList<>();
        inOrder(data,this.tree);
        return data.iterator();
    }

    private void postOrder(UnorderedArrayList<T> temp,BinaryTreeAVLNode<T> current){
        if(current!=null){
            postOrder(temp,current.left);
            postOrder(temp,current.right);
            try {
                temp.addToRear(current.element);
            } catch (NullElementValueException ex) {}
        }
    }
    @Override
    public Iterator<T> iteratorPostOrder() {
        UnorderedArrayList<T> temp=new UnorderedArrayList<>();
        postOrder(temp, this.tree);
        return temp.iterator();
    }

    @Override
    public Iterator<T> iteratorLevelOrder() {
        QueueADT<BinaryTreeAVLNode<T>> queue=new CircularArrayQueue<>();
        UnorderedListADT<T> results=new UnorderedArrayList<>();
        try {
            queue.enqueue(this.tree);
        } catch (NullElementValueException ex) {
            return results.iterator();
        }
        
        BinaryTreeAVLNode<T> current=null; 
        while(!queue.isEmpty()){
            try {
                current=queue.dequeue();
            } catch (InvalidOperationException ex) {}
           try {
                if(current.getLeft()!=null)queue.enqueue(current.getLeft());
                if(current.getRight()!=null)queue.enqueue(current.getRight());
                results.addToRear(current.getElement());
            } catch (NullElementValueException ex) {}    
        }
        return results.iterator();
    }
}
