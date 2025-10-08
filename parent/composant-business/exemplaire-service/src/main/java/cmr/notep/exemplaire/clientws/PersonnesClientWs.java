package cmr.notep.exemplaire.clientws;

import cmr.notep.api.IPersonnesApi;
import cmr.notep.exceptions.ParcoursException;
import cmr.notep.exceptions.enumeration.ParcoursExceptionCodeEnum;
import cmr.notep.exemplaire.config.ExemplaireConfig;
import cmr.notep.modele.Documents;
import cmr.notep.modele.IPersonnes;
import cmr.notep.modele.Personnes;
import cmr.notep.modele.PersonnesPhysique;
import cmr.notep.utile.serialiser.JacksonHelper;
import cmr.notep.wstools.api.IGenericWsClientApi;
import cmr.notep.wstools.modeles.GenericWsRequest;
import cmr.notep.wstools.modeles.GenericWsResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile("!ittest")
public class PersonnesClientWs implements IPersonnesApi {
    public static final String URI_PERSONNES = "/personnes/";

    private final IGenericWsClientApi genericWsClientApi;
    private final ExemplaireConfig exemplaireConfig;

    public PersonnesClientWs(IGenericWsClientApi genericWsClientApi, ExemplaireConfig exemplaireConfig) {
        this.genericWsClientApi = genericWsClientApi;
        this.exemplaireConfig = exemplaireConfig;
    }

    @Override
    public List<IPersonnes> avoirToutPersonnesPhysiques() {
        return List.of();
    }

    @Override
    public PersonnesPhysique posterPersonnePhysique(PersonnesPhysique Personnes) {
        return null;
    }

    @Override
    public Personnes avoirPersonne(String idPersonne) throws ParcoursException {
        GenericWsRequest request = GenericWsRequest.builder()
                .url(exemplaireConfig.getUrlDocumentApiService() + URI_PERSONNES + idPersonne)
                .method("GET")
                .build();
        GenericWsResponse response = genericWsClientApi.sendRequest(request);
        if (response.getCode() == 200)
            return JacksonHelper.objetFromJson(response.getReponse(), Personnes.class);
        throw new ParcoursException(ParcoursExceptionCodeEnum.INTERNAL_ERROR ,"Erreur lors de la récupération du document " + idPersonne + " code http : " + response.getReponse() + " reponse : " + response.getReponse());

    }

    @Override
    public List<IPersonnes> avoirToutPersonnes() {
        return null;
    }

    @Override
    public Personnes posterPersonne(Personnes Personnes) {
        return null;
    }
}
