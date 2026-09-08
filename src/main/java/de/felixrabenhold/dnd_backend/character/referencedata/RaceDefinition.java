package de.felixrabenhold.dnd_backend.character.referencedata;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

import java.util.Set;

@Entity
@Table(name = "races")
public class RaceDefinition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    @ElementCollection
    @CollectionTable(name = "race_traits", joinColumns = @JoinColumn(name = "race_id"))
    @Column(name = "trait")
    private Set<String> traits;

    protected RaceDefinition() {}

    public RaceDefinition(String name, String description, Set<String> traits) {
        this.name = name;
        this.description = description;
        this.traits = traits;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public Set<String> getTraits() { return traits; }
}