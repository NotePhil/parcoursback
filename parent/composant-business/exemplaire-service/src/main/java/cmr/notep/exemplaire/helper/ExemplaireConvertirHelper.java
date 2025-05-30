package cmr.notep.exemplaire.helper;

import cmr.notep.api.*;
import cmr.notep.exceptions.ParcoursException;
import cmr.notep.exemplaire.modele.*;
import cmr.notep.modele.Documents;
import cmr.notep.modele.Mouvements;
import cmr.notep.modele.OrdreEtats;
import cmr.notep.modele.PrecoMouvements;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

import static cmr.notep.exemplaire.config.ExemplaireConfig.dozerMapperBean;

public class ExemplaireConvertirHelper {
    public static Exemplaires convertirExemplaireInterneEnExemplaire(IDocumentsApi documentsApi, IPersonnesApi personnesApi,IAttributsApi attributsApi,
                                                                     IPrecoMouvementsApi precoMouvementsApi, IRessourcesApi ressourcesApi, IDistributeursApi distributeursApi,
                                                                     IPersonnelsApi personnelsApi, IValidationsApi validationsApi, IEtatsApi etatsApi, ExemplairesInterne exemplairesInterne) throws ParcoursException {
        Documents document = documentsApi.avoirDocument(exemplairesInterne.getDocumentId());
        Exemplaires exemplaire = Exemplaires.builder().build();
        dozerMapperBean.map(document, exemplaire);
        dozerMapperBean.map(exemplairesInterne, exemplaire);
        exemplaire.setPersonneBeneficiaire(
                StringUtils.isNotBlank(exemplairesInterne.getPersonneBeneficiaireId())?
                        personnesApi.avoirPersonne(exemplairesInterne.getPersonneBeneficiaireId()):null);
        exemplaire.setPersonneRattachee(
                StringUtils.isNotBlank(exemplairesInterne.getPersonneRattacheeId())?
                        personnesApi.avoirPersonne(exemplairesInterne.getPersonneRattacheeId()):null);
        List<OrdreEtats> ordreEtatsList = convertirListeOrdreEtatsInterneEnOrdreEtats(etatsApi, personnelsApi, validationsApi, exemplairesInterne.getOrdreEtatsInternes());
        exemplaire.setOrdreEtats(ordreEtatsList);
        List<Mouvements> mouvements = convertirListeMouvemntsInterneEnMouvements(precoMouvementsApi, ressourcesApi, distributeursApi,exemplairesInterne.getMouvementsInternes());
        exemplaire.setMouvements(mouvements);
        List<PersonnesDestinataires> personnesDestinataires = convertirListePersonneDestinataireInterneEnPersonneDestinataire(personnesApi,exemplairesInterne.getPersonnesDestinatairesInternes());
        exemplaire.setPersonnesDestinataires(personnesDestinataires);
        List<ExemplaireAttributs> exemplaireAttributs = convertirListExemplaireAttributsInterneEnExemplaireAttributs(attributsApi,exemplairesInterne.getExemplaireAttributsInternes());
        exemplaire.setExemplaireAttributs(exemplaireAttributs);

        return exemplaire;
    }

    private static List<ExemplaireAttributs> convertirListExemplaireAttributsInterneEnExemplaireAttributs(IAttributsApi attributsApi, List<ExemplaireAttributsInterne> exemplaireAttributs) {
        if(CollectionUtils.isEmpty(exemplaireAttributs))
            return new ArrayList<>();
        return exemplaireAttributs.stream().map(exemplaireAttributInterne -> {
            ExemplaireAttributs exemplaireAttribut = null;
            try {
                exemplaireAttribut = ExemplaireAttributs.builder()
                        .valeur(exemplaireAttributInterne.getValeur())
                        .dateCreation(exemplaireAttributInterne.getDateCreation())
                        .dateModification(exemplaireAttributInterne.getDateModification())
                        .attribut(
                                StringUtils.isNotBlank(exemplaireAttributInterne.getAttributId())?
                                        attributsApi.avoirAttribut(exemplaireAttributInterne.getAttributId()):null)
                        .build();
            } catch (ParcoursException e) {
                throw new RuntimeException(e);
            }
            return exemplaireAttribut;
        }).toList();
    }

