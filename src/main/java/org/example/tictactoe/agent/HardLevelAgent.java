package org.example.tictactoe.agent;

import org.example.tictactoe.domain.Player;

import java.util.List;

public class HardLevelAgent extends Agent {

    public HardLevelAgent(String[][] board, List<String> emptyCells) {
        super(board, emptyCells);
    }

    @Override
    public void setCoordinates(Player player) {
        String aiSymbol = player.getSymbol();
        String opponentSymbol = aiSymbol.equals("X") ? "O" : "X";

        int bestScore = Integer.MIN_VALUE;
        int bestRow = -1;
        int bestCol = -1;

        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if (board[r][c] == null) {
                    board[r][c] = aiSymbol;

                    int score = minimax(0, false, aiSymbol, opponentSymbol);

                    board[r][c] = null;

                    if (score > bestScore) {
                        bestScore = score;
                        bestRow = r;
                        bestCol = c;
                    }
                }
            }
        }

        makeComputerMove(player, bestRow, bestCol);
    }

    private int minimax(int depth, boolean isMaximizing, String aiSymbol, String opponentSymbol) {
        String winner = checkWinnerForMinimax();

        if (aiSymbol.equals(winner)) {
            return 10 - depth;
        } else if (opponentSymbol.equals(winner)) {
            return depth - 10;
        } else if (isBoardFullForMinimax()) {
            return 0;
        }

        if (isMaximizing) {
            int bestScore = Integer.MIN_VALUE;
            for (int r = 0; r < 3; r++) {
                for (int c = 0; c < 3; c++) {
                    if (board[r][c] == null) {
                        board[r][c] = aiSymbol;
                        int score = minimax(depth + 1, false, aiSymbol, opponentSymbol);
                        board[r][c] = null;
                        bestScore = Math.max(score, bestScore);
                    }
                }
            }
            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;
            for (int r = 0; r < 3; r++) {
                for (int c = 0; c < 3; c++) {
                    if (board[r][c] == null) {
                        board[r][c] = opponentSymbol;
                        int score = minimax(depth + 1, true, aiSymbol, opponentSymbol);
                        board[r][c] = null;
                        bestScore = Math.min(score, bestScore);
                    }
                }
            }
            return bestScore;
        }
    }

    private String checkWinnerForMinimax() {
        // Rows & Columns check
        for (int i = 0; i < 3; i++) {
            if (board[i][0] != null && board[i][0].equals(board[i][1]) && board[i][1].equals(board[i][2])) {
                return board[i][0];
            }
            if (board[0][i] != null && board[0][i].equals(board[1][i]) && board[1][i].equals(board[2][i])) {
                return board[0][i];
            }
        }

        if (board[0][0] != null && board[0][0].equals(board[1][1]) && board[1][1].equals(board[2][2])) {
            return board[0][0];
        }
        if (board[0][2] != null && board[0][2].equals(board[1][1]) && board[1][1].equals(board[2][0])) {
            return board[0][2];
        }

        return null;
    }

    private boolean isBoardFullForMinimax() {
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if (board[r][c] == null) {
                    return false;
                }
            }
        }
        return true;
    }
}
