-- Script to create the database and tables

DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS players CASCADE;
DROP TABLE IF EXISTS games CASCADE;
DROP TABLE IF EXISTS shots CASCADE;
DROP TABLE IF EXISTS ships CASCADE;
DROP TABLE IF EXISTS ship_types CASCADE;

CREATE TABLE users
(
    id              SERIAL PRIMARY KEY,
    username        VARCHAR(50)  NOT NULL,
    email           VARCHAR(320) NOT NULL CHECK ( email ~ '^[A-Za-z0-9+_.-]+@(.+)$'),
    hashed_password VARCHAR(512) NOT NULL,
    points          INT          NOT NULL,

    UNIQUE (username, email)
);

CREATE TABLE games
(
    id                        SERIAL PRIMARY KEY,
    creator                   INT         NOT NULL REFERENCES users (id),
    name                      VARCHAR(50) NOT NULL,
    grid_size                 INT         NOT NULL,
    max_time_for_layout_phase INT         NOT NULL,
    shots_per_round           INT         NOT NULL,
    max_time_per_shot         INT         NOT NULL,
    phase                     VARCHAR(50) NOT NULL
        CHECK ( phase IN ('WAITING_FOR_PLAYERS', 'PLACING_SHIPS', 'IN_PROGRESS', 'FINISHED') ),
    phase_end_time            TIMESTAMP   NOT NULL, -- TODO check this name
    round                     INT,
    turn                      INT,
    winner                    INT
);

CREATE TABLE players
(
    id      SERIAL PRIMARY KEY,
    game    INT NOT NULL REFERENCES games (id),
    -- Has to be user_id because user is a reserved word
    user_id INT NOT NULL REFERENCES users (id),
    points  INT NOT NULL,

    UNIQUE (game, user_id)
);

ALTER TABLE games
    ADD CONSTRAINT turn_fk
        FOREIGN KEY (turn) REFERENCES players (id),
    ADD CONSTRAINT winner_fk
        FOREIGN KEY (winner) REFERENCES users (id);

CREATE TABLE shots
(
    id     SERIAL PRIMARY KEY,
    number INT        NOT NULL,
    round  INT        NOT NULL,
    game   INT        NOT NULL,
    player INT        NOT NULL REFERENCES players (id),
    row    INT        NOT NULL,
    col    INT        NOT NULL,
    result VARCHAR(4) NOT NULL CHECK (result IN ('HIT', 'MISS', 'SUNK')),

    UNIQUE (game, player, round, number)
);

CREATE TABLE ship_types
(
    id        SERIAL PRIMARY KEY,
    game      INT         NOT NULL REFERENCES games (id),
    ship_name VARCHAR(50) NOT NULL,
    quantity  INT         NOT NULL,
    size      INT         NOT NULL,
    points    INT         NOT NULL,

    UNIQUE (game, ship_name)
);

CREATE TABLE ships
(
    id          SERIAL PRIMARY KEY,
    player      INT         NOT NULL REFERENCES players (id),
    type        INT         NOT NULL REFERENCES ship_types (id),
    row         INT         NOT NULL,
    col         CHAR        NOT NULL,
    orientation VARCHAR(10) NOT NULL CHECK ( orientation IN ('HORIZONTAL', 'VERTICAL') ),
    lives       INT         NOT NULL
);
