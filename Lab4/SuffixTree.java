import java.util.*;

public class SuffixTree {
   private InternalNode root;
   private String sequence;

   public SuffixTree(String dna) {
      root = new InternalNode(-1, -1);
      sequence = dna.toLowerCase();
      System.out.println(sequence);
      buildTree();
   }

   public ArrayList findString(String toFind) {
      return new ArrayList();
   }
   
   private void printTree(Node cur) {
      System.out.println("-------------------------");
      System.out.println(cur.getStartIdx());
      System.out.println(cur.getEndIdx());
       //                               A     T      C      G      $
      boolean[] test = new boolean[]{false, false, false, false, false};
      
      if(cur instanceof InternalNode && ((InternalNode)cur).a != null) {
         System.out.println("a");
         test[0] = true;
      }
      if(cur instanceof InternalNode && ((InternalNode)cur).t != null) {
         System.out.println("t");
         test[1] = true;
      }
      if(cur instanceof InternalNode && ((InternalNode)cur).c != null) {
         System.out.println("c");
         test[2] = true;
      }
      if(cur instanceof InternalNode && ((InternalNode)cur).g != null) {
         System.out.println("g");
         test[3] = true;
      } 
      if(cur instanceof InternalNode && ((InternalNode)cur).dolla != null) {
         System.out.println("$");
         test[4] = true;
      }
      
      if(test[0])
         printTree(((InternalNode)cur).a);
      if(test[1])
         printTree(((InternalNode)cur).t);
      if(test[2])
         printTree(((InternalNode)cur).c);
      if(test[3])
         printTree(((InternalNode)cur).g);
      if(test[4])
         printTree(((InternalNode)cur).dolla);
   }

   private void buildTree() {
      TraverseInfo traversal = null;

      for(int i = 0; i < sequence.length(); i++) {
         char curChar = sequence.substring(i).charAt(0);
         
         //If root case
         if(curChar == 'a' && root.a == null) {
            root.a = new LeafNode(i, i, sequence.length() - 1);
         }
         else if(curChar == 't' && root.t == null)
            root.t = new LeafNode(i, i, sequence.length() - 1);
         else if(curChar == 'c' && root.c == null)
            root.c = new LeafNode(i, i, sequence.length() - 1);
         else if(curChar == 'g' && root.g == null)
            root.g = new LeafNode(i, i, sequence.length() - 1);
         else if(curChar == '$' && root.dolla == null)
            root.dolla = new LeafNode(i, i, sequence.length() - 1);
         else {
            switch(curChar) {
               case('a'):
                  traversal = traverse(root.a, root, sequence.substring(i), i);
                  break;
               case('t'):
                  traversal = traverse(root.t, root, sequence.substring(i), i);
                  break;
               case('c'):
                  traversal = traverse(root.c, root, sequence.substring(i), i);
                  break;
               case('g'):
                  traversal = traverse(root.g, root, sequence.substring(i), i);
                  break;
            }
            graft(traversal, i);
         }
      }
      printTree(root);
   }

