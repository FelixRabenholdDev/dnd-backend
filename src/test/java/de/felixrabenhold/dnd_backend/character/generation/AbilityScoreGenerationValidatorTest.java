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

class AbilityScoreGenerationValidatorTest {

    private final AbilityScoreGenerationValidator validator = new AbilityScoreGenerationValidator();

    private final RaceDefinition race = new RaceDefinition("TestRace", "Test", Set.of());
    private final CharacterClassDefinition characterClass = new CharacterClassDefinition("TestClass", "Test", Set.of());
    private final BackgroundDefinition background = new BackgroundDefinition(
            "TestBackground", "Test", Set.of(Ability.STRENGTH, Ability.DEXTERITY, Ability.CONSTITUTION), Set.of()
    );

    private PlayerCharacter buildCharacter(GenerationMethod method, CharacterStatsEmbeddable stats) {
        return new PlayerCharacter(
                "Test", race, characterClass, background, 1, method, stats, Map.of()
        );
    }

    private ConstraintValidatorContext mockContext() {
        return Mockito.mock(ConstraintValidatorContext.class, Answers.RETURNS_DEEP_STUBS);
    }

    @Test
    void standardArrayWithCorrectValuesIsValid() {
        PlayerCharacter character = buildCharacter(
                GenerationMethod.STANDARD_ARRAY,
                new CharacterStatsEmbeddable(15, 14, 13, 12, 10, 8)
        );
        assertTrue(validator.isValid(character, mockContext()));
    }

    @Test
    void standardArrayInAnyOrderIsValid() {
        PlayerCharacter character = buildCharacter(
                GenerationMethod.STANDARD_ARRAY,
                new CharacterStatsEmbeddable(8, 10, 12, 13, 14, 15)
        );
        assertTrue(validator.isValid(character, mockContext()));
    }

    @Test
    void standardArrayWithWrongValuesIsInvalid() {
        PlayerCharacter character = buildCharacter(
                GenerationMethod.STANDARD_ARRAY,
                new CharacterStatsEmbeddable(15, 15, 13, 12, 10, 8)
        );
        assertFalse(validator.isValid(character, mockContext()));
    }

    @Test
    void pointBuyExactlyAtBudgetIsValid() {
        PlayerCharacter character = buildCharacter(
                GenerationMethod.POINT_BUY,
                new CharacterStatsEmbeddable(15, 14, 13, 10, 10, 10)
        );
        assertTrue(validator.isValid(character, mockContext()));
    }

    @Test
    void pointBuyOverBudgetIsInvalid() {
        PlayerCharacter character = buildCharacter(
                GenerationMethod.POINT_BUY,
                new CharacterStatsEmbeddable(15, 15, 15, 15, 15, 15)
        );
        assertFalse(validator.isValid(character, mockContext()));
    }

    @Test
    void pointBuyWithValueAboveFifteenIsInvalid() {
        PlayerCharacter character = buildCharacter(
                GenerationMethod.POINT_BUY,
                new CharacterStatsEmbeddable(16, 8, 8, 8, 8, 8)
        );
        assertFalse(validator.isValid(character, mockContext()));
    }

    @Test
    void rolledWithinValidRangeIsValid() {
        PlayerCharacter character = buildCharacter(
                GenerationMethod.ROLLED,
                new CharacterStatsEmbeddable(18, 3, 12, 12, 12, 12)
        );
        assertTrue(validator.isValid(character, mockContext()));
    }

    @Test
    void rolledOutsideValidRangeIsInvalid() {
        PlayerCharacter character = buildCharacter(
                GenerationMethod.ROLLED,
                new CharacterStatsEmbeddable(19, 12, 12, 12, 12, 12)
        );
        assertFalse(validator.isValid(character, mockContext()));
    }
}