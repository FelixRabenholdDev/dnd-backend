package de.felixrabenhold.dnd_backend.character;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.felixrabenhold.dnd_backend.auth.AppUser;
import de.felixrabenhold.dnd_backend.character.generation.GenerationMethod;
import de.felixrabenhold.dnd_backend.character.referencedata.BackgroundDefinition;
import de.felixrabenhold.dnd_backend.character.referencedata.CharacterClassDefinition;
import de.felixrabenhold.dnd_backend.character.referencedata.RaceDefinition;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PlayerCharacterResponseTest {

    @Test
    void serializedResponseNeverContainsPasswordHash() throws Exception {
        AppUser owner = new AppUser("felix", "$2a$10$geheimerHashWertDerNieRausDarf");

        RaceDefinition race = new RaceDefinition("Half-Orc", "Test", Set.of());
        CharacterClassDefinition characterClass = new CharacterClassDefinition("Barbarian", "Test", Set.of());
        BackgroundDefinition background = new BackgroundDefinition(
                "Soldier", "Test", Set.of(Ability.STRENGTH, Ability.DEXTERITY, Ability.CONSTITUTION), Set.of()
        );

        PlayerCharacter character = new PlayerCharacter(
                "Grimjaw", race, characterClass, background, 2,
                GenerationMethod.POINT_BUY,
                new CharacterStatsEmbeddable(18, 12, 16, 8, 10, 9),
                Map.of()
        );
        character.setOwner(owner);

        PlayerCharacterResponse response = PlayerCharacterResponse.fromEntity(character);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(response);

        assertFalse(json.contains("passwordHash"));
        assertFalse(json.contains("geheimerHashWertDerNieRausDarf"));
        assertTrue(json.contains("\"ownerUsername\":\"felix\""));
    }
}