   //Returns last traveresed parent node and index of last matched char
   private TraverseInfo traverse(Node current, Node parent, String toMatch, int idxOfToMatch) {
      int j = current.getStartIdx(), i = 0;
      Node newCur = null;

      for(; j <= current.getEndIdx() + 1 && i < toMatch.length(); i++, j++, idxOfToMatch++) {
         //If should split
         if(toMatch.charAt(i) != sequence.charAt(j)) {
            if(toMatch.charAt(i) == 'a' && current instanceof InternalNode && ((InternalNode)current).a != null && j == current.getEndIdx() + 1)
               traverse(((InternalNode)current).a, current, toMatch.substring(i), idxOfToMatch);
            else if(toMatch.charAt(i) == 't' && current instanceof InternalNode && ((InternalNode)current).t != null && j == current.getEndIdx() + 1)
               traverse(((InternalNode)current).t, current, toMatch.substring(i), idxOfToMatch);
            else if(toMatch.charAt(i) == 'c' && current instanceof InternalNode && ((InternalNode)current).c != null && j == current.getEndIdx() + 1)
               traverse(((InternalNode)current).c, current, toMatch.substring(i), idxOfToMatch);
            else if(toMatch.charAt(i) == 'g' && current instanceof InternalNode && ((InternalNode)current).g != null && j == current.getEndIdx() + 1)
               traverse(((InternalNode)current).g, current, toMatch.substring(i), idxOfToMatch);
            else
               return new TraverseInfo(j - 1, current, parent, toMatch.substring(i), sequence.charAt(current.getStartIdx()), idxOfToMatch);
         }
      }
      
      if(j == current.getEndIdx() + 2) {
         i--;
         idxOfToMatch--;
      }

      //We know current is an internal node if we get past the loop
      InternalNode tmp = (InternalNode)current;
      
      if(toMatch.charAt(i) == 'a' && tmp.a != null)
         newCur = tmp.a;
      else if(toMatch.charAt(i) == 't' && tmp.t != null)
         newCur = tmp.t;
      else if(toMatch.charAt(i) == 'c' && tmp.c != null)
         newCur = tmp.c;
      else if (toMatch.charAt(i) == 'g' && tmp.g != null)
         newCur = tmp.g;
      else
         System.out.println("Should never get here");

      return traverse(newCur, current, toMatch.substring(i), idxOfToMatch);
   }

