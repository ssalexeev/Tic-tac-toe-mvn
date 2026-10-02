package org.example.tictactoe.domain;


import org.example.tictactoe.domain.enums.PlayerType;

public class Player {
    private final PlayerType type;
    private final String symbol;

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
