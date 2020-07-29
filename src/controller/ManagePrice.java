package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.ParentalControl;
import model.Ticket;
import util.SwitcherDisplay;

import java.io.IOException;

public class ManagePrice {

    //Toolbar buttons
    @FXML private Button btnSale;
    @FXML private Button btnMovie;
    @FXML private Button btnTheater;
    @FXML private Button btnGenre;
    @FXML private Button btnPrice;
    @FXML private Button btnMovieSession;
    @FXML private Button btnCancelSale;
    @FXML private Button btnReport;

    @FXML private Button btnConfirmPrice;
    @FXML private Button btnUpdatePrice;
    @FXML private Label lbPriceFieldTitle;
    @FXML private Button btnRemoveSession;
    @FXML private TextField txtSessionType;
    @FXML private TextField txtSessionPrice;

    /*Verificar Classes*/
    @FXML private TableView<Ticket> tableSession;
    @FXML private TableColumn<Ticket, String> cSessionType;
    @FXML private TableColumn<Ticket, Double> cSessionPrice;

    private Ticket t = new Ticket();

    @FXML
    public void initialize(){
        fill();
    }

    public void fill(){

            cSessionType.setCellValueFactory(new PropertyValueFactory<>("type"));
            cSessionPrice.setCellValueFactory(new PropertyValueFactory<>("value"));
            tableSession.setItems(t.loadTable());
    }


    public void newSale(ActionEvent actionEvent) {
        Stage stage = (Stage)btnSale.getScene().getWindow();
        SwitcherDisplay display = new SwitcherDisplay();
        display.show("/view/NewSale.fxml",stage,"Cistemax - Gerenciar Vendas", btnSale.getScene().getWidth(), btnSale.getScene().getHeight());
    }

    public void mngMovie(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage)btnSale.getScene().getWindow();
        SwitcherDisplay display = new SwitcherDisplay();
        display.show("/view/ManageMovie.fxml", stage, "Cistemax - Gerenciar Filmes",btnSale.getScene().getWidth(), btnSale.getScene().getHeight());
    }

    public void mngTheater(ActionEvent actionEvent) {
        Stage stage = (Stage)btnSale.getScene().getWindow();
        SwitcherDisplay display = new SwitcherDisplay();
        display.show("/view/ManageTheater.fxml",stage,"Cistemax - Gerenciar Salas",btnSale.getScene().getWidth(), btnSale.getScene().getHeight());
    }

    public void mngGenre(ActionEvent actionEvent) {
        Stage stage = (Stage)btnSale.getScene().getWindow();
        SwitcherDisplay display = new SwitcherDisplay();
        display.show("/view/ManageGenre.fxml",stage,"Cistemax - Gerenciar Gêneros",btnSale.getScene().getWidth(), btnSale.getScene().getHeight());
    }

    public void mngPrice(ActionEvent actionEvent) {
        Stage stage = (Stage)btnSale.getScene().getWindow();
        SwitcherDisplay display = new SwitcherDisplay();
        display.show("/view/ManagePrice.fxml",stage,"Cistemax - Gerenciar Preço",btnSale.getScene().getWidth(), btnSale.getScene().getHeight());
    }

    public void mngMovieSession(ActionEvent actionEvent) {
        Stage stage = (Stage)btnSale.getScene().getWindow();
        SwitcherDisplay display = new SwitcherDisplay();
        display.show("/view/ManageMovieSession.fxml",stage,"Cistemax - Gerenciar Sessões",btnSale.getScene().getWidth(), btnSale.getScene().getHeight());
    }

    public void mngCancelSale(ActionEvent actionEvent) {
        Stage stage = (Stage)btnSale.getScene().getWindow();
        SwitcherDisplay display = new SwitcherDisplay();
        display.show("/view/CancelSale.fxml",stage,"Cistemax - Cancelar Venda ",btnSale.getScene().getWidth(), btnSale.getScene().getHeight());
    }

    public void mngReport(ActionEvent actionEvent) {
        Stage stage = (Stage)btnSale.getScene().getWindow();
        SwitcherDisplay display = new SwitcherDisplay();
        display.show("/view/Report.fxml",stage,"Cistemax - Emitir Relatório",btnSale.getScene().getWidth(), btnSale.getScene().getHeight());
    }

    public void CancelOp(ActionEvent actionEvent) {
        this.newSale(actionEvent);
    }

    public void updatePrice(ActionEvent actionEvent) {
        lbPriceFieldTitle.setText("Alterar Preço da Sessão");
        btnConfirmPrice.setText("Confirma Alteração");

        Ticket ticket=tableSession.getSelectionModel().getSelectedItem();
        txtSessionType.setText(ticket.getType());
        txtSessionPrice.setText(String.valueOf(ticket.getValue()));

    }

    public void confirm(ActionEvent actionEvent) {
        Ticket ticket = new Ticket();
        ticket.setType(txtSessionType.getText());
        ticket.setValue(Double.parseDouble(txtSessionPrice.getText()));
        t.addTicket(ticket);
        tableSession.setItems(t.loadTable());
        txtSessionType.clear();
        txtSessionPrice.clear();

        if(btnConfirmPrice.getText().equals("Confirma Alteração")){
            t.removeTicket(tableSession.getSelectionModel().getSelectedItem());
            lbPriceFieldTitle.setText("Cadastrar Novo Tipo de Sessão");
            btnConfirmPrice.setText("Confirmar");
        }
    }

    public void remove(ActionEvent actionEvent) {
        t.removeTicket(tableSession.getSelectionModel().getSelectedItem());
        tableSession.setItems(t.loadTable());
    }
}
