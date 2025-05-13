--
-- PostgreSQL database dump
--

-- Dumped from database version 16.3
-- Dumped by pg_dump version 16.3

-- Started on 2025-02-13 02:42:03

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 5255 (class 1262 OID 28060)
-- Name: parcoursbackv2; Type: DATABASE; Schema: -; Owner: -
--

CREATE DATABASE parcoursbackv2 WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'French_France.1252';


\connect parcoursbackv2

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 5 (class 2615 OID 2200)
-- Name: document; Type: SCHEMA; Schema: -; Owner: -
--

CREATE SCHEMA document;


--
-- TOC entry 5256 (class 0 OID 0)
-- Dependencies: 5
-- Name: SCHEMA document; Type: COMMENT; Schema: -; Owner: -
--

COMMENT ON SCHEMA document IS 'standard public schema';


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 259 (class 1259 OID 28677)
-- Name: actions; Type: TABLE; Schema: document; Owner: -
--

CREATE TABLE document.actions (
    id character varying(255) NOT NULL,
    libelle character varying(255),
    etat boolean,
    datecreation date,
    datemodification date,
    elementsbase_id character varying(255)
);


--
-- TOC entry 261 (class 1259 OID 28691)
-- Name: actionslangues; Type: TABLE; Schema: document; Owner: -
--

CREATE TABLE document.actionslangues (
    langues_id character varying(255) NOT NULL,
    actions_id character varying(255) NOT NULL,
    valeurlibelle character varying(255)
);


--
-- TOC entry 215 (class 1259 OID 28061)
-- Name: associer; Type: TABLE; Schema: document; Owner: -
--

CREATE TABLE document.associer (
    obligatoire boolean,
    ordre integer,
    attributs_id character varying(255) NOT NULL,
    categories_id character varying(255) NOT NULL
);


--
-- TOC entry 216 (class 1259 OID 28066)
-- Name: attributs; Type: TABLE; Schema: document; Owner: -
--

CREATE TABLE document.attributs (
    id character varying(255) NOT NULL,
    titre character varying(255) NOT NULL,
    description character varying(255),
    etat boolean DEFAULT true,
    datecreation date,
    datemodification date,
    type character varying(255) NOT NULL,
    valeurpardefaut character varying(255)
);


--
-- TOC entry 217 (class 1259 OID 28072)
-- Name: caisses; Type: TABLE; Schema: document; Owner: -
--

CREATE TABLE document.caisses (
    id character varying(255) NOT NULL,
    libelle character varying(255),
    solde double precision,
    type character varying(255),
    detailjson character varying(255)
);


--
-- TOC entry 218 (class 1259 OID 28077)
-- Name: categories; Type: TABLE; Schema: document; Owner: -
--

CREATE TABLE document.categories (
    id character varying(255) NOT NULL,
    ordre character varying(255),
    libelle character varying(255),
    etat boolean DEFAULT true,
    datecreation date,
    datemodification date,
    documents_id character varying(255)
);


--
-- TOC entry 219 (class 1259 OID 28083)
-- Name: comptes; Type: TABLE; Schema: document; Owner: -
--

CREATE TABLE document.comptes (
    id character varying(255) NOT NULL,
    datecreation date,
    etat character varying(255),
    montantdecouvertmax double precision,
    libelle character varying(255),
    personnes_id character varying(255) NOT NULL,
    solde double precision
);


--
-- TOC entry 220 (class 1259 OID 28088)
-- Name: concerner; Type: TABLE; Schema: document; Owner: -
--

CREATE TABLE document.concerner (
    distributeurs_id character varying(255) NOT NULL,
    precomouvementsqtes_id character varying(255) NOT NULL
);


--
-- TOC entry 221 (class 1259 OID 28093)
-- Name: constituer; Type: TABLE; Schema: document; Owner: -
--

CREATE TABLE document.constituer (
    attributs_id character varying(255) NOT NULL,
    documents_id character varying(255) NOT NULL
);


--
-- TOC entry 222 (class 1259 OID 28098)
-- Name: deltasoldes; Type: TABLE; Schema: document; Owner: -
--

CREATE TABLE document.deltasoldes (
    id character varying(255) NOT NULL,
    montantavant double precision,
    montantapres double precision,
    datecreation date,
    typemouvement character varying(255) NOT NULL,
    comptes_id character varying(255) NOT NULL,
    exemplaires_id character varying(255) NOT NULL
);


--
-- TOC entry 223 (class 1259 OID 28103)
-- Name: docetats; Type: TABLE; Schema: document; Owner: -
--

CREATE TABLE document.docetats (
    id character varying(255) NOT NULL,
    ordre integer,
    datecreation date,
    datemodification date,
    validations_id character varying(255),
    etats_id character varying(255),
    documents_id character varying(255),
    etapes_id character varying(255)
);


--
-- TOC entry 224 (class 1259 OID 28108)
-- Name: docetats_predecesseurs; Type: TABLE; Schema: document; Owner: -
--

CREATE TABLE document.docetats_predecesseurs (
    docetats_id character varying(255) NOT NULL,
    predecesseur_id character varying(255) NOT NULL
);


--
-- TOC entry 225 (class 1259 OID 28113)
-- Name: documents; Type: TABLE; Schema: document; Owner: -
--

CREATE TABLE document.documents (
    id character varying(255) NOT NULL,
    titre character varying(255) NOT NULL,
    description character varying(255),
    etat boolean,
    datecreation date,
    datemodification date,
    typemouvement character varying(255),
    afficherprix character varying(255),
    afficherunite boolean,
    afficherdistributeur boolean,
    prixeditable boolean,
    contientressources boolean,
    estencaissable boolean
);


--
-- TOC entry 226 (class 1259 OID 28118)
-- Name: documentspromotions; Type: TABLE; Schema: document; Owner: -
--

CREATE TABLE document.documentspromotions (
    documents_id character varying(255) NOT NULL,
    promotions_id character varying(255) NOT NULL
);


--
-- TOC entry 265 (class 1259 OID 28713)
-- Name: elements; Type: TABLE; Schema: document; Owner: -
--

CREATE TABLE document.elements (
    id character varying(255) NOT NULL,
    libelle character varying(255),
    etat boolean,
    datesouscription date,
    datemodification date,
    menus_id character varying(255),
    elementsbases_id character varying(255)
);


--
-- TOC entry 263 (class 1259 OID 28703)
-- Name: elementsbaselanques; Type: TABLE; Schema: document; Owner: -
--

CREATE TABLE document.elementsbaselanques (
    langues_id character varying(255) NOT NULL,
    elementsbases_id character varying(255) NOT NULL,
    valeurlibelle character varying(255)
);


--
-- TOC entry 260 (class 1259 OID 28684)
-- Name: elementsbases; Type: TABLE; Schema: document; Owner: -
--

CREATE TABLE document.elementsbases (
    id character varying(255) NOT NULL,
    libelle character varying(255),
    etat boolean,
    datesouscription date,
    datemodification date,
    moduleangular character varying(255)
);


--
-- TOC entry 264 (class 1259 OID 28708)
-- Name: elementslangues; Type: TABLE; Schema: document; Owner: -
--

CREATE TABLE document.elementslangues (
    langues_id character varying(255) NOT NULL,
    elements_id character varying(255) NOT NULL,
    valeurlibelle character varying(255)
);


--
-- TOC entry 227 (class 1259 OID 28123)
-- Name: etapes; Type: TABLE; Schema: document; Owner: -
--

CREATE TABLE document.etapes (
    id character varying(255) NOT NULL,
    libelle character varying(255),
    etat character varying(255),
    datemodification date,
    parcours_id character varying(255)
);


--
-- TOC entry 228 (class 1259 OID 28128)
-- Name: etats; Type: TABLE; Schema: document; Owner: -
--

CREATE TABLE document.etats (
    id character varying(255) NOT NULL,
    libelle character varying(255),
    description character varying(255),
    datecreation date,
    datemodification date
);


--
-- TOC entry 229 (class 1259 OID 28133)
-- Name: exemplaires; Type: TABLE; Schema: document; Owner: -
--

CREATE TABLE document.exemplaires (
    id character varying(255) NOT NULL,
    personnes_id character varying(255) NOT NULL,
    documents_id character varying(255)
);


--
-- TOC entry 230 (class 1259 OID 28138)
-- Name: familles; Type: TABLE; Schema: document; Owner: -
--

CREATE TABLE document.familles (
    id character varying(255) NOT NULL,
    libelle character varying(255),
    description character varying(255),
    etat boolean,
    datecreation date,
    datemodification date
);


--
-- TOC entry 231 (class 1259 OID 28143)
-- Name: famillespromotions; Type: TABLE; Schema: document; Owner: -
--

CREATE TABLE document.famillespromotions (
    familles_id character varying(255) NOT NULL,
    promotions_id character varying(255) NOT NULL
);


--
-- TOC entry 232 (class 1259 OID 28148)
-- Name: filesattentes; Type: TABLE; Schema: document; Owner: -
--

CREATE TABLE document.filesattentes (
    id character varying(255) NOT NULL,
    datecreation date,
    datemodification date,
    etat boolean,
    services_id character varying(255)
);


--
-- TOC entry 270 (class 1259 OID 28746)
-- Name: groupes; Type: TABLE; Schema: document; Owner: -
--

CREATE TABLE document.groupes (
    id character varying(255) NOT NULL,
    libelle character varying(255),
    etat boolean,
    datecreation date,
    datemodification date,
    menus_id character varying(255)
);


--
-- TOC entry 233 (class 1259 OID 28153)
-- Name: jouerroles; Type: TABLE; Schema: document; Owner: -
--

CREATE TABLE document.jouerroles (
    id character varying(255) NOT NULL,
    etat boolean,
    datecreation date,
    datemodification date,
    datefin date,
    datedebut date,
    personnels_id character varying(255),
    roles_id character varying(255)
);


--
-- TOC entry 262 (class 1259 OID 28696)
-- Name: langues; Type: TABLE; Schema: document; Owner: -
--

CREATE TABLE document.langues (
    id character varying(255) NOT NULL,
    libelle character varying(255),
    etat boolean,
    datesouscription date,
    datemodification date
);


--
-- TOC entry 266 (class 1259 OID 28720)
-- Name: menus; Type: TABLE; Schema: document; Owner: -
--

CREATE TABLE document.menus (
    id character varying(255) NOT NULL,
    etat boolean,
    datecreation date,
    utilisateurs_id character varying(255),
    groupes_id character varying(255)
);


--
-- TOC entry 234 (class 1259 OID 28158)
-- Name: missions; Type: TABLE; Schema: document; Owner: -
--

CREATE TABLE document.missions (
    id character varying(255) NOT NULL,
    libelle character varying(255) NOT NULL,
    description character varying(255),
    etat boolean,
    datecreation date NOT NULL,
    datemodification date,
    services_id character varying(255)
);


--
-- TOC entry 235 (class 1259 OID 28163)
-- Name: mouvementcaisses; Type: TABLE; Schema: document; Owner: -
--

CREATE TABLE document.mouvementcaisses (
    id character varying(255) NOT NULL,
    montant double precision,
    moyenpaiement character varying(255),
    referencepaiement character varying(255),
    detailjson character varying(255),
    etat character varying(255),
    typemvt character varying(255),
    libelle character varying(255),
    caisses_id character varying(255),
    comptes_id character varying(255),
    personnels_id character varying(255),
    exemplaires_id character varying(255),
    datecreation date
);


--
-- TOC entry 236 (class 1259 OID 28168)
-- Name: mouvements; Type: TABLE; Schema: document; Owner: -
--

CREATE TABLE document.mouvements (
    id character varying(255) NOT NULL,
    description character varying(255) NOT NULL,
    quantite integer,
    prix double precision,
    datecreation date NOT NULL,
    dateperemption date,
    datemodification date,
    ressources_id character varying(255),
    distributeurs_id character varying(255)
);


--
-- TOC entry 237 (class 1259 OID 28173)
-- Name: ordreetats; Type: TABLE; Schema: document; Owner: -
--

CREATE TABLE document.ordreetats (
    id character varying(255) NOT NULL,
    datecreation date,
    datemodification date,
    datefinvote date,
    ordre integer,
    etats_id character varying(255)
);


--
-- TOC entry 268 (class 1259 OID 28734)
-- Name: organisations; Type: TABLE; Schema: document; Owner: -
--

CREATE TABLE document.organisations (
    id character varying(255) NOT NULL,
    raisonsociale character varying(255)
);


--
-- TOC entry 269 (class 1259 OID 28741)
-- Name: organiser; Type: TABLE; Schema: document; Owner: -
--

CREATE TABLE document.organiser (
    organisations_id character varying(255) NOT NULL,
    utilisateurs_id character varying(255) NOT NULL
);


--
-- TOC entry 238 (class 1259 OID 28178)
-- Name: parcours; Type: TABLE; Schema: document; Owner: -
--

CREATE TABLE document.parcours (
    id character varying(255) NOT NULL,
    libelle character varying(255),
    datecreation date,
    datemodification date
);


--
-- TOC entry 239 (class 1259 OID 28183)
-- Name: personnels; Type: TABLE; Schema: document; Owner: -
--

CREATE TABLE document.personnels (
    id character varying(255) NOT NULL,
    dateentree date,
    nom character varying(255),
    datenaissance date,
    telephone character varying(255),
    datesortie date,
    email character varying(255),
    prenom character varying(255),
    sexe character varying(255),
    datemodification date
);


--
-- TOC entry 240 (class 1259 OID 28188)
-- Name: personnes; Type: TABLE; Schema: document; Owner: -
--

CREATE TABLE document.personnes (
    id character varying(255) NOT NULL,
    adresse character varying(255),
    mail character varying(255),
    telephone character varying(255),
    qrcodevalue character varying(255),
    code character varying(255),
    raisonsociale character varying(255),
    nom character varying(255),
    prenom character varying(255),
    sexe character varying(255),
    datenaissance date,
    datecreation date,
    datemodification date,
    person_type character varying(255) NOT NULL,
    comptes_id character varying(255),
    CONSTRAINT personnes_person_type_check CHECK (((person_type)::text = ANY (ARRAY[('personnesmorales'::character varying)::text, ('personnesphysique'::character varying)::text, ('distributeurs'::character varying)::text])))
);


--
-- TOC entry 241 (class 1259 OID 28194)
-- Name: personnes_s; Type: TABLE; Schema: document; Owner: -
--

CREATE TABLE document.personnes_s (
    id character varying(255) NOT NULL,
    adresse character varying(255),
    mail character varying(255),
    telephone character varying(255),
    qrcodevalue character varying(255),
    code character varying(255),
    raisonsociale character varying(255),
    nom character varying(255) NOT NULL,
    prenom character varying(255),
    sexe character varying(255),
    datenaissance date NOT NULL,
    datecreation date NOT NULL,
    datemodification date,
    person_type character varying(255) NOT NULL
);


--
-- TOC entry 242 (class 1259 OID 28199)
-- Name: precomouvements; Type: TABLE; Schema: document; Owner: -
--

CREATE TABLE document.precomouvements (
    id character varying(255) NOT NULL,
    libelle character varying(255) NOT NULL,
    etat boolean,
    datecreation date NOT NULL,
    datemodification date,
    typemouvement character varying(255)
);


--
-- TOC entry 243 (class 1259 OID 28204)
-- Name: precomouvementsqtes; Type: TABLE; Schema: document; Owner: -
--

CREATE TABLE document.precomouvementsqtes (
    id character varying(255) NOT NULL,
    qtemin integer,
    qtemax integer,
    montantmin double precision,
    montantmax double precision,
    datecreation date,
    datemodification date,
    precomouvements_id character varying(255) NOT NULL,
    ressources_id character varying(255)
);


--
-- TOC entry 244 (class 1259 OID 28209)
-- Name: promotions; Type: TABLE; Schema: document; Owner: -
--

CREATE TABLE document.promotions (
    id character varying(255) NOT NULL,
    datedebut date,
    datefin date,
    codeunique character varying(255),
    typeremise character varying(255),
    valeurremise double precision,
    datecreation date,
    datemodification date,
    distributeurs_id character varying(255)
);


--
-- TOC entry 245 (class 1259 OID 28214)
-- Name: rattacher; Type: TABLE; Schema: document; Owner: -
--

CREATE TABLE document.rattacher (
    personnes_id character varying(255) NOT NULL,
    rattacher_id character varying(255) NOT NULL
);


--
-- TOC entry 246 (class 1259 OID 28219)
-- Name: remplir; Type: TABLE; Schema: document; Owner: -
--

CREATE TABLE document.remplir (
    datefin date,
    datecreation date,
    datedebut date,
    etat boolean,
    droitajouter boolean,
    droitmodifier boolean,
    droitconsulter boolean,
    droitvalider boolean,
    roles_id character varying(255) NOT NULL,
    missions_id character varying(255) NOT NULL
);


--
-- TOC entry 247 (class 1259 OID 28224)
-- Name: respecter; Type: TABLE; Schema: document; Owner: -
--

CREATE TABLE document.respecter (
    mouvements_id character varying(255) NOT NULL,
    precomouvements_id character varying(255) NOT NULL
);


--
-- TOC entry 248 (class 1259 OID 28229)
-- Name: ressources; Type: TABLE; Schema: document; Owner: -
--

CREATE TABLE document.ressources (
    id character varying(255) NOT NULL,
    libelle character varying(255),
    description character varying(255),
    etat boolean,
    datecreation date,
    datemodification date,
    quantite integer,
    seuilalerte integer,
    prixentree double precision,
    prixsortie double precision,
    unite character varying(255),
    familles_id character varying(255)
);


--
-- TOC entry 249 (class 1259 OID 28234)
-- Name: ressourcespromotions; Type: TABLE; Schema: document; Owner: -
--

CREATE TABLE document.ressourcespromotions (
    promotions_id character varying(255) NOT NULL,
    ressources_id character varying(255) NOT NULL
);


--
-- TOC entry 250 (class 1259 OID 28239)
-- Name: roles; Type: TABLE; Schema: document; Owner: -
--

CREATE TABLE document.roles (
    id character varying(255) NOT NULL,
    titre character varying(255),
    description character varying(255),
    etat boolean,
    datecreation date,
    datemodification date
);


--
-- TOC entry 251 (class 1259 OID 28244)
-- Name: sapplique; Type: TABLE; Schema: document; Owner: -
--

CREATE TABLE document.sapplique (
    familles_id character varying(255) NOT NULL,
    precomouvementsqtes_id character varying(255) NOT NULL
);


--
-- TOC entry 252 (class 1259 OID 28249)
-- Name: services; Type: TABLE; Schema: document; Owner: -
--

CREATE TABLE document.services (
    id character varying(255) NOT NULL,
    description character varying(255),
    libelle character varying(255),
    localisation character varying(255),
    etat boolean,
    datecreation date,
    datemodification date,
    codeunique character varying(255) NOT NULL,
    filesattentes_id character varying(255)
);


--
-- TOC entry 253 (class 1259 OID 28254)
-- Name: suivre; Type: TABLE; Schema: document; Owner: -
--

CREATE TABLE document.suivre (
    documents_id character varying(255) NOT NULL,
    precomouvements_id character varying(255) NOT NULL
);


