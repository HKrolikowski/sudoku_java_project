package pl.first.sudoku;

import java.io.Serializable;

public interface SudokuObserver extends Serializable {
    void update();
}
