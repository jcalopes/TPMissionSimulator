/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package interfaces;

import exceptions.NullElementValueException;

/**
 *
 * @author JoaoLopes 8190221
 */
public interface OrderedListADT<T> extends ListADT<T> {
    public void add(T element)throws NullElementValueException;
}
