package com.williamfiset.algorithms.graphtheory;

import com.williamfiset.algorithms.graphtheory.ColoredGraph.Node;

import java.util.Arrays;

public class GraphColoring {
    /**
     * An implementation of the greedy graph coloring algorithm.
     * @param graph to be colored
     */
    static public void greedy(ColoredGraph graph) {
        // Determine the max degree of the graph and thereby the max number of colors required
        int maxDegree = graph.getMaxDegree();
        // Array for tracking used colors, each index corresponds to a color
        boolean[] usedColors = new boolean[maxDegree];

        for (Node node : graph.getNodes()) {
            // Clear color tracker
            Arrays.fill(usedColors, false);
            for (Node neighbor : node.getNeighbors()) {
                if (neighbor.getColor() != -1) {         // -1 represents an uncolored node
                    // Mark the color as taken
                    usedColors[neighbor.getColor()] = true;
                }
            }

            int currentColor = 0;
            // Find the first free color for the node
            for (boolean colorTaken : usedColors) {
                if (colorTaken) {
                    currentColor++;
                } else {
                    break;
                }
            }

            node.setColor(currentColor);
        }
    }
}
