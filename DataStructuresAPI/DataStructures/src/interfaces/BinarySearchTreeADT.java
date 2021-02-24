/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package interfaces;

import exceptions.ElementNotFoundException;
import exceptions.InvalidOperationException;

/**
 *
 */
/**
 * BinarySearchTreeADT defines the interface to a binary search tree.
 *
 */
public interface BinarySearchTreeADT<T> extends BinaryTreeADT<T> {

    /**
     * Adds the specified element to the proper location in this tree.
     *
     * @param element the element to be added to this tree
     */
    public void addElement(T element)throws InvalidOperationException,ClassCastException;

    /**
     * Removes and returns the specified element from this tree.
     *
     * @param targetElement the element to be removed from this tree
     * @return the element removed from this tree
     */
    public T removeElement(T targetElement)throws ElementNotFoundException,InvalidOperationException;

    /**
     * Removes all occurences of the specified element from this tree.
     *
     * @param targetElement the element that the list will have all instances of
     * it removed
     */
    public void removeAllOccurrences(T targetElement)throws ElementNotFoundException,InvalidOperationException;

    /**
     * Removes and returns the smallest element from this tree.
     *
     * @return the smallest element from this tree.
     */
    public T removeMin()throws InvalidOperationException;

    /**
     * Removes and returns the largest element from this tree.
     *
     * @return the largest element from this tree
     */
    public T removeMax()throws InvalidOperationException;

    /**
     * Returns a reference to the smallest element in this tree.
     *
     * @return a reference to the smallest element in this tree
     */
    public T findMin()throws ElementNotFoundException ;

    /**
     * Returns a reference to the largest element in this tree.
     *
     * @return a reference to the largest element in this tree
     */
    public T findMax()throws ElementNotFoundException ;

}