    private static List<Mouvements> convertirListeMouvemntsInterneEnMouvements(IPrecoMouvementsApi precoMouvementsApi, IRessourcesApi ressourcesApi, IDistributeursApi distributeursApi, List<MouvementsInterne> mouvementsInternes) {
        if(CollectionUtils.isEmpty(mouvementsInternes))
            return new ArrayList<>();
        return mouvementsInternes.stream().map(mouvementInterne -> {
            Mouvements mouvement = null;
            try {
                List<PrecoMouvements> precoMouvementsRespecter = new ArrayList<>();
                if(!CollectionUtils.isEmpty(mouvementInterne.getPrecoMouvementsRespecterIds()))
                    for (String idPreco : mouvementInterne.getPrecoMouvementsRespecterIds()) {
                        if(StringUtils.isNotBlank(idPreco))
                            precoMouvementsRespecter.add(precoMouvementsApi.avoirPrecoMouvement(idPreco));
                    }
                List<PrecoMouvements> precoMouvementsVioler = new ArrayList<>();
                if(!CollectionUtils.isEmpty(mouvementInterne.getPrecoMouvementsViolerIds()))
                    for (String idPrecoMouvement : mouvementInterne.getPrecoMouvementsViolerIds()) {
                        if(StringUtils.isNotBlank((idPrecoMouvement)))
                            precoMouvementsVioler.add(precoMouvementsApi.avoirPrecoMouvement(idPrecoMouvement));
                    }
                mouvement = Mouvements.builder()
                        .dateCreation(mouvementInterne.getDateCreation())
                        .id(mouvementInterne.getId())
                        .datePeremption(mouvementInterne.getDatePeremption())
                        .description(mouvementInterne.getDescription())
                        .prix(mouvementInterne.getPrix())
                        .quantite(mouvementInterne.getQuantite())
                        .ressource(
                                StringUtils.isNotBlank(mouvementInterne.getRessourcesId())?
                                        ressourcesApi.avoirRessource(mouvementInterne.getRessourcesId()):null)
                        .distributeur(
                                StringUtils.isNotBlank(mouvementInterne.getDistributeursId())?
                                        distributeursApi.avoirDistributeur(mouvementInterne.getDistributeursId()):null)
                        .precoMouvementsRespecter(precoMouvementsRespecter)
                        .precoMouvementsVioler(precoMouvementsVioler)
                        .build();
            } catch (ParcoursException e) {
                throw new RuntimeException(e);
            }
            return mouvement;
        }).toList();
    }

    private static List<PersonnesDestinataires> convertirListePersonneDestinataireInterneEnPersonneDestinataire(IPersonnesApi personnesApi, List<PersonnesDestinatairesInterne> personnesDestinatairesInternes) {
        if(CollectionUtils.isEmpty(personnesDestinatairesInternes))
            return new ArrayList<>();
        return personnesDestinatairesInternes.stream().map(personneDestinataireIntene -> {
            PersonnesDestinataires personneDestinataire = null;
            try {
                personneDestinataire = PersonnesDestinataires.builder()
                        .dateEnvoi(personneDestinataireIntene.getDateEnvoi())
                        .methodeEnvoi(personneDestinataireIntene.getMethodeEnvoi())
                        .personne(
                                StringUtils.isNotBlank(personneDestinataireIntene.getPersonneId())?
                                        personnesApi.avoirPersonne(personneDestinataireIntene.getPersonneId()):null)
                        .build();

            } catch (ParcoursException e) {
                throw new RuntimeException(e);
            }
            return personneDestinataire;
        }).toList();
    }

