-- Script to insert some data into the database

INSERT INTO users(username, hashed_password, points)
VALUES ('guest', '9f9fe9550947eeaa9e5d9df64145c80bf78f91725e75187468075518c09cbf14', 0),
       ('andre-j3sus', '9f9fe9550947eeaa9e5d9df64145c80bf78f91725e75187468075518c09cbf14', 0),
       ('nyckoka', '9f9fe9550947eeaa9e5d9df64145c80bf78f91725e75187468075518c09cbf14', 0),
       ('devandrepascoa', '9f9fe9550947eeaa9e5d9df64145c80bf78f91725e75187468075518c09cbf14', 0);

INSERT INTO games(creator, name, grid_size, max_time_for_layout_phase, shots_per_round, max_time_per_shot, phase,
                  round, turn, winner)
VALUES (1, 'Game 1', 10, 60, 1, 10, 'WAITING_FOR_PLAYERS', 1, 1, NULL),
       (2, 'Game 2', 16, 60, 2, 20, 'WAITING_FOR_PLAYERS', 1, 1, NULL),
       (3, 'Game 3', 12, 60, 1, 30, 'WAITING_FOR_PLAYERS', 1, 1, NULL),
       (4, 'Game 4', 21, 60, 3, 15, 'WAITING_FOR_PLAYERS', 1, 1, NULL);

INSERT INTO players(game, user_id, points)
VALUES (1, 1, 0),
       (1, 2, 0),
       (2, 1, 0),
       (2, 2, 0),
       (3, 1, 0),
       (3, 2, 0),
       (4, 3, 0),
       (4, 4, 0);

