package de.felixrabenhold.dnd_backend.character.generation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class AbilityScoreRoller {

    public static List<RolledAbilityScore> rollSixScores() {
        List<RolledAbilityScore> results = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            results.add(rollOneScore());
        }
        return results;
    }

    private static RolledAbilityScore rollOneScore() {
        List<Integer> rolls = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            rolls.add(ThreadLocalRandom.current().nextInt(1, 7));
        }
        int dropped = Collections.min(rolls);
        int total = rolls.stream().mapToInt(Integer::intValue).sum() - dropped;

        return new RolledAbilityScore(rolls, dropped, total);
    }
}