package main.utilities.interfaces.devs;

import java.sql.*;
import java.util.Scanner;

public class DeveloperInterface {
    private static final Scanner scan = new Scanner(System.in);
    static final String outerSectionDivider = "--------------------";
    static final String innerSectionDivider = "----------";

    public static void printDeveloperOptions(Connection connection) {

        while (true) {
            System.out.println("\n~~~~~~ Developer Dashboard ~~~~~~");
            System.out.println("[1] Add New Game");
            System.out.println("[2] Update Game Details");
            System.out.println("[3] Main Menu");
            System.out.println("[Q] Logout");
            System.out.println("[E] Exit App");
            System.out.print("> ");

            String input = scan.nextLine().trim();
            switch (input) {
                case "1":
                    addGame(connection);
                    break;
                case "2":
                    updateGame(connection);
                    break;
                case "3":
                    System.out.println("Returning to Main Menu..");
                    return;
                case "q":
                    System.out.println("Logging out..");
                    return;
                case "e":
                    System.out.println("Exiting app..");
                    System.exit(0);
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    private static void addGame(Connection connection) {
        System.out.print("Game Title: ");
        String title = scan.nextLine().trim();

        System.out.print("Genre: ");
        String genre = scan.nextLine().trim();

        System.out.print("Publisher: ");
        String publisher = scan.nextLine().trim();

        System.out.print("Price: ");
        double price = Double.parseDouble(scan.nextLine().trim());

        System.out.print("In-game Purchases? (true/false): ");
        boolean inGamePurchases = Boolean.parseBoolean(scan.nextLine().trim());

        System.out.print("Release Date (YYYY-MM-DD): ");
        String releaseDate = scan.nextLine().trim();

        String query = "INSERT INTO vitals.games (game_title, genre, publisher, price, " +
                "in_game_purchases, release_date) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, title);
            stmt.setString(2, genre);
            stmt.setString(3, publisher);
            stmt.setDouble(4, price);
            stmt.setBoolean(5, inGamePurchases);
            stmt.setDate(6, java.sql.Date.valueOf(releaseDate));

            stmt.executeUpdate();
            System.out.println("Game added successfully.");
        } catch (SQLException e) {
            System.out.println(e.getErrorCode() + ": " + e.getMessage());
        }
    }

    private static void updateGame(Connection connection) {
        System.out.print("Enter Game ID to update: ");
        int gameId = Integer.parseInt(scan.nextLine().trim());

        System.out.print("New Price: ");
        double price = Double.parseDouble(scan.nextLine().trim());

        System.out.print("In-game Purchases? (true/false): ");
        boolean inGamePurchases = Boolean.parseBoolean(scan.nextLine().trim());

        String query = "UPDATE vitals.games SET price = ?, in_game_purchases = ? WHERE game_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setDouble(1, price);
            stmt.setBoolean(2, inGamePurchases);
            stmt.setInt(3, gameId);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Game updated successfully.");
            } else {
                System.out.println("Game ID not found.");
            }
        } catch (SQLException e) {
            System.out.println(e.getErrorCode() + ": " + e.getMessage());
        }
    }
}
