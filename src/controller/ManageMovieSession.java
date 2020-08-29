package controller;

import com.google.gson.Gson;
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
import util.Regex;
import util.Utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

import static util.Utils.mostrarAlerta;

public class ManageMovieSession extends MenuPrincipal {

    Regex regex = new Regex();
    @FXML
    private Button btnRemoveSession;
    @FXML
    private Button btnConfirmSession;
    @FXML
    private Button btnUpdateSession;
    //@FXML private Button btnCancelOp;
    @FXML
    private Label lbSessionFieldTitle;
    @FXML
    private CheckBox checkPromo;
    @FXML
    private CheckBox checkMon;
    @FXML
    private CheckBox checkTue;
    @FXML
    private CheckBox checkWed;
    @FXML
    private CheckBox checkThu;
    @FXML
    private CheckBox checkFri;
    @FXML
    private CheckBox checkSat;
    @FXML
    private CheckBox checkSun;
    @FXML
    private TextField txtHour;
    @FXML
    private TextField txtMin;
    @FXML
    private DatePicker dtInitial;
    @FXML
    private DatePicker dtFinal;
    /*Verificar Classe*/
    @FXML
    private ChoiceBox<Theater> cbTheater;
    @FXML
    private ChoiceBox<Ticket> cbSession;
    @FXML
    private ChoiceBox<Movie> cbMovieSession;
    /*Verificar Classe*/
    @FXML
    private TableView<Session> tableSession;
    @FXML
    private TableColumn<Session, String> cMovieSession;
    @FXML
    private TableColumn<Session, String> cStartsAt;
    @FXML
    private TableColumn<Session, String> cEndsAt;
    @FXML
    private TableColumn<Session, Boolean> cSessionType;
    @FXML
    private TableColumn<Session, String> cSessionDate;
    private ObservableList<Ticket> listTicket = FXCollections.observableArrayList();
    private ObservableList<Theater> listTheater = FXCollections.observableArrayList();
    private ObservableList<Movie> listMovie = FXCollections.observableArrayList();

    @FXML
    public void initialize() throws SQLException {
        fill();
    }

