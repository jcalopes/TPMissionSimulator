package linkedListSentinela;

import exceptions.ElementNotFoundException;
import exceptions.InvalidOperationException;
import exceptions.NullElementValueException;
import interfaces.ListADT;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

/**
 *This class stores a super class linked list implemented with sentinel node.
 *
 */

public abstract class LinkedListSentinela<T> implements ListADT<T> {

    protected LinkedNode<T> head;
    protected LinkedNode<T> tail;
    protected int count;
    protected int modCount;

    /**
     * Constructor for the linked list.
     */
    public LinkedListSentinela() {
        this.count = 0;
        this.head = new LinkedNode<>();
        this.tail = new LinkedNode<>();
        this.head.setNext(tail);
        this.tail.setNext(null);
        this.modCount = 0;
    }

    /**
     * Remove the first node from the linked list.
     * @return
     * @throws InvalidOperationException 
     */
    @Override
    public T removeFirst() throws InvalidOperationException {
        if (count == 0) {
            throw new InvalidOperationException("The list is empty!");
        }
        LinkedNode<T> firstElement = this.head.getNext();
        this.head.setNext(firstElement.getNext());
        this.count--;
        this.modCount++;
        return firstElement.getElement();
    }

    /**
     * Remove the last node from the linked list.
     * @return
     * @throws InvalidOperationException 
     */
    @Override
    public T removeLast() throws InvalidOperationException {
 if(count==0) throw new InvalidOperationException("The list is empty!");
       LinkedNode<T> current=this.head.getNext();
       
       if(this.count==1){
           this.head.setNext(this.tail);
           this.count--;
           return current.getElement();
       }
       
       LinkedNode<T> previous=null;
            
       while(!current.getElement().equals(this.last())){
           previous=current;
           current=current.getNext();
       }
       
       previous.setNext(current.getNext());
       this.count--;
       this.modCount++;
       return current.getElement();
    }

    /**
     * Remove a specific node from the linked list.
     * @param element
     * @return the node removed
     * @throws ElementNotFoundException 
     */
    @Override
    public T remove(T element) throws ElementNotFoundException {
        if (!this.contains(element)) {
            throw new ElementNotFoundException("The element doesn´t exist!");
        }
        LinkedNode<T> current = this.head.getNext();
        LinkedNode<T> previous = this.head;

        while (!current.getElement().equals(element)) {
            previous = current;
            current = current.getNext();
        }

        previous.setNext(current.getNext());
        this.count--;
        this.modCount++;
        return current.getElement();
    }

    /**
     * Return the first node from linked list.
     * @return the first node
     * @throws InvalidOperationException 
     */
    @Override
    public T first() throws InvalidOperationException {
        if (count == 0) {
            throw new InvalidOperationException("The list is empty!");
        }
        return this.head.getNext().getElement();
    }

    /**
     * Return the last node from the linked list.
     * @return the last node
     * @throws InvalidOperationException 
     */
    @Override
    public T last() throws InvalidOperationException {
        if (count == 0) {
            throw new InvalidOperationException("The list is empty!");
        }
        LinkedNode<T> current = this.head.getNext();
        LinkedNode<T> previous = null;

        while (current != this.tail) {
            previous = current;
            current = current.getNext();
        }
        return previous.getElement();
    }

    /**
     * Check if contain a specific element in the linked list.
     * @param target
     * @return 
     */
    @Override
    public boolean contains(T target) {
        LinkedNode<T> current = this.head.getNext();
        while (current != this.tail) {
            if (current.getElement().equals(target)) {
                return true;
            } else {
                current = current.getNext();
            }
        }
        return false;
    }

    /**
     * Check if the linked list is empty.
     * @return boolean value
     */
    @Override
    public boolean isEmpty() {
        return (this.count == 0);
    }

    /**
     * Return the number of elements in the linked list.
     * @return 
     */
    @Override
    public int size() {
        return this.count;
    }

    /**
     * Return an iterator of the linked list.
     * @return 
     */
    @Override
    public Iterator<T> iterator() {
        return new linkedIterator();
    }

        /**
     * Check if a element exist and return it.
     * @param target
     * @return element searched
     * @throws ElementNotFoundException 
     */
    public T getElement(T target)throws ElementNotFoundException,NullElementValueException{
        if(target==null){
           throw new NullElementValueException("The element is null");
        }
        
        LinkedNode<T> current=this.head.getNext();
        
        while(current!=this.tail){
            if(current.getElement().equals(target)){
                return current.getElement();
            } 
            current=current.getNext();
        }
        throw new ElementNotFoundException("The element does not exist");
    }
    
    /**
     * Return a string representation of the linked list.
     * @return the information
     */
    @Override
    public String toString() {
        String s = "";
        LinkedNode<T> current = this.head.getNext();

        while (current.getElement() != null) {
            s += "\n " + current.toString();
            current = current.getNext();
        }
        return s;
    }
    
    /**
     * This inner class build an iterator of the linked list.
     */
    public class linkedIterator implements Iterator<T> {

        private LinkedNode<T> current;
        private boolean okToRemove;
        private int expectedModCount;

        /**
         * Constructor for the iterator.
         */
        public linkedIterator() {
            this.current = head;
            this.okToRemove = false;
            this.expectedModCount = modCount;
        }

        /**
         * Check if exist the next element from the linked list.
         * @return 
         */
        @Override
        public boolean hasNext() {
            if (modCount == expectedModCount) {
                return (this.current.getNext() != tail);
            } else {
                throw new ConcurrentModificationException();
            }
        }

        /**
         * Return the current node and move to next node.
         * @return currrent node
         */
        @Override
        public T next() {
            if (this.hasNext()) {
                this.okToRemove = true;
                this.current = this.current.getNext();
                return current.getElement();
            } else {
                throw new IllegalStateException();
            }
        }

        /**
         * Remove the current node.
         */
        @Override
        public void remove() {
            if (this.okToRemove) {
                try {
                    LinkedListSentinela.this.remove(current.getElement());
                    this.okToRemove = false;
                    this.expectedModCount++;
                } catch (ElementNotFoundException ex) {
                    System.out.println(ex);
                }
            } else {
                throw new IllegalStateException();
            }
        }
    }
   
}
