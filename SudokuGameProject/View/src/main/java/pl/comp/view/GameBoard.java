package pl.comp.view;

import static java.util.ResourceBundle.getBundle;

import java.io.IOException;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import pl.comp.view.binding.BindingSudokuBoardGameBoard;
import pl.comp.view.binding.FieldOnlyNumbers;
import pl.comp.view.binding.FieldStringConversion;
import pl.comp.view.database.BoardProperName;
import pl.first.sudoku.BacktrackingSudokuSolver;
import pl.first.sudoku.Dao;
import pl.first.sudoku.FileSudokuBoardDao;
import pl.first.sudoku.JdbcSudokuBoardDao;
import pl.first.sudoku.SudokuBoard;
import pl.first.sudoku.SudokuBoardDaoFactory;
import pl.first.sudoku.exceptions.DaoException;
import pl.first.sudoku.exceptions.FileReadException;
import pl.first.sudoku.exceptions.JdbcWriteException;
import pl.first.sudoku.exceptions.LoadSceneException;


public class GameBoard {
    private SudokuBoard sudokuBoardCurrent;
    private SudokuBoard sudokuBoardSolved;
    private SudokuBoard sudokuFull;
    private final TextField[][] sudokuGridLabels;
    private final BindingSudokuBoardGameBoard[][] fieldBinders;
    public Button comingBack;
    private static final Logger logger = Logger.getLogger(GameBoard.class.getName());
    ResourceBundle bundle;

    @FXML
    private TextField tableName;

