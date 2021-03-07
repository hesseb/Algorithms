package com.williamfiset.algorithms.graphtheory.networkflow;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class BipartiteGraph {


    private List<List<Integer>> adjacencyList = new ArrayList<>();

    private int vertexCount;
    private int numEdges = 0;
    int [] colorArr;

    public BipartiteGraph(int V) {
        this.vertexCount = V;
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
        numEdges++;
    }

    public void removeDirectedEdge(int from, int to) {
        for (int i = 0; i < adjacencyList.get(from).size(); i++) {
            if (adjacencyList.get(from).get(i) == to) {
                adjacencyList.get(from).remove(i);
                break;
            }
        }
    }
    public boolean hasEdge(int x, int y) {
        if (adjacencyList.get(x).contains(y) || adjacencyList.get(y).contains(x)){
            return true;
        }
        return false;
    }

    public void removeEdge(int from, int to) {
        removeDirectedEdge(from, to);
        removeDirectedEdge(to, from);
        numEdges--;
    }

    public int getColor(int vertex) {
        return colorArr[vertex];
    }

    public List<Integer> getAdjecencyList(int vertex) {
        return adjacencyList.get(vertex);
    }

    public int getVertexCount() {
        return this.vertexCount;
    }


    public int getNumEdges(){
        return this.numEdges;
    }


    public boolean isBipartite() {
        colorArr = new int[vertexCount];
        for (int i = 0; i < colorArr.length ; i++) {
            colorArr[i] = -1;
        }


        LinkedList<Integer> queue = new LinkedList<>();
        for (int src = 0; src < vertexCount; src++) {
            if (colorArr[src] == -1) {
                colorArr[src] = 0;
            }

        queue.add(src);

        while(!queue.isEmpty()) {

            int u = queue.poll();

            if(hasEdge(u,u)) {
               return false;
            }

            for (int i = 0; i < adjacencyList.get(u).size(); i++) {

                int dest = adjacencyList.get(u).get(i);

                if (colorArr[dest] == -1) {
                    if (colorArr[u] == 0) {
                        colorArr[dest] = 1;
                    }else if (colorArr[u] == 1) {
                        colorArr[dest] = 0;
                    }
                    queue.add(dest);
                }else if (colorArr[u] == colorArr[dest]) {
                    return false;
                }
            }
        }
        }
        return true;
    }

}
