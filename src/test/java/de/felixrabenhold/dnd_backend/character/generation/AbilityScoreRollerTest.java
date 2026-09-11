package de.felixrabenhold.dnd_backend.character.generation;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AbilityScoreRollerTest {

    @Test
    void rollingProducesExactlySixScores() {
        List<RolledAbilityScore> scores = AbilityScoreRoller.rollSixScores();
        assertEquals(6, scores.size());
    }

    @RepeatedTest(20)
    void eachRolledScoreHasFourDiceAndCorrectTotal() {
        for (RolledAbilityScore score : AbilityScoreRoller.rollSixScores()) {
            assertEquals(4, score.rolls().size());

            int expectedDropped = score.rolls().stream().min(Integer::compareTo).orElseThrow();
            assertEquals(expectedDropped, score.droppedRoll());

            int expectedTotal = score.rolls().stream().mapToInt(Integer::intValue).sum() - expectedDropped;
            assertEquals(expectedTotal, score.total());

            assertTrue(score.total() >= 3 && score.total() <= 18);
        }
    }
}