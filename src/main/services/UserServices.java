package main.services;

import main.models.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserServices {
    public static List<User> users = new ArrayList<>();
    public static HashMap<Integer, User> userMap = new HashMap<>();

    // temporary data
    static {
        users.add(new User(1, "CelloPro", "cellopro@email.com", "gus", "USA"));
        users.add(new User(2, "Tedressel", "tedressel@email.com", "donnie", "Canada"));
        users.add(new User(3, "Brownii", "brownii@email.com", "brownii", "Mexico"));

        for (User user : users) {
            userMap.put(user.getUserId(), user);
        }

    }
    public void addUser(User user) {
        users.add(user);
        userMap.put(user.getUserId(), user);

        System.out.println(user.getUserId() + ": " + user.getUsername() + " has been created.");
    }

    public static String getUserPassword(String password) {
        for (User user : users) {
            if (user.getPassword().equals(password)) {
                return user.getPassword();
            } else {
                System.out.println("Invalid Password.");
            }
        }
        return null;
    }

    public static void listAllUsers() {
        for (User user : users) {
            System.out.println(user.getUserId() + ": " + user.getUsername());
        }
    }

    public static void getUserById(int userId) {
        userMap.get(userId);
    }

    public static User getUserByUsername(String username) {
        for (User user : users)
            if (user.getUsername().equals(username)) {
                return user;
            } else {
                System.out.println("Invalid User ID, or does not exist.");
            }
        return null;
    }

    public static User getUserByEmail(String email) {
        for (User user : users)
            if (user.getEmail().equals(email)) {
                return user;
            } else {
                System.out.println("Invalid User email, or does not exist.");
            }
        return null;
    }

    public static String getUserCountry(int userId) {
        for (User user : users)
            if (user.getUserId() == userId) {
                return user.getCountry();
            } else {
                System.out.println("Invalid User ID, or does not exist.");
            }
        return "";
    }


}