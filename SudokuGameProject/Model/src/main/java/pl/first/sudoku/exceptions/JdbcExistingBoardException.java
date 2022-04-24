package pl.first.sudoku.exceptions;

import java.util.ResourceBundle;

public class JdbcExistingBoardException extends JdbcException {
    public JdbcExistingBoardException(String message, Throwable cause) {
        super(message, cause);
    }

    public JdbcExistingBoardException(String message) {
        super(message);
    }

    @Override
    public String getLocalizedMessage() {
        ResourceBundle bundle = ResourceBundle.getBundle("pl.first.sudoku.Language");
        return bundle.getObject("boardExists").toString();
    }
}
