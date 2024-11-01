-- DROP DATABASE IF EXISTS exemplaire;
CREATE SCHEMA IF NOT EXISTS exemplaire AUTHORIZATION sa;

CREATE TABLE IF NOT EXISTS exemplaire.ordreetats
(
    id               VARCHAR(255) NOT NULL,
    description      VARCHAR(255) NOT NULL,
    datecreation     Date,
    datemodification Date,
    datefinvote      Date,
    ordre            INTEGER,
    etats_id         VARCHAR(255),
    exemplaire_id     VARCHAR(255),
    CONSTRAINT pk_ordreetats PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS exemplaire.mouvements
(
    id               VARCHAR(255) NOT NULL,
    description      VARCHAR(255) NOT NULL,
    quantite         INTEGER,
    prix             DOUBLE PRECISION,
    datecreation     Date NOT NULL,
    dateperemption   Date,
    datemodification Date,
    ressources_id    VARCHAR(255),
    distributeurs_id VARCHAR(255),
    exemplaires_id   VARCHAR(255),
    CONSTRAINT pk_mouvements PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS exemplaire.respecterprecomouvement
(
    mouvements_id      VARCHAR(255) NOT NULL,
    precomouvements_id VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS exemplaire.violerprecomouvement
(
    mouvements_id      VARCHAR(255) NOT NULL,
    precomouvements_id VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS exemplaire.exemplaires
(
    id                 VARCHAR(255) NOT NULL,
    code               VARCHAR(255),
    codebarre          VARCHAR(255),
    titre              VARCHAR(255),
    document_id        VARCHAR(255) NOT NULL,
    datecreation       Date NOT NULL,
    datemodification   Date,
    personnebeneficiaire VARCHAR(255),
    personnerattachee VARCHAR(255),
    CONSTRAINT pk_exemplaires PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS exemplaire.exemplairesattributs
(
    exemplaires_id   VARCHAR(255)                        NOT NULL,
    attribut_id      VARCHAR(255),
    valeur           VARCHAR(255),
    datecreation     Date NOT NULL,
    datemodification Date
);

CREATE TABLE IF NOT EXISTS exemplaire.exemplairesparents
(
    exemplaires_id VARCHAR(255) NOT NULL,
    parent_id             VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS exemplaire.personnesdestinataires
(
    exemplaires_id VARCHAR(255) NOT NULL,
    personneid     VARCHAR(255),
    dateenvoi      Date,
    methodeenvoi   VARCHAR(255)
);

CREATE TABLE exemplaire.etatsvalidations
(
    id            VARCHAR(255) NOT NULL,
    methode       VARCHAR(255),
    personnel_id  VARCHAR(255),
    validation_id VARCHAR(255),
    datecreation  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    ordreetat_id  VARCHAR(255),
    CONSTRAINT pk_etatsvalidations PRIMARY KEY (id)
);

ALTER TABLE exemplaire.etatsvalidations
    ADD CONSTRAINT FK_ETATSVALIDATIONS_ON_ORDREETAT FOREIGN KEY (ordreetat_id) REFERENCES exemplaire.ordreetats (id);

ALTER TABLE exemplaire.exemplairesattributs
    ADD CONSTRAINT IF NOT EXISTS fk_exemplairesattributs_on_exemplaires_entity FOREIGN KEY (exemplaires_id) REFERENCES exemplaire.exemplaires (id);

ALTER TABLE exemplaire.exemplairesparents
    ADD CONSTRAINT IF NOT EXISTS fk_exemplairesparents_on_exemplaires_entity FOREIGN KEY (exemplaires_id) REFERENCES exemplaire.exemplaires (id);

ALTER TABLE exemplaire.personnesdestinataires
    ADD CONSTRAINT IF NOT EXISTS fk_personnesdestinataires_on_exemplaires_entity FOREIGN KEY (exemplaires_id) REFERENCES exemplaire.exemplaires (id);

ALTER TABLE exemplaire.mouvements
    ADD CONSTRAINT IF NOT EXISTS FK_MOUVEMENTS_ON_EXEMPLAIRES FOREIGN KEY (exemplaires_id) REFERENCES exemplaire.exemplaires (id);

ALTER TABLE exemplaire.respecterprecomouvement
    ADD CONSTRAINT IF NOT EXISTS fk_respecterprecomouvement_on_mouvements_entity FOREIGN KEY (mouvements_id) REFERENCES exemplaire.mouvements (id);

ALTER TABLE exemplaire.violerprecomouvement
    ADD CONSTRAINT IF NOT EXISTS fk_violerprecomouvement_on_mouvements_entity FOREIGN KEY (mouvements_id) REFERENCES exemplaire.mouvements (id);

ALTER TABLE exemplaire.ordreetats
    ADD CONSTRAINT IF NOT EXISTS FK_ORDREETATS_ON_EXEMPLAIREID FOREIGN KEY (exemplaire_id) REFERENCES exemplaire.exemplaires (id);