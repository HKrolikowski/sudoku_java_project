package pl.comp.view;

import org.junit.jupiter.api.Test;
import org.w3c.dom.ls.LSOutput;
import pl.first.sudoku.BacktrackingSudokuSolver;
import pl.first.sudoku.SudokuBoard;

import java.util.Locale;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;

class MenuTest {
    @Test
    void testLATWY() {
        Locale loc = new Locale("pl");
        ResourceBundle bundle = ResourceBundle.getBundle("Language", loc);
        assertEquals(bundle.getString("_language1"), "ANGIELSKI");
        Difficulty e = Difficulty.HARD;
        assertEquals(e.getLabel(), "TRUDNY");
    }
}