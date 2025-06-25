--
-- PostgreSQL database dump
--

-- Dumped from database version 16.3
-- Dumped by pg_dump version 16.3

-- Started on 2025-06-25 08:57:06

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
-- TOC entry 5093 (class 1262 OID 28060)
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
-- TOC entry 7 (class 2615 OID 45546)
-- Name: login; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA login;


ALTER SCHEMA login OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 279 (class 1259 OID 45547)
-- Name: actions; Type: TABLE; Schema: login; Owner: postgres
--

CREATE TABLE login.actions (
    id character varying(255) NOT NULL,
    libelle character varying(255),
    etat boolean,
    datecreation date,
    datemodification date,
    elementsbase_id character varying(255)
);


ALTER TABLE login.actions OWNER TO postgres;

--
-- TOC entry 280 (class 1259 OID 45552)
-- Name: actionslangues; Type: TABLE; Schema: login; Owner: postgres
--

CREATE TABLE login.actionslangues (
    langues_id character varying(255) NOT NULL,
    actions_id character varying(255) NOT NULL,
    valeurlibelle character varying(255)
);


ALTER TABLE login.actionslangues OWNER TO postgres;

--
-- TOC entry 281 (class 1259 OID 45619)
-- Name: elements; Type: TABLE; Schema: login; Owner: postgres
--

CREATE TABLE login.elements (
    id character varying(255) NOT NULL,
    libelle character varying(255),
    etat boolean,
    datesouscription date,
    datemodification date,
    menus_id character varying(255),
    elementsbases_id character varying(255)
);


ALTER TABLE login.elements OWNER TO postgres;

--
-- TOC entry 282 (class 1259 OID 45624)
-- Name: elementsbaselanques; Type: TABLE; Schema: login; Owner: postgres
--

CREATE TABLE login.elementsbaselanques (
    langues_id character varying(255) NOT NULL,
    elementsbases_id character varying(255) NOT NULL,
    valeurlibelle character varying(255)
);


ALTER TABLE login.elementsbaselanques OWNER TO postgres;

--
-- TOC entry 283 (class 1259 OID 45629)
-- Name: elementsbases; Type: TABLE; Schema: login; Owner: postgres
--

CREATE TABLE login.elementsbases (
    id character varying(255) NOT NULL,
    libelle character varying(255),
    etat boolean,
    datesouscription date,
    datemodification date,
    moduleangular character varying(255),
    element_id character varying(255)
);


ALTER TABLE login.elementsbases OWNER TO postgres;

--
-- TOC entry 284 (class 1259 OID 45634)
-- Name: elementslangues; Type: TABLE; Schema: login; Owner: postgres
--

CREATE TABLE login.elementslangues (
    langues_id character varying(255) NOT NULL,
    elements_id character varying(255) NOT NULL,
    valeurlibelle character varying(255)
);


ALTER TABLE login.elementslangues OWNER TO postgres;

--
-- TOC entry 285 (class 1259 OID 45669)
-- Name: groupes; Type: TABLE; Schema: login; Owner: postgres
--

CREATE TABLE login.groupes (
    id character varying(255) NOT NULL,
    libelle character varying(255),
    etat boolean,
    datecreation date,
    datemodification date,
    menus_id character varying(255)
);


ALTER TABLE login.groupes OWNER TO postgres;

--
-- TOC entry 286 (class 1259 OID 45679)
-- Name: langues; Type: TABLE; Schema: login; Owner: postgres
--

CREATE TABLE login.langues (
    id character varying(255) NOT NULL,
    libelle character varying(255),
    etat boolean,
    datesouscription date,
    datemodification date
);


ALTER TABLE login.langues OWNER TO postgres;

--
-- TOC entry 287 (class 1259 OID 45684)
-- Name: menus; Type: TABLE; Schema: login; Owner: postgres
--

CREATE TABLE login.menus (
    id character varying(255) NOT NULL,
    etat boolean,
    datecreation date,
    utilisateurs_id character varying(255),
    groupes_id character varying(255)
);


ALTER TABLE login.menus OWNER TO postgres;

--
-- TOC entry 288 (class 1259 OID 45709)
-- Name: organisations; Type: TABLE; Schema: login; Owner: postgres
--

CREATE TABLE login.organisations (
    id character varying(255) NOT NULL,
    raisonsociale character varying(255)
);


ALTER TABLE login.organisations OWNER TO postgres;

--
-- TOC entry 289 (class 1259 OID 45714)
-- Name: organiser; Type: TABLE; Schema: login; Owner: postgres
--

CREATE TABLE login.organiser (
    organisations_id character varying(255) NOT NULL,
    utilisateurs_id character varying(255) NOT NULL
);


ALTER TABLE login.organiser OWNER TO postgres;

--
-- TOC entry 290 (class 1259 OID 45750)
-- Name: promotions; Type: TABLE; Schema: login; Owner: postgres
--

