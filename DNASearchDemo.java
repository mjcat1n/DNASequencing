import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Demonstrate searching a DNA database with multiple scoring algorithms.
 * @author Dr. Jody Paul
 * @version 2026-03-10a
 */
public class DNASearchDemo {
    static final SequenceScoringAlgorithm EXAMPLE_ALGORITHM = new ExamplePositionMatchAlgorithm();
    static final SequenceScoringAlgorithm LEVENSHTEIN_ALGORITHM = new Levenshtein();
    /**
     * Runs the example program.
     *
     * @param args command-line arguments; not used in this demo
     */
    public static void main(String[] args) {
        List<DNASequence> database = new ArrayList<>();

        database.add(new DNASequence("Human_Example", "ACGTACGT"));
        database.add(new DNASequence("Mouse_Example", "ACGTTCGT"));
        database.add(new DNASequence("Fish_Example", "TTTTACGA"));

        String query = "ACGTACGA";

        List<SequenceScoringAlgorithm> algorithms = new ArrayList<>();
        algorithms.add(EXAMPLE_ALGORITHM);
        algorithms.add(LEVENSHTEIN_ALGORITHM);
        AlgorithmComparisonManager manager = new AlgorithmComparisonManager(algorithms);
        List<MatchResult> results = manager.compareAll(query, database);

        System.out.println("Query sequence: " + query);
        System.out.println();

        for (MatchResult result : results) {
            System.out.println("Algorithm: " + result.getAlgorithmName());
            System.out.println("Best match label: " + result.getBestSequence().getLabel());
            System.out.println("Best match sequence: " + result.getBestSequence().getSequence());
            System.out.println("Score: " + result.getScore());
            System.out.println();
        }
    }

    public static String choose(File f) throws FileNotFoundException {
        String result = null;
        Random rand = new Random();
        int n = 0;
        for(Scanner sc = new Scanner(f); sc.hasNext(); )
        {
            ++n;
            String line = sc.nextLine();
            if(rand.nextInt(n) == 0)
            result = line;         
        }

        return result;      
    }
}