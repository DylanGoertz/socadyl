Socadyl — Phase 1 Plan
CS 3354 — Fall 2026
Team: Dylan Goertz, Cassie, Sofie.
Status: AI-assisted starting plan; implementation and test results are pending.
Commit this file as docs/PHASE1_PLAN.md before starting implementation.
Replace the two username placeholders with actual GitHub usernames before committing.
Scope
Build a Java console chess application with an initialized 8x8 board, labeled
coordinates, all six piece types, piece-specific movement rules, input parsing,
captures, and alternating turns. Reject malformed coordinates, empty sources,
wrong-side moves, own-piece captures, blocked sliding moves, and invalid movement
patterns without changing the board or turn. Document that check, checkmate,
stalemate, castling, en passant, and promotion are outside this Phase 1 scope.
Do not present the result as a complete chess rules engine.
Use Java 21, standard Java libraries, and a src directory with game, board,
pieces, and utils packages. Add Javadoc to every class, field, constructor,
and method as work proceeds. Generate browsable Javadoc before submission.
Ownership and branches
Member	Branch	Main responsibilities
Dylan Goertz	feature/DylanGoertz	Position, Board, initialization, display, move execution and captures; repository integration
Cassie	feature/ssiepir	Color, abstract Piece, six piece subclasses, reusable movement helpers and movement tests
Sofie	feature/d-sof	Move parsing, Player, Game, console loop, turn handling, runnable README and input tests


Each member documents and tests their own code and maintains their own AI notes.
Dylan manages main and dev. Everyone opens at least one feature-to-dev PR.
Review cycle: Dylan reviews Cassie; Cassie reviews Sofie; Sofie reviews Dylan.
Reviews must describe actual inspection or tests. Never invent contributions,
approvals, test outcomes, or AI usage for someone else.
Shared design contracts
- Position is immutable: row 0 is rank 8, row 7 is rank 1, column 0 is A.
  It converts A1-H8 coordinates, checks bounds, and implements equality.
- Color has WHITE and BLACK and a way to obtain the opposite color.
- Piece stores color and current Position. possibleMoves(Board) returns
  movement candidates including valid opponent captures, excluding friendly
  destinations and blocked paths. These candidates do not enforce king safety.
- Board stores Piece[8][8]. It offers getPiece(Position), initialization,
  display, and a move operation accepting source, destination, and active color.
  A rejected move must not mutate anything. An accepted move updates the
  destination, clears the source, updates the piece position, and records a capture.
- Pawn moves toward decreasing rows for white and increasing rows for black.
  Two-square moves require the starting rank and two clear squares. Diagonal
  moves require an opposing piece; ordinary forward moves cannot capture.
- A parsing utility turns input into source and destination Positions; accept
  mixed case and extra whitespace, plus E2E4 and E2-E4 as convenience formats.
  Invalid input produces a clear error. Support help and quit separately.
- Game owns the active player and changes turns only after a successful move.
  Player represents a player's color. End of input exits cleanly.
- Use wp/bp, wR/bR, wN/bN, wB/bB, wQ/bQ, wK/bK and ## for empty squares.
Milestone 1 — Project setup and core model
Tasks:
- Dylan commits this plan before implementation; establishes dev.
- Everyone creates their own feature branch from dev and their AI notes file.
- Dylan implements Position and establishes the Board interface/skeleton.
- Cassie implements Color and abstract Piece plus compilable subclass skeletons.
- Sofie implements Player and a minimal Game entry point and parser skeleton.
- Agree on method signatures before dependent implementations begin.
Manual tests:
- Compile all source files together and launch the entry point.
- Confirm A1 maps to row 7/column 0 and H8 to row 0/column 7.
- Reject A0, I1, and malformed coordinates cleanly.
Evidence: real commits describing setup and core model work; record actual
test results in commit/PR notes. Skeletons are temporary, not finished features.
Milestone 2 — Piece movement
Tasks:
- Cassie implements all six subclasses in small meaningful commits, using
  shared ray-tracing logic for rook, bishop, and queen where appropriate.
- Dylan completes board lookup and capture/move interfaces for integration.
- Sofie implements and tests parsing while piece movement is developed.
Manual tests:
- In small controlled board fixtures, verify rook straight movement, bishop
  diagonals, queen both, knight L shapes, and king one-square movement.
