package pieces;

/**
 * Represents the two sides in chess.
 */
public enum Color {
    /** White side. */
    w,

    /** Black side. */
    b;

    /**
     * Returns the opposing side.
     *
     * @return opposite color
     */
    public Color opposite() {
        return this == w ? b : w;
    }
}