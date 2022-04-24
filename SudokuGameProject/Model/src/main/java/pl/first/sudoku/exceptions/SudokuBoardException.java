package pl.first.sudoku.exceptions;

public class SudokuBoardException extends IllegalArgumentException {
    public SudokuBoardException(String message) {
        super(message);
    }
}
