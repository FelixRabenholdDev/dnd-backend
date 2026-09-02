package de.felixrabenhold.dnd_backend.config;

import java.time.Instant;
import java.util.Map;

public record ValidationErrorResponse(
        Instant timestamp,
        int status,
        String message,
        Map<String, String> fieldErrors
) {
}
