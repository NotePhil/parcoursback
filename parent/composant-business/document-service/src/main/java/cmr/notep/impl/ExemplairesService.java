package cmr.notep.impl;

import cmr.notep.api.IExemplairesApi;
import cmr.notep.business.ExemplairesBusiness;
import cmr.notep.exceptions.ParcoursException;
import cmr.notep.modele.Exemplaires;
import jakarta.transaction.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Transactional
public class ExemplairesService implements IExemplairesApi {

    private ExemplairesBusiness exemplairesBusiness;

    public ExemplairesService(ExemplairesBusiness exemplairesBusiness) {
        this.exemplairesBusiness = exemplairesBusiness;
    }

    @Override
    public Exemplaires avoirExemplaire(String idExemplaire) throws ParcoursException {
        return this.exemplairesBusiness.avoirExemplaires(idExemplaire);
    }

    @Override
    public List<Exemplaires> avoirToutExemplaires() {
        return this.exemplairesBusiness.avoirTousExemplaires();
    }

    @Override
    public Exemplaires posterExemplaires(Exemplaires exemplaires) {
        return this.exemplairesBusiness.posterExemplaire(exemplaires);
    }
}
