package de.felixrabenhold.dnd_backend;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PlayerCharacterRepository extends JpaRepository<PlayerCharacter, Long> {
    List<PlayerCharacter> findByOwner(AppUser owner);
    Optional<PlayerCharacter> findByIdAndOwner(Long id, AppUser owner);
}
