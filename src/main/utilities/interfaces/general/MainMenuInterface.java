package main.utilities.interfaces.general;

import main.utilities.interfaces.users.*;
import main.utilities.interfaces.devs.*;
import main.utilities.interfaces.recruiters.*;
import main.utilities.interfaces.admins.*;

import java.sql.*;
import java.util.Scanner;

public class MainMenuInterface {
    static final private Scanner scan = new Scanner(System.in);

    public static void printMainMenuInterface(Connection connection, int userId) {
        String role = getUserRole(connection, userId);

        while (true) {
            System.out.println("\n~~~~~~ Main Menu ~~~~~~");
            System.out.println("[1] User Dashboard");
            System.out.println("[2] Search Users");
            System.out.println("[3] Search Games");

            if (role.equalsIgnoreCase("developer")) {
                System.out.println("[4] Developer Options");
            } else if (role.equalsIgnoreCase("recruiter")) {
                System.out.println("[4] Recruiter Options");
            } else if (role.equalsIgnoreCase("admin")) {
                System.out.println("[4] Admin Options");
            }

            System.out.println("[Q] Logout");
            System.out.println("[E] Exit");
            System.out.print("> ");

            String input = scan.nextLine().toLowerCase().trim();

            switch (input) {
                case "1":
                    UserDashboardInterface.printUserDashboard(connection, userId);
                    break;
                case "2":
                    SearchUsersInterface.printSearchUsers(connection);
                    break;
                case "3":
                    SearchGamesInterface.printSearchGames(connection);
                    break;
                case "4":
                    getRoleOptions(connection, role);
                    break;
                case "q":
                    System.out.println("Logging out..");
                    return;
                case "e":
                    System.out.println("Exiting app..");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Try again.");
            }

        }
    }

    private static String getUserRole(Connection connection, int userId) {
        String userRole = "player"; //default
        String sql = "SELECT role FROM vitals.users WHERE user_id = ?;";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()) {
                userRole = rs.getString("role");
            }
        } catch (SQLException e) {
            System.out.println(e.getErrorCode() + ": " + e.getMessage());
        }
        return userRole;
    }

    private static void getRoleOptions(Connection connection, String role) {
        switch (role) {
            case "developer":
                DeveloperInterface.printDeveloperOptions(connection);
                break;
            case "recruiter":
                RecruiterInterface.printRecruiterOptions(connection);
                break;
            case "admin":
                AdminInterface.printAdminOptions(connection);
                break;
            default:
                System.out.println("Invalid role provided.");
        }
    }

}
