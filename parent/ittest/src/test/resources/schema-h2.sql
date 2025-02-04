-- DROP DATABASE IF EXISTS parcours;
CREATE SCHEMA parcours AUTHORIZATION sa;
-- generate tables from entities
CREATE TABLE IF NOT EXISTS attributs
(
    id               VARCHAR(255)         NOT NULL,
    titre            VARCHAR(255) NOT NULL,
    description      VARCHAR(255),
    etat             BOOLEAN DEFAULT TRUE,
    datecreation     DATE,
    datemodification DATE,
    type             VARCHAR(255) NOT NULL,
    valeurpardefaut  VARCHAR(255),
    CONSTRAINT pk_attributs PRIMARY KEY (id)
);
CREATE TABLE IF NOT EXISTS categories
(
    id               VARCHAR(255) NOT NULL,
    ordre            VARCHAR(255),
    libelle          VARCHAR(255),
    etat             BOOLEAN DEFAULT TRUE,
    datecreation     DATE,
    datemodification DATE,
    documents_id     VARCHAR(255),
    CONSTRAINT pk_categories PRIMARY KEY (id)
);
CREATE TABLE IF NOT EXISTS associer
(
    obligatoire   BOOLEAN,
    ordre         INTEGER,
    attributs_id  VARCHAR(255) NOT NULL,
    categories_id VARCHAR(255) NOT NULL,
    CONSTRAINT pk_associer PRIMARY KEY (attributs_id, categories_id)
);
CREATE TABLE IF NOT EXISTS concerner
(
    distributeurs_id       VARCHAR(255) NOT NULL,
    precomouvementsqtes_id VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS constituer
(
    attributs_id VARCHAR(255) NOT NULL,
    documents_id VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS documents
(
    id                   VARCHAR(255)         NOT NULL,
    titre                VARCHAR(255) NOT NULL,
    description          VARCHAR(255),
    etat                 BOOLEAN,
    datecreation         DATE,
    datemodification     DATE,
    typemouvement        VARCHAR(255),
    afficherprix         VARCHAR(255),
    afficherunite        BOOLEAN,
    afficherdistributeur BOOLEAN,
    prixeditable         BOOLEAN,
    contientressources   BOOLEAN,
    estencaissable BOOLEAN,
    CONSTRAINT pk_documents PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS suivre
(
    documents_id       VARCHAR(255) NOT NULL,
    precomouvements_id VARCHAR(255) NOT NULL
);
CREATE TABLE IF NOT EXISTS etats
(
    id               VARCHAR(255) NOT NULL,
    libelle          VARCHAR(255),
    description      VARCHAR(255),
    datecreation     DATE,
    datemodification DATE,
    CONSTRAINT pk_etats PRIMARY KEY (id)
);
CREATE TABLE IF NOT EXISTS etapes
(
    id               VARCHAR(255) NOT NULL,
    libelle          VARCHAR(255),
    etat             VARCHAR(255),
    datemodification DATE,
    parcours_id      VARCHAR(255),
    CONSTRAINT pk_etapes PRIMARY KEY (id)
);
CREATE TABLE IF NOT EXISTS familles
(
    id               VARCHAR(255) NOT NULL,
    libelle          VARCHAR(255),
    description      VARCHAR(255),
    etat             BOOLEAN,
    datecreation     DATE,
    datemodification DATE,
    CONSTRAINT pk_familles PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS sapplique
(
    familles_id            VARCHAR(255) NOT NULL,
    precomouvementsqtes_id VARCHAR(255) NOT NULL
);
CREATE TABLE IF NOT EXISTS filesattentes
(
    id               VARCHAR(255) NOT NULL,
    datecreation     DATE,
    datemodification DATE,
    etat             BOOLEAN,
    services_id      VARCHAR(255),
    CONSTRAINT pk_filesattentes PRIMARY KEY (id)
);
CREATE TABLE IF NOT EXISTS jouerroles
(
    id               VARCHAR(255) NOT NULL,
    etat             BOOLEAN,
    datecreation     DATE,
    datemodification DATE,
    datefin          DATE,
    datedebut        DATE,
    personnels_id    VARCHAR(255),
    roles_id         VARCHAR(255),
    CONSTRAINT pk_jouerroles PRIMARY KEY (id)
);
CREATE TABLE IF NOT EXISTS missions
(
    id               VARCHAR(255)                        NOT NULL,
    libelle          VARCHAR(255)                NOT NULL,
    description      VARCHAR(255),
    etat             BOOLEAN,
    datecreation     DATE NOT NULL,
    datemodification DATE,
    services_id      VARCHAR(255),
    CONSTRAINT pk_missions PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS traiter
(
    documents_id VARCHAR(255) NOT NULL,
    missions_id  VARCHAR(255) NOT NULL
);
CREATE TABLE IF NOT EXISTS mouvements
(
    id               VARCHAR(255)                        NOT NULL,
    description      VARCHAR(255)                NOT NULL,
    quantite         INTEGER,
    prix             DOUBLE PRECISION,
    datecreation     DATE NOT NULL,
    dateperemption   DATE ,
    datemodification DATE,
    ressources_id    VARCHAR(255),
    distributeurs_id VARCHAR(255),
    CONSTRAINT pk_mouvements PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS mouvementcaisses
(
    id               VARCHAR(255)                        NOT NULL,
    montant             DOUBLE PRECISION,
    moyenpaiement VARCHAR(255),
    referencepaiement   VARCHAR(255),
    detailJSON  VARCHAR(255),
    datecreation    DATE,
    etat    VARCHAR(255),
    typeMvt VARCHAR(255),,
    libelle VARCHAR(255),
    caisses_id  VARCHAR(255),
    comptes_id  VARCHAR(255),
    personnels_id   VARCHAR(255),
    exemplaires_id  VARCHAR(255),
    CONSTRAINT pk_mouvementcaisses PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS respecter
(
    mouvements_id      VARCHAR(255) NOT NULL,
    precomouvements_id VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS violer
(
    mouvements_id      VARCHAR(255) NOT NULL,
    precomouvements_id VARCHAR(255) NOT NULL
);
CREATE TABLE IF NOT EXISTS ordreetats
(
    id               VARCHAR(255) NOT NULL,
    datecreation     DATE,
    datemodification DATE,
    datefinvote      DATE,
    ordre            INTEGER,
    etats_id         VARCHAR(255),
    CONSTRAINT pk_ordreetats PRIMARY KEY (id)
);
CREATE TABLE IF NOT EXISTS parcours
(
    id               VARCHAR(255) NOT NULL,
    libelle          VARCHAR(255),
    datecreation     DATE,
    datemodification DATE,
    CONSTRAINT pk_parcours PRIMARY KEY (id)
);
CREATE TABLE IF NOT EXISTS personnels
(
    id               VARCHAR(255) NOT NULL,
    dateentree       DATE,
    nom              VARCHAR(255),
    datenaissance    DATE,
    telephone        VARCHAR(255),
    datesortie       DATE,
    email            VARCHAR(255),
    prenom           VARCHAR(255),
    sexe             VARCHAR(255),
    datemodification DATE,
    CONSTRAINT pk_personnels PRIMARY KEY (id)
);
CREATE TABLE IF NOT EXISTS deltasoldes (
    id               VARCHAR(255) NOT NULL,
    montantavant             DOUBLE PRECISION,
    montantapres             DOUBLE PRECISION,
    datecreation               date,
    typemouvement   VARCHAR(255) NOT NULL,
    comptes_id  VARCHAR(255) NOT NULL,
    exemplaires_id  VARCHAR(255) NOT NULL,
    CONSTRAINT pk_deltasoldes PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS comptes (
    id               VARCHAR(255) NOT NULL,
    solde               DOUBLE PRECISION,
    datecreation               date,
    etat    VARCHAR(255),
    montantdecouvertmax             DOUBLE PRECISION,
    libelle VARCHAR(255),
    personnes_id VARCHAR(255) NOT NULL,
    CONSTRAINT pk_comptes PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS caisses(
    id               VARCHAR(255) NOT NULL,
    libelle VARCHAR(255),
    solde   DOUBLE PRECISION,
    type VARCHAR(255),
    detailJSON VARCHAR(255),
    CONSTRAINT pk_caisses PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS exemplaires (
    id               VARCHAR(255) NOT NULL,
    personnes_id    VARCHAR(255) NOT NULL,
    documents_id    VARCHAR(255) NOT NULL,
    CONSTRAINT pk_exemplaires PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS personnes
(
    id               VARCHAR(255) NOT NULL,
    adresse          VARCHAR(255),
    mail             VARCHAR(255),
    telephone        VARCHAR(255),
    qrcodevalue      VARCHAR(255),
    code             VARCHAR(255),
    raisonsociale    VARCHAR(255),
    nom                  VARCHAR(255) ,
    prenom               VARCHAR(255),
    sexe                 VARCHAR(255),
    datenaissance        date         ,
    datecreation     DATE ,
    datemodification DATE,
    comptes_id VARCHAR(255) NOT NULL,
    person_type VARCHAR(255) NOT NULL CHECK (person_type IN ('personnesmorales', 'personnesphysique', 'distributeurs')),
    CONSTRAINT pk_personnes PRIMARY KEY (id)
);
--
--CREATE TABLE IF NOT EXISTS distributeurs
--(
--    distributeurs_id VARCHAR(255) NOT NULL,
--    datemodification DATE,
--    code             VARCHAR(255),
--    raisonsociale    VARCHAR(255),
--    CONSTRAINT pk_distributeurs PRIMARY KEY (distributeurs_id)
--);
--
--CREATE TABLE IF NOT EXISTS personnesmorales
--(
--    personnesmorales_id VARCHAR(255) NOT NULL,
--    raisonsociale       VARCHAR(255),
--    code                VARCHAR(255),
--    datemodification    DATE,
--    CONSTRAINT pk_personnesmorales PRIMARY KEY (personnesmorales_id)
--);
--CREATE TABLE IF NOT EXISTS personnesphysique
--(
--    personnesphysique_id VARCHAR(255)         NOT NULL,
--    nom                  VARCHAR(255) NOT NULL,
--    prenom               VARCHAR(255),
--    sexe                 VARCHAR(255),
--    datenaissance        date         NOT NULL,
--    datemodification     DATE,
--    CONSTRAINT pk_personnesphysique PRIMARY KEY (personnesphysique_id)
--);

CREATE TABLE IF NOT EXISTS rattacher
(
    personnes_id VARCHAR(255) NOT NULL,
    rattacher_id VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS precomouvements
(
    id               VARCHAR(255)         NOT NULL,
    libelle          VARCHAR(255) NOT NULL,
    etat             BOOLEAN,
    datecreation     date         NOT NULL,
    datemodification date,
    typemouvement    VARCHAR(255),
    CONSTRAINT pk_precomouvements PRIMARY KEY (id)
);
CREATE TABLE IF NOT EXISTS precomouvementsqtes
(
    id                 VARCHAR(255) NOT NULL,
    qtemin             INTEGER,
    qtemax             INTEGER,
    montantmin         DOUBLE PRECISION,
    montantmax         DOUBLE PRECISION,
    datecreation       DATE,
    datemodification   DATE,
    precomouvements_id VARCHAR(255) NOT NULL,
    ressources_id      VARCHAR(255),
    CONSTRAINT pk_precomouvementsqtes PRIMARY KEY (id)
);
CREATE TABLE IF NOT EXISTS documentspromotions
(
    documents_id  VARCHAR(255) NOT NULL,
    promotions_id VARCHAR(255) NOT NULL
);

    CREATE TABLE IF NOT EXISTS famillespromotions
(
    familles_id   VARCHAR(255) NOT NULL,
    promotions_id VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS promotions
(
    id                      VARCHAR(255) NOT NULL,
    datedebut               DATE,
    datefin                 DATE,
    codeunique              VARCHAR(255),
    typeremise              VARCHAR(255),
    valeurremise            DOUBLE PRECISION,
    datecreation            DATE,
    datemodification        DATE,
    distributeurs_id VARCHAR(255),
    CONSTRAINT pk_promotions PRIMARY KEY (id)
);
CREATE TABLE IF NOT EXISTS remplir
(
    datefin        DATE,
    datecreation   DATE,
    datedebut      DATE,
    etat           BOOLEAN,
    droitajouter   BOOLEAN,
    droitmodifier  BOOLEAN,
    droitconsulter BOOLEAN,
    droitvalider   BOOLEAN,
    roles_id       VARCHAR(255) NOT NULL,
    missions_id    VARCHAR(255) NOT NULL,
    CONSTRAINT pk_remplir PRIMARY KEY (roles_id, missions_id)
);
CREATE TABLE IF NOT EXISTS ressources
(
    id               VARCHAR(255) NOT NULL,
    libelle          VARCHAR(255),
    description      VARCHAR(255),
    etat             BOOLEAN,
    datecreation     DATE,
    datemodification DATE,
    quantite         INTEGER,
    seuilalerte      INTEGER,
    prixentree       DOUBLE PRECISION,
    prixsortie       DOUBLE PRECISION,
    unite            VARCHAR(255),
    familles_id      VARCHAR(255),
    CONSTRAINT pk_ressources PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS ressourcespromotions
(
    promotions_id VARCHAR(255) NOT NULL,
    ressources_id VARCHAR(255) NOT NULL
);
CREATE TABLE IF NOT EXISTS roles
(
    id               VARCHAR(255) NOT NULL,
    titre            VARCHAR(255),
    description      VARCHAR(255),
    etat             BOOLEAN,
    datecreation     DATE,
    datemodification DATE,
    CONSTRAINT pk_roles PRIMARY KEY (id)
);
CREATE TABLE IF NOT EXISTS services
(
    id               VARCHAR(255)         NOT NULL,
    description      VARCHAR(255),
    libelle          VARCHAR(255),
    localisation     VARCHAR(255),
    etat             BOOLEAN,
    datecreation     DATE,
    datemodification DATE,
    codeunique       VARCHAR(255) NOT NULL,
    filesattentes_id VARCHAR(255),
    CONSTRAINT pk_services PRIMARY KEY (id)
);
CREATE TABLE IF NOT EXISTS tickets
(
    id               VARCHAR(255) NOT NULL,
    codecourt        VARCHAR(255),
    datecreation     DATE,
    datemodification DATE,
    CONSTRAINT pk_tickets PRIMARY KEY (id)
);
CREATE TABLE IF NOT EXISTS ticketsfilesattentes
(
    id               VARCHAR(255) NOT NULL,
    etat             BOOLEAN,
    dateaffectation  DATE,
    tickets_id       VARCHAR(255),
    filesattentes_id VARCHAR(255),
    CONSTRAINT pk_ticketsfilesattentes PRIMARY KEY (id)
);
CREATE TABLE IF NOT EXISTS validations
(
    id               VARCHAR(255) NOT NULL,
    code             VARCHAR(255),
    etat             VARCHAR(255),
    datecreation     DATE,
    datemodification DATE,
    typevote         VARCHAR(255),
    dureevote        INTEGER,
    typevalidation   VARCHAR(255),
    roles_id         VARCHAR(255),
    CONSTRAINT pk_validations PRIMARY KEY (id)
);
CREATE TABLE IF NOT EXISTS docetats
(
    id                          VARCHAR(255) NOT NULL,
    ordre                       INTEGER,
    datecreation                DATE,
    datemodification            DATE,
    validations_id              VARCHAR(255),
    etats_id                    VARCHAR(255),
    documents_id                VARCHAR(255),
    etapes_id                   VARCHAR(255),
    CONSTRAINT pk_docetats PRIMARY KEY (id)
);
CREATE TABLE IF NOT EXISTS docetats_predecesseurs
(
    docetats_id       VARCHAR(255) NOT NULL,
    predecesseur_id VARCHAR(255) NOT NULL,
    CONSTRAINT pk_docetats_predecesseurs PRIMARY KEY (docetats_id, predecesseur_id)
);



CREATE TABLE actions (
    id VARCHAR(255) NOT NULL PRIMARY KEY,
    libelle VARCHAR(255),
    etat BOOLEAN,
    datecreation DATE,
    datemodification DATE,
    elementsbase_id VARCHAR(255)
);

CREATE TABLE elementsbases (

    id VARCHAR(255) NOT NULL PRIMARY KEY,
    libelle VARCHAR(255),
    etat BOOLEAN,
    datesouscription DATE,
    datemodification DATE,
    moduleangular VARCHAR(255)
);

CREATE TABLE actionslangues (

    langues_id VARCHAR(255),
    actions_id VARCHAR(255),
    valeurlibelle VARCHAR(255)
);

CREATE TABLE langues (

    id VARCHAR(255) NOT NULL PRIMARY KEY,
    libelle VARCHAR(255),
    etat BOOLEAN,
    datesouscription DATE,
    datemodification DATE
);

CREATE TABLE elementsbaselanques (

    langues_id VARCHAR(255),
    elementsbases_id VARCHAR(255),
    valeurlibelle VARCHAR(255)
);

CREATE TABLE elementslangues(
    langues_id VARCHAR(255),
    elements_id VARCHAR(255),
    valeurlibelle VARCHAR(255)
);

CREATE TABLE elements(
    id VARCHAR(255) NOT NULL PRIMARY KEY,
    libelle VARCHAR(255),
    etat BOOLEAN,
    datesouscription DATE,
    datemodification DATE,
    menus_id VARCHAR(255),
    elementsbases_id VARCHAR(255)
);

CREATE TABLE menus (
    id VARCHAR(255) NOT NULL PRIMARY KEY,
    etat BOOLEAN,
    datecreation DATE,
    utilisateurs_id VARCHAR(255),
    groupes_id VARCHAR(255)
);

CREATE TABLE utilisateurs (
    id VARCHAR(255) NOT NULL PRIMARY KEY,
    login VARCHAR(255),
    mdp VARCHAR(255),
    etat VARCHAR(255),
    datecreation DATE,
    datemodification DATE,
    groupes_id VARCHAR(255),
    menus_id VARCHAR(255)
);

CREATE TABLE organisations (
    id VARCHAR(255) NOT NULL PRIMARY KEY,
    raisonsociale VARCHAR(255),
);

CREATE TABLE organiser (
    organisations_id VARCHAR(255),
    utilisateurs_id VARCHAR(255)
);

CREATE TABLE groupes (
    id VARCHAR(255) NOT NULL PRIMARY KEY,
    libelle VARCHAR(255),
    etat BOOLEAN,
    datecreation DATE,
    datemodification DATE,
    menus_id VARCHAR(255)
);

CREATE TABLE actions (
    id VARCHAR(255) NOT NULL PRIMARY KEY,
    libelle VARCHAR(255),
    etat BOOLEAN,
    datecreation DATE,
    datemodification DATE,
    elementsbase_id VARCHAR(255)
);

CREATE TABLE elementsbases (

    id VARCHAR(255) NOT NULL PRIMARY KEY,
    libelle VARCHAR(255),
    etat BOOLEAN,
    datesouscription DATE,
    datemodification DATE,
    moduleangular VARCHAR(255)
);

CREATE TABLE actionslangues (

    langues_id VARCHAR(255),
    actions_id VARCHAR(255),
    valeurlibelle VARCHAR(255)
);

CREATE TABLE langues (

    id VARCHAR(255) NOT NULL PRIMARY KEY,
    libelle VARCHAR(255),
    etat BOOLEAN,
    datesouscription DATE,
    datemodification DATE
);

CREATE TABLE elementsbaselanques (

    langues_id VARCHAR(255),
    elementsbases_id VARCHAR(255),
    valeurlibelle VARCHAR(255)
);

CREATE TABLE elementslangues(
    langues_id VARCHAR(255),
    elements_id VARCHAR(255),
    valeurlibelle VARCHAR(255)
);

CREATE TABLE elements(
    id VARCHAR(255) NOT NULL PRIMARY KEY,
    libelle VARCHAR(255),
    etat BOOLEAN,
    datesouscription DATE,
    datemodification DATE,
    menus_id VARCHAR(255),
    elementsbases_id VARCHAR(255)
);

CREATE TABLE menus (
    id VARCHAR(255) NOT NULL PRIMARY KEY,
    etat BOOLEAN,
    datecreation DATE,
    utilisateurs_id VARCHAR(255),
    groupes_id VARCHAR(255)
);

CREATE TABLE utilisateurs (
    id VARCHAR(255) NOT NULL PRIMARY KEY,
    login VARCHAR(255),
    mdp VARCHAR(255),
    etat VARCHAR(255),
    datecreation DATE,
    datemodification DATE,
    groupes_id VARCHAR(255),
    menus_id VARCHAR(255)
);

CREATE TABLE organisations (
    id VARCHAR(255) NOT NULL PRIMARY KEY,
    raisonsociale VARCHAR(255)
);

CREATE TABLE organiser (
    organisations_id VARCHAR(255),
    utilisateurs_id VARCHAR(255)
);

CREATE TABLE groupes (
    id VARCHAR(255) NOT NULL PRIMARY KEY,
    libelle VARCHAR(255),
    etat BOOLEAN,
    datecreation DATE,
    datemodification DATE,
    menus_id VARCHAR(255)
);

ALTER TABLE groupes ADD CONSTRAINT FK_MENUS_GROUPES
    FOREIGN KEY (menus_id) REFERENCES menus(id);

ALTER TABLE organiser  ADD CONSTRAINT FK_ORGANISATION_ORGANISER
    FOREIGN KEY (organisations_id) REFERENCES organisations(id);

ALTER TABLE organiser  ADD CONSTRAINT FK_UTILISATEURS_ORGANISER
    FOREIGN KEY (utilisateurs_id) REFERENCES utilisateurs(id);

ALTER TABLE organiser ADD CONSTRAINT PK_ORGANISER
    PRIMARY KEY (organisations_id,utilisateurs_id);

ALTER TABLE utilisateurs ADD CONSTRAINT FK_GROUPES_UTILISATEURS
    FOREIGN KEY (groupes_id) REFERENCES groupes(id);

ALTER TABLE utilisateurs ADD CONSTRAINT FK_MENUS_UTILISATEURS
    FOREIGN KEY (menus_id) REFERENCES menus(id);

ALTER TABLE menus ADD CONSTRAINT FK_UTILISATEUR_MENUS
    FOREIGN KEY (utilisateurs_id) REFERENCES utilisateurs(id);

ALTER TABLE menus ADD CONSTRAINT FK_GROUPES_MENUS
    FOREIGN KEY (groupes_id) REFERENCES groupes(id);

ALTER TABLE elements ADD CONSTRAINT PK_COMPOSITE_ELEMENTS
    UNIQUE (menus_id,elementsbases_id);

ALTER TABLE elements ADD CONSTRAINT FK_MENUS_ELEMENTS
    FOREIGN KEY (menus_id) REFERENCES menus(id);

ALTER TABLE elements ADD CONSTRAINT FK_ELEMENTSBASE_ELEMENTS
    FOREIGN KEY (elementsbases_id) REFERENCES elementsbases(id);

ALTER TABLE elementslangues ADD CONSTRAINT FK_LANGUES_ELEMENTSLANGUES
    FOREIGN KEY (langues_id) REFERENCES langues(id);

ALTER TABLE elementslangues ADD CONSTRAINT FK_ELEMENTS_ELEMENTSLANGUES
    FOREIGN KEY (elements_id) REFERENCES elements(id);

ALTER TABLE elementslangues ADD CONSTRAINT PK_ELEMENTSLANGUES
    PRIMARY KEY (langues_id,elements_id);

ALTER TABLE elementsbaselanques ADD CONSTRAINT FK_ELEMENTSBASE_ELEMENTSBASELANGUES
    FOREIGN KEY (elementsbases_id) REFERENCES elementsbases(id);

ALTER TABLE elementsbaselanques ADD CONSTRAINT FK_LANGUES_ELEMENTSBASELANGUES
    FOREIGN KEY (langues_id) REFERENCES langues(id);

ALTER TABLE elementsbaselanques ADD CONSTRAINT PK_ELEMENTSBASESLANGUES
    PRIMARY KEY (langues_id,elementsbases_id);

ALTER TABLE actionslangues ADD CONSTRAINT FK_ACTIONS_ACTIONSLANGUES
    FOREIGN KEY (actions_id) REFERENCES actions(id);

ALTER TABLE actionslangues ADD CONSTRAINT FK_LANGUES_ACTIONSLANGUES
    FOREIGN KEY (langues_id) REFERENCES langues(id);

ALTER TABLE actionslangues ADD CONSTRAINT PK_ACTIONSLANGUES
    PRIMARY KEY (langues_id,actions_id);

ALTER TABLE actions ADD CONSTRAINT FK_ACTIONS_ELEMENTBASES
    FOREIGN KEY (elementsbase_id) REFERENCES elementsbases(id);

alter table mouvements add constraint fk_distributeurs_mouvements
foreign key (distributeurs_id) references personnes(id);

ALTER TABLE mouvementcaisses
    ADD CONSTRAINT FK_MOUVEMENTCAISSES_CAISSES FOREIGN KEY (caisses_id) REFERENCES caisses(id);
ALTER TABLE mouvementcaisses
    ADD CONSTRAINT FK_MOUVEMENTCAISSES_COMPTES FOREIGN KEY (comptes_id) REFERENCES comptes(id);
ALTER TABLE mouvementcaisses
    ADD CONSTRAINT FK_MOUVEMENTCAISSES_PERSONNELS FOREIGN KEY (personnels_id) REFERENCES personnels(id);
ALTER TABLE mouvementcaisses
    ADD CONSTRAINT FK_MOUVEMENTSCAISSES_EXEMPLAIRES  FOREIGN KEY (exemplaires_id) REFERENCES exemplaires(id);
ALTER TABLE exemplaires
    ADD CONSTRAINT FK_EXEMPLAIRES_PERSONNES FOREIGN KEY (personnes_id) REFERENCES personnes(id);
ALTER TABLE deltasoldes
    ADD CONSTRAINT FK_DELTASOLDES_COMPTES FOREIGN KEY (comptes_id) REFERENCES comptes(id);
ALTER TABLE deltasoldes
    ADD CONSTRAINT FK_DELTASOLDES_EXEMPLAIRES FOREIGN KEY (exemplaires_id) REFERENCES exemplaires(id);

ALTER TABLE exemplaires
    ADD CONSTRAINT FK_exemplaire_documents FOREIGN KEY (documents_id) REFERENCES documents(id);

ALTER TABLE comptes
    ADD CONSTRAINT FK_COMPTES_PERSONNES FOREIGN KEY (personnes_id) REFERENCES personnes(id);
ALTER TABLE personnes
    ADD CONSTRAINT fk_personnes_comptes FOREIGN KEY (comptes_id) REFERENCES comptes(id);
ALTER TABLE docetats_predecesseurs
    ADD CONSTRAINT FK_DOCETATS_PREDECESSEURS_ON_DOCETATS FOREIGN KEY (docetats_id) REFERENCES docetats (id);
ALTER TABLE docetats_predecesseurs
    ADD CONSTRAINT FK_DOCETATS_PREDECESSEURS_SUIVANT_ON_DOCETATS FOREIGN KEY (predecesseur_id) REFERENCES docetats (id);

ALTER TABLE docetats
    ADD CONSTRAINT FK_DOCETATS_ON_DOCUMENTS FOREIGN KEY (documents_id) REFERENCES documents (id);

alter table promotions add constraint fk_distributeurs_promotions
foreign key (distributeurs_id) references personnes(id);

ALTER TABLE docetats
    ADD CONSTRAINT FK_DOCETATS_ON_ETAPES FOREIGN KEY (etapes_id) REFERENCES etapes (id);

ALTER TABLE docetats
    ADD CONSTRAINT FK_DOCETATS_ON_ETATS FOREIGN KEY (etats_id) REFERENCES etats (id);

ALTER TABLE docetats
    ADD CONSTRAINT FK_DOCETATS_ON_VALIDATIONS FOREIGN KEY (validations_id) REFERENCES validations (id);
ALTER TABLE validations
    ADD CONSTRAINT FK_VALIDATIONS_ON_ROLES FOREIGN KEY (roles_id) REFERENCES roles (id);
ALTER TABLE ticketsfilesattentes
    ADD CONSTRAINT FK_TICKETSFILESATTENTES_ON_FILESATTENTES FOREIGN KEY (filesattentes_id) REFERENCES filesattentes (id);

ALTER TABLE ticketsfilesattentes
    ADD CONSTRAINT FK_TICKETSFILESATTENTES_ON_TICKETS FOREIGN KEY (tickets_id) REFERENCES tickets (id);
ALTER TABLE services
    ADD CONSTRAINT uc_services_codeunique UNIQUE (codeunique);

ALTER TABLE services
    ADD CONSTRAINT uc_services_filesattentes UNIQUE (filesattentes_id);

ALTER TABLE services
    ADD CONSTRAINT FK_SERVICES_ON_FILESATTENTES FOREIGN KEY (filesattentes_id) REFERENCES filesattentes (id);
ALTER TABLE ressources
    ADD CONSTRAINT FK_RESSOURCES_ON_FAMILLES FOREIGN KEY (familles_id) REFERENCES familles (id);

ALTER TABLE ressourcespromotions
    ADD CONSTRAINT fk_res_on_promotions_entity FOREIGN KEY (promotions_id) REFERENCES promotions (id);

ALTER TABLE ressourcespromotions
    ADD CONSTRAINT fk_res_on_ressources_entity FOREIGN KEY (ressources_id) REFERENCES ressources (id);

ALTER TABLE ressourcespromotions
    ADD CONSTRAINT pk_ressourcespromotions PRIMARY KEY (ressources_id,promotions_id);

ALTER TABLE remplir
    ADD CONSTRAINT FK_REMPLIR_ON_MISSIONS FOREIGN KEY (missions_id) REFERENCES missions (id);

ALTER TABLE remplir
    ADD CONSTRAINT FK_REMPLIR_ON_ROLES FOREIGN KEY (roles_id) REFERENCES roles (id);
ALTER TABLE promotions
    ADD CONSTRAINT uc_promotions_codeunique UNIQUE (codeunique);

--ALTER TABLE promotions
--    ADD CONSTRAINT FK_PROMOTIONS_ON_DISTRIBUTEURS_ENTITY FOREIGN KEY (distributeurs_id) REFERENCES distributeurs (distributeurs_id);

ALTER TABLE documentspromotions
    ADD CONSTRAINT fk_doc_on_documents_entity FOREIGN KEY (documents_id) REFERENCES documents (id);

ALTER TABLE documentspromotions
    ADD CONSTRAINT fk_doc_on_promotions_entity FOREIGN KEY (promotions_id) REFERENCES promotions (id);

ALTER TABLE documentspromotions
    ADD CONSTRAINT pk_documentspromotions PRIMARY KEY (documents_id,promotions_id);

ALTER TABLE famillespromotions
    ADD CONSTRAINT fk_fam_on_familles_entity FOREIGN KEY (familles_id) REFERENCES familles (id);

ALTER TABLE famillespromotions
    ADD CONSTRAINT fk_fam_on_promotions_entity FOREIGN KEY (promotions_id) REFERENCES promotions (id);

ALTER TABLE famillespromotions
    ADD CONSTRAINT pk_famillespromotions PRIMARY KEY(familles_id,promotions_id);

ALTER TABLE precomouvementsqtes
    ADD CONSTRAINT FK_PRECOMOUVEMENTSQTES_ON_PRECOMOUVEMENTS FOREIGN KEY (precomouvements_id) REFERENCES precomouvements (id);

ALTER TABLE precomouvementsqtes
    ADD CONSTRAINT FK_PRECOMOUVEMENTSQTES_ON_RESSOURCES FOREIGN KEY (ressources_id) REFERENCES ressources (id);

ALTER TABLE rattacher
    ADD CONSTRAINT fk_rattacher_on_personnes FOREIGN KEY (personnes_id) REFERENCES personnes (id);

ALTER TABLE rattacher
    ADD CONSTRAINT fk_rattacher_on_personnes_bis FOREIGN KEY (rattacher_id) REFERENCES personnes (id);

ALTER TABLE rattacher
    ADD CONSTRAINT pk_rattacher PRIMARY KEY (personnes_id,rattacher_id);

--ALTER TABLE personnesmorales
--    ADD CONSTRAINT FK_PERSONNESMORALES_ON_PERSONNESMORALES FOREIGN KEY (personnesmorales_id) REFERENCES personnes (id);

--ALTER TABLE rattacher
--    ADD CONSTRAINT fk_rattacher_on_personnes FOREIGN KEY (personnes_id) REFERENCES personnes (id);
--ALTER TABLE rattacher
--    ADD CONSTRAINT fk_rattacher_on_personnes FOREIGN KEY (personnes_id) REFERENCES personnes (id);
ALTER TABLE ordreetats
    ADD CONSTRAINT FK_ORDREETATS_ON_ETATS FOREIGN KEY (etats_id) REFERENCES etats (id);
--ALTER TABLE mouvements
--    ADD CONSTRAINT FK_MOUVEMENTS_ON_DISTRIBUTEURS FOREIGN KEY (distributeurs_id) REFERENCES distributeurs (distributeurs_id);

ALTER TABLE mouvements
    ADD CONSTRAINT FK_MOUVEMENTS_ON_RESSOURCES FOREIGN KEY (ressources_id) REFERENCES ressources (id);

ALTER TABLE respecter
    ADD CONSTRAINT fk_respecter_on_mouvements_entity FOREIGN KEY (mouvements_id) REFERENCES mouvements (id);

ALTER TABLE respecter
    ADD CONSTRAINT fk_respecter_on_preco_mouvements_entity FOREIGN KEY (precomouvements_id) REFERENCES precomouvements (id);

ALTER TABLE respecter
    ADD CONSTRAINT PK_RESPECTER PRIMARY KEY (mouvements_id,precomouvements_id);

ALTER TABLE violer
    ADD CONSTRAINT fk_violer_on_mouvements_entity FOREIGN KEY (mouvements_id) REFERENCES mouvements (id);

ALTER TABLE violer
    ADD CONSTRAINT fk_violer_on_preco_mouvements_entity FOREIGN KEY (precomouvements_id) REFERENCES precomouvements (id);

ALTER TABLE violer
    ADD CONSTRAINT pk_violer PRIMARY KEY (precomouvements_id,mouvements_id);

ALTER TABLE missions
    ADD CONSTRAINT FK_MISSIONS_ON_SERVICES FOREIGN KEY (services_id) REFERENCES services (id);

ALTER TABLE traiter
    ADD CONSTRAINT fk_traiter_on_documents_entity FOREIGN KEY (documents_id) REFERENCES documents (id);

ALTER TABLE traiter
    ADD CONSTRAINT fk_traiter_on_missions_entity FOREIGN KEY (missions_id) REFERENCES missions (id);

ALTER TABLE traiter
    ADD CONSTRAINT pk_traiter PRIMARY KEY (missions_id,documents_id);

ALTER TABLE jouerroles
    ADD CONSTRAINT FK_JOUERROLES_ON_PERSONNELS FOREIGN KEY (personnels_id) REFERENCES personnels (id);

ALTER TABLE jouerroles
    ADD CONSTRAINT FK_JOUERROLES_ON_ROLES FOREIGN KEY (roles_id) REFERENCES roles (id);
ALTER TABLE filesattentes
    ADD CONSTRAINT uc_filesattentes_services UNIQUE (services_id);

ALTER TABLE filesattentes
    ADD CONSTRAINT FK_FILESATTENTES_ON_SERVICES FOREIGN KEY (services_id) REFERENCES services (id);

ALTER TABLE sapplique
    ADD CONSTRAINT fk_sapplique_on_familles_entity FOREIGN KEY (familles_id) REFERENCES familles (id);

ALTER TABLE sapplique
    ADD CONSTRAINT fk_sapplique_on_preco_mouvements_qtes_entity FOREIGN KEY (precomouvementsqtes_id) REFERENCES precomouvementsqtes (id);

ALTER TABLE sapplique
    ADD CONSTRAINT pk_sapplique PRIMARY KEY (familles_id,precomouvementsqtes_id);

ALTER TABLE etapes
    ADD CONSTRAINT FK_ETAPES_ON_PARCOURS FOREIGN KEY (parcours_id) REFERENCES parcours (id);

ALTER TABLE constituer
    ADD CONSTRAINT fk_con_on_attributs_entity FOREIGN KEY (attributs_id) REFERENCES attributs (id);

ALTER TABLE constituer
    ADD CONSTRAINT fk_con_on_documents_entity FOREIGN KEY (documents_id) REFERENCES documents (id);

ALTER TABLE constituer
    ADD CONSTRAINT pk_constituer PRIMARY KEY (attributs_id,documents_id);

ALTER TABLE suivre
    ADD CONSTRAINT fk_suivre_on_documents_entity FOREIGN KEY (documents_id) REFERENCES documents (id);

ALTER TABLE suivre
    ADD CONSTRAINT fk_suivre_on_preco_mouvements_entity FOREIGN KEY (precomouvements_id) REFERENCES precomouvements (id);

ALTER TABLE suivre
    ADD CONSTRAINT PK_suivre PRIMARY KEY (documents_id,precomouvements_id);

ALTER TABLE concerner
    ADD CONSTRAINT fk_concerner_on_distributeurs_entity FOREIGN KEY (distributeurs_id) REFERENCES personnes (id);

ALTER TABLE concerner
    ADD CONSTRAINT fk_concerner_on_preco_mouvements_qtes_entity FOREIGN KEY (precomouvementsqtes_id) REFERENCES precomouvementsqtes (id);

ALTER TABLE concerner
    ADD CONSTRAINT pk_concerner PRIMARY KEY (precomouvementsqtes_id,distributeurs_id);

ALTER TABLE associer
    ADD CONSTRAINT FK_ASSOCIER_ON_ATTRIBUTS FOREIGN KEY (attributs_id) REFERENCES attributs (id);

ALTER TABLE associer
    ADD CONSTRAINT FK_ASSOCIER_ON_CATEGORIES FOREIGN KEY (categories_id) REFERENCES categories (id);

ALTER TABLE categories
    ADD CONSTRAINT FK_CATEGORIES_ON_DOCUMENTS FOREIGN KEY (documents_id) REFERENCES documents (id);