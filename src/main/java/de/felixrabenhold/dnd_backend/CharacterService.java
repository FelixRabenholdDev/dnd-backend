package de.felixrabenhold.dnd_backend;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CharacterService {

    private final PlayerCharacterRepository repository;

    public CharacterService(PlayerCharacterRepository repository) {
        this.repository = repository;
    }

    public List<PlayerCharacter> findAll() {
        return repository.findAll();
    }

    public List<PlayerCharacter> findStrongerThan(int strengthThreshold) {
        return repository.findAll().stream()
                .filter(c -> c.getStats().getStrength() >= strengthThreshold)
                .toList();
    }

    public PlayerCharacter save(PlayerCharacter character) {
        return repository.save(character);
    }
}
