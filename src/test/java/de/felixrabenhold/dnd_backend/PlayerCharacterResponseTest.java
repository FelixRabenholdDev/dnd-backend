package de.felixrabenhold.dnd_backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PlayerCharacterResponseTest {

    @Test
    void serializedResponseNeverContainsPasswordHash() throws Exception {
        AppUser owner = new AppUser("felix", "$2a$10$geheimerHashWertDerNieRausDarf");

        PlayerCharacter character = new PlayerCharacter(
                "Grimjaw", "Barbarian", "Half-Orc", 2,
                new CharacterStatsEmbeddable(18, 12, 16, 8, 10, 9),
                owner
        );

        PlayerCharacterResponse response = PlayerCharacterResponse.fromEntity(character);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(response);

        assertFalse(json.contains("passwordHash"));
        assertFalse(json.contains("geheimerHashWertDerNieRausDarf"));
        assertTrue(json.contains("\"ownerUsername\":\"felix\""));
    }
}
