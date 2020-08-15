package controller;

import com.sun.org.apache.bcel.internal.generic.INSTANCEOF;
import dao.TicketDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.ParentalControl;
import model.Ticket;
import util.SwitcherDisplay;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
        try {
            ticket.setType(txtSessionType.getText());
            String s = new String(txtSessionPrice.getText().replace(",","."));

            if(ticket.isDouble(s) && !ticket.getType().equals("")) {
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
                    int max = dao.MaxId();
                    ticket.setId(max);
                    dao.save(ticket);
                    tableSession.setItems(dao.readAll());
                }
                txtSessionType.clear();
                txtSessionPrice.clear();
            }else {
                    /*Desvia para tratamento de exceção*/
                    throw new NumberFormatException();
                }

        } catch (NumberFormatException n){
            MsgErro msg = new MsgErro();
            msg.show();
        }
    }

    public void remove(ActionEvent actionEvent) throws SQLException {
        TicketDAO dao = new TicketDAO();
        dao.delete(tableSession.getSelectionModel().getSelectedItem());
        tableSession.setItems(dao.readAll());
    }

}
