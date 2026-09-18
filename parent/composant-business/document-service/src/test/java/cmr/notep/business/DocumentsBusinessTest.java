package cmr.notep.business;

import cmr.notep.dao.AttributsEntity;
import cmr.notep.dao.DaoAccessorService;
import cmr.notep.dao.DocumentsAssociesEntity;
import cmr.notep.dao.DocumentsEntity;
import cmr.notep.dao.EtatsEntity;
import cmr.notep.modele.Attributs;
import cmr.notep.modele.Documents;
import cmr.notep.modele.DocumentsAssocies;
import cmr.notep.modele.Etats;
import cmr.notep.repository.AttributsRepository;
import cmr.notep.repository.CategoriesRepository;
import cmr.notep.repository.DocEtatsRepository;
import cmr.notep.repository.DocumentsRepository;
import cmr.notep.repository.EtatsRepository;
import org.dozer.DozerBeanMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@DisplayName("Tests - DocumentsBusiness")
class DocumentsBusinessTest {

    private DocumentsBusiness documentsBusiness;
    private AutoCloseable mocks;

    @Mock
    private DaoAccessorService daoAccessorService;

    @Mock
    private CategoriesBusiness categoriesBusiness;

    @Mock
    private DocEtatsBusiness docEtatsBusiness;

    @Mock
    private DozerBeanMapper dozerMapperBean;

    @Mock
    private DocumentsRepository documentsRepository;

    @Mock
    private AttributsRepository attributsRepository;

    @Mock
    private CategoriesRepository categoriesRepository;

    @Mock
    private DocEtatsRepository docEtatsRepository;

    @Mock
    private EtatsRepository etatsRepository;

