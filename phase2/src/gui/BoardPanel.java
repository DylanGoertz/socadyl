package gui;

import controller.GameController;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Objects;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import model.GameState;
import model.Piece;
import model.Position;

/** Click-source/click-destination board backed by the application's shared controller. */
@SuppressWarnings("serial")
public final class BoardPanel extends JPanel {
    private static final Color LIGHT = new Color(238, 229, 210);
    private static final Color DARK = new Color(118, 145, 119);
    private final GameController controller;
    private final JButton[][] squares = new JButton[8][8];
    private final JLabel message = new JLabel("Select a White piece to begin.");
    private final Runnable listener = this::stateChanged;
    private Position selected;

    /** Builds a labeled board; create and use on the Swing event-dispatch thread. */
    public BoardPanel(GameController controller) {
        this.controller = Objects.requireNonNull(controller, "controller");
        setLayout(new BorderLayout(8, 8));
        setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        JPanel grid = new JPanel(new GridLayout(9, 9, 2, 2));
        grid.setPreferredSize(new Dimension(570, 570));
        grid.add(new JLabel());
        for (int col = 0; col < 8; col++)
            grid.add(new JLabel(String.valueOf((char) ('A' + col)), SwingConstants.CENTER));
        for (int row = 0; row < 8; row++) {
            grid.add(new JLabel(String.valueOf(8 - row), SwingConstants.CENTER));
            for (int col = 0; col < 8; col++) {
                Position position = new Position(row, col);
                JButton button = new JButton();
                button.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
                button.setOpaque(true);
                button.setContentAreaFilled(true);
                button.setFocusPainted(false);
                button.setMargin(new java.awt.Insets(1, 1, 1, 1));
                button.setName(position.toString());
                button.addActionListener(event -> select(position));
                squares[row][col] = button;
                grid.add(button);
            }
        }
        add(grid, BorderLayout.CENTER);
        message.setBorder(BorderFactory.createEmptyBorder(4, 0, 4, 0));
        add(message, BorderLayout.SOUTH);
        controller.addChangeListener(listener);
        refresh();
    }

    private void select(Position position) {
        GameState state = controller.state();
        if (state.isGameOver()) return;
        Piece piece = state.pieceAt(position);
        if (selected == null) {
            if (piece == null || piece.color() != state.turn()) {
                message.setText("Select a " + state.turn() + " piece first.");
                return;
            }
            selected = position;
            message.setText("Selected " + position + ". Choose a destination; click again to cancel.");
            refresh();
        } else if (selected.equals(position)) {
            selected = null;
            message.setText("Selection cleared. Select a " + state.turn() + " piece.");
            refresh();
        } else {
            try {
                controller.move(selected, position);
            } catch (IllegalArgumentException error) {
                message.setText(error.getMessage());
            }
        }
    }

    private void stateChanged() {
        selected = null;
        message.setText(controller.state().isGameOver()
                ? controller.state().winner() + " wins!"
                : "Select a " + controller.state().turn() + " piece.");
        refresh();
    }

    private void refresh() {
        GameState state = controller.state();
        for (int row = 0; row < 8; row++) for (int col = 0; col < 8; col++) {
            Position position = new Position(row, col);
            JButton button = squares[row][col];
            Piece piece = state.pieceAt(position);
            button.setBackground((row + col) % 2 == 0 ? LIGHT : DARK);
            button.setBorder(BorderFactory.createLineBorder(
                    position.equals(selected) ? new Color(235, 161, 34) : new Color(67, 83, 70),
                    position.equals(selected) ? 4 : 1));
            // Explicit labels stay readable even when a platform lacks chess-symbol fonts.
            button.setText(piece == null ? "" : "<html><center>"
                    + (piece.color() == Piece.Color.WHITE ? "W" : "B")
                    + "<br>" + piece.type().name().toLowerCase(java.util.Locale.ROOT)
                    + "</center></html>");
            button.setForeground(piece != null && piece.color() == Piece.Color.WHITE
                    ? new Color(34, 55, 115) : new Color(10, 20, 15));
            String label = position + (piece == null ? ": empty" : ": " + piece.color() + " " + piece.type());
            button.setToolTipText(label);
            button.getAccessibleContext().setAccessibleName(label);
            button.setEnabled(!state.isGameOver());
        }
    }

    /** Releases the refresh listener when the containing window is disposed. */
    public void detach() { controller.removeChangeListener(listener); }
}
