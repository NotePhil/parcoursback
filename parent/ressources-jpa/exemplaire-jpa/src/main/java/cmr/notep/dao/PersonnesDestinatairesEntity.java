package cmr.notep.dao;

import lombok.*;
import org.dozer.Mapping;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;
import java.util.Date;

@Getter
@Setter
@Embeddable
public class PersonnesDestinatairesEntity {
    @Column(name = "personneid")
    private String personneId;
    @Column(name = "dateenvoi")
    private Date dateEnvoi;
    @Column(name = "methodeenvoi")
    private String methodeEnvoi;
}
