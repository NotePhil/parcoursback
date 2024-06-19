package cmr.notep.dao;

import ch.qos.logback.core.spi.CyclicBufferTracker;
import lombok.Getter;
import lombok.Setter;
import org.dozer.Mapping;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "validations")
public class ValidationsEntity {
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "code")
    private String code ;

    @Column(name = "etat")
    private String etat ;

    @Column(name = "datecreation")
    private Date dateCreation;
    @Column(name = "datemodification")
    private Date dateModification;
    @Column(name = "typevote")
    private String typeVote ;
    @Column(name = "dureevote")
    private int dureeVote ;
    @Column(name = "typevalidation")
    private String typeValidation ;

    @ManyToOne
    @JoinColumn(name = "roles_id")
    @Mapping("role")
    private RolesEntity roleEntity;

    @OneToMany(mappedBy = "validationsEntity" , fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @Mapping("docetats")
    private List<DocEtatsEntity> docEtatsEntities;
}
