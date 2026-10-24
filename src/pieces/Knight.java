package pieces;

import board.Board;
import board.Position;
import java.util.ArrayList;
import java.util.List;

/**
 * A knight that moves in L shapes and can jump over pieces.
 */
public class Knight extends Piece {

    /**
     * Creates a knight.
     *
     * @param position starting position
     * @param color owning side
     */
    public Knight(Position position, Color color) {
        super(position, color);
    }

    /** {@inheritDoc} */
    @Override
    protected char getTypeChar() {
        return 'N';
    }

    /** {@inheritDoc} */
    @Override
    public List<Position> possibleMoves(Board board) {
        List<Position> moves = new ArrayList<>();

        int[][] jumps = {
            {-2, -1}, {-2, 1}, {-1, -2}, {-1, 2},
            {1, -2}, {1, 2}, {2, -1}, {2, 1}
        };

        for (int[] jump : jumps) {
            addStep(
                board,
                moves,
                getPosition().getRow() + jump[0],
                getPosition().getColumn() + jump[1]
            );
        }

        return moves;
    }
}