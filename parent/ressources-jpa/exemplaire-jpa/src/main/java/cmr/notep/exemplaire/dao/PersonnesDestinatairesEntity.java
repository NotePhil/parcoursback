package cmr.notep.exemplaire.dao;

import lombok.*;
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
