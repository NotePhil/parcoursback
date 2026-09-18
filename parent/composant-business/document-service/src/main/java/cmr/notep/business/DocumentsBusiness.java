package cmr.notep.business;


import java.util.*;
import java.util.stream.Collectors;


import cmr.notep.config.mapper.MapperBean;
import cmr.notep.dao.*;
import cmr.notep.exceptions.ParcoursException;
import cmr.notep.exceptions.enumeration.ParcoursExceptionCodeEnum;
import cmr.notep.modele.Categories;
import cmr.notep.modele.DocEtats;
import cmr.notep.modele.Etats;
import cmr.notep.modele.DocumentsAssocies;
import cmr.notep.repository.CategoriesRepository;
import cmr.notep.repository.DocEtatsRepository;
import cmr.notep.repository.DocumentsRepository;
import cmr.notep.repository.EtatsRepository;
import cmr.notep.utils.BusinessEntityHelper;
import org.dozer.DozerBeanMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cmr.notep.modele.Documents;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import static cmr.notep.util.BeanCopyUtils.copyCollectionsWithDozer;

@Component
@Slf4j
@Transactional
public class DocumentsBusiness {
    private final DaoAccessorService daoAccessorService;
    
    private final CategoriesBusiness categoriesBusiness;
    private final DocEtatsBusiness docEtatsBusiness;
    private final DozerBeanMapper dozerMapperBean;
    //private final BusinessEntityHelper businessEntityHelper; , BusinessEntityHelper businessEntityHelper

    public DocumentsBusiness(DaoAccessorService daoAccessorService, CategoriesBusiness categoriesBusiness,
                              DocEtatsBusiness docEtatsBusiness, DozerBeanMapper dozerMapperBean) {
        this.daoAccessorService = daoAccessorService;
        this.categoriesBusiness = categoriesBusiness;
        this.docEtatsBusiness = docEtatsBusiness;
        this.dozerMapperBean = dozerMapperBean;
    }

    public List<Documents> avoirTousDocuments() {
        log.debug("avoirTousDocuments called");
       return daoAccessorService.getRepository(DocumentsRepository.class).findAll()
                .stream().map(doc ->dozerMapperBean.map(doc,Documents.class))
                .collect(Collectors.toList());
    }

    /**
     * Code métier permettant d'enregistrer un nouveau document
     * @param document document à créer ou mettre à jour
     * @return le document sauvegardé avec ses relations synchronisées
     */
    public Documents posterDocument(Documents document){
        log.debug("[posterDocument] poster un document");

        DocumentsRepository documentsRepo = daoAccessorService.getRepository(DocumentsRepository.class);

        DocumentsEntity documentEntity;
        // Si document existe en base -> récupérer l'entité et copier les propriétés scalaires
        if (document.getIdDocument() != null) {
            documentEntity = documentsRepo.findById(document.getIdDocument())
                    .orElseThrow(() -> new ParcoursException(cmr.notep.exceptions.enumeration.ParcoursExceptionCodeEnum.NOT_FOUND, "Document non trouvé : " + document.getIdDocument()));
            // Copier uniquement les propriétés scalaires (exclure collections)
            //copyPropertiesExcludingCollections(document, documentEntity);
            MapperBean.getDozerMapperWithoutList().map(document, documentEntity);
        } else {
            // Nouveau document
            documentEntity = dozerMapperBean.map(document, DocumentsEntity.class);
            // S'assurer que les collections sont vides pour les gérer explicitement
            if (documentEntity.getCategories() != null) {
                documentEntity.getCategories().clear();
            }
            if (documentEntity.getDocEtats() != null) {
                documentEntity.getDocEtats().clear();
            }
            if (documentEntity.getDocumentsAssocies() != null) {
                documentEntity.getDocumentsAssocies().clear();
            }
            documentsRepo.save(documentEntity);
            document.setIdDocument(documentEntity.getId());
        }
        
        // Gérer les catégories via le business dédié (synchronisation des entités liées)
        gererCategoriesDocument(document, documentEntity);

        // Gérer les documents associés : simple lien de dépendance vers des documents déjà existants
        gererDocumentsAssociesDocument(document, documentEntity);

        List <DocEtats> docEtatsListToInsert = new ArrayList<>();
        Map<String, Set<DocEtats>>  docEtatMapBoucle = new HashMap<>();
        if(!CollectionUtils.isEmpty(document.getDocEtats()))
            docEtatMapBoucle = BusinessEntityHelper.getDocEtatsListToInsert(document.getDocEtats(),docEtatsListToInsert);
        // Gérer les états du document (création si id absent, modification si id présent)
        gererDocEtatsDocument(document, documentEntity,docEtatsListToInsert,docEtatMapBoucle);

        // Pour les relations ManyToMany sans attributs (ex: attributs) on laisse JPA gérer directement via le mapping Dozer
        // Mapper le modèle vers l'entité pour que JPA prenne en charge les relations simples
        // Attention : ne pas écraser les collections déjà synchronisées explicitement ci-dessus
        if(document.getIdDocument() != null){
            copyCollectionsWithDozer(document, documentEntity, dozerMapperBean, "categories", "docEtats", "documentsAssocies");
        }

        DocumentsEntity saved = documentsRepo.save(documentEntity);
        Documents result = dozerMapperBean.map(saved, Documents.class);

        // Dozer peut confondre le document parent et le document associé quand les types sont identiques.
        // On projette explicitement la collection pour garantir le bon id de document associé côté modèle.
        if (!CollectionUtils.isEmpty(document.getDocumentsAssocies())) {
            Set<DocumentsAssocies> associes = document.getDocumentsAssocies().stream()
                    .map(link -> DocumentsAssocies.builder()
                            .id(link.getId())
                            .document(link.getDocument() == null ? null : Documents.builder()
                                    .idDocument(link.getDocument().getIdDocument())
                                    .titre(link.getDocument().getTitre())
                                    .build())
                            .etat(link.getEtat() == null ? null : Etats.builder().id(link.getEtat().getId()).build())
                            .build())
                    .collect(Collectors.toSet());
            result.setDocumentsAssocies(associes);
        }

        return result;
    }

