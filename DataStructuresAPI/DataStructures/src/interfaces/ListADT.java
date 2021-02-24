/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package interfaces;

import exceptions.ElementNotFoundException;
import exceptions.InvalidOperationException;
import java.util.Iterator;

/**
 *
 * @author JoaoLopes 8190221
 */
public interface ListADT<T> extends Iterable<T>{
    public T removeFirst()throws InvalidOperationException;
    public T removeLast()throws InvalidOperationException;
    public T remove(T element)throws ElementNotFoundException;
    public T first()throws InvalidOperationException;
    public T last()throws InvalidOperationException;
    public boolean contains(T target);
    public boolean isEmpty();
    public int size();
    public Iterator<T> iterator();
    public String toString();
}
