package cmr.notep.api;


import cmr.notep.modele.Missions;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("missions")
public interface IMissionsApi {
    @GetMapping(
            path = "/{idMission}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    Missions avoirMissions (@NonNull @PathVariable(name="idMission") String idMission);

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    List<Missions> avoirToutMissions();

    @DeleteMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    void supprimerMissions(@NonNull @RequestBody Missions missions);

    @PostMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    Missions posterMissions(@NonNull @RequestBody Missions missions) ;
}
