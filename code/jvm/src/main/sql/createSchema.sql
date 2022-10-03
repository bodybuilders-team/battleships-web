DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS players CASCADE;
DROP TABLE IF EXISTS games CASCADE;
DROP TABLE IF EXISTS gamestates CASCADE;
DROP TABLE IF EXISTS gameconfigs CASCADE;
DROP TABLE IF EXISTS shots CASCADE;
DROP TABLE IF EXISTS ships CASCADE;
DROP TABLE IF EXISTS shiptypes CASCADE;
DROP TABLE IF EXISTS game_shiptypes CASCADE;

CREATE TABLE users
(
    username        VARCHAR(50) PRIMARY KEY,
    hashed_password VARCHAR(512) NOT NULL,
    points          INT          NOT NULL
);

CREATE TABLE games
(
    id                        SERIAL PRIMARY KEY,
    creator                   VARCHAR(50) REFERENCES users (username),
    name                      VARCHAR(50) NOT NULL,
    grid_size                 INT         NOT NULL,
    max_time_for_layout_phase INT         NOT NULL,
    shots_per_round           INT         NOT NULL,
    max_time_per_shot         INT         NOT NULL,
    phase                     VARCHAR(50) NOT NULL CHECK ( phase IN
                                                           ('WAITING_FOR_PLAYERS', 'PLACING_SHIPS', 'IN_PROGRESS',
                                                            'FINISHED') ),
    round                     INT         NOT NULL,
    turn                      VARCHAR(50),
    winner                    VARCHAR(50)
);

CREATE TABLE players
(
    id          SERIAL PRIMARY KEY,
    game_id  INT         NOT NULL REFERENCES games (id),
    username VARCHAR(50) NOT NULL REFERENCES users (username),
    points   INT         NOT NULL,

    PRIMARY KEY (username, game_id)
);

ALTER TABLE games
    ADD CONSTRAINT fk_turn
        FOREIGN KEY (turn, id)
            REFERENCES players (username, game_id),
    ADD CONSTRAINT fk_winner
        FOREIGN KEY (winner, id)
            REFERENCES players (username, game_id);

CREATE TABLE shots
(
    id          SERIAL PRIMARY KEY,
    shot_number INT         NOT NULL,
    round       INT         NOT NULL,
    game_id     INT         NOT NULL,
    player      VARCHAR(50) NOT NULL,
    row         INT         NOT NULL,
    col         INT         NOT NULL,
    result      VARCHAR(4)  NOT NULL CHECK (result IN ('HIT', 'MISS', 'SUNK')),

    FOREIGN KEY (player, game_id) REFERENCES players (username, game_id),
    PRIMARY KEY (shot_number, round, game_id, player)
);

CREATE TABLE game_shiptypes
(
    id          SERIAL PRIMARY KEY,
    game_id   INT         NOT NULL REFERENCES games (id),
    ship_name VARCHAR(50) NOT NULL,
    quantity  INT         NOT NULL,
    size      INT         NOT NULL,
    points    INT         NOT NULL,

    PRIMARY KEY (game_id, ship_name)
);

CREATE TABLE ships
(
    id          SERIAL PRIMARY KEY,
    game_id     INT         NOT NULL,
    player      VARCHAR(50) NOT NULL,
    ship_name   VARCHAR(50) NOT NULL,
    row         INT         NOT NULL,
    col         CHAR        NOT NULL,
    orientation VARCHAR(10) NOT NULL CHECK ( orientation IN ('HORIZONTAL', 'VERTICAL') ),
    lives       INT         NOT NULL,

    FOREIGN KEY (game_id, ship_name) REFERENCES game_shiptypes (game_id, ship_name),
    FOREIGN KEY (game_id, player) REFERENCES players (game_id, username)
);
