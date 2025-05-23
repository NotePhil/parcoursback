package cmr.notep.impl;

import cmr.notep.api.IMissionsApi;
import cmr.notep.business.MissionsBusiness;
import cmr.notep.modele.Missions;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@Transactional
public class MissionsService implements IMissionsApi {

    private final MissionsBusiness missionsBusiness;

    public MissionsService(MissionsBusiness missionsBusiness) {
        this.missionsBusiness = missionsBusiness;
    }


    @Override
    public Missions avoirMissions(String idMission) {
        return missionsBusiness.avoirMission(idMission);
    }

    @Override
    public List<Missions> avoirToutMissions() {
        return missionsBusiness.avoirTousMission();
    }

    @Override
    public void supprimerMissions(Missions missions) {

    }

    @Override
    public Missions posterMissions(Missions missions) {
        return missionsBusiness.posterMission(missions);
    }

}
