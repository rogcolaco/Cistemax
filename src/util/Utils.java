package util;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Utils {
    public static void mostrarAlerta (String titleText, String headerText, String contextText, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(titleText);
        alert.setHeaderText(headerText);
        alert.setContentText(contextText);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();

        Image image = new Image("/Image/logoTransparente.png");
        stage.getIcons().add(image);

        alert.showAndWait();
    }
}