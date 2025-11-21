--
-- PostgreSQL database dump
--

\restrict LWLHMWBzJV6gm8lRDXsHG616jSBKXPDATPPragz95WIIadjFUcXQiQhiSr2rrJh

-- Dumped from database version 18.0
-- Dumped by pg_dump version 18.0

-- Started on 2025-10-30 02:18:41

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 5498 (class 1262 OID 16525)
-- Name: parcoursbackv2; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE parcoursbackv2 WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'French_France.1252';


ALTER DATABASE parcoursbackv2 OWNER TO postgres;

\unrestrict LWLHMWBzJV6gm8lRDXsHG616jSBKXPDATPPragz95WIIadjFUcXQiQhiSr2rrJh
\connect parcoursbackv2
\restrict LWLHMWBzJV6gm8lRDXsHG616jSBKXPDATPPragz95WIIadjFUcXQiQhiSr2rrJh

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 6 (class 2615 OID 16526)
-- Name: document; Type: SCHEMA; Schema: -; Owner: pg_database_owner
--

CREATE SCHEMA document;


ALTER SCHEMA document OWNER TO pg_database_owner;

--
-- TOC entry 5499 (class 0 OID 0)
-- Dependencies: 6
-- Name: SCHEMA document; Type: COMMENT; Schema: -; Owner: pg_database_owner
--

COMMENT ON SCHEMA document IS 'standard public schema';


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 220 (class 1259 OID 16527)
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
-- TOC entry 221 (class 1259 OID 16533)
-- Name: actionslangues; Type: TABLE; Schema: document; Owner: postgres
--

CREATE TABLE document.actionslangues (
    langues_id character varying(255) NOT NULL,
    actions_id character varying(255) NOT NULL,
    valeurlibelle character varying(255)
);


ALTER TABLE document.actionslangues OWNER TO postgres;

--
-- TOC entry 222 (class 1259 OID 16540)
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
-- TOC entry 223 (class 1259 OID 16547)
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
-- TOC entry 224 (class 1259 OID 16556)
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
-- TOC entry 225 (class 1259 OID 16562)
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
-- TOC entry 226 (class 1259 OID 16569)
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
-- TOC entry 227 (class 1259 OID 16575)
-- Name: concerner; Type: TABLE; Schema: document; Owner: postgres
--

CREATE TABLE document.concerner (
    distributeurs_id character varying(255) NOT NULL,
    precomouvementsqtes_id character varying(255) NOT NULL
);


ALTER TABLE document.concerner OWNER TO postgres;

--
-- TOC entry 228 (class 1259 OID 16582)
-- Name: constituer; Type: TABLE; Schema: document; Owner: postgres
--

CREATE TABLE document.constituer (
    attributs_id character varying(255) NOT NULL,
    documents_id character varying(255) NOT NULL
);


ALTER TABLE document.constituer OWNER TO postgres;

--
-- TOC entry 229 (class 1259 OID 16589)
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
-- TOC entry 230 (class 1259 OID 16598)
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
-- TOC entry 231 (class 1259 OID 16604)
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
-- TOC entry 232 (class 1259 OID 16610)
-- Name: docetats_predecesseurs; Type: TABLE; Schema: document; Owner: postgres
--

CREATE TABLE document.docetats_predecesseurs (
    docetats_id character varying(255) NOT NULL,
    predecesseur_id character varying(255) NOT NULL
);


ALTER TABLE document.docetats_predecesseurs OWNER TO postgres;

--
-- TOC entry 233 (class 1259 OID 16617)
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
    afficherprix character varying(255),
    afficherunite boolean,
    afficherdistributeur boolean,
    prixeditable boolean,
    contientressources boolean,
    estencaissable boolean
);


ALTER TABLE document.documents OWNER TO postgres;

--
-- TOC entry 234 (class 1259 OID 16624)
-- Name: documentspromotions; Type: TABLE; Schema: document; Owner: postgres
--

CREATE TABLE document.documentspromotions (
    documents_id character varying(255) NOT NULL,
    promotions_id character varying(255) NOT NULL
);


ALTER TABLE document.documentspromotions OWNER TO postgres;

--
-- TOC entry 235 (class 1259 OID 16631)
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
-- TOC entry 236 (class 1259 OID 16637)
-- Name: elementsbaselanques; Type: TABLE; Schema: document; Owner: postgres
--

CREATE TABLE document.elementsbaselanques (
    langues_id character varying(255) NOT NULL,
    elementsbases_id character varying(255) NOT NULL,
    valeurlibelle character varying(255)
);


ALTER TABLE document.elementsbaselanques OWNER TO postgres;

--
-- TOC entry 237 (class 1259 OID 16644)
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
-- TOC entry 238 (class 1259 OID 16650)
-- Name: elementslangues; Type: TABLE; Schema: document; Owner: postgres
--

CREATE TABLE document.elementslangues (
    langues_id character varying(255) NOT NULL,
    elements_id character varying(255) NOT NULL,
    valeurlibelle character varying(255)
);


ALTER TABLE document.elementslangues OWNER TO postgres;

--
-- TOC entry 239 (class 1259 OID 16657)
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
-- TOC entry 240 (class 1259 OID 16663)
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
-- TOC entry 241 (class 1259 OID 16669)
-- Name: exemplaires; Type: TABLE; Schema: document; Owner: postgres
--

CREATE TABLE document.exemplaires (
    id character varying(255) NOT NULL,
    personnes_id character varying(255) NOT NULL,
    documents_id character varying(255)
);


ALTER TABLE document.exemplaires OWNER TO postgres;

--
-- TOC entry 242 (class 1259 OID 16676)
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
-- TOC entry 243 (class 1259 OID 16682)
-- Name: famillespromotions; Type: TABLE; Schema: document; Owner: postgres
--

CREATE TABLE document.famillespromotions (
    familles_id character varying(255) NOT NULL,
    promotions_id character varying(255) NOT NULL
);


ALTER TABLE document.famillespromotions OWNER TO postgres;

--
-- TOC entry 244 (class 1259 OID 16689)
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
-- TOC entry 245 (class 1259 OID 16695)
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
-- TOC entry 246 (class 1259 OID 16701)
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
-- TOC entry 247 (class 1259 OID 16707)
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
-- TOC entry 248 (class 1259 OID 16713)
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
-- TOC entry 249 (class 1259 OID 16719)
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
-- TOC entry 250 (class 1259 OID 16727)
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
-- TOC entry 251 (class 1259 OID 16733)
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
-- TOC entry 252 (class 1259 OID 16741)
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
-- TOC entry 253 (class 1259 OID 16747)
-- Name: organisations; Type: TABLE; Schema: document; Owner: postgres
--

CREATE TABLE document.organisations (
    id character varying(255) NOT NULL,
    raisonsociale character varying(255)
);


ALTER TABLE document.organisations OWNER TO postgres;

--
-- TOC entry 254 (class 1259 OID 16753)
-- Name: organiser; Type: TABLE; Schema: document; Owner: postgres
--

CREATE TABLE document.organiser (
    organisations_id character varying(255) NOT NULL,
    utilisateurs_id character varying(255) NOT NULL
);


ALTER TABLE document.organiser OWNER TO postgres;

--
-- TOC entry 255 (class 1259 OID 16760)
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
-- TOC entry 256 (class 1259 OID 16766)
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
-- TOC entry 257 (class 1259 OID 16772)
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
-- TOC entry 258 (class 1259 OID 16778)
-- Name: personnesmorales; Type: TABLE; Schema: document; Owner: postgres
--

CREATE TABLE document.personnesmorales (
    personnesmorales_id character varying(255) NOT NULL,
    raisonsociale character varying(255),
    code character varying(255)
);


ALTER TABLE document.personnesmorales OWNER TO postgres;

--
-- TOC entry 259 (class 1259 OID 16784)
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
-- TOC entry 260 (class 1259 OID 16792)
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
-- TOC entry 261 (class 1259 OID 16800)
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
-- TOC entry 262 (class 1259 OID 16807)
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
-- TOC entry 263 (class 1259 OID 16813)
-- Name: rattacher; Type: TABLE; Schema: document; Owner: postgres
--

CREATE TABLE document.rattacher (
    personnes_id character varying(255) NOT NULL,
    rattacher_id character varying(255) NOT NULL
);


ALTER TABLE document.rattacher OWNER TO postgres;

--
-- TOC entry 264 (class 1259 OID 16820)
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
-- TOC entry 265 (class 1259 OID 16826)
-- Name: respecter; Type: TABLE; Schema: document; Owner: postgres
--

CREATE TABLE document.respecter (
    mouvements_id character varying(255) NOT NULL,
    precomouvements_id character varying(255) NOT NULL
);


ALTER TABLE document.respecter OWNER TO postgres;

--
-- TOC entry 266 (class 1259 OID 16833)
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
-- TOC entry 267 (class 1259 OID 16839)
-- Name: ressourcespromotions; Type: TABLE; Schema: document; Owner: postgres
--

CREATE TABLE document.ressourcespromotions (
    promotions_id character varying(255) NOT NULL,
    ressources_id character varying(255) NOT NULL
);


ALTER TABLE document.ressourcespromotions OWNER TO postgres;

--
-- TOC entry 268 (class 1259 OID 16846)
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
-- TOC entry 269 (class 1259 OID 16852)
-- Name: sapplique; Type: TABLE; Schema: document; Owner: postgres
--

CREATE TABLE document.sapplique (
    familles_id character varying(255) NOT NULL,
    precomouvementsqtes_id character varying(255) NOT NULL
);


ALTER TABLE document.sapplique OWNER TO postgres;

--
-- TOC entry 270 (class 1259 OID 16859)
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
-- TOC entry 271 (class 1259 OID 16866)
-- Name: suivre; Type: TABLE; Schema: document; Owner: postgres
--

CREATE TABLE document.suivre (
    documents_id character varying(255) NOT NULL,
    precomouvements_id character varying(255) NOT NULL
);


ALTER TABLE document.suivre OWNER TO postgres;

--
-- TOC entry 272 (class 1259 OID 16873)
-- Name: tickets; Type: TABLE; Schema: document; Owner: postgres
--

CREATE TABLE document.tickets (
    id character varying(255) NOT NULL,
    codecourt character varying(255),
    datecreation date,
    datemodification date,
    statut character varying(255),
    personnesphysique_id character varying(255),
    idunique character varying(255)
);


ALTER TABLE document.tickets OWNER TO postgres;

--
-- TOC entry 273 (class 1259 OID 16879)
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
-- TOC entry 274 (class 1259 OID 16885)
-- Name: traiter; Type: TABLE; Schema: document; Owner: postgres
--

CREATE TABLE document.traiter (
    documents_id character varying(255) NOT NULL,
    missions_id character varying(255) NOT NULL
);


ALTER TABLE document.traiter OWNER TO postgres;

--
-- TOC entry 275 (class 1259 OID 16892)
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
-- TOC entry 276 (class 1259 OID 16898)
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
-- TOC entry 277 (class 1259 OID 16904)
-- Name: violer; Type: TABLE; Schema: document; Owner: postgres
--

CREATE TABLE document.violer (
    mouvements_id character varying(255) NOT NULL,
    precomouvements_id character varying(255) NOT NULL
);


ALTER TABLE document.violer OWNER TO postgres;

--
-- TOC entry 5435 (class 0 OID 16527)
-- Dependencies: 220
-- Data for Name: actions; Type: TABLE DATA; Schema: document; Owner: postgres
--

INSERT INTO document.actions (id, libelle, etat, datecreation, datemodification, elementsbase_id) VALUES ('j6bca7iy-5i6j-4c8f-3340-7a1b0c9d8e7f', 'Afficher', true, '2024-01-01', '2024-01-01', 'u1lmn2tj-0t1u-4d8f-6495-8k7l6m5n4o3p') ON CONFLICT DO NOTHING;
INSERT INTO document.actions (id, libelle, etat, datecreation, datemodification, elementsbase_id) VALUES ('k7cdb8jz-6j7k-4d90-2259-6b0c9d8e7f6g', 'Ajouter', true, '2024-01-01', '2024-01-01', 'v2mno3uk-1u2v-4e90-5304-7l6m5n4o3p2q') ON CONFLICT DO NOTHING;
INSERT INTO document.actions (id, libelle, etat, datecreation, datemodification, elementsbase_id) VALUES ('l8dec9ka-7k8l-4fa1-1168-5c9d8e7f6g5h', 'Modifier', true, '2024-01-01', '2024-01-01', 'w3nop4vl-2v3w-4fa1-4213-6m5n4o3p2q1r') ON CONFLICT DO NOTHING;
INSERT INTO document.actions (id, libelle, etat, datecreation, datemodification, elementsbase_id) VALUES ('m9efd0lb-8l9m-40b2-0077-4d8e7f6g5h4i', 'Supprimer', true, '2024-01-01', '2024-01-01', 'x4opq5wm-3w4x-40b2-3122-5n4o3p2q1r0s') ON CONFLICT DO NOTHING;
INSERT INTO document.actions (id, libelle, etat, datecreation, datemodification, elementsbase_id) VALUES ('n0fge1mc-9m0n-41c3-7986-3e7f6g5h4i3j', 'Exporter', true, '2024-01-01', '2024-01-01', 'y5pqr6xn-4x5y-41c3-2031-4o3p2q1r0s9t') ON CONFLICT DO NOTHING;


--
-- TOC entry 5436 (class 0 OID 16533)
-- Dependencies: 221
-- Data for Name: actionslangues; Type: TABLE DATA; Schema: document; Owner: postgres
--

INSERT INTO document.actionslangues (langues_id, actions_id, valeurlibelle) VALUES ('e1vwx2dt-0d1e-4729-4495-8u7v6w5x4y3z', 'j6bca7iy-5i6j-4c8f-3340-7a1b0c9d8e7f', 'Afficher') ON CONFLICT DO NOTHING;
INSERT INTO document.actionslangues (langues_id, actions_id, valeurlibelle) VALUES ('f2wxy3eu-1e2f-483a-3304-7v6w5x4y3z2a', 'j6bca7iy-5i6j-4c8f-3340-7a1b0c9d8e7f', 'Show') ON CONFLICT DO NOTHING;
INSERT INTO document.actionslangues (langues_id, actions_id, valeurlibelle) VALUES ('e1vwx2dt-0d1e-4729-4495-8u7v6w5x4y3z', 'k7cdb8jz-6j7k-4d90-2259-6b0c9d8e7f6g', 'Ajouter') ON CONFLICT DO NOTHING;
INSERT INTO document.actionslangues (langues_id, actions_id, valeurlibelle) VALUES ('f2wxy3eu-1e2f-483a-3304-7v6w5x4y3z2a', 'k7cdb8jz-6j7k-4d90-2259-6b0c9d8e7f6g', 'Add') ON CONFLICT DO NOTHING;
INSERT INTO document.actionslangues (langues_id, actions_id, valeurlibelle) VALUES ('e1vwx2dt-0d1e-4729-4495-8u7v6w5x4y3z', 'l8dec9ka-7k8l-4fa1-1168-5c9d8e7f6g5h', 'Modifier') ON CONFLICT DO NOTHING;


--
-- TOC entry 5437 (class 0 OID 16540)
-- Dependencies: 222
-- Data for Name: associer; Type: TABLE DATA; Schema: document; Owner: postgres
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
-- TOC entry 5438 (class 0 OID 16547)
-- Dependencies: 223
-- Data for Name: attributs; Type: TABLE DATA; Schema: document; Owner: postgres
--

