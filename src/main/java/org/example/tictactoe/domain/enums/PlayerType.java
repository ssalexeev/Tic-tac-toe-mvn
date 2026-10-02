package org.example.tictactoe.domain.enums;

public enum PlayerType {
    USER("user"),

    AI_EASY("easy"),
    AI_MEDIUM("medium"),
    AI_HARD("hard");

    private final String value;

    PlayerType(String value) {
        this.value = value;
    }


    public static PlayerType fromValue(String value) {
        for (PlayerType type : values()) {
            if (type.value.equals(value)) return type;
        }
        throw new IllegalArgumentException("Unknown player type: " + value);
    }
    public String getValue() {
        return value;
    }

}
