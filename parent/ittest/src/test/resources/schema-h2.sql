-- DROP DATABASE IF EXISTS parcours;
CREATE SCHEMA IF NOT EXISTS document AUTHORIZATION sa;
-- generate tables from entities
CREATE TABLE IF NOT EXISTS document.attributs
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
CREATE TABLE IF NOT EXISTS document.categories
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
CREATE TABLE IF NOT EXISTS document.associer
(
    obligatoire   BOOLEAN,
    ordre         INTEGER,
    attributs_id  VARCHAR(255) NOT NULL,
    categories_id VARCHAR(255) NOT NULL,
    CONSTRAINT pk_associer PRIMARY KEY (attributs_id, categories_id)
);
CREATE TABLE IF NOT EXISTS document.concerner
(
    distributeurs_id       VARCHAR(255) NOT NULL,
    precomouvementsqtes_id VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS constituer
(
    attributs_id VARCHAR(255) NOT NULL,
    documents_id VARCHAR(255) NOT NULL
);

CREATE TABLE documents
(
    id                   VARCHAR(255)         NOT NULL,
    titre                VARCHAR(255) NOT NULL,
    description          VARCHAR(255),
    etat                 BOOLEAN,
    datecreation         DATE,
    datemodification     DATE,
    typemouvement        VARCHAR(255),
    afficherprix         BOOLEAN,
    afficherunite        BOOLEAN,
    afficherdistributeur BOOLEAN,
    prixeditable         BOOLEAN,
    contientressources   BOOLEAN,
    estencaissable BOOLEAN,
    CONSTRAINT pk_documents PRIMARY KEY (id)
);
CREATE TABLE IF NOT EXISTS document.suivre
(
    documents_id       VARCHAR(255) NOT NULL,
    precomouvements_id VARCHAR(255) NOT NULL
);
CREATE TABLE IF NOT EXISTS document.etats
(
    id               VARCHAR(255) NOT NULL,
    libelle          VARCHAR(255),
    description      VARCHAR(255),
    datecreation     DATE,
    datemodification DATE,
    CONSTRAINT pk_etats PRIMARY KEY (id)
);
CREATE TABLE IF NOT EXISTS document.etapes
(
    id               VARCHAR(255) NOT NULL,
    libelle          VARCHAR(255),
    etat             VARCHAR(255),
    datemodification DATE,
    parcours_id      VARCHAR(255),
    CONSTRAINT pk_etapes PRIMARY KEY (id)
);
CREATE TABLE IF NOT EXISTS document.familles
(
    id               VARCHAR(255) NOT NULL,
    libelle          VARCHAR(255),
    description      VARCHAR(255),
    etat             BOOLEAN,
    datecreation     DATE,
    datemodification DATE,
    CONSTRAINT pk_familles PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS document.sapplique
(
    familles_id            VARCHAR(255) NOT NULL,
    precomouvementsqtes_id VARCHAR(255) NOT NULL
);
CREATE TABLE IF NOT EXISTS document.filesattentes
(
    id               VARCHAR(255) NOT NULL,
    datecreation     DATE,
    datemodification DATE,
    etat             BOOLEAN,
    services_id      VARCHAR(255),
    CONSTRAINT pk_filesattentes PRIMARY KEY (id)
);
CREATE TABLE IF NOT EXISTS document.jouerroles
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
CREATE TABLE IF NOT EXISTS document.missions
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

CREATE TABLE IF NOT EXISTS document.traiter
(
    documents_id VARCHAR(255) NOT NULL,
    missions_id  VARCHAR(255) NOT NULL
);
CREATE TABLE IF NOT EXISTS document.mouvements
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

CREATE TABLE IF NOT EXISTS document.mouvementcaisses
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

CREATE TABLE IF NOT EXISTS document.respecter
(
    mouvements_id      VARCHAR(255) NOT NULL,
    precomouvements_id VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS document.violer
(
    mouvements_id      VARCHAR(255) NOT NULL,
    precomouvements_id VARCHAR(255) NOT NULL
);
CREATE TABLE IF NOT EXISTS document.ordreetats
(
    id               VARCHAR(255) NOT NULL,
    datecreation     DATE,
    datemodification DATE,
    datefinvote      DATE,
    ordre            INTEGER,
    etats_id         VARCHAR(255),
    CONSTRAINT pk_ordreetats PRIMARY KEY (id)
);
CREATE TABLE IF NOT EXISTS document.parcours
(
    id               VARCHAR(255) NOT NULL,
    libelle          VARCHAR(255),
    datecreation     DATE,
    datemodification DATE,
    CONSTRAINT pk_parcours PRIMARY KEY (id)
);
CREATE TABLE IF NOT EXISTS document.personnels
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
CREATE TABLE IF NOT EXISTS document.deltasoldes (
    id               VARCHAR(255) NOT NULL,
    montantavant             DOUBLE PRECISION,
    montantapres             DOUBLE PRECISION,
    datecreation               date,
    typemouvement   VARCHAR(255) NOT NULL,
    comptes_id  VARCHAR(255) NOT NULL,
    exemplaires_id  VARCHAR(255) NOT NULL,
    CONSTRAINT pk_deltasoldes PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS document.comptes (
    id               VARCHAR(255) NOT NULL,
    solde               DOUBLE PRECISION,
    datecreation               date,
    etat    VARCHAR(255),
    montantdecouvertmax             DOUBLE PRECISION,
    libelle VARCHAR(255),
    personnes_id VARCHAR(255) NOT NULL,
    CONSTRAINT pk_comptes PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS document.caisses(
    id               VARCHAR(255) NOT NULL,
    libelle VARCHAR(255),
    solde   DOUBLE PRECISION,
    type VARCHAR(255),
    detailJSON VARCHAR(255),
    CONSTRAINT pk_caisses PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS document.exemplaires (
    id               VARCHAR(255) NOT NULL,
    personnes_id    VARCHAR(255) NOT NULL,
    documents_id    VARCHAR(255) NOT NULL,
    CONSTRAINT pk_exemplaires PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS document.personnes
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
CREATE TABLE IF NOT EXISTS document.precomouvements
(
    id               VARCHAR(255)         NOT NULL,
    libelle          VARCHAR(255) NOT NULL,
    etat             BOOLEAN,
    datecreation     date         NOT NULL,
    datemodification date,
    typemouvement    VARCHAR(255),
    CONSTRAINT pk_precomouvements PRIMARY KEY (id)
);
CREATE TABLE IF NOT EXISTS document.precomouvementsqtes
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
CREATE TABLE IF NOT EXISTS document.documentspromotions
(
    documents_id  VARCHAR(255) NOT NULL,
    promotions_id VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS document.famillespromotions
(
    familles_id   VARCHAR(255) NOT NULL,
    promotions_id VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS document.promotions
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
CREATE TABLE IF NOT EXISTS document.remplir
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
CREATE TABLE IF NOT EXISTS document.ressources
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

CREATE TABLE IF NOT EXISTS document.ressourcespromotions
(
    promotions_id VARCHAR(255) NOT NULL,
    ressources_id VARCHAR(255) NOT NULL
);
CREATE TABLE IF NOT EXISTS document.roles
(
    id               VARCHAR(255) NOT NULL,
    titre            VARCHAR(255),
    description      VARCHAR(255),
    etat             BOOLEAN,
    datecreation     DATE,
    datemodification DATE,
    CONSTRAINT pk_roles PRIMARY KEY (id)
);
CREATE TABLE IF NOT EXISTS document.services
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
CREATE TABLE IF NOT EXISTS document.tickets
(
    id               VARCHAR(255) NOT NULL,
    codecourt        VARCHAR(255),
    datecreation     DATE,
    datemodification DATE,
    CONSTRAINT pk_tickets PRIMARY KEY (id)
);
CREATE TABLE IF NOT EXISTS document.ticketsfilesattentes
(
    id               VARCHAR(255) NOT NULL,
    etat             BOOLEAN,
    dateaffectation  DATE,
    tickets_id       VARCHAR(255),
    filesattentes_id VARCHAR(255),
    CONSTRAINT pk_ticketsfilesattentes PRIMARY KEY (id)
);
CREATE TABLE IF NOT EXISTS document.validations
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
CREATE TABLE IF NOT EXISTS document.docetats
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
CREATE TABLE IF NOT EXISTS document.docetats_predecesseurs
(
    docetats_id       VARCHAR(255) NOT NULL,
    predecesseur_id VARCHAR(255) NOT NULL,
    CONSTRAINT pk_docetats_predecesseurs PRIMARY KEY (docetats_id, predecesseur_id)
);



CREATE TABLE document.actions (
    id VARCHAR(255) NOT NULL PRIMARY KEY,
    libelle VARCHAR(255),
    etat BOOLEAN,
    datecreation DATE,
    datemodification DATE,
    elementsbase_id VARCHAR(255)
);

CREATE TABLE document.elementsbases (

    id VARCHAR(255) NOT NULL PRIMARY KEY,
    libelle VARCHAR(255),
    etat BOOLEAN,
    datesouscription DATE,
    datemodification DATE,
    moduleangular VARCHAR(255)
);

CREATE TABLE document.actionslangues (

    langues_id VARCHAR(255),
    actions_id VARCHAR(255),
    valeurlibelle VARCHAR(255)
);

CREATE TABLE document.langues (

    id VARCHAR(255) NOT NULL PRIMARY KEY,
    libelle VARCHAR(255),
    etat BOOLEAN,
    datesouscription DATE,
    datemodification DATE
);

CREATE TABLE document.elementsbaselanques (

    langues_id VARCHAR(255),
    elementsbases_id VARCHAR(255),
    valeurlibelle VARCHAR(255)
);

CREATE TABLE document.elementslangues(
    langues_id VARCHAR(255),
    elements_id VARCHAR(255),
    valeurlibelle VARCHAR(255)
);

CREATE TABLE document.elements(
    id VARCHAR(255) NOT NULL PRIMARY KEY,
    libelle VARCHAR(255),
    etat BOOLEAN,
    datesouscription DATE,
    datemodification DATE,
    menus_id VARCHAR(255),
    elementsbases_id VARCHAR(255)
);

CREATE TABLE document.menus (
    id VARCHAR(255) NOT NULL PRIMARY KEY,
    etat BOOLEAN,
    datecreation DATE,
    utilisateurs_id VARCHAR(255),
    groupes_id VARCHAR(255)
);

CREATE TABLE document.utilisateurs (
    id VARCHAR(255) NOT NULL PRIMARY KEY,
    login VARCHAR(255),
    mdp VARCHAR(255),
    etat VARCHAR(255),
    datecreation DATE,
    datemodification DATE,
    groupes_id VARCHAR(255),
    menus_id VARCHAR(255)
);

CREATE TABLE document.organisations (
    id VARCHAR(255) NOT NULL PRIMARY KEY,
    raisonsociale VARCHAR(255),
);

CREATE TABLE document.organiser (
    organisations_id VARCHAR(255),
    utilisateurs_id VARCHAR(255)
);

CREATE TABLE document.groupes (
    id VARCHAR(255) NOT NULL PRIMARY KEY,
    libelle VARCHAR(255),
    etat BOOLEAN,
    datecreation DATE,
    datemodification DATE,
    menus_id VARCHAR(255)
);

CREATE TABLE document.actions (
    id VARCHAR(255) NOT NULL PRIMARY KEY,
    libelle VARCHAR(255),
    etat BOOLEAN,
    datecreation DATE,
    datemodification DATE,
    elementsbase_id VARCHAR(255)
);

CREATE TABLE document.elementsbases (

    id VARCHAR(255) NOT NULL PRIMARY KEY,
    libelle VARCHAR(255),
    etat BOOLEAN,
    datesouscription DATE,
    datemodification DATE,
    moduleangular VARCHAR(255)
);

CREATE TABLE document.actionslangues (

    langues_id VARCHAR(255),
    actions_id VARCHAR(255),
    valeurlibelle VARCHAR(255)
);

CREATE TABLE document.langues (

    id VARCHAR(255) NOT NULL PRIMARY KEY,
    libelle VARCHAR(255),
    etat BOOLEAN,
    datesouscription DATE,
    datemodification DATE
);

CREATE TABLE document.elementsbaselanques (

    langues_id VARCHAR(255),
    elementsbases_id VARCHAR(255),
    valeurlibelle VARCHAR(255)
);

CREATE TABLE document.elementslangues(
    langues_id VARCHAR(255),
    elements_id VARCHAR(255),
    valeurlibelle VARCHAR(255)
);

CREATE TABLE document.elements(
    id VARCHAR(255) NOT NULL PRIMARY KEY,
    libelle VARCHAR(255),
    etat BOOLEAN,
    datesouscription DATE,
    datemodification DATE,
    menus_id VARCHAR(255),
    elementsbases_id VARCHAR(255)
);

CREATE TABLE document.menus (
    id VARCHAR(255) NOT NULL PRIMARY KEY,
    etat BOOLEAN,
    datecreation DATE,
    utilisateurs_id VARCHAR(255),
    groupes_id VARCHAR(255)
);

CREATE TABLE document.utilisateurs (
    id VARCHAR(255) NOT NULL PRIMARY KEY,
    login VARCHAR(255),
    mdp VARCHAR(255),
    etat VARCHAR(255),
    datecreation DATE,
    datemodification DATE,
    groupes_id VARCHAR(255),
    menus_id VARCHAR(255)
);

CREATE TABLE document.organisations (
    id VARCHAR(255) NOT NULL PRIMARY KEY,
    raisonsociale VARCHAR(255)
);

CREATE TABLE document.organiser (
    organisations_id VARCHAR(255),
    utilisateurs_id VARCHAR(255)
);

CREATE TABLE document.groupes (
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

ALTER TABLE docetats
    ADD CONSTRAINT FK_DOCETATS_ON_ETAPES FOREIGN KEY (etapes_id) REFERENCES etapes (id);

ALTER TABLE document.docetats
    ADD CONSTRAINT IF NOT EXISTS  FK_DOCETATS_ON_ETAPES FOREIGN KEY (etapes_id) REFERENCES etapes (id);

ALTER TABLE document.docetats
    ADD CONSTRAINT IF NOT EXISTS  FK_DOCETATS_ON_ETATS FOREIGN KEY (etats_id) REFERENCES etats (id);

ALTER TABLE document.docetats
    ADD CONSTRAINT IF NOT EXISTS  FK_DOCETATS_ON_VALIDATIONS FOREIGN KEY (validations_id) REFERENCES validations (id);
ALTER TABLE document.validations
    ADD CONSTRAINT IF NOT EXISTS  FK_VALIDATIONS_ON_ROLES FOREIGN KEY (roles_id) REFERENCES roles (id);
ALTER TABLE document.ticketsfilesattentes
    ADD CONSTRAINT IF NOT EXISTS  FK_TICKETSFILESATTENTES_ON_FILESATTENTES FOREIGN KEY (filesattentes_id) REFERENCES filesattentes (id);

ALTER TABLE document.ticketsfilesattentes
    ADD CONSTRAINT IF NOT EXISTS  FK_TICKETSFILESATTENTES_ON_TICKETS FOREIGN KEY (tickets_id) REFERENCES tickets (id);
ALTER TABLE document.services
    ADD CONSTRAINT IF NOT EXISTS  uc_services_codeunique UNIQUE (codeunique);

ALTER TABLE document.services
    ADD CONSTRAINT IF NOT EXISTS  uc_services_filesattentes UNIQUE (filesattentes_id);

ALTER TABLE document.services
    ADD CONSTRAINT IF NOT EXISTS  FK_SERVICES_ON_FILESATTENTES FOREIGN KEY (filesattentes_id) REFERENCES filesattentes (id);
ALTER TABLE document.ressources
    ADD CONSTRAINT IF NOT EXISTS  FK_RESSOURCES_ON_FAMILLES FOREIGN KEY (familles_id) REFERENCES familles (id);

ALTER TABLE document.ressourcespromotions
    ADD CONSTRAINT IF NOT EXISTS  fk_res_on_promotions_entity FOREIGN KEY (promotions_id) REFERENCES promotions (id);

ALTER TABLE document.ressourcespromotions
    ADD CONSTRAINT IF NOT EXISTS  fk_res_on_ressources_entity FOREIGN KEY (ressources_id) REFERENCES ressources (id);
ALTER TABLE document.remplir
    ADD CONSTRAINT IF NOT EXISTS  FK_REMPLIR_ON_MISSIONS FOREIGN KEY (missions_id) REFERENCES missions (id);

ALTER TABLE document.remplir
    ADD CONSTRAINT IF NOT EXISTS  FK_REMPLIR_ON_ROLES FOREIGN KEY (roles_id) REFERENCES roles (id);
ALTER TABLE document.promotions
    ADD CONSTRAINT IF NOT EXISTS  uc_promotions_codeunique UNIQUE (codeunique);

ALTER TABLE document.promotions
    ADD CONSTRAINT IF NOT EXISTS  FK_PROMOTIONS_ON_DISTRIBUTEURS_ENTITY FOREIGN KEY (distributeurs_id) REFERENCES distributeurs (distributeurs_id);

ALTER TABLE document.documentspromotions
    ADD CONSTRAINT IF NOT EXISTS  fk_doc_on_documents_entity FOREIGN KEY (documents_id) REFERENCES documents (id);

ALTER TABLE document.famillespromotions
    ADD CONSTRAINT fk_fam_on_familles_entity FOREIGN KEY (familles_id) REFERENCES familles (id);

ALTER TABLE document.famillespromotions
    ADD CONSTRAINT fk_fam_on_promotions_entity FOREIGN KEY (promotions_id) REFERENCES promotions (id);
ALTER TABLE document.precomouvementsqtes
    ADD CONSTRAINT FK_PRECOMOUVEMENTSQTES_ON_PRECOMOUVEMENTS FOREIGN KEY (precomouvements_id) REFERENCES precomouvements (id);

ALTER TABLE document.precomouvementsqtes
    ADD CONSTRAINT FK_PRECOMOUVEMENTSQTES_ON_RESSOURCES FOREIGN KEY (ressources_id) REFERENCES ressources (id);
ALTER TABLE document.personnesphysique
    ADD CONSTRAINT FK_PERSONNESPHYSIQUE_ON_PERSONNESPHYSIQUE FOREIGN KEY (personnesphysique_id) REFERENCES personnes (id);

ALTER TABLE document.rattacher
    ADD CONSTRAINT fk_rattacher_on_personnes FOREIGN KEY (personnes_id) REFERENCES personnes (id);

ALTER TABLE document.rattacher
    ADD CONSTRAINT fk_rattacher_on_personnes_bis FOREIGN KEY (rattacher_id) REFERENCES personnes (id);

ALTER TABLE document.personnesmorales
    ADD CONSTRAINT FK_PERSONNESMORALES_ON_PERSONNESMORALES FOREIGN KEY (personnesmorales_id) REFERENCES personnes (id);

--ALTER TABLE rattacher
--    ADD CONSTRAINT fk_rattacher_on_personnes FOREIGN KEY (personnes_id) REFERENCES personnes (id);
--ALTER TABLE rattacher
--    ADD CONSTRAINT fk_rattacher_on_personnes FOREIGN KEY (personnes_id) REFERENCES personnes (id);
ALTER TABLE document.ordreetats
    ADD CONSTRAINT FK_ORDREETATS_ON_ETATS FOREIGN KEY (etats_id) REFERENCES etats (id);
ALTER TABLE document.mouvements
    ADD CONSTRAINT FK_MOUVEMENTS_ON_DISTRIBUTEURS FOREIGN KEY (distributeurs_id) REFERENCES distributeurs (distributeurs_id);

ALTER TABLE document.mouvements
    ADD CONSTRAINT FK_MOUVEMENTS_ON_RESSOURCES FOREIGN KEY (ressources_id) REFERENCES ressources (id);

ALTER TABLE document.respecter
    ADD CONSTRAINT fk_respecter_on_mouvements_entity FOREIGN KEY (mouvements_id) REFERENCES mouvements (id);

ALTER TABLE document.respecter
    ADD CONSTRAINT fk_respecter_on_preco_mouvements_entity FOREIGN KEY (precomouvements_id) REFERENCES precomouvements (id);

ALTER TABLE document.violer
    ADD CONSTRAINT fk_violer_on_mouvements_entity FOREIGN KEY (mouvements_id) REFERENCES mouvements (id);

ALTER TABLE document.violer
    ADD CONSTRAINT fk_violer_on_preco_mouvements_entity FOREIGN KEY (precomouvements_id) REFERENCES precomouvements (id);
ALTER TABLE missions
    ADD CONSTRAINT FK_MISSIONS_ON_SERVICES FOREIGN KEY (services_id) REFERENCES services (id);

ALTER TABLE document.traiter
    ADD CONSTRAINT fk_traiter_on_documents_entity FOREIGN KEY (documents_id) REFERENCES documents (id);

ALTER TABLE document.traiter
    ADD CONSTRAINT fk_traiter_on_missions_entity FOREIGN KEY (missions_id) REFERENCES missions (id);
ALTER TABLE jouerroles
    ADD CONSTRAINT FK_JOUERROLES_ON_PERSONNELS FOREIGN KEY (personnels_id) REFERENCES personnels (id);

ALTER TABLE document.jouerroles
    ADD CONSTRAINT FK_JOUERROLES_ON_ROLES FOREIGN KEY (roles_id) REFERENCES roles (id);
ALTER TABLE document.filesattentes
    ADD CONSTRAINT uc_filesattentes_services UNIQUE (services_id);

ALTER TABLE document.filesattentes
    ADD CONSTRAINT FK_FILESATTENTES_ON_SERVICES FOREIGN KEY (services_id) REFERENCES services (id);

ALTER TABLE document.sapplique
    ADD CONSTRAINT fk_sapplique_on_familles_entity FOREIGN KEY (familles_id) REFERENCES familles (id);

ALTER TABLE document.sapplique
    ADD CONSTRAINT fk_sapplique_on_preco_mouvements_qtes_entity FOREIGN KEY (precomouvementsqtes_id) REFERENCES precomouvementsqtes (id);
ALTER TABLE document.etapes
    ADD CONSTRAINT FK_ETAPES_ON_PARCOURS FOREIGN KEY (parcours_id) REFERENCES parcours (id);
ALTER TABLE document.constituer
    ADD CONSTRAINT fk_con_on_attributs_entity FOREIGN KEY (attributs_id) REFERENCES attributs (id);

ALTER TABLE document.constituer
    ADD CONSTRAINT fk_con_on_documents_entity FOREIGN KEY (documents_id) REFERENCES documents (id);

ALTER TABLE document.suivre
    ADD CONSTRAINT fk_suivre_on_documents_entity FOREIGN KEY (documents_id) REFERENCES documents (id);

ALTER TABLE document.suivre
    ADD CONSTRAINT fk_suivre_on_preco_mouvements_entity FOREIGN KEY (precomouvements_id) REFERENCES precomouvements (id);
ALTER TABLE distributeurs
    ADD CONSTRAINT FK_DISTRIBUTEURS_ON_DISTRIBUTEURS FOREIGN KEY (distributeurs_id) REFERENCES personnes (id);

ALTER TABLE document.concerner
    ADD CONSTRAINT fk_concerner_on_distributeurs_entity FOREIGN KEY (precomouvementsqtes_id) REFERENCES distributeurs (distributeurs_id);

ALTER TABLE document.concerner
    ADD CONSTRAINT fk_concerner_on_preco_mouvements_qtes_entity FOREIGN KEY (distributeurs_id) REFERENCES precomouvementsqtes (id);

ALTER TABLE document.associer
    ADD CONSTRAINT FK_ASSOCIER_ON_ATTRIBUTS FOREIGN KEY (attributs_id) REFERENCES attributs (id);

ALTER TABLE document.associer
    ADD CONSTRAINT FK_ASSOCIER_ON_CATEGORIES FOREIGN KEY (categories_id) REFERENCES categories (id);

ALTER TABLE document.categories
    ADD CONSTRAINT FK_CATEGORIES_ON_DOCUMENTS FOREIGN KEY (documents_id) REFERENCES documents (id);