CREATE TABLE login.promotions (
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


ALTER TABLE login.promotions OWNER TO postgres;

--
-- TOC entry 291 (class 1259 OID 45815)
-- Name: utilisateurs; Type: TABLE; Schema: login; Owner: postgres
--

CREATE TABLE login.utilisateurs (
    id character varying(255) NOT NULL,
    login character varying(255),
    mdp character varying(255),
    etat character varying(255),
    datecreation date,
    datemodification date,
    groupes_id character varying(255),
    roles character varying(255),
    nom character varying(255),
    prenom character varying(255)
);


ALTER TABLE login.utilisateurs OWNER TO postgres;

--
-- TOC entry 5075 (class 0 OID 45547)
-- Dependencies: 279
-- Data for Name: actions; Type: TABLE DATA; Schema: login; Owner: postgres
--

INSERT INTO login.actions (id, libelle, etat, datecreation, datemodification, elementsbase_id) VALUES ('j6bca7iy-5i6j-4c8f-3340-7a1b0c9d8e7f', 'Afficher', true, '2024-01-01', '2024-01-01', 'u1lmn2tj-0t1u-4d8f-6495-8k7l6m5n4o3p') ON CONFLICT DO NOTHING;
INSERT INTO login.actions (id, libelle, etat, datecreation, datemodification, elementsbase_id) VALUES ('k7cdb8jz-6j7k-4d90-2259-6b0c9d8e7f6g', 'Ajouter', true, '2024-01-01', '2024-01-01', 'v2mno3uk-1u2v-4e90-5304-7l6m5n4o3p2q') ON CONFLICT DO NOTHING;
INSERT INTO login.actions (id, libelle, etat, datecreation, datemodification, elementsbase_id) VALUES ('l8dec9ka-7k8l-4fa1-1168-5c9d8e7f6g5h', 'Modifier', true, '2024-01-01', '2024-01-01', 'w3nop4vl-2v3w-4fa1-4213-6m5n4o3p2q1r') ON CONFLICT DO NOTHING;
INSERT INTO login.actions (id, libelle, etat, datecreation, datemodification, elementsbase_id) VALUES ('m9efd0lb-8l9m-40b2-0077-4d8e7f6g5h4i', 'Supprimer', true, '2024-01-01', '2024-01-01', 'x4opq5wm-3w4x-40b2-3122-5n4o3p2q1r0s') ON CONFLICT DO NOTHING;
INSERT INTO login.actions (id, libelle, etat, datecreation, datemodification, elementsbase_id) VALUES ('n0fge1mc-9m0n-41c3-7986-3e7f6g5h4i3j', 'Exporter', true, '2024-01-01', '2024-01-01', 'y5pqr6xn-4x5y-41c3-2031-4o3p2q1r0s9t') ON CONFLICT DO NOTHING;


--
-- TOC entry 5076 (class 0 OID 45552)
-- Dependencies: 280
-- Data for Name: actionslangues; Type: TABLE DATA; Schema: login; Owner: postgres
--

INSERT INTO login.actionslangues (langues_id, actions_id, valeurlibelle) VALUES ('e1vwx2dt-0d1e-4729-4495-8u7v6w5x4y3z', 'j6bca7iy-5i6j-4c8f-3340-7a1b0c9d8e7f', 'Afficher') ON CONFLICT DO NOTHING;
INSERT INTO login.actionslangues (langues_id, actions_id, valeurlibelle) VALUES ('f2wxy3eu-1e2f-483a-3304-7v6w5x4y3z2a', 'j6bca7iy-5i6j-4c8f-3340-7a1b0c9d8e7f', 'Show') ON CONFLICT DO NOTHING;
INSERT INTO login.actionslangues (langues_id, actions_id, valeurlibelle) VALUES ('e1vwx2dt-0d1e-4729-4495-8u7v6w5x4y3z', 'k7cdb8jz-6j7k-4d90-2259-6b0c9d8e7f6g', 'Ajouter') ON CONFLICT DO NOTHING;
INSERT INTO login.actionslangues (langues_id, actions_id, valeurlibelle) VALUES ('f2wxy3eu-1e2f-483a-3304-7v6w5x4y3z2a', 'k7cdb8jz-6j7k-4d90-2259-6b0c9d8e7f6g', 'Add') ON CONFLICT DO NOTHING;
INSERT INTO login.actionslangues (langues_id, actions_id, valeurlibelle) VALUES ('e1vwx2dt-0d1e-4729-4495-8u7v6w5x4y3z', 'l8dec9ka-7k8l-4fa1-1168-5c9d8e7f6g5h', 'Modifier') ON CONFLICT DO NOTHING;


--
-- TOC entry 5077 (class 0 OID 45619)
-- Dependencies: 281
-- Data for Name: elements; Type: TABLE DATA; Schema: login; Owner: postgres
--

INSERT INTO login.elements (id, libelle, etat, datesouscription, datemodification, menus_id, elementsbases_id) VALUES ('z6qrs7yo-5y6z-42d4-1940-3p2q1r0s9t8u', 'Tableau de bord principal', true, '2024-01-01', '2024-01-01', 'p6ghi7oe-5o6p-483a-3940-3f2g1h0i9j8k', 'u1lmn2tj-0t1u-4d8f-6495-8k7l6m5n4o3p') ON CONFLICT DO NOTHING;
INSERT INTO login.elements (id, libelle, etat, datesouscription, datemodification, menus_id, elementsbases_id) VALUES ('a7rst8zp-6z7a-43e5-0859-2q1r0s9t8u7v', 'Ajouter un utilisateur', true, '2024-01-01', '2024-01-01', 'q7hij8pf-6p7q-494b-2859-2g1h0i9j8k7l', 'v2mno3uk-1u2v-4e90-5304-7l6m5n4o3p2q') ON CONFLICT DO NOTHING;
INSERT INTO login.elements (id, libelle, etat, datesouscription, datemodification, menus_id, elementsbases_id) VALUES ('b8stu9aq-7a8b-44f6-7768-1r0s9t8u7v6w', 'Supprimer un groupe', true, '2024-01-01', '2024-01-01', 'r8ijk9qg-7q8r-4a5c-1768-1h0i9j8k7l6m', 'w3nop4vl-2v3w-4fa1-4213-6m5n4o3p2q1r') ON CONFLICT DO NOTHING;
INSERT INTO login.elements (id, libelle, etat, datesouscription, datemodification, menus_id, elementsbases_id) VALUES ('c9tuv0br-8b9c-4507-6677-0s9t8u7v6w5x', 'Modifier un menu', true, '2024-01-01', '2024-01-01', 's9jkl0rh-8r9s-4b6d-0677-0i9j8k7l6m5n', 'x4opq5wm-3w4x-40b2-3122-5n4o3p2q1r0s') ON CONFLICT DO NOTHING;
INSERT INTO login.elements (id, libelle, etat, datesouscription, datemodification, menus_id, elementsbases_id) VALUES ('d0uvw1cs-9c0d-4618-5586-9t8u7v6w5x4y', 'Afficher les éléments', true, '2024-01-01', '2024-01-01', 't0klm1si-9s0t-4c7e-7586-9j8k7l6m5n4o', 'y5pqr6xn-4x5y-41c3-2031-4o3p2q1r0s9t') ON CONFLICT DO NOTHING;


--
-- TOC entry 5078 (class 0 OID 45624)
-- Dependencies: 282
-- Data for Name: elementsbaselanques; Type: TABLE DATA; Schema: login; Owner: postgres
--

INSERT INTO login.elementsbaselanques (langues_id, elementsbases_id, valeurlibelle) VALUES ('e1vwx2dt-0d1e-4729-4495-8u7v6w5x4y3z', 'u1lmn2tj-0t1u-4d8f-6495-8k7l6m5n4o3p', 'Tableau de bord') ON CONFLICT DO NOTHING;
INSERT INTO login.elementsbaselanques (langues_id, elementsbases_id, valeurlibelle) VALUES ('f2wxy3eu-1e2f-483a-3304-7v6w5x4y3z2a', 'u1lmn2tj-0t1u-4d8f-6495-8k7l6m5n4o3p', 'Dashboard') ON CONFLICT DO NOTHING;
INSERT INTO login.elementsbaselanques (langues_id, elementsbases_id, valeurlibelle) VALUES ('e1vwx2dt-0d1e-4729-4495-8u7v6w5x4y3z', 'v2mno3uk-1u2v-4e90-5304-7l6m5n4o3p2q', 'Gestion des utilisateurs') ON CONFLICT DO NOTHING;
INSERT INTO login.elementsbaselanques (langues_id, elementsbases_id, valeurlibelle) VALUES ('f2wxy3eu-1e2f-483a-3304-7v6w5x4y3z2a', 'v2mno3uk-1u2v-4e90-5304-7l6m5n4o3p2q', 'User Management') ON CONFLICT DO NOTHING;
INSERT INTO login.elementsbaselanques (langues_id, elementsbases_id, valeurlibelle) VALUES ('e1vwx2dt-0d1e-4729-4495-8u7v6w5x4y3z', 'w3nop4vl-2v3w-4fa1-4213-6m5n4o3p2q1r', 'Gestion des groupes') ON CONFLICT DO NOTHING;


--
-- TOC entry 5079 (class 0 OID 45629)
-- Dependencies: 283
-- Data for Name: elementsbases; Type: TABLE DATA; Schema: login; Owner: postgres
--

INSERT INTO login.elementsbases (id, libelle, etat, datesouscription, datemodification, moduleangular, element_id) VALUES ('u1lmn2tj-0t1u-4d8f-6495-8k7l6m5n4o3p', 'Tableau de bord', true, '2024-01-01', '2024-01-01', 'dashboard', 'a7rst8zp-6z7a-43e5-0859-2q1r0s9t8u7v') ON CONFLICT DO NOTHING;
INSERT INTO login.elementsbases (id, libelle, etat, datesouscription, datemodification, moduleangular, element_id) VALUES ('v2mno3uk-1u2v-4e90-5304-7l6m5n4o3p2q', 'Gestion des utilisateurs', true, '2024-01-01', '2024-01-01', 'userManagement', 'b8stu9aq-7a8b-44f6-7768-1r0s9t8u7v6w') ON CONFLICT DO NOTHING;
INSERT INTO login.elementsbases (id, libelle, etat, datesouscription, datemodification, moduleangular, element_id) VALUES ('w3nop4vl-2v3w-4fa1-4213-6m5n4o3p2q1r', 'Gestion des groupes', true, '2024-01-01', '2024-01-01', 'groupManagement', 'c9tuv0br-8b9c-4507-6677-0s9t8u7v6w5x') ON CONFLICT DO NOTHING;
INSERT INTO login.elementsbases (id, libelle, etat, datesouscription, datemodification, moduleangular, element_id) VALUES ('x4opq5wm-3w4x-40b2-3122-5n4o3p2q1r0s', 'Gestion des menus', true, '2024-01-01', '2024-01-01', 'menuManagement', 'd0uvw1cs-9c0d-4618-5586-9t8u7v6w5x4y') ON CONFLICT DO NOTHING;
INSERT INTO login.elementsbases (id, libelle, etat, datesouscription, datemodification, moduleangular, element_id) VALUES ('y5pqr6xn-4x5y-41c3-2031-4o3p2q1r0s9t', 'Gestion des éléments', true, '2024-01-01', '2024-01-01', 'elementManagement', 'z6qrs7yo-5y6z-42d4-1940-3p2q1r0s9t8u') ON CONFLICT DO NOTHING;


--
-- TOC entry 5080 (class 0 OID 45634)
-- Dependencies: 284
-- Data for Name: elementslangues; Type: TABLE DATA; Schema: login; Owner: postgres
--

INSERT INTO login.elementslangues (langues_id, elements_id, valeurlibelle) VALUES ('e1vwx2dt-0d1e-4729-4495-8u7v6w5x4y3z', 'z6qrs7yo-5y6z-42d4-1940-3p2q1r0s9t8u', 'Tableau de bord principal') ON CONFLICT DO NOTHING;
INSERT INTO login.elementslangues (langues_id, elements_id, valeurlibelle) VALUES ('f2wxy3eu-1e2f-483a-3304-7v6w5x4y3z2a', 'z6qrs7yo-5y6z-42d4-1940-3p2q1r0s9t8u', 'Main Dashboard') ON CONFLICT DO NOTHING;
INSERT INTO login.elementslangues (langues_id, elements_id, valeurlibelle) VALUES ('e1vwx2dt-0d1e-4729-4495-8u7v6w5x4y3z', 'a7rst8zp-6z7a-43e5-0859-2q1r0s9t8u7v', 'Ajouter un utilisateur') ON CONFLICT DO NOTHING;
INSERT INTO login.elementslangues (langues_id, elements_id, valeurlibelle) VALUES ('f2wxy3eu-1e2f-483a-3304-7v6w5x4y3z2a', 'a7rst8zp-6z7a-43e5-0859-2q1r0s9t8u7v', 'Add User') ON CONFLICT DO NOTHING;
INSERT INTO login.elementslangues (langues_id, elements_id, valeurlibelle) VALUES ('e1vwx2dt-0d1e-4729-4495-8u7v6w5x4y3z', 'b8stu9aq-7a8b-44f6-7768-1r0s9t8u7v6w', 'Supprimer un groupe') ON CONFLICT DO NOTHING;


--
-- TOC entry 5081 (class 0 OID 45669)
-- Dependencies: 285
-- Data for Name: groupes; Type: TABLE DATA; Schema: login; Owner: postgres
--

INSERT INTO login.groupes (id, libelle, etat, datecreation, datemodification, menus_id) VALUES ('f6jkl74e-5e6f-4e90-596a-3b2a1e0d9c8a', 'Administrateurs', true, '2024-01-01', '2024-01-01', 'p6ghi7oe-5o6p-483a-3940-3f2g1h0i9j8k') ON CONFLICT DO NOTHING;
INSERT INTO login.groupes (id, libelle, etat, datecreation, datemodification, menus_id) VALUES ('g7mno85f-6f7g-4fa1-4859-2a1e0d9c8a7b', 'Utilisateurs', true, '2024-01-01', '2024-01-01', 'q7hij8pf-6p7q-494b-2859-2g1h0i9j8k7l') ON CONFLICT DO NOTHING;
INSERT INTO login.groupes (id, libelle, etat, datecreation, datemodification, menus_id) VALUES ('h8pqr96g-7g8h-40b2-5748-1e0d9c8a7b6c', 'Invités', true, '2024-01-01', '2024-01-01', 'r8ijk9qg-7q8r-4a5c-1768-1h0i9j8k7l6m') ON CONFLICT DO NOTHING;
INSERT INTO login.groupes (id, libelle, etat, datecreation, datemodification, menus_id) VALUES ('i9stu07h-8h9i-41c3-6637-0d9c8a7b6c5d', 'Managers', true, '2024-01-01', '2024-01-01', 's9jkl0rh-8r9s-4b6d-0677-0i9j8k7l6m5n') ON CONFLICT DO NOTHING;
INSERT INTO login.groupes (id, libelle, etat, datecreation, datemodification, menus_id) VALUES ('j0vwx18i-9i0j-42d4-7526-9c8a7b6c5d4e', 'Techniciens', true, '2024-01-01', '2024-01-01', 't0klm1si-9s0t-4c7e-7586-9j8k7l6m5n4o') ON CONFLICT DO NOTHING;
INSERT INTO login.groupes (id, libelle, etat, datecreation, datemodification, menus_id) VALUES ('f6jkl74e-5e6f-4e90-596a-3b2a1e0a7c8a', 'Admin', true, '2024-01-01', '2024-01-01', 'p6ghi7oe-5o6p-483a-3940-3f2g1h779j8k') ON CONFLICT DO NOTHING;
INSERT INTO login.groupes (id, libelle, etat, datecreation, datemodification, menus_id) VALUES ('a1b2c3d4-e5f6-7890-abcd-1234567890ef', 'Testeurs', true, '2025-05-10', '2025-05-10', 't0klm1si-9s0t-4c7e-7586-9j8k7l6m5n4o') ON CONFLICT DO NOTHING;


--
-- TOC entry 5082 (class 0 OID 45679)
-- Dependencies: 286
-- Data for Name: langues; Type: TABLE DATA; Schema: login; Owner: postgres
--

INSERT INTO login.langues (id, libelle, etat, datesouscription, datemodification) VALUES ('e1vwx2dt-0d1e-4729-4495-8u7v6w5x4y3z', 'Français', true, '2024-01-01', '2024-01-01') ON CONFLICT DO NOTHING;
INSERT INTO login.langues (id, libelle, etat, datesouscription, datemodification) VALUES ('f2wxy3eu-1e2f-483a-3304-7v6w5x4y3z2a', 'Anglais', true, '2024-01-01', '2024-01-01') ON CONFLICT DO NOTHING;
INSERT INTO login.langues (id, libelle, etat, datesouscription, datemodification) VALUES ('g3xyz4fv-2f3g-494b-2213-6w5x4y3z2a1b', 'Espagnol', true, '2024-01-01', '2024-01-01') ON CONFLICT DO NOTHING;
INSERT INTO login.langues (id, libelle, etat, datesouscription, datemodification) VALUES ('h4yza5gw-3g4h-4a5c-1122-5x4y3z2a1b0c', 'Allemand', true, '2024-01-01', '2024-01-01') ON CONFLICT DO NOTHING;
INSERT INTO login.langues (id, libelle, etat, datesouscription, datemodification) VALUES ('i5zab6hx-4h5i-4b6d-0031-4y3z2a1b0c9d', 'Italien', true, '2024-01-01', '2024-01-01') ON CONFLICT DO NOTHING;


--
-- TOC entry 5083 (class 0 OID 45684)
-- Dependencies: 287
-- Data for Name: menus; Type: TABLE DATA; Schema: login; Owner: postgres
--

INSERT INTO login.menus (id, etat, datecreation, utilisateurs_id, groupes_id) VALUES ('p6ghi7oe-5o6p-483a-3940-3f2g1h0i9j8k', true, '2024-01-01', 'k1yzabc2j-0j1k-43e5-8415-8a7b6c5d4e3f', 'f6jkl74e-5e6f-4e90-596a-3b2a1e0d9c8a') ON CONFLICT DO NOTHING;
INSERT INTO login.menus (id, etat, datecreation, utilisateurs_id, groupes_id) VALUES ('q7hij8pf-6p7q-494b-2859-2g1h0i9j8k7l', true, '2024-01-01', 'l2bcd3ka-1k2l-44f6-9304-7b6c5d4e3f2g', 'g7mno85f-6f7g-4fa1-4859-2a1e0d9c8a7b') ON CONFLICT DO NOTHING;
INSERT INTO login.menus (id, etat, datecreation, utilisateurs_id, groupes_id) VALUES ('r8ijk9qg-7q8r-4a5c-1768-1h0i9j8k7l6m', true, '2024-01-01', 'm3cde4lb-2l3m-4507-0213-6c5d4e3f2g1h', 'h8pqr96g-7g8h-40b2-5748-1e0d9c8a7b6c') ON CONFLICT DO NOTHING;
INSERT INTO login.menus (id, etat, datecreation, utilisateurs_id, groupes_id) VALUES ('s9jkl0rh-8r9s-4b6d-0677-0i9j8k7l6m5n', true, '2024-01-01', 'n4efg5mc-3m4n-4618-1122-5d4e3f2g1h0i', 'i9stu07h-8h9i-41c3-6637-0d9c8a7b6c5d') ON CONFLICT DO NOTHING;
INSERT INTO login.menus (id, etat, datecreation, utilisateurs_id, groupes_id) VALUES ('p6ghi7oe-5o6p-483a-3940-3f2g1h779j8k', true, '2024-01-01', '0190615e-1101-7209-9932-7020bbd55714', 'f6jkl74e-5e6f-4e90-596a-3b2a1e0a7c8a') ON CONFLICT DO NOTHING;
INSERT INTO login.menus (id, etat, datecreation, utilisateurs_id, groupes_id) VALUES ('b2c3d4e5-f6a7-8901-bcde-2345678901fa', true, '2025-05-10', 'e9285758-0c3a-46ae-947e-9af343ac5f62', 'a1b2c3d4-e5f6-7890-abcd-1234567890ef') ON CONFLICT DO NOTHING;
INSERT INTO login.menus (id, etat, datecreation, utilisateurs_id, groupes_id) VALUES ('t0klm1si-9s0t-4c7e-7586-9j8k7l6m5n4o', true, '2024-01-01', 'o5fgh6nd-4n5o-4729-2031-4e3f2g1h0i9j', 'j0vwx18i-9i0j-42d4-7526-9c8a7b6c5d4e') ON CONFLICT DO NOTHING;


--
-- TOC entry 5084 (class 0 OID 45709)
-- Dependencies: 288
-- Data for Name: organisations; Type: TABLE DATA; Schema: login; Owner: postgres
--

INSERT INTO login.organisations (id, raisonsociale) VALUES ('a1eebc99-9c0b-4ef8-bb6d-6bb9bd380a13', 'Entreprise A') ON CONFLICT DO NOTHING;
INSERT INTO login.organisations (id, raisonsociale) VALUES ('b2fcd30a-1a2b-4a5c-9e8f-7d6a5b4c3d2e', 'Entreprise B') ON CONFLICT DO NOTHING;
INSERT INTO login.organisations (id, raisonsociale) VALUES ('c3abc41b-2b3c-4b6d-8a9b-6e5c4d3b2f1a', 'Association C') ON CONFLICT DO NOTHING;
INSERT INTO login.organisations (id, raisonsociale) VALUES ('d4def52c-3c4d-4c7e-7b8a-5d4b3c2a1e0d', 'Fondation D') ON CONFLICT DO NOTHING;
INSERT INTO login.organisations (id, raisonsociale) VALUES ('e5ghi63d-4d5e-4d8f-6a7b-4c3a2b1d0c9b', 'Collectivité E') ON CONFLICT DO NOTHING;
INSERT INTO login.organisations (id, raisonsociale) VALUES ('c3d4e5f6-a7b8-9012-cdef-3456789012ab', 'Entreprise Test') ON CONFLICT DO NOTHING;


--
-- TOC entry 5085 (class 0 OID 45714)
-- Dependencies: 289
-- Data for Name: organiser; Type: TABLE DATA; Schema: login; Owner: postgres
--

INSERT INTO login.organiser (organisations_id, utilisateurs_id) VALUES ('b2fcd30a-1a2b-4a5c-9e8f-7d6a5b4c3d2e', 'l2bcd3ka-1k2l-44f6-9304-7b6c5d4e3f2g') ON CONFLICT DO NOTHING;
INSERT INTO login.organiser (organisations_id, utilisateurs_id) VALUES ('c3abc41b-2b3c-4b6d-8a9b-6e5c4d3b2f1a', 'm3cde4lb-2l3m-4507-0213-6c5d4e3f2g1h') ON CONFLICT DO NOTHING;
INSERT INTO login.organiser (organisations_id, utilisateurs_id) VALUES ('d4def52c-3c4d-4c7e-7b8a-5d4b3c2a1e0d', 'n4efg5mc-3m4n-4618-1122-5d4e3f2g1h0i') ON CONFLICT DO NOTHING;
INSERT INTO login.organiser (organisations_id, utilisateurs_id) VALUES ('e5ghi63d-4d5e-4d8f-6a7b-4c3a2b1d0c9b', 'o5fgh6nd-4n5o-4729-2031-4e3f2g1h0i9j') ON CONFLICT DO NOTHING;
INSERT INTO login.organiser (organisations_id, utilisateurs_id) VALUES ('a1eebc99-9c0b-4ef8-bb6d-6bb9bd380a13', 'e9285758-0c3a-46ae-947e-9af343ac5f62') ON CONFLICT DO NOTHING;


--
-- TOC entry 5086 (class 0 OID 45750)
-- Dependencies: 290
-- Data for Name: promotions; Type: TABLE DATA; Schema: login; Owner: postgres
--

INSERT INTO login.promotions (id, datedebut, datefin, codeunique, typeremise, valeurremise, datecreation, datemodification, distributeurs_id) VALUES ('1979bd79-f81b-498b-b247-e7b9bbb3f600', '2022-01-01', '2022-01-01', 'R5', 'garantie', 54.25, '2000-04-10', '2022-01-01', '1979bd79-f71b-498b-b247-e7b9bbb3f600') ON CONFLICT DO NOTHING;
INSERT INTO login.promotions (id, datedebut, datefin, codeunique, typeremise, valeurremise, datecreation, datemodification, distributeurs_id) VALUES ('1979bd79-f81b-498b-b247-e7b9bbb3f601', '2022-01-01', '2022-01-01', 'R54', 'virement', 74.25, '2004-08-10', '2022-01-01', '0618e585-f82a-4d5f-af1c-54f880d766d3') ON CONFLICT DO NOTHING;
INSERT INTO login.promotions (id, datedebut, datefin, codeunique, typeremise, valeurremise, datecreation, datemodification, distributeurs_id) VALUES ('1979bd79-f81b-498b-b247-e7b9bbb3f602', '2022-01-01', '2022-01-01', 'R45', 'devoir', 95.23, '2002-10-10', '2022-01-01', 'd301ff83-2a62-4e6d-aa23-57c7825bcd57') ON CONFLICT DO NOTHING;


--
-- TOC entry 5087 (class 0 OID 45815)
-- Dependencies: 291
-- Data for Name: utilisateurs; Type: TABLE DATA; Schema: login; Owner: postgres
--

INSERT INTO login.utilisateurs (id, login, mdp, etat, datecreation, datemodification, groupes_id, roles, nom, prenom) VALUES ('0190615e-1101-7209-9932-7020bbd55714', 'sdvfdbrfg', 'sdvdfbfg', 'false', NULL, NULL, 'f6jkl74e-5e6f-4e90-596a-3b2a1e0d9c8a', NULL, NULL, NULL) ON CONFLICT DO NOTHING;
INSERT INTO login.utilisateurs (id, login, mdp, etat, datecreation, datemodification, groupes_id, roles, nom, prenom) VALUES ('l2bcd3ka-1k2l-44f6-9304-7b6c5d4e3f2g', 'user1', 'password', 'true', '2024-01-01', '2024-01-01', 'h8pqr96g-7g8h-40b2-5748-1e0d9c8a7b6c', NULL, NULL, NULL) ON CONFLICT DO NOTHING;
INSERT INTO login.utilisateurs (id, login, mdp, etat, datecreation, datemodification, groupes_id, roles, nom, prenom) VALUES ('m3cde4lb-2l3m-4507-0213-6c5d4e3f2g1h', 'user2', 'password', 'true', '2024-01-01', '2024-01-01', 'i9stu07h-8h9i-41c3-6637-0d9c8a7b6c5d', NULL, NULL, NULL) ON CONFLICT DO NOTHING;
INSERT INTO login.utilisateurs (id, login, mdp, etat, datecreation, datemodification, groupes_id, roles, nom, prenom) VALUES ('n4efg5mc-3m4n-4618-1122-5d4e3f2g1h0i', 'user3', 'password', 'true', '2024-01-01', '2024-01-01', 'j0vwx18i-9i0j-42d4-7526-9c8a7b6c5d4e', NULL, NULL, NULL) ON CONFLICT DO NOTHING;
INSERT INTO login.utilisateurs (id, login, mdp, etat, datecreation, datemodification, groupes_id, roles, nom, prenom) VALUES ('o5fgh6nd-4n5o-4729-2031-4e3f2g1h0i9j', 'user4', 'password', 'true', '2024-01-01', '2024-01-01', 'f6jkl74e-5e6f-4e90-596a-3b2a1e0d9c8a', NULL, NULL, NULL) ON CONFLICT DO NOTHING;
INSERT INTO login.utilisateurs (id, login, mdp, etat, datecreation, datemodification, groupes_id, roles, nom, prenom) VALUES ('k1yzabc2j-0j1k-43e5-8415-8a7b6c5d4e3f', 'admin0', '$2a$10$EixZaYVK1fsbw1Zfbx3OpOeIx2lr1Zfbx3OpOeIx2lr1Zfbx3OpOe', 'true', '2024-01-01', '2024-01-01', 'g7mno85f-6f7g-4fa1-4859-2a1e0d9c8a7b', 'ADMIN', NULL, NULL) ON CONFLICT DO NOTHING;
INSERT INTO login.utilisateurs (id, login, mdp, etat, datecreation, datemodification, groupes_id, roles, nom, prenom) VALUES ('e9285758-0c3a-46ae-947e-9af343ac5f62', 'nouvelutilisateur', '$2a$10$VrDiZpX8FawtT2ghB1tqpOOQh/mx2XXe3r8Vi6Essb1KXxbFg6/Fq', 'true', NULL, NULL, 'a1b2c3d4-e5f6-7890-abcd-1234567890ef', 'ADMIN', 'ariel', 'sheney') ON CONFLICT DO NOTHING;


--
-- TOC entry 4885 (class 2606 OID 45831)
-- Name: actions actions_pkey; Type: CONSTRAINT; Schema: login; Owner: postgres
--

ALTER TABLE ONLY login.actions
    ADD CONSTRAINT actions_pkey PRIMARY KEY (id);


--
-- TOC entry 4889 (class 2606 OID 45833)
-- Name: elements elements_pkey; Type: CONSTRAINT; Schema: login; Owner: postgres
--

ALTER TABLE ONLY login.elements
    ADD CONSTRAINT elements_pkey PRIMARY KEY (id);


--
-- TOC entry 4895 (class 2606 OID 45835)
-- Name: elementsbases elementsbases_pkey; Type: CONSTRAINT; Schema: login; Owner: postgres
--

ALTER TABLE ONLY login.elementsbases
    ADD CONSTRAINT elementsbases_pkey PRIMARY KEY (id);


--
-- TOC entry 4899 (class 2606 OID 45837)
-- Name: groupes groupes_pkey; Type: CONSTRAINT; Schema: login; Owner: postgres
--

ALTER TABLE ONLY login.groupes
    ADD CONSTRAINT groupes_pkey PRIMARY KEY (id);


--
-- TOC entry 4901 (class 2606 OID 45839)
-- Name: langues langues_pkey; Type: CONSTRAINT; Schema: login; Owner: postgres
--

ALTER TABLE ONLY login.langues
    ADD CONSTRAINT langues_pkey PRIMARY KEY (id);


--
-- TOC entry 4903 (class 2606 OID 45841)
-- Name: menus menus_pkey; Type: CONSTRAINT; Schema: login; Owner: postgres
--

ALTER TABLE ONLY login.menus
    ADD CONSTRAINT menus_pkey PRIMARY KEY (id);


--
-- TOC entry 4905 (class 2606 OID 45843)
-- Name: organisations organisations_pkey; Type: CONSTRAINT; Schema: login; Owner: postgres
--

ALTER TABLE ONLY login.organisations
    ADD CONSTRAINT organisations_pkey PRIMARY KEY (id);


--
-- TOC entry 4887 (class 2606 OID 45845)
-- Name: actionslangues pk_actionslangues; Type: CONSTRAINT; Schema: login; Owner: postgres
--

ALTER TABLE ONLY login.actionslangues
    ADD CONSTRAINT pk_actionslangues PRIMARY KEY (langues_id, actions_id);


--
-- TOC entry 4891 (class 2606 OID 45855)
-- Name: elements pk_composite_elements; Type: CONSTRAINT; Schema: login; Owner: postgres
--

ALTER TABLE ONLY login.elements
    ADD CONSTRAINT pk_composite_elements UNIQUE (menus_id, elementsbases_id);


--
-- TOC entry 4893 (class 2606 OID 45873)
-- Name: elementsbaselanques pk_elementsbaseslangues; Type: CONSTRAINT; Schema: login; Owner: postgres
--

ALTER TABLE ONLY login.elementsbaselanques
    ADD CONSTRAINT pk_elementsbaseslangues PRIMARY KEY (langues_id, elementsbases_id);


--
-- TOC entry 4897 (class 2606 OID 45875)
-- Name: elementslangues pk_elementslangues; Type: CONSTRAINT; Schema: login; Owner: postgres
--

ALTER TABLE ONLY login.elementslangues
    ADD CONSTRAINT pk_elementslangues PRIMARY KEY (langues_id, elements_id);


--
-- TOC entry 4907 (class 2606 OID 45899)
-- Name: organiser pk_organiser; Type: CONSTRAINT; Schema: login; Owner: postgres
--

ALTER TABLE ONLY login.organiser
    ADD CONSTRAINT pk_organiser PRIMARY KEY (organisations_id, utilisateurs_id);


--
-- TOC entry 4909 (class 2606 OID 45913)
-- Name: promotions pk_promotions; Type: CONSTRAINT; Schema: login; Owner: postgres
--

ALTER TABLE ONLY login.promotions
    ADD CONSTRAINT pk_promotions PRIMARY KEY (id);


--
-- TOC entry 4911 (class 2606 OID 45945)
-- Name: promotions uc_promotions_codeunique; Type: CONSTRAINT; Schema: login; Owner: postgres
--

ALTER TABLE ONLY login.promotions
    ADD CONSTRAINT uc_promotions_codeunique UNIQUE (codeunique);


--
-- TOC entry 4913 (class 2606 OID 46331)
-- Name: utilisateurs uniq_login; Type: CONSTRAINT; Schema: login; Owner: postgres
--

ALTER TABLE ONLY login.utilisateurs
    ADD CONSTRAINT uniq_login UNIQUE (login);


--
-- TOC entry 4915 (class 2606 OID 45951)
-- Name: utilisateurs utilisateurs_pkey; Type: CONSTRAINT; Schema: login; Owner: postgres
--

ALTER TABLE ONLY login.utilisateurs
    ADD CONSTRAINT utilisateurs_pkey PRIMARY KEY (id);


--
-- TOC entry 4917 (class 2606 OID 45952)
-- Name: actionslangues fk_actions_actionslangues; Type: FK CONSTRAINT; Schema: login; Owner: postgres
--

ALTER TABLE ONLY login.actionslangues
    ADD CONSTRAINT fk_actions_actionslangues FOREIGN KEY (actions_id) REFERENCES login.actions(id);


--
-- TOC entry 4916 (class 2606 OID 45957)
-- Name: actions fk_actions_elementbases; Type: FK CONSTRAINT; Schema: login; Owner: postgres
--

ALTER TABLE ONLY login.actions
    ADD CONSTRAINT fk_actions_elementbases FOREIGN KEY (elementsbase_id) REFERENCES login.elementsbases(id);


--
-- TOC entry 4923 (class 2606 OID 46062)
-- Name: elementsbases fk_element; Type: FK CONSTRAINT; Schema: login; Owner: postgres
--

ALTER TABLE ONLY login.elementsbases
    ADD CONSTRAINT fk_element FOREIGN KEY (element_id) REFERENCES login.elements(id);


--
-- TOC entry 4924 (class 2606 OID 46067)
-- Name: elementslangues fk_elements_elementslangues; Type: FK CONSTRAINT; Schema: login; Owner: postgres
--

ALTER TABLE ONLY login.elementslangues
    ADD CONSTRAINT fk_elements_elementslangues FOREIGN KEY (elements_id) REFERENCES login.elements(id);


--
-- TOC entry 4919 (class 2606 OID 46072)
-- Name: elements fk_elementsbase_elements; Type: FK CONSTRAINT; Schema: login; Owner: postgres
--

ALTER TABLE ONLY login.elements
    ADD CONSTRAINT fk_elementsbase_elements FOREIGN KEY (elementsbases_id) REFERENCES login.elementsbases(id);


--
-- TOC entry 4921 (class 2606 OID 46077)
-- Name: elementsbaselanques fk_elementsbase_elementsbaselangues; Type: FK CONSTRAINT; Schema: login; Owner: postgres
--

ALTER TABLE ONLY login.elementsbaselanques
    ADD CONSTRAINT fk_elementsbase_elementsbaselangues FOREIGN KEY (elementsbases_id) REFERENCES login.elementsbases(id);


--
-- TOC entry 4927 (class 2606 OID 46112)
-- Name: menus fk_groupes_menus; Type: FK CONSTRAINT; Schema: login; Owner: postgres
--

ALTER TABLE ONLY login.menus
    ADD CONSTRAINT fk_groupes_menus FOREIGN KEY (groupes_id) REFERENCES login.groupes(id);


--
-- TOC entry 4931 (class 2606 OID 46117)
-- Name: utilisateurs fk_groupes_utilisateurs; Type: FK CONSTRAINT; Schema: login; Owner: postgres
--

ALTER TABLE ONLY login.utilisateurs
    ADD CONSTRAINT fk_groupes_utilisateurs FOREIGN KEY (groupes_id) REFERENCES login.groupes(id);


--
-- TOC entry 4918 (class 2606 OID 46132)
-- Name: actionslangues fk_langues_actionslangues; Type: FK CONSTRAINT; Schema: login; Owner: postgres
--

ALTER TABLE ONLY login.actionslangues
    ADD CONSTRAINT fk_langues_actionslangues FOREIGN KEY (langues_id) REFERENCES login.langues(id);


--
-- TOC entry 4922 (class 2606 OID 46137)
-- Name: elementsbaselanques fk_langues_elementsbaselangues; Type: FK CONSTRAINT; Schema: login; Owner: postgres
--

ALTER TABLE ONLY login.elementsbaselanques
    ADD CONSTRAINT fk_langues_elementsbaselangues FOREIGN KEY (langues_id) REFERENCES login.langues(id);


--
-- TOC entry 4925 (class 2606 OID 46142)
-- Name: elementslangues fk_langues_elementslangues; Type: FK CONSTRAINT; Schema: login; Owner: postgres
--

ALTER TABLE ONLY login.elementslangues
    ADD CONSTRAINT fk_langues_elementslangues FOREIGN KEY (langues_id) REFERENCES login.langues(id);


--
-- TOC entry 4920 (class 2606 OID 46147)
-- Name: elements fk_menus_elements; Type: FK CONSTRAINT; Schema: login; Owner: postgres
--

ALTER TABLE ONLY login.elements
    ADD CONSTRAINT fk_menus_elements FOREIGN KEY (menus_id) REFERENCES login.menus(id);


--
-- TOC entry 4926 (class 2606 OID 46152)
-- Name: groupes fk_menus_groupes; Type: FK CONSTRAINT; Schema: login; Owner: postgres
--

ALTER TABLE ONLY login.groupes
    ADD CONSTRAINT fk_menus_groupes FOREIGN KEY (menus_id) REFERENCES login.menus(id);


--
-- TOC entry 4929 (class 2606 OID 46197)
-- Name: organiser fk_organisation_organiser; Type: FK CONSTRAINT; Schema: login; Owner: postgres
--

ALTER TABLE ONLY login.organiser
    ADD CONSTRAINT fk_organisation_organiser FOREIGN KEY (organisations_id) REFERENCES login.organisations(id);


--
-- TOC entry 4928 (class 2606 OID 46297)
-- Name: menus fk_utilisateur_menus; Type: FK CONSTRAINT; Schema: login; Owner: postgres
--

ALTER TABLE ONLY login.menus
    ADD CONSTRAINT fk_utilisateur_menus FOREIGN KEY (utilisateurs_id) REFERENCES login.utilisateurs(id);


--
-- TOC entry 4930 (class 2606 OID 46302)
-- Name: organiser fk_utilisateurs_organiser; Type: FK CONSTRAINT; Schema: login; Owner: postgres
--

ALTER TABLE ONLY login.organiser
    ADD CONSTRAINT fk_utilisateurs_organiser FOREIGN KEY (utilisateurs_id) REFERENCES login.utilisateurs(id);


-- Completed on 2025-06-25 08:57:07

--
-- PostgreSQL database dump complete
--

