/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graph;

import exceptions.ElementNotFoundException;
import exceptions.InvalidOperationException;
import exceptions.InvalidWeightValueException;
import exceptions.NoPathAvailableException;
import exceptions.NullElementValueException;
import exceptions.RepeatedElementException;
import interfaces.GraphADT;
import java.util.Iterator;
import linkedListSentinela.UnorderedLinkedList;
import list.UnorderedArrayList;
import queue.LinkedQueue;
import stack.LinkedStack;

/**
 * An implementation of the GraphADTinterface for a directed graph
 * using an adjacency matrix to indicate the presence/absence of edges
 * connecting vertices in the graph.
 */
public class AdjMatrixDiGraph<T> implements GraphADT<T> {

    protected final int DEFAULT_CAPACITY = 3;
    protected int numVertices; // number of vertices in the graph
    protected boolean[][] adjMatrix; // adjacency matrix
    protected T[] vertices; // values of vertices
    protected int numEdges = 0;// number of edges in the graph

    /**
     * Constructor. Create an empty instance of a directed graph.
     */
    public AdjMatrixDiGraph() {
        this.numVertices = 0;
        this.adjMatrix = new boolean[DEFAULT_CAPACITY][DEFAULT_CAPACITY];
        this.vertices = (T[]) new Object[DEFAULT_CAPACITY];
    }

    /**
     * Getter for the vertex index.
     *
     * @param vertex
     * @return the index value
     * @throws NullElementValueException
     * @throws ElementNotFoundException
     */
    public int getIndex(T vertex) throws NullElementValueException, ElementNotFoundException {
        if (vertex == null) {
            throw new NullElementValueException("The vertex is null");
        }

        for (int i = 0; i < this.numVertices; i++) {
            if (this.vertices[i].equals(vertex)) {
                return i;
            }
        }
        throw new ElementNotFoundException("Vertex not found!");

    }

    /**
     * Check if the index of the vertex is valid.
     *
     * @param startIndex
     * @return boolean value
     */
    @Override
    public boolean indexIsValid(int startIndex) {
        return (startIndex >= 0 && startIndex < this.numVertices);
    }

    /**
     * Inserts an edge between two vertices of the graph.
     *
     * @param vertex1 the first vertex
     * @param vertex2 the second vertex
     * @throws exceptions.NullElementValueException
     * @throws exceptions.ElementNotFoundException
     */
    @Override
    public void addEdge(T vertex1, T vertex2) throws NullElementValueException, ElementNotFoundException, InvalidWeightValueException {
        addEdge(getIndex(vertex1), getIndex(vertex2));
        this.numEdges++;
    }

    /**
     * Inserts an edge between two vertices of the graph.
     *
     * @param index1 the first index
     * @param index2 the second index
     */
    private void addEdge(int index1, int index2) {
        if (indexIsValid(index1) && indexIsValid(index2)) {
            adjMatrix[index1][index2] = true;
        }
    }

    /**
     * Resize the array of vertex.
     */
    private void expandCapacity() {
        T[] temp = (T[]) new Object[this.vertices.length + DEFAULT_CAPACITY];

        boolean[][] newAdj = new boolean[this.vertices.length + DEFAULT_CAPACITY][this.vertices.length + DEFAULT_CAPACITY];

        for (int i = 0; i < this.numVertices; i++) {
            for (int j = 0; j < this.numVertices; j++) {
                newAdj[i][j] = this.adjMatrix[i][j];
            }
            //shift array de vertices
            temp[i] = this.vertices[i];
        }

        this.vertices = temp;
        this.adjMatrix = newAdj;
    }

    /**
     * Adds a vertex to the graph, expanding the capacity of the graph if
     * necessary.It also associates an object with the vertex.
     *
     * @param vertex the vertex to add to the graph
     * @throws exceptions.RepeatedElementException
     */
    @Override
    public void addVertex(T vertex) throws NullElementValueException, RepeatedElementException {
        if (vertex == null) {
            throw new NullElementValueException("The element is null!");
        }
        if (numVertices == vertices.length) {
            expandCapacity();
        }

        try {
            this.getIndex(vertex);
            throw new RepeatedElementException("The vertex already exist!");
        } catch (ElementNotFoundException ex) {
            vertices[numVertices] = vertex;
            for (int i = 0; i <= numVertices; i++) {
                adjMatrix[numVertices][i] = false;
                adjMatrix[i][numVertices] = false;
            }
            numVertices++;
        }
    }

    /**
     * Returns an iterator that performs a breadth first search traversal
     * starting at the given index.
     *
     * @param startIndex the index to begin the search from
     * @return an iterator that performs a breadth first traversal
     * @throws exceptions.NullElementValueException
     * @throws exceptions.InvalidOperationException
     */
    private Iterator<T> iteratorBFS(int startIndex) throws NullElementValueException, InvalidOperationException {
        Integer x;
        LinkedQueue<Integer> traversalQueue = new LinkedQueue<>();
        UnorderedArrayList<T> resultList = new UnorderedArrayList<>();
        if (!indexIsValid(startIndex)) {
            return resultList.iterator();
        }

        boolean[] visited = new boolean[numVertices];
        for (int i = 0; i < numVertices; i++) {
            visited[i] = false;
        }

        traversalQueue.enqueue(startIndex);
        visited[startIndex] = true;
        while (!traversalQueue.isEmpty()) {
            x = traversalQueue.dequeue();
            resultList.addToRear(vertices[x]);
            /**
             * Find all vertices adjacent to x that have not been visited and
             * queue them up
             */
            for (int i = 0; i < numVertices; i++) {
                if (adjMatrix[x][i] && !visited[i]) {
                    traversalQueue.enqueue(i);
                    visited[i] = true;
                }
            }
        }
        return resultList.iterator();
    }

