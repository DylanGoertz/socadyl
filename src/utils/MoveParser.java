
package utils;

import board.Position;

/**
 * Converts user input into chessboard positions.
 */
public class MoveParser {

    /**
     * Parses a move entered by the player.
     *
     * @param input the player's typed move
     * @return an array containing the start and end positions
     * @throws IllegalArgumentException if the input is invalid
     */
    public static Position[] parseMove(String input) {

        // Make sure the input is not null.
        if (input == null) {
            throw new IllegalArgumentException("Please enter a move.");
        }

        // Remove extra whitespace and optional hyphen.
        String cleaned = input.trim()
                              .toUpperCase()
                              .replaceAll("[\\s-]+", "");

        // A move should contain exactly four characters.
        if (!cleaned.matches("[A-H][1-8][A-H][1-8]")) {
            throw new IllegalArgumentException(
                "Invalid move. Use a format like E2 E4."
            );
        }

        // Separate the starting and ending coordinates.
        String start = cleaned.substring(0, 2);
        String end = cleaned.substring(2, 4);

        // Convert the coordinates into Position objects.
        Position source = Position.fromChessNotation(start);
        Position destination = Position.fromChessNotation(end);

        // Return both positions to the Game class.
        return new Position[] {source, destination};
    }
}
