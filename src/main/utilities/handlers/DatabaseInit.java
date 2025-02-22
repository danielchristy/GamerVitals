package main.utilities.handlers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseInit {
    public static final String URL = "jdbc:postgresql://localhost:5432/GamerVitals";
    public static final String USER = "postgres";
    public static final String PASSWORD = "daCjava25!";

    public static Connection run() {
        Connection connection = null;
        System.out.println("***Connecting to database..***");

        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("**Successfully connected to database.**");

            System.out.println("***Checking database for schema and tables..***");
            SchemaManager.getOrCreateSchema(connection);
            SchemaManager.getOrCreateTables(connection);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }
}