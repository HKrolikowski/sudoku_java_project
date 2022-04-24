package pl.comp.view.binding;

import javafx.beans.property.SimpleIntegerProperty;
import pl.first.sudoku.SudokuField;

public class BindingSudokuBoardGameBoard extends SimpleIntegerProperty {
    public BindingSudokuBoardGameBoard(SudokuField sudokuField) {
        super(sudokuField.getFieldValue());
        this.addListener((v, oldValue, newValue) -> sudokuField.setFieldValue(newValue.intValue()));
}
}