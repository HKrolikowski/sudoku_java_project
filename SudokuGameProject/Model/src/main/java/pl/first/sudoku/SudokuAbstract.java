package pl.first.sudoku;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import pl.first.sudoku.exceptions.CloneException;
import pl.first.sudoku.exceptions.SudokuAbstractException;

public abstract class SudokuAbstract implements Serializable, Cloneable {
    protected List<SudokuField> list;
    private ResourceBundle bundle = ResourceBundle.getBundle("pl.first.sudoku.Language");

    @Override
    protected SudokuAbstract clone() throws CloneException {
        try (ByteArrayOutputStream bo = new ByteArrayOutputStream();
             ObjectOutputStream o = new ObjectOutputStream(bo);) {
            o.writeObject(this);

            ByteArrayInputStream bi = new ByteArrayInputStream(bo.toByteArray());
            ObjectInputStream oi = new ObjectInputStream(bi);
            return (SudokuAbstract) oi.readObject();
        } catch (Exception e) {
            throw new CloneException(bundle.getObject("cloneFailed").toString());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof SudokuAbstract)) {
            return false;
        }

        SudokuAbstract that = (SudokuAbstract) o;

        return new EqualsBuilder().append(list, that.list).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(list).toHashCode();
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("list", list)
                .toString();
    }

    public SudokuAbstract(List<SudokuField> fields) {
        if (fields.size() == 9) {
            list = fields;
        } else {
            throw new SudokuAbstractException(bundle.getObject("list9").toString());
        }
    }

    public boolean verify() {
        boolean wrong = true;
        for (int i = 0; i < 9; i++) {
            for (int j = i + 1; j < 9; j++) {
                if (list.get(i).getFieldValue() == list.get(j).getFieldValue()) {
                    wrong = false;
                }
            }
        }
        return wrong;
    }
}