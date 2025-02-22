package main.utilities.interfaces.recruiters;

import java.sql.*;
import java.util.Scanner;

public class RecruiterInterface {
    static final private Scanner scan = new Scanner(System.in);
    static final String outerSectionDivider = "--------------------";
    static final String innerSectionDivider = "----------";

    public static void printRecruiterOptions(Connection connection) {

        while (true) {
            System.out.println("\n~~~~~~ Recruiter Dashboard ~~~~~~");
            System.out.println("[1] Search User by Username");
            System.out.println("[2] Search Players by Country");
            System.out.println("[3] Search Players by Game");
            System.out.println("[4] Search Players by Rank");
            System.out.println("[5] Exit to Main Menu");
            System.out.println("[Q] Logout");
            System.out.println("[E] Exit App");
            System.out.print("> ");

            String input = scan.nextLine().toLowerCase().trim();

            switch (input) {
                case "1":
                    searchByUsername(connection);
                    break;
                case "2":
                    searchByCountry(connection);
                    break;
                case "3":
                    searchByGame(connection);
                    break;
                case "4":
                    searchByRank(connection);
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

    private static void searchByUsername(Connection connection) {
        System.out.println(outerSectionDivider);
        System.out.print("Enter username to search: ");
        String username = scan.nextLine().trim();
        String query = "SELECT user_id, username, email, country FROM vitals.users WHERE username ILIKE ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, "%" + username + "%");
            ResultSet rs = stmt.executeQuery();

            System.out.println("\n=== User Search Results ===");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("user_id"));
                System.out.println("Username: " + rs.getString("username"));
                System.out.println("Email: " + rs.getString("email"));
                System.out.println("Country: " + rs.getString("country"));
                System.out.println(innerSectionDivider);
            }
        } catch (SQLException e) {
            System.out.println(e.getErrorCode() + ": " + e.getMessage());
        }
    }

    private static void searchByCountry(Connection connection) {
        System.out.println(outerSectionDivider);
        System.out.print("Enter country to search for players: ");
        String country = scan.nextLine().trim();
        String query = "SELECT user_id, username, email FROM vitals.users WHERE country ILIKE ? AND role = 'player'";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, "%" + country + "%");
            ResultSet rs = stmt.executeQuery();

            System.out.println("\n=== Players from " + country + " ===");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("user_id"));
                System.out.println("Username: " + rs.getString("username"));
                System.out.println("Email: " + rs.getString("email"));
                System.out.println(innerSectionDivider);
            }
        } catch (SQLException e) {
            System.out.println(e.getErrorCode() + ": " + e.getMessage());
        }
    }

    private static void searchByGame(Connection connection) {
        System.out.println(outerSectionDivider);
        System.out.print("Enter game title to search for players: ");
        String gameTitle = scan.nextLine().trim();

        String query = """
                SELECT u.user_id, u.username, g.game_title, us.time_played
                FROM vitals.users u
                JOIN vitals.user_stats us ON u.user_id = us.user_id
                JOIN vitals.games g ON us.game_id = g.game_id
                WHERE g.game_title ILIKE ?
                """;

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, "%" + gameTitle + "%");
            ResultSet rs = stmt.executeQuery();

            System.out.println("\n====== Players who played " + gameTitle + " ======");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("user_id"));
                System.out.println("Username: " + rs.getString("username"));
                int timePlayed = rs.getInt("time_played");
                System.out.println("Playtime: " + formatTime(timePlayed));
                System.out.println(innerSectionDivider);
            }
        } catch (SQLException e) {
            System.out.println(e.getErrorCode() + ": " + e.getMessage());
        }
    }

    private static void searchByRank(Connection connection) {
        System.out.println(outerSectionDivider);
        System.out.print("Enter rank to search for players: ");
        String rank = scan.nextLine().trim();

        String query = """
                SELECT u.user_id, u.username, g.game_title, us.current_rank
                FROM vitals.users u
                JOIN vitals.user_stats us ON u.user_id = us.user_id
                JOIN vitals.games g ON us.game_id = g.game_id
                WHERE us.current_rank ILIKE ?
                """;

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, "%" + rank + "%");
            ResultSet rs = stmt.executeQuery();

            System.out.println("\n=== Players with Rank " + rank + " ===");
            while (rs.next()) {
                System.out.println(innerSectionDivider);
                System.out.println("ID: " + rs.getInt("user_id"));
                System.out.println("Username: " + rs.getString("username"));
                System.out.println("Game: " + rs.getString("game_title"));
                System.out.println("Current Rank: " + rs.getString("current_rank"));
                System.out.println(innerSectionDivider);
            }
        } catch (SQLException e) {
            System.out.println(e.getErrorCode() + ": " + e.getMessage());
        }
    }

    private static String formatTime(int minutes) {
        int days = minutes / (24 * 60);
        int hours = (minutes / (24 * 60)) / 60;
        int mins = minutes % 60;
        return String.format("%d days %02d:%02d:00", days, hours, mins);
    }
}
