package cmr.notep.dao;


import lombok.Getter;
import lombok.Setter;
import org.dozer.Mapping;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "roles", schema = "document")
public class RolesEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "UUID")
    private String id;

    @Column(name = "titre")
    private String titre;

    @Column(name = "description")
    private String description;

    @Column(name = "etat")
    private Boolean etat ;

    @Column(name = "datecreation", updatable = false)
    private Date dateCreation;
    @Column(name = "datemodification")
    private Date dateModification;
    @OneToMany(mappedBy = "rolesEntity", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @Mapping("personnels")
    private List<JouerRolesEntity> jouerRolesEntities;

    @OneToMany(mappedBy = "rolesEntity", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @Mapping("missions")
    private List<RemplirEntity> remplirEntities;

    @OneToMany(mappedBy = "roleEntity" , fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @Mapping("validations")
    private List<ValidationsEntity> validationsEntities;
}
