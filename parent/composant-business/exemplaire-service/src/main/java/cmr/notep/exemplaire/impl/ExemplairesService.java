package cmr.notep.exemplaire.impl;

import cmr.notep.exceptions.ParcoursException;
import cmr.notep.exemplaire.api.IExemplairesApi;
import cmr.notep.exemplaire.business.ExemplairesBusiness;
import cmr.notep.exemplaire.modele.Exemplaires;
import lombok.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import jakarta.transaction.Transactional;
import java.util.List;
@RestController
@Transactional
public class ExemplairesService implements IExemplairesApi {
    protected final ExemplairesBusiness exemplairesBusiness;

    public ExemplairesService(ExemplairesBusiness exemplairesBusiness) {
        this.exemplairesBusiness = exemplairesBusiness;
    }

    @Override
    public Exemplaires posterExemplaire(@NonNull Exemplaires exemplaire) throws ParcoursException {
        return exemplairesBusiness.posterExemplaire(exemplaire);
    }

    @Override
    public Exemplaires avoirExemplaire(@NonNull String idExemplaire) throws ParcoursException {
        return exemplairesBusiness.avoirExemplaire(idExemplaire);
    }

    @Override
    public List<Exemplaires> avoirTousExemplaires() throws ParcoursException {
        return exemplairesBusiness.avoirTousExemplaires();
    }

    @Override
    public Boolean SupprimerExemplaire(Exemplaires exemplaire) {
        return null;
    }
}
