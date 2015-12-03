public class Alignment {
    String sequence;
    String pattern;
    Entry[][] table;
    int gapPenalty;
    int[][] subMatrix;


    public Alignment(String seq, String pat, int gapPen, int[][] subMat) {
        sequence = seq;
        pattern = pat;
        gapPenalty = gapPen;
        subMatrix = subMat;
        //               [rows][columns]
        table = new Entry[pat.length() + 1][seq.length() + 1];
    }


    public void buildTable() {
        // Top left, base case
        table[0][0] = new Entry(0, Direction.NONE);

        for(int i = 1; i <= sequence.length(); i++)
            table[0][i] = new Entry(gapPenalty * i, Direction.LEFT);
        for(int i = 1; i <= pattern.length(); i++)
            table[i][0] = new Entry(gapPenalty * i, Direction.UP);

        for(int i = 1; i <= pattern.length(); i++) {
            for(int j = 1; j <= sequence.length(); j++) {
                Entry replace = new Entry(table[i - 1][j - 1].getScore() + score(pattern.charAt(i), sequence.charAt(j)), Direction.DIAG);
                Entry insert = new Entry(table[i][j - 1].getScore() + gapPenalty, Direction.UP);
                Entry delete = new Entry(table[i - 1][j].getScore() + gapPenalty, Direction.LEFT);

                table[i][j] = getMaxEntry(replace, insert, delete);
            }
        }
    }

    
    private int score(char patChar, char seqChar) {
       return subMatrix[translateScore(patChar)][translateScore(seqChar)];
    }
    
    
    private Entry getMaxEntry(Entry replace, Entry insert, Entry delete) {
        int max = Math.max(replace.getScore(), Math.max(insert.getScore(), delete.getScore()));

        if(max == replace.getScore())
            return replace;
        else if(max == insert.getScore())
            return insert;
        else
            return delete;
    }
    

    private class Entry {
        int score;
        Direction direction;

        public Entry(int scor, Direction dir) {
            score = scor;
            direction = dir;
        }

        public int getScore() {
            return score;
        }
    }


    public enum Direction {
        UP, LEFT, DIAG, NONE
    }
    
    
    private int translateScore(char c) {
       String lazy = "ARNDCQEGHILKMFPSTWYV";
       for(int i = 0; i < lazy.length(); i++) {
          if(c == lazy.charAt(i))
             return i;
       }
       return -1;
    }
}
