package de.felixrabenhold.dnd_backend.character;

import de.felixrabenhold.dnd_backend.auth.AppUser;
import de.felixrabenhold.dnd_backend.auth.AppUserRepository;
import de.felixrabenhold.dnd_backend.character.generation.GenerationMethod;
import de.felixrabenhold.dnd_backend.character.referencedata.BackgroundDefinition;
import de.felixrabenhold.dnd_backend.character.referencedata.BackgroundDefinitionRepository;
import de.felixrabenhold.dnd_backend.character.referencedata.CharacterClassDefinition;
import de.felixrabenhold.dnd_backend.character.referencedata.CharacterClassDefinitionRepository;
import de.felixrabenhold.dnd_backend.character.referencedata.RaceDefinition;
import de.felixrabenhold.dnd_backend.character.referencedata.RaceDefinitionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.postgresql.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Testcontainers
class PlayerCharacterRepositoryIntegrationTest {

    @Container
    @ServiceConnection(name = "postgres")
    static PostgreSQLContainer postgres = new PostgreSQLContainer("postgres:16");

    @Autowired
    private PlayerCharacterRepository repository;

    @Autowired
    private AppUserRepository userRepository;

    @Autowired
    private RaceDefinitionRepository raceRepository;

    @Autowired
    private CharacterClassDefinitionRepository classRepository;

    @Autowired
    private BackgroundDefinitionRepository backgroundRepository;

    @Test
    void savedCharacterCanBeRetrievedFromDatabase() {
        AppUser owner = userRepository.save(new AppUser("integrationtestuser", "irrelevanterHash"));
        RaceDefinition race = raceRepository.save(new RaceDefinition("IntegrationTestRace", "Test", Set.of()));
        CharacterClassDefinition characterClass = classRepository.save(new CharacterClassDefinition("IntegrationTestClass", "Test", Set.of()));
        BackgroundDefinition background = backgroundRepository.save(new BackgroundDefinition(
                "IntegrationTestBackground", "Test", Set.of(Ability.DEXTERITY, Ability.CONSTITUTION, Ability.INTELLIGENCE), Set.of()
        ));

        PlayerCharacter character = new PlayerCharacter(
                "Integration Test Hero", race, characterClass, background, 3,
                GenerationMethod.STANDARD_ARRAY,
                new CharacterStatsEmbeddable(15, 14, 13, 12, 10, 8),
                Map.of(Ability.DEXTERITY, 1, Ability.CONSTITUTION, 1, Ability.INTELLIGENCE, 1)
        );

        character.setOwner(owner);

        PlayerCharacter saved = repository.save(character);

        assertTrue(saved.getId() > 0);
        assertEquals("Integration Test Hero", repository.findById(saved.getId()).orElseThrow().getName());
    }
}