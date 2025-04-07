package cmr.notep.utils;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Field;

@Builder
//@NoArgsConstructor
//@AllArgsConstructor
@Setter
@Getter
public class BusinessEntityHelper<T> {

    public T saveEntity(T businessEntity) {
        //determinate if the businessEntity have collection (list or set)
        for (Field field : businessEntity.getClass().getDeclaredFields()) {
            if(field.getType().isAssignableFrom(java.util.List.class)){



            }
        }
        return businessEntity;
    }


}
