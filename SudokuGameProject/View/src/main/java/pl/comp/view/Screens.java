package pl.comp.view;

import java.io.IOException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public enum Screens {
    begin("menu.fxml"),
    winner("winner.fxml"),
    sudoku("gameBoard.fxml");
    ResourceBundle bundle;

    public static int WIDTH = 640;
    public static int HEIGHT = 640;
    public String file;

    Screens(String file) {
        this.file = file;
    }

    FXMLLoader getFxmlLoader(ResourceBundle bundle) {
        this.bundle = bundle;
        return new FXMLLoader(getClass().getResource(file), bundle);
    }

    public static Scene getJavaFxScene(FXMLLoader fxmlLoader) throws IOException {
        return new Scene(fxmlLoader.load(), WIDTH, HEIGHT);
    }

    public void load(Stage stage, Scene scene) {
        String ceeses = Screens.begin.getClass().getResource("style.css").toExternalForm();
        scene.getStylesheets().add(ceeses);
        stage.setScene(scene);
    }

    public static void showSceneErrorAlert() {
        ResourceBundle bundle = ResourceBundle.getBundle("Language");
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(bundle.getString("_notloaded"));
        alert.setHeaderText(bundle.getString("_notloaded"));
        alert.setContentText(bundle.getString("_notloaded"));
        alert.showAndWait().ifPresent(rs -> {
            if (rs == ButtonType.OK) {
                System.out.println("OK");
            }
        });
    }

    static Stage getStageFromEvent(ActionEvent event) {
        Node node = (Node) event.getSource();
        Stage thisStage = (Stage) node.getScene().getWindow();
        return thisStage;
    }
}