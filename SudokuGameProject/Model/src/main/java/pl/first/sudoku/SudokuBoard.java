package pl.first.sudoku;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import pl.first.sudoku.exceptions.SudokuBoardException;

public class SudokuBoard implements SudokuObserver, Serializable, Cloneable {
    private SudokuField[][] board = new SudokuField[9][9];
    private SudokuSolver sudokuSolver;
    private boolean isOkay;
    private ResourceBundle bundle = ResourceBundle.getBundle("pl.first.sudoku.Language");

    public SudokuBoard() {
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                SudokuField field = new SudokuField();
                board[x][y] = field;
            }
        }
        this.sudokuSolver = new BacktrackingSudokuSolver();
    }

    @Override
    public SudokuBoard clone() {
        SudokuBoard clone = new SudokuBoard(sudokuSolver);
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                clone.set(i, j, this.get(i, j));
            }
        }
        return clone;
    }

    public SudokuBoard(SudokuSolver solver) {
        sudokuSolver = solver;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                SudokuField field = new SudokuField();
                board[i][j] = field;
                field.addObserver(this);
            }
        }
    }

    public int get(int x, int y) {
        if (x > -1 && x < 9 && y > -1 && y < 9) {
            return board[x][y].getFieldValue();
        } else {
            throw new SudokuBoardException(bundle.getObject("sudokuBoardGet").toString());
        }
    }

    public void set(int x, int y, int value) {
        if (x > -1 && x < 9 && y > -1 && y < 9) {
            (board[x][y]).setFieldValue(value);
        } else {
            throw new SudokuBoardException(bundle.getObject("sudokuBoardSet").toString());
        }
    }

    public void solveGame() {
        sudokuSolver.solve(this);
    }

    private boolean checkBoard() {
        for (int i = 0; i < 9; i++) {
            if (getRow(i).verify() == false || getColumn(i).verify() == false) {
                return false;
            }
            if (i % 3 == 1) {
                for (int j = 0; j < 9; j += 3) {
                    if (getBox(i, j).verify() == false) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public SudokuRow getRow(int x) {
        List<SudokuField> values = Arrays.asList(new SudokuField[9]);
        if (x >= 0 && x < 9) {
            for (int i = 0; i < 9; i++) {
                values.set(i, board[x][i]);
            }
            return new SudokuRow(values);
        } else {
            throw new SudokuBoardException(bundle.getObject("getRow").toString());
        }
    }

    public SudokuColumn getColumn(int y) {
        List<SudokuField> values = Arrays.asList(new SudokuField[9]);
        if (y >= 0 && y < 9) {
            for (int i = 0; i < 9; i++) {
                values.set(i, board[i][y]);
            }
            return new SudokuColumn(values);
        } else {
            throw new SudokuBoardException(bundle.getObject("getColumn").toString());
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof SudokuBoard)) {
            return false;
        }

        SudokuBoard that = (SudokuBoard) o;

        return new EqualsBuilder()
                .append(isOkay, that.isOkay)
                .append(board, that.board)
                .append(sudokuSolver, that.sudokuSolver)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(board).append(sudokuSolver)
                .append(isOkay).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("board", board)
                .append("sudokuSolver", sudokuSolver)
                .append("isOkay", isOkay)
                .toString();
    }

    public SudokuBox getBox(int x, int y) {
        if (x >= 0 && x < 9 && y >= 0 && y < 9) {
            List<SudokuField> values = Arrays.asList(new SudokuField[9]);
            int startRow = x / 3;
            int startColumn = y / 3;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    int currentRow = startRow * 3 + i;
                    int currentColumn = startColumn * 3 + j;
                    values.set(i * 3 + j, board[currentRow][currentColumn]);
                }
            }
            return new SudokuBox(values);
        } else {
            throw new SudokuBoardException(bundle.getObject("getBox").toString());
        }
    }

    public void update() {
        if (!checkBoard()) {
            isOkay = false;
        } else {
            isOkay = true;
        }
    }

    public SudokuField getField(int x, int y) {
        return board[x][y];
    }

    public boolean getIsOkay() {
        return isOkay;
    }
}