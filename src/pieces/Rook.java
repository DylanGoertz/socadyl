package pieces;

import board.Board;
import board.Position;
import java.util.List;

/**
 * A rook that moves along rows and columns.
 */
public class Rook extends Piece {

    /**
     * Creates a rook.
     *
     * @param position starting position
     * @param color owning side
     */
    public Rook(Position position, Color color) {
        super(position, color);
    }

    /** {@inheritDoc} */
    @Override
    protected char getTypeChar() {
        return 'R';
    }

    /** {@inheritDoc} */
    @Override
    public List<Position> possibleMoves(Board board) {
        return raysFor(board, new int[][] {
            {-1, 0}, {1, 0}, {0, -1}, {0, 1}
        });
    }
}