package main.utilities.interfaces;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import java.sql.Connection;

public class SignupInterface {
    static Scanner scan = new Scanner(System.in);

    static final private String signupPrompt = "Create a New Account - ";
    static final private String newAccountEmail = "Enter Email: ";
    static final private String newAccountUsername = "Create Username: ";
    static final private String newAccountRole = "Choose a role: [P]layer, [D]eveloper, [R]ecruiter";
    static final private String newAccountCountry = "Enter Country of Residence: ";
    static final private String newAccountPassword = "Create Password: ";
    static final private String confirmPassword = "Reenter Password: ";

    public static void printSignupInterface(Connection connection) {
        enum ROLES {PLAYER, DEVELOPER, RECRUITER, ADMIN}

        String sql = "INSERT INTO users(username, email, country, password";

        System.out.println(signupPrompt);
        System.out.println();

        // get new user account info
        System.out.println(newAccountEmail);
        String email = scan.nextLine();

        System.out.println(newAccountUsername);
        String username = scan.nextLine();

        System.out.println(newAccountCountry);
        String country = scan.nextLine();

        System.out.println(newAccountRole);
        String roleChoice = scan.nextLine();
        try {
            ROLES user_role = ROLES.valueOf(roleChoice);
        } catch (IllegalArgumentException e) {
            System.out.println("Role choice not found: " + ": " + e.getMessage());
        }

        System.out.println(newAccountPassword);
        String password = scan.nextLine();

        System.out.println(confirmPassword);
        String confirmPassword = scan.nextLine();

        // validate password matching
        if (!confirmPassword.equals(password)) {
            System.out.println("Passwords do not match - Please try again.");

        }
    }

        public static boolean createAccount(Connection connection, String email,
                                            String username, String country,
                                            String role, String password){
        String sql = "INSERT INTO users(email, username, country, role, password) VALUES(? ? ? ? ? ?);";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, username);
            stmt.setString(3, country);
            stmt.setString(4, role);
            stmt.setString(5, password);
        } catch (SQLException e) {
            System.out.println(e.getErrorCode() + ": " + e.getMessage());
        }
        return false;
        }

}

