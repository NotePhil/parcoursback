# Configuration et Dépendances - Système de Transactions de Soldes

## 📋 Vue d'ensemble

Ce document explique les dépendances requises, les configurations et les étapes pour intégrer le système de transactions de soldes dans votre projet Spring Boot.

## 🔧 Dépendances Maven

Assurez-vous que votre `pom.xml` contient les dépendances suivantes :

```xml
<!-- Spring Boot Web -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>

<!-- Spring Data JPA -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>

<!-- PostgreSQL Driver -->
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <version>42.6.0</version>
    <scope>runtime</scope>
</dependency>

<!-- Lombok -->
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <optional>true</optional>
</dependency>

<!-- Dozer (Mapping) -->
<dependency>
    <groupId>net.sf.dozer</groupId>
    <artifactId>dozer</artifactId>
    <version>5.5.1</version>
</dependency>

<!-- Jackson (JSON) -->
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
</dependency>

<!-- Tests -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>

<dependency>
    <groupId>org.mockito</groupId>
    <artifactId>mockito-core</artifactId>
    <scope>test</scope>
</dependency>
```

## 🔌 Configuration Spring Boot

### application.properties

```properties
# DataSource
spring.datasource.url=jdbc:postgresql://localhost:5432/parcours
spring.datasource.username=postgres
spring.datasource.password=your_password
spring.datasource.driver-class-name=org.postgresql.Driver

# JPA/Hibernate
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.jdbc.batch_size=20
spring.jpa.properties.hibernate.order_inserts=true
spring.jpa.properties.hibernate.order_updates=true

# Transactions
spring.jpa.properties.hibernate.enable_lazy_load_no_trans=true

# Jackson
spring.jackson.serialization.write-dates-as-timestamps=false
spring.jackson.time-zone=Europe/Paris
spring.jackson.locale=fr_FR

# Logging
logging.level.org.springframework.web=INFO
logging.level.org.hibernate.SQL=DEBUG
logging.level.org.hibernate.type.descriptor.sql.BasicBinder=TRACE
```

### application.yml (Alternativement)

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/parcours
    username: postgres
    password: your_password
    driver-class-name: org.postgresql.Driver
  
  jpa:
    database-platform: org.hibernate.dialect.PostgreSQLDialect
    hibernate:
      ddl-auto: validate
    show-sql: false
    properties:
      hibernate:
        format_sql: true
        jdbc:
          batch_size: 20
        order_inserts: true
        order_updates: true
        enable_lazy_load_no_trans: true
  
  jackson:
    serialization:
      write-dates-as-timestamps: false
    time-zone: Europe/Paris
    locale: fr_FR
```

## 🗄️ Configuration Base de Données

### 1. Créer la base de données

```sql
CREATE DATABASE parcours;
```

### 2. Créer le schéma

```sql
CREATE SCHEMA document AUTHORIZATION postgres;
```

### 3. Exécuter les migrations

Exécuter le fichier `schema-pg.sql` pour créer les tables principales :
```bash
psql -U postgres -d parcours -f schema-pg.sql
```

Exécuter le fichier `migration-transactions-soldes.sql` pour créer les tables de transaction :
```bash
psql -U postgres -d parcours -f migration-transactions-soldes.sql
```

## 🚀 Intégration dans le Projet

### 1. Ajouter le scanner de composants

Dans votre classe d'application principale :

```java
@SpringBootApplication(scanBasePackages = {
    "cmr.notep",
    "cmr.notep.api",
    "cmr.notep.business",
    "cmr.notep.impl"
})
public class DocumentApplication {
    public static void main(String[] args) {
        SpringApplication.run(DocumentApplication.class, args);
    }
}
```

### 2. Configuration du Dozer Mapper

Créer une configuration Spring pour Dozer (si elle n'existe pas) :

```java
@Configuration
public class DozerMapperConfig {
    
