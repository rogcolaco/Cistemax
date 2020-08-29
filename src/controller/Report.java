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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static util.Utils.mostrarAlerta;

public class Report extends MenuPrincipal {

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
    @FXML
    protected TabPane tbFilter;

    private ObservableList<Theater> listTheater = FXCollections.observableArrayList();
    private ObservableList<Movie> listMovie = FXCollections.observableArrayList();
    private ObservableList<Session> listSession = FXCollections.observableArrayList();

    @FXML
    public void initialize() throws SQLException {
        fill();
    }

    public void fillFilters() throws SQLException {
        ReportDAO reportDAO = new ReportDAO();
        ArrayList<Sale> sales = reportDAO.readAll(calDateStartReport.getValue().toString(), calDateEndReport.getValue().toString());
        Set saleSesions = new HashSet();
        saleSesions = sales.stream().map(s -> s.getSessionId()).collect(Collectors.toSet());
        TheaterDAO daoTheater = new TheaterDAO();
        MovieDAO   daoMovie = new MovieDAO();
        SessionDAO daoSession = new SessionDAO();


        listTheater = daoTheater.readAll();
        listMovie   = daoMovie.readAll();
        Set finalSaleSesions = saleSesions;
        listSession = daoSession.
                readAll().
                stream()
                .filter(s -> finalSaleSesions.contains(s.getId()))
                .collect(Collector.of(
                        FXCollections::observableArrayList,
                        ObservableList::add,
                        (l1, l2) -> { l1.addAll(l2); return l1; }));

        cbMovieReport.setItems(listMovie);
        cbTheaterReport.setItems(listTheater);
        cbSessionReport.setItems(listSession);

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
            sb.append("ID Venda; Data da Venda; Preco do Ingresso; Assentos; Qtd Assentos Promocionais; Total da Venda; Id da Sessso\n");
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
                } else {

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
                        sb.append(sale.getSessionId());
                        sb.append("\n");
                    }
                    writer.write(sb.toString());
                }
            }
            mostrarAlerta("Relatórios", "Relatório Emitido com Sucesso", "", Alert.AlertType.INFORMATION);
        } catch (Exception e) {
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