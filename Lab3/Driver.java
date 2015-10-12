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

    public static void main(String[] args) throws FileNotFoundException, IOException {
        int sequenceLength;
        
        genes = new ArrayList();
        fasta = new File("Dere3L1_FASTA.txt");
        gtf = new File("Dere3L1_gtf.txt");
        in =  new Scanner(gtf);
        
        sequenceLength = getSeqLength();    
        
        System.out.println("sequenceLength is " + sequenceLength + "\n");
        
        processGTF(in);
        
        System.out.println("\n\nSummary of genes");
        System.out.println("****************************\n");
        
        for (int i = 0; i < genes.size(); i++)
            genes.get(i).printGene();
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
    
    /**
     * Calculates the average gene size for the input DNA sequence
     */
    private double averageGeneSize() {
        return 0.0;
    }
    
    /**
     * Calculates the average exon size for the input DNA sequence
     */
    private double averageExonSize() {
        return 0.0;
    }
    
}
