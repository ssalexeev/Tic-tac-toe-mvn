package agent;

import org.example.tictactoe.agent.HardLevelAgent;
import org.example.tictactoe.domain.Player;
import org.example.tictactoe.domain.enums.PlayerType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HardLevelAgentTest {

    private String[][] board;
    private List<String> emptyCells;
    private HardLevelAgent agent;
    private Player aiPlayer;

    @BeforeEach
    void setUp() {
        board = new String[3][3];
        emptyCells = new ArrayList<>();
        agent = new HardLevelAgent(board, emptyCells);
        aiPlayer = new Player(PlayerType.AI_HARD, "X");
    }

    private void addEmptyCell(int r, int c) {
        emptyCells.add((r + 1) + "" + (c + 1));
    }

    @Test
    void testTakesImmediateWinningMove() {
        // Board: X | X | _
        board[0][0] = "X"; board[0][1] = "X"; addEmptyCell(0, 2);
        board[1][0] = "O"; board[1][1] = "O"; addEmptyCell(1, 2);

        agent.setCoordinates(aiPlayer);

        assertEquals("X", board[0][2], "Minimax should pick the immediate win");
    }

    @Test
    void testBlocksOpponentWin() {
        // Opponent "O" is about to win on diagonal (0,0) and (1,1)
        board[0][0] = "O";
        board[1][1] = "O";
        addEmptyCell(2, 2);
        addEmptyCell(0, 1);

        agent.setCoordinates(aiPlayer);

        assertEquals("X", board[2][2], "Minimax should block the opponent's winning cell");
    }

    @Test
    void testPreventsOpponentFork() {
        // Corner trap scenario: Opponent has opposite corners (0,0) and (2,2)
        // AI MUST play an edge (e.g., 0,1; 1,0; 1,2; 2,1) to avoid giving opponent a fork opportunity
        board[0][0] = "O"; board[1][1] = "X"; board[2][2] = "O";
        addEmptyCell(0, 1); addEmptyCell(1, 0); addEmptyCell(1, 2); addEmptyCell(2, 1);
        addEmptyCell(0, 2); addEmptyCell(2, 0);

        agent.setCoordinates(aiPlayer);

        // One of the side/edge cells should be picked
        boolean playedEdge = "X".equals(board[0][1]) || "X".equals(board[1][0]) ||
                "X".equals(board[1][2]) || "X".equals(board[2][1]);
        assertTrue(playedEdge, "Hard AI should play an edge cell to prevent a fork");
    }
}
