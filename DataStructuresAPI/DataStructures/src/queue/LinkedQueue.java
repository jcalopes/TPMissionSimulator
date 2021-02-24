
package queue;

import exceptions.InvalidOperationException;
import exceptions.NullElementValueException;
import interfaces.QueueADT;
import linkedListSentinela.LinkedNode;

/**
 * This class store a queue implemented by a linked list.
 */
public class LinkedQueue<T> implements QueueADT<T> {

    private LinkedNode<T> front;
    private LinkedNode<T> rear;
    private int count;

    /**
     * Constructor for the queue.
     */
    public LinkedQueue() {
        this.count = 0;
        this.front = null;
        this.rear = null;
    }

    /**
     * Add a new element to the queue.
     *
     * @param element
     * @throws NullElementValueException
     */
    @Override
    public void enqueue(Object element) throws NullElementValueException {
        if (element == null) {
            throw new NullElementValueException("Element contain null value!");
        }

        LinkedNode<T> node = new LinkedNode(element);

        if (this.size() == 0) {
            this.front = node;
            this.rear = node;
        } else {
            this.rear.setNext(node);
            this.rear = node;
        }
        count++;
    }

    /**
     * Remove the element to the queue.
     *
     * @return the element removed.
     * @throws InvalidOperationException
     */
    @Override
    public T dequeue() throws InvalidOperationException {
        if (this.isEmpty()) {
            throw new InvalidOperationException("The queue is empty!");
        }

        T removed = this.front.getElement();

        this.front = this.front.getNext();
        if (this.size() == 0) {
            this.rear = null;
        }

        count--;
        return removed;
    }

    /**
     * Return the first element in the queue.
     *
     * @return the first element
     */
    @Override
    public T first() {
        return this.front.getElement();
    }

    /**
     * Check if the queue is empty.
     *
     * @return bollean value
     */
    @Override
    public boolean isEmpty() {
        return (this.front == null);
    }

    /**
     * Return the number of elements in the queue.
     *
     * @return the number of the elements
     */
    @Override
    public int size() {
        return this.count;
    }

    @Override
    public String toString() {
        String info = "\n Linked Queue";
        LinkedNode<T> current = this.front;

        while (current != null) {
            info += "\n " + current.toString();
            current = current.getNext();
        }
        return info;
    }
}
