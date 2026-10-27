package cmr.notep.helper;

import cmr.notep.exceptions.ParcoursException;
import cmr.notep.gateway.DocumentGatewayStrategy;
import cmr.notep.modele.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

import static cmr.notep.config.ExemplaireConfig.dozerMapperBean;

public class ExemplaireConvertirHelper {
    public static Exemplaires convertirExemplaireInterneEnExemplaire(DocumentGatewayStrategy documentGatewayApi, ExemplairesInterne exemplairesInterne) throws ParcoursException {
        Documents document = documentGatewayApi.avoirDocument(exemplairesInterne.getDocumentId());
        Exemplaires exemplaire = Exemplaires.builder().build();
        dozerMapperBean.map(document, exemplaire);
        dozerMapperBean.map(exemplairesInterne, exemplaire);
        exemplaire.setPersonneBeneficiaire(
                StringUtils.isNotBlank(exemplairesInterne.getPersonneBeneficiaireId())?
                        documentGatewayApi.avoirPersonne(exemplairesInterne.getPersonneBeneficiaireId()):null);
        exemplaire.setPersonneRattachee(
                StringUtils.isNotBlank(exemplairesInterne.getPersonneRattacheeId())?
                        documentGatewayApi.avoirPersonne(exemplairesInterne.getPersonneRattacheeId()):null);
        exemplaire.setPersonneEmettrice(convertirListePersonneEmettriceInterneEnPersonneEmettrice(documentGatewayApi, exemplairesInterne.getPersonneEmettriceInterne()));
        List<OrdreEtats> ordreEtatsList = convertirListeOrdreEtatsInterneEnOrdreEtats(documentGatewayApi, exemplairesInterne.getOrdreEtatsInternes());
        exemplaire.setOrdreEtats(ordreEtatsList);
        List<Mouvements> mouvements = convertirListeMouvemntsInterneEnMouvements(documentGatewayApi,exemplairesInterne.getMouvementsInternes());
        exemplaire.setMouvements(mouvements);
        List<Correspondants> personnesDestinataires = convertirListePersonneDestinataireInterneEnPersonneDestinataire(documentGatewayApi,exemplairesInterne.getPersonnesDestinatairesInternes());
        exemplaire.setPersonnesDestinataires(personnesDestinataires);
        List<ExemplaireAttributs> exemplaireAttributs = convertirListExemplaireAttributsInterneEnExemplaireAttributs(documentGatewayApi,exemplairesInterne.getExemplaireAttributsInternes());
        exemplaire.setExemplaireAttributs(exemplaireAttributs);
        List<MouvementSoldeCaisses> mouvementSoldeCaisses = convertirListeMouvementSoldeCaissesInterneEnMouvementSoldeCaisses(exemplairesInterne.getMouvementSoldeCaissesInternes());
        exemplaire.setMouvementSoldeCaisses(mouvementSoldeCaisses);

        return exemplaire;
    }

    private static List<Correspondants> convertirListePersonneEmettriceInterneEnPersonneEmettrice(DocumentGatewayStrategy documentGatewayApi, List<CorrespondantsInterne> correspondantsInternes) {
        if (CollectionUtils.isEmpty(correspondantsInternes)) {
            return new ArrayList<>();
        }
        List<Correspondants> correspondants = new ArrayList<>();
        for (CorrespondantsInterne correspondantInterne : correspondantsInternes) {
            try {
                correspondants.add(Correspondants.builder()
                        .dateEchange(correspondantInterne.getDateEchange())
                        .methodeUtilisee(correspondantInterne.getMethodeUtilisee())
                        .personne(StringUtils.isNotBlank(correspondantInterne.getPersonneId())
                                ? documentGatewayApi.avoirPersonne(correspondantInterne.getPersonneId())
                                : null)
                        .build());
            } catch (ParcoursException e) {
                throw new RuntimeException(e);
            }
        }
        return correspondants;
    }

