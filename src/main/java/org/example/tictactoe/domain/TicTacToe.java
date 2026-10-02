package org.example.tictactoe.domain;

import org.example.tictactoe.domain.enums.GameState;
import org.example.tictactoe.domain.enums.GameStatus;
import org.example.tictactoe.domain.enums.PlayerType;
import org.example.tictactoe.agent.EasyLevelAgent;
import org.example.tictactoe.agent.HardLevelAgent;
import org.example.tictactoe.agent.MediumLevelAgent;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.InputMismatchException;
import java.util.Scanner;

public class TicTacToe {
    private final Scanner scanner;
    private final PrintStream out;

    private Board board;
    private Player firstPlayer;
    private Player secondPlayer;
    private GameState gameState;
    private GameStatus gameStatus;

    private HardLevelAgent hardLevelAgent;
    private MediumLevelAgent mediumLevelAgent;
    private EasyLevelAgent easyLevelAgent;

    // Default constructor uses standard I/O
    public TicTacToe() {
        this(System.in, System.out);
    }

    // Injectable streams for unit testing
    public TicTacToe(InputStream in, PrintStream out) {
        this.scanner = new Scanner(in);
        this.out = out;
    }

    public void startGame() {
        while (true) {
            setStartOptions();
            if (gameState == GameState.EXIT) {
                break;
            }

            initContext();

            while (gameStatus == GameStatus.NOT_FINISHED) {
                executeTurn(firstPlayer);
                if (gameStatus != GameStatus.NOT_FINISHED) break;

                executeTurn(secondPlayer);
            }
        }
    }

    private void initContext() {
        this.board = new Board();
        this.hardLevelAgent = new HardLevelAgent(board.getGrid(), board.getEmptyCells());
        this.mediumLevelAgent = new MediumLevelAgent(board.getGrid(), board.getEmptyCells());
        this.easyLevelAgent = new EasyLevelAgent(board.getGrid(), board.getEmptyCells());

        this.gameStatus = GameStatus.NOT_FINISHED;
        printBoard();
    }

    private void executeTurn(Player player) {
        inputPlayerCoordinates(player);
        this.gameStatus = board.checkStatus();

        if (this.gameStatus == GameStatus.WIN) {
            out.println(board.getWinnerSymbol() + " wins");
        } else if (this.gameStatus == GameStatus.DRAW) {
            out.println("Draw");
        }
    }

    private void inputPlayerCoordinates(Player player) {
        switch (player.getType()) {
            case USER -> enterCoordinates(player);
            case AI_EASY -> easyLevelAgent.setCoordinates(player);
            case AI_MEDIUM -> mediumLevelAgent.setCoordinates(player);
            case AI_HARD -> hardLevelAgent.setCoordinates(player);
        }
    }

    public void enterCoordinates(Player player) {
        int x, y;
        while (true) {
            out.print("Enter the coordinates: ");
            try {
                x = scanner.nextInt() - 1;
                y = scanner.nextInt() - 1;
            } catch (InputMismatchException e) {
                out.println("You should enter numbers!");
                scanner.nextLine();
                continue;
            }

            if (x < 0 || x > 2 || y < 0 || y > 2) {
                out.println("Coordinates should be from 1 to 3!");
                continue;
            }

            if (!board.placeSymbol(x, y, player.getSymbol())) {
                out.println("This cell is occupied! Choose another one!");
                continue;
            }

            break;
        }
        printBoard();
    }

    private void setStartOptions() {
        while (true) {
            out.print("Input command: ");
            if (!scanner.hasNextLine()) {
                gameState = GameState.EXIT;
                return;
            }

            String line = scanner.nextLine().trim();
            if (line.isEmpty()) continue;

            String[] parts = line.split("\\s+");
            try {
                gameState = GameState.fromValue(parts[0]);
                if (gameState == GameState.EXIT) {
                    return;
                }

                if (parts.length < 3) {
                    out.println("Bad parameters!");
                    continue;
                }

                firstPlayer = new Player(PlayerType.fromValue(parts[1]), "X");
                secondPlayer = new Player(PlayerType.fromValue(parts[2]), "O");
                break;
            } catch (IllegalArgumentException e) {
                out.println("Bad parameters!");
            }
        }
    }

    public void printBoard() {
        out.println("---------");
        for (String[] row : board.getGrid()) {
            out.print("| ");
            for (String val : row) {
                out.print((val == null ? " " : val) + " ");
            }
            out.println("|");
        }
        out.println("---------");
    }
}

