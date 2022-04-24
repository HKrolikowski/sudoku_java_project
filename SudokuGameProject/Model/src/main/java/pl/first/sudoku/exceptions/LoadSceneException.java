package pl.first.sudoku.exceptions;

import java.io.IOException;

public class LoadSceneException extends IOException {
    public LoadSceneException(String message, Throwable cause) {
        super(message, cause);
    }
}
