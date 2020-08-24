package controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dao.SaleDAO;
import dao.SessionDAO;
import dao.TicketDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.Sale;
import model.Seats;
import model.Session;
import model.Ticket;
import util.ErroDbAccess;
import util.Utils;

import javax.swing.*;
import java.awt.print.PrinterException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static util.Utils.mostrarAlerta;

public class NewSaleController extends MenuPrincipal{

    @FXML private Label lbTotal;
    @FXML private Label lbDate;

    @FXML private ChoiceBox<Session> cbSessionSale;
    @FXML private ChoiceBox<Integer> cbPromoTickets;
    @FXML private Button btnSetSale;
    //@FXML private Button btnCancelOp;
    @FXML private DatePicker calSessionDate;

    @FXML private TableView<Seats> tableSeats;
    @FXML private TableColumn<Seats, Integer> cSeat;
    @FXML private TableColumn<Seats, Boolean> cStatus;

    ErroDbAccess erro = new ErroDbAccess();


    @FXML
    public void initialize() throws SQLException {
        calSessionDate.setValue(java.time.LocalDate.now());
        fill();
    }

    private ObservableList<Session> listSession = FXCollections.observableArrayList();


    public void fill() throws SQLException {

        SessionDAO daoSession = new SessionDAO();
        listSession= daoSession.readAll(calSessionDate.getValue().toString());
        cbSessionSale.setItems(listSession);
        cbPromoTickets.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
            try {
                lbTotal.setText("Valor Total: R$ " + total());
            } catch (SQLException e) {
                lbTotal.setText("Valor Total: R$ ");
            }
        });

        calSessionDate.setDayCellFactory(picker -> new DateCell(){
            public void updateItem(LocalDate date, boolean empty){
                super.updateItem(date,empty);
                LocalDate today = LocalDate.now();
                setDisable(empty || date.compareTo(today)<0);
            }
        });

    }

    public void updateSeats(ActionEvent actionEvent) throws SQLException {
        Gson gson = new Gson();
        SessionDAO daoSession = new SessionDAO();
        Session s = new Session();
        ObservableList<Seats> seats = FXCollections.observableArrayList();
        s = daoSession.readOne(cbSessionSale.getSelectionModel().getSelectedItem().getId());

        if(!(cbSessionSale.getSelectionModel().getSelectedItem() == null)){
            calSessionDate.setDisable(true);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String sessionDate = calSessionDate.getValue().format(formatter);
            lbDate.setText("Data Selecionada: " + sessionDate);
            lbDate.setStyle("-fx-font-weight: bold;");
        } else {
            calSessionDate.setDisable(false);
            lbDate.setText("Data: ");
            lbDate.setStyle("-fx-font-weight: normal;");
        }

        HashMap<Integer, Boolean> seatMap = new Gson().fromJson(s.getSeats(), new TypeToken<HashMap<Integer, Boolean>>() {}.getType());
        tableSeats.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        for(Map.Entry<Integer, Boolean> map : seatMap.entrySet()){
            Seats seat = new Seats();
            seat.setNumber(map.getKey());
            seat.setAvailability(map.getValue());
            seats.add(seat);
        }

        cSeat.setCellValueFactory(new PropertyValueFactory<>("number"));
        cStatus.setCellValueFactory(new PropertyValueFactory<>("availability"));
        tableSeats.setItems(seats);
        cStatus.setCellFactory(col -> new TableCell<Seats, Boolean>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty) ;
                setText(empty ? null : item ? "Sim" : "Não" );
                if (getItem() != null) {
                    if (getItem()) {
                        this.setStyle("-fx-background-color: #ed5765; -fx-font-weight: bold;");
                    } else {
                        this.setStyle("-fx-background-color: #93bf85; -fx-font-weight: bold;");
                    }
                }
            }
        });
    }

    public void updatePromotionalTickets(MouseEvent mouseEvent) throws SQLException {
        ArrayList<Seats> p = new ArrayList<>(tableSeats.getSelectionModel().getSelectedItems());
        ObservableList<Integer> amount = FXCollections.observableArrayList();
        for (int i = 0; i <= p.size(); i++) {
            amount.add(i);
        }
        cbPromoTickets.setItems(amount);
    }

    public ArrayList<Integer> selectedSeats() {
        ArrayList<Seats> seats = new ArrayList<>(tableSeats.getSelectionModel().getSelectedItems());
        ArrayList<Integer> selected = new ArrayList<>();
        for (Seats seat : seats) {
            selected.add(seat.getNumber());
        }
        return selected;
    }

    public String updatedSeats(ArrayList<Integer> selectedSeats) throws SQLException {
        Gson gson = new Gson();
        SessionDAO daoSession = new SessionDAO();
        Session s = new Session();
        s = daoSession.readOne(cbSessionSale.getSelectionModel().getSelectedItem().getId());
        HashMap<Integer, Boolean> seatMap = new Gson().fromJson(s.getSeats(), new TypeToken<HashMap<Integer, Boolean>>() {}.getType());
        for (int seat : selectedSeats) {
            seatMap.put(seat, true);
        }
        return gson.toJson(seatMap);
    }

    public void printTicket(int id,String date, Session session, double price, ArrayList selected, int qtdSeats, int qtdPromotional, double totalSale) throws PrinterException {
        String intro = "### CISTEMAX ###\n\n";
        String strId = "Número da venda: " + id + "\n";
        String strDate = "Data: " + date + "\n";
        String strSession = "Sessão: " + session.toString() + "\n";
        String strPrice = "Valor unitário dos ingressos: R$" + String.valueOf(price).replace(".",",") + "\n";
        String strQtdSeats = "Quantidade de assentos totais: " + qtdSeats + "\n";
        String strQtdPromocionais = "Quantidade de assentos promocionais: " + qtdPromotional + "\n";
        String strSelected = "Assentos escolhidos: " + selected.toString().replace("[","").replace("]","") + "\n\n";
        String strTotal = "Total da compra: R$" + String.valueOf(totalSale).replace(".",",") + "\n";
        String strThanks = "\n\nObrigado pela preferência!" + "\n";
        JTextArea myTicket = new JTextArea();
        myTicket.setLineWrap(true);
        myTicket.append(intro + strId + strDate + strSession + strPrice + strQtdSeats + strQtdPromocionais +strSelected + strTotal + strThanks);
        myTicket.print();
    }

    public void validSale() {
        ArrayList<String> erros = new ArrayList<>();
        if (cbSessionSale.getValue() == null) {
            erros.add("Sessão não pode ser vazia\n");
        }
        if (cbPromoTickets.getValue() == null) {
            erros.add("Quantidade de tickets promocionais não pode ser vazia\n");
        }
        mostrarAlerta("Vendas", "Erro ao executar a venda.", Utils.trataErros(erros), Alert.AlertType.ERROR);
    }
    public Boolean validSale (Session session, ArrayList<Integer> selecteds) {
        ArrayList<String> erros = new ArrayList<>();
        HashMap<Integer, Boolean> seatMap = new Gson().fromJson(session.getSeats(), new TypeToken<HashMap<Integer, Boolean>>() {}.getType());
        for (int selected: selecteds) {
            if(seatMap.get(selected)){
                erros.add("Assento " + selected + " ocupado.\n");
            }
        }
        if (erros.isEmpty()){
            return true;
        } else {
            mostrarAlerta("Vendas", "Erro ao executar a venda.", Utils.trataErros(erros), Alert.AlertType.ERROR);
            return false;
        }
    }

    public void executeSale(ActionEvent actionEvent) throws SQLException, PrinterException {
        try {
            SaleDAO saleDAO = new SaleDAO();
            TicketDAO ticketDAO = new TicketDAO();
            SessionDAO sessionDAO = new SessionDAO();
            Session session = new Session();
            Double price = ticketDAO.ticketPrice(cbSessionSale.getSelectionModel().getSelectedItem().getTicket());
            ArrayList<Integer> selected = selectedSeats();
            int qtdSeats = selected.size();
            int qtdPromotional = cbPromoTickets.getSelectionModel().getSelectedItem();
            double totalSale = Double.parseDouble(total().replace(",","."));

            session = sessionDAO.readOne(cbSessionSale.getSelectionModel().getSelectedItem().getId());
            if (validSale(session, selected)){
                session.setSeats(updatedSeats(selected));
                Sale sale = new Sale(price,
                        selected.toString().replace("]","").replace("[","").replace(" ",""),
                        qtdPromotional,
                        totalSale,
                        session);

                sessionDAO.update(session);
                saleDAO.save(sale);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                String sessionDate = calSessionDate.getValue().format(formatter);
                printTicket(saleDAO.MaxId(), sessionDate, session, price, selected, qtdSeats, qtdPromotional, totalSale);
                updateSeats(new ActionEvent());
                lbTotal.setText("Valor Total:");
                cbPromoTickets.getSelectionModel().clearSelection();
                calSessionDate.setValue(java.time.LocalDate.now());
                cbSessionSale.valueProperty().set(null);
                tableSeats.getItems().clear();
                calSessionDate.setDisable(false);
                lbDate.setText("Data: ");
                lbDate.setStyle("-fx-font-weight: normal;");
                fill();
            }
        } catch (Exception e) {
            //e.printStackTrace();
            validSale();
        }
    }

    public String total() throws SQLException {

        Double totalSale;

        TicketDAO ticketDAO = new TicketDAO();
        Ticket ticket = ticketDAO.ticketById(cbSessionSale.getSelectionModel().getSelectedItem().getTicket());
        Session session = cbSessionSale.getSelectionModel().getSelectedItem();
        ArrayList<Integer> selected = selectedSeats();
        int qtdSeats = selected.size();
        int qtdPromotional = cbPromoTickets.getSelectionModel().isEmpty() ? 0 : cbPromoTickets.getSelectionModel().getSelectedItem();

        if(session.isPromotional()) {
            totalSale = (qtdSeats  * (ticket.ApplyDiscount()));
            cbPromoTickets.setValue(qtdSeats);
        } else {
            totalSale = ((qtdSeats - qtdPromotional) * ticket.getValue()) + (qtdPromotional * ticket.ApplyDiscount());
        }

        return String.format("%.2f", totalSale);
    }

    public void changeSessionDate(ActionEvent actionEvent) throws SQLException {
        try {
            if(!calSessionDate.getValue().equals(java.time.LocalDate.now())) {
                tableSeats.getItems().clear();
                fill();
            }
        } catch (Exception e){
            mostrarAlerta("Vendas", "Erro ao executar a venda.", "Por Favor Preencha o Campo 'Data' ", Alert.AlertType.ERROR);
            cbSessionSale.getSelectionModel().clearSelection();
            calSessionDate.setValue(java.time.LocalDate.now());
        }
    }
}
