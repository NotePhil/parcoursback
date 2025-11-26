--
-- PostgreSQL database dump
--

-- Dumped from database version 16.3
-- Dumped by pg_dump version 16.3

-- Started on 2025-09-26 11:31:10

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
-- TOC entry 5342 (class 1262 OID 28060)
-- Name: parcoursbackv2; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE parcoursbackv2 WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'French_France.1252';


ALTER DATABASE parcoursbackv2 OWNER TO postgres;

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
-- Name: document; Type: SCHEMA; Schema: -; Owner: pg_database_owner
--

CREATE SCHEMA document;


ALTER SCHEMA document OWNER TO pg_database_owner;

--
-- TOC entry 5343 (class 0 OID 0)
-- Dependencies: 5
-- Name: SCHEMA document; Type: COMMENT; Schema: -; Owner: pg_database_owner
--

COMMENT ON SCHEMA document IS 'standard public schema';


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 258 (class 1259 OID 28677)
-- Name: actions; Type: TABLE; Schema: document; Owner: postgres
--

CREATE TABLE document.actions (
    id character varying(255) NOT NULL,
    libelle character varying(255),
    etat boolean,
    datecreation date,
    datemodification date,
    elementsbase_id character varying(255)
);


ALTER TABLE document.actions OWNER TO postgres;

--
-- TOC entry 260 (class 1259 OID 28691)
-- Name: actionslangues; Type: TABLE; Schema: document; Owner: postgres
--

CREATE TABLE document.actionslangues (
    langues_id character varying(255) NOT NULL,
    actions_id character varying(255) NOT NULL,
    valeurlibelle character varying(255)
);


ALTER TABLE document.actionslangues OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 28061)
-- Name: associer; Type: TABLE; Schema: document; Owner: postgres
--

CREATE TABLE document.associer (
    obligatoire boolean,
    ordre integer,
    attributs_id character varying(255) NOT NULL,
    categories_id character varying(255) NOT NULL
);


ALTER TABLE document.associer OWNER TO postgres;

--
-- TOC entry 218 (class 1259 OID 28066)
-- Name: attributs; Type: TABLE; Schema: document; Owner: postgres
--

CREATE TABLE document.attributs (
    id character varying(255) NOT NULL,
    titre character varying(255) NOT NULL,
    description character varying(255),
    etat boolean DEFAULT true,
    datecreation date,
    datemodification date,
    valeurpardefaut character varying(255),
    type_attribut character varying(255) NOT NULL
);


ALTER TABLE document.attributs OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 28072)
-- Name: caisses; Type: TABLE; Schema: document; Owner: postgres
--

CREATE TABLE document.caisses (
    id character varying(255) NOT NULL,
    libelle character varying(255),
    solde double precision,
    type character varying(255),
    detailjson character varying(255),
    etat boolean
);


ALTER TABLE document.caisses OWNER TO postgres;

--
-- TOC entry 220 (class 1259 OID 28077)
-- Name: categories; Type: TABLE; Schema: document; Owner: postgres
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


ALTER TABLE document.categories OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 28083)
-- Name: comptes; Type: TABLE; Schema: document; Owner: postgres
--

CREATE TABLE document.comptes (
    id character varying(255) NOT NULL,
    datecreation date,
    etat character varying(255),
    montantdecouvertmax double precision,
    libelle character varying(255),
    personnes_id character varying(255),
    solde double precision
);


ALTER TABLE document.comptes OWNER TO postgres;

--
-- TOC entry 222 (class 1259 OID 28088)
-- Name: concerner; Type: TABLE; Schema: document; Owner: postgres
--

CREATE TABLE document.concerner (
    distributeurs_id character varying(255) NOT NULL,
    precomouvementsqtes_id character varying(255) NOT NULL
);


ALTER TABLE document.concerner OWNER TO postgres;

--
-- TOC entry 223 (class 1259 OID 28093)
-- Name: constituer; Type: TABLE; Schema: document; Owner: postgres
--

CREATE TABLE document.constituer (
    attributs_id character varying(255) NOT NULL,
    documents_id character varying(255) NOT NULL
);


ALTER TABLE document.constituer OWNER TO postgres;

--
-- TOC entry 224 (class 1259 OID 28098)
-- Name: deltasoldes; Type: TABLE; Schema: document; Owner: postgres
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


ALTER TABLE document.deltasoldes OWNER TO postgres;

--
-- TOC entry 294 (class 1259 OID 46356)
-- Name: distributeurs; Type: TABLE; Schema: document; Owner: postgres
--

CREATE TABLE document.distributeurs (
    distributeurs_id character varying(255) NOT NULL,
    datemodification date,
    code character varying(255),
    raisonsociale character varying(255)
);


ALTER TABLE document.distributeurs OWNER TO postgres;

--
-- TOC entry 225 (class 1259 OID 28103)
-- Name: docetats; Type: TABLE; Schema: document; Owner: postgres
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


ALTER TABLE document.docetats OWNER TO postgres;

--
-- TOC entry 226 (class 1259 OID 28108)
-- Name: docetats_predecesseurs; Type: TABLE; Schema: document; Owner: postgres
--

CREATE TABLE document.docetats_predecesseurs (
    docetats_id character varying(255) NOT NULL,
    predecesseur_id character varying(255) NOT NULL
);


ALTER TABLE document.docetats_predecesseurs OWNER TO postgres;

--
-- TOC entry 227 (class 1259 OID 28113)
-- Name: documents; Type: TABLE; Schema: document; Owner: postgres
--

CREATE TABLE document.documents (
    id character varying(255) NOT NULL,
    titre character varying(255) NOT NULL,
    description character varying(255),
    etat boolean,
    datecreation date,
    datemodification date,
    typemouvement character varying(255),
    afficherprix boolean,
    afficherunite boolean,
    afficherdistributeur boolean,
    prixeditable boolean,
    contientressources boolean,
    estEncaissable boolean
);


ALTER TABLE document.documents OWNER TO postgres;

--
-- TOC entry 228 (class 1259 OID 28118)
-- Name: documentspromotions; Type: TABLE; Schema: document; Owner: postgres
--

CREATE TABLE document.documentspromotions (
    documents_id character varying(255) NOT NULL,
    promotions_id character varying(255) NOT NULL
);


ALTER TABLE document.documentspromotions OWNER TO postgres;

--
-- TOC entry 264 (class 1259 OID 28713)
-- Name: elements; Type: TABLE; Schema: document; Owner: postgres
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


ALTER TABLE document.elements OWNER TO postgres;

--
-- TOC entry 262 (class 1259 OID 28703)
-- Name: elementsbaselanques; Type: TABLE; Schema: document; Owner: postgres
--

CREATE TABLE document.elementsbaselanques (
    langues_id character varying(255) NOT NULL,
    elementsbases_id character varying(255) NOT NULL,
    valeurlibelle character varying(255)
);


ALTER TABLE document.elementsbaselanques OWNER TO postgres;

--
-- TOC entry 259 (class 1259 OID 28684)
-- Name: elementsbases; Type: TABLE; Schema: document; Owner: postgres
--

CREATE TABLE document.elementsbases (
    id character varying(255) NOT NULL,
    libelle character varying(255),
    etat boolean,
    datesouscription date,
    datemodification date,
    moduleangular character varying(255),
    element_id character varying(255)
);


ALTER TABLE document.elementsbases OWNER TO postgres;

--
-- TOC entry 263 (class 1259 OID 28708)
-- Name: elementslangues; Type: TABLE; Schema: document; Owner: postgres
--

CREATE TABLE document.elementslangues (
    langues_id character varying(255) NOT NULL,
    elements_id character varying(255) NOT NULL,
    valeurlibelle character varying(255)
);


ALTER TABLE document.elementslangues OWNER TO postgres;

--
-- TOC entry 229 (class 1259 OID 28123)
-- Name: etapes; Type: TABLE; Schema: document; Owner: postgres
--

CREATE TABLE document.etapes (
    id character varying(255) NOT NULL,
    libelle character varying(255),
    etat character varying(255),
    datemodification date,
    parcours_id character varying(255)
);


ALTER TABLE document.etapes OWNER TO postgres;

--
-- TOC entry 230 (class 1259 OID 28128)
-- Name: etats; Type: TABLE; Schema: document; Owner: postgres
--

CREATE TABLE document.etats (
    id character varying(255) NOT NULL,
    libelle character varying(255),
    description character varying(255),
    datecreation date,
    datemodification date
);


ALTER TABLE document.etats OWNER TO postgres;

--
-- TOC entry 231 (class 1259 OID 28133)
-- Name: exemplaires; Type: TABLE; Schema: document; Owner: postgres
--

CREATE TABLE document.exemplaires (
    id character varying(255) NOT NULL,
    personnes_id character varying(255) NOT NULL,
    documents_id character varying(255)
);


ALTER TABLE document.exemplaires OWNER TO postgres;

--
-- TOC entry 232 (class 1259 OID 28138)
-- Name: familles; Type: TABLE; Schema: document; Owner: postgres
--

CREATE TABLE document.familles (
    id character varying(255) NOT NULL,
    libelle character varying(255),
    description character varying(255),
    etat boolean,
    datecreation date,
    datemodification date
);


ALTER TABLE document.familles OWNER TO postgres;

--
-- TOC entry 233 (class 1259 OID 28143)
-- Name: famillespromotions; Type: TABLE; Schema: document; Owner: postgres
--

CREATE TABLE document.famillespromotions (
    familles_id character varying(255) NOT NULL,
    promotions_id character varying(255) NOT NULL
);


ALTER TABLE document.famillespromotions OWNER TO postgres;

--
-- TOC entry 234 (class 1259 OID 28148)
-- Name: filesattentes; Type: TABLE; Schema: document; Owner: postgres
--

CREATE TABLE document.filesattentes (
    id character varying(255) NOT NULL,
    datecreation date,
    datemodification date,
    etat boolean,
    services_id character varying(255)
);


ALTER TABLE document.filesattentes OWNER TO postgres;

--
-- TOC entry 269 (class 1259 OID 28746)
-- Name: groupes; Type: TABLE; Schema: document; Owner: postgres
--

CREATE TABLE document.groupes (
    id character varying(255) NOT NULL,
    libelle character varying(255),
    etat boolean,
    datecreation date,
    datemodification date,
    menus_id character varying(255)
);


ALTER TABLE document.groupes OWNER TO postgres;

--
-- TOC entry 235 (class 1259 OID 28153)
-- Name: jouerroles; Type: TABLE; Schema: document; Owner: postgres
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


ALTER TABLE document.jouerroles OWNER TO postgres;

--
-- TOC entry 261 (class 1259 OID 28696)
-- Name: langues; Type: TABLE; Schema: document; Owner: postgres
--

CREATE TABLE document.langues (
    id character varying(255) NOT NULL,
    libelle character varying(255),
    etat boolean,
    datesouscription date,
    datemodification date
);


ALTER TABLE document.langues OWNER TO postgres;

--
-- TOC entry 265 (class 1259 OID 28720)
-- Name: menus; Type: TABLE; Schema: document; Owner: postgres
--

CREATE TABLE document.menus (
    id character varying(255) NOT NULL,
    etat boolean,
    datecreation date,
    utilisateurs_id character varying(255),
    groupes_id character varying(255)
);


ALTER TABLE document.menus OWNER TO postgres;

--
-- TOC entry 236 (class 1259 OID 28158)
-- Name: missions; Type: TABLE; Schema: document; Owner: postgres
--

CREATE TABLE document.missions (
    id character varying(255) NOT NULL,
    libelle character varying(255) NOT NULL,
    description character varying(255),
    etat boolean,
    datecreation date NOT NULL,
    datemodification date,
    services_id character varying(255),
    idlogin character varying(255)
);


ALTER TABLE document.missions OWNER TO postgres;

--
-- TOC entry 237 (class 1259 OID 28163)
-- Name: mouvementcaisses; Type: TABLE; Schema: document; Owner: postgres
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


ALTER TABLE document.mouvementcaisses OWNER TO postgres;

--
-- TOC entry 238 (class 1259 OID 28168)
-- Name: mouvements; Type: TABLE; Schema: document; Owner: postgres
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


ALTER TABLE document.mouvements OWNER TO postgres;

--
-- TOC entry 239 (class 1259 OID 28173)
-- Name: ordreetats; Type: TABLE; Schema: document; Owner: postgres
--

CREATE TABLE document.ordreetats (
    id character varying(255) NOT NULL,
    datecreation date,
    datemodification date,
    datefinvote date,
    ordre integer,
    etats_id character varying(255)
);


ALTER TABLE document.ordreetats OWNER TO postgres;

--
-- TOC entry 267 (class 1259 OID 28734)
-- Name: organisations; Type: TABLE; Schema: document; Owner: postgres
--

CREATE TABLE document.organisations (
    id character varying(255) NOT NULL,
    raisonsociale character varying(255)
);


ALTER TABLE document.organisations OWNER TO postgres;

--
-- TOC entry 268 (class 1259 OID 28741)
-- Name: organiser; Type: TABLE; Schema: document; Owner: postgres
--

CREATE TABLE document.organiser (
    organisations_id character varying(255) NOT NULL,
    utilisateurs_id character varying(255) NOT NULL
);


ALTER TABLE document.organiser OWNER TO postgres;

--
-- TOC entry 240 (class 1259 OID 28178)
-- Name: parcours; Type: TABLE; Schema: document; Owner: postgres
--

CREATE TABLE document.parcours (
    id character varying(255) NOT NULL,
    libelle character varying(255),
    datecreation date,
    datemodification date
);


ALTER TABLE document.parcours OWNER TO postgres;

--
-- TOC entry 295 (class 1259 OID 46404)
-- Name: personnels; Type: TABLE; Schema: document; Owner: postgres
--

CREATE TABLE document.personnels (
    personnels_id character varying(255) NOT NULL,
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


ALTER TABLE document.personnels OWNER TO postgres;

--
-- TOC entry 241 (class 1259 OID 28188)
-- Name: personnes; Type: TABLE; Schema: document; Owner: postgres
--

CREATE TABLE document.personnes (
    id character varying(255) NOT NULL,
    adresse character varying(255),
    mail character varying(255),
    telephone character varying(255),
    qrcodevalue character varying(255),
    code character varying(255),
    raisonsociale character varying(255),
    datenaissance date,
    datecreation date,
    datemodification date,
    comptes_id character varying(255),
    type character varying(255),
    ticket_id character varying(255),
    etat boolean
);


ALTER TABLE document.personnes OWNER TO postgres;

--
-- TOC entry 293 (class 1259 OID 46344)
-- Name: personnesmorales; Type: TABLE; Schema: document; Owner: postgres
--

CREATE TABLE document.personnesmorales (
    personnesmorales_id character varying(255) NOT NULL,
    raisonsociale character varying(255),
    code character varying(255)
);


ALTER TABLE document.personnesmorales OWNER TO postgres;

--
-- TOC entry 292 (class 1259 OID 46332)
-- Name: personnesphysique; Type: TABLE; Schema: document; Owner: postgres
--

CREATE TABLE document.personnesphysique (
    personnesphysique_id character varying(255) NOT NULL,
    nom character varying(255) NOT NULL,
    prenom character varying(255),
    sexe character varying(255),
    datenaissance date NOT NULL,
    datemodification date
);


ALTER TABLE document.personnesphysique OWNER TO postgres;

--
-- TOC entry 242 (class 1259 OID 28199)
-- Name: precomouvements; Type: TABLE; Schema: document; Owner: postgres
--

CREATE TABLE document.precomouvements (
    id character varying(255) NOT NULL,
    libelle character varying(255) NOT NULL,
    etat boolean,
    datecreation date NOT NULL,
    datemodification date,
    typemouvement character varying(255)
);


ALTER TABLE document.precomouvements OWNER TO postgres;

--
-- TOC entry 243 (class 1259 OID 28204)
-- Name: precomouvementsqtes; Type: TABLE; Schema: document; Owner: postgres
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


ALTER TABLE document.precomouvementsqtes OWNER TO postgres;

--
-- TOC entry 244 (class 1259 OID 28209)
-- Name: promotions; Type: TABLE; Schema: document; Owner: postgres
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


ALTER TABLE document.promotions OWNER TO postgres;

--
-- TOC entry 245 (class 1259 OID 28214)
-- Name: rattacher; Type: TABLE; Schema: document; Owner: postgres
--

CREATE TABLE document.rattacher (
    personnes_id character varying(255) NOT NULL,
    rattacher_id character varying(255) NOT NULL
);


ALTER TABLE document.rattacher OWNER TO postgres;

--
-- TOC entry 296 (class 1259 OID 46418)
-- Name: remplir; Type: TABLE; Schema: document; Owner: postgres
--

CREATE TABLE document.remplir (
    id character varying(255) NOT NULL,
    roles_id character varying(255),
    missions_id character varying(255),
    datefin date,
    datecreation date,
    datedebut date,
    etat boolean,
    droitajouter boolean,
    droitmodifier boolean,
    droitconsulter boolean,
    droitvalider boolean
);


ALTER TABLE document.remplir OWNER TO postgres;

--
-- TOC entry 246 (class 1259 OID 28224)
-- Name: respecter; Type: TABLE; Schema: document; Owner: postgres
--

CREATE TABLE document.respecter (
    mouvements_id character varying(255) NOT NULL,
    precomouvements_id character varying(255) NOT NULL
);


ALTER TABLE document.respecter OWNER TO postgres;

--
-- TOC entry 247 (class 1259 OID 28229)
-- Name: ressources; Type: TABLE; Schema: document; Owner: postgres
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


ALTER TABLE document.ressources OWNER TO postgres;

--
-- TOC entry 248 (class 1259 OID 28234)
-- Name: ressourcespromotions; Type: TABLE; Schema: document; Owner: postgres
--

CREATE TABLE document.ressourcespromotions (
    promotions_id character varying(255) NOT NULL,
    ressources_id character varying(255) NOT NULL
);


ALTER TABLE document.ressourcespromotions OWNER TO postgres;

--
-- TOC entry 249 (class 1259 OID 28239)
-- Name: roles; Type: TABLE; Schema: document; Owner: postgres
--

CREATE TABLE document.roles (
    id character varying(255) NOT NULL,
    titre character varying(255),
    description character varying(255),
    etat boolean,
    datecreation date,
    datemodification date
);


ALTER TABLE document.roles OWNER TO postgres;

--
-- TOC entry 250 (class 1259 OID 28244)
-- Name: sapplique; Type: TABLE; Schema: document; Owner: postgres
--

CREATE TABLE document.sapplique (
    familles_id character varying(255) NOT NULL,
    precomouvementsqtes_id character varying(255) NOT NULL
);


ALTER TABLE document.sapplique OWNER TO postgres;

--
-- TOC entry 251 (class 1259 OID 28249)
-- Name: services; Type: TABLE; Schema: document; Owner: postgres
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


ALTER TABLE document.services OWNER TO postgres;

--
-- TOC entry 252 (class 1259 OID 28254)
-- Name: suivre; Type: TABLE; Schema: document; Owner: postgres
--

CREATE TABLE document.suivre (
    documents_id character varying(255) NOT NULL,
    precomouvements_id character varying(255) NOT NULL
);


ALTER TABLE document.suivre OWNER TO postgres;

--
-- TOC entry 253 (class 1259 OID 28259)
-- Name: tickets; Type: TABLE; Schema: document; Owner: postgres
--

CREATE TABLE document.tickets (
    id character varying(255) NOT NULL,
    codecourt character varying(255),
    datecreation date,
    datemodification date,
    personne_id character varying(255),
    statut character varying(255)
);


ALTER TABLE document.tickets OWNER TO postgres;

--
-- TOC entry 254 (class 1259 OID 28264)
-- Name: ticketsfilesattentes; Type: TABLE; Schema: document; Owner: postgres
--

CREATE TABLE document.ticketsfilesattentes (
    id character varying(255) NOT NULL,
    etat boolean,
    dateaffectation date,
    tickets_id character varying(255),
    filesattentes_id character varying(255)
);


ALTER TABLE document.ticketsfilesattentes OWNER TO postgres;

--
-- TOC entry 255 (class 1259 OID 28269)
-- Name: traiter; Type: TABLE; Schema: document; Owner: postgres
--

CREATE TABLE document.traiter (
    documents_id character varying(255) NOT NULL,
    missions_id character varying(255) NOT NULL
);


ALTER TABLE document.traiter OWNER TO postgres;

--
-- TOC entry 266 (class 1259 OID 28727)
-- Name: utilisateurs; Type: TABLE; Schema: document; Owner: postgres
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


ALTER TABLE document.utilisateurs OWNER TO postgres;

--
-- TOC entry 256 (class 1259 OID 28274)
-- Name: validations; Type: TABLE; Schema: document; Owner: postgres
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
    roles_id character varying(255),
    libelle character varying(255)
);


ALTER TABLE document.validations OWNER TO postgres;

--
-- TOC entry 257 (class 1259 OID 28279)
-- Name: violer; Type: TABLE; Schema: document; Owner: postgres
--

CREATE TABLE document.violer (
    mouvements_id character varying(255) NOT NULL,
    precomouvements_id character varying(255) NOT NULL
);


ALTER TABLE document.violer OWNER TO postgres;

