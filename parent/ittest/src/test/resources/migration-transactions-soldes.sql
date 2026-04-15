-- SQL Migration: Création des tables pour les transactions de soldes
-- Database: PostgreSQL
-- Schema: document
-- Date: 2024-03-17

-- ============================================================
-- TABLE: mouvements_solde_ressources
-- Description: Historique des modifications de quantité pour les ressources
-- ============================================================
CREATE TABLE IF NOT EXISTS document.mouvements_solde_ressources (
    id UUID NOT NULL PRIMARY KEY DEFAULT gen_random_uuid(),
    ressources_id UUID NOT NULL,
    quantite_initiale INTEGER NOT NULL,
    quantite_mouvementee INTEGER NOT NULL,
    quantite_finale INTEGER NOT NULL,
    type_transaction VARCHAR(50) NOT NULL, -- ENTREE, SORTIE, AJUSTEMENT
    description VARCHAR(500),
    motif VARCHAR(255),
    code_reference VARCHAR(255),
    created_by VARCHAR(255) NOT NULL,
    datecreation TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    validee BOOLEAN DEFAULT FALSE,
    validee_by VARCHAR(255),
    date_validation TIMESTAMP,
    statut VARCHAR(50) NOT NULL DEFAULT 'EN_ATTENTE', -- EN_ATTENTE, VALIDEE, REJETEE

    CONSTRAINT fk_mouvements_ressources
        FOREIGN KEY (ressources_id)
        REFERENCES document.ressources(id) ON DELETE CASCADE,

    CONSTRAINT ck_type_transaction_ressources
        CHECK (type_transaction IN ('ENTREE', 'SORTIE', 'AJUSTEMENT')),

    CONSTRAINT ck_statut_ressources
        CHECK (statut IN ('EN_ATTENTE', 'VALIDEE', 'REJETEE'))
);

-- Créer des indices pour les recherches fréquentes
CREATE INDEX idx_mouvements_ressources_ressources_id
    ON document.mouvements_solde_ressources(ressources_id);

CREATE INDEX idx_mouvements_ressources_statut
    ON document.mouvements_solde_ressources(statut);

CREATE INDEX idx_mouvements_ressources_type_transaction
    ON document.mouvements_solde_ressources(type_transaction);

CREATE INDEX idx_mouvements_ressources_datecreation
    ON document.mouvements_solde_ressources(datecreation DESC);

-- ============================================================
-- TABLE: mouvements_solde_caisses
-- Description: Historique des modifications de solde pour les caisses
-- ============================================================
CREATE TABLE IF NOT EXISTS document.mouvements_solde_caisses (
    id UUID NOT NULL PRIMARY KEY DEFAULT gen_random_uuid(),
    caisses_id UUID NOT NULL,
    solde_initiale DOUBLE PRECISION NOT NULL,
    solde_mouvementee DOUBLE PRECISION NOT NULL,
    solde_finale DOUBLE PRECISION NOT NULL,
    type_transaction VARCHAR(50) NOT NULL, -- DEBIT, CREDIT, AJUSTEMENT
    description VARCHAR(500),
    motif VARCHAR(255),
    code_reference VARCHAR(255),
    created_by VARCHAR(255) NOT NULL,
    datecreation TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    validee BOOLEAN DEFAULT FALSE,
    validee_by VARCHAR(255),
    date_validation TIMESTAMP,
    statut VARCHAR(50) NOT NULL DEFAULT 'EN_ATTENTE', -- EN_ATTENTE, VALIDEE, REJETEE

    CONSTRAINT fk_mouvements_caisses
        FOREIGN KEY (caisses_id)
        REFERENCES document.caisses(id) ON DELETE CASCADE,

    CONSTRAINT ck_type_transaction_caisses
        CHECK (type_transaction IN ('DEBIT', 'CREDIT', 'AJUSTEMENT')),

    CONSTRAINT ck_statut_caisses
        CHECK (statut IN ('EN_ATTENTE', 'VALIDEE', 'REJETEE'))
);

-- Créer des indices pour les recherches fréquentes
CREATE INDEX idx_mouvements_caisses_caisses_id
    ON document.mouvements_solde_caisses(caisses_id);

CREATE INDEX idx_mouvements_caisses_statut
    ON document.mouvements_solde_caisses(statut);

CREATE INDEX idx_mouvements_caisses_type_transaction
    ON document.mouvements_solde_caisses(type_transaction);

CREATE INDEX idx_mouvements_caisses_datecreation
    ON document.mouvements_solde_caisses(datecreation DESC);

-- ============================================================
-- VUES: Statistiques et Rapports
-- ============================================================

-- Vue: Mouvements ressources par statut
CREATE OR REPLACE VIEW document.vw_mouvements_ressources_statuts AS
SELECT
    r.id as ressources_id,
    r.libelle,
    m.statut,
    COUNT(*) as nombre_mouvements,
    COALESCE(SUM(m.quantite_mouvementee), 0) as quantite_totale
FROM document.ressources r
LEFT JOIN document.mouvements_solde_ressources m ON r.id = m.ressources_id
GROUP BY r.id, r.libelle, m.statut;

-- Vue: Mouvements caisses par statut
CREATE OR REPLACE VIEW document.vw_mouvements_caisses_statuts AS
SELECT
    c.id as caisses_id,
    c.libelle,
    m.statut,
    COUNT(*) as nombre_mouvements,
    COALESCE(SUM(m.solde_mouvementee), 0) as solde_total