    @Bean
    public DozerBeanMapper dozerBeanMapper() {
        return new DozerBeanMapper();
    }
}
```

### 3. Enregistrer les Repositories

Les repositories sont enregistrés automatiquement via `@EnableJpaRepositories` (inclus dans `@SpringBootApplication`).

## 📦 Structure des Fichiers

```
parent/
├── ressources-jpa/
│   └── document-jpa/
│       ├── src/main/java/cmr/notep/
│       │   ├── dao/
│       │   │   ├── RessourcesEntity.java
│       │   │   ├── CaissesEntity.java
│       │   │   ├── MouvementSoldeRessourcesEntity.java
│       │   │   └── MouvementSoldeCaissesEntity.java
│       │   └── repository/
│       │       ├── RessourcesRepository.java
│       │       ├── CaissesRepository.java
│       │       ├── MouvementSoldeRessourcesRepository.java
│       │       └── MouvementSoldeCaissesRepository.java
│       └── src/test/resources/
│           └── schema-pg.sql
│           └── migration-transactions-soldes.sql
│
└── composant-business/
    ├── document-interface/
    │   └── src/main/java/cmr/notep/
    │       ├── modele/
    │       │   ├── Ressources.java
    │       │   ├── Caisses.java
    │       │   ├── MouvementSoldeRessource.java
    │       │   └── MouvementSoldeCaisse.java
    │       └── api/
    │           ├── IRessourcesTransactionApi.java
    │           └── ICaissesTransactionApi.java
    │
    └── document-service/
        ├── src/main/java/cmr/notep/
        │   ├── business/
        │   │   ├── SoldeRessourcesTransactionBusiness.java
        │   │   └── SoldeCaissesTransactionBusiness.java
        │   ├── impl/
        │   │   ├── RessourcesTransactionController.java
        │   │   └── CaissesTransactionController.java
        │   └── config/
        │       └── DocumentConfig.java
        └── src/test/java/cmr/notep/business/
            └── SoldeRessourcesTransactionBusinessTest.java
```

## ✅ Checklist de Vérification

- [ ] Dépendances Maven mises à jour
- [ ] Base de données PostgreSQL configurée
- [ ] Schéma `document` créé
- [ ] Tables migrées avec `schema-pg.sql`
- [ ] Tables de transaction migrées avec `migration-transactions-soldes.sql`
- [ ] `application.properties` ou `application.yml` configuré
- [ ] Services métier enregistrés comme composants Spring
- [ ] Repositories déclarés et accessibles
- [ ] Contrôleurs décorés avec `@RestController`
- [ ] Tests unitaires passent
- [ ] Application démarre sans erreurs

## 🧪 Test de Démarrage

1. **Compiler le projet**
   ```bash
   mvn clean compile
   ```

2. **Exécuter les tests**
   ```bash
   mvn test
   ```

3. **Lancer l'application**
   ```bash
   mvn spring-boot:run
   ```

4. **Vérifier la santé**
   ```bash
   curl http://localhost:8080/actuator/health
   ```

## 🔍 Vérification des Endpoints

Une fois l'application démarrée, tester les endpoints :

```bash
# Lister les mouvements en attente
curl -X GET http://localhost:8080/ressources/transactions/resource-001/en-attente

# Enregistrer un mouvement
curl -X POST http://localhost:8080/ressources/transactions/resource-001/mouvement \
  -H "Content-Type: application/json" \
  -d '{...}'
```

## 🐛 Dépannage

### Erreur: Table non trouvée
```
org.postgresql.util.PSQLException: ERROR: relation "document.ressources" does not exist
```
**Solution:** Exécuter `schema-pg.sql` pour créer les tables principales.

### Erreur: UUID extension manquante
```
org.postgresql.util.PSQLException: ERROR: function gen_random_uuid() does not exist
```
**Solution:** Activer l'extension UUID dans PostgreSQL :
```sql
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
```

### Erreur: DaoAccessorService non trouvé
```
No qualifying bean of type 'cmr.notep.dao.DaoAccessorService' available
```
**Solution:** Vérifier que `DaoAccessorService` est enregistré comme un Bean Spring (avec `@Component` ou `@Service`).

### Erreur: Dozer Mapper non configuré
```
java.lang.NullPointerException: Cannot invoke method on null object
```
**Solution:** Créer une configuration Dozer Bean ou vérifier l'initialisation du mapper.

## 📊 Monitoring

Pour monitorer les transactions, accéder aux vues SQL :

```sql
-- Transactions en attente pour une ressource
SELECT * FROM document.vw_ressources_mouvements_attente;

-- Transactions en attente pour une caisse
SELECT * FROM document.vw_caisses_mouvements_attente;

-- Statistiques par statut
SELECT * FROM document.vw_mouvements_ressources_statuts;
SELECT * FROM document.vw_mouvements_caisses_statuts;
```

## 🔐 Sécurité

1. **Authentification** : Ajouter Spring Security pour protéger les endpoints
2. **Audit** : Les mouvements conservent `createdBy` et `valideeBy`
3. **Validation** : Les mouvements sont validés avant d'être appliqués
4. **Transactions** : Utiliser `@Transactional` pour l'intégrité

## 📚 Ressources Supplémentaires

- Documentation API : `API_ENDPOINTS.md`
- Guide de Transaction : `GUIDE_TRANSACTIONS_SOLDES.md`
- Résumé des Modifications : `RESUME_MODIFICATIONS.md`
- Schéma SQL : `schema-pg.sql`
- Migration Transactions : `migration-transactions-soldes.sql`