    public void fill() throws SQLException {

        /*Preenche o choice box com dados dos tipos de sessão cadastrados*/
        TicketDAO daoTicket = new TicketDAO();
        listTicket = daoTicket.readAll();
        cbSession.setItems(listTicket);

        /*Preenche o choice box com dados das salas cadastradas*/
        TheaterDAO daoTheater = new TheaterDAO();
        listTheater = daoTheater.readAll();
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
        cSessionType.setCellFactory(col -> new TableCell<Session, Boolean>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : item ? "Sim" : "Não");
            }
        });
    }

    public void updateSession(ActionEvent actionEvent) {
        lbSessionFieldTitle.setText("Alterar Sala");
        btnConfirmSession.setText("Confirma Alteração");
    }

    private String createSeatMap(int qtd) {
        Map<Integer, Boolean> seats = new HashMap<Integer, Boolean>();
        Gson gson = new Gson();

        for (int i = 1; i <= qtd; i++) {
            seats.put(i, false);
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
            if (checkFill()) {
                ArrayList<String> errors = new ArrayList<>();
                ArrayList<Session> sessions = new ArrayList<>();
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
                    if (validSession(session)) {
                        if (checkMon.isSelected() && date.getDayOfWeek().toString() == "MONDAY") {
                            errors.add("Já existe uma sessão para a data: " + date.toString() + "\n");
                        }
                        if (checkTue.isSelected() && date.getDayOfWeek().toString() == "TUESDAY") {
                            errors.add("Já existe uma sessão para a data: " + date.toString() + "\n");
                        }
                        if (checkWed.isSelected() && date.getDayOfWeek().toString() == "WEDNESDAY") {
                            errors.add("Já existe uma sessão para a data: " + date.toString() + "\n");
                        }
                        if (checkThu.isSelected() && date.getDayOfWeek().toString() == "THURSDAY") {
                            errors.add("Já existe uma sessão para a data: " + date.toString() + "\n");
                        }
                        if (checkFri.isSelected() && date.getDayOfWeek().toString() == "FRIDAY") {
                            errors.add("Já existe uma sessão para a data: " + date.toString() + "\n");
                        }
                        if (checkSat.isSelected() && date.getDayOfWeek().toString() == "SATURDAY") {
                            errors.add("Já existe uma sessão para a data: " + date.toString() + "\n");
                        }
                        if (checkSun.isSelected() && date.getDayOfWeek().toString() == "SUNDAY") {
                            errors.add("Já existe uma sessão para a data: " + date.toString() + "\n");
                        }
                    } else {
                        sessions.add(session);
                    }
                }

                if (errors.isEmpty()) {
                    for (Session s : sessions) {
                        Date date = formatter.parse(s.getDate());
                        LocalDate d = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                        if (checkMon.isSelected() && d.getDayOfWeek().toString() == "MONDAY") {
                            dao.save(s);
                        }
                        if (checkTue.isSelected() && d.getDayOfWeek().toString() == "TUESDAY") {
                            dao.save(s);
                        }
                        if (checkWed.isSelected() && d.getDayOfWeek().toString() == "WEDNESDAY") {
                            dao.save(s);
                        }
                        if (checkThu.isSelected() && d.getDayOfWeek().toString() == "THURSDAY") {
                            dao.save(s);
                        }
                        if (checkFri.isSelected() && d.getDayOfWeek().toString() == "FRIDAY") {
                            dao.save(s);
                        }
                        if (checkSat.isSelected() && d.getDayOfWeek().toString() == "SATURDAY") {
                            dao.save(s);
                        }
                        if (checkSun.isSelected() && d.getDayOfWeek().toString() == "SUNDAY") {
                            dao.save(s);
                        }
                        tableSession.setItems(dao.readAll(cbTheater.getSelectionModel().getSelectedItem().getId()));
                    }
                } else {
                    mostrarAlerta("Vendas", "Erro ao cadastrar Sessão.", Utils.trataErros(errors), Alert.AlertType.ERROR);
                }
            }
        } catch (Exception e) {
            MsgErro msg = new MsgErro();
            msg.show();
        }
    }

    public void deleteSession(ActionEvent actionEvent) throws SQLException {
        SessionDAO dao = new SessionDAO();
        if (tableSession.getSelectionModel().getSelectedItem() != null) {
            dao.delete(tableSession.getSelectionModel().getSelectedItem());
            tableSession.setItems(dao.readAll(cbTheater.getSelectionModel().getSelectedItem().getId()));
        } else {
            return;
        }
    }

    public void changeStartDate(ActionEvent actionEvent) {
        dtFinal.setValue(null);
        dtFinal.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = dtInitial.getValue();
                setDisable(empty || date.compareTo(today) < 0);
            }
        });
    }

    public boolean checkFill() {
        ArrayList<String> erros = new ArrayList<>();
        if (cbTheater.getSelectionModel().getSelectedItem() == null) {
            erros.add("Sala não pode ser vazia.\n");
        }
        if (cbMovieSession.getSelectionModel().getSelectedItem() == null) {
            erros.add("Nome do Filme não pode ser vazio.\n");
        }
        if (cbSession.getSelectionModel().getSelectedItem() == null) {
            erros.add("Tipo de Sessão não pode ser vazio.\n");
        }

        if (!regex.isInt(txtHour.getText())) {
            erros.add("Hora de Início da sessão deve conter apenas números entre 0h e 23h.\n");
        } else if (Integer.parseInt(txtHour.getText()) > 23 || Integer.parseInt(txtHour.getText()) < 0) {
            erros.add("Hora de Início da sessão deve ser entre 0h e 23h.\n");
        }

        if (!regex.isInt(txtMin.getText())) {
            erros.add("Minutos de Início da sessão deve conter apenas números entre 0min e 59min.\n");
        } else if (Integer.parseInt(txtMin.getText()) > 59 || Integer.parseInt(txtMin.getText()) < 0) {
            erros.add("Minutos de Início da sessão deve conter apenas números entre 0min e 59min.\n");
        }

        if (dtInitial.getValue() == null) {
            erros.add("Data de início da Sessão não pode ser vazio.\n");
        }
        if (dtFinal.getValue() == null) {
            erros.add("Data de fim da Sessão não pode ser vazio.\n");
        }
        if (!checkMon.isSelected() && !checkTue.isSelected() && !checkWed.isSelected() && !checkThu.isSelected() && !checkFri.isSelected()
                && !checkSat.isSelected() && !checkSun.isSelected()) {
            erros.add("Selecione pelo menos um dia da semana para exibição da sessão.\n");
        }

        if (erros.isEmpty()) {
            return true;
        } else {
            mostrarAlerta("Sessão", "Erro ao cadastrar Sessão.", Utils.trataErros(erros), Alert.AlertType.ERROR);
            return false;
        }
    }
}
