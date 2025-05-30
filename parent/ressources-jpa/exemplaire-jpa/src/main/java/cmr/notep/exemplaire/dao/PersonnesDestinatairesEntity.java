package cmr.notep.exemplaire.dao;

import jakarta.persistence.*;
import lombok.*;
import org.dozer.Mapping;

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
