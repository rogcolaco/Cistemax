package dao;

import model.Sale;
import util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ReportDAO implements DAO<Sale> {

    public ArrayList<Sale> readAll(String initialDate, String finalDate) {
        ArrayList<Sale> list = new ArrayList<>();
        Connection conn = ConnectionFactory.createConnection();
        try {
            String sql = "select * from sale where date >= ? AND date <= ?";
            PreparedStatement prep = conn.prepareStatement(sql);
            prep.setString(1, initialDate);
            prep.setString(2, finalDate);
            ResultSet res = prep.executeQuery();
            while (res.next()) {
                Sale sale = new Sale(res.getInt("id"),
                        res.getString("date"),
                        res.getDouble("price"),
                        res.getString("seats"),
                        res.getInt("qtd_seat_promotional"),
                        res.getDouble("total_sale"),
                        res.getInt("session")
                );
                list.add(sale);
            }
            conn.close();
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public void save(Sale f) throws SQLException {

    }

    @Override
    public void update(Sale f) throws SQLException {

    }

    @Override
    public void delete(Sale f) throws SQLException {

    }
}
