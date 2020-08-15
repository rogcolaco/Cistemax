package controller;

import dao.TicketDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Ticket;
import util.Regex;

import java.sql.SQLException;

import static util.Utils.mostrarAlerta;

public class ManagePrice extends MenuPrincipal{

    @FXML private Button btnConfirmPrice;
    @FXML private Button btnUpdatePrice;
    //@FXML private Button btnCancelOp;
    @FXML private Label lbPriceFieldTitle;
    @FXML private Button btnRemoveSession;
    @FXML private TextField txtSessionType;
    @FXML private TextField txtSessionPrice;

    @FXML private TableView<Ticket> tableSession;
    @FXML private TableColumn<Ticket, String> cSessionType;
    @FXML private TableColumn<Ticket, Double> cSessionPrice;

    Regex regex = new Regex();

    @FXML
    public void initialize(){
        fill();
    }

    public void fill(){
        TicketDAO dao = new TicketDAO();
        cSessionType.setCellValueFactory(new PropertyValueFactory<>("type"));
        cSessionPrice.setCellValueFactory(new PropertyValueFactory<>("value"));
        tableSession.setItems(dao.readAll());
    }


    public void updatePrice(ActionEvent actionEvent) {
        lbPriceFieldTitle.setText("Alterar Preço da Sessão");
        btnConfirmPrice.setText("Alterar");

        Ticket ticket=tableSession.getSelectionModel().getSelectedItem();
        txtSessionType.setText(ticket.getType());
        txtSessionPrice.setText(String.valueOf(ticket.getValue()));
    }

    public void confirm(ActionEvent actionEvent) throws SQLException{
        Ticket ticket = new Ticket();
        TicketDAO dao = new TicketDAO();
            ticket.setType(txtSessionType.getText());
            String s = new String(txtSessionPrice.getText().replace(",", "."));

            if (regex.isDouble(s) && !ticket.getType().equals("")) {
                ticket.setValue(Double.parseDouble(s));

                /*Caso o botão de confirmação seja utilizado para alterar um ticket*/
                if (btnConfirmPrice.getText().equals("Alterar")) {
                    ticket.setId(tableSession.getSelectionModel().getSelectedItem().getId());
                    dao.update(ticket);
                    tableSession.setItems(dao.readAll());
                    lbPriceFieldTitle.setText("Cadastrar Novo Tipo de Sessão");
                    btnConfirmPrice.setText("Confirmar");

                /*Caso o botão de confirmação seja utilizado para salvar um ticket novo*/
                } else {
                    int max = dao.maxId();
                    ticket.setId(max);
                    dao.save(ticket);
                    tableSession.setItems(dao.readAll());
                }
                txtSessionType.clear();
                txtSessionPrice.clear();
            } else {
                if (ticket.getType().equals("")) {
                    mostrarAlerta("Tipos de Sessão e Preços", "Tipo de Sessão", "Por Favor Preencha o Campo 'Tipo da Sessão'", Alert.AlertType.ERROR);
                    return;
                }

                if (!regex.isDouble(s)) {
                    mostrarAlerta("Tipos de Sessão e Preços", "Tipo de Sessão", "Por Favor Preencha o Campo 'Preço' em Reais", Alert.AlertType.ERROR);
                    return;
                }
            }
    }

    public void remove(ActionEvent actionEvent) throws SQLException {
        TicketDAO dao = new TicketDAO();
        dao.delete(tableSession.getSelectionModel().getSelectedItem());
        tableSession.setItems(dao.readAll());
    }

}
