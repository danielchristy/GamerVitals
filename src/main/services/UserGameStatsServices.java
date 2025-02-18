package main.services;

import main.models.UserGameStats;
import main.models.User;
import main.models.Game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class UserGameStatsServices {
    public static List<User> users = UserServices.users;
    public static List<Game> games = GameServices.games;
    public static HashMap<Integer, User> userMap = UserServices.userMap;
    public static HashMap<Integer, Game> gameMap = GameServices.gameMap;

    public List<UserGameStats> userStats = new ArrayList<>();

    // User Stats
    public void addGameStatsforUser(int userId, int gameId, int timePlayed, String currentRank, String peakRank) {
        if (!userMap.containsKey(userId) || !gameMap.containsKey(gameId)) {
            System.out.println("Invalid user or game ID.");
            return;
        }
        userStats.add(new UserGameStats(userId, gameId, timePlayed, currentRank, peakRank));
    }

    public List<UserGameStats> getUserStats(int userId) {
        List<UserGameStats> result = new ArrayList<>();
        for (UserGameStats stats : userStats) {
            if (UserServices.userMap.containsKey(userId)) {
                result.add(stats);
            }
        }
        return result;
    }

    public String getUserRank(int userId) {
        List<String[]> rank = new ArrayList<>();
        for (UserGameStats stats : userStats) {
            if (stats.getUserId() == userId) {
                rank.add(new String[]{stats.getCurrentRank(), stats.getPeakRank()});
            }
        }
        return rank.toString();
    }

    public int getUserTotalTimePlayed(int userId) {
        int timePlayed = 0;
        for (UserGameStats stats : userStats) {
            if (UserServices.userMap.containsKey(userId)) {
                timePlayed += stats.getTimePlayed();
            }
        }
        return timePlayed;
    }

    // Game Stats
    public int getTotalPlayerCount(int gameId) {
        int players = 0;
        for (UserGameStats stats : userStats) {
            if (GameServices.gameMap.containsKey(gameId)) {
                players++;
            }
        }
        return players;
    }

    public int getTotalTimePlayed(int gameId) {
        int timePlayed = 0;
        for (UserGameStats stats: userStats) {
            if (GameServices.gameMap.containsKey(gameId)) {
                timePlayed += stats.getTimePlayed();
            }
        }
        return timePlayed;
    }

    public HashMap<String, Integer> getTotalPlayersByCountry(int gameId) {
        HashMap<String, Integer> demographics = new HashMap<>();
        for (UserGameStats stats : userStats) {
            if (GameServices.gameMap.containsKey(gameId)) {
                User user = userMap.get(stats.getUserId());
                String country = user.getCountry();
                demographics.put(country, demographics.getOrDefault(country, 0) + 1);
            }
        }
        return demographics;
    }

}
