package de.felixrabenhold.dnd_backend.character.referencedata;

import de.felixrabenhold.dnd_backend.character.Ability;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

import java.util.Set;

@Entity
@Table(name = "backgrounds")
public class BackgroundDefinition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    @ElementCollection(targetClass = Ability.class)
    @CollectionTable(name = "background_eligible_abilities", joinColumns = @JoinColumn(name = "background_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "ability")
    private Set<Ability> eligibleAbilities;

    @ElementCollection
    @CollectionTable(name = "background_traits", joinColumns = @JoinColumn(name = "background_id"))
    @Column(name = "trait")
    private Set<String> traits;

    protected BackgroundDefinition() {}

    public BackgroundDefinition(String name, String description, Set<Ability> eligibleAbilities, Set<String> traits) {
        this.name = name;
        this.description = description;
        this.eligibleAbilities = eligibleAbilities;
        this.traits = traits;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public Set<Ability> getEligibleAbilities() { return eligibleAbilities; }
    public Set<String> getTraits() { return traits; }
}