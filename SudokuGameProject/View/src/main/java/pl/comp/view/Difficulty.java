package pl.comp.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import pl.first.sudoku.SudokuBoard;


public enum Difficulty {

    EASY(5, ""),
    MEDIUM(35, ""),
    HARD(55, "");
    private int blanks;
    private String label;

    Difficulty(int blanks, String label) {
        ResourceBundle bundle = ResourceBundle.getBundle("Language");
        this.blanks = blanks;
        if (blanks == 5) {
            this.label = bundle.getString("_difficultyEasy");
        } else if (blanks == 35) {
            this.label = bundle.getString("_difficultyMedium");
        } else if (blanks == 55) {
            this.label = bundle.getString("_difficultyHard");
        }
    }

    public Difficulty setLabel(String label) {
        this.label = label;
        return this;
    }

    @Override
    public String toString() {
        return label;
    }

    public String getLabel() {
        return label;
    }

    public void clearFields(SudokuBoard sudokuBoard) {
        int empty = 0;
        List<Integer> column = new ArrayList<>();
        List<Integer> row = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            column.add(i);
            row.add(i);
        }
        while (empty < blanks) {
            Collections.shuffle(column);
            Collections.shuffle(row);
            int x = column.get(0);
            int y = row.get(0);
            if (sudokuBoard.get(x, y) != 0) {
                sudokuBoard.set(x, y, 0);
                empty++;
            }
        }
    }
}