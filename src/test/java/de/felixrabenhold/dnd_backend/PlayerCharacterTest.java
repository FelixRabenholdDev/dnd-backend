package de.felixrabenhold.dnd_backend;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlayerCharacterTest {

    @Test
    void proficiencyBonusAtLevelOneIsTwo() {
        PlayerCharacter character = new PlayerCharacter(
                "TestHero", "Fighter", "Human", 1,
                new CharacterStatsEmbeddable(10, 10, 10, 10, 10, 10)
        );

        assertEquals(2, character.getProficiencyBonus());
    }

    @Test
    void proficiencyBonusIncreasesAtLevelFive() {
        PlayerCharacter character = new PlayerCharacter(
                "TestHero", "Fighter", "Human", 5,
                new CharacterStatsEmbeddable(10, 10, 10, 10, 10, 10)
        );

        assertEquals(3, character.getProficiencyBonus());
    }

    @Test
    void proficiencyBonusAtLevelTwentyIsSix() {
        PlayerCharacter character = new PlayerCharacter(
                "TestHero", "Fighter", "Human", 20,
                new CharacterStatsEmbeddable(10, 10, 10, 10, 10, 10)
        );

        assertEquals(6, character.getProficiencyBonus());
    }
}
