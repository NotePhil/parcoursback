# 🔍 GUIDE DE RELECTURE - IMPLÉMENTATION SYNCHRONISATION RELATIONS

**Pour vérifier rapidement les implémentations clés**

---

## ✅ CHECKLIST DE RELECTURE

### 1. CODE D'ERREUR MÉTIER
📍 **Fichier** : `commun-outil/src/main/java/cmr/notep/exceptions/enumeration/ParcoursExceptionCodeEnum.java`
```java
RELATION_SYNC_FAILED("Failed to synchronize relationship: check foreign key constraints and data integrity");
```
✅ Vérifié : `ParcoursExceptionCodeEnum` ligne ~20 → nouveau code ajouté

📍 **Fichier** : `commun-outil/src/main/java/cmr/notep/exceptions/GlobalExceptionHandler.java`
```java
case RELATION_SYNC_FAILED -> HttpStatus.BAD_REQUEST;
```
✅ Vérifié : mapping HTTP 400 → nouveau switch case

---

### 2. PATTERN STRATÉGIE GATEWAY

#### Interface
📍 **Fichier** : `exemplaire-service/src/main/java/cmr/notep/exemplaire/gateway/DocumentGatewayStrategy.java`
```java
public interface DocumentGatewayStrategy {
    // 12 méthodes : avoirAttribut, avoirDistributeur, posterDocument, etc.
}
```
✅ Vérifié : Interface complète, tous les services document présents

#### Implémentation Injection
📍 **Fichier** : `exemplaire-service/src/main/java/cmr/notep/exemplaire/gateway/InjectedDocumentGatewayStrategy.java`
```java
@Component
@Slf4j
public class InjectedDocumentGatewayStrategy implements DocumentGatewayStrategy {
    // @Autowired pour chaque interface API (IAttributsApi, IDistributeursApi, etc.)
    // Chaque méthode appelle l'interface correspondante
}
```
✅ Vérifié : Délégation correcte aux interfaces @Autowired

#### Implémentation WS
📍 **Fichier** : `exemplaire-service/src/main/java/cmr/notep/exemplaire/gateway/WsDocumentGatewayStrategy.java`
```java
@Component
@Slf4j
public class WsDocumentGatewayStrategy implements DocumentGatewayStrategy {
    // @Autowired pour chaque *ClientWs (AttributsClientWs, DistributeursClientWs, etc.)
    // Chaque méthode appelle le client WS correspondant
}
```
✅ Vérifié : Délégation correcte aux clients WS

#### Configuration Spring
📍 **Fichier** : `exemplaire-service/src/main/java/cmr/notep/exemplaire/config/GatewayStrategyConfig.java`
```java
@Configuration
public class GatewayStrategyConfig {
    
    @Bean
    @ConditionalOnProperty(name = "parcours.gateway.mode", 
                          havingValue = "injection", 
                          matchIfMissing = true)
    public DocumentGatewayStrategy injectedGatewayStrategy(...) { ... }
    
    @Bean
    @ConditionalOnProperty(name = "parcours.gateway.mode", 
                          havingValue = "ws")
    public DocumentGatewayStrategy wsGatewayStrategy(...) { ... }
}
```
✅ Vérifié : Deux beans conditionnels, injection mode défaut

#### Propriété
📍 **Fichier** : `exemplaire-service/src/main/resources/application.properties`
```properties
# Sélecteur de stratégie d'accès aux dépendances du service document
# injection = appels directs aux interfaces métier (défaut, cohérence transactionnelle)
# ws = appels HTTP REST vers document-service (déploiement distribué)
parcours.gateway.mode=injection
```
✅ Vérifié : Propriété commentée et définie à "injection"

---

### 3. MÉTHODES POSTER* HARMONISÉES

#### PersonnelsBusiness.posterPersonnel
📍 **Fichier** : `document-service/src/main/java/cmr/notep/business/PersonnelsBusiness.java`

