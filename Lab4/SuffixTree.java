public class SuffixTree {
   private InternalNode root;
   
   public ArrayList findString(String toFind) {
      
   }

   public void buildTree(String dna) {
      
   }

   private class InternalNode implements Node extends Indices {
      Node a;
      Node t;
      Node c;
      Node g;
      Node dolla;
   }

   private class LeafNode implements Node extends Indices {
      int index;
   }

   private abstract class Indices {
      int startIdx;
      int endIdx;
   }
}
