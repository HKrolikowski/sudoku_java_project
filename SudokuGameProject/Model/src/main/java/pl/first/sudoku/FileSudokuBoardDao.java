package pl.first.sudoku;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ResourceBundle;
import pl.first.sudoku.exceptions.FileReadException;
import pl.first.sudoku.exceptions.FileWriteException;

public class FileSudokuBoardDao implements Dao<SudokuBoard> {
    String file;
    private ResourceBundle bundle = ResourceBundle.getBundle("pl.first.sudoku.Language_en");

    public FileSudokuBoardDao(String file) {
        this.file = file;
    }

    @Override
    public void close() {
    }

    @Override
    public SudokuBoard read() throws FileReadException {
        SudokuBoard board = null;
        try (FileInputStream f = new FileInputStream(file);
             ObjectInputStream o = new ObjectInputStream(f);) {
            board = (SudokuBoard) o.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new FileReadException(bundle.getObject("readFailed").toString(), e);
        }
        return board;
    }

    @Override
    public void write(SudokuBoard obj) throws FileWriteException {
        try (FileOutputStream f = new FileOutputStream(file);
             ObjectOutputStream o = new ObjectOutputStream(f)) {
            o.writeObject(obj);
        } catch (IOException e) {
            throw new FileWriteException(bundle.getObject("writeFailed").toString(), e);
        }
    }
}
