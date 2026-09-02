DELETE FROM player_characters;

ALTER TABLE player_characters
    ADD COLUMN owner_id BIGINT NOT NULL REFERENCES app_users(id);