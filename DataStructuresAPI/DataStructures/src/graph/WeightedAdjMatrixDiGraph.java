package graph;

import exceptions.ElementNotFoundException;
import exceptions.InvalidOperationException;
import exceptions.InvalidWeightValueException;
import exceptions.NoPathAvailableException;
import exceptions.NullElementValueException;
import exceptions.RepeatedElementException;
import heap.ArrayHeap;
import interfaces.NetworkADT;
import java.util.Iterator;
import list.UnorderedArrayList;

/**
 * An implementation of the GraphADT interface for an weighted directed
 * graph using an adjacency matrix to indicate the presence/absence of edges
 * connecting vertices in the graph.
 *
 */
public class WeightedAdjMatrixDiGraph<T> extends AdjMatrixDiGraph<T> implements NetworkADT<T> {

    protected double[][] weights;
    private final int DEFAULT_WEIGHT = 5;

    /**
     * Construtor for a weighted adjacency matrix of an oriented graph.
     */
    public WeightedAdjMatrixDiGraph() {
        super();
        this.weights = new double[DEFAULT_CAPACITY][DEFAULT_CAPACITY];
        for (int i = 0; i < DEFAULT_CAPACITY; i++) {
            for (int j = 0; j < DEFAULT_CAPACITY; j++) {
                this.weights[i][j] = -1;
            }
        }
    }

    /**
     * Overload addEdge of AdjMatrixDiGraph.Inserts an edge between two vertices
     * of the graph.
     *
     * @param vertex1 the first vertex
     * @param vertex2 the second vertex
     * @throws exceptions.NullElementValueException
     * @throws exceptions.ElementNotFoundException
     */
    @Override
    public void addEdge(T vertex1, T vertex2, double weight) throws NullElementValueException, ElementNotFoundException, InvalidWeightValueException {
        if (weight < 0) {
            throw new InvalidWeightValueException("The weight value must be at least 0!");
        }
        super.addEdge(vertex1, vertex2);
        int vpos1 = this.getIndex(vertex1);
        int vpos2 = this.getIndex(vertex2);
        this.weights[vpos1][vpos2] = weight;
    }

    /**
     * Overload method from AdjMatrixDiGraph.Inserts an edge between two
     * vertices of the graph.
     *
     * @param vertex1 the first vertex
     * @param vertex2 the second vertex
     * @throws exceptions.NullElementValueException
     * @throws exceptions.ElementNotFoundException
     */
    @Override
    public void addEdge(T vertex1, T vertex2) throws NullElementValueException, ElementNotFoundException, InvalidWeightValueException {
        this.addEdge(vertex1, vertex2, DEFAULT_WEIGHT);
    }

    /**
     * Resize the weights array.
     */
    protected void expandCapacity() {

        double[][] newAdj = new double[this.weights.length + DEFAULT_CAPACITY][this.weights.length + DEFAULT_CAPACITY];

        for (int i = 0; i < newAdj.length; i++) {
            for (int j = 0; j < newAdj.length; j++) {
                newAdj[i][j] = -1;
            }
        }

        for (int i = 0; i < this.numVertices; i++) {
            for (int j = 0; j < this.numVertices; j++) {
                newAdj[i][j] = this.weights[i][j];
            }
        }
        this.weights = newAdj;
    }

    /**
     * Overload addVertex of AdjMatrixDiGraph.Adds a vertex to the graph,
     * expanding the capacity of the graph if necessary. It also associates an
     * object with the vertex.
     *
     * @param vertex the vertex to add to the graph
     */
    @Override
    public void addVertex(T vertex) throws NullElementValueException, RepeatedElementException {
        if (this.weights.length == this.numVertices) {
            this.expandCapacity();
        }
        super.addVertex(vertex);

    }

    /**
     * Overload removeVertex of AdjMatrixDiGraph.Remove the vertex from the graph.
     *
     * @param vertex
     * @throws ElementNotFoundException
     * @throws NullElementValueException
     */
    @Override
    public void removeVertex(T vertex) throws ElementNotFoundException, NullElementValueException {
        int start = getIndex(vertex);
        super.removeVertex(vertex);
        int endIndex = this.numVertices + 1;

        for (int j = start; j < endIndex - 1; j++) {
            for (int i = 0; i < endIndex; i++) {
                this.weights[i][j] = this.weights[i][j + 1];

            }
        }

        for (int j = start; j < endIndex - 1; j++) {
            for (int i = 0; i < endIndex; i++) {
                this.weights[j][i] = this.weights[j + 1][i];
            }
        }
    }

