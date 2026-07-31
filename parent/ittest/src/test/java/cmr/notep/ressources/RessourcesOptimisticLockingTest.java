package cmr.notep.ressources;

import cmr.notep.commun.AbstractIttest;
import cmr.notep.config.ItTestConfig;
import cmr.notep.modele.Ressources;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Vérifie que la gestion de version (@Version) sur RessourcesEntity empêche
 * la mise à jour d'une occurrence avec une version obsolète (verrouillage optimiste).
 */
@SpringBootTest(classes = {ItTestConfig.class})
@Transactional()
@Slf4j
public class RessourcesOptimisticLockingTest extends AbstractIttest {

    @PersistenceContext
    EntityManager entityManager;

    /**
     * Scénario :
     * 1. Créer une ressource (version initiale = 0).
     * 2. Simuler 2 lectures concurrentes de la même ressource (même version).
     * 3. Le "premier utilisateur" sauvegarde sa modification -> la version est incrémentée en base.
     * 4. Le "second utilisateur" tente de sauvegarder avec sa version désormais obsolète
     *    -> JPA/Hibernate doit lever une exception de verrouillage optimiste.
     */
    @Test
    @Order(1)
    @SneakyThrows
    public void testModificationAvecMauvaiseVersionLeveException() {
        // 1. Création
        Ressources created = ressourceService.posterRessources(Ressources.builder()
                .libelle("Ressource Versionnee")
                .description("Ressource pour test de verrouillage optimiste")
                .etat(true)
                .quantite(30)
                .seuilAlerte(3)
                .prixEntree(2.0)
                .prixSortie(4.0)
                .unite("PCS")
                .dateCreation(new Date())
                .build());
        entityManager.flush();

        Assertions.assertNotNull(created.getVersion(), "La version doit être initialisée après création");

        // 2. Deux "utilisateurs" lisent la même ressource, avec la même version
        Ressources utilisateur1 = ressourceService.avoirRessource(created.getId());
        Ressources utilisateur2 = ressourceService.avoirRessource(created.getId());
        Assertions.assertEquals(utilisateur1.getVersion(), utilisateur2.getVersion(),
                "Les deux lectures doivent avoir la même version au départ");

        // 3. Utilisateur 1 modifie et sauvegarde en premier -> la version est incrémentée
        utilisateur1.setLibelle("Modifié par utilisateur 1");
        ressourceService.posterRessources(utilisateur1);
        entityManager.flush();

        // 4. Utilisateur 2 tente de sauvegarder avec la version désormais obsolète
        utilisateur2.setLibelle("Modifié par utilisateur 2 - version obsolete");

        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> {
            ressourceService.posterRessources(utilisateur2);
            entityManager.flush();
        }, "Une exception de verrouillage optimiste doit être levée pour une version obsolète");

        boolean estUneExceptionDeVerrouillageOptimiste =
                exception instanceof jakarta.persistence.OptimisticLockException
                || exception instanceof org.springframework.orm.ObjectOptimisticLockingFailureException
                || (exception.getCause() != null
                        && (exception.getCause() instanceof jakarta.persistence.OptimisticLockException
                            || exception.getCause().getClass().getSimpleName().toLowerCase().contains("optimisticlock")));

        Assertions.assertTrue(estUneExceptionDeVerrouillageOptimiste,
                "L'exception levée doit être liée à un conflit de version optimiste, obtenu : "
                        + exception.getClass().getName());

        log.info("✓ Test réussi : la mise à jour avec une version obsolète lève bien une exception de verrouillage optimiste");
    }
}

