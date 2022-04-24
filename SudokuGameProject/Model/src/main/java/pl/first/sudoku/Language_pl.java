package pl.first.sudoku;

import java.io.Serializable;
import java.util.ListResourceBundle;

public class Language_pl extends ListResourceBundle implements Serializable {

    @Override
    protected Object[][] getContents() {
        return new Object[][]{
                {"writeFailed", "Zapisywanie nieudane."},
                {"readFailed", "Odczytywanie nieudane!"},
                {"list9", "Lista musi mieć 9 elementów!"},
                {"getColumn", "getColumn otrzymało złe parametry"},
                {"getRow", "getRow otrzymało złe parametry"},
                {"getBox", "getBox otrzymało złe parametry"},
                {"sudokuBoardGet", "SudokuBoard get otrzymało złe parametry"},
                {"sudokuBoardSet", "SudokuBoard set otrzymało złe parametry"},
                {"sudokuFieldSet", "SudokuField set otrzymało złe parametry"},
                {"sudokuFieldCompareTo", "Obiekt jest nullem!"},
                {"cloneFailed", "Klonowanie nieudane"},
                {"queryFailed", "Zapytanie do bazy zakończone niepowodzeniem"},
                {"connectingFailed", "Lączenie z bazą danych nieudane!"},
                {"tableExists", "Taka tablica istnieje!"},
                {"boardExists", "Taka plansza istnieje!"},
                {"connectionNotClosed", "Blad zamykania polaczenia!"},
                {"readFailed", "Blad odczytu pliku!"},
                {"writeFailed", "Blad zapisu pliku!"},
                {"queryExecutionFailed", "Blad wykonania kwerendy!"}
        };
    }
}
