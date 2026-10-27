package cmr.notep.business;

import cmr.notep.dao.CaissesReellesEntity;
import cmr.notep.dao.DaoAccessorService;
import cmr.notep.dao.MouvementSoldeCaissesEntity;
import cmr.notep.exemplaire.repository.CaissesReellesRepository;
import cmr.notep.exemplaire.repository.MouvementSoldeCaissesRepository;
import cmr.notep.modele.MouvementSoldeCaisses;
import cmr.notep.modele.TypeMouvement;
import org.dozer.DozerBeanMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DisplayName("Tests - SoldeCaissesTransactionBusiness")
public class SoldeCaissesTransactionBusinessTest {

    private SoldeCaissesTransactionBusiness soldeCaissesTransactionBusiness;

    @Mock
    private DaoAccessorService daoAccessorService;

    @Mock
    private CaissesReellesRepository caissesRepository;

    @Mock
    private MouvementSoldeCaissesRepository mouvementRepository;

    @Mock
    private DozerBeanMapper dozerMapperBean;

    private CaissesReellesEntity caisse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        soldeCaissesTransactionBusiness = new SoldeCaissesTransactionBusiness(daoAccessorService, dozerMapperBean);

        caisse = new CaissesReellesEntity();
        caisse.setId("caisse-001");
        caisse.setSolde(250.0);
        caisse.setCaisseId("CAISSE-TEST");

        when(dozerMapperBean.map(any(MouvementSoldeCaissesEntity.class), eq(MouvementSoldeCaisses.class)))
                .thenAnswer(invocation -> {
                    MouvementSoldeCaissesEntity src = invocation.getArgument(0);
                    MouvementSoldeCaisses dst = new MouvementSoldeCaisses();
                    dst.setId(src.getId());
                    dst.setSoldeInitiale(src.getSoldeInitiale());
                    dst.setSoldeMovementee(src.getSoldeMovementee());
                    dst.setSoldeFinale(src.getSoldeFinale());
                    dst.setTypeTransaction(src.getTypeTransaction());
                    dst.setDescription(src.getDescription());
                    dst.setMotif(src.getMotif());
                    dst.setCodeReference(src.getCodeReference());
                    dst.setCreatedBy(src.getCreatedBy());
                    dst.setValidee(src.getValidee());
                    dst.setValideeBy(src.getValideeBy());
                    dst.setStatut(src.getStatut());
                    return dst;
                });
    }

    @Test
    @DisplayName("Enregistrer un mouvement de caisse valide")
    void testEnregistrerMouvementCaisseValide() {
        when(daoAccessorService.getRepository(CaissesReellesRepository.class))
                .thenReturn(caissesRepository);
        when(caissesRepository.findById("caisse-001"))
                .thenReturn(Optional.of(caisse));

        MouvementSoldeCaissesEntity mouvementSauvegarde = new MouvementSoldeCaissesEntity();
        mouvementSauvegarde.setId("mouvement-caisse-001");
        mouvementSauvegarde.setSoldeInitiale(250.0);
        mouvementSauvegarde.setSoldeMovementee(50.0);
        mouvementSauvegarde.setSoldeFinale(300.0);
        mouvementSauvegarde.setTypeTransaction(TypeMouvement.Ajout.name());
        mouvementSauvegarde.setStatut("VALIDEE");
        mouvementSauvegarde.setValidee(true);

        when(daoAccessorService.getRepository(MouvementSoldeCaissesRepository.class))
                .thenReturn(mouvementRepository);
        when(mouvementRepository.save(any(MouvementSoldeCaissesEntity.class)))
                .thenReturn(mouvementSauvegarde);

        MouvementSoldeCaisses mouvement = soldeCaissesTransactionBusiness.enregistrerMouvement(
                "caisse-001",
                50.0,
                TypeMouvement.Ajout.name(),
                "Réapprovisionnement",
                "Apport de caisse",
                "CAISSE-REF-001",
                "user@test.com"
        );

        assertNotNull(mouvement);
        assertEquals("mouvement-caisse-001", mouvement.getId());
        assertEquals(250.0, mouvement.getSoldeInitiale());
        assertEquals(300.0, mouvement.getSoldeFinale());
        assertEquals("VALIDEE", mouvement.getStatut());
        assertTrue(mouvement.getValidee());
    }

    @Test
    @DisplayName("Enregistrer un débit avec solde insuffisant - Exception")
    void testEnregistrerDebitSoldeInsuffisant() {
        when(daoAccessorService.getRepository(CaissesReellesRepository.class))
                .thenReturn(caissesRepository);
        when(caissesRepository.findById("caisse-001"))
                .thenReturn(Optional.of(caisse));

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                soldeCaissesTransactionBusiness.enregistrerMouvement(
                        "caisse-001",
                        500.0,
                        TypeMouvement.Reduire.name(),
                        "Paiement",
                        "Sortie de caisse",
                        "CAISSE-REF-INSUFF",
                        "user@test.com"
                ));

        assertTrue(exception.getMessage().contains("Solde insuffisant"));
        verify(mouvementRepository, never()).save(any(MouvementSoldeCaissesEntity.class));
    }
}


