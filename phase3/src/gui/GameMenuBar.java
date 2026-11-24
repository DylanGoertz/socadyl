package gui;

import controller.GameController;
import java.awt.Component;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import persistence.SessionFile;

/** New Game, Save and Load actions for the shared Swing game window. */
@SuppressWarnings("serial") // The live Swing menu is never serialized.
public final class GameMenuBar extends JMenuBar {
    private static final long serialVersionUID = 1L;

    private final GameController controller;
    private final Component parent;
    private JFileChooser chooser;

    /** Construct and install this menu bar on Swing's event-dispatch thread. */
    public GameMenuBar(GameController controller, Component parent) {
        this.controller = Objects.requireNonNull(controller, "controller");
        this.parent = Objects.requireNonNull(parent, "parent");

        JMenu file = new JMenu("File");

        JMenuItem newGame = new JMenuItem("New Game");
        newGame.addActionListener(event -> controller.newGame());
        file.add(newGame);

        JMenuItem save = new JMenuItem("Save");
        save.addActionListener(event -> save());
        file.add(save);

        JMenuItem load = new JMenuItem("Load");
        load.addActionListener(event -> load());
        file.add(load);

        add(file);
    }

    private JFileChooser chooser() {
        if (chooser == null) {
            chooser = new JFileChooser();
        }
        return chooser;
    }

    private void save() {
        JFileChooser dialog = chooser();
        dialog.setDialogTitle("Save Socadyl Game");
        if (dialog.showSaveDialog(parent) != JFileChooser.APPROVE_OPTION) {
            return;
        }

        try {
            Path target = dialog.getSelectedFile().toPath();
            if (Files.exists(target) && JOptionPane.showConfirmDialog(parent,
                    "Replace the existing save file?", "Confirm Replace",
                    JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
                return;
            }
            SessionFile.write(target, controller.exportSession());
        } catch (IOException | IllegalArgumentException | SecurityException error) {
            showError("Could not save game", error);
        }
    }

    private void load() {
        JFileChooser dialog = chooser();
        dialog.setDialogTitle("Load Socadyl Game");
        if (dialog.showOpenDialog(parent) != JFileChooser.APPROVE_OPTION) {
            return;
        }

        try {
            controller.restoreSession(SessionFile.read(dialog.getSelectedFile().toPath()));
        } catch (IOException | IllegalArgumentException | SecurityException error) {
            showError("Could not load game", error);
        }
    }

    private void showError(String title, Exception error) {
        JOptionPane.showMessageDialog(parent, error.getMessage(), title, JOptionPane.ERROR_MESSAGE);
    }
}
