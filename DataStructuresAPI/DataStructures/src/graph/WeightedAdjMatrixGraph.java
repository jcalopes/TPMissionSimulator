
package graph;

import exceptions.ElementNotFoundException;
import exceptions.FailureSpanningTreeException;
import exceptions.InvalidOperationException;
import exceptions.InvalidWeightValueException;
import exceptions.NullElementValueException;
import heap.LinkedHeap;
import interfaces.NetworkADT;

/**
 *
 * An implementation of the GraphADT interface for a undirected graph
 * using an adjacency matrix to indicate the presence/absence of edges
 * connecting vertices in the graph.
 */
public class WeightedAdjMatrixGraph<T> extends WeightedAdjMatrixDiGraph<T> implements NetworkADT<T> {

    public WeightedAdjMatrixGraph() {
        super();
    }

    /**
     * Overload method from WeightedAdjMatrixDiGraph. Add a vertex with a
     * specific weight.
     *
     * @param vertex1
     * @param vertex2
     * @param weight
     * @throws NullElementValueException
     * @throws ElementNotFoundException
     */
    @Override
    public void addEdge(T vertex1, T vertex2, double weight) throws NullElementValueException, ElementNotFoundException, InvalidWeightValueException {
        super.addEdge(vertex1, vertex2, weight);
        int vpos1 = this.getIndex(vertex1);
        int vpos2 = this.getIndex(vertex2);
        this.weights[vpos2][vpos1] = weight;
        this.adjMatrix[vpos2][vpos1] = true;
    }

    /**
     * Overload method from WeightedAdjMatrixDiGraph.
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
        this.weights[vpos2][vpos1] = -1;
        this.adjMatrix[vpos2][vpos1] = false;
    }

    /**
     * Returns a minimum spanning tree of the network by Prim´s algorithm
     *
     * @return a minimum spanning tree of the network
     */
    public WeightedAdjMatrixGraph<T> mstNetwork(T src) throws NullElementValueException, ElementNotFoundException, InvalidOperationException, FailureSpanningTreeException {
        int root = this.getIndex(src);
        LinkedHeap<WeightedNode> minHeap = new LinkedHeap<>();
        boolean allVerticesVisited = false;
        boolean[] visited = new boolean[this.numVertices];
        WeightedAdjMatrixGraph<T> resultGraph = new WeightedAdjMatrixGraph<>();
        if (isEmpty()) {
            throw new FailureSpanningTreeException("The graph is empty");
        }
        if (!this.isConnected()) {
            throw new FailureSpanningTreeException("The graph is not connected");
        }

        resultGraph.vertices = (T[]) new Object[this.numVertices];
        resultGraph.adjMatrix = new boolean[this.numVertices][this.numVertices];
        resultGraph.weights = new double[this.numVertices][this.numVertices];

        visited[root] = true;
        resultGraph.vertices[root] = this.vertices[root];
        resultGraph.numVertices++;
        //Procurar arestas incidentes no vertice inicial
        for (int i = 0; i < this.numVertices; i++) {
            if (this.adjMatrix[root][i]) {
                WeightedNode node = new WeightedNode(root, this.weights[root][i]);
                minHeap.addElement(node);
            }
        }

        while (resultGraph.numEdges < this.numVertices - 1 && !allVerticesVisited) {
            WeightedNode minCost = null;
            try {
                minCost = minHeap.removeMin();
            } catch (InvalidOperationException ex) {
            }

            //Encontrar a aresta com menor custo
            int i = 0;
            boolean foundEdge = false;
            while (!foundEdge && i < this.numVertices) {
                if (this.weights[minCost.getIndex()][i] == minCost.getCost() && !visited[i]) {
                    resultGraph.adjMatrix[minCost.getIndex()][i] = true;
                    resultGraph.adjMatrix[i][minCost.getIndex()] = true;
                    resultGraph.vertices[i] = this.vertices[i];
                    resultGraph.numVertices++;
                    resultGraph.weights[minCost.getIndex()][i] = minCost.getCost();
                    resultGraph.weights[i][minCost.getIndex()] = minCost.getCost();
                    resultGraph.numEdges++;
                    foundEdge = true;
                    visited[i] = true;
                } else {
                    i++;
                }
            }
            //Adicionar arestas incidentes do vertice adicionado
            if (foundEdge) {
                for (int j = 0; j < this.numVertices; j++) {
                    if (this.adjMatrix[i][j] && !visited[j]) {
                        WeightedNode node = new WeightedNode(i, this.weights[i][j]);
                        minHeap.addElement(node);
                    }
                }
            } else {
                allVerticesVisited = true;
            }
        }
        return resultGraph;
    }

}
