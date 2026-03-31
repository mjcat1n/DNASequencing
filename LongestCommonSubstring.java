/**
 * Algorithm that scores two DNA sequences by the length of their
 * longest common substring (a contiguous sequence of characters
 * shared by both strings).
 *
 * A longer common substring indicates greater similarity between
 * the two sequences.
 *
 * @author cantinmatt
 * @version 2026-03-31
 */
public class LongestCommonSubstring implements SequenceScoringAlgorithm {

    /**
     * Returns the display name of this algorithm.
     * @return the algorithm name
     */
    @Override
    public String getName() {
        return "Longest Common Substring";
    }

    /**
     * Computes the length of the longest common substring between
     * two DNA sequences using dynamic programming.
     *
     * @param databaseSequence a DNA sequence from the database
     * @param querySequence the query DNA sequence
     * @return the length of the longest contiguous substring shared by both sequences
     */
    @Override
    public double score(String databaseSequence, String querySequence) {
        int len1 = databaseSequence.length();
        int len2 = querySequence.length();
        int longest = 0;

        int[][] dp = new int[len1 + 1][len2 + 1];

        for (int i = 1; i <= len1; i++) {
            for (int j = 1; j <= len2; j++) {
                if (databaseSequence.charAt(i - 1) == querySequence.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                    if (dp[i][j] > longest) {
                        longest = dp[i][j];
                    }
                } else {
                    dp[i][j] = 0;
                }
            }
        }

        return longest;
    }

    /**
     * Indicates that a higher score represents a better match.
     * @return true because a longer common substring means greater similarity
     */
    @Override
    public boolean higherScoreIsBetter() {
        return true;
    }
}
