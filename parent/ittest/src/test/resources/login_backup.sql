--
-- PostgreSQL database dump
--

-- Dumped from database version 16.3
-- Dumped by pg_dump version 16.3

-- Started on 2025-04-25 02:50:53

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
-- TOC entry 5073 (class 1262 OID 28060)
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

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 282 (class 1259 OID 45547)
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
-- TOC entry 283 (class 1259 OID 45552)
-- Name: actionslangues; Type: TABLE; Schema: login; Owner: postgres
--

CREATE TABLE login.actionslangues (
    langues_id character varying(255) NOT NULL,
    actions_id character varying(255) NOT NULL,
    valeurlibelle character varying(255)
);


ALTER TABLE login.actionslangues OWNER TO postgres;

--
-- TOC entry 284 (class 1259 OID 45619)
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
-- TOC entry 285 (class 1259 OID 45624)
-- Name: elementsbaselanques; Type: TABLE; Schema: login; Owner: postgres
--

CREATE TABLE login.elementsbaselanques (
    langues_id character varying(255) NOT NULL,
    elementsbases_id character varying(255) NOT NULL,
    valeurlibelle character varying(255)
);


ALTER TABLE login.elementsbaselanques OWNER TO postgres;

--
-- TOC entry 286 (class 1259 OID 45629)
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
-- TOC entry 287 (class 1259 OID 45634)
-- Name: elementslangues; Type: TABLE; Schema: login; Owner: postgres
--

CREATE TABLE login.elementslangues (
    langues_id character varying(255) NOT NULL,
    elements_id character varying(255) NOT NULL,
    valeurlibelle character varying(255)
);


ALTER TABLE login.elementslangues OWNER TO postgres;

--
-- TOC entry 288 (class 1259 OID 45669)
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
-- TOC entry 289 (class 1259 OID 45679)
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
-- TOC entry 290 (class 1259 OID 45684)
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
-- TOC entry 291 (class 1259 OID 45709)
-- Name: organisations; Type: TABLE; Schema: login; Owner: postgres
--

CREATE TABLE login.organisations (
    id character varying(255) NOT NULL,
    raisonsociale character varying(255)
);


ALTER TABLE login.organisations OWNER TO postgres;

--
-- TOC entry 292 (class 1259 OID 45714)
-- Name: organiser; Type: TABLE; Schema: login; Owner: postgres
--

CREATE TABLE login.organiser (
    organisations_id character varying(255) NOT NULL,
    utilisateurs_id character varying(255) NOT NULL
);


ALTER TABLE login.organiser OWNER TO postgres;

--
-- TOC entry 293 (class 1259 OID 45750)
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
-- TOC entry 294 (class 1259 OID 45815)
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
    menus_id character varying(255)
);


ALTER TABLE login.utilisateurs OWNER TO postgres;

--
-- TOC entry 5055 (class 0 OID 45547)
-- Dependencies: 282
-- Data for Name: actions; Type: TABLE DATA; Schema: login; Owner: postgres
--

INSERT INTO login.actions VALUES ('j6bca7iy-5i6j-4c8f-3340-7a1b0c9d8e7f', 'Afficher', true, '2024-01-01', '2024-01-01', 'u1lmn2tj-0t1u-4d8f-6495-8k7l6m5n4o3p') ON CONFLICT DO NOTHING;
INSERT INTO login.actions VALUES ('k7cdb8jz-6j7k-4d90-2259-6b0c9d8e7f6g', 'Ajouter', true, '2024-01-01', '2024-01-01', 'v2mno3uk-1u2v-4e90-5304-7l6m5n4o3p2q') ON CONFLICT DO NOTHING;
INSERT INTO login.actions VALUES ('l8dec9ka-7k8l-4fa1-1168-5c9d8e7f6g5h', 'Modifier', true, '2024-01-01', '2024-01-01', 'w3nop4vl-2v3w-4fa1-4213-6m5n4o3p2q1r') ON CONFLICT DO NOTHING;
INSERT INTO login.actions VALUES ('m9efd0lb-8l9m-40b2-0077-4d8e7f6g5h4i', 'Supprimer', true, '2024-01-01', '2024-01-01', 'x4opq5wm-3w4x-40b2-3122-5n4o3p2q1r0s') ON CONFLICT DO NOTHING;
INSERT INTO login.actions VALUES ('n0fge1mc-9m0n-41c3-7986-3e7f6g5h4i3j', 'Exporter', true, '2024-01-01', '2024-01-01', 'y5pqr6xn-4x5y-41c3-2031-4o3p2q1r0s9t') ON CONFLICT DO NOTHING;


--
-- TOC entry 5056 (class 0 OID 45552)
-- Dependencies: 283
-- Data for Name: actionslangues; Type: TABLE DATA; Schema: login; Owner: postgres
--

