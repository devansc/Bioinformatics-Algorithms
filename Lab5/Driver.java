import java.io.*;

public class Driver {
   public static void main(String[] args) {
      GCReader gcreader = new GCReader();

      try {
         gcreader.ReadGCContent(new File(args[0]));
      }
      catch(Exception e) {
         System.out.println(e);
      }
   }

   public void exportToCSV() {
      
   }
}

