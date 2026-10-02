package org.example.tictactoe.service;

import org.example.tictactoe.domain.TicTacToe;

import java.util.List;

public class EasyLevelAgent extends Agent{
    public EasyLevelAgent(String[][] board, List<String> emptyCells) {
        super(board, emptyCells);
    }

    public void setCoordinates(TicTacToe.Player player){
        super.setRandomCoordinates(player);
    }


}
