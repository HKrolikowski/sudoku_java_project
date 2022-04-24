package pl.first.sudoku;

import org.junit.jupiter.api.Test;
import pl.first.sudoku.exceptions.SudokuFieldCompareToException;
import pl.first.sudoku.exceptions.SudokuFieldSetException;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SudokuFieldTest {
    SudokuField field = new SudokuField();

    @Test
    public void setFieldValueTestCorrect() {
        field.setFieldValue(1);
        assertTrue(field.getFieldValue() == 1);
    }

    @Test
    public void setFieldValueFailed() {
        boolean thrown = false;
        try {
            field.setFieldValue(-1);
        } catch (SudokuFieldSetException e) {
            thrown = true;
        }
        assertTrue(thrown, "Nie rzucono wyjatku.");
        thrown = false;
        try {
            field.setFieldValue(10);
        } catch (SudokuFieldSetException e) {
            thrown = true;
        }
        assertTrue(thrown, "Nie rzucono wyjatku.");
    }

    @Test
    public void equalsTest() {
        SudokuField field1 = new SudokuField();
        SudokuField field2 = new SudokuField();
        assertTrue(field1.equals(field2) && field2.equals(field1));
        assertEquals(field1.hashCode(), field2.hashCode());
        assertTrue(field1.equals(field1));
        field1.setFieldValue(2);
        field2.setFieldValue(3);
        assertFalse(field1.equals(field2));
        assertFalse(field1.equals(null));
        List<SudokuField> list = Arrays.asList(new SudokuField[9]);
        assertFalse(field1.equals(list));
    }

    @Test
    void testHashCode() {
        SudokuField field1 = new SudokuField();
        SudokuField field2 = new SudokuField();
        assertEquals(field1.hashCode(), field2.hashCode());
        field1.setFieldValue(1);
        field2.setFieldValue(2);
        assertNotEquals(field1.hashCode(), field2.hashCode());
        assertNotNull(field1.hashCode());
    }

    @Test
    void testToString() {
        SudokuField field1 = new SudokuField();
        SudokuField field2 = new SudokuField();
        assertNotNull(field1.toString());
        assertNotEquals(field1.toString(), field2.toString());
        assertEquals(field1.toString(), field1.toString());
    }

    @Test
    public void cloneTest() throws CloneNotSupportedException{
        SudokuField s1 = new SudokuField();
        s1.setFieldValue(9);
        SudokuField s2 = s1.clone();
        assertEquals(s1, s2);
        assertTrue(s1 != s2); //nie sa tym samym obiektem (sa rozlaczne)
        s1.setFieldValue(2);
        assertTrue(s1.getFieldValue() == 2);
        assertTrue(s2.getFieldValue() == 9);
    }

    @Test
    public void compareToTest() {
        SudokuField field1 = new SudokuField();
        SudokuField field2 = new SudokuField();
        field1.setFieldValue(1);
        field2.setFieldValue(2);
        assertTrue(field1.compareTo(field2) == -1);
        assertTrue(field2.compareTo(field1) == 1);
        assertTrue(field1.compareTo(field1) == 0);
        boolean thrown = false;
        try {
            field1.compareTo(null);
        } catch (SudokuFieldCompareToException e) {
            thrown = true;
        }
        assertTrue(thrown);
    }

}