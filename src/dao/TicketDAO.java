package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import model.Ticket;
import util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static util.Utils.mostrarAlerta;

public class TicketDAO implements  DAO<Ticket> {

    public double ticketPrice(int id) throws SQLException {
            Connection conn = ConnectionFactory.createConnection();
            try{
                String sql = "select id, price from ticket where id= ?";
                PreparedStatement prep = conn.prepareStatement(sql);
                prep.setInt(1, id);
                ResultSet res = prep.executeQuery();
                if (res != null){
                    Ticket price = new Ticket(res.getInt("id"), res.getDouble("price"));
                    conn.close();
                    return price.getValue();
                }
                return 0.0;
            } catch (SQLException e) {
                erro.erroBdAcess();
            }
            return 0.0;
        }

    public Ticket ticketById(int id) throws SQLException {
        Connection conn = ConnectionFactory.createConnection();
        try{
            String sql = "select * from ticket where id= ?";
            PreparedStatement prep = conn.prepareStatement(sql);
            prep.setInt(1, id);
            ResultSet res = prep.executeQuery();
            if (res != null){
                Ticket ticket = new Ticket(res.getInt("id"), res.getDouble("price"), res.getString("type"));
                conn.close();
                return ticket;
            }
        } catch (SQLException e) {
            conn.close();
            erro.erroBdAcess();
        }
        return null;
    }

    public ObservableList<Ticket> readAll() {
        ObservableList<Ticket> list = FXCollections.observableArrayList();
        Connection conn = ConnectionFactory.createConnection();
        try {
            String sql = "select * from ticket";
            PreparedStatement prep = conn.prepareStatement(sql);
            ResultSet res = prep.executeQuery();
            while (res.next()) {
                Ticket ticket = new Ticket(res.getInt("id"), res.getDouble("price"), res.getString("type"));
                list.add(ticket);
            }
            return list;
        } catch (SQLException e) {
            erro.erroBdAcess();
        }
        return null;
    }

    @Override
    public void save(Ticket f) throws SQLException {
        Connection conn = ConnectionFactory.createConnection();
        conn.setAutoCommit(false);
        try {
            String sql = "insert into ticket (id, type, price) values (?, ?, ?)";
            PreparedStatement prep = conn.prepareStatement(sql);
            prep.setInt(1, f.getId());
            prep.setString(2, f.getType());
            prep.setDouble(3, f.getValue());
            prep.execute();
            conn.commit();
        } catch (SQLException e) {
            erro.erroBdAcess();;
        } finally {
            conn.close();
        }
    }


    @Override
    public void update(Ticket f) throws SQLException {
        Connection conn = ConnectionFactory.createConnection();
        conn.setAutoCommit(false);
        try{
            String sql = "update ticket set type = ?, price = ? where id = ?";
            PreparedStatement prep = conn.prepareStatement(sql);
            prep.setString(1, f.getType());
            prep.setDouble(2, f.getValue());
            prep.setInt(3, f.getId());
            prep.execute();
            conn.commit();
        } catch (Exception e) {
            erro.erroBdAcess();
        } finally {
            conn.close();
        }
    }

    @Override
    public void delete(Ticket f) throws SQLException {
        Connection conn = ConnectionFactory.createConnection();
        conn.setAutoCommit(false);
        try {
            String sql = "delete from ticket where id = ?";
            PreparedStatement prep = conn.prepareStatement(sql);
            prep.setInt(1, f.getId());
            prep.execute();
            conn.commit();
        } catch (Exception e) {
            mostrarAlerta("Preço","Erro ao excluir tipo de preço","Existe pelo menos 1 sessão cadastrada com esse tipo de sessão.", Alert.AlertType.ERROR);
        } finally {
            conn.close();
        }
    }

    public int maxId() throws SQLException {
        Connection conn = ConnectionFactory.createConnection();
        try {
            String sql = "select max(id) as id from ticket";
            PreparedStatement prep = conn.prepareStatement(sql);
            ResultSet res = prep.executeQuery();

            int max = res.getInt("id") + 1;

            return max;
        } catch (SQLException e) {
            erro.erroBdAcess();
        } finally {
            conn.close();
        }
        return 0;
    }

}
