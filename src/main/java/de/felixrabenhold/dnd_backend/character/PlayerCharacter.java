package de.felixrabenhold.dnd_backend.character;

import de.felixrabenhold.dnd_backend.auth.AppUser;
import de.felixrabenhold.dnd_backend.character.generation.GenerationMethod;
import de.felixrabenhold.dnd_backend.character.generation.ValidAbilityScoreGeneration;
import de.felixrabenhold.dnd_backend.character.generation.ValidBackgroundBonus;
import de.felixrabenhold.dnd_backend.character.referencedata.BackgroundDefinition;
import de.felixrabenhold.dnd_backend.character.referencedata.CharacterClassDefinition;
import de.felixrabenhold.dnd_backend.character.referencedata.RaceDefinition;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.HashMap;
import java.util.Map;

@ValidAbilityScoreGeneration
@ValidBackgroundBonus
@Entity
@Table(name = "player_characters")
public class PlayerCharacter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @ManyToOne
    @JoinColumn(name = "race_id", nullable = false)
    private RaceDefinition race;

    @ManyToOne
    @JoinColumn(name = "class_id", nullable = false)
    private CharacterClassDefinition characterClass;

    @ManyToOne
    @JoinColumn(name = "background_id", nullable = false)
    private BackgroundDefinition background;

    @Min(1)
    private int level;

    @NotNull
    @Enumerated(EnumType.STRING)
    private GenerationMethod generationMethod;

    @NotNull
    @Valid
    @Embedded
    private CharacterStatsEmbeddable stats;

    @ElementCollection
    @CollectionTable(name = "player_character_background_bonuses", joinColumns = @JoinColumn(name = "player_character_id"))
    @MapKeyColumn(name = "ability")
    @MapKeyEnumerated(EnumType.STRING)
    @Column(name = "bonus_amount")
    private Map<Ability, Integer> backgroundBonuses = new HashMap<>();

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private AppUser owner;

    protected PlayerCharacter() {
        //leer für Hibernate
    }

    public PlayerCharacter(String name, RaceDefinition race, CharacterClassDefinition characterClass,
                           BackgroundDefinition background, int level, GenerationMethod generationMethod,
                           CharacterStatsEmbeddable stats, Map<Ability, Integer> backgroundBonuses) {
        this.name = name;
        this.race = race;
        this.characterClass = characterClass;
        this.background = background;
        this.level = level;
        this.generationMethod = generationMethod;
        this.stats = stats;
        this.backgroundBonuses = backgroundBonuses;
    }

    public Long getId() { return id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public RaceDefinition getRace() { return race; }
    public void setRace(RaceDefinition race) { this.race = race; }

    public CharacterClassDefinition getCharacterClass() { return characterClass; }
    public void setCharacterClass(CharacterClassDefinition characterClass) { this.characterClass = characterClass; }

    public BackgroundDefinition getBackground() { return background; }
    public void setBackground(BackgroundDefinition background) { this.background = background; }

    public int getLevel() { return level; }
    public void setLevel(int level) { this.level = level; }

    public GenerationMethod getGenerationMethod() { return generationMethod; }
    public void setGenerationMethod(GenerationMethod generationMethod) { this.generationMethod = generationMethod; }

    public CharacterStatsEmbeddable getStats() { return stats; }
    public void setStats(CharacterStatsEmbeddable stats) { this.stats = stats; }

    public Map<Ability, Integer> getBackgroundBonuses() { return backgroundBonuses; }
    public void setBackgroundBonuses(Map<Ability, Integer> backgroundBonuses) { this.backgroundBonuses = backgroundBonuses; }

    public AppUser getOwner() { return owner; }
    public void setOwner(AppUser owner) {this.owner = owner; }

    public int getProficiencyBonus() {
        return switch (level) {
            case 1, 2, 3, 4 -> 2;
            case 5, 6, 7, 8 -> 3;
            case 9, 10, 11, 12 -> 4;
            case 13, 14, 15, 16 -> 5;
            case 17, 18, 19, 20 -> 6;
            default -> throw new IllegalStateException("Unerwartetes Level: " + level);
        };
    }

    public int getBaseScore(Ability ability) {
        return switch (ability) {
            case STRENGTH -> stats.getStrength();
            case DEXTERITY -> stats.getDexterity();
            case CONSTITUTION -> stats.getConstitution();
            case INTELLIGENCE -> stats.getIntelligence();
            case WISDOM -> stats.getWisdom();
            case CHARISMA -> stats.getCharisma();
        };
    }

    public int getEffectiveScore(Ability ability) {
        return getBaseScore(ability) + backgroundBonuses.getOrDefault(ability, 0);
    }

    public int getEffectiveModifier(Ability ability) {
        return Math.floorDiv(getEffectiveScore(ability) - 10, 2);
    }
}
