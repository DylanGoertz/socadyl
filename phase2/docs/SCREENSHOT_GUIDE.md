# Screenshots for the Phase 2 PDF

Run `sh phase2/run-tests.sh` and then `sh phase2/run.sh` from your repository root
on your Mac. Use Command+Shift+4, then Space, and click the application window to
capture it. Keep the full board and history panel readable. Do not use the old
Phase 1 console game for these screenshots.

## 1. Initial board — 01-initial.png

Capture the initial game showing all pieces, coordinates, alternating square
colors, White's turn, empty history/capture lists and disabled Undo.

## 2. Moves and capture — 02-capture.png

Click the source square followed by the destination for each move:

1. E2 → E5 (White pawn).
2. D7 → E5 (Black pawn captures White's pawn).

Capture the resulting board and sidebar. Expect two history entries, White's
turn, and one pawn in “Black captured.” This also demonstrates unrestricted
movement instead of normal pawn movement rules.

## 3. Save — 03-save.png

Open File → Save. Choose a location such as Documents and enter
`socadyl-demo.tsv`. Capture the native save dialog with the application visible
if practical, then complete the save. Keep this file for the later Load step.
A screenshot of the File menu showing New Game, Save and Load can supplement it.

## 4. Undo capture — 04-undo.png

Click Undo. Capture the updated board/history. White's pawn must be back on E5,
Black's pawn back on D7, Black's turn, one history entry, and no captured pieces.

## 5. New Game — 05-new-game.png

Choose File → New Game. Capture the reset board: all 32 pieces in their initial
positions, White's turn, empty history/capture lists and Undo disabled.

## 6. Load — 06-load.png

Choose File → Load and open `socadyl-demo.tsv`. Capture the restored game. It
must match screenshot 02: Black pawn on E5, White pawn captured, two history
entries and White's turn. Click Undo afterward and confirm it also matches
screenshot 04; this verifies saved undo history.

## 7. Winner — 07-winner.png

Choose File → New Game. Click D1, then E8. The White queen captures the Black
King even with intervening pieces, as allowed in Phase 2.

Capture the winner popup **before clicking OK**. The updated board should be
visible behind it. Clicking OK terminates the application; this is expected.

## 8. Optional test evidence — 08-tests.png

Capture the terminal showing both passing totals: 39 core and 32 integration
checks. This supplements the execution screenshots; it does not replace them.

## Additional checks before reporting success

- Restart and move A2 → A3, then D8 → E1. Confirm Black wins and OK exits.
- Try clicking an empty square or Black piece at the start: no move or turn change.
- Select E2 then D2: friendly capture rejected, no history entry.
- Cancel a save/load dialog: game unchanged.
- Load a deliberately invalid text file: error dialog, current game unchanged.
- Save to the same filename again: replacement confirmation appears; No cancels.
- Resize the window and check labels/history are readable. Capture rows have
  horizontal scrolling when many pieces have been captured.

Bring back your screenshots and any observed errors. The final PDF should
explain board/movement/captures, King-capture termination, both extra features,
source design and any limitations. Include all four team members and the repo URL.
