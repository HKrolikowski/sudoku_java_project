package pl.first.sudoku.exceptions;

public class FileReadException extends DaoException {
    public FileReadException(String message, Throwable cause) {
        super(message, cause);
    }
}
