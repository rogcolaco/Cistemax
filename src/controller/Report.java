package controller;

import dao.GenreDAO;
import dao.ReportDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Menu;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Sale;
import util.SwitcherDisplay;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Report extends MenuPrincipal {

    @FXML private DatePicker calDateStartReport;
    @FXML private DatePicker calDateEndReport;
    @FXML private Button btnRequestReport;
    //@FXML private Button btnCancelOp;

    @FXML public void initialize(){ fill(); }

    public void fill(){
        calDateEndReport.setValue(java.time.LocalDate.now());
        calDateStartReport.setValue(java.time.LocalDate.now());
    }

    public void generateReport(ActionEvent actionEvent) {
        try {
            ReportDAO reportDAO = new ReportDAO();
//        System.out.println(calDateEndReport.getValue());
            ArrayList<Sale> sales = reportDAO.readAll(calDateStartReport.getValue().toString(), calDateEndReport.getValue().toString());
            StringBuilder sb = new StringBuilder();
            sb.append("ID Venda; Data da Venda; Preco do Ingresso; Assentos; Qtd Assentos Promocionais; Total da Venda; Id da Sessso\n");
            Files.createDirectories(Paths.get("reports/"));
            String pathName = "reports/report-" + calDateStartReport.getValue().toString() + "-" + calDateEndReport.getValue().toString() +".csv";
            if (sales != null) {
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
            ReportSuccess msg = new ReportSuccess();
            msg.show();
        } catch (Exception e) {
            MsgErro msg = new MsgErro();
            msg.show();
        }
    }
}
