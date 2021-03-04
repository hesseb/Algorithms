package com.williamfiset.algorithms.graphtheory;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import java.util.List;

public class GraphImplementation {
    //adjacency list to represent graph
    private List<List<Integer>> adjacencyLists = new ArrayList<>();
    //number of edges present in graph
    private int numEdges=0;
    //the maximum amount of vertices that can be present in the graph
    private int numVertices;
    private String illegalArgumentExceptionMessage = "length of edge pairings not matching up";
    private String operationNotSupportedExceptionMessage = "cannot add directed edge to undirected graph";
    private boolean directed = true;

    private boolean[] visited;

    /**
     * Initializes the adjacency list to have empty lists for each vertex
     */
    public void initAdjacencyList(){
        //adds one extra element in case element 0 is unused
        for(int i=0; i<=numVertices; i++){
            adjacencyLists.add(new ArrayList<Integer>());
        }
    }

    /**
     * Returns the entire adjacency list
     *
     * @return adjacencyLists
     */
    public List<List<Integer>> getAdjacencyLists(){
        return adjacencyLists;
    }

    /**
     * Returns the neighbors of a specified vertex
     *
     * @param vertex
     * @return neighbors
     */
    public List<Integer> getNeighbors(int vertex){
        return adjacencyLists.get(vertex);
    }

    /**
     * Clears the graph meaning that all the adjacency lists are cleared and the all vectors are set to not visited
     */
    public void clear(){
        for(int i=0; i<=numVertices; i++){
            adjacencyLists.get(i).clear();
            visited[i] = false;
        }
    }

    /**
     * Returns a string representation of the graph in adjacency list form
     *
     * @return String
     */
    public String toString(){
        StringBuilder sb = new StringBuilder("");
        for(int i=0; i<numVertices; i++){
            if(adjacencyLists.get(i).isEmpty()){
               continue;
            }
            sb.append(i+":\n");

            int last = adjacencyLists.get(i).size()-1;
            for(int j=0; j<last; j++){
                sb.append(i+"->"+ adjacencyLists.get(i).get(j)+", ");
            }
            sb.append(i+"->"+ adjacencyLists.get(i).get(last));

            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * Constructor which does not specify edges, but instead just the (maximum) number of vertices
     *
     * @param numVertices
     */
    public GraphImplementation(int numVertices){
        this.numVertices = numVertices;

        //adds one extra element in case element 0 is unused
        visited = new boolean[numVertices+1];
        initAdjacencyList();
    }

    /**
     * Constructor which specifies edges to add to the graph, number of vertices in the graph and whether the graph
     * is directed or undirected
     *
     * Throws exception if length of vector of first element in each clause is not the same as the vector with second element in each clause
     *
     * @param a
     * @param b
     * @param directed
     * @param numVertices
     * @throws IllegalArgumentException
     */
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

    /**
     * Returns exception message for when length of vector of first element in each clause is not the same as the vector
     * with second element in each clause
     *
     * Used for checking that the exception message is the same as expected when running tests
     *
     * @return
     */
    public String getIllegalArgumentExceptionMessage(){
        return illegalArgumentExceptionMessage;
    }

    /**
     * Returns exception message for when directed edge is added to a graph that is undirected
     * and not called by the addUndirectedEdge() method specifically
     *
     * Used for checking that the exception message is the same as expected when running tests
     *
     * @return
     */
    public String getOperationNotSupportedExceptionMessage(){
        return operationNotSupportedExceptionMessage;
    }

    /**
     * Adds a directed edge to the graph
     *
     * Throws an exception if the directed edge is added to a graph that is created to be an undirected edge
     * if the method is not specifically called from the method addUndirectedEdge() in an undirected graph
     *
     * @param a
     * @param b
     * @throws OperationNotSupportedException
     */
    public void addDirectedEdge(int a, int b)throws OperationNotSupportedException{
        if(!directed){
            throw new OperationNotSupportedException(operationNotSupportedExceptionMessage);
        }
        adjacencyLists.get(a).add(b);
        numEdges++;
    }

    /**
     * Adds an undirected edge to a graph by adding two directed edges (one in each direction)
     *
     * Temporarily sets the directed flag to true to allow the operation of adding a directed edge to an
     * undirected graph and not getting an exception.
     *
     * Sets the directed flag back to false when the edges have been added.
     *
     * @param a
     * @param b
     * @throws OperationNotSupportedException
     */
    public void addUndirectedEdge(int a, int b)throws OperationNotSupportedException{
        //temporarily set directed flag to true to allow adding edge to graph
        directed = true;
        addDirectedEdge(a,b);
        addDirectedEdge(b,a);
        directed = false;
    }

    /**
     * Removes the specified (directed) edge from the graph if it exists, otherwise nothing will change
     *
     * @param a
     * @param b
     */
    public void removeEdge(int a, int b){
        for(int i = 0; i< adjacencyLists.get(a).size(); i++){
            if(adjacencyLists.get(a).get(i) == b){
                adjacencyLists.get(a).remove(i);
                numEdges -= 1;
                break;
            }
        }
    }

    /**
     * Removes the specified undirected edge from the graph by removing a directed edge in both directions between
     * the two vertices.
     *
     * @param a
     * @param b
     */
    public void removeUndirectedEdge(int a, int b){
        removeEdge(a, b);
        removeEdge(b, a);
    }

    /**
     * Checks whether the specified edge exists in the graph or not
     *
     * Useful for when checking whether an edge exists before and after having been removed/added.
     *
     * @param a
     * @param b
     * @return boolean
     */
    public boolean existsEdge(int a, int b){
        boolean exists = false;
        for(int i = 0; i< adjacencyLists.get(a).size(); i++){
            if(adjacencyLists.get(a).get(i) == b){
                exists = true;
                break;
            }
        }
        return exists;
    }

    /**
     * Returns the number of edges in the graph
     *
     * If the graph is directed, it returns the number of directed edges
     *
     * If the graph is undirected, it returns the number of directed edges divided by two.
     *
     * @return int
     */
    public int getNumEdges(){
        return directed ? numEdges : numEdges/2;
    }

    /**
     * Returns the number of vertices in the graph
     *
     * @return int
     */
    public int getNumVertices(){
        return numVertices;
    }

    /**
     * Returns whether or not a specified vertex has been visited or not
     *
     * @param vertex
     * @return
     */
    public boolean isVisited(int vertex) {
        return visited[vertex];
    }

    /**
     * Sets the specified vertex to be either visited or not visited depending on what value is given
     *
     * @param vertex
     * @param value
     */
    public void setVisited(int vertex, boolean value){
        visited[vertex] = value;
    }
}
