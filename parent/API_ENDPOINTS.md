# Endpoints API - Système de Transactions de Soldes

## Base URLs

```
Ressources: /ressources/transactions
Caisses: /caisses/transactions
```

## Caisses - CRUD (`/caisses`)

### POST `/caisses`
- **Sans `id`**: cree une nouvelle caisse.
- **Avec `id`**: met a jour une caisse existante.
- **Si `id` inexistant**: retourne une `ParcoursException` avec code `NOT_FOUND`.
- **Champs proteges en mise a jour**: `type`, `solde`, `detailsJson` sont conserves depuis la base (non destructif).
- **Version optimiste**: le champ `version` est obligatoire pour eviter les ecrasements concurrents; une version obsolete provoque un conflit de verrouillage optimiste.
- **Null tolerant**: si `detailsJson` est null dans la requete de mise a jour, la valeur existante est conservee.

**Exemple creation:**
```json
{
  "libelle": "Caisse principale",
  "type": "CAISSE",
  "solde": 10000.0,
  "etat": true,
  "detailsJson": {
    "x10000": 5,
    "x5000": 2
  }
}
```

**Exemple mise a jour controlee:**
```json
{
  "id": "caisse-uuid",
  "version": 0,
  "libelle": "Caisse principale - accueil",
  "etat": false,
  "type": "TENTATIVE_IGNOREE",
  "solde": 0.0,
  "detailsJson": null
}
```

---

## Ressources - Transactions

### 1. Enregistrer un Mouvement
**Endpoint:** `POST /ressources/transactions/{idRessource}/mouvement`

**Paramètres URL:**
- `idRessource` (String, obligatoire) : ID de la ressource

**Body (JSON):**
```json
{
  "quantiteMouvementee": 50,
  "typeTransaction": "SORTIE",
  "description": "Sortie magasin",
  "motif": "Vente client",
  "codeReference": "VENTE-2024-001",
  "createdBy": "magasinier@example.com"
}
```

**Response (200 OK):**
```json
{
  "id": "mouvement-uuid",
  "ressourcesId": "resource-uuid",
  "quantiteInitiale": 100,
  "quantiteMouvementee": 50,
  "quantiteFinale": 50,
  "typeTransaction": "SORTIE",
  "description": "Sortie magasin",
  "motif": "Vente client",
  "codeReference": "VENTE-2024-001",
  "createdBy": "magasinier@example.com",
  "dateCreation": "2024-03-17T10:30:00Z",
  "validee": false,
  "statut": "EN_ATTENTE"
}
```

**Erreurs possibles:**
- 404: Ressource introuvable
- 400: Quantité insuffisante (pour SORTIE)
- 400: Paramètres invalides

---

### 2. Valider un Mouvement
**Endpoint:** `PUT /ressources/transactions/mouvement/{idMouvement}/valider`

**Paramètres URL:**
- `idMouvement` (String, obligatoire) : ID du mouvement

**Paramètres Query:**
- `valideeBy` (String, obligatoire) : ID/email de l'utilisateur validateur

**Response (200 OK):**
```json
{
  "id": "mouvement-uuid",
  "statut": "VALIDEE",
  "validee": true,
  "valideeBy": "manager@example.com",
  "dateValidation": "2024-03-17T11:00:00Z"
}
```

**Erreurs possibles:**
- 404: Mouvement introuvable
- 400: Mouvement déjà validé
- 400: Quantité insuffisante au moment de la validation

---

### 3. Rejeter un Mouvement
**Endpoint:** `PUT /ressources/transactions/mouvement/{idMouvement}/rejeter`

**Paramètres URL:**
- `idMouvement` (String, obligatoire) : ID du mouvement

**Paramètres Query:**
- `raison` (String, obligatoire) : Raison du rejet

**Response (204 No Content)**

**Erreurs possibles:**
- 404: Mouvement introuvable
- 400: Mouvement déjà validé (ne peut pas rejeter)

---

### 4. Obtenir l'Historique
**Endpoint:** `GET /ressources/transactions/{idRessource}/historique`

**Paramètres URL:**
- `idRessource` (String, obligatoire) : ID de la ressource

**Response (200 OK):**
```json
[
  {
    "id": "mouvement-1",
    "statut": "VALIDEE",
    "typeTransaction": "ENTREE",
    "quantiteMouvementee": 100,
    "createdBy": "fournisseur@example.com",
    "dateCreation": "2024-03-16T09:00:00Z",
    "valideeBy": "manager@example.com",
    "dateValidation": "2024-03-16T10:00:00Z"
  },
  {
    "id": "mouvement-2",
    "statut": "VALIDEE",
    "typeTransaction": "SORTIE",
    "quantiteMouvementee": 50,
    "createdBy": "magasinier@example.com",
    "dateCreation": "2024-03-17T10:30:00Z",
    "valideeBy": "manager@example.com",
    "dateValidation": "2024-03-17T11:00:00Z"
  }
]
```

---

### 5. Mouvements en Attente
**Endpoint:** `GET /ressources/transactions/{idRessource}/en-attente`

**Paramètres URL:**
- `idRessource` (String, obligatoire) : ID de la ressource

**Response (200 OK):**
```json
[
  {
    "id": "mouvement-pending-1",
    "typeTransaction": "SORTIE",
    "quantiteMouvementee": 30,
    "statut": "EN_ATTENTE",
    "createdBy": "magasinier@example.com",
    "dateCreation": "2024-03-17T14:30:00Z",
    "validee": false
  }
]
```