    private static List<OrdreEtats> convertirListeOrdreEtatsInterneEnOrdreEtats(IEtatsApi etatsApi, IPersonnelsApi personnelsApi, IValidationsApi validationsApi, List<OrdreEtatsInterne> ordreEtatsInternes) throws ParcoursException {
        if(CollectionUtils.isEmpty(ordreEtatsInternes))
            return new ArrayList<>();
        return ordreEtatsInternes.stream().map(ordreEtatInterne -> {
            OrdreEtats ordreEtat = null;
            try {
                ordreEtat = dozerMapperBean.map(ordreEtatInterne, OrdreEtats.class);
                ordreEtat.setEtat(
                        StringUtils.isNotBlank(ordreEtatInterne.getEtatsId())?
                                etatsApi.avoirEtat(ordreEtatInterne.getEtatsId()):null);
                ordreEtat.setEtatsValidations(convertirEtatsValidationsInterneEnEtatsValidations(personnelsApi,
                        validationsApi,ordreEtatInterne.getEtatsValidationsInternes()));
            } catch (ParcoursException e) {
                throw new RuntimeException(e);
            }
            return ordreEtat;
        }).toList();
    }

    private static List<EtatsValidations> convertirEtatsValidationsInterneEnEtatsValidations(IPersonnelsApi personnelsApi, IValidationsApi validationsApi, List<EtatsValidationsInterne> etatsValidationsInternes) {
        if(CollectionUtils.isEmpty(etatsValidationsInternes))
            return new ArrayList<>();
        return etatsValidationsInternes.stream()
                .filter(etatsValidationsInterne -> StringUtils.isNotBlank(etatsValidationsInterne.getId()))
                .map(etatsValidationsInterne -> {
                    EtatsValidations etatsValidation = null;
                    try {
                        etatsValidation = dozerMapperBean.map(etatsValidationsInterne, EtatsValidations.class);
                        etatsValidation.setPersonnel(
                                StringUtils.isNotBlank(etatsValidationsInterne.getPersonnelId())?
                                        personnelsApi.avoirPersonnel(etatsValidationsInterne.getPersonnelId()):null);
                        etatsValidation.setValidation(
                                StringUtils.isNotBlank(etatsValidationsInterne.getValidationId())?
                                        validationsApi.avoirValidation(etatsValidationsInterne.getValidationId()):null);
                    } catch (ParcoursException e) {
                        throw new RuntimeException(e);
                    }
                    return etatsValidation;
                }).toList();
    }

    public static ExemplairesInterne convertirExemplaireEnExemplaireInternes(Exemplaires exemplaire) {
        ExemplairesInterne exemplaireInterne = ExemplairesInterne.builder().build();
        dozerMapperBean.map(exemplaire, exemplaireInterne);
        if(exemplaire.getPersonneBeneficiaire()!=null)
            exemplaireInterne.setPersonneBeneficiaireId(exemplaire.getPersonneBeneficiaire().getId());
        if(exemplaire.getPersonneRattachee()!=null)
            exemplaireInterne.setPersonneRattacheeId(exemplaire.getPersonneRattachee().getId());
        if(!CollectionUtils.isEmpty(exemplaire.getOrdreEtats()))
            exemplaireInterne.setOrdreEtatsInternes(convertirListOrdreEtatsEnOrdreEtatsInterne(exemplaire.getOrdreEtats()));
        if(!CollectionUtils.isEmpty(exemplaire.getMouvements()))
            exemplaireInterne.setMouvementsInternes(convertirListMouvementsEnMouvementsInterne(exemplaire.getMouvements()));
        if(!CollectionUtils.isEmpty(exemplaire.getPersonnesDestinataires()))
            exemplaireInterne.setPersonnesDestinatairesInternes(convertirListPersonnesDestinatairesEnPersonnesDestinatairesInterne(exemplaire.getPersonnesDestinataires()));
        if(!CollectionUtils.isEmpty(exemplaire.getExemplaireAttributs()))
         exemplaireInterne.setExemplaireAttributsInternes(convertirListExemplaireAttributsEnExemplaireAttributsInterne(exemplaire.getExemplaireAttributs()));
        return exemplaireInterne;
    }

