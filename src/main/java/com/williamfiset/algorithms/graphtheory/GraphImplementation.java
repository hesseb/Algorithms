package com.williamfiset.algorithms.graphtheory;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import java.util.List;

public class GraphImplementation {
    //adjacency list to represent graph
    private List<List<Integer>> adjacencyList = new ArrayList<>();
    //number of edges present in graph
    private int numEdges=0;
    //the maximum amount of vertices that can be present in the graph
    private int numVertices;
    private String illegalArgumentExceptionMessage = "length of edge pairings not matching up";
    private String operationNotSupportedExceptionMessage = "cannot add directed edge to undirected graph";
    private boolean directed = true;

    private boolean[] visited;

    public void initAdjacencyList(){
        //adds one extra element in case element 0 is unused
        for(int i=0; i<=numVertices; i++){
            adjacencyList.add(new ArrayList<Integer>());
        }
    }

    public List<List<Integer>> getAdjacencyList(){
        return adjacencyList;
    }

    public List<Integer> getNeighbors(int vertex){
        return adjacencyList.get(vertex);
    }

    public void clear(){
        for(int i=0; i<=numVertices; i++){
            adjacencyList.get(i).clear();
            visited[i] = false;
        }
    }

    public String toString(){
        StringBuilder sb = new StringBuilder("");
        for(int i=0; i<numVertices; i++){
            if(adjacencyList.get(i).isEmpty()){
               continue;
            }
            sb.append(i+":\n");

            int last = adjacencyList.get(i).size()-1;
            for(int j=0; j<last; j++){
                sb.append(i+"->"+adjacencyList.get(i).get(j)+", ");
            }
            sb.append(i+"->"+adjacencyList.get(i).get(last));

            sb.append("\n");
        }
        return sb.toString();
    }

    public GraphImplementation(int numVertices){
        this.numVertices = numVertices;

        //adds one extra element in case element 0 is unused
        visited = new boolean[numVertices+1];
        initAdjacencyList();
    }

    public GraphImplementation(int[] a, int[] b, boolean directed, int numVertices)throws IllegalArgumentException{
        if(a.length != b.length){
            throw new IllegalArgumentException(illegalArgumentExceptionMessage);
        }
        this.numVertices = numVertices;
        this.directed = directed;

        //adds one extra element in case element 0 is unused
        visited = new boolean[numVertices+1];

        initAdjacencyList();

        for(int i=0; i<a.length; i++){
            if(directed){
                try{
                    addDirectedEdge(a[i], b[i]);
                }catch(OperationNotSupportedException exception){
                    //This code is unreachable as directed would not be false if this clause is entered
                    exception.printStackTrace();
                }
            } else{
                try{
                    addUndirectedEdge(a[i], b[i]);
                }catch(OperationNotSupportedException exception){
                    //This code is unreachable due to the temporary change of directed flag value when adding directed edge
                    exception.printStackTrace();
                }
            }
        }

    }

    public String getIllegalArgumentExceptionMessage(){
        return illegalArgumentExceptionMessage;
    }

    public String getOperationNotSupportedExceptionMessage(){
        return operationNotSupportedExceptionMessage;
    }

    public void addDirectedEdge(int a, int b)throws OperationNotSupportedException{
        if(!directed){
            throw new OperationNotSupportedException(operationNotSupportedExceptionMessage);
        }
        adjacencyList.get(a).add(b);
        numEdges++;
    }

    public void addUndirectedEdge(int a, int b)throws OperationNotSupportedException{
        //temporarily set directed flag to true to allow adding edge to graph
        directed = true;
        addDirectedEdge(a,b);
        addDirectedEdge(b,a);
        directed = false;
    }

    public void removeEdge(int a, int b){
        for(int i=0; i<adjacencyList.get(a).size(); i++){
            if(adjacencyList.get(a).get(i) == b){
                adjacencyList.get(a).remove(i);
                numEdges -= 1;
                break;
            }
        }
    }

    public void removeUndirectedEdge(int a, int b){
        removeEdge(a, b);
        removeEdge(b, a);
    }

    public boolean existsEdge(int a, int b){
        boolean exists = false;
        for(int i=0; i<adjacencyList.get(a).size(); i++){
            if(adjacencyList.get(a).get(i) == b){
                exists = true;
                break;
            }
        }
        return exists;
    }

    public int getNumEdges(){
        return directed ? numEdges : numEdges/2;
    }

    public int getNumVertices(){
        return numVertices;
    }

    public boolean isVisited(int vertex) {
        return visited[vertex];
    }

    public void setVisited(int vertex, boolean value){
        visited[vertex] = value;
    }
}
