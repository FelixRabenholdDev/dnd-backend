package de.felixrabenhold.dnd_backend;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@Embeddable
public class CharacterStatsEmbeddable {

    @Min(1)
    @Max(30)
    private int strength;

    @Min(1)
    @Max(30)
    private int dexterity;

    @Min(1)
    @Max(30)
    private int constitution;

    @Min(1)
    @Max(30)
    private int intelligence;

    @Min(1)
    @Max(30)
    private int wisdom;

    @Min(1)
    @Max(30)
    private int charisma;

    protected CharacterStatsEmbeddable() {
        //leer für Hibernate
    }

    public CharacterStatsEmbeddable(int strength, int dexterity, int constitution, int intelligence, int wisdom, int charisma) {
        this.strength = strength;
        this.dexterity = dexterity;
        this.constitution = constitution;
        this.intelligence = intelligence;
        this.wisdom = wisdom;
        this.charisma = charisma;
    }

    public int getStrength() { return strength; }
    public void setStrength(int strength) { this.strength = strength; }

    public int getDexterity() { return dexterity; }
    public void setDexterity(int dexterity) { this.dexterity = dexterity; }

    public int getConstitution() { return constitution; }
    public void setConstitution(int constitution) { this.constitution = constitution; }

    public int getIntelligence() { return intelligence; }
    public void setIntelligence(int intelligence) { this.intelligence = intelligence; }

    public int getWisdom() { return wisdom; }
    public void setWisdom(int wisdom) { this.wisdom = wisdom; }

    public int getCharisma() { return charisma; }
    public void setCharisma(int charisma) { this.charisma = charisma; }

    public int getStrengthModifier() {
        return calculateModifier(strength);
    }

    public int getDexterityModifier() {
        return calculateModifier(dexterity);
    }

    public int getConstitutionModifier() {
        return calculateModifier(constitution);
    }

    public int getIntelligenceModifier() {
        return calculateModifier(intelligence);
    }

    public int getWisdomModifier() {
        return calculateModifier(wisdom);
    }

    public int getCharismaModifier() {
        return calculateModifier(charisma);
    }

    private int calculateModifier(int attributeValue) {
        return Math.floorDiv(attributeValue - 10, 2);
    }
}
