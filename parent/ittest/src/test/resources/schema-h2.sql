-- DROP DATABASE IF EXISTS parcours;
CREATE SCHEMA parcours AUTHORIZATION sa;

CREATE TABLE IF NOT EXISTS attributs
(
    id VARCHAR(255)  NOT NULL,
    titre VARCHAR(255)  NOT NULL,
    description VARCHAR(255) ,
    etat boolean,
    datecreation date,
    datemodification date,
    type character varying  NOT NULL DEFAULT 'double, float, date, int, boolean,  String, ',
    valeurpardefaut VARCHAR(255) ,
    CONSTRAINT attributs_pkey PRIMARY KEY (id)
);


CREATE TABLE IF NOT EXISTS documents
(
    id VARCHAR(255) NOT NULL,
    titre VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    typemouvement VARCHAR(255),
    etat BOOLEAN,
    afficherunite BOOLEAN,
    afficherdistributeur BOOLEAN,
    prixeditable BOOLEAN,
    contientressources BOOLEAN,
    afficherprix VARCHAR(25),
    datecreation DATE,
    datemodification DATE,
    CONSTRAINT documents_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS constituer
(
    documents_id VARCHAR(255)  NOT NULL,
    attributs_id VARCHAR(255)  NOT NULL,
    CONSTRAINT constituer_pkey PRIMARY KEY (documents_id, attributs_id),
    FOREIGN KEY (documents_id) REFERENCES documents(id),
    FOREIGN KEY (attributs_id) REFERENCES attributs(id)
);

CREATE TABLE IF NOT EXISTS categories (
    id VARCHAR PRIMARY KEY,
    ordre VARCHAR(255),
    libelle VARCHAR(255),
    documents_id VARCHAR(255) NOT NULL,
    datecreation DATE,
    datemodification DATE,
    FOREIGN KEY (documents_id) REFERENCES documents(id)
);

CREATE TABLE IF NOT EXISTS associer (
    attributs_id VARCHAR NOT NULL,
    categories_id VARCHAR NOT NULL,
    obligatoire boolean,
    ordre INT ,
    PRIMARY KEY (attributs_id, categories_id),
    FOREIGN KEY (attributs_id) REFERENCES attributs(id),
    FOREIGN KEY (categories_id) REFERENCES categories(id)
);

CREATE TABLE IF NOT EXISTS services (
    id VARCHAR NOT NULL PRIMARY KEY,
    libelle VARCHAR(255) ,
    description VARCHAR(255) ,
    codeunique VARCHAR NOT NULL UNIQUE,
    localisation VARCHAR(255) ,
    etat boolean,
    datecreation date,
    datemodification date
);

CREATE TABLE IF NOT EXISTS missions (
    id VARCHAR NOT NULL PRIMARY KEY,
    libelle VARCHAR(255) ,
    description VARCHAR(255) ,
    etat boolean,
    datecreation date,
    datemodification date,
    services_id VARCHAR  NULL,
    FOREIGN KEY (services_id) REFERENCES services(id)
);

CREATE TABLE IF NOT EXISTS ressources(
     id VARCHAR NOT NULL PRIMARY KEY,
     libelle VARCHAR(255) ,
    etat boolean,
    datecreation date,
    datemodification date,
    quantite int,
    seuilalerte int,
    prixentree double,
    prixsortie double,
    unite VARCHAR(15),
    familles_id VARCHAR NULL
    --FOREIGN KEY (familles_id) REFERENCES familles(id)
    );

CREATE TABLE IF NOT EXISTS familles
(
    id VARCHAR NOT NULL PRIMARY KEY,
    libelle VARCHAR(255) ,
    description VARCHAR(255) ,
    etat boolean,
    datecreation date,
    datemodification date
    );

CREATE TABLE IF NOT EXISTS precomouvements(
    id VARCHAR(255) NOT NULL PRIMARY KEY,
    libelle VARCHAR(255) ,
    etat boolean,
    datecreation date,
    datemodification date,
    typemouvement VARCHAR(255)
    );

CREATE TABLE IF NOT EXISTS precomouvementsqtes(
      id VARCHAR NOT NULL PRIMARY KEY,
      qtemin int,
      qtemax int,
      montantmin int ,
      montantmax int,
      precomouvements_id VARCHAR NOT NULL ,
      ressources_id VARCHAR NULL,
      datecreation date,
      datemodification date,
      FOREIGN KEY (precomouvements_id) REFERENCES precomouvements(id),
      FOREIGN KEY (ressources_id) REFERENCES ressources(id)
    );

ALTER TABLE ressources ADD CONSTRAINT ressources_familles_fk FOREIGN KEY (familles_id) REFERENCES familles(id);

CREATE TABLE IF NOT EXISTS sapplique
(
    familles_id VARCHAR(255)  NOT NULL,
    precomouvementsqtes_id VARCHAR NOT NULL,
    CONSTRAINT sapplique_pkey PRIMARY KEY (familles_id, precomouvementsqtes_id),
    CONSTRAINT familles_precomouvements_fk FOREIGN KEY (familles_id)  REFERENCES familles(id),
    CONSTRAINT precomouvementsqtes_familles_fk FOREIGN KEY (precomouvementsqtes_id) REFERENCES precomouvementsqtes(id)
    );

CREATE TABLE IF NOT EXISTS suivre
(
    documents_id VARCHAR(255)  NOT NULL,
    precomouvements_id VARCHAR NOT NULL,
    CONSTRAINT suivre_pkey PRIMARY KEY (documents_id, precomouvements_id),
    CONSTRAINT documents_precomouvements_fk FOREIGN KEY (documents_id)  REFERENCES documents(id),
    CONSTRAINT precomouvements_documents_fk FOREIGN KEY (precomouvements_id) REFERENCES precomouvements(id)
);

CREATE TABLE IF NOT EXISTS traiter
(
    documents_id VARCHAR(255)  NOT NULL,
    missions_id VARCHAR(255)  NOT NULL,
    CONSTRAINT traiter_pkey PRIMARY KEY (documents_id, missions_id),
    CONSTRAINT documents_missions_fk FOREIGN KEY (documents_id)  REFERENCES documents(id),
    CONSTRAINT missions_documents_fk FOREIGN KEY (missions_id) REFERENCES missions(id)
);