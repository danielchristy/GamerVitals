package main.utilities.interfaces.general;

import java.util.Scanner;
import java.sql.*;

public class LoginInterface {
    static Scanner scan = new Scanner(System.in);
    static final String divider = "============================================";
    static boolean logged_in;
    static final String loginPrompt = "Login to Existing Account - ";
    static final String accountLoginPrompt = "Enter Username or Email (or [E]xit): > ";
    static final String accountPasswordPrompt = "Enter Password: > ";

    public static int printLoginInterface(Connection connection) {
        logged_in = false;

        while (!logged_in) {
            System.out.println(divider);
            System.out.println(loginPrompt);

            // get existing user account info
            System.out.print(accountLoginPrompt);
            String usernameOrEmail = scan.next();

            if (usernameOrEmail.equalsIgnoreCase("e")) {
                System.out.println("Exiting.. ");
                System.exit(0);
            }

            // check for user in db
            int userId = validateAccount(connection, usernameOrEmail);

            if (userId != 0) {
                System.out.println("Welcome Back!");
                return userId;
            } else {
                System.out.println("-- Account not found. Please try again, or signup. --");
            }
        }

        // account not validated
        return 0;
    }

    public static int validateAccount(Connection connection, String usernameOrEmail) {
        System.out.print(accountPasswordPrompt);
        String password = scan.next();

        String sql = "SELECT * FROM vitals.users WHERE email = ? OR username = ?;";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, usernameOrEmail);
            stmt.setString(2, usernameOrEmail);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String accountPassword = rs.getString("password");

                    if (accountPassword.equals(password)) {
                        return rs.getInt("user_id");
                    } else {
                        System.out.println("-- Incorrect password. --");
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("   !!! ACCOUNT NOT FOUND ISSUE !!!");
            System.out.println("-- " + e.getErrorCode() + ": " + e.getMessage());
        }

        // if account info not found above, credentials are invalid
        return 0;
    }

}