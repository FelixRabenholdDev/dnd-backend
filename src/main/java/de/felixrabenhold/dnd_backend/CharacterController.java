package de.felixrabenhold.dnd_backend;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/characters")
public class CharacterController {

    private final CharacterService characterService;

    public CharacterController(CharacterService characterService) {
        this.characterService = characterService;
    }

    @GetMapping
    public  List<PlayerCharacter> getAllCharacters() {
        return characterService.findAll();
    }

    @GetMapping("/strong/{threshold}")
    public List<PlayerCharacter> getStrongCharacters(@PathVariable int threshold) {
        return characterService.findStrongerThan(threshold);
    }

    @PostMapping
    public PlayerCharacter createCharacter(@RequestBody PlayerCharacter character) {
        return characterService.save(character);
    }
}
