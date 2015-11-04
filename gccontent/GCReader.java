import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.io.IOException;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.lang.StringBuilder;

public class GCReader {
    int windowWidth;
    int stepSize;

    double[] gcCounts;
    int[] totalCounts;
    int nCount;
    ArrayList<Double> gcPercents;

    Reader fileReader;

    public String ReadGCContent(String content, int windowWidth, int stepSize) throws FileNotFoundException, IOException {
        fileReader = new StringReader(content);
        int chr;

        this.windowWidth = windowWidth;
        this.stepSize = stepSize;
        
        String basePairs = GetBasePairs(fileReader);

        int lenBP = basePairs.length();
        int lenLists = (lenBP / stepSize) + 1;
        gcCounts = new double[lenLists];
        totalCounts = new int[lenLists];
        gcPercents = new ArrayList<Double>();

        calculate(basePairs);
        
        buildCSV(System.getProperty("user.home") + "/Desktop/Pasted");
        return System.getProperty("user.home" ) + "/Desktop/Pasted-Output.csv";
    }

    
    public void ReadGCContent(File file, int windowWidth, int stepSize) throws FileNotFoundException, IOException, Exception {
        fileReader = new FileReader(file);
        int chr;

        //Eating newline at beginning of file
        while ((chr = fileReader.read()) != -1) {
            char basePair = (char) chr;
            if (basePair == '\n')
                break;
        }
        
        this.windowWidth = windowWidth;
        this.stepSize = stepSize;
        

        String basePairs = GetBasePairs(fileReader);

        int lenBP = basePairs.length();
        int lenLists = (lenBP / stepSize) + 1;
        gcCounts = new double[lenLists];
        totalCounts = new int[lenLists];
        gcPercents = new ArrayList<Double>();

        calculate(basePairs);
        buildCSV(file + "");
    }

    private String GetBasePairs(Reader fileReader) throws IOException{
        String returnVal = "";
        int chr;

        while ((chr = fileReader.read()) != -1) {
            char basePair = Character.toUpperCase((char)chr);
            if (basePair == 'A' || basePair == 'C' ||  basePair == 'T' ||  basePair == 'G' ||    basePair == 'N') {
                returnVal += basePair;
            }
        }
        return returnVal;
    }

    /*
     * 01 23 4
     * GG GA A
     * 
     * window 3
     * step   2
     */

    private void incGC(int maxList, int minList) {
        for (int i = minList; i <= maxList; i++) {
            gcCounts[i]++;
            totalCounts[i]++;
            //gcCounts.set(i, gcCounts.get(i) + 1);
            //totalCounts.set(i, totalCounts.get(i) + 1);
        }
    }

    private void incTotal(int maxList, int minList) {
        for (int i = minList; i <= maxList; i++) {
            totalCounts[i]++;
            //totalCounts.set(i, totalCounts.get(i) + 1);
        }
    }

    private void calculate(String basePairs) {
        int listMax = -1, listMin = 0;

        for (int chr = 0; chr < basePairs.length(); chr++) {
            if (chr % stepSize == 0) {
                listMax++;
            }
            if (chr - windowWidth >= 0 && (chr - windowWidth) % stepSize == 0) 
                listMin++;

            switch(basePairs.charAt(chr)) {
                case 'G': case 'C':
                    incGC(listMax, listMin);
                    break;
                case 'A': case 'T':
                    incTotal(listMax, listMin);
                    break;
                case 'N':
                    nCount++;
                    break;
                default:
                    break;
            }
        }
        calculateGCContent(basePairs.length());
    }

    private double calculateGCContent(int finalPos) {
        for (int i = 0; i < gcCounts.length; i++) {
            double gcCount = gcCounts[i];
            double gcContent = Math.round(gcCount / (double) totalCounts[i] * 10000);
            gcContent = gcContent / 100;
            if (totalCounts[i] > 0)
                gcPercents.add(gcContent);
        }
        return 0;
    }

    

    //Builds a CSV file contianing the window positions and GC Counts for the input specified
    private void buildCSV(String filename) throws FileNotFoundException, UnsupportedEncodingException{
        PrintWriter writer = new PrintWriter(filename + "Win-" + windowWidth + ",Step-" + stepSize + "-Output.csv", "UTF-8");

        writer.println("Starting Position of the Window,%GC,TotalNCount");

        for(int i = 0; i < gcPercents.size(); i++) {
            if (i == 0) 
                writer.println((i * stepSize + 1) + "," + gcPercents.get(i) + "," + nCount);
            else 
                writer.println((i * stepSize + 1) + "," + gcPercents.get(i));
        }

        writer.close();
    }
    
    //Builds a CSV file contianing the window positions and GC Counts for the input specified
    private String outputResults() throws FileNotFoundException, UnsupportedEncodingException{
        StringBuilder writer = new StringBuilder();

        writer.append("Starting Position of the Window,%GC,TotalNCount\n");
        
        for(int i = 0; i < gcPercents.size(); i++) {
            String line = (i * stepSize + 1) + "," + gcPercents.get(i) + "\n";
            if (i == 0) 
                line = (i * stepSize + 1) + "," + gcPercents.get(i) + "," + nCount + "\n";
            writer.append(line);
        }

        return writer.toString();
    }
}
