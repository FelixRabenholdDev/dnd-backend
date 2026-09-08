package de.felixrabenhold.dnd_backend.character.referencedata;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/reference-data")
public class ReferenceDataController {

    private final RaceDefinitionRepository raceRepository;
    private final CharacterClassDefinitionRepository classRepository;
    private final BackgroundDefinitionRepository backgroundRepository;

    public ReferenceDataController(
            RaceDefinitionRepository raceRepository,
            CharacterClassDefinitionRepository classRepository,
            BackgroundDefinitionRepository backgroundRepository
    ) {
        this.raceRepository = raceRepository;
        this.classRepository = classRepository;
        this.backgroundRepository = backgroundRepository;
    }

    @GetMapping("/races")
    public List<RaceDefinition> getRaces() {
        return raceRepository.findAll();
    }

    @GetMapping("/classes")
    public List<CharacterClassDefinition> getClasses() {
        return classRepository.findAll();
    }

    @GetMapping("/backgrounds")
    public List<BackgroundDefinition> getBackgrounds() {
        return backgroundRepository.findAll();
    }
}