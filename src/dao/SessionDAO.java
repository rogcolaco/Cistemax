package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Genre;
import model.Movie;
import model.Session;
import util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SessionDAO implements DAO <Session>{

        public ObservableList<Session> readAll() throws SQLException {
            ObservableList<Session> list = FXCollections.observableArrayList();
            Connection conn = ConnectionFactory.createConnection();
            try{
                String sql = "select * from session";
                PreparedStatement prep = conn.prepareStatement(sql);
                ResultSet res = prep.executeQuery();
                return list;
            } catch (SQLException e) {
                conn.close();
                e.printStackTrace();
            }
            return null;
        }

    @Override
    public void update(Session f) throws SQLException {

    }

    @Override
    public void delete(Session f) throws SQLException {

    }

//        public Movie getById(int id) throws SQLException {
//            Connection conn = ConnectionFactory.createConnection();
//            try{
//                String sql = "select * from movie where id = ?";
//                PreparedStatement prep = conn.prepareStatement(sql);
//                prep.setInt(1,id);
//                ResultSet res = prep.executeQuery();
//                if (res != null){
//                    GenreDAO genreDao = new GenreDAO();
//                    Genre genre = genreDao.getById(res.getInt("genre"));
//                    Movie movie = new Movie(res.getInt("id"),
//                            res.getInt("duration"),
//                            res.getString("name"),
//                            res.getString("director"),
//                            res.getString("parentalRating"),
//                            res.getBoolean("inTheaters"),
//                            genre
//                    );
//                    conn.close();
//                    return  movie;
//                }
//                conn.close();
//                return null;
//            } catch (SQLException e) {
//                conn.close();
//                e.printStackTrace();
//            }
//            return null;
//        }
//
        @Override
        public void save(Session f) throws SQLException {
            Connection conn = ConnectionFactory.createConnection();
            conn.setAutoCommit(false);
            try {
                int id = this.MaxId();
                String sql = "insert into session (id, starts_at, ends_at, seat_map, movie, theater, promotional) " +
                        "values (?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement prep = conn.prepareStatement(sql);
                prep.setInt(1, id);
                prep.setString(2, f.getStarts());
                prep.setString(3, f.getEnds());
                prep.setString(4, f.getSeats());
                prep.setInt(5, f.getMovie());
                prep.setInt(6, f.getTheater());
                prep.setBoolean(7, f.isPromotional());
                prep.execute();
                prep.close();
                conn.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                conn.close();
            }

        }
//
//        @Override
//        public void update(Session f) throws SQLException {
//            Connection conn = ConnectionFactory.createConnection();
//            conn.setAutoCommit(false);
//            try{
//                String sql = "update session set starts_at = ?, ends_at = ?, seat_map = ?, movie = ?, " +
//                        "theater = ?, promotional = ? where id = ?";
//                PreparedStatement prep = conn.prepareStatement(sql);
//                prep.setString(1, f.getStart());
//                prep.setString(2, f.getEnd());
//                prep.setString(3, f.getSeats());
//                prep.setInt(4, f.getMovie().getId());
//                prep.setInt(5, f.getTheater().getId);
//                prep.setBoolean(6, f.getPromotional());
//                prep.setInt(7, f.getId());
//                prep.execute();
//                conn.commit();
//            } catch (Exception e) {
//                e.printStackTrace();
//            } finally {
//                conn.close();
//            }
//        }
//
//        @Override
//        public void delete(Session f) throws SQLException {
//            Connection conn = ConnectionFactory.createConnection();
//            conn.setAutoCommit(false);
//            try {
//                String sql = "delete from movie where id = ?";
//                PreparedStatement prep = conn.prepareStatement(sql);
//                prep.setInt(1, f.getId());
//                prep.execute();
//                conn.commit();
//            } catch (Exception e) {
//                e.printStackTrace();
//            } finally {
//                conn.close();
//            }
//        }
//
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
