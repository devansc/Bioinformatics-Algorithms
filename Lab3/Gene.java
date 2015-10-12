
/**
 * Write a description of class Gene here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.ArrayList;

public class Gene
{
    
    private String name;
    private int startCodon;
    private int stopCodon;
    private int geneSize;
    private ArrayList<Exon> exons;
    
    /**
     * Constructor for objects of class Gene
     */
    public Gene(String geneName)
    {
        name = geneName;
        exons = new ArrayList();
    }

    /**
     * Adds a new exon to the list of known exons for this gene
     */
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
