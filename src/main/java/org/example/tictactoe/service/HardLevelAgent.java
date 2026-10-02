package org.example.tictactoe.service;


import org.example.tictactoe.domain.TicTacToe;

import java.util.List;

import static org.example.tictactoe.domain.TicTacToe.getTriplets;


public class HardLevelAgent extends Agent{

    public HardLevelAgent(String[][] board, List<String> emptyCells) {
        super(board, emptyCells);
    }

    public void setCoordinates(TicTacToe.Player player) {
        String aiSymbol = player.getSymbol();
        String opponentSymbol = aiSymbol.equals("X") ? "O" : "X";

        int bestScore = Integer.MIN_VALUE;
        int bestRow = -1;
        int bestCol = -1;

        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if (board[r][c] == null) {
                    // Make the move
                    board[r][c] = aiSymbol;

                    // Call minimax with false because opponent moves next
                    int score = minimax(false, aiSymbol, opponentSymbol);

                    // Undo the move
                    board[r][c] = null;

                    if (score > bestScore) {
                        bestScore = score;
                        bestRow = r;
                        bestCol = c;
                    }
                }
            }
        }

        // Apply the best move found
        makeComputerMove(player, bestRow, bestCol);
    }

    private int minimax(boolean isMaximizing, String aiSymbol, String opponentSymbol) {
        // Terminal state evaluation
        String winner = checkWinnerForMinimax();
        if (aiSymbol.equals(winner)) {
            return 10;
        } else if (opponentSymbol.equals(winner)) {
            return -10;
        } else if (isBoardFullForMinimax()) {
            return 0;
        }

        if (isMaximizing) {
            int bestScore = Integer.MIN_VALUE;
            for (int r = 0; r < 3; r++) {
                for (int c = 0; c < 3; c++) {
                    if (board[r][c] == null) {
                        board[r][c] = aiSymbol;
                        int score = minimax(false, aiSymbol, opponentSymbol);
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
                        int score = minimax(true, aiSymbol, opponentSymbol);
                        board[r][c] = null;
                        bestScore = Math.min(score, bestScore);
                    }
                }
            }
            return bestScore;
        }
    }

    // Helper method to evaluate wins without mutating game status
    private String checkWinnerForMinimax() {
        String[][] triplets = getTriplets(board);
        for (String[] line : triplets) {
            if (line[0] != null && line[0].equals(line[1]) && line[1].equals(line[2])) {
                return line[0];
            }
        }
        return null;
    }

    // Helper method to evaluate full board state without relying on emptyCells
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
