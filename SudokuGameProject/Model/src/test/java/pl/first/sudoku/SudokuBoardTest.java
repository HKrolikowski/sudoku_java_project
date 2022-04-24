package pl.first.sudoku;

import org.junit.jupiter.api.Test;
import pl.first.sudoku.exceptions.SudokuBoardException;

import static org.junit.jupiter.api.Assertions.*;

public class SudokuBoardTest {
    private SudokuSolver solver = new BacktrackingSudokuSolver();
    private SudokuSolver solver1 = new BacktrackingSudokuSolver();
    private SudokuBoard sudoku1 = new SudokuBoard(solver);
    private SudokuBoard sudoku = new SudokuBoard(solver);
    private SudokuBoard sudoku2 = new SudokuBoard(solver);
    private SudokuBoard sudoku3 = new SudokuBoard(solver1);

    @Test
    public void setTestCorrect() {
        int b = 0;
        int c = 8;
        int d = 9;
        //X
        sudoku.set(b, b, b);
        assertTrue(sudoku.get(b, b) == b);
        sudoku.set(c, b, b);
        assertTrue(sudoku.get(c, b) == b);
        //Y
        sudoku.set(b, c, b);
        assertTrue(sudoku.get(b, c) == b);
        //V
        sudoku.set(b, b, d);
        assertTrue(sudoku.get(b, b) == d);
    }

    @Test
    public void setTestFailed() {
        int a = -1;
        int b = 0;
        int c = 8;
        int d = 9;
        int f = 10;
        boolean thrown = false;
        //X
        sudoku.set(2, 2, 3);
        try {
            sudoku.set(a, b, c);
        } catch (SudokuBoardException e) {
            thrown = true;
        }
        assertTrue(thrown, "Nie rzucono wyjatku.");
        thrown = false;
        try {
            sudoku.set(d, b, b);
        } catch (SudokuBoardException e) {
            thrown = true;
        }
        assertTrue(thrown, "Nie rzucono wyjatku.");
        //Y
        thrown = false;
        try {
            sudoku.set(b, a, c);
        } catch (SudokuBoardException e) {
            thrown = true;
        }
        assertTrue(thrown, "Nie rzucono wyjatku.");
        thrown = false;
        try {
            sudoku.set(b, d, b);
        } catch (SudokuBoardException e) {
            thrown = true;
        }
        assertTrue(thrown, "Nie rzucono wyjatku.");
    }

    @Test
    public void getTest() {
        int a = -1;
        int b = 0;
        int c = 9;
        boolean thrown = false;
        try {
            sudoku.get(a, b);
        } catch (SudokuBoardException e) {
            thrown = true;
        }
        assertTrue(thrown, "Nie rzucono wyjatku.");
        thrown = false;
        try {
            sudoku.get(b, a);
        } catch (SudokuBoardException e) {
            thrown = true;
        }
        assertTrue(thrown, "Nie rzucono wyjatku.");
        thrown = false;
        try {
            sudoku.get(c, b);
        } catch (SudokuBoardException e) {
            thrown = true;
        }
        assertTrue(thrown, "Nie rzucono wyjatku.");
        thrown = false;
        try {
            sudoku.get(b, c);
        } catch (SudokuBoardException e) {
            thrown = true;
        }
        assertTrue(thrown, "Nie rzucono wyjatku.");
    }

    @Test
    public void getRowTestFailed() {
        boolean thrown = false;
        try {
            sudoku1.getRow(-1);
        } catch (SudokuBoardException e) {
            thrown = true;
        }
        assertTrue(thrown, "Nie rzucono wyjatku.");
        thrown = false;
        try {
            sudoku1.getRow(9);
        } catch (SudokuBoardException e) {
            thrown = true;
        }
        assertTrue(thrown, "Nie rzucono wyjatku.");
    }

    @Test
    public void getRowTestCorrect() {
        assertTrue(sudoku1.getRow(0) != null);
        assertTrue(sudoku1.getRow(8) != null);
    }

