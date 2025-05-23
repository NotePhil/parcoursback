package cmr.notep.impl;

import cmr.notep.api.IFamillesApi;
import cmr.notep.business.FamillesBusiness;
import cmr.notep.modele.Familles;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@Transactional
public class FamillesService implements IFamillesApi {

    private final FamillesBusiness famillesBusiness;

    public FamillesService(FamillesBusiness famillesBusiness) {
        this.famillesBusiness = famillesBusiness;
    }

    @Override
    public Familles posterFamille(@NonNull Familles familles) {
        return famillesBusiness.posterFamille(familles);
    }

    @Override
    public Familles avoirFamille(@NonNull String idFamille) {
        return famillesBusiness.avoirFamille(idFamille);
    }

    @Override
    public List<Familles> avoirTousFamilles() {
        System.out.println("calling avoir tout familles");
        return famillesBusiness.avoirTousFamilles();
    }

    @Override
    public Boolean SupprimerFamilles(@NonNull Familles familles) {
        return null;
    }
}
