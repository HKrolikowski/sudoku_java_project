package pl.first.sudoku.exceptions;

import java.util.ResourceBundle;

public class JdbcConnectingToBaseException extends JdbcException {
    public JdbcConnectingToBaseException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String getLocalizedMessage() {
        ResourceBundle bundle = ResourceBundle.getBundle("pl.first.sudoku.Language");
        return bundle.getObject("connectingFailed").toString();
    }
}
