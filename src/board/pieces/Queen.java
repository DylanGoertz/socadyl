import java.util.List;

public class Queen extends Piece {
    private Color color;
    private Position startPos;

    public Queen(Position startPos, Color color) {
        super("q", false, color);
        this.startPos = startPos;
        this.color = color;
    }

    protected char getTypeChar() {
        return 'q';
    }

    public String getSymbol() {
        char colorChar = (getColor() == Color.w) ? 'w' : 'b';
        return "" + colorChar + getTypeChar();
    }

    @Override
    public List<Position> possibleMoves(Board board) {
        List<Position> moves = raysFor(board, STRAIGHT);
        moves.addAll(raysFor(board, DIAGONAL));
        return moves;
    }
    
}
