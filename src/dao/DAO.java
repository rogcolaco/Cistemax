package dao;

import util.ErroDbAccess;

import java.sql.SQLException;

public interface DAO<T> {

    ErroDbAccess erro = new ErroDbAccess();

    void save (T f) throws SQLException;
    void update (T f) throws SQLException;
    void delete (T f) throws SQLException;

}
