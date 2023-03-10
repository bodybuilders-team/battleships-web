-- Script to clean up the database

TRUNCATE users CASCADE;
ALTER SEQUENCE users_id_seq RESTART WITH 1;

TRUNCATE refresh_tokens CASCADE;
ALTER SEQUENCE refresh_tokens_id_seq RESTART WITH 1;

TRUNCATE revoked_access_tokens CASCADE;
ALTER SEQUENCE revoked_access_tokens_id_seq RESTART WITH 1;

TRUNCATE players CASCADE;
ALTER SEQUENCE players_id_seq RESTART WITH 1;

TRUNCATE games CASCADE;
ALTER SEQUENCE games_id_seq RESTART WITH 1;

TRUNCATE shots CASCADE;
ALTER SEQUENCE shots_id_seq RESTART WITH 1;

TRUNCATE ship_types CASCADE;
ALTER SEQUENCE ship_types_id_seq RESTART WITH 1;

TRUNCATE ships CASCADE;
ALTER SEQUENCE ships_id_seq RESTART WITH 1;

TRUNCATE refresh_tokens CASCADE;
ALTER SEQUENCE refresh_tokens_id_seq RESTART WITH 1;
