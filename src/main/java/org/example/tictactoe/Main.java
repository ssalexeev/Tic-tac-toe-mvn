package org.example.tictactoe;

import org.example.tictactoe.domain.TicTacToe;

public class Main {
    public static void main(String[] args) {
        TicTacToe tacToeService = new TicTacToe();
        tacToeService.startGame();
    }
}
