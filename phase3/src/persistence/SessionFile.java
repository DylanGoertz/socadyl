package persistence;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import model.GameSession;
import model.MoveRecord;
import model.Piece;
import model.Position;

/** Reads and writes versioned Socadyl move histories as UTF-8 TSV. */
public final class SessionFile {
    private static final String FORMAT = "SOCADYL_PHASE3";
    private static final int MAX_BYTES = 128 * 1024;

    // Replaying a move retains a snapshot of the preceding history for undo.
    private static final int MAX_MOVES = 2_000;

    private SessionFile() { }

    /** Parses a complete file before the caller attempts to restore it. */
    public static GameSession read(Path source) throws IOException {
        byte[] bytes;
        try (InputStream input = Files.newInputStream(source)) {
            bytes = input.readNBytes(MAX_BYTES + 1);
        }
        if (bytes.length > MAX_BYTES) {
            throw new IOException("Save file exceeds 128 KiB.");
        }

        String content = StandardCharsets.UTF_8.newDecoder()
                .decode(ByteBuffer.wrap(bytes)).toString();

        try (BufferedReader reader = new BufferedReader(new StringReader(content))) {
            String header = reader.readLine();
            String[] fields = header == null ? new String[0] : header.split("\\t", -1);
            if (fields.length != 2 || !FORMAT.equals(fields[0])) {
                throw new IOException("Missing or invalid Socadyl save header.");
            }
            if (!Integer.toString(GameSession.CURRENT_VERSION).equals(fields[1])) {
                throw new IOException("Unsupported Socadyl save version.");
            }

            List<MoveRecord> moves = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                if (moves.size() == MAX_MOVES) {
                    throw new IOException("Save file exceeds " + MAX_MOVES + " moves.");
                }
                try {
                    moves.add(parseMove(line));
                } catch (IllegalArgumentException error) {
                    throw new IOException("Invalid move on line " + (moves.size() + 2)
                            + ". " + error.getMessage(), error);
                }
            }
            return new GameSession(GameSession.CURRENT_VERSION, moves);
        }
    }

    private static MoveRecord parseMove(String line) {
        String[] fields = line.split("\\t", -1);
        if (fields.length != 6) {
            throw new IllegalArgumentException("Expected six tab-separated fields.");
        }

        boolean noCapture = fields[4].equals("-");
        if (noCapture != fields[5].equals("-")) {
            throw new IllegalArgumentException("Capture requires both a color and a type.");
        }

        Piece piece = new Piece(Piece.Color.valueOf(fields[2]), Piece.Type.valueOf(fields[3]));
        Piece captured = noCapture ? null
                : new Piece(Piece.Color.valueOf(fields[4]), Piece.Type.valueOf(fields[5]));

        return new MoveRecord(Position.fromChessNotation(fields[0]),
                Position.fromChessNotation(fields[1]), piece, captured);
    }

    /** Writes a temporary file beside the target before replacing the save. */
    public static void write(Path destination, GameSession session) throws IOException {
        if (session.moves().size() > MAX_MOVES) {
            throw new IOException("Cannot save a game with more than " + MAX_MOVES + " moves.");
        }

        Path target = destination.toAbsolutePath();
        Path temporary = Files.createTempFile(target.getParent(), ".socadyl-", ".tmp");

        try {
            try (BufferedWriter writer = Files.newBufferedWriter(temporary, StandardCharsets.UTF_8)) {
                writer.write(FORMAT + "\t" + session.version());
                writer.newLine();

                for (MoveRecord move : session.moves()) {
                    Piece captured = move.capturedPiece();
                    writer.write(String.join("\t", move.from().toString(), move.to().toString(),
                            move.piece().color().name(), move.piece().type().name(),
                            captured == null ? "-" : captured.color().name(),
                            captured == null ? "-" : captured.type().name()));
                    writer.newLine();
                }
            }

            try {
                Files.move(temporary, target, StandardCopyOption.ATOMIC_MOVE,
                        StandardCopyOption.REPLACE_EXISTING);
            } catch (AtomicMoveNotSupportedException error) {
                Files.move(temporary, target, StandardCopyOption.REPLACE_EXISTING);
            }
        } finally {
            Files.deleteIfExists(temporary);
        }
    }
}
