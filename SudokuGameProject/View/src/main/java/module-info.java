module pl.comp.view {
    requires javafx.controls;
    requires javafx.fxml;
	requires ModelProject;
    requires java.logging;
    requires java.sql;

    exports pl.comp.view;
	opens pl.comp.view;
    exports pl.comp.view.binding;
    opens pl.comp.view.binding;
}