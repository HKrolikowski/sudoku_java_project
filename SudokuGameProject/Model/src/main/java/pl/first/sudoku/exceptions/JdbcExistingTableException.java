package pl.first.sudoku.exceptions;

import java.util.ResourceBundle;

public class JdbcExistingTableException extends JdbcException {
    public JdbcExistingTableException(String message, Throwable cause) {
        super(message, cause);
    }

    public JdbcExistingTableException(String message) {
        super(message);
    }

    @Override
    public String getLocalizedMessage() {
        ResourceBundle bundle = ResourceBundle.getBundle("pl.first.sudoku.Language");
        return bundle.getObject("tableExists").toString();
    }
}
