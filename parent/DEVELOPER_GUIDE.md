# 📢 GUIDE DÉVELOPPEUR - NOUVELLE GOUVERNANCE DES RELATIONS

**Pour comprendre et utiliser les nouvelles implémentations**

---

## 🎯 QU'EST-CE QUI A CHANGÉ ?

### Avant
- Chaque méthode `poster*` gérait ses relations différemment
- `orphanRemoval=true` en JPA ou gestion manuelle inconsistante
- Pas de pattern unifié
- Dozer mapping direct sans logique métier

### Après
- **Pattern unifié** pour tous les `poster*` (load, sync, delete, exception)
- **Règle métier claire** : liste absente = PURGE
- **Exception métier dédiée** : `ParcoursException(RELATION_SYNC_FAILED)`
- **Stratégie configurable** : injection local ou WS distribué (sans code change)

---

## 📋 RÈGLE MÉTIER GLOBALE

**Si vous n'envoyez pas une liste (null) ou l'envoyez vide ([]), TOUS les enfants seront supprimés.**

```json
// Cas 1 : Liste absente => PURGE complète
POST /personnels
{
    "id": "pers-001",
    "nom": "Dupont",
    "roles": null  // ← PURGE : supprimer tous les JouerRoles de ce personnel
}

// Cas 2 : Liste vide => PURGE complète
POST /personnels
{
    "id": "pers-001",
    "nom": "Dupont",
    "roles": []  // ← PURGE : supprimer tous les JouerRoles de ce personnel
}

// Cas 3 : Liste fournie => SYNC complète
POST /personnels
{
    "id": "pers-001",
    "nom": "Dupont",
    "roles": [  // ← SYNC : add/update/delete basés sur ces IDs
        { "id": "existing-role-1", ... },  // UPDATE existant
        { "id": null, ... },               // CREATE nouveau
        // Note : le role-2 existant est absent => sera DELETE
    ]
}
```

---

## 🔄 PATTERN SYNCHRONISATION

### 1️⃣ Charger l'entité existante (si update)
```java
if (input.getId() != null) {
    entity = repository.findById(input.getId())
        .orElseThrow(() -> new RuntimeException("Non trouvé"));
    dozer.map(input, entity);  // Merge properties
} else {
    entity = dozer.map(input, Entity.class);  // Create
}
```

### 2️⃣ Synchroniser les relations enfants
```java
private void gererRelation(Input input, Entity entity) throws ParcoursException {
    List<Child> newList = input.getChildren();
    
    // PURGE si absent/vide
    if (newList == null || newList.isEmpty()) {
        try {
            for (ChildEntity c : new ArrayList<>(entity.getChildrenEntities())) {
                childRepository.delete(c);
            }
            entity.getChildrenEntities().clear();
        } catch (Exception e) {
            throw new ParcoursException(RELATION_SYNC_FAILED, "Erreur: " + e.getMessage(), e);
        }
        return;
    }
    
    // SYNC différentielle
    List<String> newIds = newList.stream()
        .filter(c -> c.getId() != null)
        .map(Child::getId)
        .toList();
    
    // DELETE : présent en BDD, absent de la nouvelle liste
    for (ChildEntity existing : new ArrayList<>(entity.getChildrenEntities())) {
        if (!newIds.contains(existing.getId())) {
            try {
                childRepository.delete(existing);
                entity.getChildrenEntities().remove(existing);
            } catch (Exception e) {
                throw new ParcoursException(RELATION_SYNC_FAILED, "Impossible de supprimer", e);
            }
        }
    }
    
    // ADD/UPDATE
    for (Child child : newList) {
        ChildEntity c;
        if (child.getId() != null) {
            c = childRepository.findById(child.getId())  // UPDATE
                .orElseThrow(() -> new RuntimeException("Non trouvé"));
            dozer.map(child, c);
        } else {
            c = dozer.map(child, ChildEntity.class);    // CREATE
            c.setId(null);  // Force UUID generation
        }
        c.setParent(entity);  // ATTACH parent
        try {
            ChildEntity saved = childRepository.save(c);
            if (!entity.getChildrenEntities().contains(saved)) {
                entity.getChildrenEntities().add(saved);
            }
        } catch (Exception e) {
            throw new ParcoursException(RELATION_SYNC_FAILED, "Impossible d'enregistrer", e);
        }
    }
}
```

