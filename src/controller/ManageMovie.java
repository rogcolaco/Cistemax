package controller;

import com.oracle.webservices.internal.api.message.PropertySet;
import com.sun.org.apache.bcel.internal.generic.INSTANCEOF;
import dao.GenreDAO;
import dao.MovieDAO;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Genre;
import model.Movie;
import model.ParentalControl;
import model.Theater;
import util.Regex;
import util.SwitcherDisplay;

import java.io.IOException;
import java.sql.SQLException;

import static util.Utils.mostrarAlerta;

public class ManageMovie extends MenuPrincipal{

    @FXML private Button btnUpdateMovie;
    @FXML private Button btnConfirmMovie;
    //@FXML private Button btnCancelOp;
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

    Regex regex = new Regex();

    @FXML
    public void initialize() throws SQLException {
        /*Preenche o Choice Box da Classificação indicativa*/
        ParentalControl pc = new ParentalControl();
        cbParentalControl.setItems(pc.loadParentalControl());
        Fill();
    }

    public void Fill() throws SQLException {
        GenreDAO daoGenre = new GenreDAO();
        cbGenre.setItems(daoGenre.readAll());
        MovieDAO daoMovie = new MovieDAO();
        cMovieName.setCellValueFactory(new PropertyValueFactory<>("name"));
        cDirMovie.setCellValueFactory(new  PropertyValueFactory<>("director"));
        cGenre.setCellValueFactory(new PropertyValueFactory<>("genre"));
        cCartaz.setCellValueFactory(new PropertyValueFactory<>("inTheaters"));
        tableMovie.setItems(daoMovie.readAll());
    }


    public void updateMovie(ActionEvent actionEvent) {
        if(tableMovie.getSelectionModel().getSelectedItem() != null) {
            lbMovieFieldTitle.setText("Alterar Filme");
            btnConfirmMovie.setText("Alterar");
            Movie movie = tableMovie.getSelectionModel().getSelectedItem();
            txtMovieName.setText(movie.getName());
            txtDirName.setText(movie.getDirector());
            txtDuration.setText(String.valueOf(movie.getDuration()));
            cbGenre.setValue(movie.getGenre());
            cbParentalControl.setValue(movie.getParentalRating());
            checkCartaz.setSelected(movie.getInTheaters());
        }
    }

    public void addMovie(ActionEvent actionEvent) throws SQLException {
        Movie movie = new Movie();
        MovieDAO dao = new MovieDAO();
        movie.setName(txtMovieName.getText());
        movie.setDirector(txtDirName.getText());
        if(!txtDuration.getText().equals("") && regex.isInt(txtDuration.getText())){
            movie.setDuration(Integer.parseInt(txtDuration.getText()));
        } else{
            mostrarAlerta("Filmes", "Erro no Preenchimento de Dados", "Por Favor Preencha o Campo 'Duração' com um valor inteiro positivo", Alert.AlertType.ERROR);
            return;
        }
        movie.setGenre(cbGenre.getSelectionModel().getSelectedItem());
        movie.setInTheaters(checkCartaz.isSelected());
        movie.setParentalRating(cbParentalControl.getValue());
        boolean b = fillError(movie);

        if (!b) {
            /*Caso o botão de confirmação seja utilizado para alterar um filme*/
            if (btnConfirmMovie.getText().equals("Alterar")) {
                movie.setId(tableMovie.getSelectionModel().getSelectedItem().getId());
                dao.update(movie);
                tableMovie.setItems(dao.readAll());
                lbMovieFieldTitle.setText("Cadastrar Novo Gênero");
                btnConfirmMovie.setText("Confirmar");

                /*Caso o botão de confirmação seja utilizado para salvar um filme novo*/
            } else {
                //int max = dao.MaxId();
                //movie.setId(max);
                dao.save(movie);
                tableMovie.setItems(dao.readAll());
            }
            txtMovieName.clear();
            txtDirName.clear();
            txtDuration.clear();
            cbGenre.getSelectionModel().clearSelection();
            cbParentalControl.getSelectionModel().clearSelection();
            checkCartaz.setSelected(false);
            tableMovie.setItems(dao.readAll());
        }
        return;
    }

    public void deleteMovie(ActionEvent actionEvent) throws SQLException {
        MovieDAO dao = new MovieDAO();
        if (tableMovie.getSelectionModel().getSelectedItem() != null) {
            dao.delete(tableMovie.getSelectionModel().getSelectedItem());
            tableMovie.setItems(dao.readAll());
        } else { return;}
    }

    public boolean fillError(Movie movie){
        if (movie.getName().equals("")) {
            mostrarAlerta("Filmes", "Erro no Preenchimento de Dados", "Por Favor Preencha o Campo 'Nome do Filme'", Alert.AlertType.ERROR);
            return true;
        }
        if (movie.getDirector().equals("")) {
            mostrarAlerta("Filmes", "Erro no Preenchimento de Dados", "Por Favor Preencha o Campo 'Diretor'", Alert.AlertType.ERROR);
            return true;
        }
        if (movie.getGenre() == null) {
            mostrarAlerta("Filmes", "Erro no Preenchimento de Dados", "Por Favor Preencha o Campo 'Gênero'", Alert.AlertType.ERROR);
            return true;
        }
        if (movie.getParentalRating() == null) {
            mostrarAlerta("Filmes", "Erro no Preenchimento de Dados", "Por Favor Preencha o Campo 'Classificação Indicativa'", Alert.AlertType.ERROR);
            return true;
        }
        return false;
    }
}
