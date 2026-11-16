import controller.GameController;
import gui.BoardPanel;
import gui.GameMenuBar;
import java.awt.Component;
import java.awt.Container;
import java.awt.image.BufferedImage;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.swing.*;
import model.*;
import panels.HistoryPanel;
import persistence.SessionFile;

/** Headless Swing/persistence integration checks; uses real button actions on the EDT. */
public final class IntegrationTest {
    private static int checks;
    private static void check(boolean passed, String message) {
        checks++;
        if (!passed) throw new AssertionError(message);
    }
    private static <T> T find(Container root, Class<T> type, String name) {
        for (Component child : root.getComponents()) {
            if (type.isInstance(child) && (name == null || name.equals(child.getName())
                    || child instanceof JButton && name.equals(((JButton) child).getText())))
                return type.cast(child);
            if (child instanceof Container) {
                T result = find((Container) child, type, name);
                if (result != null) return result;
            }
        }
        return null;
    }
    private static void click(BoardPanel board, String square) {
        find(board, JButton.class, square).doClick(0);
    }
    private static Position p(String square) { return Position.fromChessNotation(square); }
    private static void layout(Container container) {
        container.doLayout();
        for (Component child : container.getComponents())
            if (child instanceof Container) layout((Container) child);
    }
    private static void exercise() throws Exception {
        GameController c = new GameController();
        BoardPanel board = new BoardPanel(c);
        HistoryPanel history = new HistoryPanel(c);
        JButton undo = find(history, JButton.class, "Undo");
        JList<?> moves = find(history, JList.class, null);
        check(!undo.isEnabled(), "Undo starts disabled");
        click(board, "E7"); click(board, "E5");
        check(c.state().moves().isEmpty(), "cannot select opponent or empty square");
        click(board, "E2"); click(board, "D2");
        check(c.state().moves().isEmpty(), "friendly capture rejected");
        click(board, "E2"); click(board, "E5");
        check(c.state().moves().isEmpty(), "same square cancels selection");
        click(board, "E2"); click(board, "E5");
        check(c.state().turn() == Piece.Color.BLACK, "click move changes turn");
        check(moves.getModel().getSize() == 1, "history refreshes");
        click(board, "D7"); click(board, "E5");
        check(c.state().capturedBy(Piece.Color.BLACK).size() == 1, "capture updates model");
        check(moves.getModel().getSize() == 2, "capture history");
        check(undo.isEnabled(), "Undo enabled");
        Path file = Files.createTempFile("socadyl-integration-", ".tsv");
        try {
            GameSession captured = c.exportSession();
            SessionFile.write(file, captured);
            undo.doClick(0);
            check(c.state().pieceAt(p("E5")).color() == Piece.Color.WHITE, "undo restores captured pawn");
            check(c.state().pieceAt(p("D7")) != null, "undo restores attacker");
            check(c.state().capturedBy(Piece.Color.BLACK).isEmpty(), "undo clears captured list");
            GameMenuBar menu = new GameMenuBar(c, board);
            click(board, "A7");
            menu.getMenu(0).getItem(0).doClick(0);
            check(c.state().moves().isEmpty() && !undo.isEnabled(), "New menu resets model and panel");
            click(board, "A6");
            check(c.state().moves().isEmpty(), "new game clears selection");
            c.restoreSession(SessionFile.read(file));
            check(c.exportSession().equals(captured), "file save/load exact roundtrip");
            check(moves.getModel().getSize() == 2 && undo.isEnabled(), "load refreshes panel");
            undo.doClick(0);
            check(c.state().turn() == Piece.Color.BLACK, "undo works after file load");
            click(board, "A7"); c.restoreSession(captured); click(board, "A6");
            check(c.state().moves().size() == 2, "load clears selection");
            GameState before = c.state();
            Files.writeString(file, "SOCADYL_PHASE2\t1\nE2\tE5\tWHITE\tPAWN\t-\t-\nD7\tE5\tBLACK\tPAWN\t-\t-\n");
            try { c.restoreSession(SessionFile.read(file)); throw new AssertionError("bad capture accepted"); }
            catch (IllegalArgumentException expected) { check(c.state() == before, "bad load is atomic"); }
            for (String bad : new String[]{"", "OTHER\t1\n", "SOCADYL_PHASE2\t2\n", "SOCADYL_PHASE2\t1\nE2\tE5\tWHITE\tPAWN\t-\tPAWN\n"}) {
                Files.writeString(file, bad);
                try { SessionFile.read(file); throw new AssertionError("bad file accepted"); }
                catch (java.io.IOException expected) { checks++; }
            }
            Files.write(file, new byte[128 * 1024 + 1]);
            try { SessionFile.read(file); throw new AssertionError("oversize accepted"); }
            catch (java.io.IOException expected) { checks++; }
            c.newGame(); SessionFile.write(file, c.exportSession());
            c.restoreSession(SessionFile.read(file));
            check(c.state().moves().isEmpty(), "initial position save/load");
            click(board, "D1"); click(board, "E8");
            check(c.state().winner() == Piece.Color.WHITE, "click King capture wins");
            check(!undo.isEnabled(), "Undo disabled at game over");
            check(!find(board, JButton.class, "A1").isEnabled(), "board disabled at game over");
            SessionFile.write(file, c.exportSession()); c.newGame(); c.restoreSession(SessionFile.read(file));
            check(c.state().winner() == Piece.Color.WHITE, "saved winner restored");
            c.newGame(); click(board, "A2"); click(board, "A3"); click(board, "D8"); click(board, "E1");
            check(c.state().winner() == Piece.Color.BLACK, "Black click capture wins");
            c.newGame();
            // Exercise actual Swing painting even without a display server.
            JPanel content = new JPanel(new java.awt.BorderLayout());
            content.add(board, java.awt.BorderLayout.CENTER); content.add(history, java.awt.BorderLayout.EAST);
            content.setSize(960, 650); layout(content);
            BufferedImage image = new BufferedImage(960, 650, BufferedImage.TYPE_INT_RGB);
            java.awt.Graphics2D graphics = image.createGraphics();
            content.printAll(graphics); graphics.dispose();
            if (System.getProperty("socadyl.preview") != null)
                javax.imageio.ImageIO.write(image, "png", Path.of(System.getProperty("socadyl.preview")).toFile());
            check(find(board, JButton.class, "E2").getWidth() > 40, "board layout has usable squares");
            board.detach(); history.detach();
            c.move(p("E2"), p("E5"));
            check(moves.getModel().getSize() == 0, "detached panel stops listening");
        } finally { Files.deleteIfExists(file); }
    }
    /** Runs the UI tests on the event-dispatch thread, without opening native windows. */
    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            try { exercise(); } catch (Exception error) { throw new RuntimeException(error); }
        });
        System.out.println("PASS: " + checks + " integration checks");
    }
}
