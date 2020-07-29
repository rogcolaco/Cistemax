package dao;

import java.sql.SQLException;

public interface DAO<T> {

    void save (T f) throws SQLException;
    void update (T f) throws SQLException;
    void delete (T f) throws SQLException;

}
