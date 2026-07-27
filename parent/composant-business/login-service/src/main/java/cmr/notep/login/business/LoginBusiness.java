package cmr.notep.login.business;

import cmr.notep.exceptions.ParcoursException;
import cmr.notep.exceptions.enumeration.ParcoursExceptionCodeEnum;
import cmr.notep.login.gateway.PersonnelGatewayStrategy;
import cmr.notep.login.modele.Groupe;
import cmr.notep.login.modele.LoginRequest;
import cmr.notep.login.modele.LoginResponse;
import cmr.notep.login.modele.Utilisateur;
import cmr.notep.login.repository.UtilisateurRepository;
import cmr.notep.modele.Personnels;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static cmr.notep.login.config.LoginConfig.dozerMapperBean;

@Component
@Slf4j
@Transactional
public class LoginBusiness {

    private final UtilisateurRepository utilisateurRepository;
    private final UtilisateurBusiness utilisateurBusiness;
    private final GroupeBusiness groupeBusiness;
    private final PersonnelGatewayStrategy personnelGatewayStrategy;

    public LoginBusiness(UtilisateurRepository utilisateurRepository,
                         UtilisateurBusiness utilisateurBusiness,
                         GroupeBusiness groupeBusiness,
                         PersonnelGatewayStrategy personnelGatewayStrategy) {
        this.utilisateurRepository = utilisateurRepository;
        this.utilisateurBusiness = utilisateurBusiness;
        this.groupeBusiness = groupeBusiness;
        this.personnelGatewayStrategy = personnelGatewayStrategy;
    }

    public LoginResponse authentifier(LoginRequest request) throws ParcoursException {
        // Validation des champs obligatoires
        if (StringUtils.isBlank(request.getLogin()) || StringUtils.isBlank(request.getPassWord()))
            throw new ParcoursException(ParcoursExceptionCodeEnum.INVALID_INPUT,
                    "Login et mot de passe obligatoires");

        // Vérification des identifiants en base
        var entityOpt = utilisateurRepository.findByLoginAndPassword(request.getLogin(), request.getPassWord());
        if (entityOpt.isEmpty())
            throw new ParcoursException(ParcoursExceptionCodeEnum.OPERATION_INTERDITE,
                    "Identifiants incorrects pour le login : " + request.getLogin());

        var entity = entityOpt.get();
        log.info("Authentification réussie pour : {}", entity.getLogin());

        // Construction de l'utilisateur enrichi
        Utilisateur utilisateur = utilisateurBusiness.avoirUtilisateur(entity.getId());

        // Enrichissement avec les données Personnel si disponibles
        if (StringUtils.isNotBlank(entity.getPersonnelId())) {
            try {
                Personnels personnel = personnelGatewayStrategy.avoirPersonnel(entity.getPersonnelId());
                if (personnel != null) {
                    // Compléter les données de l'utilisateur depuis Personnel
                    if (StringUtils.isBlank(utilisateur.getNom()) && personnel.getNom() != null)
                        utilisateur.setNom(String.valueOf(personnel.getNom()));
                    if (StringUtils.isBlank(utilisateur.getPrenom()) && personnel.getPrenom() != null)
                        utilisateur.setPrenom(String.valueOf(personnel.getPrenom()));
                }
            } catch (Exception e) {
                log.warn("Impossible de récupérer les données Personnel pour id={} : {}", entity.getPersonnelId(), e.getMessage());
            }
        }

        // Construction du menu du groupe avec traduction éventuelle
        String langue = StringUtils.isBlank(request.getLangue()) ? "fr" : request.getLangue();
        Groupe groupe = null;
        if (StringUtils.isNotBlank(entity.getGroupeId())) {
            groupe = groupeBusiness.avoirGroupeAvecTraduction(entity.getGroupeId(), langue);
        }

        return LoginResponse.builder()
                .id(entity.getId())
                .login(entity.getLogin())
                .user(utilisateur)
                .groupe(groupe)
                .build();
    }
}
