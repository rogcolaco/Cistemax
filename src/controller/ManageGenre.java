package controller;

import dao.GenreDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Genre;
import util.SwitcherDisplay;

import java.io.IOException;
import java.sql.SQLException;

public class ManageGenre {

    //Toolbar buttons
    @FXML
    private Button btnSale;
    @FXML private Button btnMovie;
    @FXML private Button btnTheater;
    @FXML private Button btnGenre;
    @FXML private Button btnPrice;
    @FXML private Button btnMovieSession;
    @FXML private Button btnCancelSale;
    @FXML private Button btnReport;

    @FXML private Button btnUpdateGenre;
    @FXML private Button btnConfirmGenre;
    @FXML private Label lbGenreFieldTitle;
    @FXML private Button btnRemoveGenre;
    @FXML private TextField txtGenreName;

    /*Confirmar Classes*/
    @FXML private TableView<Genre> tableGenre;
    @FXML private TableColumn<Genre, String> cGenre;

    @FXML public void initialize(){ fill(); }

    public void fill(){
        GenreDAO dao = new GenreDAO();
        cGenre.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableGenre.setItems(dao.readAll());
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
        display.show("/view/CancelSale.fxml",stage,"Cistemax - Cancelar Venda",btnSale.getScene().getWidth(), btnSale.getScene().getHeight());
    }

    public void mngReport(ActionEvent actionEvent) {
        Stage stage = (Stage)btnSale.getScene().getWindow();
        SwitcherDisplay display = new SwitcherDisplay();
        display.show("/view/Report.fxml",stage,"Cistemax - Emitir Relatório",btnSale.getScene().getWidth(), btnSale.getScene().getHeight());
    }

    public void CancelOp(ActionEvent actionEvent) {
        this.newSale(actionEvent);
    }

    public void updateGenre(ActionEvent actionEvent) {
        lbGenreFieldTitle.setText("Alterar Gênero");
        btnConfirmGenre.setText("Alterar");

        Genre genre = tableGenre.getSelectionModel().getSelectedItem();
        txtGenreName.setText((genre.getName()));
    }

    public void removeGenre(ActionEvent actionEvent) throws SQLException {
        GenreDAO dao = new GenreDAO();
        dao.delete(tableGenre.getSelectionModel().getSelectedItem());
        tableGenre.setItems(dao.readAll());
    }

    public void addGenre(ActionEvent actionEvent) throws SQLException {
        Genre genre = new Genre();
        GenreDAO dao = new GenreDAO();

        genre.setName(txtGenreName.getText());
        /*Caso o botão de confirmação seja utilizado para alterar um gênero*/
        if (btnConfirmGenre.getText().equals("Alterar")) {
            genre.setId(tableGenre.getSelectionModel().getSelectedItem().getId());
            dao.update(genre);
            tableGenre.setItems(dao.readAll());
            lbGenreFieldTitle.setText("Cadastrar Novo Gênero");
            btnConfirmGenre.setText("Confirmar");

            /*Caso o botão de confirmação seja utilizado para salvar um novo gênero*/
        } else {
            int max = dao.MaxId();
            genre.setId(max);
            dao.save(genre);
            tableGenre.setItems(dao.readAll());
        }
        txtGenreName.clear();
    }
}