**Points clés à vérifier** :
```java
public Personnels posterPersonnel(Personnels personnel) throws ParcoursException {
    // 1. Charger existant si ID
    if (personnel.getId() != null) {
        entitySaved = personnelsRepo.findById(personnel.getId())
            .orElseThrow(...);
        dozerMapperBean.map(personnel, entitySaved);
    } else {
        entitySaved = dozerMapperBean.map(personnel, PersonnelsEntity.class);
    }
    
    // 2. Appeler helper de gestion relations
    gererJouerRoles(personnel, entitySaved);
    
    // 3. Save + return
    entitySaved = personnelsRepo.save(entitySaved);
    return dozerMapperBean.map(entitySaved, Personnels.class);
}

private void gererJouerRoles(Personnels personnel, PersonnelsEntity entitySaved) 
                              throws ParcoursException {
    // PURGE si liste absente
    if (CollectionUtils.isEmpty(personnel.getRoles())) {
        // delete tous les JouerRolesEntity existants
        try { ... } catch (Exception e) {
            throw new ParcoursException(RELATION_SYNC_FAILED, ..., e);
        }
        return;
    }
    
    // SYNC sinon (delete absent, update existant, create nouveau)
    // ...
}
```
✅ Vérifié : Pattern complet, exception handling

#### CategoriesBusiness.posterCategorie
📍 **Fichier** : `document-service/src/main/java/cmr/notep/business/CategoriesBusiness.java`

**Points clés** : Même pattern que PersonnelsBusiness + helper `gererAttributsCategorie()`
✅ Vérifié : Load/Sync/Delete/Exception coherent

#### DistributeursBusiness.posterDistributeur
📍 **Fichier** : `document-service/src/main/java/cmr/notep/business/DistributeursBusiness.java`

**Points clés** : 
- Deux relations : `precoMouvementsQtes` et `promotions`
- Deux helpers : `gererPrecoMouvementsQtesDistributeur()` et `gererPromotionsDistributeur()`
- Même pattern appliqué deux fois indépendamment
✅ Vérifié : Load/Sync × 2/Delete × 2/Exception

#### PrecomouvementsBusiness.posterPrecomouvement
📍 **Fichier** : `document-service/src/main/java/cmr/notep/business/PrecomouvementsBusiness.java`

**Points clés** : Helper `gererPrecoMouvementsQtes()` avec try/catch pour ParcoursException
✅ Vérifié : Flush() ajouté, ParcoursException(RELATION_SYNC_FAILED) levée

#### ServicesBusiness.posterService
📍 **Fichier** : `document-service/src/main/java/cmr/notep/business/ServicesBusiness.java`

**Points clés** :
```java
public Services posterService(Services tache) throws ParcoursException {
    try {
        ServicesEntity saved = this.daoAccessorService.getRepository(ServicesRepository.class)
                .save(dozerMapperBean.map(tache, ServicesEntity.class));
        entityManager.flush();  // ← FLUSH explicite pour capturer exception
        return dozerMapperBean.map(saved, Services.class);
    } catch (DataIntegrityViolationException e) {
        throw new ParcoursException(ParcoursExceptionCodeEnum.DUPLICATE_KEY, 
                                    e.getMostSpecificCause().getMessage(), e);
    }
}
```
✅ Vérifié : Flush() + DataIntegrityViolationException handling

---

## 🎯 POINTS D'ATTENTION À RELEVER

### Critique
- [ ] **Imports ParcoursException** : Tous les fichiers métier importent `ParcoursException` et `ParcoursExceptionCodeEnum`
- [ ] **Try/Catch complets** : Tous les `gererXxx()` enveloppent delete/save dans try/catch → ParcoursException
- [ ] **Flush explicite** : `entityManager.flush()` présent après `save()` dans ServicesBusiness

### Important
- [ ] **Pattern cohérent** : Tous les 5 `posterXxx` suivent Load/Sync/Delete/Exception
- [ ] **Liste absente = purge** : Vérifier `CollectionUtils.isEmpty()` puis `clear()` + `delete()` chaque existant
- [ ] **Rattachement parent** : `enfant.setParent(parent)` avant `save(enfant)`

