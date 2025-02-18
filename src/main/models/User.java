package main.models;

public class User {
    private int userId;
    private String username;
    private String email;
    private String password;
    private String country;
    // Maybe allow choice between player, developer, recruiter, admin

    public User(int userId, String username, String email, String password, String country) {
        this.userId= userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.country = country;
    }

    public int getUserId() { return userId; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getCountry() { return country; }




}
