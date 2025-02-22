package main.utilities.interfaces.general;

import java.util.Scanner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginInterface {
    static Scanner scan = new Scanner(System.in);
    static boolean logged_in;
    static final String loginPrompt = "Login to Existing Account - ";
    static final String accountLoginPrompt = "Enter Username or Email (or [E}xit): > ";
    static final String accountPasswordPrompt = "Enter Password: > ";

    public static boolean printLoginInterface(Connection connection) {
        logged_in = false;

        while (!logged_in) {
            System.out.println(loginPrompt);
            System.out.println();

            // get existing user account info
            System.out.print(accountLoginPrompt);
            String usernameOrEmail = scan.nextLine();

            if (usernameOrEmail.equalsIgnoreCase("e")) {
                System.out.println("Exiting.. ");
                return false;
            }

            System.out.print(accountPasswordPrompt);
            String password = scan.nextLine();

            // check for user in db
            if (validateAccount(connection, usernameOrEmail, password)) {
                System.out.println("Welcome Back!");
                return true;
            } else {
                System.out.println("Account not found. \n Please try again, or signup.");
            }
        }
        return false;
    }

    public static boolean validateAccount(Connection connection, String usernameOrEmail, String password) {
        String sql = "SELECT * FROM users.sql WHERE email = ? OR username = ?;";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, usernameOrEmail);
            stmt.setString(2, usernameOrEmail);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String accountPassword = rs.getString("password");

                    if (accountPassword.equals(password)) {
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getErrorCode() + ": " + e.getMessage());
        }

        // if account info not found above, credentials are invalid
        return false;
    }
}
