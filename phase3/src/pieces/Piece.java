package pieces;

import board.Board;
import board.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Base class for chess pieces.
 * Movement candidates do not enforce king safety.
 */
public abstract class Piece {

    /** Side that owns this piece. */
    private final Color color;

    /** Current board position. */
    private Position position;

    /** Whether this piece has been captured. */
    private boolean captured;

    /**
     * Creates a piece at a valid position.
     *
     * @param position starting position
     * @param color owning side
     */
    protected Piece(Position position, Color color) {
        this.position = Objects.requireNonNull(position);
        this.color = Objects.requireNonNull(color);
        this.captured = false;
    }

    /**
     * Returns the owning side.
     *
     * @return piece color
     */
    public Color getColor() {
        return color;
    }

    /**
     * Returns the current position.
     *
     * @return current position
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Reports whether the piece was captured.
     *
     * @return true if captured
     */
    public boolean isCaptured() {
        return captured;
    }

    /**
     * Updates capture status.
     *
     * @param captured new capture status
     */
    public void setCaptured(boolean captured) {
        this.captured = captured;
    }

    /**
     * Updates the stored position.
     * Board is responsible for validating and executing moves.
     *
     * @param position new position
     */
    public void move(Position position) {
        this.position = Objects.requireNonNull(position);
    }

    /**
     * Returns the piece's identifying letter.
     *
     * @return p, R, N, B, Q, or K
     */
    protected abstract char getTypeChar();

    /**
     * Returns the piece's identifying name.
     *
     * @return piece letter
     */
    public String getName() {
        return String.valueOf(getTypeChar());
    }

    /**
     * Returns the two-character console symbol.
     *
     * @return symbol such as wp or bK
     */
    public String getSymbol() {
        return color.name() + getTypeChar();
    }

    /**
     * Finds ordinary movement candidates on the supplied board.
     * Implementations exclude friendly destinations and blocked paths,
     * but do not check whether a move exposes the king.
     *
     * @param board current board
     * @return available destination positions
     */
    public abstract List<Position> possibleMoves(Board board);

    /**
     * Checks whether coordinates are inside the board.
     *
     * @param row candidate row
     * @param column candidate column
     * @return true if coordinates are valid
     */
    protected boolean inBounds(int row, int column) {
        return Position.isValid(row, column);
    }

    /**
     * Adds an on-board destination unless occupied by a friendly piece.
     *
     * @param board current board
     * @param moves destination list
     * @param row destination row
     * @param column destination column
     */
    protected void addStep(
            Board board, List<Position> moves, int row, int column) {

        if (!inBounds(row, column)) {
            return;
        }

        Position target = new Position(row, column);
        Piece occupant = board.getPiece(target);

        if (occupant == null || occupant.getColor() != color) {
            moves.add(target);
        }
    }

    /**
     * Finds sliding moves in the supplied directions.
     * Each ray stops at the first occupied square.
     *
     * @param board current board
     * @param directions row and column offsets for each direction
     * @return available sliding destinations
     */
    protected List<Position> raysFor(Board board, int[][] directions) {
        List<Position> moves = new ArrayList<>();

        for (int[] direction : directions) {
            int row = position.getRow() + direction[0];
            int column = position.getColumn() + direction[1];

            while (inBounds(row, column)) {
                Position target = new Position(row, column);
                Piece occupant = board.getPiece(target);

                if (occupant == null) {
                    moves.add(target);
                } else {
                    if (occupant.getColor() != color) {
                        moves.add(target);
                    }
                    break;
                }

                row += direction[0];
                column += direction[1];
            }
        }

        return moves;
    }
}