    /**
     * Synchronise les DocEtats d'un document.
     * <ul>
     *     <li>Liste absente/vide alors qu'il existe des DocEtats en base → purge complète.</li>
     *     <li>Occurrence sans {@code id} → création.</li>
     *     <li>Occurrence avec {@code id} → modification.</li>
     *     <li>DocEtat existant non présent dans la nouvelle liste → suppression.</li>
     * </ul>
     */
    private void gererDocEtatsDocument(Documents document, DocumentsEntity documentEntity, List<DocEtats> docEtatsListToInsert, Map<String, Set<DocEtats>> docEtatMapBoucle) {
        DocEtatsRepository docEtatsRepo = daoAccessorService.getRepository(DocEtatsRepository.class);

        // On s'appuie sur une lecture directe en base (et non sur la collection JPA en mémoire,
        // potentiellement obsolète dans le même contexte de persistance) pour connaître l'état réel.
        List<DocEtatsEntity> existingEntities;
        try {
            existingEntities = docEtatsRepo.findByDocumentsEntityId(documentEntity.getId());
        } catch (DataIntegrityViolationException e) {
            throw new ParcoursException(ParcoursExceptionCodeEnum.NOT_FOUND,
                    "Impossible de récupérer les DocEtats existants pour le document : " + e.getMessage(), e);
        }
        // Liste absente/vide → purge complète des DocEtats liés
        if (CollectionUtils.isEmpty(document.getDocEtats())) {
            if (!existingEntities.isEmpty()) {
                //TODO loger dans un journal spécifique ces suppressions
                for (DocEtatsEntity existing : existingEntities) {
                    docEtatsBusiness.supprimerDocEtatEntity(existing, docEtatsRepo);
                }
            }
            documentEntity.setDocEtats(new ArrayList<>());
            return;
        }

        // Référencer le document (id uniquement, pour éviter tout cycle de mapping) sur chaque occurrence
        List<DocEtats> savedDocEtats = docEtatsListToInsert.stream().map(docEtat -> {
               docEtat.setDocument(Documents.builder().idDocument(document.getIdDocument()).build());
               return docEtatsBusiness.posterDocEtat(docEtat);
        }).toList();

        HashMap<String, DocEtats> savedDocEtatsMap = new HashMap<>();
        Set<String> wantedIds = savedDocEtats.stream()
                .map(docEtat -> {
                    savedDocEtatsMap.put(docEtat.getEtat().getId(), docEtat);
                    return docEtat.getId();
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        // Supprimer les DocEtats existants qui ne sont plus désirés
        for (DocEtatsEntity existing : existingEntities) {
            if (!wantedIds.contains(existing.getId())) {
                docEtatsBusiness.supprimerDocEtatEntity(existing, docEtatsRepo);
            }
        }

        docEtatMapBoucle.forEach((etatId, predecesseurs) -> {
            DocEtats savedDocEtat = savedDocEtatsMap.get(etatId);
            if (savedDocEtat != null) {
                predecesseurs = predecesseurs.stream()
                        .map(predecesseur -> savedDocEtatsMap.get(predecesseur.getEtat().getId()))
                        .filter(Objects::nonNull)
                        .collect(Collectors.toSet());
                if(savedDocEtat.getPredecesseurDocEtat() == null) {
                    savedDocEtat.setPredecesseurDocEtat(new HashSet<>());
                }
                savedDocEtat.getPredecesseurDocEtat().addAll(predecesseurs);
                docEtatsBusiness.posterDocEtat(savedDocEtat);
            }
        });

        // Recharger les entités gérées par JPA pour la relation
        documentEntity.setDocEtats(savedDocEtats.stream()
                .map(de -> docEtatsRepo.findById(de.getId())
                        .orElseThrow(() -> new ParcoursException(ParcoursExceptionCodeEnum.NOT_FOUND,
                                "DocEtat introuvable après enregistrement : " + de.getId())))
                .collect(Collectors.toList()));
    }

    /**
     * Synchronise les documents associés (dépendances) d'un document.
     * Règle métier : il ne s'agit que d'un lien de dépendance vers des documents déjà existants en base.
     * <ul>
     *     <li>Chaque document associé doit posséder un {@code idDocument} valide et exister en base,
     *     sinon une exception {@link ParcoursExceptionCodeEnum#NOT_FOUND} est levée.</li>
     *     <li>Aucune mise à jour du contenu du document associé n'est effectuée : seul le lien est tracé.</li>
     *     <li>Un document ne peut pas être associé à lui-même.</li>
     *     <li>Liste {@code null} → aucune modification (champ non fourni par le client).</li>
     *     <li>Liste vide → purge complète des associations.</li>
     * </ul>
     */
    private void gererDocumentsAssociesDocument(Documents document, DocumentsEntity documentEntity) {
        // Champ non fourni par le client : on ne touche pas aux associations existantes.
        if (document.getDocumentsAssocies() == null) {
            return;
        }

        if (documentEntity.getDocumentsAssocies() == null) {
            documentEntity.setDocumentsAssocies(new HashSet<>());
        }

        // Liste vide -> purge complète des associations.
        if (document.getDocumentsAssocies().isEmpty()) {
            documentEntity.getDocumentsAssocies().clear();
            return;
        }

        DocumentsRepository documentsRepo = daoAccessorService.getRepository(DocumentsRepository.class);
        EtatsRepository etatsRepo = daoAccessorService.getRepository(EtatsRepository.class);

        Map<String, DocumentsAssociesEntity> existingByBusinessKey = documentEntity.getDocumentsAssocies().stream()
                .filter(existing -> existing.getDocumentAssocie() != null && existing.getEtat() != null)
                .collect(Collectors.toMap(
                        existing -> existing.getDocumentAssocie().getId() + "|" + existing.getEtat().getId(),
                        existing -> existing,
                        (a, b) -> a));

        Set<DocumentsAssociesEntity> resolved = document.getDocumentsAssocies().stream()
                .map(assoc -> {
                    if (assoc == null || assoc.getDocument() == null) {
                        throw new ParcoursException(ParcoursExceptionCodeEnum.OPERATION_INTERDITE,
                                "Un document associe doit referencer un document existant");
                    }

                    String idAssocie = assoc.getDocument().getIdDocument();
                    if (idAssocie == null) {
                        throw new ParcoursException(ParcoursExceptionCodeEnum.OPERATION_INTERDITE,
                                "Un document associe doit posseder un identifiant existant");
                    }

                    if (idAssocie.equals(document.getIdDocument())) {
                        throw new ParcoursException(ParcoursExceptionCodeEnum.OPERATION_INTERDITE,
                                "Un document ne peut pas etre associe a lui-meme");
                    }

                    if (assoc.getEtat() == null || assoc.getEtat().getId() == null) {
                        throw new ParcoursException(ParcoursExceptionCodeEnum.OPERATION_INTERDITE,
                                "Un document associe doit posseder un etat existant");
                    }

                    DocumentsEntity associeEntity = documentsRepo.findById(idAssocie)
                            .orElseThrow(() -> new ParcoursException(ParcoursExceptionCodeEnum.NOT_FOUND,
                                    "Document associe introuvable : " + idAssocie));

                    EtatsEntity etatEntity = etatsRepo.findById(assoc.getEtat().getId())
                            .orElseThrow(() -> new ParcoursException(ParcoursExceptionCodeEnum.NOT_FOUND,
                                    "Etat associe introuvable : " + assoc.getEtat().getId()));

                    String businessKey = idAssocie + "|" + etatEntity.getId();
                    DocumentsAssociesEntity link = existingByBusinessKey.getOrDefault(businessKey, new DocumentsAssociesEntity());

                    link.setDocumentsEntity(documentEntity);
                    link.setDocumentAssocie(associeEntity);
                    link.setEtat(etatEntity);
                    if (link.getDateCreation() == null) {
                        link.setDateCreation(new Date());
                    }
                    return link;
                })
                .collect(Collectors.toCollection(HashSet::new));

        documentEntity.getDocumentsAssocies().clear();
        documentEntity.getDocumentsAssocies().addAll(resolved);
    }

    // Synchronise les catégories d'un document : ajout/modification/suppression
    private void gererCategoriesDocument(Documents document, DocumentsEntity documentEntity) throws ParcoursException {
        // Récupérer le repository des catégories
        CategoriesRepository categorieRepo = daoAccessorService.getRepository(CategoriesRepository.class);

        // Si la liste fournie est vide -> purge complète des catégories liées
        if (CollectionUtils.isEmpty(document.getCategories())&& !CollectionUtils.isEmpty(documentEntity.getCategories())) {
            //TODO loger dans un journal spécifique ces suppressions
            for (CategoriesEntity existing : new ArrayList<>(documentEntity.getCategories())) {
                categoriesBusiness.supprimerCategoryEntity(existing, categorieRepo);
            }
            return;
        }
        //Ano on si document présent, alors JPA tente de sauvegarder les catégories du  document sans réussir à gérer cette relation avec attribut
        // Appeler le business Categories pour créer/modifier chaque catégorie fournie
        List<Categories> savedCats = document.getCategories()!=null ? document.getCategories().stream().map(cat -> {
            try {
                cat.setDocument(document);
                return categoriesBusiness.posterCategorie(cat).orElse(null);
            } catch (ParcoursException e) {
                throw e;
            }
        }).filter(Objects::nonNull).toList() : new ArrayList<>();

        // Construire set des ids souhaités
        Set<String> wantedIds = savedCats.stream()
                .map(Categories::getId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        // Supprimer les catégories existantes qui ne sont plus désirées
        if (documentEntity.getCategories() != null) {
            for (CategoriesEntity existing : new ArrayList<>(documentEntity.getCategories())) {
                if (!wantedIds.contains(existing.getId())) {
                    try {
                        categoriesBusiness.supprimerCategoryEntity(existing, categorieRepo);
                        documentEntity.getCategories().remove(existing);
                    } catch (Exception e) {
                        throw new ParcoursException(cmr.notep.exceptions.enumeration.ParcoursExceptionCodeEnum.RELATION_SYNC_FAILED,
                                "Impossible de supprimer la catégorie liée: " + e.getMessage(), e);
                    }
                }
            }
        } else {
            documentEntity.setCategories(new ArrayList<>());
        }

        // Mise à jour des relations JPA
        documentEntity.getCategories().clear();
        documentEntity.setCategories(savedCats.stream()
                .map(cat -> {
                   CategoriesEntity entity = dozerMapperBean.map(cat, CategoriesEntity.class);
                   entity.setDocumentsEntity(documentEntity);
                   return entity;
                })
                .collect(Collectors.toList()));
    }


    public Documents assignEtatDoc(Etats etat ,  Documents document)
    {

        log.debug("[assignEtatDoc] assigner un état au document");

        if (etat == null || document == null) {
            throw new IllegalArgumentException("Etat et Document doivent être non nuls");
        }

        Optional<EtatsEntity> optionalEtatEntity = this.daoAccessorService.getRepository(EtatsRepository.class)
                .findById(etat.getId());
        if (optionalEtatEntity.isEmpty()) {
            throw new ParcoursException(ParcoursExceptionCodeEnum.NOT_FOUND,
                    "Etat non trouvé en base : " + etat.getId());
        }
        EtatsEntity etatEntity = optionalEtatEntity.get();

        Optional<DocumentsEntity> optionalDocumentEntity = this.daoAccessorService.getRepository(DocumentsRepository.class)
                .findById(document.getIdDocument());
        if (optionalDocumentEntity.isEmpty()) {
            throw new ParcoursException(ParcoursExceptionCodeEnum.NOT_FOUND,
                    "Document non trouvé en base : " + document.getIdDocument());
        }
        DocumentsEntity documentEntity = optionalDocumentEntity.get();

        boolean exists = documentEntity.getDocEtats().stream()
                .anyMatch(de -> de.getEtatsEntity().getId().equals(etatEntity.getId()));
        if (exists) {
            log.debug("[assignEtatDoc] Etat déjà assigné au document");
            return dozerMapperBean.map(documentEntity, Documents.class);
        }

        DocEtatsEntity newDocEtat = new DocEtatsEntity();
        newDocEtat.setEtatsEntity(etatEntity);
        newDocEtat.setDocumentsEntity(documentEntity);
        newDocEtat.setDateCreation(new Date());

       documentEntity.getDocEtats().add(newDocEtat);

       DocumentsEntity savedDocument = this.daoAccessorService.getRepository(DocumentsRepository.class).save(documentEntity);

       return dozerMapperBean.map(savedDocument, Documents.class);
    }

    public Documents avoirDocument(String idDoc) {
        log.debug("[avoirDocument] called");
        return dozerMapperBean.map(
                this.daoAccessorService.getRepository(DocumentsRepository.class)
                        .findById(idDoc)
                        .orElseThrow(() -> new ParcoursException(ParcoursExceptionCodeEnum.NOT_FOUND, "Document non trouvé")),Documents.class);
    }

    public List<Etats> avoirEtatsPossiblesDocument(String idDoc) {
        log.debug("[avoirEtatsPossiblesDocument] called for document {}", idDoc);

        // Vérifie que le document existe bien avant de calculer les états possibles.
        avoirDocument(idDoc);

        List<DocEtats> docEtats = docEtatsBusiness.listeDocEtatsParDocument(idDoc);
        if (CollectionUtils.isEmpty(docEtats)) {
            return new ArrayList<>();
        }

        Map<String, Etats> etatsPossibles = new LinkedHashMap<>();
        docEtats.stream()
                .sorted(Comparator.comparingInt(DocEtats::getOrdre)
                        .thenComparing(docEtat -> docEtat.getId() == null ? "" : docEtat.getId()))
                .map(DocEtats::getEtat)
                .filter(Objects::nonNull)
                .filter(etat -> etat.getId() != null)
                .forEach(etat -> etatsPossibles.putIfAbsent(etat.getId(), etat));

        return new ArrayList<>(etatsPossibles.values());
    }

    public void supprimerDocument(Documents document) {
        log.debug("[supprimerDocument] Suppression du document avec l'ID : " + document.getIdDocument());
        this.daoAccessorService.getRepository(DocumentsRepository.class)
                .deleteById(document.getIdDocument());
    }

    public Documents modifierDocument(Documents document) {
        log.debug("[modifierDocument] Modification du document avec l'ID : " + document.getIdDocument());
        // Vérifier que le document existe avant de le modifier
        this.daoAccessorService.getRepository(DocumentsRepository.class)
                .findById(document.getIdDocument())
                .orElseThrow(() -> new ParcoursException(ParcoursExceptionCodeEnum.NOT_FOUND, "Document non trouvé"));

        if(!CollectionUtils.isEmpty(document.getCategories())) {
            List<Categories> categoriesList = document.getCategories().stream()
                    .map(categorie -> {
                        try {
                            log.debug("[modifierDocument] idDocument : " + document.getIdDocument() + " modifier une categorie " + categorie);
                            categorie.setDocument(document);
                            return categoriesBusiness.posterCategorie(categorie).orElse(null);
                        } catch (ParcoursException e) {
                            throw e;
                        }
                    })
                    .filter(Objects::nonNull)
                    .toList();
            document.getCategories().clear();
            document.getCategories().addAll(categoriesList);
        }
        return dozerMapperBean.map(
                this.daoAccessorService.getRepository(DocumentsRepository.class)
                        .save(dozerMapperBean.map(document, DocumentsEntity.class)),
                Documents.class);
    }
}
