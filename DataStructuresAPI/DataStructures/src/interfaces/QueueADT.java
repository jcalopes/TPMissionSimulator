/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package interfaces;

import exceptions.InvalidOperationException;
import exceptions.NullElementValueException;

/**
 *
 * @author JoaoLopes 8190221
 */
public interface QueueADT<T> {
    public void enqueue(T element)throws NullElementValueException;
    public T dequeue()throws InvalidOperationException;
    public T first();
    public boolean isEmpty();
    public int size();
    public String toString();
}
