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
}
