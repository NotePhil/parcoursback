package cmr.notep.business;

import cmr.notep.dao.DaoAccessorService;
import cmr.notep.dao.MouvementsEntity;
import cmr.notep.dao.RessourcesReellesEntity;
import cmr.notep.exemplaire.repository.MouvementRepository;
import cmr.notep.exemplaire.repository.RessourcesReellesRepository;
import cmr.notep.modele.Mouvements;
import org.dozer.DozerBeanMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("Tests - SoldeRessourcesTransactionBusiness")
public class SoldeRessourcesTransactionBusinessTest {

    private SoldeRessourcesTransactionBusiness soldeRessourcesTransactionBusiness;

    @Mock
    private DaoAccessorService daoAccessorService;

    @Mock
    private RessourcesReellesRepository ressourcesRepository;

    @Mock
    private MouvementRepository mouvementRepository;

    @Mock
    private DozerBeanMapper dozerMapperBean;

    private RessourcesReellesEntity ressource;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        soldeRessourcesTransactionBusiness = new SoldeRessourcesTransactionBusiness(daoAccessorService, dozerMapperBean);

        ressource = new RessourcesReellesEntity();
        ressource.setId("ressource-001");
        ressource.setQuantite(100);

        when(dozerMapperBean.map(any(MouvementsEntity.class), org.mockito.ArgumentMatchers.eq(Mouvements.class)))
                .thenAnswer(invocation -> {
                    MouvementsEntity src = invocation.getArgument(0);
                    Mouvements dst = new Mouvements();
                    dst.setId(src.getId());
                    dst.setQuantiteInitiale(src.getQuantiteInitiale());
                    dst.setQuantiteMouvementee(src.getQuantiteMouvementee());
                    dst.setQuantiteFinale(src.getQuantiteFinale());
                    dst.setValidee(src.getValidee());
                    dst.setValideeBy(src.getValideeBy());
                    dst.setStatut(src.getStatut());
                    return dst;
                });
    }

    @Test
    @DisplayName("Enregistrer un mouvement de ressource valide")
    void testEnregistrerMouvementRessourceValide() {
        when(daoAccessorService.getRepository(RessourcesReellesRepository.class)).thenReturn(ressourcesRepository);
        when(ressourcesRepository.findById("ressource-001")).thenReturn(Optional.of(ressource));

        MouvementsEntity mouvementSauvegarde = new MouvementsEntity();
        mouvementSauvegarde.setId("mouvement-001");
        mouvementSauvegarde.setQuantiteInitiale(100);
        mouvementSauvegarde.setQuantiteMouvementee(30);
        mouvementSauvegarde.setQuantiteFinale(70);
        mouvementSauvegarde.setStatut("EN_ATTENTE");
        mouvementSauvegarde.setValidee(false);

        when(daoAccessorService.getRepository(MouvementRepository.class)).thenReturn(mouvementRepository);
        when(mouvementRepository.save(any(MouvementsEntity.class))).thenReturn(mouvementSauvegarde);

        Mouvements mouvement = soldeRessourcesTransactionBusiness.enregistrerMouvement(
                "ressource-001",
                30,
                "Reduire",
                "Test sortie",
                "Vente client",
                "VENTE-001",
                "user@test.com"
        );

        assertNotNull(mouvement);
        assertEquals("mouvement-001", mouvement.getId());
        assertEquals(100, mouvement.getQuantiteInitiale());
        assertEquals(70, mouvement.getQuantiteFinale());
        assertEquals("EN_ATTENTE", mouvement.getStatut());
    }

    @Test
    @DisplayName("Enregistrer une sortie avec quantité insuffisante - Exception")
    void testEnregistrerSortieQuantiteInsuffisante() {
        when(daoAccessorService.getRepository(RessourcesReellesRepository.class)).thenReturn(ressourcesRepository);
        when(ressourcesRepository.findById("ressource-001")).thenReturn(Optional.of(ressource));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> soldeRessourcesTransactionBusiness.enregistrerMouvement(
                "ressource-001",
                150,
                "Reduire",
                "Test sortie",
                "Vente client",
                "VENTE-001",
                "user@test.com"
        ));

        assertEquals("Quantité insuffisante. Disponible: 100, Demandée: 150", exception.getMessage());
        verify(mouvementRepository, never()).save(any(MouvementsEntity.class));
    }
}


