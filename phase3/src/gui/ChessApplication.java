package gui;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/** Entry point for the standalone Phase 3 Swing game. */
public final class ChessApplication {
    private ChessApplication() { }

    /** Starts the GUI on Swing's event-dispatch thread. */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Consistent button backgrounds on macOS, Windows and Linux.
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException error) {
                System.err.println("Using default look and feel: " + error.getMessage());
            }
            new ChessFrame().setVisible(true);
        });
    }
}
