import java.util.ArrayList;
import java.util.List;

public class King extends Piece {
    private Color color;
    private Position startPos;

    public King(Position startPos, Color color) {
        super("k", false, color);
        this.startPos = startPos;
        this.color = color;
    }

    protected char getTypeChar() {
        return 'k';
    }

    public String getSymbol() {
        char colorChar = (getColor() == Color.w) ? 'w' : 'b';
        return "" + colorChar + getTypeChar();
    }

    @Override
    public List<Position> possibleMoves(Board board) {
        List<Position> moves = new ArrayList<>();
        for (int dr = -1; dr <= 1; dr++) {
            for (int dc = -1; dc <= 1; dc++) {
                if (dr == 0 && dc == 0) {
                    continue;
                }
                addStep(board, moves, getPosition().getRow() + dr, getPosition().getCol() + dc);
            }
        }
        return moves;
    }
    
}
