package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Theater;
import model.Ticket;
import util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TheaterDAO implements DAO<Theater>{

    public ObservableList<Theater> readAll() {
        ObservableList<Theater> list = FXCollections.observableArrayList();
        Connection conn = ConnectionFactory.createConnection();
        try {
            String sql = "select * from theater";
            PreparedStatement prep = conn.prepareStatement(sql);
            ResultSet res = prep.executeQuery();
            while (res.next()) {
                Theater theater = new Theater(res.getInt("id"), res.getString("name"), res.getInt("qtdSeats"));
                list.add(theater);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public void save(Theater f) throws SQLException {
        Connection conn = ConnectionFactory.createConnection();
        conn.setAutoCommit(false);
        try {
            String sql = "insert into theater (id, name, qtdSeats) values (?, ?, ?)";
            PreparedStatement prep = conn.prepareStatement(sql);
            prep.setInt(1, f.getId());
            prep.setString(2, f.getName());
            prep.setDouble(3, f.getQtdSeats());
            prep.execute();
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.close();
        }
    }

    @Override
    public void update(Theater f) throws SQLException {
        Connection conn = ConnectionFactory.createConnection();
        conn.setAutoCommit(false);
        try{
            String sql = "update theater set name = ?, qtdSeats = ? where id = ?";
            PreparedStatement prep = conn.prepareStatement(sql);
            prep.setString(1, f.getName());
            prep.setDouble(2, f.getQtdSeats());
            prep.setInt(3, f.getId());
            prep.execute();
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.close();
        }
    }

    @Override
    public void delete(Theater f) throws SQLException {
        Connection conn = ConnectionFactory.createConnection();
        conn.setAutoCommit(false);
        try {
            String sql = "delete from theater where id = ?";
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
            String sql = "select max(id) as id from theater";
            PreparedStatement prep = conn.prepareStatement(sql);
            ResultSet res = prep.executeQuery();

            int max = res.getInt("id") + 1;

            return max;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.close();
        }
        return 0;
    }
}
