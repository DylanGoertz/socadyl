# Phase 2 integration verification — 2026-11-15

Codex executed the shell test script with the Java 21 compiler/runtime:

- `PASS: 39 core checks`
- `PASS: 32 integration checks`

The integration checks use Swing on its event-dispatch thread without a display
server. They exercise actual board selection and Undo/New button actions,
history refresh, captures, saved-session round trips, Undo after load, invalid
save rejection, reset/load selection clearing, both King-capture winners,
end-game input disabling, listener detachment and layout/painting.

An offscreen rendering of the board/history was inspected for layout. It is not
presented as a user's execution screenshot or native-dialog evidence.

Javadocs were generated with structural doclint enabled. The patch was checked
with `git diff --check`. The entire Phase 2 source is compiled by both test scripts.

Not executed here: a native JFrame session, file chooser/overwrite/error dialogs,
winner popup/process exit, macOS-specific behavior, or Windows PowerShell scripts.
Dylan must perform the manual checks in SCREENSHOT_GUIDE.md before submission.
