# Phase 2 integration contract

## Shared ownership

Dylan: `model` and `controller` packages (implemented here).
Sofie: `gui` application, frame and board.
Cassie: history/capture/undo panel and final PDF assembly.
Chris: menu and save/load file encoding, plus final launch instructions.

Keep all Phase 2 Java source under `phase2/src/`, with directories matching
packages. Compile into `phase2/out/`. Use Java 17-compatible source (JDK 21 works).
Pass ONE controller into each panel/menu constructor. All mutations and listener
callbacks must run on Swing's event-dispatch thread. A listener should refresh
views, not trigger more controller mutations. Unregister listeners when disposing
views. The API intentionally has no Swing dependencies.

## Sofie — board and game window

```java
GameController controller = new GameController();
// In each panel:
controller.addChangeListener(this::refreshFromState);
// Also call refreshFromState once when the panel is created.
Piece piece = controller.state().pieceAt(new Position(row, column));
// null means empty; otherwise use piece.color() and piece.type().
try {
    controller.move(selectedSquare, destinationSquare);
} catch (IllegalArgumentException error) {
    // Display error.getMessage(); do not change turn or board yourself.
}
```

Create the GUI with `SwingUtilities.invokeLater`. Use `state().turn()` for the
turn label. Clear selection after moves, undo, new game and load. The core does
not store selection. Display every type distinctly; A8 is the top-left square.

In ONE frame-level listener, check `state().winner() != null`, show the winner in
`JOptionPane.showMessageDialog`, then terminate the standalone application after
the user acknowledges it. Do not let individual panels each show the popup.
No additional moves are accepted once a King has been captured. Undoing a win is
supported for core testing/restoration but the running GUI must terminate as the
assignment requires. On loading a finished game, use the same end-game handling.

## Cassie — history, captured pieces and undo

- `controller.state().moves()` is the chronological immutable history.
- `MoveRecord.toString()` identifies player/type, from/to, and any capture.
- `controller.state().capturedBy(Piece.Color.WHITE)` means pieces WHITE captured;
  use BLACK for pieces BLACK captured. Do not interpret this as pieces White lost.
- Enable the Undo button with `controller.canUndo()`.
- Call `controller.undo()`; it restores board, turn, captures, winner and history.
- Register a change listener so history and button state update on moves, undo,
  reset and load. No separate HistoryManager/undo stack is necessary.
- Collect real screenshots of initial board, moves/captures, winner popup and
  both extra features for the PDF once the integrated application runs.

## Chris — New, Save and Load

- New: `controller.newGame()`; refresh happens through listeners.
- Save: `GameSession session = controller.exportSession()`.
- Load: parse a complete file into a GameSession, then call
  `controller.restoreSession(session)` once. It validates the entire sequence
  before changing the live state. Catch parsing/IO/IllegalArgumentException
  failures and show a useful error while preserving the existing game.
- Save does not mutate the game. Canceling a file dialog must do nothing.
- Loaded sessions include complete undo history. Saving after undo saves only
  the currently active history, not the abandoned moves.

Suggested dependency-free UTF-8 format (tab-separated fields):

```text
SOCADYL_PHASE2\t1
E2\tE5\tWHITE\tPAWN\t-\t-
D7\tE5\tBLACK\tPAWN\tWHITE\tPAWN
```

Here `\t` represents a real tab. The header contains format identifier and
version. Each subsequent line is source, destination, moving color, moving type,
captured color, captured type. Both capture fields are `-` when no capture occurs.
An initial game has only the header. Parse squares with
`Position.fromChessNotation`, enums with `valueOf`, and construct each
`MoveRecord`. Then construct `new GameSession(version, moves)` and restore it.
Require exact field counts, both capture fields together, a valid header and
version; reject incomplete/corrupt files. Consider reasonable file-size/move-count
limits. Prefer writing to a temporary file next to the destination and replacing
it after successful completion, preserving an existing save if writing fails.
Do not use Java native object deserialization for arbitrary save files.

Replaying history is exact because Phase 2 begins from one standard arrangement
and moves are deterministic. Do not add arbitrary board-editing/loading APIs
without updating this contract. GUI selection, window size and colors are not
part of game state; settings are not one of our chosen extra features.

## Dylan — integrate and review

1. Add this `phase2/` folder to a fresh feature branch based on latest `dev`.
2. Run the core tests, commit, push and open a PR into `dev` for review.
3. Share this contract so teammates base their branches on the merged core.
4. Review each component PR and run the integrated app before merging.
5. Test E2→E5, D7→E5 (capture), Undo; then Save, New, Load, Undo.
6. On a fresh game, D1→E8 must win for White despite intervening pieces.
7. Confirm winner popup appears and app terminates after acknowledgement.
8. Generate Javadocs and collect actual screenshots for Cassie's final PDF.

The Phase 2 assignment calls for a repository link and PDF. The full GUI, actual
file IO and submission screenshots are still team integration work, not claimed
as completed by the core tests.
