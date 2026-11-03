package model;

import java.util.List;

/**
 * Versioned save-data contract. Replaying these moves from the standard starting
 * board restores board, turn, winner, captures and undo history exactly.
 * File encoding and file dialogs belong to the persistence/UI components.
 */
public record GameSession(int version, List<MoveRecord> moves) {
    /** Current format version. */
    public static final int CURRENT_VERSION = 1;

    /** Copies the move list so callers cannot mutate a saved session. */
    public GameSession {
        if (version != CURRENT_VERSION)
            throw new IllegalArgumentException("Unsupported save version: " + version);
        moves = List.copyOf(moves);
    }
}
