
package game;

import java.util.Scanner;
import board.Board;
import board.Position;
import pieces.Color;
import utils.MoveParser;

/**
 * Controls the chess game, including player turns,
 * console input, and move execution.
 */
public class Game {

    /** The chessboard used for this game. */
    private final Board board;

    /** The player controlling white pieces. */
    private final Player whitePlayer;

    /** The player controlling black pieces. */
    private final Player blackPlayer;

    /** The color whose turn it currently is. */
    private Color currentTurn;

    /**
     * Creates a new game with a board and two players.
     */
    public Game() {
        board = new Board();
        whitePlayer = new Player(Color.w);
        blackPlayer = new Player(Color.b);
        currentTurn = Color.w;
    }

    /**
     * Starts the game.
     */
    public void start() {
        System.out.println("Welcome to Socadyl Chess.");
        System.out.println("Enter moves like E2 E4.");
        System.out.println("Type 'help' for instructions or 'quit' to exit.");
        play();
    }

    /**
     * Runs the console game loop.
     */
    public void play() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            board.display();

            String playerName =
                currentTurn == whitePlayer.getColor() ? "White" : "Black";

            System.out.println(playerName + "'s turn.");
            System.out.print("Enter move: ");

            if (!scanner.hasNextLine()) {
                break;
            }

            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("quit")) {
                end();
                break;
            }

            if (input.equalsIgnoreCase("help")) {
                System.out.println("Enter a move such as E2 E4.");
                System.out.println("Type 'quit' to end the game.");
                continue;
            }

            try {
                Position[] positions = MoveParser.parseMove(input);

                board.movePiece(
                    positions[0],
                    positions[1],
                    currentTurn
                );

                System.out.println("Move accepted!");

                // Switch turns only after a successful move.
                currentTurn =
                    currentTurn == Color.w ? Color.b : Color.w;

            } catch (IllegalArgumentException e) {
                System.out.println("Invalid move: " + e.getMessage());
            }
        }
    }

    /**
     * Ends the current game.
     */
    public void end() {
        System.out.println("Thanks for playing.");
    }

    /**
     * Launches the chess game.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        Game game = new Game();
        game.start();
    }
}
