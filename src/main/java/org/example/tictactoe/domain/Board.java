package org.example.tictactoe.domain;

import org.example.tictactoe.domain.enums.GameStatus;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private final String[][] grid = new String[3][3];
    private final List<String> emptyCells = new ArrayList<>();

    public Board() {
        initEmptyCells();
    }

    private void initEmptyCells() {
        for (int row = 1; row <= 3; row++) {
            for (int col = 1; col <= 3; col++) {
                emptyCells.add(row + "" + col);
            }
        }
    }

    public boolean placeSymbol(int x, int y, String symbol) {
        if (x < 0 || x > 2 || y < 0 || y > 2) {
            return false;
        }
        if (grid[x][y] != null) {
            return false;
        }
        grid[x][y] = symbol;
        emptyCells.remove((x + 1) + "" + (y + 1));
        return true;
    }

    public String[][] getGrid() {
        return grid;
    }

    public List<String> getEmptyCells() {
        return emptyCells;
    }

    public GameStatus checkStatus() {
        String[][] triplets = getTriplets(grid);

        for (String[] line : triplets) {
            if (line[0] != null && line[0].equals(line[1]) && line[1].equals(line[2])) {
                return GameStatus.WIN;
            }
        }

        if (!emptyCells.isEmpty()) {
            return GameStatus.NOT_FINISHED;
        }

        return GameStatus.DRAW;
    }

    public String getWinnerSymbol() {
        String[][] triplets = getTriplets(grid);
        for (String[] line : triplets) {
            if (line[0] != null && line[0].equals(line[1]) && line[1].equals(line[2])) {
                return line[0];
            }
        }
        return null;
    }

    public static String[][] getTriplets(String[][] grid) {
        String[][] triplets = new String[8][3];

        for (int i = 0; i < 3; i++) {
            triplets[i][0] = grid[i][0];
            triplets[i][1] = grid[i][1];
            triplets[i][2] = grid[i][2];
        }

        for (int j = 0; j < 3; j++) {
            triplets[3 + j][0] = grid[0][j];
            triplets[3 + j][1] = grid[1][j];
            triplets[3 + j][2] = grid[2][j];
        }

        triplets[6][0] = grid[0][0];
        triplets[6][1] = grid[1][1];
        triplets[6][2] = grid[2][2];

        triplets[7][0] = grid[0][2];
        triplets[7][1] = grid[1][1];
        triplets[7][2] = grid[2][0];

        return triplets;
    }
}