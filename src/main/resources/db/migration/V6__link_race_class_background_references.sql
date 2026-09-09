DELETE FROM player_characters;

ALTER TABLE player_characters DROP COLUMN race;
ALTER TABLE player_characters DROP COLUMN character_class;

ALTER TABLE player_characters ADD COLUMN race_id BIGINT NOT NULL REFERENCES races(id);
ALTER TABLE player_characters ADD COLUMN class_id BIGINT NOT NULL REFERENCES character_classes(id);
ALTER TABLE player_characters ADD COLUMN background_id BIGINT NOT NULL REFERENCES backgrounds(id);

CREATE TABLE player_character_background_bonuses (
                                                     player_character_id BIGINT NOT NULL REFERENCES player_characters(id),
                                                     ability VARCHAR(50) NOT NULL,
                                                     bonus_amount INTEGER NOT NULL
);