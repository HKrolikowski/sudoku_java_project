package pl.first.sudoku;

import org.junit.jupiter.api.Test;
import pl.first.sudoku.exceptions.CloneException;
import pl.first.sudoku.exceptions.SudokuAbstractException;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SudokuAbstractTest {

    @Test
    public void ConstructorTestFailed() {
        boolean thrown = false;
        List<SudokuField> list = Arrays.asList(new SudokuField[10]);
        for (int i = 0; i < 10; i++) {
            SudokuField field = new SudokuField();
            list.set(i, field);
        }
        try {
            SudokuAbstract row = new SudokuRow(list);
        } catch (SudokuAbstractException e) {
            thrown = true;
        }
        assertTrue(thrown, "Nie rzucono wyjatku.");
    }

    @Test
    public void VerifyTestCorrect() {
        List<SudokuField> list = Arrays.asList(new SudokuField[9]);

        for (int i = 0; i < 9; i++) {
            SudokuField field = new SudokuField();
            field.setFieldValue(i + 1);
            list.set(i, field);
        }
        SudokuAbstract row = new SudokuRow(list);
        assertTrue(row.verify());
    }

    @Test
    public void VerifyTestFailed() {
        List<SudokuField> list = Arrays.asList(new SudokuField[9]);
        for (int i = 0; i < 9; i++) {
            SudokuField field = new SudokuField();
            field.setFieldValue(i + 1);
            list.set(i, field);
        }
        (list.get(0)).setFieldValue(2);
        SudokuAbstract row = new SudokuRow(list);
        assertTrue(!row.verify());
    }

    @Test
    public void equalsTest() {
        List<SudokuField> list = Arrays.asList(new SudokuField[9]);
        List<SudokuField> list2 = Arrays.asList(new SudokuField[9]);
        for (int i = 0; i < 9; i++) {
            SudokuField field = new SudokuField();
            field.setFieldValue(i);
            list.set(i, field);
            SudokuField field2 = new SudokuField();
            field2.setFieldValue(2);
            list2.set(i, field2);
        }
        SudokuAbstract box1 = new SudokuBox(list);
        SudokuAbstract box2 = new SudokuBox(list);
        assertTrue(box1.equals(box2) && box2.equals(box1));
        assertEquals(box1.hashCode(), box2.hashCode());
        assertTrue(box1.equals(box1));
        SudokuField field = new SudokuField();
        assertFalse(box1.equals(field));
        SudokuAbstract row1 = new SudokuRow(list2);
        assertFalse(box1.equals(row1));
    }

    @Test
    void testHashCode() {
        List<SudokuField> list = Arrays.asList(new SudokuField[9]);
        List<SudokuField> list2 = Arrays.asList(new SudokuField[9]);
        for (int i = 0; i < 9; i++) {
            SudokuField field = new SudokuField();
            list.set(i, field);
            SudokuField field2 = new SudokuField();
            field2.setFieldValue(2);
            list2.set(i, field2);
        }
        SudokuAbstract box1 = new SudokuBox(list);
        SudokuAbstract box2 = new SudokuBox(list);
        assertEquals(box1.hashCode(), box2.hashCode());
        assertNotNull(box1.hashCode());
        SudokuAbstract row1 = new SudokuRow(list2);
        assertNotEquals(box1.hashCode(), row1.hashCode());
    }

    @Test
    void testToString() {
        List<SudokuField> list = Arrays.asList(new SudokuField[9]);
        for (int i = 0; i < 9; i++) {
            SudokuField field = new SudokuField();
            list.set(i, field);
        }
        SudokuAbstract box1 = new SudokuBox(list);
        SudokuAbstract box2 = new SudokuBox(list);
        assertNotNull(box1.toString());
        assertNotEquals(box1.toString(), box2.toString());
        assertEquals(box1.toString(), box1.toString());
    }

    @Test
    public void cloneTest() {
        boolean thrown = false;
        List<SudokuField> list = Arrays.asList(new SudokuField[9]);
        for (int i = 0; i < 9; i++) {
            SudokuField field = new SudokuField();
            list.set(i, field);
        }
        SudokuRow row = new SudokuRow(list);
        try {
            SudokuAbstract clone = row.clone();
            assertTrue(clone.equals(row));
            assertTrue(clone != row);
        } catch (CloneException e) {
            thrown = true;
        }
        assertFalse(thrown);

        SudokuAbstract column = new SudokuColumn(list);
        try {
            SudokuAbstract clone = column.clone();
            assertTrue(clone.equals(column));
            assertTrue(clone != column);
        } catch (CloneException e) {
            thrown = true;
        }
        assertFalse(thrown);

        SudokuBox box= new SudokuBox(list);
        try {
            SudokuAbstract clone = box.clone();
            assertTrue(clone.equals(box));
            assertTrue(clone != box);
        } catch (CloneException e) {
            thrown = true;
        }
        assertFalse(thrown);
    }
}