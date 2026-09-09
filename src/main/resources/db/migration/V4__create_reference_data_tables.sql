CREATE TABLE races (
                       id BIGSERIAL PRIMARY KEY,
                       name VARCHAR(255) NOT NULL UNIQUE,
                       description TEXT
);

CREATE TABLE character_classes (
                                   id BIGSERIAL PRIMARY KEY,
                                   name VARCHAR(255) NOT NULL UNIQUE,
                                   description TEXT
);

CREATE TABLE backgrounds (
                             id BIGSERIAL PRIMARY KEY,
                             name VARCHAR(255) NOT NULL UNIQUE,
                             description TEXT
);

CREATE TABLE background_eligible_abilities (
                                               background_id BIGINT NOT NULL REFERENCES backgrounds(id),
                                               ability VARCHAR(50) NOT NULL
);

CREATE TABLE race_traits (
                             race_id BIGINT NOT NULL REFERENCES races(id),
                             trait VARCHAR(255) NOT NULL
);

CREATE TABLE class_traits (
                              class_id BIGINT NOT NULL REFERENCES character_classes(id),
                              trait VARCHAR(255) NOT NULL
);

CREATE TABLE background_traits (
                                   background_id BIGINT NOT NULL REFERENCES backgrounds(id),
                                   trait VARCHAR(255) NOT NULL
);

INSERT INTO races (name, description) VALUES
                                          ('Human', 'Vielseitig und anpassungsfähig, in fast jeder Region der Welt vertreten.'),
                                          ('Elf', 'Langlebig und naturverbunden, mit ausgeprägten Sinnen.'),
                                          ('Dwarf', 'Zäh und traditionsbewusst, meist in unterirdischen Reichen beheimatet.'),
                                          ('Halfling', 'Klein und wendig, bekannt für Mut trotz geringer Statur.'),
                                          ('Dragonborn', 'Drakonischer Abstammung, mit angeborenem Elementarodem.'),
                                          ('Gnome', 'Neugierig und erfinderisch, oft mit einer Vorliebe für Magie oder Technik.'),
                                          ('Orc', 'Kraftvoll und ausdauernd, mit starker Gemeinschaftsbindung.'),
                                          ('Tiefling', 'Von einem Pakt mit einem Erzteufel gezeichnete Abstammung.');

INSERT INTO character_classes (name, description) VALUES
                                                      ('Fighter', 'Meister im Umgang mit Waffen und Rüstung.'),
                                                      ('Wizard', 'Gelehrter der arkanen Magie, wirkt Zauber durch Studium.'),
                                                      ('Rogue', 'Geschickt und heimlich, spezialisiert auf Präzision statt roher Kraft.'),
                                                      ('Cleric', 'Kanal göttlicher Macht, unterstützt und heilt Verbündete.'),
                                                      ('Barbarian', 'Kämpft in wilder Raserei mit roher, unkontrollierter Kraft.'),
                                                      ('Ranger', 'Waldläufer mit Verbindung zur Natur und Kampfgeschick.'),
                                                      ('Paladin', 'An einen heiligen Eid gebunden, kämpft für eine höhere Sache.'),
                                                      ('Bard', 'Wirkt Magie durch Musik, Geschichten und Charisma.');

INSERT INTO backgrounds (name, description) VALUES
                                                ('Acolyte', 'Diente in einem Tempel, vertraut mit religiösen Riten.'),
                                                ('Criminal', 'Lebte am Rande der Legalität, geschickt in Täuschung und Heimlichkeit.'),
                                                ('Soldier', 'Diente in einer militärischen Einheit, diszipliniert im Kampf.'),
                                                ('Sailor', 'Verbrachte Jahre auf See, gewohnt an Entbehrungen und Gefahr.'),
                                                ('Entertainer', 'Reisender Künstler, lebt von Auftritten vor Publikum.'),
                                                ('Noble', 'Aus privilegiertem Haus, geschult in Etikette und Führung.'),
                                                ('Guard', 'Bewachte Tore, Karawanen oder Amtsträger.'),
                                                ('Farmer', 'Arbeitete das Land, ausdauernd und bodenständig.');

INSERT INTO background_eligible_abilities (background_id, ability)
SELECT id, unnest(ARRAY['INTELLIGENCE', 'WISDOM', 'CHARISMA']) FROM backgrounds WHERE name = 'Acolyte';
INSERT INTO background_eligible_abilities (background_id, ability)
SELECT id, unnest(ARRAY['DEXTERITY', 'CONSTITUTION', 'INTELLIGENCE']) FROM backgrounds WHERE name = 'Criminal';
INSERT INTO background_eligible_abilities (background_id, ability)
SELECT id, unnest(ARRAY['STRENGTH', 'DEXTERITY', 'CONSTITUTION']) FROM backgrounds WHERE name = 'Soldier';
INSERT INTO background_eligible_abilities (background_id, ability)
SELECT id, unnest(ARRAY['STRENGTH', 'DEXTERITY', 'WISDOM']) FROM backgrounds WHERE name = 'Sailor';
INSERT INTO background_eligible_abilities (background_id, ability)
SELECT id, unnest(ARRAY['STRENGTH', 'DEXTERITY', 'CHARISMA']) FROM backgrounds WHERE name = 'Entertainer';
INSERT INTO background_eligible_abilities (background_id, ability)
SELECT id, unnest(ARRAY['STRENGTH', 'INTELLIGENCE', 'CHARISMA']) FROM backgrounds WHERE name = 'Noble';
INSERT INTO background_eligible_abilities (background_id, ability)
SELECT id, unnest(ARRAY['STRENGTH', 'INTELLIGENCE', 'WISDOM']) FROM backgrounds WHERE name = 'Guard';
INSERT INTO background_eligible_abilities (background_id, ability)
SELECT id, unnest(ARRAY['STRENGTH', 'CONSTITUTION', 'WISDOM']) FROM backgrounds WHERE name = 'Farmer';

INSERT INTO race_traits (race_id, trait)
SELECT id, unnest(ARRAY['Dunkelsicht 18m', 'Vorteil bei Rettungswürfen gegen Bezauberung'])
FROM races WHERE name = 'Elf';

INSERT INTO race_traits (race_id, trait)
SELECT id, unnest(ARRAY['Dunkelsicht 36m', 'Zäh: zusätzliche Trefferpunkte pro Level'])
FROM races WHERE name = 'Dwarf';

INSERT INTO class_traits (class_id, trait)
SELECT id, unnest(ARRAY['Trefferwürfel: W10', 'Kampfstil (Level 1)'])
FROM character_classes WHERE name = 'Fighter';