package pieces;

import board.Board;
import board.Position;
import java.util.List;

/**
 * A bishop that moves diagonally.
 */
public class Bishop extends Piece {

    /**
     * Creates a bishop.
     *
     * @param position starting position
     * @param color owning side
     */
    public Bishop(Position position, Color color) {
        super(position, color);
    }

    /** {@inheritDoc} */
    @Override
    protected char getTypeChar() {
        return 'B';
    }

    /** {@inheritDoc} */
    @Override
    public List<Position> possibleMoves(Board board) {
        return raysFor(board, new int[][] {
            {-1, -1}, {-1, 1}, {1, -1}, {1, 1}
        });
    }
}