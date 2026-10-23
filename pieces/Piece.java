import java.util.ArrayList;
import java.util.List;

public abstract class Piece {
    private String name;
    private boolean captured;
    private Position position;
    private Color color;

    public Piece(String name, boolean captured, Color color) {
        this.name = name;
        this.captured = captured;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }

    public boolean isCaptured() {
        return captured;
    }

    public void setCaptured(boolean captured) {
        this.captured = captured;
    }

    public Position getPosition() {
        return position;
    }

    public void move(Position position) {
        this.position = position;
    }

    public abstract List<Position> possibleMoves(Board board);

    public boolean inBounds(int row, int col) {
        return getPosition().isValid(row, col);
    }

//Awaiting board implementation 
    protected void addRay(Board board, List<Position> moves, int dRow, int dCol) {
        int row = position.getRow() + dRow;
        int col = position.getCol() + dCol;
        while (inBounds(row, col)) {
            Position target = new Position(row, col);
            Piece occupant = board.getPiece(target);
            if (occupant == null) {
                moves.add(target);
            } else {
                if (occupant.getColor() != color) {
                    moves.add(target);
                }
                break;
            }
            row += dRow;
            col += dCol;
        }
    }

    protected List<Position> raysFor(Board board, int[][] directions) {
        List<Position> moves = new ArrayList<>();
        for (int[] d : directions) {
            addRay(board, moves, d[0], d[1]);
        }
        return moves;
    }

    //Straight directions for rook and queen
    protected static final int[][] STRAIGHT = { {-1, 0}, {1, 0}, {0, -1}, {0, 1} };
 
    //Diagonal directions for bishop and queen
    protected static final int[][] DIAGONAL = { {-1, -1}, {-1, 1}, {1, -1}, {1, 1} };
}

enum Color {
    w, b
}