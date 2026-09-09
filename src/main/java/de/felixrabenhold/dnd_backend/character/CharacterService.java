package de.felixrabenhold.dnd_backend.character;

import de.felixrabenhold.dnd_backend.auth.AppUser;
import de.felixrabenhold.dnd_backend.auth.CurrentUserService;
import de.felixrabenhold.dnd_backend.character.referencedata.BackgroundDefinition;
import de.felixrabenhold.dnd_backend.character.referencedata.BackgroundDefinitionRepository;
import de.felixrabenhold.dnd_backend.character.referencedata.CharacterClassDefinition;
import de.felixrabenhold.dnd_backend.character.referencedata.CharacterClassDefinitionRepository;
import de.felixrabenhold.dnd_backend.character.referencedata.RaceDefinition;
import de.felixrabenhold.dnd_backend.character.referencedata.RaceDefinitionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CharacterService {

    private final PlayerCharacterRepository repository;
    private final CurrentUserService currentUserService;
    private final RaceDefinitionRepository raceRepository;
    private final CharacterClassDefinitionRepository classRepository;
    private final BackgroundDefinitionRepository backgroundRepository;

    public CharacterService(
            PlayerCharacterRepository repository,
            CurrentUserService currentUserService,
            RaceDefinitionRepository raceRepository,
            CharacterClassDefinitionRepository classRepository,
            BackgroundDefinitionRepository backgroundRepository
    ) {
        this.repository = repository;
        this.currentUserService = currentUserService;
        this.raceRepository = raceRepository;
        this.classRepository = classRepository;
        this.backgroundRepository = backgroundRepository;
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

    public PlayerCharacter createFromRequest(PlayerCharacterCreateRequest request) {
        AppUser currentUser = currentUserService.getCurrentUser();

        RaceDefinition race = raceRepository.findById(request.raceId())
                .orElseThrow(() -> new IllegalArgumentException("Unbekannte Rasse: " + request.raceId()));
        CharacterClassDefinition characterClass = classRepository.findById(request.classId())
                .orElseThrow(() -> new IllegalArgumentException("Unbekannte Klasse: " + request.classId()));
        BackgroundDefinition background = backgroundRepository.findById(request.backgroundId())
                .orElseThrow(() -> new IllegalArgumentException("Unbekannter Background: " + request.backgroundId()));

        PlayerCharacter character = new PlayerCharacter(
                request.name(), race, characterClass, background, request.level(),
                request.generationMethod(), request.stats(), request.backgroundBonuses()
        );
        character.setOwner(currentUser);

        return repository.save(character);
    }
}
