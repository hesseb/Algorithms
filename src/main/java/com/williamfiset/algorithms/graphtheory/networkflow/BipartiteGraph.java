package com.williamfiset.algorithms.graphtheory.networkflow;
import org.graalvm.compiler.graph.Graph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BipartiteGraph {


    private List<List<Integer>> adjacencyList = new ArrayList<>();

    private int vertexCount;
    private int numEdges = 0;
    private int leftPartition;
    private int rightPartition;
    private boolean[] marked;
    private boolean isBipartite;

    public BipartiteGraph(int V1, int V2, int E) {
        this.vertexCount = V1 + V2;
        this.leftPartition = V1;
        this.rightPartition = V2;
        this.numEdges = E;
        initAdjacencyList();

    }

    private void initAdjacencyList() {
        for(int i=0; i<vertexCount; i++){
            adjacencyList.add(new ArrayList<Integer>());
        }
    }

    public void addEdge(int from, int to) {
        adjacencyList.get(from).add(to);
        adjacencyList.get(to).add(from);
    }

    public void removeDirectedEdge(int from, int to) {
        for (int i = 0; i < adjacencyList.get(from).size(); i++) {
            if (adjacencyList.get(from).get(i) == to) {
                adjacencyList.get(from).remove(i);
                numEdges--;
                break;
            }
        }
    }

    public void removeEdge(int from, int to) {
        removeDirectedEdge(from, to);
        removeDirectedEdge(to, from);
    }


    private void isBipartite() {
        int src = 0;
        int [] colorArr = new int[vertexCount];
        for (int i = 0; i < colorArr.length ; i++) {
            colorArr[i] = -1;
        }
        colorArr[src] = 1;

        LinkedList<Integer> queue = new LinkedList<>();
        queue.add(src);
        while(!queue.isEmpty()) {

            int u = queue.poll();

            if(adjacencyList.get(u).contains(u)) {
                isBipartite = false;
            }

            for (int i = 0; i < vertexCount; i++) {

                if(adjacencyList.get(u).get(i) > 0 && colorArr[i] == -1) {
                    colorArr[i] = 1 - colorArr[u];
                    queue.add(i);
                }else if(adjacencyList.get(u).get(i) > 0 && colorArr[i] == colorArr[u]) {
                    isBipartite = false;
                }
            }
        }
        isBipartite = true;
    }


    private boolean hasAugmentedPath(Graph biGraph) {
        marked = new boolean[vertexCount];






        return false;
    }



    public static void main(String[] args) {

    }

}
