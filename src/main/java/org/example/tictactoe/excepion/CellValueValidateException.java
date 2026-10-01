package org.example.tictactoe.excepion;

public class CellValueValidateException extends RuntimeException{
    public CellValueValidateException(String message) {
        super(message);
    }
}
