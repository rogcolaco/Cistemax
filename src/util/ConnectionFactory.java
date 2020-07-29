package util;

import java.sql.*;

public class ConnectionFactory implements AutoCloseable {

        private static Connection connection;
        private static PreparedStatement preparedStatement;
        private static Statement statement;

        public static Connection createConnection(){
            try {
                connection = DriverManager.getConnection("jdbc:sqlite:database.db");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return connection;
        }

        public static PreparedStatement createPreparedStatement(String sql){
            try {
                preparedStatement = connection.prepareStatement(sql);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return preparedStatement;
        }

        public static Statement createStatement(){
            try {
                statement = connection.createStatement();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return statement;
        }


        @Override
        public void close() {
            if (connection != null){
                try {
                    connection.close();
                    if (preparedStatement != null){
                        preparedStatement.close();
                    }
                    if (statement != null){
                        statement.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        }

}
