# Documentation: Gestion des Transactions de Soldes

## Vue d'ensemble

Ce système a été conçu pour gérer les modifications de soldes des Ressources et des Caisses de manière transactionnelle, sécurisée et auditable. Les modifications directes de solde ne sont **pas autorisées** et doivent toujours passer par les services métier dédiés.

## Architecture

### Composants principaux

1. **Entités de transaction**
   - `MouvementSoldeRessourcesEntity` : Trace tous les mouvements de quantité pour une ressource
   - `MouvementSoldeCaissesEntity` : Trace tous les mouvements de solde pour une caisse

2. **Services métier**
   - `SoldeRessourcesTransactionBusiness` : Gère les transactions pour les ressources
   - `SoldeCaissesTransactionBusiness` : Gère les transactions pour les caisses

3. **Repositories**
   - `MouvementSoldeRessourcesRepository` : Accès aux données des mouvements ressource
   - `MouvementSoldeCaissesRepository` : Accès aux données des mouvements caisse

4. **Contrôleurs API**
   - `RessourcesTransactionController` : Endpoints REST pour ressources
   - `CaissesTransactionController` : Endpoints REST pour caisses

## Flux de transaction

### 1. Enregistrement du mouvement (statut: EN_ATTENTE)
```
POST /ressources/transactions/{idRessource}/mouvement
{
  "quantiteMouvementee": 10,
  "typeTransaction": "SORTIE",
  "description": "Sortie de stock",
  "motif": "Vente",
  "codeReference": "CMD-2024-001",
  "createdBy": "user@example.com"
}
```

### 2. Validation du mouvement
```
PUT /ressources/transactions/mouvement/{idMouvement}/valider?valideeBy=manager@example.com
```
À cette étape, la quantité est effectivement modifiée dans la ressource.

### 3. Historique et audit
```
GET /ressources/transactions/{idRessource}/historique
GET /ressources/transactions/{idRessource}/en-attente
```

## Types de transactions

### Pour les Ressources
- **ENTREE** : Augmente la quantité (réception de stock)
- **SORTIE** : Diminue la quantité (validation de sortie)
- **AJUSTEMENT** : Modifie la quantité directement (inventaire)

### Pour les Caisses
- **CREDIT** : Augmente le solde (recevoir de l'argent)
- **DEBIT** : Diminue le solde (dépenser de l'argent)
- **AJUSTEMENT** : Modifie le solde directement (correction)

## Statuts de mouvement

1. **EN_ATTENTE** : Mouvement enregistré, en attente de validation
2. **VALIDEE** : Mouvement validé et appliqué au solde
3. **REJETEE** : Mouvement rejeté et annulé

## Contrôles et validations

### Validation automatique
- Vérification de la disponibilité pour les sorties
- Vérification du solde minimal pour les débits
- Audit trail complet avec utilisateurs créateurs/validateurs
- Timestamps d'audit automatiques

### Gestion des erreurs
```java
// Tentative de sortie avec quantité insuffisante
MouvementSoldeRessource mouvement = soldeRessourcesTransactionBusiness.enregistrerMouvement(
    "ressource-001",
    100, // quantité demandée
    "SORTIE",
    "Test",
    "Test sortie",
    "TEST-001",
    "user@example.com"
);
// Lance: RuntimeException("Quantité insuffisante...")
```

## Requêtes utiles

### Obtenir le solde réel actuel
```java
RessourcesEntity ressource = repository.findById(id).orElse(null);
Integer soldeActuel = ressource.getQuantite(); // Solde réel
```

### Rechercher les mouvements par période
```java
List<MouvementSoldeRessourcesEntity> mouvements = 
    repository.findMovementsByPeriod(ressourceId, dateDebut, dateFin);
```

### Calculer les totaux
```java
Integer totalEntrees = repository.getTotalEntrees(ressourceId);
Integer totalSorties = repository.getTotalSorties(ressourceId);
```

## Considérations de sécurité

1. **Immutabilité du solde** : Le champ `solde` en base ne doit jamais être modifié directement
2. **Auditabilité complète** : Chaque modification est tracée avec utilisateur et timestamp
3. **Validation préalable** : Les mouvements sont d'abord enregistrés, puis validés
4. **Traçabilité** : Les mouvements rejetés conservent une trace du refus

## Migration et intégration

### Pour les Ressources existantes
Créer un mouvement d'ajustement initial lors de la migration :
```java
soldeRessourcesTransactionBusiness.enregistrerMouvement(
    "ressource-001",
    quantiteActuelle,
    "AJUSTEMENT",
    "Solde initial lors de la migration",
    "MIGRATION",
    "INIT-" + timestamp,
    "system@example.com"
);
```

### Points d'intégration
- Les services métier utilisent `DaoAccessorService` pour l'accès DAO
- Les modèles d'interface utilisent Dozer pour le mapping
- Les repositories utilisent Spring Data JPA avec requêtes JPQL

## Exemples d'utilisation

### Enregistrer une sortie de ressource
```java
@PostMapping("/entree")
public MouvementSoldeRessource enregistrerEntree(
        @RequestBody MouvementSoldeRessource mouvement) {
    return soldeRessourcesTransactionBusiness.enregistrerMouvement(
            mouvement.getRessourcesId(),
            mouvement.getQuantiteMouvementee(),
            "ENTREE",
            mouvement.getDescription(),
            mouvement.getMotif(),
            mouvement.getCodeReference(),
            getCurrentUser()
    );
}
```

### Valider automatiquement après vérification
```java
@PostMapping("/valider/{mouvementId}")
@Transactional
public MouvementSoldeRessource validerMouvement(
        @PathVariable String mouvementId,
        @PathVariable String valideeBy) {
    // Effectuer des vérifications métier avant validation
    return soldeRessourcesTransactionBusiness.validerMouvement(
            mouvementId,
            valideeBy
    );
}
```

## Roadmap future

- [ ] Notifications lors de validation/rejet
- [ ] Workflow d'approbation multi-niveaux
- [ ] Rapports d'audit complets
- [ ] Intégration avec système de paiement
- [ ] Réconciliation automatique des stocks