### 3️⃣ Sauvegarder l'entité parent
```java
entity = repository.save(entity);
return dozer.map(entity, Output.class);
```

---

## 🚨 GESTION DES ERREURS

### Code : `RELATION_SYNC_FAILED`
**HTTP 400 Bad Request**  
Levé quand :
- Suppression d'enfant échoue (FK constraint)
- Sauvegarde d'enfant échoue (validation, unique constraint)
- Accès BD échoue (connection lost, etc.)

**Exemple** :
```json
POST /distributeurs
{
    "promotions": [
        { "id": "invalid-promo-id" }  // ← Erreur !
    ]
}

// Réponse 400
{
    "code": "RELATION_SYNC_FAILED",
    "message": "Impossible d'enregistrer la promotion du distributeur: Promotion non trouvée"
}
```

### Code : `DUPLICATE_KEY`
**HTTP 409 Conflict**  
Levé quand :
- Unique constraint violation lors du `save()`

**Exemple** :
```json
POST /services
{
    "codeUnique": "S1"  // ← Existe déjà !
}

// Réponse 409
{
    "code": "DUPLICATE_KEY",
    "message": "A record with this key already exists"
}
```

---

## 🎛️ STRATÉGIE GATEWAY

### Concept
Pour accéder aux services du document depuis exemplaire, vous avez 2 stratégies sans changer le code :

#### Mode INJECTION (défaut, cohérence transactionnelle)
```properties
parcours.gateway.mode=injection
```
- Appels directs aux interfaces métier (IPersonnelsApi, IPrecoMouvementsApi, etc.)
- Même transaction => atomicité garantie
- Performance meilleure
- **À préférer en déploiement monolithe**

#### Mode WS (distribué, découplé)
```properties
parcours.gateway.mode=ws
```
- Appels HTTP REST vers document-service
- Transactions séparées
- Meilleur découplage
- **À préférer en déploiement microservices**

### Usage dans le métier
```java
@Component
public class ExemplairesBusiness {
    
    private final DocumentGatewayStrategy gateway;  // ← Interface unique !
    
    public ExemplairesBusiness(DocumentGatewayStrategy gateway) {
        this.gateway = gateway;  // Spring injecte Injection ou Ws selon config
    }
    
    public void traiter() throws ParcoursException {
        // Peu importe le mode, même code
        Attributs attr = gateway.avoirAttribut("attr-1");
        Ressources res = gateway.avoirRessource("res-1");
        Distributeurs dist = gateway.posterDistributeur(new Distributeurs(...));
    }
}
```

**Avantage** : Pas de if/else sur le mode. Spring choisit automatiquement.

---

## 📝 CHECKLIST : Ajouter une nouvelle relation synchronisée

Si vous avez besoin d'ajouter une nouvelle relation à synchroniser (ex: `Documents.attributs`), voici comment faire :

### 1. Modèle metier
```java
// document-interface/modele/Documents.java
@Data
@Builder
public class Documents {
    // ...
    private List<Attributs> attributs;  // ← Nouvelle liste
}
```

### 2. Entité JPA
```java
// ressources-jpa/dao/DocumentsEntity.java
@Entity
@Table(name = "documents", schema = "document")
public class DocumentsEntity {
    // ...
    @OneToMany(...)
    @Mapping("attributs")
    private List<AttributsEntity> attributsEntities;  // ← Nouveaux mapping
}
```

