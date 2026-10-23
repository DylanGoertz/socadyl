import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece {
    private Color color;
    private Position startPos;

    private static final int[][] JUMPS = {
        {-2, -1}, {-2, 1}, {-1, -2}, {-1, 2},
        {1, -2}, {1, 2}, {2, -1}, {2, 1}
    };
    
    public Knight(Position startPos, Color color) {
        super("n", false, color);
        this.startPos = startPos;
        this.color = color;
    }

    protected char getTypeChar() {
        return 'n';
    }

    public String getSymbol() {
        char colorChar = (getColor() == Color.w) ? 'w' : 'b';
        return "" + colorChar + getTypeChar();
    }

    @Override
    public List<Position> possibleMoves(Board board) {
        List<Position> moves = new ArrayList<>();
        for (int[] j : JUMPS) {
            addStep(board, moves, getPosition().getRow() + j[0], getPosition().getCol() + j[1]);
        }
        return moves;
    }
}
