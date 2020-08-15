package util;

import javafx.scene.control.Alert;

public class Utils {
    public static void mostrarAlerta (String titleText, String headerText, String contextText, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(titleText);
        alert.setHeaderText(headerText);
        alert.setContentText(contextText);
        alert.showAndWait();
    }
}
