package cmr.notep.config.mapper;

import cmr.notep.dao.TicketsFilesAttentesEntity;
import cmr.notep.modele.TicketsFilesAttentes;
import org.dozer.CustomConverter;
import org.dozer.DozerBeanMapper;

public class TicketsFilesAttentesConverter implements CustomConverter {
    @Override
    public Object convert(Object existingDestinationFieldValue, Object sourceFieldValue, Class<?> destinationClass, Class<?> sourceClass) {
        if (sourceFieldValue == null) {
            return null;
        }

        if (sourceFieldValue instanceof TicketsFilesAttentesEntity) {
            TicketsFilesAttentesEntity source = (TicketsFilesAttentesEntity) sourceFieldValue;
            if (destinationClass.equals(TicketsFilesAttentes.class)) {
                TicketsFilesAttentes destination = new DozerBeanMapper().map(source, TicketsFilesAttentes.class);
                return destination;
            }
        }
        return null;
    }
}