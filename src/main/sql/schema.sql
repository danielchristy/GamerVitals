CREATE SCHEMA vitals;

CREATE TYPE schema.user_role AS ENUM ('player', 'developer', 'recruiter', 'admin');

CREATE TABLE schema.users(
    user_id SERIAL PRIMARY KEY,
    email VARCHAR(100) UNIQUE NOT NULL,
    username VARCHAR(50) UNIQUE NOT NULL,
    country VARCHAR(100) NOT NULL,
    role user_role NOT NULL,
    password VARCHAR(50) UNIQUE NOT NULL,
    created_on TIMESTAMP NOT NULL
);

CREATE TABLE schema.games(
    game_id SERIAL PRIMARY KEY,
    game_title VARCHAR(100) UNIQUE NOT NULL,
    genre VARCHAR(100) NOT NULL,
    publisher VARCHAR(100) NOT NULL,
    price MONEY NOT NULL,
    in_game_purchases BOOLEAN DEFAULT FALSE NOT NULL,
    release_date DATE NOT NULL,
    created_on TIMESTAMP NOT NULL
);

CREATE TABLE schema.user_stats(
    user_id INT NOT NULL,
    game_id INT NOT NULL,
    time_played INT NOT NULL,
    start_rank VARCHAR(50) NOT NULL,
    current_rank VARCHAR(50) NOT NULL,
    peak_rank VARCHAR(50) NOT NULL,
    PRIMARY KEY (user_id, game_id),
    FOREIGN KEY (user_id) REFERENCES schema.users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (game_id) REFERENCES schema.game(game_id) ON DELETE CASCADE
);