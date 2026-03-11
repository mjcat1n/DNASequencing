/**
 * Defines the behavior for a DNA sequence scoring algorithm.
 *
 * Implementations may represent either similarity or distance metrics.
 * The higherScoreIsBetter method tells the rest of the system how to
 * interpret the score.
 *
 * @author Dr. Jody Paul
 * @version 2026-03-10
 */
public interface SequenceScoringAlgorithm {

    /**
     * Returns the display name of this algorithm.
     * @return the algorithm name
     */
    String getName();

    /**
     * Computes a score between a database sequence and a query sequence.
     *
     * @param databaseSequence a DNA sequence from the database
     * @param querySequence the query DNA sequence
     * @return the score assigned by this algorithm
     */
    double score(String databaseSequence, String querySequence);

    /**
     * Indicates whether larger scores are better matches.
     * @return true if larger scores are better; false if smaller scores are better
     */
    boolean higherScoreIsBetter();
}
