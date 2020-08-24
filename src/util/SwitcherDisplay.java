package util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class SwitcherDisplay {

    public void show(String fxmlResource, Stage stage, String stageTitle, Double width, Double height) {
        Pane sceneGraph = null;
        try {
            FXMLLoader loader = new FXMLLoader();
            sceneGraph = FXMLLoader.load(getClass().getResource(fxmlResource));
            Scene scene = new Scene(sceneGraph, width, height);

            stage.setScene(scene);
            stage.setTitle(stageTitle);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
