import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
// import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Tests for the SequenceMatcher class.
 *
 * @author  Dr. Jody Paul
 * @version 2026-03-10
 */
public class SequenceMatcherTest {

    /**
     * Verifies that the matcher returns the best match when higher scores are better.
     */
    @Test
    public void findBestMatch_ShouldReturnBestMatch_WhenHigherScoreIsBetter() {
        List<DNASequence> database = Arrays.asList(
            new DNASequence("Seq1", "ACGT"),
            new DNASequence("Seq2", "ACGA"),
            new DNASequence("Seq3", "TTTT")
        );

        SequenceMatcher matcher = new SequenceMatcher(new ExamplePositionMatchAlgorithm());

        MatchResult result = matcher.findBestMatch("ACGA", database);

        assertEquals("Exact Position Matches", result.getAlgorithmName());
        assertEquals("Seq2", result.getBestSequence().getLabel());
        assertEquals("ACGA", result.getBestSequence().getSequence());
        assertEquals(4.0, result.getScore());
    }

    /**
     * Verifies that the matcher returns the best match when lower scores are better.
     */
    @Test
    public void findBestMatch_ShouldReturnBestMatch_WhenLowerScoreIsBetter() {
        List<DNASequence> database = Arrays.asList(
            new DNASequence("Seq1", "ACGT"),
            new DNASequence("Seq2", "ACGTA"),
            new DNASequence("Seq3", "AC")
        );

        SequenceScoringAlgorithm algorithm = new SequenceScoringAlgorithm() {

            /**
             * Returns the display name of this algorithm.
             *
             * @return the algorithm name
             */
            @Override
            public String getName() {
                return "Length Difference";
            }

            /**
             * Computes the absolute difference in sequence lengths.
             *
             * @param databaseSequence a DNA sequence from the database
             * @param querySequence the query DNA sequence
             * @return the absolute difference in length
             */
            @Override
            public double score(String databaseSequence, String querySequence) {
                return Math.abs(databaseSequence.length() - querySequence.length());
            }

            /**
             * Indicates whether higher scores are better.
             *
             * @return false because smaller differences are better
             */
            @Override
            public boolean higherScoreIsBetter() {
                return false;
            }
        };

        SequenceMatcher matcher = new SequenceMatcher(algorithm);

        MatchResult result = matcher.findBestMatch("ACGTA", database);

        assertEquals("Length Difference", result.getAlgorithmName());
        assertEquals("Seq2", result.getBestSequence().getLabel());
        assertEquals(0.0, result.getScore());
    }

    /**
     * Verifies that sequence normalization converts lowercase input to uppercase.
     */
    @Test
    public void normalizeAndValidate_ShouldConvertToUppercase() {
        String normalized = DNASequence.normalizeAndValidate(" acgt ");

        assertEquals("ACGT", normalized);
    }

    /**
     * Verifies that invalid DNA characters are rejected.
     */
    @Test
    public void normalizeAndValidate_ShouldThrowForInvalidCharacter() {
        assertThrows(IllegalArgumentException.class, () -> {
            DNASequence.normalizeAndValidate("ACGX");
        });
    }

    /**
     * Verifies that an empty database causes an exception.
     */
    @Test
    public void findBestMatch_ShouldThrowForEmptyDatabase() {
        SequenceMatcher matcher = new SequenceMatcher(new ExamplePositionMatchAlgorithm());

        assertThrows(IllegalArgumentException.class, () -> {
            matcher.findBestMatch("ACGT", List.of());
        });
    }

    /**
     * Verifies that an invalid query sequence causes an exception.
     */
    @Test
    public void findBestMatch_ShouldThrowForInvalidQuery() {
        List<DNASequence> database = Arrays.asList(
            new DNASequence("Seq1", "ACGT")
        );

        SequenceMatcher matcher = new SequenceMatcher(new ExamplePositionMatchAlgorithm());

        assertThrows(IllegalArgumentException.class, () -> {
            matcher.findBestMatch("ACGX", database);
        });
    }

    /**
     * Verifies that the example algorithm counts matching positions correctly.
     */
    @Test
    public void score_ShouldCountMatchingPositions() {
        SequenceScoringAlgorithm algorithm = new ExamplePositionMatchAlgorithm();

        double score = algorithm.score("ACGT", "ACGA");

        assertEquals(3.0, score);
    }

    /**
     * Verifies that the matcher constructor rejects a null algorithm.
     */
    @Test
    public void constructor_ShouldThrowForNullAlgorithm() {
        assertThrows(NullPointerException.class, () -> {
            new SequenceMatcher(null);
        });
    }

    /**
     * Verifies that findBestMatch rejects a null query sequence.
     */
    @Test
    public void findBestMatch_ShouldThrowForNullQuery() {
        List<DNASequence> database = Arrays.asList(
            new DNASequence("Seq1", "ACGT")
        );

        SequenceMatcher matcher = new SequenceMatcher(new ExamplePositionMatchAlgorithm());

        assertThrows(NullPointerException.class, () -> {
            matcher.findBestMatch(null, database);
        });
    }

    /**
     * Verifies that findBestMatch rejects a null database.
     */
    @Test
    public void findBestMatch_ShouldThrowForNullDatabase() {
        SequenceMatcher matcher = new SequenceMatcher(new ExamplePositionMatchAlgorithm());

        assertThrows(NullPointerException.class, () -> {
            matcher.findBestMatch("ACGT", null);
        });
    }
}
