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
@Table(name = "etapes", schema = "document")
public class EtapesEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "UUID")
    private String id;

    @Column(name = "libelle")
    private String libelle;

    @Column(name = "etat")
    private String etat;
    @Column(name = "datemodification")
    private Date dateModification;
    @ManyToOne
    @JoinColumn(name = "parcours_id")
    @Mapping("parcour")
    private ParcoursEntity parcoursEntity;

    @OneToMany(mappedBy = "etapesEntity" , fetch = FetchType.LAZY ,  cascade = {CascadeType.ALL})
    @Mapping("docEtats")
    private List<DocEtatsEntity> docEtatsEntities;

}
