package pl.comp.view;

import java.io.IOException;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import pl.first.sudoku.exceptions.LoadSceneException;

public class Winner {
    public Button comingBack;
    ResourceBundle bundle;
    private static final Logger logger = Logger.getLogger(GameBoard.class.getName());

    @FXML
    private void initialize() {
        bundle = ResourceBundle.getBundle("Language");
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
}
