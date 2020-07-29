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

    private static void clear(){
        System.out.println("Cleaning up...");
        try {
            Files.deleteIfExists(Paths.get("database.db"));}
        catch (IOException e) {e.printStackTrace();}
    }

    private static void build(){
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:database.db");
             Statement stmt = conn.createStatement()) {
            StringBuilder sql = new StringBuilder();
            sql.append("CREATE TABLE genre (\n" +
                    "\tid INTEGER NOT NULL PRIMARY KEY,\n" +
                    "\tname TEXT\n);\n" +

                    "CREATE TABLE movie" +
                    "id INTEGER NOT NULL PRIMARY KEY,\n" +
                    "\tname TEXT,\n" +
                    "\tdirector TEXT,\n" +
                    "\tinTheaters BOOLEAN,\n" +
                    "\tduration INTEGER,\n" +
                    "\tgenre INTEGER,\n" +
                    "\tgenre INTEGER,\n" +
                    "\tFOREIGN KEY('genre') REFERENCES 'genre'('id')\n);");
            stmt.execute(sql.toString());

            System.out.println("Database has been created ...");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
