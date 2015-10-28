import java.util.*;
import java.io.*;

public class Driver {
   private static SuffixTree tree;
   
   public static void main(String[] args) {
      String sequence = "";
      Scanner scan = null;

      try {
         scan = new Scanner(new File(args[0]));
      }
      catch(FileNotFoundException e) {
         System.out.println(e);
      }

      //Eating first line of FASTA file
      scan.nextLine();

      //Constructing DNA sequence String from file
      while(scan.hasNextLine())
         sequence += scan.nextLine();

      tree = new SuffixTree(sequence + "$");
      
      tree.reset();
      findString("TCTAGG");
   }
   
   public static void findString(String toMatch) {
      ArrayList indices = tree.findString(toMatch.toLowerCase(), tree.getRoot());
      
      if(indices == null)
         System.out.println("Substring not found");
      
      System.out.println(indices);
   }
   
   public void exportToCSV() {
      
   }
}
