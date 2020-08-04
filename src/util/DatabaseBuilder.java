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

                    "CREATE TABLE movie (\n" +
                    "\tid INTEGER NOT NULL PRIMARY KEY,\n" +
                    "\tname TEXT,\n" +
                    "\tdirector TEXT,\n" +
                    "\tparentalRating TEXT,\n" +
                    "\tduration INTEGER,\n" +
                    "\tgenre INTEGER,\n" +
                    "\tinTheaters BOOLEAN,\n" +
                    "\tFOREIGN KEY('genre') REFERENCES 'genre'('id')\n);\n" +

                    "CREATE TABLE ticket (\n " +
                    "\tid integer not null primary key, \n" +
                    "\ttype TEXT not null, \n" +
                    "\tprice number not null \n);\n" +

                    "CREATE TABLE theater (\n" +
                    "\tid integer not null primary key, \n" +
                    "\tname TEXT not null, \n" +
                    "\tqtdSeats number not null \n);\n");

            stmt.executeUpdate(sql.toString());

            System.out.println("Database has been created ...");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
