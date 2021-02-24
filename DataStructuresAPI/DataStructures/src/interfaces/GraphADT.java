/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import exceptions.ElementNotFoundException;
import exceptions.InvalidOperationException;
import exceptions.InvalidWeightValueException;
import exceptions.NoPathAvailableException;
import exceptions.NullElementValueException;
import exceptions.RepeatedElementException;
import java.util.Iterator;
import linkedListSentinela.UnorderedLinkedList;

/**
 *
 * @author JoaoLopes 8190221
 */
/**
 * GraphADT defines the interface to a graph data structure.
 *
 */
public interface GraphADT<T> {

    /**
     * Adds a vertex to this graph, associating object with vertex.
     *
     * @param vertex the vertex to be added to this graph
     * @throws exceptions.NullElementValueException
     * @throws exceptions.ElementNotFoundException
     */
    public void addVertex(T vertex)throws NullElementValueException,ElementNotFoundException,RepeatedElementException;

    /**
     * Removes a single vertex with the given value from this graph.
     *
     * @param vertex the vertex to be removed from this graph
     * @throws exceptions.NullElementValueException
     * @throws exceptions.ElementNotFoundException
     */
    public void removeVertex(T vertex)throws NullElementValueException,ElementNotFoundException;

    /**
     * Inserts an edge between two vertices of this graph.
     *
     * @param vertex1 the first vertex
     * @param vertex2 the second vertex
     * @throws exceptions.NullElementValueException
     * @throws exceptions.ElementNotFoundException
     */
    public void addEdge(T vertex1, T vertex2)throws NullElementValueException,ElementNotFoundException,InvalidWeightValueException;

    /**
     * Removes an edge between two vertices of this graph.
     *
     * @param vertex1 the first vertex
     * @param vertex2 the second vertex
     * @throws exceptions.ElementNotFoundException
     * @throws exceptions.NullElementValueException
     * @throws exceptions.InvalidOperationException
     */
    public void removeEdge(T vertex1, T vertex2) throws ElementNotFoundException,NullElementValueException,InvalidOperationException;

    /**
     * Returns a breadth first iterator starting with the given vertex.
     *
     * @param startVertex the starting vertex
     * @return a breadth first iterator beginning at the given vertex
     * @throws exceptions.ElementNotFoundException
     * @throws exceptions.NullElementValueException
     * @throws exceptions.InvalidOperationException
     */
    public Iterator<T> iteratorBFS(T startVertex)throws ElementNotFoundException,NullElementValueException,InvalidOperationException;

    /**
     * Returns a depth first iterator starting with the given vertex.
     *
     * @param startVertex the starting vertex
     * @return a depth first iterator starting at the given vertex
     * @throws exceptions.ElementNotFoundException
     * @throws exceptions.NullElementValueException
     * @throws exceptions.InvalidOperationException
     */
    public Iterator<T> iteratorDFS(T startVertex)throws ElementNotFoundException,NullElementValueException,InvalidOperationException;

    /**
     * Returns an iterator that contains the shortest path between the two
     * vertices.
     *
     * @param startVertex the starting vertex
     * @param targetVertex the ending vertex
     * @return an iterator that contains the shortest path between the two
     * vertices
     * @throws exceptions.NullElementValueException
     * @throws exceptions.ElementNotFoundException
     */
    public Iterator<T> iteratorShortestPath(T startVertex, T targetVertex) throws NullElementValueException,ElementNotFoundException,NoPathAvailableException;

    /**
     * Returns true if this graph is empty, false otherwise.
     *
     * @return true if this graph is empty
     */
    public boolean isEmpty();

    /**
     * Returns true if this graph is connected, false otherwise.
     *
     * @return true if this graph is connected
     */
    public boolean isConnected();

    /**
     * Returns the number of vertices in this graph.
     *
     * @return the integer number of vertices in this graph
     */
    public int size();

    /**
     * Returns a string representation of the adjacency matrix.
     *
     * @return a string representation of the adjacency matrix
     */
    public String toString();
    
    /**
     * Check if one specific vertex is neighbor with other vertex.
     * @param origin
     * @param destiny
     * @return
     * @throws NullElementValueException
     * @throws ElementNotFoundException 
     */
    public boolean isNeighbor(T origin,T destiny) throws NullElementValueException, ElementNotFoundException;
    
    /**
     * Getter for the number of edges.
     * @return Number of edges.
     */
    public int getNumEdges();
    
    /**
     * Getter for the vertex.
     * @param index
     * @return vertex
     */
    public T getVertex(int index);
    
    /**
     * Check if the index of the vertex is valid.
     *
     * @param startIndex
     * @return boolean value
     */
    public boolean indexIsValid(int startIndex);
    
    /**
     * Getter for neighbors of a specific vertex.
     * @param vertex
     * @return Iterator for all neighbors
     */
    public UnorderedLinkedList<T> getNeighbors(T vertex) throws NullElementValueException, ElementNotFoundException;
}