    @Test
    public void getColumnTestFailed() {
        boolean thrown = false;
        try {
            sudoku1.getColumn(-1);
        } catch (SudokuBoardException e) {
            thrown = true;
        }
        assertTrue(thrown, "Nie rzucono wyjatku.");
        thrown = false;
        try {
            sudoku1.getColumn(9);
        } catch (SudokuBoardException e) {
            thrown = true;
        }
        assertTrue(thrown, "Nie rzucono wyjatku.");
    }

    @Test
    public void getColumnTestCorrect() {
        assertTrue(sudoku1.getColumn(0) != null);
        assertTrue(sudoku1.getColumn(8) != null);
    }

    @Test
    public void getBoxTestFailed() {
        boolean thrown = false;
        try {
            sudoku1.getBox(-1, 0);
        } catch (SudokuBoardException e) {
            thrown = true;
        }
        assertTrue(thrown, "Nie rzucono wyjatku.");
        thrown = false;
        try {
            sudoku1.getBox(9, 0);
        } catch (SudokuBoardException e) {
            thrown = true;
        }
        assertTrue(thrown, "Nie rzucono wyjatku.");
        thrown = false;
        try {
            sudoku1.getBox(0, -1);
        } catch (SudokuBoardException e) {
            thrown = true;
        }
        assertTrue(thrown, "Nie rzucono wyjatku.");
        thrown = false;
        try {
            sudoku1.getBox(0, 9);
        } catch (SudokuBoardException e) {
            thrown = true;
        }
        assertTrue(thrown, "Nie rzucono wyjatku.");
    }

    @Test
    public void getBoxTestCorrect() {
        assertTrue(sudoku1.getBox(0, 0) != null);
        assertTrue(sudoku1.getBox(8, 8) != null);
    }

    @Test
    public void checkBoardTest() {
        sudoku2.solveGame();
        assertTrue(sudoku2.getIsOkay());
        //ten sam rzad
        int pom1 = sudoku2.get(0, 0);
        int pom2 = sudoku2.get(0, 7);
        sudoku2.set(0, 7, pom1);
        assertFalse(sudoku2.getIsOkay());
        sudoku2.set(0, 7, pom2);
        assertTrue(sudoku2.getIsOkay());
        //ta sama kolumna
        pom2 = sudoku2.get(8, 0);
        sudoku2.set(8, 0, pom1);
        assertFalse(sudoku2.getIsOkay());
        sudoku2.set(8, 0, pom2);
        assertTrue(sudoku2.getIsOkay());
        //ten sam kwadrat
        pom2 = sudoku2.get(1, 1);
        sudoku2.set(1, 1, pom1);
        assertFalse(sudoku2.getIsOkay());
        sudoku2.set(1, 1, pom2);
        assertTrue(sudoku2.getIsOkay());
    }

    @Test
    public void equalsTest() {
        assertFalse(sudoku1.equals(solver));
        assertFalse(sudoku1.equals(sudoku3));
        assertFalse(sudoku1.equals(null));
        assertTrue(sudoku1.equals(sudoku2) && sudoku2.equals(sudoku1));
        assertEquals(sudoku1.hashCode(), sudoku2.hashCode());
        assertTrue(sudoku1.equals(sudoku1));
    }

    @Test
    public void TestHashCode() {
        assertEquals(sudoku1.hashCode(), sudoku2.hashCode());
        assertNotEquals(sudoku1.hashCode(), sudoku3.hashCode());
        assertNotNull(sudoku1.hashCode());
    }

    @Test
    public void TestToString() {
        assertNotNull(sudoku1.toString());
        assertNotEquals(sudoku1.toString(), sudoku2.toString());
        assertEquals(sudoku1.toString(), sudoku1.toString());
    }

    @Test
    public void cloneTest(){
        sudoku1.solveGame();
        SudokuBoard s1 = sudoku1.clone();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                assertEquals(sudoku1.get(i, j), s1.get(i, j));
            }
        }
        assertTrue(s1 != sudoku1);
        s1.solveGame();
        boolean same = true;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (sudoku1.get(i, j) != s1.get(i, j)) {
                    same = false;
                }
            }
        }
        assertFalse(same);
    }

}