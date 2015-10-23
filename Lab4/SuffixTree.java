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

      }
   }

   //Returns last traveresed parent node and index of last matched char
   private TraverseInfo traverse(Node current, Node parent, String toMatch) {
      int j = current.startIdx, i;

      if(current.startIdx != -1) {
         for(i = 0; j < current.endIdx && i < toMatch.length; i++, j++) {
            //If should split
            if(toMatch.charAt(i) != sequence.charAt(j) {
               return new TraverseInfo(j - 1, current, parent);
            }
         }
      }

      if(current instanceOf InternalNode) {
         if(toMatch.charAt(i) == 'a' && current.a) {
            traverse(current.a, toMatch.substring(i));
         }
      }

      TraverseInfo data;

      return data;
   }

   //Adding another leaf node to an preexisting leaf node
   private void graft() {
      
   }

   private class TraverseInfo {
      int indexOfChar;
      Node current;
      Node parent;

      public TraverseInfo(int idx, Node cur, Node par) {
         indexOfChar = idx;
         current = cur;
         parent = par;
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
   }

   private class LeafNode extends Indices implements Node {
      int index;

      public LeafNode(int idx, int startIdx, int endIdx) {
         index = idx;
	 this.startIdx = startIdx;
         this.endIdx = endIdx;
      }
   }

   private abstract class Indices {
      public int startIdx;
      public int endIdx;
   }
}