INSERT INTO login.actionslangues VALUES ('e1vwx2dt-0d1e-4729-4495-8u7v6w5x4y3z', 'j6bca7iy-5i6j-4c8f-3340-7a1b0c9d8e7f', 'Afficher') ON CONFLICT DO NOTHING;
INSERT INTO login.actionslangues VALUES ('f2wxy3eu-1e2f-483a-3304-7v6w5x4y3z2a', 'j6bca7iy-5i6j-4c8f-3340-7a1b0c9d8e7f', 'Show') ON CONFLICT DO NOTHING;
INSERT INTO login.actionslangues VALUES ('e1vwx2dt-0d1e-4729-4495-8u7v6w5x4y3z', 'k7cdb8jz-6j7k-4d90-2259-6b0c9d8e7f6g', 'Ajouter') ON CONFLICT DO NOTHING;
INSERT INTO login.actionslangues VALUES ('f2wxy3eu-1e2f-483a-3304-7v6w5x4y3z2a', 'k7cdb8jz-6j7k-4d90-2259-6b0c9d8e7f6g', 'Add') ON CONFLICT DO NOTHING;
INSERT INTO login.actionslangues VALUES ('e1vwx2dt-0d1e-4729-4495-8u7v6w5x4y3z', 'l8dec9ka-7k8l-4fa1-1168-5c9d8e7f6g5h', 'Modifier') ON CONFLICT DO NOTHING;


--
-- TOC entry 5057 (class 0 OID 45619)
-- Dependencies: 284
-- Data for Name: elements; Type: TABLE DATA; Schema: login; Owner: postgres
--

INSERT INTO login.elements VALUES ('z6qrs7yo-5y6z-42d4-1940-3p2q1r0s9t8u', 'Tableau de bord principal', true, '2024-01-01', '2024-01-01', 'p6ghi7oe-5o6p-483a-3940-3f2g1h0i9j8k', 'u1lmn2tj-0t1u-4d8f-6495-8k7l6m5n4o3p') ON CONFLICT DO NOTHING;
INSERT INTO login.elements VALUES ('a7rst8zp-6z7a-43e5-0859-2q1r0s9t8u7v', 'Ajouter un utilisateur', true, '2024-01-01', '2024-01-01', 'q7hij8pf-6p7q-494b-2859-2g1h0i9j8k7l', 'v2mno3uk-1u2v-4e90-5304-7l6m5n4o3p2q') ON CONFLICT DO NOTHING;
INSERT INTO login.elements VALUES ('b8stu9aq-7a8b-44f6-7768-1r0s9t8u7v6w', 'Supprimer un groupe', true, '2024-01-01', '2024-01-01', 'r8ijk9qg-7q8r-4a5c-1768-1h0i9j8k7l6m', 'w3nop4vl-2v3w-4fa1-4213-6m5n4o3p2q1r') ON CONFLICT DO NOTHING;
INSERT INTO login.elements VALUES ('c9tuv0br-8b9c-4507-6677-0s9t8u7v6w5x', 'Modifier un menu', true, '2024-01-01', '2024-01-01', 's9jkl0rh-8r9s-4b6d-0677-0i9j8k7l6m5n', 'x4opq5wm-3w4x-40b2-3122-5n4o3p2q1r0s') ON CONFLICT DO NOTHING;
INSERT INTO login.elements VALUES ('d0uvw1cs-9c0d-4618-5586-9t8u7v6w5x4y', 'Afficher les éléments', true, '2024-01-01', '2024-01-01', 't0klm1si-9s0t-4c7e-7586-9j8k7l6m5n4o', 'y5pqr6xn-4x5y-41c3-2031-4o3p2q1r0s9t') ON CONFLICT DO NOTHING;


--
-- TOC entry 5058 (class 0 OID 45624)
-- Dependencies: 285
-- Data for Name: elementsbaselanques; Type: TABLE DATA; Schema: login; Owner: postgres
--

