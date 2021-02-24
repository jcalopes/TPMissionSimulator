/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package doublyLinkedList;

import exceptions.ElementNotFoundException;
import exceptions.InvalidOperationException;
import interfaces.ListADT;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 
 *  Nome:João Carlos Abreu Lopes 
    Número:8190221
    Turma:T1
 */
public abstract class DoublyLinkedListSentinela<T> implements ListADT<T>{
    protected DoublyLinkedNode<T> head;
    protected DoublyLinkedNode<T> tail;
    protected int count;
    protected int modCount;
    
    public DoublyLinkedListSentinela(){
        this.count=0;
        this.head=new DoublyLinkedNode<>();
        this.tail=new DoublyLinkedNode<>();
        this.head.setPrevious(null);
        this.head.setNext(this.tail);
        this.tail.setPrevious(this.head);
        this.tail.setNext(null);
        this.modCount=0;
    }
    
    @Override
    public T removeFirst() throws InvalidOperationException {
       if (this.isEmpty()){
            throw new InvalidOperationException("The list is empty!");
        }
        else{
            DoublyLinkedNode<T> removed=this.head.getNext();
            this.head.setNext(removed.getNext());
            removed.getNext().setPrevious(this.head);
            this.count--;
            this.modCount++;
            return removed.getElement();
        }      
    }

    @Override
    public T removeLast() throws InvalidOperationException {
          if(this.isEmpty()){
            throw new InvalidOperationException("The list is empty!");
        }
        else{
            DoublyLinkedNode<T> removed=this.tail.getPrevious();
            removed.getPrevious().setNext(this.tail);
            this.tail.setPrevious(removed.getPrevious());
            this.count--;
            this.modCount++;
            return removed.getElement();
        }
    }

    @Override
    public T remove(T element) throws ElementNotFoundException {
       if(!this.contains(element)){
           throw new ElementNotFoundException("This element doesn´t exist!");
       }
       
       DoublyLinkedNode<T> remove=this.head.getNext();
       while(remove.getNext()!=null && (!remove.getElement().equals(element))){
           remove=remove.getNext();
       }
       
       (remove.getPrevious()).setNext(remove.getNext());
       (remove.getNext()).setPrevious(remove.getPrevious());
       this.count--;
       this.modCount++;
       return remove.getElement();
    }

    @Override
    public T first() throws InvalidOperationException {
        return this.head.getNext().getElement();
    }

    @Override
    public T last() throws InvalidOperationException {
        return this.tail.getPrevious().getElement();
    }

    @Override
    public boolean contains(T target) {
       DoublyLinkedNode<T> current=this.head.getNext();
       while(current.getElement()!=null){
           if(current.getElement().equals(target)){
               return true;
           }
           current=current.getNext();
       }
       return false;
    }

    @Override
    public boolean isEmpty() {
        return (this.count == 0);
    }

    @Override
    public int size() {
        return this.count;
    }

    @Override
    public String toString() {
        return printDoublyLinkedList(this.head.getNext());
    }
    
    public String invertPrint(){
        return printInvertDoublyList(this.tail.getPrevious());
    }

    //Teste com recursividade
    private String printInvertDoublyList(DoublyLinkedNode node){
        String info;
        if(node.getElement()==null)
            return "";
        else{
            info=node.getElement()+"\n"+printInvertDoublyList(node.getPrevious());
        }
        return info;
        
    }
    //Teste com recursividade
    private String printDoublyLinkedList(DoublyLinkedNode first) {
        String info;    
        if (first.getElement() == null) {
            return "";
        } else {
            info = first.getElement().toString() + "\n" 
                    + printDoublyLinkedList(first.getNext());
        }
        return info;
    }
    
    @Override
    public Iterator<T> iterator() {
       return new LinkedListIterator();
    }
    
      public class LinkedListIterator implements Iterator<T>{
        private DoublyLinkedNode<T> currentNode;
        private int expectedModCount;
        private boolean okToRemove;
               
        public LinkedListIterator(){
            this.currentNode=head;
            this.expectedModCount=modCount;
            this.okToRemove=false;
        }

        @Override
        public boolean hasNext() throws ConcurrentModificationException{
            if(this.expectedModCount==modCount){
                return (currentNode.getNext()!=tail);}
            else{
                throw new ConcurrentModificationException("The list was changed!");
            }
        }

        @Override
        public T next() throws ConcurrentModificationException{
            if(this.hasNext()){
                okToRemove=true;
                currentNode=currentNode.getNext();
                T atualNode=currentNode.getElement();
                return atualNode;
            }
            else{
                throw new NoSuchElementException();
            }
        }
        
        @Override
        public void remove(){
            if(okToRemove){
                try {
                    DoublyLinkedListSentinela.this.remove(currentNode.getElement());
                    expectedModCount++;
                    okToRemove=false;
                } catch (ElementNotFoundException ex) {
                    System.out.println("ex");
                }
            }
            else{
                throw new IllegalStateException();
            }
        }
    } 
      
      public boolean invertList(){
          if(this.count==0)return false;
          
          int iteracoes=(int)this.count/2;
          DoublyLinkedNode<T> start=this.head.getNext();
          DoublyLinkedNode<T> end=this.tail.getPrevious();
          
          while(iteracoes>0){
              T temp=end.getElement();
              
              end.setElement(start.getElement());
              start.setElement(temp);
              
              iteracoes--;
              start=start.getNext();
              end=end.getPrevious();
          }
          return true;
      }

}
