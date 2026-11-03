# Socadyl Phase 2 — Dylan's model and controller

This folder adds the Phase 2 game core without changing Phase 1. Requires JDK 17+
(the repository's JDK 21 also works). No external dependencies.

## Run the core tests

From the repository root, macOS/Linux:

```sh
sh phase2/run-tests.sh
```

Windows PowerShell:

```powershell
powershell -ExecutionPolicy Bypass -File phase2/run-tests.ps1
```

These tests run in a terminal; they are not the Swing application. Sofie's GUI,
Cassie's history panel and Chris's persistence/menu implementation still need to
be connected. Build Phase 1 and Phase 2 separately because they use different
rules. Do not import Phase 1 pieces or call Phase 1 move validation.

## Decisions adopted

- White starts; turns alternate only after successful moves.
- Every piece can move to any square, including jumping over pieces.
- Reject empty source, same square, wrong player and friendly capture.
- Capture an enemy King to win; block further moves.
- Row 0 is rank 8; column 0 is file A.
- A single GameController belongs to the application and is shared by all panels.
- GameState, Piece, Position, MoveRecord and GameSession are immutable.
- The controller owns undo; the history panel must not create another undo stack.
- Use menu New/Save/Load and history/captured pieces/Undo as the two extra features.
- GUI owns the winner dialog and termination after acknowledgement.
- Saved move history reconstructs all game state, including undo; format version 1.

See [TEAM_HANDOFF.md](docs/TEAM_HANDOFF.md) for exact calls and integration steps.

## Add these files to your repository

Start from your local socadyl repository with a clean working tree:

```sh
git switch dev
git pull origin dev
git switch -c phase2/dylan-core
```

Copy the extracted `phase2` folder into the repository root. Run the tests above,
then:

```sh
git add phase2
git commit -m "Add Phase 2 game model, controller, undo and session validation"
git push -u origin phase2/dylan-core
```

Open a GitHub pull request with base `dev` and compare `phase2/dylan-core`. Ask a
teammate to review before merging. These commands assume the new branch name does
not already exist; use another descriptive name if needed. Review local changes
before switching branches. Send teammates `phase2/docs/TEAM_HANDOFF.md` once the
core is available. Their code should target this API rather than copy the model.
