package de.felixrabenhold.dnd_backend.character;

import de.felixrabenhold.dnd_backend.character.generation.GenerationMethod;
import de.felixrabenhold.dnd_backend.character.referencedata.BackgroundDefinition;
import de.felixrabenhold.dnd_backend.character.referencedata.CharacterClassDefinition;
import de.felixrabenhold.dnd_backend.character.referencedata.RaceDefinition;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlayerCharacterTest {

    private final RaceDefinition testRace = new RaceDefinition("Human", "Test", Set.of());
    private final CharacterClassDefinition testClass = new CharacterClassDefinition("Fighter", "Test", Set.of());
    private final BackgroundDefinition testBackground = new BackgroundDefinition(
            "Soldier", "Test", Set.of(Ability.STRENGTH, Ability.DEXTERITY, Ability.CONSTITUTION), Set.of()
    );

    private PlayerCharacter buildCharacter(int level) {
        return new PlayerCharacter(
                "Test Hero", testRace, testClass, testBackground, level,
                GenerationMethod.STANDARD_ARRAY,
                new CharacterStatsEmbeddable(10, 10, 10, 10, 10, 10),
                Map.of()
        );
    }

    @Test
    void proficiencyBonusAtLevelOneIsTwo() {
        assertEquals(2, buildCharacter(1).getProficiencyBonus());
    }

    @Test
    void proficiencyBonusIncreasesAtLevelFive() {
        assertEquals(3, buildCharacter(5).getProficiencyBonus());
    }

    @Test
    void proficiencyBonusAtLevelTwentyIsSix() {
        assertEquals(6, buildCharacter(20).getProficiencyBonus());
    }
}