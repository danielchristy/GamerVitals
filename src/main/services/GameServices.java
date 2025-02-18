package main.services;

import main.models.Game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GameServices {
    public static List<Game> games = new ArrayList<>();
    public static HashMap<Integer, Game> gameMap = new HashMap<>();

    // temporary data
    static {
        games.add(new Game(1, "League of Legends", "MOBA", "Riot", "PC"));
        games.add(new Game(2, "Marvel Rivals", "Hero Shooter", "NetEase", "PC, PlayStation, Xbox"));

        for (Game game : games) {
            gameMap.put(game.getGameId(), game);
        }
    }

    public static void addGame(Game game) {
        System.out.println(game.getGameId() + ": " + game.getGameTitle() + " has been created.");
    }

    public static void listAllGames() {
        for (Game game : games) {
            System.out.println(game.getGameId() + ": " + game.getGameTitle());
        }
    }

    public static void getGamebyId(int gameId) {
        gameMap.get(gameId);
    }

    public static Game getGamebyTitle(String title) {
        for (Game game : games)
            if (game.getGameTitle().equals(title)) {
                return game;
            } else {
                System.out.println("Invalid game title, or does not exist.");
            }
        return null;
    }

    public static String getGameGenre(String title) {
        for (Game game : games)
            if (game.getGameTitle().equals(title)) {
                return game.getGenre();
            } else {
                System.out.println("Invalid game, or does not exist.");
            }
        return null;
    }

    public static String getGamePublisher(String title) {
        for (Game game : games)
            if (game.getGameTitle().equals(title)) {
                return game.getPublisher();
            } else {
                System.out.println("Invalid game, or does not exist.");
            }
        return null;
    }

    public static String getGamePlatform(String title) {
        for (Game game : games)
            if (game.getGameTitle().equals(title)) {
                return game.getPlatform();
            }
        return null;
    }
}