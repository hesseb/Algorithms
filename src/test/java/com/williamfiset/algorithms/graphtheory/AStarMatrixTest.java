package com.williamfiset.algorithms.graphtheory;

import static com.google.common.truth.Truth.assertThat;

import java.lang.reflect.Array;
import java.util.*;
import org.junit.*;

public class AStarMatrixTest {

    private static final double EPS = 1e-5;

    @Test(expected = IllegalArgumentException.class)
    public void testStartNodeOutOfBounds() {
        int xSide = 2;
        int ySide = 1;

        boolean[] stage = {true, true};

        int start = 10;
        int end = 0;

        AStar_Matrix AStar_Instance = new AStar_Matrix(xSide, ySide, stage);
        ArrayList<Integer> path = AStar_Instance.astar(start, end);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testStartNodeNotTraversable() {
        int xSide = 2;
        int ySide = 1;

        boolean[] stage = {true, false};

        int start = 1;
        int end = 0;

        AStar_Matrix AStar_Instance = new AStar_Matrix(xSide, ySide, stage);
        ArrayList<Integer> path = AStar_Instance.astar(start, end);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEndNodeOutOfBounds() {
        int xSide = 2;
        int ySide = 1;

        boolean[] stage = {true, true};

        int start = 0;
        int end = 10;

        AStar_Matrix AStar_Instance = new AStar_Matrix(xSide, ySide, stage);
        ArrayList<Integer> path = AStar_Instance.astar(start, end);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEndNodeNotTraversable() {
        int xSide = 2;
        int ySide = 1;

        boolean[] stage = {true, false};

        int start = 0;
        int end = 1;

        AStar_Matrix AStar_Instance = new AStar_Matrix(xSide, ySide, stage);
        ArrayList<Integer> path = AStar_Instance.astar(start, end);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidNodeIndex() {
        int xSide = 2;
        int ySide = 1;

        boolean[] stage = {true, false};

        AStar_Matrix AStar_Instance = new AStar_Matrix(xSide, ySide, stage);
        AStar_Instance.mat.getNode(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidMatrixDimension() {
        int xSide = 2;
        int ySide = 2;

        boolean[] stage = {true, false};

        AStar_Matrix AStar_Instance = new AStar_Matrix(xSide, ySide, stage);
    }

    @Test
    public void testMatrixToString() {
        int xSide = 2;
        int ySide = 2;

        boolean[] stage = {true, false,
                false, true};

        AStar_Matrix AStar_Instance = new AStar_Matrix(xSide, ySide, stage);
        String expected = "[ 0][xx]\n[xx][ 3]\n";
        assert(AStar_Instance.mat.toString().equals(expected));
    }

    @Test
    public void testGetters() {
        int xSide = 3;
        int ySide = 3;

        boolean[] stage = {true, false, true,
                true, false, true,
                true, true, true};

        int start = 0;
        int end = 2;

        AStar_Matrix AStar_Instance = new AStar_Matrix(xSide, ySide, stage);
        ArrayList<Integer> path = AStar_Instance.astar(start, end);

        double expectedG = 4.8;
        double expectedH = 0.0;
        double expectedF = expectedG + expectedH;

        assert(AStar_Instance.mat.getNode(end).getG() == expectedG);
        assert(AStar_Instance.mat.getNode(end).getH() == expectedH);
        assert(AStar_Instance.mat.getNode(end).getF() == expectedF);

        expectedG = 3.8;
        expectedH = 1.0;
        expectedF = expectedG + expectedH;

        assert(AStar_Instance.mat.getNode(AStar_Instance.mat.getNode(end).parentIndex).getF() == expectedF);
        assert(AStar_Instance.mat.getNode(AStar_Instance.mat.getNode(end).parentIndex).getG() == expectedG);
        assert(AStar_Instance.mat.getNode(AStar_Instance.mat.getNode(end).parentIndex).getH() == expectedH);
    }

    @Test
    public void testAStar1() {
        int xSide = 5;
        int ySide = 4;

        boolean[] stage = {true, false, true, true, true,
                true, false, true, false,  true,
                true, false, true, false,  true,
                true, true, true, false,  true};

        int start = 19;
        int end = 0;

        AStar_Matrix AStar_Instance = new AStar_Matrix(xSide, ySide, stage);

        ArrayList<Integer> path = AStar_Instance.astar(start, end);
        Collections.reverse(path);
        ArrayList<Integer> expected = new ArrayList<>(Arrays.asList(19, 14, 9, 3, 7, 12, 16, 10, 5, 0));

        assert(path.equals(expected));
    }

    @Test
    public void testAStar2() {
        int xSide = 3;
        int ySide = 3;

        boolean[] stage = {true, false, true,
                           true, false, true,
                           true, false, true};

        int start = 8;
        int end = 0;

        AStar_Matrix AStar_Instance = new AStar_Matrix(xSide, ySide, stage);

        ArrayList<Integer> path = AStar_Instance.astar(start, end);
        assert(path.isEmpty());
    }

    @Test
    public void testAStar3() {
        int xSide = 3;
        int ySide = 3;

        boolean[] stage = {true, true, true,
                true, true, true,
                true, true, true};

        int start = 8;
        int end = 0;

        AStar_Matrix AStar_Instance = new AStar_Matrix(xSide, ySide, stage);

        ArrayList<Integer> path = AStar_Instance.astar(start, end);
        Collections.reverse(path);
        ArrayList<Integer> expected = new ArrayList<>(Arrays.asList(8, 4, 0));

        assert(path.equals(expected));
    }

    @Test
    public void testAStar4() {
        int xSide = 3;
        int ySide = 3;

        boolean[] stage = {true, true, true,
                true, true, true,
                true, true, true};

        int start = 0;
        int end = 0;

        AStar_Matrix AStar_Instance = new AStar_Matrix(xSide, ySide, stage);

        ArrayList<Integer> path = AStar_Instance.astar(start, end);
        Collections.reverse(path);
        ArrayList<Integer> expected = new ArrayList<>(Arrays.asList(0));

        assert(path.equals(expected));
    }
}
