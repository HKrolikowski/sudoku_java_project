module ModelProject {
	requires org.apache.commons.lang3;
    requires java.sql;
    exports pl.first.sudoku;
	opens pl.first.sudoku;
    exports pl.first.sudoku.exceptions;
}