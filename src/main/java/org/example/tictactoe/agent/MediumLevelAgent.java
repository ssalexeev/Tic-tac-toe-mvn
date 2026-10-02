package org.example.tictactoe.agent;

import org.example.tictactoe.domain.Player;

import java.util.List;

public class MediumLevelAgent extends Agent{

    public MediumLevelAgent(String[][] board, List<String> emptyCells) {
        super(board, emptyCells);
    }


    public void setCoordinates(Player player) {
        String opponentSymbol = player.getSymbol().equals("X") ? "O" : "X";

        // 1. Check for a Winning Move
        int[] winningMove = findWinningOrBlockingMove(player.getSymbol());
        if (winningMove != null) {
            makeComputerMove(player, winningMove[0], winningMove[1]);
            return;
        }

        // 2. Check for a Blocking Move
        int[] blockingMove = findWinningOrBlockingMove(opponentSymbol);
        if (blockingMove != null) {
            makeComputerMove(player, blockingMove[0], blockingMove[1]);
            return;
        }

        super.setRandomCoordinates(player);
    }

    private int[] findWinningOrBlockingMove(String targetSymbol) {
        int[][][] lines = {
                // Rows
                {{0, 0}, {0, 1}, {0, 2}},
                {{1, 0}, {1, 1}, {1, 2}},
                {{2, 0}, {2, 1}, {2, 2}},
                // Columns
                {{0, 0}, {1, 0}, {2, 0}},
                {{0, 1}, {1, 1}, {2, 1}},
                {{0, 2}, {1, 2}, {2, 2}},
                // Diagonals
                {{0, 0}, {1, 1}, {2, 2}},
                {{0, 2}, {1, 1}, {2, 0}}
        };

        for (int[][] line : lines) {
            int targetCount = 0;
            int emptyCount = 0;
            int[] emptyCell = null;

            for (int[] cell : line) {
                String val = board[cell[0]][cell[1]];
                if (targetSymbol.equals(val)) {
                    targetCount++;
                } else if (val == null) {
                    emptyCount++;
                    emptyCell = cell;
                }
            }

            if (targetCount == 2 && emptyCount == 1) {
                return emptyCell;
            }
        }

        return null;
    }
}
