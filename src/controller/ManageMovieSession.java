package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import util.SwitcherDisplay;

import java.io.IOException;

public class ManageMovieSession {

    //Toolbar buttons
    @FXML private Button btnSale;
    @FXML private Button btnMovie;
    @FXML private Button btnTheater;
    @FXML private Button btnGenre;
    @FXML private Button btnPrice;
    @FXML private Button btnMovieSession;
    @FXML private Button btnCancelSale;
    @FXML private Button btnReport;

    @FXML private Button btnRemoveSession;
    @FXML private Button btnConfirmSession;
    @FXML private Button btnUpdateSession;
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

    /*Verificar Classe*/
    @FXML private ChoiceBox<String> cbTheater;
    @FXML private ChoiceBox<String> cbSession;
    @FXML private ChoiceBox<String> cbMovieSession;

    /*Verificar Classe*/
    @FXML private TableView<String> tableSession;
    @FXML private TableColumn<String,String> cMovieSession;
    @FXML private TableColumn<String, String> cDirMovieSession;
    @FXML private TableColumn<String,String> cGenre;
    @FXML private TableColumn<String,String> cSessionType;

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

    public void updateSession(ActionEvent actionEvent) {
        lbSessionFieldTitle.setText("Alterar Sala");
        btnConfirmSession.setText("Confirma Alteração");
    }
}
