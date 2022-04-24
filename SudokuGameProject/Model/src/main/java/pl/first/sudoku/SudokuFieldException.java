package pl.first.sudoku;

public class SudokuFieldException extends IllegalArgumentException {
    public SudokuFieldException(final String message) {
        super(message);
    }
}
