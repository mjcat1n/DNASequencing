import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Run multiple sequence scoring algorithms against the same query and database.
 * @author Dr. Jody Paul
 * @version 2026-03-10
 */
public class AlgorithmComparisonManager {
    private final List<SequenceScoringAlgorithm> algorithms;

    /**
     * Create a manager for a list of algorithms.
     * @param algorithms the algorithms to run
     */
    public AlgorithmComparisonManager(List<SequenceScoringAlgorithm> algorithms) {
        Objects.requireNonNull(algorithms, "algorithms must not be null");
        if (algorithms.isEmpty()) {
            throw new IllegalArgumentException("algorithms must not be empty");
        }
        this.algorithms = new ArrayList<>(algorithms);
    }

    /**
     * Run all algorithms against the given query and database.
     * @param querySequence the query DNA sequence
     * @param database the database of labeled DNA sequences
     * @return a list of match results, one per algorithm
     */
    public List<MatchResult> compareAll(String querySequence, List<DNASequence> database) {
        List<MatchResult> results = new ArrayList<>();
        for (SequenceScoringAlgorithm algorithm : algorithms) {
            SequenceMatcher matcher = new SequenceMatcher(algorithm);
            MatchResult result = matcher.findBestMatch(querySequence, database);
            results.add(result);
        }
        return results;
    }

    /**
     * Return a defensive copy of the registered algorithms.
     * @return the registered algorithms
     */
    public List<SequenceScoringAlgorithm> getAlgorithms() {
        return new ArrayList<>(algorithms);
    }
}