INSERT INTO login.elementsbaselanques VALUES ('e1vwx2dt-0d1e-4729-4495-8u7v6w5x4y3z', 'u1lmn2tj-0t1u-4d8f-6495-8k7l6m5n4o3p', 'Tableau de bord') ON CONFLICT DO NOTHING;
INSERT INTO login.elementsbaselanques VALUES ('f2wxy3eu-1e2f-483a-3304-7v6w5x4y3z2a', 'u1lmn2tj-0t1u-4d8f-6495-8k7l6m5n4o3p', 'Dashboard') ON CONFLICT DO NOTHING;
INSERT INTO login.elementsbaselanques VALUES ('e1vwx2dt-0d1e-4729-4495-8u7v6w5x4y3z', 'v2mno3uk-1u2v-4e90-5304-7l6m5n4o3p2q', 'Gestion des utilisateurs') ON CONFLICT DO NOTHING;
INSERT INTO login.elementsbaselanques VALUES ('f2wxy3eu-1e2f-483a-3304-7v6w5x4y3z2a', 'v2mno3uk-1u2v-4e90-5304-7l6m5n4o3p2q', 'User Management') ON CONFLICT DO NOTHING;
INSERT INTO login.elementsbaselanques VALUES ('e1vwx2dt-0d1e-4729-4495-8u7v6w5x4y3z', 'w3nop4vl-2v3w-4fa1-4213-6m5n4o3p2q1r', 'Gestion des groupes') ON CONFLICT DO NOTHING;


--
-- TOC entry 5059 (class 0 OID 45629)
-- Dependencies: 286
-- Data for Name: elementsbases; Type: TABLE DATA; Schema: login; Owner: postgres
--

INSERT INTO login.elementsbases VALUES ('u1lmn2tj-0t1u-4d8f-6495-8k7l6m5n4o3p', 'Tableau de bord', true, '2024-01-01', '2024-01-01', 'dashboard', 'a7rst8zp-6z7a-43e5-0859-2q1r0s9t8u7v') ON CONFLICT DO NOTHING;
INSERT INTO login.elementsbases VALUES ('v2mno3uk-1u2v-4e90-5304-7l6m5n4o3p2q', 'Gestion des utilisateurs', true, '2024-01-01', '2024-01-01', 'userManagement', 'b8stu9aq-7a8b-44f6-7768-1r0s9t8u7v6w') ON CONFLICT DO NOTHING;
INSERT INTO login.elementsbases VALUES ('w3nop4vl-2v3w-4fa1-4213-6m5n4o3p2q1r', 'Gestion des groupes', true, '2024-01-01', '2024-01-01', 'groupManagement', 'c9tuv0br-8b9c-4507-6677-0s9t8u7v6w5x') ON CONFLICT DO NOTHING;
INSERT INTO login.elementsbases VALUES ('x4opq5wm-3w4x-40b2-3122-5n4o3p2q1r0s', 'Gestion des menus', true, '2024-01-01', '2024-01-01', 'menuManagement', 'd0uvw1cs-9c0d-4618-5586-9t8u7v6w5x4y') ON CONFLICT DO NOTHING;
INSERT INTO login.elementsbases VALUES ('y5pqr6xn-4x5y-41c3-2031-4o3p2q1r0s9t', 'Gestion des éléments', true, '2024-01-01', '2024-01-01', 'elementManagement', 'z6qrs7yo-5y6z-42d4-1940-3p2q1r0s9t8u') ON CONFLICT DO NOTHING;


--
-- TOC entry 5060 (class 0 OID 45634)
-- Dependencies: 287
-- Data for Name: elementslangues; Type: TABLE DATA; Schema: login; Owner: postgres
--

INSERT INTO login.elementslangues VALUES ('e1vwx2dt-0d1e-4729-4495-8u7v6w5x4y3z', 'z6qrs7yo-5y6z-42d4-1940-3p2q1r0s9t8u', 'Tableau de bord principal') ON CONFLICT DO NOTHING;
INSERT INTO login.elementslangues VALUES ('f2wxy3eu-1e2f-483a-3304-7v6w5x4y3z2a', 'z6qrs7yo-5y6z-42d4-1940-3p2q1r0s9t8u', 'Main Dashboard') ON CONFLICT DO NOTHING;
INSERT INTO login.elementslangues VALUES ('e1vwx2dt-0d1e-4729-4495-8u7v6w5x4y3z', 'a7rst8zp-6z7a-43e5-0859-2q1r0s9t8u7v', 'Ajouter un utilisateur') ON CONFLICT DO NOTHING;
INSERT INTO login.elementslangues VALUES ('f2wxy3eu-1e2f-483a-3304-7v6w5x4y3z2a', 'a7rst8zp-6z7a-43e5-0859-2q1r0s9t8u7v', 'Add User') ON CONFLICT DO NOTHING;
INSERT INTO login.elementslangues VALUES ('e1vwx2dt-0d1e-4729-4495-8u7v6w5x4y3z', 'b8stu9aq-7a8b-44f6-7768-1r0s9t8u7v6w', 'Supprimer un groupe') ON CONFLICT DO NOTHING;


--
-- TOC entry 5061 (class 0 OID 45669)
-- Dependencies: 288
-- Data for Name: groupes; Type: TABLE DATA; Schema: login; Owner: postgres
--

