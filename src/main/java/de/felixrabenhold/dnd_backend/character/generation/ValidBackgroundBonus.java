package de.felixrabenhold.dnd_backend.character.generation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = BackgroundBonusValidator.class)
public @interface ValidBackgroundBonus {
    String message() default "Ungültige Verteilung der Background-Boni";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}