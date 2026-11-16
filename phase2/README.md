# Socadyl Phase 2

From the repository root on macOS/Linux:

```sh
sh phase2/run-tests.sh
sh phase2/run.sh
```

Windows PowerShell:

```powershell
powershell -ExecutionPolicy Bypass -File phase2/run-tests.ps1
powershell -ExecutionPolicy Bypass -File phase2/run.ps1
```

Requires JDK 17+ and a graphical desktop. The tests can run headlessly.

The application combines Dylan's model/controller, Cassie's history panel and
Chris's menu/persistence. The board, frame, launch scripts and integration tests
were completed with Codex assistance at Dylan's request. Existing teammates'
contributions are retained; this does not attribute the new GUI to Sofie.

The shared controller owns every state mutation. The board selects squares and
requests moves; history renders state and calls undo; the menu exports/restores
sessions. Immutable previous states provide undo, including captured pieces.
Saving move history reconstructs the same state and undo stack on load.

White starts; alternate successful turns; permit arbitrary movement and jumps;
reject wrong-side/empty sources and friendly capture. Capturing the opposing King
wins and ends the application after the dialog is acknowledged. No check,
checkmate, castling, promotion or en passant rules are implemented or required for
this Phase 2 movement model.

Save files are UTF-8 TSV with a versioned header. File reads are limited to
128 KiB and 2,000 moves. Games longer than 2,000 moves cannot be saved with this
format. Invalid/corrupt loads preserve the current game. Settings customization
is not selected as an extra feature.

See the [main README](../README.md), [screenshot guide](docs/SCREENSHOT_GUIDE.md),
[submission checklist](docs/SUBMISSION_CHECKLIST.md), and [test results](docs/TEST_RESULTS.md).
