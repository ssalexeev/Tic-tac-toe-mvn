package org.example.tictactoe.enums;

public enum GameState {
    START("start"),
    EXIT("exit");

    private final String value;

    GameState(String value) {
        this.value = value;
    }

    public static GameState fromValue(String value) {
        for (GameState state : values()) {
            if (state.value.equals(value)) return state;
        }
        throw new IllegalArgumentException("Unknown player type: " + value);
    }

    public String getValue() {
        return value;
    }
}
