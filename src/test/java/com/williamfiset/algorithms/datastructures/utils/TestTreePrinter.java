package com.williamfiset.algorithms.datastructures.utils;

import com.williamfiset.algorithms.datastructures.balancedtree.AVLTreeRecursive;
import org.junit.*;

public class TestTreePrinter {
    @Test
    public void test() {
        AVLTreeRecursive<String> tree = new AVLTreeRecursive<>();
        tree.insert("1");
        tree.insert("2");
        tree.insert("3");
        tree.insert("4");
        tree.insert("5");
        tree.insert("6");
        tree.insert("7");

        String[] lines = tree.toString().split("\n");
        assert(lines[0].trim().replace(" ", "").equals("4"));
        assert(lines[2].trim().replace(" ", "").equals("26"));
        assert(lines[4].trim().replace(" ", "").equals("1357"));

        AVLTreeRecursive<String> tree2 = new AVLTreeRecursive<>();
        assert(tree2.toString().trim().equals(""));
    }
}
