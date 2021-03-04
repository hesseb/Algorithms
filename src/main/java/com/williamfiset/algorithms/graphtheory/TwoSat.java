package com.williamfiset.algorithms.graphtheory;

import java.util.ArrayList;
import java.util.*;
import com.williamfiset.algorithms.utils.graphutils.Utils;

import javax.naming.OperationNotSupportedException;

public class TwoSat {
    private boolean isSatisfiable = false;
    private boolean solved = false;

    private int[] a;
    private int[] b;


    private GraphImplementation graph;
    private GraphImplementation inverseGraph;

    private static int numVariables;
    private static int numClauses;

    private String clauseExceptionMessage = "number of clauses do not match up";
    private String variableAmountNegativeExceptionMessage = "number of variables cannot be negative";

    private boolean[] visited;
    private boolean[] visitedInverse;

    private int MAX;


    private int[] scc;
    private int counter = 1;

    Stack<Integer> stack = new Stack<Integer>();
    public TwoSat(int[] a, int[] b, int numVariables) throws IllegalArgumentException{
        if(a.length!=b.length){
            throw new IllegalArgumentException(clauseExceptionMessage);
        }
        if(numVariables < 0){
            throw new IllegalArgumentException(variableAmountNegativeExceptionMessage);
        }
        this.a = a;
        this.b = b;
        numClauses = a.length;
        this.numVariables = numVariables;

        MAX = Math.max(numVariables*2, numClauses*4);

        graph = new GraphImplementation(MAX);
        inverseGraph = new GraphImplementation(MAX);

        visited = new boolean[MAX];
        visitedInverse = new boolean[MAX];
        scc = new int[MAX];

        solve();
    }

    public String getClauseExceptionMessage(){
        return clauseExceptionMessage;
    }

    public String getVariableAmountNegativeExceptionMessage(){
        return variableAmountNegativeExceptionMessage;
    }

    public void dfsFirst(int u){
        if(visited[u]) {
            return;
        }

        visited[u] = true;
        for(int i = 0; i< graph.getAdjacencyList().get(u).size(); i++){
            dfsFirst(graph.getAdjacencyList().get(u).get(i));
        }

        stack.push(u);
    }

    public void dfsSecond(int u){
        if(visitedInverse[u]){
            return;
        }

        visitedInverse[u] = true;
        for(int i = 0; i< inverseGraph.getAdjacencyList().get(u).size(); i++){
            dfsSecond((inverseGraph.getAdjacencyList().get(u).get(i)));
        }

        scc[u] = counter;
    }

    public boolean isTwoSatisfiable(){
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

            //TODO: check if the cases can be combined
            try{
                if(a[i] > 0 && b[i] > 0){
                    //add edge from negated a to b
                    graph.addDirectedEdge(a[i]+numVariables, b[i]);
                    //add flipped edge to inverse graph
                    inverseGraph.addDirectedEdge(b[i], a[i]+numVariables);

                    //add edge from negated b to a
                    graph.addDirectedEdge(b[i]+numVariables, a[i]);
                    //add flipped edge to inverse graph
                    inverseGraph.addDirectedEdge(a[i], b[i]+numVariables);
                } else if(a[i] > 0 && b[i] < 0){
                    //add edge from negated a to b
                    graph.addDirectedEdge(a[i]+numVariables, numVariables-b[i]);
                    //add flipped edge to inverse graph
                    inverseGraph.addDirectedEdge(numVariables-b[i], a[i]+numVariables);

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
                    graph.addDirectedEdge(b[i]+numVariables, numVariables-a[i]);
                    //add flipped edge to inverse graph
                    inverseGraph.addDirectedEdge(numVariables-a[i], b[i]+numVariables);
                } else{
                    //add edge from negated a to b
                    graph.addDirectedEdge(-a[i], numVariables-b[i]);
                    //add flipped edge to inverse graph
                    inverseGraph.addDirectedEdge(numVariables-b[i],-a[i]);

                    //add edge from negated b to a
                    graph.addDirectedEdge(-b[i], numVariables-a[i]);
                    //add flipped edge to inverse graph
                    inverseGraph.addDirectedEdge(numVariables-a[i], -b[i]);
                }
            }catch(OperationNotSupportedException exception){
                exception.printStackTrace();
            }

        }

        //First step of Kosaraju's Algorithm
        for(int i=1; i<=2*numVariables; i++){
            if(!visited[i]){
                dfsFirst(i);
            }
        }

        //Second step of Kosaraju's algorithm
        //the id of the strongly connected component the node is part of is stored in scc
        while(!stack.isEmpty()){
            int elem = stack.pop();

            if(!visitedInverse[elem]){
                dfsSecond(elem);
                counter++;
            }
        }

        for(int i=1; i<=numVariables; i++){

            //check if variable and negated variable are in the same Strongly Connected Component
            if(scc[i] == scc[i+numVariables]){
                isSatisfiable = false;
                solved = true;
                return;
            }
        }

        isSatisfiable = true;
        solved = true;
    }

}
