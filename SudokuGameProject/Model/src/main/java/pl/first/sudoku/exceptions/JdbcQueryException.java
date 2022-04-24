package pl.first.sudoku.exceptions;

import java.util.ResourceBundle;

public class JdbcQueryException extends JdbcException {
    public JdbcQueryException(String message, Throwable cause) {
        super(message, cause);
    }

    public JdbcQueryException(String message) {
        super(message);
    }

    @Override
    public String getLocalizedMessage() {
        ResourceBundle bundle = ResourceBundle.getBundle("pl.first.sudoku.Language");
        return bundle.getObject("queryFailed").toString();
    }

    public JdbcQueryException() {
        super();
    }
}