    @BeforeEach
    void setUp() {
        mocks = MockitoAnnotations.openMocks(this);
        documentsBusiness = new DocumentsBusiness(daoAccessorService, categoriesBusiness, docEtatsBusiness, dozerMapperBean);

        when(daoAccessorService.getRepository(DocumentsRepository.class)).thenReturn(documentsRepository);
        when(daoAccessorService.getRepository(AttributsRepository.class)).thenReturn(attributsRepository);
        when(daoAccessorService.getRepository(CategoriesRepository.class)).thenReturn(categoriesRepository);
        when(daoAccessorService.getRepository(DocEtatsRepository.class)).thenReturn(docEtatsRepository);
        when(daoAccessorService.getRepository(EtatsRepository.class)).thenReturn(etatsRepository);
        when(docEtatsRepository.findByDocumentsEntityId(org.mockito.ArgumentMatchers.anyString()))
                .thenReturn(new ArrayList<>());
        when(docEtatsBusiness.listeDocEtatsParDocument(org.mockito.ArgumentMatchers.anyString()))
                .thenReturn(new ArrayList<>());

        when(documentsRepository.save(any(DocumentsEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        when(dozerMapperBean.map(any(DocumentsEntity.class), eq(Documents.class)))
                .thenAnswer(invocation -> {
                    DocumentsEntity entity = invocation.getArgument(0);
                    Documents model = new Documents();
                    model.setIdDocument(entity.getId());
                    model.setTitre(entity.getTitre());
                    model.setDescription(entity.getDescription());
                    model.setAttributs(entity.getAttributs() == null
                            ? new ArrayList<>()
                            : entity.getAttributs().stream().map(this::toAttribut).toList());
                    return model;
                });

        when(dozerMapperBean.map(any(Documents.class), eq(DocumentsEntity.class)))
                .thenAnswer(invocation -> {
                    Documents model = invocation.getArgument(0);
                    DocumentsEntity entity = new DocumentsEntity();
                    entity.setId(model.getIdDocument());
                    entity.setTitre(model.getTitre());
                    entity.setDescription(model.getDescription());
                    entity.setCategories(new ArrayList<>());
                    entity.setAttributs(new ArrayList<>());
                    return entity;
                });

        when(dozerMapperBean.map(any(Attributs.class), eq(AttributsEntity.class)))
                .thenAnswer(invocation -> {
                    Attributs model = invocation.getArgument(0);
                    AttributsEntity entity = new AttributsEntity();
                    entity.setId(model.getId());
                    entity.setTitre(model.getTitre());
                    return entity;
                });
    }

    @AfterEach
    void tearDown() throws Exception {
        if (mocks != null) {
            mocks.close();
        }
    }

    @Test
    @DisplayName("posterDocument synchronise les attributs du modèle vers attributsEntities")
    void posterDocumentDoitCopierAttributsVersAttributsEntities() {
        Documents document = new Documents();
        document.setIdDocument("doc-1");
        document.setTitre("Titre");
        document.setDescription("Description");
        document.setAttributs(List.of(
                Attributs.builder().id("attr-1").titre("Attr 1").build(),
                Attributs.builder().id("attr-2").titre("Attr 2").build()
        ));

        DocumentsEntity existingEntity = new DocumentsEntity();
        existingEntity.setId("doc-1");
        existingEntity.setTitre("Old titre");
        existingEntity.setDescription("Old description");
        existingEntity.setCategories(new ArrayList<>());
        existingEntity.setAttributs(new ArrayList<>(List.of(existingAttributEntity("old-attr", "Old attr"))));

        when(documentsRepository.findById("doc-1")).thenReturn(Optional.of(existingEntity));
        when(attributsRepository.findById("attr-1")).thenReturn(Optional.of(existingAttributEntity("attr-1", "Attr 1")));
        when(attributsRepository.findById("attr-2")).thenReturn(Optional.of(existingAttributEntity("attr-2", "Attr 2")));

        Documents saved = documentsBusiness.posterDocument(document);

        assertNotNull(saved);
        assertEquals("doc-1", saved.getIdDocument());
        assertNotNull(existingEntity.getAttributs());
        assertEquals(2, existingEntity.getAttributs().size(), "La liste attributs doit être synchronisée");
        assertTrue(existingEntity.getAttributs().stream().anyMatch(attr -> "attr-1".equals(attr.getId())));
        assertTrue(existingEntity.getAttributs().stream().anyMatch(attr -> "attr-2".equals(attr.getId())));
        assertEquals(2, saved.getAttributs().size(), "Le document retourné doit refléter les attributs synchronisés");
    }

    @Test
    @DisplayName("avoirEtatsPossiblesDocument renvoie les etats distincts dans l'ordre des docEtats")
    void avoirEtatsPossiblesDocumentDoitRetournerLesEtatsDistinctsEtOrdonnes() {
        DocumentsEntity existingEntity = new DocumentsEntity();
        existingEntity.setId("doc-possible");
        existingEntity.setTitre("Document possible");

        when(documentsRepository.findById("doc-possible")).thenReturn(Optional.of(existingEntity));

        Etats etatB = Etats.builder().id("etat-b").libelle("B").build();
        Etats etatA = Etats.builder().id("etat-a").libelle("A").build();
        Etats etatC = Etats.builder().id("etat-c").libelle("C").build();

        List<cmr.notep.modele.DocEtats> docEtats = List.of(
                cmr.notep.modele.DocEtats.builder().ordre(30).etat(etatB).build(),
                cmr.notep.modele.DocEtats.builder().ordre(10).etat(etatA).build(),
                cmr.notep.modele.DocEtats.builder().ordre(20).etat(etatC).build(),
                cmr.notep.modele.DocEtats.builder().ordre(40).etat(etatA).build()
        );
        when(docEtatsBusiness.listeDocEtatsParDocument("doc-possible")).thenReturn(docEtats);

        List<Etats> result = documentsBusiness.avoirEtatsPossiblesDocument("doc-possible");

        assertNotNull(result);
        assertEquals(3, result.size(), "Les doublons d'état doivent être supprimés");
        assertEquals("etat-a", result.get(0).getId(), "Le premier état doit être celui du plus petit ordre");
        assertEquals("etat-c", result.get(1).getId(), "Le deuxième état doit respecter l'ordre croissant");
        assertEquals("etat-b", result.get(2).getId(), "Le troisième état doit respecter l'ordre croissant");
    }

    @Test
    @DisplayName("posterDocument synchronise les documents associes sans muter le document lie")
    void posterDocumentDoitSynchroniserDocumentsAssocies() {
        Documents document = new Documents();
        document.setIdDocument("doc-1");
        document.setTitre("Principal");

        Documents linkedDocument = new Documents();
        linkedDocument.setIdDocument("doc-2");
        linkedDocument.setTitre("Associe");

        Etats linkedEtat = Etats.builder().id("etat-1").build();
        Set<DocumentsAssocies> associes = new HashSet<>();
        associes.add(DocumentsAssocies.builder().document(linkedDocument).etat(linkedEtat).build());
        document.setDocumentsAssocies(associes);

        DocumentsEntity principalEntity = new DocumentsEntity();
        principalEntity.setId("doc-1");
        principalEntity.setCategories(new ArrayList<>());
        principalEntity.setDocEtats(new ArrayList<>());
        principalEntity.setDocumentsAssocies(new HashSet<>());

        DocumentsEntity linkedEntity = new DocumentsEntity();
        linkedEntity.setId("doc-2");
        linkedEntity.setTitre("Associe");

        EtatsEntity etatEntity = new EtatsEntity();
        etatEntity.setId("etat-1");

        when(documentsRepository.findById("doc-1")).thenReturn(Optional.of(principalEntity));
        when(documentsRepository.findById("doc-2")).thenReturn(Optional.of(linkedEntity));
        when(etatsRepository.findById("etat-1")).thenReturn(Optional.of(etatEntity));

        documentsBusiness.posterDocument(document);

        assertNotNull(principalEntity.getDocumentsAssocies());
        assertEquals(1, principalEntity.getDocumentsAssocies().size(), "Une seule association doit être synchronisée");

        DocumentsAssociesEntity lien = principalEntity.getDocumentsAssocies().iterator().next();
        assertEquals("doc-1", lien.getDocumentsEntity().getId(), "Le document parent doit être correctement référencé");
        assertEquals("etat-1", lien.getEtat().getId(), "L'état doit être correctement référencé");
        assertNotNull(lien.getDateCreation(), "La date de création du lien doit être renseignée");
    }

    private AttributsEntity existingAttributEntity(String id, String titre) {
        AttributsEntity entity = new AttributsEntity();
        entity.setId(id);
        entity.setTitre(titre);
        return entity;
    }

    private Attributs toAttribut(AttributsEntity entity) {
        return Attributs.builder()
                .id(entity.getId())
                .titre(entity.getTitre())
                .build();
    }
}


