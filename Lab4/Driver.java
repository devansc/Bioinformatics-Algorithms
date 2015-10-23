import java.util.*;
import java.io.*;

public class Driver {
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

      SuffixTree tree = new SuffixTree(sequence);
   }
}
