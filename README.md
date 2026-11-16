# Socadyl

CS 3354 — Software Engineering, Fall 2026, Texas State University.

## Team

- Dylan Goertz — @DylanGoertz
- Cassie Pirtle — @ssiepir
- Sofia Dubose — @d-sof
- Chris Noria-Espana — @chri651

## Phase 2: Swing chess

A two-player Java Swing game with an 8x8 board, click-to-move controls, captures,
turn display and a winner dialog when a King is captured. Each piece is labeled
with W/B and its full type name for reliable display across platforms.

Phase 2 deliberately permits every piece to move to any square, including jumps.
White moves first. Empty sources, wrong-side selection and friendly captures are
rejected. Clicking the selected square again cancels selection.

Two complete extra features are included:

1. **File menu:** New Game, Save and Load. Files preserve board, turn, captures,
   chronological history and undo state.
2. **History panel:** numbered moves, pieces captured by each player and Undo.
   Captured-piece rows scroll horizontally when needed.

The game ends after a King capture. A dialog identifies the winner; clicking OK
closes the application. Take the winner screenshot before clicking OK.

## Requirements and launch

Install a JDK (Java Development Kit) 17 or newer; JDK 21 is recommended.
Both `java -version` and `javac -version` must work in your terminal.
A graphical desktop is required for the application. No external libraries.

From the repository root on macOS/Linux:

```sh
sh phase2/run.sh
```

Windows PowerShell:

```powershell
powershell -ExecutionPolicy Bypass -File phase2/run.ps1
```

The scripts compile the application and launch `gui.ChessApplication`.
All Phase 2 components share a single `GameController` created by `ChessFrame`.

## Tests

macOS/Linux:

```sh
sh phase2/run-tests.sh
```

Windows PowerShell:

```powershell
powershell -ExecutionPolicy Bypass -File phase2/run-tests.ps1
```

Expected results: `PASS: 39 core checks` and `PASS: 32 integration checks`.
These compile all Phase 2 source and exercise core behavior, Swing board/history
button actions and save/load. Tests run without opening windows. Native file
dialogs, the winner popup and process exit also require the manual checklist.

## Screenshots and submission

Follow [SCREENSHOT_GUIDE.md](phase2/docs/SCREENSHOT_GUIDE.md) for exact moves and
screenshots, and [SUBMISSION_CHECKLIST.md](phase2/docs/SUBMISSION_CHECKLIST.md)
for the remaining manual checks. One team member submits the public repository
link and PDF with real screenshots and feature explanations. No Phase 2 video
is required. The report and local execution screenshots are not included yet.

## Source layout

- `phase2/src/model/`: immutable positions, pieces, game state and save data.
- `phase2/src/controller/`: moves, undo, reset and validated session restoration.
- `phase2/src/gui/`: Swing entry point, frame, board and menu.
- `phase2/src/panels/`: history, captures and Undo panel.
- `phase2/src/persistence/`: versioned UTF-8 save-file encoding.
- `phase2/test/`: dependency-free regression tests.
- `phase2/docs/`: design, test evidence and submission instructions.
- `src/`: preserved Phase 1 console game, built separately.

## Javadocs

```sh
javadoc -encoding UTF-8 -d phase2/out/javadoc -sourcepath phase2/src model controller gui panels persistence
```

Open `phase2/out/javadoc/index.html` after generation. Documentation output and
compiled classes are ignored by Git.

## Phase 1 console game

Phase 1 retains its own chess movement rules and console interface. On macOS/Linux:

```sh
mkdir -p out
find src -name '*.java' > out/phase1-sources.txt
javac -d out @out/phase1-sources.txt
java -cp out game.Game
```

On PowerShell, compile with `javac -d out (Get-ChildItem -Recurse src -Filter *.java).FullName`,
then run `java -cp out game.Game`. Build Phase 1 and Phase 2 separately.
