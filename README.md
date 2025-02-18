# GamerVitals - BCCA UNIT 7 PROJECT (JAVA)

---

## About GamerVitals
GamerVitals is (currently) a command line application developed using Java and PostgreSQL.

GamerVitals allows users to create an account and monitor their stats, as well as others, across a variety of games.


### Tools Used:
 - IntelliJ (Oracle OpenJDK 23.0.1)
 - PgAdmin 4 (PostgreSQL)

---

## Database Structure and Query Ideas (initial draft)

- Table 1 [ USER_INFO ]
  - user_id (Unique - PK)
  - username
  - email
  - password
  - birthday
  - country
  - created_date

- Table 2 [ GAME_INFO ]
  - game_id (Unique - PK)
  - game_title
  - genre
  - publisher_developer
  - release_date
  - platform

- Table 3 [ USER_GAME_STATS ]
  - user_id, game_id (Composite - PK)
  - time_played
  - current_rank
  - peak_rank

1. SELECT username from country [DEMOGRAPHICS]
   - SELECT username from game_title
2. SELECT game_title from publisher_developer [GAME INFO]
   - SELECT game_title by release date
   - SELECT game_title by genre
   - SELECT game_title by platform
3. SELECT username, time_played by game (JOIN) [Player Engagement]
4. SELECT game_title, total_players(sum) (JOIN) [Game Popularity]
5. SELECT username, rank_info by game (JOIN) [Competitive Performance]