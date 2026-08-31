package de.felixrabenhold.dnd_backend;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.postgresql.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

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

    @Test
    void savedCharacterCanBeRetrievedFromDatabase() {
        PlayerCharacter character = new PlayerCharacter(
                "Integration Test Hero", "Rogue", "Halfling", 3,
                new CharacterStatsEmbeddable(12, 18, 13, 10, 14, 11)
        );

        PlayerCharacter saved = repository.save(character);

        assertTrue(saved.getId() > 0);
        assertEquals("Integration Test Hero", repository.findById(saved.getId()).orElseThrow().getName());
    }
}
