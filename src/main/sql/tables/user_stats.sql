CREATE TABLE IF NOT EXISTS vitals.user_stats(
    user_id INT NOT NULL,
    game_id INT NOT NULL,
    time_played INT NOT NULL,
    start_rank VARCHAR(50),
    current_rank VARCHAR(50),
    peak_rank VARCHAR(50),
    PRIMARY KEY (user_id, game_id),
    FOREIGN KEY (user_id) REFERENCES vitals.users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (game_id) REFERENCES vitals.games(game_id) ON DELETE CASCADE
);