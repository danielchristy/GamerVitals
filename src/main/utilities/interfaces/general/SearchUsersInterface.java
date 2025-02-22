package main.utilities.interfaces.general;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class SearchUsersInterface {
    private static final Scanner scan = new Scanner(System.in);
    static final String outerSectionDivider = "--------------------";
    static final String innerSectionDivider = "----------";

    public static void printSearchUsers(Connection connection) {
        System.out.print("Enter username to search: ");
        String username = scan.nextLine();

        String query = "SELECT user_id, username, role, country FROM vitals.users WHERE username ILIKE ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            // formatting for ILIKE - case-insensitive search for usernames
            stmt.setString(1, "%" + username + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                System.out.println(outerSectionDivider);
                System.out.println("User ID: " + rs.getInt("user_id"));
                System.out.println("Username: " + rs.getString("username"));
                System.out.println("Role: " + rs.getString("role"));
                System.out.println("Country: " + rs.getString("country"));
                System.out.println(innerSectionDivider);
            }
            System.out.println(outerSectionDivider);

        } catch (SQLException e) {
            System.out.println("Error searching users: " + e.getMessage());
        }
    }
}
