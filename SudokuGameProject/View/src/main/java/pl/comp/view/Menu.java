package pl.comp.view;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.stage.Stage;
import pl.comp.view.database.DatabaseFunctions;
import pl.first.sudoku.Dao;
import pl.first.sudoku.FileSudokuBoardDao;
import pl.first.sudoku.JdbcSudokuBoardDao;
import pl.first.sudoku.SudokuBoard;
import pl.first.sudoku.SudokuBoardDaoFactory;
import pl.first.sudoku.exceptions.DaoException;
import pl.first.sudoku.exceptions.FileReadException;
import pl.first.sudoku.exceptions.JdbcQueryException;
import pl.first.sudoku.exceptions.LoadSceneException;

public class Menu {
    public Label authors;
    public Button start;
    public Label titleLabel;
    public Label difficultyText;
    public Label languageText;
    private Language lang;
    private Authors authorsClass = new Authors();
    private static final Logger logger = Logger.getLogger(Menu.class.getName());

    public Locale locale;
    ResourceBundle bundle = ResourceBundle.getBundle("Language");
    FXMLLoader fxmlLoader = Screens.sudoku.getFxmlLoader(bundle);
    DatabaseFunctions functions = new DatabaseFunctions();

    @FXML
    private ChoiceBox<Difficulty> difficultyOption;
    @FXML
    ListView<String> listViewDatabase = new ListView<>();

    @FXML
    private ChoiceBox<Language> languageOption;

    @FXML
    private void initialize() throws SQLException {
        difficultyOption.getItems().add(Difficulty.EASY.setLabel(
                bundle.getString("_difficultyEasy")));
        difficultyOption.getItems().add(Difficulty.MEDIUM.setLabel(
                bundle.getString("_difficultyMedium")));
        difficultyOption.getItems().add(Difficulty.HARD.setLabel(
                bundle.getString("_difficultyHard")));
        difficultyOption.setValue(Difficulty.EASY);
        languageOption.getItems().add(Language.ENGLISH.setLabel(
                bundle.getString("_language1")));
        languageOption.getItems().add(Language.POLISH.setLabel(
                bundle.getString("_language2")));
        authorsClass.setBundle(bundle);
        authors.setText(bundle.getString("_Authors")
                + authorsClass.getObject("1") + " & "
                + authorsClass.getObject("2"));

        listViewDatabase.getItems().addAll(this.functions.selectAll());
        listViewDatabase.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    @FXML
    protected void onStartButtonClick(ActionEvent event) {
        Scene scene;
        try {
            scene = Screens.getJavaFxScene(fxmlLoader);
            GameBoard sudokuSceneController = fxmlLoader.getController();
            sudokuSceneController.prepareSudokuBoardToGame(difficultyOption.getValue());
            Stage stage = Screens.getStageFromEvent(event);
            Screens.begin.load(stage, scene);
            logger.info(bundle.getString("_gameStart"));
        } catch (IOException e) {
            Screens.showSceneErrorAlert();
            logger.info(String.valueOf(new LoadSceneException(bundle.getString("_loadFailed"), e)));
        }
    }

    @FXML
    protected void onLoadSudokuDatabaseButtonClick(ActionEvent event) throws IOException {
        if (this.listViewDatabase.getSelectionModel().getSelectedItem() != null) {
            Scene scene;
            try {
                Dao<SudokuBoard> jdbcFile = SudokuBoardDaoFactory.getDatabaseDao(
                        this.listViewDatabase.getSelectionModel().getSelectedItem());
                FXMLLoader fxmlLoader = Screens.sudoku.getFxmlLoader(bundle);
                scene = Screens.getJavaFxScene(fxmlLoader);
                GameBoard sudokuSceneController = fxmlLoader.getController();
                sudokuSceneController.prepareSudokuBoardToGameDatabase(
                        (JdbcSudokuBoardDao) jdbcFile);
                Stage stage = Screens.getStageFromEvent(event);
                Screens.begin.load(stage, scene);
                logger.info(bundle.getString("_fileLoaded"));
            } catch (DaoException e) {
                Screens.showSceneErrorAlert();
                logger.info(new JdbcQueryException(
                        e.getLocalizedMessage(), e).getMessage());
            }
        }
    }

    @FXML
    protected void onLoadSudokuButtonClick(ActionEvent event)
            throws DaoException {
        Dao<SudokuBoard> current = SudokuBoardDaoFactory.getFileDao("savedBoardCurrent");
        Dao<SudokuBoard> start = SudokuBoardDaoFactory.getFileDao("savedBoardStart");
        if (start.read() != null && current.read() != null) {
            Scene scene;
            try {
                scene = Screens.getJavaFxScene(fxmlLoader);
                GameBoard sudokuSceneController = fxmlLoader.getController();
                sudokuSceneController.prepareSudokuBoardToGame(
                        (FileSudokuBoardDao) start, (FileSudokuBoardDao) current);
                Stage stage = Screens.getStageFromEvent(event);
                Screens.begin.load(stage, scene);
                logger.info(bundle.getString("_fileLoaded"));
            } catch (IOException e) {
                Screens.showSceneErrorAlert();
                logger.info(String.valueOf(
                        new LoadSceneException(bundle.getString("_loadFailed"), e)));
            }

        }
    }

    @FXML
    protected void onConfirmLanguageButtonClick(ActionEvent event) {
        this.lang = languageOption.getSelectionModel().getSelectedItem();
        if (lang != null) {
            if (lang.equals(Language.ENGLISH)) {
                locale = new Locale("en");
                Locale.setDefault(new Locale("en"));

            } else if (lang.equals(Language.POLISH)) {
                locale = new Locale("pl");
                Locale.setDefault(new Locale("pl"));
            }
            bundle = ResourceBundle.getBundle("Language", locale);

            fxmlLoader = Screens.begin.getFxmlLoader(bundle);
            Stage stage = Screens.getStageFromEvent(event);
            Scene scene;
            try {
                scene = Screens.getJavaFxScene(fxmlLoader);
                Screens.begin.load(stage, scene);
                logger.info(bundle.getString("_languageSet"));
            } catch (IOException e) {
                Screens.showSceneErrorAlert();
                logger.info(String.valueOf(
                        new LoadSceneException(bundle.getString("_loadFailed"), e)));
            }

        }
    }
}