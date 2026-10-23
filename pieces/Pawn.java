import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {
    private Color color;
    private Position startPos;

    public Pawn(Position startPos, Color color) {
        super("p", false, color);
        this.startPos = startPos;
        this.color = color;
    }

    protected char getTypeChar() {
        return 'p';
    }

    public String getSymbol() {
        char colorChar = (getColor() == Color.w) ? 'w' : 'b';
        return "" + colorChar + getTypeChar();
    }

        @Override
        public List<Position> possibleMoves(Board board) {
        List<Position> moves = new ArrayList<>();
        int dir = (getColor() == Color.w) ? -1 : 1;
        int startRow = (getColor() == Color.w) ? 6 : 1;
        int row = getPosition().row;
        int col = getPosition().col;
 
        // Forward one 
        int oneAhead = row + dir;
        if (inBounds(oneAhead, col) && board.getPiece(new Position(oneAhead, col)) == null) {
            moves.add(new Position(oneAhead, col));
 
            // Forward two from starting rank 
            int twoAhead = row + 2 * dir;
//Awaiting board implementation for .getPiece() method
            if (row == startRow && inBounds(twoAhead, col)
                    && board.getPiece(new Position(twoAhead, col)) == null) {
                moves.add(new Position(twoAhead, col));
            }
        }
        }
}