INSERT INTO login.groupes VALUES ('f6jkl74e-5e6f-4e90-596a-3b2a1e0d9c8a', 'Administrateurs', true, '2024-01-01', '2024-01-01', 'p6ghi7oe-5o6p-483a-3940-3f2g1h0i9j8k') ON CONFLICT DO NOTHING;
INSERT INTO login.groupes VALUES ('g7mno85f-6f7g-4fa1-4859-2a1e0d9c8a7b', 'Utilisateurs', true, '2024-01-01', '2024-01-01', 'q7hij8pf-6p7q-494b-2859-2g1h0i9j8k7l') ON CONFLICT DO NOTHING;
INSERT INTO login.groupes VALUES ('h8pqr96g-7g8h-40b2-5748-1e0d9c8a7b6c', 'Invités', true, '2024-01-01', '2024-01-01', 'r8ijk9qg-7q8r-4a5c-1768-1h0i9j8k7l6m') ON CONFLICT DO NOTHING;
INSERT INTO login.groupes VALUES ('i9stu07h-8h9i-41c3-6637-0d9c8a7b6c5d', 'Managers', true, '2024-01-01', '2024-01-01', 's9jkl0rh-8r9s-4b6d-0677-0i9j8k7l6m5n') ON CONFLICT DO NOTHING;
INSERT INTO login.groupes VALUES ('j0vwx18i-9i0j-42d4-7526-9c8a7b6c5d4e', 'Techniciens', true, '2024-01-01', '2024-01-01', 't0klm1si-9s0t-4c7e-7586-9j8k7l6m5n4o') ON CONFLICT DO NOTHING;
INSERT INTO login.groupes VALUES ('f6jkl74e-5e6f-4e90-596a-3b2a1e0a7c8a', 'Admin', true, '2024-01-01', '2024-01-01', 'p6ghi7oe-5o6p-483a-3940-3f2g1h779j8k') ON CONFLICT DO NOTHING;


--
-- TOC entry 5062 (class 0 OID 45679)
-- Dependencies: 289
-- Data for Name: langues; Type: TABLE DATA; Schema: login; Owner: postgres
--

INSERT INTO login.langues VALUES ('e1vwx2dt-0d1e-4729-4495-8u7v6w5x4y3z', 'Français', true, '2024-01-01', '2024-01-01') ON CONFLICT DO NOTHING;
INSERT INTO login.langues VALUES ('f2wxy3eu-1e2f-483a-3304-7v6w5x4y3z2a', 'Anglais', true, '2024-01-01', '2024-01-01') ON CONFLICT DO NOTHING;
INSERT INTO login.langues VALUES ('g3xyz4fv-2f3g-494b-2213-6w5x4y3z2a1b', 'Espagnol', true, '2024-01-01', '2024-01-01') ON CONFLICT DO NOTHING;
INSERT INTO login.langues VALUES ('h4yza5gw-3g4h-4a5c-1122-5x4y3z2a1b0c', 'Allemand', true, '2024-01-01', '2024-01-01') ON CONFLICT DO NOTHING;
INSERT INTO login.langues VALUES ('i5zab6hx-4h5i-4b6d-0031-4y3z2a1b0c9d', 'Italien', true, '2024-01-01', '2024-01-01') ON CONFLICT DO NOTHING;


--
-- TOC entry 5063 (class 0 OID 45684)
-- Dependencies: 290
-- Data for Name: menus; Type: TABLE DATA; Schema: login; Owner: postgres
--

INSERT INTO login.menus VALUES ('p6ghi7oe-5o6p-483a-3940-3f2g1h0i9j8k', true, '2024-01-01', 'k1yzabc2j-0j1k-43e5-8415-8a7b6c5d4e3f', 'f6jkl74e-5e6f-4e90-596a-3b2a1e0d9c8a') ON CONFLICT DO NOTHING;
INSERT INTO login.menus VALUES ('q7hij8pf-6p7q-494b-2859-2g1h0i9j8k7l', true, '2024-01-01', 'l2bcd3ka-1k2l-44f6-9304-7b6c5d4e3f2g', 'g7mno85f-6f7g-4fa1-4859-2a1e0d9c8a7b') ON CONFLICT DO NOTHING;
INSERT INTO login.menus VALUES ('r8ijk9qg-7q8r-4a5c-1768-1h0i9j8k7l6m', true, '2024-01-01', 'm3cde4lb-2l3m-4507-0213-6c5d4e3f2g1h', 'h8pqr96g-7g8h-40b2-5748-1e0d9c8a7b6c') ON CONFLICT DO NOTHING;
INSERT INTO login.menus VALUES ('s9jkl0rh-8r9s-4b6d-0677-0i9j8k7l6m5n', true, '2024-01-01', 'n4efg5mc-3m4n-4618-1122-5d4e3f2g1h0i', 'i9stu07h-8h9i-41c3-6637-0d9c8a7b6c5d') ON CONFLICT DO NOTHING;
INSERT INTO login.menus VALUES ('t0klm1si-9s0t-4c7e-7586-9j8k7l6m5n4o', true, '2024-01-01', 'o5fgh6nd-4n5o-4729-2031-4e3f2g1h0i9j', 'j0vwx18i-9i0j-42d4-7526-9c8a7b6c5d4e') ON CONFLICT DO NOTHING;
INSERT INTO login.menus VALUES ('p6ghi7oe-5o6p-483a-3940-3f2g1h779j8k', true, '2024-01-01', '0190615e-1101-7209-9932-7020bbd55714', 'f6jkl74e-5e6f-4e90-596a-3b2a1e0a7c8a') ON CONFLICT DO NOTHING;


