package pl.comp.view.binding;

import java.util.regex.Pattern;
import javafx.scene.control.TextFormatter;

public class FieldOnlyNumbers extends TextFormatter<Object> {
    public FieldOnlyNumbers() {
        super(change -> {
            if (Pattern.compile("[1-9]|^$").matcher(change.getControlNewText()).matches()) {
                return change;
            } else {
                return null;
            }
        });
    }

}
