package pl.first.sudoku;

public class SudokuBoardException extends IllegalArgumentException {
    public SudokuBoardException(final String message) {
        super(message);
    }
}