    private static List<ExemplaireAttributsInterne> convertirListExemplaireAttributsEnExemplaireAttributsInterne(List<ExemplaireAttributs> exemplaireAttributs) {
        return exemplaireAttributs.stream().map(exemplaireAttribut -> {
            ExemplaireAttributsInterne exemplaireAttributInterne = ExemplaireAttributsInterne.builder().build();
            dozerMapperBean.map(exemplaireAttribut, exemplaireAttributInterne);
            exemplaireAttributInterne.setAttributId(exemplaireAttribut.getAttribut().getId());
            return exemplaireAttributInterne;
        }).toList();
    }

    private static List<PersonnesDestinatairesInterne> convertirListPersonnesDestinatairesEnPersonnesDestinatairesInterne(List<PersonnesDestinataires> personnesDestinataires) {
        return personnesDestinataires.stream().map(personnesDestinataire -> {
            PersonnesDestinatairesInterne personnesDestinataireInterne = PersonnesDestinatairesInterne.builder().build();
            dozerMapperBean.map(personnesDestinataire, personnesDestinataireInterne);
            personnesDestinataireInterne.setPersonneId(personnesDestinataire.getPersonne().getId());
            return personnesDestinataireInterne;
        }).toList();
    }

    private static List<MouvementsInterne> convertirListMouvementsEnMouvementsInterne(List<Mouvements> mouvements) {
        return mouvements.stream().map(mouvement -> {
            MouvementsInterne mouvementInterne = MouvementsInterne.builder().build();
            dozerMapperBean.map(mouvement, mouvementInterne);
            mouvementInterne.setRessourcesId(mouvement.getRessource().getId());
            if(mouvement.getDistributeur()!=null)
                mouvementInterne.setDistributeursId(mouvement.getDistributeur().getId());
            if(CollectionUtils.isEmpty(mouvement.getPrecoMouvementsRespecter()))
                mouvementInterne.setPrecoMouvementsRespecterIds(mouvement.getPrecoMouvementsRespecter().stream().map(PrecoMouvements::getId).toList());
            if(CollectionUtils.isEmpty(mouvement.getPrecoMouvementsVioler()))
                mouvementInterne.setPrecoMouvementsViolerIds(mouvement.getPrecoMouvementsVioler().stream().map(PrecoMouvements::getId).toList());
            return mouvementInterne;
        }).toList();
    }

    private static List<OrdreEtatsInterne> convertirListOrdreEtatsEnOrdreEtatsInterne(List<OrdreEtats> ordreEtats) {
        return ordreEtats.stream().map(ordreEtat -> {
            OrdreEtatsInterne ordreEtatInterne = OrdreEtatsInterne.builder().build();
            dozerMapperBean.map(ordreEtat, ordreEtatInterne);
            ordreEtatInterne.setEtatsId(ordreEtat.getEtat().getId());
            if(!CollectionUtils.isEmpty(ordreEtat.getEtatsValidations()))
                convertirListOrdreEtatEnOrdreEtatsInterne(ordreEtat, ordreEtatInterne);
            return ordreEtatInterne;
        }).toList();
    }

    private static void convertirListOrdreEtatEnOrdreEtatsInterne(OrdreEtats ordreEtat, OrdreEtatsInterne ordreEtatInterne) {
        ordreEtatInterne.setEtatsValidationsInternes(ordreEtat.getEtatsValidations().stream().map(etatsValidation -> {
            EtatsValidationsInterne etatsValidationInterne = EtatsValidationsInterne.builder().build();
            dozerMapperBean.map(etatsValidation, etatsValidationInterne);
            etatsValidationInterne.setPersonnelId(etatsValidation.getPersonnel().getId());
            etatsValidationInterne.setValidationId(etatsValidation.getValidation().getId());
            return etatsValidationInterne;
        }).toList());
    }
}
