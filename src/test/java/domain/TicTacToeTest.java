package domain;

import org.example.tictactoe.domain.TicTacToe;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class TicTacToeTest {

    @Test
    void testGameOutputWithCustomStreams() {
        String input = String.join("\n",
                "start user user",
                "1 1", // X
                "2 1", // O
                "1 2", // X
                "2 2", // O
                "1 3", // X wins
                "exit"
        ) + "\n";

        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        TicTacToe game = new TicTacToe(in, new PrintStream(out));
        game.startGame();

        String output = out.toString();
        assertTrue(output.contains("X wins"));
    }
}
