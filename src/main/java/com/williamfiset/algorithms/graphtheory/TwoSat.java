package com.williamfiset.algorithms.graphtheory;

import java.util.*;

import javax.naming.OperationNotSupportedException;

public class TwoSat {
    private boolean isSatisfiable = false;
    private boolean solved = false;

    private int[] a;
    private int[] b;


    private GraphImplementation graph;
    private GraphImplementation inverseGraph;

    private static int numVertices;
    private static int numClauses;

    private String clauseExceptionMessage = "number of clauses do not match up";
    private String vertexAmountNegativeExceptionMessage = "number of variables cannot be negative";
    private String illegalStateExceptionMessage = "clauses have not yet been specified";

    private int MAX;


    private int[] scc;
    private int counter = 1;

    Stack<Integer> stack = new Stack<Integer>();


    /**
     * Initializes (maximum) number of vertices and the vector specifying which SCC a vertex belongs to
     *
     * @param numVertices
     * @throws IllegalArgumentException
     */
    public void initializeVertices(int numVertices)throws IllegalArgumentException{
        if(numVertices < 0){
            throw new IllegalArgumentException(vertexAmountNegativeExceptionMessage);
        }

        this.numVertices = numVertices;

        MAX = numVertices*2+1;

        graph = new GraphImplementation(MAX);
        inverseGraph = new GraphImplementation(MAX);

        scc = new int[MAX];
    }

    /**
     * Constructor which only specifies the number of vertices without specifying the clauses
     *
     * @param numVertices
     * @throws IllegalArgumentException
     */
    public TwoSat(int numVertices)throws IllegalArgumentException{
        initializeVertices(numVertices);
    }

    /**
     * Constructor specifying both the number of vertices and the exact clauses of the instance
     *
     * @param a
     * @param b
     * @param numVertices
     * @throws IllegalArgumentException
     */
    public TwoSat(int[] a, int[] b, int numVertices) throws IllegalArgumentException{
        initializeVertices(numVertices);
        setClauses(a,b);
    }

    /**
     * Clears the instance meaning that the adjacency lists and visited-vectors of the graphs are cleared
     * and the scc vector is also reset. If the instance was considered solved that will no longer be the case.
     */
    public void clear(){
        graph.clear();
        inverseGraph.clear();

        for(int i=0; i<MAX; i++){
            scc[i] = 0;
        }

        solved = false;
        isSatisfiable = false;
    }

    /**
     * Sets the clauses to the specified clauses.
     *
     * If clauses have already been set for the instance, then clear() needs to be called before calling this function.
     *
     * If instance has a number of vertices different to the number of vertices in the specified clauses,
     * then initializeVertices() needs to be called with the new number of vertices before (or immediately after)
     * calling this method.
     *
     * @param a
     * @param b
     * @throws IllegalArgumentException
     */
    public void setClauses(int[] a, int[] b)throws IllegalArgumentException{
        if(a.length!=b.length){
            throw new IllegalArgumentException(clauseExceptionMessage);
        }
        this.a = a;
        this.b = b;
        numClauses = a.length;
    }

    /**
     * Returns exception message for when clause lengths do not match up
     *
     * Used for checking that the exception message is the same as expected when running tests
     *
     * @return String
     */
    public String getClauseExceptionMessage(){
        return clauseExceptionMessage;
    }

    /**
     * Returns exception message for when vertex amount given is negative
     *
     * Used for checking that the exception message is the same as expected when running tests
     *
     * @return String
     */
    public String getVertexAmountNegativeExceptionMessage(){
        return vertexAmountNegativeExceptionMessage;
    }

    /**
     * Returns exception message for when solve is called without specifying clauses first
     *
     * Used for checking that the exception message is the same as expected when running tests
     *
     * @return String
     */
    public String getIllegalStateExceptionMessage(){
        return illegalStateExceptionMessage;
    }


    /**
     * Performs dfs on the original graph, constructed by adding (not A) -> B and (not B) -> A for clause (A or B).
     *
     * @param u
     */
    public void dfsFirst(int u){
        if(graph.isVisited(u)) {
            return;
        }

        graph.setVisited(u, true);
        for(int v = 0; v< graph.getNeighbors(u).size(); v++){
            dfsFirst(graph.getNeighbors(u).get(v));
        }

        stack.push(u);
    }

