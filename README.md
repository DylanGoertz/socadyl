# Socadyl

## Class
CS 3354 — Software Engineering
Fall 2026

## Team Members
- Dylan Goertz — GitHub: @DylanGoertz
- Cassie Pirtle — GitHub: @ssiepir
- Sofia Dubose — GitHub: @d-sof

## Project
This repository contains our team's work for the Socadyl project.

Socadyl is a two-player, console-based chess game developed in Java. The game displays a standard 8×8 chessboard and allows players to enter moves through the terminal.

## Features
- Displays an 8×8 chessboard with coordinates A–H and 1–8.
- Represents chess pieces using two-character symbols such as wp, bR, and wK.
- Supports move inputs such as E2 E4.
- Validates basic piece movement, turn ownership, and captures.
- Rejects invalid moves without switching turns.
- Supports help and quit commands.

## Requirements
- Java Development Kit (JDK) 21

## How to Compile and Run
Open a PowerShell terminal in the project's root directory.

1. Compile the Java files:

javac -d out (Get-ChildItem -Recurse src -Filter *.java).FullName

2. Run the game:

java -cp out game.Game

## How to Play

1. The game begins with White's turn.
2. Enter a move using starting and destination coordinates, such as E2 E4.
3. If the move is valid, the board updates and the turn switches to the other player.
4. If the move is invalid, an error message appears and the same player can try again.
5. Enter help to display instructions or quit to exit.

## Project Structure

- src/board/ — Chessboard representation and board positions.
- src/pieces/ — Chess pieces, colors, and movement rules.
- src/game/ — Player information and the main game loop.
- src/utils/ — Move parsing and supporting utilities.

## Documentation

Open docs/javadoc/index.html in a web browser to view the generated documentation.