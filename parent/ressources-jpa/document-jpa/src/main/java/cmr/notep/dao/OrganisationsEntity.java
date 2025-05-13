package cmr.notep.dao;

import lombok.Getter;
import lombok.Setter;
import org.dozer.Mapping;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "organisations")
public class OrganisationsEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "UUID")
    private String id;

    @Column(name = "raisonsociale")
    private String raisonSociale;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinTable(name ="organiser" ,
    joinColumns = @JoinColumn(name = "organisations_id") ,
            inverseJoinColumns = @JoinColumn(name = "utilisateurs_id"))
    @Mapping("utilisateurs")
    private List<UtilisateursEntity> utilisateursEntities ;
}
