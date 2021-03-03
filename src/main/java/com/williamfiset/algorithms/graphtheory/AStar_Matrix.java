package com.williamfiset.algorithms.graphtheory;

import java.util.*;

public class AStar_Matrix
{

    public static class Node implements Comparable<Node>
    {

        int id;
        private double f, g, h;
        boolean traversable;
        int parentIndex;

        private static final double EPS = 1e-7;
    
        public Node(int id, boolean traversable)
        {
            this.id = id;
            this.traversable = traversable;
            this.g = Double.POSITIVE_INFINITY;
            this.h = Double.POSITIVE_INFINITY;
            this.parentIndex = -1;
        }

        public void setG(double g)
        {
            this.g = g;
            this.f = this.g + this.h;
        }

        public void setH(double h)
        {
            this.h = h;
            this.f = this.g + this.h;
        }

        public double getG() {
            return g;
        }

        public double getH() {
            return h;
        }

        public double getF() {
            return f;
        }

        // Sort by f cost and break ties with h
        @Override
        public int compareTo(Node other)
        {
            if (Math.abs(this.f - other.f) < EPS)
            {
                if (Math.abs(this.h - other.h) < EPS) return 0;
                return (this.h - other.h) <= 0 ? +1 : -1;
            }
            return (this.f - other.f) <= 0 ? +1 : -1;
        }
    }

    public static class Matrix
    {
        int xSide;
        int ySide;
        Node[] nodes;
    
        public Matrix(int xSide, int ySide, boolean[] nodes)
        {
            if (xSide * ySide != nodes.length) throw new IllegalArgumentException("Matrix dimensions not valid. x*y != nodes.length");
            this.xSide = xSide;
            this.ySide = ySide;
            this.nodes = new Node[xSide * ySide];
            
            for(int j = 0; j < ySide; j++)
            {
                for(int i = 0; i < xSide; i++)
                {
                    int index = j*xSide + i;
                    this.nodes[index] = new Node(index, nodes[index]);
                }
            }
        }
        
        public boolean isValidIndex(int index)
        {
            if (index >= xSide * ySide) return false;
            return index >= 0;
        }

        public Node getNode(int index)
        {
            if (isValidIndex(index)) return nodes[index];
            else throw new IllegalArgumentException("Invalid index.");
        }

        @Override
        public String toString()
        {
            String str = "";
            for(int j = 0; j < ySide; j++)
            {
                for(int i = 0; i < xSide; i++)
                {
                    if (nodes[j*xSide + i].traversable)
                    {
                        int index = j*xSide + i;
                        str += "[";
                        if (index < 10) str += " ";
                        str += index + "]";
                    }
                    else str += "[xx]";
                }
                str += "\n";
            }

            return str;
        }

        //Converts index to coordinates and calculates euclidean distance between nodes
        public double getHeuristic(int node, int end)
        {
            int nodeX = node % xSide;
            int nodeY = node / ySide;
    
            int endX = end % xSide;
            int endY = end / ySide;
    
            int dx = (nodeX - endX) * (nodeX - endX);
            int dy = (nodeY - endY) * (nodeY - endY);
    
            return dx + dy;
        }
        
        public ArrayList<Integer> getPath(Node node)
        {
            ArrayList<Integer> path = new ArrayList<>();

            path.add(node.id);

            while(node.parentIndex != -1)
            {
                path.add(node.parentIndex);
                node = getNode(node.parentIndex);
            }

            return path;
        }
    }
    
    public static Matrix mat;

    public AStar_Matrix(int xSide, int ySide, boolean[] stage)
    {
        mat = new Matrix(xSide, ySide, stage);
    }

    public static void main(String[] args)
    {
        int xSide = 3;
        int ySide = 3;

        boolean[] stage = {true, false, true,
                true, false, true,
                true, true, true};

        int start = 0;
        int end = 2;

        mat = new Matrix(xSide, ySide, stage);
        ArrayList<Integer> path = astar(start, end);
        
        if (path.isEmpty())
        {
            System.out.println("No path found.");
        }
        else
        {
            System.out.println("Start: " + start + "\tGoal: " + end);
            System.out.println(mat);
            Collections.reverse(path);
            System.out.println(path);
            System.out.println("Path cost: " + mat.getNode(end).getF());
            System.out.println("Path cost: " + mat.getNode(end).getH());
            System.out.println("Path cost: " + mat.getNode(end).getG());
        }
    }

    public static ArrayList<Integer> astar(int start, int end)
    {
        if (!mat.isValidIndex(start))
        {
            throw new IllegalArgumentException("Start node index invalid.");
        }
        if (!mat.getNode(start).traversable)
        {
            throw new IllegalArgumentException("Start node not traversable.");
        }

        if (!mat.isValidIndex(end))
        {
            throw new IllegalArgumentException("End node index invalid.");
        }
        if (!mat.getNode(end).traversable)
        {
            throw new IllegalArgumentException("End node not traversable.");
        }

        PriorityQueue<Node> queue = new PriorityQueue<>();
        Set<Integer> openSet = new HashSet<>();
        Set<Integer> closedSet = new HashSet<>();

        Node startNode = mat.getNode(start);

        startNode.setG(0);
        startNode.setH(mat.getHeuristic(start, end));

        queue.offer(startNode);
        openSet.add(start);

        while (!queue.isEmpty())
        {
            Node currNode = queue.poll();
            openSet.remove(currNode.id);
            closedSet.add(currNode.id);

            if (currNode.id == end) return mat.getPath(currNode); //return

            //Stops edge nodes from checking wrong neighbors
            int minI, maxI, minJ, maxJ;
            minI = -1;
            minJ = -1;
            maxI = 1;
            maxJ = 1;
            //if curr % xSide == ySide -> on right edge of stage
            //if curr % xSide == 0 -> on left edge of stage
            if (currNode.id % mat.xSide == (mat.xSide - 1)) maxI = 0;
            else if (currNode.id % mat.xSide == 0) minI = 0;

            //if curr / xSide == ySide-1 -> on last row
            //if curr / xSide == 0 -> on first row
            if (currNode.id / mat.xSide == (mat.ySide-1)) maxJ = 0;
            else if (currNode.id / mat.xSide == 0) minJ = 0;

            //Cycle through current nodes neighbors
            for (int i = minI; i <= maxI; i++)
            {
                for (int j = minJ; j <= maxJ; j++)
                {
                    //Check index is not self, is valid, traversable and not in closed set
                    if (i == 0 && j == 0) continue;
                    
                    int neighborIndex = currNode.id + i + j*mat.xSide;

                    if (!mat.isValidIndex(neighborIndex)) continue;

                    Node neighborNode = mat.getNode(neighborIndex);
                    if (!neighborNode.traversable) continue;
                    if (closedSet.contains(neighborIndex)) continue;

                    //Checks if neighbor is on diagonal path or adjacent
                    double distance = (i == 0 || j == 0) ? 1.0 : 1.4;

                    double neighborG = currNode.getG() + distance;
                                            
                    if (neighborG < neighborNode.getG() || !openSet.contains(neighborIndex))
                    {
                        neighborNode.setG(neighborG);
                        neighborNode.setH(mat.getHeuristic(neighborIndex, end));
                        neighborNode.parentIndex = currNode.id;

                        if (!openSet.contains(neighborIndex))
                        {
                            queue.offer(neighborNode);
                            openSet.add(neighborIndex);
                        }
                    }
                }
            }
        }

        //If here, no path found, returns empty path
        return new ArrayList<>();
    }
}