package util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseBuilder {

    public static void main(String[] args) {
        clear();
        build();
    }

    private static void clear() {
        System.out.println("Cleaning up...");
        try {
            Files.deleteIfExists(Paths.get("database.db"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void build() {
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:database.db");
             Statement stmt = conn.createStatement()) {
            StringBuilder sql = new StringBuilder();

            sql.append("CREATE TABLE genre (" +
                    " id INTEGER NOT NULL PRIMARY KEY," +
                    " name TEXT);" +

                    "CREATE TABLE movie (" +
                    " id INTEGER NOT NULL PRIMARY KEY," +
                    " name TEXT," +
                    " director TEXT," +
                    " parentalRating TEXT," +
                    " duration INTEGER," +
                    " genre INTEGER," +
                    " inTheaters BOOLEAN," +
                    " FOREIGN KEY('genre') REFERENCES 'genre'('id')" +
                    " ON DELETE RESTRICT);" +

                    "CREATE TABLE ticket ( " +
                    " id integer not null primary key, " +
                    " type TEXT not null, " +
                    " price number(8,2) not null );" +

                    "CREATE TABLE theater (" +
                    " id integer not null primary key, " +
                    " name TEXT not null, " +
                    " qtdSeats number not null );" +

                    "CREATE TABLE session (" +
                    " id INTEGER NOT NULL PRIMARY KEY," +
                    " date TEXT, " +
                    " starts_at TEXT," +
                    " ends_at TEXT," +
                    " seat_map TEXT," +
                    " movie INTEGER," +
                    " theater INTEGER," +
                    " ticket INTEGER," +
                    " promotional BOOLEAN," +
                    " FOREIGN KEY('movie') REFERENCES 'movie'('id') ON DELETE RESTRICT," +
                    " FOREIGN KEY('ticket') REFERENCES 'ticket'('id') ON DELETE RESTRICT," +
                    " FOREIGN KEY('theater') REFERENCES 'theater'('id') ON DELETE RESTRICT);" +

                    "CREATE TABLE sale (" +
                    " id INTEGER NOT NULL PRIMARY KEY, " +
                    " date TEXT, " +
                    " price DOUBLE, " +
                    " seats TEXT, " +
                    " qtd_seat_promotional INT, " +
                    " total_sale DOUBLE, " +
                    " session INT, " +
                    " FOREIGN KEY('session') REFERENCES 'session'('id') ON DELETE RESTRICT); "
            );

            stmt.executeUpdate(sql.toString());

            System.out.println("Database has been created ...");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
