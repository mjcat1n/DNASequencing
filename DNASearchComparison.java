import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Demonstrate searching a DNA database with multiple scoring algorithms.
 * If exactly two command-line arguments are provided:
 *   args[0] = query sequence filename
 *   args[1] = database filename in FASTA-like format
 * Otherwise, the program uses hardcoded demo data.
 *
 * Query file behavior:
 * - All whitespace is ignored, including spaces and newlines.
 *
 * Database file behavior:
 * - Each sequence begins with a documentation/label line starting with '>'
 * - That full line after '>' is used as the label
 * - Sequence data may span multiple lines
 * - All whitespace in sequence lines is ignored
 * - This is consistent with FASTA formatting https://en.wikipedia.org/wiki/FASTA_format
 *
 * @author Dr. Jody Paul
 * @version 2026-03-16
 */
public class DNASearchComparison {
    static final SequenceScoringAlgorithm EXAMPLE_ALGORITHM = new ExamplePositionMatchAlgorithm();
    static final SequenceScoringAlgorithm LEVENSHTEIN = new Levenshtein();
    static final SequenceScoringAlgorithm LEVENSHTEINDP = new LevenshteinDP();

    /**
     * Runs the DNA sequence matching comparison.
     * @param args command-line arguments
     *      If exactly two command-line arguments are provided:
     *      args[0] = query sequence filename
     *      args[1] = database filename in FASTA-like format
     *
     * Otherwise, the program uses hardcoded demo data.
     */
    public static void main(String[] args) {
        List<DNASequence> database;
        String query;

        try {
            if (args.length == 2) {
                query = readQueryFromFile(args[0]);
                database = readDatabaseFromFile(args[1]);
            } else {
                database = createHardcodedDatabase();
                query = "ACGTACGA";
            }

            List<SequenceScoringAlgorithm> algorithms = new ArrayList<>();
            algorithms.add(EXAMPLE_ALGORITHM);
            algorithms.add(LEVENSHTEINDP);
            algorithms.add(LEVENSHTEIN);

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

        } catch (IOException e) {
            System.err.println("Error reading input file(s): " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Input format error: " + e.getMessage());
        }
    }

    /**
     * Create a simple hardcoded demo database.
     * @return hardcoded list of DNA sequences
     */
    private static List<DNASequence> createHardcodedDatabase() {
        List<DNASequence> database = new ArrayList<>();
        database.add(new DNASequence("Human_Example", "ACGTACGT"));
        database.add(new DNASequence("Mouse_Example", "ACGTTCGT"));
        database.add(new DNASequence("Fish_Example", "TTTTACGA"));
        return database;
    }

    /**
     * Read a query DNA sequence from a file, ignoring all whitespace.
     *
     * @param filename the query filename
     * @return the normalized query sequence
     * @throws IOException if the file cannot be read
     * @throws IllegalArgumentException if the resulting query is empty
     */
    private static String readQueryFromFile(String filename) throws IOException {
        String content = Files.readString(Path.of(filename));
        String query = removeWhitespace(content);

        if (query.isEmpty()) {
            throw new IllegalArgumentException("Query file contains no sequence data.");
        }

        return query;
    }

    /**
     * Reads a FASTA-like DNA database from a file.
     * Each sequence must be preceded by a line beginning with '>'.
     * The text after '>' is used as the label.
     * Sequence lines may span multiple lines; all whitespace is ignored.
     *
     * @param filename the database filename
     * @return the list of DNA sequences
     * @throws IOException if the file cannot be read
     * @throws IllegalArgumentException if the format is invalid
     */
    private static List<DNASequence> readDatabaseFromFile(String filename) throws IOException {
        List<String> lines = Files.readAllLines(Path.of(filename));
        List<DNASequence> database = new ArrayList<>();
        String currentLabel = null;
        StringBuilder currentSequence = new StringBuilder();

        for (String line : lines) {
            String trimmed = line.trim();
            if (trimmed.isEmpty()) {
                continue;
            }

            if (trimmed.startsWith(">")) {
                if (currentLabel != null) {
                    String sequence = removeWhitespace(currentSequence.toString());
                    if (sequence.isEmpty()) {
                        throw new IllegalArgumentException(
                            "Sequence for label \"" + currentLabel + "\" is empty.");
                    }
                    database.add(new DNASequence(currentLabel, sequence));
                }
                currentLabel = trimmed.substring(1).trim();
                if (currentLabel.isEmpty()) {
                    throw new IllegalArgumentException("Encountered a label line with no label text.");
                }
                currentSequence = new StringBuilder();
            } else {
                if (currentLabel == null) {
                    throw new IllegalArgumentException(
                        "Database sequence data found before any label line beginning with '>'.");
                }
                currentSequence.append(trimmed);
            }
        }

        if (currentLabel != null) {
            String sequence = removeWhitespace(currentSequence.toString());
            if (sequence.isEmpty()) {
                throw new IllegalArgumentException(
                    "Sequence for label \"" + currentLabel + "\" is empty.");
            }
            database.add(new DNASequence(currentLabel, sequence));
        }

        if (database.isEmpty()) {
            throw new IllegalArgumentException("Database file contains no labeled sequences.");
        }

        return database;
    }

    /**
     * Remove all whitespace characters from a string.
     *
     * @param text the input text
     * @return text with all whitespace removed
     */
    private static String removeWhitespace(String text) {
        return text.replaceAll("\\s+", "");
    }
}
