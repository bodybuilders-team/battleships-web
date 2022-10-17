-- Script to insert some data into the database

INSERT INTO users(username, email, hashed_password, points)
VALUES ('guest', 'guest@mail.com', '9f9fe9550947eeaa9e5d9df64145c80bf78f91725e75187468075518c09cbf14', 0),
       ('andre-j3sus', 'andre@mail.com', '9f9fe9550947eeaa9e5d9df64145c80bf78f91725e75187468075518c09cbf14', 0),
       ('nyckoka', 'nyck@mail.com', '9f9fe9550947eeaa9e5d9df64145c80bf78f91725e75187468075518c09cbf14', 0),
       ('devandrepascoa', 'andre@parcoa.org', '9f9fe9550947eeaa9e5d9df64145c80bf78f91725e75187468075518c09cbf14', 0);

INSERT INTO games(creator, name, grid_size, max_time_for_layout_phase, shots_per_round, max_time_per_round, phase,
                  phase_end_time, round, turn, winner)
VALUES (2, 'Game 1', 10, 60, 1, 10, 'WAITING_FOR_PLAYERS', now(), NULL, NULL, NULL),
       (3, 'Game 2', 16, 60, 2, 20, 'WAITING_FOR_PLAYERS', now(), NULL, NULL, NULL),
       (4, 'Game 3', 12, 60, 1, 30, 'WAITING_FOR_PLAYERS', now(), NULL, NULL, NULL);

INSERT INTO players(game, user_id, points)
VALUES (1, 2, 0),
       (2, 3, 0),
       (3, 4, 0);

