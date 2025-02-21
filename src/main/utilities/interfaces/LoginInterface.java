package main.utilities.interfaces;

import java.util.Scanner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class LoginInterface {
//    static String loginUsernameOrEmail;
//    static String loginPassword;

    static Scanner scan = new Scanner(System.in);

    static final String loginPrompt = "Login to Existing Account - ";
    static final String accountLoginPrompt = "Enter Username or Email: ";
    static final String accountPasswordPrompt = "Enter Password: ";

    public static boolean printLoginInterface(Connection connection) {
        System.out.println(loginPrompt);
        System.out.println();

        // get existing user account info
        System.out.println(accountLoginPrompt);
        String usernameOrEmail = scan.nextLine();

        System.out.println(accountPasswordPrompt);
        String password = scan.nextLine();

        // check for user in db
        if (validateAccount(connection, usernameOrEmail, password)) {
            System.out.println("Welcome Back!");
                return true;
        } else {
            System.out.println("Account not found. Please try again, or signup.");
            return false;
        }
    }

    public static boolean validateAccount(Connection connection, String usernameOrEmail, String password) {
        String sql = "SELECT * FROM users WHERE email = ? OR username = ?";

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
