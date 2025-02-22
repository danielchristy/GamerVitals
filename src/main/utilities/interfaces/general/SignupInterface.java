package main.utilities.interfaces.general;

import java.util.Scanner;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class SignupInterface {
    static Scanner scan = new Scanner(System.in);

    static final private String signupPrompt = "Create a New Account - ";
    static final private String newAccountEmail = "Enter Email: ";
    static final private String newAccountUsername = "Create Username: ";
    static final private String newAccountRole = "Choose a role: [P]layer, [D]eveloper, [R]ecruiter";
    static final private String newAccountCountry = "Enter Country of Residence: ";
    static final private String newAccountPassword = "Create Password: ";
    static final private String confirmPassword = "Reenter Password: ";

    enum ROLES {PLAYER, DEVELOPER, RECRUITER, ADMIN}

    public static void printSignupInterface(Connection connection) {

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

        String role = switch (roleChoice) {
            case "P" -> ROLES.PLAYER.name();
            case "D" -> ROLES.DEVELOPER.name();
            case "R" -> ROLES.RECRUITER.name();
            case "A" -> ROLES.ADMIN.name();
            default -> {
                System.out.println("Invalid role choice. Defaulting to PLAYER.");
                yield ROLES.PLAYER.name();
            }
        };

        System.out.println(newAccountPassword);
        String password = scan.nextLine();

        System.out.println(confirmPassword);
        String confirmPassword = scan.nextLine();

        // validate password matching
        if (!confirmPassword.equals(password)) {
            System.out.println("Passwords do not match - Please try again.");
        } else {
            boolean created = createAccount(connection, email, username, country, role, password);

            if (created) {
                System.out.println("Account created!");
            } else {
                System.out.println("Failed to create account: ");
                System.out.println("Account either exists, or provided information could not be accepted.. ");
            }
        }
    }

        public static boolean createAccount(Connection connection, String email, String username,
                                            String country, String role, String password) {
            String sql = "INSERT INTO users.sql(email, username, country, role, password) VALUES(? ? ? ? ? ?);";

            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, email);
                stmt.setString(2, username);
                stmt.setString(3, country);
                stmt.setString(4, role);
                stmt.setString(5, password);

                // check to see if data was inserted
                return stmt.executeUpdate() > 0;

            } catch (SQLException e) {
                // no data was inserted, or faulty data
                // can definitely add more descriptors for why creation failed here..
                System.out.println(e.getErrorCode() + ": " + e.getMessage());
                return false;
            }
        }
}

