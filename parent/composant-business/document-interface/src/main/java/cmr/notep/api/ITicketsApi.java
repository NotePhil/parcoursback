package cmr.notep.api;

import cmr.notep.modele.Tickets;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("tickets")
public interface ITicketsApi {

    @GetMapping(
            path = "/{idTickets}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    Tickets avoirTicket (@NonNull @PathVariable(name="idTickets") String idTickets);

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    List<Tickets> avoirToutTickets();

    @DeleteMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    void supprimerTicket(@NonNull @RequestBody Tickets Tickets);

    @PostMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    Tickets posterTicket(@NonNull @RequestBody Tickets Tickets) ;
}
