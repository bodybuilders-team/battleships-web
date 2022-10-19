-- Script to create the database and tables

DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS refresh_tokens CASCADE;
DROP TABLE IF EXISTS players CASCADE;
DROP TABLE IF EXISTS games CASCADE;
DROP TABLE IF EXISTS shots CASCADE;
DROP TABLE IF EXISTS ships CASCADE;
DROP TABLE IF EXISTS ship_types CASCADE;

CREATE TABLE users
(
    id              SERIAL PRIMARY KEY,
    username        VARCHAR(40)  NOT NULL UNIQUE,
    email           VARCHAR(320) NOT NULL UNIQUE,
    password_hash VARCHAR(512) NOT NULL,
    points          INT          NOT NULL,

    CONSTRAINT username_length CHECK (char_length(username) >= 3),
    CONSTRAINT email_is_valid CHECK ( email ~ '^[A-Za-z0-9+_.-]+@(.+)$'),
    CONSTRAINT points_are_valid CHECK ( points >= 0 )
);

CREATE TABLE refresh_tokens
(
    id              SERIAL PRIMARY KEY,
    user_id         INT          NOT NULL REFERENCES users (id),
    token_hash      VARCHAR(512) NOT NULL,
    expiration_date TIMESTAMP    NOT NULL,

    UNIQUE (user_id, token_hash)
);

CREATE TABLE games
(
    id                        SERIAL PRIMARY KEY,
    creator                   INT         NOT NULL REFERENCES users (id),
    name                      VARCHAR(40) NOT NULL,
    grid_size                 INT         NOT NULL,
    max_time_for_layout_phase INT         NOT NULL,
    shots_per_round           INT         NOT NULL,
    max_time_per_round        INT         NOT NULL,
    phase                     VARCHAR(20) NOT NULL,
    phase_end_time            TIMESTAMP   NOT NULL,
    round                     INT,
    turn                      INT,
    winner                    INT,

    CONSTRAINT name_length CHECK (char_length(name) > 0),
    CONSTRAINT grid_size_is_valid CHECK ( grid_size >= 7 AND grid_size <= 18 ),
    CONSTRAINT max_time_for_layout_phase_is_valid
        CHECK ( max_time_for_layout_phase >= 10 AND max_time_for_layout_phase <= 120 ),
    CONSTRAINT shots_per_round_is_valid CHECK ( shots_per_round >= 1 AND shots_per_round <= 5 ),
    CONSTRAINT max_time_per_round_is_valid CHECK ( max_time_per_round >= 10 AND max_time_per_round <= 120 ),
    CONSTRAINT phase_is_valid CHECK ( phase IN ('WAITING_FOR_PLAYERS', 'GRID_LAYOUT', 'IN_PROGRESS', 'FINISHED') ),
    CONSTRAINT round_is_valid CHECK ( round >= 1 )
);

CREATE TABLE players
(
    id      SERIAL PRIMARY KEY,
    game    INT NOT NULL REFERENCES games (id),
    user_id INT NOT NULL REFERENCES users (id), -- Has to be user_id because user is a reserved word
    points  INT NOT NULL,

    UNIQUE (game, user_id),

    CONSTRAINT points_are_valid CHECK ( points >= 0 )
);

ALTER TABLE games
    ADD CONSTRAINT turn_fk
        FOREIGN KEY (turn) REFERENCES players (id),
    ADD CONSTRAINT winner_fk
        FOREIGN KEY (winner) REFERENCES users (id);

CREATE TABLE shots
(
    id     SERIAL PRIMARY KEY,
    round  INT        NOT NULL,
    game   INT        NOT NULL,
    player INT        NOT NULL REFERENCES players (id),
    col    CHAR       NOT NULL,
    row    INT        NOT NULL,
    result VARCHAR(4) NOT NULL,

    UNIQUE (game, player, round),

    CONSTRAINT round_is_valid CHECK ( round >= 1),
    CONSTRAINT col_is_valid CHECK ( col >= 'A' AND col <= 'R' ),
    CONSTRAINT row_is_valid CHECK ( row >= 1 AND row <= 18 ),
    CONSTRAINT result_is_valid CHECK ( result IN ('HIT', 'MISS', 'SUNK') )
);

CREATE TABLE ship_types
(
    id        SERIAL PRIMARY KEY,
    game      INT         NOT NULL REFERENCES games (id),
    ship_name VARCHAR(40) NOT NULL,
    quantity  INT         NOT NULL,
    size      INT         NOT NULL,
    points    INT         NOT NULL,

    UNIQUE (game, ship_name),

    CONSTRAINT ship_name_length CHECK (char_length(ship_name) > 0),
    CONSTRAINT quantity_is_valid CHECK ( quantity >= 0 AND quantity <= 10 ),
    CONSTRAINT size_is_valid CHECK ( size >= 1 AND size <= 7 ),
    CONSTRAINT points_are_valid CHECK ( points >= 1 AND points <= 100 )
);

CREATE TABLE ships
(
    id          SERIAL PRIMARY KEY,
    player      INT         NOT NULL REFERENCES players (id),
    type        INT         NOT NULL REFERENCES ship_types (id),
    col         CHAR        NOT NULL,
    row         INT         NOT NULL,
    orientation VARCHAR(10) NOT NULL,
    lives       INT         NOT NULL,

    CONSTRAINT col_is_valid CHECK ( col >= 'A' AND col <= 'R' ),
    CONSTRAINT row_is_valid CHECK ( row >= 0 AND row <= 18 ),
    CONSTRAINT orientation_is_valid CHECK ( orientation IN ('HORIZONTAL', 'VERTICAL') ),
    CONSTRAINT lives_are_valid CHECK ( lives >= 0 AND lives <= 7 )
);
