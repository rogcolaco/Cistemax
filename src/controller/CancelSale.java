package controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dao.SaleDAO;
import dao.SessionDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Sale;
import model.Session;
import util.SwitcherDisplay;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import controller.NewSaleController.*;

public class CancelSale extends MenuPrincipal{

    //Toolbar buttons
    @FXML private Button btnSale;
    @FXML private Button btnMovie;
    @FXML private Button btnTheater;
    @FXML private Button btnGenre;
    @FXML private Button btnPrice;
    @FXML private Button btnMovieSession;
    @FXML private Button btnCancelSale;
    @FXML private Button btnReport;

    @FXML Button btnConfirmCancelSale;
    @FXML TextField txtIdCancelSale;

    public String updatedSeats(int id, List<String> selectedSeats) throws SQLException {
        Gson gson = new Gson();
        SessionDAO daoSession = new SessionDAO();
        Session s = daoSession.readOne(id);
        HashMap<Integer, Boolean> seatMap = new Gson().fromJson(s.getSeats(), new TypeToken<HashMap<Integer, Boolean>>() {}.getType());
        for (String seat : selectedSeats) {
            seatMap.put(Integer.parseInt(seat.replace(" ","")), false);
        }
        return gson.toJson(seatMap);
    }

    public void deleteSale(ActionEvent actionEvent) {
        try{
            SaleDAO saleDAO = new SaleDAO();
            SessionDAO sessionDAO = new SessionDAO();
            String id = txtIdCancelSale.getText();
            Sale sale = saleDAO.getById(Integer.parseInt(id));
            Session session = sessionDAO.readOne(sale.getSessionId());
            List<String> deletedSeats = Arrays.asList(sale.getSeats().split(","));
            String updatedSeats = updatedSeats(session.getId(), deletedSeats);

            session.setSeats(updatedSeats);
            saleDAO.delete(sale);
            sessionDAO.update(session);

            ReportSuccess msg = new ReportSuccess();
            msg.show();

            txtIdCancelSale.clear();

        } catch (Exception e) {
            System.out.println(e);
            MsgErro msg = new MsgErro();
            msg.show();
        }

    }

}
