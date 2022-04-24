package pl.first.sudoku;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BacktrackingSudokuSolverTest{
    private SudokuSolver solver = new BacktrackingSudokuSolver();
    private SudokuBoard sudoku1 = new SudokuBoard(solver);
    private SudokuBoard sudoku2 = new SudokuBoard(solver);
    @Test
    public void solve() {
        solver.solve(sudoku1);
        for (int i = 0; i < 9; i++) {
            for(int j=0; j<9; j++) {
                for(int k=j+1; k<9; k++) {
                    //sprawdzenie wierszy
                    if(sudoku1.get(i, j)==sudoku1.get(i, k)) {
                        fail("Rzad "+i+" nie jest poprawnie utworzony.");
                    }
                    //sprawdzenie kolumn
                    if(sudoku1.get(j, i)==sudoku1.get(k, i)) {
                        fail("Kolumna "+i+" nie jest poprawnie utworzona.");
                    }
                }
                //sprawdzenie kwadratow
                int startRow = i / 3;
                int startColumn = j / 3;
                int number = sudoku1.get(i, j);
                for (int k = 0; k < 3; k++) {
                    for (int l = 0; l < 3; l++) {
                        int currentRow = startRow * 3 + k;
                        int currentColumn = startColumn * 3 + l;
                        if (sudoku1.get(currentRow, currentColumn) == number
                                && (currentRow != i && currentColumn != j) && sudoku1.get(currentRow, currentColumn) != 0) {
                            fail("Czesc zawierajaca pole "+ i +", "+ j +" jest zle utworzona.");
                        }
                    }
                }
            }
        }
    }
    //sprawdzenie dwoch roznych sudoku
    @Test
    public void fillBoardDifferenceTest() {
        sudoku1.solveGame();
        sudoku2.solveGame();
        boolean isSame = true;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (sudoku1.get(i, j) != sudoku2.get(i, j)) {
                    isSame = false;
                }
            }
        }
        assertTrue(!isSame, "Wygenerowane plansze sa takie same.");
    }
}
