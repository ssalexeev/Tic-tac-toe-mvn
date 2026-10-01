package org.example.tictactoe.excepion;

public class CoordinatesOutOfBoundException extends RuntimeException{
    public CoordinatesOutOfBoundException(String message) {
        super(message);
    }
}
