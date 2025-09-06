package cmr.notep.config.mapper;

import cmr.notep.dao.TicketsEntity;
import cmr.notep.dao.TicketsFilesAttentesEntity;
import cmr.notep.modele.Tickets;
import cmr.notep.modele.TicketsFilesAttentes;
import org.dozer.CustomConverter;
import org.dozer.DozerBeanMapper;

import java.util.ArrayList;
import java.util.List;

public class TicketsConverter implements CustomConverter {
    @Override
    public Object convert(Object existingDestinationFieldValue, Object sourceFieldValue, Class<?> destinationClass, Class<?> sourceClass) {
        if (sourceFieldValue == null) {
            return null;
        }

        if (sourceFieldValue instanceof TicketsEntity) {
            TicketsEntity source = (TicketsEntity) sourceFieldValue;
            if (destinationClass.equals(Tickets.class)) {
                Tickets destination = new DozerBeanMapper().map(source, Tickets.class);

                // Manually map ticketsFilesAttentesEntities to ticketsfilesattentes
                List<TicketsFilesAttentes> ticketsFilesAttentesList = new ArrayList<>();
                for (TicketsFilesAttentesEntity ticketsFilesAttentesEntity : source.getTicketsFilesAttentesEntities()) {
                    TicketsFilesAttentes ticketsFilesAttentes = new DozerBeanMapper().map(ticketsFilesAttentesEntity, TicketsFilesAttentes.class);
                    ticketsFilesAttentesList.add(ticketsFilesAttentes);
                }
                destination.setTicketsfilesattentes(ticketsFilesAttentesList);

                return destination;
            }
        }
        return null;
    }
}