package com.williamfiset.algorithms.graphtheory;

import java.util.ArrayList;
import java.util.*;

public class TwoSat {
    private boolean isSatisfiable = false;
    private boolean solved = false;

    private int[] a;
    private int[] b;


    private List<List<Integer>> adjacencyList = new ArrayList<>();
    private List<List<Integer>> adjacencyListInverse = new ArrayList<>();

    private static int numVariables;
    private static int numClauses;

    private boolean[] visited;
    private boolean[] visitedInverse;

    private int MAX;


    private int[] scc;
    private int counter = 1;

    Stack<Integer> stack = new Stack<Integer>();
    public TwoSat(int[] a, int[] b, int numVariables){
        if(a.length!=b.length){
            throw new IllegalArgumentException("number of clauses do not match up");
        }
        this.a = a;
        this.b = b;
        numClauses = a.length;
        this.numVariables = numVariables;

        MAX = Math.max(numVariables*2, numClauses*4);

        for(int i=0; i<MAX; i++){
            adjacencyList.add(new ArrayList<Integer>());
            adjacencyListInverse.add(new ArrayList<Integer>());
        }

        visited = new boolean[MAX];
        visitedInverse = new boolean[MAX];
        scc = new int[MAX];

        solve();
    }

    public void addEdge(int a, int b){
        adjacencyList.get(a).add(b);
    }

    public void addEdgeInverse(int a, int b){
        adjacencyListInverse.get(b).add(a);
    }

    public void dfsFirst(int u){
        if(visited[u]) {
            return;
        }

        visited[u] = true;
        for(int i = 0; i< adjacencyList.get(u).size(); i++){
            dfsFirst(adjacencyList.get(u).get(i));
        }

        stack.push(u);
    }

    public void dfsSecond(int u){
        if(visitedInverse[u]){
            return;
        }

        visitedInverse[u] = true;
        for(int i = 0; i< adjacencyListInverse.get(u).size(); i++){
            dfsSecond((adjacencyListInverse.get(u).get(i)));
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

        //adding the edges to the graph representing the conjuctive formula

        //x is mapped to x
        //negation of x is mapped to n+x (=n-(-x))



        for(int i=0; i<numClauses; i++){
            //TODO: check if the cases can be combined into one
            if(a[i] > 0 && b[i] > 0){
                addEdge(a[i]+numVariables, b[i]);
                addEdgeInverse(a[i]+numVariables, b[i]);
                addEdge(b[i]+numVariables, a[i]);
                addEdgeInverse(b[i]+numVariables, a[i]);
            } else if(a[i] > 0 && b[i] < 0){
                addEdge(a[i]+numVariables, numVariables-b[i]);
                addEdgeInverse(a[i]+numVariables, numVariables-b[i]);
                addEdge(-b[i], a[i]);
                addEdgeInverse(-b[i], a[i]);
            } else if(a[i] < 0 && b[i] > 0){
                addEdge(-a[i], b[i]);
                addEdgeInverse(-a[i], b[i]);
                addEdge(b[i]+numVariables, numVariables-a[i]);
                addEdgeInverse(b[i]+numVariables, numVariables-a[i]);
            } else{
                addEdge(-a[i], numVariables-b[i]);
                addEdgeInverse(-a[i], numVariables-b[i]);
                addEdge(-b[i], numVariables-a[i]);
                addEdgeInverse(-b[i], numVariables-a[i]);
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

    public static void main(String[] args){
        int[] a = {1,-2,-1,3,-3,-4,-3};
        int[] b = {2,3,-2,4,5,-5,4};
        TwoSat twosat = new TwoSat(a, b, 5);
        System.out.println("isSolvable: "+twosat.isTwoSatisfiable());
    }

}
