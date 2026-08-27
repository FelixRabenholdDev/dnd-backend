package de.felixrabenhold.dnd_backend;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CharacterService {

    private final List<CharacterStats> allCharacters = List.of(
            new CharacterStats(15, 14, 13, 12, 10, 8),
            new CharacterStats(8, 16, 12, 14, 13, 10),
            new CharacterStats(18, 10, 15, 8, 12, 14)
    );

    public List<CharacterStats> findAll() {
        return allCharacters;
    }

    public List<CharacterStats> findStrongerThan(int strengthThreshold) {
        return allCharacters.stream()
                .filter(c -> c.strength() >= strengthThreshold)
                .toList();
    }
}