### Minor
- [ ] Pas de `orphanRemoval=true` en JPA (responsibility in business, pas decorator)
- [ ] Commentaires sur blocs complexes de sync (utile pour maintenance)

---

## 📝 CHECKLIST RELECTURE DÉTAILLÉE

### Imports & Dépendances
- [ ] ParcoursException importé dans business classes
- [ ] ParcoursExceptionCodeEnum importé dans business classes
- [ ] EntityManager importé dans ServicesBusiness (@PersistenceContext)
- [ ] CollectionUtils importé (spring-core)
- [ ] ArrayList, List importés

### Exceptions
- [ ] ParcoursException hérité de `RuntimeException` (pas checked)
- [ ] ParcoursException construits avec (code, message, cause)
- [ ] Tous les `catch(Exception)` → ParcoursException(RELATION_SYNC_FAILED, ..., e)
- [ ] DataIntegrityViolationException → ParcoursException(DUPLICATE_KEY, ..., e) dans ServicesBusiness

### Synchronisation Collection
- [ ] Création `List<String> newIds` pour tracer IDs à conserver
- [ ] Boucle DELETE sur existants → vérifier si NOT in newIds → delete + remove
- [ ] Boucle ADD/UPDATE sur new liste → charger si ID existant, create si absent
- [ ] Set ID=null en créa pour forcer UUID generation
- [ ] Set parent avant save enfant

### Configuration Spring
- [ ] @ConditionalOnProperty sur chaque bean stratégie
- [ ] Deux beans différents pour injection vs ws
- [ ] Propriété `parcours.gateway.mode` définie dans `application.properties`
- [ ] Défaut = "injection" (matchIfMissing=true)

---

## 🧪 TESTS À FAIRE (orientation pour ittests)

Pour chaque métier avec listes (Personnels, PrecoMouvements, Distributeurs, Categories) :

```
Test 1 : CREATE avec liste vide [] → aucun enfant en BDD ✓
Test 2 : CREATE avec 1 enfant → 1 enfant créé ✓
Test 3 : UPDATE : 2 existants → garder 1er, supprimer 2ème, ajouter 3ème → résultat 2 enfants ✓
Test 4 : UPDATE : liste null → PURGE complète ✓
Test 5 : UPDATE : ID enfant inexistant → RuntimeException ✓
Test 6 : FK violation → ParcoursException(RELATION_SYNC_FAILED) ✓
Test 7 : Duplicate key → ParcoursException(DUPLICATE_KEY) ✓
```

---

## 📊 RÉSUMÉ RAPIDE

| Composant | Fichier | Statut | Check |
|-----------|---------|--------|-------|
| **Code erreur** | ParcoursExceptionCodeEnum.java | ✅ | RELATION_SYNC_FAILED added |
| **Handler HTTP** | GlobalExceptionHandler.java | ✅ | 400 mapped |
| **Interface Gateway** | DocumentGatewayStrategy.java | ✅ | 12 méthodes |
| **Impl Injection** | InjectedDocumentGatewayStrategy.java | ✅ | @Autowired delegation |
| **Impl WS** | WsDocumentGatewayStrategy.java | ✅ | ClientWs delegation |
| **Config Spring** | GatewayStrategyConfig.java | ✅ | 2 beans conditionnels |
| **Propriété** | application.properties | ✅ | mode=injection |
| **PersonnelsBusiness** | PersonnelsBusiness.java | ✅ | Load/Sync/Delete |
| **CategoriesBusiness** | CategoriesBusiness.java | ✅ | Load/Sync/Delete |
| **DistributeursBusiness** | DistributeursBusiness.java | ✅ | Load/Sync × 2/Delete × 2 |
| **PrecomouvementsBusiness** | PrecomouvementsBusiness.java | ✅ | Load/Sync/Delete |
| **ServicesBusiness** | ServicesBusiness.java | ✅ | Flush + exception |

---

**Fait pour** : Code review rapide  
**Usage** : Relecture systématique des implémentations clés  
**Durée estimée** : 20-30 minutes de relecture complète

🎯 Bon courage pour la relecture !