--
-- TOC entry 254 (class 1259 OID 28259)
-- Name: tickets; Type: TABLE; Schema: document; Owner: -
--

CREATE TABLE document.tickets (
    id character varying(255) NOT NULL,
    codecourt character varying(255),
    datecreation date,
    datemodification date
);


--
-- TOC entry 255 (class 1259 OID 28264)
-- Name: ticketsfilesattentes; Type: TABLE; Schema: document; Owner: -
--

CREATE TABLE document.ticketsfilesattentes (
    id character varying(255) NOT NULL,
    etat boolean,
    dateaffectation date,
    tickets_id character varying(255),
    filesattentes_id character varying(255)
);


--
-- TOC entry 256 (class 1259 OID 28269)
-- Name: traiter; Type: TABLE; Schema: document; Owner: -
--

CREATE TABLE document.traiter (
    documents_id character varying(255) NOT NULL,
    missions_id character varying(255) NOT NULL
);


--
-- TOC entry 267 (class 1259 OID 28727)
-- Name: utilisateurs; Type: TABLE; Schema: document; Owner: -
--

CREATE TABLE document.utilisateurs (
    id character varying(255) NOT NULL,
    login character varying(255),
    mdp character varying(255),
    etat character varying(255),
    datecreation date,
    datemodification date,
    groupes_id character varying(255),
    menus_id character varying(255)
);


--
-- TOC entry 257 (class 1259 OID 28274)
-- Name: validations; Type: TABLE; Schema: document; Owner: -
--

CREATE TABLE document.validations (
    id character varying(255) NOT NULL,
    code character varying(255),
    etat character varying(255),
    datecreation date,
    datemodification date,
    typevote character varying(255),
    dureevote integer,
    typevalidation character varying(255),
    roles_id character varying(255)
);


--
-- TOC entry 258 (class 1259 OID 28279)
-- Name: violer; Type: TABLE; Schema: document; Owner: -
--

CREATE TABLE document.violer (
    mouvements_id character varying(255) NOT NULL,
    precomouvements_id character varying(255) NOT NULL
);


--
-- TOC entry 5238 (class 0 OID 28677)
-- Dependencies: 259
-- Data for Name: actions; Type: TABLE DATA; Schema: document; Owner: -
--



--
-- TOC entry 5240 (class 0 OID 28691)
-- Dependencies: 261
-- Data for Name: actionslangues; Type: TABLE DATA; Schema: document; Owner: -
--



--
-- TOC entry 5194 (class 0 OID 28061)
-- Dependencies: 215
-- Data for Name: associer; Type: TABLE DATA; Schema: document; Owner: -
--

INSERT INTO document.associer (obligatoire, ordre, attributs_id, categories_id) VALUES (true, 1, 'a1eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', '36b843d8-f8a7-4b76-9980-1add9edc2364') ON CONFLICT DO NOTHING;
INSERT INTO document.associer (obligatoire, ordre, attributs_id, categories_id) VALUES (true, 2, 'a1eebc99-9c0b-4ef8-bb6d-6bb9bd380a13', '36b843d8-f8a7-4b76-9980-1add9edc2364') ON CONFLICT DO NOTHING;
INSERT INTO document.associer (obligatoire, ordre, attributs_id, categories_id) VALUES (false, 3, 'a1eebc99-9c0b-4ef8-bb6d-6bb9bd380a15', '36b843d8-f8a7-4b76-9980-1add9edc2364') ON CONFLICT DO NOTHING;
INSERT INTO document.associer (obligatoire, ordre, attributs_id, categories_id) VALUES (true, 1, 'a2eebc99-9c0b-4ef8-bb6d-6bb9bd380a12', 'be9ef47f-d5a2-4254-ab79-af860672553e') ON CONFLICT DO NOTHING;
INSERT INTO document.associer (obligatoire, ordre, attributs_id, categories_id) VALUES (false, 2, 'a9eebc99-9c0b-4ef8-bb6d-6bb9bd380a19', 'be9ef47f-d5a2-4254-ab79-af860672553e') ON CONFLICT DO NOTHING;
INSERT INTO document.associer (obligatoire, ordre, attributs_id, categories_id) VALUES (true, 3, 'a2eebc99-9c0b-4ef8-bb6d-6bb9bd380a20', 'be9ef47f-d5a2-4254-ab79-af860672553e') ON CONFLICT DO NOTHING;
INSERT INTO document.associer (obligatoire, ordre, attributs_id, categories_id) VALUES (true, 1, 'a1eebc99-9c0b-4ef8-bb6d-6bb9bd380a13', 'be8ef47f-d5a2-4254-ab79-af860672553e') ON CONFLICT DO NOTHING;
INSERT INTO document.associer (obligatoire, ordre, attributs_id, categories_id) VALUES (true, 1, 'a4eebc99-9c0b-4ef8-bb6d-6bb9bd380a14', 'be8ef57f-d5a2-4254-ab79-af860672553e') ON CONFLICT DO NOTHING;
INSERT INTO document.associer (obligatoire, ordre, attributs_id, categories_id) VALUES (true, 1, 'a1eebc99-9c0b-4ef8-bb6d-6bb9bd380a15', 'be8ef56f-d5a2-4254-ab79-af860672553e') ON CONFLICT DO NOTHING;
INSERT INTO document.associer (obligatoire, ordre, attributs_id, categories_id) VALUES (true, 1, 'a6eebc99-9c0b-4ef8-bb6d-6bb9bd380a16', 'be8ef59f-d5a2-4254-ab79-af860672553e') ON CONFLICT DO NOTHING;
INSERT INTO document.associer (obligatoire, ordre, attributs_id, categories_id) VALUES (true, 1, 'a7eebc99-9c0b-4ef8-bb6d-6bb9bd380a17', 'be8ef59f-d5a3-4254-ab79-af860672553e') ON CONFLICT DO NOTHING;
INSERT INTO document.associer (obligatoire, ordre, attributs_id, categories_id) VALUES (true, 1, 'a8eebc99-9c0b-4ef8-bb6d-6bb9bd380a18', 'be8ef59f-d5a4-4254-ab79-af860672553e') ON CONFLICT DO NOTHING;
INSERT INTO document.associer (obligatoire, ordre, attributs_id, categories_id) VALUES (true, 1, 'a9eebc99-9c0b-4ef8-bb6d-6bb9bd380a19', 'be8ef59f-d5a4-4254-ab99-af860672553e') ON CONFLICT DO NOTHING;


--
-- TOC entry 5195 (class 0 OID 28066)
-- Dependencies: 216
-- Data for Name: attributs; Type: TABLE DATA; Schema: document; Owner: -
--

