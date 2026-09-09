package de.felixrabenhold.dnd_backend.character;

public record EffectiveAbilityScores(
        int strength, int strengthModifier,
        int dexterity, int dexterityModifier,
        int constitution, int constitutionModifier,
        int intelligence, int intelligenceModifier,
        int wisdom, int wisdomModifier,
        int charisma, int charismaModifier
) {
    public static EffectiveAbilityScores fromCharacter(PlayerCharacter character) {
        return new EffectiveAbilityScores(
                character.getEffectiveScore(Ability.STRENGTH), character.getEffectiveModifier(Ability.STRENGTH),
                character.getEffectiveScore(Ability.DEXTERITY), character.getEffectiveModifier(Ability.DEXTERITY),
                character.getEffectiveScore(Ability.CONSTITUTION), character.getEffectiveModifier(Ability.CONSTITUTION),
                character.getEffectiveScore(Ability.INTELLIGENCE), character.getEffectiveModifier(Ability.INTELLIGENCE),
                character.getEffectiveScore(Ability.WISDOM), character.getEffectiveModifier(Ability.WISDOM),
                character.getEffectiveScore(Ability.CHARISMA), character.getEffectiveModifier(Ability.CHARISMA)
        );
    }
}