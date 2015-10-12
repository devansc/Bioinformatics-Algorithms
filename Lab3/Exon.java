
/**
 * Write a description of class Exon here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Exon
{
    // instance variables - replace the example below with your own
    private int startLoc;
    private int stopLoc;

    /**
     * Constructor for objects of class Exon
     */
    public Exon(int start, int stop) {
        startLoc = start;
        stopLoc = stop;
    }

    /**
     * An example of a method - replace this comment with your own
     * 
     * @param  y   a sample parameter for a method
     * @return     the sum of x and y 
     */
    public int getStart() {
        return startLoc;
    }
    
    public int getStop() {
        return stopLoc;
    }
    
    public String toString() {
        return "Start: " + startLoc + "\tStop: " + stopLoc;
    }
}
