import java.util.Objects;

/**
 * Represent one labeled DNA sequence in the database.
 * @author Dr. Jody Paul
 * @version 2026-03-10a
 */
public class DNASequence {
    private final String label;
    private final String sequence;

    /**
     * Create a labeled DNA sequence.
     *
     * @param label the label associated with the sequence
     * @param sequence the DNA sequence text
     */
    public DNASequence(String label, String sequence) {
        this.label = Objects.requireNonNull(label, "label must not be null");
        this.sequence = normalizeAndValidate(sequence);
    }

    /**
     * Return the label for this sequence.
     * @return the label
     */
    public String getLabel() {
        return label;
    }

    /**
     * Return the normalized DNA sequence.
     * @return the DNA sequence
     */
    public String getSequence() {
        return sequence;
    }

    /**
     * Normalize the input sequence and check that it contains only valid DNA bases.
     *
     * @param sequence the sequence to normalize and validate
     * @return the normalized uppercase sequence
     * @throws NullPointerException if sequence is null
     * @throws IllegalArgumentException if sequence is empty or contains invalid characters
     */
    public static String normalizeAndValidate(String sequence) {
        Objects.requireNonNull(sequence, "sequence must not be null");
        String normalized = sequence.trim().toUpperCase();
        if (normalized.isEmpty()) {
            throw new IllegalArgumentException("sequence must not be empty");
        }
        for (int i = 0; i < normalized.length(); i++) {
            char base = normalized.charAt(i);
            if (base != 'A' && base != 'C' && base != 'G' && base != 'T') {
                throw new IllegalArgumentException(
                    "Invalid DNA base '" + base + "' in sequence: " + normalized
                );
            }
        }
        return normalized;
    }

    /**
     * Return a readable string representation of this object.
     * @return a string containing the label and sequence
     */
    @Override
    public String toString() {
        return label + ": " + sequence;
    }
}
