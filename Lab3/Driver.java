/**
 * Main Driver class for Lab 3
 * 
 * @author Team 5
 */
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Driver
{
    // Collection of every gene found in the given GTF file
    private static ArrayList<Gene> genes;
    private static Scanner in;
    private static File fasta;
    private static File gtf;

    public Driver(File fasta, File gtf) {
        this.fasta = fasta;
        this.gtf = gtf;
    }

    public void start() throws IOException {
        int sequenceLength;
        genes = new ArrayList();
        in =  new Scanner(gtf);
        
        sequenceLength = getSeqLength();    
        
        System.out.println("sequenceLength is " + sequenceLength + "\n");
        
        processGTF(in);
        
        /*
        System.out.println("\n\nSummary of genes");
        System.out.println("****************************\n");
        
        for (int i = 0; i < genes.size(); i++)
            genes.get(i).printGene();
        */

        double totalGeneSize = totalGeneSize();
        //System.out.println("Average gene size " + );
        double totalExonSize = totalExonSize();
        //System.out.println("Average exon size " + );
        //System.out.println("Relative exon coverage " +  + "%");
        buildCSV(averageGeneSize(totalGeneSize), (int)totalGeneSize, averageExonSize(totalExonSize), (int)totalExonSize, round2(100 * totalExonSize / sequenceLength), sequenceLength);
    }

    //Builds a CSV file contianing the window positions and GC Counts for the input specified
    private void buildCSV(double avgGeneSize, int sizeAllGenes, double avgExonSize, int sizeAllExons, double relExonCoverage, int totalLength) throws FileNotFoundException, UnsupportedEncodingException{
        PrintWriter writer = new PrintWriter("lab3-Output.csv", "UTF-8");
        writer.println("Average gene size,Size of all genes,Average exon size,Size of all exons,Relative exon coverage (%),Total length of DNA");
        writer.printf(avgGeneSize + ","  + sizeAllGenes + "," + avgExonSize + "," + sizeAllExons + "," + relExonCoverage + "," + totalLength);

        writer.close();
    }

    /**
     * Walks through the entire FASTA file to figure out the total
     *     length of the input DNA sequence
     */
    private static int getSeqLength() throws FileNotFoundException, IOException {
        FileReader fileIn = new FileReader(fasta);
        int count = 0, i = 0;

        while (fileIn.ready() && fileIn.read() != '\n')
            ;
            
        while (fileIn.ready()) {
            if (fileIn.read() != '\n')
                count++;
        }
            
        return count;
    }
    
    /**
     * Processes the given GTF file, creating the corresponding genes in the process
     */
    private static void processGTF(Scanner in) {
        String[] currLine;
        String currGName = null;
        String currGRoot = null;
        String tempName, tempRoot;
        Gene currGene = null;
        
        while (in.hasNextLine()) {                        
            currLine = in.nextLine().split("\\s", 9);
            tempName = currLine[8].split(" ")[1].replace("\"", "").replace(";", "");
            tempRoot = tempName.substring(0, tempName.lastIndexOf("-"));
            
            // Checks to see if we are now looking at a completely
            //     new gene, not just a new splice-variant
            if (!tempRoot.equals(currGRoot)) {
                currGene = new Gene(tempName);
                currGRoot = tempRoot;
                currGName = tempName;
                genes.add(currGene);
            }            
            
            // Checks to see if we are still looking at the same
            //     gene and splice-variant
            if (tempName.equals(currGName)) {
                switch (currLine[2]) {
                    case "start_codon":
                        currGene.setStart(Integer.valueOf(currLine[3]));
                        break;
                    case "stop_codon":
                        currGene.setStop(Integer.valueOf(currLine[3]));
                        break;
                    case "exon":
                        currGene.newExon(new Exon(Integer.valueOf(currLine[3]), Integer.valueOf(currLine[4])));
                        break;
                }
            }        
        }
    }


    private double round2(double orig) {
        double rounded = Math.round(orig * 100);
        rounded = rounded / 100;
        return rounded;
    }
    
    /**
     * Calculates the average gene size for the input DNA sequence
     */
    private double averageGeneSize(double totalGeneSize) {
        return round2(totalGeneSize / genes.size());
    }
    
    private double totalGeneSize() {
        double sum = 0;

        for (int i = 0; i < genes.size(); i++)
            genes.get(i).calculateGeneSize();

        for (int i = 0; i < genes.size(); i++)
            sum += genes.get(i).getSize();

        return sum;
    }
    
    /**
     * Calculates the average exon size for the input DNA sequence
     */
    private double averageExonSize(double totalExonSize) {
        double numExons = 0;
        for (int i = 0; i < genes.size(); i++) {
            numExons += genes.get(i).getExons().size();
        }
        //System.out.println("Total Exon size " + totalExonSize + " num exons " + numExons);
        return round2(totalExonSize / numExons);  
    }

    private double totalExonSize() {
        double totalExonSize = 0;
        for (int i = 0; i < genes.size(); i++) {
            ArrayList<Exon> geneExons = genes.get(i).getExons();
            for (int j = 0; j < geneExons.size(); j++) {
                totalExonSize += geneExons.get(j).getStop() - geneExons.get(j).getStart();
            }
        }
        return totalExonSize;
    }
    
}
