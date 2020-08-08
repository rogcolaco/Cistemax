package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Genre;
import model.Movie;
import util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MovieDAO implements DAO <Movie>{

    public ObservableList<Movie> readAll() throws SQLException {
        return readAll(false);
    }

    public ObservableList<Movie> readAll(Boolean inTheaters) throws SQLException {
        ObservableList<Movie> list = FXCollections.observableArrayList();
        Connection conn = ConnectionFactory.createConnection();
        try{
            String sql = inTheaters ? "select * from movie where inTheaters = true" : "select * from movie ";
            PreparedStatement prep = conn.prepareStatement(sql);
            ResultSet res = prep.executeQuery();
            while (res.next()){
                GenreDAO genreDao = new GenreDAO();
                Genre genre = genreDao.getById(res.getInt("genre"));
                Movie movie = new Movie(res.getInt("id"),
                        res.getInt("duration"),
                        res.getString("name"),
                        res.getString("director"),
                        res.getString("parentalRating"),
                        res.getBoolean("inTheaters"),
                        genre
                );
                list.add(movie);
            }
            return  list;
        } catch (SQLException e) {
            conn.close();
            e.printStackTrace();
        }
        return null;
    }

    public static Movie getById(int id) throws SQLException {
        Connection conn = ConnectionFactory.createConnection();
        try{
            String sql = "select * from movie where id = ?";
            PreparedStatement prep = conn.prepareStatement(sql);
            prep.setInt(1,id);
            ResultSet res = prep.executeQuery();
            if (res != null){
                GenreDAO genreDao = new GenreDAO();
                Genre genre = genreDao.getById(res.getInt("genre"));
                Movie movie = new Movie(res.getInt("id"),
                        res.getInt("duration"),
                        res.getString("name"),
                        res.getString("director"),
                        res.getString("parentalRating"),
                        res.getBoolean("inTheaters"),
                        genre
                );
                conn.close();
                return  movie;
            }
            conn.close();
            return null;
        } catch (SQLException e) {
            conn.close();
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void save(Movie f) throws SQLException {
        Connection conn = ConnectionFactory.createConnection();
        conn.setAutoCommit(false);
        try {
            int id = this.MaxId();
            String sql = "insert into movie (id, duration, name, director, parentalRating, genre, inTheaters) " +
                    "values (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement prep = conn.prepareStatement(sql);
            prep.setInt(1, id);
            prep.setInt(2, f.getDuration());
            prep.setString(3, f.getName());
            prep.setString(4, f.getDirector());
            prep.setString(5, f.getParentalRating());
            prep.setInt(6, f.getGenre().getId());
            prep.setBoolean(7, f.getInTheaters());
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
    public void update(Movie f) throws SQLException {
        Connection conn = ConnectionFactory.createConnection();
        conn.setAutoCommit(false);
        try{
            String sql = "update movie set duration = ?, name = ?, director = ?, parentalRating = ?, " +
                    "inTheaters = ?, genre = ? where id = ?";
            PreparedStatement prep = conn.prepareStatement(sql);
            prep.setInt(1, f.getDuration());
            prep.setString(2, f.getName());
            prep.setString(3, f.getDirector());
            prep.setString(4, f.getParentalRating());
            prep.setBoolean(5, f.getInTheaters());
            prep.setInt(6, f.getGenre().getId());
            prep.setInt(7, f.getId());
            prep.execute();
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.close();
        }
    }

    @Override
    public void delete(Movie f) throws SQLException {
        Connection conn = ConnectionFactory.createConnection();
        conn.setAutoCommit(false);
        try {
            String sql = "delete from movie where id = ?";
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
            String sql = "select max(id) as id from movie";
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
