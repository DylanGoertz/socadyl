package controller;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Objects;
import model.GameSession;
import model.GameState;
import model.MoveRecord;
import model.Position;

/**
 * Shared game coordinator for the board, history and menu components.
 * Use on one thread (the Swing event-dispatch thread in the GUI).
 * Listeners run synchronously after successful changes and must not throw or
 * modify the controller. The GUI owns dialogs and application termination.
 */
public final class GameController {
    private GameState state = GameState.newGame();
    private Deque<GameState> undo = new ArrayDeque<>();
    private final List<Runnable> listeners = new ArrayList<>();

    /** Creates a controller with a fresh standard game. */
    public GameController() { }
    /** Returns the current immutable state. */
    public GameState state() { return state; }
    /** Registers a refresh callback, called after successful mutations. */
    public void addChangeListener(Runnable listener) {
        listeners.add(Objects.requireNonNull(listener, "listener"));
    }
    /** Removes a previously registered callback. */
    public void removeChangeListener(Runnable listener) { listeners.remove(listener); }
    private void notifyChanged() {
        for (Runnable listener : List.copyOf(listeners)) listener.run();
    }
    /** Applies a move, or throws IllegalArgumentException without changing state. */
    public void move(Position from, Position to) {
        GameState next = state.move(from, to);
        undo.push(state);
        state = next;
        notifyChanged();
    }
    /** Reports whether the most recent successful move can be undone. */
    public boolean canUndo() { return !undo.isEmpty(); }
    /** Restores the complete previous position; returns false if history is empty. */
    public boolean undo() {
        if (undo.isEmpty()) return false;
        state = undo.pop();
        notifyChanged();
        return true;
    }
    /** Resets board, turn, winner, captures and undo/history. */
    public void newGame() {
        state = GameState.newGame();
        undo.clear();
        notifyChanged();
    }
    /** Exports immutable data for Chris's file persistence implementation. */
    public GameSession exportSession() {
        return new GameSession(GameSession.CURRENT_VERSION, state.moves());
    }
    /**
     * Validates and replays an imported session, then atomically replaces state
     * and undo history. Corrupt moves/captures leave the current game untouched.
     * @throws IllegalArgumentException if the session is missing or inconsistent
     */
    public void restoreSession(GameSession session) {
        if (session == null) throw new IllegalArgumentException("A saved session is required.");
        GameState restored = GameState.newGame();
        Deque<GameState> restoredUndo = new ArrayDeque<>();
        for (MoveRecord recorded : session.moves()) {
            GameState next = restored.move(recorded.from(), recorded.to());
            MoveRecord actual = next.moves().get(next.moves().size() - 1);
            if (!actual.equals(recorded))
                throw new IllegalArgumentException("Saved move does not match the board.");
            restoredUndo.push(restored);
            restored = next;
        }
        state = restored;
        undo = restoredUndo;
        notifyChanged();
    }
}
