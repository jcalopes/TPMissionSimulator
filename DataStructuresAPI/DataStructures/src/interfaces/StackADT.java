/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package interfaces;

import exceptions.InvalidOperationException;

/**
 *
 * @author JoaoLopes 8190221
 */
public interface StackADT<T> {
    
    public void push(T element);
    public T pop()throws InvalidOperationException;
    public T peek();
    public boolean isEmpty();
    public int size();
    @Override
    public String toString();
}
