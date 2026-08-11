package cmr.notep.business;

import cmr.notep.dao.*;
import cmr.notep.exceptions.ParcoursException;
import cmr.notep.exceptions.enumeration.ParcoursExceptionCodeEnum;
import cmr.notep.modele.Associer;
import cmr.notep.modele.Categories;
import cmr.notep.repository.AssocierRepository;
import cmr.notep.repository.CategoriesRepository;
import cmr.notep.util.BeanCopyUtils;
import lombok.extern.slf4j.Slf4j;
import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Component
@Slf4j
@Transactional
public class CategoriesBusiness {
    private final DaoAccessorService daoAccessorService;
    private final DozerBeanMapper dozerMapperBean;

    public CategoriesBusiness(DaoAccessorService daoAccessorService, DozerBeanMapper dozerMapperBean){
        this.daoAccessorService = daoAccessorService ;
        this.dozerMapperBean = dozerMapperBean;
    }

    public Categories avoirCategorie(String id) {
        return dozerMapperBean.map(
                this.daoAccessorService.getRepository(CategoriesRepository.class)
                        .findById(id)
                        .orElseThrow(()->new RuntimeException("Categorie inexistante")), Categories.class);
    }

    public List<Categories> avoirToutCategorie() {
        return daoAccessorService.getRepository(CategoriesRepository.class).findAll()
                .stream().map(cat ->dozerMapperBean.map(cat, Categories.class))
                .collect(Collectors.toList());
    }

    public void supprimerCategory(Categories categories) throws ParcoursException
    {
        CategoriesRepository categorieRepo = daoAccessorService.getRepository(CategoriesRepository.class);

        // Récupérer la catégorie avec ses associations
        CategoriesEntity categorieEntity = categorieRepo.findById(categories.getId())
                .orElseThrow(() -> new RuntimeException("Catégorie non trouvée : " + categories.getId()));

        supprimerCategoryEntity(categorieEntity, categorieRepo);
    }

    public void supprimerCategoryEntity(CategoriesEntity categorieEntity, CategoriesRepository categorieRepo) {
        // Supprimer toutes les associations avec les attributs
        try {
            if (categorieEntity.getAttributsEntities() != null && !categorieEntity.getAttributsEntities().isEmpty()) {
                AssocierRepository associerRepo = daoAccessorService.getRepository(AssocierRepository.class);
                for (AssocierEntity existing : new ArrayList<>(categorieEntity.getAttributsEntities())) {
                    associerRepo.delete(existing);
                }
                categorieEntity.getAttributsEntities().clear();
            }
        } catch (Exception e) {
            throw new ParcoursException(ParcoursExceptionCodeEnum.RELATION_SYNC_FAILED,
                    "Impossible de supprimer les associations de la catégorie: " + e.getMessage(), e);
        }

        // Supprimer la catégorie
        categorieRepo.deleteById(categorieEntity.getId());
    }

    public Optional<Categories> posterCategorie(Categories categorie) throws ParcoursException {
        CategoriesRepository categorieRepo = daoAccessorService.getRepository(CategoriesRepository.class);
        CategoriesEntity categorieEntity;

        if (categorie.getId() != null) {
            categorieEntity = categorieRepo.findById(categorie.getId())
                    .orElseThrow(() -> new ParcoursException(ParcoursExceptionCodeEnum.NOT_FOUND,"Catégorie non trouvée : " + categorie.getId()));
            // au lieu de dozerMapperBean.map(categorie, categorieEntity) nous copions uniquement les propriétés scalaires
            BeanCopyUtils.copyPropertiesExcludingCollections(categorie, categorieEntity);
            if (categorie.getAttributs() == null || categorie.getAttributs().isEmpty()) {
                // Liste vide sur une catégorie existante → suppression + 204 No Content
                supprimerCategoryEntity(categorieEntity, categorieRepo);
                return Optional.empty();
            }
        } else {
            if(categorie.getAttributs() == null || categorie.getAttributs().isEmpty())
                throw new ParcoursException(ParcoursExceptionCodeEnum.OPERATION_INTERDITE,
                        "Une catégorie doit avoir au moins un attribut associé.");
            CategoriesEntity newCategorieEntity = dozerMapperBean.map(categorie, CategoriesEntity.class);
            newCategorieEntity.getAttributsEntities().clear();
            newCategorieEntity = categorieRepo.save(newCategorieEntity);
            categorieEntity = dozerMapperBean.map(categorie, CategoriesEntity.class);
            categorieEntity.setId(newCategorieEntity.getId());
        }

        gererAttributsCategorie(categorie, categorieEntity);

        categorieEntity = categorieRepo.save(categorieEntity);
        return Optional.of(dozerMapperBean.map(categorieEntity, Categories.class));
    }

