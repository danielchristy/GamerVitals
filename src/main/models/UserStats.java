package main.models;

public class UserStats {
    private int userId;
    private int gameId;
    private int timePlayed;
    private String currentRank;
    private String peakRank;

    public UserStats(int userId, int gameId, int timePlayed, String currentRank, String peakRank) {
        this.userId = userId;
        this.gameId = gameId;
        this.timePlayed = timePlayed;
        this.currentRank = currentRank;
        this.peakRank = peakRank;
    }

    public int getUserId() { return userId; }
    public int getGameId() { return gameId; }
    public int getTimePlayed() { return timePlayed; }
    public String getCurrentRank() { return currentRank; }
    public String getPeakRank() { return peakRank; }




}