--
-- TOC entry 5064 (class 0 OID 45709)
-- Dependencies: 291
-- Data for Name: organisations; Type: TABLE DATA; Schema: login; Owner: postgres
--

INSERT INTO login.organisations VALUES ('a1eebc99-9c0b-4ef8-bb6d-6bb9bd380a13', 'Entreprise A') ON CONFLICT DO NOTHING;
INSERT INTO login.organisations VALUES ('b2fcd30a-1a2b-4a5c-9e8f-7d6a5b4c3d2e', 'Entreprise B') ON CONFLICT DO NOTHING;
INSERT INTO login.organisations VALUES ('c3abc41b-2b3c-4b6d-8a9b-6e5c4d3b2f1a', 'Association C') ON CONFLICT DO NOTHING;
INSERT INTO login.organisations VALUES ('d4def52c-3c4d-4c7e-7b8a-5d4b3c2a1e0d', 'Fondation D') ON CONFLICT DO NOTHING;
INSERT INTO login.organisations VALUES ('e5ghi63d-4d5e-4d8f-6a7b-4c3a2b1d0c9b', 'Collectivité E') ON CONFLICT DO NOTHING;


--
-- TOC entry 5065 (class 0 OID 45714)
-- Dependencies: 292
-- Data for Name: organiser; Type: TABLE DATA; Schema: login; Owner: postgres
--

INSERT INTO login.organiser VALUES ('a1eebc99-9c0b-4ef8-bb6d-6bb9bd380a13', 'k1yzabc2j-0j1k-43e5-8415-8a7b6c5d4e3f') ON CONFLICT DO NOTHING;
INSERT INTO login.organiser VALUES ('b2fcd30a-1a2b-4a5c-9e8f-7d6a5b4c3d2e', 'l2bcd3ka-1k2l-44f6-9304-7b6c5d4e3f2g') ON CONFLICT DO NOTHING;
INSERT INTO login.organiser VALUES ('c3abc41b-2b3c-4b6d-8a9b-6e5c4d3b2f1a', 'm3cde4lb-2l3m-4507-0213-6c5d4e3f2g1h') ON CONFLICT DO NOTHING;
INSERT INTO login.organiser VALUES ('d4def52c-3c4d-4c7e-7b8a-5d4b3c2a1e0d', 'n4efg5mc-3m4n-4618-1122-5d4e3f2g1h0i') ON CONFLICT DO NOTHING;
INSERT INTO login.organiser VALUES ('e5ghi63d-4d5e-4d8f-6a7b-4c3a2b1d0c9b', 'o5fgh6nd-4n5o-4729-2031-4e3f2g1h0i9j') ON CONFLICT DO NOTHING;


--
-- TOC entry 5066 (class 0 OID 45750)
-- Dependencies: 293
-- Data for Name: promotions; Type: TABLE DATA; Schema: login; Owner: postgres
--

INSERT INTO login.promotions VALUES ('1979bd79-f81b-498b-b247-e7b9bbb3f600', '2022-01-01', '2022-01-01', 'R5', 'garantie', 54.25, '2000-04-10', '2022-01-01', '1979bd79-f71b-498b-b247-e7b9bbb3f600') ON CONFLICT DO NOTHING;
INSERT INTO login.promotions VALUES ('1979bd79-f81b-498b-b247-e7b9bbb3f601', '2022-01-01', '2022-01-01', 'R54', 'virement', 74.25, '2004-08-10', '2022-01-01', '0618e585-f82a-4d5f-af1c-54f880d766d3') ON CONFLICT DO NOTHING;
INSERT INTO login.promotions VALUES ('1979bd79-f81b-498b-b247-e7b9bbb3f602', '2022-01-01', '2022-01-01', 'R45', 'devoir', 95.23, '2002-10-10', '2022-01-01', 'd301ff83-2a62-4e6d-aa23-57c7825bcd57') ON CONFLICT DO NOTHING;