- Confirm sliding pieces stop at blockers, may capture the first opponent,
  and cannot pass through that opponent; knights can jump blockers.
- Check edge and corner positions never produce off-board destinations.
- Test both pawn colors, starting double moves, blocked moves, diagonal
  captures, and rejection of backward moves and empty diagonal destinations.
Evidence: meaningful piece/parser commits and recorded fixture test outcomes.
Milestone 3 — Board setup, rendering, and move execution
Tasks:
- Dylan places all 32 pieces, displays A-H above and 8-1 at left, and
  implements validation plus atomic board/position/capture updates.
- Cassie integrates movement methods with the initialized board and fixes
  movement issues found through board tests.
- Sofie integrates input and board feedback, and starts README run instructions.
Manual tests:
- Confirm black back rank at 8, black pawns at 7, white pawns at 2, white
  back rank at 1; queens on D and kings on E; 32 occupied squares total.
- Move E2 E4: E2 becomes ## and E4 becomes wp.
- Verify empty sources, own-piece captures, wrong-side moves, same-square
  moves, and blocked paths leave every board field unchanged.
- From a fresh board use E2 E4, D7 D5, E4 D5; confirm the black pawn is
  captured and the white pawn's stored position is D5.
Evidence: board implementation commits and integrated board test results.
Milestone 4 — CLI, documentation, and delivery
Tasks:
- Sofie completes the repeated board/prompt loop, help/quit, error recovery,
  end-of-input handling, turn changes, README commands and feature summary.
- Dylan integrates approved PRs into dev and verifies a clean checkout.
- Cassie leads the piece regression check; all members finish their Javadocs
  and personal AI notes. Generate Javadoc and retain accessible output.
- Each member opens a PR into dev and reviews their assigned teammate's PR.
- Dylan opens the final dev-to-main PR and merges after checks/review.
- Keep main, dev, and all three remote feature branches until grading.
- Record and share the demo; one teammate submits both required links.
Manual tests:
- Run from a clean checkout using only the README commands.
- Fresh game: E2 E4, E7 E5, G1 F3, B8 C6; inspect the board after each move.
- Test lower-case input, surrounding/repeated spaces, E2E4, and E2-E4
  from an appropriate fresh board/state.
- Try garbage, blank input, A9 A4, wrong-side moves, and own-piece captures;
  ensure the same player is prompted again and the game remains usable.
- Confirm accepted moves change turns once; help does not change turns;
  quit and end of input exit without an exception.
- Generate Javadoc successfully and open its index page.
- Verify repository visibility and video access using a viewer account or
  signed-out browser as appropriate for the selected sharing policy.
Evidence: CLI/docs/fix commits, PR descriptions with actual test outcomes,
individual reviews, generated documentation, and the final integration PR.
Individual AI usage notes
Each person maintains docs/ai-usage/<github-username>.md after each use.
Template:
Date / task
- Asked: [actual request]
- Used or changed: [what you adopted and personally changed]
- Result or feedback: [actual outcome, including pending/failed tests]
- AI created the commit: [yes/no; review changes and message before pushing]
Dylan's initial entry can truthfully record requesting an AI-assisted Phase 1
plan and team split based on the PDF and rubric. State that implementation
and testing are pending. Cassie and Sofie write their own actual histories.
README and video checklist
README: course/team names and usernames; Java version; exact compile/run
commands; Javadoc command and output location; example moves and output;
features implemented; explicit limitations; test instructions.
Suggested video outline (the assignment gives no required duration):
1. Introduce Socadyl and all three members.
2. Explain package responsibilities and Piece inheritance; each member
   briefly explains code they contributed.
3. Compile/run; display the starting board and move prompt.
4. Enter several moves and a capture; demonstrate an invalid move without
   losing the turn; show help/quit if useful.
5. Briefly show README, generated Javadoc, and development PRs.
Upload the recording to Microsoft OneDrive using the TXST account. Enable
link sharing that lets the instructor view it and verify access. One member
submits the GitHub repository URL and shared video URL on Canvas.
The rubric separately requires every member to rate each other member 0-10
for contribution; use the instructor's designated submission method. Add a
short justification for extreme scores if required. Do not fabricate ratings.
