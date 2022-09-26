DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS players CASCADE;
DROP TABLE IF EXISTS games CASCADE;
DROP TABLE IF EXISTS ships CASCADE;
DROP TABLE IF EXISTS shiptypes CASCADE;
DROP TABLE IF EXISTS shots CASCADE;

CREATE TABLE users
(
    username        VARCHAR(20) PRIMARY KEY,
    hashed_password VARCHAR(512) NOT NULL,
    points          INT       NOT NULL
);

CREATE TABLE players
(
    username VARCHAR(20) REFERENCES users (username),
    game_id  INT NOT NULL,
    points   INT NOT NULL,

    PRIMARY KEY (username, game_id)
);

CREATE TABLE games
(
    id                  SERIAL PRIMARY KEY,
    player1             VARCHAR(20),
    player2             VARCHAR(20),
    current_player_turn VARCHAR(20),
    winner              VARCHAR(20),
    current_round       INT         NOT NULL,
    state               VARCHAR(20) NOT NULL CHECK
        ( state IN ('WAITING_FOR_PLAYERS', 'PLACING_SHIPS', 'IN_PROGRESS', 'FINISHED') ),

    FOREIGN KEY (player1, id) REFERENCES players (username, game_id),
    FOREIGN KEY (player2, id) REFERENCES players (username, game_id),
    FOREIGN KEY (current_player_turn, id) REFERENCES players (username, game_id),
    FOREIGN KEY (winner, id) REFERENCES players (username, game_id)
);

ALTER TABLE players
    ADD CONSTRAINT fk_game_id
        FOREIGN KEY (game_id)
            REFERENCES games (id);

CREATE TABLE shots
(
    shot_number INT         NOT NULL,
    round       INT         NOT NULL,
    game_id     INT         NOT NULL REFERENCES games (id),
    row         INT         NOT NULL,
    col         INT         NOT NULL,
    result      VARCHAR(4)  NOT NULL CHECK (result IN ('HIT', 'MISS', 'SUNK')),
    player      VARCHAR(20) NOT NULL,

    FOREIGN KEY (player, game_id) REFERENCES players (username, game_id),
    PRIMARY KEY (shot_number, round, game_id, player)
);

CREATE TABLE shiptypes
(
    ship_name VARCHAR(10) PRIMARY KEY,
    size      INT NOT NULL,
    points    INT NOT NULL
);

CREATE TABLE ships
(
    id          SERIAL PRIMARY KEY,
    orientation VARCHAR(1) NOT NULL CHECK ( orientation IN ('H', 'V') ),
    row         INT        NOT NULL,
    col         CHAR       NOT NULL,
    type        VARCHAR(10) REFERENCES shiptypes (ship_name),
    lives       INT        NOT NULL
);
