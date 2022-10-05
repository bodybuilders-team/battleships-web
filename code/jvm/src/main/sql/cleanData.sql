-- Script to clean up the database

TRUNCATE users CASCADE;
TRUNCATE players CASCADE;

TRUNCATE games CASCADE;
ALTER SEQUENCE games_id_seq RESTART WITH 1;
TRUNCATE gamestates CASCADE;
TRUNCATE gameconfigs CASCADE;

TRUNCATE shots CASCADE;

TRUNCATE shiptypes CASCADE;
TRUNCATE ships CASCADE;

TRUNCATE gameconfig_shiptypes CASCADE;
