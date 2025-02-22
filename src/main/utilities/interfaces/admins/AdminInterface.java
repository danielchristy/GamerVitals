package main.utilities.interfaces.admins;

import java.sql.*;
import java.util.Scanner;

public class AdminInterface {
    static final private Scanner scan = new Scanner(System.in);
    static final String outerSectionDivider = "--------------------";
    static final String innerSectionDivider = "----------";

    public static void printAdminOptions(Connection connection) {

        while (true) {
            System.out.println("\n~~~~~~ Admin Dashboard ~~~~~~");
            System.out.println("[1] View All Users");
            System.out.println("[2] Delete User");
            System.out.println("[3] View All Games");
            System.out.println("[4] Delete Game");
            System.out.println("[5] Main Menu");
            System.out.println("[Q] Logout");
            System.out.println("[E] Exit App");
            System.out.print("> ");

            String input = scan.nextLine().toLowerCase().trim();

            switch (input) {
                case "1":
                    viewAllUsers(connection);
                    break;
                case "2":
                    deleteUser(connection);
                    break;
                case "3":
                    viewAllGames(connection);
                    break;
                case "4":
                    deleteGame(connection);
                    break;
                case "5":
                    System.out.println("Returning to Main Menu..");
                    return;
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

    private static void viewAllUsers(Connection connection) {
        System.out.println(outerSectionDivider);
        String query = "SELECT user_id, username, email, role FROM vitals.users";

        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("\n=== Users List ===");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("user_id"));
                System.out.println("Username: " + rs.getString("username"));
                System.out.println("Email: " + rs.getString("email"));
                System.out.println("Role: " + rs.getString("role"));
                System.out.println(innerSectionDivider);
            }
        } catch (SQLException e) {
            System.out.println(e.getErrorCode() + ": " + e.getMessage());
        }
    }

    private static void deleteUser(Connection connection) {
        System.out.println(outerSectionDivider);
        System.out.print("Enter User ID to delete: ");
        int userId = Integer.parseInt(scan.nextLine().trim());

        String query = "DELETE FROM vitals.users WHERE user_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("User deleted successfully.");
            } else {
                System.out.println("User not found.");
            }
        } catch (SQLException e) {
            System.out.println(e.getErrorCode() + ": " + e.getMessage());
        }
    }

    private static void viewAllGames(Connection connection) {
        System.out.println(outerSectionDivider);
        String query = "SELECT game_id, game_title, genre, publisher FROM vitals.games";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("\n=== Games List ===");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("game_id"));
                System.out.println("Title: " + rs.getString("game_title"));
                System.out.println("Genre: " + rs.getString("genre"));
                System.out.println("Publisher: " + rs.getString("publisher"));
                System.out.println(innerSectionDivider);
            }
        } catch (SQLException e) {
            System.out.println(e.getErrorCode() + ": " + e.getMessage());
        }
    }

    private static void deleteGame(Connection connection) {
        System.out.println(outerSectionDivider);
        System.out.print("Enter Game ID to delete: ");
        int gameId = Integer.parseInt(scan.nextLine().trim());

        String query = "DELETE FROM vitals.games WHERE game_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, gameId);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Game deleted successfully.");
            } else {
                System.out.println("Game ID not found.");
            }
        } catch (SQLException e) {
            System.out.println(e.getErrorCode() + ": " + e.getMessage());
        }
    }

}
