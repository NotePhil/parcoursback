package cmr.notep.login.business;

import cmr.notep.exceptions.ParcoursException;
import cmr.notep.exceptions.enumeration.ParcoursExceptionCodeEnum;
import cmr.notep.login.dao.UtilisateurEntity;
import cmr.notep.login.modele.Role;
import cmr.notep.login.modele.Utilisateur;
import cmr.notep.login.modele.UtilisateurRole;
import cmr.notep.login.repository.RoleRepository;
import cmr.notep.login.repository.UtilisateurRepository;
import cmr.notep.login.repository.UtilisateurRoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static cmr.notep.login.config.LoginConfig.dozerMapperBean;

@Component
@Slf4j
@Transactional
public class UtilisateurBusiness {

    private final UtilisateurRepository utilisateurRepository;
    private final UtilisateurRoleRepository utilisateurRoleRepository;
    private final RoleRepository roleRepository;

    public UtilisateurBusiness(UtilisateurRepository utilisateurRepository,
                                UtilisateurRoleRepository utilisateurRoleRepository,
                                RoleRepository roleRepository) {
        this.utilisateurRepository = utilisateurRepository;
        this.utilisateurRoleRepository = utilisateurRoleRepository;
        this.roleRepository = roleRepository;
    }

    public Utilisateur posterUtilisateur(Utilisateur utilisateur) throws ParcoursException {
        UtilisateurEntity entity = dozerMapperBean.map(utilisateur, UtilisateurEntity.class);
        UtilisateurEntity saved = utilisateurRepository.save(entity);
        return construireUtilisateur(saved);
    }

    public Utilisateur avoirUtilisateur(String id) throws ParcoursException {
        UtilisateurEntity entity = utilisateurRepository.findById(id)
                .orElseThrow(() -> new ParcoursException(ParcoursExceptionCodeEnum.NOT_FOUND, "Utilisateur introuvable : " + id));
        return construireUtilisateur(entity);
    }

    public List<Utilisateur> avoirTousUtilisateurs() {
        return utilisateurRepository.findAll().stream().map(e -> {
            try { return construireUtilisateur(e); }
            catch (ParcoursException ex) { return dozerMapperBean.map(e, Utilisateur.class); }
        }).toList();
    }

    public Utilisateur modifierUtilisateur(String id, Utilisateur utilisateur) throws ParcoursException {
        UtilisateurEntity entity = utilisateurRepository.findById(id)
                .orElseThrow(() -> new ParcoursException(ParcoursExceptionCodeEnum.NOT_FOUND, "Utilisateur introuvable : " + id));
        entity.setNom(utilisateur.getNom());
        entity.setPrenom(utilisateur.getPrenom());
        entity.setTelephone(utilisateur.getTelephone());
        entity.setMail(utilisateur.getMail());
        entity.setSexe(utilisateur.getSexe());
        entity.setType(utilisateur.getType());
        entity.setGroupeId(utilisateur.getGroupeId());
        entity.setPersonnelId(utilisateur.getPersonnelId());
        return construireUtilisateur(utilisateurRepository.save(entity));
    }

    public void supprimerUtilisateur(String id) throws ParcoursException {
        if (!utilisateurRepository.existsById(id))
            throw new ParcoursException(ParcoursExceptionCodeEnum.NOT_FOUND, "Utilisateur introuvable : " + id);
        utilisateurRepository.deleteById(id);
    }

    private Utilisateur construireUtilisateur(UtilisateurEntity entity) throws ParcoursException {
        Utilisateur utilisateur = dozerMapperBean.map(entity, Utilisateur.class);
        List<UtilisateurRole> roles = utilisateurRoleRepository.findByUtilisateurId(entity.getId()).stream()
                .map(ur -> {
                    Role role = roleRepository.findById(ur.getRoleId())
                            .map(r -> dozerMapperBean.map(r, Role.class))
                            .orElse(null);
                    return UtilisateurRole.builder()
                            .id(ur.getId())
                            .role(role)
                            .status(ur.getStatus())
                            .dateDebut(ur.getDateDebut())
                            .dateFin(ur.getDateFin())
                            .build();
                }).toList();
        utilisateur.setRoles(roles);
        return utilisateur;
    }
}
