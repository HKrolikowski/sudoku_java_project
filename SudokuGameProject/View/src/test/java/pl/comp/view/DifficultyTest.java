package pl.comp.view;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import pl.first.sudoku.BacktrackingSudokuSolver;
import pl.first.sudoku.SudokuBoard;

class DifficultyTest {

    @Test
    void testLATWY() {
        SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        Difficulty difficultyLevel;
        sudokuBoard.solveGame();
        difficultyLevel = Difficulty.EASY;
        difficultyLevel.clearFields(sudokuBoard);
        int blank = 0;
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                if (sudokuBoard.get(x, y) == 0) {
                    blank++;
                }
            }
        }
        assertEquals(blank, 5);
    }

    @Test
    void testSREDNI() {
        SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        Difficulty difficultyLevel;
        sudokuBoard.solveGame();
        difficultyLevel = Difficulty.MEDIUM;
        difficultyLevel.clearFields(sudokuBoard);
        int blank = 0;
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                if (sudokuBoard.get(x, y) == 0) {
                    blank++;
                }
            }
        }
        assertEquals(blank, 35);
    }

    @Test
    void testTRUDNY() {
        SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        Difficulty difficultyLevel;
        sudokuBoard.solveGame();
        difficultyLevel = Difficulty.HARD;
        difficultyLevel.clearFields(sudokuBoard);
        int blankFields = 0;
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                if (sudokuBoard.get(x, y) == 0) {
                    blankFields++;
                }
            }
        }
        assertEquals(blankFields, 55);
    }
}