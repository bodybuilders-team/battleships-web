DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS players CASCADE;
DROP TABLE IF EXISTS games CASCADE;
DROP TABLE IF EXISTS gamestates CASCADE;
DROP TABLE IF EXISTS gameconfigs CASCADE;
DROP TABLE IF EXISTS shots CASCADE;
DROP TABLE IF EXISTS ships CASCADE;
DROP TABLE IF EXISTS shiptypes CASCADE;
DROP TABLE IF EXISTS gameconfig_shiptypes CASCADE;

CREATE TABLE users
(
    username        VARCHAR(20) PRIMARY KEY,
    hashed_password VARCHAR(512) NOT NULL,
    points          INT          NOT NULL
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
    id      SERIAL PRIMARY KEY,
    name    VARCHAR(20) NOT NULL,
    player1 VARCHAR(20) NOT NULL,
    player2 VARCHAR(20),
    state   INT         NOT NULL,
    config  INT         NOT NULL,

    FOREIGN KEY (player1, id) REFERENCES players (username, game_id),
    FOREIGN KEY (player2, id) REFERENCES players (username, game_id)
);

CREATE TABLE gamestates
(
    game_id INT PRIMARY KEY REFERENCES games (id),
    phase   VARCHAR(20) NOT NULL CHECK
        ( phase IN ('WAITING_FOR_PLAYERS', 'PLACING_SHIPS', 'IN_PROGRESS', 'FINISHED') ),
    round   INT,
    turn    VARCHAR(20),
    winner  VARCHAR(20),

    FOREIGN KEY (turn, game_id) REFERENCES players (username, game_id),
    FOREIGN KEY (winner, game_id) REFERENCES players (username, game_id)
);

CREATE TABLE gameconfigs
(
    game_id                   INT PRIMARY KEY REFERENCES games (id),
    grid_size                 INT NOT NULL,
    max_time_for_layout_phase INT NOT NULL,
    shots_per_round           INT NOT NULL,
    max_time_per_shot         INT NOT NULL
);

ALTER TABLE players
    ADD CONSTRAINT fk_game_id
        FOREIGN KEY (game_id)
            REFERENCES games (id);

ALTER TABLE games
    ADD CONSTRAINT fk_state
        FOREIGN KEY (state)
            REFERENCES gamestates (game_id),

    ADD CONSTRAINT fk_config
        FOREIGN KEY (config)
            REFERENCES gameconfig (game_id);

CREATE TABLE shots
(
    shot_number INT         NOT NULL,
    round       INT         NOT NULL,
    game_id     INT         NOT NULL REFERENCES games (id),
    player      VARCHAR(20) NOT NULL,
    row         INT         NOT NULL,
    col         INT         NOT NULL,
    result      VARCHAR(4)  NOT NULL CHECK (result IN ('HIT', 'MISS', 'SUNK')),

    FOREIGN KEY (player, game_id) REFERENCES players (username, game_id),
    PRIMARY KEY (shot_number, round, game_id, player)
);

CREATE TABLE shiptypes
(
    ship_name VARCHAR(10) PRIMARY KEY,
    size      INT NOT NULL,
    points    INT NOT NULL
);

CREATE TABLE gameconfig_shiptypes
(
    game_id   INT REFERENCES games (id),
    ship_name VARCHAR(10) REFERENCES shiptypes (ship_name),

    PRIMARY KEY (game_id, ship_name)
);

CREATE TABLE ships
(
    id          SERIAL PRIMARY KEY,
    type        VARCHAR(10) REFERENCES shiptypes (ship_name),
    row         INT         NOT NULL,
    col         CHAR        NOT NULL,
    orientation VARCHAR(10) NOT NULL CHECK ( orientation IN ('HORIZONTAL', 'VERTICAL') ),
    lives       INT         NOT NULL
);
