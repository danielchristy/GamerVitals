package main;

import main.utilities.handlers.*;
import main.utilities.interfaces.general.*;

import java.sql.*;

public class Main {

    public static void main(String[] args) {
        final String divider = "============================================";
        boolean loggedIn = false;
        int userId = 0;
        System.out.println(divider);

        // establish connection to run app
        try (Connection connection = DatabaseInit.run()) {
            System.out.println(divider);

            String launch = StartingInterface.printMainInterface();

            while (!loggedIn){
                switch (launch) {
                    case "l":
                        userId = LoginInterface.printLoginInterface(connection);
                        if (userId != 0) {
                            loggedIn = true;
                        }
                        break;
                    case "s":
                        SignupInterface.printSignupInterface(connection);
                        break;
                    case "q":
                        System.out.println("Exiting app..");
                        System.exit(0);
                        break;
                }
            }

            while (loggedIn) {
                MainMenuInterface.printMainMenuInterface(connection, userId);
            }

        } catch (SQLException e) {
            // connection not established
            System.out.println("*Failed to establish connection with database: *");
            System.out.println(e.getErrorCode() + ": " + e.getMessage());
        }
    }
}

