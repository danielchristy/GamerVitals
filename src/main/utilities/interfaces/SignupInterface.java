package main.utilities.interfaces;

import java.sql.Connection;
import java.util.Scanner;

public class SignupInterface {
    static Scanner scan = new Scanner(System.in);

    static final private String signupPrompt = "Create a New Account - ";
    static final private String newAccountEmail = "Enter Email: ";
    static final private String newAccountUsername = "Create Username: ";
    static final private String newAccountCountry = "Enter Country of Residence: ";
    static final private String newAccountPassword = "Create Password: ";
    static final private String confirmPassword = "Reenter Password: ";


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

        System.out.println(newAccountPassword);
        String password = scan.nextLine();

        System.out.println(confirmPassword);
        String confirmPassword = scan.nextLine();

        // validate password matching
        if (!confirmPassword.equals(password)) {
            System.out.println("Passwords do not match - Please try again.");
            return;
        }

    }
}
