import java.util.Objects;

/**
 * Store the result of a sequence search.
 * @author Dr. Jody Paul
 * @version 2026-03-10
 */
public class MatchResult {
    private final String algorithmName;
    private final DNASequence bestSequence;
    private final double score;

    /**
     * Creates a match result.
     *
     * @param algorithmName the name of the algorithm used
     * @param bestSequence the best matching sequence
     * @param score the score assigned to the best match
     */
    public MatchResult(String algorithmName, DNASequence bestSequence, double score) {
        this.algorithmName = Objects.requireNonNull(algorithmName, "algorithmName must not be null");
        this.bestSequence = Objects.requireNonNull(bestSequence, "bestSequence must not be null");
        this.score = score;
    }

    /**
     * Returns the name of the algorithm used.
     *
     * @return the algorithm name
     */
    public String getAlgorithmName() {
        return algorithmName;
    }

    /**
     * Returns the best matching sequence.
     *
     * @return the best matching DNA sequence
     */
    public DNASequence getBestSequence() {
        return bestSequence;
    }

    /**
     * Returns the score of the best matching sequence.
     *
     * @return the best score
     */
    public double getScore() {
        return score;
    }
}
