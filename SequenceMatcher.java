import java.util.List;
import java.util.Objects;

/**
 * Searches a database of DNA sequences using a provided scoring algorithm.
 * @author Dr. Jody Paul
 * @version 2026-03-10
 */
public class SequenceMatcher {
    private final SequenceScoringAlgorithm algorithm;

    /**
     * Creates a matcher that uses the given scoring algorithm.
     *
     * @param algorithm the scoring algorithm to use
     */
    public SequenceMatcher(SequenceScoringAlgorithm algorithm) {
        this.algorithm = Objects.requireNonNull(algorithm, "algorithm must not be null");
    }

    /**
     * Finds the best matching sequence in the database for the given query.
     *
     * @param querySequence the query DNA sequence
     * @param database the database of labeled DNA sequences
     * @return a MatchResult containing the best match and its score
     */
    public MatchResult findBestMatch(String querySequence, List<DNASequence> database) {
        Objects.requireNonNull(querySequence, "querySequence must not be null");
        Objects.requireNonNull(database, "database must not be null");

        if (database.isEmpty()) {
            throw new IllegalArgumentException("database must not be empty");
        }

        String normalizedQuery = DNASequence.normalizeAndValidate(querySequence);

        double bestScore = algorithm.higherScoreIsBetter()
                ? Double.NEGATIVE_INFINITY
                : Double.POSITIVE_INFINITY;

        DNASequence bestSequence = null;

        for (DNASequence currentSequence : database) {
            double currentScore = algorithm.score(currentSequence.getSequence(), normalizedQuery);

            if (isBetterScore(currentScore, bestScore)) {
                bestScore = currentScore;
                bestSequence = currentSequence;
            }
        }

        return new MatchResult(algorithm.getName(), bestSequence, bestScore);
    }

    /**
     * Determines whether a candidate score is better than the current best score.
     *
     * @param candidateScore the candidate score
     * @param bestScore the current best score
     * @return true if the candidate score is better; false otherwise
     */
    private boolean isBetterScore(double candidateScore, double bestScore) {
        if (algorithm.higherScoreIsBetter()) {
            return candidateScore > bestScore;
        }

        return candidateScore < bestScore;
    }
}