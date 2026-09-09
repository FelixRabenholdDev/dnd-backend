package de.felixrabenhold.dnd_backend.character.generation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AbilityScoreGenerationValidator.class)
public @interface ValidAbilityScoreGeneration {
    String message() default "Attributswerte entsprechen nicht der gewählten Erstellungsmethode";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
