-- DROP DATABASE IF EXISTS exemplaire;
--CREATE DATABASE IF NOT EXISTS parcours;
CREATE SCHEMA IF NOT EXISTS exemplaire AUTHORIZATION postgres;

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

CREATE TABLE IF NOT EXISTS exemplaire.ressourcesreelles
(
    id               VARCHAR(255) NOT NULL,
    version          INTEGER,
    datecreation     TIMESTAMP,
    datemodification TIMESTAMP,
    quantite         INTEGER,
    seuilalerte      INTEGER,
    prixentree       DOUBLE PRECISION,
    prixsortie       DOUBLE PRECISION,
    unite            VARCHAR(255),
    scanbarcode      VARCHAR(255),
    ressource_id     VARCHAR(255),
    CONSTRAINT pk_ressourcesreelles PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS exemplaire.caissesreelles
(
    id               VARCHAR(255) NOT NULL,
    version          INTEGER,
    solde            DOUBLE PRECISION NOT NULL,
    caisse_id        VARCHAR(255) NOT NULL,
    datecreation     TIMESTAMP,
    datemodification TIMESTAMP,
    CONSTRAINT pk_caissesreelles PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS exemplaire.mouvements
(
    id               VARCHAR(255) NOT NULL,
    description      VARCHAR(255) NOT NULL,
    quantite_initiale INTEGER,
    quantite_mouvementee INTEGER,
    quantite_finale  INTEGER,
    prix             DOUBLE PRECISION,
    type_transaction VARCHAR(255),
    validee          BOOLEAN,
    validee_by       VARCHAR(255),
    date_validation  TIMESTAMP,
    statut           VARCHAR(255),
    datecreation     Date NOT NULL,
    dateperemption   Date,
    datemodification Date,
    ressourcesreelles_id VARCHAR(255),
    distributeurs_id VARCHAR(255),
    exemplaires_id   VARCHAR(255),
    CONSTRAINT pk_mouvements PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS exemplaire.mouvements_solde_caisses
(
    id                VARCHAR(255) NOT NULL,
    caissesreelles_id VARCHAR(255) NOT NULL,
    solde_initiale    DOUBLE PRECISION,
    solde_mouvementee DOUBLE PRECISION,
    solde_finale      DOUBLE PRECISION,
    type_transaction  VARCHAR(255),
    description       VARCHAR(255),
    motif             VARCHAR(255),
    code_reference    VARCHAR(255),
    created_by        VARCHAR(255),
    datecreation      TIMESTAMP,
    validee           BOOLEAN,
    validee_by        VARCHAR(255),
    date_validation   TIMESTAMP,
    statut            VARCHAR(255),
    exemplaires_id    VARCHAR(255),
    CONSTRAINT pk_mouvements_solde_caisses PRIMARY KEY (id)
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
    intitule              VARCHAR(255),
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

CREATE TABLE IF NOT EXISTS exemplaire.correspondants
(
    exemplaires_id VARCHAR(255) NOT NULL,
    personneid     VARCHAR(255),
    dateechange    TIMESTAMP,
    methodeutilisee   VARCHAR(255)
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

ALTER TABLE exemplaire.mouvements
    ADD CONSTRAINT IF NOT EXISTS FK_MOUVEMENTS_ON_EXEMPLAIRES FOREIGN KEY (exemplaires_id) REFERENCES exemplaire.exemplaires (id);

ALTER TABLE exemplaire.correspondants
    ADD CONSTRAINT IF NOT EXISTS fk_correspondants_on_exemplaires_entity FOREIGN KEY (exemplaires_id) REFERENCES exemplaire.exemplaires (id);


ALTER TABLE exemplaire.mouvements
    ADD CONSTRAINT IF NOT EXISTS FK_MOUVEMENTS_ON_RESSOURCESREELLES FOREIGN KEY (ressourcesreelles_id) REFERENCES exemplaire.ressourcesreelles (id);

ALTER TABLE exemplaire.mouvements_solde_caisses
    ADD CONSTRAINT IF NOT EXISTS FK_MVT_SOLDE_CAISSES_ON_CAISSESREELLES FOREIGN KEY (caissesreelles_id) REFERENCES exemplaire.caissesreelles (id);

ALTER TABLE exemplaire.mouvements_solde_caisses
    ADD CONSTRAINT IF NOT EXISTS FK_MVT_SOLDE_CAISSES_ON_EXEMPLAIRES FOREIGN KEY (exemplaires_id) REFERENCES exemplaire.exemplaires (id);

ALTER TABLE exemplaire.respecterprecomouvement
    ADD CONSTRAINT IF NOT EXISTS fk_respecterprecomouvement_on_mouvements_entity FOREIGN KEY (mouvements_id) REFERENCES exemplaire.mouvements (id);

ALTER TABLE exemplaire.violerprecomouvement
    ADD CONSTRAINT IF NOT EXISTS fk_violerprecomouvement_on_mouvements_entity FOREIGN KEY (mouvements_id) REFERENCES exemplaire.mouvements (id);

ALTER TABLE exemplaire.ordreetats
    ADD CONSTRAINT IF NOT EXISTS FK_ORDREETATS_ON_EXEMPLAIREID FOREIGN KEY (exemplaire_id) REFERENCES exemplaire.exemplaires (id);