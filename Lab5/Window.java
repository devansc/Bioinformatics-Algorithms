public class Window {
   int start;
   int end;
   int numC;
   int numG;
   int numCpG;
   
   public Window(int start, int end) {
      numC = 0;
      numG = 0;
      numCpG = 0;
      this.start = start;
      this.end = end;
   }
   
   public void incC() {
      numC++;
   }
   
   public void incG() {
      numG++;
   }
   
   public void incCpG() {
      numCpG++;
   }
   
   public double obsOverExp() {
      if(numC == 0 || numG == 0)
         return 0;
      return (numCpG * 200) / (numC * numG);
   }
   
   public double gcPercent() {
      return ((numC + numG) / 200) * 100;
   }
   
   public void printWindow() {
      System.out.println("Start Position: " + start);
      System.out.println("End Position: " + end);
      System.out.println("Obs/Exp: " + obsOverExp());
      System.out.println("%GC: " + gcPercent());
   }
}