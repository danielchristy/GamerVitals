package main;

import main.utilities.handlers.*;
import main.utilities.interfaces.general.*;

import java.sql.*;

public class Main {
    static boolean running;

    public static void main(String[] args) {
        running = true;
        final String divider = "============================================";

        while (running) {
            boolean loggedIn = false;
            int userId = 0;
            System.out.println(divider);

            // establish connection to run app
            try (Connection connection = DatabaseInit.run()) {
                String launch = StartingInterface.printMainInterface();

                while (!loggedIn) {
                    switch (launch) {
                        case "l":
                            userId = LoginInterface.printLoginInterface(connection);
                            if (userId != 0) {
                                loggedIn = true;
                            }
                            break;
                        case "s":
                            SignupInterface.printSignupInterface(connection);
                            launch = StartingInterface.printMainInterface();
                            break;
                        case "q":
                            System.out.println("Exiting app..");
                            running = false;
                            System.exit(0);
                            break;
                        default:
                            System.out.println("Not valid, try again.");
                            break;
                    }
                    break;
                }

                while (loggedIn) {
                    loggedIn = MainMenuInterface.printMainMenuInterface(connection, userId, loggedIn);
                }

            } catch (SQLException e) {
                // connection not established
                System.out.println("*Failed to establish connection with database: *");
                System.out.println(e.getErrorCode() + ": " + e.getMessage());
            }
        }
    }
}

