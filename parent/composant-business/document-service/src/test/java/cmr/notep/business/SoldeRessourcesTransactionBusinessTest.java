package cmr.notep.business;

import cmr.notep.dao.DaoAccessorService;
import cmr.notep.dao.MouvementSoldeRessourcesEntity;
import cmr.notep.dao.RessourcesEntity;
import cmr.notep.modele.MouvementSoldeRessource;
import cmr.notep.repository.MouvementSoldeRessourcesRepository;
import cmr.notep.repository.RessourcesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.dozer.DozerBeanMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@DisplayName("Tests - SoldeRessourcesTransactionBusiness")
public class SoldeRessourcesTransactionBusinessTest {

    private SoldeRessourcesTransactionBusiness soldeRessourcesTransactionBusiness;

    @Mock
    private DaoAccessorService daoAccessorService;

    @Mock
    private RessourcesRepository ressourcesRepository;

    @Mock
    private MouvementSoldeRessourcesRepository mouvementRepository;

    @Mock
    private DozerBeanMapper dozerMapperBean;

    private RessourcesEntity ressource;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        soldeRessourcesTransactionBusiness = new SoldeRessourcesTransactionBusiness(daoAccessorService,dozerMapperBean);

        // Configuration de la ressource de test
        ressource = new RessourcesEntity();
        ressource.setId("ressource-001");
        ressource.setLibelle("Ressource Test");
        ressource.setQuantite(100);
        ressource.setEtat(true);

