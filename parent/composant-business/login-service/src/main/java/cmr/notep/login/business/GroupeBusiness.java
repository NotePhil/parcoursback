package cmr.notep.login.business;

import cmr.notep.exceptions.ParcoursException;
import cmr.notep.exceptions.enumeration.ParcoursExceptionCodeEnum;
import cmr.notep.login.dao.GroupeEntity;
import cmr.notep.login.modele.Fonctionnalite;
import cmr.notep.login.modele.Groupe;
import cmr.notep.login.modele.Menu;
import cmr.notep.login.repository.GroupeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static cmr.notep.login.config.LoginConfig.dozerMapperBean;

@Component
@Slf4j
@Transactional
public class GroupeBusiness {

    private final GroupeRepository groupeRepository;
    private final FonctionnaliteBusiness fonctionnaliteBusiness;

    public GroupeBusiness(GroupeRepository groupeRepository, FonctionnaliteBusiness fonctionnaliteBusiness) {
        this.groupeRepository = groupeRepository;
        this.fonctionnaliteBusiness = fonctionnaliteBusiness;
    }

    public Groupe posterGroupe(Groupe groupe) throws ParcoursException {
        GroupeEntity entity = dozerMapperBean.map(groupe, GroupeEntity.class);
        GroupeEntity saved = groupeRepository.save(entity);
        return dozerMapperBean.map(saved, Groupe.class);
    }

    public Groupe avoirGroupe(String id) throws ParcoursException {
        GroupeEntity entity = groupeRepository.findById(id)
                .orElseThrow(() -> new ParcoursException(ParcoursExceptionCodeEnum.NOT_FOUND, "Groupe introuvable : " + id));
        return construireGroupe(entity, "fr");
    }

    public Groupe avoirGroupeAvecTraduction(String id, String langue) throws ParcoursException {
        GroupeEntity entity = groupeRepository.findById(id)
                .orElseThrow(() -> new ParcoursException(ParcoursExceptionCodeEnum.NOT_FOUND, "Groupe introuvable : " + id));
        return construireGroupe(entity, langue);
    }

    public List<Groupe> avoirTousGroupes() {
        return groupeRepository.findAll().stream().map(e -> {
            try { return construireGroupe(e, "fr"); }
            catch (ParcoursException ex) { return dozerMapperBean.map(e, Groupe.class); }
        }).toList();
    }

    public Groupe modifierGroupe(String id, Groupe groupe) throws ParcoursException {
        GroupeEntity entity = groupeRepository.findById(id)
                .orElseThrow(() -> new ParcoursException(ParcoursExceptionCodeEnum.NOT_FOUND, "Groupe introuvable : " + id));
        entity.setLibelle(groupe.getLibelle());
        entity.setEtat(groupe.getEtat());
        return construireGroupe(groupeRepository.save(entity), "fr");
    }

    public void supprimerGroupe(String id) throws ParcoursException {
        if (!groupeRepository.existsById(id))
            throw new ParcoursException(ParcoursExceptionCodeEnum.NOT_FOUND, "Groupe introuvable : " + id);
        groupeRepository.deleteById(id);
    }

    private Groupe construireGroupe(GroupeEntity entity, String langue) throws ParcoursException {
        Groupe groupe = dozerMapperBean.map(entity, Groupe.class);
        List<Fonctionnalite> fonctionnalites = fonctionnaliteBusiness.avoirFonctionnalitesDuGroupe(entity.getId());

        if (!"fr".equalsIgnoreCase(langue)) {
            fonctionnalites = fonctionnalites.stream().map(f -> {
                try { return fonctionnaliteBusiness.traduire(f, langue); }
                catch (ParcoursException e) { return f; }
            }).toList();
        }

        groupe.setMenu(Menu.builder()
                .langue(langue)
                .fonctionnalites(fonctionnalites)
                .build());
        return groupe;
    }
}
