# Phase 2 final checklist

## Implemented and checked automatically

- [x] Combined model/controller, history panel and menu/persistence.
- [x] Swing application, labeled 8x8 board and click-to-move controls.
- [x] Shared controller, turns, captures, undo, reset and validated load.
- [x] Winner dialog and termination handler implemented.
- [x] Compiled all Phase 2 sources with Java 21.
- [x] 39 core tests and 32 headless integration checks pass.
- [x] Shell/PowerShell launch scripts and documented launch commands provided.
- [x] Javadocs generated and structural doclint checked.

## Still requires Dylan's local run

- [ ] Run the complete application on macOS with a JDK.
- [ ] Confirm native Save/Load/overwrite/error dialogs work.
- [ ] Confirm White and Black winner dialogs appear and OK closes the app.
- [ ] Check layout/resizing and captured-piece scrolling.
- [ ] Follow SCREENSHOT_GUIDE.md and save actual screenshots.
- [ ] Complete the final PDF using screenshots and accurate feature explanations.
- [ ] Push final integration branch; review and merge into dev.
- [ ] Merge tested dev into main and check the public repository opens correctly.
- [ ] One team member submits repository URL and PDF to Canvas.

Do not check the manual items solely because the automated tests passed. The
PowerShell scripts were reviewed but not run in the Linux test environment.
