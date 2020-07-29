package dao;

import model.Genre;
import util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GenreDAO implements DAO <Genre>{

    public List<Genre> readAll(){
        List<Genre> list = null;
        Connection conn = ConnectionFactory.createConnection();
        try{
            String sql = "select * from genre";
            PreparedStatement prep = conn.prepareStatement(sql);
            ResultSet res = prep.executeQuery();
            while (res.next()){
                Genre genre = new Genre(res.getInt("id"), res.getString("name"));
                list.add(genre);
            }
            return  list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void save(Genre f) throws SQLException {
        Connection conn = ConnectionFactory.createConnection();
        conn.setAutoCommit(false);
        try {
            int id = this.MaxId();
            String sql = "insert into genre (id, name) values (?, ?)";
            PreparedStatement prep = conn.prepareStatement(sql);
            prep.setInt(1, id);
            prep.setString(2, f.getName());
            prep.execute();
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.close();
        }

    }

    @Override
    public void update(Genre f) throws SQLException {
        Connection conn = ConnectionFactory.createConnection();
        conn.setAutoCommit(false);
        try{
            String sql = "update gente set name = ? where id = ?";
            PreparedStatement prep = conn.prepareStatement(sql);
            prep.setString(1, f.getName());
            prep.setInt(2, f.getId());
            prep.execute();
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.close();
        }
    }

    @Override
    public void delete(Genre f) throws SQLException {
        Connection conn = ConnectionFactory.createConnection();
        conn.setAutoCommit(false);
        try {
            String sql = "delete from genre where id = ?";
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
            String sql = "select max(id) from genre";
            PreparedStatement prep = conn.prepareStatement(sql);
            ResultSet res = prep.executeQuery();
            int max = res.getInt("id");
            return max;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.close();
        }
        return 0;
    }

}
