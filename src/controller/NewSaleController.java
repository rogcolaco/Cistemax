package controller;

import dao.SessionDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Session;
import util.SwitcherDisplay;

import java.io.IOException;
import java.sql.SQLException;

public class NewSaleController extends MenuPrincipal{

    //Toolbar buttons
    @FXML private Button btnSale;
    @FXML private Button btnMovie;
    @FXML private Button btnTheater;
    @FXML private Button btnGenre;
    @FXML private Button btnPrice;
    @FXML private Button btnMovieSession;
    @FXML private Button btnCancelSale;
    @FXML private Button btnReport;

    @FXML private ChoiceBox<Session> cbSessionSale;
    @FXML private ChoiceBox<Integer> cbPromoTickets;
    @FXML private Button btnSetSale;
    @FXML private Button btnCancelOp;

    /*Verificar Classes*/
    @FXML private TableView<Session> tableSeats;
    @FXML private TableColumn<Session, Integer> cSeat;
    @FXML private TableColumn<Session, Boolean> cStatus;

    @FXML
    public void initialize() throws SQLException {
        fill();
    }

    private ObservableList<Session> listSession = FXCollections.observableArrayList();


    public void fill() throws SQLException {

        SessionDAO daoSession = new SessionDAO();
        listSession= daoSession.readAll(0, true);;
        cbSessionSale.setItems(listSession);

    }

}
