package cmr.notep.impl;

import cmr.notep.api.ICategoriesApi;
import cmr.notep.business.CategoriesBusiness;
import cmr.notep.exceptions.ParcoursException;
import cmr.notep.modele.Categories;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@Transactional
public class CategoryService implements ICategoriesApi {
    private final CategoriesBusiness categoriesBusiness;
    public CategoryService(CategoriesBusiness categoriesBusiness) {
        this.categoriesBusiness = categoriesBusiness;
    }

    public Categories avoirCategorie(String id) {
        return categoriesBusiness.avoirCategorie(id);
    }

    @Override
    public List<Categories> avoirToutCategories() {
        return categoriesBusiness.avoirToutCategorie();
    }

    public List<Categories> avoirToutCategorie() { return categoriesBusiness.avoirToutCategorie(); }

    public void supprimerCategorie(@NonNull Categories categories) { categoriesBusiness.supprimerCategory(categories); }

    public Categories posterCategorie(@NonNull Categories categorie) throws ParcoursException { return categoriesBusiness.posterCategorie(categorie);}

}
