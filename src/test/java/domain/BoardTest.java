package domain;

import org.example.tictactoe.domain.Board;
import org.example.tictactoe.domain.enums.GameStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BoardTest {
    private Board board;

    @BeforeEach
    void setUp() {
        board = new Board();
    }

    @Test
    void testPlaceSymbolValidAndOccupied() {
        assertTrue(board.placeSymbol(0, 0, "X"));
        assertFalse(board.placeSymbol(0, 0, "O"), "Should fail when cell is already occupied");
    }

    @Test
    void testWinConditionRow() {
        board.placeSymbol(0, 0, "X");
        board.placeSymbol(0, 1, "X");
        board.placeSymbol(0, 2, "X");

        assertEquals(GameStatus.WIN, board.checkStatus());
        assertEquals("X", board.getWinnerSymbol());
    }

    @Test
    void testDrawCondition() {
        // Row 1: X O X
        board.placeSymbol(0, 0, "X"); board.placeSymbol(0, 1, "O"); board.placeSymbol(0, 2, "X");
        // Row 2: X O O
        board.placeSymbol(1, 0, "X"); board.placeSymbol(1, 1, "O"); board.placeSymbol(1, 2, "O");
        // Row 3: O X X
        board.placeSymbol(2, 0, "O"); board.placeSymbol(2, 1, "X"); board.placeSymbol(2, 2, "X");

        assertEquals(GameStatus.DRAW, board.checkStatus());
    }
}