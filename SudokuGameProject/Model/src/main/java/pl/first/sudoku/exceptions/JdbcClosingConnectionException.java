package pl.first.sudoku.exceptions;

import java.util.ResourceBundle;

public class JdbcClosingConnectionException extends JdbcException {
    public JdbcClosingConnectionException(String message, Throwable cause) {
        super(message, cause);
    }

    public JdbcClosingConnectionException(String message) {
        super(message);
    }

    @Override
    public String getLocalizedMessage() {
        ResourceBundle bundle = ResourceBundle.getBundle("pl.first.sudoku.Language");
        return bundle.getObject("connectionNotClosed").toString();
    }
}
