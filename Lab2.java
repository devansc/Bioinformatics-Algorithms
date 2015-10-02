import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;
import java.io.FileReader;

public class Lab2 {
    public static void main(String[] args) throws FileNotFoundException, IOException {
        FileReader fileReader = new FileReader(new File(args[0]));
        int chr;
        while ((chr = fileReader.read()) != -1) {
            char basePair = (char) chr;
            if (basePair == '\n')
                break;
        }

        double totalBasePairs = 0, gcCount = 0;
        while ((chr = fileReader.read()) != -1) {
            char basePair = (char) chr;
            if (basePair == 'A' || basePair == 'T' || basePair == 'G' || basePair == 'C') {
                if (basePair == 'G' || basePair == 'C')
                    gcCount++;
                totalBasePairs++;
            }
        }

        System.out.println("total pairs: " + totalBasePairs);
        System.out.println("total GC pairs: " + gcCount);

        double gcContent = Math.round(gcCount / totalBasePairs * 10000);
        gcContent = gcContent / 100;
        System.out.println("Found GC-Content of " + gcContent + "%");
    }
}
