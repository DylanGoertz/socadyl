package gui;

import controller.GameController;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import panels.HistoryPanel;

/** Main window combining the board, menu and history around one controller. */
@SuppressWarnings("serial")
public final class ChessFrame extends JFrame {
    private final GameController controller = new GameController();
    private final BoardPanel board = new BoardPanel(controller);
    private final HistoryPanel history = new HistoryPanel(controller);
    private final Runnable winnerListener = this::checkWinner;
    private boolean winnerScheduled;

    /** Creates a complete fresh game; call on Swing's event-dispatch thread. */
    public ChessFrame() {
        super("Socadyl — CS 3354 Phase 3");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        JLabel heading = new JLabel("  SOCADYL  |  Click a piece, then its destination. W = White, B = Black.");
        heading.setBorder(BorderFactory.createEmptyBorder(12, 8, 8, 8));
        add(heading, BorderLayout.NORTH);
        add(board, BorderLayout.CENTER);
        add(history, BorderLayout.EAST);
        setJMenuBar(new GameMenuBar(controller, this));
        controller.addChangeListener(winnerListener);
        setMinimumSize(new Dimension(920, 660));
        pack();
        setLocationRelativeTo(null);
    }

    private void checkWinner() {
        if (!controller.state().isGameOver() || winnerScheduled) return;
        winnerScheduled = true;
        // Queue until every panel has refreshed; only the frame shows this dialog.
        SwingUtilities.invokeLater(() -> {
            if (!isDisplayable()) return;
            JOptionPane.showMessageDialog(this,
                    controller.state().winner() + " wins by checkmate!\n"
                    + "The game will close when you click OK.",
                    "Game Over — Socadyl", JOptionPane.INFORMATION_MESSAGE);
            dispose();
            System.exit(0);
        });
    }

    /** Detaches listeners before releasing the native window. */
    @Override public void dispose() {
        controller.removeChangeListener(winnerListener);
        board.detach();
        history.detach();
        super.dispose();
    }
}
