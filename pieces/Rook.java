import java.util.List;

public class Rook extends Piece {
    private Color color;
    private Position startPos;

    public Rook(Position startPos, Color color) {
        super("r", false, color);
        this.startPos = startPos;
        this.color = color;
    }

    protected char getTypeChar() {
        return 'r';
    }

    public String getSymbol() {
        char colorChar = (getColor() == Color.w) ? 'w' : 'b';
        return "" + colorChar + getTypeChar();
    }

    @Override
    public List<Position> possibleMoves(Board board) {
        return raysFor(board, STRAIGHT);
    }
    
}
