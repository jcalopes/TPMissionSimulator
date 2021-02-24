/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package list;

import exceptions.ElementNotFoundException;
import exceptions.InvalidOperationException;
import interfaces.ListADT;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *
 * Nome:João Carlos Abreu Lopes Número:8190221 Turma:T1
 */
public abstract class List<T> implements ListADT<T> {

    private final int DEFAULT_CAPACITY = 10;
    protected T[] list;
    protected int rear;
    protected int modCount;

    public List() {
        this.rear = 0;
        this.list = (T[]) new Object[DEFAULT_CAPACITY];
        this.modCount=0;
    }

    public List(int capacity) {
        this.rear = 0;
        this.list = (T[]) new Object[capacity];
        this.modCount=0;
    }

    @Override
    public T removeFirst() throws InvalidOperationException {
        if (this.isEmpty()) {
            throw new InvalidOperationException("The list is empty!");
        }

        T removed = this.list[0];

        for (int i = 0; i < this.rear - 1; i++) {
            this.list[i] = this.list[i + 1];
        }
        this.list[this.rear-1]=null;
        this.rear--;
        this.modCount++;
        return removed;
    }

    @Override
    public T removeLast() throws InvalidOperationException {
        if (this.isEmpty()) {
            throw new InvalidOperationException("The list is empty!");
        }

        T removed = this.list[rear - 1];
        this.list[rear - 1] = null;
        this.rear--;
        this.modCount++;
        return removed;
    }

    @Override
    public T remove(T element) throws ElementNotFoundException {
        if (!this.contains(element)) {
            throw new ElementNotFoundException("The element doesn´t exist in the list!");
        } else {
            int posElement=posElement(element);
            for(int i=posElement;i<this.rear-1;i++){
                this.list[i]=this.list[i+1];
            }
        }
        this.list[rear-1]=null;
        this.rear--;
        this.modCount++;
        return element;
    }

    @Override
    public T first() throws InvalidOperationException {
        if (!this.isEmpty()) {
            return this.list[0];
        } else {
            throw new InvalidOperationException("The list is empty!");
        }
    }

    @Override
    public T last() throws InvalidOperationException {
        if (!this.isEmpty()) {
            return (this.list[rear - 1]);
        } else {
            throw new InvalidOperationException("The list is empty!");
        }
    }

    @Override
    public boolean contains(T target) {
        for(int i=0;i<this.rear;i++){
            if(this.list[i].equals(target))
                return true;
        }
        return false;
    }

    @Override
    public boolean isEmpty() {
        return (this.rear == 0);
    }

    @Override
    public int size() {
        return this.rear;
    }

    @Override
    public Iterator<T> iterator() {
        return new IteratorArray();
    }

    protected int posElement(T element)throws ElementNotFoundException {
        for (int i = 0; i < this.rear; i++) {
            if (this.list[i].equals(element)) {
                return i;
            }
        }
        throw new ElementNotFoundException("The element doesn´t exist!");
    }
    
    public void expandCapacity(){
        if(this.size()==this.list.length){
            T[] temp=(T[])new Object[this.list.length+DEFAULT_CAPACITY];
            for(int i=0;i<this.size();i++){
                temp[i]=this.list[i];
            }
            this.list=temp;
        }
    }
    
    public class IteratorArray implements Iterator<T> {  
    private int current;
    private int expectedModCount;
    private Boolean okToRemove;
    
    public IteratorArray(){
        this.current=-1;
        this.expectedModCount=modCount;
        this.okToRemove=false;
    }
    
    @Override
    public boolean hasNext() {
        if(modCount==this.expectedModCount){
            return (this.current+1<rear);
        }
        else{
            throw new ConcurrentModificationException("The list was changed!");
        }   
    }

    @Override
    public T next() {
        if(this.hasNext()){
            this.okToRemove=true;
            return list[++this.current];
        }
        else{
            throw new NoSuchElementException();
        }          
    }
    
    @Override
    public void remove(){
       if(okToRemove){
           try {
               List.this.remove(list[current]);
               expectedModCount++;
               okToRemove=false;
           } catch (ElementNotFoundException ex) {
               System.out.println(ex);
           }
       }
       else{
           throw new IllegalStateException();
       }
    }
    
}
    @Override
     public String toString(){
        String s="\n ArrayList";
        for(int i=0;i<this.rear;i++){
            s+="\n"+list[i].toString();
        }
        return s;
    }
}
