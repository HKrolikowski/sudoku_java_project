package pl.first.sudoku.exceptions;

public class SudokuFieldSetException extends IllegalArgumentException {
    public SudokuFieldSetException(String message) {
        super(message);
    }
}
