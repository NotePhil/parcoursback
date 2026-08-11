package cmr.notep.caisses;

import cmr.notep.api.ICaissesApi;
import cmr.notep.commun.AbstractIttest;
import cmr.notep.config.ItTestConfig;
import cmr.notep.modele.Caisses;
import cmr.notep.modele.DetailsJson;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

/**
 * Verifie que posterCaisse met a jour uniquement les champs autorises
 * et preserve les champs metier sensibles.
 */
@SpringBootTest(classes = {ItTestConfig.class})
@Transactional
@Slf4j
public class CaissesControlledUpdateTest extends AbstractIttest {

    @Autowired
    private ICaissesApi caisseService;

    @Test
    @Order(1)
    @SneakyThrows
    public void testPosterCaisseMetAJourLibelleEtatSansModifierChampsSensibles() {
        DetailsJson detailsInitiaux = new DetailsJson();
        detailsInitiaux.setX10000(4);
        detailsInitiaux.setX5000(2);

        Caisses created = caisseService.posterCaisse(Caisses.builder()
                .libelle("Caisse integration")
                .type("CAISSE")
                .solde(1500.75)
                .etat(true)
                .detailsJson(detailsInitiaux)
                .build());

        Assertions.assertNotNull(created.getId(), "La caisse doit etre creee");
        Assertions.assertNotNull(created.getVersion(), "La version doit etre initialisee");

        Caisses tentativeUpdate = Caisses.builder()
                .id(created.getId())
                .version(created.getVersion())
                .libelle("Caisse modifiee")
                .etat(false)
                .type("TYPE_INTERDIT")
                .solde(0.0)
                .detailsJson(null)
                .build();

        Caisses updated = caisseService.posterCaisse(tentativeUpdate);

        Assertions.assertEquals("Caisse modifiee", updated.getLibelle(), "Le libelle doit etre modifie");
        Assertions.assertFalse(updated.isEtat(), "L'etat doit etre modifie");
        Assertions.assertEquals(created.getType(), updated.getType(), "Le type doit etre preserve");
        Assertions.assertEquals(created.getSolde(), updated.getSolde(), 0.0001, "Le solde doit etre preserve");
        Assertions.assertNotNull(updated.getDetailsJson(), "detailsJson doit etre preserve meme si null est envoye");
        Assertions.assertEquals(created.getDetailsJson().getX10000(), updated.getDetailsJson().getX10000(),
                "detailsJson doit etre preserve");

        Caisses relue = caisseService.avoirCaisse(updated.getId());
        Assertions.assertEquals(created.getType(), relue.getType(), "Le type en base doit rester identique");
        Assertions.assertEquals(created.getSolde(), relue.getSolde(), 0.0001, "Le solde en base doit rester identique");
        Assertions.assertNotNull(relue.getDetailsJson(), "detailsJson en base doit rester non null");

        log.info("Test reussi : posterCaisse preserve type/solde/detailsJson et met a jour libelle/etat");
    }
}
