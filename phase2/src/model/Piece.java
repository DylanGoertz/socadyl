package model;

import java.util.Objects;

/** Immutable piece value. Its position belongs to GameState, not the piece. */
public record Piece(Color color, Type type) {
    /** The two players. */
    public enum Color {
        WHITE, BLACK;
        /** Returns the other player. */
        public Color opposite() { return this == WHITE ? BLACK : WHITE; }
    }
    /** The six visual piece types; Phase 2 does not restrict their movement. */
    public enum Type { PAWN, ROOK, KNIGHT, BISHOP, QUEEN, KING }

    /** Requires both a color and a type. */
    public Piece {
        Objects.requireNonNull(color, "color");
        Objects.requireNonNull(type, "type");
    }
}
