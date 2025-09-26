-- Suppression des schémas et tables existants pour une réinitialisation complète
DROP SCHEMA IF EXISTS document CASCADE;

-- Création du schéma
CREATE SCHEMA IF NOT EXISTS document;

-- Tables
CREATE TABLE IF NOT EXISTS document.actions (
    id VARCHAR(255) NOT NULL,
    libelle VARCHAR(255),
    etat BOOLEAN,
    datecreation DATE,
    datemodification DATE,
    elementsbase_id VARCHAR(255),
    CONSTRAINT pk_actions PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS document.actionslangues (
    langues_id VARCHAR(255) NOT NULL,
    actions_id VARCHAR(255) NOT NULL,
    valeurlibelle VARCHAR(255),
    CONSTRAINT pk_actionslangues PRIMARY KEY (langues_id, actions_id)
);

CREATE TABLE IF NOT EXISTS document.associer (
    obligatoire BOOLEAN,
    ordre INTEGER,
    attributs_id VARCHAR(255) NOT NULL,
    categories_id VARCHAR(255) NOT NULL,
    CONSTRAINT pk_associer PRIMARY KEY (attributs_id, categories_id)
);

CREATE TABLE IF NOT EXISTS document.attributs (
    id VARCHAR(255) NOT NULL,
    titre VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    etat BOOLEAN DEFAULT TRUE,
    datecreation DATE,
    datemodification DATE,
    valeurpardefaut VARCHAR(255),
    type_attribut VARCHAR(255) NOT NULL,
    CONSTRAINT pk_attributs PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS document.caisses (
    id VARCHAR(255) NOT NULL,
    libelle VARCHAR(255),
    solde DOUBLE PRECISION,
    type VARCHAR(255),
    detailjson VARCHAR(255),
    etat BOOLEAN
);

CREATE TABLE IF NOT EXISTS document.categories (
    id VARCHAR(255) NOT NULL,
    ordre VARCHAR(255),
    libelle VARCHAR(255),
    etat BOOLEAN DEFAULT TRUE,
    datecreation DATE,
    datemodification DATE,
    documents_id VARCHAR(255),
    CONSTRAINT pk_categories PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS document.comptes (
    id VARCHAR(255) NOT NULL,
    datecreation DATE,
    etat VARCHAR(255),
    montantdecouvertmax DOUBLE PRECISION,
    libelle VARCHAR(255),
    personnes_id VARCHAR(255),
    solde DOUBLE PRECISION,
    CONSTRAINT pk_comptes PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS document.concerner (
    distributeurs_id VARCHAR(255) NOT NULL,
    precomouvementsqtes_id VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS document.constituer (
    attributs_id VARCHAR(255) NOT NULL,
    documents_id VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS document.deltasoldes (
    id VARCHAR(255) NOT NULL,
    montantavant DOUBLE PRECISION,
    montantapres DOUBLE PRECISION,
    datecreation DATE,
    typemouvement VARCHAR(255) NOT NULL,
    comptes_id VARCHAR(255) NOT NULL,
    exemplaires_id VARCHAR(255) NOT NULL,
    CONSTRAINT pk_deltasoldes PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS document.distributeurs (
    distributeurs_id VARCHAR(255) NOT NULL,
    datemodification DATE,
    code VARCHAR(255),
    raisonsociale VARCHAR(255),
    CONSTRAINT pk_distributeurs PRIMARY KEY (distributeurs_id)
);

CREATE TABLE IF NOT EXISTS document.docetats (
    id VARCHAR(255) NOT NULL,
    ordre INTEGER,
    datecreation DATE,
    datemodification DATE,
    validations_id VARCHAR(255),
    etats_id VARCHAR(255),
    documents_id VARCHAR(255),
    etapes_id VARCHAR(255),
    CONSTRAINT pk_docetats PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS document.docetats_predecesseurs (
    docetats_id VARCHAR(255) NOT NULL,
    predecesseur_id VARCHAR(255) NOT NULL,
    CONSTRAINT pk_docetats_predecesseurs PRIMARY KEY (docetats_id, predecesseur_id)
);

CREATE TABLE IF NOT EXISTS document.documents (
    id VARCHAR(255) NOT NULL,
    titre VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    etat BOOLEAN,
    datecreation DATE,
    datemodification DATE,
    typemouvement VARCHAR(255),
    afficherprix BOOLEAN,
    afficherunite BOOLEAN,
    afficherdistributeur BOOLEAN,
    prixeditable BOOLEAN,
    contientressources BOOLEAN,
    estencaissable BOOLEAN,
    CONSTRAINT pk_documents PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS document.documentspromotions (
    documents_id VARCHAR(255) NOT NULL,
    promotions_id VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS document.elements (
    id VARCHAR(255) NOT NULL,
    libelle VARCHAR(255),
    etat BOOLEAN,
    datesouscription DATE,
    datemodification DATE,
    menus_id VARCHAR(255),
    elementsbases_id VARCHAR(255),
    CONSTRAINT pk_elements PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS document.elementsbases (
    id VARCHAR(255) NOT NULL,
    libelle VARCHAR(255),
    etat BOOLEAN,
    datesouscription DATE,
    datemodification DATE,
    moduleangular VARCHAR(255),
    element_id VARCHAR(255),
    CONSTRAINT pk_elementsbases PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS document.elementsbaselanques (
    langues_id VARCHAR(255) NOT NULL,
    elementsbases_id VARCHAR(255) NOT NULL,
    valeurlibelle VARCHAR(255),
    CONSTRAINT pk_elementsbaselanques PRIMARY KEY (langues_id, elementsbases_id)
);

CREATE TABLE IF NOT EXISTS document.elementslangues (
    langues_id VARCHAR(255) NOT NULL,
    elements_id VARCHAR(255) NOT NULL,
    valeurlibelle VARCHAR(255),
    CONSTRAINT pk_elementslangues PRIMARY KEY (langues_id, elements_id)
);

CREATE TABLE IF NOT EXISTS document.etapes (
    id VARCHAR(255) NOT NULL,
    libelle VARCHAR(255),
    etat VARCHAR(255),
    datemodification DATE,
    parcours_id VARCHAR(255),
    CONSTRAINT pk_etapes PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS document.etats (
    id VARCHAR(255) NOT NULL,
    libelle VARCHAR(255),
    description VARCHAR(255),
    datecreation DATE,
    datemodification DATE,
    CONSTRAINT pk_etats PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS document.exemplaires (
    id VARCHAR(255) NOT NULL,
    personnes_id VARCHAR(255) NOT NULL,
    documents_id VARCHAR(255),
    CONSTRAINT pk_exemplaires PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS document.familles (
    id VARCHAR(255) NOT NULL,
    libelle VARCHAR(255),
    description VARCHAR(255),
    etat BOOLEAN,
    datecreation DATE,
    datemodification DATE,
    CONSTRAINT pk_familles PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS document.famillespromotions (
    familles_id VARCHAR(255) NOT NULL,
    promotions_id VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS document.filesattentes (
    id VARCHAR(255) NOT NULL,
    datecreation DATE,
    datemodification DATE,
    etat BOOLEAN,
    services_id VARCHAR(255),
    CONSTRAINT pk_filesattentes PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS document.groupes (
    id VARCHAR(255) NOT NULL,
    libelle VARCHAR(255),
    etat BOOLEAN,
    datecreation DATE,
    datemodification DATE,
    menus_id VARCHAR(255),
    CONSTRAINT pk_groupes PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS document.jouerroles (
    id VARCHAR(255) NOT NULL,
    etat BOOLEAN,
    datecreation DATE,
    datemodification DATE,
    datefin DATE,
    datedebut DATE,
    personnels_id VARCHAR(255),
    roles_id VARCHAR(255),
    CONSTRAINT pk_jouerroles PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS document.langues (
    id VARCHAR(255) NOT NULL,
    libelle VARCHAR(255),
    etat BOOLEAN,
    datesouscription DATE,
    datemodification DATE,
    CONSTRAINT pk_langues PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS document.menus (
    id VARCHAR(255) NOT NULL,
    etat BOOLEAN,
    datecreation DATE,
    utilisateurs_id VARCHAR(255),
    groupes_id VARCHAR(255),
    CONSTRAINT pk_menus PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS document.missions (
    id VARCHAR(255) NOT NULL,
    libelle VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    etat BOOLEAN,
    datecreation DATE NOT NULL,
    datemodification DATE,
    services_id VARCHAR(255),
    idlogin VARCHAR(255),
    CONSTRAINT pk_missions PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS document.mouvementcaisses (
    id VARCHAR(255) NOT NULL,
    montant DOUBLE PRECISION,
    moyenpaiement VARCHAR(255),
    referencepaiement VARCHAR(255),
    detailjson VARCHAR(255),
    etat VARCHAR(255),
    typemvt VARCHAR(255),
    libelle VARCHAR(255),
    caisses_id VARCHAR(255),
    comptes_id VARCHAR(255),
    personnels_id VARCHAR(255),
    exemplaires_id VARCHAR(255),
    datecreation DATE,
    CONSTRAINT pk_mouvementcaisses PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS document.mouvements (
    id VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    quantite INTEGER,
    prix DOUBLE PRECISION,
    datecreation DATE NOT NULL,
    dateperemption DATE,
    datemodification DATE,
    ressources_id VARCHAR(255),
    distributeurs_id VARCHAR(255),
    CONSTRAINT pk_mouvements PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS document.ordreetats (
    id VARCHAR(255) NOT NULL,
    datecreation DATE,
    datemodification DATE,
    datefinvote DATE,
    ordre INTEGER,
    etats_id VARCHAR(255),
    CONSTRAINT pk_ordreetats PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS document.organisations (
    id VARCHAR(255) NOT NULL,
    raisonsociale VARCHAR(255),
    CONSTRAINT pk_organisations PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS document.organiser (
    organisations_id VARCHAR(255) NOT NULL,
    utilisateurs_id VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS document.parcours (
    id VARCHAR(255) NOT NULL,
    libelle VARCHAR(255),
    datecreation DATE,
    datemodification DATE,
    CONSTRAINT pk_parcours PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS document.personnels (
    personnels_id VARCHAR(255) NOT NULL,
    dateentree DATE,
    nom VARCHAR(255),
    datenaissance DATE,
    telephone VARCHAR(255),
    datesortie DATE,
    email VARCHAR(255),
    prenom VARCHAR(255),
    sexe VARCHAR(255),
    datemodification DATE,
    CONSTRAINT pk_personnels PRIMARY KEY (personnels_id)
);

CREATE TABLE IF NOT EXISTS document.personnes (
    id VARCHAR(255) NOT NULL,
    adresse VARCHAR(255),
    mail VARCHAR(255),
    telephone VARCHAR(255),
    qrcodevalue VARCHAR(255),
    code VARCHAR(255),
    raisonsociale VARCHAR(255),
    datenaissance DATE,
    datecreation DATE,
    datemodification DATE,
    comptes_id VARCHAR(255),
    type VARCHAR(255),
    ticket_id VARCHAR(255),
    etat BOOLEAN,
    CONSTRAINT pk_personnes PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS document.personnesmorales (
    personnesmorales_id VARCHAR(255) NOT NULL,
    raisonsociale VARCHAR(255),
    code VARCHAR(255),
    CONSTRAINT pk_personnesmorales PRIMARY KEY (personnesmorales_id)
);

CREATE TABLE IF NOT EXISTS document.personnesphysique (
    personnesphysique_id VARCHAR(255) NOT NULL,
    nom VARCHAR(255) NOT NULL,
    prenom VARCHAR(255),
    sexe VARCHAR(255),
    datenaissance DATE NOT NULL,
    datemodification DATE,
    CONSTRAINT pk_personnesphysique PRIMARY KEY (personnesphysique_id)
);

CREATE TABLE IF NOT EXISTS document.precomouvements (
    id VARCHAR(255) NOT NULL,
    libelle VARCHAR(255) NOT NULL,
    etat BOOLEAN,
    datecreation DATE NOT NULL,
    datemodification DATE,
    typemouvement VARCHAR(255),
    CONSTRAINT pk_precomouvements PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS document.precomouvementsqtes (
    id VARCHAR(255) NOT NULL,
    qtemin INTEGER,
    qtemax INTEGER,
    montantmin DOUBLE PRECISION,
    montantmax DOUBLE PRECISION,
    datecreation DATE,
    datemodification DATE,
    precomouvements_id VARCHAR(255) NOT NULL,
    ressources_id VARCHAR(255),
    CONSTRAINT pk_precomouvementsqtes PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS document.promotions (
    id VARCHAR(255) NOT NULL,
    datedebut DATE,
    datefin DATE,
    codeunique VARCHAR(255),
    typeremise VARCHAR(255),
    valeurremise DOUBLE PRECISION,
    datecreation DATE,
    datemodification DATE,
    distributeurs_id VARCHAR(255),
    CONSTRAINT pk_promotions PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS document.rattacher (
    personnes_id VARCHAR(255) NOT NULL,
    rattacher_id VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS document.remplir (
    id VARCHAR(255) NOT NULL,
    roles_id VARCHAR(255),
    missions_id VARCHAR(255),
    datefin DATE,
    datecreation DATE,
    datedebut DATE,
    etat BOOLEAN,
    droitajouter BOOLEAN,
    droitmodifier BOOLEAN,
    droitconsulter BOOLEAN,
    droitvalider BOOLEAN,
    CONSTRAINT pk_remplir PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS document.respecter (
    mouvements_id VARCHAR(255) NOT NULL,
    precomouvements_id VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS document.ressources (
    id VARCHAR(255) NOT NULL,
    libelle VARCHAR(255),
    description VARCHAR(255),
    etat BOOLEAN,
    datecreation DATE,
    datemodification DATE,
    quantite INTEGER,
    seuilalerte INTEGER,
    prixentree DOUBLE PRECISION,
    prixsortie DOUBLE PRECISION,
    unite VARCHAR(255),
    familles_id VARCHAR(255),
    CONSTRAINT pk_ressources PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS document.ressourcespromotions (
    promotions_id VARCHAR(255) NOT NULL,
    ressources_id VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS document.roles (
    id VARCHAR(255) NOT NULL,
    titre VARCHAR(255),
    description VARCHAR(255),
    etat BOOLEAN,
    datecreation DATE,
    datemodification DATE,
    CONSTRAINT pk_roles PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS document.sapplique (
    familles_id VARCHAR(255) NOT NULL,
    precomouvementsqtes_id VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS document.services (
    id VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    libelle VARCHAR(255),
    localisation VARCHAR(255),
    etat BOOLEAN,
    datecreation DATE,
    datemodification DATE,
    codeunique VARCHAR(255) NOT NULL,
    filesattentes_id VARCHAR(255),
    CONSTRAINT pk_services PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS document.suivre (
    documents_id VARCHAR(255) NOT NULL,
    precomouvements_id VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS document.tickets (
    id VARCHAR(255) NOT NULL,
    codecourt VARCHAR(255),
    datecreation DATE,
    datemodification DATE,
    personne_id VARCHAR(255),
    statut VARCHAR(255),
    CONSTRAINT pk_tickets PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS document.ticketsfilesattentes (
    id VARCHAR(255) NOT NULL,
    etat BOOLEAN,
    dateaffectation DATE,
    tickets_id VARCHAR(255),
    filesattentes_id VARCHAR(255),
    CONSTRAINT pk_ticketsfilesattentes PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS document.traiter (
    documents_id VARCHAR(255) NOT NULL,
    missions_id VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS document.utilisateurs (
    id VARCHAR(255) NOT NULL,
    login VARCHAR(255),
    mdp VARCHAR(255),
    etat VARCHAR(255),
    datecreation DATE,
    datemodification DATE,
    groupes_id VARCHAR(255),
    menus_id VARCHAR(255),
    CONSTRAINT pk_utilisateurs PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS document.validations (
    id VARCHAR(255) NOT NULL,
    code VARCHAR(255),
    etat VARCHAR(255),
    datecreation DATE,
    datemodification DATE,
    typevote VARCHAR(255),
    dureevote INTEGER,
    typevalidation VARCHAR(255),
    roles_id VARCHAR(255),
    libelle VARCHAR(255),
    CONSTRAINT pk_validations PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS document.violer (
    mouvements_id VARCHAR(255) NOT NULL,
    precomouvements_id VARCHAR(255) NOT NULL
);

-- Ajout des contraintes de clés étrangères et des contraintes d'unicité
ALTER TABLE document.actionslangues
    ADD CONSTRAINT IF NOT EXISTS fk_actions_actionslangues FOREIGN KEY (actions_id) REFERENCES document.actions (id);

ALTER TABLE document.actions
    ADD CONSTRAINT IF NOT EXISTS fk_actions_elementbases FOREIGN KEY (elementsbase_id) REFERENCES document.elementsbases (id);

ALTER TABLE document.associer
    ADD CONSTRAINT IF NOT EXISTS FK_ASSOCIER_ON_ATTRIBUTS FOREIGN KEY (attributs_id) REFERENCES document.attributs (id);

ALTER TABLE document.associer
    ADD CONSTRAINT IF NOT EXISTS FK_ASSOCIER_ON_CATEGORIES FOREIGN KEY (categories_id) REFERENCES document.categories (id);

ALTER TABLE document.categories
    ADD CONSTRAINT IF NOT EXISTS FK_CATEGORIES_ON_DOCUMENTS FOREIGN KEY (documents_id) REFERENCES document.documents (id);

ALTER TABLE document.comptes
    ADD CONSTRAINT IF NOT EXISTS FK_COMPTES_ON_PERSONNES FOREIGN KEY (personnes_id) REFERENCES document.personnes (id);

ALTER TABLE document.concerner
    ADD CONSTRAINT IF NOT EXISTS fk_concerner_on_distributeurs_entity FOREIGN KEY (distributeurs_id) REFERENCES document.distributeurs (distributeurs_id);

ALTER TABLE document.concerner
    ADD CONSTRAINT IF NOT EXISTS fk_concerner_on_preco_mouvements_qtes_entity FOREIGN KEY (precomouvementsqtes_id) REFERENCES document.precomouvementsqtes (id);

ALTER TABLE document.constituer
    ADD CONSTRAINT IF NOT EXISTS fk_con_on_attributs_entity FOREIGN KEY (attributs_id) REFERENCES document.attributs (id);

ALTER TABLE document.constituer
    ADD CONSTRAINT IF NOT EXISTS fk_con_on_documents_entity FOREIGN KEY (documents_id) REFERENCES document.documents (id);

ALTER TABLE document.deltasoldes
    ADD CONSTRAINT IF NOT EXISTS FK_DELTASOLDES_COMPTES FOREIGN KEY (comptes_id) REFERENCES document.comptes (id);

ALTER TABLE document.deltasoldes
    ADD CONSTRAINT IF NOT EXISTS FK_DELTASOLDES_EXEMPLAIRES FOREIGN KEY (exemplaires_id) REFERENCES document.exemplaires (id);

ALTER TABLE document.distributeurs
    ADD CONSTRAINT IF NOT EXISTS FK_DISTRIBUTEURS_ON_DISTRIBUTEURS FOREIGN KEY (distributeurs_id) REFERENCES document.personnes (id);

ALTER TABLE document.docetats_predecesseurs
    ADD CONSTRAINT IF NOT EXISTS FK_DOCETATS_PREDECESSEURS_ON_DOCETATS FOREIGN KEY (docetats_id) REFERENCES document.docetats (id);

ALTER TABLE document.docetats_predecesseurs
    ADD CONSTRAINT IF NOT EXISTS FK_DOCETATS_PREDECESSEURS_SUIVANT_ON_DOCETATS FOREIGN KEY (predecesseur_id) REFERENCES document.docetats (id);

ALTER TABLE document.docetats
    ADD CONSTRAINT IF NOT EXISTS FK_DOCETATS_ON_DOCUMENTS FOREIGN KEY (documents_id) REFERENCES document.documents (id);

ALTER TABLE document.docetats
    ADD CONSTRAINT IF NOT EXISTS FK_DOCETATS_ON_ETAPES FOREIGN KEY (etapes_id) REFERENCES document.etapes (id);

ALTER TABLE document.docetats
    ADD CONSTRAINT IF NOT EXISTS FK_DOCETATS_ON_ETATS FOREIGN KEY (etats_id) REFERENCES document.etats (id);

ALTER TABLE document.docetats
    ADD CONSTRAINT IF NOT EXISTS FK_DOCETATS_ON_VALIDATIONS FOREIGN KEY (validations_id) REFERENCES document.validations (id);

ALTER TABLE document.exemplaires
    ADD CONSTRAINT IF NOT EXISTS FK_EXEMPLAIRES_ON_DOCUMENTS FOREIGN KEY (documents_id) REFERENCES document.documents (id);

ALTER TABLE document.exemplaires
    ADD CONSTRAINT IF NOT EXISTS FK_EXEMPLAIRES_ON_PERSONNES FOREIGN KEY (personnes_id) REFERENCES document.personnes (id);

ALTER TABLE document.filesattentes
    ADD CONSTRAINT IF NOT EXISTS FK_FILESATTENTES_ON_SERVICES FOREIGN KEY (services_id) REFERENCES document.services (id);

ALTER TABLE document.jouerroles
    ADD CONSTRAINT IF NOT EXISTS FK_JOUERROLES_ON_PERSONNELS FOREIGN KEY (personnels_id) REFERENCES document.personnels (personnels_id);

ALTER TABLE document.jouerroles
    ADD CONSTRAINT IF NOT EXISTS FK_JOUERROLES_ON_ROLES FOREIGN KEY (roles_id) REFERENCES document.roles (id);

ALTER TABLE document.missions
    ADD CONSTRAINT IF NOT EXISTS FK_MISSIONS_ON_SERVICES FOREIGN KEY (services_id) REFERENCES document.services (id);

ALTER TABLE document.mouvementcaisses
    ADD CONSTRAINT IF NOT EXISTS FK_MOUVEMENTCAISSES_CAISSES FOREIGN KEY (caisses_id) REFERENCES document.caisses (id);

ALTER TABLE document.mouvementcaisses
    ADD CONSTRAINT IF NOT EXISTS FK_MOUVEMENTCAISSES_COMPTES FOREIGN KEY (comptes_id) REFERENCES document.comptes (id);

ALTER TABLE document.mouvementcaisses
    ADD CONSTRAINT IF NOT EXISTS FK_MOUVEMENTCAISSES_PERSONNELS FOREIGN KEY (personnels_id) REFERENCES document.personnels (personnels_id);

ALTER TABLE document.mouvementcaisses
    ADD CONSTRAINT IF NOT EXISTS FK_MOUVEMENTCAISSES_EXEMPLAIRES FOREIGN KEY (exemplaires_id) REFERENCES document.exemplaires (id);

ALTER TABLE document.organiser
    ADD CONSTRAINT IF NOT EXISTS FK_ORGANISER_ON_ORGANISATIONS FOREIGN KEY (organisations_id) REFERENCES document.organisations (id);

ALTER TABLE document.organiser
    ADD CONSTRAINT IF NOT EXISTS FK_ORGANISER_ON_UTILISATEURS FOREIGN KEY (utilisateurs_id) REFERENCES document.utilisateurs (id);

ALTER TABLE document.parcours
    ADD CONSTRAINT IF NOT EXISTS FK_ETAPES_ON_PARCOURS FOREIGN KEY (id) REFERENCES document.parcours (id);

ALTER TABLE document.personnels
    ADD CONSTRAINT IF NOT EXISTS FK_PERSONNELS_ON_PERSONNES FOREIGN KEY (personnels_id) REFERENCES document.personnes (id);

ALTER TABLE document.personnesmorales
    ADD CONSTRAINT IF NOT EXISTS FK_PERSONNESMORALES_ON_PERSONNESMORALES FOREIGN KEY (personnesmorales_id) REFERENCES document.personnes (id);

ALTER TABLE document.personnesphysique
    ADD CONSTRAINT IF NOT EXISTS FK_PERSONNESPHYSIQUE_ON_PERSONNESPHYSIQUE FOREIGN KEY (personnesphysique_id) REFERENCES document.personnes (id);

ALTER TABLE document.precomouvementsqtes
    ADD CONSTRAINT IF NOT EXISTS FK_PRECOMOUVEMENTSQTES_ON_PRECOMOUVEMENTS FOREIGN KEY (precomouvements_id) REFERENCES document.precomouvements (id);

ALTER TABLE document.precomouvementsqtes
    ADD CONSTRAINT IF NOT EXISTS FK_PRECOMOUVEMENTSQTES_ON_RESSOURCES FOREIGN KEY (ressources_id) REFERENCES document.ressources (id);

ALTER TABLE document.promotions
    ADD CONSTRAINT IF NOT EXISTS FK_PROMOTIONS_ON_DISTRIBUTEURS_ENTITY FOREIGN KEY (distributeurs_id) REFERENCES document.distributeurs (distributeurs_id);

ALTER TABLE document.rattacher
    ADD CONSTRAINT IF NOT EXISTS fk_rattacher_on_personnes FOREIGN KEY (personnes_id) REFERENCES document.personnes (id);

ALTER TABLE document.rattacher
    ADD CONSTRAINT IF NOT EXISTS fk_rattacher_on_personnes_bis FOREIGN KEY (rattacher_id) REFERENCES document.personnes (id);

ALTER TABLE document.remplir
    ADD CONSTRAINT IF NOT EXISTS FK_REMPLIR_ON_MISSIONS FOREIGN KEY (missions_id) REFERENCES document.missions (id);

ALTER TABLE document.remplir
    ADD CONSTRAINT IF NOT EXISTS FK_REMPLIR_ON_ROLES FOREIGN KEY (roles_id) REFERENCES document.roles (id);

ALTER TABLE document.ressources
    ADD CONSTRAINT IF NOT EXISTS FK_RESSOURCES_ON_FAMILLES FOREIGN KEY (familles_id) REFERENCES document.familles (id);

ALTER TABLE document.ressourcespromotions
    ADD CONSTRAINT IF NOT EXISTS fk_res_on_promotions_entity FOREIGN KEY (promotions_id) REFERENCES document.promotions (id);

ALTER TABLE document.ressourcespromotions
    ADD CONSTRAINT IF NOT EXISTS fk_res_on_ressources_entity FOREIGN KEY (ressources_id) REFERENCES document.ressources (id);

ALTER TABLE document.sapplique
    ADD CONSTRAINT IF NOT EXISTS fk_sapplique_on_familles_entity FOREIGN KEY (familles_id) REFERENCES document.familles (id);

ALTER TABLE document.sapplique
    ADD CONSTRAINT IF NOT EXISTS fk_sapplique_on_preco_mouvements_qtes_entity FOREIGN KEY (precomouvementsqtes_id) REFERENCES document.precomouvementsqtes (id);

ALTER TABLE document.services
    ADD CONSTRAINT IF NOT EXISTS FK_SERVICES_ON_FILESATTENTES FOREIGN KEY (filesattentes_id) REFERENCES document.filesattentes (id);

ALTER TABLE document.suivre
    ADD CONSTRAINT IF NOT EXISTS fk_suivre_on_documents_entity FOREIGN KEY (documents_id) REFERENCES document.documents (id);

ALTER TABLE document.suivre
    ADD CONSTRAINT IF NOT EXISTS fk_suivre_on_preco_mouvements_entity FOREIGN KEY (precomouvements_id) REFERENCES document.precomouvements (id);

ALTER TABLE document.tickets
    ADD CONSTRAINT IF NOT EXISTS FK_TICKETS_ON_PERSONNES FOREIGN KEY (personne_id) REFERENCES document.personnes (id);

ALTER TABLE document.ticketsfilesattentes
    ADD CONSTRAINT IF NOT EXISTS FK_TICKETSFILESATTENTES_ON_FILESATTENTES FOREIGN KEY (filesattentes_id) REFERENCES document.filesattentes (id);

ALTER TABLE document.ticketsfilesattentes
    ADD CONSTRAINT IF NOT EXISTS FK_TICKETSFILESATTENTES_ON_TICKETS FOREIGN KEY (tickets_id) REFERENCES document.tickets (id);

ALTER TABLE document.traiter
    ADD CONSTRAINT IF NOT EXISTS fk_traiter_on_documents_entity FOREIGN KEY (documents_id) REFERENCES document.documents (id);

ALTER TABLE document.traiter
    ADD CONSTRAINT IF NOT EXISTS fk_traiter_on_missions_entity FOREIGN KEY (missions_id) REFERENCES document.missions (id);

ALTER TABLE document.utilisateurs
    ADD CONSTRAINT IF NOT EXISTS FK_UTILISATEURS_ON_GROUPES FOREIGN KEY (groupes_id) REFERENCES document.groupes (id);

ALTER TABLE document.validations
    ADD CONSTRAINT IF NOT EXISTS FK_VALIDATIONS_ON_ROLES FOREIGN KEY (roles_id) REFERENCES document.roles (id);

ALTER TABLE document.violer
    ADD CONSTRAINT IF NOT EXISTS fk_violer_on_mouvements_entity FOREIGN KEY (mouvements_id) REFERENCES document.mouvements (id);

ALTER TABLE document.violer
    ADD CONSTRAINT IF NOT EXISTS fk_violer_on_preco_mouvements_entity FOREIGN KEY (precomouvements_id) REFERENCES document.precomouvements (id);

-- Ajout des contraintes d'unicité
ALTER TABLE document.services
    ADD CONSTRAINT IF NOT EXISTS uc_services_codeunique UNIQUE (codeunique);

ALTER TABLE document.services
    ADD CONSTRAINT IF NOT EXISTS uc_services_filesattentes UNIQUE (filesattentes_id);

ALTER TABLE document.promotions
    ADD CONSTRAINT IF NOT EXISTS uc_promotions_codeunique UNIQUE (codeunique);