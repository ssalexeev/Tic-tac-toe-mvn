package org.example;

import tictactoe.enums.GameState;
import tictactoe.enums.GameStatus;
import tictactoe.enums.PlayerType;
import tictactoe.service.EasyLevelAgent;
import tictactoe.service.HardLevelAgent;
import tictactoe.service.MediumLevelAgent;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;


public class TicTacToe {
    private String[][] board;

    private Player firsPlayer;
    private Player secondPlayer;

    private GameState gameState;
    private GameStatus gameStatus = GameStatus.NOT_FINISHED;

    private List<String> emptyCells;

    private Scanner scanner;

    private  HardLevelAgent hardLevelAgent;
    private  MediumLevelAgent mediumLevelAgent;
    private  EasyLevelAgent easyLevelAgent;


    public void initEmptyCells() {
        this.emptyCells = new ArrayList<>();

        for (int row = 1; row <= 3; row++) {
            for (int col = 1; col <= 3; col++) {
                emptyCells.add(row + "" + col);
            }
        }
    }



    public void startGame() {
        this.scanner = new Scanner(System.in);

        while (true) {
            setStartOptions();
            if (gameState == GameState.EXIT) {
                break;
            }

            initContext();

            // Active Game Loop
            while (gameStatus == GameStatus.NOT_FINISHED) {
                inputPlayerCoordinates(firsPlayer);
                checkGameStatus();
                if (gameStatus != GameStatus.NOT_FINISHED) break;

                inputPlayerCoordinates(secondPlayer);
                checkGameStatus();
            }
        }
        scanner.close();
    }

    private void initContext(){
        board = new String[3][3];
        initEmptyCells();
        this.hardLevelAgent = new HardLevelAgent(board,emptyCells);
        this.mediumLevelAgent = new MediumLevelAgent(board,emptyCells);
        this.easyLevelAgent = new EasyLevelAgent(board,emptyCells);

        gameStatus = GameStatus.NOT_FINISHED;
        printBoard();
    }

    private void inputPlayerCoordinates(Player player) {
        switch (player.type) {
            case USER -> enterCoordinates(player);
            case AI_EASY -> easyLevelAgent.setCoordinates(player);
            case AI_MEDIUM -> mediumLevelAgent.setCoordinates(player);
            case AI_HARD -> hardLevelAgent.setCoordinates(player);
        }
    }

    private void setStartOptions() {
        while (true) {
            System.out.print("Input command: ");
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) continue;

            String[] parts = line.split("\\s+");
            try {
                gameState = GameState.fromValue(parts[0]);
                if (gameState == GameState.EXIT) {
                    return;
                }

                if (parts.length < 3) {
                    System.out.println("Bad parameters!");
                    continue;
                }

                firsPlayer = new Player(PlayerType.fromValue(parts[1]), "X");
                secondPlayer = new Player(PlayerType.fromValue(parts[2]), "O");
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Bad parameters!");
            }
        }
    }

    private void checkGameStatus() {
        String[][] triplets = getTriplets(board);

        for (String[] line : triplets) {
            if (line[0] != null && line[0].equals(line[1]) && line[1].equals(line[2])) {
                System.out.println(line[0] + " wins");
                this.gameStatus = GameStatus.WIN;
                return;
            }
        }

        if (!emptyCells.isEmpty()) {
            this.gameStatus = GameStatus.NOT_FINISHED;
            return;
        }

        System.out.println("Draw");
        this.gameStatus = GameStatus.DRAW;
    }


    public void enterCoordinates(Player player) {
        int x;
        int y;
        while (true) {
            System.out.print("Enter the coordinates: ");
            try {
                x = scanner.nextInt() - 1;
                y = scanner.nextInt() - 1;
            } catch (InputMismatchException e) {
                System.out.println("You should enter numbers!");
                scanner.nextLine();
                continue;
            }

            // Coordinates bound check
            if (x < 0 || x > 2 || y < 0 || y > 2) {
                System.out.println("Coordinates should be from 1 to 3!");
                continue;
            }
            // Cell is occupied check
            if (board[x][y] != null) {
                System.out.println("This cell is occupied! Choose another one!");
                continue;
            }

            break;
        }

        String key = (x + 1) + "" + (y + 1);
        emptyCells.remove(key);

        this.board[x][y] = player.symbol;
        printBoard();
    }

    public static String[][] getTriplets(String[][] table) {
        String[][] triplets = new String[8][3];

        for (int i = 0; i < 3; i++) {
            triplets[i][0] = table[i][0];
            triplets[i][1] = table[i][1];
            triplets[i][2] = table[i][2];
        }

        for (int j = 0; j < 3; j++) {
            triplets[3 + j][0] = table[0][j];
            triplets[3 + j][1] = table[1][j];
            triplets[3 + j][2] = table[2][j];
        }

        triplets[6][0] = table[0][0];
        triplets[6][1] = table[1][1];
        triplets[6][2] = table[2][2];

        triplets[7][0] = table[0][2];
        triplets[7][1] = table[1][1];
        triplets[7][2] = table[2][0];

        return triplets;
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


    public static class Player {
        private PlayerType type;
        private String symbol;

        public Player(PlayerType type, String symbol) {
            this.type = type;
            this.symbol = symbol;
        }

        public PlayerType getType() {
            return type;
        }

        public String getSymbol() {
            return symbol;
        }
    }
}


