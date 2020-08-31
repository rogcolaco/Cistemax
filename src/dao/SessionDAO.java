package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Movie;
import model.Session;
import util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class SessionDAO implements DAO<Session> {
    public Session readOne(int idSession) throws SQLException {
        Connection conn = ConnectionFactory.createConnection();
        try {
            String sql = "select * from session where id = ?";
            PreparedStatement prep = conn.prepareStatement(sql);
            prep.setInt(1, idSession);
            ResultSet res = prep.executeQuery();
            while (res.next()) {
                MovieDAO movieDAO = new MovieDAO();
                Movie movieName = MovieDAO.getById(res.getInt("movie"));
                Session session = new Session(res.getInt("id"),
                        res.getInt("theater"),
                        res.getString("starts_at"),
                        res.getString("ends_at"),
                        res.getString("date"),
                        res.getBoolean("promotional"),
                        movieName.getName(),
                        res.getString("seat_map"),
                        res.getInt("ticket"),
                        movieName);
                conn.close();
                return session;
            }

        } catch (SQLException e) {
            conn.close();
            e.printStackTrace();
        }
        return null;
    }


    public ObservableList<Session> readAll() throws SQLException {
        ObservableList<Session> list = FXCollections.observableArrayList();
        Connection conn = ConnectionFactory.createConnection();
        try {
            String sql = "select * from session";
            PreparedStatement prep = conn.prepareStatement(sql);
            ResultSet res = prep.executeQuery();
            while (res.next()) {
                MovieDAO movieDAO = new MovieDAO();
                Movie movieName = MovieDAO.getById(res.getInt("movie"));
                Session session = new Session(res.getInt("id"),
                        res.getInt("theater"),
                        res.getString("starts_at"),
                        res.getString("ends_at"),
                        res.getString("date"),
                        res.getBoolean("promotional"),
                        movieName.getName(),
                        res.getString("seat_map"),
                        res.getInt("ticket"),
                        movieName);
                list.add(session);
            }
            return list;
        } catch (SQLException e) {
            conn.close();
            e.printStackTrace();
        }
        return null;
    }
    public ObservableList<Session> readAll(int idTheater) throws SQLException {
        ObservableList<Session> list = FXCollections.observableArrayList();
        Connection conn = ConnectionFactory.createConnection();
        try {
            String sql = "select * from session where theater = ?";
            PreparedStatement prep = conn.prepareStatement(sql);
            prep.setInt(1, idTheater);
            ResultSet res = prep.executeQuery();
            while (res.next()) {
                MovieDAO movieDAO = new MovieDAO();
                Movie movieName = MovieDAO.getById(res.getInt("movie"));
                Session session = new Session(res.getInt("id"),
                        res.getInt("theater"),
                        res.getString("starts_at"),
                        res.getString("ends_at"),
                        res.getString("date"),
                        res.getBoolean("promotional"),
                        movieName.getName(),
                        res.getString("seat_map"),
                        res.getInt("ticket"),
                        movieName);
                list.add(session);
            }
            return list;
        } catch (SQLException e) {
            conn.close();
            e.printStackTrace();
        }
        return null;
    }

    public ObservableList<Session> readAll(String date) throws SQLException {
        ObservableList<Session> list = FXCollections.observableArrayList();
        Connection conn = ConnectionFactory.createConnection();
        try {
            String sql = "select * from session where date = ?";
            PreparedStatement prep = conn.prepareStatement(sql);
            prep.setString(1, date);
            ResultSet res = prep.executeQuery();
            while (res.next()) {
                MovieDAO movieDAO = new MovieDAO();
                Movie movieName = MovieDAO.getById(res.getInt("movie"));
                Session session = new Session(res.getInt("id"),
                        res.getInt("theater"),
                        res.getString("starts_at"),
                        res.getString("ends_at"),
                        res.getString("date"),
                        res.getBoolean("promotional"),
                        movieName.getName(),
                        res.getString("seat_map"),
                        res.getInt("ticket"),
                        movieName);
                list.add(session);
            }
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
            conn.rollback();
        } finally {
            conn.close();
        }

    }

    @Override
    public void update(Session f) throws SQLException {
        Connection conn = ConnectionFactory.createConnection();
        conn.setAutoCommit(false);
        try {
            String sql = "update session set seat_map = ? where id = ?";
            PreparedStatement prep = conn.prepareStatement(sql);
            prep.setString(1, f.getSeats());
            prep.setInt(2, f.getId());
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

    public ResultSet checkSessions(Session f) throws SQLException {
        Connection conn = ConnectionFactory.createConnection();
        conn.setAutoCommit(false);
        try {
            String sql = "SELECT id " +
                    "FROM session " +
                    "WHERE " +
                    "(date = ? AND theater = ? AND starts_at >= ? AND starts_at <= ?) " +
                    "OR (date = ? AND theater = ? AND ends_at >= ? AND ends_at <= ?) " +
                    "OR (date = ? AND theater = ? AND starts_at < ? AND ends_at > ?) " +
                    "LIMIT 1";
            PreparedStatement prep = conn.prepareStatement(sql);
            prep.setString(1, f.getDate());
            prep.setInt(2, f.getTheater());
            prep.setString(3, f.getStarts());
            prep.setString(4, f.getEnds());
            prep.setString(5, f.getDate());
            prep.setInt(6, f.getTheater());
            prep.setString(7, f.getStarts());
            prep.setString(8, f.getEnds());
            prep.setString(9, f.getDate());
            prep.setInt(10, f.getTheater());
            prep.setString(11, f.getStarts());
            prep.setString(12, f.getEnds());
            ResultSet res = prep.executeQuery();
            return res;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.close();
        }
        return null;
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
