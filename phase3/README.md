# Socadyl • Phase 3
A two-player Java Swing board with legal movement, captures, alternating turns, check and checkmate. Click the origin square and then the destination. If the destination is rejected, click another destination or click the origin to cancel. The File menu supports New, Save and Load. Undo restores a previous position during play.

## Run from the repository root
Install JDK 17+ with desktop Swing support. No external Java libraries are required.

| Action | macOS / Linux | Windows PowerShell |
|---|---|---|
| Run tests | sh phase3/run-tests.sh | ./phase3/run-tests.ps1 |
| Launch | sh phase3/run.sh | ./phase3/run.ps1 |

## Demonstration
1. Try E2 → E5. White retains the turn, the history stays empty, and the error appears below the board. Click E2 again to clear the selection.
2. Play E2 → E4, D7 → D5, F1 → B5. Black is in check. Try A7 → A6; it cannot resolve the check. Click A7 again to cancel. Capture this screen for the PDF.
3. C7 → C6 blocks the bishop. B5 → C6 captures the pawn with check; Undo restores both pieces.
4. File → New. Play F2 → F3, E7 → E5, G2 → G4, D8 → H4. Black wins by checkmate. Take a screenshot before clicking OK.

## Architecture and scope
[View the class diagram](docs/Architecture.svg) or edit its draw.io source. The Swing panels share GameController. Immutable GameState snapshots support Undo; Rules evaluates hypothetical positions with Phase 1's Board and piece subclasses. Loading replays moves through the same validation path. Phase 2 save files are deliberately incompatible.

Core movement rules only: no castling, en passant, promotion, stalemate or other draw detection. Checkmate ends the game. No network or computer opponent. Earlier phase folders remain unchanged.

## Submission
Open docs/Report.html in a desktop browser, select an actual Phase 3 GUI screenshot and print to PDF (Letter, default scale, headers/footers off). Inspect both pages before submitting the PDF and the GitHub link directly to this folder. The HTML requires no upload or network connection.

AI assistance: Dylan asked Codex to integrate the phases and exercise engineering judgment. Adapted the existing Swing and piece code, implemented king-safety checks, tested the backend and Swing actions, and drafted the architecture diagram and submission materials. No new work is attributed to other teammates.
