package agent;

import org.example.tictactoe.agent.EasyLevelAgent;
import org.example.tictactoe.domain.Player;
import org.example.tictactoe.domain.enums.PlayerType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EasyLevelAgentTest {
    private String[][] board;
    private List<String> emptyCells;
    private EasyLevelAgent agent;
    private Player player;

    @BeforeEach
    void setUp() {
        board = new String[3][3];
        emptyCells = new ArrayList<>();
        for (int r = 1; r <= 3; r++) {
            for (int c = 1; c <= 3; c++) {
                emptyCells.add(r + "" + c);
            }
        }
        agent = new EasyLevelAgent(board, emptyCells);
        player = new Player(PlayerType.AI_EASY, "O");
    }

    @Test
    void testSetCoordinatesPlacesSymbolInEmptyCell() {
        agent.setCoordinates(player);

        assertEquals(8, emptyCells.size());

        int count = 0;
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if ("O".equals(board[r][c])) {
                    count++;
                }
            }
        }
        assertEquals(1, count);
    }
}
