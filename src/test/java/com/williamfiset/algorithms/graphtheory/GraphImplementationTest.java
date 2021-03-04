package com.williamfiset.algorithms.graphtheory;

import org.graalvm.compiler.graph.Graph;
import org.junit.Test;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import java.util.List;

public class GraphImplementationTest {
    @Test
    public void testSimpleConstructor(){
        int numVertices = 1;
        GraphImplementation graph = new GraphImplementation(numVertices);
        assert(graph.getNumVertices()==numVertices && graph.getNumEdges() == 0);
    }

    @Test
    public void testDirectedEdgeConstructor(){
        GraphImplementation graph = new GraphImplementation(new int[]{0}, new int[]{1}, true, 2);
        assert(graph.existsEdge(0,1) && graph.getNumEdges()==1);
    }

    @Test
    public void testUndirectedEdgeConstructor(){
        GraphImplementation graph = new GraphImplementation(new int[]{0}, new int[]{1}, false, 2);
        assert(graph.existsEdge(0,1) && graph.existsEdge(1,0) && graph.getNumEdges()==1);
    }

    @Test
    public void testDirectedGraphConstructorIllegalArgumentExceptionClause(){
        GraphImplementation graph = new GraphImplementation(2);
        try{
            graph = new GraphImplementation(new int[]{0}, new int[]{1,2}, true, 2);
        } catch(IllegalArgumentException exception){
            assert(exception.getMessage().equals(graph.getIllegalArgumentExceptionMessage()));
        }
    }

    @Test
    public void testAddDirectedEdge(){
        GraphImplementation graph = new GraphImplementation(2);
        try{
            graph.addDirectedEdge(0,1);
        }catch(OperationNotSupportedException exception){
            exception.printStackTrace();
        }
        assert(graph.existsEdge(0,1) && graph.getNumEdges()==1);
    }

    @Test
    public void testAddUndirectedEdge(){
        GraphImplementation graph = new GraphImplementation(2);
        try{
            graph.addUndirectedEdge(0,1);
        }catch(OperationNotSupportedException exception){
            exception.printStackTrace();
        }
        assert((graph.existsEdge(0,1) && graph.existsEdge(1,0)) && graph.getNumEdges()==1);
    }

    @Test
    public void testAddDirectedEdgeToUndirectedGraph(){
        GraphImplementation graph = new GraphImplementation(new int[]{0}, new int[]{1}, false, 2);
        try{
            graph.addDirectedEdge(0,1);
        }catch(OperationNotSupportedException exception){
            assert(exception.getMessage().equals(graph.getOperationNotSupportedExceptionMessage()));
        }
    }

    @Test
    public void testRemoveDirectedEdge(){
        GraphImplementation graph = new GraphImplementation(new int[]{0}, new int[]{1}, true, 2);
        assert(graph.existsEdge(0,1) && graph.getNumEdges()==1);
        graph.removeEdge(0,1);
        assert(!graph.existsEdge(0,1) && graph.getNumEdges()==0);
    }

    @Test
    public void testRemoveUndirectedEdge(){
        GraphImplementation graph = new GraphImplementation(new int[]{0}, new int[]{1}, false, 2);
        assert((graph.existsEdge(0,1))&& graph.existsEdge(1,0) && graph.getNumEdges()==1);
        graph.removeUndirectedEdge(0,1);
        assert(!(graph.existsEdge(0,1)|| graph.existsEdge(1,0)) && graph.getNumEdges()==0);
    }

    @Test
    public void testVisited(){
        GraphImplementation graph = new GraphImplementation(2);
        assert(!graph.isVisited(1));
        graph.setVisited(1, true);
        assert(graph.isVisited(1));
    }

    public boolean allEmpty(List<List<Integer>> adjacencyList){
        boolean allEmpty = true;
        for(int i=0; i<adjacencyList.size(); i++){
            if(!adjacencyList.get(i).isEmpty()){
                allEmpty = false;
                break;
            }
        }
        return allEmpty;
    }

    @Test
    public void testClear(){
        GraphImplementation graph = new GraphImplementation(new int[]{0}, new int[]{1}, false, 2);
        assert(!allEmpty(graph.getAdjacencyList()));
        graph.clear();
        assert(allEmpty(graph.getAdjacencyList()));
    }

    @Test
    public void testGetNeighbors(){
        GraphImplementation graph = new GraphImplementation(new int[]{0}, new int[]{1}, false, 2);
        int vertex = 0;
        assert(graph.getAdjacencyList().get(vertex).equals(graph.getNeighbors(vertex)));
    }

    @Test
    public void testPrintGraph(){
        String expected = "0:\n" +
                "0->1, 0->2\n" +
                "1:\n" +
                "1->0, 1->2\n" +
                "2:\n" +
                "2->1, 2->0\n";
        GraphImplementation graph = new GraphImplementation(new int[]{0,1,2}, new int[]{1,2,0}, false, 4);

        assert(graph.toString().equals(expected));
    }

}
