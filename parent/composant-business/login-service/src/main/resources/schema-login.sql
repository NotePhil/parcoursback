-- ============================================================
-- Schéma login : utilisateurs, groupes, fonctionnalités, menu
-- ============================================================

CREATE SCHEMA IF NOT EXISTS login;

-- Groupes (profils d'accès)
CREATE TABLE IF NOT EXISTS login.groupes (
    id         UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    libelle    VARCHAR(100) NOT NULL,
    etat       VARCHAR(50)
);

-- Rôles
CREATE TABLE IF NOT EXISTS login.roles (
    id            UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    titre         VARCHAR(100) NOT NULL,
    description   TEXT,
    etat          BOOLEAN DEFAULT TRUE,
    datecreation  TIMESTAMP DEFAULT now()
);

-- Utilisateurs
CREATE TABLE IF NOT EXISTS login.utilisateurs (
    id            UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    login         VARCHAR(150) NOT NULL UNIQUE,
    password      VARCHAR(255) NOT NULL,
    personnel_id  UUID,
    groupe_id     UUID REFERENCES login.groupes(id),
    nom           VARCHAR(100),
    prenom        VARCHAR(100),
    telephone     VARCHAR(30),
    mail          VARCHAR(150),
    sexe          VARCHAR(10),
    datenaissance TIMESTAMP,
    dateentree    TIMESTAMP DEFAULT now(),
    type          VARCHAR(50),
    qrcodevalue   VARCHAR(255)
);

-- Association utilisateur-rôle
CREATE TABLE IF NOT EXISTS login.utilisateur_roles (
    id             UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    utilisateur_id UUID NOT NULL REFERENCES login.utilisateurs(id),
    role_id        UUID NOT NULL REFERENCES login.roles(id),
    status         BOOLEAN DEFAULT TRUE,
    datedebut      TIMESTAMP DEFAULT now(),
    datefin        TIMESTAMP
);

-- Fonctionnalités du menu (liées à un groupe)
CREATE TABLE IF NOT EXISTS login.fonctionnalites (
    id         UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    fonction   VARCHAR(150) NOT NULL,
    icone      VARCHAR(100),
    actif      VARCHAR(50),
    ordre      INTEGER NOT NULL DEFAULT 0,
    groupe_id  UUID NOT NULL REFERENCES login.groupes(id)
);

-- Éléments de menu (liés à une fonctionnalité)
CREATE TABLE IF NOT EXISTS login.elements (
    id                 UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nom                VARCHAR(150) NOT NULL,
    lien               VARCHAR(255),
    bouton             VARCHAR(10),
    ordre              INTEGER NOT NULL DEFAULT 0,
    fonctionnalite_id  UUID NOT NULL REFERENCES login.fonctionnalites(id)
);

-- Actions (liées à un élément)
CREATE TABLE IF NOT EXISTS login.actions (
    id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nom         VARCHAR(150) NOT NULL,
    lien        VARCHAR(255),
    bouton      VARCHAR(10),
    type        VARCHAR(50),
    ordre       INTEGER NOT NULL DEFAULT 0,
    element_id  UUID NOT NULL REFERENCES login.elements(id)
);
