package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import util.SwitcherDisplay;

import java.io.IOException;

public class ManageMovie {

    //Toolbar buttons
    @FXML private Button btnSale;
    @FXML private Button btnMovie;
    @FXML private Button btnTheater;
    @FXML private Button btnGenre;
    @FXML private Button btnPrice;
    @FXML private Button btnMovieSession;
    @FXML private Button btnCancelSale;
    @FXML private Button btnReport;

    @FXML private Button btnUpdateMovie;
    @FXML private Button btnConfirmMovie;
    @FXML private Label lbMovieFieldTitle;
    @FXML private ChoiceBox<String> cbParentalControl;

    /*Verificar Classes*/
    @FXML private TableView<String> tableMovie;
    @FXML private TableColumn<String,String> cMovieName;
    @FXML private TableColumn<String,String> cDirMovie;
    @FXML private TableColumn<String,String> cGenre;
    @FXML private TableColumn<String,String> cCartaz;

    /*Verificar Classes*/
    @FXML private ChoiceBox<String> cbType;
    @FXML private ChoiceBox<String> cbGenre;

    @FXML private CheckBox checkCartaz;
    @FXML private TextField txtMovieName;
    @FXML private TextField txtDirName;


    private ObservableList<String> obsParentalControl = FXCollections.observableArrayList();


    @FXML
    public void initialize(){
        cbParentalControl.setItems(loadParentalControl());
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
        this.loadParentalControl();
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

    public void updateMovie(ActionEvent actionEvent) {
        lbMovieFieldTitle.setText("Alterar Filme");
        btnConfirmMovie.setText("Confirma Alteração");
    }


    public ObservableList<String>  loadParentalControl(){

        obsParentalControl.add(new String("Livre"));
        obsParentalControl.add(new String("+12 (doze) anos"));
        obsParentalControl.add(new String("+14 (catorze) anos"));
        obsParentalControl.add(new String("+16 (dezesseis) anos"));
        obsParentalControl.add(new String("+18 (dezoito) anos"));

        return obsParentalControl;

    }


}
