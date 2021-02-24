package graph;

import exceptions.ElementNotFoundException;
import exceptions.FailureSpanningTreeException;
import exceptions.InvalidOperationException;
import exceptions.InvalidWeightValueException;
import exceptions.NullElementValueException;
import exceptions.RepeatedElementException;
import interfaces.GraphADT;
import queue.LinkedQueue;

/**
 * An implementation of the GraphADT interface for an undirected graph
 * using an adjacency matrix to indicate the presence/absence of edges
 * connecting vertices in the graph.
 */
public class AdjMatrixGraph<T> extends AdjMatrixDiGraph<T> implements GraphADT<T> {

    /**
     * Creates an empty graph.
     */
    public AdjMatrixGraph() {
        super();
    }

    /**
     * Overload method from AdjMatrixDiGraph.Inserts an edge between two vertex of the graph.
     *
     * @param vertex1 the first vertex
     * @param vertex2 the second vertex
     * @throws exceptions.NullElementValueException
     * @throws exceptions.ElementNotFoundException
     */
    @Override
    public void addEdge(T vertex1, T vertex2) throws NullElementValueException, ElementNotFoundException, InvalidWeightValueException {
        super.addEdge(vertex1, vertex2);
        int vpos1 = this.getIndex(vertex1);
        int vpos2 = this.getIndex(vertex2);
        adjMatrix[vpos2][vpos1] = true;
    }

    /**
     * Overload method from AdjMatrixDiGraph.Remove the edge between two vertex from an undirected graph.
     *
     * @param vertex1
     * @param vertex2
     * @throws ElementNotFoundException
     * @throws NullElementValueException
     * @throws InvalidOperationException
     */
    @Override
    public void removeEdge(T vertex1, T vertex2) throws ElementNotFoundException, NullElementValueException, InvalidOperationException {
        super.removeEdge(vertex1, vertex2);
        int vpos1 = this.getIndex(vertex1);
        int vpos2 = this.getIndex(vertex2);
        this.adjMatrix[vpos2][vpos1] = false;
    }

    /**
     * Build the spanning tree from the graph.
     *
     * @return the spanning tree
     * @throws FailureSpanningTreeException
     */
    public AdjMatrixGraph<T> spanningTree() throws FailureSpanningTreeException {
        if (this.isEmpty()) {
            throw new FailureSpanningTreeException("The graph is empty");
        }

        if (!this.isConnected()) {
            throw new FailureSpanningTreeException("The graph is not connected!");
        }

        boolean[] visited = new boolean[this.numVertices];
        AdjMatrixGraph<T> result = new AdjMatrixGraph<>();
        result.vertices = (T[]) new Object[this.numVertices];
        result.adjMatrix = new boolean[this.numVertices][this.numVertices];
        LinkedQueue<Integer> queue = new LinkedQueue<>();

        visited[0] = true;
        try {
            result.addVertex(this.vertices[0]);
        } catch (NullElementValueException | RepeatedElementException ex) {
        }

        try {
            queue.enqueue(0);
        } catch (NullElementValueException ex) {
        }

        while (result.numEdges < this.numVertices - 1 || !queue.isEmpty()) {
            int currentIndex = 0;
            try {
                currentIndex = queue.dequeue();
            } catch (InvalidOperationException ex) {
            }
            //Procurar vertices vizinhos não visitados
            for (int i = 0; i < this.numVertices; i++) {
                if (this.adjMatrix[currentIndex][i] && !visited[i]) {
                    result.vertices[i] = this.vertices[i];
                    result.numVertices++;
                    result.adjMatrix[currentIndex][i] = true;
                    result.adjMatrix[i][currentIndex] = true;
                    result.numEdges++;
                    visited[i] = true;
                    try {
                        queue.enqueue(i);
                    } catch (NullElementValueException ex) {
                    }
                }
            }
        }
        if (result.numEdges == this.numVertices - 1) {
            return result;
        } else {
            throw new FailureSpanningTreeException("Spanning Tree was not built correctly");
        }
    }
}
