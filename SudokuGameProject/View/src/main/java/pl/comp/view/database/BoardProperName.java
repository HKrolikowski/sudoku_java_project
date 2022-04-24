package pl.comp.view.database;

import java.util.regex.Pattern;
import javafx.scene.control.TextFormatter;

public class BoardProperName extends TextFormatter<Object> {
    public BoardProperName() {
        super(change -> {
            if (Pattern.compile("[0-9]{1,20}|^$").matcher(
                    change.getControlNewText()).matches()) {
                return change;
            } else {
                return null;
            }
        });
    }

}