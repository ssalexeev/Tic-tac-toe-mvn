package agent;

import org.example.tictactoe.agent.MediumLevelAgent;
import org.example.tictactoe.domain.Player;
import org.example.tictactoe.domain.enums.PlayerType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class MediumLevelAgentTest {

    private String[][] board;
    private List<String> emptyCells;
    private MediumLevelAgent agent;
    private Player aiPlayer;

    @BeforeEach
    void setUp() {
        board = new String[3][3];
        emptyCells = new ArrayList<>();
        agent = new MediumLevelAgent(board, emptyCells);
        aiPlayer = new Player(PlayerType.AI_MEDIUM, "O");
    }

    private void addEmptyCell(int r, int c) {
        emptyCells.add((r + 1) + "" + (c + 1));
    }

    @Test
    void testPrioritizesWinningMoveOverBlocking() {
        // Row 0 has two "O"s (AI) -> AI can win at (0, 2)
        board[0][0] = "O"; board[0][1] = "O"; addEmptyCell(0, 2);

        // Row 1 has two "X"s (Opponent) -> AI could block at (1, 2)
        board[1][0] = "X"; board[1][1] = "X"; addEmptyCell(1, 2);

        agent.setCoordinates(aiPlayer);

        assertEquals("O", board[0][2], "AI should complete its winning move");
        assertNull(board[1][2], "AI should not block if it can win immediately");
    }

    @Test
    void testBlocksOpponentWinningMove() {
        board[0][0] = "X";
        board[1][1] = "X";
        addEmptyCell(2, 2); // (2,2) -> grid 3,3

        agent.setCoordinates(aiPlayer);

        assertEquals("O", board[2][2], "AI should block opponent at (2,2)");
    }

    @Test
    void testPlaysRandomlyWhenNoWinOrBlockAvailable() {
        board[0][0] = "X"; addEmptyCell(0, 1); addEmptyCell(0, 2);
        addEmptyCell(1, 0); addEmptyCell(1, 1); addEmptyCell(1, 2);
        addEmptyCell(2, 0); addEmptyCell(2, 1); addEmptyCell(2, 2);

        agent.setCoordinates(aiPlayer);

        assertEquals(7, emptyCells.size());
    }
}
