package pieces;

import board.Board;
import board.Position;
import java.util.List;

/**
 * A queen that moves along rows, columns, and diagonals.
 */
public class Queen extends Piece {

    /**
     * Creates a queen.
     *
     * @param position starting position
     * @param color owning side
     */
    public Queen(Position position, Color color) {
        super(position, color);
    }

    /** {@inheritDoc} */
    @Override
    protected char getTypeChar() {
        return 'Q';
    }

    /** {@inheritDoc} */
    @Override
    public List<Position> possibleMoves(Board board) {
        return raysFor(board, new int[][] {
            {-1, 0}, {1, 0}, {0, -1}, {0, 1},
            {-1, -1}, {-1, 1}, {1, -1}, {1, 1}
        });
    }
}