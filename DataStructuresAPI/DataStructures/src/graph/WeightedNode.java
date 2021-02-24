package graph;

/**
 *Represents a class that stores a node with weight and the index from the array of vertex.
 * Used for identify the origin vertex that have a specific edge cost. Help to build a minimum spanning tree 
 * and to find the shortest path weight.
 */
public class WeightedNode implements Comparable<WeightedNode> {
    int index;
    double cost;

    /**
     * ´Construtor for a weighted node.
     * @param index
     * @param cost 
     */
    public WeightedNode(int index, double cost) {
        this.index = index;
        this.cost = cost;
    }

    /**
     * Getter for the index from the origin vertex related to the edge cost.
     * @return 
     */
    public int getIndex() {
        return index;
    }

    /**
     * Getter for a edge cost.
     * @return 
     */
    public double getCost() {
        return cost;
    }

    /**
     * Setter for an index.
     * @param index 
     */
    public void setIndex(int index) {
        this.index = index;
    }

    /**
     * Setter for the cost.
     * @param cost 
     */
    public void setCost(double cost) {
        this.cost = cost;
    }

    /**
     * Compare the cost between two nodes.
     * @param o
     * @return 
     */
    @Override
    public int compareTo(WeightedNode o) {
        if (this.getCost() < o.getCost()) {
            return -1;
        } else if (this.getCost() == o.getCost()) {
            return 0;
        } else {
            return 1;
        }
    }

}
