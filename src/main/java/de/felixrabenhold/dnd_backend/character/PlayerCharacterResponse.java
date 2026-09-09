package de.felixrabenhold.dnd_backend.character;

import de.felixrabenhold.dnd_backend.character.generation.GenerationMethod;

public record PlayerCharacterResponse(
        Long id,
        String name,
        String raceName,
        String className,
        String backgroundName,
        int level,
        GenerationMethod generationMethod,
        CharacterStatsEmbeddable baseStats,
        EffectiveAbilityScores effectiveStats,
        int proficiencyBonus,
        String ownerUsername
) {
    public static PlayerCharacterResponse fromEntity(PlayerCharacter character) {
        return new PlayerCharacterResponse(
                character.getId(),
                character.getName(),
                character.getRace().getName(),
                character.getCharacterClass().getName(),
                character.getBackground().getName(),
                character.getLevel(),
                character.getGenerationMethod(),
                character.getStats(),
                EffectiveAbilityScores.fromCharacter(character),
                character.getProficiencyBonus(),
                character.getOwner().getUsername()
        );
    }
}