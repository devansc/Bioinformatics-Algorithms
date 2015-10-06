package gccontent;

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
    int nCount = 0;

    ArrayList<Double> gcCounts = new ArrayList<Double>();
    ArrayList<Double> gcPercents = new ArrayList<Double>();

    Reader fileReader;

    public String ReadGCContent(String content, int windowWidth, int stepSize) throws FileNotFoundException, IOException {
        fileReader = new StringReader(content);
        int chr;
        
        this.windowWidth = windowWidth;
        this.stepSize = stepSize;
        
        calculate();
        return outputResults();
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
        
        calculate();
        buildCSV(file + "");
    }


    private void calculate() throws IOException {
        int chr, position = 0, nCount = 0;

        while ((chr = fileReader.read()) != -1) {
            char basePair = (char) chr;
            switch(basePair) {
                case 'G':
                    prep_gc_count(position);
                    increment_gc_count(position);
                    position++;
                    break;
                case 'C':
                    prep_gc_count(position);
                    increment_gc_count(position);
                    position++;
                    break;
                case 'A':
                    prep_gc_count(position);
                    position++;
                    break;
                case 'T':
                    prep_gc_count(position);
                    position++;
                    break;
                case 'N':
                    prep_gc_count(position);
                    nCount++;
                    position++;
                    break;
                default:
                    break;
            }
        }
        for (int i = 0; i < gcCounts.size(); i++) {
            //System.out.printf("Window %d gc-count %f\n", i, gcCounts.get(i));
        }
        calculateGCContent(position);
    }

    private double calculateGCContent(int finalPos) {
        for (int i = 0; i < gcCounts.size(); i++) {
            double gcCount = (double) gcCounts.get(i);
            if (stepSize * i + windowWidth >= finalPos) {
                //System.out.println("i " + i + " finalPos " + finalPos);
                int lenWindow = finalPos - stepSize * i;
                double gcContent = Math.round(gcCount / lenWindow * 10000);
                //System.out.println("len window " + i + " is " + lenWindow);
                gcContent = gcContent / 100;
                gcPercents.add(gcContent);
            } else {
                double gcContent = Math.round(gcCount / windowWidth * 10000);
                //System.out.println("len window " + i + " is " + windowWidth);
                gcContent = gcContent / 100;
                gcPercents.add(gcContent);
            }
            //System.out.printf("\n\nWindow %d gc-content %f\n", i, gcPercents.get(i));
        }
        return 0;
    }

    private void prep_gc_count(int position) {
        int limit = position / stepSize;
        while (gcCounts.size () <= limit) {
            gcCounts.add((double)0);
        }
    }

    private void increment_gc_count(int position) {
        int limit = position / stepSize;
        int numIncrements = windowWidth / stepSize; 
        //System.out.println("incrementing gc count position " + position);

        while (limit >= 0 && numIncrements-- > 0) {
            //System.out.println("incrementing window " + limit);
            gcCounts.set(limit, gcCounts.get(limit) + 1);      
            limit--;
        }
    }


    //Builds a CSV file contianing the window positions and GC Counts for the input specified
    private void buildCSV(String filename) throws FileNotFoundException, UnsupportedEncodingException{
        PrintWriter writer = new PrintWriter(filename + "-Output.csv", "UTF-8");

        writer.println("Starting Position of the Window,%GC");

        for(int i = 0; i < gcPercents.size(); i++)
            writer.println((i * stepSize + 1) + "," + gcPercents.get(i));

        writer.close();
    }
    
    //Builds a CSV file contianing the window positions and GC Counts for the input specified
    private String outputResults() throws FileNotFoundException, UnsupportedEncodingException{
        StringBuilder writer = new StringBuilder();

        writer.append("Starting Position of the Window,%GC\n");
        
        for(int i = 0; i < gcPercents.size(); i++) {
            String line = (i * stepSize + 1) + "," + gcPercents.get(i) + "\n";
            writer.append(line);
        }

        return writer.toString();
    }
}
