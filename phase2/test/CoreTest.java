import controller.GameController;
import model.*;
import model.Piece.Color;
import model.Piece.Type;
import java.util.ArrayList;
import java.util.List;

/** Dependency-free regression tests: run with java -cp phase2/out CoreTest. */
public final class CoreTest {
    private static int checks;
    private static Position p(String text) { return Position.fromChessNotation(text); }
    private static void check(boolean value, String message) {
        checks++;
        if (!value) throw new AssertionError(message);
    }
    private static void reject(Runnable action) {
        try { action.run(); } catch (IllegalArgumentException expected) { checks++; return; }
        throw new AssertionError("Expected rejected operation");
    }
    private static void move(GameController c, String from, String to) { c.move(p(from), p(to)); }
    /** Exercises rules, immutable snapshots, event notifications, undo and restoration. */
    public static void main(String[] args) {
        GameController c = new GameController();
        int[] events = {0};
        Runnable listener = () -> events[0]++;
        c.addChangeListener(listener);
        GameState start = c.state();
        int pieces = 0;
        for (int r = 0; r < 8; r++) for (int col = 0; col < 8; col++)
            if (start.pieceAt(new Position(r, col)) != null) pieces++;
        check(pieces == 32, "32 starting pieces");
        check(start.turn() == Color.WHITE, "White first");
        check(start.pieceAt(p("D1")).type() == Type.QUEEN, "White queen");
        check(start.pieceAt(p("E8")).type() == Type.KING, "Black king");
        check(p(" a1 ").equals(new Position(7, 0)), "notation orientation");
        reject(() -> p("A9"));
        reject(() -> new Position(-1, 0));
        reject(() -> move(c, "E3", "E4"));
        reject(() -> move(c, "E7", "E5"));
        reject(() -> move(c, "E2", "E2"));
        reject(() -> move(c, "E2", "D2"));
        reject(() -> c.move(null, p("A1")));
        check(c.state() == start && events[0] == 0 && !c.canUndo(), "reject is atomic");
        check(!c.undo(), "empty undo");
        move(c, "E2", "E5");
        check(c.state().turn() == Color.BLACK, "turn alternates");
        check(start.pieceAt(p("E2")) != null && start.moves().isEmpty(), "old snapshot unchanged");
        move(c, "D7", "E5");
        check(c.state().capturedBy(Color.BLACK).size() == 1, "capture attribution");
        check(c.state().pieceAt(p("E5")).color() == Color.BLACK, "capturing piece on destination");
        GameSession saved = c.exportSession();
        check(c.undo(), "undo capture");
        check(c.state().pieceAt(p("D7")) != null
                && c.state().pieceAt(p("E5")).color() == Color.WHITE, "undo restores both pieces");
        check(c.state().turn() == Color.BLACK && c.state().capturedBy(Color.BLACK).isEmpty(), "undo restores turn/captures");
        check(saved.moves().size() == 2, "saved session unchanged by undo");
        c.restoreSession(saved);
        check(c.exportSession().equals(saved), "session round trip");
        check(c.undo() && c.undo() && !c.canUndo(), "loaded undo stack");
        check(c.state().pieceAt(p("E2")) != null && c.state().moves().isEmpty(), "loaded undo reaches initial board");
        c.restoreSession(saved);
        GameState before = c.state();
        int beforeEvents = events[0];
        List<MoveRecord> corrupt = new ArrayList<>(saved.moves());
        corrupt.set(1, new MoveRecord(p("D7"), p("E5"), new Piece(Color.BLACK, Type.PAWN), null));
        reject(() -> c.restoreSession(new GameSession(1, corrupt)));
        check(c.state() == before && events[0] == beforeEvents && c.canUndo(), "failed restore atomic");
        reject(() -> new GameSession(2, List.of()));
        c.newGame();
        check(c.state().moves().isEmpty() && !c.canUndo() && c.state().winner() == null, "reset clears history/winner");
        move(c, "D1", "E8");
        check(c.state().winner() == Color.WHITE && c.state().isGameOver(), "jumping king capture wins");
        check(c.state().capturedBy(Color.WHITE).get(0).type() == Type.KING, "captured king recorded");
        reject(() -> move(c, "A7", "A6"));
        GameSession won = c.exportSession();
        c.newGame();
        c.restoreSession(won);
        check(c.state().winner() == Color.WHITE, "winner restored");
        check(c.undo() && !c.state().isGameOver() && c.state().pieceAt(p("E8")).type() == Type.KING, "undo victory");
        move(c, "A2", "A3");
        move(c, "D8", "E1");
        check(c.state().winner() == Color.BLACK, "Black can win");
        c.newGame();
        move(c, "A2", "H6");
        check(c.state().pieceAt(p("H6")).type() == Type.PAWN, "unrestricted pawn movement");
        c.undo();
        move(c, "B2", "B5");
        check(c.state().moves().size() == 1 && c.state().pieceAt(p("H6")) == null, "new timeline after undo");
        try { c.state().moves().clear(); throw new AssertionError("Mutable history"); }
        catch (UnsupportedOperationException expected) { checks++; }
        c.removeChangeListener(listener);
        beforeEvents = events[0];
        c.newGame();
        check(events[0] == beforeEvents, "listener removed");
        System.out.println("PASS: " + checks + " core checks");
    }
}
