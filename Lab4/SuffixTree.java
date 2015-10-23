import java.util.*;

public class SuffixTree {
   private InternalNode root;
   
   public ArrayList findString(String toFind) {
      return new ArrayList();
   }

   public void buildTree(String dna) {
      Node child = new LeafNode(1, 0, dna.length() - 1);

      root.addChild(dna.charAt('0'), child);
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
      int startIdx;
      int endIdx;
   }
}
