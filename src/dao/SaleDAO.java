package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Genre;
import model.Movie;
import model.Sale;
import model.Session;
import util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class SaleDAO implements DAO <Sale>{
    public Sale getById(int id) throws SQLException {
        Sale sale = null;
        Connection conn = ConnectionFactory.createConnection();
        try{
            String sql = "select id, seats, session from sale where id= ?";
            PreparedStatement prep = conn.prepareStatement(sql);
            prep.setInt(1, id);
            ResultSet res = prep.executeQuery();
            if (res != null){
                sale = new Sale(res.getInt("id"), res.getString("seats"), res.getInt("session"));
                conn.close();
                return sale;
            }
            return null;
        } catch (SQLException e) {
            conn.close();
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void save(Sale f) throws SQLException {
        Connection conn = ConnectionFactory.createConnection();
        conn.setAutoCommit(false);
        try {
            int id = this.MaxId() + 1;
            String sql = "insert into sale (id, date, price, seats, qtd_seat_promotional, total_sale, session) " +
                    "values (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement prep = conn.prepareStatement(sql);
            prep.setInt(1, id);
            prep.setString(2, f.getDate());
            prep.setDouble(3, f.getPrice());
            prep.setString(4, f.getSeats());
            prep.setInt(5, f.getQtdSeatPromotional());
            prep.setDouble(6, f.getTotalSale());
            prep.setInt(7, f.getSession().getId());
            prep.execute();
            prep.close();
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.close();
        }

    }

    @Override
    public void update(Sale f) throws SQLException {
    }

    @Override
    public void delete(Sale f) throws SQLException {
        Connection conn = ConnectionFactory.createConnection();
        conn.setAutoCommit(false);
        try {
            String sql = "delete from sale where id = ?";
            PreparedStatement prep = conn.prepareStatement(sql);
            prep.setInt(1, f.getId());
            prep.execute();
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.close();
        }
    }

    public int MaxId() throws SQLException {
        Connection conn = ConnectionFactory.createConnection();
        try {
            String sql = "select max(id) as id from sale";
            PreparedStatement prep = conn.prepareStatement(sql);
            ResultSet res = prep.executeQuery();
            int max = res.getInt("id");
            conn.close();

            return max;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.close();
        }
        return 0;
    }

}
