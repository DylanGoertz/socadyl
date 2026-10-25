package pieces;

import board.Board;
import board.Position;
import java.util.ArrayList;
import java.util.List;

/**
 * A pawn with forward movement and diagonal captures.
 * Promotion and en passant are not implemented.
 */
public class Pawn extends Piece {

    /**
     * Creates a pawn.
     *
     * @param position starting position
     * @param color owning side
     */
    public Pawn(Position position, Color color) {
        super(position, color);
    }

    /** {@inheritDoc} */
    @Override
    protected char getTypeChar() {
        return 'p';
    }

    /** {@inheritDoc} */
    @Override
    public List<Position> possibleMoves(Board board) {
        List<Position> moves = new ArrayList<>();

        int direction = getColor() == Color.w ? -1 : 1;
        int startingRow = getColor() == Color.w ? 6 : 1;
        int row = getPosition().getRow();
        int column = getPosition().getColumn();
        int nextRow = row + direction;

        // Forward movement requires an empty square.
        if (inBounds(nextRow, column)) {
            Position oneAhead = new Position(nextRow, column);

            if (board.getPiece(oneAhead) == null) {
                moves.add(oneAhead);

                int doubleRow = row + 2 * direction;

                if (row == startingRow && inBounds(doubleRow, column)) {
                    Position twoAhead = new Position(doubleRow, column);

                    if (board.getPiece(twoAhead) == null) {
                        moves.add(twoAhead);
                    }
                }
            }
        }

        // Diagonal movement requires an opposing piece.
        for (int offset : new int[] {-1, 1}) {
            int targetColumn = column + offset;

            if (inBounds(nextRow, targetColumn)) {
                Position target = new Position(nextRow, targetColumn);
                Piece occupant = board.getPiece(target);

                if (occupant != null
                        && occupant.getColor() != getColor()) {
                    moves.add(target);
                }
            }
        }

        return moves;
    }
}