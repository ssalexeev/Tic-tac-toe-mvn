package org.example.tictactoe.service;

import org.example.tictactoe.TicTacToe;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Agent {
    protected String[][] board;
    protected final List<String> emptyCells;


    public Agent(String[][] board, List<String> emptyCells) {
        this.board = board;
        this.emptyCells = emptyCells;
    }

    protected void makeComputerMove(TicTacToe.Player player, int x, int y) {
        this.board[x][y] = player.getSymbol();

        String key = (x + 1) + "" + (y + 1);
        emptyCells.remove(key);

        System.out.println("Making move level \"" + player.getType().getValue() + "\"");
        printBoard();
    }

    public abstract void setCoordinates(TicTacToe.Player player);

    protected void setRandomCoordinates(TicTacToe.Player player) {
        int lastIdx = emptyCells.size() - 1;
        int randIdx = ThreadLocalRandom.current().nextInt(emptyCells.size());

        String[] pair = emptyCells.get(randIdx).split("");
        int x = Integer.parseInt(pair[0]) - 1;
        int y = Integer.parseInt(pair[1]) - 1;

        this.board[x][y] = player.getSymbol();
        System.out.println("Making move level \"" + player.getType().getValue() + "\" ");
        printBoard();

        emptyCells.set(randIdx, emptyCells.get(lastIdx));
        emptyCells.remove(lastIdx);

    }

    public void printBoard() {
        System.out.println("---------");
        for (String[] row : board) {
            System.out.print("| ");
            for (String val : row) {
                if (val == null) {
                    System.out.print(" " + " ");
                } else {
                    System.out.print(val + " ");
                }

            }
            System.out.print("|");
            System.out.println();
        }
        System.out.println("---------");
    }
}
