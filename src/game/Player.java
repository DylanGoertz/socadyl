package game;

import pieces.Color;

/**
 * Represents a player in the chess game.
 */
public class Player {

    /** The color assigned to this player. */
    private final Color color;

    /**
     * Creates a player with the given color.
     *
     * @param color the player's chess color
     */
    public Player(Color color) {
        this.color = color;
    }

    /**
     * Returns the player's color.
     *
     * @return the player's color
     */
    public Color getColor() {
        return color;
    }
}
