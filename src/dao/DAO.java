package dao;

import util.ErroDbAcess;

import java.sql.SQLException;

public interface DAO<T> {

    ErroDbAcess erro = new ErroDbAcess();

    void save (T f) throws SQLException;
    void update (T f) throws SQLException;
    void delete (T f) throws SQLException;

}