--
-- TOC entry 5067 (class 0 OID 45815)
-- Dependencies: 294
-- Data for Name: utilisateurs; Type: TABLE DATA; Schema: login; Owner: postgres
--

INSERT INTO login.utilisateurs VALUES ('0190615e-1101-7209-9932-7020bbd55714', 'sdvfdbrfg', 'sdvdfbfg', 'false', NULL, NULL, 'f6jkl74e-5e6f-4e90-596a-3b2a1e0d9c8a', 'p6ghi7oe-5o6p-483a-3940-3f2g1h0i9j8k') ON CONFLICT DO NOTHING;
INSERT INTO login.utilisateurs VALUES ('k1yzabc2j-0j1k-43e5-8415-8a7b6c5d4e3f', 'admin', 'password', 'true', '2024-01-01', '2024-01-01', 'g7mno85f-6f7g-4fa1-4859-2a1e0d9c8a7b', 'q7hij8pf-6p7q-494b-2859-2g1h0i9j8k7l') ON CONFLICT DO NOTHING;
INSERT INTO login.utilisateurs VALUES ('l2bcd3ka-1k2l-44f6-9304-7b6c5d4e3f2g', 'user1', 'password', 'true', '2024-01-01', '2024-01-01', 'h8pqr96g-7g8h-40b2-5748-1e0d9c8a7b6c', 'r8ijk9qg-7q8r-4a5c-1768-1h0i9j8k7l6m') ON CONFLICT DO NOTHING;
INSERT INTO login.utilisateurs VALUES ('m3cde4lb-2l3m-4507-0213-6c5d4e3f2g1h', 'user2', 'password', 'true', '2024-01-01', '2024-01-01', 'i9stu07h-8h9i-41c3-6637-0d9c8a7b6c5d', 's9jkl0rh-8r9s-4b6d-0677-0i9j8k7l6m5n') ON CONFLICT DO NOTHING;
INSERT INTO login.utilisateurs VALUES ('n4efg5mc-3m4n-4618-1122-5d4e3f2g1h0i', 'user3', 'password', 'true', '2024-01-01', '2024-01-01', 'j0vwx18i-9i0j-42d4-7526-9c8a7b6c5d4e', 't0klm1si-9s0t-4c7e-7586-9j8k7l6m5n4o') ON CONFLICT DO NOTHING;
INSERT INTO login.utilisateurs VALUES ('o5fgh6nd-4n5o-4729-2031-4e3f2g1h0i9j', 'user4', 'password', 'true', '2024-01-01', '2024-01-01', 'f6jkl74e-5e6f-4e90-596a-3b2a1e0d9c8a', 'p6ghi7oe-5o6p-483a-3940-3f2g1h779j8k') ON CONFLICT DO NOTHING;


--
-- TOC entry 4866 (class 2606 OID 45831)
-- Name: actions actions_pkey; Type: CONSTRAINT; Schema: login; Owner: postgres
--

ALTER TABLE ONLY login.actions
    ADD CONSTRAINT actions_pkey PRIMARY KEY (id);


--
-- TOC entry 4870 (class 2606 OID 45833)
-- Name: elements elements_pkey; Type: CONSTRAINT; Schema: login; Owner: postgres
--

ALTER TABLE ONLY login.elements
    ADD CONSTRAINT elements_pkey PRIMARY KEY (id);


--
-- TOC entry 4876 (class 2606 OID 45835)
-- Name: elementsbases elementsbases_pkey; Type: CONSTRAINT; Schema: login; Owner: postgres
--

ALTER TABLE ONLY login.elementsbases
    ADD CONSTRAINT elementsbases_pkey PRIMARY KEY (id);


--
-- TOC entry 4880 (class 2606 OID 45837)
-- Name: groupes groupes_pkey; Type: CONSTRAINT; Schema: login; Owner: postgres
--

ALTER TABLE ONLY login.groupes
    ADD CONSTRAINT groupes_pkey PRIMARY KEY (id);


--
-- TOC entry 4882 (class 2606 OID 45839)
-- Name: langues langues_pkey; Type: CONSTRAINT; Schema: login; Owner: postgres
--

ALTER TABLE ONLY login.langues
    ADD CONSTRAINT langues_pkey PRIMARY KEY (id);


--
-- TOC entry 4884 (class 2606 OID 45841)
-- Name: menus menus_pkey; Type: CONSTRAINT; Schema: login; Owner: postgres
--

ALTER TABLE ONLY login.menus
    ADD CONSTRAINT menus_pkey PRIMARY KEY (id);


--
-- TOC entry 4886 (class 2606 OID 45843)
-- Name: organisations organisations_pkey; Type: CONSTRAINT; Schema: login; Owner: postgres
--

