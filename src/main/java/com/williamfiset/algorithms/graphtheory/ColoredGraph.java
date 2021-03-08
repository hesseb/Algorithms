package com.williamfiset.algorithms.graphtheory;

import java.util.ArrayList;

/**
 * A representation of a colored graph implemented using an ArrayList for the nodes and an ArrayList inside each node
 * for the neighbors. The colors are also stored within each node and is represented using integers,
 * with -1 representing the un-colored state.
 */
public class ColoredGraph {
    ArrayList<Node> nodes = new ArrayList<>();

    /**
     * Adds an undirected edge between two nodes.
     *
     * @param n1 node 1
     * @param n2 node 2
     */
    public void addEdge(Node n1, Node n2) {
        n1.addNeighbor(n2);
        n2.addNeighbor(n1);
    }

    /**
     * Gets the number of different colors in the graph.
     *
     * @return count of unique colors
     */
    public int getNrColors() {
        boolean[] colorCounter = new boolean[getMaxDegree()];
        for (Node node : this.getNodes()) {
            colorCounter[node.getColor()] = true;
        }
        int nrColors = 0;
        for (boolean colorUsed : colorCounter) {
            if (colorUsed) {
                nrColors++;
            }
        }
        return nrColors;
    }

    /**
     * Adds a node to the graph.
     */
    public void addNode() {
        nodes.add(new Node());
    }

    /**
     * Gets the node from the given index from the graph.
     *
     * @param index of node in the graph
     * @return node
     */
    public Node getNode(int index) throws IllegalArgumentException {
        if (index < 0) {
            throw new IllegalArgumentException();
        } else {
            return nodes.get(index);
        }
    }

    /**
     * Gets all the nodes in the graph.
     *
     * @return nodes
     */
    public ArrayList<Node> getNodes() {
        return nodes;
    }

    /**
     * Determines the maximum degree of a node in the graph.
     *
     * @return maxDegree
     */
    public int getMaxDegree() {
        int maxDegree = 0;
        for (Node node : this.getNodes()) {
            if (maxDegree < node.getNrOfNeighbors()) {
                maxDegree = node.getNrOfNeighbors();
            }
        }
        return maxDegree;
    }

    /**
     * A representation of a colored node. The colors are represented using integers,
     * with -1 representing the uncolored state.
     */
    public class Node {
        ArrayList<Node> neighbors = new ArrayList<>();
        int color = -1;

        /**
         * Takes a node and adds it as a neighbor of the calling node.
         *
         * @param n node to be added as neighbor
         */
        private void addNeighbor(Node n) {
            neighbors.add(n);
        }

        /**
         * Sets the color of a node. Colors are represented using integers >=0.
         *
         * @param color number representing the color
         */
        public void setColor(int color) throws IllegalArgumentException {
            if (color < 0) {
                throw new IllegalArgumentException();
            } else {
                this.color = color;
            }
        }

        /**
         * Gets the number of neighbors of the calling node.
         *
         * @return nrOfNeighbors
         */
        public int getNrOfNeighbors() {
            return neighbors.size();
        }

        /**
         * Gets all the neighbors of the calling node.
         *
         * @return neighbors
         */
        public ArrayList<Node> getNeighbors() {
            return neighbors;
        }

        /**
         * Gets the number representing the color of the calling node.
         *
         * @return color
         */
        public int getColor() {
            return color;
        }
    }
}
