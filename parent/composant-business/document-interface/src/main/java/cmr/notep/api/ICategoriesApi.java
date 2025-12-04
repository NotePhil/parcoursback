package cmr.notep.api;

import cmr.notep.exceptions.ParcoursException;
import cmr.notep.modele.Categories;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("categories")
public interface ICategoriesApi {
    @GetMapping(
            path = "/{idCategories}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    Categories avoirCategorie (@NonNull @PathVariable(name="idCategories") String idCategorie) throws ParcoursException;

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    List<Categories> avoirToutCategories();

    @DeleteMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    void supprimerCategorie(@NonNull @RequestBody Categories categorie);

    @PostMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    Categories posterCategorie(@NonNull @RequestBody Categories categorie) throws ParcoursException;

}
