# dnd-backend

Spring-Boot-API für die Verwaltung von D&D-5e-(2024)-Charakteren — Teil des
[pnp-character-manager](https://github.com/FelixRabenholdDev/pnp-character-manager)-Projekts.

## Tech-Stack

- Java 21 (LTS)
- Spring Boot 4
- Spring Data JPA + PostgreSQL
- Flyway (Datenbankmigrationen)
- Spring Security + JWT (zustandslose Authentifizierung)
- springdoc-openapi (interaktive API-Dokumentation)
- JUnit 5 + Testcontainers
- Mockito (Unit-Tests für isolierte Validierungslogik)

## Voraussetzungen

- Java 21
- Maven (oder der mitgelieferte Wrapper `./mvnw`)
- Docker (für die lokale PostgreSQL-Instanz)

## Lokal starten

1. PostgreSQL-Container starten:
```bash
   docker run --name dnd-postgres-dev \
     -e POSTGRES_DB=dnd_characters \
     -e POSTGRES_USER=dnd_dev \
     -e POSTGRES_PASSWORD=devpassword \
     -p 5432:5432 \
     -d postgres:16
```
*(Beim erneuten Start, falls der Container schon existiert: `docker start dnd-postgres-dev`)*

2. Anwendung starten:
```bash
   ./mvnw spring-boot:run
```

Die API läuft danach unter `http://localhost:8080`.

## API-Dokumentation

Interaktive Swagger-UI, sobald die Anwendung läuft:

http://localhost:8080/swagger-ui.html


## Tests ausführen

```bash
./mvnw test
```

Integrationstests nutzen Testcontainers und benötigen eine laufende Docker-Umgebung — es wird
automatisch eine temporäre, isolierte PostgreSQL-Instanz für die Testdauer gestartet.

## Wichtige Endpoints

| Methode | Pfad | Beschreibung | Auth nötig |
|---|---|---|---|
| POST | `/api/auth/register` | Neuen Nutzer registrieren | Nein |
| POST | `/api/auth/login` | Einloggen, JWT erhalten | Nein |
| GET | `/api/characters` | Eigene Charaktere auflisten | Ja |
| GET | `/api/characters/{id}` | Einzelnen Charakter abrufen | Ja |
| POST | `/api/characters` | Neuen Charakter anlegen | Ja |
| GET | `/api/reference-data/races` | Verfügbare Rassen auflisten | Nein |
| GET | `/api/reference-data/classes` | Verfügbare Klassen auflisten | Nein |
| GET | `/api/reference-data/backgrounds` | Verfügbare Backgrounds auflisten | Nein |
| GET | `/api/characters/roll-ability-scores` | Sechs Attributswerte würfeln (4W6, niedrigsten verwerfen) | Ja |

Vollständige, immer aktuelle Dokumentation aller Endpoints: siehe Swagger-UI.

## Projektstruktur

```
src/main/java/de/felixrabenhold/dnd_backend/
├── character/     Charakter-Domäne (Entity, Repository, Service, Controller)
├── auth/          Authentifizierung & Autorisierung (JWT, User-Verwaltung)
└── config/        Technische Konfiguration (Security, OpenAPI, Fehlerbehandlung)
```