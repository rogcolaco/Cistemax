package controller;

import dao.MovieDAO;
import dao.ReportDAO;
import dao.SessionDAO;
import dao.TheaterDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Movie;
import model.Sale;
import model.Session;
import model.Theater;

import java.io.File;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static util.Utils.mostrarAlerta;

public class Report extends MenuPrincipal {

    @FXML
    protected TabPane tbFilter;
    @FXML
    private DatePicker calDateStartReport;
    @FXML
    private DatePicker calDateEndReport;
    @FXML
    private Button btnRequestReport;
    //@FXML private Button btnCancelOp;
    @FXML
    private ChoiceBox<Movie> cbMovieReport;
    @FXML
    private ChoiceBox<Theater> cbTheaterReport;
    @FXML
    private ChoiceBox<Session> cbSessionReport;
    private ObservableList<Theater> listTheater = FXCollections.observableArrayList();
    private ObservableList<Movie> listMovie = FXCollections.observableArrayList();
    private ObservableList<Session> listSession = FXCollections.observableArrayList();

    @FXML
    public void initialize() throws SQLException {
        fill();
    }

    public ArrayList sessions(ArrayList<Sale> sales) {
        ArrayList<Session> sessions = sales.
                stream().
                map(s -> s.getSession())
                .collect(Collectors.toCollection(ArrayList::new));
        return sessions;
    }

    public void fillFilters() throws SQLException {
        try {
            ReportDAO reportDAO = new ReportDAO();
            TheaterDAO daoTheater = new TheaterDAO();
            MovieDAO daoMovie = new MovieDAO();
            SessionDAO daoSession = new SessionDAO();
            ArrayList<Sale> sales = reportDAO.readAll(calDateStartReport.getValue().toString(), calDateEndReport.getValue().toString());
            ArrayList<Session> sessions = sessions(sales);

            Set saleSessions = sales.stream().map(s -> s.getSessionId()).collect(Collectors.toSet());
            Set movieIds = sessions.stream().map(s -> s.getFilme().getId()).collect(Collectors.toSet());
            Set theaterIds = sessions.stream().map(s -> s.getTheater()).collect(Collectors.toSet());

            listTheater = daoTheater
                    .readAll()
                    .stream()
                    .filter(m -> theaterIds.contains(m.getId()))
                    .collect(Collector.of(
                            FXCollections::observableArrayList,
                            ObservableList::add,
                            (l1, l2) -> {
                                l1.addAll(l2);
                                return l1;
                            }));
            listMovie = daoMovie
                    .readAll()
                    .stream()
                    .filter(m -> movieIds.contains(m.getId()))
                    .collect(Collector.of(
                            FXCollections::observableArrayList,
                            ObservableList::add,
                            (l1, l2) -> {
                                l1.addAll(l2);
                                return l1;
                            }));
            listSession = daoSession.
                    readAll().
                    stream()
                    .filter(s -> saleSessions.contains(s.getId()))
                    .collect(Collector.of(
                            FXCollections::observableArrayList,
                            ObservableList::add,
                            (l1, l2) -> {
                                l1.addAll(l2);
                                return l1;
                            }));

            cbMovieReport.setItems(listMovie);
            cbTheaterReport.setItems(listTheater);
            cbSessionReport.setItems(listSession);
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Relatórios", "Erro ao Emitir Relatório", "", Alert.AlertType.ERROR);
        }

    }

    public void fill() throws SQLException {
        calDateEndReport.setValue(java.time.LocalDate.now());
        calDateStartReport.setValue(java.time.LocalDate.now());
        calDateEndReport.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = calDateStartReport.getValue();
                setDisable(empty || date.compareTo(today) < 0);
            }
        });
        fillFilters();
    }

    public void generateReport(ActionEvent actionEvent) {
        try {
            ReportDAO reportDAO = new ReportDAO();
            ArrayList<Sale> sales = reportDAO.readAll(calDateStartReport.getValue().toString(), calDateEndReport.getValue().toString());
            StringBuilder sb = new StringBuilder();
            sb.append("ID Venda; Data da Venda; Preco do Ingresso; Assentos; Qtd Assentos Promocionais; Total da Venda; Sessão\n");
            Files.createDirectories(Paths.get("reports/"));
            String pathName = "reports/report-" + calDateStartReport.getValue().toString() + "-" + calDateEndReport.getValue().toString() + ".csv";
            if (sales != null) {
                if (tbFilter.getSelectionModel().getSelectedIndex() == 0) {
                    if (!cbSessionReport.getSelectionModel().isEmpty()) {
                        Session session = cbSessionReport.getSelectionModel().getSelectedItem();
                        sales = sales
                                .stream()
                                .filter(s -> s.getSessionId() == session.getId())
                                .collect(Collectors.toCollection(ArrayList::new));
                    }
                }

                try (PrintWriter writer = new PrintWriter(new File(pathName))) {
                    for (Sale sale : sales) {
                        sb.append(sale.getId());
                        sb.append(";");
                        sb.append(sale.getDate());
                        sb.append(";");
                        sb.append(sale.getPrice());
                        sb.append(";");
                        sb.append(sale.getSeats());
                        sb.append(";");
                        sb.append(sale.getQtdSeatPromotional());
                        sb.append(";");
                        sb.append(sale.getTotalSale());
                        sb.append(";");
                        sb.append(sale.getSession());
                        sb.append("\n");
                    }
                    writer.write(sb.toString());
                }
            }
            mostrarAlerta("Relatórios", "Relatório Emitido com Sucesso", "", Alert.AlertType.INFORMATION);
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Relatórios", "Erro ao Emitir Relatório", "", Alert.AlertType.ERROR);
        }
    }

    public void changeStartDate(ActionEvent actionEvent) {
        calDateEndReport.setValue(null);
        calDateEndReport.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = calDateStartReport.getValue();
                setDisable(empty || date.compareTo(today) < 0);
            }
        });
    }
}