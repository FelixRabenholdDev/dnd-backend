package de.felixrabenhold.dnd_backend.character.generation;

import de.felixrabenhold.dnd_backend.character.Ability;
import de.felixrabenhold.dnd_backend.character.PlayerCharacter;
import de.felixrabenhold.dnd_backend.character.referencedata.BackgroundDefinition;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class BackgroundBonusValidator implements ConstraintValidator<ValidBackgroundBonus, PlayerCharacter> {

    @Override
    public boolean isValid(PlayerCharacter character, ConstraintValidatorContext context) {
        BackgroundDefinition background = character.getBackground();
        Map<Ability, Integer> bonuses = character.getBackgroundBonuses();

        if (background == null || bonuses == null) {
            return true;
        }

        if (!background.getEligibleAbilities().containsAll(bonuses.keySet())) {
            addViolation(context, "Boni dürfen nur auf die für diesen Background zulässigen Attribute verteilt werden: "
                    + background.getEligibleAbilities());
            return false;
        }

        List<Integer> sortedValues = bonuses.values().stream()
                .sorted(Collections.reverseOrder())
                .toList();

        boolean validDistribution =
                (bonuses.size() == 2 && sortedValues.equals(List.of(2, 1))) ||
                        (bonuses.size() == 3 && sortedValues.equals(List.of(1, 1, 1)));

        if (!validDistribution) {
            addViolation(context, "Background-Boni müssen entweder +2/+1 auf zwei Attribute oder +1/+1/+1 auf drei Attribute verteilt werden");
            return false;
        }

        return true;
    }

    private void addViolation(ConstraintValidatorContext context, String message) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message)
                .addPropertyNode("backgroundBonuses")
                .addConstraintViolation();
    }
}