package main.services;

import main.models.Game;
import main.models.User;

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

    public void addGame(Game game) {
        System.out.println(game.getGameId() + ": " + game.getGameTitle() + " has been created.");
    }

    public Game getGamebyId(int gameId) {
        return gameMap.get(gameId);
    }

    public void listAllGames() {
        for (Game game : games) {
            System.out.println(game.getGameId() + ": " + game.getGameTitle());
        }
    }

    public void getGameGenre(Game game) {
        System.out.println(game.getGameTitle() + ": " + game.getGenre());
    }

    public void getGamePublisher(Game game) {
        System.out.println(game.getGameTitle() + ": " + game.getPublisher());
    }

    public void getGamePlatform(Game game) {
        System.out.println(game.getGameTitle() + ": " + game.getPlatform());
    }
}