--
-- TOC entry 5320 (class 0 OID 28677)
-- Dependencies: 258
-- Data for Name: actions; Type: TABLE DATA; Schema: document; Owner: postgres
--

INSERT INTO document.actions VALUES ('j6bca7iy-5i6j-4c8f-3340-7a1b0c9d8e7f', 'Afficher', true, '2024-01-01', '2024-01-01', 'u1lmn2tj-0t1u-4d8f-6495-8k7l6m5n4o3p') ON CONFLICT DO NOTHING;
INSERT INTO document.actions VALUES ('k7cdb8jz-6j7k-4d90-2259-6b0c9d8e7f6g', 'Ajouter', true, '2024-01-01', '2024-01-01', 'v2mno3uk-1u2v-4e90-5304-7l6m5n4o3p2q') ON CONFLICT DO NOTHING;
INSERT INTO document.actions VALUES ('l8dec9ka-7k8l-4fa1-1168-5c9d8e7f6g5h', 'Modifier', true, '2024-01-01', '2024-01-01', 'w3nop4vl-2v3w-4fa1-4213-6m5n4o3p2q1r') ON CONFLICT DO NOTHING;
INSERT INTO document.actions VALUES ('m9efd0lb-8l9m-40b2-0077-4d8e7f6g5h4i', 'Supprimer', true, '2024-01-01', '2024-01-01', 'x4opq5wm-3w4x-40b2-3122-5n4o3p2q1r0s') ON CONFLICT DO NOTHING;
INSERT INTO document.actions VALUES ('n0fge1mc-9m0n-41c3-7986-3e7f6g5h4i3j', 'Exporter', true, '2024-01-01', '2024-01-01', 'y5pqr6xn-4x5y-41c3-2031-4o3p2q1r0s9t') ON CONFLICT DO NOTHING;


--
-- TOC entry 5322 (class 0 OID 28691)
-- Dependencies: 260
-- Data for Name: actionslangues; Type: TABLE DATA; Schema: document; Owner: postgres
--

INSERT INTO document.actionslangues VALUES ('e1vwx2dt-0d1e-4729-4495-8u7v6w5x4y3z', 'j6bca7iy-5i6j-4c8f-3340-7a1b0c9d8e7f', 'Afficher') ON CONFLICT DO NOTHING;
INSERT INTO document.actionslangues VALUES ('f2wxy3eu-1e2f-483a-3304-7v6w5x4y3z2a', 'j6bca7iy-5i6j-4c8f-3340-7a1b0c9d8e7f', 'Show') ON CONFLICT DO NOTHING;
INSERT INTO document.actionslangues VALUES ('e1vwx2dt-0d1e-4729-4495-8u7v6w5x4y3z', 'k7cdb8jz-6j7k-4d90-2259-6b0c9d8e7f6g', 'Ajouter') ON CONFLICT DO NOTHING;
INSERT INTO document.actionslangues VALUES ('f2wxy3eu-1e2f-483a-3304-7v6w5x4y3z2a', 'k7cdb8jz-6j7k-4d90-2259-6b0c9d8e7f6g', 'Add') ON CONFLICT DO NOTHING;
INSERT INTO document.actionslangues VALUES ('e1vwx2dt-0d1e-4729-4495-8u7v6w5x4y3z', 'l8dec9ka-7k8l-4fa1-1168-5c9d8e7f6g5h', 'Modifier') ON CONFLICT DO NOTHING;


--
-- TOC entry 5279 (class 0 OID 28061)
-- Dependencies: 217
-- Data for Name: associer; Type: TABLE DATA; Schema: document; Owner: postgres
--

INSERT INTO document.associer VALUES (true, 1, 'a1eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', '36b843d8-f8a7-4b76-9980-1add9edc2364') ON CONFLICT DO NOTHING;
INSERT INTO document.associer VALUES (true, 2, 'a1eebc99-9c0b-4ef8-bb6d-6bb9bd380a13', '36b843d8-f8a7-4b76-9980-1add9edc2364') ON CONFLICT DO NOTHING;
INSERT INTO document.associer VALUES (false, 3, 'a1eebc99-9c0b-4ef8-bb6d-6bb9bd380a15', '36b843d8-f8a7-4b76-9980-1add9edc2364') ON CONFLICT DO NOTHING;
INSERT INTO document.associer VALUES (true, 1, 'a2eebc99-9c0b-4ef8-bb6d-6bb9bd380a12', 'be9ef47f-d5a2-4254-ab79-af860672553e') ON CONFLICT DO NOTHING;
INSERT INTO document.associer VALUES (false, 2, 'a9eebc99-9c0b-4ef8-bb6d-6bb9bd380a19', 'be9ef47f-d5a2-4254-ab79-af860672553e') ON CONFLICT DO NOTHING;
INSERT INTO document.associer VALUES (true, 3, 'a2eebc99-9c0b-4ef8-bb6d-6bb9bd380a20', 'be9ef47f-d5a2-4254-ab79-af860672553e') ON CONFLICT DO NOTHING;
INSERT INTO document.associer VALUES (true, 1, 'a1eebc99-9c0b-4ef8-bb6d-6bb9bd380a13', 'be8ef47f-d5a2-4254-ab79-af860672553e') ON CONFLICT DO NOTHING;
INSERT INTO document.associer VALUES (true, 1, 'a4eebc99-9c0b-4ef8-bb6d-6bb9bd380a14', 'be8ef57f-d5a2-4254-ab79-af860672553e') ON CONFLICT DO NOTHING;
INSERT INTO document.associer VALUES (true, 1, 'a1eebc99-9c0b-4ef8-bb6d-6bb9bd380a15', 'be8ef56f-d5a2-4254-ab79-af860672553e') ON CONFLICT DO NOTHING;
INSERT INTO document.associer VALUES (true, 1, 'a6eebc99-9c0b-4ef8-bb6d-6bb9bd380a16', 'be8ef59f-d5a2-4254-ab79-af860672553e') ON CONFLICT DO NOTHING;
INSERT INTO document.associer VALUES (true, 1, 'a7eebc99-9c0b-4ef8-bb6d-6bb9bd380a17', 'be8ef59f-d5a3-4254-ab79-af860672553e') ON CONFLICT DO NOTHING;
INSERT INTO document.associer VALUES (true, 1, 'a8eebc99-9c0b-4ef8-bb6d-6bb9bd380a18', 'be8ef59f-d5a4-4254-ab79-af860672553e') ON CONFLICT DO NOTHING;
INSERT INTO document.associer VALUES (true, 1, 'a9eebc99-9c0b-4ef8-bb6d-6bb9bd380a19', 'be8ef59f-d5a4-4254-ab99-af860672553e') ON CONFLICT DO NOTHING;


--
-- TOC entry 5280 (class 0 OID 28066)
-- Dependencies: 218
-- Data for Name: attributs; Type: TABLE DATA; Schema: document; Owner: postgres
--

