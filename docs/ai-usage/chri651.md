# Chris Noria — AI Usage

## Phase 1 testing and demonstration preparation

- Asked: Help understanding the rubric, GitHub workflow,
  and preparing a manual testing checklist.
- Used or changed: Used AI guidance to create my feature
  branch and prepare the testing checklist.
- Result or feedback: Checklist created. Compilation and
  gameplay testing have not started.
- AI created the commit: No. I committed through GitHub.

## Earlier reference implementation

- Asked: Generate Phase 1 Java code from the instructions.
- Used or changed: AI supplied a separate reference project.
  It has not been integrated into the team's repository.
- Result or feedback: I am preparing to test and demonstrate
  the team's implementation.
- AI created the commit: No.

## October 25, 2026 — Compilation and Manual Testing

- Asked: I asked ChatGPT how to compile and run the team's Java
  program on macOS, interpret terminal errors, select manual test
  cases, and document my observed results.

- Used or changed: I used the suggested terminal commands and test
  sequence. ChatGPT helped identify a missing output-directory
  argument and an unmatched quotation mark in commands I entered.
  I compiled using javac --release 21 -d out @sources.txt and ran
  the program using java -cp out game.Game.

- Result or feedback: Compilation succeeded using JDK 25.0.2 with
  Java 21 as the compilation target. I manually tested startup,
  help, malformed input, invalid coordinates, wrong-side moves,
  friendly-capture rejection, blocked rook movement, pawn moves,
  a pawn capture, knight moves, turn-taking, and quitting.
  The observed behavior matched expectations for these tests.
  Lowercase input and extra spacing have not been tested yet.

- Documentation assistance: ChatGPT drafted wording for my testing
  report and helped interpret the screenshots I provided. I used
  that wording to document the results of tests I performed.

- Issue observed: Malformed input produced repeated wording:
  “Invalid move: Invalid move...”. Input rejection still worked.

- AI created the commit: No. I entered the commands, performed the
  tests, and created the GitHub commits myself.

- Current status: Testing results have been documented.
  The demonstration video has not been recorded yet.
