package controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sun.org.apache.xpath.internal.operations.Bool;
import dao.SessionDAO;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Session;
import util.SwitcherDisplay;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

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

    public void updateSeats(ActionEvent actionEvent) throws SQLException {
        Gson gson = new Gson();
        SessionDAO daoSession = new SessionDAO();
        Session s = new Session();
        s = daoSession.readOne(cbSessionSale.getSelectionModel().getSelectedItem().getId());
        HashMap<Integer, Boolean> seatMap = new Gson().fromJson(s.getSeats(), new TypeToken<HashMap<Integer, Boolean>>() {}.getType());
        System.out.println(seatMap);

        for(Map.Entry<Integer, Boolean> map : seatMap.entrySet()){
//            cSeat.setCellValueFactory(c -> new SimpleStringProperty(map.getKey().toString()));
//            cStatus.setCellValueFactory(c -> new SimpleStringProperty(map.getValue().toString()));
            System.out.println(map.getKey());
            System.out.println(map.getValue());
        }
    }

}
