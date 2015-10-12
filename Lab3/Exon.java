
/**
 * Lab 3
 * 
 * @author Team 5 
 */
public class Exon
{
    private int startLoc;
    private int stopLoc;

    public Exon(int start, int stop) {
        startLoc = start;
        stopLoc = stop;
    }

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
