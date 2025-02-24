package main.utilities.handlers;

import java.io.*;
import java.sql.*;
import java.util.stream.Collectors;

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
                // schema found
                if (result.next() && result.getBoolean(1)) {
                    System.out.println("> Schema " + schema_name + " found.");

                    // now check for tables inside schema
                    getOrCreateTables(connection);
                } else {
                    System.out.println("**Schema " + schema_name + " not found. Creating..");

                    // schema not found - create
                    createSchema(connection);

                    // make tables after schema is made
                    getOrCreateTables(connection);
                }
            }
        } catch (SQLException e) {
            System.out.println("   !!! SCHEMA NOT FOUND ISSUE !!!");
            System.out.println("-- " + e.getErrorCode() + ": " + e.getMessage());
        }
    }

    public static void getOrCreateTables(Connection connection) {
        // get tables
        String sqlFindTable = "SELECT EXISTS (SELECT FROM information_schema.tables " +
                "WHERE table_name = ?);";

        try (PreparedStatement table_stmt = connection.prepareStatement(sqlFindTable)) {
            for (String table : tables) {
                table_stmt.setString(1, table);

                try (ResultSet result = table_stmt.executeQuery()) {
                    if (result.next() && result.getBoolean(1)) {
                        System.out.println("> Table: " + table + " found.");
                    } else {
                        System.out.println("**Table: " + table + " not found. Creating..");

                        // create
                        createTables(connection, table);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("   !!! TABLE NOT FOUND ISSUE !!!");
            System.out.println("-- " + e.getErrorCode() + ": " + e.getMessage());
        }
    }

    public static void createSchema(Connection connection) {
        String sqlCheckRoles = "SELECT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'user_role');";
        String sqlRoleCreation = "CREATE TYPE IF NOT EXISTS vitals.user_role " +
                "AS ENUM ('player', 'developer', 'recruiter', 'admin');";
        String sqlCreateSchema = "CREATE SCHEMA IF NOT EXISTS " + schema_name;
        System.out.println("> Creating " + schema_name + "..");



        try (Statement roleStmt = connection.createStatement()) {
            ResultSet rs = roleStmt.executeQuery(sqlCheckRoles);
            if (rs.next() && !rs.getBoolean(1)) {
                roleStmt.executeUpdate(sqlRoleCreation);
                System.out.println("> created user roles.");
            } else {
                System.out.println("> user roles already exist.");
            }
        } catch (SQLException e) {
            System.out.println("!!! ROLE CREATION ISSUE !!!");
            System.out.println(e.getErrorCode() + ": " + e.getMessage());
        }

        try (Statement schemaStmt = connection.createStatement()) {
            schemaStmt.executeUpdate(sqlCreateSchema);
            System.out.println("> " + schema_name + " created.");
            } catch (SQLException e) {
                System.out.println("!!! CREATE SCHEMA ISSUE !!!");
                System.out.println(e.getErrorCode() + ": " + e.getMessage());
            }

    }

    public static void createTables(Connection connection, String tableName) {
        if (tableName.equals("users")) {
            createTableFromFile(connection, tableName);
        }

        if (tableName.equals("games")) {
            createTableFromFile(connection, tableName);
        }

        if (tableName.equals("user_stats")) {
            createTableFromFile(connection, tableName);
        }
    }

    public static void createTableFromFile(Connection connection, String tableName) {
        String filePath = schema_files_directory + "/" + tableName + ".sql";

        try (BufferedReader sql = new BufferedReader(new FileReader(filePath))) {
            String sqlStmt = sql.lines().collect(Collectors.joining("\n"));

            try (Statement stmt = connection.createStatement()) {
                stmt.executeUpdate(sqlStmt);
                System.out.println("> Table " + tableName + " created.");
            } catch (SQLException e) {
                System.out.println("!!! CREATE TABLE ISSUE !!!");
                System.out.println("-- " + e.getErrorCode() + ": " + e.getMessage());
            }
        } catch (IOException e) {
            System.out.println("!!! READ FILE ISSUE");
            throw new RuntimeException(e.getMessage());
        }
    }
}
