# Core verification — 2026-11-03

Executed by Codex in the development environment:

- Compiled the model/controller and CoreTest using Eclipse ECJ 3.38 targeting Java 17.
- Compiled again with javac 17.0.20 using the delivered run-tests.sh script and
  `-Xlint:all`; no javac warnings were emitted.
- Ran the test suite on OpenJDK 17.0.20: `PASS: 39 core checks`.
- Generated model/controller Javadocs with structural doclint enabled; no errors.
- Checked patch whitespace with `git diff --check`.

Coverage includes standard initial board, coordinate parsing, invalid selections,
turn switching, unrestricted moves/jumping, captures, immutable snapshots,
undo including captured-piece restoration, reset, save-session replay and loaded
undo history, atomic rejection of inconsistent save data, winner restoration,
both players capturing the enemy King, and listener removal.

Not yet exercised: Swing display/input, winner dialog and process exit, actual
save/load file parsing and writing, Windows PowerShell script execution, or the
teammates' integration. Core tests do not establish that the full assignment is
ready to submit. No GUI screenshots are fabricated or included.
