
package queue;

import exceptions.InvalidOperationException;
import exceptions.NullElementValueException;
import interfaces.QueueADT;

/**
 * This class store a queue implemented by an array.
 */
public class CircularArrayQueue<T> implements QueueADT<T>{
    private final int DEFAULT_CAPACITY=10;
    private int front;
    private int rear;
    private int count;
    private T[] queue;
    
    /**
     * Constructor for the queue.
     */
    public CircularArrayQueue(){
        this.queue=(T[])new Object[DEFAULT_CAPACITY];
        this.count=0;
        this.front=0;
        this.rear=0;
    }
    
    /**
     * Constructor for the queue.
     * @param capacity 
     */
    public CircularArrayQueue(int capacity){
        this.queue=(T[]) new Object[capacity];
        this.count=0;
        this.front=0;
        this.rear=0;
    }
    
    /**
     * Resize the capacity of the array.
     */
    private void expandCapacity(){
        T[] tempQueue=(T[])new Object[this.count+DEFAULT_CAPACITY];
        int current=this.front;
        
        for(int i=0;i<this.count;i++){
            tempQueue[i]=this.queue[current++];
            if(current==this.queue.length){
                current=0;
            }
        }
        this.queue=tempQueue;
        this.front=0;
        this.rear=this.count;
    }
    
    /**
     * Add a new element to the queue.
     * @param element
     * @throws NullElementValueException 
     */
    @Override
    public void enqueue(T element) throws NullElementValueException {
        if(element==null)throw new NullElementValueException("The element is null!");
        int length=this.queue.length;
        if(this.count==length){
            this.expandCapacity();
        }
        /*Alternativa menos eficiente
        if(this.rear==this.queue.length){
            this.rear=0;
        }
        
        this.queue[rear++]=element;
        */
        this.queue[this.rear]=element;
        this.rear=(this.rear+1)%this.queue.length;

        this.count++;
    }

    /**
     * Remove the element to the queue.
     * @return the element removed.
     * @throws InvalidOperationException 
     */
    @Override
    public T dequeue() throws InvalidOperationException {
        if(this.isEmpty())throw new InvalidOperationException("The queue is empty!");
        
        T removed=this.queue[this.front];
        this.queue[this.front]=null;
        
         /*Alternativa menos eficiente
        this.front++;
        
        if(this.front==this.queue.length){
            this.front=0;}
        */
        this.front=(this.front+1)%this.queue.length; 
         
        this.count--;
        return removed;
    }

    /**
     * Return the first element in the queue.
     * @return the first element
     */
    @Override
    public T first() {
        return this.queue[this.front];
    }

    /**
     * Check if the queue is empty.
     * @return bollean value
     */
    @Override
    public boolean isEmpty() {
         return (this.count==0);
    }

    /**
     * Return the number of elements in the queue.
     * @return the number of the elements
     */
    @Override
    public int size() {
       return this.count;
    }
    
    public String toString(){
        String info="\n Circular Array Queue";
        int current=this.front;
        
        for(int i=0;i<this.count;i++){
            info+="\n "+ this.queue[current++];
            if(current==this.queue.length){
                current=0;}
        }
        return info;
    }
}
