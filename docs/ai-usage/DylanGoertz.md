# Dylan Goertz — AI Usage Log

## October 6, 2026 — Phase 1 planning and repository setup
- Asked: Help interpret the project instructions and rubric, divide work
  among Dylan, Cassie, and Sofie, and explain repository setup.
- Used or changed: Used the AI-assisted starting plan and task division
  and added the plan to docs/PHASE1_PLAN.md on GitHub.
- Result or feedback: The plan is committed before implementation.
  Java implementation and testing have not started.
- AI created the commit: No. I committed the file through GitHub.
## October 6, 2026 — Position class implementation and testing

- Asked: Help implement a Java Position class with coordinate conversion, bounds validation, equality checks, and Javadoc comments. Also requested compilation and testing instructions.
- Used or changed: Used the AI-provided Position.java implementation and added a .gitignore for compiled Java output and macOS files. Corrected a folder creation command after accidentally entering /src/game instead of src/game.
- Result or feedback: Compilation succeeded. JShell tests confirmed that E2 maps to (6,4), A1 to (7,0), and H8 to (0,7). Converting back to notation returned E2. Equality and bounds checks returned the expected results. A9, I1, and a negative row correctly threw IllegalArgumentException.
- AI created the commit: No. AI supplied suggested Git commands; the implementation commit has not yet been created.

## October 24, 2026 — Board implementation and piece integration

- Asked: Review Cassie’s piece classes for compatibility with Position.java and provide corrected Java files plus a Board implementation.
- Used or changed: Used AI-generated replacements to integrate Cassie’s initial piece work. Moved the classes into src/pieces, added package declarations and imports, created public Color.java, fixed position initialization and coordinate access, completed pawn movement and captures, and added shared movement helpers and Javadoc. Added Board.java for initial setup, display, move validation, and capture tracking.
- Result or feedback: Compilation succeeded. JShell tests demonstrated E2 E4, D7 D5, and E4 D5. The capture count was 1, and the white pawn’s stored position was D5. Attempting A8 A7 correctly rejected capturing a friendly piece. Broader piece-movement tests and integration with Sofie’s console interface remain pending. Check/checkmate and special moves are not implemented.
- AI created the commit: No. AI supplied code and suggested Git commands; I applied the changes and ran the tests myself.