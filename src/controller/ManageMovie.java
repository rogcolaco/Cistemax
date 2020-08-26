package controller;

import dao.GenreDAO;
import dao.MovieDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Genre;
import model.Movie;
import model.ParentalControl;
import util.Regex;
import util.Utils;

import java.sql.SQLException;
import java.util.ArrayList;

import static util.Utils.mostrarAlerta;

public class ManageMovie extends MenuPrincipal {

    Regex regex = new Regex();
    @FXML
    private Button btnUpdateMovie;
    @FXML
    private Button btnConfirmMovie;

    @FXML
    private Label lbMovieFieldTitle;
    @FXML
    private ChoiceBox<String> cbParentalControl;

    @FXML
    private TableView<Movie> tableMovie;
    @FXML
    private TableColumn<String, String> cMovieName;
    @FXML
    private TableColumn<String, String> cDirMovie;
    @FXML
    private TableColumn<Genre, String> cGenre;
    @FXML
    private TableColumn<Boolean, Boolean> cCartaz;

    @FXML
    private ChoiceBox<Genre> cbGenre;
    @FXML
    private CheckBox checkCartaz;
    @FXML
    private TextField txtMovieName;
    @FXML
    private TextField txtDirName;
    @FXML
    private TextField txtDuration;

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
        cDirMovie.setCellValueFactory(new PropertyValueFactory<>("director"));
        cGenre.setCellValueFactory(new PropertyValueFactory<>("genre"));
        cCartaz.setCellValueFactory(new PropertyValueFactory<>("inTheaters"));
        tableMovie.setItems(daoMovie.readAll());
        cCartaz.setCellFactory(col -> new TableCell<Boolean, Boolean>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : item ? "Sim" : "Não");
                if (getItem() != null) {
                    if (getItem()) {
                        this.setStyle("-fx-background-color: #93bf85; -fx-font-weight: bold;");
                    } else {
                        this.setStyle("-fx-background-color: #ed5765; -fx-font-weight: bold;");
                    }
                }
            }
        });
    }


    public void updateMovie(ActionEvent actionEvent) {
        if (tableMovie.getSelectionModel().getSelectedItem() != null) {
            lbMovieFieldTitle.setText("Alterar Filme");
            btnConfirmMovie.setText("Alterar");
            Movie movie = tableMovie.getSelectionModel().getSelectedItem();
            txtMovieName.setText(movie.getName());
            txtDirName.setText(movie.getDirector());
            txtDuration.setText(String.valueOf(movie.getDuration()));
            cbGenre.getSelectionModel().select(movie.getGenre());
            cbParentalControl.setValue(movie.getParentalRating());
            checkCartaz.setSelected(movie.getInTheaters());
        }
    }

    public void addMovie(ActionEvent actionEvent) throws SQLException {
        Movie movie = new Movie();
        MovieDAO dao = new MovieDAO();
        movie.setName(txtMovieName.getText().trim());
        movie.setDirector(txtDirName.getText().trim());
        if (!txtDuration.getText().trim().equals("") && regex.isInt(txtDuration.getText().trim())) {
            movie.setDuration(Integer.parseInt(txtDuration.getText().trim()));
        } else {
            mostrarAlerta("Filmes", "Erro no Preenchimento de Dados", "Por Favor Preencha o Campo 'Duração' com um valor inteiro positivo", Alert.AlertType.ERROR);
            return;
        }
        movie.setGenre(cbGenre.getSelectionModel().getSelectedItem());
        movie.setInTheaters(checkCartaz.isSelected());
        movie.setParentalRating(cbParentalControl.getValue());

        if (fillError(movie)){
            /*Caso o botão de confirmação seja utilizado para alterar um filme*/
            if (btnConfirmMovie.getText().equals("Alterar")) {
                movie.setId(tableMovie.getSelectionModel().getSelectedItem().getId());
                dao.update(movie);
                tableMovie.setItems(dao.readAll());
                lbMovieFieldTitle.setText("Cadastrar Novo Gênero");
                btnConfirmMovie.setText("Confirmar");

                /*Caso o botão de confirmação seja utilizado para salvar um filme novo*/
            } else {
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
            tableMovie.refresh();
            tableMovie.setItems(dao.readAll());
        } else {
            return;
        }
    }

    public boolean fillError(Movie movie) {
        ArrayList<String> erros = new ArrayList<>();
        if (movie.getName().equals("")) {
            erros.add("Campo 'Nome do Filme' não pode ser vazio. \n");
        }
        if (movie.getDirector().equals("")) {
            erros.add("Campo 'Diretor' não pode ser vazio. \n");
        }
        if (movie.getGenre() == null) {
            erros.add("Campo 'Gênero' não pode ser vazio. \n");
        }
        if (movie.getParentalRating() == null) {
            erros.add("Campo 'Classificação Indicativa' não pode ser vazio. \n");
        }
        if (erros.isEmpty()) {
            return true;
        } else {
            mostrarAlerta("Sessão", "Erro ao cadastrar Sessão.", Utils.trataErros(erros), Alert.AlertType.ERROR);
            return false;
        }
    }
}
