/**
 * Algorithm that scores two DNA sequences by the length of their
 * longest common subsequence (the longest sequence of characters
 * shared by both strings, not necessarily contiguous).
 *
 * A longer common subsequence indicates greater similarity between
 * the two sequences.
 *
 * @author Matt Cantin
 * @version 2026-03-31
 */
public class LongestCommonSubsequence implements SequenceScoringAlgorithm {

    /**
     * Returns the display name of this algorithm.
     * @return the algorithm name
     */
    @Override
    public String getName() {
        return "Longest Common Subsequence";
    }

    /**
     * Computes the length of the longest common subsequence between
     * two DNA sequences using dynamic programming.
     * Unlike the longest common substring, the characters do not need
     * to be contiguous.
     *
     * @param databaseSequence a DNA sequence from the database
     * @param querySequence the query DNA sequence
     * @return the length of the longest common subsequence of both sequences
     */
    @Override
    public double score(String databaseSequence, String querySequence) {
        int len1 = databaseSequence.length();
        int len2 = querySequence.length();

        int[][] dp = new int[len1 + 1][len2 + 1];

        for (int i = 1; i <= len1; i++) {
            for (int j = 1; j <= len2; j++) {
                if (databaseSequence.charAt(i - 1) == querySequence.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        return dp[len1][len2];
    }

    /**
     * Indicates that a higher score represents a better match.
     * @return true because a longer common subsequence means greater similarity
     */
    @Override
    public boolean higherScoreIsBetter() {
        return true;
    }
}
