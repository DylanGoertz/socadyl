# Phase 1 Manual Testing

Tester: Chris Noria
GitHub username: chri651
Test date: October 25, 2026
Branch tested: dev
Commit tested: [insert the commit identifier for the downloaded version]

## Environment

- Operating system: macOS
- Java runtime: OpenJDK Temurin 25.0.2
- Java compiler: javac 25.0.2
- Compilation target: Java 21

## Compilation and Execution

Commands used:

    mkdir -p out
    find src -name '*.java' > sources.txt
    javac --release 21 -d out @sources.txt
    java -cp out game.Game

Compilation completed without errors after correcting my command-entry
mistakes. The program started and displayed the initial board and
White's move prompt.

## Test Checklist

- [x] Compile and launch the program
- [x] Verify initial board and coordinates
- [x] Verify the help command
- [x] Verify valid moves and alternating turns
- [x] Verify an opponent-piece capture
- [x] Verify wrong-side moves are rejected
- [x] Verify friendly captures are rejected
- [x] Verify blocked rook movement is rejected
- [x] Verify malformed input is handled
- [x] Verify invalid coordinates are rejected
- [x] Verify the documented exit command
- [ ] Verify lowercase input and extra spacing

## Actual Results

| Test | Actual result | Status |
|---|---|---|
| Initial board | Correct starting pieces and A–H / 1–8 coordinate labels appeared. | Pass |
| help | Instructions appeared, and White retained the turn. | Pass |
| hello | An error appeared; the program continued without changing the board or turn. | Pass |
| E9 E4 | Invalid coordinates were rejected without changing the board or turn. | Pass |
| E7 E5 on White's turn | Moving Black's pawn was rejected. | Pass |
| A1 A2 | Capturing my own pawn was rejected. | Pass |
| A1 A3 | Moving the rook through the pawn on A2 was rejected. | Pass |
| E2 E4, then D7 D5 | Both pawns moved correctly, and turns alternated. | Pass |
| E4 D5 | White's pawn captured Black's pawn on D5, and E4 became empty. | Pass |
| G8 F6, then G1 F3 | Both knights moved correctly, and turns alternated. | Pass |
| quit | The program printed “Thanks for playing.” and returned to the terminal prompt. | Pass |
| Lowercase and extra spacing | Not tested yet. | Not run |

The rejected inputs and moves shown during testing preserved the board
and the current player's turn.

## Issues Found

Malformed input produced the repeated wording:
“Invalid move: Invalid move...”.

This is a minor message-formatting issue. The input was still rejected
correctly, and the program continued running.

No other issues were observed in the test cases listed above.

## Contribution

My contribution covered compiling and running the team's implementation,
performing the manual tests listed above, and documenting the results.
Demonstration video preparation is still in progress.

## Testing Scope

These results cover only the manual tests listed above. They do not
establish correctness for every possible chess position or movement rule.
The program was run on Java 25.0.2 with compilation targeting Java 21;
execution on a Java 21 runtime was not tested.

## Demonstration Video

Not recorded yet.