ALTER TABLE ONLY login.organisations
    ADD CONSTRAINT organisations_pkey PRIMARY KEY (id);


--
-- TOC entry 4868 (class 2606 OID 45845)
-- Name: actionslangues pk_actionslangues; Type: CONSTRAINT; Schema: login; Owner: postgres
--

ALTER TABLE ONLY login.actionslangues
    ADD CONSTRAINT pk_actionslangues PRIMARY KEY (langues_id, actions_id);


--
-- TOC entry 4872 (class 2606 OID 45855)
-- Name: elements pk_composite_elements; Type: CONSTRAINT; Schema: login; Owner: postgres
--

ALTER TABLE ONLY login.elements
    ADD CONSTRAINT pk_composite_elements UNIQUE (menus_id, elementsbases_id);


--
-- TOC entry 4874 (class 2606 OID 45873)
-- Name: elementsbaselanques pk_elementsbaseslangues; Type: CONSTRAINT; Schema: login; Owner: postgres
--

ALTER TABLE ONLY login.elementsbaselanques
    ADD CONSTRAINT pk_elementsbaseslangues PRIMARY KEY (langues_id, elementsbases_id);


--
-- TOC entry 4878 (class 2606 OID 45875)
-- Name: elementslangues pk_elementslangues; Type: CONSTRAINT; Schema: login; Owner: postgres
--

ALTER TABLE ONLY login.elementslangues
    ADD CONSTRAINT pk_elementslangues PRIMARY KEY (langues_id, elements_id);


--
-- TOC entry 4888 (class 2606 OID 45899)
-- Name: organiser pk_organiser; Type: CONSTRAINT; Schema: login; Owner: postgres
--

ALTER TABLE ONLY login.organiser
    ADD CONSTRAINT pk_organiser PRIMARY KEY (organisations_id, utilisateurs_id);


--
-- TOC entry 4890 (class 2606 OID 45913)
-- Name: promotions pk_promotions; Type: CONSTRAINT; Schema: login; Owner: postgres
--

ALTER TABLE ONLY login.promotions
    ADD CONSTRAINT pk_promotions PRIMARY KEY (id);


--
-- TOC entry 4892 (class 2606 OID 45945)
-- Name: promotions uc_promotions_codeunique; Type: CONSTRAINT; Schema: login; Owner: postgres
--

ALTER TABLE ONLY login.promotions
    ADD CONSTRAINT uc_promotions_codeunique UNIQUE (codeunique);


--
-- TOC entry 4894 (class 2606 OID 45951)
-- Name: utilisateurs utilisateurs_pkey; Type: CONSTRAINT; Schema: login; Owner: postgres
--

ALTER TABLE ONLY login.utilisateurs
    ADD CONSTRAINT utilisateurs_pkey PRIMARY KEY (id);


--
-- TOC entry 4896 (class 2606 OID 45952)
-- Name: actionslangues fk_actions_actionslangues; Type: FK CONSTRAINT; Schema: login; Owner: postgres
--

ALTER TABLE ONLY login.actionslangues
    ADD CONSTRAINT fk_actions_actionslangues FOREIGN KEY (actions_id) REFERENCES login.actions(id);


--
-- TOC entry 4895 (class 2606 OID 45957)
-- Name: actions fk_actions_elementbases; Type: FK CONSTRAINT; Schema: login; Owner: postgres
--

ALTER TABLE ONLY login.actions
    ADD CONSTRAINT fk_actions_elementbases FOREIGN KEY (elementsbase_id) REFERENCES login.elementsbases(id);


--
-- TOC entry 4902 (class 2606 OID 46062)
-- Name: elementsbases fk_element; Type: FK CONSTRAINT; Schema: login; Owner: postgres
--

ALTER TABLE ONLY login.elementsbases
    ADD CONSTRAINT fk_element FOREIGN KEY (element_id) REFERENCES login.elements(id);


--
-- TOC entry 4903 (class 2606 OID 46067)
-- Name: elementslangues fk_elements_elementslangues; Type: FK CONSTRAINT; Schema: login; Owner: postgres
--

ALTER TABLE ONLY login.elementslangues
    ADD CONSTRAINT fk_elements_elementslangues FOREIGN KEY (elements_id) REFERENCES login.elements(id);


--
-- TOC entry 4898 (class 2606 OID 46072)
-- Name: elements fk_elementsbase_elements; Type: FK CONSTRAINT; Schema: login; Owner: postgres
--

ALTER TABLE ONLY login.elements
    ADD CONSTRAINT fk_elementsbase_elements FOREIGN KEY (elementsbases_id) REFERENCES login.elementsbases(id);


