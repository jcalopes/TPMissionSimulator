/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import exceptions.ElementNotFoundException;
import exceptions.InvalidWeightValueException;
import exceptions.NoPathAvailableException;
import exceptions.NullElementValueException;
import java.util.Iterator;

/**
 *
 * @author JoaoLopes 8190221
 */
public interface NetworkADT<T> extends GraphADT<T> {

    /**
     * Inserts an edge between two vertices of this graph.
     *
     * @param vertex1 the first vertex
     * @param vertex2 the second vertex
     * @param weight the weight
     * @throws exceptions.NullElementValueException
     * @throws exceptions.ElementNotFoundException
     */
    public void addEdge(T vertex1, T vertex2, double weight)throws NullElementValueException,ElementNotFoundException,InvalidWeightValueException ;

    /**
     * Returns the weight of the shortest path in this network.
     *
     * @param vertex1 the first vertex
     * @param vertex2 the second vertex
     * @return the weight of the shortest path in this network
     */
    public Iterator<T> shortestPathWeight(T vertex1, T vertex2) throws NullElementValueException, ElementNotFoundException,NoPathAvailableException;
    public double shortestPathWeightCost(T vertex1, T vertex2) throws NullElementValueException, ElementNotFoundException,NoPathAvailableException;
}
