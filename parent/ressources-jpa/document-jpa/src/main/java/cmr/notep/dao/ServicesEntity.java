package cmr.notep.dao;

import lombok.Getter;
import lombok.Setter;
import org.dozer.Mapping;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "services")
public class ServicesEntity {

    @Id
    @Column(name = "id" , nullable = false)
    private String id ;
    @Column(name = "description")
    private String description;
    @Column(name = "libelle")
    private String libelle;
    @Column(name = "localisation")
    private String localisation;
    @Column(name = "etat")
    private Boolean etat;
    @Column(name = "datecreation")
    private Date dateCreation;
    @Column(name = "datemodification")
    private Date dateModification;
    @Column(name = "codeunique", nullable = false , unique = true)
    private String codeUnique;
    @OneToMany(mappedBy = "service", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @Mapping("mission")
    private List<MissionsEntity> missionEntities ;
}