    /**
     * Performs dfs on a graph which has all the edges flipped compared to the original graph
     *
     * Uses this to specify which Strongly Connected Component the vertex belongs to
     * @param u
     */
    public void dfsSecond(int u){
        if(inverseGraph.isVisited(u)){
            return;
        }

        inverseGraph.setVisited(u, true);
        for(int v = 0; v< inverseGraph.getNeighbors(u).size(); v++){
            dfsSecond((inverseGraph.getNeighbors(u).get(v)));
        }

        scc[u] = counter;
    }


    /**
     * Returns whether or not the instance has any solutions
     *
     * Throws an exception if no clauses are specified
     *
     * @return
     * @throws IllegalStateException
     */
    public boolean isTwoSatisfiable()throws IllegalStateException{
        if(a == null || b == null || a.length == 0 || b.length == 0){
            throw new IllegalStateException(illegalStateExceptionMessage);
        }

        if(!solved){
            solve();
        }
        return isSatisfiable;
    }

    /**
     * Solves the instance by:
     *
     * -Constructing the graph by adding (not A) -> B and (not B) -> A for any clause (A and B).
     *
     * -Checking if there is a path from any vertex A to (not A) and from (not A) to A. If so,
     * then the instance has no solution, otherwise solutions do exist.
     */
    private void solve(){

        //adding the edges to the graph representing the conjunctive formula

        //x is mapped to x
        //negation of x is mapped to n+x (=n-(-x))
        for(int i=0; i<numClauses; i++){

            try{
                if(a[i] > 0 && b[i] > 0){
                    //add edge from negated a to b
                    graph.addDirectedEdge(a[i]+ numVertices, b[i]);
                    //add flipped edge to inverse graph
                    inverseGraph.addDirectedEdge(b[i], a[i]+ numVertices);

                    //add edge from negated b to a
                    graph.addDirectedEdge(b[i]+ numVertices, a[i]);
                    //add flipped edge to inverse graph
                    inverseGraph.addDirectedEdge(a[i], b[i]+ numVertices);
                } else if(a[i] > 0 && b[i] < 0){
                    //add edge from negated a to b
                    graph.addDirectedEdge(a[i]+ numVertices, numVertices -b[i]);
                    //add flipped edge to inverse graph
                    inverseGraph.addDirectedEdge(numVertices -b[i], a[i]+ numVertices);

                    //add edge from negated b to a
                    graph.addDirectedEdge(-b[i], a[i]);
                    //add flipped edge to inverse graph
                    inverseGraph.addDirectedEdge(a[i], -b[i]);
                } else if(a[i] < 0 && b[i] > 0){
                    //add edge from negated a to b
                    graph.addDirectedEdge(-a[i], b[i]);
                    //add flipped edge to inverse graph
                    inverseGraph.addDirectedEdge(b[i], -a[i]);

                    //add edge from negated b to a
                    graph.addDirectedEdge(b[i]+ numVertices, numVertices -a[i]);
                    //add flipped edge to inverse graph
                    inverseGraph.addDirectedEdge(numVertices -a[i], b[i]+ numVertices);
                } else{
                    //add edge from negated a to b
                    graph.addDirectedEdge(-a[i], numVertices -b[i]);
                    //add flipped edge to inverse graph
                    inverseGraph.addDirectedEdge(numVertices -b[i],-a[i]);

                    //add edge from negated b to a
                    graph.addDirectedEdge(-b[i], numVertices -a[i]);
                    //add flipped edge to inverse graph
                    inverseGraph.addDirectedEdge(numVertices -a[i], -b[i]);
                }
            }catch(OperationNotSupportedException exception){
                exception.printStackTrace();
            }

        }

        //First step of Kosaraju's Algorithm
        for(int v = 1; v<=2* numVertices; v++){
            if(!graph.isVisited(v)){
                dfsFirst(v);
            }
        }

        //Second step of Kosaraju's algorithm
        //the id of the strongly connected component the node is part of is stored in scc
        while(!stack.isEmpty()){
            int elem = stack.pop();

            if(!inverseGraph.isVisited(elem)){
                dfsSecond(elem);
                counter++;
            }
        }

        for(int v = 1; v<= numVertices; v++){

            //check if variable and negated variable are in the same Strongly Connected Component
            if(scc[v] == scc[v+ numVertices]){
                isSatisfiable = false;
                solved = true;
                return;
            }
        }

        isSatisfiable = true;
        solved = true;
    }

}
