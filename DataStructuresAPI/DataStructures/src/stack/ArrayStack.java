package stack;

import exceptions.InvalidOperationException;
import interfaces.StackADT;

/**
 * This class store an stack implemented by an array.
 */
public class ArrayStack<T> implements StackADT<T> {

    private T[] stack;
    private int top;
    private final int DEFAULT_CAPACITY = 10;

    /**
     * Constructor for the stack.
     */
    public ArrayStack() {
        this.top = 0;
        this.stack = (T[]) new Object[DEFAULT_CAPACITY];
    }

    /**
     * Constructor for the stack.
     *
     * @param capacity
     */
    public ArrayStack(int capacity) {
        this.top = 0;
        this.stack = (T[]) new Object[capacity];
    }

    /**
     * Resize the capacity of the array.
     */
    private void expandCapacity() {
        T[] tempStack = (T[]) new Object[this.size() + DEFAULT_CAPACITY];

        for (int i = 0; i < this.size(); i++) {
            tempStack[i] = this.stack[i];
        }
        this.stack = tempStack;
    }

    /**
     * Add a new element to the stack.
     *
     * @param element
     */
    @Override
    public void push(T element) {
        int capacity = this.capacity();
        if (top == capacity) {
            this.expandCapacity();
        }

        this.stack[top++] = element;
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

        this.top--;
        return this.stack[top];
    }

    /**
     * Return the top element from the stack.
     *
     * @return the top element
     */
    @Override
    public T peek() {
        return this.stack[top - 1];
    }

    /**
     * Check if the stack is empty.
     *
     * @return boolean value
     */
    @Override
    public boolean isEmpty() {
        return (this.size() == 0);
    }

    /**
     * Return the number of the elements stored in the stack.
     *
     * @return the number of elements
     */
    @Override
    public int size() {
        return this.top;
    }

    private int capacity() {
        return this.stack.length;
    }

    @Override
    public String toString() {
        String s = "\n Stack:";

        for (int i = 0; i < this.top; i++) {
            s += "\n =" + this.stack[i];
        }

        return s;
    }
}
