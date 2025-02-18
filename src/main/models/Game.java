package main.models;

public class Game {
    private int gameId;
    private String gameTitle;
    private String genre;
    private String publisher;
    private String platform;

    public Game(int gameId, String gameTitle, String genre, String publisher, String platform) {
        this.gameId= gameId;
        this.gameTitle = gameTitle;
        this.genre = genre;
        this.publisher = publisher;
        this.platform = platform;
    }

    public int getGameId() { return gameId; }
    public String getGameTitle() { return gameTitle; }
    public String getGenre() { return genre; }
    public String getPublisher() { return publisher; }
    public String getPlatform() { return platform; }




}
