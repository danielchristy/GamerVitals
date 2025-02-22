package main.utilities.handlers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

public class SchemaManager {
    static final String schema_name = "vitals";
    static final String[] tables = {"users", "games", "user_stats"};
    static final String schema_files_directory = "src/main/sql/tables";

    public static void getOrCreateSchema(Connection connection) {
        // get schema
        String sqlSchema = "SELECT EXISTS (SELECT FROM pg_namespace " +
                "WHERE nspname = ?);";

        try (PreparedStatement schema_stmt = connection.prepareStatement(sqlSchema)) {
            schema_stmt.setString(1, schema_name);

            try (ResultSet result = schema_stmt.executeQuery()) {
                if (result.next() && result.getBoolean(1)) {
                    System.out.println("**Schema " + schema_name + " found.**");
                } else {
                    System.out.println("*Schema " + schema_name + " not found. Creating..*");

                    // create
                    createSchema(connection);
                }
            }
        } catch (SQLException e) {
            System.out.println("SCHEMA NOT FOUND ISSUE");
            System.out.println(e.getErrorCode() + ": " + e.getMessage());
        }
    }

    public static void getOrCreateTables(Connection connection) {
        // get tables
        String sqlFindTable = "SELECT EXISTS (SELECT FROM information_schema.tables " +
                "WHERE table_name = ?);";
        System.out.println("**Tables found: **");

        try (PreparedStatement table_stmt = connection.prepareStatement(sqlFindTable)) {
            for (String table : tables) {
                table_stmt.setString(1, table);

                try (ResultSet result = table_stmt.executeQuery()) {
                    if (result.next() && result.getBoolean(1)) {
                        System.out.println("Table: " + table + " found.");
                    } else {
                        System.out.println("*Table: " + table + " not found. Creating..*");

                        // create
                        createTables(connection, table);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("TABLE NOT FOUND ISSUE");
            System.out.println(e.getErrorCode() + ": " + e.getMessage());
        }
    }

    public static void createSchema(Connection connection) {
        String sqlCreateSchema = "CREATE SCHEMA IF NOT EXISTS " + schema_name;

        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(sqlCreateSchema);
            System.out.println(schema_name + " created.");
        } catch (SQLException e) {
            System.out.println("CREATE SCHEMA ISSUE");
            System.out.println(e.getErrorCode() + ": " + e.getMessage());
        }
    }

    public static void createTables(Connection connection, String tableName) {

        String filePath = schema_files_directory + "/" + tableName + ".sql";

        String sql = readFile(filePath);

        if (sql != null && !sql.isEmpty()) {
            try (Statement stmt = connection.createStatement()) {
                stmt.executeUpdate(sql);
                System.out.println("Table " + tableName + " created.");
            } catch (SQLException e) {
                System.out.println("CREATE TABLE ISSUE");
                System.out.println(e.getErrorCode() + ": " + e.getMessage());
            }
        }
    }

    public static String readFile(String filepath) {
        StringBuilder content = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            System.out.println("Error with: " + filepath);
            System.out.println(e.getMessage());
            return null;
        }
        return content.toString();
    }
}
