package pl.first.sudoku.exceptions;

import java.util.ResourceBundle;

public class JdbcWriteException extends JdbcException {
    public JdbcWriteException(String message, Throwable cause) {
        super(message, cause);
    }

    public JdbcWriteException(String message) {
        super(message);
    }

    @Override
    public String getLocalizedMessage() {
        ResourceBundle bundle = ResourceBundle.getBundle("pl.first.sudoku.Language");
        return bundle.getObject("writeFailed").toString();
    }
}