--
-- TOC entry 4900 (class 2606 OID 46077)
-- Name: elementsbaselanques fk_elementsbase_elementsbaselangues; Type: FK CONSTRAINT; Schema: login; Owner: postgres
--

ALTER TABLE ONLY login.elementsbaselanques
    ADD CONSTRAINT fk_elementsbase_elementsbaselangues FOREIGN KEY (elementsbases_id) REFERENCES login.elementsbases(id);


--
-- TOC entry 4906 (class 2606 OID 46112)
-- Name: menus fk_groupes_menus; Type: FK CONSTRAINT; Schema: login; Owner: postgres
--

ALTER TABLE ONLY login.menus
    ADD CONSTRAINT fk_groupes_menus FOREIGN KEY (groupes_id) REFERENCES login.groupes(id);


--
-- TOC entry 4910 (class 2606 OID 46117)
-- Name: utilisateurs fk_groupes_utilisateurs; Type: FK CONSTRAINT; Schema: login; Owner: postgres
--

ALTER TABLE ONLY login.utilisateurs
    ADD CONSTRAINT fk_groupes_utilisateurs FOREIGN KEY (groupes_id) REFERENCES login.groupes(id);


--
-- TOC entry 4897 (class 2606 OID 46132)
-- Name: actionslangues fk_langues_actionslangues; Type: FK CONSTRAINT; Schema: login; Owner: postgres
--

ALTER TABLE ONLY login.actionslangues
    ADD CONSTRAINT fk_langues_actionslangues FOREIGN KEY (langues_id) REFERENCES login.langues(id);


--
-- TOC entry 4901 (class 2606 OID 46137)
-- Name: elementsbaselanques fk_langues_elementsbaselangues; Type: FK CONSTRAINT; Schema: login; Owner: postgres
--

ALTER TABLE ONLY login.elementsbaselanques
    ADD CONSTRAINT fk_langues_elementsbaselangues FOREIGN KEY (langues_id) REFERENCES login.langues(id);


--
-- TOC entry 4904 (class 2606 OID 46142)
-- Name: elementslangues fk_langues_elementslangues; Type: FK CONSTRAINT; Schema: login; Owner: postgres
--

ALTER TABLE ONLY login.elementslangues
    ADD CONSTRAINT fk_langues_elementslangues FOREIGN KEY (langues_id) REFERENCES login.langues(id);


--
-- TOC entry 4899 (class 2606 OID 46147)
-- Name: elements fk_menus_elements; Type: FK CONSTRAINT; Schema: login; Owner: postgres
--

ALTER TABLE ONLY login.elements
    ADD CONSTRAINT fk_menus_elements FOREIGN KEY (menus_id) REFERENCES login.menus(id);


--
-- TOC entry 4905 (class 2606 OID 46152)
-- Name: groupes fk_menus_groupes; Type: FK CONSTRAINT; Schema: login; Owner: postgres
--

ALTER TABLE ONLY login.groupes
    ADD CONSTRAINT fk_menus_groupes FOREIGN KEY (menus_id) REFERENCES login.menus(id);


--
-- TOC entry 4911 (class 2606 OID 46157)
-- Name: utilisateurs fk_menus_utilisateurs; Type: FK CONSTRAINT; Schema: login; Owner: postgres
--

ALTER TABLE ONLY login.utilisateurs
    ADD CONSTRAINT fk_menus_utilisateurs FOREIGN KEY (menus_id) REFERENCES login.menus(id);


--
-- TOC entry 4908 (class 2606 OID 46197)
-- Name: organiser fk_organisation_organiser; Type: FK CONSTRAINT; Schema: login; Owner: postgres
--

ALTER TABLE ONLY login.organiser
    ADD CONSTRAINT fk_organisation_organiser FOREIGN KEY (organisations_id) REFERENCES login.organisations(id);


--
-- TOC entry 4907 (class 2606 OID 46297)
-- Name: menus fk_utilisateur_menus; Type: FK CONSTRAINT; Schema: login; Owner: postgres
--

ALTER TABLE ONLY login.menus
    ADD CONSTRAINT fk_utilisateur_menus FOREIGN KEY (utilisateurs_id) REFERENCES login.utilisateurs(id);


--
-- TOC entry 4909 (class 2606 OID 46302)
-- Name: organiser fk_utilisateurs_organiser; Type: FK CONSTRAINT; Schema: login; Owner: postgres
--

ALTER TABLE ONLY login.organiser
    ADD CONSTRAINT fk_utilisateurs_organiser FOREIGN KEY (utilisateurs_id) REFERENCES login.utilisateurs(id);


-- Completed on 2025-04-25 02:50:54

--
-- PostgreSQL database dump complete
--