    /**
     * Overload removeEdge of AdjMatrixDiGraph.
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
        int pos1 = getIndex(vertex1);
        int pos2 = getIndex(vertex2);

        this.weights[pos1][pos2] = -1;
    }

    /**
     * Find the path with minimum cost between two vertex.
     *
     * @param vertex1
     * @param vertex2
     * @return an iterator for the minimum cost path.
     * @throws NullElementValueException
     * @throws ElementNotFoundException
     * @throws NoPathAvailableException
     */
    @Override
    public Iterator<T> shortestPathWeight(T vertex1, T vertex2) throws NullElementValueException, ElementNotFoundException, NoPathAvailableException {
        int startIndex = getIndex(vertex1);
        int targetIndex = getIndex(vertex2);
        if (vertex1.equals(vertex2)) {
            UnorderedArrayList<T> list = new UnorderedArrayList<>();
            list.addToFront(vertex2);
            return list.iterator();
        }
        //lista das distâncias de cada vértice à origem
        WeightedNode[] cost = new WeightedNode[this.numVertices];
        for (int i = 0; i < this.numVertices; i++) {
            WeightedNode current = new WeightedNode(i, Double.MAX_VALUE);
            cost[i] = current;
        }

        boolean[] visited = new boolean[numVertices];

        //lista com o predecessor de cada vértice
        int[] predecessor = new int[this.numVertices];
        cost[startIndex].setCost(0);
        predecessor[startIndex] = -1;

        //Selecionar vértice vizinhos
        ArrayHeap<WeightedNode> minNode = new ArrayHeap<>();

        Integer currentNode = 0;
        try {
            minNode.addElement(cost[startIndex]);
        } catch (InvalidOperationException | ClassCastException ex) {
        }

        //Enquanto existirem nodes a serem visitados
        while (!minNode.isEmpty()) {
            try {
                //Identificar o node com menor custo
                currentNode = minNode.removeMin().getIndex();
            } catch (InvalidOperationException ex) {
            }
            for (int i = 0; i < this.numVertices; i++) {
                //Identificar vizinhos que não tenham sido visitados 
                if (this.adjMatrix[currentNode][i] && !visited[i]
                        && cost[currentNode].getCost() + this.weights[currentNode][i] < cost[i].getCost()) {
                    cost[i].setCost(cost[currentNode].getCost() + this.weights[currentNode][i]);
                    predecessor[i] = currentNode;
                    try {
                        minNode.addElement(cost[i]);
                    } catch (InvalidOperationException | ClassCastException ex) {
                    }
                }
            }
            visited[currentNode] = true;
        }

        if (cost[targetIndex].getCost() == Double.MAX_VALUE) {
            throw new NoPathAvailableException("No path available between two vertex");
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
     * Representation of the graph.
     *
     * @return the graph by a string.
     */
    @Override
    public String toString() {
        String s = "\n";
        for (int i = 0; i < this.numVertices; i++) {
            for (int j = 0; j < this.numVertices; j++) {
                s += "\t" + this.weights[i][j] + "   ";
            }
            s += "\n \n";
        }
        return s;
    }

    /**
     * Cost between two vertex.
     *
     * @param vertex1
     * @param vertex2
     * @return
     * @throws NullElementValueException
     * @throws ElementNotFoundException
     */
    public double getEdgeCost(T vertex1, T vertex2) throws NullElementValueException, ElementNotFoundException {
        int startIndex = getIndex(vertex1);
        int endIndex = getIndex(vertex2);
        return this.weights[startIndex][endIndex];
    }
    
    /**
     * Update the cost of an edge, if exist, between two valid vertex.
     * @param vertex1 
     * @param vertex2 
     */
    public void setEdgeCost(T vertex1,T vertex2,double cost) throws NullElementValueException, ElementNotFoundException, InvalidOperationException{
        int posOrigin=this.getIndex(vertex1);
        int posDestiny=this.getIndex(vertex2);
        if(cost<0){
            throw new InvalidOperationException("The cost must be higher than 0");
        }
        if(this.isNeighbor(vertex1, vertex2)){
            this.weights[posOrigin][posDestiny]=cost;
        }
        else{
            throw new InvalidOperationException("There is no conection between these two vertex.");
        }
        
    }

    /**
     * Return the minimum cost between two vertex.
     *
     * @param vertex1
     * @param vertex2
     * @return the cost value.
     * @throws NullElementValueException
     * @throws ElementNotFoundException
     * @throws NoPathAvailableException
     */
    @Override
    public double shortestPathWeightCost(T vertex1, T vertex2) throws NullElementValueException, ElementNotFoundException, NoPathAvailableException {
        int startIndex = getIndex(vertex1);
        int targetIndex = getIndex(vertex2);
        if (vertex1.equals(vertex2)) {
            return 0;
        }
        //lista das distâncias de cada vértice à origem
        WeightedNode[] cost = new WeightedNode[this.numVertices];
        for (int i = 0; i < this.numVertices; i++) {
            WeightedNode current = new WeightedNode(i, Double.MAX_VALUE);
            cost[i] = current;
        }

        boolean[] visited = new boolean[numVertices];

        //lista com o predecessor de cada vértice
        int[] predecessor = new int[this.numVertices];
        cost[startIndex].setCost(0);
        predecessor[startIndex] = -1;

        //Selecionar vértice vizinhos
        ArrayHeap<WeightedNode> minNode = new ArrayHeap<>();

        Integer currentNode = 0;
        try {
            minNode.addElement(cost[startIndex]);
        } catch (InvalidOperationException | ClassCastException ex) {
        }

        //Enquanto existirem nodes a serem visitados
        while (!minNode.isEmpty()) {
            try {
                //Identificar o node com menor custo
                currentNode = minNode.removeMin().getIndex();
            } catch (InvalidOperationException ex) {
            }
            for (int i = 0; i < this.numVertices; i++) {
                //Identificar vizinhos com 
                if (this.adjMatrix[currentNode][i] && !visited[i]
                        && cost[currentNode].getCost() + this.weights[currentNode][i] < cost[i].getCost()) {
                    cost[i].setCost(cost[currentNode].getCost() + this.weights[currentNode][i]);
                    predecessor[i] = currentNode;
                    try {
                        minNode.addElement(cost[i]);
                    } catch (InvalidOperationException | ClassCastException ex) {
                    }
                }
            }
            visited[currentNode] = true;
        }

        if (cost[targetIndex].getCost() == Double.MAX_VALUE) {
            throw new NoPathAvailableException("No path available between two vertex");
        }

        return cost[targetIndex].getCost();
    }
}
