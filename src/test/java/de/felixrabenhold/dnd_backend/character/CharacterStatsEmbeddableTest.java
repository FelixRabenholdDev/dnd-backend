package de.felixrabenhold.dnd_backend.character;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CharacterStatsEmbeddableTest {

    @Test
    void modifierForAverageAttributeIsZero() {
        CharacterStatsEmbeddable stats = new CharacterStatsEmbeddable(10, 10, 10, 10, 10, 10);

        assertEquals(0, stats.getStrengthModifier());
    }

    @Test
    void modifierForHighAttributeRoundsDownCorrectly() {
        CharacterStatsEmbeddable stats = new CharacterStatsEmbeddable(17, 10, 10, 10, 10, 10);

        assertEquals(3, stats.getStrengthModifier());
    }

    @Test
    void modifierForLowOddAttributeRoundsDownTowardsNegativeInfinity() {
        CharacterStatsEmbeddable stats = new CharacterStatsEmbeddable(7, 10, 10, 10, 10, 10);

        assertEquals(-2, stats.getStrengthModifier());
    }
}
