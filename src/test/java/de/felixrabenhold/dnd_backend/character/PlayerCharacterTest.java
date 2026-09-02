package de.felixrabenhold.dnd_backend.character;

import de.felixrabenhold.dnd_backend.auth.AppUser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlayerCharacterTest {

    private final AppUser testOwner = new AppUser("testuser", "irrelevanterHash");

    @Test
    void proficiencyBonusAtLevelOneIsTwo() {
        PlayerCharacter character = new PlayerCharacter(
                "TestHero", "Fighter", "Human", 1,
                new CharacterStatsEmbeddable(10, 10, 10, 10, 10, 10),
                testOwner
        );

        assertEquals(2, character.getProficiencyBonus());
    }

    @Test
    void proficiencyBonusIncreasesAtLevelFive() {
        PlayerCharacter character = new PlayerCharacter(
                "TestHero", "Fighter", "Human", 5,
                new CharacterStatsEmbeddable(10, 10, 10, 10, 10, 10),
                testOwner
        );

        assertEquals(3, character.getProficiencyBonus());
    }

    @Test
    void proficiencyBonusAtLevelTwentyIsSix() {
        PlayerCharacter character = new PlayerCharacter(
                "TestHero", "Fighter", "Human", 20,
                new CharacterStatsEmbeddable(10, 10, 10, 10, 10, 10),
                testOwner
        );

        assertEquals(6, character.getProficiencyBonus());
    }
}
