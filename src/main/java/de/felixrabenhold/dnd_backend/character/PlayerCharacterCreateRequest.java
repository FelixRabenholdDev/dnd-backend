package de.felixrabenhold.dnd_backend.character;

import de.felixrabenhold.dnd_backend.character.generation.GenerationMethod;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Map;

public record PlayerCharacterCreateRequest(
        @NotBlank String name,
        @NotNull Long raceId,
        @NotNull Long classId,
        @NotNull Long backgroundId,
        @Min(1) int level,
        @NotNull GenerationMethod generationMethod,
        @NotNull @Valid CharacterStatsEmbeddable stats,
        Map<Ability, Integer> backgroundBonuses
) {}