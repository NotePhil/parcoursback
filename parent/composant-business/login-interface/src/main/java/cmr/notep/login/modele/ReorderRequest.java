package cmr.notep.login.modele;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

/**
 * Requête de réordonnancement : liste ordonnée des ids avec leur nouvel ordre.
 * Utilisée par les endpoints /reorder de chaque entité du menu.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReorderRequest {

    /** Liste des ids dans le nouvel ordre souhaité (index 0 = ordre 1). */
    private List<String> ids;
}
