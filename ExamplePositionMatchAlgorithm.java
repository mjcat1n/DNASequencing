/**
 * A simple example similarity algorithm.
 *
 * This algorithm counts the number of matching characters
 * in corresponding positions up to the length of the shorter sequence.
 *
 * @author Dr. Jody Paul
 * @version 2026-03-10
*/
public class ExamplePositionMatchAlgorithm implements SequenceScoringAlgorithm {

    /**
     * Returns the display name of this algorithm.
     * @return the algorithm name
     */
    @Override
    public String getName() {
        return "Exact Position Matches";
    }

    /**
     * Counts matching positions between two DNA sequences up to the
     * length of the shorter sequence.
     *
     * @param databaseSequence a DNA sequence from the database
     * @param querySequence the query DNA sequence
     * @return the number of matching positions
     */
    @Override
    public double score(String databaseSequence, String querySequence) {
        int minLength = Math.min(databaseSequence.length(), querySequence.length());
        int matches = 0;

        for (int i = 0; i < minLength; i++) {
            if (databaseSequence.charAt(i) == querySequence.charAt(i)) {
                matches++;
            }
        }

        return matches;
    }

    /**
     * Indicate that larger scores are better matches.
     * @return true because more matching positions means a better match
     */
    @Override
    public boolean higherScoreIsBetter() {
        return true;
    }
}
