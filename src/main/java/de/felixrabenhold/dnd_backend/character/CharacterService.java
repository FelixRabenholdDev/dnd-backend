package de.felixrabenhold.dnd_backend.character;

import de.felixrabenhold.dnd_backend.auth.AppUser;
import de.felixrabenhold.dnd_backend.auth.CurrentUserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CharacterService {

    private final PlayerCharacterRepository repository;
    private final CurrentUserService currentUserService;

    public CharacterService(PlayerCharacterRepository repository, CurrentUserService currentUserService) {
        this.repository = repository;
        this.currentUserService = currentUserService;
    }

    public List<PlayerCharacter> findAll() {
        AppUser currentUser = currentUserService.getCurrentUser();
        return repository.findByOwner(currentUser);
    }

    public List<PlayerCharacter> findStrongerThan(int strengthThreshold) {
        AppUser currentUser = currentUserService.getCurrentUser();
        return repository.findByOwner(currentUser).stream()
                .filter(c -> c.getStats().getStrength() >= strengthThreshold)
                .toList();
    }

    public PlayerCharacter save(PlayerCharacter character) {
        AppUser currentUser = currentUserService.getCurrentUser();
        character.setOwner(currentUser);
        return repository.save(character);
    }

    public PlayerCharacter findByIdForCurrentUser(Long id) {
        AppUser currentUser = currentUserService.getCurrentUser();
        return repository.findByIdAndOwner(id, currentUser)
                .orElseThrow(() -> new IllegalArgumentException("Charakter nicht gefunden"));
    }
}
