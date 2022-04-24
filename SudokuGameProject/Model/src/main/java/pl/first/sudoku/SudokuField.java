package pl.first.sudoku;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import pl.first.sudoku.exceptions.CloneException;
import pl.first.sudoku.exceptions.SudokuFieldCompareToException;
import pl.first.sudoku.exceptions.SudokuFieldSetException;

public class SudokuField implements Serializable, Cloneable, Comparable<SudokuField> {
    private int value;
    private List<SudokuObserver> observers = new ArrayList<SudokuObserver>();
    private ResourceBundle bundle = ResourceBundle.getBundle("pl.first.sudoku.Language");

    @Override
    protected SudokuField clone() throws CloneException {
        try {
            return (SudokuField) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new CloneException(bundle.getObject("cloneFailed").toString());
        }
    }

    @Override
    public int compareTo(SudokuField o) {
        if (o == null) {
            throw new SudokuFieldCompareToException(
                    bundle.getObject("sudokuFieldCompareTo").toString());
        }
        return Integer.compare(this.value, o.getFieldValue());
    }

    public int getFieldValue() {
        return value;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("value", value)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof SudokuField)) {
            return false;
        }

        SudokuField that = (SudokuField) o;

        return new EqualsBuilder()
                .append(value, that.value)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(value).toHashCode();
    }

    public void setFieldValue(int value) {
        if (value > -1 && value < 10) {
            this.value = value;
            notifyObservers();
        } else {
            throw new SudokuFieldSetException(bundle.getObject("sudokuFieldSet").toString());
        }

    }

    public void addObserver(SudokuObserver obs) {
        observers.add(obs);
    }

    //public void removeObserver(SudokuObserver obs) { observers.remove(obs); }

    public void notifyObservers() {
        for (int i = 0; i < observers.size(); i++) {
            observers.get(i).update();
        }
    }
}
