import java.util.*;

public class SuffixTree {
   private InternalNode root;
   private String sequence;

   public SuffixTree(String dna) {
      root = new InternalNode(-1, -1);
      sequence = dna;
      buildTree();
   }

   public ArrayList findString(String toFind) {
      return new ArrayList();
   }

   private void buildTree() {
      Node child = new LeafNode(1, 0, sequence.length() - 1);
      TraverseInfo traversal;

      root.addChild(sequence.charAt(0), child);

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

      switch(toMatch.charAt(0)) {
         case 'a':
            if(pathTaken == 'a') {
               parent.a = new InternalNode(current.getStartIdx(), charIdx);
               ((InternalNode)parent.a).a = new LeafNode(leafLabel, charIdx + 1, sequence.length() - 1);

               switch(sequence.charAt(charIdx + 1)) {
                  case 't':
                     ((InternalNode)parent.a).t = current;
                     break;
               }
            }
            break;
      }
      current.setStartIdx(charIdx + 1);
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
