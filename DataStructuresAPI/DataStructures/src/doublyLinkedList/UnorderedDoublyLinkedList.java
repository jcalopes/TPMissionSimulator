/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package doublyLinkedList;

import exceptions.ElementNotFoundException;
import exceptions.NullElementValueException;
import interfaces.UnorderedListADT;

/**
 * This class stores an unordered linked list.
 */
public class UnorderedDoublyLinkedList<T> extends DoublyLinkedListSentinela<T> implements UnorderedListADT<T> {

    /**
     * Add a new element to front the list.
     * @param element
     * @throws NullElementValueException 
     */
    @Override
    public void addToFront(T element) throws NullElementValueException{
        if(element==null) throw new NullElementValueException("The element is null!");
        DoublyLinkedNode<T> newNode=new DoublyLinkedNode<>(element);
        
        newNode.setNext(this.head.getNext());
        newNode.setPrevious(this.head);
        (this.head.getNext()).setPrevious(newNode);
        this.head.setNext(newNode);
        this.modCount++;
        this.count++;      
    }

    /**
     * Add new element to rear the list.
     * @param element
     * @throws NullElementValueException 
     */
    @Override
    public void addToRear(T element) throws NullElementValueException {
        if(element==null)throw new NullElementValueException("The element is null!");
        DoublyLinkedNode<T> newNode=new DoublyLinkedNode<>(element);
        
        newNode.setNext(this.tail);
        newNode.setPrevious(this.tail.getPrevious());
        (this.tail.getPrevious()).setNext(newNode);
        this.tail.setPrevious(newNode);
        this.modCount++;
        this.count++;
    }

    /**
     * Add new element to after an element.
     * @param element
     * @param target
     * @throws ElementNotFoundException
     * @throws NullElementValueException 
     */
    @Override
    public void addAfter(T element, T target) throws ElementNotFoundException,NullElementValueException{
        if(element==null || target==null)throw new NullElementValueException("The element is null");
        if(!this.contains(target))throw new ElementNotFoundException("The element doesn´t exist!");
        
        DoublyLinkedNode<T> newNode=new DoublyLinkedNode<>(element);
        DoublyLinkedNode<T> current=this.head.getNext();
        while(current.getElement()!=null && !target.equals(current.getElement())){
            current=current.getNext();
        }

        newNode.setPrevious(current);
        newNode.setNext(current.getNext());
        current.setNext(newNode);
        (newNode.getNext()).setPrevious(newNode);
        
        this.modCount++;
        this.count++;
    }
}
