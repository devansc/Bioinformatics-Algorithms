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

    ArrayList<Double> gcCounts = new ArrayList<Double>();
    ArrayList<Integer> nCounts = new ArrayList<Integer>();
    ArrayList<Integer> totalCounts = new ArrayList<Integer>();
    ArrayList<Double> gcPercents = new ArrayList<Double>();

    Reader fileReader;

    public String ReadGCContent(String content, int windowWidth, int stepSize) throws FileNotFoundException, IOException {
        fileReader = new StringReader(content);
        int chr;
        
        this.windowWidth = windowWidth;
        this.stepSize = stepSize;
        
        String basePairs = GetBasePairs(fileReader);
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

    private void calculate(String basePairs) throws IOException {
        int chr, startingPos = 0, curPos = 0, windowPos = 0;


        for ( ; startingPos < basePairs.length(); windowPos++) {
            totalCounts.add((int)0);
            gcCounts.add((double)0);
            nCounts.add((int)0);
            for ( ; curPos < startingPos + windowWidth && curPos < basePairs.length() ; curPos++ ){
                switch(basePairs.charAt(curPos)) {
                    case 'G': case 'C':
                        gcCounts.set(windowPos, gcCounts.get(windowPos) + 1);
                        totalCounts.set(windowPos, totalCounts.get(windowPos) + 1);
                        break;
                    case 'A': case 'T':
                        totalCounts.set(windowPos, totalCounts.get(windowPos) + 1);
                        break;
                    case 'N':
                        nCounts.set(windowPos, nCounts.get(windowPos) + 1);
                        break;
                    default:
                        break;
                }
            }
            startingPos += stepSize;
            curPos -= windowWidth - stepSize;
        }
        calculateGCContent(curPos);
    }

    private double calculateGCContent(int finalPos) {
        for (int i = 0; i < gcCounts.size(); i++) {
            double gcCount = (double) gcCounts.get(i);
            double gcContent = Math.round(gcCount / (double) totalCounts.get(i) * 10000);
            gcContent = gcContent / 100;
            gcPercents.add(gcContent);
        }
        return 0;
    }

    
    private int calculateTotalNCount() {
        int totalNCount = 0;
        for (int i = 0; i < nCounts.size(); i++)
            totalNCount += nCounts.get(i);
        return totalNCount;
    }


    //Builds a CSV file contianing the window positions and GC Counts for the input specified
    private void buildCSV(String filename) throws FileNotFoundException, UnsupportedEncodingException{
        PrintWriter writer = new PrintWriter(filename + "-Output.csv", "UTF-8");

        writer.println("Starting Position of the Window,%GC,TotalNCount");

        for(int i = 0; i < gcPercents.size(); i++) {
            if (i == 0) 
                writer.println((i * stepSize + 1) + "," + gcPercents.get(i) + "," + calculateTotalNCount());
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
                line = (i * stepSize + 1) + "," + gcPercents.get(i) + "," + calculateTotalNCount() + "\n";
            writer.append(line);
        }

        return writer.toString();
    }
}
