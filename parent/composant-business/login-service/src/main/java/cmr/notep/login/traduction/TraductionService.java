package cmr.notep.login.traduction;

import cmr.notep.exceptions.ParcoursException;

/**
 * Abstraction du service de traduction externe.
 * Implémentation : DeepL API Free.
 */
public interface TraductionService {

    /**
     * Traduit un texte de la langue source vers la langue cible.
     *
     * @param texte       texte à traduire
     * @param langueCible code langue cible (ex: "EN", "ES", "DE")
     * @return texte traduit
     */
    String traduire(String texte, String langueCible) throws ParcoursException;
}
