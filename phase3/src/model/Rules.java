package model;

import board.Board;
import java.util.ArrayList;
import java.util.List;
import model.Piece.Color;

/** Uses the Phase 1 pieces for geometry, then simulates moves to protect the king. */
final class Rules {
    private final List<Piece> squares;
    private final Board geometry;

    Rules(List<Piece> squares) {
        this.squares = squares;
        var placement = new ArrayList<pieces.Piece>();
        for (int i=0; i<64; i++) {
            Piece p = squares.get(i);
            if (p == null) continue;
            var pos = new board.Position(i/8,i%8);
            var side = p.color()==Color.WHITE ? pieces.Color.w : pieces.Color.b;
            placement.add(switch (p.type()) {
                case PAWN -> new pieces.Pawn(pos,side);
                case ROOK -> new pieces.Rook(pos,side);
                case KNIGHT -> new pieces.Knight(pos,side);
                case BISHOP -> new pieces.Bishop(pos,side);
                case QUEEN -> new pieces.Queen(pos,side);
                case KING -> new pieces.King(pos,side);
            });
        }
        geometry = new Board(placement);
    }
    private static board.Position backend(Position p) { return new board.Position(p.row(),p.column()); }
    boolean legal(Position from, Position to, Color side) {
        var piece = geometry.getPiece(backend(from));
        if (piece == null || squares.get(from.row()*8+from.column()).color()!=side) return false;
        return piece.possibleMoves(geometry).contains(backend(to)) && safe(from,to,side);
    }
    private boolean safe(Position from, Position to, Color side) {
        int dest=to.row()*8+to.column();
        Piece target=squares.get(dest);
        if (target!=null && target.type()==Piece.Type.KING) return false;
        var next=new ArrayList<>(squares);
        next.set(dest,next.set(from.row()*8+from.column(),null));
        return !new Rules(next).checked(side);
    }
    boolean checked(Color side) {
        int king=squares.indexOf(new Piece(side,Piece.Type.KING));
        var target=new board.Position(king/8,king%8);
        for(int i=0;i<64;i++) {
            Piece p=squares.get(i);
            if(p!=null && p.color()!=side && geometry.getPiece(new board.Position(i/8,i%8))
                    .possibleMoves(geometry).contains(target)) return true;
        }
        return false;
    }
    boolean hasReply(Color side) {
        for(int i=0;i<64;i++) {
            Piece p=squares.get(i);
            if(p==null || p.color()!=side) continue;
            var from=new Position(i/8,i%8);
            for(var to:geometry.getPiece(backend(from)).possibleMoves(geometry))
                if(safe(from,new Position(to.getRow(),to.getColumn()),side)) return true;
        }
        return false;
    }
}
