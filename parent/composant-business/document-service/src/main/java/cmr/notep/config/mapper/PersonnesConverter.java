package cmr.notep.config.mapper;

import cmr.notep.dao.*;
import cmr.notep.modele.*;
import org.dozer.CustomConverter;
import org.dozer.DozerBeanMapper;
import org.hibernate.collection.internal.PersistentBag;

import java.util.ArrayList;
import java.util.List;


public class PersonnesConverter implements CustomConverter {

    private final DozerBeanMapper dozerMapperBean;
    public PersonnesConverter(DozerBeanMapper dozerMapper) {
        this.dozerMapperBean = dozerMapper;
    }

    @Override
    public Object convert(Object existingDestinationFieldValue, Object sourceFieldValue, Class<?> destinationClass, Class<?> sourceClass) {
        if(sourceFieldValue == null) {
            return null;
        } else if (sourceFieldValue instanceof PersistentBag) {
            List<Object> listReturn = new ArrayList<>();
             ((PersistentBag) sourceFieldValue).iterator().forEachRemaining(item -> listReturn.add(convertOnePerson(item)));
            return listReturn;
        }
        return convertOnePerson(sourceFieldValue);
    }

    private Object convertOnePerson(Object sourceFieldValue) {
        if (sourceFieldValue instanceof DistributeursEntity)
            return dozerMapperBean.map(sourceFieldValue, Distributeurs.class);
        else if (sourceFieldValue instanceof PersonnesPhysiquesEntity)
            return dozerMapperBean.map(sourceFieldValue, PersonnesPhysique.class);
        else if (sourceFieldValue instanceof PersonnesMoralesEntity)
            return dozerMapperBean.map(sourceFieldValue, PersonnesMorale.class);
        else if (sourceFieldValue instanceof PersonnelsEntity) {
            return dozerMapperBean.map(sourceFieldValue, Personnels.class);
        } else if (sourceFieldValue instanceof PersonnesEntity)
            return dozerMapperBean.map(sourceFieldValue, Personnes.class);
        else if (sourceFieldValue instanceof Distributeurs)
            return dozerMapperBean.map(sourceFieldValue, DistributeursEntity.class);
        else if (sourceFieldValue instanceof PersonnesPhysique)
            return dozerMapperBean.map(sourceFieldValue, PersonnesPhysiquesEntity.class);
        else if (sourceFieldValue instanceof PersonnesMorale)
            return dozerMapperBean.map(sourceFieldValue, PersonnesMoralesEntity.class);
        else if (sourceFieldValue instanceof Personnels)
            return dozerMapperBean.map(sourceFieldValue, PersonnelsEntity.class);
        else if (sourceFieldValue instanceof Personnes)
            return dozerMapperBean.map(sourceFieldValue, PersonnesEntity.class);
        else {
            return null;
        }
    }

}
