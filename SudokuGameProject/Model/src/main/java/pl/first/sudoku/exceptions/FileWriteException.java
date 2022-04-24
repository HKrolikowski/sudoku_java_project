package pl.first.sudoku.exceptions;

public class FileWriteException extends DaoException {
    public FileWriteException(String message, Throwable cause) {
        super(message, cause);
    }
}