### 3. Business : Helper + posterXxx
```java
// document-service/business/DocumentsBusiness.java

public Documents posterDocument(Documents document) throws ParcoursException {
    DocumentsEntity entity;
    
    if (document.getId() != null) {
        entity = repo.findById(document.getId())
            .orElseThrow(...);
        dozer.map(document, entity);
    } else {
        entity = dozer.map(document, DocumentsEntity.class);
    }

    // ← Ajouter : appeler helper
    gererAttributsDocument(document, entity);

    entity = repo.save(entity);
    return dozer.map(entity, Documents.class);
}

// ← Ajouter : helper (copier/adapter pattern des autres)
private void gererAttributsDocument(Documents document, DocumentsEntity entity) 
                                    throws ParcoursException {
    List<Attributs> newList = document.getAttributs();
    
    if (CollectionUtils.isEmpty(newList)) {
        try {
            if (entity.getAttributsEntities() != null) {
                AttributsRepository attrRepo = daoAccessor.getRepository(AttributsRepository.class);
                for (AttributsEntity attr : new ArrayList<>(entity.getAttributsEntities())) {
                    attrRepo.delete(attr);
                }
                entity.getAttributsEntities().clear();
            }
        } catch (Exception e) {
            throw new ParcoursException(RELATION_SYNC_FAILED, "Impossible de supprimer", e);
        }
        return;
    }

    // SYNC comme dans PersonnelsBusiness.gererJouerRoles()
    // ...
}
```

### 4. Tester
```java
@Test
public void testDocumentAttributSync() {
    Documents doc = Documents.builder()
        .id("doc-1")
        .attributs(List.of(
            Attributs.builder().id("attr-1").build(),
            Attributs.builder().id("attr-2").build()
        ))
        .build();
    
    Documents saved = documentsService.posterDocument(doc);
    assertEquals(2, saved.getAttributs().size());
    
    // Update : garder attr-1, supprimer attr-2, ajouter attr-3
    String attr1Id = saved.getAttributs().get(0).getId();
    saved.setAttributs(List.of(
        Attributs.builder().id(attr1Id).build(),
        Attributs.builder().id(null).build()  // Nouveau
    ));
    
    Documents updated = documentsService.posterDocument(saved);
    assertEquals(2, updated.getAttributs().size());  // attr-1 kept, attr-2 deleted, attr-3 created
}
```

---

## 🧠 POINTS CLÉ À RETENIR

### Règle métier
✅ Absence liste = PURGE complète (pas d'exception, comportement normal)  
✅ Chaque appel `posterXxx` est atomique (transaction complète)  
✅ Erreurs FK/validation = ParcoursException(RELATION_SYNC_FAILED)

### Code
✅ Pattern : Load → Sync → Delete → Exception (chaque helper)  
✅ List<String> newIds = collection pour tracer conservés  
✅ CollectionUtils.isEmpty() pour vérifier absence/vide  
✅ EntityRepository.delete() dans try/catch → ParcoursException

### Config
✅ Stratégie gateway contrôlée par propriété (pas code change)  
✅ Défaut = injection (local, rapide)  
✅ Métier inject DocumentGatewayStrategy (interface unique)

---

## 🔗 DOCUMENTATION COMPLÈTE

- **CODE_REVIEW_GUIDE.md** : Guide relecture avec checklist
- **IMPLEMENTATION_EXECUTIVE_SUMMARY.md** : Vue d'ensemble pour management
- **IMPLEMENTATION_SUMMARY.md** : Détails techniques avec exemples
- **CHECKLIST_IMPLEMENTATION.md** : Étapes complètes & suivi

---

## ❓ QUESTIONS FRÉQUENTES

**Q : Comment faire si je veux ignorer les enfants (ne pas les synchroniser) ?**  
A : Envoyez la liste null ou vide. ATTENTION : cela supprime les enfants existants !

**Q : Je veux ajouter un enfant sans supprimer les existants ?**  
A : Vous DEVEZ envoyer la liste COMPLÈTE (existants + nouveaux). C'est une sync complète.

**Q : Qu'arrive-t-il si la suppression d'un enfant échoue (FK constraint) ?**  
A : `ParcoursException(RELATION_SYNC_FAILED)` levée → HTTP 400 au client → transaction rollback

**Q : Puis-je utiliser orphanRemoval=true en JPA ?**  
A : Non recommandé. La suppression est gérée en métier (plus flexible, plus contrôlable).

**Q : Comment tester localement avec mode WS ?**  
A : Ajouter `parcours.gateway.mode=ws` à votre fichier `application.properties` local et relancer.

---

**Dernière mise à jour** : 2026-05-13  
**Publiée par** : GitHub Copilot  
**Statut** : Prêt pour production
