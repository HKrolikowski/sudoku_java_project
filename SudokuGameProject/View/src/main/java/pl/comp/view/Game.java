package pl.comp.view;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.first.sudoku.exceptions.LoadSceneException;

public class Game extends Application {
    private static final Logger logger = Logger.getLogger(Game.class.getName());
    private static ResourceBundle bundle = ResourceBundle.getBundle("Language");

    static {
        try (FileInputStream f = new FileInputStream(
                "View\\src\\main\\resources\\logging.properties")) {
            LogManager.getLogManager().readConfiguration(f);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage stage) throws LoadSceneException {
        Locale.setDefault(new Locale("pl"));
        FXMLLoader fxmlLoader = new FXMLLoader(Game.class.getResource("menu.fxml"), bundle);
        try {
            Scene scene = new Scene(fxmlLoader.load(), 640, 640);
            String css1 = this.getClass().getResource("style.css").toExternalForm();
            scene.getStylesheets().add(css1);
            stage.setTitle(bundle.getString("_title"));
            stage.setScene(scene);
        } catch (IOException e) {
            Screens.showSceneErrorAlert();
            logger.info(String.valueOf(new LoadSceneException(bundle.getString("_loadFailed"), e)));
        }

        stage.show();
    }

    public static void main(String[] args) {
        logger.info(bundle.getString("_initialize"));
        launch();
    }
}