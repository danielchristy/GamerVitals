package main;


import main.models.User;
//import main.services.UserGameStatsServices;

import java.util.Scanner;

import static main.services.GameServices.*;
import static main.services.UserServices.*;

public class Main {
    private static Scanner scan = new Scanner(System.in);
//    private static UserServices userServices = new UserServices();
//    private static UserGameStatsServices statsServices = new UserGameStatsServices();


    public static void main(String[] args) {
        boolean running = true;
//        boolean loggedIn = false;

        System.out.println("GamerVitals - Player and Game Stats");

        while (running) {
            // Initial Menu

            System.out.print("[L]ogin, View [P]layers, View [G]ames, [Q]uit: ");

            String menuInput = scan.nextLine().toLowerCase().trim();

            switch (menuInput) {
                case "l":
                    // User Menu (Login, Create Account, Logout)
                    System.out.print("Do you have an account? [Y]es/[N]o: ");
                    String hasAccountInput = scan.nextLine().toLowerCase().trim();

                    if (hasAccountInput.equals("y")) {
                        System.out.print("Enter Username or Email: ");
                        String loginPrimary = scan.nextLine().toLowerCase().trim();

                        User user = getUserByUsername(loginPrimary);

                        if (user == null) {
                            user = getUserByEmail(loginPrimary);
                        }

                        if (user != null) {
                            System.out.print("Enter Password: ");
                            String loginPassword = scan.nextLine().toLowerCase().trim();

                            if (checkPassword(user, loginPassword)) {
                                System.out.println("Login Successful.");
//                                loggedIn = true;
                            } else {
                                System.out.println("Invalid Password. Redirecting to main menu..");
                            }
                        } else {
                            System.out.println("Username and/or Email not found. Redirecting to main menu..");
                        }
                        break;
                    } else {
                        if (hasAccountInput.equals("n")) {
                            System.out.println("Sign up: ");

                            System.out.println("Enter a username: ");
                            System.out.println("> ");
                            String newUserUsername = scan.nextLine().trim();

                            System.out.println("Enter email: ");
                            System.out.println("> ");
                            String newUserEmail = scan.nextLine().trim();

                            System.out.println("Enter a password: ");
                            System.out.println("> ");
                            String newUserPassword = scan.nextLine().trim();

                            System.out.println("Enter your country of residence: ");
                            System.out.println("> ");
                            String newUserCountry = scan.nextLine().trim();

                            addUser(newUserUsername, newUserEmail, newUserPassword, newUserCountry);
                            System.out.println("Account Created Successfully. ");
                        } else {
                            System.out.println("Invalid Input.");
                        }
                        break;
                    }

                case "p":
                    System.out.print("View Player by: [A]ll users, [ID], user[N]ame, [E]mail, [C]ountry: ");
                    String playerSearchInput = scan.nextLine().toLowerCase().trim();

                    switch (playerSearchInput) {
                        case "a" -> {listAllUsers(); break;}
                        case "id" -> {
                            System.out.println("Enter the user ID you'd like to search for: ");
                            System.out.println("> ");
                            int userId = scan.nextInt();
                            scan.nextLine();
                            getUserById(userId);
                        }
                        case "n" -> {
                            System.out.println("Enter the user's username you'd like to search for: ");
                            System.out.println("> ");
                            String username = scan.nextLine().trim();
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
                    break;

                case "g":
                    System.out.println("View Games by: [A]ll games, [ID], [T]itle, [G]enre, [P]ublisher, Plat[f]orm: ");
                    System.out.println("> ");
                    String gameSearchInput = scan.nextLine().toLowerCase().trim();

                    switch (gameSearchInput) {
                        case "a" -> {listAllGames(); break;}
                        case "id" -> {
                            System.out.println("Enter the game ID you'd like to search for: ");
                            System.out.println("> ");
                            int gameId = scan.nextInt();
                            scan.nextLine();
                            getGamebyId(gameId);
                        }
                        case "t" -> {
                            System.out.println("Enter the game title you'd like to search for: ");
                            System.out.println("> ");
                            String title = scan.nextLine();
                            getGamebyTitle(title);
                        }
                        case "g" -> {
                            System.out.println("Enter the game title you'd like to search for: ");
                            System.out.println("> ");
                            String title = scan.nextLine();
                            getGameGenre(title);
                        }
                        case "p" -> {
                            System.out.println("Enter the game title you'd like to search for: ");
                            System.out.println("> ");
                            String title = scan.nextLine();
                            getGamePublisher(title);
                        }
                        case "f" -> {
                            System.out.println("Enter the game title you'd like to search for: ");
                            System.out.println("> ");
                            String title = scan.nextLine();
                            getGamePlatform(title);
                        }
                        default -> System.out.println("Invalid Input.");
                    }
                    break;

                case "q":
                    System.out.println("Exiting application..");
                    running = false;
                    break;

                default:
                    System.out.println("Invalid input.");
            }
        }
        scan.close();
    }
}
