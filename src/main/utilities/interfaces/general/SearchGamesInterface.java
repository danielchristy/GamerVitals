package main.utilities.interfaces.general;

import java.sql.*;
import java.util.Scanner;

public class SearchGamesInterface {
    static final private Scanner scan = new Scanner(System.in);
    static final String outerSectionDivider = "--------------------";
    static final String innerSectionDivider = "----------";

    public static void printSearchGames(Connection connection) {
        System.out.println(outerSectionDivider);
        System.out.print("Enter a game title to search: > ");
        String gameTitle = scan.nextLine();

        String sql = "SELECT * FROM vitals.games WHERE game_title ILIKE ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            // formatting for ILIKE - case-insensitive search for game titles
            stmt.setString(1, "%" + gameTitle + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                System.out.println("Game ID: " + rs.getInt("game_id"));
                System.out.println("Title: " + rs.getString("game_title"));
                System.out.println("Genre: " + rs.getString("genre"));
                System.out.println("Publisher: " + rs.getString("publisher"));
                System.out.println("Price: " + rs.getString("price"));
                System.out.println("In Game Purchases: " + (rs.getBoolean("in_game_purchases") ? "YES" : "NO"));
                System.out.println("Release Date: " + rs.getDate("release_date"));
                System.out.println(innerSectionDivider);
            }
            System.out.println(outerSectionDivider);

        } catch (SQLException e) {
            System.out.println("Error searching users: " + e.getMessage());
        }
    }

}
