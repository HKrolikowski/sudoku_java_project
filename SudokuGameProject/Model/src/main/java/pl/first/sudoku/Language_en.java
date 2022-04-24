package pl.first.sudoku;

import java.io.Serializable;
import java.util.ListResourceBundle;

public class Language_en extends ListResourceBundle implements Serializable {

    @Override
    protected Object[][] getContents() {
        return new Object[][]{
                {"writeFailed", "Writting failed."},
                {"readFailed", "Reading failed!"},
                {"list9", "List must have 9 elements!"},
                {"getColumn", "getColumn required specific numbers."},
                {"getRow", "getRow required specific numbers."},
                {"getBox", "getBox required specific numbers."},
                {"sudokuBoardGet", "SudokuBoard get required specific numbers."},
                {"sudokuBoardSet", "SudokuBoard set required specific numbers."},
                {"sudokuFieldSet", "SudokuField set required specific numbers."},
                {"sudokuFieldCompareTo", "object is null."},
                {"cloneFailed", "Clone failed!"},
                {"queryFailed", "Query to database failed!"},
                {"connectingFailed", "Connecting to database failed!"},
                {"tableExists", "This table already exists!"},
                {"boardExists", "This board already exists!"},
                {"connectionNotClosed", "Failure in closing connection!"},
                {"readFailed", "Failure in reading the file!"},
                {"writeFailed", "Failure in writing the file!"},
                {"queryExecutionFailed", "Failure in query execution!"}
        };
    }
}