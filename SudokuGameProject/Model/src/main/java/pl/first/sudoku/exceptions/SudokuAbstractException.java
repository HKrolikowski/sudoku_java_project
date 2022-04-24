package pl.first.sudoku.exceptions;

public class SudokuAbstractException extends IllegalArgumentException {
    public SudokuAbstractException(String message) {
        super(message);
    }
}