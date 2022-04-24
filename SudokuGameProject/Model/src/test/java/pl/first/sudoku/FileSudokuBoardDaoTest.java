package pl.first.sudoku;

import org.junit.jupiter.api.Test;
import pl.first.sudoku.exceptions.FileReadException;
import pl.first.sudoku.exceptions.FileWriteException;

import static org.junit.jupiter.api.Assertions.*;

public class FileSudokuBoardDaoTest {
    private SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
    private SudokuBoard sudokuBoard2 = null;

    @Test
    public void ReadWriteTest() {
        try(Dao<SudokuBoard> file= SudokuBoardDaoFactory.getFileDao("test");
            Dao<SudokuBoard> file2= SudokuBoardDaoFactory.getFileDao(";test*")) {
            sudokuBoard.solveGame();
            assertTrue(sudokuBoard.getIsOkay());
            file2.write(sudokuBoard);
            file.write(sudokuBoard);
            sudokuBoard2 = file.read();
            assertNotNull(sudokuBoard2);
            assertTrue(sudokuBoard2.getIsOkay());
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    assertTrue(sudokuBoard.get(i, j) == sudokuBoard2.get(i, j));
                }
            }
        } catch (FileWriteException e) {

        } catch (FileReadException e) {

        } catch (java.lang.Exception e) {

        }
    }

    @Test
    public void ReadWriteTest2() {
        try(Dao<SudokuBoard> file= SudokuBoardDaoFactory.getFileDao("test")) {
            sudokuBoard.solveGame();
            assertTrue(sudokuBoard.getIsOkay());
            file.write(sudokuBoard);
            sudokuBoard2 = file.read();
            assertNotNull(sudokuBoard2);
            assertTrue(sudokuBoard2.getIsOkay());
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    assertTrue(sudokuBoard.get(i, j) == sudokuBoard2.get(i, j));
                }
            }
        } catch(java.lang.Exception e) {
        }
    }

}
