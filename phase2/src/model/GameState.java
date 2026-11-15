package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import model.Piece.Color;
import model.Piece.Type;

/** Immutable complete position with move history. Safe to retain for undo. */
public final class GameState {
    private final List<Piece> board;
    private final Color turn;
    private final Color winner;
    private final List<MoveRecord> moves;

    private GameState(List<Piece> board, Color turn, Color winner, List<MoveRecord> moves) {
        this.board = Collections.unmodifiableList(new ArrayList<>(board));
        this.turn = turn;
        this.winner = winner;
        this.moves = List.copyOf(moves);
    }

    /** Creates the standard 32-piece arrangement with White moving first. */
    public static GameState newGame() {
        List<Piece> board = new ArrayList<>(Collections.nCopies(64, null));
        Type[] back = {Type.ROOK, Type.KNIGHT, Type.BISHOP, Type.QUEEN,
                       Type.KING, Type.BISHOP, Type.KNIGHT, Type.ROOK};
        for (int col = 0; col < 8; col++) {
            board.set(col, new Piece(Color.BLACK, back[col]));
            board.set(8 + col, new Piece(Color.BLACK, Type.PAWN));
            board.set(48 + col, new Piece(Color.WHITE, Type.PAWN));
            board.set(56 + col, new Piece(Color.WHITE, back[col]));
        }
        return new GameState(board, Color.WHITE, null, List.of());
    }

    /** Returns a piece, or null for an empty square. */
    public Piece pieceAt(Position square) {
        Objects.requireNonNull(square, "square");
        return board.get(square.row() * 8 + square.column());
    }
    /** Returns the active player; after victory this remains the winner. */
    public Color turn() { return turn; }
    /** Returns the winner, or null while play continues. */
    public Color winner() { return winner; }
    /** Reports whether a King has been captured. */
    public boolean isGameOver() { return winner != null; }
    /** Returns an immutable chronological list of successful moves. */
    public List<MoveRecord> moves() { return moves; }
    /** Returns immutable captured pieces credited to the specified player. */
    public List<Piece> capturedBy(Color player) {
        Objects.requireNonNull(player, "player");
        return moves.stream().filter(m -> m.piece().color() == player)
                .map(MoveRecord::capturedPiece).filter(Objects::nonNull).toList();
    }

    /**
     * Returns a new position after a move. Rejects invalid selections without
     * changing this state. Any piece may jump/move to any square in Phase 2.
     * @throws IllegalArgumentException for an illegal selection or ended game
     */
    public GameState move(Position from, Position to) {
        if (from == null || to == null)
            throw new IllegalArgumentException("Select a source and destination.");
        if (isGameOver()) throw new IllegalArgumentException("The game has ended.");
        if (from.equals(to)) throw new IllegalArgumentException("Choose a different destination.");
        Piece moving = pieceAt(from);
        if (moving == null) throw new IllegalArgumentException("The source square is empty.");
        if (moving.color() != turn) throw new IllegalArgumentException("It is " + turn + "'s turn.");
        Piece captured = pieceAt(to);
        if (captured != null && captured.color() == turn)
            throw new IllegalArgumentException("You cannot capture your own piece.");
        List<Piece> nextBoard = new ArrayList<>(board);
        nextBoard.set(from.row() * 8 + from.column(), null);
        nextBoard.set(to.row() * 8 + to.column(), moving);
        List<MoveRecord> nextMoves = new ArrayList<>(moves);
        nextMoves.add(new MoveRecord(from, to, moving, captured));
        Color nextWinner = captured != null && captured.type() == Type.KING ? turn : null;
        return new GameState(nextBoard, nextWinner == null ? turn.opposite() : turn,
                nextWinner, nextMoves);
    }
}