   //Adding another leaf node to an preexisting leaf node
   private void graft(TraverseInfo data, int leafLabel) {
      InternalNode parent = (InternalNode)data.parent;
      Node current = data.current;
      int charIdx = data.indexOfChar;
      String toMatch = data.toMatch;
      char pathTaken = data.pathTaken;
      int curStartIdx = current.getStartIdx();
      int newLeafStartIdx = data.idxOfToMatch;

      switch(toMatch.charAt(0)) {
         case 'a':
            if(pathTaken == 'a') {
               if(current.getEndIdx() != charIdx) {
                  current.setStartIdx(charIdx + 1);
                  parent.a = new InternalNode(curStartIdx, charIdx);
               }
               ((InternalNode)parent.a).a = new LeafNode(leafLabel, newLeafStartIdx, sequence.length() - 1);

               if(current.getEndIdx() != charIdx) {
                  switch(sequence.charAt(charIdx + 1)) {
                     case 't':
                        ((InternalNode)parent.a).t = current;
                        break;
                     case 'c':
                        ((InternalNode)parent.a).c = current;
                        break;
                     case 'g':
                        ((InternalNode)parent.a).g = current;
                        break;
                     case '$':
                        ((InternalNode)parent.a).dolla = current;
                        break;
                  }
               }
            }
            else if(pathTaken == 't') {
               if(current.getEndIdx() != charIdx) {
                  current.setStartIdx(charIdx + 1);
                  parent.t = new InternalNode(curStartIdx, charIdx);
               }
               ((InternalNode)parent.t).a = new LeafNode(leafLabel, newLeafStartIdx, sequence.length() - 1);

               if(current.getEndIdx() != charIdx) {
                  switch(sequence.charAt(charIdx + 1)) {
                     case 't':
                        ((InternalNode)parent.t).t = current;
                        break;
                     case 'c':
                        ((InternalNode)parent.t).c = current;
                        break;
                     case 'g':
                        ((InternalNode)parent.t).g = current;
                        break;
                     case '$':
                        ((InternalNode)parent.t).dolla = current;
                        break;
                  }
               }
            }
            else if(pathTaken == 'c') {
               if(current.getEndIdx() != charIdx) {
                  current.setStartIdx(charIdx + 1);
                  parent.c = new InternalNode(curStartIdx, charIdx);
               }
               ((InternalNode)parent.c).a = new LeafNode(leafLabel, newLeafStartIdx, sequence.length() - 1);

               if(current.getEndIdx() != charIdx) {
                  switch(sequence.charAt(charIdx + 1)) {
                     case 'c':
                        ((InternalNode)parent.c).c = current;
                        break;
                     case 't':
                        ((InternalNode)parent.c).t = current;
                        break;
                     case 'g':
                        ((InternalNode)parent.c).g = current;
                        break;
                     case '$':
                        ((InternalNode)parent.c).dolla = current;
                        break;
                  }
               }
            }
            else if(pathTaken == 'g') {
               if(current.getEndIdx() != charIdx) {
                  current.setStartIdx(charIdx + 1);
                  parent.g = new InternalNode(curStartIdx, charIdx);
               }
               ((InternalNode)parent.g).a = new LeafNode(leafLabel, newLeafStartIdx, sequence.length() - 1);

               if(current.getEndIdx() != charIdx) {
                  switch(sequence.charAt(charIdx + 1)) {
                     case 'g':
                        ((InternalNode)parent.g).g = current;
                        break;
                     case 't':
                        ((InternalNode)parent.g).t = current;
                        break;
                     case 'c':
                        ((InternalNode)parent.g).c = current;
                        break;
                     case '$':
                        ((InternalNode)parent.g).dolla = current;
                        break;
                  }
               }
            }
            break;
         case 't':
            if(pathTaken == 'a') {
               if(current.getEndIdx() != charIdx) {
                  current.setStartIdx(charIdx + 1);
                  parent.a = new InternalNode(curStartIdx, charIdx);
               }
               ((InternalNode)parent.a).t = new LeafNode(leafLabel, newLeafStartIdx, sequence.length() - 1);

               if(current.getEndIdx() != charIdx) {
                  switch(sequence.charAt(charIdx + 1)) {
                     case 'a':
                        ((InternalNode)parent.a).a = current;
                        break;
                     case 'c':
                        ((InternalNode)parent.a).c = current;
                        break;
                     case 'g':
                        ((InternalNode)parent.a).g = current;
                        break;
                     case '$':
                        ((InternalNode)parent.a).dolla = current;
                        break;
                  }
               }
            }
            else if(pathTaken == 't') {
               if(current.getEndIdx() != charIdx) {
                  current.setStartIdx(charIdx + 1);
                  parent.t = new InternalNode(curStartIdx, charIdx);
               }
               ((InternalNode)parent.t).t = new LeafNode(leafLabel, newLeafStartIdx, sequence.length() - 1);

               if(current.getEndIdx() != charIdx) {
                  switch(sequence.charAt(charIdx + 1)) {
                     case 'a':
                        ((InternalNode)parent.t).a = current;
                        break;
                     case 'c':
                        ((InternalNode)parent.t).c = current;
                        break;
                     case 'g':
                        ((InternalNode)parent.t).g = current;
                        break;
                     case '$':
                        ((InternalNode)parent.t).dolla = current;
                        break;
                  }
               }
            }
            else if(pathTaken == 'c') {
               if(current.getEndIdx() != charIdx) {
                  current.setStartIdx(charIdx + 1);
                  parent.c = new InternalNode(curStartIdx, charIdx);
               }
               ((InternalNode)parent.c).t = new LeafNode(leafLabel, newLeafStartIdx, sequence.length() - 1);

               if(current.getEndIdx() != charIdx) {
                  switch(sequence.charAt(charIdx + 1)) {
                     case 'a':
                        ((InternalNode)parent.c).a = current;
                        break;
                     case 'c':
                        ((InternalNode)parent.c).c = current;
                        break;
                     case 'g':
                        ((InternalNode)parent.c).g = current;
                        break;
                     case '$':
                        ((InternalNode)parent.c).dolla = current;
                        break;
                  }
               }
            }
            else if(pathTaken == 'g') {
               if(current.getEndIdx() != charIdx) {
                  current.setStartIdx(charIdx + 1);
                  parent.g = new InternalNode(curStartIdx, charIdx);
               }
               ((InternalNode)parent.g).t = new LeafNode(leafLabel, newLeafStartIdx, sequence.length() - 1);

               if(current.getEndIdx() != charIdx) {
                  switch(sequence.charAt(charIdx + 1)) {
                     case 'a':
                        ((InternalNode)parent.g).a = current;
                        break;
                     case 'g':
                        ((InternalNode)parent.g).g = current;
                        break;
                     case 'c':
                        ((InternalNode)parent.g).c = current;
                        break;
                     case '$':
                        ((InternalNode)parent.g).dolla = current;
                        break;
                  }
               }
            }
            break;
         case 'c':
            if(pathTaken == 'a') {
               if(current.getEndIdx() != charIdx) {
                  current.setStartIdx(charIdx + 1);
                  parent.a = new InternalNode(curStartIdx, charIdx);
               }
               ((InternalNode)parent.a).c = new LeafNode(leafLabel, newLeafStartIdx, sequence.length() - 1);

               if(current.getEndIdx() != charIdx) {
                  switch(sequence.charAt(charIdx + 1)) {
                     case 't':
                        ((InternalNode)parent.a).t = current;
                        break;
                     case 'a':
                        ((InternalNode)parent.a).a = current;
                        break;
                     case 'g':
                        ((InternalNode)parent.a).g = current;
                        break;
                     case '$':
                        ((InternalNode)parent.a).dolla = current;
                        break;
                  }
               }
            }
            else if(pathTaken == 't') {
               if(current.getEndIdx() != charIdx) {
                  current.setStartIdx(charIdx + 1);
                  parent.t = new InternalNode(curStartIdx, charIdx);
               }
               ((InternalNode)parent.t).c = new LeafNode(leafLabel, newLeafStartIdx, sequence.length() - 1);

               if(current.getEndIdx() != charIdx) {
                  switch(sequence.charAt(charIdx + 1)) {
                     case 'a':
                        ((InternalNode)parent.t).a = current;
                        break;
                     case 't':
                        ((InternalNode)parent.t).t = current;
                        break;
                     case 'g':
                        ((InternalNode)parent.t).g = current;
                        break;
                     case '$':
                        ((InternalNode)parent.t).dolla = current;
                        break;
                  }
               }
            }
            else if(pathTaken == 'c') {
               if(current.getEndIdx() != charIdx) {
                  current.setStartIdx(charIdx + 1);
                  parent.c = new InternalNode(curStartIdx, charIdx);
               }
               ((InternalNode)parent.c).c = new LeafNode(leafLabel, newLeafStartIdx, sequence.length() - 1);

               if(current.getEndIdx() != charIdx) {
                  switch(sequence.charAt(charIdx + 1)) {
                     case 'a':
                        ((InternalNode)parent.c).a = current;
                        break;
                     case 't':
                        ((InternalNode)parent.c).t = current;
                        break;
                     case 'g':
                        ((InternalNode)parent.c).g = current;
                        break;
                     case '$':
                        ((InternalNode)parent.c).dolla = current;
                        break;
                  }
               }
            }
            else if(pathTaken == 'g') {
               if(current.getEndIdx() != charIdx) {
                  current.setStartIdx(charIdx + 1);
                  parent.g = new InternalNode(curStartIdx, charIdx);
               }
               ((InternalNode)parent.g).c = new LeafNode(leafLabel, newLeafStartIdx, sequence.length() - 1);

               if(current.getEndIdx() != charIdx) {
                  switch(sequence.charAt(charIdx + 1)) {
                     case 'a':
                        ((InternalNode)parent.g).a = current;
                        break;
                     case 't':
                        ((InternalNode)parent.g).t = current;
                        break;
                     case 'g':
                        ((InternalNode)parent.g).g = current;
                        break;
                     case '$':
                        ((InternalNode)parent.g).dolla = current;
                        break;
                  }
               }
            }
            break;
         case 'g':
            if(pathTaken == 'a') {
               if(current.getEndIdx() != charIdx) {
                  current.setStartIdx(charIdx + 1);
                  parent.a = new InternalNode(curStartIdx, charIdx);
               }
               ((InternalNode)parent.a).g = new LeafNode(leafLabel, newLeafStartIdx, sequence.length() - 1);

               if(current.getEndIdx() != charIdx) {
                  switch(sequence.charAt(charIdx + 1)) {
                     case 'a':
                        ((InternalNode)parent.a).a = current;
                        break;
                     case 't':
                        ((InternalNode)parent.a).t = current;
                        break;
                     case 'c':
                        ((InternalNode)parent.a).c = current;
                        break;
                     case '$':
                        ((InternalNode)parent.a).dolla = current;
                        break;
                  }
               }
            }
            else if(pathTaken == 't') {
               if(current.getEndIdx() != charIdx) {
                  current.setStartIdx(charIdx + 1);
                  parent.t = new InternalNode(curStartIdx, charIdx);
               }
               ((InternalNode)parent.t).g = new LeafNode(leafLabel, newLeafStartIdx, sequence.length() - 1);

               if(current.getEndIdx() != charIdx) {
                  switch(sequence.charAt(charIdx + 1)) {
                     case 'a':
                        ((InternalNode)parent.t).a = current;
                        break;
                     case 't':
                        ((InternalNode)parent.t).t = current;
                        break;
                     case 'c':
                        ((InternalNode)parent.t).c = current;
                        break;
                     case '$':
                        ((InternalNode)parent.t).dolla = current;
                        break;
                  }
               }
            }
            else if(pathTaken == 'c') {
               if(current.getEndIdx() != charIdx) {
                  current.setStartIdx(charIdx + 1);
                  parent.c = new InternalNode(curStartIdx, charIdx);
               }
               ((InternalNode)parent.c).g = new LeafNode(leafLabel, newLeafStartIdx, sequence.length() - 1);

               if(current.getEndIdx() != charIdx) {
                  switch(sequence.charAt(charIdx + 1)) {
                     case 'a':
                        ((InternalNode)parent.c).a = current;
                        break;
                     case 't':
                        ((InternalNode)parent.c).t = current;
                        break;
                     case 'c':
                        ((InternalNode)parent.c).c = current;
                        break;
                     case '$':
                        ((InternalNode)parent.c).dolla = current;
                        break;
                  }
               }
            }
            else if(pathTaken == 'g') {
               if(current.getEndIdx() != charIdx) {
                  current.setStartIdx(charIdx + 1);
                  parent.g = new InternalNode(curStartIdx, charIdx);
               }
               ((InternalNode)parent.g).g = new LeafNode(leafLabel, newLeafStartIdx, sequence.length() - 1);

               if(current.getEndIdx() != charIdx) {
                  switch(sequence.charAt(charIdx + 1)) {
                     case 'a':
                        ((InternalNode)parent.g).a = current;
                        break;
                     case 't':
                        ((InternalNode)parent.g).t = current;
                        break;
                     case 'c':
                        ((InternalNode)parent.g).c = current;
                        break;
                     case '$':
                        ((InternalNode)parent.g).dolla = current;
                        break;
                  }
               }
            }
            break;
         case '$':
            if(pathTaken == 'a') {
               if(current.getEndIdx() != charIdx) {
                  current.setStartIdx(charIdx + 1);
                  parent.a = new InternalNode(curStartIdx, charIdx);
               }
               ((InternalNode)parent.a).dolla = new LeafNode(leafLabel, newLeafStartIdx, sequence.length() - 1);

               if(current.getEndIdx() != charIdx) {
                  switch(sequence.charAt(charIdx + 1)) {
                     case 't':
                        ((InternalNode)parent.a).t = current;
                        break;
                     case 'c':
                        ((InternalNode)parent.a).c = current;
                        break;
                     case 'g':
                        ((InternalNode)parent.a).g = current;
                        break;
                     case 'a':
                        ((InternalNode)parent.a).a = current;
                        break;
                  }
               }
            }
            else if(pathTaken == 't') {
               if(current.getEndIdx() != charIdx) {
                  current.setStartIdx(charIdx + 1);
                  parent.t = new InternalNode(curStartIdx, charIdx);
               }
               ((InternalNode)parent.t).dolla = new LeafNode(leafLabel, newLeafStartIdx, sequence.length() - 1);

               if(current.getEndIdx() != charIdx) {
                  switch(sequence.charAt(charIdx + 1)) {
                     case 'a':
                        ((InternalNode)parent.t).a = current;
                        break;
                     case 'c':
                        ((InternalNode)parent.t).c = current;
                        break;
                     case 'g':
                        ((InternalNode)parent.t).g = current;
                        break;
                     case 't':
                        ((InternalNode)parent.t).t = current;
                        break;
                  }
               }
            }
            else if(pathTaken == 'c') {
               if(current.getEndIdx() != charIdx) {
                  current.setStartIdx(charIdx + 1);
                  parent.c = new InternalNode(curStartIdx, charIdx);
               }
               ((InternalNode)parent.c).dolla = new LeafNode(leafLabel, newLeafStartIdx, sequence.length() - 1);

               if(current.getEndIdx() != charIdx) {
                  switch(sequence.charAt(charIdx + 1)) {
                     case 'a':
                        ((InternalNode)parent.c).a = current;
                        break;
                     case 't':
                        ((InternalNode)parent.c).t = current;
                        break;
                     case 'g':
                        ((InternalNode)parent.c).g = current;
                        break;
                     case 'c':
                        ((InternalNode)parent.c).c = current;
                        break;
                  }
               }
            }
            else if(pathTaken == 'g') {
               if(current.getEndIdx() != charIdx) {
                  current.setStartIdx(charIdx + 1);
                  parent.g = new InternalNode(curStartIdx, charIdx);
               }
               ((InternalNode)parent.g).dolla = new LeafNode(leafLabel, newLeafStartIdx, sequence.length() - 1);

               if(current.getEndIdx() != charIdx) {
                  switch(sequence.charAt(charIdx + 1)) {
                     case 'a':
                        ((InternalNode)parent.g).a = current;
                        break;
                     case 't':
                        ((InternalNode)parent.g).t = current;
                        break;
                     case 'c':
                        ((InternalNode)parent.g).c = current;
                        break;
                     case 'g':
                        ((InternalNode)parent.g).g = current;
                        break;
                  }
               }
            }
            break;
      }
   }
   