---

## Caisses - Transactions

### 1. Enregistrer un Mouvement
**Endpoint:** `POST /caisses/transactions/{idCaisse}/mouvement`

**Paramètres URL:**
- `idCaisse` (String, obligatoire) : ID de la caisse

**Body (JSON):**
```json
{
  "soldeMovementee": 500.00,
  "typeTransaction": "CREDIT",
  "description": "Reçu client",
  "motif": "Paiement vente",
  "codeReference": "FACT-2024-001",
  "createdBy": "caissier@example.com"
}
```

**Response (200 OK):**
```json
{
  "id": "mouvement-uuid",
  "caissesId": "caisse-uuid",
  "soldeInitiale": 1000.00,
  "soldeMovementee": 500.00,
  "soldeFinale": 1500.00,
  "typeTransaction": "CREDIT",
  "description": "Reçu client",
  "motif": "Paiement vente",
  "codeReference": "FACT-2024-001",
  "createdBy": "caissier@example.com",
  "dateCreation": "2024-03-17T10:30:00Z",
  "validee": false,
  "statut": "EN_ATTENTE"
}
```

**Erreurs possibles:**
- 404: Caisse introuvable
- 400: Solde insuffisant (pour DEBIT)
- 400: Paramètres invalides

---

### 2. Valider un Mouvement
**Endpoint:** `PUT /caisses/transactions/mouvement/{idMouvement}/valider`

**Paramètres URL:**
- `idMouvement` (String, obligatoire) : ID du mouvement

**Paramètres Query:**
- `valideeBy` (String, obligatoire) : ID/email de l'utilisateur validateur

**Response (200 OK):**
```json
{
  "id": "mouvement-uuid",
  "statut": "VALIDEE",
  "validee": true,
  "soldeFinale": 1500.00,
  "valideeBy": "manager@example.com",
  "dateValidation": "2024-03-17T11:00:00Z"
}
```

---

### 3. Rejeter un Mouvement
**Endpoint:** `PUT /caisses/transactions/mouvement/{idMouvement}/rejeter`

**Paramètres URL:**
- `idMouvement` (String, obligatoire) : ID du mouvement

**Paramètres Query:**
- `raison` (String, obligatoire) : Raison du rejet

**Response (204 No Content)**

---

### 4. Obtenir l'Historique
**Endpoint:** `GET /caisses/transactions/{idCaisse}/historique`

**Paramètres URL:**
- `idCaisse` (String, obligatoire) : ID de la caisse

**Response (200 OK):**
```json
[
  {
    "id": "mouvement-1",
    "statut": "VALIDEE",
    "typeTransaction": "CREDIT",
    "soldeMovementee": 1000.00,
    "createdBy": "caissier@example.com",
    "dateCreation": "2024-03-16T09:00:00Z",
    "valideeBy": "manager@example.com",
    "dateValidation": "2024-03-16T10:00:00Z"
  }
]
```

---

### 5. Mouvements en Attente
**Endpoint:** `GET /caisses/transactions/{idCaisse}/en-attente`

**Paramètres URL:**
- `idCaisse` (String, obligatoire) : ID de la caisse

**Response (200 OK):**
```json
[
  {
    "id": "mouvement-pending-1",
    "typeTransaction": "DEBIT",
    "soldeMovementee": 250.00,
    "statut": "EN_ATTENTE",
    "createdBy": "caissier@example.com",
    "dateCreation": "2024-03-17T14:30:00Z",
    "validee": false
  }
]
```

---

## Codes d'Erreur

| Code | Description |
|------|-------------|
| 200 | OK - Succès |
| 204 | No Content - Opération réussie sans contenu |
| 400 | Bad Request - Paramètres invalides ou validation échouée |
| 404 | Not Found - Ressource/Caisse/Mouvement introuvable |
| 409 | Conflict - Opération en conflit avec l'état actuel |
| 500 | Internal Server Error - Erreur serveur |

---

## Exemples d'Utilisation avec cURL

### Enregistrer un mouvement de ressource
```bash
curl -X POST http://localhost:8080/ressources/transactions/resource-001/mouvement \
  -H "Content-Type: application/json" \
  -d '{
    "quantiteMouvementee": 50,
    "typeTransaction": "SORTIE",
    "description": "Vente",
    "motif": "Client ABC",
    "codeReference": "CMD-001",
    "createdBy": "user@example.com"
  }'
```

### Valider un mouvement
```bash
curl -X PUT "http://localhost:8080/ressources/transactions/mouvement/mouvement-uuid/valider?valideeBy=manager@example.com" \
  -H "Content-Type: application/json"
```

### Obtenir l'historique
```bash
curl -X GET http://localhost:8080/ressources/transactions/resource-001/historique \
  -H "Accept: application/json"
```

---

## Types de Transactions

### Ressources
| Type | Direction | Description |
|------|-----------|-------------|
| ENTREE | + | Augmente la quantité (réception) |
| SORTIE | - | Diminue la quantité (vente/consomation) |
| AJUSTEMENT | ± | Modifie directement (inventaire) |

### Caisses
| Type | Direction | Description |
|------|-----------|-------------|
| CREDIT | + | Reçoit de l'argent |
| DEBIT | - | Dépense de l'argent |
| AJUSTEMENT | ± | Correction de compte |

---

## Statuts de Mouvement

- **EN_ATTENTE** : Mouvement enregistré, attend validation
- **VALIDEE** : Mouvement appliqué au solde
- **REJETEE** : Mouvement annulé avec justification
