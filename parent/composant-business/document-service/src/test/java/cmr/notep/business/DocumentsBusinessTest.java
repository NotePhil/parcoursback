package cmr.notep.business;

import cmr.notep.dao.AttributsEntity;
import cmr.notep.dao.DaoAccessorService;
import cmr.notep.dao.DocumentsEntity;
import cmr.notep.modele.Attributs;
import cmr.notep.modele.Documents;
import cmr.notep.repository.AttributsRepository;
import cmr.notep.repository.CategoriesRepository;
import cmr.notep.repository.DocumentsRepository;
import org.dozer.DozerBeanMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@DisplayName("Tests - DocumentsBusiness")
class DocumentsBusinessTest {

    private DocumentsBusiness documentsBusiness;

    @Mock
    private DaoAccessorService daoAccessorService;

    @Mock
    private CategoriesBusiness categoriesBusiness;

    @Mock
    private DozerBeanMapper dozerMapperBean;

    @Mock
    private DocumentsRepository documentsRepository;

    @Mock
    private AttributsRepository attributsRepository;

    @Mock
    private CategoriesRepository categoriesRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        documentsBusiness = new DocumentsBusiness(daoAccessorService, categoriesBusiness, dozerMapperBean);

        when(daoAccessorService.getRepository(DocumentsRepository.class)).thenReturn(documentsRepository);
        when(daoAccessorService.getRepository(AttributsRepository.class)).thenReturn(attributsRepository);
        when(daoAccessorService.getRepository(CategoriesRepository.class)).thenReturn(categoriesRepository);

        when(documentsRepository.save(any(DocumentsEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        when(dozerMapperBean.map(any(DocumentsEntity.class), eq(Documents.class)))
                .thenAnswer(invocation -> {
                    DocumentsEntity entity = invocation.getArgument(0);
                    Documents model = new Documents();
                    model.setIdDocument(entity.getId());
                    model.setTitre(entity.getTitre());
                    model.setDescription(entity.getDescription());
                    model.setAttributs(entity.getAttributsEntities() == null
                            ? new ArrayList<>()
                            : entity.getAttributsEntities().stream().map(this::toAttribut).toList());
                    return model;
                });

        when(dozerMapperBean.map(any(Documents.class), eq(DocumentsEntity.class)))
                .thenAnswer(invocation -> {
                    Documents model = invocation.getArgument(0);
                    DocumentsEntity entity = new DocumentsEntity();
                    entity.setId(model.getIdDocument());
                    entity.setTitre(model.getTitre());
                    entity.setDescription(model.getDescription());
                    entity.setCategoriesEntities(new ArrayList<>());
                    entity.setAttributsEntities(new ArrayList<>());
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
        existingEntity.setCategoriesEntities(new ArrayList<>());
        existingEntity.setAttributsEntities(new ArrayList<>(List.of(existingAttributEntity("old-attr", "Old attr"))));

        when(documentsRepository.findById("doc-1")).thenReturn(Optional.of(existingEntity));
        when(attributsRepository.findById("attr-1")).thenReturn(Optional.of(existingAttributEntity("attr-1", "Attr 1")));
        when(attributsRepository.findById("attr-2")).thenReturn(Optional.of(existingAttributEntity("attr-2", "Attr 2")));

        Documents saved = documentsBusiness.posterDocument(document);

        assertNotNull(saved);
        assertEquals("doc-1", saved.getIdDocument());
        assertNotNull(existingEntity.getAttributsEntities());
        assertEquals(2, existingEntity.getAttributsEntities().size(), "La liste attributsEntities doit être synchronisée");
        assertTrue(existingEntity.getAttributsEntities().stream().anyMatch(attr -> "attr-1".equals(attr.getId())));
        assertTrue(existingEntity.getAttributsEntities().stream().anyMatch(attr -> "attr-2".equals(attr.getId())));
        assertEquals(2, saved.getAttributs().size(), "Le document retourné doit refléter les attributs synchronisés");
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


