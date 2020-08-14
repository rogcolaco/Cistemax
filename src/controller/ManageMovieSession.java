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
import model.Movie;
import model.Session;
import model.Theater;
import model.Ticket;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import com.google.gson.Gson;

public class ManageMovieSession extends MenuPrincipal{

    @FXML private Button btnRemoveSession;
    @FXML private Button btnConfirmSession;
    @FXML private Button btnUpdateSession;
    @FXML private Button btnCancelOp;
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
    @FXML private TableColumn<Session, String> cMovieSession;
    @FXML private TableColumn<Session, String> cStartsAt;
    @FXML private TableColumn<Session, String> cEndsAt;
    @FXML private TableColumn<Session, String> cSessionType;
    @FXML private TableColumn<Session, String> cSessionDate;

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
        cMovieSession.setCellValueFactory(new PropertyValueFactory<>("movieName"));
        cSessionDate.setCellValueFactory(new PropertyValueFactory<>("date"));
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

    public boolean validSession(Session session) throws SQLException {
        SessionDAO dao = new SessionDAO();
        ResultSet res = dao.checkSessions(session);
        return res.next();
    }

    public void addSession(ActionEvent actionEvent) throws SQLException, ParseException {
        try {
            Session session = new Session();
            SessionDAO dao = new SessionDAO();
            SimpleDateFormat formatoHoraMin = new SimpleDateFormat("HH:mm");
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Calendar c = Calendar.getInstance();
            Date initialDate = formatter.parse(dtInitial.getValue().toString());
            Date finalDate = formatter.parse(dtFinal.getValue().toString());
            Date startsAt = formatoHoraMin.parse(txtHour.getText() + ":" + txtMin.getText());
            int duration = cbMovieSession.getSelectionModel().getSelectedItem().getDuration();
            int idMovie = cbMovieSession.getSelectionModel().getSelectedItem().getId();
            int idTheater = cbTheater.getSelectionModel().getSelectedItem().getId();
            int qtdSeat = cbTheater.getSelectionModel().getSelectedItem().getQtdSeats();
            int idTicket = cbSession.getSelectionModel().getSelectedItem().getId();
            c.setTime(startsAt);
            c.add(Calendar.MINUTE, duration);

            LocalDate initialDt = initialDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate finalDt = finalDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            for (LocalDate date = initialDt; !date.isAfter(finalDt); date = date.plusDays(1)) {
                session.setId(dao.MaxId());
                session.setDate(date.toString());
                session.setStarts(formatoHoraMin.format(startsAt));
                session.setEnds(formatoHoraMin.format(c.getTime()));
                session.setSeats(createSeatMap(qtdSeat));
                session.setMovie(idMovie);
                session.setTheater(idTheater);
                session.setTicket(idTicket);
                session.setPromotional(checkPromo.isSelected());
                if(validSession(session)) {
                    MsgErro msg = new MsgErro();
                    msg.show();
                    break;
                }
                if (checkMon.isSelected() && date.getDayOfWeek().toString() == "MONDAY") {
                    dao.save(session);
                }
                if (checkTue.isSelected() && date.getDayOfWeek().toString() == "TUESDAY") {
                    dao.save(session);
                }
                if (checkWed.isSelected() && date.getDayOfWeek().toString() == "WEDNESDAY") {
                    dao.save(session);
                }
                if (checkThu.isSelected() && date.getDayOfWeek().toString() == "THURSDAY") {
                    dao.save(session);
                }
                if (checkFri.isSelected() && date.getDayOfWeek().toString() == "FRIDAY") {
                    dao.save(session);
                }
                if (checkSat.isSelected() && date.getDayOfWeek().toString() == "SATURDAY") {
                    dao.save(session);
                }
                if (checkSun.isSelected() && date.getDayOfWeek().toString() == "SUNDAY") {
                    dao.save(session);
                }
                tableSession.setItems(dao.readAll(cbTheater.getSelectionModel().getSelectedItem().getId()));

            }
        } catch(Exception e) {
            MsgErro msg = new MsgErro();
            msg.show();
        }
    }

    public void deleteSession(ActionEvent actionEvent) throws SQLException {
        SessionDAO dao = new SessionDAO();
        if (tableSession.getSelectionModel().getSelectedItem() != null) {
            dao.delete(tableSession.getSelectionModel().getSelectedItem());
            tableSession.setItems(dao.readAll(cbTheater.getSelectionModel().getSelectedItem().getId()));
        } else { return;}
    }
}
