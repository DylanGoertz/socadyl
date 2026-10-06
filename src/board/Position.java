package board;

import java.util.Locale;

/**
 * Represents an immutable position on an 8-by-8 chessboard.
 * Row 0 represents rank 8, and row 7 represents rank 1.
 * Column 0 represents file A, and column 7 represents file H.
 */
public final class Position {

    /** Zero-based row index. */
    private final int row;

    /** Zero-based column index. */
    private final int column;

    /**
     * Creates a position using array indexes.
     *
     * @param row row index from 0 to 7
     * @param column column index from 0 to 7
     * @throws IllegalArgumentException if either index is outside the board
     */
    public Position(int row, int column) {
        if (!isValid(row, column)) {
            throw new IllegalArgumentException(
                "Row and column must be between 0 and 7."
            );
        }

        this.row = row;
        this.column = column;
    }

    /**
     * Checks whether indexes are inside the board.
     *
     * @param row row index to check
     * @param column column index to check
     * @return true if both indexes are between 0 and 7
     */
    public static boolean isValid(int row, int column) {
        return row >= 0 && row < 8
            && column >= 0 && column < 8;
    }

    /**
     * Converts a chess coordinate such as E2 into a position.
     * Lowercase letters and surrounding whitespace are accepted.
     *
     * @param notation chess coordinate from A1 through H8
     * @return the corresponding position
     * @throws IllegalArgumentException if the coordinate is invalid or null
     */
    public static Position fromChessNotation(String notation) {
        if (notation == null) {
            throw new IllegalArgumentException(
                "Enter a coordinate from A1 to H8."
            );
        }

        String normalized = notation.strip().toUpperCase(Locale.ROOT);

        if (!normalized.matches("[A-H][1-8]")) {
            throw new IllegalArgumentException(
                "Invalid coordinate. Use A1 through H8."
            );
        }

        int column = normalized.charAt(0) - 'A';
        int row = 8 - (normalized.charAt(1) - '0');

        return new Position(row, column);
    }

    /**
     * Returns the row index.
     *
     * @return row index from 0 to 7
     */
    public int getRow() {
        return row;
    }

    /**
     * Returns the column index.
     *
     * @return column index from 0 to 7
     */
    public int getColumn() {
        return column;
    }

    /**
     * Converts this position to a chess coordinate.
     *
     * @return coordinate such as E2
     */
    @Override
    public String toString() {
        char file = (char) ('A' + column);
        int rank = 8 - row;

        return String.valueOf(file) + rank;
    }

    /**
     * Checks whether another object represents the same square.
     *
     * @param other object to compare with this position
     * @return true when both positions have the same row and column
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof Position)) {
            return false;
        }

        Position position = (Position) other;

        return row == position.row && column == position.column;
    }

    /**
     * Returns a hash code consistent with position equality.
     *
     * @return hash code for this position
     */
    @Override
    public int hashCode() {
        return 31 * row + column;
    }
}