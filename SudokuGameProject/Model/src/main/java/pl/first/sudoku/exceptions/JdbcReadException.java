package pl.first.sudoku.exceptions;

import java.util.ResourceBundle;

public class JdbcReadException extends JdbcException {
    public JdbcReadException(String message, Throwable cause) {
        super(message, cause);
    }

    public JdbcReadException(String message) {
        super(message);
    }

    @Override
    public String getLocalizedMessage() {
        ResourceBundle bundle = ResourceBundle.getBundle("pl.first.sudoku.Language");
        return bundle.getObject("readFailed").toString();
    }
}
