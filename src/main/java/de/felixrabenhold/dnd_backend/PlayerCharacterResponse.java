package de.felixrabenhold.dnd_backend;

public record PlayerCharacterResponse(
        Long id,
        String name,
        String characterClass,
        String race,
        int level,
        CharacterStatsEmbeddable stats,
        int proficiencyBonus,
        String ownerUsername
) {
    public static PlayerCharacterResponse fromEntity(PlayerCharacter character) {
        return new PlayerCharacterResponse(
                character.getId(),
                character.getName(),
                character.getCharacterClass(),
                character.getRace(),
                character.getLevel(),
                character.getStats(),
                character.getProficiencyBonus(),
                character.getOwner().getUsername()
        );
    }
}
