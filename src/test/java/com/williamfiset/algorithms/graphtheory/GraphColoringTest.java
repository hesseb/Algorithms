package com.williamfiset.algorithms.graphtheory;

import org.junit.*;

public class GraphColoringTest {

    @Test
    public void testGreedy() {
        ColoredGraph graph = new ColoredGraph();
        graph.addNode();
        graph.addNode();
        graph.addNode();
        graph.addNode();
        graph.addNode();
        graph.addNode();
        graph.addEdge(graph.getNode(0), graph.getNode(1));
        graph.addEdge(graph.getNode(1), graph.getNode(2));
        graph.addEdge(graph.getNode(2), graph.getNode(3));
        graph.addEdge(graph.getNode(0), graph.getNode(4));
        graph.addEdge(graph.getNode(1), graph.getNode(4));
        graph.addEdge(graph.getNode(2), graph.getNode(4));
        graph.addEdge(graph.getNode(3), graph.getNode(4));
        graph.addEdge(graph.getNode(5), graph.getNode(1));
        graph.addEdge(graph.getNode(5), graph.getNode(0));

        GraphColoring.greedy(graph);

        assert(graph.getNode(0).getColor() == 0);
        assert(graph.getNode(1).getColor() == 1);
        assert(graph.getNode(2).getColor() == 0);
        assert(graph.getNode(3).getColor() == 1);
        assert(graph.getNode(4).getColor() == 2);
        assert(graph.getNode(4).getColor() == 2);
        assert(graph.getNode(5).getColor() == 2);
        assert(graph.getNrColors() == 3);
    }

    @Test
    public void testEmptyGreedy() {
        ColoredGraph graph = new ColoredGraph();
        assert (graph.getNrColors() == 0);
    }

    @Test
    public void testCompleteGraphGreedy() {
        ColoredGraph graph = new ColoredGraph();
        int size = 1000;
        for (int i = 0; i < size; i++) {
            graph.addNode();
        }
        for (int i = 0; i < size; i++) {
            for (int j = i; j < size; j++) {
                graph.addEdge(graph.getNode(i), graph.getNode(j));
                graph.addEdge(graph.getNode(j), graph.getNode(i));
            }
        }

        GraphColoring.greedy(graph);
        assert(graph.getNrColors() == size);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testNegativeColor() {
        ColoredGraph graph = new ColoredGraph();
        graph.addNode();
        graph.getNode(0).setColor(-1);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testNegativeIndex() {
        ColoredGraph graph = new ColoredGraph();
        graph.getNode(-1);
    }
}
