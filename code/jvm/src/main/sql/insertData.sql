INSERT INTO users(username, hashed_password, points)
VALUES ('guest', '9f9fe9550947eeaa9e5d9df64145c80bf78f91725e75187468075518c09cbf14', 0);

INSERT INTO games(name, grid_size, max_time_for_layout_phase, shots_per_round, max_time_per_shot, phase, round, turn,
                  winner)
VALUES ('game1', 10, 60, 1, 60, 'WAITING_FOR_PLAYERS', 0, NULL, NULL);

INSERT INTO players(username, game_id, points)
VALUES ('guest', 1, 0);

INSERT INTO game_shiptypes(game_id, ship_name, size, points)
VALUES (1, 'Carrier', 5, 5), (1, 'Battleship', 4, 4), (1, 'Cruiser', 3, 3), (1, 'Submarine', 3, 3), (1, 'Destroyer', 2, 2);
