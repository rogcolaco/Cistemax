package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Menu;
import javafx.stage.Stage;
import util.SwitcherDisplay;

import java.io.IOException;

public class Report extends MenuPrincipal {

    //Toolbar buttons
    @FXML private Button btnSale;
    @FXML private Button btnMovie;
    @FXML private Button btnTheater;
    @FXML private Button btnGenre;
    @FXML private Button btnPrice;
    @FXML private Button btnMovieSession;
    @FXML private Button btnCancelSale;
    @FXML private Button btnReport;

    @FXML private DatePicker calDateStartReport;
    @FXML private DatePicker calDateEndReport;
    @FXML private Button btnRequestReport;
    @FXML private Button btnCancelOp;



}