    private static List<MouvementSoldeCaisses> convertirListeMouvementSoldeCaissesInterneEnMouvementSoldeCaisses(List<MouvementSoldeCaissesInterne> mouvementSoldeCaissesInternes) {
        if (CollectionUtils.isEmpty(mouvementSoldeCaissesInternes)) {
            return new ArrayList<>();
        }
        List<MouvementSoldeCaisses> mouvements = new ArrayList<>();
        for (MouvementSoldeCaissesInterne mouvementInterne : mouvementSoldeCaissesInternes) {
            MouvementSoldeCaisses mouvement = dozerMapperBean.map(mouvementInterne, MouvementSoldeCaisses.class);
            if (mouvementInterne.getCaissesReellesInterne() != null) {
                mouvement.setCaissesReelles(dozerMapperBean.map(mouvementInterne.getCaissesReellesInterne(), CaissesReelles.class));
            }
            mouvements.add(mouvement);
        }
        return mouvements;
    }

    private static List<ExemplaireAttributs> convertirListExemplaireAttributsInterneEnExemplaireAttributs(DocumentGatewayStrategy documentGatewayApi, List<ExemplaireAttributsInterne> exemplaireAttributs) {
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
                                        documentGatewayApi.avoirAttribut(exemplaireAttributInterne.getAttributId()):null)
                        .build();
            } catch (ParcoursException e) {
                throw new RuntimeException(e);
            }
            return exemplaireAttribut;
        }).toList();
    }

    private static List<Mouvements> convertirListeMouvemntsInterneEnMouvements(DocumentGatewayStrategy documentGatewayApi,  List<MouvementsInterne> mouvementsInternes) {
        if(CollectionUtils.isEmpty(mouvementsInternes))
            return new ArrayList<>();
        return mouvementsInternes.stream().map(mouvementInterne -> {
            Mouvements mouvement = null;
                List<PrecoMouvements> precoMouvementsRespecter = new ArrayList<>();
                if(!CollectionUtils.isEmpty(mouvementInterne.getPrecoMouvementsRespecterIds()))
                    for (String idPreco : mouvementInterne.getPrecoMouvementsRespecterIds()) {
                        if(StringUtils.isNotBlank(idPreco))
                            precoMouvementsRespecter.add(documentGatewayApi.avoirPrecoMouvement(idPreco));
                    }
                List<PrecoMouvements> precoMouvementsVioler = new ArrayList<>();
                if(!CollectionUtils.isEmpty(mouvementInterne.getPrecoMouvementsViolerIds()))
                    for (String idPrecoMouvement : mouvementInterne.getPrecoMouvementsViolerIds()) {
                        if(StringUtils.isNotBlank((idPrecoMouvement)))
                            precoMouvementsVioler.add(documentGatewayApi.avoirPrecoMouvement(idPrecoMouvement));
                    }
                RessourcesReelles ressourceReelle = convertirRessourceReelleInterneEnRessourceReelle(documentGatewayApi, mouvementInterne.getRessourceReelleInterne());
                mouvement = Mouvements.builder()
                        .dateCreation(mouvementInterne.getDateCreation())
                        .dateModification(mouvementInterne.getDateModification())
                        .id(mouvementInterne.getId())
                        .datePeremption(mouvementInterne.getDatePeremption())
                        .description(mouvementInterne.getDescription())
                        .prix(mouvementInterne.getPrix())
                        .quantiteInitiale(mouvementInterne.getQuantiteInitiale())
                        .quantiteMouvementee(mouvementInterne.getQuantiteMouvementee())
                        .quantiteFinale(mouvementInterne.getQuantiteFinale())
                        .typeTransaction(mouvementInterne.getTypeTransaction())
                        .validee(mouvementInterne.getValidee())
                        .valideeBy(mouvementInterne.getValideeBy())
                        .dateValidation(mouvementInterne.getDateValidation())
                        .statut(mouvementInterne.getStatut())
                        .ressourceReelle(ressourceReelle)
                        .distributeur(
                                StringUtils.isNotBlank(mouvementInterne.getDistributeursId())?
                                        documentGatewayApi.avoirDistributeur(mouvementInterne.getDistributeursId()):null)
                        .precoMouvementsRespecter(precoMouvementsRespecter)
                        .precoMouvementsVioler(precoMouvementsVioler)
                        .build();

            return mouvement;
        }).toList();
    }

    private static RessourcesReelles convertirRessourceReelleInterneEnRessourceReelle(DocumentGatewayStrategy documentGatewayApi, RessourcesReellesInterne ressourceReelleInterne) {
        if (ressourceReelleInterne == null) {
            return null;
        }
            return RessourcesReelles.builder()
                    .id(ressourceReelleInterne.getId())
                    .version(ressourceReelleInterne.getVersion())
                    .dateCreation(ressourceReelleInterne.getDateCreation())
                    .dateModification(ressourceReelleInterne.getDateModification())
                    .quantite(ressourceReelleInterne.getQuantite())
                    .seuilAlerte(ressourceReelleInterne.getSeuilAlerte())
                    .prixEntree(ressourceReelleInterne.getPrixEntree())
                    .prixSortie(ressourceReelleInterne.getPrixSortie())
                    .unite(ressourceReelleInterne.getUnite())
                    .scanBarCode(ressourceReelleInterne.getScanBarCode())
                    .ressource(StringUtils.isNotBlank(ressourceReelleInterne.getRessourceId())
                            ? documentGatewayApi.avoirRessource(ressourceReelleInterne.getRessourceId())
                            : null)
                    .build();
    }

    private static List<Correspondants> convertirListePersonneDestinataireInterneEnPersonneDestinataire(DocumentGatewayStrategy documentGatewayApi, List<CorrespondantsInterne> personnesDestinatairesInternes) {
        if(CollectionUtils.isEmpty(personnesDestinatairesInternes))
            return new ArrayList<>();
        return personnesDestinatairesInternes.stream().map(personneDestinataireIntene -> {
            Correspondants personneDestinataire = null;
                personneDestinataire = Correspondants.builder()
                        .dateEchange(personneDestinataireIntene.getDateEchange())
                        .methodeUtilisee(personneDestinataireIntene.getMethodeUtilisee())
                        .personne(
                                StringUtils.isNotBlank(personneDestinataireIntene.getPersonneId())?
                                        documentGatewayApi.avoirPersonne(personneDestinataireIntene.getPersonneId()):null)
                        .build();
            return personneDestinataire;
        }).toList();
    }

    private static List<OrdreEtats> convertirListeOrdreEtatsInterneEnOrdreEtats(DocumentGatewayStrategy documentGatewayApi,List<OrdreEtatsInterne> ordreEtatsInternes) throws ParcoursException {
        if(CollectionUtils.isEmpty(ordreEtatsInternes))
            return new ArrayList<>();
        return ordreEtatsInternes.stream().map(ordreEtatInterne -> {
            OrdreEtats ordreEtat = null;
                ordreEtat = dozerMapperBean.map(ordreEtatInterne, OrdreEtats.class);
                ordreEtat.setEtat(
                        StringUtils.isNotBlank(ordreEtatInterne.getEtatsId())?
                                documentGatewayApi.avoirEtat(ordreEtatInterne.getEtatsId()):null);
                ordreEtat.setEtatsValidations(convertirEtatsValidationsInterneEnEtatsValidations(documentGatewayApi, ordreEtatInterne.getEtatsValidationsInternes()));
            return ordreEtat;
        }).toList();
    }

    private static List<EtatsValidations> convertirEtatsValidationsInterneEnEtatsValidations(DocumentGatewayStrategy documentGatewayApi, List<EtatsValidationsInterne> etatsValidationsInternes) {
        if(CollectionUtils.isEmpty(etatsValidationsInternes))
            return new ArrayList<>();
        return etatsValidationsInternes.stream()
                .filter(etatsValidationsInterne -> StringUtils.isNotBlank(etatsValidationsInterne.getId()))
                .map(etatsValidationsInterne -> {
                    EtatsValidations etatsValidation = null;
                        etatsValidation = dozerMapperBean.map(etatsValidationsInterne, EtatsValidations.class);
                        etatsValidation.setPersonnel(
                                StringUtils.isNotBlank(etatsValidationsInterne.getPersonnelId())?
                                        documentGatewayApi.avoirPersonnel(etatsValidationsInterne.getPersonnelId()):null);
                        etatsValidation.setValidation(
                                StringUtils.isNotBlank(etatsValidationsInterne.getValidationId())?
                                        documentGatewayApi.avoirValidation(etatsValidationsInterne.getValidationId()):null);
                    return etatsValidation;
                }).toList();
    }

    public static ExemplairesInterne convertirExemplaireEnExemplaireInternes(Exemplaires exemplaire) {
        ExemplairesInterne exemplaireInterne = ExemplairesInterne.builder().build();
        dozerMapperBean.map(exemplaire, exemplaireInterne);
        exemplaireInterne.setIdDocument(exemplaire.getIdDocument());
        if(exemplaire.getPersonneBeneficiaire()!=null)
            exemplaireInterne.setPersonneBeneficiaireId(exemplaire.getPersonneBeneficiaire().getId());
        if(exemplaire.getPersonneRattachee()!=null)
            exemplaireInterne.setPersonneRattacheeId(exemplaire.getPersonneRattachee().getId());
        if(!CollectionUtils.isEmpty(exemplaire.getPersonneEmettrice()))
            exemplaireInterne.setPersonneEmettriceInterne(convertirListPersonneEmettriceEnPersonneEmettriceInterne(exemplaire.getPersonneEmettrice()));
        if(!CollectionUtils.isEmpty(exemplaire.getOrdreEtats()))
            exemplaireInterne.setOrdreEtatsInternes(convertirListOrdreEtatsEnOrdreEtatsInterne(exemplaire.getOrdreEtats()));
        if(!CollectionUtils.isEmpty(exemplaire.getMouvements()))
            exemplaireInterne.setMouvementsInternes(convertirListMouvementsEnMouvementsInterne(exemplaire.getMouvements()));
        if(!CollectionUtils.isEmpty(exemplaire.getPersonnesDestinataires()))
            exemplaireInterne.setPersonnesDestinatairesInternes(convertirListPersonnesDestinatairesEnPersonnesDestinatairesInterne(exemplaire.getPersonnesDestinataires()));
        if(!CollectionUtils.isEmpty(exemplaire.getExemplaireAttributs()))
         exemplaireInterne.setExemplaireAttributsInternes(convertirListExemplaireAttributsEnExemplaireAttributsInterne(exemplaire.getExemplaireAttributs()));
        if(!CollectionUtils.isEmpty(exemplaire.getMouvementSoldeCaisses()))
            exemplaireInterne.setMouvementSoldeCaissesInternes(convertirListMouvementSoldeCaissesEnMouvementSoldeCaissesInterne(exemplaire.getMouvementSoldeCaisses()));
        return exemplaireInterne;
    }

    private static List<CorrespondantsInterne> convertirListPersonneEmettriceEnPersonneEmettriceInterne(List<Correspondants> personneEmettrice) {
        List<CorrespondantsInterne> correspondants = new ArrayList<>();
        for (Correspondants correspondant : personneEmettrice) {
            correspondants.add(CorrespondantsInterne.builder()
                    .dateEchange(correspondant.getDateEchange())
                    .methodeUtilisee(correspondant.getMethodeUtilisee())
                    .personneId(correspondant.getPersonne() != null ? correspondant.getPersonne().getId() : null)
                    .build());
        }
        return correspondants;
    }

    private static List<MouvementSoldeCaissesInterne> convertirListMouvementSoldeCaissesEnMouvementSoldeCaissesInterne(List<MouvementSoldeCaisses> mouvementSoldeCaisses) {
        List<MouvementSoldeCaissesInterne> mouvements = new ArrayList<>();
        for (MouvementSoldeCaisses mouvement : mouvementSoldeCaisses) {
            MouvementSoldeCaissesInterne mouvementInterne = dozerMapperBean.map(mouvement, MouvementSoldeCaissesInterne.class);
            if (mouvement.getCaissesReelles() != null) {
                mouvementInterne.setCaissesReellesInterne(dozerMapperBean.map(mouvement.getCaissesReelles(), CaissesReellesInterne.class));
            }
            mouvements.add(mouvementInterne);
        }
        return mouvements;
    }

    private static List<ExemplaireAttributsInterne> convertirListExemplaireAttributsEnExemplaireAttributsInterne(List<ExemplaireAttributs> exemplaireAttributs) {
        return exemplaireAttributs.stream().map(exemplaireAttribut -> {
            ExemplaireAttributsInterne exemplaireAttributInterne = ExemplaireAttributsInterne.builder().build();
            dozerMapperBean.map(exemplaireAttribut, exemplaireAttributInterne);
            exemplaireAttributInterne.setAttributId(exemplaireAttribut.getAttribut() != null ? exemplaireAttribut.getAttribut().getId() : null);
            return exemplaireAttributInterne;
        }).toList();
    }

    private static List<CorrespondantsInterne> convertirListPersonnesDestinatairesEnPersonnesDestinatairesInterne(List<Correspondants> personnesDestinataires) {
        return personnesDestinataires.stream().map(personnesDestinataire -> {
            CorrespondantsInterne personnesDestinataireInterne = CorrespondantsInterne.builder()
                    .dateEchange(personnesDestinataire.getDateEchange())
                    .methodeUtilisee(personnesDestinataire.getMethodeUtilisee())
                    .personneId(personnesDestinataire.getPersonne() != null ? personnesDestinataire.getPersonne().getId() : null)
                    .build();
            return personnesDestinataireInterne;
        }).toList();
    }

    private static List<MouvementsInterne> convertirListMouvementsEnMouvementsInterne(List<Mouvements> mouvements) {
        return mouvements.stream().map(mouvement -> {
            MouvementsInterne mouvementInterne = MouvementsInterne.builder().build();
            dozerMapperBean.map(mouvement, mouvementInterne);
            if (mouvement.getRessourceReelle() != null) {
                mouvementInterne.setRessourceReelleInterne(convertirRessourceReelleEnInterne(mouvement.getRessourceReelle()));
            }
            if(mouvement.getDistributeur()!=null)
                mouvementInterne.setDistributeursId(mouvement.getDistributeur().getId());
            if(!CollectionUtils.isEmpty(mouvement.getPrecoMouvementsRespecter()))
                mouvementInterne.setPrecoMouvementsRespecterIds(mouvement.getPrecoMouvementsRespecter().stream().map(PrecoMouvements::getId).toList());
            if(!CollectionUtils.isEmpty(mouvement.getPrecoMouvementsVioler()))
                mouvementInterne.setPrecoMouvementsViolerIds(mouvement.getPrecoMouvementsVioler().stream().map(PrecoMouvements::getId).toList());
            return mouvementInterne;
        }).toList();
    }

    private static RessourcesReellesInterne convertirRessourceReelleEnInterne(RessourcesReelles ressourceReelle) {
        return RessourcesReellesInterne.builder()
                .id(ressourceReelle.getId())
                .version(ressourceReelle.getVersion())
                .dateCreation(ressourceReelle.getDateCreation())
                .dateModification(ressourceReelle.getDateModification())
                .quantite(ressourceReelle.getQuantite())
                .seuilAlerte(ressourceReelle.getSeuilAlerte())
                .prixEntree(ressourceReelle.getPrixEntree())
                .prixSortie(ressourceReelle.getPrixSortie())
                .unite(ressourceReelle.getUnite())
                .scanBarCode(ressourceReelle.getScanBarCode())
                .ressourceId(ressourceReelle.getRessource() != null ? ressourceReelle.getRessource().getId() : null)
                .build();
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
