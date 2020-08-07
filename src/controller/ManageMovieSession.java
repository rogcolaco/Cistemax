package controller;

import dao.MovieDAO;
import dao.SessionDAO;
import dao.TheaterDAO;
import dao.TicketDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Movie;
import model.Session;
import model.Theater;
import model.Ticket;
import org.sqlite.date.DateFormatUtils;
import util.SwitcherDisplay;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import com.google.gson.Gson;

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
    @FXML private DatePicker dtInitial;
    @FXML private DatePicker dtFinal;


    /*Verificar Classe*/
    @FXML private ChoiceBox<Theater> cbTheater;
    @FXML private ChoiceBox<Ticket> cbSession;
    @FXML private ChoiceBox<Movie> cbMovieSession;

    /*Verificar Classe*/
    @FXML private TableView<Session> tableSession;
    @FXML private TableColumn<String,String> cMovieSession;
    @FXML private TableColumn<String, String> cStartsAt;
    @FXML private TableColumn<String, String> cEndsAt;
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

    public void updateMovies(ActionEvent actionEvent) throws SQLException {
        SessionDAO daoSession = new SessionDAO();
        cMovieSession.setCellValueFactory(new PropertyValueFactory<>("movie"));
        cStartsAt.setCellValueFactory(new PropertyValueFactory<>("starts"));
        cEndsAt.setCellValueFactory(new PropertyValueFactory<>("ends"));
        cSessionType.setCellValueFactory(new PropertyValueFactory<>("promotional"));
        tableSession.setItems(daoSession.readAll(cbTheater.getSelectionModel().getSelectedItem().getId()));
    }

    public void updateSession(ActionEvent actionEvent) {
        lbSessionFieldTitle.setText("Alterar Sala");
        btnConfirmSession.setText("Confirma Alteração");
    }

    private String createSeatMap(int qtd) {
        Map<Integer, Boolean> seats = new HashMap<Integer, Boolean>();
        Gson gson = new Gson();

        for (int i = 1; i <= qtd; i++) {
            seats.put(i,false);
        }

        return gson.toJson(seats);
    }

//    private Map<Integer, Boolean> readSeatMap(String j) {
//        Gson gson = new Gson();
//        Map<Integer, Boolean> json = gson.fromJson(j);
//        return json;
//    }

    public void addSession(ActionEvent actionEvent) throws SQLException, ParseException {
        Session session = new Session();
        SessionDAO dao = new SessionDAO();
        SimpleDateFormat formatoHoraMin = new SimpleDateFormat("HH:mm");
        Calendar c = Calendar.getInstance();
        Date startsAt = formatoHoraMin.parse(txtHour.getText() + ":" + txtMin.getText());
        int duration = cbMovieSession.getSelectionModel().getSelectedItem().getDuration();
        int idMovie = cbMovieSession.getSelectionModel().getSelectedItem().getId();
        int idTheater = cbTheater.getSelectionModel().getSelectedItem().getId();
        int qtdSeat = cbTheater.getSelectionModel().getSelectedItem().getQtdSeats();
        c.setTime(startsAt);
        c.add(Calendar.MINUTE, duration);

        System.out.println("Max id: " + dao.MaxId());
        session.setId(dao.MaxId());
        System.out.println("Initial Date: " + dtInitial.getValue());
        session.setDate(dtInitial.getValue().toString());
        System.out.println("Starts: " + formatoHoraMin.format(startsAt));
        session.setStarts(formatoHoraMin.format(startsAt));
        System.out.println("Ends: " + formatoHoraMin.format(c.getTime()));
        session.setEnds(formatoHoraMin.format(c.getTime()));
        System.out.println("Seat Map:" + createSeatMap(qtdSeat));
        session.setSeats(createSeatMap(qtdSeat));
        System.out.println("ID Movie: " + idMovie);
        session.setMovie(idMovie);
        System.out.println("ID Theater: " + idTheater);
        session.setTheater(idTheater);
        System.out.println("Promotional:" + checkPromo.isSelected());
        session.setPromotional(checkPromo.isSelected());
        dao.save(session);
    }
}
