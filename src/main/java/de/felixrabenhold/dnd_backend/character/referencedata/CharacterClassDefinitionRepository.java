package de.felixrabenhold.dnd_backend.character.referencedata;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CharacterClassDefinitionRepository extends JpaRepository<CharacterClassDefinition, Long> {
}