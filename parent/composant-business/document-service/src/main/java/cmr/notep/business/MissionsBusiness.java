package cmr.notep.business;


import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import cmr.notep.dao.MissionsEntity;
import cmr.notep.modele.Missions;
import cmr.notep.repository.MissionsRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cmr.notep.dao.DaoAccessorService;
import lombok.extern.slf4j.Slf4j;

import static cmr.notep.config.DocumentConfig.dozerMapperBean;

@Component
@Slf4j
@Transactional

public class MissionsBusiness {
    private final DaoAccessorService daoAccessorService ;

    public MissionsBusiness(DaoAccessorService daoAccessorService) { this.daoAccessorService = daoAccessorService ;}

    public List <Missions> avoirTousMission()
    {
        return daoAccessorService.getRepository(MissionsRepository.class).findAll()
                .stream().map(mission -> dozerMapperBean.map(mission, Missions.class))
                .collect(Collectors.toList());
    }

    public Missions avoirMission(String idMission)
    {
        return dozerMapperBean.map(this.daoAccessorService.getRepository(MissionsRepository.class).findById(idMission)
                .orElseThrow(()->new RuntimeException("Missions non enregistré")), Missions.class);
    }

    public Missions posterMission (Missions missions){
        missions.setDateCreation(new Date());
        return dozerMapperBean.map(
                this.daoAccessorService.getRepository(MissionsRepository.class)
                        .save(dozerMapperBean.map(missions, MissionsEntity.class)),
                Missions.class
        );
    }

    public void supprimerMission(Missions mission) {
        daoAccessorService.getRepository(MissionsRepository.class)
                .deleteById(mission.getId().toString());
    }

    public Missions modifierMission(Missions mission) {
        // Vérifier que la mission existe avant de la modifier
        this.daoAccessorService.getRepository(MissionsRepository.class)
                .findById(mission.getId().toString())
                .orElseThrow(() -> new RuntimeException("Mission non enregistrée"));

        mission.setDateModification(new Date());
        return dozerMapperBean.map(
                this.daoAccessorService.getRepository(MissionsRepository.class)
                        .save(dozerMapperBean.map(mission, MissionsEntity.class)),
                Missions.class);
    }
}
