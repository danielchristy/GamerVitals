package main;


import main.models.User;
import main.services.UserGameStatsServices;
import main.services.UserServices;

import java.util.Scanner;

import static main.services.UserServices.*;

public class Main {
    private static Scanner scan = new Scanner(System.in);
    private static UserServices userServices = new UserServices();
    private static UserGameStatsServices statsServices = new UserGameStatsServices();


    public static void main(String[] args) {

        boolean loggedIn = false;

        // Initial Menu
        System.out.println("GamerVitals - Player and Game Stats");

        System.out.println("[L]ogin, View [P]layers, View [G]ames, [Q]uit");
        System.out.println("> ");

        String menuInput = scan.nextLine().toLowerCase().trim();

        switch (menuInput) {
            case "l":
                // User Menu (Login, Create Account, Logout)
                System.out.println("Do you have an account? [Y]es/[N]o: ");
                System.out.println("> ");
                String hasAccountInput = scan.nextLine().toLowerCase().trim();

                switch (hasAccountInput) {
                    case "y" -> {
                        System.out.println("Enter Username or Email: ");
                        System.out.println("> ");
                        String loginPrimary = scan.nextLine().toLowerCase().trim();

                        for (User user : users)
                            if (users.contains(getUserByUsername(loginPrimary)) || users.contains(getUserByEmail(loginPrimary)))
                            {

                            System.out.println("Enter Password: ");
                            System.out.println("> ");
                            String loginPassword = scan.nextLine().toLowerCase().trim();



                            if (users.contains(getUserByUsername(loginPrimary)) || (users.contains(getUserByEmail(loginPrimary))
                                    && (users.contains(getUserPassword(loginPassword))))) {
                                System.out.println("Login Successful.");
                            } else {
                                System.out.println("Invalid Password. Redirecting to main menu..");
                            }
                            }

                        else {
                            System.out.println("Username and/or Email not found. Redirecting to main menu..");
                                }

                    }
                    case "n" -> {

                    }
                    default -> System.out.println("Invalid Input.");

                }


            case "p":
                System.out.println("View Player by: [A]ll users, [ID], user[N]ame, [E]mail, [C]ountry: ");
                System.out.println("> ");
                String userInput = scan.nextLine().toLowerCase().trim();
                switch (userInput) {
                    case "a" -> listAllUsers();
                    case "id" -> {
                        System.out.println("Enter the user's ID you'd like to search for: ");
                        System.out.println("> ");
                        int userId = scan.nextInt();
                        scan.nextLine();
                        getUserById(userId);
                    }
                    case "n" -> {
                        System.out.println("Enter the user's username you'd like to search for: ");
                        System.out.println("> ");
                        String username = scan.nextLine().toLowerCase().trim();
                        getUserByUsername(username);
                    }
                    case "e" -> {
                        System.out.println("Enter the user's email you'd like to search for: ");
                        System.out.println("> ");
                        String email = scan.nextLine();
                        getUserByEmail(email);
                    }
                    case "c" -> {
                        System.out.println("Enter the user's name to see their country: ");
                        System.out.println("> ");
                        int userId = scan.nextInt();
                        scan.nextLine();
                        getUserCountry(userId);
                    }
                    default -> System.out.println("Invalid Input.");

                }

            default:
                System.out.println("Invalid input.");
        }

        System.out.println("Login:");
        System.out.println("Enter Username: ");
        String username = scan.nextLine();
        System.out.println("Enter Password: ");
        String password = scan.nextLine();
    }
}