    private void gererCategorieVide(CategoriesEntity categorieEntity) {
        AssocierRepository associerRepo = daoAccessorService.getRepository(AssocierRepository.class);
        try {
            if(categorieEntity.getAttributsEntities() != null && !categorieEntity.getAttributsEntities().isEmpty()){
                for (AssocierEntity existing : new ArrayList<>(categorieEntity.getAttributsEntities())){
                    associerRepo.delete(existing);
                }
                categorieEntity.getAttributsEntities().clear();
                daoAccessorService.getRepository(CategoriesRepository.class).save(categorieEntity);
            }
        }catch (Exception e){
            log.warn("Impossible de vider les attributs de la catégorie: {}", e.getMessage());
        }
    }

    private void gererAttributsCategorie(Categories categorie, CategoriesEntity categorieEntity) throws ParcoursException {
        // Synchroniser les attributs
        AssocierRepository associerRepo = daoAccessorService.getRepository(AssocierRepository.class);
        List<AssocierEntity> existingAssocier = categorieEntity.getAttributsEntities() != null
                ? new ArrayList<>(categorieEntity.getAttributsEntities())
                : new ArrayList<>();

        // Supprimer ceux non présents dans la nouvelle liste
        for (AssocierEntity existing : new ArrayList<>(existingAssocier)) {
            boolean found = categorie.getAttributs().stream()
                    .anyMatch(attr -> attr.getId() != null &&
                              attr.getId().getAttributsId().equalsIgnoreCase(existing.getId().getAttributsId()));
            if (!found) {
                try {
                    associerRepo.delete(existing);
                    categorieEntity.getAttributsEntities().remove(existing);
                } catch (Exception e) {
                    throw new ParcoursException(ParcoursExceptionCodeEnum.RELATION_SYNC_FAILED,
                            "Impossible de supprimer l'attribut associé: " + e.getMessage(), e);
                }
            }
        }

        if (categorieEntity.getAttributsEntities() == null) {
            categorieEntity.setAttributsEntities(new ArrayList<>());
        }

        // Ajouter ou mettre à jour les attributs
        for (Associer attr : categorie.getAttributs()) {

            try {
                AssocierEntity assocEntity = new AssocierEntity();
                dozerMapperBean.map(attr, assocEntity);
                assocEntity.setCategorie(categorieEntity);
                if (assocEntity.getId() == null) {
                    assocEntity.setId(AssocierEntityID.builder()
                            .categoriesId(categorieEntity.getId())
                            .attributsId(attr.getAttribut().getId())
                            .build());
                }
                AssocierEntity savedAssoc = associerRepo.save(assocEntity);
                if (!categorieEntity.getAttributsEntities().contains(savedAssoc)) {
                    categorieEntity.getAttributsEntities().add(savedAssoc);
                }
            } catch (Exception e) {
                throw new ParcoursException(ParcoursExceptionCodeEnum.RELATION_SYNC_FAILED,
                        "Impossible d'ajouter/modifier l'attribut associé: " + e.getMessage(), e);
            }
        }
    }
}
