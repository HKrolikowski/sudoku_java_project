package pl.first.sudoku.exceptions;

import java.sql.SQLException;
import java.util.ResourceBundle;

public class DbTableExistsException extends SQLException {
    public DbTableExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public DbTableExistsException(String message) {
        super(message);
    }

    @Override
    public String getLocalizedMessage() {
        ResourceBundle bundle = ResourceBundle.getBundle("pl.first.sudoku.Language");
        return bundle.getObject("tableExists").toString();
    }
}
