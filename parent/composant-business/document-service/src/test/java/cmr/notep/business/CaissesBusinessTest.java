package cmr.notep.business;

import cmr.notep.dao.CaissesEntity;
import cmr.notep.dao.DaoAccessorService;
import cmr.notep.exceptions.ParcoursException;
import cmr.notep.exceptions.enumeration.ParcoursExceptionCodeEnum;
import cmr.notep.modele.Caisses;
import cmr.notep.modele.DetailsJson;
import cmr.notep.repository.CaissesRepository;
import org.dozer.DozerBeanMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@DisplayName("Tests - CaissesBusiness")
class CaissesBusinessTest {

    private CaissesBusiness caissesBusiness;

    @Mock
    private DaoAccessorService daoAccessorService;

    @Mock
    private DozerBeanMapper dozerMapperBean;

    @Mock
    private CaissesRepository caissesRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        caissesBusiness = new CaissesBusiness(daoAccessorService, dozerMapperBean);

        when(daoAccessorService.getRepository(CaissesRepository.class)).thenReturn(caissesRepository);
        when(caissesRepository.save(any(CaissesEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        when(dozerMapperBean.map(any(Caisses.class), eq(CaissesEntity.class)))
                .thenAnswer(invocation -> {
                    Caisses src = invocation.getArgument(0);
                    CaissesEntity entity = new CaissesEntity();
                    entity.setId(src.getId());
                    entity.setVersion(src.getVersion());
                    entity.setLibelle(src.getLibelle());
                    entity.setType(src.getType());
                    entity.setSolde(src.getSolde());
                    entity.setEtat(src.isEtat());
                    entity.setDetailsJson(src.getDetailsJson());
                    return entity;
                });

        when(dozerMapperBean.map(any(CaissesEntity.class), eq(Caisses.class)))
                .thenAnswer(invocation -> {
                    CaissesEntity src = invocation.getArgument(0);
                    return Caisses.builder()
                            .id(src.getId())
                            .version(src.getVersion())
                            .libelle(src.getLibelle())
                            .type(src.getType())
                            .solde(src.getSolde())
                            .etat(src.isEtat())
                            .detailsJson(src.getDetailsJson())
                            .build();
                });
    }

    @Test
    @DisplayName("detailsJson present et type non cash -> erreur INVALID_INPUT")
    void testDetailsJsonPresentTypeNonCashDoitEchouer() {
        DetailsJson detailsJson = new DetailsJson();
        detailsJson.setX10000(1);

        Caisses caisse = Caisses.builder()
                .libelle("Caisse A")
                .type("BANQUE")
                .solde(10000)
                .etat(true)
                .detailsJson(detailsJson)
                .build();

        ParcoursException exception = assertThrows(ParcoursException.class,
                () -> caissesBusiness.posterCaisse(caisse));

        assertEquals(ParcoursExceptionCodeEnum.INVALID_INPUT, exception.getCode());
        assertTrue(exception.getMessage().contains("detailsJson"));
        verify(caissesRepository, never()).save(any(CaissesEntity.class));
    }

    @Test
    @DisplayName("detailsJson present, type cash, solde incoherent -> erreur INVALID_INPUT")
    void testCashAvecDetailsEtSoldeIncoherentDoitEchouer() {
        DetailsJson detailsJson = new DetailsJson();
        detailsJson.setX10000(2);
        detailsJson.setX500B(1); // total attendu: 20500

        Caisses caisse = Caisses.builder()
                .libelle("Caisse B")
                .type("cash")
                .solde(20000)
                .etat(true)
                .detailsJson(detailsJson)
                .build();

        ParcoursException exception = assertThrows(ParcoursException.class,
                () -> caissesBusiness.posterCaisse(caisse));

        assertEquals(ParcoursExceptionCodeEnum.INVALID_INPUT, exception.getCode());
        assertTrue(exception.getMessage().contains("Solde incoherent"));
        verify(caissesRepository, never()).save(any(CaissesEntity.class));
    }

    @Test
    @DisplayName("detailsJson present, type cash, solde coherent -> creation OK")
    void testCashAvecDetailsEtSoldeCoherentDoitPasser() throws ParcoursException {
        DetailsJson detailsJson = new DetailsJson();
        detailsJson.setX10000(2);
        detailsJson.setX500B(1); // 20500

        Caisses caisse = Caisses.builder()
                .libelle("Caisse C")
                .type("cash")
                .solde(20500)
                .etat(true)
                .detailsJson(detailsJson)
                .build();

        Caisses resultat = caissesBusiness.posterCaisse(caisse);

        assertNotNull(resultat);
        assertEquals("cash", resultat.getType());
        assertEquals(20500, resultat.getSolde(), 0.0001);
        verify(caissesRepository, times(1)).save(any(CaissesEntity.class));
    }

    @Test
    @DisplayName("type cash et solde 0 autorise avec detailsJson null")
    void testCashSoldeZeroAvecDetailsNullAutorise() throws ParcoursException {
        Caisses caisse = Caisses.builder()
                .libelle("Caisse D")
                .type("cash")
                .solde(0)
                .etat(true)
                .detailsJson(null)
                .build();

        Caisses resultat = caissesBusiness.posterCaisse(caisse);

        assertNotNull(resultat);
        assertEquals(0, resultat.getSolde(), 0.0001);
        verify(caissesRepository, times(1)).save(any(CaissesEntity.class));
    }

    @Test
    @DisplayName("type cash et solde 0 autorise avec detailsJson somme 0")
    void testCashSoldeZeroAvecDetailsSommeZeroAutorise() throws ParcoursException {
        DetailsJson detailsJson = new DetailsJson(); // toutes les valeurs a 0

        Caisses caisse = Caisses.builder()
                .libelle("Caisse E")
                .type("cash")
                .solde(0)
                .etat(true)
                .detailsJson(detailsJson)
                .build();

        Caisses resultat = caissesBusiness.posterCaisse(caisse);

        assertNotNull(resultat);
        assertEquals(0, resultat.getSolde(), 0.0001);
        verify(caissesRepository, times(1)).save(any(CaissesEntity.class));
    }

    @Test
    @DisplayName("creation d'une caisse non cash sans detailsJson -> creation OK")
    void testCreationCaisseNonCashSansDetailsJsonDoitPasser() throws ParcoursException {
        Caisses caisse = Caisses.builder()
                .libelle("Caisse non cash")
                .type("BANQUE")
                .solde(120000)
                .etat(true)
                .detailsJson(null)
                .build();

        Caisses resultat = caissesBusiness.posterCaisse(caisse);

        assertNotNull(resultat);
        assertEquals("BANQUE", resultat.getType());
        assertEquals(120000, resultat.getSolde(), 0.0001);
        verify(caissesRepository, times(1)).save(any(CaissesEntity.class));
    }
}
