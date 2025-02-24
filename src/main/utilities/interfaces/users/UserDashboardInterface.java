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
            System.out.println("[3] Update/Create Stats");
            System.out.println("[4] Return to Main Menu");
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
                    updateOrAddUserStats(connection, userId);
                    break;
                case "4":
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

    private static void updateOrAddUserStats(Connection connection, int userId) {
        System.out.println(outerSectionDivider);
        System.out.println("\n=== Update or Add User Stats ===");

        displayGames(connection);

        System.out.print("Enter game title to update or add stats for: ");
        String gameTitle = scan.next().trim().toLowerCase();

        String username = getUsername(connection, userId);
        int gameId = getGameId(connection, gameTitle);
        if (gameId == -1) {
            System.out.println("Game not found.");
            return;
        }

        if (userHasStats(connection, userId, gameId)) {
            updateStats(connection, userId, gameId);
        } else {
            addStats(connection, userId, gameId, username, gameTitle);
        }
    }

    private static String getUsername(Connection connection, int userId) {
        String getUsernameQuery = "SELECT username FROM vitals.users WHERE user_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(getUsernameQuery)) {
            stmt.setInt(1, userId);  // Corrected parameter setting
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("username");
                } else {
                    System.out.println("username for: " + userId + "not found..");
                    return "";
                }
            }
        } catch (SQLException e) {
            System.out.println("!!! GET USERNAME ISSUE !!!");
            System.out.println("-- " + e.getErrorCode() + ": " + e.getMessage());
        }
        return "";
    }

    private static void displayGames(Connection connection) {
        String getGamesQuery = "SELECT game_id, game_title FROM vitals.games";
        System.out.println("Available games: ");
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(getGamesQuery)) {
            while (rs.next()) {
                System.out.println("-> " + rs.getString("game_title"));
            }
        } catch (SQLException e) {
            System.out.println("!!! DISPLAY GAMES ISSUE !!!");
            System.out.println("-- " + e.getErrorCode() + ": " + e.getMessage());
        }
    }

    private static int getGameId(Connection connection, String gameTitle) {
        String getGameIdQuery = "SELECT game_id FROM vitals.games WHERE game_title ILIKE ?";
        try (PreparedStatement gameStmt = connection.prepareStatement(getGameIdQuery)) {
            gameStmt.setString(1, "%" + gameTitle + "%");
            ResultSet gameRs = gameStmt.executeQuery();
            if (gameRs.next()) {
                return gameRs.getInt("game_id");
            }
        } catch (SQLException e) {
            System.out.println("!!! GET GAME ID ISSUE !!!");
            System.out.println("-- " + e.getErrorCode() + ": " + e.getMessage());
        }
        return -1;
    }

    private static boolean userHasStats(Connection connection, int userId, int gameId) {
        String checkStatsQuery = "SELECT 1 FROM vitals.user_stats WHERE user_id = ? AND game_id = ?";
        try (PreparedStatement checkStats = connection.prepareStatement(checkStatsQuery)) {
            checkStats.setInt(1, userId);
            checkStats.setInt(2, gameId);
            ResultSet statsRs = checkStats.executeQuery();
            return statsRs.next();
        } catch (SQLException e) {
            System.out.println("!!! GET USER STATS ISSUE !!!");
            System.out.println("-- " + e.getErrorCode() + ": " + e.getMessage());
        }
        return false;
    }

    private static void updateStats(Connection connection, int userId, int gameId) {
        System.out.println("Input new stats: ");

        System.out.print("New Time Played [minutes]: ");
        int timePlayed = scan.nextInt();
        scan.nextLine();

        System.out.print("New Start Rank: ");
        String startRank = scan.next();

        System.out.print("New Current Rank: ");
        String currentRank = scan.next();

        System.out.print("New Peak Rank: ");
        String peakRank = scan.next();


        String updateStatsQuery = "UPDATE vitals.user_stats SET time_played = ?, start_rank = ?, " +
                "current_rank = ?, peak_rank = ? WHERE user_id = ? AND game_id = ?";
        try (PreparedStatement updateStmt = connection.prepareStatement(updateStatsQuery)) {
            updateStmt.setInt(3, timePlayed);
            updateStmt.setString(4, startRank);
            updateStmt.setString(5, currentRank);
            updateStmt.setString(6, peakRank);
            updateStmt.setInt(7, userId);
            updateStmt.setInt(8, gameId);

            int rowsUpdated = updateStmt.executeUpdate();
            System.out.println(rowsUpdated > 0 ? "Stats updated." : "Could not update stats.");
        } catch (SQLException e) {
            System.out.println("!!! UPDATE STATS ERROR !!!");
            System.out.println("-- " + e.getErrorCode() + ": " + e.getMessage());
        }
    }

    private static void addStats(Connection connection, int userId, int gameId, String username, String gameTitle) {
        System.out.println("Add initial stats: ");

        System.out.print("New Time Played [minutes]: ");
        int timePlayed = scan.nextInt();
        scan.nextLine();

        System.out.print("New Start Rank: ");
        String startRank = scan.next();

        System.out.print("New Current Rank: ");
        String currentRank = scan.next();

        System.out.print("New Peak Rank: ");
        String peakRank = scan.next();

        String insertStatsQuery = "INSERT INTO vitals.user_stats (user_id, username, game_id, game_title, " +
                "time_played, start_rank, current_rank, peak_rank) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement insertStmt = connection.prepareStatement(insertStatsQuery)) {
            insertStmt.setInt(1, userId);
            insertStmt.setString(2, username);
            insertStmt.setInt(3, gameId);
            insertStmt.setString(4, gameTitle);
            insertStmt.setInt(5, timePlayed);
            insertStmt.setString(6, startRank);
            insertStmt.setString(7, currentRank);
            insertStmt.setString(8, peakRank);

            int rowsInserted = insertStmt.executeUpdate();
            System.out.println(rowsInserted > 0 ? "Stats added." : "Could not add stats.");
        } catch (SQLException e) {
            System.out.println("!!! CREATE STATS ERROR !!!");
            System.out.println("-- " + e.getErrorCode() + ": " + e.getMessage());
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
                    SELECT u.username, u.country, g.game_title, us.time_played,
                        COALESCE(us.start_rank, '') AS start_rank,
                        COALESCE(us.current_rank, '') AS current_rank,
                        COALESCE(us.peak_rank, '') AS peak_rank
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
        int hours = (minutes % (24 * 60)) / 60;
        int mins = minutes % 60;
        return String.format("%d days %d hours %d mins", days, hours, mins);
    }
}


