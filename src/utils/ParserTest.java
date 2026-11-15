
package utils;

import board.Position;

/**
 * Simple manual tests for MoveParser.
 */
public class ParserTest {

    /**
     * Runs example parser tests.
     *
     * @param args unused command-line arguments
     */
    public static void main(String[] args) {

        String[] tests = {
            "E2 E4",
            "e2 e4",
            " E2   E4 ",
            "E2E4",
            "E2-E4",
            "A9 A4",
            "hello"
        };

        for (String test : tests) {
            try {
                Position[] move = MoveParser.parseMove(test);

                System.out.println(
                    test + " -> " +
                    move[0] + " to " + move[1]
                );

            } catch (IllegalArgumentException e) {
                System.out.println(
                    test + " -> ERROR: " + e.getMessage()
                );
            }
        }
    }
}
