package controller;

import dao.GenreDAO;
import dao.MovieDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Genre;
import model.Movie;
import model.ParentalControl;
import util.SwitcherDisplay;

import java.io.IOException;
import java.sql.SQLException;

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
    @FXML private TableView<Movie> tableMovie;
    @FXML private TableColumn<String,String> cMovieName;
    @FXML private TableColumn<String,String> cDirMovie;
    @FXML private TableColumn<Genre,String> cGenre;
    @FXML private TableColumn<String,String> cCartaz;

    /*Verificar Classes*/
    @FXML private ChoiceBox<Genre> cbGenre;

    @FXML private CheckBox checkCartaz;
    @FXML private TextField txtMovieName;
    @FXML private TextField txtDirName;
    @FXML private TextField txtDuration;


    @FXML
    public void initialize(){
        /*Preenche o Choice Box da Classificação indicativa*/
        ParentalControl pc = new ParentalControl();
        cbParentalControl.setItems(pc.loadParentalControl());
    }

    public void Fill(){
        GenreDAO daoGenre = new GenreDAO();
        cbGenre.setItems(daoGenre.readAll());
        MovieDAO daoMovie = new MovieDAO();
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

    public void updateMovie(ActionEvent actionEvent) {
        lbMovieFieldTitle.setText("Alterar Filme");
        btnConfirmMovie.setText("Confirma Alteração");
    }

    public void addMovie(ActionEvent actionEvent) throws SQLException {
        Movie movie = new Movie();
        MovieDAO dao = new MovieDAO();

        movie.setName(txtMovieName.getText());
        /*Caso o botão de confirmação seja utilizado para alterar um filme*/
        if (btnConfirmMovie.getText().equals("Alterar")) {
            movie.setId(tableMovie.getSelectionModel().getSelectedItem().getId());
            //dao.update(movie);
            tableMovie.setItems(dao.readAll());
            //lbGenreFieldTitle.setText("Cadastrar Novo Gênero");
            //btnConfirmGenre.setText("Confirmar");

            /*Caso o botão de confirmação seja utilizado para salvar um filme novo*/
        } else {
            int max = dao.MaxId();
            movie.setId(max);
            dao.save(movie);
            tableMovie.setItems(dao.readAll());
        }
        txtMovieName.clear();
    }
}
