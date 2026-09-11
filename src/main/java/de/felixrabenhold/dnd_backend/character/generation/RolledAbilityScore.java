package de.felixrabenhold.dnd_backend.character.generation;

import java.util.List;

public record RolledAbilityScore(List<Integer> rolls, int droppedRoll, int total) {}