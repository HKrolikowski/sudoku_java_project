package pl.first.sudoku.exceptions;

public class JdbcException extends DaoException {

    public JdbcException(String message, Throwable cause) {
        super(message, cause);
    }

    public JdbcException(Throwable cause) {
        super(cause);
    }

    public JdbcException(String message) {
        super(message);
    }

    public JdbcException() {
        super();
    }
}
