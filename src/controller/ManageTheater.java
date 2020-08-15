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
import util.Regex;
import util.SwitcherDisplay;

import java.io.IOException;
import java.sql.SQLException;

import static util.Utils.mostrarAlerta;

public class ManageTheater extends MenuPrincipal{

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

        try {
            theater.setName(txtTheaterName.getText());
            theater.setQtdSeats(Integer.parseInt(txtQtdSeats.getText()));

            if(!theater.getName().equals("")) {

                /*Caso o botão de confirmação seja utilizado para alterar um ticket*/
                if (btnConfirmTheater.getText().equals("Alterar")) {
                    theater.setId(tableTheater.getSelectionModel().getSelectedItem().getId());
                    dao.update(theater);
                    tableTheater.setItems(dao.readAll());
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
            } else{
                    mostrarAlerta("Sala", "Nome da Sala", "Por Favor Preencha o Campo 'Nome da Sala' ", Alert.AlertType.ERROR);
            }
        } catch (NumberFormatException n){
            mostrarAlerta("Sala", "Nome da Sala", "Por Favor Preencha o Campo 'Qtd Assentos' apenas com números", Alert.AlertType.ERROR);
        }
    }

    public void remove(ActionEvent actionEvent) throws SQLException {
        TheaterDAO dao = new TheaterDAO();
        dao.delete(tableTheater.getSelectionModel().getSelectedItem());
        tableTheater.setItems(dao.readAll());
    }
}
