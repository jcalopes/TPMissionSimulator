/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package linkedListSentinela;

import exceptions.ElementNotFoundException;
import exceptions.NullElementValueException;
import interfaces.UnorderedListADT;

public class UnorderedLinkedList<T> extends LinkedListSentinela<T> implements UnorderedListADT<T>{

    public UnorderedLinkedList(){
        super();
    }
    
    /**
     * Add a new node to front the linked list.
     * @param element
     * @throws NullElementValueException 
     */
    @Override
    public void addToFront(T element) throws NullElementValueException {
        if(element==null){
            throw new NullElementValueException("The element doesn´t exist!");
        }
        else{
            LinkedNode<T> newNode=new LinkedNode(element);
            newNode.setNext(this.head.getNext());
            this.head.setNext(newNode);
            this.count++;
            this.modCount++;
        }
    }

    /**
     * Add new node to rear the linked list.
     * @param element
     * @throws NullElementValueException 
     */
    @Override
    public void addToRear(T element) throws NullElementValueException {
        if(element==null){
            throw new NullElementValueException("The element doesn´t exist!");
        }
        else{
            LinkedNode current=this.head.getNext();
            LinkedNode previous=this.head;

            while(current!=this.tail){
                previous=current;
                current=current.getNext();
            }
                        
            LinkedNode<T> newNode=new LinkedNode(element);
            previous.setNext(newNode);
            newNode.setNext(this.tail);
            this.count++;
            this.modCount++;
        }
    }

    /**
     * Add new node after the a specific node.
     * @param element
     * @param target
     * @throws ElementNotFoundException
     * @throws NullElementValueException 
     */
    @Override
    public void addAfter(T element, T target) throws ElementNotFoundException, NullElementValueException {
        if(element==null){
            throw new NullElementValueException("The element doesn´t exist!");
        }
        else{
            LinkedNode<T> current=this.head.getNext();
            while(current!=this.tail && !current.getElement().equals(target)){
                current=current.getNext();
            }
            if(current==this.tail)throw new ElementNotFoundException("The element doesn´t exists!");
            LinkedNode newNode=new LinkedNode(element);
            newNode.setNext(current.getNext());
            current.setNext(newNode);
            this.count++;
            this.modCount++;
        }
    }
}
