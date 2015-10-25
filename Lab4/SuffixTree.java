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
       //                  A     T      C      G      $
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
         System.out.println("$$$$$$$$");
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
      TraverseInfo traversal;

      for(int i = 0; i < sequence.length(); i++) {
         traversal = traverse(root, null, sequence.substring(i));

         //If root case
         if(traversal.indexOfChar == -1) {
            if(sequence.substring(i).charAt(0) == 'a')
               root.a = new LeafNode(i, i, sequence.length() - 1);
            if(sequence.substring(i).charAt(0) == 't')
               root.t = new LeafNode(i, i, sequence.length() - 1);
            if(sequence.substring(i).charAt(0) == 'c')
               root.c = new LeafNode(i, i, sequence.length() - 1);
            if(sequence.substring(i).charAt(0) == 'g')
               root.g = new LeafNode(i, i, sequence.length() - 1);
            if(sequence.substring(i).charAt(0) == '$')
               root.dolla = new LeafNode(i, i, sequence.length() - 1);
         }
         else {
            graft(traversal, i);
         }
      }
      
      printTree(root);
   }

   //Returns last traveresed parent node and index of last matched char
   private TraverseInfo traverse(Node current, Node parent, String toMatch) {
      int j = current.getStartIdx(), i = 0;
      Node newCur = null;

      if(current.getStartIdx() != -1) {
         for(; j < current.getEndIdx() && i < toMatch.length(); i++, j++) {
            //If should split
            if(toMatch.charAt(i) != sequence.charAt(j)) {
               return new TraverseInfo(j - 1, current, parent, toMatch.substring(i), sequence.charAt(current.getStartIdx()));
            }
         }
      }
      else {
         //Root case
         return new TraverseInfo(-1, null, null, toMatch, '.');
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
         System.out.println("SHOULD NEVER HIT THIS");

      return traverse(newCur, current, toMatch.substring(i));
   }

   //Adding another leaf node to an preexisting leaf node
   private void graft(TraverseInfo data, int leafLabel) {
      InternalNode parent = (InternalNode)data.parent;
      Node current = data.current;
      int charIdx = data.indexOfChar;
      String toMatch = data.toMatch;
      char pathTaken = data.pathTaken;
      int curStartIdx = current.getStartIdx();

      current.setStartIdx(charIdx + 1);

      switch(toMatch.charAt(0)) {
         case 'a':
            if(pathTaken == 'a') {
               parent.a = new InternalNode(curStartIdx, charIdx);
               ((InternalNode)parent.a).a = new LeafNode(leafLabel, charIdx + 1, sequence.length() - 1);

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
            else if(pathTaken == 't') {
               parent.t = new InternalNode(curStartIdx, charIdx);
               ((InternalNode)parent.t).a = new LeafNode(leafLabel, charIdx + 1, sequence.length() - 1);

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
            else if(pathTaken == 'c') {
               parent.c = new InternalNode(curStartIdx, charIdx);
               ((InternalNode)parent.c).a = new LeafNode(leafLabel, charIdx + 1, sequence.length() - 1);

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
            else if(pathTaken == 'g') {
               parent.g = new InternalNode(curStartIdx, charIdx);
               ((InternalNode)parent.g).a = new LeafNode(leafLabel, charIdx + 1, sequence.length() - 1);

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
            break;
         case 't':
            if(pathTaken == 'a') {
               parent.a = new InternalNode(curStartIdx, charIdx);
               ((InternalNode)parent.a).t = new LeafNode(leafLabel, charIdx + 1, sequence.length() - 1);

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
            else if(pathTaken == 't') {
               parent.t = new InternalNode(curStartIdx, charIdx);
               ((InternalNode)parent.t).t = new LeafNode(leafLabel, charIdx + 1, sequence.length() - 1);

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
            else if(pathTaken == 'c') {
               parent.c = new InternalNode(curStartIdx, charIdx);
               ((InternalNode)parent.c).t = new LeafNode(leafLabel, charIdx + 1, sequence.length() - 1);

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
            else if(pathTaken == 'g') {
               parent.g = new InternalNode(curStartIdx, charIdx);
               ((InternalNode)parent.g).t = new LeafNode(leafLabel, charIdx + 1, sequence.length() - 1);

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
            break;
         case 'c':
            if(pathTaken == 'a') {
               parent.a = new InternalNode(curStartIdx, charIdx);
               ((InternalNode)parent.a).c = new LeafNode(leafLabel, charIdx + 1, sequence.length() - 1);

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
            else if(pathTaken == 't') {
               parent.t = new InternalNode(curStartIdx, charIdx);
               ((InternalNode)parent.t).c = new LeafNode(leafLabel, charIdx + 1, sequence.length() - 1);

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
            else if(pathTaken == 'c') {
               parent.c = new InternalNode(curStartIdx, charIdx);
               ((InternalNode)parent.c).c = new LeafNode(leafLabel, charIdx + 1, sequence.length() - 1);

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
            else if(pathTaken == 'g') {
               parent.g = new InternalNode(curStartIdx, charIdx);
               ((InternalNode)parent.g).c = new LeafNode(leafLabel, charIdx + 1, sequence.length() - 1);

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
            break;
         case 'g':
            if(pathTaken == 'a') {
               parent.a = new InternalNode(curStartIdx, charIdx);
               ((InternalNode)parent.a).g = new LeafNode(leafLabel, charIdx + 1, sequence.length() - 1);

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
            else if(pathTaken == 't') {
               parent.t = new InternalNode(curStartIdx, charIdx);
               ((InternalNode)parent.t).g = new LeafNode(leafLabel, charIdx + 1, sequence.length() - 1);

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
            else if(pathTaken == 'c') {
               parent.c = new InternalNode(curStartIdx, charIdx);
               ((InternalNode)parent.c).g = new LeafNode(leafLabel, charIdx + 1, sequence.length() - 1);

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
            else if(pathTaken == 'g') {
               parent.g = new InternalNode(curStartIdx, charIdx);
               ((InternalNode)parent.g).g = new LeafNode(leafLabel, charIdx + 1, sequence.length() - 1);

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
            break;
         case '$':
            if(pathTaken == 'a') {
               parent.a = new InternalNode(curStartIdx, charIdx);
               ((InternalNode)parent.a).dolla = new LeafNode(leafLabel, charIdx + 1, sequence.length() - 1);

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
            else if(pathTaken == 't') {
               parent.t = new InternalNode(curStartIdx, charIdx);
               ((InternalNode)parent.t).dolla = new LeafNode(leafLabel, charIdx + 1, sequence.length() - 1);

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
            else if(pathTaken == 'c') {
               parent.c = new InternalNode(curStartIdx, charIdx);
               ((InternalNode)parent.c).dolla = new LeafNode(leafLabel, charIdx + 1, sequence.length() - 1);

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
            else if(pathTaken == 'g') {
               parent.g = new InternalNode(curStartIdx, charIdx);
               ((InternalNode)parent.g).dolla = new LeafNode(leafLabel, charIdx + 1, sequence.length() - 1);

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
            break;
      }
   }

   private class TraverseInfo {
      int indexOfChar;
      String toMatch;
      Node current;
      Node parent;
      char pathTaken;

      public TraverseInfo(int idx, Node cur, Node par, String toMatch, char path) {
         indexOfChar = idx;
         current = cur;
         parent = par;
         this.toMatch = toMatch;
         this.pathTaken = path;
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
