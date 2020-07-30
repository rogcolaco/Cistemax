package controller;

import dao.TheaterDAO;
import dao.TicketDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Theater;
import model.Ticket;
import util.SwitcherDisplay;

import java.io.IOException;
import java.sql.SQLException;

public class ManageTheater {
    //Toolbar buttons
    @FXML private Button btnSale;
    @FXML private Button btnMovie;
    @FXML private Button btnTheater;
    @FXML private Button btnGenre;
    @FXML private Button btnPrice;
    @FXML private Button btnMovieSession;
    @FXML private Button btnCancelSale;
    @FXML private Button btnReport;

    @FXML private Button btnUpdateTheater;
    @FXML private Button btnConfirmTheater;
    @FXML private Label lbTheaterFieldTitle;
    @FXML private Button btnRemoveTheater;
    @FXML private TextField txtTheaterName;
    @FXML private TextField txtQtdSeats;

    /*Verificar Classes*/
    @FXML private TableView<Theater> tableTheater;
    @FXML private TableColumn<Theater, String> cTheaterName;
    @FXML private TableColumn<Theater, Integer> cQtdSeats;

    @FXML
    public void initialize(){
        fill();
    }

    public void fill(){
        TheaterDAO dao = new TheaterDAO();
        cTheaterName.setCellValueFactory(new PropertyValueFactory<>("name"));
        cQtdSeats.setCellValueFactory(new PropertyValueFactory<>("qtdSeats"));
        tableTheater.setItems(dao.readAll());
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

    public void updateTheater(ActionEvent actionEvent) {
        lbTheaterFieldTitle.setText("Alterar Sala");
        btnConfirmTheater.setText("Alterar");

        Theater theater=tableTheater.getSelectionModel().getSelectedItem();
        txtTheaterName.setText(theater.getName());
        txtQtdSeats.setText(String.valueOf(theater.getQtdSeats()));
    }

    public void confirm(ActionEvent actionEvent) throws SQLException {
        Theater theater = new Theater();
        TheaterDAO dao = new TheaterDAO();
        theater.setName(txtTheaterName.getText());
        theater.setQtdSeats(Integer.parseInt(txtQtdSeats.getText()));

        /*Caso o botão de confirmação seja utilizado para alterar um ticket*/
        if(btnConfirmTheater.getText().equals("Alterar")){
            theater.setId(tableTheater.getSelectionModel().getSelectedItem().getId());
            dao.update(theater);
            tableTheater.setItems(dao.readAll());
            //t.removeTicket(tableSession.getSelectionModel().getSelectedItem());
            lbTheaterFieldTitle.setText("Cadastrar Nova Sala");
            btnConfirmTheater.setText("Confirmar");

            /*Caso o botão de confirmação seja utilizado para salvar um ticket novo*/
        } else {
            int max = dao.MaxId();
            theater.setId(max);
            dao.save(theater);
            tableTheater.setItems(dao.readAll());
        }
        txtTheaterName.clear();
        txtQtdSeats.clear();
    }

    public void remove(ActionEvent actionEvent) throws SQLException {
        TheaterDAO dao = new TheaterDAO();
        dao.delete(tableTheater.getSelectionModel().getSelectedItem());
        tableTheater.setItems(dao.readAll());
    }
}
