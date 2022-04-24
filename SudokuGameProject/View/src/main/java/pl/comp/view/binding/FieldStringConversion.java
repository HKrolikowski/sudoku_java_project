package pl.comp.view.binding;

import javafx.util.converter.NumberStringConverter;

public class FieldStringConversion extends NumberStringConverter {
    @Override
    public String toString(Number value) {
        if (value.intValue() == 0) {
            return "";
        }
        return super.toString(value);
    }

    @Override
    public Number fromString(String value) {
        if (value.length() == 0) {
            return 0;
        }
        return super.fromString(value);
    }
}
