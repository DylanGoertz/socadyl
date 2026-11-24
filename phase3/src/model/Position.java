package model;

import java.util.Locale;

/** A board coordinate: row 0 is rank 8; column 0 is file A. */
public record Position(int row, int column) {
    /** Rejects coordinates outside the board. */
    public Position {
        if (row < 0 || row > 7 || column < 0 || column > 7)
            throw new IllegalArgumentException("Position must be on the board.");
    }

    /** Parses a square such as E2, ignoring surrounding whitespace and case. */
    public static Position fromChessNotation(String square) {
        if (square == null) throw new IllegalArgumentException("Square is required.");
        String text = square.strip().toUpperCase(Locale.ROOT);
        if (!text.matches("[A-H][1-8]"))
            throw new IllegalArgumentException("Use a square from A1 through H8.");
        return new Position(8 - (text.charAt(1) - '0'), text.charAt(0) - 'A');
    }

    /** Returns the square in chess notation. */
    @Override public String toString() {
        return "" + (char) ('A' + column) + (8 - row);
    }
}
