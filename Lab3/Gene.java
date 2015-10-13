
/**
 * Lab 3
 *
 * @author Team 5
 */

import java.util.ArrayList;

public class Gene
{
    
    private String name;
    private int startCodon;
    private int stopCodon;
    private int geneSize;
    private ArrayList<Exon> exons;
    
    public Gene(String geneName)
    {
        name = geneName;
        exons = new ArrayList();
    }

    public void newExon(Exon toAdd) {
        exons.add(toAdd);
    }
    
    /**
     * Prints out the name of the gene, the start and stop codon locations,
     *     each exon contained within (and its respective start/stop locations),
     *     and the total size of the gene.
     */
    public void printGene() {
        
        System.out.println("\nInformation for gene " + name + ":\n");
        System.out.println("\tStart codon: " +  startCodon + "\t\tStop codon: " + stopCodon + "\n");
        for (int i = 0; i < exons.size(); i++) {
            System.out.println("\t\tExon " + i + ": " + exons.get(i).toString());
        }
    }

    public void calculateGeneSize() {
        int lastBP;
        int firstBP;
        if (stopCodon < startCodon) { // backwards
            lastBP = exons.get(0).getStart() + 3;
            firstBP = exons.get(exons.size() - 1).getStop();
        } else { 
            lastBP = exons.get(exons.size() - 1).getStop() - 3;
            firstBP = exons.get(0).getStart();
        }
        geneSize = Math.abs(lastBP - firstBP);
    }
    
    /**
     * Returns the list of exons contained within this gene
     */
    public ArrayList<Exon> getExons() {
        return exons;
    }
    
    public void setStart(int start) {
        startCodon = start;
    }
    
    public void setStop(int stop) {
        stopCodon = stop;
    }
    
    public String getName() {
        return name;
    }
    
    public int getSize() {
        return geneSize;
    }
    
    public int getStart() {
        return startCodon;
    }
    
    public int getStop() {
        return stopCodon;
    }
}
