package pl.first.sudoku.exceptions;

import java.sql.SQLException;
import java.util.ResourceBundle;

public class DbExecuteQueryException extends SQLException {
    public DbExecuteQueryException(String message, Throwable cause) {
        super(message, cause);
    }

    public DbExecuteQueryException(String message) {
        super(message);
    }

    @Override
    public String getLocalizedMessage() {
        ResourceBundle bundle = ResourceBundle.getBundle("pl.first.sudoku.Language");
        return bundle.getObject("queryExecutionFailed").toString();
    }
}
