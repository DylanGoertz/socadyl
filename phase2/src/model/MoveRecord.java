package model;

import java.util.Objects;

/** One successful move; capturedPiece is null when the destination was empty. */
public record MoveRecord(Position from, Position to, Piece piece, Piece capturedPiece) {
    /** Requires the source, destination and moving piece. */
    public MoveRecord {
        Objects.requireNonNull(from, "from");
        Objects.requireNonNull(to, "to");
        Objects.requireNonNull(piece, "piece");
    }

    /** Human-readable history text suitable for the history panel. */
    @Override public String toString() {
        return piece.color() + " " + piece.type() + " " + from + " → " + to
                + (capturedPiece == null ? "" : " (captured " + capturedPiece.color()
                + " " + capturedPiece.type() + ")");
    }
}
