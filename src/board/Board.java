package board;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import pieces.Bishop;
import pieces.Color;
import pieces.King;
import pieces.Knight;
import pieces.Pawn;
import pieces.Piece;
import pieces.Queen;
import pieces.Rook;

/**
 * Stores and displays an 8-by-8 chessboard.
 * Validates ordinary movement, ownership, and captures.
 * Does not enforce check or other advanced chess rules.
 */
public class Board {

    /** Pieces indexed by row and column; null means empty. */
    private final Piece[][] squares = new Piece[8][8];

    /** Pieces captured during the current game. */
    private final List<Piece> capturedPieces = new ArrayList<>();

    /**
     * Creates a board in the standard starting arrangement.
     */
    public Board() {
        initialize();
    }

    /**
     * Resets the board and captured-piece list for a new game.
     */
    public void initialize() {
        for (Piece[] row : squares) {
            Arrays.fill(row, null);
        }
        capturedPieces.clear();

        setupSide(Color.b, 0, 1);
        setupSide(Color.w, 7, 6);
    }

    /**
     * Places one side's starting pieces.
     *
     * @param color owning side
     * @param backRow row for major pieces
     * @param pawnRow row for pawns
     */
    private void setupSide(Color color, int backRow, int pawnRow) {
        placeInitialPiece(new Rook(new Position(backRow, 0), color));
        placeInitialPiece(new Knight(new Position(backRow, 1), color));
        placeInitialPiece(new Bishop(new Position(backRow, 2), color));
        placeInitialPiece(new Queen(new Position(backRow, 3), color));
        placeInitialPiece(new King(new Position(backRow, 4), color));
        placeInitialPiece(new Bishop(new Position(backRow, 5), color));
        placeInitialPiece(new Knight(new Position(backRow, 6), color));
        placeInitialPiece(new Rook(new Position(backRow, 7), color));

        for (int column = 0; column < 8; column++) {
            placeInitialPiece(new Pawn(new Position(pawnRow, column), color));
        }
    }

    /**
     * Stores a new piece at its starting position.
     *
     * @param piece piece to place
     */
    private void placeInitialPiece(Piece piece) {
        Position position = piece.getPosition();
        squares[position.getRow()][position.getColumn()] = piece;
    }

    /**
     * Looks up a piece at a board position.
     *
     * @param position square to inspect
     * @return occupying piece, or null if empty
     */
    public Piece getPiece(Position position) {
        Objects.requireNonNull(position);
        return squares[position.getRow()][position.getColumn()];
    }

    /**
     * Returns a read-only snapshot of captured pieces.
     *
     * @return captured pieces
     */
    public List<Piece> getCapturedPieces() {
        return List.copyOf(capturedPieces);
    }

    /**
     * Validates and executes an ordinary move.
     * Rejected moves leave the board unchanged.
     * Game must change turns only after this method succeeds.
     *
     * @param from source square
     * @param to destination square
     * @param currentTurn side allowed to move
     * @throws IllegalArgumentException if the move is invalid
     */
    public void movePiece(Position from, Position to, Color currentTurn) {
        if (from == null || to == null || currentTurn == null) {
            throw new IllegalArgumentException(
                "Source, destination, and current turn are required."
            );
        }

        if (from.equals(to)) {
            throw new IllegalArgumentException(
                "Choose a different destination square."
            );
        }

        Piece movingPiece = getPiece(from);
        Piece destinationPiece = getPiece(to);

        if (movingPiece == null) {
            throw new IllegalArgumentException(
                "There is no piece at " + from + "."
            );
        }

        if (movingPiece.getColor() != currentTurn) {
            throw new IllegalArgumentException(
                "You must move your own piece."
            );
        }

        if (destinationPiece != null
                && destinationPiece.getColor() == currentTurn) {
            throw new IllegalArgumentException(
                "You cannot capture your own piece."
            );
        }

        // Kings are never captured in chess.
        if (destinationPiece instanceof King) {
            throw new IllegalArgumentException(
                "Kings cannot be captured."
            );
        }

        if (!movingPiece.possibleMoves(this).contains(to)) {
            throw new IllegalArgumentException(
                "That piece cannot move from " + from + " to " + to + "."
            );
        }

        // All validation happens before changing any state.
        if (destinationPiece != null) {
            destinationPiece.setCaptured(true);
            capturedPieces.add(destinationPiece);
        }

        squares[from.getRow()][from.getColumn()] = null;
        squares[to.getRow()][to.getColumn()] = movingPiece;
        movingPiece.move(to);
    }

    /**
     * Prints the board with files A-H and ranks 8-1.
     */
    public void display() {
        System.out.println();
        System.out.println("   A  B  C  D  E  F  G  H");

        for (int row = 0; row < 8; row++) {
            System.out.print((8 - row) + "  ");

            for (int column = 0; column < 8; column++) {
                Piece piece = squares[row][column];
                String symbol = piece == null ? "##" : piece.getSymbol();
                System.out.print(symbol + " ");
            }

            System.out.println();
        }

        System.out.println();
    }
}