    /**
     * Returns an iterator that performs a depth first search traversal starting
     * at the given index.
     *
     * @param startIndex the index to begin the search traversal from
     * @return an iterator that performs a depth first traversal
     */
    private Iterator<T> iteratorDFS(int startIndex) {
        Integer x;
        boolean found;
        LinkedStack<Integer> traversalStack = new LinkedStack<>();
        UnorderedArrayList<T> resultList = new UnorderedArrayList<>();
        boolean[] visited = new boolean[numVertices];
        if (!indexIsValid(startIndex)) {
            return resultList.iterator();
        }
        for (int i = 0; i < numVertices; i++) {
            visited[i] = false;
        }

        traversalStack.push(startIndex);
        try {
            resultList.addToRear(vertices[startIndex]);
        } catch (NullElementValueException ex) {}
        visited[startIndex] = true;
        while (!traversalStack.isEmpty()) {
            x = traversalStack.peek();
            found = false;
            /**
             * Find a vertex adjacent to x that has not been visited and push it
             * on the stack
             */
            for (int i = 0; (i < numVertices) && !found; i++) {
                if (adjMatrix[x][i] && !visited[i]) {
                    traversalStack.push(i);
                    try {
                        resultList.addToRear(vertices[i]);
                    } catch (NullElementValueException ex) {}
                    visited[i] = true;
                    found = true;
                }
            }
            if (!found && !traversalStack.isEmpty()) {
                try {
                    traversalStack.pop();
                } catch (InvalidOperationException ex) {
                }
            }
        }
        return resultList.iterator();
    }

    /**
     * Remove vertex from the digraph.
     *
     * @param vertex
     * @throws ElementNotFoundException
     * @throws NullElementValueException
     */
    @Override
    public void removeVertex(T vertex) throws ElementNotFoundException, NullElementValueException {

        //shift coluna
        for (int j = getIndex(vertex); j < this.numVertices - 1; j++) {
            for (int i = 0; i < this.numVertices; i++) {
                this.adjMatrix[i][j] = this.adjMatrix[i][j + 1];
            }
        }

        for (int j = getIndex(vertex); j < this.numVertices - 1; j++) {
            for (int i = 0; i < this.numVertices; i++) {
                this.adjMatrix[j][i] = this.adjMatrix[j + 1][i];
            }
            //shift do array vertices
            this.vertices[j] = this.vertices[j + 1];
        }

        for (int i = 0; i < this.numVertices; i++) {
            this.adjMatrix[i][this.numVertices - 1] = false;
            this.adjMatrix[this.numVertices - 1][i] = false;
        }
        this.numVertices--;
    }

    /**
     * Remove edge between two vertex from the digraph.
     *
     * @param vertex1
     * @param vertex2
     * @throws ElementNotFoundException
     * @throws NullElementValueException
     * @throws InvalidOperationException
     */
    @Override
    public void removeEdge(T vertex1, T vertex2) throws ElementNotFoundException, NullElementValueException, InvalidOperationException {
        int pos1 = getIndex(vertex1);
        int pos2 = getIndex(vertex2);

        if (this.adjMatrix[pos1][pos2]) {
            this.adjMatrix[pos1][pos2] = false;
        } else {
            throw new InvalidOperationException("This edge doesn´t exist!");
        }
    }

    /**
     * Returns a breath first search iterator from the digraph.
     *
     * @param startVertex
     * @return an iterator
     * @throws ElementNotFoundException
     * @throws InvalidOperationException
     * @throws NullElementValueException
     */
    @Override
    public Iterator<T> iteratorBFS(T startVertex) throws ElementNotFoundException, InvalidOperationException, NullElementValueException {
        int index = getIndex(startVertex);

        return this.iteratorBFS(index);
    }

    /**
     * Returns a depth first search iterator from the digraph.
     *
     * @param startVertex
     * @return an iterator
     * @throws InvalidOperationException
     * @throws NullElementValueException
     * @throws ElementNotFoundException
     */
    @Override
    public Iterator<T> iteratorDFS(T startVertex) throws InvalidOperationException, NullElementValueException, ElementNotFoundException {
        int index = getIndex(startVertex);

        return this.iteratorDFS(index);
    }