INSERT INTO document.attributs VALUES ('a1eebc99-9c0b-4ef8-bb6d-6bb9bd380a13', 'Age', 'Description age', true, '2022-01-01', '2022-01-02', NULL, 'Text') ON CONFLICT DO NOTHING;
INSERT INTO document.attributs VALUES ('a1eebc99-9c0b-4ef8-bb6d-6bb9bd380a15', 'Teint', 'Coloration', true, '2022-01-01', '2022-01-02', 'Noir', 'Checkbox') ON CONFLICT DO NOTHING;
INSERT INTO document.attributs VALUES ('a2eebc99-9c0b-4ef8-bb6d-6bb9bd380a12', 'Poids', 'Description Poids', true, '2022-01-01', '2022-01-02', NULL, 'Radio') ON CONFLICT DO NOTHING;
INSERT INTO document.attributs VALUES ('a2eebc99-9c0b-4ef8-bb6d-6bb9bd380a20', 'Date de naissance', 'Date de naissance', true, '2022-01-01', '2022-01-02', NULL, 'Date') ON CONFLICT DO NOTHING;
INSERT INTO document.attributs VALUES ('a4eebc99-9c0b-4ef8-bb6d-6bb9bd380a14', 'Sexe', 'Sexe ', true, '2022-01-01', '2022-01-02', NULL, 'Date') ON CONFLICT DO NOTHING;
INSERT INTO document.attributs VALUES ('a6eebc99-9c0b-4ef8-bb6d-6bb9bd380a16', 'Groupe sangin', 'Groupe sangin', true, '2022-01-01', '2022-01-02', 'A, A+, A-, B, B+, B-, AB, AB+, AB-, O, O+, O-', 'Textarea') ON CONFLICT DO NOTHING;
INSERT INTO document.attributs VALUES ('a7eebc99-9c0b-4ef8-bb6d-6bb9bd380a17', 'Allergies', 'Allergies connues ', true, '2022-01-01', '2022-01-02', NULL, 'Date') ON CONFLICT DO NOTHING;
INSERT INTO document.attributs VALUES ('a8eebc99-9c0b-4ef8-bb6d-6bb9bd380a18', 'Nom', 'Nom de la personne', true, '2022-01-01', '2022-01-02', NULL, 'Url') ON CONFLICT DO NOTHING;
INSERT INTO document.attributs VALUES ('a9eebc99-9c0b-4ef8-bb6d-6bb9bd380a19', 'Adresse', 'Adresse postale', true, '2022-01-01', '2022-01-02', NULL, 'Url') ON CONFLICT DO NOTHING;
INSERT INTO document.attributs VALUES ('a1eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 'decription taille des Taille sur taille', 'Description Taille sur tailles', false, '2022-01-01', '2022-01-02', NULL, 'Textarea') ON CONFLICT DO NOTHING;
INSERT INTO document.attributs VALUES ('a3cbf07d-eee9-44b3-89b8-c8e2d6db4f06', 'decription taille des Taille sur taille', 'Description Taille sur tailles', false, '2022-01-01', '2022-01-02', NULL, 'Textarea') ON CONFLICT DO NOTHING;
INSERT INTO document.attributs VALUES ('9332738c-a1c1-4807-9b0f-f47f11c05b91', 'decription taille des Taille sur taille', 'Description Taille sur tailles', false, '2022-01-01', '2022-01-02', NULL, 'Textarea') ON CONFLICT DO NOTHING;
INSERT INTO document.attributs VALUES ('82e987a2-488e-4152-8926-db875f940c66', 'decription taille des Taille sur taille', 'Description Taille sur tailles', false, '2022-01-01', '2022-01-02', NULL, 'Textarea') ON CONFLICT DO NOTHING;
INSERT INTO document.attributs VALUES ('0ec60831-7876-4a59-aeab-5f283b7a4c68', 'decription taille des Taille sur taille33', 'Description Taille sur tailles', false, '2022-01-01', '2022-01-02', NULL, 'Textarea') ON CONFLICT DO NOTHING;
INSERT INTO document.attributs VALUES ('7d7090e7-3204-47ca-b42a-cc0fa8827f3b', 'decription taille des Taille sur taille333', 'Description Taille sur tailles378', true, '2021-11-01', '2022-05-02', NULL, 'Textarea') ON CONFLICT DO NOTHING;
INSERT INTO document.attributs VALUES ('0372b2c2-7afd-4498-a3fa-ab0aa45d2775', 'decription taille des Taille sur taille533', 'Description Taille sur tailles3758', true, '2021-12-02', '2022-04-02', NULL, 'Textarea') ON CONFLICT DO NOTHING;
INSERT INTO document.attributs VALUES ('876b1513-c1b8-4cc7-a3a2-aa808e6a06ba', 'decription taille des Taille sur taille052', 'Description Taille sur tailles3078518', true, '2021-12-02', '2022-04-02', NULL, 'Textarea') ON CONFLICT DO NOTHING;
INSERT INTO document.attributs VALUES ('8d70edb8-f543-4282-b17b-c97a8b71b563', 'decription taille des Taille sur taille752', 'Description Taille sur tailles30787518', true, '2021-12-01', '2022-04-01', NULL, 'Textarea') ON CONFLICT DO NOTHING;
INSERT INTO document.attributs VALUES ('cd302436-268f-46e7-8e61-df356bd8625d', 'desciptionbetabetalambda', 'taille en description2', true, NULL, NULL, NULL, 'Number') ON CONFLICT DO NOTHING;
INSERT INTO document.attributs VALUES ('8bc12e32-86d3-4da2-b49e-1054bf6c8798', 'desciptionbetabetalambdgghgjhja', 'bienfghgj,', true, NULL, NULL, NULL, 'Number') ON CONFLICT DO NOTHING;
INSERT INTO document.attributs VALUES ('beebfb6e-7046-471a-8d0d-a4fdb4e7aa43', 'desciptionbetabetalambdgghgjhja', 'bienfghgj,', true, NULL, NULL, NULL, 'Number') ON CONFLICT DO NOTHING;
INSERT INTO document.attributs VALUES ('36bb8723-4a93-4f01-ad1a-874640f550b2', 'desciptionbetabetalambdgghgjhja', 'bienfghgj,', true, NULL, NULL, '5', 'Number') ON CONFLICT DO NOTHING;


--
-- TOC entry 5281 (class 0 OID 28072)
-- Dependencies: 219
-- Data for Name: caisses; Type: TABLE DATA; Schema: document; Owner: postgres
--

INSERT INTO document.caisses VALUES ('a1f8e3b0-4c6d-4e5e-ab7a-2a8b9b9c8d6f', 'Caisse principale', 10000, 'Caisse', '{"description": "Caisse principale"}', true) ON CONFLICT DO NOTHING;
INSERT INTO document.caisses VALUES ('b3a7c8d6-1e2f-3d4c-cb6a-9e8f0a7b6c5d', 'Caisse en ligne', 7500, 'Caisse', '{"description": "Caisse en ligne"}', true) ON CONFLICT DO NOTHING;
INSERT INTO document.caisses VALUES ('d9e2f1c2-8b3a-4f6c-bd5e-1a7b8a7c6d5e', 'Caisse secondaire3', 5000, 'Caisse', NULL, true) ON CONFLICT DO NOTHING;
INSERT INTO document.caisses VALUES ('f0a7b6c5-d4e3f2a1-8d9c-7d6e-5f4a3b2c1e0d', 'Caisse mobile3', 3000, 'Caisse', NULL, true) ON CONFLICT DO NOTHING;


--
-- TOC entry 5282 (class 0 OID 28077)
-- Dependencies: 220
-- Data for Name: categories; Type: TABLE DATA; Schema: document; Owner: postgres
--

INSERT INTO document.categories VALUES ('36b843d8-f8a7-4b76-9980-1add9edc2364', '0', 'Informations Personnelles', true, '2022-01-01', '2022-01-02', '0190615e-1101-7209-9932-7020bbd556f1') ON CONFLICT DO NOTHING;
INSERT INTO document.categories VALUES ('be9ef47f-d5a2-4254-ab79-af860672553e', '1', 'Informations Primaires', true, '2022-01-01', '2022-01-02', '0190615e-1101-7209-9932-7020bbd556f1') ON CONFLICT DO NOTHING;
INSERT INTO document.categories VALUES ('be8ef47f-d5a2-4254-ab79-af860672553e', '0', 'Conditions Générales', true, '2022-01-01', '2022-01-02', '0190615e-1101-7209-9932-7020bbd556f2') ON CONFLICT DO NOTHING;
INSERT INTO document.categories VALUES ('be8ef57f-d5a2-4254-ab79-af860672553e', '1', 'Conditions Particulières', true, '2022-01-01', '2022-01-02', '0190615e-1101-7209-9932-7020bbd556f2') ON CONFLICT DO NOTHING;
INSERT INTO document.categories VALUES ('be8ef56f-d5a2-4254-ab79-af860672553e', '1', 'Informations Personnelles', true, '2022-01-01', '2022-01-02', '0190615e-1101-7209-9932-7020bbd556f3') ON CONFLICT DO NOTHING;
INSERT INTO document.categories VALUES ('be8ef59f-d5a2-4254-ab79-af860672553e', '1', 'Informations Primaires', true, '2022-01-01', '2022-01-02', '0190615e-1101-7209-9932-7020bbd556f4') ON CONFLICT DO NOTHING;
INSERT INTO document.categories VALUES ('be8ef59f-d5a3-4254-ab79-af860672553e', '1', 'Conditions Générales', true, '2022-01-01', '2022-01-02', '0190615e-1101-7209-9932-7020bbd556f5') ON CONFLICT DO NOTHING;
INSERT INTO document.categories VALUES ('be8ef59f-d5a4-4254-ab79-af860672553e', '0', 'Conditions Générales', true, '2022-01-01', '2022-01-02', '0190615e-1101-7209-9932-7020bbd556f6') ON CONFLICT DO NOTHING;
INSERT INTO document.categories VALUES ('be8ef59f-d5a4-4254-ab99-af860672553e', '0', 'Catégorie par Defaut', true, '2022-01-01', '2022-01-02', '0190615e-1101-7209-9932-7020bbd556f7') ON CONFLICT DO NOTHING;


--
-- TOC entry 5283 (class 0 OID 28083)
-- Dependencies: 221
-- Data for Name: comptes; Type: TABLE DATA; Schema: document; Owner: postgres
--

INSERT INTO document.comptes VALUES ('00012f83-2a62-4e6d-aa23-57c7825bcd57', '2024-08-19', 'actif', 2000, 'Compte entreprise', '11111111-aaaa-bbbb-cccc-111111111111', 102.32) ON CONFLICT DO NOTHING;
INSERT INTO document.comptes VALUES ('1179bd79-f71b-498b-b247-e7b9bbb3f600', '2024-08-19', 'inactif', 0, 'Compte jeunesse', '44444444-dddd-eeee-ffff-444444444444', 3553.2) ON CONFLICT DO NOTHING;
INSERT INTO document.comptes VALUES ('1518e585-f82a-4d5f-af1c-54f880d766d3', '2024-08-19', 'actif', 2000, 'Compte entreprise', '33333333-cccc-dddd-eeee-333333333333', 646583.32) ON CONFLICT DO NOTHING;
INSERT INTO document.comptes VALUES ('a1f8e3b0-4c6d-4e5e-7b7a-2a8b9b9c8d6f', '2024-08-19', 'actif', 500, 'Compte courant', 'bbbbbbbb-4444-5555-6666-bbbbbbbbbbbb', 14785) ON CONFLICT DO NOTHING;
INSERT INTO document.comptes VALUES ('adbff692-418d-43ab-a196-fcc3114b2daa', '2024-08-19', 'actif', 500, 'Compte courant', '99999999-2222-3333-4444-999999999999', 31656.325) ON CONFLICT DO NOTHING;
INSERT INTO document.comptes VALUES ('b3a7c8d6-1e2f-3d4c-7b6a-9e8f0a7b6c5d', '2024-08-19', 'inactif', 0, 'Compte jeunesse', '8a9b710a-a7fb-44ca-9587-b427b163cdab', 789654.23) ON CONFLICT DO NOTHING;
INSERT INTO document.comptes VALUES ('d301ff83-1b62-4e6d-aa23-57c7825bcd57', '2024-08-19', 'actif', 1000, 'Épargne', '1e8b6f56-7525-45bf-aa7b-96c0a9ae4198', 1153548.35) ON CONFLICT DO NOTHING;
INSERT INTO document.comptes VALUES ('d9e2f1c2-8b3a-4f6c-7d5e-1a7b8a7c6d5e', '2024-08-19', 'actif', 1000, 'Épargne', '4390615e-1101-7209-9932-7020bbd556f1', 635663.254) ON CONFLICT DO NOTHING;
INSERT INTO document.comptes VALUES ('f0a7b6c5-d4e3f2a1-7b9c-7d6e-5f4a3b2c1e0d', '2024-08-19', 'actif', 2000, 'Compte entreprise', '6b7894b5-1b07-487a-b8fe-d14ab61c1b2e', 655318.369) ON CONFLICT DO NOTHING;
INSERT INTO document.comptes VALUES ('a1234567-89ab-cdef-0123-456789abcdef', '2025-08-09', 'actif', 1000, 'Compte Personnel 1', '4390615e-1101-7209-9932-7020bbd556f3', 0) ON CONFLICT DO NOTHING;
INSERT INTO document.comptes VALUES ('b2345678-9abc-def0-1234-56789abcdef0', '2025-08-09', 'actif', 1500, 'Compte Personnel 2', '4390615e-1101-7209-9932-7020bbd556f2', 0) ON CONFLICT DO NOTHING;
INSERT INTO document.comptes VALUES ('a1111111-1111-1111-1111-111111111111', '2025-08-09', 'actif', 1000, 'Compte Personnel 1', '22222222-bbbb-cccc-dddd-222222222222', 0) ON CONFLICT DO NOTHING;
INSERT INTO document.comptes VALUES ('b2222222-2222-2222-2222-222222222222', '2025-08-09', 'actif', 1500, 'Compte Personnel 2', '3b277afa-98fc-4b0f-9b16-1d2fb4aa8ec6', 0) ON CONFLICT DO NOTHING;


--
-- TOC entry 5284 (class 0 OID 28088)
-- Dependencies: 222
-- Data for Name: concerner; Type: TABLE DATA; Schema: document; Owner: postgres
--



--
-- TOC entry 5285 (class 0 OID 28093)
-- Dependencies: 223
-- Data for Name: constituer; Type: TABLE DATA; Schema: document; Owner: postgres
--

INSERT INTO document.constituer VALUES ('a1eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', '0190615e-1101-7209-9932-7020bbd556f1') ON CONFLICT DO NOTHING;
INSERT INTO document.constituer VALUES ('a1eebc99-9c0b-4ef8-bb6d-6bb9bd380a13', '0190615e-1101-7209-9932-7020bbd556f1') ON CONFLICT DO NOTHING;
INSERT INTO document.constituer VALUES ('a1eebc99-9c0b-4ef8-bb6d-6bb9bd380a15', '0190615e-1101-7209-9932-7020bbd556f1') ON CONFLICT DO NOTHING;
INSERT INTO document.constituer VALUES ('a2eebc99-9c0b-4ef8-bb6d-6bb9bd380a12', '0190615e-1101-7209-9932-7020bbd556f1') ON CONFLICT DO NOTHING;
INSERT INTO document.constituer VALUES ('a9eebc99-9c0b-4ef8-bb6d-6bb9bd380a19', '0190615e-1101-7209-9932-7020bbd556f1') ON CONFLICT DO NOTHING;
INSERT INTO document.constituer VALUES ('a2eebc99-9c0b-4ef8-bb6d-6bb9bd380a20', '0190615e-1101-7209-9932-7020bbd556f1') ON CONFLICT DO NOTHING;
INSERT INTO document.constituer VALUES ('a1eebc99-9c0b-4ef8-bb6d-6bb9bd380a13', '0190615e-1101-7209-9932-7020bbd556f2') ON CONFLICT DO NOTHING;
INSERT INTO document.constituer VALUES ('a4eebc99-9c0b-4ef8-bb6d-6bb9bd380a14', '0190615e-1101-7209-9932-7020bbd556f2') ON CONFLICT DO NOTHING;
INSERT INTO document.constituer VALUES ('a2eebc99-9c0b-4ef8-bb6d-6bb9bd380a20', '0190615e-1101-7209-9932-7020bbd556f2') ON CONFLICT DO NOTHING;
INSERT INTO document.constituer VALUES ('a1eebc99-9c0b-4ef8-bb6d-6bb9bd380a15', '0190615e-1101-7209-9932-7020bbd556f3') ON CONFLICT DO NOTHING;
INSERT INTO document.constituer VALUES ('a6eebc99-9c0b-4ef8-bb6d-6bb9bd380a16', '0190615e-1101-7209-9932-7020bbd556f4') ON CONFLICT DO NOTHING;
INSERT INTO document.constituer VALUES ('a7eebc99-9c0b-4ef8-bb6d-6bb9bd380a17', '0190615e-1101-7209-9932-7020bbd556f5') ON CONFLICT DO NOTHING;
INSERT INTO document.constituer VALUES ('a8eebc99-9c0b-4ef8-bb6d-6bb9bd380a18', '0190615e-1101-7209-9932-7020bbd556f6') ON CONFLICT DO NOTHING;
INSERT INTO document.constituer VALUES ('a9eebc99-9c0b-4ef8-bb6d-6bb9bd380a19', '0190615e-1101-7209-9932-7020bbd556f7') ON CONFLICT DO NOTHING;
INSERT INTO document.constituer VALUES ('a2eebc99-9c0b-4ef8-bb6d-6bb9bd380a20', '0190615e-1101-7209-9932-7020bbd556f8') ON CONFLICT DO NOTHING;


--
-- TOC entry 5286 (class 0 OID 28098)
-- Dependencies: 224
-- Data for Name: deltasoldes; Type: TABLE DATA; Schema: document; Owner: postgres
--

INSERT INTO document.deltasoldes VALUES ('aad314f5-84ad-77cd-874d-9c55f0e45790', 651651.258, 51555.32, '2022-01-02', 'retrait', 'b3a7c8d6-1e2f-3d4c-7b6a-9e8f0a7b6c5d', 'b3a7c8d6-1e2f-3d4c-5b6a-9e8f0a7b6c5d') ON CONFLICT DO NOTHING;
INSERT INTO document.deltasoldes VALUES ('aad314f5-84ad-77cd-874d-9c55f0e45791', 651651.258, 51555.32, '2022-01-02', 'retrait', 'a1f8e3b0-4c6d-4e5e-7b7a-2a8b9b9c8d6f', 'a1f8e3b0-4c6d-4e5e-9b7a-2a8b9b9c8d6f') ON CONFLICT DO NOTHING;
INSERT INTO document.deltasoldes VALUES ('aad314f5-84ad-77cd-874d-9c55f0e45792', 564566.21, 6544864.21, '2022-01-02', 'paiement', 'd9e2f1c2-8b3a-4f6c-7d5e-1a7b8a7c6d5e', 'd9e2f1c2-8b3a-4f6c-9d5e-1a7b8a7c6d5e') ON CONFLICT DO NOTHING;
INSERT INTO document.deltasoldes VALUES ('aad314f5-84ad-77cd-874d-9c55f0e45793', 48646.58, 454151.14, '2022-01-02', 'paiement', 'f0a7b6c5-d4e3f2a1-7b9c-7d6e-5f4a3b2c1e0d', 'f0a7b6c5-d4e3f2a1-8b9c-7d6e-5f4a3b2c1e0d') ON CONFLICT DO NOTHING;
INSERT INTO document.deltasoldes VALUES ('aad314f5-84ad-77qd-874d-9c55f0e45790', 45245.6, 4587.35, '2022-01-02', 'retrait', 'd301ff83-1b62-4e6d-aa23-57c7825bcd57', 'a1f1e3b0-4c6d-4e5e-9b7a-2a8b9b9c8d6f') ON CONFLICT DO NOTHING;
INSERT INTO document.deltasoldes VALUES ('aad314f5-84ad-77qd-874e-9c55f0e45790', 452.21, 452452.26, '2022-01-02', 'depots', 'adbff692-418d-43ab-a196-fcc3114b2daa', 'a1f1e4b0-4c6d-4e5e-9b7a-2a8b9b9c8d6f') ON CONFLICT DO NOTHING;


--
-- TOC entry 5334 (class 0 OID 46356)
-- Dependencies: 294
-- Data for Name: distributeurs; Type: TABLE DATA; Schema: document; Owner: postgres
--

INSERT INTO document.distributeurs VALUES ('99999999-2222-3333-4444-999999999999', '2025-06-01', 'D001', 'Distributeur Alpha') ON CONFLICT DO NOTHING;
INSERT INTO document.distributeurs VALUES ('aaaaaaaa-3333-4444-5555-aaaaaaaaaaaa', '2025-06-02', 'D002', 'Distributeur Beta') ON CONFLICT DO NOTHING;
INSERT INTO document.distributeurs VALUES ('bbbbbbbb-4444-5555-6666-bbbbbbbbbbbb', '2025-06-03', 'D003', 'Distributeur Gamma') ON CONFLICT DO NOTHING;
INSERT INTO document.distributeurs VALUES ('c3d4e5f6-7890-ab12-c3d4-e5f67890abcd', '2025-06-04', 'D004', 'Distributeur Delta') ON CONFLICT DO NOTHING;


--
-- TOC entry 5287 (class 0 OID 28103)
-- Dependencies: 225
-- Data for Name: docetats; Type: TABLE DATA; Schema: document; Owner: postgres
--

INSERT INTO document.docetats VALUES ('be8ef4af-d5a2-4254-cd79-af860672553e', 15, '2022-03-02', '2022-10-02', '1901bd80-f71b-498b-b247-e7b9bbb3f602', 'e190615e-1101-7209-9932-7020bbd556f1', '0190615e-1101-7209-9932-7020bbd556f1', '1901bd79-f71b-498b-b247-e7b9bbb3f600') ON CONFLICT DO NOTHING;
INSERT INTO document.docetats VALUES ('be8ef4af-d5a2-4254-cd79-af860672554e', 10, '2002-01-02', '2022-11-02', '1901bd80-f71b-498b-b247-e7b9bbb3f601', 'e190615e-1101-7209-9932-7020bbd556f2', '0190615e-1101-7209-9932-7020bbd556f2', '1901bd79-f71b-498b-b247-e7b9bbb3f601') ON CONFLICT DO NOTHING;
INSERT INTO document.docetats VALUES ('be8ef4af-d5a2-4254-cd79-af860672555e', 11, '2014-01-12', '2022-02-02', '1901bd80-f71b-498b-b247-e7b9bbb3f600', 'e190615e-1101-7209-9932-7020bbd556f3', '0190615e-1101-7209-9932-7020bbd556f3', '1901bd79-f71b-498b-b247-e7b9bbb3f602') ON CONFLICT DO NOTHING;
INSERT INTO document.docetats VALUES ('be8ef4af-d5a2-4254-cd79-af860672556e', 12, '2023-10-25', '2024-03-02', '1901bd80-f71b-498b-b247-e7b9bbb3f603', 'e190615e-1101-7209-9932-7020bbd556f4', '0190615e-1101-7209-9932-7020bbd556f4', '1901bd79-f71b-498b-b247-e7b9bbb3f602') ON CONFLICT DO NOTHING;
INSERT INTO document.docetats VALUES ('be8ef4af-d5a2-4254-cd79-af860672557e', 16, '2020-11-15', '2024-05-02', '1901bd80-f71b-498b-b247-e7b9bbb3f604', 'e190615e-1101-7209-9932-7020bbd556f4', '0190615e-1101-7209-9932-7020bbd556f5', '1901bd79-f71b-498b-b247-e7b9bbb3f600') ON CONFLICT DO NOTHING;


--
-- TOC entry 5288 (class 0 OID 28108)
-- Dependencies: 226
-- Data for Name: docetats_predecesseurs; Type: TABLE DATA; Schema: document; Owner: postgres
--

INSERT INTO document.docetats_predecesseurs VALUES ('be8ef4af-d5a2-4254-cd79-af860672553e', 'be8ef4af-d5a2-4254-cd79-af860672555e') ON CONFLICT DO NOTHING;
INSERT INTO document.docetats_predecesseurs VALUES ('be8ef4af-d5a2-4254-cd79-af860672554e', 'be8ef4af-d5a2-4254-cd79-af860672556e') ON CONFLICT DO NOTHING;


--
-- TOC entry 5289 (class 0 OID 28113)
-- Dependencies: 227
-- Data for Name: documents; Type: TABLE DATA; Schema: document; Owner: postgres
--

INSERT INTO document.documents VALUES ('0190615e-1101-7209-9932-7020bbd556f1', 'Note intervention', 'Document delivre par le medecin ou un infirmier de l''etablissement', true, '2023-03-31', '2023-03-31', 'Ajout', 'true', true, true, true, true, true) ON CONFLICT DO NOTHING;
INSERT INTO document.documents VALUES ('0190615e-1101-7209-9932-7020bbd556f2', 'Fiche de suivi', 'Document delivre par le medecin ou un infirmier de l''etablissement', true, '2023-03-31', '2023-03-31', 'Neutre', 'true', true, true, true, true, true) ON CONFLICT DO NOTHING;
INSERT INTO document.documents VALUES ('0190615e-1101-7209-9932-7020bbd556f3', 'Fiche de soin', 'Document delivre par le medecin ou un infirmier de l''etablissement', true, NULL, NULL, 'Ajout', 'true', true, true, true, true, false) ON CONFLICT DO NOTHING;
INSERT INTO document.documents VALUES ('0190615e-1101-7209-9932-7020bbd556f4', 'Formulaire de sortie', 'Document delivre par le medecin ou un infirmier de l''etablissement', true, NULL, NULL, 'Neutre', 'true', true, true, true, true, true) ON CONFLICT DO NOTHING;
INSERT INTO document.documents VALUES ('0190615e-1101-7209-9932-7020bbd556f5', 'ordonnance', 'Document delivre par le medecin ou un infirmier de l''etablissement', true, NULL, NULL, 'Neutre', 'true', true, true, true, true, false) ON CONFLICT DO NOTHING;
INSERT INTO document.documents VALUES ('0190615e-1101-7209-9932-7020bbd556f6', 'Bon de commande', 'Document delivre par le chef service', true, NULL, NULL, 'Neutre', 'true', true, true, true, true, false) ON CONFLICT DO NOTHING;
INSERT INTO document.documents VALUES ('0190615e-1101-7209-9932-7020bbd556f7', 'Bon de livraison', 'Document delivre par ', true, NULL, NULL, 'Reduire', 'true', true, true, true, true, true) ON CONFLICT DO NOTHING;
INSERT INTO document.documents VALUES ('0190615e-1101-7209-9932-7020bbd556f8', 'Fiche de selection', 'Document delivre par le magasinier', true, NULL, NULL, 'Neutre', 'true', true, true, true, true, false) ON CONFLICT DO NOTHING;


--
-- TOC entry 5290 (class 0 OID 28118)
-- Dependencies: 228
-- Data for Name: documentspromotions; Type: TABLE DATA; Schema: document; Owner: postgres
--

INSERT INTO document.documentspromotions VALUES ('0190615e-1101-7209-9932-7020bbd556f1', '1979bd79-f81b-498b-b247-e7b9bbb3f600') ON CONFLICT DO NOTHING;
INSERT INTO document.documentspromotions VALUES ('0190615e-1101-7209-9932-7020bbd556f8', '1979bd79-f81b-498b-b247-e7b9bbb3f602') ON CONFLICT DO NOTHING;
INSERT INTO document.documentspromotions VALUES ('0190615e-1101-7209-9932-7020bbd556f5', '1979bd79-f81b-498b-b247-e7b9bbb3f601') ON CONFLICT DO NOTHING;


--
-- TOC entry 5326 (class 0 OID 28713)
-- Dependencies: 264
-- Data for Name: elements; Type: TABLE DATA; Schema: document; Owner: postgres
--

INSERT INTO document.elements VALUES ('z6qrs7yo-5y6z-42d4-1940-3p2q1r0s9t8u', 'Tableau de bord principal', true, '2024-01-01', '2024-01-01', 'p6ghi7oe-5o6p-483a-3940-3f2g1h0i9j8k', 'u1lmn2tj-0t1u-4d8f-6495-8k7l6m5n4o3p') ON CONFLICT DO NOTHING;
INSERT INTO document.elements VALUES ('a7rst8zp-6z7a-43e5-0859-2q1r0s9t8u7v', 'Ajouter un utilisateur', true, '2024-01-01', '2024-01-01', 'q7hij8pf-6p7q-494b-2859-2g1h0i9j8k7l', 'v2mno3uk-1u2v-4e90-5304-7l6m5n4o3p2q') ON CONFLICT DO NOTHING;
INSERT INTO document.elements VALUES ('b8stu9aq-7a8b-44f6-7768-1r0s9t8u7v6w', 'Supprimer un groupe', true, '2024-01-01', '2024-01-01', 'r8ijk9qg-7q8r-4a5c-1768-1h0i9j8k7l6m', 'w3nop4vl-2v3w-4fa1-4213-6m5n4o3p2q1r') ON CONFLICT DO NOTHING;
INSERT INTO document.elements VALUES ('c9tuv0br-8b9c-4507-6677-0s9t8u7v6w5x', 'Modifier un menu', true, '2024-01-01', '2024-01-01', 's9jkl0rh-8r9s-4b6d-0677-0i9j8k7l6m5n', 'x4opq5wm-3w4x-40b2-3122-5n4o3p2q1r0s') ON CONFLICT DO NOTHING;
INSERT INTO document.elements VALUES ('d0uvw1cs-9c0d-4618-5586-9t8u7v6w5x4y', 'Afficher les éléments', true, '2024-01-01', '2024-01-01', 't0klm1si-9s0t-4c7e-7586-9j8k7l6m5n4o', 'y5pqr6xn-4x5y-41c3-2031-4o3p2q1r0s9t') ON CONFLICT DO NOTHING;


--
-- TOC entry 5324 (class 0 OID 28703)
-- Dependencies: 262
-- Data for Name: elementsbaselanques; Type: TABLE DATA; Schema: document; Owner: postgres
--

INSERT INTO document.elementsbaselanques VALUES ('e1vwx2dt-0d1e-4729-4495-8u7v6w5x4y3z', 'u1lmn2tj-0t1u-4d8f-6495-8k7l6m5n4o3p', 'Tableau de bord') ON CONFLICT DO NOTHING;
INSERT INTO document.elementsbaselanques VALUES ('f2wxy3eu-1e2f-483a-3304-7v6w5x4y3z2a', 'u1lmn2tj-0t1u-4d8f-6495-8k7l6m5n4o3p', 'Dashboard') ON CONFLICT DO NOTHING;
INSERT INTO document.elementsbaselanques VALUES ('e1vwx2dt-0d1e-4729-4495-8u7v6w5x4y3z', 'v2mno3uk-1u2v-4e90-5304-7l6m5n4o3p2q', 'Gestion des utilisateurs') ON CONFLICT DO NOTHING;
INSERT INTO document.elementsbaselanques VALUES ('f2wxy3eu-1e2f-483a-3304-7v6w5x4y3z2a', 'v2mno3uk-1u2v-4e90-5304-7l6m5n4o3p2q', 'User Management') ON CONFLICT DO NOTHING;
INSERT INTO document.elementsbaselanques VALUES ('e1vwx2dt-0d1e-4729-4495-8u7v6w5x4y3z', 'w3nop4vl-2v3w-4fa1-4213-6m5n4o3p2q1r', 'Gestion des groupes') ON CONFLICT DO NOTHING;


--
-- TOC entry 5321 (class 0 OID 28684)
-- Dependencies: 259
-- Data for Name: elementsbases; Type: TABLE DATA; Schema: document; Owner: postgres
--

INSERT INTO document.elementsbases VALUES ('u1lmn2tj-0t1u-4d8f-6495-8k7l6m5n4o3p', 'Tableau de bord', true, '2024-01-01', '2024-01-01', 'dashboard', 'a7rst8zp-6z7a-43e5-0859-2q1r0s9t8u7v') ON CONFLICT DO NOTHING;
INSERT INTO document.elementsbases VALUES ('v2mno3uk-1u2v-4e90-5304-7l6m5n4o3p2q', 'Gestion des utilisateurs', true, '2024-01-01', '2024-01-01', 'userManagement', 'b8stu9aq-7a8b-44f6-7768-1r0s9t8u7v6w') ON CONFLICT DO NOTHING;
INSERT INTO document.elementsbases VALUES ('w3nop4vl-2v3w-4fa1-4213-6m5n4o3p2q1r', 'Gestion des groupes', true, '2024-01-01', '2024-01-01', 'groupManagement', 'c9tuv0br-8b9c-4507-6677-0s9t8u7v6w5x') ON CONFLICT DO NOTHING;
INSERT INTO document.elementsbases VALUES ('x4opq5wm-3w4x-40b2-3122-5n4o3p2q1r0s', 'Gestion des menus', true, '2024-01-01', '2024-01-01', 'menuManagement', 'd0uvw1cs-9c0d-4618-5586-9t8u7v6w5x4y') ON CONFLICT DO NOTHING;
INSERT INTO document.elementsbases VALUES ('y5pqr6xn-4x5y-41c3-2031-4o3p2q1r0s9t', 'Gestion des éléments', true, '2024-01-01', '2024-01-01', 'elementManagement', 'z6qrs7yo-5y6z-42d4-1940-3p2q1r0s9t8u') ON CONFLICT DO NOTHING;


--
-- TOC entry 5325 (class 0 OID 28708)
-- Dependencies: 263
-- Data for Name: elementslangues; Type: TABLE DATA; Schema: document; Owner: postgres
--

INSERT INTO document.elementslangues VALUES ('e1vwx2dt-0d1e-4729-4495-8u7v6w5x4y3z', 'z6qrs7yo-5y6z-42d4-1940-3p2q1r0s9t8u', 'Tableau de bord principal') ON CONFLICT DO NOTHING;
INSERT INTO document.elementslangues VALUES ('f2wxy3eu-1e2f-483a-3304-7v6w5x4y3z2a', 'z6qrs7yo-5y6z-42d4-1940-3p2q1r0s9t8u', 'Main Dashboard') ON CONFLICT DO NOTHING;
INSERT INTO document.elementslangues VALUES ('e1vwx2dt-0d1e-4729-4495-8u7v6w5x4y3z', 'a7rst8zp-6z7a-43e5-0859-2q1r0s9t8u7v', 'Ajouter un utilisateur') ON CONFLICT DO NOTHING;
INSERT INTO document.elementslangues VALUES ('f2wxy3eu-1e2f-483a-3304-7v6w5x4y3z2a', 'a7rst8zp-6z7a-43e5-0859-2q1r0s9t8u7v', 'Add User') ON CONFLICT DO NOTHING;
INSERT INTO document.elementslangues VALUES ('e1vwx2dt-0d1e-4729-4495-8u7v6w5x4y3z', 'b8stu9aq-7a8b-44f6-7768-1r0s9t8u7v6w', 'Supprimer un groupe') ON CONFLICT DO NOTHING;


--
-- TOC entry 5291 (class 0 OID 28123)
-- Dependencies: 229
-- Data for Name: etapes; Type: TABLE DATA; Schema: document; Owner: postgres
--

INSERT INTO document.etapes VALUES ('1901bd79-f71b-498b-b247-e7b9bbb3f600', 'Paracetamol', 'true', '2022-01-02', '1900bd79-f71b-498b-b247-e7b9bbb3f602') ON CONFLICT DO NOTHING;
INSERT INTO document.etapes VALUES ('1901bd79-f71b-498b-b247-e7b9bbb3f601', 'Cartouche d''encre', 'true', '2020-01-08', '1900bd79-f71b-498b-b247-e7b9bbb3f600') ON CONFLICT DO NOTHING;
INSERT INTO document.etapes VALUES ('1901bd79-f71b-498b-b247-e7b9bbb3f602', 'Scanner', 'true', '2014-01-04', '1900bd79-f71b-498b-b247-e7b9bbb3f601') ON CONFLICT DO NOTHING;


--
-- TOC entry 5292 (class 0 OID 28128)
-- Dependencies: 230
-- Data for Name: etats; Type: TABLE DATA; Schema: document; Owner: postgres
--

INSERT INTO document.etats VALUES ('e190615e-1101-7209-9932-7020bbd556f2', 'Valide', 'Valide', '2022-01-01', '2022-01-02') ON CONFLICT DO NOTHING;
INSERT INTO document.etats VALUES ('e190615e-1101-7209-9932-7020bbd556f3', 'Rejete', 'Rejete', '2022-01-01', '2022-01-02') ON CONFLICT DO NOTHING;
INSERT INTO document.etats VALUES ('e190615e-1101-7209-9932-7020bbd556f4', 'En attente', 'En attente', '2022-01-01', '2022-01-02') ON CONFLICT DO NOTHING;
INSERT INTO document.etats VALUES ('e190615e-1101-7209-9932-7020bbd556f1', 'En cours', 'En cours d''utilisation', '2022-01-01', NULL) ON CONFLICT DO NOTHING;


--
-- TOC entry 5293 (class 0 OID 28133)
-- Dependencies: 231
-- Data for Name: exemplaires; Type: TABLE DATA; Schema: document; Owner: postgres
--

INSERT INTO document.exemplaires VALUES ('a1f1e3b0-4c6d-4e5e-9b7a-2a8b9b9c8d6f', '44444444-dddd-eeee-ffff-444444444444', NULL) ON CONFLICT DO NOTHING;
INSERT INTO document.exemplaires VALUES ('a1f1e4b0-4c6d-4e5e-9b7a-2a8b9b9c8d6f', '11111111-aaaa-bbbb-cccc-111111111111', NULL) ON CONFLICT DO NOTHING;
INSERT INTO document.exemplaires VALUES ('a1f8e3b0-4c6d-4e5e-9b7a-2a8b9b9c8d6f', 'b2c3d4e5-f678-90ab-12c3-d4e5f67890ab', NULL) ON CONFLICT DO NOTHING;
INSERT INTO document.exemplaires VALUES ('b3a7c8d6-1e2f-3d4c-5b6a-9e8f0a7b6c5d', '4390615e-1101-7209-9932-7020bbd556f2', NULL) ON CONFLICT DO NOTHING;
INSERT INTO document.exemplaires VALUES ('d9e2f1c2-8b3a-4f6c-9d5e-1a7b8a7c6d5e', '1e8b6f56-7525-45bf-aa7b-96c0a9ae4198', NULL) ON CONFLICT DO NOTHING;
INSERT INTO document.exemplaires VALUES ('f0a7b6c5-d4e3f2a1-8b9c-7d6e-5f4a3b2c1e0d', '99999999-2222-3333-4444-999999999999', NULL) ON CONFLICT DO NOTHING;


--
-- TOC entry 5294 (class 0 OID 28138)
-- Dependencies: 232
-- Data for Name: familles; Type: TABLE DATA; Schema: document; Owner: postgres
--

INSERT INTO document.familles VALUES ('f190615e-1101-7209-9932-7020bbd556f1', 'Medicaments', 'Medicaments', true, '2022-01-01', '2022-01-02') ON CONFLICT DO NOTHING;
INSERT INTO document.familles VALUES ('f190615e-1101-7209-9932-7020bbd556f2', 'Consommables Informatiques', 'Consommables Informatiques', true, '2022-01-01', '2022-01-02') ON CONFLICT DO NOTHING;
INSERT INTO document.familles VALUES ('f190615e-1101-7209-9932-7020bbd556f3', 'BioMedical', 'BioMedical', true, '2022-01-01', '2022-01-02') ON CONFLICT DO NOTHING;
INSERT INTO document.familles VALUES ('f190615e-1101-7209-9932-7020bbd556f4', 'Accessoirestest', 'Accessoires', true, '2022-01-01', NULL) ON CONFLICT DO NOTHING;


--
-- TOC entry 5295 (class 0 OID 28143)
-- Dependencies: 233
-- Data for Name: famillespromotions; Type: TABLE DATA; Schema: document; Owner: postgres
--

INSERT INTO document.famillespromotions VALUES ('f190615e-1101-7209-9932-7020bbd556f1', '1979bd79-f81b-498b-b247-e7b9bbb3f602') ON CONFLICT DO NOTHING;
INSERT INTO document.famillespromotions VALUES ('f190615e-1101-7209-9932-7020bbd556f3', '1979bd79-f81b-498b-b247-e7b9bbb3f601') ON CONFLICT DO NOTHING;
INSERT INTO document.famillespromotions VALUES ('f190615e-1101-7209-9932-7020bbd556f4', '1979bd79-f81b-498b-b247-e7b9bbb3f600') ON CONFLICT DO NOTHING;


--
-- TOC entry 5296 (class 0 OID 28148)
-- Dependencies: 234
-- Data for Name: filesattentes; Type: TABLE DATA; Schema: document; Owner: postgres
--

INSERT INTO document.filesattentes VALUES ('f190615e-1101-7209-9932-7020bbd556f1', '2022-01-01', '2022-01-02', true, '2190615e-1101-7209-9932-7020bbd556f1') ON CONFLICT DO NOTHING;
INSERT INTO document.filesattentes VALUES ('f190615e-1101-7209-9932-7020bbd556f2', '2022-01-01', '2022-01-02', true, '2190615e-1101-7209-9932-7020bbd556f2') ON CONFLICT DO NOTHING;
INSERT INTO document.filesattentes VALUES ('f190615e-1101-7209-9932-7020bbd556f3', '2022-01-01', '2022-01-02', true, '2190615e-1101-7209-9932-7020bbd556f3') ON CONFLICT DO NOTHING;


--
-- TOC entry 5331 (class 0 OID 28746)
-- Dependencies: 269
-- Data for Name: groupes; Type: TABLE DATA; Schema: document; Owner: postgres
--

INSERT INTO document.groupes VALUES ('f6jkl74e-5e6f-4e90-596a-3b2a1e0d9c8a', 'Administrateurs', true, '2024-01-01', '2024-01-01', 'p6ghi7oe-5o6p-483a-3940-3f2g1h0i9j8k') ON CONFLICT DO NOTHING;
INSERT INTO document.groupes VALUES ('g7mno85f-6f7g-4fa1-4859-2a1e0d9c8a7b', 'Utilisateurs', true, '2024-01-01', '2024-01-01', 'q7hij8pf-6p7q-494b-2859-2g1h0i9j8k7l') ON CONFLICT DO NOTHING;
INSERT INTO document.groupes VALUES ('h8pqr96g-7g8h-40b2-5748-1e0d9c8a7b6c', 'Invités', true, '2024-01-01', '2024-01-01', 'r8ijk9qg-7q8r-4a5c-1768-1h0i9j8k7l6m') ON CONFLICT DO NOTHING;
INSERT INTO document.groupes VALUES ('i9stu07h-8h9i-41c3-6637-0d9c8a7b6c5d', 'Managers', true, '2024-01-01', '2024-01-01', 's9jkl0rh-8r9s-4b6d-0677-0i9j8k7l6m5n') ON CONFLICT DO NOTHING;
INSERT INTO document.groupes VALUES ('j0vwx18i-9i0j-42d4-7526-9c8a7b6c5d4e', 'Techniciens', true, '2024-01-01', '2024-01-01', 't0klm1si-9s0t-4c7e-7586-9j8k7l6m5n4o') ON CONFLICT DO NOTHING;
INSERT INTO document.groupes VALUES ('f6jkl74e-5e6f-4e90-596a-3b2a1e0a7c8a', 'Admin', true, '2024-01-01', '2024-01-01', 'p6ghi7oe-5o6p-483a-3940-3f2g1h779j8k') ON CONFLICT DO NOTHING;


--
-- TOC entry 5297 (class 0 OID 28153)
-- Dependencies: 235
-- Data for Name: jouerroles; Type: TABLE DATA; Schema: document; Owner: postgres
--

INSERT INTO document.jouerroles VALUES ('d76fd017-cceb-4926-8705-380b08ad9c6a', NULL, '2022-01-01', '2022-01-02', NULL, '2024-01-01', '4390615e-1101-7209-9932-7020bbd556f1', '5190615e-1101-7209-9932-7020bbd556f1') ON CONFLICT DO NOTHING;
INSERT INTO document.jouerroles VALUES ('d39bcc09-ffe6-48d1-8582-f1173671d59f', NULL, '2022-01-01', '2022-01-02', NULL, '2024-01-01', '4390615e-1101-7209-9932-7020bbd556f2', '6130615e-1101-7209-9932-7020bbd556f2') ON CONFLICT DO NOTHING;
INSERT INTO document.jouerroles VALUES ('9183d626-0c1a-4f70-8556-1c417d5feb91', NULL, '2022-01-01', '2022-01-02', NULL, '2024-01-01', '4390615e-1101-7209-9932-7020bbd556f3', '6191615e-1101-7209-9932-7020bbd556f3') ON CONFLICT DO NOTHING;


--
-- TOC entry 5323 (class 0 OID 28696)
-- Dependencies: 261
-- Data for Name: langues; Type: TABLE DATA; Schema: document; Owner: postgres
--

INSERT INTO document.langues VALUES ('e1vwx2dt-0d1e-4729-4495-8u7v6w5x4y3z', 'Français', true, '2024-01-01', '2024-01-01') ON CONFLICT DO NOTHING;
INSERT INTO document.langues VALUES ('f2wxy3eu-1e2f-483a-3304-7v6w5x4y3z2a', 'Anglais', true, '2024-01-01', '2024-01-01') ON CONFLICT DO NOTHING;
INSERT INTO document.langues VALUES ('g3xyz4fv-2f3g-494b-2213-6w5x4y3z2a1b', 'Espagnol', true, '2024-01-01', '2024-01-01') ON CONFLICT DO NOTHING;
INSERT INTO document.langues VALUES ('h4yza5gw-3g4h-4a5c-1122-5x4y3z2a1b0c', 'Allemand', true, '2024-01-01', '2024-01-01') ON CONFLICT DO NOTHING;
INSERT INTO document.langues VALUES ('i5zab6hx-4h5i-4b6d-0031-4y3z2a1b0c9d', 'Italien', true, '2024-01-01', '2024-01-01') ON CONFLICT DO NOTHING;


--
-- TOC entry 5327 (class 0 OID 28720)
-- Dependencies: 265
-- Data for Name: menus; Type: TABLE DATA; Schema: document; Owner: postgres
--

INSERT INTO document.menus VALUES ('p6ghi7oe-5o6p-483a-3940-3f2g1h0i9j8k', true, '2024-01-01', 'k1yzabc2j-0j1k-43e5-8415-8a7b6c5d4e3f', 'f6jkl74e-5e6f-4e90-596a-3b2a1e0d9c8a') ON CONFLICT DO NOTHING;
INSERT INTO document.menus VALUES ('q7hij8pf-6p7q-494b-2859-2g1h0i9j8k7l', true, '2024-01-01', 'l2bcd3ka-1k2l-44f6-9304-7b6c5d4e3f2g', 'g7mno85f-6f7g-4fa1-4859-2a1e0d9c8a7b') ON CONFLICT DO NOTHING;
INSERT INTO document.menus VALUES ('r8ijk9qg-7q8r-4a5c-1768-1h0i9j8k7l6m', true, '2024-01-01', 'm3cde4lb-2l3m-4507-0213-6c5d4e3f2g1h', 'h8pqr96g-7g8h-40b2-5748-1e0d9c8a7b6c') ON CONFLICT DO NOTHING;
INSERT INTO document.menus VALUES ('s9jkl0rh-8r9s-4b6d-0677-0i9j8k7l6m5n', true, '2024-01-01', 'n4efg5mc-3m4n-4618-1122-5d4e3f2g1h0i', 'i9stu07h-8h9i-41c3-6637-0d9c8a7b6c5d') ON CONFLICT DO NOTHING;
INSERT INTO document.menus VALUES ('t0klm1si-9s0t-4c7e-7586-9j8k7l6m5n4o', true, '2024-01-01', 'o5fgh6nd-4n5o-4729-2031-4e3f2g1h0i9j', 'j0vwx18i-9i0j-42d4-7526-9c8a7b6c5d4e') ON CONFLICT DO NOTHING;
INSERT INTO document.menus VALUES ('p6ghi7oe-5o6p-483a-3940-3f2g1h779j8k', true, '2024-01-01', '0190615e-1101-7209-9932-7020bbd55714', 'f6jkl74e-5e6f-4e90-596a-3b2a1e0a7c8a') ON CONFLICT DO NOTHING;


--
-- TOC entry 5298 (class 0 OID 28158)
-- Dependencies: 236
-- Data for Name: missions; Type: TABLE DATA; Schema: document; Owner: postgres
--

INSERT INTO document.missions VALUES ('3190615e-1101-7209-9932-7020bbd556f1', 'Encaissement', 'recu de paiement lié à une mission', true, '2000-03-07', '1990-03-07', '2190615e-1101-7209-9932-7020bbd556f1', NULL) ON CONFLICT DO NOTHING;
INSERT INTO document.missions VALUES ('3190615e-1101-7209-9932-7020bbd556f2', 'Resultat Labo', 'Communiquer les résultats du labo aux patients', true, '2000-03-07', '1990-03-07', '2190615e-1101-7209-9932-7020bbd556f1', NULL) ON CONFLICT DO NOTHING;
INSERT INTO document.missions VALUES ('3190615e-1101-7209-9932-7020bbd556f4', 'Prelevement Labo', 'Prélévement fait par laboratoire', true, '2000-03-07', '1990-03-07', '2190615e-1101-7209-9932-7020bbd556f2', NULL) ON CONFLICT DO NOTHING;
INSERT INTO document.missions VALUES ('3190615e-1101-7209-9932-7020bbd556f3', 'Hospitalisation8', 'bon d''entrée et de sortie est une mission', true, '2000-03-07', NULL, '2190615e-1101-7209-9932-7020bbd556f2', NULL) ON CONFLICT DO NOTHING;
INSERT INTO document.missions VALUES ('3190615e-1101-7209-9932-7020bbd556f5', 'Consultation et Spécialiste', 'Consultation faite par un médecin', true, '2000-03-07', NULL, '2190615e-1101-7209-9932-7020bbd556f3', NULL) ON CONFLICT DO NOTHING;
INSERT INTO document.missions VALUES ('3190615e-1101-7209-9932-7020bbd556f6', 'Consultation et Spécialiste', 'Consultation faite par une infirmière', true, '2000-03-07', NULL, '2190615e-1101-7209-9932-7020bbd556f3', NULL) ON CONFLICT DO NOTHING;


--
-- TOC entry 5299 (class 0 OID 28163)
-- Dependencies: 237
-- Data for Name: mouvementcaisses; Type: TABLE DATA; Schema: document; Owner: postgres
--

INSERT INTO document.mouvementcaisses VALUES ('97d314f5-84ad-77cd-874d-9c55f0e45790', 45.21, 'sur commande', NULL, NULL, NULL, NULL, NULL, 'a1f8e3b0-4c6d-4e5e-ab7a-2a8b9b9c8d6f', 'a1f8e3b0-4c6d-4e5e-7b7a-2a8b9b9c8d6f', '4390615e-1101-7209-9932-7020bbd556f1', 'a1f8e3b0-4c6d-4e5e-9b7a-2a8b9b9c8d6f', '2022-01-02') ON CONFLICT DO NOTHING;
INSERT INTO document.mouvementcaisses VALUES ('bfee750d-9b8c-7475-9373-08eff9b3ccb7', 1258.36, 'vente à credit', NULL, NULL, NULL, NULL, NULL, 'd9e2f1c2-8b3a-4f6c-bd5e-1a7b8a7c6d5e', 'd9e2f1c2-8b3a-4f6c-7d5e-1a7b8a7c6d5e', '4390615e-1101-7209-9932-7020bbd556f2', 'd9e2f1c2-8b3a-4f6c-9d5e-1a7b8a7c6d5e', '2022-01-02') ON CONFLICT DO NOTHING;
INSERT INTO document.mouvementcaisses VALUES ('0e7cea07-d09e-7f79-be6c-6dd27aecbce6', 1548.21, 'achat express', NULL, NULL, NULL, NULL, NULL, 'b3a7c8d6-1e2f-3d4c-cb6a-9e8f0a7b6c5d', 'b3a7c8d6-1e2f-3d4c-7b6a-9e8f0a7b6c5d', '4390615e-1101-7209-9932-7020bbd556f3', 'b3a7c8d6-1e2f-3d4c-5b6a-9e8f0a7b6c5d', '2022-01-02') ON CONFLICT DO NOTHING;
INSERT INTO document.mouvementcaisses VALUES ('decaedc8-a908-7cad-bdd6-0403a2614f22', 4563.21, 'inventaire', NULL, NULL, NULL, NULL, NULL, 'f0a7b6c5-d4e3f2a1-8d9c-7d6e-5f4a3b2c1e0d', 'f0a7b6c5-d4e3f2a1-7b9c-7d6e-5f4a3b2c1e0d', '4390615e-1101-7209-9932-7020bbd556f3', 'f0a7b6c5-d4e3f2a1-8b9c-7d6e-5f4a3b2c1e0d', '2022-01-02') ON CONFLICT DO NOTHING;
INSERT INTO document.mouvementcaisses VALUES ('0e7cea07-d09e-7f79-be6c-6dd27aecbc47', 5245.54, 'sur commande', NULL, NULL, NULL, NULL, NULL, 'b3a7c8d6-1e2f-3d4c-cb6a-9e8f0a7b6c5d', 'd301ff83-1b62-4e6d-aa23-57c7825bcd57', '4390615e-1101-7209-9932-7020bbd556f3', 'a1f1e3b0-4c6d-4e5e-9b7a-2a8b9b9c8d6f', '2022-01-02') ON CONFLICT DO NOTHING;
INSERT INTO document.mouvementcaisses VALUES ('0e7cea07-d09e-7f79-be6c-6dd27aecbc14', 42542453.54, 'sur commande', NULL, NULL, NULL, NULL, NULL, 'f0a7b6c5-d4e3f2a1-8d9c-7d6e-5f4a3b2c1e0d', 'adbff692-418d-43ab-a196-fcc3114b2daa', '4390615e-1101-7209-9932-7020bbd556f1', 'a1f1e4b0-4c6d-4e5e-9b7a-2a8b9b9c8d6f', '2022-01-02') ON CONFLICT DO NOTHING;


--
-- TOC entry 5300 (class 0 OID 28168)
-- Dependencies: 238
-- Data for Name: mouvements; Type: TABLE DATA; Schema: document; Owner: postgres
--

INSERT INTO document.mouvements VALUES ('0e7cea07-d09e-4f79-be6c-6dd27aecbce6', 'achat express', 5, 2000, '2022-01-01', NULL, '2022-01-02', '6190615e-1101-7209-9932-7020bbd556f3', 'bbbbbbbb-4444-5555-6666-bbbbbbbbbbbb') ON CONFLICT DO NOTHING;
INSERT INTO document.mouvements VALUES ('97d314f5-84ad-47cd-874d-9c55f0e45790', 'sur commande', 10, 5000, '2022-01-01', NULL, '2022-01-02', '6190615e-1101-7209-9932-7020bbd556f1', 'c3d4e5f6-7890-ab12-c3d4-e5f67890abcd') ON CONFLICT DO NOTHING;
INSERT INTO document.mouvements VALUES ('bfee750d-9b8c-4475-9373-08eff9b3ccb7', 'vente à credit', 20, 10000, '2022-01-01', NULL, '2022-01-02', '6190615e-1101-7209-9932-7020bbd556f2', '99999999-2222-3333-4444-999999999999') ON CONFLICT DO NOTHING;
INSERT INTO document.mouvements VALUES ('decaedc8-a908-4cad-bdd6-0403a2614f22', 'inventaire', 5, 2000, '2022-01-01', NULL, '2022-01-02', '6190615e-1101-7209-9932-7020bbd556f4', 'aaaaaaaa-3333-4444-5555-aaaaaaaaaaaa') ON CONFLICT DO NOTHING;


--
-- TOC entry 5301 (class 0 OID 28173)
-- Dependencies: 239
-- Data for Name: ordreetats; Type: TABLE DATA; Schema: document; Owner: postgres
--

INSERT INTO document.ordreetats VALUES ('97d314a5-84ad-47cd-874d-9c55f0e45790', '2022-01-01', '2022-01-02', '2022-01-02', 2, 'e190615e-1101-7209-9932-7020bbd556f3') ON CONFLICT DO NOTHING;
INSERT INTO document.ordreetats VALUES ('97d314a5-84ad-47cd-874d-9c55f0e45791', '2022-01-01', '2022-01-02', '2022-01-02', 5, 'e190615e-1101-7209-9932-7020bbd556f2') ON CONFLICT DO NOTHING;
INSERT INTO document.ordreetats VALUES ('97d314a5-84ad-47cd-874d-9c55f0e45792', '2022-01-01', '2022-01-02', '2022-01-02', 8, 'e190615e-1101-7209-9932-7020bbd556f1') ON CONFLICT DO NOTHING;


--
-- TOC entry 5329 (class 0 OID 28734)
-- Dependencies: 267
-- Data for Name: organisations; Type: TABLE DATA; Schema: document; Owner: postgres
--

INSERT INTO document.organisations VALUES ('a1eebc99-9c0b-4ef8-bb6d-6bb9bd380a13', 'Entreprise A') ON CONFLICT DO NOTHING;
INSERT INTO document.organisations VALUES ('b2fcd30a-1a2b-4a5c-9e8f-7d6a5b4c3d2e', 'Entreprise B') ON CONFLICT DO NOTHING;
INSERT INTO document.organisations VALUES ('c3abc41b-2b3c-4b6d-8a9b-6e5c4d3b2f1a', 'Association C') ON CONFLICT DO NOTHING;
INSERT INTO document.organisations VALUES ('d4def52c-3c4d-4c7e-7b8a-5d4b3c2a1e0d', 'Fondation D') ON CONFLICT DO NOTHING;
INSERT INTO document.organisations VALUES ('e5ghi63d-4d5e-4d8f-6a7b-4c3a2b1d0c9b', 'Collectivité E') ON CONFLICT DO NOTHING;


--
-- TOC entry 5330 (class 0 OID 28741)
-- Dependencies: 268
-- Data for Name: organiser; Type: TABLE DATA; Schema: document; Owner: postgres
--

INSERT INTO document.organiser VALUES ('a1eebc99-9c0b-4ef8-bb6d-6bb9bd380a13', 'k1yzabc2j-0j1k-43e5-8415-8a7b6c5d4e3f') ON CONFLICT DO NOTHING;
INSERT INTO document.organiser VALUES ('b2fcd30a-1a2b-4a5c-9e8f-7d6a5b4c3d2e', 'l2bcd3ka-1k2l-44f6-9304-7b6c5d4e3f2g') ON CONFLICT DO NOTHING;
INSERT INTO document.organiser VALUES ('c3abc41b-2b3c-4b6d-8a9b-6e5c4d3b2f1a', 'm3cde4lb-2l3m-4507-0213-6c5d4e3f2g1h') ON CONFLICT DO NOTHING;
INSERT INTO document.organiser VALUES ('d4def52c-3c4d-4c7e-7b8a-5d4b3c2a1e0d', 'n4efg5mc-3m4n-4618-1122-5d4e3f2g1h0i') ON CONFLICT DO NOTHING;
INSERT INTO document.organiser VALUES ('e5ghi63d-4d5e-4d8f-6a7b-4c3a2b1d0c9b', 'o5fgh6nd-4n5o-4729-2031-4e3f2g1h0i9j') ON CONFLICT DO NOTHING;
INSERT INTO document.organiser VALUES ('b2fcd30a-1a2b-4a5c-9e8f-7d6a5b4c3d2e', 'k1yzabc2j-0j1k-43e5-8415-8a7b6c5d4e3f') ON CONFLICT DO NOTHING;


--
-- TOC entry 5302 (class 0 OID 28178)
-- Dependencies: 240
-- Data for Name: parcours; Type: TABLE DATA; Schema: document; Owner: postgres
--

INSERT INTO document.parcours VALUES ('1900bd79-f71b-498b-b247-e7b9bbb3f600', 'Paracetamol', '2022-01-02', '2022-01-02') ON CONFLICT DO NOTHING;
INSERT INTO document.parcours VALUES ('1900bd79-f71b-498b-b247-e7b9bbb3f601', 'Cartouche d''encre', '2020-01-08', '2022-01-02') ON CONFLICT DO NOTHING;
INSERT INTO document.parcours VALUES ('1900bd79-f71b-498b-b247-e7b9bbb3f602', 'Scanner', '2014-01-04', '2022-01-02') ON CONFLICT DO NOTHING;


--
-- TOC entry 5335 (class 0 OID 46404)
-- Dependencies: 295
-- Data for Name: personnels; Type: TABLE DATA; Schema: document; Owner: postgres
--

INSERT INTO document.personnels VALUES ('4390615e-1101-7209-9932-7020bbd556f1', '2024-07-27', 'Tagne', '2000-04-10', '655455487', NULL, 'tagnewillie@gmail.com', 'Willy', 'M', NULL) ON CONFLICT DO NOTHING;
INSERT INTO document.personnels VALUES ('4390615e-1101-7209-9932-7020bbd556f2', '2024-07-27', 'Peter', '2004-08-10', '655455487', NULL, 'peteralan@gmail.com', 'Alan', 'M', NULL) ON CONFLICT DO NOTHING;
INSERT INTO document.personnels VALUES ('4390615e-1101-7209-9932-7020bbd556f3', '2024-07-27', 'Dombo', '2002-10-10', '655455487', NULL, 'dombogilles@gmail.com', 'Gilles', 'M', NULL) ON CONFLICT DO NOTHING;
INSERT INTO document.personnels VALUES ('1e8b6f56-7525-45bf-aa7b-96c0a9ae4198', '2023-01-01', 'Tagne', '1980-01-01', NULL, '2025-08-26', NULL, 'Willy1', 'M', NULL) ON CONFLICT DO NOTHING;
INSERT INTO document.personnels VALUES ('8a9b710a-a7fb-44ca-9587-b427b163cdab', '2023-01-01', 'kouam', '1980-01-01', NULL, '0002-11-30 BC', NULL, 'Esaie Ledoux', 'M', NULL) ON CONFLICT DO NOTHING;


--
-- TOC entry 5303 (class 0 OID 28188)
-- Dependencies: 241
-- Data for Name: personnes; Type: TABLE DATA; Schema: document; Owner: postgres
--

INSERT INTO document.personnes VALUES ('11111111-aaaa-bbbb-cccc-111111111111', '12 rue des Fleurs', 'alice@email.com', '0600000001', NULL, NULL, NULL, '1992-05-10', NULL, NULL, '00012f83-2a62-4e6d-aa23-57c7825bcd57', 'personnePhysique', 'be8ef47f-d5a2-4254-cd79-af860672553e', false) ON CONFLICT DO NOTHING;
INSERT INTO document.personnes VALUES ('33333333-cccc-dddd-eeee-333333333333', '56 boulevard St Michel', 'carole@email.com', '0600000003', NULL, NULL, NULL, '1979-07-15', NULL, NULL, '1518e585-f82a-4d5f-af1c-54f880d766d3', 'personnePhysique', 'be8ef47f-d5a2-4254-cd79-af860672556e', false) ON CONFLICT DO NOTHING;
INSERT INTO document.personnes VALUES ('bbbbbbbb-4444-5555-6666-bbbbbbbbbbbb', '7 boulevard Distribution', 'contact@dist3.com', '0800000003', 'QR011', 'DIST003', 'Distributeur Trois', NULL, '2025-06-04', NULL, 'a1f8e3b0-4c6d-4e5e-7b7a-2a8b9b9c8d6f', 'distributeur', NULL, true) ON CONFLICT DO NOTHING;
INSERT INTO document.personnes VALUES ('99999999-2222-3333-4444-999999999999', '5 rue du Commerce', 'contact@dist1.com', '0800000001', 'QR009', 'DIST001', 'Distributeur Un', NULL, '2025-06-04', NULL, 'adbff692-418d-43ab-a196-fcc3114b2daa', 'distributeur', NULL, true) ON CONFLICT DO NOTHING;
INSERT INTO document.personnes VALUES ('6b7894b5-1b07-487a-b8fe-d14ab61c1b2e', 'Bonaloka', 'kouamdoux@gmail.com', '697700650', NULL, NULL, NULL, NULL, NULL, NULL, 'f0a7b6c5-d4e3f2a1-7b9c-7d6e-5f4a3b2c1e0d', 'personnePhysique', 'f4d5e6g7-b8c9-7d01-2e3f-456780a12bcd', false) ON CONFLICT DO NOTHING;
INSERT INTO document.personnes VALUES ('44444444-dddd-eeee-ffff-444444444444', '78 impasse des Lilas', 'daniel@email.com', '0600000004', NULL, NULL, NULL, '1995-02-28', NULL, NULL, '1179bd79-f71b-498b-b247-e7b9bbb3f600', 'personnePhysique', 'g5e6f7h8-c9d0-8e12-3f4g-567801b23cde', false) ON CONFLICT DO NOTHING;
INSERT INTO document.personnes VALUES ('77777777-0000-1111-2222-777777777777', '3 boulevard des Affaires', 'contact@ent3.com', '0700000003', 'QR007', 'ENT003', 'Entreprise Gamma', NULL, '2025-06-04', NULL, NULL, 'personneMorale', NULL, false) ON CONFLICT DO NOTHING;
INSERT INTO document.personnes VALUES ('88888888-1111-2222-3333-888888888888', '4 place de la Victoire', 'contact@ent4.com', '0700000004', 'QR008', 'ENT004', 'Entreprise Delta', NULL, '2025-06-04', NULL, NULL, 'personneMorale', NULL, true) ON CONFLICT DO NOTHING;
INSERT INTO document.personnes VALUES ('aaaaaaaa-3333-4444-5555-aaaaaaaaaaaa', '6 avenue des Ventes', 'contact@dist2.com', '0800000002', 'QR010', 'DIST002', 'Distributeur Deux', NULL, '2025-06-04', NULL, NULL, 'distributeur', NULL, false) ON CONFLICT DO NOTHING;
INSERT INTO document.personnes VALUES ('22222222-bbbb-cccc-dddd-222222222222', '34 avenue Victor Hugo', 'bob@email.com', '0600000002', 'QR002', NULL, NULL, '1988-11-23', '2025-06-04', NULL, 'a1111111-1111-1111-1111-111111111111', 'personnePhysique', 'be8ef47f-d5a2-4254-cd79-af860672555e', false) ON CONFLICT DO NOTHING;
INSERT INTO document.personnes VALUES ('3b277afa-98fc-4b0f-9b16-1d2fb4aa8ec6', 'Bonaloka', 'kouamdoux@gmail.com', '697700650', NULL, NULL, NULL, NULL, NULL, NULL, 'b2222222-2222-2222-2222-222222222222', 'personnePhysique', 'be8ef47f-d5a2-4254-cd79-af860672557e', false) ON CONFLICT DO NOTHING;
INSERT INTO document.personnes VALUES ('c3d4e5f6-7890-ab12-c3d4-e5f67890abcd', '11 boulevard Sud', 'contact@dist5.com', '0800000005', 'QR015', 'DIST005', 'Distributeur Cinq', NULL, '2025-06-04', NULL, 'adbff692-418d-43ab-a196-fcc3114b2daa', 'distributeur', NULL, true) ON CONFLICT DO NOTHING;
INSERT INTO document.personnes VALUES ('8a9b710a-a7fb-44ca-9587-b427b163cdab', NULL, NULL, '697700650', NULL, NULL, NULL, NULL, NULL, NULL, 'b3a7c8d6-1e2f-3d4c-7b6a-9e8f0a7b6c5d', 'personnel', 'e3c4d5f6-a7b8-6c90-1d2e-3456f78901ab', false) ON CONFLICT DO NOTHING;
INSERT INTO document.personnes VALUES ('1e8b6f56-7525-45bf-aa7b-96c0a9ae4198', NULL, NULL, '89005864904', NULL, NULL, NULL, NULL, NULL, NULL, 'd301ff83-1b62-4e6d-aa23-57c7825bcd57', 'personnel', 'be8ef47f-d5a2-4254-cd79-af860672554e', false) ON CONFLICT DO NOTHING;
INSERT INTO document.personnes VALUES ('4390615e-1101-7209-9932-7020bbd556f1', NULL, 'tagnewillie@gmail.com', '655455487', NULL, NULL, NULL, NULL, '2024-07-27', NULL, 'd9e2f1c2-8b3a-4f6c-7d5e-1a7b8a7c6d5e', 'personnel', 'd2b3c4e5-f6a7-5b89-0c1d-2345e67890fa', true) ON CONFLICT DO NOTHING;
INSERT INTO document.personnes VALUES ('d4e5f678-90ab-12c3-d4e5-f67890abcdea', '12 place Centrale', 'contact@ent6.com', '0700000006', 'QR016', 'ENT006', 'Entreprise Zeta', NULL, '2025-06-04', NULL, NULL, 'personneMorale', NULL, true) ON CONFLICT DO NOTHING;
INSERT INTO document.personnes VALUES ('b2c3d4e5-f678-90ab-12c3-d4e5f67890ab', '10 avenue Libre', 'contact@ent5.com', '0700000005', 'QR014', 'ENT005', 'Entreprise Epsilon', NULL, '2025-06-04', NULL, NULL, 'personneMorale', NULL, false) ON CONFLICT DO NOTHING;
INSERT INTO document.personnes VALUES ('4390615e-1101-7209-9932-7020bbd556f3', NULL, 'dombogilles@gmail.com', '655455487', NULL, NULL, NULL, NULL, '2024-07-27', NULL, 'a1234567-89ab-cdef-0123-456789abcdef', 'personnel', 'be8ef47f-d5a2-4254-cd79-af860672558e', false) ON CONFLICT DO NOTHING;
INSERT INTO document.personnes VALUES ('4390615e-1101-7209-9932-7020bbd556f2', NULL, 'peteralan@gmail.com', '655455487', NULL, NULL, NULL, NULL, '2024-07-27', NULL, 'b2345678-9abc-def0-1234-56789abcdef0', 'personnel', 'c1a2b3d4-e5f6-4a78-9b0c-1234d56789ef', true) ON CONFLICT DO NOTHING;


--
-- TOC entry 5333 (class 0 OID 46344)
-- Dependencies: 293
-- Data for Name: personnesmorales; Type: TABLE DATA; Schema: document; Owner: postgres
--

INSERT INTO document.personnesmorales VALUES ('77777777-0000-1111-2222-777777777777', 'Entreprise Alpha', 'ENT001') ON CONFLICT DO NOTHING;
INSERT INTO document.personnesmorales VALUES ('88888888-1111-2222-3333-888888888888', 'Entreprise Beta', 'ENT002') ON CONFLICT DO NOTHING;
INSERT INTO document.personnesmorales VALUES ('b2c3d4e5-f678-90ab-12c3-d4e5f67890ab', 'Entreprise Gamma', 'ENT003') ON CONFLICT DO NOTHING;
INSERT INTO document.personnesmorales VALUES ('d4e5f678-90ab-12c3-d4e5-f67890abcdea', 'Entreprise Delta', 'ENT004') ON CONFLICT DO NOTHING;


--
-- TOC entry 5332 (class 0 OID 46332)
-- Dependencies: 292
-- Data for Name: personnesphysique; Type: TABLE DATA; Schema: document; Owner: postgres
--

INSERT INTO document.personnesphysique VALUES ('22222222-bbbb-cccc-dddd-222222222222', 'Martin', 'Bob', 'M', '1988-11-23', '2025-06-02') ON CONFLICT DO NOTHING;
INSERT INTO document.personnesphysique VALUES ('11111111-aaaa-bbbb-cccc-111111111111', 'Dupont3', 'Alice', 'F', '2025-08-06', '2025-06-01') ON CONFLICT DO NOTHING;
INSERT INTO document.personnesphysique VALUES ('33333333-cccc-dddd-eeee-333333333333', 'Durand25', 'Carole', 'F', '2025-08-06', '2025-06-03') ON CONFLICT DO NOTHING;
INSERT INTO document.personnesphysique VALUES ('3b277afa-98fc-4b0f-9b16-1d2fb4aa8ec6', 'Martin2', 'Esaie Ledoux', 'M', '2025-08-07', NULL) ON CONFLICT DO NOTHING;
INSERT INTO document.personnesphysique VALUES ('44444444-dddd-eeee-ffff-444444444444', 'Petit425', 'Daniel', 'M', '2025-08-07', '2025-06-04') ON CONFLICT DO NOTHING;
INSERT INTO document.personnesphysique VALUES ('6b7894b5-1b07-487a-b8fe-d14ab61c1b2e', 'Martin36Petit425', 'Esaie Ledoux', 'M', '2025-08-07', NULL) ON CONFLICT DO NOTHING;


--
-- TOC entry 5304 (class 0 OID 28199)
-- Dependencies: 242
-- Data for Name: precomouvements; Type: TABLE DATA; Schema: document; Owner: postgres
--

INSERT INTO document.precomouvements VALUES ('6290615e-1101-7209-9932-7020bbd556f1', 'Inventaire', true, '2022-01-01', '2022-01-02', 'Neutre') ON CONFLICT DO NOTHING;
INSERT INTO document.precomouvements VALUES ('17ee6932-2fcd-4b93-9c4c-0a4dbf659bff', 'Achat', true, '2022-01-01', '2022-01-02', 'Ajout') ON CONFLICT DO NOTHING;
INSERT INTO document.precomouvements VALUES ('77b8577f-6d26-4376-af30-a3c8f75a9194', 'interdiction Infirmière', true, '2022-01-01', '2022-01-02', 'Neutre') ON CONFLICT DO NOTHING;
INSERT INTO document.precomouvements VALUES ('6290615e-1101-7209-9932-7020bbd556f5', 'Don', true, '2022-01-01', '2022-01-02', 'Neutre') ON CONFLICT DO NOTHING;
INSERT INTO document.precomouvements VALUES ('6290615e-1101-7209-9932-7020bbd556f6', 'Perte', true, '2022-01-01', '2022-01-02', 'Reduire') ON CONFLICT DO NOTHING;
INSERT INTO document.precomouvements VALUES ('6290615e-1101-7209-9932-7020bbd556f7', 'Retour', true, '2022-01-01', '2022-01-02', 'Ajout') ON CONFLICT DO NOTHING;
INSERT INTO document.precomouvements VALUES ('6290615e-1101-7209-9932-7020bbd556f8', 'Sortie magasin', true, '2022-01-01', '2022-01-02', 'Reduire') ON CONFLICT DO NOTHING;
INSERT INTO document.precomouvements VALUES ('6290615e-1101-7209-9932-7020bbd556f2', 'Vente', true, '2022-01-01', NULL, NULL) ON CONFLICT DO NOTHING;


--
-- TOC entry 5305 (class 0 OID 28204)
-- Dependencies: 243
-- Data for Name: precomouvementsqtes; Type: TABLE DATA; Schema: document; Owner: postgres
--

INSERT INTO document.precomouvementsqtes VALUES ('a97eb081-62f6-4617-ba29-64dc8593a9ff', 10, 100, 500, 5000, '2022-01-01', '2022-01-02', '6290615e-1101-7209-9932-7020bbd556f1', '6190615e-1101-7209-9932-7020bbd556f1') ON CONFLICT DO NOTHING;
INSERT INTO document.precomouvementsqtes VALUES ('a87eb081-62f6-4617-ba29-64dc8593a9ff', 20, 200, 1000, 10000, '2022-01-01', '2022-01-02', '6290615e-1101-7209-9932-7020bbd556f2', '6190615e-1101-7209-9932-7020bbd556f2') ON CONFLICT DO NOTHING;
INSERT INTO document.precomouvementsqtes VALUES ('a77eb081-62f6-4617-ba29-64dc8593a9ff', 5, 50, 200, 2000, '2022-01-01', '2022-01-02', '17ee6932-2fcd-4b93-9c4c-0a4dbf659bff', '6190615e-1101-7209-9932-7020bbd556f3') ON CONFLICT DO NOTHING;
INSERT INTO document.precomouvementsqtes VALUES ('a67eb081-62f6-4617-ba29-64dc8593a9ff', 5, 50, 200, 2000, '2022-01-01', '2022-01-02', '77b8577f-6d26-4376-af30-a3c8f75a9194', '6190615e-1101-7209-9932-7020bbd556f4') ON CONFLICT DO NOTHING;
INSERT INTO document.precomouvementsqtes VALUES ('a97eb071-62f6-4617-ba29-64dc8593a9ff', 5, 50, 200, 2000, '2022-01-01', '2022-01-02', '77b8577f-6d26-4376-af30-a3c8f75a9194', '6190615e-1101-7209-9932-7020bbd556f5') ON CONFLICT DO NOTHING;
INSERT INTO document.precomouvementsqtes VALUES ('a97eb081-72f6-4617-ba29-64dc8593a9ff', 5, 50, 200, 2000, '2022-01-01', '2022-01-02', '6290615e-1101-7209-9932-7020bbd556f6', NULL) ON CONFLICT DO NOTHING;
INSERT INTO document.precomouvementsqtes VALUES ('a97eb081-62f6-4617-ba49-64dc8593a9ff', 5, 50, 200, 2000, '2022-01-01', '2022-01-02', '6290615e-1101-7209-9932-7020bbd556f7', NULL) ON CONFLICT DO NOTHING;
INSERT INTO document.precomouvementsqtes VALUES ('a97eb081-62f6-4617-ba29-65dc8593a9ff', 5, 50, 200, 2000, '2022-01-01', '2022-01-02', '6290615e-1101-7209-9932-7020bbd556f8', NULL) ON CONFLICT DO NOTHING;
INSERT INTO document.precomouvementsqtes VALUES ('a97eb081-62f6-4617-ba29-74dc8593a9ff', 5, 50, 200, 2000, '2022-01-01', '2022-01-02', '6290615e-1101-7209-9932-7020bbd556f8', NULL) ON CONFLICT DO NOTHING;


--
-- TOC entry 5306 (class 0 OID 28209)
-- Dependencies: 244
-- Data for Name: promotions; Type: TABLE DATA; Schema: document; Owner: postgres
--

INSERT INTO document.promotions VALUES ('1979bd79-f81b-498b-b247-e7b9bbb3f600', '2022-01-01', '2022-01-01', 'R5', 'garantie', 54.25, '2000-04-10', '2022-01-01', 'aaaaaaaa-3333-4444-5555-aaaaaaaaaaaa') ON CONFLICT DO NOTHING;
INSERT INTO document.promotions VALUES ('1979bd79-f81b-498b-b247-e7b9bbb3f601', '2022-01-01', '2022-01-01', 'R54', 'virement', 74.25, '2004-08-10', '2022-01-01', '99999999-2222-3333-4444-999999999999') ON CONFLICT DO NOTHING;
INSERT INTO document.promotions VALUES ('1979bd79-f81b-498b-b247-e7b9bbb3f602', '2022-01-01', '2022-01-01', 'R45', 'devoir', 95.23, '2002-10-10', '2022-01-01', 'c3d4e5f6-7890-ab12-c3d4-e5f67890abcd') ON CONFLICT DO NOTHING;


--
-- TOC entry 5307 (class 0 OID 28214)
-- Dependencies: 245
-- Data for Name: rattacher; Type: TABLE DATA; Schema: document; Owner: postgres
--



--
-- TOC entry 5336 (class 0 OID 46418)
-- Dependencies: 296
-- Data for Name: remplir; Type: TABLE DATA; Schema: document; Owner: postgres
--

INSERT INTO document.remplir VALUES ('a1b2c3d4-e5f6-7890-ab12-c3d4e5f67890', '5190615e-1101-7209-9932-7020bbd556f1', '3190615e-1101-7209-9932-7020bbd556f1', '2025-06-04', '2025-06-01', '2025-06-01', true, true, true, true, true) ON CONFLICT DO NOTHING;
INSERT INTO document.remplir VALUES ('b2c3d4e5-f678-90ab-12c3-d4e5f67890ab', '6130615e-1101-7209-9932-7020bbd556f2', '3190615e-1101-7209-9932-7020bbd556f2', '2025-06-04', '2025-06-01', '2025-06-01', true, false, true, false, true) ON CONFLICT DO NOTHING;
INSERT INTO document.remplir VALUES ('c3d4e5f6-7890-ab12-c3d4-e5f67890abcd', '6191615e-1101-7209-9932-7020bbd556f3', '3190615e-1101-7209-9932-7020bbd556f3', '2025-06-04', '2025-06-01', '2025-06-01', false, true, false, true, false) ON CONFLICT DO NOTHING;


--
-- TOC entry 5308 (class 0 OID 28224)
-- Dependencies: 246
-- Data for Name: respecter; Type: TABLE DATA; Schema: document; Owner: postgres
--

INSERT INTO document.respecter VALUES ('97d314f5-84ad-47cd-874d-9c55f0e45790', '6290615e-1101-7209-9932-7020bbd556f1') ON CONFLICT DO NOTHING;
INSERT INTO document.respecter VALUES ('bfee750d-9b8c-4475-9373-08eff9b3ccb7', '6290615e-1101-7209-9932-7020bbd556f2') ON CONFLICT DO NOTHING;
INSERT INTO document.respecter VALUES ('0e7cea07-d09e-4f79-be6c-6dd27aecbce6', '17ee6932-2fcd-4b93-9c4c-0a4dbf659bff') ON CONFLICT DO NOTHING;


--
-- TOC entry 5309 (class 0 OID 28229)
-- Dependencies: 247
-- Data for Name: ressources; Type: TABLE DATA; Schema: document; Owner: postgres
--

INSERT INTO document.ressources VALUES ('6190615e-1101-7209-9932-7020bbd556f1', 'Paracetamol', 'Paracetamol', true, '2022-01-01', '2022-01-02', 4, 20, 1200, 2000, 'Boite', 'f190615e-1101-7209-9932-7020bbd556f1') ON CONFLICT DO NOTHING;
INSERT INTO document.ressources VALUES ('6190615e-1101-7209-9932-7020bbd556f2', 'Cartouche d''encre', 'Cartouche d''encre', true, '2022-01-01', '2022-01-02', 4, 10, 2000, 3000, 'Boite', 'f190615e-1101-7209-9932-7020bbd556f2') ON CONFLICT DO NOTHING;
INSERT INTO document.ressources VALUES ('6190615e-1101-7209-9932-7020bbd556f3', 'Scanner', 'Scanner', true, '2022-01-01', '2022-01-02', 4, 2, 12000, 20000, 'Packs', 'f190615e-1101-7209-9932-7020bbd556f2') ON CONFLICT DO NOTHING;
INSERT INTO document.ressources VALUES ('6190615e-1101-7209-9932-7020bbd556f4', 'Imprimante', 'Imprimante', true, '2022-01-01', '2022-01-02', 4, 2, 12000, 20000, 'Packs', 'f190615e-1101-7209-9932-7020bbd556f2') ON CONFLICT DO NOTHING;
INSERT INTO document.ressources VALUES ('6190615e-1101-7209-9932-7020bbd556f5', 'Souris', 'Souris', true, '2022-01-01', '2022-01-02', 40, 12, 25, 20, 'Packs', 'f190615e-1101-7209-9932-7020bbd556f2') ON CONFLICT DO NOTHING;
INSERT INTO document.ressources VALUES ('6190615e-1101-7209-9932-7020bbd556f6', 'Stylet', 'Stylet', true, '2022-01-01', '2022-01-02', 24, 9, 20, 20000, 'Packs', 'f190615e-1101-7209-9932-7020bbd556f2') ON CONFLICT DO NOTHING;
INSERT INTO document.ressources VALUES ('6190615e-1101-7209-9932-7020bbd556f7', 'Doliprane', 'Doliprane', true, '2022-01-01', '2022-01-02', 90, 40, 1200, 2000, 'Boite', 'f190615e-1101-7209-9932-7020bbd556f1') ON CONFLICT DO NOTHING;
INSERT INTO document.ressources VALUES ('6190615e-1101-7209-9932-7020bbd556f8', 'Pommade', 'Pommade', true, '2022-01-01', '2022-01-02', 20, 20, 200, 210, 'Litre', 'f190615e-1101-7209-9932-7020bbd556f1') ON CONFLICT DO NOTHING;
INSERT INTO document.ressources VALUES ('6190615e-1101-7209-9932-7020bbd556f9', 'Seringue', 'Seringue', true, '2022-01-01', '2022-01-02', 42, 20, 100, 200, 'Boite', 'f190615e-1101-7209-9932-7020bbd556f3') ON CONFLICT DO NOTHING;
INSERT INTO document.ressources VALUES ('6190615e-1101-7209-9932-7020bbd556f0', 'Perfuseur', 'Perfuseur', true, '2022-01-01', '2022-01-02', 5, 20, 90, 230, 'Boite', 'f190615e-1101-7209-9932-7020bbd556f3') ON CONFLICT DO NOTHING;
INSERT INTO document.ressources VALUES ('5f516ee3-b23d-4cf1-8d55-e301764e0501', 'Gants', 'Gants', true, '2022-01-01', '2022-01-02', 4, 20, 1200, 2000, 'Boite', 'f190615e-1101-7209-9932-7020bbd556f3') ON CONFLICT DO NOTHING;
INSERT INTO document.ressources VALUES ('5f516ee3-b23d-4cf1-8d55-e301764e0502', 'Masque', 'Masque', true, '2022-01-01', '2022-01-02', 4, 20, 1200, 2000, 'Boite', 'f190615e-1101-7209-9932-7020bbd556f3') ON CONFLICT DO NOTHING;
INSERT INTO document.ressources VALUES ('5f516ee3-b23d-4cf1-8d55-e301764e0503', 'Gel Hydroalcoolique', 'Gel Hydroalcoolique', true, '2022-01-01', '2022-01-02', 54, 200, 1200, 2000, 'Litre', 'f190615e-1101-7209-9932-7020bbd556f3') ON CONFLICT DO NOTHING;
INSERT INTO document.ressources VALUES ('5f516ee3-b23d-4cf1-8d55-e301764e0504', 'Coton', 'Coton', true, '2022-01-01', '2022-01-02', 44, 230, 1200, 2000, 'Boite', 'f190615e-1101-7209-9932-7020bbd556f3') ON CONFLICT DO NOTHING;
INSERT INTO document.ressources VALUES ('5f516ee3-b23d-4cf1-8d55-e301764e0505', 'Papier', 'Papier', true, '2022-01-01', '2022-01-02', 24, 20, 1200, 2000, 'Packs', 'f190615e-1101-7209-9932-7020bbd556f4') ON CONFLICT DO NOTHING;
INSERT INTO document.ressources VALUES ('5f516ee3-b23d-4cf1-8d55-e301764e0506', 'Encre', 'Encre', true, '2022-01-01', '2022-01-02', 4, 20, 1200, 2000, 'Boite', 'f190615e-1101-7209-9932-7020bbd556f4') ON CONFLICT DO NOTHING;
INSERT INTO document.ressources VALUES ('5f516ee3-b23d-4cf1-8d55-e301764e0507', 'Toner', 'Toner', true, '2022-01-01', '2022-01-02', 4, 20, 1200, 2000, 'Boite', 'f190615e-1101-7209-9932-7020bbd556f4') ON CONFLICT DO NOTHING;
INSERT INTO document.ressources VALUES ('5f516ee3-b23d-4cf1-8d55-e301764e0508', 'Cahier', 'Cahier', true, '2022-01-01', '2022-01-02', 4, 20, 1200, 2000, 'Packs', 'f190615e-1101-7209-9932-7020bbd556f4') ON CONFLICT DO NOTHING;


--
-- TOC entry 5310 (class 0 OID 28234)
-- Dependencies: 248
-- Data for Name: ressourcespromotions; Type: TABLE DATA; Schema: document; Owner: postgres
--

INSERT INTO document.ressourcespromotions VALUES ('1979bd79-f81b-498b-b247-e7b9bbb3f600', '6190615e-1101-7209-9932-7020bbd556f6') ON CONFLICT DO NOTHING;
INSERT INTO document.ressourcespromotions VALUES ('1979bd79-f81b-498b-b247-e7b9bbb3f601', '6190615e-1101-7209-9932-7020bbd556f4') ON CONFLICT DO NOTHING;
INSERT INTO document.ressourcespromotions VALUES ('1979bd79-f81b-498b-b247-e7b9bbb3f602', '6190615e-1101-7209-9932-7020bbd556f2') ON CONFLICT DO NOTHING;


--
-- TOC entry 5311 (class 0 OID 28239)
-- Dependencies: 249
-- Data for Name: roles; Type: TABLE DATA; Schema: document; Owner: postgres
--

INSERT INTO document.roles VALUES ('5190615e-1101-7209-9932-7020bbd556f1', 'vendeur', 'personnel au contact du client', true, '2022-01-01', '2022-01-02') ON CONFLICT DO NOTHING;
INSERT INTO document.roles VALUES ('6191615e-1101-7209-9932-7020bbd556f3', 'marcheur', 'commercial sur le terrain', true, '2022-01-01', '2022-01-02') ON CONFLICT DO NOTHING;
INSERT INTO document.roles VALUES ('6130615e-1101-7209-9932-7020bbd556f2', 'traiteur78', 'Personnel administratif', true, '2022-01-01', '2022-01-02') ON CONFLICT DO NOTHING;


--
-- TOC entry 5312 (class 0 OID 28244)
-- Dependencies: 250
-- Data for Name: sapplique; Type: TABLE DATA; Schema: document; Owner: postgres
--

INSERT INTO document.sapplique VALUES ('f190615e-1101-7209-9932-7020bbd556f1', 'a97eb081-72f6-4617-ba29-64dc8593a9ff') ON CONFLICT DO NOTHING;
INSERT INTO document.sapplique VALUES ('f190615e-1101-7209-9932-7020bbd556f2', 'a97eb081-62f6-4617-ba49-64dc8593a9ff') ON CONFLICT DO NOTHING;
INSERT INTO document.sapplique VALUES ('f190615e-1101-7209-9932-7020bbd556f3', 'a97eb081-62f6-4617-ba29-65dc8593a9ff') ON CONFLICT DO NOTHING;
INSERT INTO document.sapplique VALUES ('f190615e-1101-7209-9932-7020bbd556f1', 'a97eb081-62f6-4617-ba49-64dc8593a9ff') ON CONFLICT DO NOTHING;
INSERT INTO document.sapplique VALUES ('f190615e-1101-7209-9932-7020bbd556f2', 'a97eb081-72f6-4617-ba29-64dc8593a9ff') ON CONFLICT DO NOTHING;
INSERT INTO document.sapplique VALUES ('f190615e-1101-7209-9932-7020bbd556f3', 'a97eb081-62f6-4617-ba49-64dc8593a9ff') ON CONFLICT DO NOTHING;


--
-- TOC entry 5313 (class 0 OID 28249)
-- Dependencies: 251
-- Data for Name: services; Type: TABLE DATA; Schema: document; Owner: postgres
--

INSERT INTO document.services VALUES ('22f815cf-3164-47d9-b2cd-d1b7aa0aad3c', 'bien', 'Pharmacieee2', 'douala', true, NULL, NULL, 'azer5r25IR', NULL) ON CONFLICT DO NOTHING;
INSERT INTO document.services VALUES ('c469b798-c841-450e-b6ab-e5f1d96c7560', 'bien', 'Laboratoire78962225', 'douala', true, NULL, NULL, 'OS74', NULL) ON CONFLICT DO NOTHING;
INSERT INTO document.services VALUES ('ecc53b2b-c398-46b9-b5a8-ef18c0198bff', 'bien', 'Pharmacie269854', 'douala-garoua', true, NULL, NULL, 'SNK3', NULL) ON CONFLICT DO NOTHING;
INSERT INTO document.services VALUES ('2190615e-1101-7209-9932-7020bbd556f3', 'bien', 'Cinema269', 'douala', true, '2000-03-07', NULL, 'S3', NULL) ON CONFLICT DO NOTHING;
INSERT INTO document.services VALUES ('2190615e-1101-7209-9932-7020bbd556f1', 'bien', 'Consultation37', 'douala', true, '1972-06-12', NULL, 'S1', NULL) ON CONFLICT DO NOTHING;
INSERT INTO document.services VALUES ('2190615e-1101-7209-9932-7020bbd556f2', 'bien', 'Laboratoire78', 'douala', true, '1990-08-06', NULL, 'S2', NULL) ON CONFLICT DO NOTHING;


--
-- TOC entry 5314 (class 0 OID 28254)
-- Dependencies: 252
-- Data for Name: suivre; Type: TABLE DATA; Schema: document; Owner: postgres
--

INSERT INTO document.suivre VALUES ('0190615e-1101-7209-9932-7020bbd556f1', '6290615e-1101-7209-9932-7020bbd556f1') ON CONFLICT DO NOTHING;
INSERT INTO document.suivre VALUES ('0190615e-1101-7209-9932-7020bbd556f2', '6290615e-1101-7209-9932-7020bbd556f2') ON CONFLICT DO NOTHING;
INSERT INTO document.suivre VALUES ('0190615e-1101-7209-9932-7020bbd556f3', '17ee6932-2fcd-4b93-9c4c-0a4dbf659bff') ON CONFLICT DO NOTHING;
INSERT INTO document.suivre VALUES ('0190615e-1101-7209-9932-7020bbd556f4', '77b8577f-6d26-4376-af30-a3c8f75a9194') ON CONFLICT DO NOTHING;
INSERT INTO document.suivre VALUES ('0190615e-1101-7209-9932-7020bbd556f5', '6290615e-1101-7209-9932-7020bbd556f5') ON CONFLICT DO NOTHING;
INSERT INTO document.suivre VALUES ('0190615e-1101-7209-9932-7020bbd556f6', '6290615e-1101-7209-9932-7020bbd556f6') ON CONFLICT DO NOTHING;
INSERT INTO document.suivre VALUES ('0190615e-1101-7209-9932-7020bbd556f7', '6290615e-1101-7209-9932-7020bbd556f7') ON CONFLICT DO NOTHING;
INSERT INTO document.suivre VALUES ('0190615e-1101-7209-9932-7020bbd556f8', '6290615e-1101-7209-9932-7020bbd556f8') ON CONFLICT DO NOTHING;


--
-- TOC entry 5315 (class 0 OID 28259)
-- Dependencies: 253
-- Data for Name: tickets; Type: TABLE DATA; Schema: document; Owner: postgres
--

INSERT INTO document.tickets VALUES ('be8ef47f-d5a2-4254-cd79-af860672553e', 'rr15', '2022-03-02', '2022-10-02', '11111111-aaaa-bbbb-cccc-111111111111', NULL) ON CONFLICT DO NOTHING;
INSERT INTO document.tickets VALUES ('be8ef47f-d5a2-4254-cd79-af860672554e', 'rr10', '2002-01-02', '2022-11-02', '1e8b6f56-7525-45bf-aa7b-96c0a9ae4198', NULL) ON CONFLICT DO NOTHING;
INSERT INTO document.tickets VALUES ('be8ef47f-d5a2-4254-cd79-af860672556e', 'rr12', '2023-10-25', '2024-03-02', '33333333-cccc-dddd-eeee-333333333333', NULL) ON CONFLICT DO NOTHING;
INSERT INTO document.tickets VALUES ('be8ef47f-d5a2-4254-cd79-af860672557e', 'rr16', '2020-11-15', '2024-05-02', '3b277afa-98fc-4b0f-9b16-1d2fb4aa8ec6', NULL) ON CONFLICT DO NOTHING;
INSERT INTO document.tickets VALUES ('be8ef47f-d5a2-4254-cd79-af860672558e', 'rr14', '2022-01-02', '2023-03-02', '4390615e-1101-7209-9932-7020bbd556f3', NULL) ON CONFLICT DO NOTHING;
INSERT INTO document.tickets VALUES ('f4d5e6g7-b8c9-7d01-2e3f-456780a12bcd', 'TCKT004', '2025-08-08', '2025-08-08', '6b7894b5-1b07-487a-b8fe-d14ab61c1b2e', NULL) ON CONFLICT DO NOTHING;
INSERT INTO document.tickets VALUES ('g5e6f7h8-c9d0-8e12-3f4g-567801b23cde', 'TCKT005', '2025-07-29', '2025-08-03', '44444444-dddd-eeee-ffff-444444444444', 'En attente') ON CONFLICT DO NOTHING;
INSERT INTO document.tickets VALUES ('e3c4d5f6-a7b8-6c90-1d2e-3456f78901ab', 'TCKT003', '2025-08-01', '2025-08-06', '8a9b710a-a7fb-44ca-9587-b427b163cdab', 'Fermé') ON CONFLICT DO NOTHING;
INSERT INTO document.tickets VALUES ('c1a2b3d4-e5f6-4a78-9b0c-1234d56789ef', 'TCKT001', '2025-08-08', '2025-08-08', '4390615e-1101-7209-9932-7020bbd556f2', 'Ouvert') ON CONFLICT DO NOTHING;
INSERT INTO document.tickets VALUES ('d2b3c4e5-f6a7-5b89-0c1d-2345e67890fa', 'TCKT002', '2025-08-05', '2025-08-08', '4390615e-1101-7209-9932-7020bbd556f1', 'En cours') ON CONFLICT DO NOTHING;
INSERT INTO document.tickets VALUES ('be8ef47f-d5a2-4254-cd79-af860672555e', 'rr11', '2014-01-12', '2022-02-02', '22222222-bbbb-cccc-dddd-222222222222', NULL) ON CONFLICT DO NOTHING;


--
-- TOC entry 5316 (class 0 OID 28264)
-- Dependencies: 254
-- Data for Name: ticketsfilesattentes; Type: TABLE DATA; Schema: document; Owner: postgres
--

INSERT INTO document.ticketsfilesattentes VALUES ('be8ef47f-d7a2-4254-cd79-af860672553e', true, '2024-11-04', 'be8ef47f-d5a2-4254-cd79-af860672553e', 'f190615e-1101-7209-9932-7020bbd556f1') ON CONFLICT DO NOTHING;
INSERT INTO document.ticketsfilesattentes VALUES ('be8ef47f-d7a2-4254-cd79-af860672554e', false, '2022-12-04', 'be8ef47f-d5a2-4254-cd79-af860672554e', 'f190615e-1101-7209-9932-7020bbd556f2') ON CONFLICT DO NOTHING;
INSERT INTO document.ticketsfilesattentes VALUES ('be8ef47f-d7a2-4254-cd79-af860672555e', false, '2020-11-04', 'be8ef47f-d5a2-4254-cd79-af860672558e', 'f190615e-1101-7209-9932-7020bbd556f3') ON CONFLICT DO NOTHING;
INSERT INTO document.ticketsfilesattentes VALUES ('be8ef47f-d7a2-4254-cd79-af860672556e', false, '2021-04-04', 'be8ef47f-d5a2-4254-cd79-af860672556e', 'f190615e-1101-7209-9932-7020bbd556f3') ON CONFLICT DO NOTHING;
INSERT INTO document.ticketsfilesattentes VALUES ('be8ef47f-d7a2-4254-cd79-af860672557e', true, '2023-02-04', 'be8ef47f-d5a2-4254-cd79-af860672555e', 'f190615e-1101-7209-9932-7020bbd556f2') ON CONFLICT DO NOTHING;
INSERT INTO document.ticketsfilesattentes VALUES ('be8ef47f-d7a2-4254-cd79-af860672558e', false, '2022-01-04', 'be8ef47f-d5a2-4254-cd79-af860672557e', 'f190615e-1101-7209-9932-7020bbd556f3') ON CONFLICT DO NOTHING;


--
-- TOC entry 5317 (class 0 OID 28269)
-- Dependencies: 255
-- Data for Name: traiter; Type: TABLE DATA; Schema: document; Owner: postgres
--

INSERT INTO document.traiter VALUES ('0190615e-1101-7209-9932-7020bbd556f1', '3190615e-1101-7209-9932-7020bbd556f1') ON CONFLICT DO NOTHING;
INSERT INTO document.traiter VALUES ('0190615e-1101-7209-9932-7020bbd556f1', '3190615e-1101-7209-9932-7020bbd556f2') ON CONFLICT DO NOTHING;
INSERT INTO document.traiter VALUES ('0190615e-1101-7209-9932-7020bbd556f2', '3190615e-1101-7209-9932-7020bbd556f2') ON CONFLICT DO NOTHING;


--
-- TOC entry 5328 (class 0 OID 28727)
-- Dependencies: 266
-- Data for Name: utilisateurs; Type: TABLE DATA; Schema: document; Owner: postgres
--

INSERT INTO document.utilisateurs VALUES ('0190615e-1101-7209-9932-7020bbd55714', 'sdvfdbrfg', 'sdvdfbfg', 'false', NULL, NULL, 'f6jkl74e-5e6f-4e90-596a-3b2a1e0d9c8a', 'p6ghi7oe-5o6p-483a-3940-3f2g1h0i9j8k') ON CONFLICT DO NOTHING;
INSERT INTO document.utilisateurs VALUES ('k1yzabc2j-0j1k-43e5-8415-8a7b6c5d4e3f', 'admin', 'password', 'true', '2024-01-01', '2024-01-01', 'g7mno85f-6f7g-4fa1-4859-2a1e0d9c8a7b', 'q7hij8pf-6p7q-494b-2859-2g1h0i9j8k7l') ON CONFLICT DO NOTHING;
INSERT INTO document.utilisateurs VALUES ('l2bcd3ka-1k2l-44f6-9304-7b6c5d4e3f2g', 'user1', 'password', 'true', '2024-01-01', '2024-01-01', 'h8pqr96g-7g8h-40b2-5748-1e0d9c8a7b6c', 'r8ijk9qg-7q8r-4a5c-1768-1h0i9j8k7l6m') ON CONFLICT DO NOTHING;
INSERT INTO document.utilisateurs VALUES ('m3cde4lb-2l3m-4507-0213-6c5d4e3f2g1h', 'user2', 'password', 'true', '2024-01-01', '2024-01-01', 'i9stu07h-8h9i-41c3-6637-0d9c8a7b6c5d', 's9jkl0rh-8r9s-4b6d-0677-0i9j8k7l6m5n') ON CONFLICT DO NOTHING;
INSERT INTO document.utilisateurs VALUES ('n4efg5mc-3m4n-4618-1122-5d4e3f2g1h0i', 'user3', 'password', 'true', '2024-01-01', '2024-01-01', 'j0vwx18i-9i0j-42d4-7526-9c8a7b6c5d4e', 't0klm1si-9s0t-4c7e-7586-9j8k7l6m5n4o') ON CONFLICT DO NOTHING;
INSERT INTO document.utilisateurs VALUES ('o5fgh6nd-4n5o-4729-2031-4e3f2g1h0i9j', 'user4', 'password', 'true', '2024-01-01', '2024-01-01', 'f6jkl74e-5e6f-4e90-596a-3b2a1e0d9c8a', 'p6ghi7oe-5o6p-483a-3940-3f2g1h779j8k') ON CONFLICT DO NOTHING;


--
-- TOC entry 5318 (class 0 OID 28274)
-- Dependencies: 256
-- Data for Name: validations; Type: TABLE DATA; Schema: document; Owner: postgres
--

INSERT INTO document.validations VALUES ('1901bd80-f71b-498b-b247-e7b9bbb3f602', 'o78', 'attente', '2022-03-02', '2022-10-02', 'multiple', 14, 'prioritaire', '5190615e-1101-7209-9932-7020bbd556f1', 'en cours') ON CONFLICT DO NOTHING;
INSERT INTO document.validations VALUES ('1901bd80-f71b-498b-b247-e7b9bbb3f601', 'o89', 'suppression', '2002-01-02', '2022-11-02', 'multiple', 2, 'prioritaire', '6130615e-1101-7209-9932-7020bbd556f2', 'Resultat mise à jour') ON CONFLICT DO NOTHING;
INSERT INTO document.validations VALUES ('1901bd80-f71b-498b-b247-e7b9bbb3f604', 'o84', 'traitement', '2020-11-15', '2024-05-02', 'multiple', 40, 'prioritaire', '6191615e-1101-7209-9932-7020bbd556f3', 'en cours') ON CONFLICT DO NOTHING;
INSERT INTO document.validations VALUES ('1901bd80-f71b-498b-b247-e7b9bbb3f603', 'o82', 'traitement', '2023-10-25', '2022-10-02', 'Unitaire', 22, 'Transmission', '5190615e-1101-7209-9932-7020bbd556f1', 'Consultation38') ON CONFLICT DO NOTHING;
INSERT INTO document.validations VALUES ('ddd8446a-be11-457c-a7c8-436467b09a79', 'o8278', 'true', '2025-08-07', '2022-10-02', 'Majoritaire', 0, 'Traitement', '6191615e-1101-7209-9932-7020bbd556f3', 'Consultation38') ON CONFLICT DO NOTHING;
INSERT INTO document.validations VALUES ('1901bd80-f71b-498b-b247-e7b9bbb3f600', 'o85', 'traitement', '2014-01-12', '2022-02-02', 'multiple', 5, 'rechargeable', '5190615e-1101-7209-9932-7020bbd556f1', 'Mise en attente') ON CONFLICT DO NOTHING;


--
-- TOC entry 5319 (class 0 OID 28279)
-- Dependencies: 257
-- Data for Name: violer; Type: TABLE DATA; Schema: document; Owner: postgres
--

INSERT INTO document.violer VALUES ('97d314f5-84ad-47cd-874d-9c55f0e45790', '6290615e-1101-7209-9932-7020bbd556f6') ON CONFLICT DO NOTHING;
INSERT INTO document.violer VALUES ('bfee750d-9b8c-4475-9373-08eff9b3ccb7', '6290615e-1101-7209-9932-7020bbd556f7') ON CONFLICT DO NOTHING;
INSERT INTO document.violer VALUES ('0e7cea07-d09e-4f79-be6c-6dd27aecbce6', '6290615e-1101-7209-9932-7020bbd556f8') ON CONFLICT DO NOTHING;


--
-- TOC entry 5025 (class 2606 OID 28683)
-- Name: actions actions_pkey; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.actions
    ADD CONSTRAINT actions_pkey PRIMARY KEY (id);


--
-- TOC entry 5055 (class 2606 OID 46362)
-- Name: distributeurs distributeurs_pkey; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.distributeurs
    ADD CONSTRAINT distributeurs_pkey PRIMARY KEY (distributeurs_id);


--
-- TOC entry 5037 (class 2606 OID 28719)
-- Name: elements elements_pkey; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.elements
    ADD CONSTRAINT elements_pkey PRIMARY KEY (id);


--
-- TOC entry 5027 (class 2606 OID 28690)
-- Name: elementsbases elementsbases_pkey; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.elementsbases
    ADD CONSTRAINT elementsbases_pkey PRIMARY KEY (id);


--
-- TOC entry 5049 (class 2606 OID 28752)
-- Name: groupes groupes_pkey; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.groupes
    ADD CONSTRAINT groupes_pkey PRIMARY KEY (id);


--
-- TOC entry 5031 (class 2606 OID 28702)
-- Name: langues langues_pkey; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.langues
    ADD CONSTRAINT langues_pkey PRIMARY KEY (id);


--
-- TOC entry 5041 (class 2606 OID 28726)
-- Name: menus menus_pkey; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.menus
    ADD CONSTRAINT menus_pkey PRIMARY KEY (id);


--
-- TOC entry 5045 (class 2606 OID 28740)
-- Name: organisations organisations_pkey; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.organisations
    ADD CONSTRAINT organisations_pkey PRIMARY KEY (id);


--
-- TOC entry 5057 (class 2606 OID 46410)
-- Name: personnels personnels_pkey; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.personnels
    ADD CONSTRAINT personnels_pkey PRIMARY KEY (personnels_id);


--
-- TOC entry 5053 (class 2606 OID 46350)
-- Name: personnesmorales personnesmorales_pkey; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.personnesmorales
    ADD CONSTRAINT personnesmorales_pkey PRIMARY KEY (personnesmorales_id);


--
-- TOC entry 5051 (class 2606 OID 46338)
-- Name: personnesphysique personnesphysique_pkey; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.personnesphysique
    ADD CONSTRAINT personnesphysique_pkey PRIMARY KEY (personnesphysique_id);


--
-- TOC entry 5029 (class 2606 OID 28837)
-- Name: actionslangues pk_actionslangues; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.actionslangues
    ADD CONSTRAINT pk_actionslangues PRIMARY KEY (langues_id, actions_id);


--
-- TOC entry 4935 (class 2606 OID 28285)
-- Name: associer pk_associer; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.associer
    ADD CONSTRAINT pk_associer PRIMARY KEY (attributs_id, categories_id);


--
-- TOC entry 4937 (class 2606 OID 28287)
-- Name: attributs pk_attributs; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.attributs
    ADD CONSTRAINT pk_attributs PRIMARY KEY (id);


--
-- TOC entry 4939 (class 2606 OID 28289)
-- Name: caisses pk_caisses; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.caisses
    ADD CONSTRAINT pk_caisses PRIMARY KEY (id);


--
-- TOC entry 4941 (class 2606 OID 28291)
-- Name: categories pk_categories; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.categories
    ADD CONSTRAINT pk_categories PRIMARY KEY (id);


--
-- TOC entry 5039 (class 2606 OID 28791)
-- Name: elements pk_composite_elements; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.elements
    ADD CONSTRAINT pk_composite_elements UNIQUE (menus_id, elementsbases_id);


--
-- TOC entry 4943 (class 2606 OID 28293)
-- Name: comptes pk_comptes; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.comptes
    ADD CONSTRAINT pk_comptes PRIMARY KEY (id);


--
-- TOC entry 4945 (class 2606 OID 28295)
-- Name: concerner pk_concerner; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.concerner
    ADD CONSTRAINT pk_concerner PRIMARY KEY (precomouvementsqtes_id, distributeurs_id);


--
-- TOC entry 4947 (class 2606 OID 28297)
-- Name: constituer pk_constituer; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.constituer
    ADD CONSTRAINT pk_constituer PRIMARY KEY (attributs_id, documents_id);


--
-- TOC entry 4949 (class 2606 OID 28299)
-- Name: deltasoldes pk_deltasoldes; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.deltasoldes
    ADD CONSTRAINT pk_deltasoldes PRIMARY KEY (id);


--
-- TOC entry 4951 (class 2606 OID 28301)
-- Name: docetats pk_docetats; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.docetats
    ADD CONSTRAINT pk_docetats PRIMARY KEY (id);


--
-- TOC entry 4953 (class 2606 OID 28303)
-- Name: docetats_predecesseurs pk_docetats_predecesseurs; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.docetats_predecesseurs
    ADD CONSTRAINT pk_docetats_predecesseurs PRIMARY KEY (docetats_id, predecesseur_id);


--
-- TOC entry 4955 (class 2606 OID 28305)
-- Name: documents pk_documents; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.documents
    ADD CONSTRAINT pk_documents PRIMARY KEY (id);


--
-- TOC entry 4957 (class 2606 OID 28674)
-- Name: documentspromotions pk_documentspromotions; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.documentspromotions
    ADD CONSTRAINT pk_documentspromotions PRIMARY KEY (documents_id, promotions_id);


--
-- TOC entry 5033 (class 2606 OID 28825)
-- Name: elementsbaselanques pk_elementsbaseslangues; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.elementsbaselanques
    ADD CONSTRAINT pk_elementsbaseslangues PRIMARY KEY (langues_id, elementsbases_id);


--
-- TOC entry 5035 (class 2606 OID 28813)
-- Name: elementslangues pk_elementslangues; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.elementslangues
    ADD CONSTRAINT pk_elementslangues PRIMARY KEY (langues_id, elements_id);


--
-- TOC entry 4959 (class 2606 OID 28307)
-- Name: etapes pk_etapes; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.etapes
    ADD CONSTRAINT pk_etapes PRIMARY KEY (id);


--
-- TOC entry 4961 (class 2606 OID 28309)
-- Name: etats pk_etats; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.etats
    ADD CONSTRAINT pk_etats PRIMARY KEY (id);


--
-- TOC entry 4963 (class 2606 OID 28311)
-- Name: exemplaires pk_exemplaires; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.exemplaires
    ADD CONSTRAINT pk_exemplaires PRIMARY KEY (id);


--
-- TOC entry 4965 (class 2606 OID 28313)
-- Name: familles pk_familles; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.familles
    ADD CONSTRAINT pk_familles PRIMARY KEY (id);


--
-- TOC entry 4967 (class 2606 OID 28315)
-- Name: famillespromotions pk_famillespromotions; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.famillespromotions
    ADD CONSTRAINT pk_famillespromotions PRIMARY KEY (familles_id, promotions_id);


--
-- TOC entry 4969 (class 2606 OID 28317)
-- Name: filesattentes pk_filesattentes; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.filesattentes
    ADD CONSTRAINT pk_filesattentes PRIMARY KEY (id);


--
-- TOC entry 4973 (class 2606 OID 28319)
-- Name: jouerroles pk_jouerroles; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.jouerroles
    ADD CONSTRAINT pk_jouerroles PRIMARY KEY (id);


--
-- TOC entry 4975 (class 2606 OID 28321)
-- Name: missions pk_missions; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.missions
    ADD CONSTRAINT pk_missions PRIMARY KEY (id);


--
-- TOC entry 4977 (class 2606 OID 28323)
-- Name: mouvementcaisses pk_mouvementcaisses; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.mouvementcaisses
    ADD CONSTRAINT pk_mouvementcaisses PRIMARY KEY (id);


--
-- TOC entry 4979 (class 2606 OID 28325)
-- Name: mouvements pk_mouvements; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.mouvements
    ADD CONSTRAINT pk_mouvements PRIMARY KEY (id);


--
-- TOC entry 4981 (class 2606 OID 28327)
-- Name: ordreetats pk_ordreetats; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.ordreetats
    ADD CONSTRAINT pk_ordreetats PRIMARY KEY (id);


--
-- TOC entry 5047 (class 2606 OID 28769)
-- Name: organiser pk_organiser; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.organiser
    ADD CONSTRAINT pk_organiser PRIMARY KEY (organisations_id, utilisateurs_id);


--
-- TOC entry 4983 (class 2606 OID 28329)
-- Name: parcours pk_parcours; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.parcours
    ADD CONSTRAINT pk_parcours PRIMARY KEY (id);


--
-- TOC entry 4985 (class 2606 OID 28333)
-- Name: personnes pk_personnes; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.personnes
    ADD CONSTRAINT pk_personnes PRIMARY KEY (id);


--
-- TOC entry 4987 (class 2606 OID 28337)
-- Name: precomouvements pk_precomouvements; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.precomouvements
    ADD CONSTRAINT pk_precomouvements PRIMARY KEY (id);


--
-- TOC entry 4989 (class 2606 OID 28339)
-- Name: precomouvementsqtes pk_precomouvementsqtes; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.precomouvementsqtes
    ADD CONSTRAINT pk_precomouvementsqtes PRIMARY KEY (id);


--
-- TOC entry 4991 (class 2606 OID 28341)
-- Name: promotions pk_promotions; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.promotions
    ADD CONSTRAINT pk_promotions PRIMARY KEY (id);


--
-- TOC entry 4995 (class 2606 OID 28343)
-- Name: rattacher pk_rattacher; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.rattacher
    ADD CONSTRAINT pk_rattacher PRIMARY KEY (personnes_id, rattacher_id);


--
-- TOC entry 4997 (class 2606 OID 28347)
-- Name: respecter pk_respecter; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.respecter
    ADD CONSTRAINT pk_respecter PRIMARY KEY (mouvements_id, precomouvements_id);


--
-- TOC entry 4999 (class 2606 OID 28349)
-- Name: ressources pk_ressources; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.ressources
    ADD CONSTRAINT pk_ressources PRIMARY KEY (id);


--
-- TOC entry 5001 (class 2606 OID 28351)
-- Name: ressourcespromotions pk_ressourcespromotions; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.ressourcespromotions
    ADD CONSTRAINT pk_ressourcespromotions PRIMARY KEY (ressources_id, promotions_id);


--
-- TOC entry 5003 (class 2606 OID 28353)
-- Name: roles pk_roles; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.roles
    ADD CONSTRAINT pk_roles PRIMARY KEY (id);


--
-- TOC entry 5005 (class 2606 OID 28355)
-- Name: sapplique pk_sapplique; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.sapplique
    ADD CONSTRAINT pk_sapplique PRIMARY KEY (familles_id, precomouvementsqtes_id);


--
-- TOC entry 5007 (class 2606 OID 28357)
-- Name: services pk_services; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.services
    ADD CONSTRAINT pk_services PRIMARY KEY (id);


--
-- TOC entry 5013 (class 2606 OID 28359)
-- Name: suivre pk_suivre; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.suivre
    ADD CONSTRAINT pk_suivre PRIMARY KEY (documents_id, precomouvements_id);


--
-- TOC entry 5015 (class 2606 OID 28361)
-- Name: tickets pk_tickets; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.tickets
    ADD CONSTRAINT pk_tickets PRIMARY KEY (id);


--
-- TOC entry 5017 (class 2606 OID 28363)
-- Name: ticketsfilesattentes pk_ticketsfilesattentes; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.ticketsfilesattentes
    ADD CONSTRAINT pk_ticketsfilesattentes PRIMARY KEY (id);


--
-- TOC entry 5019 (class 2606 OID 28676)
-- Name: traiter pk_traiter; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.traiter
    ADD CONSTRAINT pk_traiter PRIMARY KEY (missions_id, documents_id);


--
-- TOC entry 5021 (class 2606 OID 28365)
-- Name: validations pk_validations; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.validations
    ADD CONSTRAINT pk_validations PRIMARY KEY (id);


--
-- TOC entry 5023 (class 2606 OID 28367)
-- Name: violer pk_violer; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.violer
    ADD CONSTRAINT pk_violer PRIMARY KEY (precomouvements_id, mouvements_id);


--
-- TOC entry 5059 (class 2606 OID 46424)
-- Name: remplir remplir_pkey; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.remplir
    ADD CONSTRAINT remplir_pkey PRIMARY KEY (id);


--
-- TOC entry 4971 (class 2606 OID 28369)
-- Name: filesattentes uc_filesattentes_services; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.filesattentes
    ADD CONSTRAINT uc_filesattentes_services UNIQUE (services_id);


--
-- TOC entry 4993 (class 2606 OID 28371)
-- Name: promotions uc_promotions_codeunique; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.promotions
    ADD CONSTRAINT uc_promotions_codeunique UNIQUE (codeunique);


--
-- TOC entry 5009 (class 2606 OID 28373)
-- Name: services uc_services_codeunique; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.services
    ADD CONSTRAINT uc_services_codeunique UNIQUE (codeunique);


--
-- TOC entry 5011 (class 2606 OID 28375)
-- Name: services uc_services_filesattentes; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.services
    ADD CONSTRAINT uc_services_filesattentes UNIQUE (filesattentes_id);


--
-- TOC entry 5043 (class 2606 OID 28733)
-- Name: utilisateurs utilisateurs_pkey; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.utilisateurs
    ADD CONSTRAINT utilisateurs_pkey PRIMARY KEY (id);


--
-- TOC entry 5134 (class 2606 OID 46363)
-- Name: distributeurs distributeurs_id_fkey; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.distributeurs
    ADD CONSTRAINT distributeurs_id_fkey FOREIGN KEY (distributeurs_id) REFERENCES document.personnes(id);


--
-- TOC entry 5117 (class 2606 OID 28826)
-- Name: actionslangues fk_actions_actionslangues; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.actionslangues
    ADD CONSTRAINT fk_actions_actionslangues FOREIGN KEY (actions_id) REFERENCES document.actions(id);


--
-- TOC entry 5115 (class 2606 OID 28838)
-- Name: actions fk_actions_elementbases; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.actions
    ADD CONSTRAINT fk_actions_elementbases FOREIGN KEY (elementsbase_id) REFERENCES document.elementsbases(id);


--
-- TOC entry 5060 (class 2606 OID 28376)
-- Name: associer fk_associer_on_attributs; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.associer
    ADD CONSTRAINT fk_associer_on_attributs FOREIGN KEY (attributs_id) REFERENCES document.attributs(id);


--
-- TOC entry 5061 (class 2606 OID 28381)
-- Name: associer fk_associer_on_categories; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.associer
    ADD CONSTRAINT fk_associer_on_categories FOREIGN KEY (categories_id) REFERENCES document.categories(id);


--
-- TOC entry 5062 (class 2606 OID 28386)
-- Name: categories fk_categories_on_documents; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.categories
    ADD CONSTRAINT fk_categories_on_documents FOREIGN KEY (documents_id) REFERENCES document.documents(id);


--
-- TOC entry 5063 (class 2606 OID 28391)
-- Name: comptes fk_comptes_personnes; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.comptes
    ADD CONSTRAINT fk_comptes_personnes FOREIGN KEY (personnes_id) REFERENCES document.personnes(id);


--
-- TOC entry 5066 (class 2606 OID 28396)
-- Name: constituer fk_con_on_attributs_entity; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.constituer
    ADD CONSTRAINT fk_con_on_attributs_entity FOREIGN KEY (attributs_id) REFERENCES document.attributs(id);


--
-- TOC entry 5067 (class 2606 OID 28401)
-- Name: constituer fk_con_on_documents_entity; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.constituer
    ADD CONSTRAINT fk_con_on_documents_entity FOREIGN KEY (documents_id) REFERENCES document.documents(id);


--
-- TOC entry 5064 (class 2606 OID 28406)
-- Name: concerner fk_concerner_on_distributeurs_entity; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.concerner
    ADD CONSTRAINT fk_concerner_on_distributeurs_entity FOREIGN KEY (distributeurs_id) REFERENCES document.personnes(id);


--
-- TOC entry 5065 (class 2606 OID 28411)
-- Name: concerner fk_concerner_on_preco_mouvements_qtes_entity; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.concerner
    ADD CONSTRAINT fk_concerner_on_preco_mouvements_qtes_entity FOREIGN KEY (precomouvementsqtes_id) REFERENCES document.precomouvementsqtes(id);


--
-- TOC entry 5068 (class 2606 OID 28416)
-- Name: deltasoldes fk_deltasoldes_comptes; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.deltasoldes
    ADD CONSTRAINT fk_deltasoldes_comptes FOREIGN KEY (comptes_id) REFERENCES document.comptes(id);


--
-- TOC entry 5069 (class 2606 OID 28421)
-- Name: deltasoldes fk_deltasoldes_exemplaires; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.deltasoldes
    ADD CONSTRAINT fk_deltasoldes_exemplaires FOREIGN KEY (exemplaires_id) REFERENCES document.exemplaires(id);


--
-- TOC entry 5089 (class 2606 OID 28843)
-- Name: mouvements fk_distributeurs_mouvements; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.mouvements
    ADD CONSTRAINT fk_distributeurs_mouvements FOREIGN KEY (distributeurs_id) REFERENCES document.personnes(id);


--
-- TOC entry 5096 (class 2606 OID 28848)
-- Name: promotions fk_distributeurs_promotions; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.promotions
    ADD CONSTRAINT fk_distributeurs_promotions FOREIGN KEY (distributeurs_id) REFERENCES document.personnes(id);


--
-- TOC entry 5076 (class 2606 OID 28426)
-- Name: documentspromotions fk_doc_on_documents_entity; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.documentspromotions
    ADD CONSTRAINT fk_doc_on_documents_entity FOREIGN KEY (documents_id) REFERENCES document.documents(id);


--
-- TOC entry 5077 (class 2606 OID 28431)
-- Name: documentspromotions fk_doc_on_promotions_entity; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.documentspromotions
    ADD CONSTRAINT fk_doc_on_promotions_entity FOREIGN KEY (promotions_id) REFERENCES document.promotions(id);


--
-- TOC entry 5070 (class 2606 OID 28436)
-- Name: docetats fk_docetats_on_documents; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.docetats
    ADD CONSTRAINT fk_docetats_on_documents FOREIGN KEY (documents_id) REFERENCES document.documents(id);


--
-- TOC entry 5071 (class 2606 OID 28441)
-- Name: docetats fk_docetats_on_etapes; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.docetats
    ADD CONSTRAINT fk_docetats_on_etapes FOREIGN KEY (etapes_id) REFERENCES document.etapes(id);


--
-- TOC entry 5072 (class 2606 OID 28446)
-- Name: docetats fk_docetats_on_etats; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.docetats
    ADD CONSTRAINT fk_docetats_on_etats FOREIGN KEY (etats_id) REFERENCES document.etats(id);


--
-- TOC entry 5073 (class 2606 OID 28451)
-- Name: docetats fk_docetats_on_validations; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.docetats
    ADD CONSTRAINT fk_docetats_on_validations FOREIGN KEY (validations_id) REFERENCES document.validations(id);


--
-- TOC entry 5074 (class 2606 OID 28456)
-- Name: docetats_predecesseurs fk_docetats_predecesseurs_on_docetats; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.docetats_predecesseurs
    ADD CONSTRAINT fk_docetats_predecesseurs_on_docetats FOREIGN KEY (docetats_id) REFERENCES document.docetats(id);


--
-- TOC entry 5075 (class 2606 OID 28461)
-- Name: docetats_predecesseurs fk_docetats_predecesseurs_suivant_on_docetats; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.docetats_predecesseurs
    ADD CONSTRAINT fk_docetats_predecesseurs_suivant_on_docetats FOREIGN KEY (predecesseur_id) REFERENCES document.docetats(id);


--
-- TOC entry 5116 (class 2606 OID 45540)
-- Name: elementsbases fk_element; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.elementsbases
    ADD CONSTRAINT fk_element FOREIGN KEY (element_id) REFERENCES document.elements(id);


--
-- TOC entry 5121 (class 2606 OID 28807)
-- Name: elementslangues fk_elements_elementslangues; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.elementslangues
    ADD CONSTRAINT fk_elements_elementslangues FOREIGN KEY (elements_id) REFERENCES document.elements(id);


--
-- TOC entry 5123 (class 2606 OID 28797)
-- Name: elements fk_elementsbase_elements; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.elements
    ADD CONSTRAINT fk_elementsbase_elements FOREIGN KEY (elementsbases_id) REFERENCES document.elementsbases(id);


--
-- TOC entry 5119 (class 2606 OID 28814)
-- Name: elementsbaselanques fk_elementsbase_elementsbaselangues; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.elementsbaselanques
    ADD CONSTRAINT fk_elementsbase_elementsbaselangues FOREIGN KEY (elementsbases_id) REFERENCES document.elementsbases(id);


--
-- TOC entry 5078 (class 2606 OID 28466)
-- Name: etapes fk_etapes_on_parcours; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.etapes
    ADD CONSTRAINT fk_etapes_on_parcours FOREIGN KEY (parcours_id) REFERENCES document.parcours(id);


--
-- TOC entry 5079 (class 2606 OID 36869)
-- Name: exemplaires fk_exemplaire_documents; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.exemplaires
    ADD CONSTRAINT fk_exemplaire_documents FOREIGN KEY (documents_id) REFERENCES document.documents(id);


--
-- TOC entry 5080 (class 2606 OID 28471)
-- Name: exemplaires fk_exemplaires_personnes; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.exemplaires
    ADD CONSTRAINT fk_exemplaires_personnes FOREIGN KEY (personnes_id) REFERENCES document.personnes(id);


--
-- TOC entry 5081 (class 2606 OID 28476)
-- Name: famillespromotions fk_fam_on_familles_entity; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.famillespromotions
    ADD CONSTRAINT fk_fam_on_familles_entity FOREIGN KEY (familles_id) REFERENCES document.familles(id);


--
-- TOC entry 5082 (class 2606 OID 28481)
-- Name: famillespromotions fk_fam_on_promotions_entity; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.famillespromotions
    ADD CONSTRAINT fk_fam_on_promotions_entity FOREIGN KEY (promotions_id) REFERENCES document.promotions(id);


--
-- TOC entry 5083 (class 2606 OID 28486)
-- Name: filesattentes fk_filesattentes_on_services; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.filesattentes
    ADD CONSTRAINT fk_filesattentes_on_services FOREIGN KEY (services_id) REFERENCES document.services(id);


--
-- TOC entry 5125 (class 2606 OID 28785)
-- Name: menus fk_groupes_menus; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.menus
    ADD CONSTRAINT fk_groupes_menus FOREIGN KEY (groupes_id) REFERENCES document.groupes(id);


--
-- TOC entry 5127 (class 2606 OID 28770)
-- Name: utilisateurs fk_groupes_utilisateurs; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.utilisateurs
    ADD CONSTRAINT fk_groupes_utilisateurs FOREIGN KEY (groupes_id) REFERENCES document.groupes(id);


--
-- TOC entry 5084 (class 2606 OID 28496)
-- Name: jouerroles fk_jouerroles_on_roles; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.jouerroles
    ADD CONSTRAINT fk_jouerroles_on_roles FOREIGN KEY (roles_id) REFERENCES document.roles(id);


--
-- TOC entry 5118 (class 2606 OID 28831)
-- Name: actionslangues fk_langues_actionslangues; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.actionslangues
    ADD CONSTRAINT fk_langues_actionslangues FOREIGN KEY (langues_id) REFERENCES document.langues(id);


--
-- TOC entry 5120 (class 2606 OID 28819)
-- Name: elementsbaselanques fk_langues_elementsbaselangues; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.elementsbaselanques
    ADD CONSTRAINT fk_langues_elementsbaselangues FOREIGN KEY (langues_id) REFERENCES document.langues(id);


--
-- TOC entry 5122 (class 2606 OID 28802)
-- Name: elementslangues fk_langues_elementslangues; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.elementslangues
    ADD CONSTRAINT fk_langues_elementslangues FOREIGN KEY (langues_id) REFERENCES document.langues(id);


--
-- TOC entry 5124 (class 2606 OID 28792)
-- Name: elements fk_menus_elements; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.elements
    ADD CONSTRAINT fk_menus_elements FOREIGN KEY (menus_id) REFERENCES document.menus(id);


--
-- TOC entry 5131 (class 2606 OID 28753)
-- Name: groupes fk_menus_groupes; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.groupes
    ADD CONSTRAINT fk_menus_groupes FOREIGN KEY (menus_id) REFERENCES document.menus(id);


--
-- TOC entry 5128 (class 2606 OID 28775)
-- Name: utilisateurs fk_menus_utilisateurs; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.utilisateurs
    ADD CONSTRAINT fk_menus_utilisateurs FOREIGN KEY (menus_id) REFERENCES document.menus(id);


--
-- TOC entry 5085 (class 2606 OID 28501)
-- Name: missions fk_missions_on_services; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.missions
    ADD CONSTRAINT fk_missions_on_services FOREIGN KEY (services_id) REFERENCES document.services(id);


--
-- TOC entry 5086 (class 2606 OID 28506)
-- Name: mouvementcaisses fk_mouvementcaisses_caisses; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.mouvementcaisses
    ADD CONSTRAINT fk_mouvementcaisses_caisses FOREIGN KEY (caisses_id) REFERENCES document.caisses(id);


--
-- TOC entry 5087 (class 2606 OID 28511)
-- Name: mouvementcaisses fk_mouvementcaisses_comptes; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.mouvementcaisses
    ADD CONSTRAINT fk_mouvementcaisses_comptes FOREIGN KEY (comptes_id) REFERENCES document.comptes(id);


--
-- TOC entry 5090 (class 2606 OID 28521)
-- Name: mouvements fk_mouvements_on_ressources; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.mouvements
    ADD CONSTRAINT fk_mouvements_on_ressources FOREIGN KEY (ressources_id) REFERENCES document.ressources(id);


--
-- TOC entry 5088 (class 2606 OID 28526)
-- Name: mouvementcaisses fk_mouvementscaisses_exemplaires; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.mouvementcaisses
    ADD CONSTRAINT fk_mouvementscaisses_exemplaires FOREIGN KEY (exemplaires_id) REFERENCES document.exemplaires(id);


--
-- TOC entry 5091 (class 2606 OID 28531)
-- Name: ordreetats fk_ordreetats_on_etats; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.ordreetats
    ADD CONSTRAINT fk_ordreetats_on_etats FOREIGN KEY (etats_id) REFERENCES document.etats(id);


--
-- TOC entry 5129 (class 2606 OID 28758)
-- Name: organiser fk_organisation_organiser; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.organiser
    ADD CONSTRAINT fk_organisation_organiser FOREIGN KEY (organisations_id) REFERENCES document.organisations(id);


--
-- TOC entry 5135 (class 2606 OID 46411)
-- Name: personnels fk_personnels_personnes; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.personnels
    ADD CONSTRAINT fk_personnels_personnes FOREIGN KEY (personnels_id) REFERENCES document.personnes(id);


--
-- TOC entry 5092 (class 2606 OID 28536)
-- Name: personnes fk_personnes_comptes; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.personnes
    ADD CONSTRAINT fk_personnes_comptes FOREIGN KEY (comptes_id) REFERENCES document.comptes(id);


--
-- TOC entry 5093 (class 2606 OID 46472)
-- Name: personnes fk_personnes_ticket; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.personnes
    ADD CONSTRAINT fk_personnes_ticket FOREIGN KEY (ticket_id) REFERENCES document.tickets(id);


--
-- TOC entry 5094 (class 2606 OID 28541)
-- Name: precomouvementsqtes fk_precomouvementsqtes_on_precomouvements; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.precomouvementsqtes
    ADD CONSTRAINT fk_precomouvementsqtes_on_precomouvements FOREIGN KEY (precomouvements_id) REFERENCES document.precomouvements(id);


--
-- TOC entry 5095 (class 2606 OID 28546)
-- Name: precomouvementsqtes fk_precomouvementsqtes_on_ressources; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.precomouvementsqtes
    ADD CONSTRAINT fk_precomouvementsqtes_on_ressources FOREIGN KEY (ressources_id) REFERENCES document.ressources(id);


--
-- TOC entry 5100 (class 2606 OID 28561)
-- Name: ressourcespromotions fk_res_on_promotions_entity; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.ressourcespromotions
    ADD CONSTRAINT fk_res_on_promotions_entity FOREIGN KEY (promotions_id) REFERENCES document.promotions(id);


--
-- TOC entry 5101 (class 2606 OID 28566)
-- Name: ressourcespromotions fk_res_on_ressources_entity; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.ressourcespromotions
    ADD CONSTRAINT fk_res_on_ressources_entity FOREIGN KEY (ressources_id) REFERENCES document.ressources(id);


--
-- TOC entry 5097 (class 2606 OID 28571)
-- Name: respecter fk_respecter_on_mouvements_entity; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.respecter
    ADD CONSTRAINT fk_respecter_on_mouvements_entity FOREIGN KEY (mouvements_id) REFERENCES document.mouvements(id);


--
-- TOC entry 5098 (class 2606 OID 28576)
-- Name: respecter fk_respecter_on_preco_mouvements_entity; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.respecter
    ADD CONSTRAINT fk_respecter_on_preco_mouvements_entity FOREIGN KEY (precomouvements_id) REFERENCES document.precomouvements(id);


--
-- TOC entry 5099 (class 2606 OID 28581)
-- Name: ressources fk_ressources_on_familles; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.ressources
    ADD CONSTRAINT fk_ressources_on_familles FOREIGN KEY (familles_id) REFERENCES document.familles(id);


--
-- TOC entry 5102 (class 2606 OID 28586)
-- Name: sapplique fk_sapplique_on_familles_entity; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.sapplique
    ADD CONSTRAINT fk_sapplique_on_familles_entity FOREIGN KEY (familles_id) REFERENCES document.familles(id);


--
-- TOC entry 5103 (class 2606 OID 28591)
-- Name: sapplique fk_sapplique_on_preco_mouvements_qtes_entity; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.sapplique
    ADD CONSTRAINT fk_sapplique_on_preco_mouvements_qtes_entity FOREIGN KEY (precomouvementsqtes_id) REFERENCES document.precomouvementsqtes(id);


--
-- TOC entry 5104 (class 2606 OID 28596)
-- Name: services fk_services_on_filesattentes; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.services
    ADD CONSTRAINT fk_services_on_filesattentes FOREIGN KEY (filesattentes_id) REFERENCES document.filesattentes(id);


--
-- TOC entry 5105 (class 2606 OID 28601)
-- Name: suivre fk_suivre_on_documents_entity; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.suivre
    ADD CONSTRAINT fk_suivre_on_documents_entity FOREIGN KEY (documents_id) REFERENCES document.documents(id);


--
-- TOC entry 5106 (class 2606 OID 28606)
-- Name: suivre fk_suivre_on_preco_mouvements_entity; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.suivre
    ADD CONSTRAINT fk_suivre_on_preco_mouvements_entity FOREIGN KEY (precomouvements_id) REFERENCES document.precomouvements(id);


--
-- TOC entry 5107 (class 2606 OID 46467)
-- Name: tickets fk_tickets_personne; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.tickets
    ADD CONSTRAINT fk_tickets_personne FOREIGN KEY (personne_id) REFERENCES document.personnes(id);


--
-- TOC entry 5108 (class 2606 OID 28611)
-- Name: ticketsfilesattentes fk_ticketsfilesattentes_on_filesattentes; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.ticketsfilesattentes
    ADD CONSTRAINT fk_ticketsfilesattentes_on_filesattentes FOREIGN KEY (filesattentes_id) REFERENCES document.filesattentes(id);


--
-- TOC entry 5109 (class 2606 OID 28616)
-- Name: ticketsfilesattentes fk_ticketsfilesattentes_on_tickets; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.ticketsfilesattentes
    ADD CONSTRAINT fk_ticketsfilesattentes_on_tickets FOREIGN KEY (tickets_id) REFERENCES document.tickets(id);


--
-- TOC entry 5110 (class 2606 OID 28621)
-- Name: traiter fk_traiter_on_documents_entity; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.traiter
    ADD CONSTRAINT fk_traiter_on_documents_entity FOREIGN KEY (documents_id) REFERENCES document.documents(id);


--
-- TOC entry 5111 (class 2606 OID 28626)
-- Name: traiter fk_traiter_on_missions_entity; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.traiter
    ADD CONSTRAINT fk_traiter_on_missions_entity FOREIGN KEY (missions_id) REFERENCES document.missions(id);


--
-- TOC entry 5126 (class 2606 OID 28780)
-- Name: menus fk_utilisateur_menus; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.menus
    ADD CONSTRAINT fk_utilisateur_menus FOREIGN KEY (utilisateurs_id) REFERENCES document.utilisateurs(id);


--
-- TOC entry 5130 (class 2606 OID 28763)
-- Name: organiser fk_utilisateurs_organiser; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.organiser
    ADD CONSTRAINT fk_utilisateurs_organiser FOREIGN KEY (utilisateurs_id) REFERENCES document.utilisateurs(id);


--
-- TOC entry 5112 (class 2606 OID 28631)
-- Name: validations fk_validations_on_roles; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.validations
    ADD CONSTRAINT fk_validations_on_roles FOREIGN KEY (roles_id) REFERENCES document.roles(id);


--
-- TOC entry 5113 (class 2606 OID 28636)
-- Name: violer fk_violer_on_mouvements_entity; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.violer
    ADD CONSTRAINT fk_violer_on_mouvements_entity FOREIGN KEY (mouvements_id) REFERENCES document.mouvements(id);


--
-- TOC entry 5114 (class 2606 OID 28641)
-- Name: violer fk_violer_on_preco_mouvements_entity; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.violer
    ADD CONSTRAINT fk_violer_on_preco_mouvements_entity FOREIGN KEY (precomouvements_id) REFERENCES document.precomouvements(id);


--
-- TOC entry 5133 (class 2606 OID 46351)
-- Name: personnesmorales personnesmorales_id_fkey; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.personnesmorales
    ADD CONSTRAINT personnesmorales_id_fkey FOREIGN KEY (personnesmorales_id) REFERENCES document.personnes(id);


--
-- TOC entry 5132 (class 2606 OID 46339)
-- Name: personnesphysique personnesphysique_id_fkey; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.personnesphysique
    ADD CONSTRAINT personnesphysique_id_fkey FOREIGN KEY (personnesphysique_id) REFERENCES document.personnes(id);


-- Completed on 2025-09-26 11:31:11

--
-- PostgreSQL database dump complete
--