        // Mock de mapping générique pour éviter les null lors des assertions
        when(dozerMapperBean.map(any(MouvementSoldeRessourcesEntity.class), eq(MouvementSoldeRessource.class)))
                .thenAnswer(invocation -> {
                    MouvementSoldeRessourcesEntity src = invocation.getArgument(0);
                    MouvementSoldeRessource dst = new MouvementSoldeRessource();
                    dst.setId(src.getId());
                    dst.setStatut(src.getStatut());
                    dst.setValidee(src.getValidee());
                    dst.setValideeBy(src.getValideeBy());
                    dst.setQuantiteInitiale(src.getQuantiteInitiale());
                    dst.setQuantiteMouvementee(src.getQuantiteMouvementee());
                    dst.setQuantiteFinale(src.getQuantiteFinale());
                    return dst;
                });
    }

    @Test
    @DisplayName("Enregistrer un mouvement de sortie valide")
    void testEnregistrerMouvementSortieValide() {
        // Setup
        when(daoAccessorService.getRepository(RessourcesRepository.class))
                .thenReturn(ressourcesRepository);
        when(ressourcesRepository.findById("ressource-001"))
                .thenReturn(Optional.of(ressource));

        MouvementSoldeRessourcesEntity mouvementSauvegarde = new MouvementSoldeRessourcesEntity();
        mouvementSauvegarde.setId("mouvement-001");
        mouvementSauvegarde.setStatut("EN_ATTENTE");

        when(daoAccessorService.getRepository(MouvementSoldeRessourcesRepository.class))
                .thenReturn(mouvementRepository);
        when(mouvementRepository.save(any(MouvementSoldeRessourcesEntity.class)))
                .thenReturn(mouvementSauvegarde);

        // Execution
        MouvementSoldeRessource mouvement = soldeRessourcesTransactionBusiness.enregistrerMouvement(
                "ressource-001",
                30,
                "SORTIE",
                "Test sortie",
                "Vente client",
                "VENTE-001",
                "user@test.com"
        );

        // Assertions
        assertNotNull(mouvement);
        assertEquals("mouvement-001", mouvement.getId());
        assertEquals("EN_ATTENTE", mouvement.getStatut());
        assertFalse(mouvement.getValidee());
    }

    @Test
    @DisplayName("Enregistrer une sortie avec quantité insuffisante - Exception")
    void testEnregistrerSortieQuantiteInsuffisante() {
        // Setup
        when(daoAccessorService.getRepository(RessourcesRepository.class))
                .thenReturn(ressourcesRepository);
        when(ressourcesRepository.findById("ressource-001"))
                .thenReturn(Optional.of(ressource));

        // Execution & Assertion
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            soldeRessourcesTransactionBusiness.enregistrerMouvement(
                    "ressource-001",
                    150, // Plus que la quantité disponible
                    "SORTIE",
                    "Test sortie",
                    "Vente client",
                    "VENTE-001",
                    "user@test.com"
            );
        });

        assertTrue(exception.getMessage().contains("Quantité insuffisante"));
    }

    @Test
    @DisplayName("Enregistrer une entrée augmente la quantité finale")
    void testEnregistrerEntreeAugmenteQuantite() {
        // Setup
        when(daoAccessorService.getRepository(RessourcesRepository.class))
                .thenReturn(ressourcesRepository);
        when(ressourcesRepository.findById("ressource-001"))
                .thenReturn(Optional.of(ressource));

        MouvementSoldeRessourcesEntity mouvementSauvegarde = new MouvementSoldeRessourcesEntity();
        mouvementSauvegarde.setId("mouvement-001");
        mouvementSauvegarde.setQuantiteInitiale(100);
        mouvementSauvegarde.setQuantiteMouvementee(50);
        mouvementSauvegarde.setQuantiteFinale(150);

        when(daoAccessorService.getRepository(MouvementSoldeRessourcesRepository.class))
                .thenReturn(mouvementRepository);
        when(mouvementRepository.save(any(MouvementSoldeRessourcesEntity.class)))
                .thenReturn(mouvementSauvegarde);

        // Execution
        MouvementSoldeRessource mouvement = soldeRessourcesTransactionBusiness.enregistrerMouvement(
                "ressource-001",
                50,
                "ENTREE",
                "Réception fournisseur",
                "Achat",
                "ACHAT-001",
                "user@test.com"
        );

        // Assertions
        assertNotNull(mouvement);
        assertEquals(100, mouvement.getQuantiteInitiale());
        assertEquals(150, mouvement.getQuantiteFinale());
    }

    @Test
    @DisplayName("Valider un mouvement en attente")
    void testValiderMouvementEnAttente() {
        // Setup
        MouvementSoldeRessourcesEntity mouvementEnAttente = new MouvementSoldeRessourcesEntity();
        mouvementEnAttente.setId("mouvement-001");
        mouvementEnAttente.setRessourcesEntity(ressource);
        mouvementEnAttente.setQuantiteFinale(70);
        mouvementEnAttente.setStatut("EN_ATTENTE");
        mouvementEnAttente.setValidee(false);

        MouvementSoldeRessourcesEntity mouvementValide = new MouvementSoldeRessourcesEntity();
        mouvementValide.setId("mouvement-001");
        mouvementValide.setStatut("VALIDEE");
        mouvementValide.setValidee(true);
        mouvementValide.setValideeBy("manager@test.com");

        when(daoAccessorService.getRepository(MouvementSoldeRessourcesRepository.class))
                .thenReturn(mouvementRepository);
        when(mouvementRepository.findById("mouvement-001"))
                .thenReturn(Optional.of(mouvementEnAttente));
        when(mouvementRepository.save(any(MouvementSoldeRessourcesEntity.class)))
                .thenReturn(mouvementValide);

        when(daoAccessorService.getRepository(RessourcesRepository.class))
                .thenReturn(ressourcesRepository);
        when(ressourcesRepository.save(any(RessourcesEntity.class)))
                .thenReturn(ressource);

        // Execution
        MouvementSoldeRessource resultat = soldeRessourcesTransactionBusiness.validerMouvement(
                "mouvement-001",
                "manager@test.com"
        );

        // Assertions
        assertNotNull(resultat);
        assertTrue(resultat.getValidee());
        assertEquals("VALIDEE", resultat.getStatut());
        assertEquals("manager@test.com", resultat.getValideeBy());
    }

    @Test
    @DisplayName("Ne pas pouvoir valider deux fois un mouvement")
    void testValiderDeuxFoisMouvement() {
        // Setup
        MouvementSoldeRessourcesEntity mouvementDejaValide = new MouvementSoldeRessourcesEntity();
        mouvementDejaValide.setId("mouvement-001");
        mouvementDejaValide.setStatut("VALIDEE");
        mouvementDejaValide.setValidee(true);

        when(daoAccessorService.getRepository(MouvementSoldeRessourcesRepository.class))
                .thenReturn(mouvementRepository);
        when(mouvementRepository.findById("mouvement-001"))
                .thenReturn(Optional.of(mouvementDejaValide));

        // Execution & Assertion
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            soldeRessourcesTransactionBusiness.validerMouvement(
                    "mouvement-001",
                    "manager@test.com"
            );
        });

        assertTrue(exception.getMessage().contains("déjà été validé"));
    }

    @Test
    @DisplayName("Rejeter un mouvement en attente")
    void testRejeterMouvementEnAttente() {
        // Setup
        MouvementSoldeRessourcesEntity mouvementEnAttente = new MouvementSoldeRessourcesEntity();
        mouvementEnAttente.setId("mouvement-001");
        mouvementEnAttente.setStatut("EN_ATTENTE");
        mouvementEnAttente.setValidee(false);
        mouvementEnAttente.setDescription("Test");

        MouvementSoldeRessourcesEntity mouvementRejete = new MouvementSoldeRessourcesEntity();
        mouvementRejete.setId("mouvement-001");
        mouvementRejete.setStatut("REJETEE");

        when(daoAccessorService.getRepository(MouvementSoldeRessourcesRepository.class))
                .thenReturn(mouvementRepository);
        when(mouvementRepository.findById("mouvement-001"))
                .thenReturn(Optional.of(mouvementEnAttente));
        when(mouvementRepository.save(any(MouvementSoldeRessourcesEntity.class)))
                .thenReturn(mouvementRejete);

        // Execution
        soldeRessourcesTransactionBusiness.rejeterMouvement(
                "mouvement-001",
                "Erreur saisie"
        );

        // Assertions
        verify(mouvementRepository, times(1)).save(any(MouvementSoldeRessourcesEntity.class));
    }

    @Test
    @DisplayName("Ressource introuvable - Exception")
    void testRessourceIntrouvable() {
        // Setup
        when(daoAccessorService.getRepository(RessourcesRepository.class))
                .thenReturn(ressourcesRepository);
        when(ressourcesRepository.findById("inexistant"))
                .thenReturn(Optional.empty());

        // Execution & Assertion
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            soldeRessourcesTransactionBusiness.enregistrerMouvement(
                    "inexistant",
                    10,
                    "SORTIE",
                    "Test",
                    "Test",
                    "TEST",
                    "user@test.com"
            );
        });

        assertTrue(exception.getMessage().contains("Ressource inexistante"));
    }
}