   int graftCount = 1;

   private class TraverseInfo {
      int indexOfChar;
      String toMatch;
      Node current;
      Node parent;
      char pathTaken;
      int idxOfToMatch;

      public TraverseInfo(int idx, Node cur, Node par, String toMatch, char path, int matchIdx) {
         indexOfChar = idx;
         current = cur;
         parent = par;
         this.toMatch = toMatch;
         pathTaken = path;
         idxOfToMatch = matchIdx;
      }
   }

   private class InternalNode extends Indices implements Node {
      Node a;
      Node t;
      Node c;
      Node g;
      Node dolla;

      public InternalNode(int startIdx, int endIdx) {
         this.startIdx = startIdx;
         this.endIdx = endIdx;
      }

      //'a', 't', 'c', 'g', 'd' for ch representing the child
      public void addChild(char ch, Node child) {
         switch(ch) {
            case('a'):
               a = child;
               break;
            case('t'):
               t = child;
               break;
            case('c'):
               c = child;
               break;
            case('g'):
               g = child;
               break;
            case('d'):
               dolla = child;
               break;
            default:
               System.out.println("Error: Could not add child " + ch + " to internal node");
         }
      }

      public boolean hasChildren() {
         return a != null || t != null || c != null || g != null || dolla != null;
      }
      
      public int getStartIdx() {
         return startIdx;
      }

      public int getEndIdx() {
         return endIdx;
      }

      public void setStartIdx(int idx) {
         startIdx = idx;
      }

      public void setEndIdx(int idx) {
         endIdx = idx;
      }
   }

   private class LeafNode extends Indices implements Node {
      int index;

      public LeafNode(int idx, int startIdx, int endIdx) {
         index = idx;
         this.startIdx = startIdx;
         this.endIdx = endIdx;
      }

      public int getStartIdx() {
         return startIdx;
      }

      public int getEndIdx() {
         return endIdx;
      }

      public void setStartIdx(int idx) {
         startIdx = idx;
      }

      public void setEndIdx(int idx) {
         //Never call this
      }
   }

   private abstract class Indices {
      public int startIdx;
      public int endIdx;
   }
}
