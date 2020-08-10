package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;


public class ReportSuccess {

    @FXML private Button btnOk;

    public void confirm(ActionEvent actionEvent) {
        this.close();
    }

    public void close(){
        Stage stage = (Stage)btnOk.getScene().getWindow();
        stage.close();
    }

    public void show(){
        Pane sceneGraph = null;
        try{
            FXMLLoader loader = new FXMLLoader();
            sceneGraph = loader.load(getClass().getResource("/view/ReportSuccess.fxml").openStream());

            Scene scene = new Scene(sceneGraph, 500, 200);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Relatório Emitido Com Sucesso");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

        } catch (IOException e){
            e.printStackTrace();
        }
    }


}
