package de.felixrabenhold.dnd_backend.character.generation;

import de.felixrabenhold.dnd_backend.character.PlayerCharacter;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class AbilityScoreGenerationValidator implements ConstraintValidator<ValidAbilityScoreGeneration, PlayerCharacter> {

    private static final List<Integer> STANDARD_ARRAY = List.of(15, 14, 13, 12, 10, 8);

    private static final Map<Integer, Integer> POINT_BUY_COST = Map.of(
            8, 0, 9, 1, 10, 2, 11, 3, 12, 4, 13, 5, 14, 7, 15, 9
    );

    private static final int POINT_BUY_BUDGET = 27;

    @Override
    public boolean isValid(PlayerCharacter character, ConstraintValidatorContext context) {
        if (character.getGenerationMethod() == null || character.getStats() == null) {
            return true;
        }

        List<Integer> scores = character.getStats().asList();

        return switch (character.getGenerationMethod()) {
            case STANDARD_ARRAY -> isValidStandardArray(scores, context);
            case POINT_BUY -> isValidPointBuy(scores, context);
            case ROLLED -> isValidRolled(scores, context);
        };
    }

    private boolean isValidStandardArray(List<Integer> scores, ConstraintValidatorContext context) {
        List<Integer> sorted = new ArrayList<>(scores);
        sorted.sort(Collections.reverseOrder());

        if (!sorted.equals(STANDARD_ARRAY)) {
            addViolation(context, "Bei Standard Array müssen die Werte genau " + STANDARD_ARRAY + " sein (in beliebiger Zuordnung)");
            return false;
        }
        return true;
    }

    private boolean isValidPointBuy(List<Integer> scores, ConstraintValidatorContext context) {
        int totalCost = 0;

        for (int score : scores) {
            Integer cost = POINT_BUY_COST.get(score);
            if (cost == null) {
                addViolation(context, "Bei Point Buy müssen alle Attributswerte zwischen 8 und 15 liegen");
                return false;
            }
            totalCost += cost;
        }

        if (totalCost > POINT_BUY_BUDGET) {
            addViolation(context, "Point-Buy-Budget überschritten: " + totalCost + " von " + POINT_BUY_BUDGET + " Punkten verwendet");
            return false;
        }
        return true;
    }

    private boolean isValidRolled(List<Integer> scores, ConstraintValidatorContext context) {
        boolean allInRange = scores.stream().allMatch(score -> score >= 3 && score <= 18);

        if (!allInRange) {
            addViolation(context, "Gewürfelte Attributswerte müssen zwischen 3 und 18 liegen");
            return false;
        }
        return true;
    }

    private void addViolation(ConstraintValidatorContext context, String message) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message).addPropertyNode("stats").addConstraintViolation();
    }
}