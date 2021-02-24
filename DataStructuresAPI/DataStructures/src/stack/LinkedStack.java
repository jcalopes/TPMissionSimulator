/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stack;

import exceptions.InvalidOperationException;
import interfaces.StackADT;
import linkedListSentinela.LinkedNode;

/**
 * *This class store an stack implemented by a linked list.
 */
public class LinkedStack<T> implements StackADT<T> {

    private LinkedNode<T> top;
    private int count;

    /**
     * Constructor for the stack.
     */
    public LinkedStack() {
        this.count = 0;
        this.top = new LinkedNode();
        this.top.setNext(null);
    }

    /**
     * Add a new element to the stack.
     *
     * @param element
     */
    @Override
    public void push(T element) {
        if (this.isEmpty()) {
            this.top.setElement(element);
        } else {
            LinkedNode<T> node = new LinkedNode(element);
            node.setNext(this.top);
            this.top = node;
        }
        this.count++;
    }

    /**
     * Removes the element from the top of the stack.
     *
     * @return the element removed.
     * @throws InvalidOperationException
     */
    @Override
    public T pop() throws InvalidOperationException {
        if (this.isEmpty()) {
            throw new InvalidOperationException("The stack is empty!");
        }

        T deleted = this.top.getElement();
        if (this.size() != 1) {
            this.top = this.top.getNext();
        }

        this.count--;
        return deleted;
    }

    /**
     * Return the top element from the stack.
     *
     * @return the top element
     */
    @Override
    public T peek() {
        return this.top.getElement();
    }

    /**
     * Check if the stack is empty.
     *
     * @return boolean value
     */
    @Override
    public boolean isEmpty() {
        return (this.count == 0);
    }

    /**
     * Return the number of the elements stored in the stack.
     *
     * @return the number of elements
     */
    @Override
    public int size() {
        return this.count;
    }

    @Override
    public String toString() {
        String s = "\n Linked Stack:";
        if (this.isEmpty())
            s += "\n <Empty>";
        else {
            LinkedNode<T> current = this.top;
            while (current != null) {
                    s += "\n " + current.getElement().toString();
                    current = current.getNext();
                }
            }
       return s;       
    }
}