FROM document.caisses c
LEFT JOIN document.mouvements_solde_caisses m ON c.id = m.caisses_id
GROUP BY c.id, c.libelle, m.statut;

-- Vue: Ressources avec mouvements en attente
CREATE OR REPLACE VIEW document.vw_ressources_mouvements_attente AS
SELECT
    r.id,
    r.libelle,
    r.quantite as solde_actuel,
    COUNT(m.id) as mouvements_attente,
    SUM(CASE WHEN m.type_transaction = 'ENTREE' THEN m.quantite_mouvementee ELSE 0 END) as entrees_attendues,
    SUM(CASE WHEN m.type_transaction = 'SORTIE' THEN m.quantite_mouvementee ELSE 0 END) as sorties_attendues
FROM document.ressources r
LEFT JOIN document.mouvements_solde_ressources m ON r.id = m.ressources_id
    AND m.statut = 'EN_ATTENTE'
GROUP BY r.id, r.libelle, r.quantite
HAVING COUNT(m.id) > 0;

-- Vue: Caisses avec mouvements en attente
CREATE OR REPLACE VIEW document.vw_caisses_mouvements_attente AS
SELECT
    c.id,
    c.libelle,
    c.solde as solde_actuel,
    COUNT(m.id) as mouvements_attente,
    SUM(CASE WHEN m.type_transaction = 'CREDIT' THEN m.solde_mouvementee ELSE 0 END) as credits_attendus,
    SUM(CASE WHEN m.type_transaction = 'DEBIT' THEN m.solde_mouvementee ELSE 0 END) as debits_attendus
FROM document.caisses c
LEFT JOIN document.mouvements_solde_caisses m ON c.id = m.caisses_id
    AND m.statut = 'EN_ATTENTE'
GROUP BY c.id, c.libelle, c.solde
HAVING COUNT(m.id) > 0;

-- ============================================================
-- FONCTIONS: Audit et Gestion
-- ============================================================

-- Fonction: Obtenir le solde réel courant d'une ressource
CREATE OR REPLACE FUNCTION document.get_ressource_solde_courant(
    p_ressource_id UUID
) RETURNS INTEGER AS $$
DECLARE
    v_solde INTEGER;
BEGIN
    SELECT quantite INTO v_solde
    FROM document.ressources
    WHERE id = p_ressource_id;

    RETURN COALESCE(v_solde, 0);
END;
$$ LANGUAGE plpgsql IMMUTABLE;

-- Fonction: Obtenir le solde réel courant d'une caisse
CREATE OR REPLACE FUNCTION document.get_caisse_solde_courant(
    p_caisse_id UUID
) RETURNS DOUBLE PRECISION AS $$
DECLARE
    v_solde DOUBLE PRECISION;
BEGIN
    SELECT solde INTO v_solde
    FROM document.caisses
    WHERE id = p_caisse_id;

    RETURN COALESCE(v_solde, 0.0);
END;
$$ LANGUAGE plpgsql IMMUTABLE;

-- Fonction: Calculer les mouvements validés pour une ressource sur une période
CREATE OR REPLACE FUNCTION document.get_mouvement_ressource_periode(
    p_ressource_id UUID,
    p_date_debut TIMESTAMP,
    p_date_fin TIMESTAMP
) RETURNS TABLE(
    type_mouvement VARCHAR,
    nombre INTEGER,
    quantite_totale BIGINT,
    dates_plus_ancien TIMESTAMP,
    dates_plus_recent TIMESTAMP
) AS $$
BEGIN
    RETURN QUERY
    SELECT
        type_transaction,
        COUNT(*)::INTEGER,
        COALESCE(SUM(quantite_mouvementee), 0)::BIGINT,
        MIN(datecreation),
        MAX(datecreation)
    FROM document.mouvements_solde_ressources
    WHERE ressources_id = p_ressource_id
        AND statut = 'VALIDEE'
        AND datecreation BETWEEN p_date_debut AND p_date_fin
    GROUP BY type_transaction;
END;
$$ LANGUAGE plpgsql STABLE;

-- Fonction: Calculer les mouvements validés pour une caisse sur une période
CREATE OR REPLACE FUNCTION document.get_mouvement_caisse_periode(
    p_caisse_id UUID,
    p_date_debut TIMESTAMP,
    p_date_fin TIMESTAMP
) RETURNS TABLE(
    type_mouvement VARCHAR,
    nombre INTEGER,
    solde_total NUMERIC,
    dates_plus_ancien TIMESTAMP,
    dates_plus_recent TIMESTAMP
) AS $$
BEGIN
    RETURN QUERY
    SELECT
        type_transaction,
        COUNT(*)::INTEGER,
        COALESCE(SUM(solde_mouvementee), 0)::NUMERIC,
        MIN(datecreation),
        MAX(datecreation)
    FROM document.mouvements_solde_caisses
    WHERE caisses_id = p_caisse_id
        AND statut = 'VALIDEE'
        AND datecreation BETWEEN p_date_debut AND p_date_fin
    GROUP BY type_transaction;
END;
$$ LANGUAGE plpgsql STABLE;

-- ============================================================
-- PERMISSIONS (ajuster selon vos besoins)
-- ============================================================

-- Exemple: Donner accès à un utilisateur spécifique
-- GRANT SELECT, INSERT, UPDATE ON document.mouvements_solde_ressources TO role_utilisateur;
-- GRANT SELECT, INSERT, UPDATE ON document.mouvements_solde_caisses TO role_utilisateur;

COMMIT;
