package main.utilities.interfaces.users;

import java.sql.*;
import java.util.Scanner;

public class UserDashboardInterface {
    static final private Scanner scan = new Scanner(System.in);
    static final String outerSectionDivider = "--------------------";
    static final String innerSectionDivider = "----------";

    public static void printUserDashboard(Connection connection, int userId) {

        while (true) {
            System.out.println("\n~~~~~~ User Dashboard ~~~~~~");
            System.out.println("[1] Update Info");
            System.out.println("[2] View Stats");
            System.out.println("[3] Return to Main Menu");
            System.out.println("[Q] Logout");
            System.out.println("[E] Exit App");
            System.out.print("> ");

            String input = scan.nextLine().toLowerCase().trim();

            switch (input) {
                case "1":
                    updateUserInfo(connection, userId);
                    break;
                case "2":
                    viewUserStats(connection, userId);
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
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void updateUserInfo(Connection connection, int userId) {
        System.out.println(outerSectionDivider);
        System.out.println("\n=== Update User Info ===");
        System.out.println("[Leave fields blank to keep existing data.]");

        // Get updated values from user
        System.out.print("New Email: ");
        String email = scan.nextLine().trim();

        System.out.print("New Username: ");
        String username = scan.nextLine().trim();

        System.out.print("New Country: ");
        String country = scan.nextLine().trim();

        System.out.print("New Password: ");
        String password = scan.nextLine().trim();

        // get new user info - if empty should not update
        StringBuilder sql = new StringBuilder("UPDATE vitals.users SET ");
        boolean updated = false;

        if (!email.isEmpty()) {
            sql.append("email = ?, ");
            updated = true;
        }
        if (!username.isEmpty()) {
            sql.append("username = ?, ");
            updated = true;
        }
        if (!country.isEmpty()) {
            sql.append("country = ?, ");
            updated = true;
        }
        if (!password.isEmpty()) {
            sql.append("password = ?, ");
            updated = true;
        }

        if (!updated) {
            System.out.println("No updates provided.");
            return;
        }

        // formatting and completing the sql query
        sql.setLength(sql.length() - 2);
        sql.append(" WHERE user_id = ?");

        try (PreparedStatement stmt = connection.prepareStatement(sql.toString())) {
            int paramIndex = 1;

            if (!email.isEmpty()) stmt.setString(paramIndex++, email);
            if (!username.isEmpty()) stmt.setString(paramIndex++, username);
            if (!country.isEmpty()) stmt.setString(paramIndex++, country);
            if (!password.isEmpty()) stmt.setString(paramIndex++, password);

            stmt.setInt(paramIndex, userId);

            int dataUpdated = stmt.executeUpdate();

            if (dataUpdated > 0) {
                System.out.println("User info updated.");
            } else {
                System.out.println("No updates made.");
            }
        } catch (SQLException e) {
            System.out.println("Failed to update user info: ");
            System.out.println(e.getErrorCode() + ": " + e.getMessage());
        }
    }

    private static void viewUserStats(Connection connection, int userId) {
        System.out.println(outerSectionDivider);
        String query = """
                    SELECT user.username, user.country, game.game_title, us.time_played,
                        COALESCE(us.start_rank, '') AS start_rank,
                        COALESCE(us.current_rank, '') AS current_rank,
                        COALESCE(us.peak_rank, '') AS peak_rank,
                    FROM vitals.users u
                    LEFT JOIN vitals.user_stats us ON u.user_id = us.user_id
                    LEFT JOIN vitals.games g ON us.game_id = g.game_id
                    WHERE u.user_id = ?
                """;

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            boolean userHasStats = false;

            while (rs.next()) {
                if (!userHasStats) {
                    System.out.println(innerSectionDivider);
                    System.out.println("\n=== User Stats ===");
                    System.out.println("Username: " + rs.getString("username"));
                    System.out.println("Country: " + rs.getString("country"));
                    userHasStats = true;
                }

                if (rs.getString("game_title") != null) {
                    System.out.println(innerSectionDivider);
                    System.out.println("Game: " + rs.getString("game_title"));
                    int timePlayed = rs.getInt("time_played");
                    System.out.println("Playtime: " + formatTime(timePlayed));

                    String startRank = rs.getString("start_rank");
                    String currentRank = rs.getString("current_rank");
                    String peakRank = rs.getString("peak_rank");

                    if (!startRank.isEmpty() || !currentRank.isEmpty() || !peakRank.isEmpty()) {
                        System.out.println("Start Rank: " + (startRank.isEmpty() ? "--" : startRank));
                        System.out.println("Current Rank: " + (currentRank.isEmpty() ? "--" : currentRank));
                        System.out.println("Peak Rank: " + (peakRank.isEmpty() ? "--" : peakRank));
                    }
                    System.out.println(innerSectionDivider);
                }
            }

            if (!userHasStats) {
                System.out.println("Stats not found for user.");
            }
        } catch (SQLException e) {
            System.out.println("Could not get user info: ");
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