    public GameBoard() {
        sudokuBoardCurrent = new SudokuBoard(new BacktrackingSudokuSolver());
        sudokuGridLabels = new TextField[9][9];
        fieldBinders = new BindingSudokuBoardGameBoard[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                sudokuGridLabels[i][j] = new TextField();
            }
        }
    }

    @FXML
    private HBox sudokuGridContainer;
    private GridPane sudokuGrid;

    @FXML
    private void initialize() {
        prepare();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                sudokuGrid.add(sudokuGridLabels[i][j], i, j);
            }
        }
        bundle = ResourceBundle.getBundle("Language");
        this.tableName.setTextFormatter(new BoardProperName());
    }

    private void prepare() {
        sudokuGrid = new GridPane();
        sudokuGrid.getStyleClass().add("grid");
        sudokuGridContainer.getChildren().add(sudokuGrid);
        addColumnsAndRowsToSudokuGrid();
        setGridCellsFixedSize(50);
        prepareFields();
    }

    private void addColumnsAndRowsToSudokuGrid() {
        sudokuGrid.getColumnConstraints().add(new ColumnConstraints(9));
        sudokuGrid.getRowConstraints().add(new RowConstraints(9));
    }

    private void setGridCellsFixedSize(int size) {
        for (var column : sudokuGrid.getColumnConstraints()) {
            column.setPrefWidth(size);
        }
        for (var row : sudokuGrid.getRowConstraints()) {
            row.setPrefHeight(size);
        }
    }

    public void prepareSudokuBoardToGame(Difficulty difficulty) {
        sudokuBoardCurrent.solveGame();
        sudokuBoardSolved = sudokuBoardCurrent.clone();
        difficulty.clearFields(sudokuBoardCurrent);
        sudokuFull = sudokuBoardCurrent.clone();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                sudokuGridLabels[i][j].setText(String.valueOf(sudokuBoardCurrent.get(i, j)));
                if (sudokuBoardCurrent.get(i, j) == 0) {
                    sudokuGridLabels[i][j].setText("");
                    sudokuGridLabels[i][j].setBackground(
                            new Background(new BackgroundFill(Color.LIGHTGREY,
                                    CornerRadii.EMPTY, Insets.EMPTY))
                    );
                } else {
                    sudokuBoardSolved.set(i, j, 0);
                    sudokuGridLabels[i][j].setEditable(false);
                }
            }
        }
        prepareFields();
        logger.info(bundle.getString("_boardPrepared"));
    }

    public void prepareSudokuBoardToGame(FileSudokuBoardDao start, FileSudokuBoardDao current)
            throws FileReadException {
        sudokuBoardSolved = start.read();
        sudokuBoardCurrent = current.read();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                sudokuGridLabels[i][j].textProperty()
                        .setValue(String.valueOf(sudokuBoardCurrent.get(i, j)));
                if (sudokuBoardCurrent.get(i, j) == 0) {
                    sudokuGridLabels[i][j].textProperty().setValue("");
                    sudokuGridLabels[i][j].setBackground(
                            new Background(new BackgroundFill(Color.LIGHTGREY,
                                    CornerRadii.EMPTY, Insets.EMPTY))
                    );
                } else if (sudokuBoardSolved.get(i, j) != 0 && sudokuBoardCurrent.get(i, j) != 0) {
                    sudokuGridLabels[i][j].textProperty()
                            .setValue(String.valueOf(sudokuBoardCurrent.get(i, j)));
                    sudokuGridLabels[i][j].setBackground(
                            new Background(new BackgroundFill(Color.LIGHTGREY,
                                    CornerRadii.EMPTY, Insets.EMPTY))
                    );
                } else {
                    sudokuGridLabels[i][j].setEditable(false);
                    sudokuBoardSolved.set(i, j, sudokuBoardCurrent.get(i, j));
                    sudokuFull = sudokuBoardSolved.clone();
                }
            }
        }
        prepareFields();
        logger.info(bundle.getString("_boardPreparedFile"));
    }

    public void prepareSudokuBoardToGameDatabase(JdbcSudokuBoardDao daoJdbc)
            throws DaoException {
        try {
            this.sudokuBoardCurrent = daoJdbc.read();
            this.sudokuBoardSolved = daoJdbc.read();
            this.sudokuBoardSolved.solveGame();
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    sudokuGridLabels[i][j].setText(String.valueOf(sudokuBoardCurrent.get(i, j)));
                    if (sudokuBoardCurrent.get(i, j) == 0) {
                        sudokuGridLabels[i][j].setText("");
                        sudokuGridLabels[i][j].setBackground(
                                new Background(new BackgroundFill(Color.LIGHTGREY,
                                        CornerRadii.EMPTY, Insets.EMPTY))
                        );
                    } else {
                        sudokuBoardSolved.set(i, j, 0);
                        sudokuGridLabels[i][j].setEditable(false);
                    }
                }
            }
            prepareFields();
            ResourceBundle bundle = ResourceBundle.getBundle("Language");
            logger.info(bundle.getString("_boardPreparedDatabase"));
        } catch (FileReadException e) {
            logger.info(e.getMessage());
        }
    }

    @FXML
    void saveSudokuBoard() throws DaoException {
        Dao<SudokuBoard> file = new FileSudokuBoardDao("savedBoardCurrent");
        Dao<SudokuBoard> file1 = new FileSudokuBoardDao("savedBoardStart");
        file.write((sudokuBoardCurrent));
        file1.write((sudokuBoardSolved));
        logger.info(bundle.getString("_boardSaved"));
    }

    @FXML
    void saveSudokuBoardtoDataBase() throws DaoException {
        if (this.tableName.textProperty().getValue() != "") {
            try (Dao<SudokuBoard> sbt =
                         SudokuBoardDaoFactory.getDatabaseDao(tableName
                                 .textProperty().getValue())) {
                ResourceBundle bundle = getBundle("Language");
                sbt.write((sudokuFull));
                logger.info(bundle.getString("_boardSavedDatabase"));
            } catch (Exception e) {
                Screens.showSceneErrorAlert();
                logger.info(String.valueOf(
                        new JdbcWriteException(bundle.getString("_loadFailed"), e)));
            }
        } else {
            logger.info(bundle.getString("_tableNameEmpty"));
        }
    }

    @FXML
    void comingBack(ActionEvent event) {
        FXMLLoader fxmlLoader = Screens.begin.getFxmlLoader(bundle);
        Stage stage = Screens.getStageFromEvent(event);
        Scene scene;
        try {
            scene = Screens.getJavaFxScene(fxmlLoader);
            Screens.begin.load(stage, scene);
            logger.info((bundle.getString("_comingBack")));
        } catch (IOException e) {
            Screens.showSceneErrorAlert();
            logger.info(String.valueOf(new LoadSceneException(bundle.getString("_loadFailed"), e)));
        }
    }

    private void prepareFields() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                fieldBinders[i][j] =
                        new BindingSudokuBoardGameBoard(sudokuBoardCurrent.getField(i, j));
                Bindings.bindBidirectional(sudokuGridLabels[i][j].textProperty(),
                        fieldBinders[i][j], new FieldStringConversion());
                sudokuGridLabels[i][j].setTextFormatter(new FieldOnlyNumbers());
            }
        }
    }


    @FXML
    protected void checkSudokuBoard(ActionEvent event) {
        boolean solved = true;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (sudokuBoardSolved.get(i, j) != sudokuBoardCurrent.get(i, j)
                        && sudokuBoardSolved.get(i, j) != 0) {
                    sudokuGridLabels[i][j].setBackground(
                            new Background(new BackgroundFill(Color.RED,
                                    CornerRadii.EMPTY, Insets.EMPTY)));
                    if (solved) {
                        solved = false;
                        logger.info(bundle.getString("_notSolved"));
                    }
                } else if (sudokuBoardSolved.get(i, j) == sudokuBoardCurrent.get(i, j)
                        && sudokuBoardSolved.get(i, j) != 0) {
                    sudokuGridLabels[i][j].setBackground(
                            new Background(new BackgroundFill(Color.LIGHTGREEN,
                                    CornerRadii.EMPTY, Insets.EMPTY)));
                }
            }
        }
        if (solved == true) {
            FXMLLoader fxmlLoader = Screens.winner.getFxmlLoader(bundle);
            Stage stage = Screens.getStageFromEvent(event);
            Scene scene;
            try {
                scene = Screens.getJavaFxScene(fxmlLoader);
                Screens.winner.load(stage, scene);
                logger.info(bundle.getString("_solved"));
            } catch (IOException e) {
                Screens.showSceneErrorAlert();
                logger.info(String.valueOf(
                        new LoadSceneException(bundle.getString("_loadFailed"), e)));
            }
        }
    }
}