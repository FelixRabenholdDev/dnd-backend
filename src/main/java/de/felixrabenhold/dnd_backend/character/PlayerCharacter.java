package de.felixrabenhold.dnd_backend.character;

import de.felixrabenhold.dnd_backend.auth.AppUser;
import de.felixrabenhold.dnd_backend.character.generation.GenerationMethod;
import de.felixrabenhold.dnd_backend.character.generation.ValidAbilityScoreGeneration;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@ValidAbilityScoreGeneration
@Entity
@Table(name = "player_characters")
public class PlayerCharacter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    private String characterClass;
    private String race;

    @Min(1)
    private int level;

    @NotNull
    @Valid
    @Embedded
    private CharacterStatsEmbeddable stats;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private AppUser owner;

    @NotNull
    @Enumerated(EnumType.STRING)
    private GenerationMethod generationMethod;

    protected PlayerCharacter() {
        //leer für Hibernate
    }

    public PlayerCharacter(String name, String characterClass, String race, int level, CharacterStatsEmbeddable stats, AppUser owner) {
        this.name = name;
        this.characterClass = characterClass;
        this.race = race;
        this.level = level;
        this.stats = stats;
        this.owner = owner;
    }

    public Long getId() { return id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCharacterClass() { return characterClass; }
    public void setCharacterClass(String characterClass) { this.characterClass = characterClass; }

    public String getRace() { return race; }
    public void setRace(String race) { this.race = race; }

    public int getLevel() { return level; }
    public void setLevel(int level) { this.level = level; }

    public CharacterStatsEmbeddable getStats() { return stats; }
    public void setStats(CharacterStatsEmbeddable stats) { this.stats = stats; }

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

    public GenerationMethod getGenerationMethod() { return generationMethod; }
    public void setGenerationMethod(GenerationMethod generationMethod) { this.generationMethod = generationMethod; }
}
