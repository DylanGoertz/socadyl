package pieces;

import board.Board;
import board.Position;
import java.util.ArrayList;
import java.util.List;

/**
 * A king that moves one square in any direction.
 * Castling and attacked-square restrictions are not implemented.
 */
public class King extends Piece {

    /**
     * Creates a king.
     *
     * @param position starting position
     * @param color owning side
     */
    public King(Position position, Color color) {
        super(position, color);
    }

    /** {@inheritDoc} */
    @Override
    protected char getTypeChar() {
        return 'K';
    }

    /** {@inheritDoc} */
    @Override
    public List<Position> possibleMoves(Board board) {
        List<Position> moves = new ArrayList<>();

        for (int rowOffset = -1; rowOffset <= 1; rowOffset++) {
            for (int columnOffset = -1; columnOffset <= 1; columnOffset++) {
                if (rowOffset == 0 && columnOffset == 0) {
                    continue;
                }

                addStep(
                    board,
                    moves,
                    getPosition().getRow() + rowOffset,
                    getPosition().getColumn() + columnOffset
                );
            }
        }

        return moves;
    }
}