package main.utilities.interfaces.general;

import java.util.Scanner;
import java.sql.*;

public class LoginInterface {
    static Scanner scan = new Scanner(System.in);
    static boolean logged_in;
    static final String loginPrompt = "Login to Existing Account - ";
    static final String accountLoginPrompt = "Enter Username or Email (or [E}xit): > ";
    static final String accountPasswordPrompt = "Enter Password: > ";

    public static int printLoginInterface(Connection connection) {
        logged_in = false;

        while (!logged_in) {
            System.out.println(loginPrompt);
            System.out.println();

            // get existing user account info
            System.out.print(accountLoginPrompt);
            String usernameOrEmail = scan.nextLine();

            if (usernameOrEmail.equalsIgnoreCase("e")) {
                System.out.println("Exiting.. ");
                return 0;
            }

            System.out.print(accountPasswordPrompt);
            String password = scan.nextLine();

            // check for user in db
            int userId = validateAccount(connection, usernameOrEmail, password);
            if (userId != 0) {
                System.out.println("Welcome Back!");
                return userId;
            } else {
                System.out.println("Account not found. \n Please try again, or signup. \n");
            }
        }

        // account not validated
        return 0;
    }

    public static int validateAccount(Connection connection, String usernameOrEmail, String password) {
        String sql = "SELECT * FROM users.sql WHERE email = ? OR username = ?;";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, usernameOrEmail);
            stmt.setString(2, usernameOrEmail);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String accountPassword = rs.getString("password");

                    if (accountPassword.equals(password)) {
                        return rs.getInt("user_id");
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getErrorCode() + ": " + e.getMessage());
        }

        // if account info not found above, credentials are invalid
        return 0;
    }

}