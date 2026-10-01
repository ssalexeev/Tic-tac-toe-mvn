package org.example.tictactoe;

public enum GameStatus {
    WIN("Wins"),
    DRAW("Draw"),
    NOT_FINISHED("Game not finished");

    private final String value;

    GameStatus(String value) {
        this.value = value;
    }


    public static GameStatus fromValue(String value) {
        for (GameStatus status : values()) {
            if (status.value.equals(value)) return status;
        }
        throw new IllegalArgumentException("Unknown player type: " + value);
    }
    public String getValue() {
        return value;
    }
}
