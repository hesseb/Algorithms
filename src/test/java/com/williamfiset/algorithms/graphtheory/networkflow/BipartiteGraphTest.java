package com.williamfiset.algorithms.graphtheory.networkflow;

import org.junit.Test;

public class BipartiteGraphTest {

    @Test
    public void testConstructor() {
        int numV = 2;
        BipartiteGraph graph = new BipartiteGraph(numV);
        assert(graph.getVertexCount() == 2 && graph.getNumEdges() == 0);
    }

    @Test
    public void testAddEdge() {
        BipartiteGraph graph = new BipartiteGraph(2);
        graph.addEdge(0,1);
        assert (graph.hasEdge(0,1) && graph.getNumEdges() == 1);
    }

    @Test
    public void testIsBipartite(){
        BipartiteGraph graph = new BipartiteGraph(4);
        // simple bipartite graph
        graph.addEdge(0,2);
        graph.addEdge(0,3);
        graph.addEdge(1,2);
        graph.addEdge(1,3);
        assert (graph.isBipartite());
        // add another edge that make the graph not bipartite
        graph.addEdge(0,1);

        assert (!graph.isBipartite());
    }

    @Test
    public void testGetVertexCount() {
        BipartiteGraph g = new BipartiteGraph(1);
        assert (g.getVertexCount() == 1);
    }

    @Test
    public void testGetColor() {
        BipartiteGraph g = new BipartiteGraph(4);
        g.addEdge(0,2);
        g.addEdge(0,3);
        g.addEdge(1,2);
        g.addEdge(1,3);
        g.isBipartite();
        assert (g.getColor(0) == 0);
    }

    @Test
    public void testRemoveEdge() {
        BipartiteGraph g = new BipartiteGraph(4);
        g.addEdge(0,2);
        g.addEdge(0,3);
        g.addEdge(1,2);
        g.addEdge(1,3);
        g.removeEdge(0,2);
        assert (!g.hasEdge(0,2) && g.getNumEdges() == 3);
    }

}
