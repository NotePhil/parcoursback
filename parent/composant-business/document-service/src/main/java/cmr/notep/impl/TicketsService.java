package cmr.notep.impl;

import cmr.notep.api.ITicketsApi;
import cmr.notep.business.TicketsBusiness;
import cmr.notep.modele.Tickets;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@Transactional
public class TicketsService implements ITicketsApi {

    private final TicketsBusiness ticketsBusiness;

    public TicketsService(TicketsBusiness ticketsBusiness) {
        this.ticketsBusiness = ticketsBusiness;
    }

    @Override
    public Tickets avoirTicket(String idTickets) {
        return ticketsBusiness.avoirTicket(idTickets);
    }

    @Override
    public List<Tickets> avoirToutTickets() {
        return ticketsBusiness.avoirToutTickets();
    }

    @Override
    public void supprimerTicket(Tickets Tickets) {
        ticketsBusiness.supprimerTicket(Tickets);
    }

    @Override
    public Tickets posterTicket(Tickets Tickets) {
        return ticketsBusiness.posterTicket(Tickets);
    }
}
