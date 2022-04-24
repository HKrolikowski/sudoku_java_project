package pl.first.sudoku.exceptions;

import java.sql.SQLException;
import java.util.ResourceBundle;

public class DbConnectionClosingException extends SQLException {
    public DbConnectionClosingException(String message, Throwable cause) {
        super(message, cause);
    }

    public DbConnectionClosingException(String message) {
        super(message);
    }

    @Override
    public String getLocalizedMessage() {
        ResourceBundle bundle = ResourceBundle.getBundle("pl.first.sudoku.Language");
        return bundle.getObject("connectionNotClosed").toString();
    }
}
