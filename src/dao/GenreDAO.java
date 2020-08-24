package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import model.Genre;
import org.apache.commons.lang3.StringUtils;
import util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static util.Utils.mostrarAlerta;

public class GenreDAO implements DAO <Genre>{

    public ObservableList<Genre> readAll(){
        ObservableList<Genre> list = FXCollections.observableArrayList();
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
            erro.erroBdAcess();
        }
        return null;
    }

    public Genre getById(int id) throws SQLException {
        Genre genre = null;
        Connection conn = ConnectionFactory.createConnection();
        try{
            String sql = "select * from genre where id= ?";
            PreparedStatement prep = conn.prepareStatement(sql);
            prep.setInt(1, id);
            ResultSet res = prep.executeQuery();
            if (res != null){
                genre = new Genre(res.getInt("id"), res.getString("name"));
                conn.close();
                return  genre;
            }
            return null;
        } catch (SQLException e) {
            conn.close();
            erro.erroBdAcess();;
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
            erro.erroBdAcess();
        } finally {
            conn.close();
        }
    }

    @Override
    public void update(Genre f) throws SQLException {
        Connection conn = ConnectionFactory.createConnection();
        conn.setAutoCommit(false);
        try{
            String sql = "update genre set name = ? where id = ?";
            PreparedStatement prep = conn.prepareStatement(sql);
            prep.setString(1, f.getName());
            prep.setInt(2, f.getId());
            prep.execute();
            conn.commit();
        } catch (Exception e) {
            erro.erroBdAcess();
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
            mostrarAlerta("Gênero","Erro ao deletar Gênero","Existe pelo menos um filme utilizando o gênero selecionado.", Alert.AlertType.ERROR);
        } finally {
            conn.close();
        }
    }

    public int MaxId() throws SQLException {
        Connection conn = ConnectionFactory.createConnection();
        try {
            String sql = "select max(id) as id from genre";
            PreparedStatement prep = conn.prepareStatement(sql);
            ResultSet res = prep.executeQuery();
            int max = res.getInt("id") + 1;
            return max;
        } catch (SQLException e) {
            erro.erroBdAcess();;
        } finally {
            conn.close();
        }
        return 0;
    }

    public Boolean UniqueGenre(String name) throws SQLException {
        Connection conn = ConnectionFactory.createConnection();
        try {
            String sql = "select name from genre ";
            PreparedStatement prep = conn.prepareStatement(sql);
            ResultSet res = prep.executeQuery();
            while(res.next()) {
                if (StringUtils.getLevenshteinDistance(StringUtils.stripAccents(res.getString("name").toLowerCase()), StringUtils.stripAccents(name).toLowerCase()) < 2) {
                    return false;
                }
            }
        } catch (SQLException e) {
            erro.erroBdAcess();;
        } finally {
            conn.close();
        }
        return true;
    }

}
