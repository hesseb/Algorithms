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

    public TwoSat(int numVertices)throws IllegalArgumentException{
        initializeVertices(numVertices);
    }

    public TwoSat(int[] a, int[] b, int numVertices) throws IllegalArgumentException{
        initializeVertices(numVertices);
        setClauses(a,b);
    }

    public void clear(){
        graph.clear();
        inverseGraph.clear();

        for(int i=0; i<MAX; i++){
            scc[i] = 0;
        }

        solved = false;
        isSatisfiable = false;
    }

    public void setClauses(int[] a, int[] b)throws IllegalArgumentException{
        if(a.length!=b.length){
            throw new IllegalArgumentException(clauseExceptionMessage);
        }
        this.a = a;
        this.b = b;
        numClauses = a.length;
    }

    public String getClauseExceptionMessage(){
        return clauseExceptionMessage;
    }

    public String getVertexAmountNegativeExceptionMessage(){
        return vertexAmountNegativeExceptionMessage;
    }

    public String getIllegalStateExceptionMessage(){
        return illegalStateExceptionMessage;
    }

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

    public boolean isTwoSatisfiable(){
        if(a == null || b == null || a.length == 0 || b.length == 0){
            throw new IllegalStateException(illegalStateExceptionMessage);
        }

        if(!solved){
            solve();
        }
        return isSatisfiable;
    }

    public void solve(){

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
