package com.williamfiset.algorithms.graphtheory.networkflow;

import org.junit.Test;



public class KoenigTest {

    @Test
    public void koenigTest() {
        Koenig k;
        BipartiteGraph biGraph = new BipartiteGraph(4);
        biGraph.addEdge(0,2);
        biGraph.addEdge(0,3);
        biGraph.addEdge(1,2);
        biGraph.addEdge(1,3);

        assert (biGraph.isBipartite());
        k = new Koenig(biGraph);

        assert k.checkSolution(biGraph);

        Koenig k2;
        BipartiteGraph nonBiGraph = new BipartiteGraph(4);
        nonBiGraph.addEdge(0,1);
        nonBiGraph.addEdge(0,2);
        nonBiGraph.addEdge(0,1);
        nonBiGraph.addEdge(1,2);

        assert (!nonBiGraph.isBipartite());
        k2 = new Koenig(nonBiGraph);

        assert (!k2.checkSolution(nonBiGraph));
    }

    @Test
    public void testLargeGraph() {
        int size = 10000;
        BipartiteGraph graph = new BipartiteGraph(size);
        for (int i = 0; i < size-1; i++) {
            graph.addEdge(i, (i+1));
        }
        assert (graph.isBipartite());
        Koenig koenig1 = new Koenig(graph);
        assert (koenig1.checkSolution(graph));

        graph.addEdge(0, size-1);
        graph.addEdge(0, 2);
        assert (!graph.isBipartite());
        Koenig koenig2 = new Koenig(graph);
        assert (!koenig2.checkSolution(graph));
    }

    @Test
    public void testEmptyGraph() {
        BipartiteGraph graph = new BipartiteGraph(0);
        assert (graph.isBipartite());
        Koenig koenig1 = new Koenig(graph);
        assert (koenig1.checkSolution(graph));
    }
}
