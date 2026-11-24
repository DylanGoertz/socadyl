package panels;

import controller.GameController;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.util.List;
import java.util.Objects;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import model.GameState;
import model.MoveRecord;
import model.Piece;
import model.Piece.Color;

/**
 * Standalone side panel showing the move history, the pieces each player
 * has captured, and an Undo button.
 * The panel listens to a {@link GameController} and refreshes itself after
 * every move, undo, new game or loaded session. Create and use it on the
 * Swing event-dispatch thread.
 */
@SuppressWarnings("serial") // Live UI components are not serialized.
public final class HistoryPanel extends JPanel {

    /** Controller that owns the game state and performs undo. */
    private final GameController controller;

    /** Backing model for the move list. */
    private final DefaultListModel<String> listModel =
        new DefaultListModel<>();

    /** Scrolling list of moves, oldest first. */
    private final JList<String> moveList = new JList<>(listModel);

    /** Shows whose turn it is, or who won. */
    private final JLabel statusLabel = new JLabel();

    /** Shows pieces captured by White. */
    private final JLabel whiteCapturedLabel = new JLabel();

    /** Shows pieces captured by Black. */
    private final JLabel blackCapturedLabel = new JLabel();

    /** Button that reverts the most recent move. */
    private final JButton undoButton = new JButton("Undo");

    /** Refresh callback registered with the controller. */
    private final Runnable changeListener = this::refresh;

    /**
     * Creates the panel and shows the controller's current state.
     *
     * @param controller game controller to display and control
     */
    public HistoryPanel(GameController controller) {
        this.controller = Objects.requireNonNull(controller, "controller");

        setLayout(new BorderLayout(0, 8));
        setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        setPreferredSize(new Dimension(330, 400));

        statusLabel.setFont(statusLabel.getFont().deriveFont(Font.BOLD));
        add(statusLabel, BorderLayout.NORTH);

        moveList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        moveList.setFocusable(false);
        JScrollPane scroll = new JScrollPane(moveList);
        scroll.setBorder(BorderFactory.createTitledBorder("Move History"));
        add(scroll, BorderLayout.CENTER);

        JPanel bottom = new JPanel();
        bottom.setLayout(new BoxLayout(bottom, BoxLayout.Y_AXIS));
        bottom.setBorder(BorderFactory.createTitledBorder("Captured"));
        styleCapturedLabel(whiteCapturedLabel);
        styleCapturedLabel(blackCapturedLabel);
        bottom.add(whiteCapturedLabel);
        bottom.add(blackCapturedLabel);

        undoButton.addActionListener(event -> controller.undo());

        JPanel south = new JPanel(new BorderLayout(0, 8));
        JScrollPane captures = new JScrollPane(bottom);
        captures.setPreferredSize(new Dimension(310, 110));
        south.add(captures, BorderLayout.CENTER);
        south.add(undoButton, BorderLayout.SOUTH);
        add(south, BorderLayout.SOUTH);

        controller.addChangeListener(changeListener);
        refresh();
    }

    /**
     * Stops listening to the controller. Call this if the panel is
     * discarded while the controller stays in use.
     */
    public void detach() {
        controller.removeChangeListener(changeListener);
    }

    /**
     * Rebuilds the history list, captured pieces, status and button state
     * from the controller's current state.
     */
    private void refresh() {
        GameState state = controller.state();

        listModel.clear();
        List<MoveRecord> moves = state.moves();
        for (int i = 0; i < moves.size(); i++) {
            listModel.addElement((i + 1) + ". " + moves.get(i));
        }
        if (!listModel.isEmpty()) {
            moveList.ensureIndexIsVisible(listModel.size() - 1);
        }

        whiteCapturedLabel.setText(
            "White captured: " + symbols(state.capturedBy(Color.WHITE)));
        blackCapturedLabel.setText(
            "Black captured: " + symbols(state.capturedBy(Color.BLACK)));

        statusLabel.setText(state.isGameOver()
            ? state.winner() + " wins!"
            : "Turn: " + state.turn() + (state.inCheck() ? " — CHECK" : ""));

        // The game ends at checkmate, so undo is off after that.
        undoButton.setEnabled(controller.canUndo() && !state.isGameOver());
    }

    /**
     * Applies the shared look for a captured-pieces row.
     *
     * @param label label to style
     */
    private static void styleCapturedLabel(JLabel label) {
        label.setFont(label.getFont().deriveFont(16f));
        label.setAlignmentX(LEFT_ALIGNMENT);
    }

    /**
     * Converts pieces to readable type names.
     *
     * @param pieces pieces to show
     * @return space-separated names, or "none" if the list is empty
     */
    private static String symbols(List<Piece> pieces) {
        if (pieces.isEmpty()) {
            return "none";
        }

        StringBuilder text = new StringBuilder();
        for (Piece piece : pieces) {
            text.append(piece.type().name().toLowerCase(java.util.Locale.ROOT)).append(' ');
        }
        return text.toString().trim();
    }

}
