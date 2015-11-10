import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;

public class GCReader {
   ArrayList<Window> windows;
   Reader fileReader;

   public void ReadGCContent(File file) throws Exception {
      fileReader = new FileReader(file);
      int chr;

      //Eating newline at beginning of file
      while((chr = fileReader.read()) != -1) {
         char basePair = (char) chr;
         if (basePair == '\n')
            break;
      }

      windows = new ArrayList<Window>();
      buildWindows(fileReader);
      pruneWindows();
      printWindows();
   }
    
   private void buildWindows(Reader fileReader) throws Exception {
      int chr, i = 0;
      boolean previousC = false, newline;
      char basePair;
      while((chr = fileReader.read()) != -1) {
         windows.add(new Window(i, i + 199));
         basePair = Character.toUpperCase((char)chr);         
         newline = false;

         switch(basePair) {
            case('C'):
               previousC = true;
               incC(i);
               break;
            case('G'):
               if(previousC) {
                  previousC = false;
                  incCpG(i);
               }
               incG(i);
               break;
            case('\n'):
               newline = true;
               break;
         }
         
         if(newline)
            windows.remove(i);
         else
            i++;
      }
      dropExtraWindows(i);
   }

   // Starting from the end and removing all windows that aren't of size 200
   private void dropExtraWindows(int stringSize) {
      int threshold = stringSize - 200;

      if(threshold > 0) {
         for(int i = stringSize - 1; i > threshold; i--)
            windows.remove(i);
      }
   }

   // Starting from the current position and incrementing all C counts of windows
   // containing that character
   private void incC(int idx) {
      for(int count = 0; idx >=0 && count < 200; count++, idx--)
         windows.get(idx).incC();
   }

   // Starting from the current position and incrementing all G counts of windows
   // containing that character
   private void incG(int idx) {
      for(int count = 0; idx >=0 && count < 200; count++, idx--)
         windows.get(idx).incG();
   }

   // Starting from the current position and incrementing all CpG counts of windows
   // containing that character
   private void incCpG(int idx) {
      for(int count = 0; idx >=0 && count < 200; count++, idx--)
         windows.get(idx).incCpG();
   }

   // Removes all windows not satisfying the CpG requirements
   private void pruneWindows() {
      for(int i = 0; i < windows.size(); i++) {
         if(windows.get(i).obsOverExp() < .6 || windows.get(i).gcPercent() < 50)
            windows.remove(i--);
      }
   }

   private void printWindows() {
      for(int i = 0; i < windows.size(); i++) {
         System.out.println("[][][][][][][][][][][]");
         System.out.println("CpG #" + i);
         windows.get(i).printWindow();
      }
   }
}
