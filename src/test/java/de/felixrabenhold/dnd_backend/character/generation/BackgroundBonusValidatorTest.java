package de.felixrabenhold.dnd_backend.character.generation;

import de.felixrabenhold.dnd_backend.character.Ability;
import de.felixrabenhold.dnd_backend.character.CharacterStatsEmbeddable;
import de.felixrabenhold.dnd_backend.character.PlayerCharacter;
import de.felixrabenhold.dnd_backend.character.referencedata.BackgroundDefinition;
import de.felixrabenhold.dnd_backend.character.referencedata.CharacterClassDefinition;
import de.felixrabenhold.dnd_backend.character.referencedata.RaceDefinition;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.mockito.Mockito;

import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BackgroundBonusValidatorTest {

    private final BackgroundBonusValidator validator = new BackgroundBonusValidator();

    private final RaceDefinition race = new RaceDefinition("TestRace", "Test", Set.of());
    private final CharacterClassDefinition characterClass = new CharacterClassDefinition("TestClass", "Test", Set.of());
    private final BackgroundDefinition background = new BackgroundDefinition(
            "TestBackground", "Test", Set.of(Ability.STRENGTH, Ability.DEXTERITY, Ability.CONSTITUTION), Set.of()
    );

    private final CharacterStatsEmbeddable stats = new CharacterStatsEmbeddable(10, 10, 10, 10, 10, 10);

    private PlayerCharacter buildCharacter(Map<Ability, Integer> bonuses) {
        return new PlayerCharacter(
                "Test", race, characterClass, background, 1, GenerationMethod.STANDARD_ARRAY, stats, bonuses
        );
    }

    private ConstraintValidatorContext mockContext() {
        return Mockito.mock(ConstraintValidatorContext.class, Answers.RETURNS_DEEP_STUBS);
    }

    @Test
    void twoPlusOneDistributionIsValid() {
        PlayerCharacter character = buildCharacter(Map.of(Ability.STRENGTH, 2, Ability.DEXTERITY, 1));
        assertTrue(validator.isValid(character, mockContext()));
    }

    @Test
    void onePlusOnePlusOneDistributionIsValid() {
        PlayerCharacter character = buildCharacter(
                Map.of(Ability.STRENGTH, 1, Ability.DEXTERITY, 1, Ability.CONSTITUTION, 1)
        );
        assertTrue(validator.isValid(character, mockContext()));
    }

    @Test
    void twoPlusTwoDistributionIsInvalid() {
        PlayerCharacter character = buildCharacter(Map.of(Ability.STRENGTH, 2, Ability.DEXTERITY, 2));
        assertFalse(validator.isValid(character, mockContext()));
    }

    @Test
    void bonusOnIneligibleAbilityIsInvalid() {
        PlayerCharacter character = buildCharacter(Map.of(Ability.WISDOM, 2, Ability.STRENGTH, 1));
        assertFalse(validator.isValid(character, mockContext()));
    }
}