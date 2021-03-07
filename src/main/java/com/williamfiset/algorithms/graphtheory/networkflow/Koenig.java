package com.williamfiset.algorithms.graphtheory.networkflow;

import java.util.LinkedList;

public class Koenig {

    private static final int UNMATCHED = -1;
    private int V; // number of vertex
    private boolean marked[]; // marked[v] = true if v is reachable via alternating path
    private int cardinality = 0; // size of the match
    private int[] edgeTo; // edgeTo[v] last edge on alternating path to v
    private int[] match; // match[v] = w if v-w is a edge in the current matching
    private BipartiteGraph graph;
    private boolean[] inMinVertexCover;


    public Koenig(BipartiteGraph G) {
        this.graph = G;
        this.V = graph.getVertexCount();

        match = new int[V];
        for (int i = 0; i < match.length; i++) {
            match[i] = UNMATCHED;
        }

        while (hasAlternatingPath(graph)) {
            int t = -1;
            for (int i = 0; i < V; i++) {
                if (!isMatched(i) && edgeTo[i] != -1) {
                    t = i;
                    break;
                }
            }

            for (int i = t; i != -1 ; i = edgeTo[edgeTo[i]]) {
                int w = edgeTo[i];
                match[w] = i;
                match[i] = w;
            }
            cardinality++;
        }
        inMinVertexCover = new boolean[V];
        for (int i = 0; i < V; i++) {
            if(graph.getColor(i) == 0 && !marked[i]) {
                inMinVertexCover[i] = true;
            }
            if (graph.getColor(i) == 1 && marked[i]) {
                inMinVertexCover[i] = true;
            }
        }
    }


    public boolean hasAlternatingPath(BipartiteGraph G) {
        marked = new boolean[V];

        edgeTo = new int[V];
        for (int i = 0; i < edgeTo.length; i++) {
            edgeTo[i] = -1;
        }

        LinkedList<Integer> queue = new LinkedList<Integer>();
        for (int i = 0; i < V; i++) {
            if (G.getColor(i) == 0 && !isMatched(i)) {
                //System.out.println(i);
                queue.add(i);
                marked[i] = true;
            }
        }

        while (!queue.isEmpty()) {
            int vertex = queue.poll();
            for (int w: G.getAdjecencyList(vertex)) {
                if (isResidualGraphEdge(vertex,w) && !marked[w]) {
                    edgeTo[w] = vertex;
                    marked[w] = true;
                    if (!isMatched(w)) {
                        return true;
                    }
                    queue.add(w);
                }
            }
        }
        return false;
    }

    public boolean isResidualGraphEdge(int x, int y) {
        if (match[x] != y && graph.getColor(x) == 0) {
            return true;
        }
        if (match[x] == y && graph.getColor(x) == 1) {
            return true;
        }
        return false;
    }

    public boolean isMatched(int vertex) {
        return match[vertex] != UNMATCHED;
    }

    public boolean checkSolution(BipartiteGraph graph) {
        for (int i = 0; i < V; i++) {
            if (match[i] == -1) continue;
            if (match[match[i]] != i) return false;
        }

        int matchedVertecies = 0;
        for (int i = 0; i < V; i++) {
            if (match[i] != -1) {
                matchedVertecies++;
            }
        }
        if (matchedVertecies != 2*cardinality) {
            return false;
        }
        int sizeOfMinVertexCover = 0;
        for (int i = 0; i < V; i++) {
            if (inMinVertexCover[i]) {
                sizeOfMinVertexCover++;
            }
        }
        if (cardinality != sizeOfMinVertexCover) {
            return false;
        }
        boolean[] isMatched = new boolean[V];
        for (int i = 0; i < V; i++) {
            int w = match[i];
            if (w == -1) {
                continue;
            }
            if (i == w) {
                return false;
            }
            if (i > w) {
                continue;
            }
            if (isMatched[i] || isMatched[w]) return false;
            isMatched[i] = true;
            isMatched[w] = true;
        }

        for (int i = 0; i < V; i++) {
            if (match[i] == -1) continue;
            boolean isEdge = false;
            for (int j : graph.getAdjecencyList(i)) {
                if (match[i] == j) isEdge = true;
            }
            if (!isEdge) {
                return false;
            }
        }

        for (int i = 0; i < V; i++) {
            for (int w: graph.getAdjecencyList(i)) {
                if (!inMinVertexCover[i] && !inMinVertexCover[w]) {
                    return false;
                }
            }
        }
        return true;
    }



    public static void main(String[] args) {
        BipartiteGraph graph = new BipartiteGraph(4);
        graph.addEdge(0,1);
        graph.addEdge(0,3);
        graph.addEdge(0,2);
        graph.addEdge(1,2);
        graph.addEdge(1,3);

        if (graph.isBipartite()) {
            Koenig k = new Koenig(graph);
            for (int i = 0; i <k.inMinVertexCover.length ; i++) {
                //System.out.println(k.inMinVertexCover[i]);
            }
            System.out.println("Bipartite: " + graph.isBipartite());
            System.out.println(k.checkSolution(graph));
        } else {
            System.out.println("Bipartite: " + graph.isBipartite());
        }

    }

}
