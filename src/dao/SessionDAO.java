package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Session;
import util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;


public class SessionDAO implements DAO <Session>{

    public ObservableList<Session> readAll(int idTheater) throws SQLException {
        ObservableList<Session> list = FXCollections.observableArrayList();
        Connection conn = ConnectionFactory.createConnection();
        try{
            String sql = "select * from session where theater = ? AND date >= ?";
            PreparedStatement prep = conn.prepareStatement(sql);
            prep.setInt(1, idTheater);
            prep.setString(2,java.time.LocalDate.now().toString());
            ResultSet res = prep.executeQuery();
            while (res.next()) {
                Session session = new Session(res.getInt("id"),
                        res.getInt("theater"),
                        res.getString("starts_at"),
                        res.getString("ends_at"),
                        res.getBoolean("promotional"));
                list.add(session);
            };
            return list;
        } catch (SQLException e) {
            conn.close();
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void save(Session f) throws SQLException {
        Connection conn = ConnectionFactory.createConnection();
        conn.setAutoCommit(false);
        try {
            int id = this.MaxId();
            String sql = "insert into session (id, date, starts_at, ends_at, seat_map, movie, theater, ticket, promotional) " +
                    "values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement prep = conn.prepareStatement(sql);
            prep.setInt(1, id);
            prep.setString(2, f.getDate());
            prep.setString(3, f.getStarts());
            prep.setString(4, f.getEnds());
            prep.setString(5, f.getSeats());
            prep.setInt(6, f.getMovie());
            prep.setInt(7, f.getTheater());
            prep.setInt(8, f.getTicket());
            prep.setBoolean(9, f.isPromotional());
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
    public void update(Session f) throws SQLException {
    }

    @Override
    public void delete(Session f) throws SQLException {
        Connection conn = ConnectionFactory.createConnection();
        conn.setAutoCommit(false);
        try {
            String sql = "delete from session where id = ?";
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
            String sql = "select max(id) as id from session";
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