INSERT INTO document.attributs (id, titre, description, etat, datecreation, datemodification, type, valeurpardefaut) VALUES ('a1eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 'Taille', 'Description Taille', true, '2022-01-01', '2022-01-02', 'String', NULL) ON CONFLICT DO NOTHING;
INSERT INTO document.attributs (id, titre, description, etat, datecreation, datemodification, type, valeurpardefaut) VALUES ('a2eebc99-9c0b-4ef8-bb6d-6bb9bd380a12', 'Poids', 'Description Poids', true, '2022-01-01', '2022-01-02', 'Double', NULL) ON CONFLICT DO NOTHING;
INSERT INTO document.attributs (id, titre, description, etat, datecreation, datemodification, type, valeurpardefaut) VALUES ('a1eebc99-9c0b-4ef8-bb6d-6bb9bd380a13', 'Age', 'Description age', true, '2022-01-01', '2022-01-02', 'Int', NULL) ON CONFLICT DO NOTHING;
INSERT INTO document.attributs (id, titre, description, etat, datecreation, datemodification, type, valeurpardefaut) VALUES ('a4eebc99-9c0b-4ef8-bb6d-6bb9bd380a14', 'Sexe', 'Sexe ', true, '2022-01-01', '2022-01-02', 'String', NULL) ON CONFLICT DO NOTHING;
INSERT INTO document.attributs (id, titre, description, etat, datecreation, datemodification, type, valeurpardefaut) VALUES ('a1eebc99-9c0b-4ef8-bb6d-6bb9bd380a15', 'Teint', 'Coloration', true, '2022-01-01', '2022-01-02', 'String', 'Noir') ON CONFLICT DO NOTHING;
INSERT INTO document.attributs (id, titre, description, etat, datecreation, datemodification, type, valeurpardefaut) VALUES ('a6eebc99-9c0b-4ef8-bb6d-6bb9bd380a16', 'Groupe sangin', 'Groupe sangin', true, '2022-01-01', '2022-01-02', 'String', 'A, A+, A-, B, B+, B-, AB, AB+, AB-, O, O+, O-') ON CONFLICT DO NOTHING;
INSERT INTO document.attributs (id, titre, description, etat, datecreation, datemodification, type, valeurpardefaut) VALUES ('a7eebc99-9c0b-4ef8-bb6d-6bb9bd380a17', 'Allergies', 'Allergies connues ', true, '2022-01-01', '2022-01-02', 'String', NULL) ON CONFLICT DO NOTHING;
INSERT INTO document.attributs (id, titre, description, etat, datecreation, datemodification, type, valeurpardefaut) VALUES ('a8eebc99-9c0b-4ef8-bb6d-6bb9bd380a18', 'Nom', 'Nom de la personne', true, '2022-01-01', '2022-01-02', 'String', NULL) ON CONFLICT DO NOTHING;
INSERT INTO document.attributs (id, titre, description, etat, datecreation, datemodification, type, valeurpardefaut) VALUES ('a9eebc99-9c0b-4ef8-bb6d-6bb9bd380a19', 'Adresse', 'Adresse postale', true, '2022-01-01', '2022-01-02', 'String', NULL) ON CONFLICT DO NOTHING;
INSERT INTO document.attributs (id, titre, description, etat, datecreation, datemodification, type, valeurpardefaut) VALUES ('a2eebc99-9c0b-4ef8-bb6d-6bb9bd380a20', 'Date de naissance', 'Date de naissance', true, '2022-01-01', '2022-01-02', 'Date', NULL) ON CONFLICT DO NOTHING;


--
-- TOC entry 5196 (class 0 OID 28072)
-- Dependencies: 217
-- Data for Name: caisses; Type: TABLE DATA; Schema: document; Owner: -
--

INSERT INTO document.caisses (id, libelle, solde, type, detailjson) VALUES ('a1f8e3b0-4c6d-4e5e-ab7a-2a8b9b9c8d6f', 'Caisse principale', 10000, 'Caisse', '{"description": "Caisse principale"}') ON CONFLICT DO NOTHING;
INSERT INTO document.caisses (id, libelle, solde, type, detailjson) VALUES ('d9e2f1c2-8b3a-4f6c-bd5e-1a7b8a7c6d5e', 'Caisse secondaire', 5000, 'Caisse', '{"description": "Caisse secondaire"}') ON CONFLICT DO NOTHING;
INSERT INTO document.caisses (id, libelle, solde, type, detailjson) VALUES ('b3a7c8d6-1e2f-3d4c-cb6a-9e8f0a7b6c5d', 'Caisse en ligne', 7500, 'Caisse', '{"description": "Caisse en ligne"}') ON CONFLICT DO NOTHING;
INSERT INTO document.caisses (id, libelle, solde, type, detailjson) VALUES ('f0a7b6c5-d4e3f2a1-8d9c-7d6e-5f4a3b2c1e0d', 'Caisse mobile', 3000, 'Caisse', '{"description": "Caisse mobile"}') ON CONFLICT DO NOTHING;


--
-- TOC entry 5197 (class 0 OID 28077)
-- Dependencies: 218
-- Data for Name: categories; Type: TABLE DATA; Schema: document; Owner: -
--

INSERT INTO document.categories (id, ordre, libelle, etat, datecreation, datemodification, documents_id) VALUES ('36b843d8-f8a7-4b76-9980-1add9edc2364', '0', 'Informations Personnelles', true, '2022-01-01', '2022-01-02', '0190615e-1101-7209-9932-7020bbd556f1') ON CONFLICT DO NOTHING;
INSERT INTO document.categories (id, ordre, libelle, etat, datecreation, datemodification, documents_id) VALUES ('be9ef47f-d5a2-4254-ab79-af860672553e', '1', 'Informations Primaires', true, '2022-01-01', '2022-01-02', '0190615e-1101-7209-9932-7020bbd556f1') ON CONFLICT DO NOTHING;
INSERT INTO document.categories (id, ordre, libelle, etat, datecreation, datemodification, documents_id) VALUES ('be8ef47f-d5a2-4254-ab79-af860672553e', '0', 'Conditions Générales', true, '2022-01-01', '2022-01-02', '0190615e-1101-7209-9932-7020bbd556f2') ON CONFLICT DO NOTHING;
INSERT INTO document.categories (id, ordre, libelle, etat, datecreation, datemodification, documents_id) VALUES ('be8ef57f-d5a2-4254-ab79-af860672553e', '1', 'Conditions Particulières', true, '2022-01-01', '2022-01-02', '0190615e-1101-7209-9932-7020bbd556f2') ON CONFLICT DO NOTHING;
INSERT INTO document.categories (id, ordre, libelle, etat, datecreation, datemodification, documents_id) VALUES ('be8ef56f-d5a2-4254-ab79-af860672553e', '1', 'Informations Personnelles', true, '2022-01-01', '2022-01-02', '0190615e-1101-7209-9932-7020bbd556f3') ON CONFLICT DO NOTHING;
INSERT INTO document.categories (id, ordre, libelle, etat, datecreation, datemodification, documents_id) VALUES ('be8ef59f-d5a2-4254-ab79-af860672553e', '1', 'Informations Primaires', true, '2022-01-01', '2022-01-02', '0190615e-1101-7209-9932-7020bbd556f4') ON CONFLICT DO NOTHING;
INSERT INTO document.categories (id, ordre, libelle, etat, datecreation, datemodification, documents_id) VALUES ('be8ef59f-d5a3-4254-ab79-af860672553e', '1', 'Conditions Générales', true, '2022-01-01', '2022-01-02', '0190615e-1101-7209-9932-7020bbd556f5') ON CONFLICT DO NOTHING;
INSERT INTO document.categories (id, ordre, libelle, etat, datecreation, datemodification, documents_id) VALUES ('be8ef59f-d5a4-4254-ab79-af860672553e', '0', 'Conditions Générales', true, '2022-01-01', '2022-01-02', '0190615e-1101-7209-9932-7020bbd556f6') ON CONFLICT DO NOTHING;
INSERT INTO document.categories (id, ordre, libelle, etat, datecreation, datemodification, documents_id) VALUES ('be8ef59f-d5a4-4254-ab99-af860672553e', '0', 'Catégorie par Defaut', true, '2022-01-01', '2022-01-02', '0190615e-1101-7209-9932-7020bbd556f7') ON CONFLICT DO NOTHING;


--
-- TOC entry 5198 (class 0 OID 28083)
-- Dependencies: 219
-- Data for Name: comptes; Type: TABLE DATA; Schema: document; Owner: -
--

INSERT INTO document.comptes (id, datecreation, etat, montantdecouvertmax, libelle, personnes_id, solde) VALUES ('00012f83-2a62-4e6d-aa23-57c7825bcd57', '2024-08-19', 'actif', 2000, 'Compte entreprise', '0001ff83-2a62-4e6d-aa23-57c7825bcd57', 102.32) ON CONFLICT DO NOTHING;
INSERT INTO document.comptes (id, datecreation, etat, montantdecouvertmax, libelle, personnes_id, solde) VALUES ('1179bd79-f71b-498b-b247-e7b9bbb3f600', '2024-08-19', 'inactif', 0, 'Compte jeunesse', '0079bd79-f71b-498b-b247-e7b9bbb3f600', 3553.2) ON CONFLICT DO NOTHING;
INSERT INTO document.comptes (id, datecreation, etat, montantdecouvertmax, libelle, personnes_id, solde) VALUES ('1518e585-f82a-4d5f-af1c-54f880d766d3', '2024-08-19', 'actif', 2000, 'Compte entreprise', '0018e585-f82a-4d5f-af1c-54f880d766d3', 646583.32) ON CONFLICT DO NOTHING;
INSERT INTO document.comptes (id, datecreation, etat, montantdecouvertmax, libelle, personnes_id, solde) VALUES ('a1f8e3b0-4c6d-4e5e-7b7a-2a8b9b9c8d6f', '2024-08-19', 'actif', 500, 'Compte courant', '0618e585-f82a-4d5f-af2c-54f880d766d3', 14785) ON CONFLICT DO NOTHING;
INSERT INTO document.comptes (id, datecreation, etat, montantdecouvertmax, libelle, personnes_id, solde) VALUES ('adbff692-418d-43ab-a196-fcc3114b2daa', '2024-08-19', 'actif', 500, 'Compte courant', 'fdbff692-6b8d-43ab-a196-fcc3114b2daa', 31656.325) ON CONFLICT DO NOTHING;
INSERT INTO document.comptes (id, datecreation, etat, montantdecouvertmax, libelle, personnes_id, solde) VALUES ('b3a7c8d6-1e2f-3d4c-7b6a-9e8f0a7b6c5d', '2024-08-19', 'inactif', 0, 'Compte jeunesse', '1979bd79-f71b-498b-b247-e7b9bbb3f600', 789654.23) ON CONFLICT DO NOTHING;
INSERT INTO document.comptes (id, datecreation, etat, montantdecouvertmax, libelle, personnes_id, solde) VALUES ('d301ff83-1b62-4e6d-aa23-57c7825bcd57', '2024-08-19', 'actif', 1000, 'Épargne', 'd301ff83-2a62-4e6d-aa23-57c7825bcd57', 1153548.35) ON CONFLICT DO NOTHING;
INSERT INTO document.comptes (id, datecreation, etat, montantdecouvertmax, libelle, personnes_id, solde) VALUES ('d9e2f1c2-8b3a-4f6c-7d5e-1a7b8a7c6d5e', '2024-08-19', 'actif', 1000, 'Épargne', '0618e585-f82a-4d5f-af2c-04f880d766d3', 635663.254) ON CONFLICT DO NOTHING;
INSERT INTO document.comptes (id, datecreation, etat, montantdecouvertmax, libelle, personnes_id, solde) VALUES ('f0a7b6c5-d4e3f2a1-7b9c-7d6e-5f4a3b2c1e0d', '2024-08-19', 'actif', 2000, 'Compte entreprise', '0618e585-f82a-4d5f-af1c-54f880d766d3', 655318.369) ON CONFLICT DO NOTHING;


--
-- TOC entry 5199 (class 0 OID 28088)
-- Dependencies: 220
-- Data for Name: concerner; Type: TABLE DATA; Schema: document; Owner: -
--



--
-- TOC entry 5200 (class 0 OID 28093)
-- Dependencies: 221
-- Data for Name: constituer; Type: TABLE DATA; Schema: document; Owner: -
--

INSERT INTO document.constituer (attributs_id, documents_id) VALUES ('a1eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', '0190615e-1101-7209-9932-7020bbd556f1') ON CONFLICT DO NOTHING;
INSERT INTO document.constituer (attributs_id, documents_id) VALUES ('a1eebc99-9c0b-4ef8-bb6d-6bb9bd380a13', '0190615e-1101-7209-9932-7020bbd556f1') ON CONFLICT DO NOTHING;
INSERT INTO document.constituer (attributs_id, documents_id) VALUES ('a1eebc99-9c0b-4ef8-bb6d-6bb9bd380a15', '0190615e-1101-7209-9932-7020bbd556f1') ON CONFLICT DO NOTHING;
INSERT INTO document.constituer (attributs_id, documents_id) VALUES ('a2eebc99-9c0b-4ef8-bb6d-6bb9bd380a12', '0190615e-1101-7209-9932-7020bbd556f1') ON CONFLICT DO NOTHING;
INSERT INTO document.constituer (attributs_id, documents_id) VALUES ('a9eebc99-9c0b-4ef8-bb6d-6bb9bd380a19', '0190615e-1101-7209-9932-7020bbd556f1') ON CONFLICT DO NOTHING;
INSERT INTO document.constituer (attributs_id, documents_id) VALUES ('a2eebc99-9c0b-4ef8-bb6d-6bb9bd380a20', '0190615e-1101-7209-9932-7020bbd556f1') ON CONFLICT DO NOTHING;
INSERT INTO document.constituer (attributs_id, documents_id) VALUES ('a1eebc99-9c0b-4ef8-bb6d-6bb9bd380a13', '0190615e-1101-7209-9932-7020bbd556f2') ON CONFLICT DO NOTHING;
INSERT INTO document.constituer (attributs_id, documents_id) VALUES ('a4eebc99-9c0b-4ef8-bb6d-6bb9bd380a14', '0190615e-1101-7209-9932-7020bbd556f2') ON CONFLICT DO NOTHING;
INSERT INTO document.constituer (attributs_id, documents_id) VALUES ('a2eebc99-9c0b-4ef8-bb6d-6bb9bd380a20', '0190615e-1101-7209-9932-7020bbd556f2') ON CONFLICT DO NOTHING;
INSERT INTO document.constituer (attributs_id, documents_id) VALUES ('a1eebc99-9c0b-4ef8-bb6d-6bb9bd380a15', '0190615e-1101-7209-9932-7020bbd556f3') ON CONFLICT DO NOTHING;
INSERT INTO document.constituer (attributs_id, documents_id) VALUES ('a6eebc99-9c0b-4ef8-bb6d-6bb9bd380a16', '0190615e-1101-7209-9932-7020bbd556f4') ON CONFLICT DO NOTHING;
INSERT INTO document.constituer (attributs_id, documents_id) VALUES ('a7eebc99-9c0b-4ef8-bb6d-6bb9bd380a17', '0190615e-1101-7209-9932-7020bbd556f5') ON CONFLICT DO NOTHING;
INSERT INTO document.constituer (attributs_id, documents_id) VALUES ('a8eebc99-9c0b-4ef8-bb6d-6bb9bd380a18', '0190615e-1101-7209-9932-7020bbd556f6') ON CONFLICT DO NOTHING;
INSERT INTO document.constituer (attributs_id, documents_id) VALUES ('a9eebc99-9c0b-4ef8-bb6d-6bb9bd380a19', '0190615e-1101-7209-9932-7020bbd556f7') ON CONFLICT DO NOTHING;
INSERT INTO document.constituer (attributs_id, documents_id) VALUES ('a2eebc99-9c0b-4ef8-bb6d-6bb9bd380a20', '0190615e-1101-7209-9932-7020bbd556f8') ON CONFLICT DO NOTHING;


--
-- TOC entry 5201 (class 0 OID 28098)
-- Dependencies: 222
-- Data for Name: deltasoldes; Type: TABLE DATA; Schema: document; Owner: -
--

INSERT INTO document.deltasoldes (id, montantavant, montantapres, datecreation, typemouvement, comptes_id, exemplaires_id) VALUES ('aad314f5-84ad-77cd-874d-9c55f0e45790', 651651.258, 51555.32, '2022-01-02', 'retrait', 'b3a7c8d6-1e2f-3d4c-7b6a-9e8f0a7b6c5d', 'b3a7c8d6-1e2f-3d4c-5b6a-9e8f0a7b6c5d') ON CONFLICT DO NOTHING;
INSERT INTO document.deltasoldes (id, montantavant, montantapres, datecreation, typemouvement, comptes_id, exemplaires_id) VALUES ('aad314f5-84ad-77cd-874d-9c55f0e45791', 651651.258, 51555.32, '2022-01-02', 'retrait', 'a1f8e3b0-4c6d-4e5e-7b7a-2a8b9b9c8d6f', 'a1f8e3b0-4c6d-4e5e-9b7a-2a8b9b9c8d6f') ON CONFLICT DO NOTHING;
INSERT INTO document.deltasoldes (id, montantavant, montantapres, datecreation, typemouvement, comptes_id, exemplaires_id) VALUES ('aad314f5-84ad-77cd-874d-9c55f0e45792', 564566.21, 6544864.21, '2022-01-02', 'paiement', 'd9e2f1c2-8b3a-4f6c-7d5e-1a7b8a7c6d5e', 'd9e2f1c2-8b3a-4f6c-9d5e-1a7b8a7c6d5e') ON CONFLICT DO NOTHING;
INSERT INTO document.deltasoldes (id, montantavant, montantapres, datecreation, typemouvement, comptes_id, exemplaires_id) VALUES ('aad314f5-84ad-77cd-874d-9c55f0e45793', 48646.58, 454151.14, '2022-01-02', 'paiement', 'f0a7b6c5-d4e3f2a1-7b9c-7d6e-5f4a3b2c1e0d', 'f0a7b6c5-d4e3f2a1-8b9c-7d6e-5f4a3b2c1e0d') ON CONFLICT DO NOTHING;
INSERT INTO document.deltasoldes (id, montantavant, montantapres, datecreation, typemouvement, comptes_id, exemplaires_id) VALUES ('aad314f5-84ad-77qd-874d-9c55f0e45790', 45245.6, 4587.35, '2022-01-02', 'retrait', 'd301ff83-1b62-4e6d-aa23-57c7825bcd57', 'a1f1e3b0-4c6d-4e5e-9b7a-2a8b9b9c8d6f') ON CONFLICT DO NOTHING;
INSERT INTO document.deltasoldes (id, montantavant, montantapres, datecreation, typemouvement, comptes_id, exemplaires_id) VALUES ('aad314f5-84ad-77qd-874e-9c55f0e45790', 452.21, 452452.26, '2022-01-02', 'depots', 'adbff692-418d-43ab-a196-fcc3114b2daa', 'a1f1e4b0-4c6d-4e5e-9b7a-2a8b9b9c8d6f') ON CONFLICT DO NOTHING;


--
-- TOC entry 5202 (class 0 OID 28103)
-- Dependencies: 223
-- Data for Name: docetats; Type: TABLE DATA; Schema: document; Owner: -
--

INSERT INTO document.docetats (id, ordre, datecreation, datemodification, validations_id, etats_id, documents_id, etapes_id) VALUES ('be8ef4af-d5a2-4254-cd79-af860672553e', 15, '2022-03-02', '2022-10-02', '1901bd80-f71b-498b-b247-e7b9bbb3f602', 'e190615e-1101-7209-9932-7020bbd556f1', '0190615e-1101-7209-9932-7020bbd556f1', '1901bd79-f71b-498b-b247-e7b9bbb3f600') ON CONFLICT DO NOTHING;
INSERT INTO document.docetats (id, ordre, datecreation, datemodification, validations_id, etats_id, documents_id, etapes_id) VALUES ('be8ef4af-d5a2-4254-cd79-af860672554e', 10, '2002-01-02', '2022-11-02', '1901bd80-f71b-498b-b247-e7b9bbb3f601', 'e190615e-1101-7209-9932-7020bbd556f2', '0190615e-1101-7209-9932-7020bbd556f2', '1901bd79-f71b-498b-b247-e7b9bbb3f601') ON CONFLICT DO NOTHING;
INSERT INTO document.docetats (id, ordre, datecreation, datemodification, validations_id, etats_id, documents_id, etapes_id) VALUES ('be8ef4af-d5a2-4254-cd79-af860672555e', 11, '2014-01-12', '2022-02-02', '1901bd80-f71b-498b-b247-e7b9bbb3f600', 'e190615e-1101-7209-9932-7020bbd556f3', '0190615e-1101-7209-9932-7020bbd556f3', '1901bd79-f71b-498b-b247-e7b9bbb3f602') ON CONFLICT DO NOTHING;
INSERT INTO document.docetats (id, ordre, datecreation, datemodification, validations_id, etats_id, documents_id, etapes_id) VALUES ('be8ef4af-d5a2-4254-cd79-af860672556e', 12, '2023-10-25', '2024-03-02', '1901bd80-f71b-498b-b247-e7b9bbb3f603', 'e190615e-1101-7209-9932-7020bbd556f4', '0190615e-1101-7209-9932-7020bbd556f4', '1901bd79-f71b-498b-b247-e7b9bbb3f602') ON CONFLICT DO NOTHING;
INSERT INTO document.docetats (id, ordre, datecreation, datemodification, validations_id, etats_id, documents_id, etapes_id) VALUES ('be8ef4af-d5a2-4254-cd79-af860672557e', 16, '2020-11-15', '2024-05-02', '1901bd80-f71b-498b-b247-e7b9bbb3f604', 'e190615e-1101-7209-9932-7020bbd556f4', '0190615e-1101-7209-9932-7020bbd556f5', '1901bd79-f71b-498b-b247-e7b9bbb3f600') ON CONFLICT DO NOTHING;


--
-- TOC entry 5203 (class 0 OID 28108)
-- Dependencies: 224
-- Data for Name: docetats_predecesseurs; Type: TABLE DATA; Schema: document; Owner: -
--

INSERT INTO document.docetats_predecesseurs (docetats_id, predecesseur_id) VALUES ('be8ef4af-d5a2-4254-cd79-af860672553e', 'be8ef4af-d5a2-4254-cd79-af860672555e') ON CONFLICT DO NOTHING;
INSERT INTO document.docetats_predecesseurs (docetats_id, predecesseur_id) VALUES ('be8ef4af-d5a2-4254-cd79-af860672554e', 'be8ef4af-d5a2-4254-cd79-af860672556e') ON CONFLICT DO NOTHING;


--
-- TOC entry 5204 (class 0 OID 28113)
-- Dependencies: 225
-- Data for Name: documents; Type: TABLE DATA; Schema: document; Owner: -
--

INSERT INTO document.documents (id, titre, description, etat, datecreation, datemodification, typemouvement, afficherprix, afficherunite, afficherdistributeur, prixeditable, contientressources, estencaissable) VALUES ('0190615e-1101-7209-9932-7020bbd556f1', 'Note intervention', 'Document delivre par le medecin ou un infirmier de l''etablissement', true, '2023-03-31', '2023-03-31', 'Ajout', 'true', true, true, true, true, true) ON CONFLICT DO NOTHING;
INSERT INTO document.documents (id, titre, description, etat, datecreation, datemodification, typemouvement, afficherprix, afficherunite, afficherdistributeur, prixeditable, contientressources, estencaissable) VALUES ('0190615e-1101-7209-9932-7020bbd556f2', 'Fiche de suivi', 'Document delivre par le medecin ou un infirmier de l''etablissement', true, '2023-03-31', '2023-03-31', 'Neutre', 'true', true, true, true, true, true) ON CONFLICT DO NOTHING;
INSERT INTO document.documents (id, titre, description, etat, datecreation, datemodification, typemouvement, afficherprix, afficherunite, afficherdistributeur, prixeditable, contientressources, estencaissable) VALUES ('0190615e-1101-7209-9932-7020bbd556f3', 'Fiche de soin', 'Document delivre par le medecin ou un infirmier de l''etablissement', true, NULL, NULL, 'Ajout', 'true', true, true, true, true, false) ON CONFLICT DO NOTHING;
INSERT INTO document.documents (id, titre, description, etat, datecreation, datemodification, typemouvement, afficherprix, afficherunite, afficherdistributeur, prixeditable, contientressources, estencaissable) VALUES ('0190615e-1101-7209-9932-7020bbd556f4', 'Formulaire de sortie', 'Document delivre par le medecin ou un infirmier de l''etablissement', true, NULL, NULL, 'Neutre', 'true', true, true, true, true, true) ON CONFLICT DO NOTHING;
INSERT INTO document.documents (id, titre, description, etat, datecreation, datemodification, typemouvement, afficherprix, afficherunite, afficherdistributeur, prixeditable, contientressources, estencaissable) VALUES ('0190615e-1101-7209-9932-7020bbd556f5', 'ordonnance', 'Document delivre par le medecin ou un infirmier de l''etablissement', true, NULL, NULL, 'Neutre', 'true', true, true, true, true, false) ON CONFLICT DO NOTHING;
INSERT INTO document.documents (id, titre, description, etat, datecreation, datemodification, typemouvement, afficherprix, afficherunite, afficherdistributeur, prixeditable, contientressources, estencaissable) VALUES ('0190615e-1101-7209-9932-7020bbd556f6', 'Bon de commande', 'Document delivre par le chef service', true, NULL, NULL, 'Neutre', 'true', true, true, true, true, false) ON CONFLICT DO NOTHING;
INSERT INTO document.documents (id, titre, description, etat, datecreation, datemodification, typemouvement, afficherprix, afficherunite, afficherdistributeur, prixeditable, contientressources, estencaissable) VALUES ('0190615e-1101-7209-9932-7020bbd556f7', 'Bon de livraison', 'Document delivre par ', true, NULL, NULL, 'Reduire', 'true', true, true, true, true, true) ON CONFLICT DO NOTHING;
INSERT INTO document.documents (id, titre, description, etat, datecreation, datemodification, typemouvement, afficherprix, afficherunite, afficherdistributeur, prixeditable, contientressources, estencaissable) VALUES ('0190615e-1101-7209-9932-7020bbd556f8', 'Fiche de selection', 'Document delivre par le magasinier', true, NULL, NULL, 'Neutre', 'true', true, true, true, true, false) ON CONFLICT DO NOTHING;


--
-- TOC entry 5205 (class 0 OID 28118)
-- Dependencies: 226
-- Data for Name: documentspromotions; Type: TABLE DATA; Schema: document; Owner: -
--

INSERT INTO document.documentspromotions (documents_id, promotions_id) VALUES ('0190615e-1101-7209-9932-7020bbd556f1', '1979bd79-f81b-498b-b247-e7b9bbb3f600') ON CONFLICT DO NOTHING;
INSERT INTO document.documentspromotions (documents_id, promotions_id) VALUES ('0190615e-1101-7209-9932-7020bbd556f8', '1979bd79-f81b-498b-b247-e7b9bbb3f602') ON CONFLICT DO NOTHING;
INSERT INTO document.documentspromotions (documents_id, promotions_id) VALUES ('0190615e-1101-7209-9932-7020bbd556f5', '1979bd79-f81b-498b-b247-e7b9bbb3f601') ON CONFLICT DO NOTHING;


--
-- TOC entry 5244 (class 0 OID 28713)
-- Dependencies: 265
-- Data for Name: elements; Type: TABLE DATA; Schema: document; Owner: -
--



--
-- TOC entry 5242 (class 0 OID 28703)
-- Dependencies: 263
-- Data for Name: elementsbaselanques; Type: TABLE DATA; Schema: document; Owner: -
--



--
-- TOC entry 5239 (class 0 OID 28684)
-- Dependencies: 260
-- Data for Name: elementsbases; Type: TABLE DATA; Schema: document; Owner: -
--



--
-- TOC entry 5243 (class 0 OID 28708)
-- Dependencies: 264
-- Data for Name: elementslangues; Type: TABLE DATA; Schema: document; Owner: -
--



--
-- TOC entry 5206 (class 0 OID 28123)
-- Dependencies: 227
-- Data for Name: etapes; Type: TABLE DATA; Schema: document; Owner: -
--

INSERT INTO document.etapes (id, libelle, etat, datemodification, parcours_id) VALUES ('1901bd79-f71b-498b-b247-e7b9bbb3f600', 'Paracetamol', 'true', '2022-01-02', '1900bd79-f71b-498b-b247-e7b9bbb3f602') ON CONFLICT DO NOTHING;
INSERT INTO document.etapes (id, libelle, etat, datemodification, parcours_id) VALUES ('1901bd79-f71b-498b-b247-e7b9bbb3f601', 'Cartouche d''encre', 'true', '2020-01-08', '1900bd79-f71b-498b-b247-e7b9bbb3f600') ON CONFLICT DO NOTHING;
INSERT INTO document.etapes (id, libelle, etat, datemodification, parcours_id) VALUES ('1901bd79-f71b-498b-b247-e7b9bbb3f602', 'Scanner', 'true', '2014-01-04', '1900bd79-f71b-498b-b247-e7b9bbb3f601') ON CONFLICT DO NOTHING;


--
-- TOC entry 5207 (class 0 OID 28128)
-- Dependencies: 228
-- Data for Name: etats; Type: TABLE DATA; Schema: document; Owner: -
--

INSERT INTO document.etats (id, libelle, description, datecreation, datemodification) VALUES ('e190615e-1101-7209-9932-7020bbd556f1', 'En cours', 'En cours', '2022-01-01', '2022-01-02') ON CONFLICT DO NOTHING;
INSERT INTO document.etats (id, libelle, description, datecreation, datemodification) VALUES ('e190615e-1101-7209-9932-7020bbd556f2', 'Valide', 'Valide', '2022-01-01', '2022-01-02') ON CONFLICT DO NOTHING;
INSERT INTO document.etats (id, libelle, description, datecreation, datemodification) VALUES ('e190615e-1101-7209-9932-7020bbd556f3', 'Rejete', 'Rejete', '2022-01-01', '2022-01-02') ON CONFLICT DO NOTHING;
INSERT INTO document.etats (id, libelle, description, datecreation, datemodification) VALUES ('e190615e-1101-7209-9932-7020bbd556f4', 'En attente', 'En attente', '2022-01-01', '2022-01-02') ON CONFLICT DO NOTHING;


--
-- TOC entry 5208 (class 0 OID 28133)
-- Dependencies: 229
-- Data for Name: exemplaires; Type: TABLE DATA; Schema: document; Owner: -
--

INSERT INTO document.exemplaires (id, personnes_id, documents_id) VALUES ('a1f8e3b0-4c6d-4e5e-9b7a-2a8b9b9c8d6f', '0618e585-f82a-4d5f-af2c-54f880d766d3', NULL) ON CONFLICT DO NOTHING;
INSERT INTO document.exemplaires (id, personnes_id, documents_id) VALUES ('d9e2f1c2-8b3a-4f6c-9d5e-1a7b8a7c6d5e', '0618e585-f82a-4d5f-af2c-04f880d766d3', NULL) ON CONFLICT DO NOTHING;
INSERT INTO document.exemplaires (id, personnes_id, documents_id) VALUES ('b3a7c8d6-1e2f-3d4c-5b6a-9e8f0a7b6c5d', '1979bd79-f71b-498b-b247-e7b9bbb3f600', NULL) ON CONFLICT DO NOTHING;
INSERT INTO document.exemplaires (id, personnes_id, documents_id) VALUES ('f0a7b6c5-d4e3f2a1-8b9c-7d6e-5f4a3b2c1e0d', '0618e585-f82a-4d5f-af1c-54f880d766d3', NULL) ON CONFLICT DO NOTHING;
INSERT INTO document.exemplaires (id, personnes_id, documents_id) VALUES ('a1f1e3b0-4c6d-4e5e-9b7a-2a8b9b9c8d6f', 'd301ff83-2a62-4e6d-aa23-57c7825bcd57', NULL) ON CONFLICT DO NOTHING;
INSERT INTO document.exemplaires (id, personnes_id, documents_id) VALUES ('a1f1e4b0-4c6d-4e5e-9b7a-2a8b9b9c8d6f', 'fdbff692-6b8d-43ab-a196-fcc3114b2daa', NULL) ON CONFLICT DO NOTHING;


--
-- TOC entry 5209 (class 0 OID 28138)
-- Dependencies: 230
-- Data for Name: familles; Type: TABLE DATA; Schema: document; Owner: -
--

INSERT INTO document.familles (id, libelle, description, etat, datecreation, datemodification) VALUES ('f190615e-1101-7209-9932-7020bbd556f1', 'Medicaments', 'Medicaments', true, '2022-01-01', '2022-01-02') ON CONFLICT DO NOTHING;
INSERT INTO document.familles (id, libelle, description, etat, datecreation, datemodification) VALUES ('f190615e-1101-7209-9932-7020bbd556f2', 'Consommables Informatiques', 'Consommables Informatiques', true, '2022-01-01', '2022-01-02') ON CONFLICT DO NOTHING;
INSERT INTO document.familles (id, libelle, description, etat, datecreation, datemodification) VALUES ('f190615e-1101-7209-9932-7020bbd556f3', 'BioMedical', 'BioMedical', true, '2022-01-01', '2022-01-02') ON CONFLICT DO NOTHING;
INSERT INTO document.familles (id, libelle, description, etat, datecreation, datemodification) VALUES ('f190615e-1101-7209-9932-7020bbd556f4', 'Accessoires', 'Accessoires', true, '2022-01-01', '2022-01-02') ON CONFLICT DO NOTHING;


--
-- TOC entry 5210 (class 0 OID 28143)
-- Dependencies: 231
-- Data for Name: famillespromotions; Type: TABLE DATA; Schema: document; Owner: -
--

INSERT INTO document.famillespromotions (familles_id, promotions_id) VALUES ('f190615e-1101-7209-9932-7020bbd556f1', '1979bd79-f81b-498b-b247-e7b9bbb3f602') ON CONFLICT DO NOTHING;
INSERT INTO document.famillespromotions (familles_id, promotions_id) VALUES ('f190615e-1101-7209-9932-7020bbd556f3', '1979bd79-f81b-498b-b247-e7b9bbb3f601') ON CONFLICT DO NOTHING;
INSERT INTO document.famillespromotions (familles_id, promotions_id) VALUES ('f190615e-1101-7209-9932-7020bbd556f4', '1979bd79-f81b-498b-b247-e7b9bbb3f600') ON CONFLICT DO NOTHING;


--
-- TOC entry 5211 (class 0 OID 28148)
-- Dependencies: 232
-- Data for Name: filesattentes; Type: TABLE DATA; Schema: document; Owner: -
--

INSERT INTO document.filesattentes (id, datecreation, datemodification, etat, services_id) VALUES ('f190615e-1101-7209-9932-7020bbd556f1', '2022-01-01', '2022-01-02', true, '2190615e-1101-7209-9932-7020bbd556f1') ON CONFLICT DO NOTHING;
INSERT INTO document.filesattentes (id, datecreation, datemodification, etat, services_id) VALUES ('f190615e-1101-7209-9932-7020bbd556f2', '2022-01-01', '2022-01-02', true, '2190615e-1101-7209-9932-7020bbd556f2') ON CONFLICT DO NOTHING;
INSERT INTO document.filesattentes (id, datecreation, datemodification, etat, services_id) VALUES ('f190615e-1101-7209-9932-7020bbd556f3', '2022-01-01', '2022-01-02', true, '2190615e-1101-7209-9932-7020bbd556f3') ON CONFLICT DO NOTHING;


--
-- TOC entry 5249 (class 0 OID 28746)
-- Dependencies: 270
-- Data for Name: groupes; Type: TABLE DATA; Schema: document; Owner: -
--



--
-- TOC entry 5212 (class 0 OID 28153)
-- Dependencies: 233
-- Data for Name: jouerroles; Type: TABLE DATA; Schema: document; Owner: -
--

INSERT INTO document.jouerroles (id, etat, datecreation, datemodification, datefin, datedebut, personnels_id, roles_id) VALUES ('d76fd017-cceb-4926-8705-380b08ad9c6a', NULL, '2022-01-01', '2022-01-02', NULL, '2024-01-01', '4390615e-1101-7209-9932-7020bbd556f1', '5190615e-1101-7209-9932-7020bbd556f1') ON CONFLICT DO NOTHING;
INSERT INTO document.jouerroles (id, etat, datecreation, datemodification, datefin, datedebut, personnels_id, roles_id) VALUES ('d39bcc09-ffe6-48d1-8582-f1173671d59f', NULL, '2022-01-01', '2022-01-02', NULL, '2024-01-01', '4390615e-1101-7209-9932-7020bbd556f2', '6130615e-1101-7209-9932-7020bbd556f2') ON CONFLICT DO NOTHING;
INSERT INTO document.jouerroles (id, etat, datecreation, datemodification, datefin, datedebut, personnels_id, roles_id) VALUES ('9183d626-0c1a-4f70-8556-1c417d5feb91', NULL, '2022-01-01', '2022-01-02', NULL, '2024-01-01', '4390615e-1101-7209-9932-7020bbd556f3', '6191615e-1101-7209-9932-7020bbd556f3') ON CONFLICT DO NOTHING;


--
-- TOC entry 5241 (class 0 OID 28696)
-- Dependencies: 262
-- Data for Name: langues; Type: TABLE DATA; Schema: document; Owner: -
--



--
-- TOC entry 5245 (class 0 OID 28720)
-- Dependencies: 266
-- Data for Name: menus; Type: TABLE DATA; Schema: document; Owner: -
--



--
-- TOC entry 5213 (class 0 OID 28158)
-- Dependencies: 234
-- Data for Name: missions; Type: TABLE DATA; Schema: document; Owner: -
--

INSERT INTO document.missions (id, libelle, description, etat, datecreation, datemodification, services_id) VALUES ('3190615e-1101-7209-9932-7020bbd556f1', 'Encaissement', 'recu de paiement lié à une mission', true, '2000-03-07', '1990-03-07', '2190615e-1101-7209-9932-7020bbd556f1') ON CONFLICT DO NOTHING;
INSERT INTO document.missions (id, libelle, description, etat, datecreation, datemodification, services_id) VALUES ('3190615e-1101-7209-9932-7020bbd556f2', 'Resultat Labo', 'Communiquer les résultats du labo aux patients', true, '2000-03-07', '1990-03-07', '2190615e-1101-7209-9932-7020bbd556f1') ON CONFLICT DO NOTHING;
INSERT INTO document.missions (id, libelle, description, etat, datecreation, datemodification, services_id) VALUES ('3190615e-1101-7209-9932-7020bbd556f3', 'Hospitalisation', 'bon d''entrée et de sortie est une mission', true, '2000-03-07', '1990-03-07', '2190615e-1101-7209-9932-7020bbd556f2') ON CONFLICT DO NOTHING;
INSERT INTO document.missions (id, libelle, description, etat, datecreation, datemodification, services_id) VALUES ('3190615e-1101-7209-9932-7020bbd556f4', 'Prelevement Labo', 'Prélévement fait par laboratoire', true, '2000-03-07', '1990-03-07', '2190615e-1101-7209-9932-7020bbd556f2') ON CONFLICT DO NOTHING;
INSERT INTO document.missions (id, libelle, description, etat, datecreation, datemodification, services_id) VALUES ('3190615e-1101-7209-9932-7020bbd556f5', 'Consultation Spécialiste', 'Consultation faite par un médecin', true, '2000-03-07', '1990-03-07', '2190615e-1101-7209-9932-7020bbd556f3') ON CONFLICT DO NOTHING;
INSERT INTO document.missions (id, libelle, description, etat, datecreation, datemodification, services_id) VALUES ('3190615e-1101-7209-9932-7020bbd556f6', 'Consultation', 'Consultation faite par une infirmière', true, '2000-03-07', '1990-03-07', '2190615e-1101-7209-9932-7020bbd556f3') ON CONFLICT DO NOTHING;


--
-- TOC entry 5214 (class 0 OID 28163)
-- Dependencies: 235
-- Data for Name: mouvementcaisses; Type: TABLE DATA; Schema: document; Owner: -
--

INSERT INTO document.mouvementcaisses (id, montant, moyenpaiement, referencepaiement, detailjson, etat, typemvt, libelle, caisses_id, comptes_id, personnels_id, exemplaires_id, datecreation) VALUES ('97d314f5-84ad-77cd-874d-9c55f0e45790', 45.21, 'sur commande', NULL, NULL, NULL, NULL, NULL, 'a1f8e3b0-4c6d-4e5e-ab7a-2a8b9b9c8d6f', 'a1f8e3b0-4c6d-4e5e-7b7a-2a8b9b9c8d6f', '4390615e-1101-7209-9932-7020bbd556f1', 'a1f8e3b0-4c6d-4e5e-9b7a-2a8b9b9c8d6f', '2022-01-02') ON CONFLICT DO NOTHING;
INSERT INTO document.mouvementcaisses (id, montant, moyenpaiement, referencepaiement, detailjson, etat, typemvt, libelle, caisses_id, comptes_id, personnels_id, exemplaires_id, datecreation) VALUES ('bfee750d-9b8c-7475-9373-08eff9b3ccb7', 1258.36, 'vente à credit', NULL, NULL, NULL, NULL, NULL, 'd9e2f1c2-8b3a-4f6c-bd5e-1a7b8a7c6d5e', 'd9e2f1c2-8b3a-4f6c-7d5e-1a7b8a7c6d5e', '4390615e-1101-7209-9932-7020bbd556f2', 'd9e2f1c2-8b3a-4f6c-9d5e-1a7b8a7c6d5e', '2022-01-02') ON CONFLICT DO NOTHING;
INSERT INTO document.mouvementcaisses (id, montant, moyenpaiement, referencepaiement, detailjson, etat, typemvt, libelle, caisses_id, comptes_id, personnels_id, exemplaires_id, datecreation) VALUES ('0e7cea07-d09e-7f79-be6c-6dd27aecbce6', 1548.21, 'achat express', NULL, NULL, NULL, NULL, NULL, 'b3a7c8d6-1e2f-3d4c-cb6a-9e8f0a7b6c5d', 'b3a7c8d6-1e2f-3d4c-7b6a-9e8f0a7b6c5d', '4390615e-1101-7209-9932-7020bbd556f3', 'b3a7c8d6-1e2f-3d4c-5b6a-9e8f0a7b6c5d', '2022-01-02') ON CONFLICT DO NOTHING;
INSERT INTO document.mouvementcaisses (id, montant, moyenpaiement, referencepaiement, detailjson, etat, typemvt, libelle, caisses_id, comptes_id, personnels_id, exemplaires_id, datecreation) VALUES ('decaedc8-a908-7cad-bdd6-0403a2614f22', 4563.21, 'inventaire', NULL, NULL, NULL, NULL, NULL, 'f0a7b6c5-d4e3f2a1-8d9c-7d6e-5f4a3b2c1e0d', 'f0a7b6c5-d4e3f2a1-7b9c-7d6e-5f4a3b2c1e0d', '4390615e-1101-7209-9932-7020bbd556f3', 'f0a7b6c5-d4e3f2a1-8b9c-7d6e-5f4a3b2c1e0d', '2022-01-02') ON CONFLICT DO NOTHING;
INSERT INTO document.mouvementcaisses (id, montant, moyenpaiement, referencepaiement, detailjson, etat, typemvt, libelle, caisses_id, comptes_id, personnels_id, exemplaires_id, datecreation) VALUES ('0e7cea07-d09e-7f79-be6c-6dd27aecbc47', 5245.54, 'sur commande', NULL, NULL, NULL, NULL, NULL, 'b3a7c8d6-1e2f-3d4c-cb6a-9e8f0a7b6c5d', 'd301ff83-1b62-4e6d-aa23-57c7825bcd57', '4390615e-1101-7209-9932-7020bbd556f3', 'a1f1e3b0-4c6d-4e5e-9b7a-2a8b9b9c8d6f', '2022-01-02') ON CONFLICT DO NOTHING;
INSERT INTO document.mouvementcaisses (id, montant, moyenpaiement, referencepaiement, detailjson, etat, typemvt, libelle, caisses_id, comptes_id, personnels_id, exemplaires_id, datecreation) VALUES ('0e7cea07-d09e-7f79-be6c-6dd27aecbc14', 42542453.54, 'sur commande', NULL, NULL, NULL, NULL, NULL, 'f0a7b6c5-d4e3f2a1-8d9c-7d6e-5f4a3b2c1e0d', 'adbff692-418d-43ab-a196-fcc3114b2daa', '4390615e-1101-7209-9932-7020bbd556f1', 'a1f1e4b0-4c6d-4e5e-9b7a-2a8b9b9c8d6f', '2022-01-02') ON CONFLICT DO NOTHING;


--
-- TOC entry 5215 (class 0 OID 28168)
-- Dependencies: 236
-- Data for Name: mouvements; Type: TABLE DATA; Schema: document; Owner: -
--

INSERT INTO document.mouvements (id, description, quantite, prix, datecreation, dateperemption, datemodification, ressources_id, distributeurs_id) VALUES ('97d314f5-84ad-47cd-874d-9c55f0e45790', 'sur commande', 10, 5000, '2022-01-01', NULL, '2022-01-02', '6190615e-1101-7209-9932-7020bbd556f1', '1979bd79-f71b-498b-b247-e7b9bbb3f600') ON CONFLICT DO NOTHING;
INSERT INTO document.mouvements (id, description, quantite, prix, datecreation, dateperemption, datemodification, ressources_id, distributeurs_id) VALUES ('bfee750d-9b8c-4475-9373-08eff9b3ccb7', 'vente à credit', 20, 10000, '2022-01-01', NULL, '2022-01-02', '6190615e-1101-7209-9932-7020bbd556f2', '0618e585-f82a-4d5f-af1c-54f880d766d3') ON CONFLICT DO NOTHING;
INSERT INTO document.mouvements (id, description, quantite, prix, datecreation, dateperemption, datemodification, ressources_id, distributeurs_id) VALUES ('0e7cea07-d09e-4f79-be6c-6dd27aecbce6', 'achat express', 5, 2000, '2022-01-01', NULL, '2022-01-02', '6190615e-1101-7209-9932-7020bbd556f3', '0618e585-f82a-4d5f-af2c-54f880d766d3') ON CONFLICT DO NOTHING;
INSERT INTO document.mouvements (id, description, quantite, prix, datecreation, dateperemption, datemodification, ressources_id, distributeurs_id) VALUES ('decaedc8-a908-4cad-bdd6-0403a2614f22', 'inventaire', 5, 2000, '2022-01-01', NULL, '2022-01-02', '6190615e-1101-7209-9932-7020bbd556f4', '0618e585-f82a-4d5f-af2c-04f880d766d3') ON CONFLICT DO NOTHING;


--
-- TOC entry 5216 (class 0 OID 28173)
-- Dependencies: 237
-- Data for Name: ordreetats; Type: TABLE DATA; Schema: document; Owner: -
--

INSERT INTO document.ordreetats (id, datecreation, datemodification, datefinvote, ordre, etats_id) VALUES ('97d314a5-84ad-47cd-874d-9c55f0e45790', '2022-01-01', '2022-01-02', '2022-01-02', 2, 'e190615e-1101-7209-9932-7020bbd556f3') ON CONFLICT DO NOTHING;
INSERT INTO document.ordreetats (id, datecreation, datemodification, datefinvote, ordre, etats_id) VALUES ('97d314a5-84ad-47cd-874d-9c55f0e45791', '2022-01-01', '2022-01-02', '2022-01-02', 5, 'e190615e-1101-7209-9932-7020bbd556f2') ON CONFLICT DO NOTHING;
INSERT INTO document.ordreetats (id, datecreation, datemodification, datefinvote, ordre, etats_id) VALUES ('97d314a5-84ad-47cd-874d-9c55f0e45792', '2022-01-01', '2022-01-02', '2022-01-02', 8, 'e190615e-1101-7209-9932-7020bbd556f1') ON CONFLICT DO NOTHING;


--
-- TOC entry 5247 (class 0 OID 28734)
-- Dependencies: 268
-- Data for Name: organisations; Type: TABLE DATA; Schema: document; Owner: -
--



--
-- TOC entry 5248 (class 0 OID 28741)
-- Dependencies: 269
-- Data for Name: organiser; Type: TABLE DATA; Schema: document; Owner: -
--



--
-- TOC entry 5217 (class 0 OID 28178)
-- Dependencies: 238
-- Data for Name: parcours; Type: TABLE DATA; Schema: document; Owner: -
--

INSERT INTO document.parcours (id, libelle, datecreation, datemodification) VALUES ('1900bd79-f71b-498b-b247-e7b9bbb3f600', 'Paracetamol', '2022-01-02', '2022-01-02') ON CONFLICT DO NOTHING;
INSERT INTO document.parcours (id, libelle, datecreation, datemodification) VALUES ('1900bd79-f71b-498b-b247-e7b9bbb3f601', 'Cartouche d''encre', '2020-01-08', '2022-01-02') ON CONFLICT DO NOTHING;
INSERT INTO document.parcours (id, libelle, datecreation, datemodification) VALUES ('1900bd79-f71b-498b-b247-e7b9bbb3f602', 'Scanner', '2014-01-04', '2022-01-02') ON CONFLICT DO NOTHING;


--
-- TOC entry 5218 (class 0 OID 28183)
-- Dependencies: 239
-- Data for Name: personnels; Type: TABLE DATA; Schema: document; Owner: -
--

INSERT INTO document.personnels (id, dateentree, nom, datenaissance, telephone, datesortie, email, prenom, sexe, datemodification) VALUES ('4390615e-1101-7209-9932-7020bbd556f1', '2024-07-27', 'Tagne', '2000-04-10', '655455487', NULL, 'tagnewillie@gmail.com', 'Willy', 'M', NULL) ON CONFLICT DO NOTHING;
INSERT INTO document.personnels (id, dateentree, nom, datenaissance, telephone, datesortie, email, prenom, sexe, datemodification) VALUES ('4390615e-1101-7209-9932-7020bbd556f2', '2024-07-27', 'Peter', '2004-08-10', '655455487', NULL, 'peteralan@gmail.com', 'Alan', 'M', NULL) ON CONFLICT DO NOTHING;
INSERT INTO document.personnels (id, dateentree, nom, datenaissance, telephone, datesortie, email, prenom, sexe, datemodification) VALUES ('4390615e-1101-7209-9932-7020bbd556f3', '2024-07-27', 'Dombo', '2002-10-10', '655455487', NULL, 'dombogilles@gmail.com', 'Gilles', 'M', NULL) ON CONFLICT DO NOTHING;


--
-- TOC entry 5219 (class 0 OID 28188)
-- Dependencies: 240
-- Data for Name: personnes; Type: TABLE DATA; Schema: document; Owner: -
--

INSERT INTO document.personnes (id, adresse, mail, telephone, qrcodevalue, code, raisonsociale, nom, prenom, sexe, datenaissance, datecreation, datemodification, person_type, comptes_id) VALUES ('0001ff83-2a62-4e6d-aa23-57c7825bcd57', NULL, NULL, NULL, NULL, '47', 'Sportif', NULL, NULL, NULL, NULL, '1990-03-07', '2022-01-01', 'personnesmorales', '00012f83-2a62-4e6d-aa23-57c7825bcd57') ON CONFLICT DO NOTHING;
INSERT INTO document.personnes (id, adresse, mail, telephone, qrcodevalue, code, raisonsociale, nom, prenom, sexe, datenaissance, datecreation, datemodification, person_type, comptes_id) VALUES ('0018e585-f82a-4d5f-af1c-54f880d766d3', NULL, NULL, NULL, NULL, '45', 'armee', NULL, NULL, NULL, NULL, '1990-03-07', '2022-01-01', 'personnesmorales', '1518e585-f82a-4d5f-af1c-54f880d766d3') ON CONFLICT DO NOTHING;
INSERT INTO document.personnes (id, adresse, mail, telephone, qrcodevalue, code, raisonsociale, nom, prenom, sexe, datenaissance, datecreation, datemodification, person_type, comptes_id) VALUES ('0079bd79-f71b-498b-b247-e7b9bbb3f600', NULL, NULL, NULL, NULL, '43', 'GOSPEL', NULL, NULL, NULL, NULL, '1990-03-07', '2022-01-01', 'personnesmorales', '1179bd79-f71b-498b-b247-e7b9bbb3f600') ON CONFLICT DO NOTHING;
INSERT INTO document.personnes (id, adresse, mail, telephone, qrcodevalue, code, raisonsociale, nom, prenom, sexe, datenaissance, datecreation, datemodification, person_type, comptes_id) VALUES ('0618e585-f82a-4d5f-af1c-54f880d766d3', NULL, NULL, NULL, NULL, 'd190616', 'Brasserie', NULL, NULL, NULL, NULL, '1990-03-07', '2022-01-01', 'distributeurs', 'f0a7b6c5-d4e3f2a1-7b9c-7d6e-5f4a3b2c1e0d') ON CONFLICT DO NOTHING;
INSERT INTO document.personnes (id, adresse, mail, telephone, qrcodevalue, code, raisonsociale, nom, prenom, sexe, datenaissance, datecreation, datemodification, person_type, comptes_id) VALUES ('0618e585-f82a-4d5f-af2c-04f880d766d3', NULL, NULL, NULL, NULL, 'd190614', 'Pharmacam', NULL, NULL, NULL, NULL, '1990-03-07', '2022-01-01', 'distributeurs', 'd9e2f1c2-8b3a-4f6c-7d5e-1a7b8a7c6d5e') ON CONFLICT DO NOTHING;
INSERT INTO document.personnes (id, adresse, mail, telephone, qrcodevalue, code, raisonsociale, nom, prenom, sexe, datenaissance, datecreation, datemodification, person_type, comptes_id) VALUES ('0618e585-f82a-4d5f-af2c-54f880d766d3', NULL, NULL, NULL, NULL, 'd190619', 'UCB', NULL, NULL, NULL, NULL, '1990-03-07', '2022-01-01', 'distributeurs', 'a1f8e3b0-4c6d-4e5e-7b7a-2a8b9b9c8d6f') ON CONFLICT DO NOTHING;
INSERT INTO document.personnes (id, adresse, mail, telephone, qrcodevalue, code, raisonsociale, nom, prenom, sexe, datenaissance, datecreation, datemodification, person_type, comptes_id) VALUES ('1979bd79-f71b-498b-b247-e7b9bbb3f600', NULL, NULL, NULL, NULL, 'd190615', 'ENEO', NULL, NULL, NULL, NULL, '1990-03-07', '2022-01-01', 'distributeurs', 'b3a7c8d6-1e2f-3d4c-7b6a-9e8f0a7b6c5d') ON CONFLICT DO NOTHING;
INSERT INTO document.personnes (id, adresse, mail, telephone, qrcodevalue, code, raisonsociale, nom, prenom, sexe, datenaissance, datecreation, datemodification, person_type, comptes_id) VALUES ('fdbff692-6b8d-43ab-a196-fcc3114b2daa', NULL, NULL, NULL, NULL, NULL, NULL, 'Nkoabang', 'pnjerrtt@gma.com', 'Masculin', '2022-01-01', '1990-03-07', '2022-01-02', 'personnesphysique', 'adbff692-418d-43ab-a196-fcc3114b2daa') ON CONFLICT DO NOTHING;
INSERT INTO document.personnes (id, adresse, mail, telephone, qrcodevalue, code, raisonsociale, nom, prenom, sexe, datenaissance, datecreation, datemodification, person_type, comptes_id) VALUES ('d301ff83-2a62-4e6d-aa23-57c7825bcd57', NULL, NULL, NULL, NULL, NULL, NULL, 'Essos', 'nayat@gma.com', 'Masculin', '2022-01-01', '1990-03-07', '2022-01-02', 'distributeurs', 'd301ff83-1b62-4e6d-aa23-57c7825bcd57') ON CONFLICT DO NOTHING;


--
-- TOC entry 5220 (class 0 OID 28194)
-- Dependencies: 241
-- Data for Name: personnes_s; Type: TABLE DATA; Schema: document; Owner: -
--



--
-- TOC entry 5221 (class 0 OID 28199)
-- Dependencies: 242
-- Data for Name: precomouvements; Type: TABLE DATA; Schema: document; Owner: -
--

INSERT INTO document.precomouvements (id, libelle, etat, datecreation, datemodification, typemouvement) VALUES ('6290615e-1101-7209-9932-7020bbd556f1', 'Inventaire', true, '2022-01-01', '2022-01-02', 'Neutre') ON CONFLICT DO NOTHING;
INSERT INTO document.precomouvements (id, libelle, etat, datecreation, datemodification, typemouvement) VALUES ('6290615e-1101-7209-9932-7020bbd556f2', 'Vente', true, '2022-01-01', '2022-01-02', 'Reduire') ON CONFLICT DO NOTHING;
INSERT INTO document.precomouvements (id, libelle, etat, datecreation, datemodification, typemouvement) VALUES ('17ee6932-2fcd-4b93-9c4c-0a4dbf659bff', 'Achat', true, '2022-01-01', '2022-01-02', 'Ajout') ON CONFLICT DO NOTHING;
INSERT INTO document.precomouvements (id, libelle, etat, datecreation, datemodification, typemouvement) VALUES ('77b8577f-6d26-4376-af30-a3c8f75a9194', 'interdiction Infirmière', true, '2022-01-01', '2022-01-02', 'Neutre') ON CONFLICT DO NOTHING;
INSERT INTO document.precomouvements (id, libelle, etat, datecreation, datemodification, typemouvement) VALUES ('6290615e-1101-7209-9932-7020bbd556f5', 'Don', true, '2022-01-01', '2022-01-02', 'Neutre') ON CONFLICT DO NOTHING;
INSERT INTO document.precomouvements (id, libelle, etat, datecreation, datemodification, typemouvement) VALUES ('6290615e-1101-7209-9932-7020bbd556f6', 'Perte', true, '2022-01-01', '2022-01-02', 'Reduire') ON CONFLICT DO NOTHING;
INSERT INTO document.precomouvements (id, libelle, etat, datecreation, datemodification, typemouvement) VALUES ('6290615e-1101-7209-9932-7020bbd556f7', 'Retour', true, '2022-01-01', '2022-01-02', 'Ajout') ON CONFLICT DO NOTHING;
INSERT INTO document.precomouvements (id, libelle, etat, datecreation, datemodification, typemouvement) VALUES ('6290615e-1101-7209-9932-7020bbd556f8', 'Sortie magasin', true, '2022-01-01', '2022-01-02', 'Reduire') ON CONFLICT DO NOTHING;


--
-- TOC entry 5222 (class 0 OID 28204)
-- Dependencies: 243
-- Data for Name: precomouvementsqtes; Type: TABLE DATA; Schema: document; Owner: -
--

INSERT INTO document.precomouvementsqtes (id, qtemin, qtemax, montantmin, montantmax, datecreation, datemodification, precomouvements_id, ressources_id) VALUES ('a97eb081-62f6-4617-ba29-64dc8593a9ff', 10, 100, 500, 5000, '2022-01-01', '2022-01-02', '6290615e-1101-7209-9932-7020bbd556f1', '6190615e-1101-7209-9932-7020bbd556f1') ON CONFLICT DO NOTHING;
INSERT INTO document.precomouvementsqtes (id, qtemin, qtemax, montantmin, montantmax, datecreation, datemodification, precomouvements_id, ressources_id) VALUES ('a87eb081-62f6-4617-ba29-64dc8593a9ff', 20, 200, 1000, 10000, '2022-01-01', '2022-01-02', '6290615e-1101-7209-9932-7020bbd556f2', '6190615e-1101-7209-9932-7020bbd556f2') ON CONFLICT DO NOTHING;
INSERT INTO document.precomouvementsqtes (id, qtemin, qtemax, montantmin, montantmax, datecreation, datemodification, precomouvements_id, ressources_id) VALUES ('a77eb081-62f6-4617-ba29-64dc8593a9ff', 5, 50, 200, 2000, '2022-01-01', '2022-01-02', '17ee6932-2fcd-4b93-9c4c-0a4dbf659bff', '6190615e-1101-7209-9932-7020bbd556f3') ON CONFLICT DO NOTHING;
INSERT INTO document.precomouvementsqtes (id, qtemin, qtemax, montantmin, montantmax, datecreation, datemodification, precomouvements_id, ressources_id) VALUES ('a67eb081-62f6-4617-ba29-64dc8593a9ff', 5, 50, 200, 2000, '2022-01-01', '2022-01-02', '77b8577f-6d26-4376-af30-a3c8f75a9194', '6190615e-1101-7209-9932-7020bbd556f4') ON CONFLICT DO NOTHING;
INSERT INTO document.precomouvementsqtes (id, qtemin, qtemax, montantmin, montantmax, datecreation, datemodification, precomouvements_id, ressources_id) VALUES ('a97eb071-62f6-4617-ba29-64dc8593a9ff', 5, 50, 200, 2000, '2022-01-01', '2022-01-02', '77b8577f-6d26-4376-af30-a3c8f75a9194', '6190615e-1101-7209-9932-7020bbd556f5') ON CONFLICT DO NOTHING;
INSERT INTO document.precomouvementsqtes (id, qtemin, qtemax, montantmin, montantmax, datecreation, datemodification, precomouvements_id, ressources_id) VALUES ('a97eb081-72f6-4617-ba29-64dc8593a9ff', 5, 50, 200, 2000, '2022-01-01', '2022-01-02', '6290615e-1101-7209-9932-7020bbd556f6', NULL) ON CONFLICT DO NOTHING;
INSERT INTO document.precomouvementsqtes (id, qtemin, qtemax, montantmin, montantmax, datecreation, datemodification, precomouvements_id, ressources_id) VALUES ('a97eb081-62f6-4617-ba49-64dc8593a9ff', 5, 50, 200, 2000, '2022-01-01', '2022-01-02', '6290615e-1101-7209-9932-7020bbd556f7', NULL) ON CONFLICT DO NOTHING;
INSERT INTO document.precomouvementsqtes (id, qtemin, qtemax, montantmin, montantmax, datecreation, datemodification, precomouvements_id, ressources_id) VALUES ('a97eb081-62f6-4617-ba29-65dc8593a9ff', 5, 50, 200, 2000, '2022-01-01', '2022-01-02', '6290615e-1101-7209-9932-7020bbd556f8', NULL) ON CONFLICT DO NOTHING;
INSERT INTO document.precomouvementsqtes (id, qtemin, qtemax, montantmin, montantmax, datecreation, datemodification, precomouvements_id, ressources_id) VALUES ('a97eb081-62f6-4617-ba29-74dc8593a9ff', 5, 50, 200, 2000, '2022-01-01', '2022-01-02', '6290615e-1101-7209-9932-7020bbd556f8', NULL) ON CONFLICT DO NOTHING;


--
-- TOC entry 5223 (class 0 OID 28209)
-- Dependencies: 244
-- Data for Name: promotions; Type: TABLE DATA; Schema: document; Owner: -
--

INSERT INTO document.promotions (id, datedebut, datefin, codeunique, typeremise, valeurremise, datecreation, datemodification, distributeurs_id) VALUES ('1979bd79-f81b-498b-b247-e7b9bbb3f600', '2022-01-01', '2022-01-01', 'R5', 'garantie', 54.25, '2000-04-10', '2022-01-01', '1979bd79-f71b-498b-b247-e7b9bbb3f600') ON CONFLICT DO NOTHING;
INSERT INTO document.promotions (id, datedebut, datefin, codeunique, typeremise, valeurremise, datecreation, datemodification, distributeurs_id) VALUES ('1979bd79-f81b-498b-b247-e7b9bbb3f601', '2022-01-01', '2022-01-01', 'R54', 'virement', 74.25, '2004-08-10', '2022-01-01', '0618e585-f82a-4d5f-af1c-54f880d766d3') ON CONFLICT DO NOTHING;
INSERT INTO document.promotions (id, datedebut, datefin, codeunique, typeremise, valeurremise, datecreation, datemodification, distributeurs_id) VALUES ('1979bd79-f81b-498b-b247-e7b9bbb3f602', '2022-01-01', '2022-01-01', 'R45', 'devoir', 95.23, '2002-10-10', '2022-01-01', 'd301ff83-2a62-4e6d-aa23-57c7825bcd57') ON CONFLICT DO NOTHING;


--
-- TOC entry 5224 (class 0 OID 28214)
-- Dependencies: 245
-- Data for Name: rattacher; Type: TABLE DATA; Schema: document; Owner: -
--

INSERT INTO document.rattacher (personnes_id, rattacher_id) VALUES ('1979bd79-f71b-498b-b247-e7b9bbb3f600', 'd301ff83-2a62-4e6d-aa23-57c7825bcd57') ON CONFLICT DO NOTHING;
INSERT INTO document.rattacher (personnes_id, rattacher_id) VALUES ('fdbff692-6b8d-43ab-a196-fcc3114b2daa', '0618e585-f82a-4d5f-af1c-54f880d766d3') ON CONFLICT DO NOTHING;


--
-- TOC entry 5225 (class 0 OID 28219)
-- Dependencies: 246
-- Data for Name: remplir; Type: TABLE DATA; Schema: document; Owner: -
--

INSERT INTO document.remplir (datefin, datecreation, datedebut, etat, droitajouter, droitmodifier, droitconsulter, droitvalider, roles_id, missions_id) VALUES ('2022-01-02', '2022-01-02', '2022-01-02', true, true, true, true, true, '6130615e-1101-7209-9932-7020bbd556f2', '3190615e-1101-7209-9932-7020bbd556f1') ON CONFLICT DO NOTHING;
INSERT INTO document.remplir (datefin, datecreation, datedebut, etat, droitajouter, droitmodifier, droitconsulter, droitvalider, roles_id, missions_id) VALUES ('2022-01-02', '2022-01-02', '2022-01-02', true, true, true, true, true, '5190615e-1101-7209-9932-7020bbd556f1', '3190615e-1101-7209-9932-7020bbd556f2') ON CONFLICT DO NOTHING;
INSERT INTO document.remplir (datefin, datecreation, datedebut, etat, droitajouter, droitmodifier, droitconsulter, droitvalider, roles_id, missions_id) VALUES ('2022-01-02', '2022-01-02', '2022-01-02', true, true, true, true, true, '6191615e-1101-7209-9932-7020bbd556f3', '3190615e-1101-7209-9932-7020bbd556f3') ON CONFLICT DO NOTHING;


--
-- TOC entry 5226 (class 0 OID 28224)
-- Dependencies: 247
-- Data for Name: respecter; Type: TABLE DATA; Schema: document; Owner: -
--

INSERT INTO document.respecter (mouvements_id, precomouvements_id) VALUES ('97d314f5-84ad-47cd-874d-9c55f0e45790', '6290615e-1101-7209-9932-7020bbd556f1') ON CONFLICT DO NOTHING;
INSERT INTO document.respecter (mouvements_id, precomouvements_id) VALUES ('bfee750d-9b8c-4475-9373-08eff9b3ccb7', '6290615e-1101-7209-9932-7020bbd556f2') ON CONFLICT DO NOTHING;
INSERT INTO document.respecter (mouvements_id, precomouvements_id) VALUES ('0e7cea07-d09e-4f79-be6c-6dd27aecbce6', '17ee6932-2fcd-4b93-9c4c-0a4dbf659bff') ON CONFLICT DO NOTHING;


--
-- TOC entry 5227 (class 0 OID 28229)
-- Dependencies: 248
-- Data for Name: ressources; Type: TABLE DATA; Schema: document; Owner: -
--

INSERT INTO document.ressources (id, libelle, description, etat, datecreation, datemodification, quantite, seuilalerte, prixentree, prixsortie, unite, familles_id) VALUES ('6190615e-1101-7209-9932-7020bbd556f1', 'Paracetamol', 'Paracetamol', true, '2022-01-01', '2022-01-02', 4, 20, 1200, 2000, 'Boite', 'f190615e-1101-7209-9932-7020bbd556f1') ON CONFLICT DO NOTHING;
INSERT INTO document.ressources (id, libelle, description, etat, datecreation, datemodification, quantite, seuilalerte, prixentree, prixsortie, unite, familles_id) VALUES ('6190615e-1101-7209-9932-7020bbd556f2', 'Cartouche d''encre', 'Cartouche d''encre', true, '2022-01-01', '2022-01-02', 4, 10, 2000, 3000, 'Boite', 'f190615e-1101-7209-9932-7020bbd556f2') ON CONFLICT DO NOTHING;
INSERT INTO document.ressources (id, libelle, description, etat, datecreation, datemodification, quantite, seuilalerte, prixentree, prixsortie, unite, familles_id) VALUES ('6190615e-1101-7209-9932-7020bbd556f3', 'Scanner', 'Scanner', true, '2022-01-01', '2022-01-02', 4, 2, 12000, 20000, 'Packs', 'f190615e-1101-7209-9932-7020bbd556f2') ON CONFLICT DO NOTHING;
INSERT INTO document.ressources (id, libelle, description, etat, datecreation, datemodification, quantite, seuilalerte, prixentree, prixsortie, unite, familles_id) VALUES ('6190615e-1101-7209-9932-7020bbd556f4', 'Imprimante', 'Imprimante', true, '2022-01-01', '2022-01-02', 4, 2, 12000, 20000, 'Packs', 'f190615e-1101-7209-9932-7020bbd556f2') ON CONFLICT DO NOTHING;
INSERT INTO document.ressources (id, libelle, description, etat, datecreation, datemodification, quantite, seuilalerte, prixentree, prixsortie, unite, familles_id) VALUES ('6190615e-1101-7209-9932-7020bbd556f5', 'Souris', 'Souris', true, '2022-01-01', '2022-01-02', 40, 12, 25, 20, 'Packs', 'f190615e-1101-7209-9932-7020bbd556f2') ON CONFLICT DO NOTHING;
INSERT INTO document.ressources (id, libelle, description, etat, datecreation, datemodification, quantite, seuilalerte, prixentree, prixsortie, unite, familles_id) VALUES ('6190615e-1101-7209-9932-7020bbd556f6', 'Stylet', 'Stylet', true, '2022-01-01', '2022-01-02', 24, 9, 20, 20000, 'Packs', 'f190615e-1101-7209-9932-7020bbd556f2') ON CONFLICT DO NOTHING;
INSERT INTO document.ressources (id, libelle, description, etat, datecreation, datemodification, quantite, seuilalerte, prixentree, prixsortie, unite, familles_id) VALUES ('6190615e-1101-7209-9932-7020bbd556f7', 'Doliprane', 'Doliprane', true, '2022-01-01', '2022-01-02', 90, 40, 1200, 2000, 'Boite', 'f190615e-1101-7209-9932-7020bbd556f1') ON CONFLICT DO NOTHING;
INSERT INTO document.ressources (id, libelle, description, etat, datecreation, datemodification, quantite, seuilalerte, prixentree, prixsortie, unite, familles_id) VALUES ('6190615e-1101-7209-9932-7020bbd556f8', 'Pommade', 'Pommade', true, '2022-01-01', '2022-01-02', 20, 20, 200, 210, 'Litre', 'f190615e-1101-7209-9932-7020bbd556f1') ON CONFLICT DO NOTHING;
INSERT INTO document.ressources (id, libelle, description, etat, datecreation, datemodification, quantite, seuilalerte, prixentree, prixsortie, unite, familles_id) VALUES ('6190615e-1101-7209-9932-7020bbd556f9', 'Seringue', 'Seringue', true, '2022-01-01', '2022-01-02', 42, 20, 100, 200, 'Boite', 'f190615e-1101-7209-9932-7020bbd556f3') ON CONFLICT DO NOTHING;
INSERT INTO document.ressources (id, libelle, description, etat, datecreation, datemodification, quantite, seuilalerte, prixentree, prixsortie, unite, familles_id) VALUES ('6190615e-1101-7209-9932-7020bbd556f0', 'Perfuseur', 'Perfuseur', true, '2022-01-01', '2022-01-02', 5, 20, 90, 230, 'Boite', 'f190615e-1101-7209-9932-7020bbd556f3') ON CONFLICT DO NOTHING;
INSERT INTO document.ressources (id, libelle, description, etat, datecreation, datemodification, quantite, seuilalerte, prixentree, prixsortie, unite, familles_id) VALUES ('5f516ee3-b23d-4cf1-8d55-e301764e0501', 'Gants', 'Gants', true, '2022-01-01', '2022-01-02', 4, 20, 1200, 2000, 'Boite', 'f190615e-1101-7209-9932-7020bbd556f3') ON CONFLICT DO NOTHING;
INSERT INTO document.ressources (id, libelle, description, etat, datecreation, datemodification, quantite, seuilalerte, prixentree, prixsortie, unite, familles_id) VALUES ('5f516ee3-b23d-4cf1-8d55-e301764e0502', 'Masque', 'Masque', true, '2022-01-01', '2022-01-02', 4, 20, 1200, 2000, 'Boite', 'f190615e-1101-7209-9932-7020bbd556f3') ON CONFLICT DO NOTHING;
INSERT INTO document.ressources (id, libelle, description, etat, datecreation, datemodification, quantite, seuilalerte, prixentree, prixsortie, unite, familles_id) VALUES ('5f516ee3-b23d-4cf1-8d55-e301764e0503', 'Gel Hydroalcoolique', 'Gel Hydroalcoolique', true, '2022-01-01', '2022-01-02', 54, 200, 1200, 2000, 'Litre', 'f190615e-1101-7209-9932-7020bbd556f3') ON CONFLICT DO NOTHING;
INSERT INTO document.ressources (id, libelle, description, etat, datecreation, datemodification, quantite, seuilalerte, prixentree, prixsortie, unite, familles_id) VALUES ('5f516ee3-b23d-4cf1-8d55-e301764e0504', 'Coton', 'Coton', true, '2022-01-01', '2022-01-02', 44, 230, 1200, 2000, 'Boite', 'f190615e-1101-7209-9932-7020bbd556f3') ON CONFLICT DO NOTHING;
INSERT INTO document.ressources (id, libelle, description, etat, datecreation, datemodification, quantite, seuilalerte, prixentree, prixsortie, unite, familles_id) VALUES ('5f516ee3-b23d-4cf1-8d55-e301764e0505', 'Papier', 'Papier', true, '2022-01-01', '2022-01-02', 24, 20, 1200, 2000, 'Packs', 'f190615e-1101-7209-9932-7020bbd556f4') ON CONFLICT DO NOTHING;
INSERT INTO document.ressources (id, libelle, description, etat, datecreation, datemodification, quantite, seuilalerte, prixentree, prixsortie, unite, familles_id) VALUES ('5f516ee3-b23d-4cf1-8d55-e301764e0506', 'Encre', 'Encre', true, '2022-01-01', '2022-01-02', 4, 20, 1200, 2000, 'Boite', 'f190615e-1101-7209-9932-7020bbd556f4') ON CONFLICT DO NOTHING;
INSERT INTO document.ressources (id, libelle, description, etat, datecreation, datemodification, quantite, seuilalerte, prixentree, prixsortie, unite, familles_id) VALUES ('5f516ee3-b23d-4cf1-8d55-e301764e0507', 'Toner', 'Toner', true, '2022-01-01', '2022-01-02', 4, 20, 1200, 2000, 'Boite', 'f190615e-1101-7209-9932-7020bbd556f4') ON CONFLICT DO NOTHING;
INSERT INTO document.ressources (id, libelle, description, etat, datecreation, datemodification, quantite, seuilalerte, prixentree, prixsortie, unite, familles_id) VALUES ('5f516ee3-b23d-4cf1-8d55-e301764e0508', 'Cahier', 'Cahier', true, '2022-01-01', '2022-01-02', 4, 20, 1200, 2000, 'Packs', 'f190615e-1101-7209-9932-7020bbd556f4') ON CONFLICT DO NOTHING;


--
-- TOC entry 5228 (class 0 OID 28234)
-- Dependencies: 249
-- Data for Name: ressourcespromotions; Type: TABLE DATA; Schema: document; Owner: -
--

INSERT INTO document.ressourcespromotions (promotions_id, ressources_id) VALUES ('1979bd79-f81b-498b-b247-e7b9bbb3f600', '6190615e-1101-7209-9932-7020bbd556f6') ON CONFLICT DO NOTHING;
INSERT INTO document.ressourcespromotions (promotions_id, ressources_id) VALUES ('1979bd79-f81b-498b-b247-e7b9bbb3f601', '6190615e-1101-7209-9932-7020bbd556f4') ON CONFLICT DO NOTHING;
INSERT INTO document.ressourcespromotions (promotions_id, ressources_id) VALUES ('1979bd79-f81b-498b-b247-e7b9bbb3f602', '6190615e-1101-7209-9932-7020bbd556f2') ON CONFLICT DO NOTHING;


--
-- TOC entry 5229 (class 0 OID 28239)
-- Dependencies: 250
-- Data for Name: roles; Type: TABLE DATA; Schema: document; Owner: -
--

INSERT INTO document.roles (id, titre, description, etat, datecreation, datemodification) VALUES ('5190615e-1101-7209-9932-7020bbd556f1', 'vendeur', 'personnel au contact du client', true, '2022-01-01', '2022-01-02') ON CONFLICT DO NOTHING;
INSERT INTO document.roles (id, titre, description, etat, datecreation, datemodification) VALUES ('6130615e-1101-7209-9932-7020bbd556f2', 'traiteur', 'Personnel administratif', true, '2022-01-01', '2022-01-02') ON CONFLICT DO NOTHING;
INSERT INTO document.roles (id, titre, description, etat, datecreation, datemodification) VALUES ('6191615e-1101-7209-9932-7020bbd556f3', 'marcheur', 'commercial sur le terrain', true, '2022-01-01', '2022-01-02') ON CONFLICT DO NOTHING;


--
-- TOC entry 5230 (class 0 OID 28244)
-- Dependencies: 251
-- Data for Name: sapplique; Type: TABLE DATA; Schema: document; Owner: -
--

INSERT INTO document.sapplique (familles_id, precomouvementsqtes_id) VALUES ('f190615e-1101-7209-9932-7020bbd556f1', 'a97eb081-72f6-4617-ba29-64dc8593a9ff') ON CONFLICT DO NOTHING;
INSERT INTO document.sapplique (familles_id, precomouvementsqtes_id) VALUES ('f190615e-1101-7209-9932-7020bbd556f2', 'a97eb081-62f6-4617-ba49-64dc8593a9ff') ON CONFLICT DO NOTHING;
INSERT INTO document.sapplique (familles_id, precomouvementsqtes_id) VALUES ('f190615e-1101-7209-9932-7020bbd556f3', 'a97eb081-62f6-4617-ba29-65dc8593a9ff') ON CONFLICT DO NOTHING;
INSERT INTO document.sapplique (familles_id, precomouvementsqtes_id) VALUES ('f190615e-1101-7209-9932-7020bbd556f4', 'a97eb081-62f6-4617-ba49-64dc8593a9ff') ON CONFLICT DO NOTHING;
INSERT INTO document.sapplique (familles_id, precomouvementsqtes_id) VALUES ('f190615e-1101-7209-9932-7020bbd556f1', 'a97eb081-62f6-4617-ba49-64dc8593a9ff') ON CONFLICT DO NOTHING;
INSERT INTO document.sapplique (familles_id, precomouvementsqtes_id) VALUES ('f190615e-1101-7209-9932-7020bbd556f2', 'a97eb081-72f6-4617-ba29-64dc8593a9ff') ON CONFLICT DO NOTHING;
INSERT INTO document.sapplique (familles_id, precomouvementsqtes_id) VALUES ('f190615e-1101-7209-9932-7020bbd556f3', 'a97eb081-62f6-4617-ba49-64dc8593a9ff') ON CONFLICT DO NOTHING;
INSERT INTO document.sapplique (familles_id, precomouvementsqtes_id) VALUES ('f190615e-1101-7209-9932-7020bbd556f4', 'a97eb081-62f6-4617-ba29-65dc8593a9ff') ON CONFLICT DO NOTHING;


--
-- TOC entry 5231 (class 0 OID 28249)
-- Dependencies: 252
-- Data for Name: services; Type: TABLE DATA; Schema: document; Owner: -
--

INSERT INTO document.services (id, description, libelle, localisation, etat, datecreation, datemodification, codeunique, filesattentes_id) VALUES ('2190615e-1101-7209-9932-7020bbd556f1', 'bien', 'Consultation', 'douala', true, '1972-06-12', '1990-03-07', 'S1', 'f190615e-1101-7209-9932-7020bbd556f1') ON CONFLICT DO NOTHING;
INSERT INTO document.services (id, description, libelle, localisation, etat, datecreation, datemodification, codeunique, filesattentes_id) VALUES ('2190615e-1101-7209-9932-7020bbd556f2', 'bien', 'Laboratoire', 'douala', true, '1990-08-06', '1990-03-07', 'S2', 'f190615e-1101-7209-9932-7020bbd556f2') ON CONFLICT DO NOTHING;
INSERT INTO document.services (id, description, libelle, localisation, etat, datecreation, datemodification, codeunique, filesattentes_id) VALUES ('2190615e-1101-7209-9932-7020bbd556f3', 'bien', 'Pharmacie', 'douala', true, '2000-03-07', '1990-03-07', 'S3', 'f190615e-1101-7209-9932-7020bbd556f3') ON CONFLICT DO NOTHING;


--
-- TOC entry 5232 (class 0 OID 28254)
-- Dependencies: 253
-- Data for Name: suivre; Type: TABLE DATA; Schema: document; Owner: -
--

INSERT INTO document.suivre (documents_id, precomouvements_id) VALUES ('0190615e-1101-7209-9932-7020bbd556f1', '6290615e-1101-7209-9932-7020bbd556f1') ON CONFLICT DO NOTHING;
INSERT INTO document.suivre (documents_id, precomouvements_id) VALUES ('0190615e-1101-7209-9932-7020bbd556f2', '6290615e-1101-7209-9932-7020bbd556f2') ON CONFLICT DO NOTHING;
INSERT INTO document.suivre (documents_id, precomouvements_id) VALUES ('0190615e-1101-7209-9932-7020bbd556f3', '17ee6932-2fcd-4b93-9c4c-0a4dbf659bff') ON CONFLICT DO NOTHING;
INSERT INTO document.suivre (documents_id, precomouvements_id) VALUES ('0190615e-1101-7209-9932-7020bbd556f4', '77b8577f-6d26-4376-af30-a3c8f75a9194') ON CONFLICT DO NOTHING;
INSERT INTO document.suivre (documents_id, precomouvements_id) VALUES ('0190615e-1101-7209-9932-7020bbd556f5', '6290615e-1101-7209-9932-7020bbd556f5') ON CONFLICT DO NOTHING;
INSERT INTO document.suivre (documents_id, precomouvements_id) VALUES ('0190615e-1101-7209-9932-7020bbd556f6', '6290615e-1101-7209-9932-7020bbd556f6') ON CONFLICT DO NOTHING;
INSERT INTO document.suivre (documents_id, precomouvements_id) VALUES ('0190615e-1101-7209-9932-7020bbd556f7', '6290615e-1101-7209-9932-7020bbd556f7') ON CONFLICT DO NOTHING;
INSERT INTO document.suivre (documents_id, precomouvements_id) VALUES ('0190615e-1101-7209-9932-7020bbd556f8', '6290615e-1101-7209-9932-7020bbd556f8') ON CONFLICT DO NOTHING;


--
-- TOC entry 5233 (class 0 OID 28259)
-- Dependencies: 254
-- Data for Name: tickets; Type: TABLE DATA; Schema: document; Owner: -
--

INSERT INTO document.tickets (id, codecourt, datecreation, datemodification) VALUES ('be8ef47f-d5a2-4254-cd79-af860672553e', 'rr15', '2022-03-02', '2022-10-02') ON CONFLICT DO NOTHING;
INSERT INTO document.tickets (id, codecourt, datecreation, datemodification) VALUES ('be8ef47f-d5a2-4254-cd79-af860672554e', 'rr10', '2002-01-02', '2022-11-02') ON CONFLICT DO NOTHING;
INSERT INTO document.tickets (id, codecourt, datecreation, datemodification) VALUES ('be8ef47f-d5a2-4254-cd79-af860672555e', 'rr11', '2014-01-12', '2022-02-02') ON CONFLICT DO NOTHING;
INSERT INTO document.tickets (id, codecourt, datecreation, datemodification) VALUES ('be8ef47f-d5a2-4254-cd79-af860672556e', 'rr12', '2023-10-25', '2024-03-02') ON CONFLICT DO NOTHING;
INSERT INTO document.tickets (id, codecourt, datecreation, datemodification) VALUES ('be8ef47f-d5a2-4254-cd79-af860672557e', 'rr16', '2020-11-15', '2024-05-02') ON CONFLICT DO NOTHING;
INSERT INTO document.tickets (id, codecourt, datecreation, datemodification) VALUES ('be8ef47f-d5a2-4254-cd79-af860672558e', 'rr14', '2022-01-02', '2023-03-02') ON CONFLICT DO NOTHING;


--
-- TOC entry 5234 (class 0 OID 28264)
-- Dependencies: 255
-- Data for Name: ticketsfilesattentes; Type: TABLE DATA; Schema: document; Owner: -
--

INSERT INTO document.ticketsfilesattentes (id, etat, dateaffectation, tickets_id, filesattentes_id) VALUES ('be8ef47f-d7a2-4254-cd79-af860672553e', true, '2024-11-04', 'be8ef47f-d5a2-4254-cd79-af860672553e', 'f190615e-1101-7209-9932-7020bbd556f1') ON CONFLICT DO NOTHING;
INSERT INTO document.ticketsfilesattentes (id, etat, dateaffectation, tickets_id, filesattentes_id) VALUES ('be8ef47f-d7a2-4254-cd79-af860672554e', false, '2022-12-04', 'be8ef47f-d5a2-4254-cd79-af860672554e', 'f190615e-1101-7209-9932-7020bbd556f2') ON CONFLICT DO NOTHING;
INSERT INTO document.ticketsfilesattentes (id, etat, dateaffectation, tickets_id, filesattentes_id) VALUES ('be8ef47f-d7a2-4254-cd79-af860672555e', false, '2020-11-04', 'be8ef47f-d5a2-4254-cd79-af860672558e', 'f190615e-1101-7209-9932-7020bbd556f3') ON CONFLICT DO NOTHING;
INSERT INTO document.ticketsfilesattentes (id, etat, dateaffectation, tickets_id, filesattentes_id) VALUES ('be8ef47f-d7a2-4254-cd79-af860672556e', false, '2021-04-04', 'be8ef47f-d5a2-4254-cd79-af860672556e', 'f190615e-1101-7209-9932-7020bbd556f3') ON CONFLICT DO NOTHING;
INSERT INTO document.ticketsfilesattentes (id, etat, dateaffectation, tickets_id, filesattentes_id) VALUES ('be8ef47f-d7a2-4254-cd79-af860672557e', true, '2023-02-04', 'be8ef47f-d5a2-4254-cd79-af860672555e', 'f190615e-1101-7209-9932-7020bbd556f2') ON CONFLICT DO NOTHING;
INSERT INTO document.ticketsfilesattentes (id, etat, dateaffectation, tickets_id, filesattentes_id) VALUES ('be8ef47f-d7a2-4254-cd79-af860672558e', false, '2022-01-04', 'be8ef47f-d5a2-4254-cd79-af860672557e', 'f190615e-1101-7209-9932-7020bbd556f3') ON CONFLICT DO NOTHING;


--
-- TOC entry 5235 (class 0 OID 28269)
-- Dependencies: 256
-- Data for Name: traiter; Type: TABLE DATA; Schema: document; Owner: -
--

INSERT INTO document.traiter (documents_id, missions_id) VALUES ('0190615e-1101-7209-9932-7020bbd556f1', '3190615e-1101-7209-9932-7020bbd556f1') ON CONFLICT DO NOTHING;
INSERT INTO document.traiter (documents_id, missions_id) VALUES ('0190615e-1101-7209-9932-7020bbd556f1', '3190615e-1101-7209-9932-7020bbd556f2') ON CONFLICT DO NOTHING;
INSERT INTO document.traiter (documents_id, missions_id) VALUES ('0190615e-1101-7209-9932-7020bbd556f2', '3190615e-1101-7209-9932-7020bbd556f2') ON CONFLICT DO NOTHING;
INSERT INTO document.traiter (documents_id, missions_id) VALUES ('0190615e-1101-7209-9932-7020bbd556f3', '3190615e-1101-7209-9932-7020bbd556f3') ON CONFLICT DO NOTHING;


--
-- TOC entry 5246 (class 0 OID 28727)
-- Dependencies: 267
-- Data for Name: utilisateurs; Type: TABLE DATA; Schema: document; Owner: -
--



--
-- TOC entry 5236 (class 0 OID 28274)
-- Dependencies: 257
-- Data for Name: validations; Type: TABLE DATA; Schema: document; Owner: -
--

INSERT INTO document.validations (id, code, etat, datecreation, datemodification, typevote, dureevote, typevalidation, roles_id) VALUES ('1901bd80-f71b-498b-b247-e7b9bbb3f602', 'o78', 'attente', '2022-03-02', '2022-10-02', 'multiple', 14, 'prioritaire', '5190615e-1101-7209-9932-7020bbd556f1') ON CONFLICT DO NOTHING;
INSERT INTO document.validations (id, code, etat, datecreation, datemodification, typevote, dureevote, typevalidation, roles_id) VALUES ('1901bd80-f71b-498b-b247-e7b9bbb3f601', 'o89', 'suppression', '2002-01-02', '2022-11-02', 'multiple', 2, 'prioritaire', '6130615e-1101-7209-9932-7020bbd556f2') ON CONFLICT DO NOTHING;
INSERT INTO document.validations (id, code, etat, datecreation, datemodification, typevote, dureevote, typevalidation, roles_id) VALUES ('1901bd80-f71b-498b-b247-e7b9bbb3f600', 'o85', 'traitement', '2014-01-12', '2022-02-02', 'multiple', 5, 'rechargeable', '6191615e-1101-7209-9932-7020bbd556f3') ON CONFLICT DO NOTHING;
INSERT INTO document.validations (id, code, etat, datecreation, datemodification, typevote, dureevote, typevalidation, roles_id) VALUES ('1901bd80-f71b-498b-b247-e7b9bbb3f603', 'o82', 'traitement', '2023-10-25', '2024-03-02', 'multiple', 22, 'prioritaire', '5190615e-1101-7209-9932-7020bbd556f1') ON CONFLICT DO NOTHING;
INSERT INTO document.validations (id, code, etat, datecreation, datemodification, typevote, dureevote, typevalidation, roles_id) VALUES ('1901bd80-f71b-498b-b247-e7b9bbb3f604', 'o84', 'traitement', '2020-11-15', '2024-05-02', 'multiple', 40, 'prioritaire', '6191615e-1101-7209-9932-7020bbd556f3') ON CONFLICT DO NOTHING;


--
-- TOC entry 5237 (class 0 OID 28279)
-- Dependencies: 258
-- Data for Name: violer; Type: TABLE DATA; Schema: document; Owner: -
--

INSERT INTO document.violer (mouvements_id, precomouvements_id) VALUES ('97d314f5-84ad-47cd-874d-9c55f0e45790', '6290615e-1101-7209-9932-7020bbd556f6') ON CONFLICT DO NOTHING;
INSERT INTO document.violer (mouvements_id, precomouvements_id) VALUES ('bfee750d-9b8c-4475-9373-08eff9b3ccb7', '6290615e-1101-7209-9932-7020bbd556f7') ON CONFLICT DO NOTHING;
INSERT INTO document.violer (mouvements_id, precomouvements_id) VALUES ('0e7cea07-d09e-4f79-be6c-6dd27aecbce6', '6290615e-1101-7209-9932-7020bbd556f8') ON CONFLICT DO NOTHING;


--
-- TOC entry 4953 (class 2606 OID 28683)
-- Name: actions actions_pkey; Type: CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.actions
    ADD CONSTRAINT actions_pkey PRIMARY KEY (id);


--
-- TOC entry 4965 (class 2606 OID 28719)
-- Name: elements elements_pkey; Type: CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.elements
    ADD CONSTRAINT elements_pkey PRIMARY KEY (id);


--
-- TOC entry 4955 (class 2606 OID 28690)
-- Name: elementsbases elementsbases_pkey; Type: CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.elementsbases
    ADD CONSTRAINT elementsbases_pkey PRIMARY KEY (id);


--
-- TOC entry 4977 (class 2606 OID 28752)
-- Name: groupes groupes_pkey; Type: CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.groupes
    ADD CONSTRAINT groupes_pkey PRIMARY KEY (id);


--
-- TOC entry 4959 (class 2606 OID 28702)
-- Name: langues langues_pkey; Type: CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.langues
    ADD CONSTRAINT langues_pkey PRIMARY KEY (id);


--
-- TOC entry 4969 (class 2606 OID 28726)
-- Name: menus menus_pkey; Type: CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.menus
    ADD CONSTRAINT menus_pkey PRIMARY KEY (id);


--
-- TOC entry 4973 (class 2606 OID 28740)
-- Name: organisations organisations_pkey; Type: CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.organisations
    ADD CONSTRAINT organisations_pkey PRIMARY KEY (id);


--
-- TOC entry 4957 (class 2606 OID 28837)
-- Name: actionslangues pk_actionslangues; Type: CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.actionslangues
    ADD CONSTRAINT pk_actionslangues PRIMARY KEY (langues_id, actions_id);


--
-- TOC entry 4857 (class 2606 OID 28285)
-- Name: associer pk_associer; Type: CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.associer
    ADD CONSTRAINT pk_associer PRIMARY KEY (attributs_id, categories_id);


--
-- TOC entry 4859 (class 2606 OID 28287)
-- Name: attributs pk_attributs; Type: CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.attributs
    ADD CONSTRAINT pk_attributs PRIMARY KEY (id);


--
-- TOC entry 4861 (class 2606 OID 28289)
-- Name: caisses pk_caisses; Type: CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.caisses
    ADD CONSTRAINT pk_caisses PRIMARY KEY (id);


--
-- TOC entry 4863 (class 2606 OID 28291)
-- Name: categories pk_categories; Type: CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.categories
    ADD CONSTRAINT pk_categories PRIMARY KEY (id);


--
-- TOC entry 4967 (class 2606 OID 28791)
-- Name: elements pk_composite_elements; Type: CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.elements
    ADD CONSTRAINT pk_composite_elements UNIQUE (menus_id, elementsbases_id);


--
-- TOC entry 4865 (class 2606 OID 28293)
-- Name: comptes pk_comptes; Type: CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.comptes
    ADD CONSTRAINT pk_comptes PRIMARY KEY (id);


--
-- TOC entry 4867 (class 2606 OID 28295)
-- Name: concerner pk_concerner; Type: CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.concerner
    ADD CONSTRAINT pk_concerner PRIMARY KEY (precomouvementsqtes_id, distributeurs_id);


--
-- TOC entry 4869 (class 2606 OID 28297)
-- Name: constituer pk_constituer; Type: CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.constituer
    ADD CONSTRAINT pk_constituer PRIMARY KEY (attributs_id, documents_id);


--
-- TOC entry 4871 (class 2606 OID 28299)
-- Name: deltasoldes pk_deltasoldes; Type: CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.deltasoldes
    ADD CONSTRAINT pk_deltasoldes PRIMARY KEY (id);


--
-- TOC entry 4873 (class 2606 OID 28301)
-- Name: docetats pk_docetats; Type: CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.docetats
    ADD CONSTRAINT pk_docetats PRIMARY KEY (id);


--
-- TOC entry 4875 (class 2606 OID 28303)
-- Name: docetats_predecesseurs pk_docetats_predecesseurs; Type: CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.docetats_predecesseurs
    ADD CONSTRAINT pk_docetats_predecesseurs PRIMARY KEY (docetats_id, predecesseur_id);


--
-- TOC entry 4877 (class 2606 OID 28305)
-- Name: documents pk_documents; Type: CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.documents
    ADD CONSTRAINT pk_documents PRIMARY KEY (id);


--
-- TOC entry 4879 (class 2606 OID 28674)
-- Name: documentspromotions pk_documentspromotions; Type: CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.documentspromotions
    ADD CONSTRAINT pk_documentspromotions PRIMARY KEY (documents_id, promotions_id);


--
-- TOC entry 4961 (class 2606 OID 28825)
-- Name: elementsbaselanques pk_elementsbaseslangues; Type: CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.elementsbaselanques
    ADD CONSTRAINT pk_elementsbaseslangues PRIMARY KEY (langues_id, elementsbases_id);


--
-- TOC entry 4963 (class 2606 OID 28813)
-- Name: elementslangues pk_elementslangues; Type: CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.elementslangues
    ADD CONSTRAINT pk_elementslangues PRIMARY KEY (langues_id, elements_id);


--
-- TOC entry 4881 (class 2606 OID 28307)
-- Name: etapes pk_etapes; Type: CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.etapes
    ADD CONSTRAINT pk_etapes PRIMARY KEY (id);


--
-- TOC entry 4883 (class 2606 OID 28309)
-- Name: etats pk_etats; Type: CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.etats
    ADD CONSTRAINT pk_etats PRIMARY KEY (id);


--
-- TOC entry 4885 (class 2606 OID 28311)
-- Name: exemplaires pk_exemplaires; Type: CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.exemplaires
    ADD CONSTRAINT pk_exemplaires PRIMARY KEY (id);


--
-- TOC entry 4887 (class 2606 OID 28313)
-- Name: familles pk_familles; Type: CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.familles
    ADD CONSTRAINT pk_familles PRIMARY KEY (id);


--
-- TOC entry 4889 (class 2606 OID 28315)
-- Name: famillespromotions pk_famillespromotions; Type: CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.famillespromotions
    ADD CONSTRAINT pk_famillespromotions PRIMARY KEY (familles_id, promotions_id);


--
-- TOC entry 4891 (class 2606 OID 28317)
-- Name: filesattentes pk_filesattentes; Type: CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.filesattentes
    ADD CONSTRAINT pk_filesattentes PRIMARY KEY (id);


--
-- TOC entry 4895 (class 2606 OID 28319)
-- Name: jouerroles pk_jouerroles; Type: CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.jouerroles
    ADD CONSTRAINT pk_jouerroles PRIMARY KEY (id);


--
-- TOC entry 4897 (class 2606 OID 28321)
-- Name: missions pk_missions; Type: CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.missions
    ADD CONSTRAINT pk_missions PRIMARY KEY (id);


--
-- TOC entry 4899 (class 2606 OID 28323)
-- Name: mouvementcaisses pk_mouvementcaisses; Type: CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.mouvementcaisses
    ADD CONSTRAINT pk_mouvementcaisses PRIMARY KEY (id);


--
-- TOC entry 4901 (class 2606 OID 28325)
-- Name: mouvements pk_mouvements; Type: CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.mouvements
    ADD CONSTRAINT pk_mouvements PRIMARY KEY (id);


--
-- TOC entry 4903 (class 2606 OID 28327)
-- Name: ordreetats pk_ordreetats; Type: CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.ordreetats
    ADD CONSTRAINT pk_ordreetats PRIMARY KEY (id);


--
-- TOC entry 4975 (class 2606 OID 28769)
-- Name: organiser pk_organiser; Type: CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.organiser
    ADD CONSTRAINT pk_organiser PRIMARY KEY (organisations_id, utilisateurs_id);


--
-- TOC entry 4905 (class 2606 OID 28329)
-- Name: parcours pk_parcours; Type: CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.parcours
    ADD CONSTRAINT pk_parcours PRIMARY KEY (id);


--
-- TOC entry 4907 (class 2606 OID 28331)
-- Name: personnels pk_personnels; Type: CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.personnels
    ADD CONSTRAINT pk_personnels PRIMARY KEY (id);


--
-- TOC entry 4909 (class 2606 OID 28333)
-- Name: personnes pk_personnes; Type: CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.personnes
    ADD CONSTRAINT pk_personnes PRIMARY KEY (id);


--
-- TOC entry 4911 (class 2606 OID 28335)
-- Name: personnes_s pk_personnes_s; Type: CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.personnes_s
    ADD CONSTRAINT pk_personnes_s PRIMARY KEY (id);


--
-- TOC entry 4913 (class 2606 OID 28337)
-- Name: precomouvements pk_precomouvements; Type: CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.precomouvements
    ADD CONSTRAINT pk_precomouvements PRIMARY KEY (id);


--
-- TOC entry 4915 (class 2606 OID 28339)
-- Name: precomouvementsqtes pk_precomouvementsqtes; Type: CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.precomouvementsqtes
    ADD CONSTRAINT pk_precomouvementsqtes PRIMARY KEY (id);


--
-- TOC entry 4917 (class 2606 OID 28341)
-- Name: promotions pk_promotions; Type: CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.promotions
    ADD CONSTRAINT pk_promotions PRIMARY KEY (id);


--
-- TOC entry 4921 (class 2606 OID 28343)
-- Name: rattacher pk_rattacher; Type: CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.rattacher
    ADD CONSTRAINT pk_rattacher PRIMARY KEY (personnes_id, rattacher_id);


--
-- TOC entry 4923 (class 2606 OID 28345)
-- Name: remplir pk_remplir; Type: CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.remplir
    ADD CONSTRAINT pk_remplir PRIMARY KEY (roles_id, missions_id);


--
-- TOC entry 4925 (class 2606 OID 28347)
-- Name: respecter pk_respecter; Type: CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.respecter
    ADD CONSTRAINT pk_respecter PRIMARY KEY (mouvements_id, precomouvements_id);


--
-- TOC entry 4927 (class 2606 OID 28349)
-- Name: ressources pk_ressources; Type: CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.ressources
    ADD CONSTRAINT pk_ressources PRIMARY KEY (id);


--
-- TOC entry 4929 (class 2606 OID 28351)
-- Name: ressourcespromotions pk_ressourcespromotions; Type: CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.ressourcespromotions
    ADD CONSTRAINT pk_ressourcespromotions PRIMARY KEY (ressources_id, promotions_id);


--
-- TOC entry 4931 (class 2606 OID 28353)
-- Name: roles pk_roles; Type: CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.roles
    ADD CONSTRAINT pk_roles PRIMARY KEY (id);


--
-- TOC entry 4933 (class 2606 OID 28355)
-- Name: sapplique pk_sapplique; Type: CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.sapplique
    ADD CONSTRAINT pk_sapplique PRIMARY KEY (familles_id, precomouvementsqtes_id);


--
-- TOC entry 4935 (class 2606 OID 28357)
-- Name: services pk_services; Type: CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.services
    ADD CONSTRAINT pk_services PRIMARY KEY (id);


--
-- TOC entry 4941 (class 2606 OID 28359)
-- Name: suivre pk_suivre; Type: CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.suivre
    ADD CONSTRAINT pk_suivre PRIMARY KEY (documents_id, precomouvements_id);


--
-- TOC entry 4943 (class 2606 OID 28361)
-- Name: tickets pk_tickets; Type: CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.tickets
    ADD CONSTRAINT pk_tickets PRIMARY KEY (id);


--
-- TOC entry 4945 (class 2606 OID 28363)
-- Name: ticketsfilesattentes pk_ticketsfilesattentes; Type: CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.ticketsfilesattentes
    ADD CONSTRAINT pk_ticketsfilesattentes PRIMARY KEY (id);


--
-- TOC entry 4947 (class 2606 OID 28676)
-- Name: traiter pk_traiter; Type: CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.traiter
    ADD CONSTRAINT pk_traiter PRIMARY KEY (missions_id, documents_id);


--
-- TOC entry 4949 (class 2606 OID 28365)
-- Name: validations pk_validations; Type: CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.validations
    ADD CONSTRAINT pk_validations PRIMARY KEY (id);


--
-- TOC entry 4951 (class 2606 OID 28367)
-- Name: violer pk_violer; Type: CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.violer
    ADD CONSTRAINT pk_violer PRIMARY KEY (precomouvements_id, mouvements_id);


--
-- TOC entry 4893 (class 2606 OID 28369)
-- Name: filesattentes uc_filesattentes_services; Type: CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.filesattentes
    ADD CONSTRAINT uc_filesattentes_services UNIQUE (services_id);


--
-- TOC entry 4919 (class 2606 OID 28371)
-- Name: promotions uc_promotions_codeunique; Type: CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.promotions
    ADD CONSTRAINT uc_promotions_codeunique UNIQUE (codeunique);


--
-- TOC entry 4937 (class 2606 OID 28373)
-- Name: services uc_services_codeunique; Type: CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.services
    ADD CONSTRAINT uc_services_codeunique UNIQUE (codeunique);


--
-- TOC entry 4939 (class 2606 OID 28375)
-- Name: services uc_services_filesattentes; Type: CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.services
    ADD CONSTRAINT uc_services_filesattentes UNIQUE (filesattentes_id);


--
-- TOC entry 4971 (class 2606 OID 28733)
-- Name: utilisateurs utilisateurs_pkey; Type: CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.utilisateurs
    ADD CONSTRAINT utilisateurs_pkey PRIMARY KEY (id);


--
-- TOC entry 5036 (class 2606 OID 28826)
-- Name: actionslangues fk_actions_actionslangues; Type: FK CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.actionslangues
    ADD CONSTRAINT fk_actions_actionslangues FOREIGN KEY (actions_id) REFERENCES document.actions(id);


--
-- TOC entry 5035 (class 2606 OID 28838)
-- Name: actions fk_actions_elementbases; Type: FK CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.actions
    ADD CONSTRAINT fk_actions_elementbases FOREIGN KEY (elementsbase_id) REFERENCES document.elementsbases(id);


--
-- TOC entry 4978 (class 2606 OID 28376)
-- Name: associer fk_associer_on_attributs; Type: FK CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.associer
    ADD CONSTRAINT fk_associer_on_attributs FOREIGN KEY (attributs_id) REFERENCES document.attributs(id);


--
-- TOC entry 4979 (class 2606 OID 28381)
-- Name: associer fk_associer_on_categories; Type: FK CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.associer
    ADD CONSTRAINT fk_associer_on_categories FOREIGN KEY (categories_id) REFERENCES document.categories(id);


--
-- TOC entry 4980 (class 2606 OID 28386)
-- Name: categories fk_categories_on_documents; Type: FK CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.categories
    ADD CONSTRAINT fk_categories_on_documents FOREIGN KEY (documents_id) REFERENCES document.documents(id);


--
-- TOC entry 4981 (class 2606 OID 28391)
-- Name: comptes fk_comptes_personnes; Type: FK CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.comptes
    ADD CONSTRAINT fk_comptes_personnes FOREIGN KEY (personnes_id) REFERENCES document.personnes(id);


--
-- TOC entry 4984 (class 2606 OID 28396)
-- Name: constituer fk_con_on_attributs_entity; Type: FK CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.constituer
    ADD CONSTRAINT fk_con_on_attributs_entity FOREIGN KEY (attributs_id) REFERENCES document.attributs(id);


--
-- TOC entry 4985 (class 2606 OID 28401)
-- Name: constituer fk_con_on_documents_entity; Type: FK CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.constituer
    ADD CONSTRAINT fk_con_on_documents_entity FOREIGN KEY (documents_id) REFERENCES document.documents(id);


--
-- TOC entry 4982 (class 2606 OID 28406)
-- Name: concerner fk_concerner_on_distributeurs_entity; Type: FK CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.concerner
    ADD CONSTRAINT fk_concerner_on_distributeurs_entity FOREIGN KEY (distributeurs_id) REFERENCES document.personnes(id);


--
-- TOC entry 4983 (class 2606 OID 28411)
-- Name: concerner fk_concerner_on_preco_mouvements_qtes_entity; Type: FK CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.concerner
    ADD CONSTRAINT fk_concerner_on_preco_mouvements_qtes_entity FOREIGN KEY (precomouvementsqtes_id) REFERENCES document.precomouvementsqtes(id);


--
-- TOC entry 4986 (class 2606 OID 28416)
-- Name: deltasoldes fk_deltasoldes_comptes; Type: FK CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.deltasoldes
    ADD CONSTRAINT fk_deltasoldes_comptes FOREIGN KEY (comptes_id) REFERENCES document.comptes(id);


--
-- TOC entry 4987 (class 2606 OID 28421)
-- Name: deltasoldes fk_deltasoldes_exemplaires; Type: FK CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.deltasoldes
    ADD CONSTRAINT fk_deltasoldes_exemplaires FOREIGN KEY (exemplaires_id) REFERENCES document.exemplaires(id);


--
-- TOC entry 5009 (class 2606 OID 28843)
-- Name: mouvements fk_distributeurs_mouvements; Type: FK CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.mouvements
    ADD CONSTRAINT fk_distributeurs_mouvements FOREIGN KEY (distributeurs_id) REFERENCES document.personnes(id);


--
-- TOC entry 5015 (class 2606 OID 28848)
-- Name: promotions fk_distributeurs_promotions; Type: FK CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.promotions
    ADD CONSTRAINT fk_distributeurs_promotions FOREIGN KEY (distributeurs_id) REFERENCES document.personnes(id);


--
-- TOC entry 4994 (class 2606 OID 28426)
-- Name: documentspromotions fk_doc_on_documents_entity; Type: FK CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.documentspromotions
    ADD CONSTRAINT fk_doc_on_documents_entity FOREIGN KEY (documents_id) REFERENCES document.documents(id);


--
-- TOC entry 4995 (class 2606 OID 28431)
-- Name: documentspromotions fk_doc_on_promotions_entity; Type: FK CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.documentspromotions
    ADD CONSTRAINT fk_doc_on_promotions_entity FOREIGN KEY (promotions_id) REFERENCES document.promotions(id);


--
-- TOC entry 4988 (class 2606 OID 28436)
-- Name: docetats fk_docetats_on_documents; Type: FK CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.docetats
    ADD CONSTRAINT fk_docetats_on_documents FOREIGN KEY (documents_id) REFERENCES document.documents(id);


--
-- TOC entry 4989 (class 2606 OID 28441)
-- Name: docetats fk_docetats_on_etapes; Type: FK CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.docetats
    ADD CONSTRAINT fk_docetats_on_etapes FOREIGN KEY (etapes_id) REFERENCES document.etapes(id);


--
-- TOC entry 4990 (class 2606 OID 28446)
-- Name: docetats fk_docetats_on_etats; Type: FK CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.docetats
    ADD CONSTRAINT fk_docetats_on_etats FOREIGN KEY (etats_id) REFERENCES document.etats(id);


--
-- TOC entry 4991 (class 2606 OID 28451)
-- Name: docetats fk_docetats_on_validations; Type: FK CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.docetats
    ADD CONSTRAINT fk_docetats_on_validations FOREIGN KEY (validations_id) REFERENCES document.validations(id);


--
-- TOC entry 4992 (class 2606 OID 28456)
-- Name: docetats_predecesseurs fk_docetats_predecesseurs_on_docetats; Type: FK CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.docetats_predecesseurs
    ADD CONSTRAINT fk_docetats_predecesseurs_on_docetats FOREIGN KEY (docetats_id) REFERENCES document.docetats(id);


--
-- TOC entry 4993 (class 2606 OID 28461)
-- Name: docetats_predecesseurs fk_docetats_predecesseurs_suivant_on_docetats; Type: FK CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.docetats_predecesseurs
    ADD CONSTRAINT fk_docetats_predecesseurs_suivant_on_docetats FOREIGN KEY (predecesseur_id) REFERENCES document.docetats(id);


--
-- TOC entry 5040 (class 2606 OID 28807)
-- Name: elementslangues fk_elements_elementslangues; Type: FK CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.elementslangues
    ADD CONSTRAINT fk_elements_elementslangues FOREIGN KEY (elements_id) REFERENCES document.elements(id);


--
-- TOC entry 5042 (class 2606 OID 28797)
-- Name: elements fk_elementsbase_elements; Type: FK CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.elements
    ADD CONSTRAINT fk_elementsbase_elements FOREIGN KEY (elementsbases_id) REFERENCES document.elementsbases(id);


--
-- TOC entry 5038 (class 2606 OID 28814)
-- Name: elementsbaselanques fk_elementsbase_elementsbaselangues; Type: FK CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.elementsbaselanques
    ADD CONSTRAINT fk_elementsbase_elementsbaselangues FOREIGN KEY (elementsbases_id) REFERENCES document.elementsbases(id);


--
-- TOC entry 4996 (class 2606 OID 28466)
-- Name: etapes fk_etapes_on_parcours; Type: FK CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.etapes
    ADD CONSTRAINT fk_etapes_on_parcours FOREIGN KEY (parcours_id) REFERENCES document.parcours(id);


--
-- TOC entry 4997 (class 2606 OID 36869)
-- Name: exemplaires fk_exemplaire_documents; Type: FK CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.exemplaires
    ADD CONSTRAINT fk_exemplaire_documents FOREIGN KEY (documents_id) REFERENCES document.documents(id);


--
-- TOC entry 4998 (class 2606 OID 28471)
-- Name: exemplaires fk_exemplaires_personnes; Type: FK CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.exemplaires
    ADD CONSTRAINT fk_exemplaires_personnes FOREIGN KEY (personnes_id) REFERENCES document.personnes(id);


--
-- TOC entry 4999 (class 2606 OID 28476)
-- Name: famillespromotions fk_fam_on_familles_entity; Type: FK CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.famillespromotions
    ADD CONSTRAINT fk_fam_on_familles_entity FOREIGN KEY (familles_id) REFERENCES document.familles(id);


--
-- TOC entry 5000 (class 2606 OID 28481)
-- Name: famillespromotions fk_fam_on_promotions_entity; Type: FK CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.famillespromotions
    ADD CONSTRAINT fk_fam_on_promotions_entity FOREIGN KEY (promotions_id) REFERENCES document.promotions(id);


--
-- TOC entry 5001 (class 2606 OID 28486)
-- Name: filesattentes fk_filesattentes_on_services; Type: FK CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.filesattentes
    ADD CONSTRAINT fk_filesattentes_on_services FOREIGN KEY (services_id) REFERENCES document.services(id);


--
-- TOC entry 5044 (class 2606 OID 28785)
-- Name: menus fk_groupes_menus; Type: FK CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.menus
    ADD CONSTRAINT fk_groupes_menus FOREIGN KEY (groupes_id) REFERENCES document.groupes(id);


--
-- TOC entry 5046 (class 2606 OID 28770)
-- Name: utilisateurs fk_groupes_utilisateurs; Type: FK CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.utilisateurs
    ADD CONSTRAINT fk_groupes_utilisateurs FOREIGN KEY (groupes_id) REFERENCES document.groupes(id);


--
-- TOC entry 5002 (class 2606 OID 28491)
-- Name: jouerroles fk_jouerroles_on_personnels; Type: FK CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.jouerroles
    ADD CONSTRAINT fk_jouerroles_on_personnels FOREIGN KEY (personnels_id) REFERENCES document.personnels(id);


--
-- TOC entry 5003 (class 2606 OID 28496)
-- Name: jouerroles fk_jouerroles_on_roles; Type: FK CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.jouerroles
    ADD CONSTRAINT fk_jouerroles_on_roles FOREIGN KEY (roles_id) REFERENCES document.roles(id);


--
-- TOC entry 5037 (class 2606 OID 28831)
-- Name: actionslangues fk_langues_actionslangues; Type: FK CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.actionslangues
    ADD CONSTRAINT fk_langues_actionslangues FOREIGN KEY (langues_id) REFERENCES document.langues(id);


--
-- TOC entry 5039 (class 2606 OID 28819)
-- Name: elementsbaselanques fk_langues_elementsbaselangues; Type: FK CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.elementsbaselanques
    ADD CONSTRAINT fk_langues_elementsbaselangues FOREIGN KEY (langues_id) REFERENCES document.langues(id);


--
-- TOC entry 5041 (class 2606 OID 28802)
-- Name: elementslangues fk_langues_elementslangues; Type: FK CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.elementslangues
    ADD CONSTRAINT fk_langues_elementslangues FOREIGN KEY (langues_id) REFERENCES document.langues(id);


--
-- TOC entry 5043 (class 2606 OID 28792)
-- Name: elements fk_menus_elements; Type: FK CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.elements
    ADD CONSTRAINT fk_menus_elements FOREIGN KEY (menus_id) REFERENCES document.menus(id);


--
-- TOC entry 5050 (class 2606 OID 28753)
-- Name: groupes fk_menus_groupes; Type: FK CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.groupes
    ADD CONSTRAINT fk_menus_groupes FOREIGN KEY (menus_id) REFERENCES document.menus(id);


--
-- TOC entry 5047 (class 2606 OID 28775)
-- Name: utilisateurs fk_menus_utilisateurs; Type: FK CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.utilisateurs
    ADD CONSTRAINT fk_menus_utilisateurs FOREIGN KEY (menus_id) REFERENCES document.menus(id);


--
-- TOC entry 5004 (class 2606 OID 28501)
-- Name: missions fk_missions_on_services; Type: FK CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.missions
    ADD CONSTRAINT fk_missions_on_services FOREIGN KEY (services_id) REFERENCES document.services(id);


--
-- TOC entry 5005 (class 2606 OID 28506)
-- Name: mouvementcaisses fk_mouvementcaisses_caisses; Type: FK CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.mouvementcaisses
    ADD CONSTRAINT fk_mouvementcaisses_caisses FOREIGN KEY (caisses_id) REFERENCES document.caisses(id);


--
-- TOC entry 5006 (class 2606 OID 28511)
-- Name: mouvementcaisses fk_mouvementcaisses_comptes; Type: FK CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.mouvementcaisses
    ADD CONSTRAINT fk_mouvementcaisses_comptes FOREIGN KEY (comptes_id) REFERENCES document.comptes(id);


--
-- TOC entry 5007 (class 2606 OID 28516)
-- Name: mouvementcaisses fk_mouvementcaisses_personnels; Type: FK CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.mouvementcaisses
    ADD CONSTRAINT fk_mouvementcaisses_personnels FOREIGN KEY (personnels_id) REFERENCES document.personnels(id);


--
-- TOC entry 5010 (class 2606 OID 28521)
-- Name: mouvements fk_mouvements_on_ressources; Type: FK CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.mouvements
    ADD CONSTRAINT fk_mouvements_on_ressources FOREIGN KEY (ressources_id) REFERENCES document.ressources(id);


--
-- TOC entry 5008 (class 2606 OID 28526)
-- Name: mouvementcaisses fk_mouvementscaisses_exemplaires; Type: FK CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.mouvementcaisses
    ADD CONSTRAINT fk_mouvementscaisses_exemplaires FOREIGN KEY (exemplaires_id) REFERENCES document.exemplaires(id);


--
-- TOC entry 5011 (class 2606 OID 28531)
-- Name: ordreetats fk_ordreetats_on_etats; Type: FK CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.ordreetats
    ADD CONSTRAINT fk_ordreetats_on_etats FOREIGN KEY (etats_id) REFERENCES document.etats(id);


--
-- TOC entry 5048 (class 2606 OID 28758)
-- Name: organiser fk_organisation_organiser; Type: FK CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.organiser
    ADD CONSTRAINT fk_organisation_organiser FOREIGN KEY (organisations_id) REFERENCES document.organisations(id);


--
-- TOC entry 5012 (class 2606 OID 28536)
-- Name: personnes fk_personnes_comptes; Type: FK CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.personnes
    ADD CONSTRAINT fk_personnes_comptes FOREIGN KEY (comptes_id) REFERENCES document.comptes(id);


--
-- TOC entry 5013 (class 2606 OID 28541)
-- Name: precomouvementsqtes fk_precomouvementsqtes_on_precomouvements; Type: FK CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.precomouvementsqtes
    ADD CONSTRAINT fk_precomouvementsqtes_on_precomouvements FOREIGN KEY (precomouvements_id) REFERENCES document.precomouvements(id);


--
-- TOC entry 5014 (class 2606 OID 28546)
-- Name: precomouvementsqtes fk_precomouvementsqtes_on_ressources; Type: FK CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.precomouvementsqtes
    ADD CONSTRAINT fk_precomouvementsqtes_on_ressources FOREIGN KEY (ressources_id) REFERENCES document.ressources(id);


--
-- TOC entry 5016 (class 2606 OID 28551)
-- Name: remplir fk_remplir_on_missions; Type: FK CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.remplir
    ADD CONSTRAINT fk_remplir_on_missions FOREIGN KEY (missions_id) REFERENCES document.missions(id);


--
-- TOC entry 5017 (class 2606 OID 28556)
-- Name: remplir fk_remplir_on_roles; Type: FK CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.remplir
    ADD CONSTRAINT fk_remplir_on_roles FOREIGN KEY (roles_id) REFERENCES document.roles(id);


--
-- TOC entry 5021 (class 2606 OID 28561)
-- Name: ressourcespromotions fk_res_on_promotions_entity; Type: FK CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.ressourcespromotions
    ADD CONSTRAINT fk_res_on_promotions_entity FOREIGN KEY (promotions_id) REFERENCES document.promotions(id);


--
-- TOC entry 5022 (class 2606 OID 28566)
-- Name: ressourcespromotions fk_res_on_ressources_entity; Type: FK CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.ressourcespromotions
    ADD CONSTRAINT fk_res_on_ressources_entity FOREIGN KEY (ressources_id) REFERENCES document.ressources(id);


--
-- TOC entry 5018 (class 2606 OID 28571)
-- Name: respecter fk_respecter_on_mouvements_entity; Type: FK CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.respecter
    ADD CONSTRAINT fk_respecter_on_mouvements_entity FOREIGN KEY (mouvements_id) REFERENCES document.mouvements(id);


--
-- TOC entry 5019 (class 2606 OID 28576)
-- Name: respecter fk_respecter_on_preco_mouvements_entity; Type: FK CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.respecter
    ADD CONSTRAINT fk_respecter_on_preco_mouvements_entity FOREIGN KEY (precomouvements_id) REFERENCES document.precomouvements(id);


--
-- TOC entry 5020 (class 2606 OID 28581)
-- Name: ressources fk_ressources_on_familles; Type: FK CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.ressources
    ADD CONSTRAINT fk_ressources_on_familles FOREIGN KEY (familles_id) REFERENCES document.familles(id);


--
-- TOC entry 5023 (class 2606 OID 28586)
-- Name: sapplique fk_sapplique_on_familles_entity; Type: FK CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.sapplique
    ADD CONSTRAINT fk_sapplique_on_familles_entity FOREIGN KEY (familles_id) REFERENCES document.familles(id);


--
-- TOC entry 5024 (class 2606 OID 28591)
-- Name: sapplique fk_sapplique_on_preco_mouvements_qtes_entity; Type: FK CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.sapplique
    ADD CONSTRAINT fk_sapplique_on_preco_mouvements_qtes_entity FOREIGN KEY (precomouvementsqtes_id) REFERENCES document.precomouvementsqtes(id);


--
-- TOC entry 5025 (class 2606 OID 28596)
-- Name: services fk_services_on_filesattentes; Type: FK CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.services
    ADD CONSTRAINT fk_services_on_filesattentes FOREIGN KEY (filesattentes_id) REFERENCES document.filesattentes(id);


--
-- TOC entry 5026 (class 2606 OID 28601)
-- Name: suivre fk_suivre_on_documents_entity; Type: FK CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.suivre
    ADD CONSTRAINT fk_suivre_on_documents_entity FOREIGN KEY (documents_id) REFERENCES document.documents(id);


--
-- TOC entry 5027 (class 2606 OID 28606)
-- Name: suivre fk_suivre_on_preco_mouvements_entity; Type: FK CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.suivre
    ADD CONSTRAINT fk_suivre_on_preco_mouvements_entity FOREIGN KEY (precomouvements_id) REFERENCES document.precomouvements(id);


--
-- TOC entry 5028 (class 2606 OID 28611)
-- Name: ticketsfilesattentes fk_ticketsfilesattentes_on_filesattentes; Type: FK CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.ticketsfilesattentes
    ADD CONSTRAINT fk_ticketsfilesattentes_on_filesattentes FOREIGN KEY (filesattentes_id) REFERENCES document.filesattentes(id);


--
-- TOC entry 5029 (class 2606 OID 28616)
-- Name: ticketsfilesattentes fk_ticketsfilesattentes_on_tickets; Type: FK CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.ticketsfilesattentes
    ADD CONSTRAINT fk_ticketsfilesattentes_on_tickets FOREIGN KEY (tickets_id) REFERENCES document.tickets(id);


--
-- TOC entry 5030 (class 2606 OID 28621)
-- Name: traiter fk_traiter_on_documents_entity; Type: FK CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.traiter
    ADD CONSTRAINT fk_traiter_on_documents_entity FOREIGN KEY (documents_id) REFERENCES document.documents(id);


--
-- TOC entry 5031 (class 2606 OID 28626)
-- Name: traiter fk_traiter_on_missions_entity; Type: FK CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.traiter
    ADD CONSTRAINT fk_traiter_on_missions_entity FOREIGN KEY (missions_id) REFERENCES document.missions(id);


--
-- TOC entry 5045 (class 2606 OID 28780)
-- Name: menus fk_utilisateur_menus; Type: FK CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.menus
    ADD CONSTRAINT fk_utilisateur_menus FOREIGN KEY (utilisateurs_id) REFERENCES document.utilisateurs(id);


--
-- TOC entry 5049 (class 2606 OID 28763)
-- Name: organiser fk_utilisateurs_organiser; Type: FK CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.organiser
    ADD CONSTRAINT fk_utilisateurs_organiser FOREIGN KEY (utilisateurs_id) REFERENCES document.utilisateurs(id);


--
-- TOC entry 5032 (class 2606 OID 28631)
-- Name: validations fk_validations_on_roles; Type: FK CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.validations
    ADD CONSTRAINT fk_validations_on_roles FOREIGN KEY (roles_id) REFERENCES document.roles(id);


--
-- TOC entry 5033 (class 2606 OID 28636)
-- Name: violer fk_violer_on_mouvements_entity; Type: FK CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.violer
    ADD CONSTRAINT fk_violer_on_mouvements_entity FOREIGN KEY (mouvements_id) REFERENCES document.mouvements(id);


--
-- TOC entry 5034 (class 2606 OID 28641)
-- Name: violer fk_violer_on_preco_mouvements_entity; Type: FK CONSTRAINT; Schema: document; Owner: -
--

ALTER TABLE ONLY document.violer
    ADD CONSTRAINT fk_violer_on_preco_mouvements_entity FOREIGN KEY (precomouvements_id) REFERENCES document.precomouvements(id);


-- Completed on 2025-02-13 02:42:04

--
-- PostgreSQL database dump complete
--

