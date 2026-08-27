package de.felixrabenhold.dnd_backend;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "player_characters")
public class PlayerCharacter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String characterClass;
    private String race;
    private int level;

    @Embedded
    private CharacterStatsEmbeddable stats;

    protected PlayerCharacter() {
        //leer für Hibernate
    }

    public PlayerCharacter(String name, String characterClass, String race, int level, CharacterStatsEmbeddable stats) {
        this.name = name;
        this.characterClass = characterClass;
        this.race = race;
        this.level = level;
        this.stats = stats;
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
}
