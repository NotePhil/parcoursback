package cmr.notep.caisses;

import cmr.notep.api.ICaissesApi;
import cmr.notep.commun.AbstractIttest;
import cmr.notep.config.ItTestConfig;
import cmr.notep.modele.Caisses;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

/**
 * Verifie que @Version sur CaissesEntity bloque les mises a jour avec version obsolete.
 */
@SpringBootTest(classes = {ItTestConfig.class})
@Transactional
@Slf4j
public class CaissesOptimisticLockingTest extends AbstractIttest {

    @Autowired
    private ICaissesApi caisseService;

    @PersistenceContext
    EntityManager entityManager;

    @Test
    @Order(1)
    @SneakyThrows
    public void testModificationAvecMauvaiseVersionLeveException() {
        Caisses created = caisseService.posterCaisse(Caisses.builder()
                .libelle("Caisse versionnee")
                .type("CAISSE")
                .solde(3000.0)
                .etat(true)
                .detailsJson(null)
                .build());
        entityManager.flush();

        Assertions.assertNotNull(created.getVersion(), "La version doit etre initialisee apres creation");

        Caisses utilisateur1 = caisseService.avoirCaisse(created.getId());
        Caisses utilisateur2 = caisseService.avoirCaisse(created.getId());
        Assertions.assertEquals(utilisateur1.getVersion(), utilisateur2.getVersion(),
                "Les deux lectures doivent avoir la meme version");

        utilisateur1.setLibelle("Modifie par utilisateur 1");
        caisseService.posterCaisse(utilisateur1);
        entityManager.flush();

        utilisateur2.setLibelle("Modifie par utilisateur 2 - version obsolete");

        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> {
            caisseService.posterCaisse(utilisateur2);
            entityManager.flush();
        }, "Une exception de verrouillage optimiste doit etre levee");

        boolean estUneExceptionDeVerrouillageOptimiste =
                exception instanceof jakarta.persistence.OptimisticLockException
                        || exception instanceof org.springframework.orm.ObjectOptimisticLockingFailureException
                        || (exception.getCause() != null
                        && (exception.getCause() instanceof jakarta.persistence.OptimisticLockException
                        || exception.getCause().getClass().getSimpleName().toLowerCase().contains("optimisticlock")));

        Assertions.assertTrue(estUneExceptionDeVerrouillageOptimiste,
                "L'exception levee doit etre liee a un conflit de version optimiste, obtenu : "
                        + exception.getClass().getName());

        log.info("Test reussi : la mise a jour avec version obsolete leve bien une exception de verrouillage optimiste");
    }
}
