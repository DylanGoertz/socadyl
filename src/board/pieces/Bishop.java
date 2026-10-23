import java.util.List;

public class Bishop extends Piece{
    private Color color;
    private Position startPos;

    public Bishop(Position startPos, Color color) {
        super("b", false, color);
        this.startPos = startPos;
        this.color = color;
    }

    protected char getTypeChar() {
        return 'b';
    }

    public String getSymbol() {
        char colorChar = (getColor() == Color.w) ? 'w' : 'b';
        return "" + colorChar + getTypeChar();
    }

    @Override
    public List<Position> possibleMoves(Board board) {
        return raysFor(board, DIAGONAL);
    }
}