INSERT INTO document.attributs (id, titre, description, etat, datecreation, datemodification, valeurpardefaut, type_attribut) VALUES ('a1eebc99-9c0b-4ef8-bb6d-6bb9bd380a13', 'Age', 'Description age', true, '2022-01-01', '2022-01-02', NULL, 'Text') ON CONFLICT DO NOTHING;
INSERT INTO document.attributs (id, titre, description, etat, datecreation, datemodification, valeurpardefaut, type_attribut) VALUES ('a1eebc99-9c0b-4ef8-bb6d-6bb9bd380a15', 'Teint', 'Coloration', true, '2022-01-01', '2022-01-02', 'Noir', 'Checkbox') ON CONFLICT DO NOTHING;
INSERT INTO document.attributs (id, titre, description, etat, datecreation, datemodification, valeurpardefaut, type_attribut) VALUES ('a2eebc99-9c0b-4ef8-bb6d-6bb9bd380a12', 'Poids', 'Description Poids', true, '2022-01-01', '2022-01-02', NULL, 'Radio') ON CONFLICT DO NOTHING;
INSERT INTO document.attributs (id, titre, description, etat, datecreation, datemodification, valeurpardefaut, type_attribut) VALUES ('a2eebc99-9c0b-4ef8-bb6d-6bb9bd380a20', 'Date de naissance', 'Date de naissance', true, '2022-01-01', '2022-01-02', NULL, 'Date') ON CONFLICT DO NOTHING;
INSERT INTO document.attributs (id, titre, description, etat, datecreation, datemodification, valeurpardefaut, type_attribut) VALUES ('a4eebc99-9c0b-4ef8-bb6d-6bb9bd380a14', 'Sexe', 'Sexe ', true, '2022-01-01', '2022-01-02', NULL, 'Date') ON CONFLICT DO NOTHING;
INSERT INTO document.attributs (id, titre, description, etat, datecreation, datemodification, valeurpardefaut, type_attribut) VALUES ('a6eebc99-9c0b-4ef8-bb6d-6bb9bd380a16', 'Groupe sangin', 'Groupe sangin', true, '2022-01-01', '2022-01-02', 'A, A+, A-, B, B+, B-, AB, AB+, AB-, O, O+, O-', 'Textarea') ON CONFLICT DO NOTHING;
INSERT INTO document.attributs (id, titre, description, etat, datecreation, datemodification, valeurpardefaut, type_attribut) VALUES ('a7eebc99-9c0b-4ef8-bb6d-6bb9bd380a17', 'Allergies', 'Allergies connues ', true, '2022-01-01', '2022-01-02', NULL, 'Date') ON CONFLICT DO NOTHING;
INSERT INTO document.attributs (id, titre, description, etat, datecreation, datemodification, valeurpardefaut, type_attribut) VALUES ('a8eebc99-9c0b-4ef8-bb6d-6bb9bd380a18', 'Nom', 'Nom de la personne', true, '2022-01-01', '2022-01-02', NULL, 'Url') ON CONFLICT DO NOTHING;
INSERT INTO document.attributs (id, titre, description, etat, datecreation, datemodification, valeurpardefaut, type_attribut) VALUES ('a9eebc99-9c0b-4ef8-bb6d-6bb9bd380a19', 'Adresse', 'Adresse postale', true, '2022-01-01', '2022-01-02', NULL, 'Url') ON CONFLICT DO NOTHING;
INSERT INTO document.attributs (id, titre, description, etat, datecreation, datemodification, valeurpardefaut, type_attribut) VALUES ('a1eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 'decription taille des Taille sur taille', 'Description Taille sur tailles', false, '2022-01-01', '2022-01-02', NULL, 'Textarea') ON CONFLICT DO NOTHING;
INSERT INTO document.attributs (id, titre, description, etat, datecreation, datemodification, valeurpardefaut, type_attribut) VALUES ('a3cbf07d-eee9-44b3-89b8-c8e2d6db4f06', 'decription taille des Taille sur taille', 'Description Taille sur tailles', false, '2022-01-01', '2022-01-02', NULL, 'Textarea') ON CONFLICT DO NOTHING;
INSERT INTO document.attributs (id, titre, description, etat, datecreation, datemodification, valeurpardefaut, type_attribut) VALUES ('9332738c-a1c1-4807-9b0f-f47f11c05b91', 'decription taille des Taille sur taille', 'Description Taille sur tailles', false, '2022-01-01', '2022-01-02', NULL, 'Textarea') ON CONFLICT DO NOTHING;
INSERT INTO document.attributs (id, titre, description, etat, datecreation, datemodification, valeurpardefaut, type_attribut) VALUES ('82e987a2-488e-4152-8926-db875f940c66', 'decription taille des Taille sur taille', 'Description Taille sur tailles', false, '2022-01-01', '2022-01-02', NULL, 'Textarea') ON CONFLICT DO NOTHING;
INSERT INTO document.attributs (id, titre, description, etat, datecreation, datemodification, valeurpardefaut, type_attribut) VALUES ('0ec60831-7876-4a59-aeab-5f283b7a4c68', 'decription taille des Taille sur taille33', 'Description Taille sur tailles', false, '2022-01-01', '2022-01-02', NULL, 'Textarea') ON CONFLICT DO NOTHING;
INSERT INTO document.attributs (id, titre, description, etat, datecreation, datemodification, valeurpardefaut, type_attribut) VALUES ('7d7090e7-3204-47ca-b42a-cc0fa8827f3b', 'decription taille des Taille sur taille333', 'Description Taille sur tailles378', true, '2021-11-01', '2022-05-02', NULL, 'Textarea') ON CONFLICT DO NOTHING;
INSERT INTO document.attributs (id, titre, description, etat, datecreation, datemodification, valeurpardefaut, type_attribut) VALUES ('0372b2c2-7afd-4498-a3fa-ab0aa45d2775', 'decription taille des Taille sur taille533', 'Description Taille sur tailles3758', true, '2021-12-02', '2022-04-02', NULL, 'Textarea') ON CONFLICT DO NOTHING;
INSERT INTO document.attributs (id, titre, description, etat, datecreation, datemodification, valeurpardefaut, type_attribut) VALUES ('876b1513-c1b8-4cc7-a3a2-aa808e6a06ba', 'decription taille des Taille sur taille052', 'Description Taille sur tailles3078518', true, '2021-12-02', '2022-04-02', NULL, 'Textarea') ON CONFLICT DO NOTHING;
INSERT INTO document.attributs (id, titre, description, etat, datecreation, datemodification, valeurpardefaut, type_attribut) VALUES ('8d70edb8-f543-4282-b17b-c97a8b71b563', 'decription taille des Taille sur taille752', 'Description Taille sur tailles30787518', true, '2021-12-01', '2022-04-01', NULL, 'Textarea') ON CONFLICT DO NOTHING;
INSERT INTO document.attributs (id, titre, description, etat, datecreation, datemodification, valeurpardefaut, type_attribut) VALUES ('cd302436-268f-46e7-8e61-df356bd8625d', 'desciptionbetabetalambda', 'taille en description2', true, NULL, NULL, NULL, 'Number') ON CONFLICT DO NOTHING;
INSERT INTO document.attributs (id, titre, description, etat, datecreation, datemodification, valeurpardefaut, type_attribut) VALUES ('8bc12e32-86d3-4da2-b49e-1054bf6c8798', 'desciptionbetabetalambdgghgjhja', 'bienfghgj,', true, NULL, NULL, NULL, 'Number') ON CONFLICT DO NOTHING;
INSERT INTO document.attributs (id, titre, description, etat, datecreation, datemodification, valeurpardefaut, type_attribut) VALUES ('beebfb6e-7046-471a-8d0d-a4fdb4e7aa43', 'desciptionbetabetalambdgghgjhja', 'bienfghgj,', true, NULL, NULL, NULL, 'Number') ON CONFLICT DO NOTHING;
INSERT INTO document.attributs (id, titre, description, etat, datecreation, datemodification, valeurpardefaut, type_attribut) VALUES ('36bb8723-4a93-4f01-ad1a-874640f550b2', 'desciptionbetabetalambdgghgjhja', 'bienfghgj,', true, NULL, NULL, '5', 'Number') ON CONFLICT DO NOTHING;


--
-- TOC entry 5439 (class 0 OID 16556)
-- Dependencies: 224
-- Data for Name: caisses; Type: TABLE DATA; Schema: document; Owner: postgres
--

INSERT INTO document.caisses (id, libelle, solde, type, detailjson, etat) VALUES ('a1f8e3b0-4c6d-4e5e-ab7a-2a8b9b9c8d6f', 'Caisse principale', 10000, 'Caisse', '{"description": "Caisse principale"}', true) ON CONFLICT DO NOTHING;
INSERT INTO document.caisses (id, libelle, solde, type, detailjson, etat) VALUES ('b3a7c8d6-1e2f-3d4c-cb6a-9e8f0a7b6c5d', 'Caisse en ligne', 7500, 'Caisse', '{"description": "Caisse en ligne"}', true) ON CONFLICT DO NOTHING;
INSERT INTO document.caisses (id, libelle, solde, type, detailjson, etat) VALUES ('d9e2f1c2-8b3a-4f6c-bd5e-1a7b8a7c6d5e', 'Caisse secondaire3', 5000, 'Caisse', NULL, true) ON CONFLICT DO NOTHING;
INSERT INTO document.caisses (id, libelle, solde, type, detailjson, etat) VALUES ('f0a7b6c5-d4e3f2a1-8d9c-7d6e-5f4a3b2c1e0d', 'Caisse mobile3', 3000, 'Caisse', NULL, true) ON CONFLICT DO NOTHING;


--
-- TOC entry 5440 (class 0 OID 16562)
-- Dependencies: 225
-- Data for Name: categories; Type: TABLE DATA; Schema: document; Owner: postgres
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
-- TOC entry 5441 (class 0 OID 16569)
-- Dependencies: 226
-- Data for Name: comptes; Type: TABLE DATA; Schema: document; Owner: postgres
--

INSERT INTO document.comptes (id, datecreation, etat, montantdecouvertmax, libelle, personnes_id, solde) VALUES ('00012f83-2a62-4e6d-aa23-57c7825bcd57', '2024-08-19', 'actif', 2000, 'Compte entreprise', '11111111-aaaa-bbbb-cccc-111111111111', 102.32) ON CONFLICT DO NOTHING;
INSERT INTO document.comptes (id, datecreation, etat, montantdecouvertmax, libelle, personnes_id, solde) VALUES ('1179bd79-f71b-498b-b247-e7b9bbb3f600', '2024-08-19', 'inactif', 0, 'Compte jeunesse', '44444444-dddd-eeee-ffff-444444444444', 3553.2) ON CONFLICT DO NOTHING;
INSERT INTO document.comptes (id, datecreation, etat, montantdecouvertmax, libelle, personnes_id, solde) VALUES ('1518e585-f82a-4d5f-af1c-54f880d766d3', '2024-08-19', 'actif', 2000, 'Compte entreprise', '33333333-cccc-dddd-eeee-333333333333', 646583.32) ON CONFLICT DO NOTHING;
INSERT INTO document.comptes (id, datecreation, etat, montantdecouvertmax, libelle, personnes_id, solde) VALUES ('a1f8e3b0-4c6d-4e5e-7b7a-2a8b9b9c8d6f', '2024-08-19', 'actif', 500, 'Compte courant', 'bbbbbbbb-4444-5555-6666-bbbbbbbbbbbb', 14785) ON CONFLICT DO NOTHING;
INSERT INTO document.comptes (id, datecreation, etat, montantdecouvertmax, libelle, personnes_id, solde) VALUES ('adbff692-418d-43ab-a196-fcc3114b2daa', '2024-08-19', 'actif', 500, 'Compte courant', '99999999-2222-3333-4444-999999999999', 31656.325) ON CONFLICT DO NOTHING;
INSERT INTO document.comptes (id, datecreation, etat, montantdecouvertmax, libelle, personnes_id, solde) VALUES ('b3a7c8d6-1e2f-3d4c-7b6a-9e8f0a7b6c5d', '2024-08-19', 'inactif', 0, 'Compte jeunesse', '8a9b710a-a7fb-44ca-9587-b427b163cdab', 789654.23) ON CONFLICT DO NOTHING;
INSERT INTO document.comptes (id, datecreation, etat, montantdecouvertmax, libelle, personnes_id, solde) VALUES ('d301ff83-1b62-4e6d-aa23-57c7825bcd57', '2024-08-19', 'actif', 1000, 'Épargne', '1e8b6f56-7525-45bf-aa7b-96c0a9ae4198', 1153548.35) ON CONFLICT DO NOTHING;
INSERT INTO document.comptes (id, datecreation, etat, montantdecouvertmax, libelle, personnes_id, solde) VALUES ('d9e2f1c2-8b3a-4f6c-7d5e-1a7b8a7c6d5e', '2024-08-19', 'actif', 1000, 'Épargne', '4390615e-1101-7209-9932-7020bbd556f1', 635663.254) ON CONFLICT DO NOTHING;
INSERT INTO document.comptes (id, datecreation, etat, montantdecouvertmax, libelle, personnes_id, solde) VALUES ('f0a7b6c5-d4e3f2a1-7b9c-7d6e-5f4a3b2c1e0d', '2024-08-19', 'actif', 2000, 'Compte entreprise', '6b7894b5-1b07-487a-b8fe-d14ab61c1b2e', 655318.369) ON CONFLICT DO NOTHING;
INSERT INTO document.comptes (id, datecreation, etat, montantdecouvertmax, libelle, personnes_id, solde) VALUES ('a1234567-89ab-cdef-0123-456789abcdef', '2025-08-09', 'actif', 1000, 'Compte Personnel 1', '4390615e-1101-7209-9932-7020bbd556f3', 0) ON CONFLICT DO NOTHING;
INSERT INTO document.comptes (id, datecreation, etat, montantdecouvertmax, libelle, personnes_id, solde) VALUES ('b2345678-9abc-def0-1234-56789abcdef0', '2025-08-09', 'actif', 1500, 'Compte Personnel 2', '4390615e-1101-7209-9932-7020bbd556f2', 0) ON CONFLICT DO NOTHING;
INSERT INTO document.comptes (id, datecreation, etat, montantdecouvertmax, libelle, personnes_id, solde) VALUES ('a1111111-1111-1111-1111-111111111111', '2025-08-09', 'actif', 1000, 'Compte Personnel 1', '22222222-bbbb-cccc-dddd-222222222222', 0) ON CONFLICT DO NOTHING;
INSERT INTO document.comptes (id, datecreation, etat, montantdecouvertmax, libelle, personnes_id, solde) VALUES ('b2222222-2222-2222-2222-222222222222', '2025-08-09', 'actif', 1500, 'Compte Personnel 2', '3b277afa-98fc-4b0f-9b16-1d2fb4aa8ec6', 0) ON CONFLICT DO NOTHING;


--
-- TOC entry 5442 (class 0 OID 16575)
-- Dependencies: 227
-- Data for Name: concerner; Type: TABLE DATA; Schema: document; Owner: postgres
--



--
-- TOC entry 5443 (class 0 OID 16582)
-- Dependencies: 228
-- Data for Name: constituer; Type: TABLE DATA; Schema: document; Owner: postgres
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
-- TOC entry 5444 (class 0 OID 16589)
-- Dependencies: 229
-- Data for Name: deltasoldes; Type: TABLE DATA; Schema: document; Owner: postgres
--

INSERT INTO document.deltasoldes (id, montantavant, montantapres, datecreation, typemouvement, comptes_id, exemplaires_id) VALUES ('aad314f5-84ad-77cd-874d-9c55f0e45790', 651651.258, 51555.32, '2022-01-02', 'retrait', 'b3a7c8d6-1e2f-3d4c-7b6a-9e8f0a7b6c5d', 'b3a7c8d6-1e2f-3d4c-5b6a-9e8f0a7b6c5d') ON CONFLICT DO NOTHING;
INSERT INTO document.deltasoldes (id, montantavant, montantapres, datecreation, typemouvement, comptes_id, exemplaires_id) VALUES ('aad314f5-84ad-77cd-874d-9c55f0e45791', 651651.258, 51555.32, '2022-01-02', 'retrait', 'a1f8e3b0-4c6d-4e5e-7b7a-2a8b9b9c8d6f', 'a1f8e3b0-4c6d-4e5e-9b7a-2a8b9b9c8d6f') ON CONFLICT DO NOTHING;
INSERT INTO document.deltasoldes (id, montantavant, montantapres, datecreation, typemouvement, comptes_id, exemplaires_id) VALUES ('aad314f5-84ad-77cd-874d-9c55f0e45792', 564566.21, 6544864.21, '2022-01-02', 'paiement', 'd9e2f1c2-8b3a-4f6c-7d5e-1a7b8a7c6d5e', 'd9e2f1c2-8b3a-4f6c-9d5e-1a7b8a7c6d5e') ON CONFLICT DO NOTHING;
INSERT INTO document.deltasoldes (id, montantavant, montantapres, datecreation, typemouvement, comptes_id, exemplaires_id) VALUES ('aad314f5-84ad-77cd-874d-9c55f0e45793', 48646.58, 454151.14, '2022-01-02', 'paiement', 'f0a7b6c5-d4e3f2a1-7b9c-7d6e-5f4a3b2c1e0d', 'f0a7b6c5-d4e3f2a1-8b9c-7d6e-5f4a3b2c1e0d') ON CONFLICT DO NOTHING;
INSERT INTO document.deltasoldes (id, montantavant, montantapres, datecreation, typemouvement, comptes_id, exemplaires_id) VALUES ('aad314f5-84ad-77qd-874d-9c55f0e45790', 45245.6, 4587.35, '2022-01-02', 'retrait', 'd301ff83-1b62-4e6d-aa23-57c7825bcd57', 'a1f1e3b0-4c6d-4e5e-9b7a-2a8b9b9c8d6f') ON CONFLICT DO NOTHING;
INSERT INTO document.deltasoldes (id, montantavant, montantapres, datecreation, typemouvement, comptes_id, exemplaires_id) VALUES ('aad314f5-84ad-77qd-874e-9c55f0e45790', 452.21, 452452.26, '2022-01-02', 'depots', 'adbff692-418d-43ab-a196-fcc3114b2daa', 'a1f1e4b0-4c6d-4e5e-9b7a-2a8b9b9c8d6f') ON CONFLICT DO NOTHING;


--
-- TOC entry 5445 (class 0 OID 16598)
-- Dependencies: 230
-- Data for Name: distributeurs; Type: TABLE DATA; Schema: document; Owner: postgres
--

INSERT INTO document.distributeurs (distributeurs_id, datemodification, code, raisonsociale) VALUES ('99999999-2222-3333-4444-999999999999', '2025-06-01', 'D001', 'Distributeur Alpha') ON CONFLICT DO NOTHING;
INSERT INTO document.distributeurs (distributeurs_id, datemodification, code, raisonsociale) VALUES ('aaaaaaaa-3333-4444-5555-aaaaaaaaaaaa', '2025-06-02', 'D002', 'Distributeur Beta') ON CONFLICT DO NOTHING;
INSERT INTO document.distributeurs (distributeurs_id, datemodification, code, raisonsociale) VALUES ('bbbbbbbb-4444-5555-6666-bbbbbbbbbbbb', '2025-06-03', 'D003', 'Distributeur Gamma') ON CONFLICT DO NOTHING;
INSERT INTO document.distributeurs (distributeurs_id, datemodification, code, raisonsociale) VALUES ('c3d4e5f6-7890-ab12-c3d4-e5f67890abcd', '2025-06-04', 'D004', 'Distributeur Delta') ON CONFLICT DO NOTHING;


--
-- TOC entry 5446 (class 0 OID 16604)
-- Dependencies: 231
-- Data for Name: docetats; Type: TABLE DATA; Schema: document; Owner: postgres
--

INSERT INTO document.docetats (id, ordre, datecreation, datemodification, validations_id, etats_id, documents_id, etapes_id) VALUES ('be8ef4af-d5a2-4254-cd79-af860672553e', 15, '2022-03-02', '2022-10-02', '1901bd80-f71b-498b-b247-e7b9bbb3f602', 'e190615e-1101-7209-9932-7020bbd556f1', '0190615e-1101-7209-9932-7020bbd556f1', '1901bd79-f71b-498b-b247-e7b9bbb3f600') ON CONFLICT DO NOTHING;
INSERT INTO document.docetats (id, ordre, datecreation, datemodification, validations_id, etats_id, documents_id, etapes_id) VALUES ('be8ef4af-d5a2-4254-cd79-af860672554e', 10, '2002-01-02', '2022-11-02', '1901bd80-f71b-498b-b247-e7b9bbb3f601', 'e190615e-1101-7209-9932-7020bbd556f2', '0190615e-1101-7209-9932-7020bbd556f2', '1901bd79-f71b-498b-b247-e7b9bbb3f601') ON CONFLICT DO NOTHING;
INSERT INTO document.docetats (id, ordre, datecreation, datemodification, validations_id, etats_id, documents_id, etapes_id) VALUES ('be8ef4af-d5a2-4254-cd79-af860672555e', 11, '2014-01-12', '2022-02-02', '1901bd80-f71b-498b-b247-e7b9bbb3f600', 'e190615e-1101-7209-9932-7020bbd556f3', '0190615e-1101-7209-9932-7020bbd556f3', '1901bd79-f71b-498b-b247-e7b9bbb3f602') ON CONFLICT DO NOTHING;
INSERT INTO document.docetats (id, ordre, datecreation, datemodification, validations_id, etats_id, documents_id, etapes_id) VALUES ('be8ef4af-d5a2-4254-cd79-af860672556e', 12, '2023-10-25', '2024-03-02', '1901bd80-f71b-498b-b247-e7b9bbb3f603', 'e190615e-1101-7209-9932-7020bbd556f4', '0190615e-1101-7209-9932-7020bbd556f4', '1901bd79-f71b-498b-b247-e7b9bbb3f602') ON CONFLICT DO NOTHING;
INSERT INTO document.docetats (id, ordre, datecreation, datemodification, validations_id, etats_id, documents_id, etapes_id) VALUES ('be8ef4af-d5a2-4254-cd79-af860672557e', 16, '2020-11-15', '2024-05-02', '1901bd80-f71b-498b-b247-e7b9bbb3f604', 'e190615e-1101-7209-9932-7020bbd556f4', '0190615e-1101-7209-9932-7020bbd556f5', '1901bd79-f71b-498b-b247-e7b9bbb3f600') ON CONFLICT DO NOTHING;


--
-- TOC entry 5447 (class 0 OID 16610)
-- Dependencies: 232
-- Data for Name: docetats_predecesseurs; Type: TABLE DATA; Schema: document; Owner: postgres
--

INSERT INTO document.docetats_predecesseurs (docetats_id, predecesseur_id) VALUES ('be8ef4af-d5a2-4254-cd79-af860672553e', 'be8ef4af-d5a2-4254-cd79-af860672555e') ON CONFLICT DO NOTHING;
INSERT INTO document.docetats_predecesseurs (docetats_id, predecesseur_id) VALUES ('be8ef4af-d5a2-4254-cd79-af860672554e', 'be8ef4af-d5a2-4254-cd79-af860672556e') ON CONFLICT DO NOTHING;


--
-- TOC entry 5448 (class 0 OID 16617)
-- Dependencies: 233
-- Data for Name: documents; Type: TABLE DATA; Schema: document; Owner: postgres
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
-- TOC entry 5449 (class 0 OID 16624)
-- Dependencies: 234
-- Data for Name: documentspromotions; Type: TABLE DATA; Schema: document; Owner: postgres
--

INSERT INTO document.documentspromotions (documents_id, promotions_id) VALUES ('0190615e-1101-7209-9932-7020bbd556f1', '1979bd79-f81b-498b-b247-e7b9bbb3f600') ON CONFLICT DO NOTHING;
INSERT INTO document.documentspromotions (documents_id, promotions_id) VALUES ('0190615e-1101-7209-9932-7020bbd556f8', '1979bd79-f81b-498b-b247-e7b9bbb3f602') ON CONFLICT DO NOTHING;
INSERT INTO document.documentspromotions (documents_id, promotions_id) VALUES ('0190615e-1101-7209-9932-7020bbd556f5', '1979bd79-f81b-498b-b247-e7b9bbb3f601') ON CONFLICT DO NOTHING;


--
-- TOC entry 5450 (class 0 OID 16631)
-- Dependencies: 235
-- Data for Name: elements; Type: TABLE DATA; Schema: document; Owner: postgres
--

INSERT INTO document.elements (id, libelle, etat, datesouscription, datemodification, menus_id, elementsbases_id) VALUES ('z6qrs7yo-5y6z-42d4-1940-3p2q1r0s9t8u', 'Tableau de bord principal', true, '2024-01-01', '2024-01-01', 'p6ghi7oe-5o6p-483a-3940-3f2g1h0i9j8k', 'u1lmn2tj-0t1u-4d8f-6495-8k7l6m5n4o3p') ON CONFLICT DO NOTHING;
INSERT INTO document.elements (id, libelle, etat, datesouscription, datemodification, menus_id, elementsbases_id) VALUES ('a7rst8zp-6z7a-43e5-0859-2q1r0s9t8u7v', 'Ajouter un utilisateur', true, '2024-01-01', '2024-01-01', 'q7hij8pf-6p7q-494b-2859-2g1h0i9j8k7l', 'v2mno3uk-1u2v-4e90-5304-7l6m5n4o3p2q') ON CONFLICT DO NOTHING;
INSERT INTO document.elements (id, libelle, etat, datesouscription, datemodification, menus_id, elementsbases_id) VALUES ('b8stu9aq-7a8b-44f6-7768-1r0s9t8u7v6w', 'Supprimer un groupe', true, '2024-01-01', '2024-01-01', 'r8ijk9qg-7q8r-4a5c-1768-1h0i9j8k7l6m', 'w3nop4vl-2v3w-4fa1-4213-6m5n4o3p2q1r') ON CONFLICT DO NOTHING;
INSERT INTO document.elements (id, libelle, etat, datesouscription, datemodification, menus_id, elementsbases_id) VALUES ('c9tuv0br-8b9c-4507-6677-0s9t8u7v6w5x', 'Modifier un menu', true, '2024-01-01', '2024-01-01', 's9jkl0rh-8r9s-4b6d-0677-0i9j8k7l6m5n', 'x4opq5wm-3w4x-40b2-3122-5n4o3p2q1r0s') ON CONFLICT DO NOTHING;
INSERT INTO document.elements (id, libelle, etat, datesouscription, datemodification, menus_id, elementsbases_id) VALUES ('d0uvw1cs-9c0d-4618-5586-9t8u7v6w5x4y', 'Afficher les éléments', true, '2024-01-01', '2024-01-01', 't0klm1si-9s0t-4c7e-7586-9j8k7l6m5n4o', 'y5pqr6xn-4x5y-41c3-2031-4o3p2q1r0s9t') ON CONFLICT DO NOTHING;


--
-- TOC entry 5451 (class 0 OID 16637)
-- Dependencies: 236
-- Data for Name: elementsbaselanques; Type: TABLE DATA; Schema: document; Owner: postgres
--

INSERT INTO document.elementsbaselanques (langues_id, elementsbases_id, valeurlibelle) VALUES ('e1vwx2dt-0d1e-4729-4495-8u7v6w5x4y3z', 'u1lmn2tj-0t1u-4d8f-6495-8k7l6m5n4o3p', 'Tableau de bord') ON CONFLICT DO NOTHING;
INSERT INTO document.elementsbaselanques (langues_id, elementsbases_id, valeurlibelle) VALUES ('f2wxy3eu-1e2f-483a-3304-7v6w5x4y3z2a', 'u1lmn2tj-0t1u-4d8f-6495-8k7l6m5n4o3p', 'Dashboard') ON CONFLICT DO NOTHING;
INSERT INTO document.elementsbaselanques (langues_id, elementsbases_id, valeurlibelle) VALUES ('e1vwx2dt-0d1e-4729-4495-8u7v6w5x4y3z', 'v2mno3uk-1u2v-4e90-5304-7l6m5n4o3p2q', 'Gestion des utilisateurs') ON CONFLICT DO NOTHING;
INSERT INTO document.elementsbaselanques (langues_id, elementsbases_id, valeurlibelle) VALUES ('f2wxy3eu-1e2f-483a-3304-7v6w5x4y3z2a', 'v2mno3uk-1u2v-4e90-5304-7l6m5n4o3p2q', 'User Management') ON CONFLICT DO NOTHING;
INSERT INTO document.elementsbaselanques (langues_id, elementsbases_id, valeurlibelle) VALUES ('e1vwx2dt-0d1e-4729-4495-8u7v6w5x4y3z', 'w3nop4vl-2v3w-4fa1-4213-6m5n4o3p2q1r', 'Gestion des groupes') ON CONFLICT DO NOTHING;


--
-- TOC entry 5452 (class 0 OID 16644)
-- Dependencies: 237
-- Data for Name: elementsbases; Type: TABLE DATA; Schema: document; Owner: postgres
--

INSERT INTO document.elementsbases (id, libelle, etat, datesouscription, datemodification, moduleangular, element_id) VALUES ('u1lmn2tj-0t1u-4d8f-6495-8k7l6m5n4o3p', 'Tableau de bord', true, '2024-01-01', '2024-01-01', 'dashboard', 'a7rst8zp-6z7a-43e5-0859-2q1r0s9t8u7v') ON CONFLICT DO NOTHING;
INSERT INTO document.elementsbases (id, libelle, etat, datesouscription, datemodification, moduleangular, element_id) VALUES ('v2mno3uk-1u2v-4e90-5304-7l6m5n4o3p2q', 'Gestion des utilisateurs', true, '2024-01-01', '2024-01-01', 'userManagement', 'b8stu9aq-7a8b-44f6-7768-1r0s9t8u7v6w') ON CONFLICT DO NOTHING;
INSERT INTO document.elementsbases (id, libelle, etat, datesouscription, datemodification, moduleangular, element_id) VALUES ('w3nop4vl-2v3w-4fa1-4213-6m5n4o3p2q1r', 'Gestion des groupes', true, '2024-01-01', '2024-01-01', 'groupManagement', 'c9tuv0br-8b9c-4507-6677-0s9t8u7v6w5x') ON CONFLICT DO NOTHING;
INSERT INTO document.elementsbases (id, libelle, etat, datesouscription, datemodification, moduleangular, element_id) VALUES ('x4opq5wm-3w4x-40b2-3122-5n4o3p2q1r0s', 'Gestion des menus', true, '2024-01-01', '2024-01-01', 'menuManagement', 'd0uvw1cs-9c0d-4618-5586-9t8u7v6w5x4y') ON CONFLICT DO NOTHING;
INSERT INTO document.elementsbases (id, libelle, etat, datesouscription, datemodification, moduleangular, element_id) VALUES ('y5pqr6xn-4x5y-41c3-2031-4o3p2q1r0s9t', 'Gestion des éléments', true, '2024-01-01', '2024-01-01', 'elementManagement', 'z6qrs7yo-5y6z-42d4-1940-3p2q1r0s9t8u') ON CONFLICT DO NOTHING;


--
-- TOC entry 5453 (class 0 OID 16650)
-- Dependencies: 238
-- Data for Name: elementslangues; Type: TABLE DATA; Schema: document; Owner: postgres
--

INSERT INTO document.elementslangues (langues_id, elements_id, valeurlibelle) VALUES ('e1vwx2dt-0d1e-4729-4495-8u7v6w5x4y3z', 'z6qrs7yo-5y6z-42d4-1940-3p2q1r0s9t8u', 'Tableau de bord principal') ON CONFLICT DO NOTHING;
INSERT INTO document.elementslangues (langues_id, elements_id, valeurlibelle) VALUES ('f2wxy3eu-1e2f-483a-3304-7v6w5x4y3z2a', 'z6qrs7yo-5y6z-42d4-1940-3p2q1r0s9t8u', 'Main Dashboard') ON CONFLICT DO NOTHING;
INSERT INTO document.elementslangues (langues_id, elements_id, valeurlibelle) VALUES ('e1vwx2dt-0d1e-4729-4495-8u7v6w5x4y3z', 'a7rst8zp-6z7a-43e5-0859-2q1r0s9t8u7v', 'Ajouter un utilisateur') ON CONFLICT DO NOTHING;
INSERT INTO document.elementslangues (langues_id, elements_id, valeurlibelle) VALUES ('f2wxy3eu-1e2f-483a-3304-7v6w5x4y3z2a', 'a7rst8zp-6z7a-43e5-0859-2q1r0s9t8u7v', 'Add User') ON CONFLICT DO NOTHING;
INSERT INTO document.elementslangues (langues_id, elements_id, valeurlibelle) VALUES ('e1vwx2dt-0d1e-4729-4495-8u7v6w5x4y3z', 'b8stu9aq-7a8b-44f6-7768-1r0s9t8u7v6w', 'Supprimer un groupe') ON CONFLICT DO NOTHING;


--
-- TOC entry 5454 (class 0 OID 16657)
-- Dependencies: 239
-- Data for Name: etapes; Type: TABLE DATA; Schema: document; Owner: postgres
--

INSERT INTO document.etapes (id, libelle, etat, datemodification, parcours_id) VALUES ('1901bd79-f71b-498b-b247-e7b9bbb3f600', 'Paracetamol', 'true', '2022-01-02', '1900bd79-f71b-498b-b247-e7b9bbb3f602') ON CONFLICT DO NOTHING;
INSERT INTO document.etapes (id, libelle, etat, datemodification, parcours_id) VALUES ('1901bd79-f71b-498b-b247-e7b9bbb3f601', 'Cartouche d''encre', 'true', '2020-01-08', '1900bd79-f71b-498b-b247-e7b9bbb3f600') ON CONFLICT DO NOTHING;
INSERT INTO document.etapes (id, libelle, etat, datemodification, parcours_id) VALUES ('1901bd79-f71b-498b-b247-e7b9bbb3f602', 'Scanner', 'true', '2014-01-04', '1900bd79-f71b-498b-b247-e7b9bbb3f601') ON CONFLICT DO NOTHING;


--
-- TOC entry 5455 (class 0 OID 16663)
-- Dependencies: 240
-- Data for Name: etats; Type: TABLE DATA; Schema: document; Owner: postgres
--

INSERT INTO document.etats (id, libelle, description, datecreation, datemodification) VALUES ('e190615e-1101-7209-9932-7020bbd556f2', 'Valide', 'Valide', '2022-01-01', '2022-01-02') ON CONFLICT DO NOTHING;
INSERT INTO document.etats (id, libelle, description, datecreation, datemodification) VALUES ('e190615e-1101-7209-9932-7020bbd556f3', 'Rejete', 'Rejete', '2022-01-01', '2022-01-02') ON CONFLICT DO NOTHING;
INSERT INTO document.etats (id, libelle, description, datecreation, datemodification) VALUES ('e190615e-1101-7209-9932-7020bbd556f4', 'En attente', 'En attente', '2022-01-01', '2022-01-02') ON CONFLICT DO NOTHING;
INSERT INTO document.etats (id, libelle, description, datecreation, datemodification) VALUES ('e190615e-1101-7209-9932-7020bbd556f1', 'En cours', 'En cours d''utilisation', '2022-01-01', NULL) ON CONFLICT DO NOTHING;


--
-- TOC entry 5456 (class 0 OID 16669)
-- Dependencies: 241
-- Data for Name: exemplaires; Type: TABLE DATA; Schema: document; Owner: postgres
--

INSERT INTO document.exemplaires (id, personnes_id, documents_id) VALUES ('a1f1e3b0-4c6d-4e5e-9b7a-2a8b9b9c8d6f', '44444444-dddd-eeee-ffff-444444444444', NULL) ON CONFLICT DO NOTHING;
INSERT INTO document.exemplaires (id, personnes_id, documents_id) VALUES ('a1f1e4b0-4c6d-4e5e-9b7a-2a8b9b9c8d6f', '11111111-aaaa-bbbb-cccc-111111111111', NULL) ON CONFLICT DO NOTHING;
INSERT INTO document.exemplaires (id, personnes_id, documents_id) VALUES ('a1f8e3b0-4c6d-4e5e-9b7a-2a8b9b9c8d6f', 'b2c3d4e5-f678-90ab-12c3-d4e5f67890ab', NULL) ON CONFLICT DO NOTHING;
INSERT INTO document.exemplaires (id, personnes_id, documents_id) VALUES ('b3a7c8d6-1e2f-3d4c-5b6a-9e8f0a7b6c5d', '4390615e-1101-7209-9932-7020bbd556f2', NULL) ON CONFLICT DO NOTHING;
INSERT INTO document.exemplaires (id, personnes_id, documents_id) VALUES ('d9e2f1c2-8b3a-4f6c-9d5e-1a7b8a7c6d5e', '1e8b6f56-7525-45bf-aa7b-96c0a9ae4198', NULL) ON CONFLICT DO NOTHING;
INSERT INTO document.exemplaires (id, personnes_id, documents_id) VALUES ('f0a7b6c5-d4e3f2a1-8b9c-7d6e-5f4a3b2c1e0d', '99999999-2222-3333-4444-999999999999', NULL) ON CONFLICT DO NOTHING;


--
-- TOC entry 5457 (class 0 OID 16676)
-- Dependencies: 242
-- Data for Name: familles; Type: TABLE DATA; Schema: document; Owner: postgres
--

INSERT INTO document.familles (id, libelle, description, etat, datecreation, datemodification) VALUES ('f190615e-1101-7209-9932-7020bbd556f1', 'Medicaments', 'Medicaments', true, '2022-01-01', '2022-01-02') ON CONFLICT DO NOTHING;
INSERT INTO document.familles (id, libelle, description, etat, datecreation, datemodification) VALUES ('f190615e-1101-7209-9932-7020bbd556f2', 'Consommables Informatiques', 'Consommables Informatiques', true, '2022-01-01', '2022-01-02') ON CONFLICT DO NOTHING;
INSERT INTO document.familles (id, libelle, description, etat, datecreation, datemodification) VALUES ('f190615e-1101-7209-9932-7020bbd556f3', 'BioMedical', 'BioMedical', true, '2022-01-01', '2022-01-02') ON CONFLICT DO NOTHING;
INSERT INTO document.familles (id, libelle, description, etat, datecreation, datemodification) VALUES ('f190615e-1101-7209-9932-7020bbd556f4', 'Accessoirestest', 'Accessoires', true, '2022-01-01', NULL) ON CONFLICT DO NOTHING;


--
-- TOC entry 5458 (class 0 OID 16682)
-- Dependencies: 243
-- Data for Name: famillespromotions; Type: TABLE DATA; Schema: document; Owner: postgres
--

INSERT INTO document.famillespromotions (familles_id, promotions_id) VALUES ('f190615e-1101-7209-9932-7020bbd556f1', '1979bd79-f81b-498b-b247-e7b9bbb3f602') ON CONFLICT DO NOTHING;
INSERT INTO document.famillespromotions (familles_id, promotions_id) VALUES ('f190615e-1101-7209-9932-7020bbd556f3', '1979bd79-f81b-498b-b247-e7b9bbb3f601') ON CONFLICT DO NOTHING;
INSERT INTO document.famillespromotions (familles_id, promotions_id) VALUES ('f190615e-1101-7209-9932-7020bbd556f4', '1979bd79-f81b-498b-b247-e7b9bbb3f600') ON CONFLICT DO NOTHING;


--
-- TOC entry 5459 (class 0 OID 16689)
-- Dependencies: 244
-- Data for Name: filesattentes; Type: TABLE DATA; Schema: document; Owner: postgres
--

INSERT INTO document.filesattentes (id, datecreation, datemodification, etat, services_id) VALUES ('f190615e-1101-7209-9932-7020bbd556f1', '2022-01-01', '2022-01-02', true, '2190615e-1101-7209-9932-7020bbd556f1') ON CONFLICT DO NOTHING;
INSERT INTO document.filesattentes (id, datecreation, datemodification, etat, services_id) VALUES ('f190615e-1101-7209-9932-7020bbd556f2', '2022-01-01', '2022-01-02', true, '2190615e-1101-7209-9932-7020bbd556f2') ON CONFLICT DO NOTHING;
INSERT INTO document.filesattentes (id, datecreation, datemodification, etat, services_id) VALUES ('f190615e-1101-7209-9932-7020bbd556f3', '2022-01-01', '2022-01-02', true, '2190615e-1101-7209-9932-7020bbd556f3') ON CONFLICT DO NOTHING;


--
-- TOC entry 5460 (class 0 OID 16695)
-- Dependencies: 245
-- Data for Name: groupes; Type: TABLE DATA; Schema: document; Owner: postgres
--

INSERT INTO document.groupes (id, libelle, etat, datecreation, datemodification, menus_id) VALUES ('f6jkl74e-5e6f-4e90-596a-3b2a1e0d9c8a', 'Administrateurs', true, '2024-01-01', '2024-01-01', 'p6ghi7oe-5o6p-483a-3940-3f2g1h0i9j8k') ON CONFLICT DO NOTHING;
INSERT INTO document.groupes (id, libelle, etat, datecreation, datemodification, menus_id) VALUES ('g7mno85f-6f7g-4fa1-4859-2a1e0d9c8a7b', 'Utilisateurs', true, '2024-01-01', '2024-01-01', 'q7hij8pf-6p7q-494b-2859-2g1h0i9j8k7l') ON CONFLICT DO NOTHING;
INSERT INTO document.groupes (id, libelle, etat, datecreation, datemodification, menus_id) VALUES ('h8pqr96g-7g8h-40b2-5748-1e0d9c8a7b6c', 'Invités', true, '2024-01-01', '2024-01-01', 'r8ijk9qg-7q8r-4a5c-1768-1h0i9j8k7l6m') ON CONFLICT DO NOTHING;
INSERT INTO document.groupes (id, libelle, etat, datecreation, datemodification, menus_id) VALUES ('i9stu07h-8h9i-41c3-6637-0d9c8a7b6c5d', 'Managers', true, '2024-01-01', '2024-01-01', 's9jkl0rh-8r9s-4b6d-0677-0i9j8k7l6m5n') ON CONFLICT DO NOTHING;
INSERT INTO document.groupes (id, libelle, etat, datecreation, datemodification, menus_id) VALUES ('j0vwx18i-9i0j-42d4-7526-9c8a7b6c5d4e', 'Techniciens', true, '2024-01-01', '2024-01-01', 't0klm1si-9s0t-4c7e-7586-9j8k7l6m5n4o') ON CONFLICT DO NOTHING;
INSERT INTO document.groupes (id, libelle, etat, datecreation, datemodification, menus_id) VALUES ('f6jkl74e-5e6f-4e90-596a-3b2a1e0a7c8a', 'Admin', true, '2024-01-01', '2024-01-01', 'p6ghi7oe-5o6p-483a-3940-3f2g1h779j8k') ON CONFLICT DO NOTHING;


--
-- TOC entry 5461 (class 0 OID 16701)
-- Dependencies: 246
-- Data for Name: jouerroles; Type: TABLE DATA; Schema: document; Owner: postgres
--

INSERT INTO document.jouerroles (id, etat, datecreation, datemodification, datefin, datedebut, personnels_id, roles_id) VALUES ('d76fd017-cceb-4926-8705-380b08ad9c6a', NULL, '2022-01-01', '2022-01-02', NULL, '2024-01-01', '4390615e-1101-7209-9932-7020bbd556f1', '5190615e-1101-7209-9932-7020bbd556f1') ON CONFLICT DO NOTHING;
INSERT INTO document.jouerroles (id, etat, datecreation, datemodification, datefin, datedebut, personnels_id, roles_id) VALUES ('d39bcc09-ffe6-48d1-8582-f1173671d59f', NULL, '2022-01-01', '2022-01-02', NULL, '2024-01-01', '4390615e-1101-7209-9932-7020bbd556f2', '6130615e-1101-7209-9932-7020bbd556f2') ON CONFLICT DO NOTHING;
INSERT INTO document.jouerroles (id, etat, datecreation, datemodification, datefin, datedebut, personnels_id, roles_id) VALUES ('9183d626-0c1a-4f70-8556-1c417d5feb91', NULL, '2022-01-01', '2022-01-02', NULL, '2024-01-01', '4390615e-1101-7209-9932-7020bbd556f3', '6191615e-1101-7209-9932-7020bbd556f3') ON CONFLICT DO NOTHING;


--
-- TOC entry 5462 (class 0 OID 16707)
-- Dependencies: 247
-- Data for Name: langues; Type: TABLE DATA; Schema: document; Owner: postgres
--

INSERT INTO document.langues (id, libelle, etat, datesouscription, datemodification) VALUES ('e1vwx2dt-0d1e-4729-4495-8u7v6w5x4y3z', 'Français', true, '2024-01-01', '2024-01-01') ON CONFLICT DO NOTHING;
INSERT INTO document.langues (id, libelle, etat, datesouscription, datemodification) VALUES ('f2wxy3eu-1e2f-483a-3304-7v6w5x4y3z2a', 'Anglais', true, '2024-01-01', '2024-01-01') ON CONFLICT DO NOTHING;
INSERT INTO document.langues (id, libelle, etat, datesouscription, datemodification) VALUES ('g3xyz4fv-2f3g-494b-2213-6w5x4y3z2a1b', 'Espagnol', true, '2024-01-01', '2024-01-01') ON CONFLICT DO NOTHING;
INSERT INTO document.langues (id, libelle, etat, datesouscription, datemodification) VALUES ('h4yza5gw-3g4h-4a5c-1122-5x4y3z2a1b0c', 'Allemand', true, '2024-01-01', '2024-01-01') ON CONFLICT DO NOTHING;
INSERT INTO document.langues (id, libelle, etat, datesouscription, datemodification) VALUES ('i5zab6hx-4h5i-4b6d-0031-4y3z2a1b0c9d', 'Italien', true, '2024-01-01', '2024-01-01') ON CONFLICT DO NOTHING;


--
-- TOC entry 5463 (class 0 OID 16713)
-- Dependencies: 248
-- Data for Name: menus; Type: TABLE DATA; Schema: document; Owner: postgres
--

INSERT INTO document.menus (id, etat, datecreation, utilisateurs_id, groupes_id) VALUES ('p6ghi7oe-5o6p-483a-3940-3f2g1h0i9j8k', true, '2024-01-01', 'k1yzabc2j-0j1k-43e5-8415-8a7b6c5d4e3f', 'f6jkl74e-5e6f-4e90-596a-3b2a1e0d9c8a') ON CONFLICT DO NOTHING;
INSERT INTO document.menus (id, etat, datecreation, utilisateurs_id, groupes_id) VALUES ('q7hij8pf-6p7q-494b-2859-2g1h0i9j8k7l', true, '2024-01-01', 'l2bcd3ka-1k2l-44f6-9304-7b6c5d4e3f2g', 'g7mno85f-6f7g-4fa1-4859-2a1e0d9c8a7b') ON CONFLICT DO NOTHING;
INSERT INTO document.menus (id, etat, datecreation, utilisateurs_id, groupes_id) VALUES ('r8ijk9qg-7q8r-4a5c-1768-1h0i9j8k7l6m', true, '2024-01-01', 'm3cde4lb-2l3m-4507-0213-6c5d4e3f2g1h', 'h8pqr96g-7g8h-40b2-5748-1e0d9c8a7b6c') ON CONFLICT DO NOTHING;
INSERT INTO document.menus (id, etat, datecreation, utilisateurs_id, groupes_id) VALUES ('s9jkl0rh-8r9s-4b6d-0677-0i9j8k7l6m5n', true, '2024-01-01', 'n4efg5mc-3m4n-4618-1122-5d4e3f2g1h0i', 'i9stu07h-8h9i-41c3-6637-0d9c8a7b6c5d') ON CONFLICT DO NOTHING;
INSERT INTO document.menus (id, etat, datecreation, utilisateurs_id, groupes_id) VALUES ('t0klm1si-9s0t-4c7e-7586-9j8k7l6m5n4o', true, '2024-01-01', 'o5fgh6nd-4n5o-4729-2031-4e3f2g1h0i9j', 'j0vwx18i-9i0j-42d4-7526-9c8a7b6c5d4e') ON CONFLICT DO NOTHING;
INSERT INTO document.menus (id, etat, datecreation, utilisateurs_id, groupes_id) VALUES ('p6ghi7oe-5o6p-483a-3940-3f2g1h779j8k', true, '2024-01-01', '0190615e-1101-7209-9932-7020bbd55714', 'f6jkl74e-5e6f-4e90-596a-3b2a1e0a7c8a') ON CONFLICT DO NOTHING;


--
-- TOC entry 5464 (class 0 OID 16719)
-- Dependencies: 249
-- Data for Name: missions; Type: TABLE DATA; Schema: document; Owner: postgres
--

INSERT INTO document.missions (id, libelle, description, etat, datecreation, datemodification, services_id, idlogin) VALUES ('3190615e-1101-7209-9932-7020bbd556f1', 'Encaissement', 'recu de paiement lié à une mission', true, '2000-03-07', '1990-03-07', '2190615e-1101-7209-9932-7020bbd556f1', NULL) ON CONFLICT DO NOTHING;
INSERT INTO document.missions (id, libelle, description, etat, datecreation, datemodification, services_id, idlogin) VALUES ('3190615e-1101-7209-9932-7020bbd556f2', 'Resultat Labo', 'Communiquer les résultats du labo aux patients', true, '2000-03-07', '1990-03-07', '2190615e-1101-7209-9932-7020bbd556f1', NULL) ON CONFLICT DO NOTHING;
INSERT INTO document.missions (id, libelle, description, etat, datecreation, datemodification, services_id, idlogin) VALUES ('3190615e-1101-7209-9932-7020bbd556f4', 'Prelevement Labo', 'Prélévement fait par laboratoire', true, '2000-03-07', '1990-03-07', '2190615e-1101-7209-9932-7020bbd556f2', NULL) ON CONFLICT DO NOTHING;
INSERT INTO document.missions (id, libelle, description, etat, datecreation, datemodification, services_id, idlogin) VALUES ('3190615e-1101-7209-9932-7020bbd556f3', 'Hospitalisation8', 'bon d''entrée et de sortie est une mission', true, '2000-03-07', NULL, '2190615e-1101-7209-9932-7020bbd556f2', NULL) ON CONFLICT DO NOTHING;
INSERT INTO document.missions (id, libelle, description, etat, datecreation, datemodification, services_id, idlogin) VALUES ('3190615e-1101-7209-9932-7020bbd556f5', 'Consultation et Spécialiste', 'Consultation faite par un médecin', true, '2000-03-07', NULL, '2190615e-1101-7209-9932-7020bbd556f3', NULL) ON CONFLICT DO NOTHING;
INSERT INTO document.missions (id, libelle, description, etat, datecreation, datemodification, services_id, idlogin) VALUES ('3190615e-1101-7209-9932-7020bbd556f6', 'Consultation et Spécialiste', 'Consultation faite par une infirmière', true, '2000-03-07', NULL, '2190615e-1101-7209-9932-7020bbd556f3', NULL) ON CONFLICT DO NOTHING;


--
-- TOC entry 5465 (class 0 OID 16727)
-- Dependencies: 250
-- Data for Name: mouvementcaisses; Type: TABLE DATA; Schema: document; Owner: postgres
--

INSERT INTO document.mouvementcaisses (id, montant, moyenpaiement, referencepaiement, detailjson, etat, typemvt, libelle, caisses_id, comptes_id, personnels_id, exemplaires_id, datecreation) VALUES ('97d314f5-84ad-77cd-874d-9c55f0e45790', 45.21, 'sur commande', NULL, NULL, NULL, NULL, NULL, 'a1f8e3b0-4c6d-4e5e-ab7a-2a8b9b9c8d6f', 'a1f8e3b0-4c6d-4e5e-7b7a-2a8b9b9c8d6f', '4390615e-1101-7209-9932-7020bbd556f1', 'a1f8e3b0-4c6d-4e5e-9b7a-2a8b9b9c8d6f', '2022-01-02') ON CONFLICT DO NOTHING;
INSERT INTO document.mouvementcaisses (id, montant, moyenpaiement, referencepaiement, detailjson, etat, typemvt, libelle, caisses_id, comptes_id, personnels_id, exemplaires_id, datecreation) VALUES ('bfee750d-9b8c-7475-9373-08eff9b3ccb7', 1258.36, 'vente à credit', NULL, NULL, NULL, NULL, NULL, 'd9e2f1c2-8b3a-4f6c-bd5e-1a7b8a7c6d5e', 'd9e2f1c2-8b3a-4f6c-7d5e-1a7b8a7c6d5e', '4390615e-1101-7209-9932-7020bbd556f2', 'd9e2f1c2-8b3a-4f6c-9d5e-1a7b8a7c6d5e', '2022-01-02') ON CONFLICT DO NOTHING;
INSERT INTO document.mouvementcaisses (id, montant, moyenpaiement, referencepaiement, detailjson, etat, typemvt, libelle, caisses_id, comptes_id, personnels_id, exemplaires_id, datecreation) VALUES ('0e7cea07-d09e-7f79-be6c-6dd27aecbce6', 1548.21, 'achat express', NULL, NULL, NULL, NULL, NULL, 'b3a7c8d6-1e2f-3d4c-cb6a-9e8f0a7b6c5d', 'b3a7c8d6-1e2f-3d4c-7b6a-9e8f0a7b6c5d', '4390615e-1101-7209-9932-7020bbd556f3', 'b3a7c8d6-1e2f-3d4c-5b6a-9e8f0a7b6c5d', '2022-01-02') ON CONFLICT DO NOTHING;
INSERT INTO document.mouvementcaisses (id, montant, moyenpaiement, referencepaiement, detailjson, etat, typemvt, libelle, caisses_id, comptes_id, personnels_id, exemplaires_id, datecreation) VALUES ('decaedc8-a908-7cad-bdd6-0403a2614f22', 4563.21, 'inventaire', NULL, NULL, NULL, NULL, NULL, 'f0a7b6c5-d4e3f2a1-8d9c-7d6e-5f4a3b2c1e0d', 'f0a7b6c5-d4e3f2a1-7b9c-7d6e-5f4a3b2c1e0d', '4390615e-1101-7209-9932-7020bbd556f3', 'f0a7b6c5-d4e3f2a1-8b9c-7d6e-5f4a3b2c1e0d', '2022-01-02') ON CONFLICT DO NOTHING;
INSERT INTO document.mouvementcaisses (id, montant, moyenpaiement, referencepaiement, detailjson, etat, typemvt, libelle, caisses_id, comptes_id, personnels_id, exemplaires_id, datecreation) VALUES ('0e7cea07-d09e-7f79-be6c-6dd27aecbc47', 5245.54, 'sur commande', NULL, NULL, NULL, NULL, NULL, 'b3a7c8d6-1e2f-3d4c-cb6a-9e8f0a7b6c5d', 'd301ff83-1b62-4e6d-aa23-57c7825bcd57', '4390615e-1101-7209-9932-7020bbd556f3', 'a1f1e3b0-4c6d-4e5e-9b7a-2a8b9b9c8d6f', '2022-01-02') ON CONFLICT DO NOTHING;
INSERT INTO document.mouvementcaisses (id, montant, moyenpaiement, referencepaiement, detailjson, etat, typemvt, libelle, caisses_id, comptes_id, personnels_id, exemplaires_id, datecreation) VALUES ('0e7cea07-d09e-7f79-be6c-6dd27aecbc14', 42542453.54, 'sur commande', NULL, NULL, NULL, NULL, NULL, 'f0a7b6c5-d4e3f2a1-8d9c-7d6e-5f4a3b2c1e0d', 'adbff692-418d-43ab-a196-fcc3114b2daa', '4390615e-1101-7209-9932-7020bbd556f1', 'a1f1e4b0-4c6d-4e5e-9b7a-2a8b9b9c8d6f', '2022-01-02') ON CONFLICT DO NOTHING;


--
-- TOC entry 5466 (class 0 OID 16733)
-- Dependencies: 251
-- Data for Name: mouvements; Type: TABLE DATA; Schema: document; Owner: postgres
--

INSERT INTO document.mouvements (id, description, quantite, prix, datecreation, dateperemption, datemodification, ressources_id, distributeurs_id) VALUES ('0e7cea07-d09e-4f79-be6c-6dd27aecbce6', 'achat express', 5, 2000, '2022-01-01', NULL, '2022-01-02', '6190615e-1101-7209-9932-7020bbd556f3', 'bbbbbbbb-4444-5555-6666-bbbbbbbbbbbb') ON CONFLICT DO NOTHING;
INSERT INTO document.mouvements (id, description, quantite, prix, datecreation, dateperemption, datemodification, ressources_id, distributeurs_id) VALUES ('97d314f5-84ad-47cd-874d-9c55f0e45790', 'sur commande', 10, 5000, '2022-01-01', NULL, '2022-01-02', '6190615e-1101-7209-9932-7020bbd556f1', 'c3d4e5f6-7890-ab12-c3d4-e5f67890abcd') ON CONFLICT DO NOTHING;
INSERT INTO document.mouvements (id, description, quantite, prix, datecreation, dateperemption, datemodification, ressources_id, distributeurs_id) VALUES ('bfee750d-9b8c-4475-9373-08eff9b3ccb7', 'vente à credit', 20, 10000, '2022-01-01', NULL, '2022-01-02', '6190615e-1101-7209-9932-7020bbd556f2', '99999999-2222-3333-4444-999999999999') ON CONFLICT DO NOTHING;
INSERT INTO document.mouvements (id, description, quantite, prix, datecreation, dateperemption, datemodification, ressources_id, distributeurs_id) VALUES ('decaedc8-a908-4cad-bdd6-0403a2614f22', 'inventaire', 5, 2000, '2022-01-01', NULL, '2022-01-02', '6190615e-1101-7209-9932-7020bbd556f4', 'aaaaaaaa-3333-4444-5555-aaaaaaaaaaaa') ON CONFLICT DO NOTHING;


--
-- TOC entry 5467 (class 0 OID 16741)
-- Dependencies: 252
-- Data for Name: ordreetats; Type: TABLE DATA; Schema: document; Owner: postgres
--

INSERT INTO document.ordreetats (id, datecreation, datemodification, datefinvote, ordre, etats_id) VALUES ('97d314a5-84ad-47cd-874d-9c55f0e45790', '2022-01-01', '2022-01-02', '2022-01-02', 2, 'e190615e-1101-7209-9932-7020bbd556f3') ON CONFLICT DO NOTHING;
INSERT INTO document.ordreetats (id, datecreation, datemodification, datefinvote, ordre, etats_id) VALUES ('97d314a5-84ad-47cd-874d-9c55f0e45791', '2022-01-01', '2022-01-02', '2022-01-02', 5, 'e190615e-1101-7209-9932-7020bbd556f2') ON CONFLICT DO NOTHING;
INSERT INTO document.ordreetats (id, datecreation, datemodification, datefinvote, ordre, etats_id) VALUES ('97d314a5-84ad-47cd-874d-9c55f0e45792', '2022-01-01', '2022-01-02', '2022-01-02', 8, 'e190615e-1101-7209-9932-7020bbd556f1') ON CONFLICT DO NOTHING;


--
-- TOC entry 5468 (class 0 OID 16747)
-- Dependencies: 253
-- Data for Name: organisations; Type: TABLE DATA; Schema: document; Owner: postgres
--

INSERT INTO document.organisations (id, raisonsociale) VALUES ('a1eebc99-9c0b-4ef8-bb6d-6bb9bd380a13', 'Entreprise A') ON CONFLICT DO NOTHING;
INSERT INTO document.organisations (id, raisonsociale) VALUES ('b2fcd30a-1a2b-4a5c-9e8f-7d6a5b4c3d2e', 'Entreprise B') ON CONFLICT DO NOTHING;
INSERT INTO document.organisations (id, raisonsociale) VALUES ('c3abc41b-2b3c-4b6d-8a9b-6e5c4d3b2f1a', 'Association C') ON CONFLICT DO NOTHING;
INSERT INTO document.organisations (id, raisonsociale) VALUES ('d4def52c-3c4d-4c7e-7b8a-5d4b3c2a1e0d', 'Fondation D') ON CONFLICT DO NOTHING;
INSERT INTO document.organisations (id, raisonsociale) VALUES ('e5ghi63d-4d5e-4d8f-6a7b-4c3a2b1d0c9b', 'Collectivité E') ON CONFLICT DO NOTHING;


--
-- TOC entry 5469 (class 0 OID 16753)
-- Dependencies: 254
-- Data for Name: organiser; Type: TABLE DATA; Schema: document; Owner: postgres
--

INSERT INTO document.organiser (organisations_id, utilisateurs_id) VALUES ('a1eebc99-9c0b-4ef8-bb6d-6bb9bd380a13', 'k1yzabc2j-0j1k-43e5-8415-8a7b6c5d4e3f') ON CONFLICT DO NOTHING;
INSERT INTO document.organiser (organisations_id, utilisateurs_id) VALUES ('b2fcd30a-1a2b-4a5c-9e8f-7d6a5b4c3d2e', 'l2bcd3ka-1k2l-44f6-9304-7b6c5d4e3f2g') ON CONFLICT DO NOTHING;
INSERT INTO document.organiser (organisations_id, utilisateurs_id) VALUES ('c3abc41b-2b3c-4b6d-8a9b-6e5c4d3b2f1a', 'm3cde4lb-2l3m-4507-0213-6c5d4e3f2g1h') ON CONFLICT DO NOTHING;
INSERT INTO document.organiser (organisations_id, utilisateurs_id) VALUES ('d4def52c-3c4d-4c7e-7b8a-5d4b3c2a1e0d', 'n4efg5mc-3m4n-4618-1122-5d4e3f2g1h0i') ON CONFLICT DO NOTHING;
INSERT INTO document.organiser (organisations_id, utilisateurs_id) VALUES ('e5ghi63d-4d5e-4d8f-6a7b-4c3a2b1d0c9b', 'o5fgh6nd-4n5o-4729-2031-4e3f2g1h0i9j') ON CONFLICT DO NOTHING;
INSERT INTO document.organiser (organisations_id, utilisateurs_id) VALUES ('b2fcd30a-1a2b-4a5c-9e8f-7d6a5b4c3d2e', 'k1yzabc2j-0j1k-43e5-8415-8a7b6c5d4e3f') ON CONFLICT DO NOTHING;


--
-- TOC entry 5470 (class 0 OID 16760)
-- Dependencies: 255
-- Data for Name: parcours; Type: TABLE DATA; Schema: document; Owner: postgres
--

INSERT INTO document.parcours (id, libelle, datecreation, datemodification) VALUES ('1900bd79-f71b-498b-b247-e7b9bbb3f600', 'Paracetamol', '2022-01-02', '2022-01-02') ON CONFLICT DO NOTHING;
INSERT INTO document.parcours (id, libelle, datecreation, datemodification) VALUES ('1900bd79-f71b-498b-b247-e7b9bbb3f601', 'Cartouche d''encre', '2020-01-08', '2022-01-02') ON CONFLICT DO NOTHING;
INSERT INTO document.parcours (id, libelle, datecreation, datemodification) VALUES ('1900bd79-f71b-498b-b247-e7b9bbb3f602', 'Scanner', '2014-01-04', '2022-01-02') ON CONFLICT DO NOTHING;


--
-- TOC entry 5471 (class 0 OID 16766)
-- Dependencies: 256
-- Data for Name: personnels; Type: TABLE DATA; Schema: document; Owner: postgres
--

INSERT INTO document.personnels (personnels_id, dateentree, nom, datenaissance, telephone, datesortie, email, prenom, sexe, datemodification) VALUES ('4390615e-1101-7209-9932-7020bbd556f1', '2024-07-27', 'Tagne', '2000-04-10', '655455487', NULL, 'tagnewillie@gmail.com', 'Willy', 'M', NULL) ON CONFLICT DO NOTHING;
INSERT INTO document.personnels (personnels_id, dateentree, nom, datenaissance, telephone, datesortie, email, prenom, sexe, datemodification) VALUES ('4390615e-1101-7209-9932-7020bbd556f2', '2024-07-27', 'Peter', '2004-08-10', '655455487', NULL, 'peteralan@gmail.com', 'Alan', 'M', NULL) ON CONFLICT DO NOTHING;
INSERT INTO document.personnels (personnels_id, dateentree, nom, datenaissance, telephone, datesortie, email, prenom, sexe, datemodification) VALUES ('4390615e-1101-7209-9932-7020bbd556f3', '2024-07-27', 'Dombo', '2002-10-10', '655455487', NULL, 'dombogilles@gmail.com', 'Gilles', 'M', NULL) ON CONFLICT DO NOTHING;
INSERT INTO document.personnels (personnels_id, dateentree, nom, datenaissance, telephone, datesortie, email, prenom, sexe, datemodification) VALUES ('1e8b6f56-7525-45bf-aa7b-96c0a9ae4198', '2023-01-01', 'Tagne', '1980-01-01', NULL, '2025-08-26', NULL, 'Willy1', 'M', NULL) ON CONFLICT DO NOTHING;
INSERT INTO document.personnels (personnels_id, dateentree, nom, datenaissance, telephone, datesortie, email, prenom, sexe, datemodification) VALUES ('8a9b710a-a7fb-44ca-9587-b427b163cdab', '2023-01-01', 'kouam', '1980-01-01', NULL, '0002-11-30 BC', NULL, 'Esaie Ledoux', 'M', NULL) ON CONFLICT DO NOTHING;


--
-- TOC entry 5472 (class 0 OID 16772)
-- Dependencies: 257
-- Data for Name: personnes; Type: TABLE DATA; Schema: document; Owner: postgres
--

INSERT INTO document.personnes (id, adresse, mail, telephone, qrcodevalue, code, raisonsociale, datenaissance, datecreation, datemodification, comptes_id, type, ticket_id, etat) VALUES ('11111111-aaaa-bbbb-cccc-111111111111', '12 rue des Fleurs', 'alice@email.com', '0600000001', NULL, NULL, NULL, '1992-05-10', NULL, NULL, '00012f83-2a62-4e6d-aa23-57c7825bcd57', 'personnePhysique', 'be8ef47f-d5a2-4254-cd79-af860672553e', false) ON CONFLICT DO NOTHING;
INSERT INTO document.personnes (id, adresse, mail, telephone, qrcodevalue, code, raisonsociale, datenaissance, datecreation, datemodification, comptes_id, type, ticket_id, etat) VALUES ('33333333-cccc-dddd-eeee-333333333333', '56 boulevard St Michel', 'carole@email.com', '0600000003', NULL, NULL, NULL, '1979-07-15', NULL, NULL, '1518e585-f82a-4d5f-af1c-54f880d766d3', 'personnePhysique', 'be8ef47f-d5a2-4254-cd79-af860672556e', false) ON CONFLICT DO NOTHING;
INSERT INTO document.personnes (id, adresse, mail, telephone, qrcodevalue, code, raisonsociale, datenaissance, datecreation, datemodification, comptes_id, type, ticket_id, etat) VALUES ('bbbbbbbb-4444-5555-6666-bbbbbbbbbbbb', '7 boulevard Distribution', 'contact@dist3.com', '0800000003', 'QR011', 'DIST003', 'Distributeur Trois', NULL, '2025-06-04', NULL, 'a1f8e3b0-4c6d-4e5e-7b7a-2a8b9b9c8d6f', 'distributeur', NULL, true) ON CONFLICT DO NOTHING;
INSERT INTO document.personnes (id, adresse, mail, telephone, qrcodevalue, code, raisonsociale, datenaissance, datecreation, datemodification, comptes_id, type, ticket_id, etat) VALUES ('99999999-2222-3333-4444-999999999999', '5 rue du Commerce', 'contact@dist1.com', '0800000001', 'QR009', 'DIST001', 'Distributeur Un', NULL, '2025-06-04', NULL, 'adbff692-418d-43ab-a196-fcc3114b2daa', 'distributeur', NULL, true) ON CONFLICT DO NOTHING;
INSERT INTO document.personnes (id, adresse, mail, telephone, qrcodevalue, code, raisonsociale, datenaissance, datecreation, datemodification, comptes_id, type, ticket_id, etat) VALUES ('6b7894b5-1b07-487a-b8fe-d14ab61c1b2e', 'Bonaloka', 'kouamdoux@gmail.com', '697700650', NULL, NULL, NULL, NULL, NULL, NULL, 'f0a7b6c5-d4e3f2a1-7b9c-7d6e-5f4a3b2c1e0d', 'personnePhysique', 'f4d5e6g7-b8c9-7d01-2e3f-456780a12bcd', false) ON CONFLICT DO NOTHING;
INSERT INTO document.personnes (id, adresse, mail, telephone, qrcodevalue, code, raisonsociale, datenaissance, datecreation, datemodification, comptes_id, type, ticket_id, etat) VALUES ('44444444-dddd-eeee-ffff-444444444444', '78 impasse des Lilas', 'daniel@email.com', '0600000004', NULL, NULL, NULL, '1995-02-28', NULL, NULL, '1179bd79-f71b-498b-b247-e7b9bbb3f600', 'personnePhysique', 'g5e6f7h8-c9d0-8e12-3f4g-567801b23cde', false) ON CONFLICT DO NOTHING;
INSERT INTO document.personnes (id, adresse, mail, telephone, qrcodevalue, code, raisonsociale, datenaissance, datecreation, datemodification, comptes_id, type, ticket_id, etat) VALUES ('77777777-0000-1111-2222-777777777777', '3 boulevard des Affaires', 'contact@ent3.com', '0700000003', 'QR007', 'ENT003', 'Entreprise Gamma', NULL, '2025-06-04', NULL, NULL, 'personneMorale', NULL, false) ON CONFLICT DO NOTHING;
INSERT INTO document.personnes (id, adresse, mail, telephone, qrcodevalue, code, raisonsociale, datenaissance, datecreation, datemodification, comptes_id, type, ticket_id, etat) VALUES ('88888888-1111-2222-3333-888888888888', '4 place de la Victoire', 'contact@ent4.com', '0700000004', 'QR008', 'ENT004', 'Entreprise Delta', NULL, '2025-06-04', NULL, NULL, 'personneMorale', NULL, true) ON CONFLICT DO NOTHING;
INSERT INTO document.personnes (id, adresse, mail, telephone, qrcodevalue, code, raisonsociale, datenaissance, datecreation, datemodification, comptes_id, type, ticket_id, etat) VALUES ('aaaaaaaa-3333-4444-5555-aaaaaaaaaaaa', '6 avenue des Ventes', 'contact@dist2.com', '0800000002', 'QR010', 'DIST002', 'Distributeur Deux', NULL, '2025-06-04', NULL, NULL, 'distributeur', NULL, false) ON CONFLICT DO NOTHING;
INSERT INTO document.personnes (id, adresse, mail, telephone, qrcodevalue, code, raisonsociale, datenaissance, datecreation, datemodification, comptes_id, type, ticket_id, etat) VALUES ('22222222-bbbb-cccc-dddd-222222222222', '34 avenue Victor Hugo', 'bob@email.com', '0600000002', 'QR002', NULL, NULL, '1988-11-23', '2025-06-04', NULL, 'a1111111-1111-1111-1111-111111111111', 'personnePhysique', 'be8ef47f-d5a2-4254-cd79-af860672555e', false) ON CONFLICT DO NOTHING;
INSERT INTO document.personnes (id, adresse, mail, telephone, qrcodevalue, code, raisonsociale, datenaissance, datecreation, datemodification, comptes_id, type, ticket_id, etat) VALUES ('3b277afa-98fc-4b0f-9b16-1d2fb4aa8ec6', 'Bonaloka', 'kouamdoux@gmail.com', '697700650', NULL, NULL, NULL, NULL, NULL, NULL, 'b2222222-2222-2222-2222-222222222222', 'personnePhysique', 'be8ef47f-d5a2-4254-cd79-af860672557e', false) ON CONFLICT DO NOTHING;
INSERT INTO document.personnes (id, adresse, mail, telephone, qrcodevalue, code, raisonsociale, datenaissance, datecreation, datemodification, comptes_id, type, ticket_id, etat) VALUES ('8a9b710a-a7fb-44ca-9587-b427b163cdab', NULL, NULL, '697700650', NULL, NULL, NULL, NULL, NULL, NULL, 'b3a7c8d6-1e2f-3d4c-7b6a-9e8f0a7b6c5d', 'personnel', 'e3c4d5f6-a7b8-6c90-1d2e-3456f78901ab', false) ON CONFLICT DO NOTHING;
INSERT INTO document.personnes (id, adresse, mail, telephone, qrcodevalue, code, raisonsociale, datenaissance, datecreation, datemodification, comptes_id, type, ticket_id, etat) VALUES ('1e8b6f56-7525-45bf-aa7b-96c0a9ae4198', NULL, NULL, '89005864904', NULL, NULL, NULL, NULL, NULL, NULL, 'd301ff83-1b62-4e6d-aa23-57c7825bcd57', 'personnel', 'be8ef47f-d5a2-4254-cd79-af860672554e', false) ON CONFLICT DO NOTHING;
INSERT INTO document.personnes (id, adresse, mail, telephone, qrcodevalue, code, raisonsociale, datenaissance, datecreation, datemodification, comptes_id, type, ticket_id, etat) VALUES ('4390615e-1101-7209-9932-7020bbd556f1', NULL, 'tagnewillie@gmail.com', '655455487', NULL, NULL, NULL, NULL, '2024-07-27', NULL, 'd9e2f1c2-8b3a-4f6c-7d5e-1a7b8a7c6d5e', 'personnel', 'd2b3c4e5-f6a7-5b89-0c1d-2345e67890fa', true) ON CONFLICT DO NOTHING;
INSERT INTO document.personnes (id, adresse, mail, telephone, qrcodevalue, code, raisonsociale, datenaissance, datecreation, datemodification, comptes_id, type, ticket_id, etat) VALUES ('d4e5f678-90ab-12c3-d4e5-f67890abcdea', '12 place Centrale', 'contact@ent6.com', '0700000006', 'QR016', 'ENT006', 'Entreprise Zeta', NULL, '2025-06-04', NULL, NULL, 'personneMorale', NULL, true) ON CONFLICT DO NOTHING;
INSERT INTO document.personnes (id, adresse, mail, telephone, qrcodevalue, code, raisonsociale, datenaissance, datecreation, datemodification, comptes_id, type, ticket_id, etat) VALUES ('b2c3d4e5-f678-90ab-12c3-d4e5f67890ab', '10 avenue Libre', 'contact@ent5.com', '0700000005', 'QR014', 'ENT005', 'Entreprise Epsilon', NULL, '2025-06-04', NULL, NULL, 'personneMorale', NULL, false) ON CONFLICT DO NOTHING;
INSERT INTO document.personnes (id, adresse, mail, telephone, qrcodevalue, code, raisonsociale, datenaissance, datecreation, datemodification, comptes_id, type, ticket_id, etat) VALUES ('4390615e-1101-7209-9932-7020bbd556f3', NULL, 'dombogilles@gmail.com', '655455487', NULL, NULL, NULL, NULL, '2024-07-27', NULL, 'a1234567-89ab-cdef-0123-456789abcdef', 'personnel', 'be8ef47f-d5a2-4254-cd79-af860672558e', false) ON CONFLICT DO NOTHING;
INSERT INTO document.personnes (id, adresse, mail, telephone, qrcodevalue, code, raisonsociale, datenaissance, datecreation, datemodification, comptes_id, type, ticket_id, etat) VALUES ('4390615e-1101-7209-9932-7020bbd556f2', NULL, 'peteralan@gmail.com', '655455487', NULL, NULL, NULL, NULL, '2024-07-27', NULL, 'b2345678-9abc-def0-1234-56789abcdef0', 'personnel', 'c1a2b3d4-e5f6-4a78-9b0c-1234d56789ef', true) ON CONFLICT DO NOTHING;
INSERT INTO document.personnes (id, adresse, mail, telephone, qrcodevalue, code, raisonsociale, datenaissance, datecreation, datemodification, comptes_id, type, ticket_id, etat) VALUES ('c3d4e5f6-7890-ab12-c3d4-e5f67890abcd', '11 boulevard Sud', 'contact@dist5.com', '0800000005', 'QR015', 'DIST005', 'Distributeur Cinq', NULL, '2025-06-04', NULL, NULL, 'distributeur', NULL, true) ON CONFLICT DO NOTHING;


--
-- TOC entry 5473 (class 0 OID 16778)
-- Dependencies: 258
-- Data for Name: personnesmorales; Type: TABLE DATA; Schema: document; Owner: postgres
--

INSERT INTO document.personnesmorales (personnesmorales_id, raisonsociale, code) VALUES ('77777777-0000-1111-2222-777777777777', 'Entreprise Alpha', 'ENT001') ON CONFLICT DO NOTHING;
INSERT INTO document.personnesmorales (personnesmorales_id, raisonsociale, code) VALUES ('88888888-1111-2222-3333-888888888888', 'Entreprise Beta', 'ENT002') ON CONFLICT DO NOTHING;
INSERT INTO document.personnesmorales (personnesmorales_id, raisonsociale, code) VALUES ('b2c3d4e5-f678-90ab-12c3-d4e5f67890ab', 'Entreprise Gamma', 'ENT003') ON CONFLICT DO NOTHING;
INSERT INTO document.personnesmorales (personnesmorales_id, raisonsociale, code) VALUES ('d4e5f678-90ab-12c3-d4e5-f67890abcdea', 'Entreprise Delta', 'ENT004') ON CONFLICT DO NOTHING;


--
-- TOC entry 5474 (class 0 OID 16784)
-- Dependencies: 259
-- Data for Name: personnesphysique; Type: TABLE DATA; Schema: document; Owner: postgres
--

INSERT INTO document.personnesphysique (personnesphysique_id, nom, prenom, sexe, datenaissance, datemodification) VALUES ('22222222-bbbb-cccc-dddd-222222222222', 'Martin', 'Bob', 'M', '1988-11-23', '2025-06-02') ON CONFLICT DO NOTHING;
INSERT INTO document.personnesphysique (personnesphysique_id, nom, prenom, sexe, datenaissance, datemodification) VALUES ('11111111-aaaa-bbbb-cccc-111111111111', 'Dupont3', 'Alice', 'F', '2025-08-06', '2025-06-01') ON CONFLICT DO NOTHING;
INSERT INTO document.personnesphysique (personnesphysique_id, nom, prenom, sexe, datenaissance, datemodification) VALUES ('33333333-cccc-dddd-eeee-333333333333', 'Durand25', 'Carole', 'F', '2025-08-06', '2025-06-03') ON CONFLICT DO NOTHING;
INSERT INTO document.personnesphysique (personnesphysique_id, nom, prenom, sexe, datenaissance, datemodification) VALUES ('3b277afa-98fc-4b0f-9b16-1d2fb4aa8ec6', 'Martin2', 'Esaie Ledoux', 'M', '2025-08-07', NULL) ON CONFLICT DO NOTHING;
INSERT INTO document.personnesphysique (personnesphysique_id, nom, prenom, sexe, datenaissance, datemodification) VALUES ('44444444-dddd-eeee-ffff-444444444444', 'Petit425', 'Daniel', 'M', '2025-08-07', '2025-06-04') ON CONFLICT DO NOTHING;
INSERT INTO document.personnesphysique (personnesphysique_id, nom, prenom, sexe, datenaissance, datemodification) VALUES ('6b7894b5-1b07-487a-b8fe-d14ab61c1b2e', 'Martin36Petit425', 'Esaie Ledoux', 'M', '2025-08-07', NULL) ON CONFLICT DO NOTHING;


--
-- TOC entry 5475 (class 0 OID 16792)
-- Dependencies: 260
-- Data for Name: precomouvements; Type: TABLE DATA; Schema: document; Owner: postgres
--

INSERT INTO document.precomouvements (id, libelle, etat, datecreation, datemodification, typemouvement) VALUES ('6290615e-1101-7209-9932-7020bbd556f1', 'Inventaire', true, '2022-01-01', '2022-01-02', 'Neutre') ON CONFLICT DO NOTHING;
INSERT INTO document.precomouvements (id, libelle, etat, datecreation, datemodification, typemouvement) VALUES ('17ee6932-2fcd-4b93-9c4c-0a4dbf659bff', 'Achat', true, '2022-01-01', '2022-01-02', 'Ajout') ON CONFLICT DO NOTHING;
INSERT INTO document.precomouvements (id, libelle, etat, datecreation, datemodification, typemouvement) VALUES ('77b8577f-6d26-4376-af30-a3c8f75a9194', 'interdiction Infirmière', true, '2022-01-01', '2022-01-02', 'Neutre') ON CONFLICT DO NOTHING;
INSERT INTO document.precomouvements (id, libelle, etat, datecreation, datemodification, typemouvement) VALUES ('6290615e-1101-7209-9932-7020bbd556f5', 'Don', true, '2022-01-01', '2022-01-02', 'Neutre') ON CONFLICT DO NOTHING;
INSERT INTO document.precomouvements (id, libelle, etat, datecreation, datemodification, typemouvement) VALUES ('6290615e-1101-7209-9932-7020bbd556f6', 'Perte', true, '2022-01-01', '2022-01-02', 'Reduire') ON CONFLICT DO NOTHING;
INSERT INTO document.precomouvements (id, libelle, etat, datecreation, datemodification, typemouvement) VALUES ('6290615e-1101-7209-9932-7020bbd556f7', 'Retour', true, '2022-01-01', '2022-01-02', 'Ajout') ON CONFLICT DO NOTHING;
INSERT INTO document.precomouvements (id, libelle, etat, datecreation, datemodification, typemouvement) VALUES ('6290615e-1101-7209-9932-7020bbd556f8', 'Sortie magasin', true, '2022-01-01', '2022-01-02', 'Reduire') ON CONFLICT DO NOTHING;
INSERT INTO document.precomouvements (id, libelle, etat, datecreation, datemodification, typemouvement) VALUES ('6290615e-1101-7209-9932-7020bbd556f2', 'Vente', true, '2022-01-01', NULL, NULL) ON CONFLICT DO NOTHING;


--
-- TOC entry 5476 (class 0 OID 16800)
-- Dependencies: 261
-- Data for Name: precomouvementsqtes; Type: TABLE DATA; Schema: document; Owner: postgres
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
-- TOC entry 5477 (class 0 OID 16807)
-- Dependencies: 262
-- Data for Name: promotions; Type: TABLE DATA; Schema: document; Owner: postgres
--

INSERT INTO document.promotions (id, datedebut, datefin, codeunique, typeremise, valeurremise, datecreation, datemodification, distributeurs_id) VALUES ('1979bd79-f81b-498b-b247-e7b9bbb3f600', '2022-01-01', '2022-01-01', 'R5', 'garantie', 54.25, '2000-04-10', '2022-01-01', 'aaaaaaaa-3333-4444-5555-aaaaaaaaaaaa') ON CONFLICT DO NOTHING;
INSERT INTO document.promotions (id, datedebut, datefin, codeunique, typeremise, valeurremise, datecreation, datemodification, distributeurs_id) VALUES ('1979bd79-f81b-498b-b247-e7b9bbb3f601', '2022-01-01', '2022-01-01', 'R54', 'virement', 74.25, '2004-08-10', '2022-01-01', '99999999-2222-3333-4444-999999999999') ON CONFLICT DO NOTHING;
INSERT INTO document.promotions (id, datedebut, datefin, codeunique, typeremise, valeurremise, datecreation, datemodification, distributeurs_id) VALUES ('1979bd79-f81b-498b-b247-e7b9bbb3f602', '2022-01-01', '2022-01-01', 'R45', 'devoir', 95.23, '2002-10-10', '2022-01-01', 'c3d4e5f6-7890-ab12-c3d4-e5f67890abcd') ON CONFLICT DO NOTHING;


--
-- TOC entry 5478 (class 0 OID 16813)
-- Dependencies: 263
-- Data for Name: rattacher; Type: TABLE DATA; Schema: document; Owner: postgres
--



--
-- TOC entry 5479 (class 0 OID 16820)
-- Dependencies: 264
-- Data for Name: remplir; Type: TABLE DATA; Schema: document; Owner: postgres
--

INSERT INTO document.remplir (id, roles_id, missions_id, datefin, datecreation, datedebut, etat, droitajouter, droitmodifier, droitconsulter, droitvalider) VALUES ('a1b2c3d4-e5f6-7890-ab12-c3d4e5f67890', '5190615e-1101-7209-9932-7020bbd556f1', '3190615e-1101-7209-9932-7020bbd556f1', '2025-06-04', '2025-06-01', '2025-06-01', true, true, true, true, true) ON CONFLICT DO NOTHING;
INSERT INTO document.remplir (id, roles_id, missions_id, datefin, datecreation, datedebut, etat, droitajouter, droitmodifier, droitconsulter, droitvalider) VALUES ('b2c3d4e5-f678-90ab-12c3-d4e5f67890ab', '6130615e-1101-7209-9932-7020bbd556f2', '3190615e-1101-7209-9932-7020bbd556f2', '2025-06-04', '2025-06-01', '2025-06-01', true, false, true, false, true) ON CONFLICT DO NOTHING;
INSERT INTO document.remplir (id, roles_id, missions_id, datefin, datecreation, datedebut, etat, droitajouter, droitmodifier, droitconsulter, droitvalider) VALUES ('c3d4e5f6-7890-ab12-c3d4-e5f67890abcd', '6191615e-1101-7209-9932-7020bbd556f3', '3190615e-1101-7209-9932-7020bbd556f3', '2025-06-04', '2025-06-01', '2025-06-01', false, true, false, true, false) ON CONFLICT DO NOTHING;


--
-- TOC entry 5480 (class 0 OID 16826)
-- Dependencies: 265
-- Data for Name: respecter; Type: TABLE DATA; Schema: document; Owner: postgres
--

INSERT INTO document.respecter (mouvements_id, precomouvements_id) VALUES ('97d314f5-84ad-47cd-874d-9c55f0e45790', '6290615e-1101-7209-9932-7020bbd556f1') ON CONFLICT DO NOTHING;
INSERT INTO document.respecter (mouvements_id, precomouvements_id) VALUES ('bfee750d-9b8c-4475-9373-08eff9b3ccb7', '6290615e-1101-7209-9932-7020bbd556f2') ON CONFLICT DO NOTHING;
INSERT INTO document.respecter (mouvements_id, precomouvements_id) VALUES ('0e7cea07-d09e-4f79-be6c-6dd27aecbce6', '17ee6932-2fcd-4b93-9c4c-0a4dbf659bff') ON CONFLICT DO NOTHING;


--
-- TOC entry 5481 (class 0 OID 16833)
-- Dependencies: 266
-- Data for Name: ressources; Type: TABLE DATA; Schema: document; Owner: postgres
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
-- TOC entry 5482 (class 0 OID 16839)
-- Dependencies: 267
-- Data for Name: ressourcespromotions; Type: TABLE DATA; Schema: document; Owner: postgres
--

INSERT INTO document.ressourcespromotions (promotions_id, ressources_id) VALUES ('1979bd79-f81b-498b-b247-e7b9bbb3f600', '6190615e-1101-7209-9932-7020bbd556f6') ON CONFLICT DO NOTHING;
INSERT INTO document.ressourcespromotions (promotions_id, ressources_id) VALUES ('1979bd79-f81b-498b-b247-e7b9bbb3f601', '6190615e-1101-7209-9932-7020bbd556f4') ON CONFLICT DO NOTHING;
INSERT INTO document.ressourcespromotions (promotions_id, ressources_id) VALUES ('1979bd79-f81b-498b-b247-e7b9bbb3f602', '6190615e-1101-7209-9932-7020bbd556f2') ON CONFLICT DO NOTHING;


--
-- TOC entry 5483 (class 0 OID 16846)
-- Dependencies: 268
-- Data for Name: roles; Type: TABLE DATA; Schema: document; Owner: postgres
--

INSERT INTO document.roles (id, titre, description, etat, datecreation, datemodification) VALUES ('5190615e-1101-7209-9932-7020bbd556f1', 'vendeur', 'personnel au contact du client', true, '2022-01-01', '2022-01-02') ON CONFLICT DO NOTHING;
INSERT INTO document.roles (id, titre, description, etat, datecreation, datemodification) VALUES ('6191615e-1101-7209-9932-7020bbd556f3', 'marcheur', 'commercial sur le terrain', true, '2022-01-01', '2022-01-02') ON CONFLICT DO NOTHING;
INSERT INTO document.roles (id, titre, description, etat, datecreation, datemodification) VALUES ('6130615e-1101-7209-9932-7020bbd556f2', 'traiteur78', 'Personnel administratif', true, '2022-01-01', '2022-01-02') ON CONFLICT DO NOTHING;


--
-- TOC entry 5484 (class 0 OID 16852)
-- Dependencies: 269
-- Data for Name: sapplique; Type: TABLE DATA; Schema: document; Owner: postgres
--

INSERT INTO document.sapplique (familles_id, precomouvementsqtes_id) VALUES ('f190615e-1101-7209-9932-7020bbd556f1', 'a97eb081-72f6-4617-ba29-64dc8593a9ff') ON CONFLICT DO NOTHING;
INSERT INTO document.sapplique (familles_id, precomouvementsqtes_id) VALUES ('f190615e-1101-7209-9932-7020bbd556f2', 'a97eb081-62f6-4617-ba49-64dc8593a9ff') ON CONFLICT DO NOTHING;
INSERT INTO document.sapplique (familles_id, precomouvementsqtes_id) VALUES ('f190615e-1101-7209-9932-7020bbd556f3', 'a97eb081-62f6-4617-ba29-65dc8593a9ff') ON CONFLICT DO NOTHING;
INSERT INTO document.sapplique (familles_id, precomouvementsqtes_id) VALUES ('f190615e-1101-7209-9932-7020bbd556f1', 'a97eb081-62f6-4617-ba49-64dc8593a9ff') ON CONFLICT DO NOTHING;
INSERT INTO document.sapplique (familles_id, precomouvementsqtes_id) VALUES ('f190615e-1101-7209-9932-7020bbd556f2', 'a97eb081-72f6-4617-ba29-64dc8593a9ff') ON CONFLICT DO NOTHING;
INSERT INTO document.sapplique (familles_id, precomouvementsqtes_id) VALUES ('f190615e-1101-7209-9932-7020bbd556f3', 'a97eb081-62f6-4617-ba49-64dc8593a9ff') ON CONFLICT DO NOTHING;


--
-- TOC entry 5485 (class 0 OID 16859)
-- Dependencies: 270
-- Data for Name: services; Type: TABLE DATA; Schema: document; Owner: postgres
--

INSERT INTO document.services (id, description, libelle, localisation, etat, datecreation, datemodification, codeunique, filesattentes_id) VALUES ('22f815cf-3164-47d9-b2cd-d1b7aa0aad3c', 'bien', 'Pharmacieee2', 'douala', true, NULL, NULL, 'azer5r25IR', NULL) ON CONFLICT DO NOTHING;
INSERT INTO document.services (id, description, libelle, localisation, etat, datecreation, datemodification, codeunique, filesattentes_id) VALUES ('c469b798-c841-450e-b6ab-e5f1d96c7560', 'bien', 'Laboratoire78962225', 'douala', true, NULL, NULL, 'OS74', NULL) ON CONFLICT DO NOTHING;
INSERT INTO document.services (id, description, libelle, localisation, etat, datecreation, datemodification, codeunique, filesattentes_id) VALUES ('ecc53b2b-c398-46b9-b5a8-ef18c0198bff', 'bien', 'Pharmacie269854', 'douala-garoua', true, NULL, NULL, 'SNK3', NULL) ON CONFLICT DO NOTHING;
INSERT INTO document.services (id, description, libelle, localisation, etat, datecreation, datemodification, codeunique, filesattentes_id) VALUES ('2190615e-1101-7209-9932-7020bbd556f3', 'bien', 'Cinema269', 'douala', true, '2000-03-07', NULL, 'S3', NULL) ON CONFLICT DO NOTHING;
INSERT INTO document.services (id, description, libelle, localisation, etat, datecreation, datemodification, codeunique, filesattentes_id) VALUES ('2190615e-1101-7209-9932-7020bbd556f1', 'bien', 'Consultation37', 'douala', true, '1972-06-12', NULL, 'S1', NULL) ON CONFLICT DO NOTHING;
INSERT INTO document.services (id, description, libelle, localisation, etat, datecreation, datemodification, codeunique, filesattentes_id) VALUES ('2190615e-1101-7209-9932-7020bbd556f2', 'bien', 'Laboratoire78', 'douala', true, '1990-08-06', NULL, 'S2', NULL) ON CONFLICT DO NOTHING;


--
-- TOC entry 5486 (class 0 OID 16866)
-- Dependencies: 271
-- Data for Name: suivre; Type: TABLE DATA; Schema: document; Owner: postgres
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
-- TOC entry 5487 (class 0 OID 16873)
-- Dependencies: 272
-- Data for Name: tickets; Type: TABLE DATA; Schema: document; Owner: postgres
--

INSERT INTO document.tickets (id, codecourt, datecreation, datemodification, statut, personnesphysique_id, idunique) VALUES ('f4d5e6g7-b8c9-7d01-2e3f-456780a12bcd', 'TCKT004', '2025-08-08', '2025-08-08', NULL, NULL, NULL) ON CONFLICT DO NOTHING;
INSERT INTO document.tickets (id, codecourt, datecreation, datemodification, statut, personnesphysique_id, idunique) VALUES ('g5e6f7h8-c9d0-8e12-3f4g-567801b23cde', 'TCKT005', '2025-07-29', '2025-08-03', 'En attente', NULL, NULL) ON CONFLICT DO NOTHING;
INSERT INTO document.tickets (id, codecourt, datecreation, datemodification, statut, personnesphysique_id, idunique) VALUES ('e3c4d5f6-a7b8-6c90-1d2e-3456f78901ab', 'TCKT003', '2025-08-01', '2025-08-06', 'Fermé', NULL, NULL) ON CONFLICT DO NOTHING;
INSERT INTO document.tickets (id, codecourt, datecreation, datemodification, statut, personnesphysique_id, idunique) VALUES ('c1a2b3d4-e5f6-4a78-9b0c-1234d56789ef', 'TCKT001', '2025-08-08', '2025-08-08', 'Ouvert', NULL, NULL) ON CONFLICT DO NOTHING;
INSERT INTO document.tickets (id, codecourt, datecreation, datemodification, statut, personnesphysique_id, idunique) VALUES ('d2b3c4e5-f6a7-5b89-0c1d-2345e67890fa', 'TCKT002', '2025-08-05', '2025-08-08', 'En cours', NULL, NULL) ON CONFLICT DO NOTHING;
INSERT INTO document.tickets (id, codecourt, datecreation, datemodification, statut, personnesphysique_id, idunique) VALUES ('be8ef47f-d5a2-4254-cd79-af860672553e', 'rr15', '2022-03-02', '2022-10-02', NULL, '22222222-bbbb-cccc-dddd-222222222222', NULL) ON CONFLICT DO NOTHING;
INSERT INTO document.tickets (id, codecourt, datecreation, datemodification, statut, personnesphysique_id, idunique) VALUES ('be8ef47f-d5a2-4254-cd79-af860672554e', 'rr10', '2002-01-02', '2022-11-02', NULL, '3b277afa-98fc-4b0f-9b16-1d2fb4aa8ec6', NULL) ON CONFLICT DO NOTHING;
INSERT INTO document.tickets (id, codecourt, datecreation, datemodification, statut, personnesphysique_id, idunique) VALUES ('be8ef47f-d5a2-4254-cd79-af860672555e', 'rr11', '2014-01-12', '2022-02-02', NULL, '33333333-cccc-dddd-eeee-333333333333', NULL) ON CONFLICT DO NOTHING;
INSERT INTO document.tickets (id, codecourt, datecreation, datemodification, statut, personnesphysique_id, idunique) VALUES ('be8ef47f-d5a2-4254-cd79-af860672556e', 'rr12', '2023-10-25', '2024-03-02', NULL, '44444444-dddd-eeee-ffff-444444444444', NULL) ON CONFLICT DO NOTHING;
INSERT INTO document.tickets (id, codecourt, datecreation, datemodification, statut, personnesphysique_id, idunique) VALUES ('be8ef47f-d5a2-4254-cd79-af860672557e', 'rr16', '2020-11-15', '2024-05-02', NULL, '11111111-aaaa-bbbb-cccc-111111111111', NULL) ON CONFLICT DO NOTHING;
INSERT INTO document.tickets (id, codecourt, datecreation, datemodification, statut, personnesphysique_id, idunique) VALUES ('be8ef47f-d5a2-4254-cd79-af860672558e', 'rr14', '2022-01-02', '2023-03-02', NULL, '6b7894b5-1b07-487a-b8fe-d14ab61c1b2e', NULL) ON CONFLICT DO NOTHING;


--
-- TOC entry 5488 (class 0 OID 16879)
-- Dependencies: 273
-- Data for Name: ticketsfilesattentes; Type: TABLE DATA; Schema: document; Owner: postgres
--

INSERT INTO document.ticketsfilesattentes (id, etat, dateaffectation, tickets_id, filesattentes_id) VALUES ('be8ef47f-d7a2-4254-cd79-af860672553e', true, '2024-11-04', 'be8ef47f-d5a2-4254-cd79-af860672553e', 'f190615e-1101-7209-9932-7020bbd556f1') ON CONFLICT DO NOTHING;
INSERT INTO document.ticketsfilesattentes (id, etat, dateaffectation, tickets_id, filesattentes_id) VALUES ('be8ef47f-d7a2-4254-cd79-af860672554e', false, '2022-12-04', 'be8ef47f-d5a2-4254-cd79-af860672554e', 'f190615e-1101-7209-9932-7020bbd556f2') ON CONFLICT DO NOTHING;
INSERT INTO document.ticketsfilesattentes (id, etat, dateaffectation, tickets_id, filesattentes_id) VALUES ('be8ef47f-d7a2-4254-cd79-af860672555e', false, '2020-11-04', 'be8ef47f-d5a2-4254-cd79-af860672558e', 'f190615e-1101-7209-9932-7020bbd556f3') ON CONFLICT DO NOTHING;
INSERT INTO document.ticketsfilesattentes (id, etat, dateaffectation, tickets_id, filesattentes_id) VALUES ('be8ef47f-d7a2-4254-cd79-af860672556e', false, '2021-04-04', 'be8ef47f-d5a2-4254-cd79-af860672556e', 'f190615e-1101-7209-9932-7020bbd556f3') ON CONFLICT DO NOTHING;
INSERT INTO document.ticketsfilesattentes (id, etat, dateaffectation, tickets_id, filesattentes_id) VALUES ('be8ef47f-d7a2-4254-cd79-af860672557e', true, '2023-02-04', 'be8ef47f-d5a2-4254-cd79-af860672555e', 'f190615e-1101-7209-9932-7020bbd556f2') ON CONFLICT DO NOTHING;
INSERT INTO document.ticketsfilesattentes (id, etat, dateaffectation, tickets_id, filesattentes_id) VALUES ('be8ef47f-d7a2-4254-cd79-af860672558e', false, '2022-01-04', 'be8ef47f-d5a2-4254-cd79-af860672557e', 'f190615e-1101-7209-9932-7020bbd556f3') ON CONFLICT DO NOTHING;


--
-- TOC entry 5489 (class 0 OID 16885)
-- Dependencies: 274
-- Data for Name: traiter; Type: TABLE DATA; Schema: document; Owner: postgres
--

INSERT INTO document.traiter (documents_id, missions_id) VALUES ('0190615e-1101-7209-9932-7020bbd556f1', '3190615e-1101-7209-9932-7020bbd556f1') ON CONFLICT DO NOTHING;
INSERT INTO document.traiter (documents_id, missions_id) VALUES ('0190615e-1101-7209-9932-7020bbd556f1', '3190615e-1101-7209-9932-7020bbd556f2') ON CONFLICT DO NOTHING;
INSERT INTO document.traiter (documents_id, missions_id) VALUES ('0190615e-1101-7209-9932-7020bbd556f2', '3190615e-1101-7209-9932-7020bbd556f2') ON CONFLICT DO NOTHING;


--
-- TOC entry 5490 (class 0 OID 16892)
-- Dependencies: 275
-- Data for Name: utilisateurs; Type: TABLE DATA; Schema: document; Owner: postgres
--

INSERT INTO document.utilisateurs (id, login, mdp, etat, datecreation, datemodification, groupes_id, menus_id) VALUES ('0190615e-1101-7209-9932-7020bbd55714', 'sdvfdbrfg', 'sdvdfbfg', 'false', NULL, NULL, 'f6jkl74e-5e6f-4e90-596a-3b2a1e0d9c8a', 'p6ghi7oe-5o6p-483a-3940-3f2g1h0i9j8k') ON CONFLICT DO NOTHING;
INSERT INTO document.utilisateurs (id, login, mdp, etat, datecreation, datemodification, groupes_id, menus_id) VALUES ('k1yzabc2j-0j1k-43e5-8415-8a7b6c5d4e3f', 'admin', 'password', 'true', '2024-01-01', '2024-01-01', 'g7mno85f-6f7g-4fa1-4859-2a1e0d9c8a7b', 'q7hij8pf-6p7q-494b-2859-2g1h0i9j8k7l') ON CONFLICT DO NOTHING;
INSERT INTO document.utilisateurs (id, login, mdp, etat, datecreation, datemodification, groupes_id, menus_id) VALUES ('l2bcd3ka-1k2l-44f6-9304-7b6c5d4e3f2g', 'user1', 'password', 'true', '2024-01-01', '2024-01-01', 'h8pqr96g-7g8h-40b2-5748-1e0d9c8a7b6c', 'r8ijk9qg-7q8r-4a5c-1768-1h0i9j8k7l6m') ON CONFLICT DO NOTHING;
INSERT INTO document.utilisateurs (id, login, mdp, etat, datecreation, datemodification, groupes_id, menus_id) VALUES ('m3cde4lb-2l3m-4507-0213-6c5d4e3f2g1h', 'user2', 'password', 'true', '2024-01-01', '2024-01-01', 'i9stu07h-8h9i-41c3-6637-0d9c8a7b6c5d', 's9jkl0rh-8r9s-4b6d-0677-0i9j8k7l6m5n') ON CONFLICT DO NOTHING;
INSERT INTO document.utilisateurs (id, login, mdp, etat, datecreation, datemodification, groupes_id, menus_id) VALUES ('n4efg5mc-3m4n-4618-1122-5d4e3f2g1h0i', 'user3', 'password', 'true', '2024-01-01', '2024-01-01', 'j0vwx18i-9i0j-42d4-7526-9c8a7b6c5d4e', 't0klm1si-9s0t-4c7e-7586-9j8k7l6m5n4o') ON CONFLICT DO NOTHING;
INSERT INTO document.utilisateurs (id, login, mdp, etat, datecreation, datemodification, groupes_id, menus_id) VALUES ('o5fgh6nd-4n5o-4729-2031-4e3f2g1h0i9j', 'user4', 'password', 'true', '2024-01-01', '2024-01-01', 'f6jkl74e-5e6f-4e90-596a-3b2a1e0d9c8a', 'p6ghi7oe-5o6p-483a-3940-3f2g1h779j8k') ON CONFLICT DO NOTHING;


--
-- TOC entry 5491 (class 0 OID 16898)
-- Dependencies: 276
-- Data for Name: validations; Type: TABLE DATA; Schema: document; Owner: postgres
--

INSERT INTO document.validations (id, code, etat, datecreation, datemodification, typevote, dureevote, typevalidation, roles_id, libelle) VALUES ('1901bd80-f71b-498b-b247-e7b9bbb3f602', 'o78', 'attente', '2022-03-02', '2022-10-02', 'multiple', 14, 'prioritaire', '5190615e-1101-7209-9932-7020bbd556f1', 'en cours') ON CONFLICT DO NOTHING;
INSERT INTO document.validations (id, code, etat, datecreation, datemodification, typevote, dureevote, typevalidation, roles_id, libelle) VALUES ('1901bd80-f71b-498b-b247-e7b9bbb3f601', 'o89', 'suppression', '2002-01-02', '2022-11-02', 'multiple', 2, 'prioritaire', '6130615e-1101-7209-9932-7020bbd556f2', 'Resultat mise à jour') ON CONFLICT DO NOTHING;
INSERT INTO document.validations (id, code, etat, datecreation, datemodification, typevote, dureevote, typevalidation, roles_id, libelle) VALUES ('1901bd80-f71b-498b-b247-e7b9bbb3f604', 'o84', 'traitement', '2020-11-15', '2024-05-02', 'multiple', 40, 'prioritaire', '6191615e-1101-7209-9932-7020bbd556f3', 'en cours') ON CONFLICT DO NOTHING;
INSERT INTO document.validations (id, code, etat, datecreation, datemodification, typevote, dureevote, typevalidation, roles_id, libelle) VALUES ('1901bd80-f71b-498b-b247-e7b9bbb3f603', 'o82', 'traitement', '2023-10-25', '2022-10-02', 'Unitaire', 22, 'Transmission', '5190615e-1101-7209-9932-7020bbd556f1', 'Consultation38') ON CONFLICT DO NOTHING;
INSERT INTO document.validations (id, code, etat, datecreation, datemodification, typevote, dureevote, typevalidation, roles_id, libelle) VALUES ('ddd8446a-be11-457c-a7c8-436467b09a79', 'o8278', 'true', '2025-08-07', '2022-10-02', 'Majoritaire', 0, 'Traitement', '6191615e-1101-7209-9932-7020bbd556f3', 'Consultation38') ON CONFLICT DO NOTHING;
INSERT INTO document.validations (id, code, etat, datecreation, datemodification, typevote, dureevote, typevalidation, roles_id, libelle) VALUES ('1901bd80-f71b-498b-b247-e7b9bbb3f600', 'o85', 'traitement', '2014-01-12', '2022-02-02', 'multiple', 5, 'rechargeable', '5190615e-1101-7209-9932-7020bbd556f1', 'Mise en attente') ON CONFLICT DO NOTHING;


--
-- TOC entry 5492 (class 0 OID 16904)
-- Dependencies: 277
-- Data for Name: violer; Type: TABLE DATA; Schema: document; Owner: postgres
--

INSERT INTO document.violer (mouvements_id, precomouvements_id) VALUES ('97d314f5-84ad-47cd-874d-9c55f0e45790', '6290615e-1101-7209-9932-7020bbd556f6') ON CONFLICT DO NOTHING;
INSERT INTO document.violer (mouvements_id, precomouvements_id) VALUES ('bfee750d-9b8c-4475-9373-08eff9b3ccb7', '6290615e-1101-7209-9932-7020bbd556f7') ON CONFLICT DO NOTHING;
INSERT INTO document.violer (mouvements_id, precomouvements_id) VALUES ('0e7cea07-d09e-4f79-be6c-6dd27aecbce6', '6290615e-1101-7209-9932-7020bbd556f8') ON CONFLICT DO NOTHING;


--
-- TOC entry 5087 (class 2606 OID 16912)
-- Name: actions actions_pkey; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.actions
    ADD CONSTRAINT actions_pkey PRIMARY KEY (id);


--
-- TOC entry 5107 (class 2606 OID 16914)
-- Name: distributeurs distributeurs_pkey; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.distributeurs
    ADD CONSTRAINT distributeurs_pkey PRIMARY KEY (distributeurs_id);


--
-- TOC entry 5117 (class 2606 OID 16916)
-- Name: elements elements_pkey; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.elements
    ADD CONSTRAINT elements_pkey PRIMARY KEY (id);


--
-- TOC entry 5123 (class 2606 OID 16918)
-- Name: elementsbases elementsbases_pkey; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.elementsbases
    ADD CONSTRAINT elementsbases_pkey PRIMARY KEY (id);


--
-- TOC entry 5141 (class 2606 OID 16920)
-- Name: groupes groupes_pkey; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.groupes
    ADD CONSTRAINT groupes_pkey PRIMARY KEY (id);


--
-- TOC entry 5145 (class 2606 OID 16922)
-- Name: langues langues_pkey; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.langues
    ADD CONSTRAINT langues_pkey PRIMARY KEY (id);


--
-- TOC entry 5147 (class 2606 OID 16924)
-- Name: menus menus_pkey; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.menus
    ADD CONSTRAINT menus_pkey PRIMARY KEY (id);


--
-- TOC entry 5157 (class 2606 OID 16926)
-- Name: organisations organisations_pkey; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.organisations
    ADD CONSTRAINT organisations_pkey PRIMARY KEY (id);


--
-- TOC entry 5163 (class 2606 OID 16928)
-- Name: personnels personnels_pkey; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.personnels
    ADD CONSTRAINT personnels_pkey PRIMARY KEY (personnels_id);


--
-- TOC entry 5167 (class 2606 OID 16930)
-- Name: personnesmorales personnesmorales_pkey; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.personnesmorales
    ADD CONSTRAINT personnesmorales_pkey PRIMARY KEY (personnesmorales_id);


--
-- TOC entry 5169 (class 2606 OID 16932)
-- Name: personnesphysique personnesphysique_pkey; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.personnesphysique
    ADD CONSTRAINT personnesphysique_pkey PRIMARY KEY (personnesphysique_id);


--
-- TOC entry 5089 (class 2606 OID 16934)
-- Name: actionslangues pk_actionslangues; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.actionslangues
    ADD CONSTRAINT pk_actionslangues PRIMARY KEY (langues_id, actions_id);


--
-- TOC entry 5091 (class 2606 OID 16936)
-- Name: associer pk_associer; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.associer
    ADD CONSTRAINT pk_associer PRIMARY KEY (attributs_id, categories_id);


--
-- TOC entry 5093 (class 2606 OID 16938)
-- Name: attributs pk_attributs; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.attributs
    ADD CONSTRAINT pk_attributs PRIMARY KEY (id);


--
-- TOC entry 5095 (class 2606 OID 16940)
-- Name: caisses pk_caisses; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.caisses
    ADD CONSTRAINT pk_caisses PRIMARY KEY (id);


--
-- TOC entry 5097 (class 2606 OID 16942)
-- Name: categories pk_categories; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.categories
    ADD CONSTRAINT pk_categories PRIMARY KEY (id);


--
-- TOC entry 5119 (class 2606 OID 16944)
-- Name: elements pk_composite_elements; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.elements
    ADD CONSTRAINT pk_composite_elements UNIQUE (menus_id, elementsbases_id);


--
-- TOC entry 5099 (class 2606 OID 16946)
-- Name: comptes pk_comptes; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.comptes
    ADD CONSTRAINT pk_comptes PRIMARY KEY (id);


--
-- TOC entry 5101 (class 2606 OID 16948)
-- Name: concerner pk_concerner; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.concerner
    ADD CONSTRAINT pk_concerner PRIMARY KEY (precomouvementsqtes_id, distributeurs_id);


--
-- TOC entry 5103 (class 2606 OID 16950)
-- Name: constituer pk_constituer; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.constituer
    ADD CONSTRAINT pk_constituer PRIMARY KEY (attributs_id, documents_id);


--
-- TOC entry 5105 (class 2606 OID 16952)
-- Name: deltasoldes pk_deltasoldes; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.deltasoldes
    ADD CONSTRAINT pk_deltasoldes PRIMARY KEY (id);


--
-- TOC entry 5109 (class 2606 OID 16954)
-- Name: docetats pk_docetats; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.docetats
    ADD CONSTRAINT pk_docetats PRIMARY KEY (id);


--
-- TOC entry 5111 (class 2606 OID 16956)
-- Name: docetats_predecesseurs pk_docetats_predecesseurs; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.docetats_predecesseurs
    ADD CONSTRAINT pk_docetats_predecesseurs PRIMARY KEY (docetats_id, predecesseur_id);


--
-- TOC entry 5113 (class 2606 OID 16958)
-- Name: documents pk_documents; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.documents
    ADD CONSTRAINT pk_documents PRIMARY KEY (id);


--
-- TOC entry 5115 (class 2606 OID 16960)
-- Name: documentspromotions pk_documentspromotions; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.documentspromotions
    ADD CONSTRAINT pk_documentspromotions PRIMARY KEY (documents_id, promotions_id);


--
-- TOC entry 5121 (class 2606 OID 16962)
-- Name: elementsbaselanques pk_elementsbaseslangues; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.elementsbaselanques
    ADD CONSTRAINT pk_elementsbaseslangues PRIMARY KEY (langues_id, elementsbases_id);


--
-- TOC entry 5125 (class 2606 OID 16964)
-- Name: elementslangues pk_elementslangues; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.elementslangues
    ADD CONSTRAINT pk_elementslangues PRIMARY KEY (langues_id, elements_id);


--
-- TOC entry 5127 (class 2606 OID 16966)
-- Name: etapes pk_etapes; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.etapes
    ADD CONSTRAINT pk_etapes PRIMARY KEY (id);


--
-- TOC entry 5129 (class 2606 OID 16968)
-- Name: etats pk_etats; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.etats
    ADD CONSTRAINT pk_etats PRIMARY KEY (id);


--
-- TOC entry 5131 (class 2606 OID 16970)
-- Name: exemplaires pk_exemplaires; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.exemplaires
    ADD CONSTRAINT pk_exemplaires PRIMARY KEY (id);


--
-- TOC entry 5133 (class 2606 OID 16972)
-- Name: familles pk_familles; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.familles
    ADD CONSTRAINT pk_familles PRIMARY KEY (id);


--
-- TOC entry 5135 (class 2606 OID 16974)
-- Name: famillespromotions pk_famillespromotions; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.famillespromotions
    ADD CONSTRAINT pk_famillespromotions PRIMARY KEY (familles_id, promotions_id);


--
-- TOC entry 5137 (class 2606 OID 16976)
-- Name: filesattentes pk_filesattentes; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.filesattentes
    ADD CONSTRAINT pk_filesattentes PRIMARY KEY (id);


--
-- TOC entry 5143 (class 2606 OID 16978)
-- Name: jouerroles pk_jouerroles; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.jouerroles
    ADD CONSTRAINT pk_jouerroles PRIMARY KEY (id);


--
-- TOC entry 5149 (class 2606 OID 16980)
-- Name: missions pk_missions; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.missions
    ADD CONSTRAINT pk_missions PRIMARY KEY (id);


--
-- TOC entry 5151 (class 2606 OID 16982)
-- Name: mouvementcaisses pk_mouvementcaisses; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.mouvementcaisses
    ADD CONSTRAINT pk_mouvementcaisses PRIMARY KEY (id);


--
-- TOC entry 5153 (class 2606 OID 16984)
-- Name: mouvements pk_mouvements; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.mouvements
    ADD CONSTRAINT pk_mouvements PRIMARY KEY (id);


--
-- TOC entry 5155 (class 2606 OID 16986)
-- Name: ordreetats pk_ordreetats; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.ordreetats
    ADD CONSTRAINT pk_ordreetats PRIMARY KEY (id);


--
-- TOC entry 5159 (class 2606 OID 16988)
-- Name: organiser pk_organiser; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.organiser
    ADD CONSTRAINT pk_organiser PRIMARY KEY (organisations_id, utilisateurs_id);


--
-- TOC entry 5161 (class 2606 OID 16990)
-- Name: parcours pk_parcours; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.parcours
    ADD CONSTRAINT pk_parcours PRIMARY KEY (id);


--
-- TOC entry 5165 (class 2606 OID 16992)
-- Name: personnes pk_personnes; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.personnes
    ADD CONSTRAINT pk_personnes PRIMARY KEY (id);


--
-- TOC entry 5171 (class 2606 OID 16994)
-- Name: precomouvements pk_precomouvements; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.precomouvements
    ADD CONSTRAINT pk_precomouvements PRIMARY KEY (id);


--
-- TOC entry 5173 (class 2606 OID 16996)
-- Name: precomouvementsqtes pk_precomouvementsqtes; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.precomouvementsqtes
    ADD CONSTRAINT pk_precomouvementsqtes PRIMARY KEY (id);


--
-- TOC entry 5175 (class 2606 OID 16998)
-- Name: promotions pk_promotions; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.promotions
    ADD CONSTRAINT pk_promotions PRIMARY KEY (id);


--
-- TOC entry 5179 (class 2606 OID 17000)
-- Name: rattacher pk_rattacher; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.rattacher
    ADD CONSTRAINT pk_rattacher PRIMARY KEY (personnes_id, rattacher_id);


--
-- TOC entry 5183 (class 2606 OID 17002)
-- Name: respecter pk_respecter; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.respecter
    ADD CONSTRAINT pk_respecter PRIMARY KEY (mouvements_id, precomouvements_id);


--
-- TOC entry 5185 (class 2606 OID 17004)
-- Name: ressources pk_ressources; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.ressources
    ADD CONSTRAINT pk_ressources PRIMARY KEY (id);


--
-- TOC entry 5187 (class 2606 OID 17006)
-- Name: ressourcespromotions pk_ressourcespromotions; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.ressourcespromotions
    ADD CONSTRAINT pk_ressourcespromotions PRIMARY KEY (ressources_id, promotions_id);


--
-- TOC entry 5189 (class 2606 OID 17008)
-- Name: roles pk_roles; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.roles
    ADD CONSTRAINT pk_roles PRIMARY KEY (id);


--
-- TOC entry 5191 (class 2606 OID 17010)
-- Name: sapplique pk_sapplique; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.sapplique
    ADD CONSTRAINT pk_sapplique PRIMARY KEY (familles_id, precomouvementsqtes_id);


--
-- TOC entry 5193 (class 2606 OID 17012)
-- Name: services pk_services; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.services
    ADD CONSTRAINT pk_services PRIMARY KEY (id);


--
-- TOC entry 5199 (class 2606 OID 17014)
-- Name: suivre pk_suivre; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.suivre
    ADD CONSTRAINT pk_suivre PRIMARY KEY (documents_id, precomouvements_id);


--
-- TOC entry 5201 (class 2606 OID 17016)
-- Name: tickets pk_tickets; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.tickets
    ADD CONSTRAINT pk_tickets PRIMARY KEY (id);


--
-- TOC entry 5203 (class 2606 OID 17018)
-- Name: ticketsfilesattentes pk_ticketsfilesattentes; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.ticketsfilesattentes
    ADD CONSTRAINT pk_ticketsfilesattentes PRIMARY KEY (id);


--
-- TOC entry 5205 (class 2606 OID 17020)
-- Name: traiter pk_traiter; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.traiter
    ADD CONSTRAINT pk_traiter PRIMARY KEY (missions_id, documents_id);


--
-- TOC entry 5209 (class 2606 OID 17022)
-- Name: validations pk_validations; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.validations
    ADD CONSTRAINT pk_validations PRIMARY KEY (id);


--
-- TOC entry 5211 (class 2606 OID 17024)
-- Name: violer pk_violer; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.violer
    ADD CONSTRAINT pk_violer PRIMARY KEY (precomouvements_id, mouvements_id);


--
-- TOC entry 5181 (class 2606 OID 17026)
-- Name: remplir remplir_pkey; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.remplir
    ADD CONSTRAINT remplir_pkey PRIMARY KEY (id);


--
-- TOC entry 5139 (class 2606 OID 17028)
-- Name: filesattentes uc_filesattentes_services; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.filesattentes
    ADD CONSTRAINT uc_filesattentes_services UNIQUE (services_id);


--
-- TOC entry 5177 (class 2606 OID 17030)
-- Name: promotions uc_promotions_codeunique; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.promotions
    ADD CONSTRAINT uc_promotions_codeunique UNIQUE (codeunique);


--
-- TOC entry 5195 (class 2606 OID 17032)
-- Name: services uc_services_codeunique; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.services
    ADD CONSTRAINT uc_services_codeunique UNIQUE (codeunique);


--
-- TOC entry 5197 (class 2606 OID 17034)
-- Name: services uc_services_filesattentes; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.services
    ADD CONSTRAINT uc_services_filesattentes UNIQUE (filesattentes_id);


--
-- TOC entry 5207 (class 2606 OID 17036)
-- Name: utilisateurs utilisateurs_pkey; Type: CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.utilisateurs
    ADD CONSTRAINT utilisateurs_pkey PRIMARY KEY (id);


--
-- TOC entry 5225 (class 2606 OID 17037)
-- Name: distributeurs distributeurs_id_fkey; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.distributeurs
    ADD CONSTRAINT distributeurs_id_fkey FOREIGN KEY (distributeurs_id) REFERENCES document.personnes(id);


--
-- TOC entry 5213 (class 2606 OID 17042)
-- Name: actionslangues fk_actions_actionslangues; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.actionslangues
    ADD CONSTRAINT fk_actions_actionslangues FOREIGN KEY (actions_id) REFERENCES document.actions(id);


--
-- TOC entry 5212 (class 2606 OID 17047)
-- Name: actions fk_actions_elementbases; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.actions
    ADD CONSTRAINT fk_actions_elementbases FOREIGN KEY (elementsbase_id) REFERENCES document.elementsbases(id);


--
-- TOC entry 5215 (class 2606 OID 17052)
-- Name: associer fk_associer_on_attributs; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.associer
    ADD CONSTRAINT fk_associer_on_attributs FOREIGN KEY (attributs_id) REFERENCES document.attributs(id);


--
-- TOC entry 5216 (class 2606 OID 17057)
-- Name: associer fk_associer_on_categories; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.associer
    ADD CONSTRAINT fk_associer_on_categories FOREIGN KEY (categories_id) REFERENCES document.categories(id);


--
-- TOC entry 5217 (class 2606 OID 17062)
-- Name: categories fk_categories_on_documents; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.categories
    ADD CONSTRAINT fk_categories_on_documents FOREIGN KEY (documents_id) REFERENCES document.documents(id);


--
-- TOC entry 5218 (class 2606 OID 17067)
-- Name: comptes fk_comptes_personnes; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.comptes
    ADD CONSTRAINT fk_comptes_personnes FOREIGN KEY (personnes_id) REFERENCES document.personnes(id);


--
-- TOC entry 5221 (class 2606 OID 17072)
-- Name: constituer fk_con_on_attributs_entity; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.constituer
    ADD CONSTRAINT fk_con_on_attributs_entity FOREIGN KEY (attributs_id) REFERENCES document.attributs(id);


--
-- TOC entry 5222 (class 2606 OID 17077)
-- Name: constituer fk_con_on_documents_entity; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.constituer
    ADD CONSTRAINT fk_con_on_documents_entity FOREIGN KEY (documents_id) REFERENCES document.documents(id);


--
-- TOC entry 5219 (class 2606 OID 17082)
-- Name: concerner fk_concerner_on_distributeurs_entity; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.concerner
    ADD CONSTRAINT fk_concerner_on_distributeurs_entity FOREIGN KEY (distributeurs_id) REFERENCES document.personnes(id);


--
-- TOC entry 5220 (class 2606 OID 17087)
-- Name: concerner fk_concerner_on_preco_mouvements_qtes_entity; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.concerner
    ADD CONSTRAINT fk_concerner_on_preco_mouvements_qtes_entity FOREIGN KEY (precomouvementsqtes_id) REFERENCES document.precomouvementsqtes(id);


--
-- TOC entry 5223 (class 2606 OID 17092)
-- Name: deltasoldes fk_deltasoldes_comptes; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.deltasoldes
    ADD CONSTRAINT fk_deltasoldes_comptes FOREIGN KEY (comptes_id) REFERENCES document.comptes(id);


--
-- TOC entry 5224 (class 2606 OID 17097)
-- Name: deltasoldes fk_deltasoldes_exemplaires; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.deltasoldes
    ADD CONSTRAINT fk_deltasoldes_exemplaires FOREIGN KEY (exemplaires_id) REFERENCES document.exemplaires(id);


--
-- TOC entry 5255 (class 2606 OID 17102)
-- Name: mouvements fk_distributeurs_mouvements; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.mouvements
    ADD CONSTRAINT fk_distributeurs_mouvements FOREIGN KEY (distributeurs_id) REFERENCES document.personnes(id);


--
-- TOC entry 5267 (class 2606 OID 17107)
-- Name: promotions fk_distributeurs_promotions; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.promotions
    ADD CONSTRAINT fk_distributeurs_promotions FOREIGN KEY (distributeurs_id) REFERENCES document.personnes(id);


--
-- TOC entry 5232 (class 2606 OID 17112)
-- Name: documentspromotions fk_doc_on_documents_entity; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.documentspromotions
    ADD CONSTRAINT fk_doc_on_documents_entity FOREIGN KEY (documents_id) REFERENCES document.documents(id);


--
-- TOC entry 5233 (class 2606 OID 17117)
-- Name: documentspromotions fk_doc_on_promotions_entity; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.documentspromotions
    ADD CONSTRAINT fk_doc_on_promotions_entity FOREIGN KEY (promotions_id) REFERENCES document.promotions(id);


--
-- TOC entry 5226 (class 2606 OID 17122)
-- Name: docetats fk_docetats_on_documents; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.docetats
    ADD CONSTRAINT fk_docetats_on_documents FOREIGN KEY (documents_id) REFERENCES document.documents(id);


--
-- TOC entry 5227 (class 2606 OID 17127)
-- Name: docetats fk_docetats_on_etapes; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.docetats
    ADD CONSTRAINT fk_docetats_on_etapes FOREIGN KEY (etapes_id) REFERENCES document.etapes(id);


--
-- TOC entry 5228 (class 2606 OID 17132)
-- Name: docetats fk_docetats_on_etats; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.docetats
    ADD CONSTRAINT fk_docetats_on_etats FOREIGN KEY (etats_id) REFERENCES document.etats(id);


--
-- TOC entry 5229 (class 2606 OID 17137)
-- Name: docetats fk_docetats_on_validations; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.docetats
    ADD CONSTRAINT fk_docetats_on_validations FOREIGN KEY (validations_id) REFERENCES document.validations(id);


--
-- TOC entry 5230 (class 2606 OID 17142)
-- Name: docetats_predecesseurs fk_docetats_predecesseurs_on_docetats; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.docetats_predecesseurs
    ADD CONSTRAINT fk_docetats_predecesseurs_on_docetats FOREIGN KEY (docetats_id) REFERENCES document.docetats(id);


--
-- TOC entry 5231 (class 2606 OID 17147)
-- Name: docetats_predecesseurs fk_docetats_predecesseurs_suivant_on_docetats; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.docetats_predecesseurs
    ADD CONSTRAINT fk_docetats_predecesseurs_suivant_on_docetats FOREIGN KEY (predecesseur_id) REFERENCES document.docetats(id);


--
-- TOC entry 5238 (class 2606 OID 17152)
-- Name: elementsbases fk_element; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.elementsbases
    ADD CONSTRAINT fk_element FOREIGN KEY (element_id) REFERENCES document.elements(id);


--
-- TOC entry 5239 (class 2606 OID 17157)
-- Name: elementslangues fk_elements_elementslangues; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.elementslangues
    ADD CONSTRAINT fk_elements_elementslangues FOREIGN KEY (elements_id) REFERENCES document.elements(id);


--
-- TOC entry 5234 (class 2606 OID 17162)
-- Name: elements fk_elementsbase_elements; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.elements
    ADD CONSTRAINT fk_elementsbase_elements FOREIGN KEY (elementsbases_id) REFERENCES document.elementsbases(id);


--
-- TOC entry 5236 (class 2606 OID 17167)
-- Name: elementsbaselanques fk_elementsbase_elementsbaselangues; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.elementsbaselanques
    ADD CONSTRAINT fk_elementsbase_elementsbaselangues FOREIGN KEY (elementsbases_id) REFERENCES document.elementsbases(id);


--
-- TOC entry 5241 (class 2606 OID 17172)
-- Name: etapes fk_etapes_on_parcours; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.etapes
    ADD CONSTRAINT fk_etapes_on_parcours FOREIGN KEY (parcours_id) REFERENCES document.parcours(id);


--
-- TOC entry 5242 (class 2606 OID 17177)
-- Name: exemplaires fk_exemplaire_documents; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.exemplaires
    ADD CONSTRAINT fk_exemplaire_documents FOREIGN KEY (documents_id) REFERENCES document.documents(id);


--
-- TOC entry 5243 (class 2606 OID 17182)
-- Name: exemplaires fk_exemplaires_personnes; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.exemplaires
    ADD CONSTRAINT fk_exemplaires_personnes FOREIGN KEY (personnes_id) REFERENCES document.personnes(id);


--
-- TOC entry 5244 (class 2606 OID 17187)
-- Name: famillespromotions fk_fam_on_familles_entity; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.famillespromotions
    ADD CONSTRAINT fk_fam_on_familles_entity FOREIGN KEY (familles_id) REFERENCES document.familles(id);


--
-- TOC entry 5245 (class 2606 OID 17192)
-- Name: famillespromotions fk_fam_on_promotions_entity; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.famillespromotions
    ADD CONSTRAINT fk_fam_on_promotions_entity FOREIGN KEY (promotions_id) REFERENCES document.promotions(id);


--
-- TOC entry 5246 (class 2606 OID 17197)
-- Name: filesattentes fk_filesattentes_on_services; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.filesattentes
    ADD CONSTRAINT fk_filesattentes_on_services FOREIGN KEY (services_id) REFERENCES document.services(id);


--
-- TOC entry 5249 (class 2606 OID 17202)
-- Name: menus fk_groupes_menus; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.menus
    ADD CONSTRAINT fk_groupes_menus FOREIGN KEY (groupes_id) REFERENCES document.groupes(id);


--
-- TOC entry 5283 (class 2606 OID 17207)
-- Name: utilisateurs fk_groupes_utilisateurs; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.utilisateurs
    ADD CONSTRAINT fk_groupes_utilisateurs FOREIGN KEY (groupes_id) REFERENCES document.groupes(id);


--
-- TOC entry 5248 (class 2606 OID 17212)
-- Name: jouerroles fk_jouerroles_on_roles; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.jouerroles
    ADD CONSTRAINT fk_jouerroles_on_roles FOREIGN KEY (roles_id) REFERENCES document.roles(id);


--
-- TOC entry 5214 (class 2606 OID 17217)
-- Name: actionslangues fk_langues_actionslangues; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.actionslangues
    ADD CONSTRAINT fk_langues_actionslangues FOREIGN KEY (langues_id) REFERENCES document.langues(id);


--
-- TOC entry 5237 (class 2606 OID 17222)
-- Name: elementsbaselanques fk_langues_elementsbaselangues; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.elementsbaselanques
    ADD CONSTRAINT fk_langues_elementsbaselangues FOREIGN KEY (langues_id) REFERENCES document.langues(id);


--
-- TOC entry 5240 (class 2606 OID 17227)
-- Name: elementslangues fk_langues_elementslangues; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.elementslangues
    ADD CONSTRAINT fk_langues_elementslangues FOREIGN KEY (langues_id) REFERENCES document.langues(id);


--
-- TOC entry 5235 (class 2606 OID 17232)
-- Name: elements fk_menus_elements; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.elements
    ADD CONSTRAINT fk_menus_elements FOREIGN KEY (menus_id) REFERENCES document.menus(id);


--
-- TOC entry 5247 (class 2606 OID 17237)
-- Name: groupes fk_menus_groupes; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.groupes
    ADD CONSTRAINT fk_menus_groupes FOREIGN KEY (menus_id) REFERENCES document.menus(id);


--
-- TOC entry 5284 (class 2606 OID 17242)
-- Name: utilisateurs fk_menus_utilisateurs; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.utilisateurs
    ADD CONSTRAINT fk_menus_utilisateurs FOREIGN KEY (menus_id) REFERENCES document.menus(id);


--
-- TOC entry 5251 (class 2606 OID 17247)
-- Name: missions fk_missions_on_services; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.missions
    ADD CONSTRAINT fk_missions_on_services FOREIGN KEY (services_id) REFERENCES document.services(id);


--
-- TOC entry 5252 (class 2606 OID 17252)
-- Name: mouvementcaisses fk_mouvementcaisses_caisses; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.mouvementcaisses
    ADD CONSTRAINT fk_mouvementcaisses_caisses FOREIGN KEY (caisses_id) REFERENCES document.caisses(id);


--
-- TOC entry 5253 (class 2606 OID 17257)
-- Name: mouvementcaisses fk_mouvementcaisses_comptes; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.mouvementcaisses
    ADD CONSTRAINT fk_mouvementcaisses_comptes FOREIGN KEY (comptes_id) REFERENCES document.comptes(id);


--
-- TOC entry 5256 (class 2606 OID 17262)
-- Name: mouvements fk_mouvements_on_ressources; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.mouvements
    ADD CONSTRAINT fk_mouvements_on_ressources FOREIGN KEY (ressources_id) REFERENCES document.ressources(id);


--
-- TOC entry 5254 (class 2606 OID 17267)
-- Name: mouvementcaisses fk_mouvementscaisses_exemplaires; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.mouvementcaisses
    ADD CONSTRAINT fk_mouvementscaisses_exemplaires FOREIGN KEY (exemplaires_id) REFERENCES document.exemplaires(id);


--
-- TOC entry 5257 (class 2606 OID 17272)
-- Name: ordreetats fk_ordreetats_on_etats; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.ordreetats
    ADD CONSTRAINT fk_ordreetats_on_etats FOREIGN KEY (etats_id) REFERENCES document.etats(id);


--
-- TOC entry 5258 (class 2606 OID 17277)
-- Name: organiser fk_organisation_organiser; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.organiser
    ADD CONSTRAINT fk_organisation_organiser FOREIGN KEY (organisations_id) REFERENCES document.organisations(id);


--
-- TOC entry 5260 (class 2606 OID 17282)
-- Name: personnels fk_personnels_personnes; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.personnels
    ADD CONSTRAINT fk_personnels_personnes FOREIGN KEY (personnels_id) REFERENCES document.personnes(id);


--
-- TOC entry 5261 (class 2606 OID 17287)
-- Name: personnes fk_personnes_comptes; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.personnes
    ADD CONSTRAINT fk_personnes_comptes FOREIGN KEY (comptes_id) REFERENCES document.comptes(id);


--
-- TOC entry 5278 (class 2606 OID 17418)
-- Name: tickets fk_personnes_on_tickets; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.tickets
    ADD CONSTRAINT fk_personnes_on_tickets FOREIGN KEY (personnesphysique_id) REFERENCES document.personnesphysique(personnesphysique_id);


--
-- TOC entry 5262 (class 2606 OID 17292)
-- Name: personnes fk_personnes_ticket; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.personnes
    ADD CONSTRAINT fk_personnes_ticket FOREIGN KEY (ticket_id) REFERENCES document.tickets(id);


--
-- TOC entry 5265 (class 2606 OID 17297)
-- Name: precomouvementsqtes fk_precomouvementsqtes_on_precomouvements; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.precomouvementsqtes
    ADD CONSTRAINT fk_precomouvementsqtes_on_precomouvements FOREIGN KEY (precomouvements_id) REFERENCES document.precomouvements(id);


--
-- TOC entry 5266 (class 2606 OID 17302)
-- Name: precomouvementsqtes fk_precomouvementsqtes_on_ressources; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.precomouvementsqtes
    ADD CONSTRAINT fk_precomouvementsqtes_on_ressources FOREIGN KEY (ressources_id) REFERENCES document.ressources(id);


--
-- TOC entry 5271 (class 2606 OID 17307)
-- Name: ressourcespromotions fk_res_on_promotions_entity; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.ressourcespromotions
    ADD CONSTRAINT fk_res_on_promotions_entity FOREIGN KEY (promotions_id) REFERENCES document.promotions(id);


--
-- TOC entry 5272 (class 2606 OID 17312)
-- Name: ressourcespromotions fk_res_on_ressources_entity; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.ressourcespromotions
    ADD CONSTRAINT fk_res_on_ressources_entity FOREIGN KEY (ressources_id) REFERENCES document.ressources(id);


--
-- TOC entry 5268 (class 2606 OID 17317)
-- Name: respecter fk_respecter_on_mouvements_entity; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.respecter
    ADD CONSTRAINT fk_respecter_on_mouvements_entity FOREIGN KEY (mouvements_id) REFERENCES document.mouvements(id);


--
-- TOC entry 5269 (class 2606 OID 17322)
-- Name: respecter fk_respecter_on_preco_mouvements_entity; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.respecter
    ADD CONSTRAINT fk_respecter_on_preco_mouvements_entity FOREIGN KEY (precomouvements_id) REFERENCES document.precomouvements(id);


--
-- TOC entry 5270 (class 2606 OID 17327)
-- Name: ressources fk_ressources_on_familles; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.ressources
    ADD CONSTRAINT fk_ressources_on_familles FOREIGN KEY (familles_id) REFERENCES document.familles(id);


--
-- TOC entry 5273 (class 2606 OID 17332)
-- Name: sapplique fk_sapplique_on_familles_entity; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.sapplique
    ADD CONSTRAINT fk_sapplique_on_familles_entity FOREIGN KEY (familles_id) REFERENCES document.familles(id);


--
-- TOC entry 5274 (class 2606 OID 17337)
-- Name: sapplique fk_sapplique_on_preco_mouvements_qtes_entity; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.sapplique
    ADD CONSTRAINT fk_sapplique_on_preco_mouvements_qtes_entity FOREIGN KEY (precomouvementsqtes_id) REFERENCES document.precomouvementsqtes(id);


--
-- TOC entry 5275 (class 2606 OID 17342)
-- Name: services fk_services_on_filesattentes; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.services
    ADD CONSTRAINT fk_services_on_filesattentes FOREIGN KEY (filesattentes_id) REFERENCES document.filesattentes(id);


--
-- TOC entry 5276 (class 2606 OID 17347)
-- Name: suivre fk_suivre_on_documents_entity; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.suivre
    ADD CONSTRAINT fk_suivre_on_documents_entity FOREIGN KEY (documents_id) REFERENCES document.documents(id);


--
-- TOC entry 5277 (class 2606 OID 17352)
-- Name: suivre fk_suivre_on_preco_mouvements_entity; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.suivre
    ADD CONSTRAINT fk_suivre_on_preco_mouvements_entity FOREIGN KEY (precomouvements_id) REFERENCES document.precomouvements(id);


--
-- TOC entry 5279 (class 2606 OID 17362)
-- Name: ticketsfilesattentes fk_ticketsfilesattentes_on_filesattentes; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.ticketsfilesattentes
    ADD CONSTRAINT fk_ticketsfilesattentes_on_filesattentes FOREIGN KEY (filesattentes_id) REFERENCES document.filesattentes(id);


--
-- TOC entry 5280 (class 2606 OID 17367)
-- Name: ticketsfilesattentes fk_ticketsfilesattentes_on_tickets; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.ticketsfilesattentes
    ADD CONSTRAINT fk_ticketsfilesattentes_on_tickets FOREIGN KEY (tickets_id) REFERENCES document.tickets(id);


--
-- TOC entry 5281 (class 2606 OID 17372)
-- Name: traiter fk_traiter_on_documents_entity; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.traiter
    ADD CONSTRAINT fk_traiter_on_documents_entity FOREIGN KEY (documents_id) REFERENCES document.documents(id);


--
-- TOC entry 5282 (class 2606 OID 17377)
-- Name: traiter fk_traiter_on_missions_entity; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.traiter
    ADD CONSTRAINT fk_traiter_on_missions_entity FOREIGN KEY (missions_id) REFERENCES document.missions(id);


--
-- TOC entry 5250 (class 2606 OID 17382)
-- Name: menus fk_utilisateur_menus; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.menus
    ADD CONSTRAINT fk_utilisateur_menus FOREIGN KEY (utilisateurs_id) REFERENCES document.utilisateurs(id);


--
-- TOC entry 5259 (class 2606 OID 17387)
-- Name: organiser fk_utilisateurs_organiser; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.organiser
    ADD CONSTRAINT fk_utilisateurs_organiser FOREIGN KEY (utilisateurs_id) REFERENCES document.utilisateurs(id);


--
-- TOC entry 5285 (class 2606 OID 17392)
-- Name: validations fk_validations_on_roles; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.validations
    ADD CONSTRAINT fk_validations_on_roles FOREIGN KEY (roles_id) REFERENCES document.roles(id);


--
-- TOC entry 5286 (class 2606 OID 17397)
-- Name: violer fk_violer_on_mouvements_entity; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.violer
    ADD CONSTRAINT fk_violer_on_mouvements_entity FOREIGN KEY (mouvements_id) REFERENCES document.mouvements(id);


--
-- TOC entry 5287 (class 2606 OID 17402)
-- Name: violer fk_violer_on_preco_mouvements_entity; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.violer
    ADD CONSTRAINT fk_violer_on_preco_mouvements_entity FOREIGN KEY (precomouvements_id) REFERENCES document.precomouvements(id);


--
-- TOC entry 5263 (class 2606 OID 17407)
-- Name: personnesmorales personnesmorales_id_fkey; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.personnesmorales
    ADD CONSTRAINT personnesmorales_id_fkey FOREIGN KEY (personnesmorales_id) REFERENCES document.personnes(id);


--
-- TOC entry 5264 (class 2606 OID 17412)
-- Name: personnesphysique personnesphysique_id_fkey; Type: FK CONSTRAINT; Schema: document; Owner: postgres
--

ALTER TABLE ONLY document.personnesphysique
    ADD CONSTRAINT personnesphysique_id_fkey FOREIGN KEY (personnesphysique_id) REFERENCES document.personnes(id);


-- Completed on 2025-10-30 02:18:41

--
-- PostgreSQL database dump complete
--

\unrestrict LWLHMWBzJV6gm8lRDXsHG616jSBKXPDATPPragz95WIIadjFUcXQiQhiSr2rrJh