    /**
     * Find the shortest path between two valid vertex.
     *
     * @param startVertex
     * @param targetVertex
     * @return an iterator
     * @throws NullElementValueException
     * @throws ElementNotFoundException
     * @throws NoPathAvailableException
     */
    @Override
    public Iterator<T> iteratorShortestPath(T startVertex, T targetVertex) throws NullElementValueException, ElementNotFoundException, NoPathAvailableException {
        int startIndex = getIndex(startVertex);
        int targetIndex = getIndex(targetVertex);
        UnorderedArrayList<T> resultList = new UnorderedArrayList<>();
        if (startVertex.equals(targetVertex)) {
            throw new NoPathAvailableException("The elements are equals");
        }
        int[] dist = new int[this.numVertices];
        int[] predecessor = new int[this.numVertices];

        Integer x = 0;
        LinkedQueue<Integer> traversalQueue = new LinkedQueue<>();

        if (!indexIsValid(startIndex)) {
            return resultList.iterator();
        }

        boolean[] visited = new boolean[numVertices];

        for (int i = 0; i < numVertices; i++) {
            visited[i] = false;
        }

        traversalQueue.enqueue(startIndex);
        visited[startIndex] = true;
        dist[0] = 0;
        predecessor[0] = -1;
        boolean found = false;
        while (!traversalQueue.isEmpty() && !found) {
            try {
                x = traversalQueue.dequeue();
                resultList.addToRear(this.vertices[x]);
            } catch (InvalidOperationException ex) {
            }
            /**
             * Identificar a vizinhança
             */
            for (int i = 0; i < numVertices; i++) {
                if (adjMatrix[x][i] && !visited[i]) {
                    predecessor[i] = x;
                    dist[i]++;
                    traversalQueue.enqueue(i);
                    visited[i] = true;
                    if (i == targetIndex) {
                        found = true;
                    }
                }
            }
        }
        if (!found) {
            throw new NoPathAvailableException("No path between vertex available!");
        }
        UnorderedArrayList<T> pathList = new UnorderedArrayList<>();
        int current = targetIndex;

        while (current != startIndex) {
            pathList.addToFront(this.vertices[current]);
            current = predecessor[current];
        }
        pathList.addToFront(this.vertices[current]);

        return pathList.iterator();
    }

    /**
     * Check if the graph is empty.
     *
     * @return
     */
    @Override
    public boolean isEmpty() {
        return (this.numVertices == 0);
    }

    /**
     * Check if the graph is connected.
     *
     * @return boolean value
     */
    @Override
    public boolean isConnected() {
        Iterator<T> it = null;
        int count = 0;
        try {
            it = this.iteratorBFS(0);
        } catch (NullElementValueException | InvalidOperationException ex) {
        }

        while (it.hasNext()) {
            it.next();
            count++;
        }
        return (count == this.numVertices);
    }

    /**
     * Return the number of vertex from the graph.
     *
     * @return
     */
    @Override
    public int size() {
        return this.numVertices;
    }

    /**
     * Getter for vertex.
     * @param vertex
     * @return vertex
     * @throws NullElementValueException
     * @throws ElementNotFoundException 
     */
    public T getVertex(T vertex) throws NullElementValueException, ElementNotFoundException {
        int index = this.getIndex(vertex);
        return this.vertices[index];
    }

    /**
     * Check if one vertex is neighbor with other.
     * @param origin
     * @param destiny
     * @return
     * @throws NullElementValueException
     * @throws ElementNotFoundException 
     */
    @Override
    public boolean isNeighbor(T origin,T destiny) throws NullElementValueException, ElementNotFoundException{
        if(origin==null || destiny==null){
            throw new NullElementValueException("The elements are invalid!");
        }
        
        int originIndex=this.getIndex(origin);
        int destinyIndex = this.getIndex(destiny);

        return this.adjMatrix[originIndex][destinyIndex];
    }

    /**
     * Getter for the number of edges.
     * @return Number of edges
     */
    @Override
    public int getNumEdges() {
        return numEdges;
    }

    /**
     * Getter for the vertex.
     * @param index
     * @return vertex
     */
    @Override
    public T getVertex(int index){
        if(index<0 || index>=this.numVertices){
            throw new IndexOutOfBoundsException("Invalid index");
        }
        
        return this.vertices[index];
    }

    /**
     * Getter for the vertex.
     * @return List of vertex
     */
    public T[] getVertices() {
        return vertices;
    }
    
    /**
     * Getter for neighbors of a specific vertex.
     * @param vertex
     * @return Iterator for all neighbors
     */
    @Override
    public UnorderedLinkedList<T> getNeighbors(T vertex) throws NullElementValueException, ElementNotFoundException{
        UnorderedLinkedList<T> neighbors=new UnorderedLinkedList<>();
        int index=this.getIndex(vertex);
        
        for(int i=0;i<this.numVertices;i++){
            if(this.adjMatrix[index][i]){
                neighbors.addToRear(this.vertices[i]);
            }
        }
        return neighbors;
    }
    
    
    /**
     * Print a representation of the digraph.
     *
     * @return
     */
    @Override
    public String toString() {
        String s = "\n";
        for (int i = 0; i < this.numVertices; i++) {
            for (int j = 0; j < this.numVertices; j++) {
                if (adjMatrix[i][j]) {
                    s += 1 + "  ";
                } else {
                    s += 0 + "  ";
                }
            }
            s += "\n \n";
        }
        return s;
    }
}
