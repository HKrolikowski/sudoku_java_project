package pl.first.sudoku;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.first.sudoku.exceptions.FileReadException;
import pl.first.sudoku.exceptions.JdbcClosingConnectionException;
import pl.first.sudoku.exceptions.JdbcExistingBoardException;
import pl.first.sudoku.exceptions.JdbcQueryException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class JdbcSudokuBoardDaoTest {

    private SudokuBoard sudokuBoard = new SudokuBoard();

    @BeforeEach
    void setUp() {
        Dao<SudokuBoard> dbBoard = SudokuBoardDaoFactory.getDatabaseDao(
                "sudokuBoard");
    }

    @Test
    void testReadWrite() throws IOException {
        sudokuBoard.solveGame();
        Files.deleteIfExists(Paths.get("database.db"));
        try (Dao<SudokuBoard> dbBoard =
                     SudokuBoardDaoFactory.getDatabaseDao("sudokuBoard")) {
            assertDoesNotThrow(() -> dbBoard.write(sudokuBoard));
            SudokuBoard sudokuBoard1 = dbBoard.read();
            for(int i=0;i<9;i++)
            {
                for(int j=0;j<9;j++)
                {
                    assertEquals(sudokuBoard.get(i,j), sudokuBoard1.get(i,j));
                }
            }
            assertTrue(sudokuBoard1 != sudokuBoard);
        } catch (Exception e) {
            e.getMessage();
        }
    }

    @Test
    void testJdbcExecuteQueryException() {
        sudokuBoard.solveGame();
        try (Dao<SudokuBoard> dbBoard =
                     SudokuBoardDaoFactory.getDatabaseDao("sudokuBoard")) {
            assertThrows(JdbcExistingBoardException.class,
                    () -> dbBoard.write(sudokuBoard));

        } catch (Exception e) {
            e.getMessage();
        }
    }

    @Test
    void testJdbcClosingResourcesException() {
        sudokuBoard.solveGame();
        SudokuBoard sudokuBoard1;
        try (Dao<SudokuBoard> dbBoard =
                     SudokuBoardDaoFactory.getDatabaseDao("")) {
//            assertDoesNotThrow(() -> dbBoard.write(sudokuBoard));
//            assertDoesNotThrow(() -> sudokuBoard1 = dbBoard.read());

        } catch (Exception e) {
            e.getMessage();
        }
    }

}
