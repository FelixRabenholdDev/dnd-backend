package de.felixrabenhold.dnd_backend.character;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/characters")
public class CharacterController {

    private final CharacterService characterService;

    public CharacterController(CharacterService characterService) {
        this.characterService = characterService;
    }

    @GetMapping
    public  List<PlayerCharacterResponse> getAllCharacters() {
        return characterService.findAll().stream()
                .map(PlayerCharacterResponse::fromEntity)
                .toList();
    }

    @GetMapping("/{id}")
    public PlayerCharacterResponse getCharacterById(@PathVariable Long id) {
        return PlayerCharacterResponse.fromEntity(characterService.findByIdForCurrentUser(id));
    }

    @GetMapping("/strong/{threshold}")
    public List<PlayerCharacterResponse> getStrongCharacters(@PathVariable int threshold) {
        return characterService.findStrongerThan(threshold).stream()
                .map(PlayerCharacterResponse::fromEntity)
                .toList();
    }

    @PostMapping
    public PlayerCharacterResponse createCharacter(@Valid @RequestBody PlayerCharacterCreateRequest request) {
        return PlayerCharacterResponse.fromEntity(characterService.createFromRequest(request));
    }
}