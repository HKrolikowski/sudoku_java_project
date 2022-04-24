package pl.first.sudoku.exceptions;

import java.sql.SQLException;
import java.util.ResourceBundle;

public class DbConnectionException extends SQLException {
    public DbConnectionException(String message, Throwable cause) {
        super(message, cause);
    }

    public DbConnectionException(String message) {
        super(message);
    }

    @Override
    public String getLocalizedMessage() {
        ResourceBundle bundle = ResourceBundle.getBundle("pl.first.sudoku.Language");
        return bundle.getObject("connectingFailed").toString();
    }
}
