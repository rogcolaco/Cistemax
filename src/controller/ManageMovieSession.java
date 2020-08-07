package controller;

import dao.TheaterDAO;
import dao.TicketDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Theater;
import model.Ticket;
import util.SwitcherDisplay;

import java.io.IOException;

public class ManageMovieSession extends MenuPrincipal{

    //Toolbar buttons
    @FXML private Button btnSale;
    @FXML private Button btnMovie;
    @FXML private Button btnTheater;
    @FXML private Button btnGenre;
    @FXML private Button btnPrice;
    @FXML private Button btnMovieSession;
    @FXML private Button btnCancelSale;
    @FXML private Button btnReport;

    @FXML private Button btnRemoveSession;
    @FXML private Button btnConfirmSession;
    @FXML private Button btnUpdateSession;
    @FXML private Label lbSessionFieldTitle;
    @FXML private CheckBox checkPromo;
    @FXML private CheckBox checkMon;
    @FXML private CheckBox checkTue;
    @FXML private CheckBox checkWed;
    @FXML private CheckBox checkThu;
    @FXML private CheckBox checkFri;
    @FXML private CheckBox checkSat;
    @FXML private CheckBox checkSun;
    @FXML private TextField txtHour;
    @FXML private TextField txtMin;

    /*Verificar Classe*/
    @FXML private ChoiceBox<Theater> cbTheater;
    @FXML private ChoiceBox<Ticket> cbSession;
    @FXML private ChoiceBox<Movie> cbMovieSession;

    /*Verificar Classe*/
    @FXML private TableView<String> tableSession;
    @FXML private TableColumn<String,String> cMovieSession;
    @FXML private TableColumn<String, String> cDirMovieSession;
    @FXML private TableColumn<String,String> cGenre;
    @FXML private TableColumn<String,String> cSessionType;

    @FXML
    public void initialize() throws SQLException {
        fill();
    }

    private ObservableList<Ticket> listTicket = FXCollections.observableArrayList();
    private ObservableList<Theater> listTheater = FXCollections.observableArrayList();
    private ObservableList<Movie> listMovie = FXCollections.observableArrayList();


    public void fill() throws SQLException {

        /*Preenche o choice box com dados dos tipos de sessão cadastrados*/
        TicketDAO daoTicket = new TicketDAO();
        listTicket = daoTicket.readAll();;
        cbSession.setItems(listTicket);

        /*Preenche o choice box com dados das salas cadastradas*/
        TheaterDAO daoTheater = new TheaterDAO();
        listTheater = daoTheater.readAll();;
        cbTheater.setItems(listTheater);

        MovieDAO daoMovie = new MovieDAO();
        listMovie = daoMovie.readAll(true);
        cbMovieSession.setItems(listMovie);
    }


    public void updateSession(ActionEvent actionEvent) {
        lbSessionFieldTitle.setText("Alterar Sala");
        btnConfirmSession.setText("Confirma Alteração");
    }
}
