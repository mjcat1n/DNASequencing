/**
 * Algorithm that scores two DNA sequences using Levenshtein edit distance.
 * The edit distance counts the minimum number of single-character insertions,
 * deletions, or substitutions needed to transform one sequence into the other.
 *
 * A smaller distance indicates greater similarity between the two sequences.
 *
 * @author cantinmatt
 * @version 2026-03-31
 */
public class Levenshtein implements SequenceScoringAlgorithm {
    public String getName() {
        return "Levenshtein Distance";
    }

    public double score(String sequence1, String sequence2) {
        int len1 = sequence1.length();
        int len2 = sequence2.length();

        // Create a matrix to store distances
        int[][] dp = new int[len1 + 1][len2 + 1];

        // Initialize the first row and column
        for (int i = 0; i <= len1; i++) {
            dp[i][0] = i;
        }
        for (int j = 0; j <= len2; j++) {
            dp[0][j] = j;
        }

        // Compute the Levenshtein distance
        for (int i = 1; i <= len1; i++) {
            for (int j = 1; j <= len2; j++) {
                int cost = (sequence1.charAt(i - 1) == sequence2.charAt(j - 1)) ? 0 : 1;
                dp[i][j] = Math.min(Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1), dp[i - 1][j - 1] + cost);
            }
        }

        return dp[len1][len2];
    }

    public boolean higherScoreIsBetter() {
        return false; // Lower distance means a better match
    }
}