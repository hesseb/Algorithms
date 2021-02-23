// Taken from @MightyPork at:
// https://stackoverflow.com/questions/4965335/how-to-print-binary-tree-diagram
package com.williamfiset.algorithms.datastructures.utils;

import java.util.*;

public class TreePrinter {

  /** Node that can be printed */
  public interface PrintableNode {

    // Get left child
    public PrintableNode getLeft();

    // Get right child
    public PrintableNode getRight();

    // Get text to be printed
    public String getText();
  }

  // Print a binary tree.
  public static String getTreeDisplay(PrintableNode root) {

    boolean[] coverage = new boolean[27];

    StringBuilder sb = new StringBuilder();
    List<List<String>> lines = new ArrayList<List<String>>();
    List<PrintableNode> level = new ArrayList<PrintableNode>();
    List<PrintableNode> next = new ArrayList<PrintableNode>();

    level.add(root);
    int nn = 1;
    int widest = 0;

    while (nn != 0) {
      coverage[0] = true;
      nn = 0;
      List<String> line = new ArrayList<String>();
      for (PrintableNode n : level) {
        coverage[1] = true;
        if (n == null) {
          coverage[2] = true;
          line.add(null);
          next.add(null);
          next.add(null);
        } else {
          coverage[3] = true;
          String aa = n.getText();
          line.add(aa);
          if (aa.length() > widest) {
            coverage[4] = true;
            widest = aa.length();
          }

          next.add(n.getLeft());
          next.add(n.getRight());

          if (n.getLeft() != null) {
            coverage[5] = true;
            nn++;
          }
          if (n.getRight() != null) {
            coverage[6] = true;
            nn++;
          }
        }
      }

      if (widest % 2 == 1) {
        coverage[7] = true;
        widest++;
      }

      lines.add(line);

      List<PrintableNode> tmp = level;
      level = next;
      next = tmp;
      next.clear();
    }

    int perpiece = lines.get(lines.size() - 1).size() * (widest + 4);
    for (int i = 0; i < lines.size(); i++) {
      coverage[8] = true;
      List<String> line = lines.get(i);
      int hpw = (int) Math.floor(perpiece / 2f) - 1;
      if (i > 0) {
        coverage[9] = true;
        for (int j = 0; j < line.size(); j++) {
          coverage[10] = true;
          // split node
          char c = ' ';
          if (j % 2 == 1) {
            coverage[11] = true;
            if (line.get(j - 1) != null) {
              coverage[12] = true;
              c = (line.get(j) != null) ? '#' : '#';
            } else {
              coverage[13] = true;
              if (j < line.size() && line.get(j) != null) {
                coverage[14] = true;
                c = '#';
              }
            }
          }
          sb.append(c);

          // lines and spaces
          if (line.get(j) == null) {
            coverage[15] = true;
            for (int k = 0; k < perpiece - 1; k++) {
              coverage[16] = true;
              sb.append(' ');
            }
          } else {
            coverage[17] = true;
            for (int k = 0; k < hpw; k++) {
              coverage[18] = true;
              if (j % 2 == 0) {
                coverage[19] = true;
                sb.append(" ");
              } else {
                coverage[20] = true;
                sb.append("#");
              }
            }
            sb.append("#");
            for (int k = 0; k < hpw; k++) {
              coverage[19] = true;
              if (j % 2 == 0) {
                coverage[21] = true;
                sb.append(" ");
              } else {
                coverage[22] = true;
                sb.append("#");
              }
            }
          }
        }
        sb.append('\n');
      }
      for (int j = 0; j < line.size(); j++) {
        coverage[23] = true;
        String f = line.get(j);
        if (f == null) {
          coverage[24] = true;
          f = "";
        }
        int gap1 = (int) Math.ceil(perpiece / 2f - f.length() / 2f);
        int gap2 = (int) Math.floor(perpiece / 2f - f.length() / 2f);

        for (int k = 0; k < gap1; k++) {
          coverage[25] = true;
          sb.append(' ');
        }
        sb.append(f);
        for (int k = 0; k < gap2; k++) {
          coverage[26] = true;
          sb.append(' ');
        }
      }
      sb.append('\n');

      perpiece /= 2;
    }

    // Coverage printing
    ArrayList<Integer> coveredIds = new ArrayList<>();
    for (int i = 0; i < coverage.length; i++) {
      if(coverage[i]) {
        coveredIds.add(i);
      }
    }
    System.out.println(coveredIds);

    return sb.toString();
  }
}
