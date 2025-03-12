package cmr.notep.exemplaire.dao;

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
@Table(name = "ordreetats", schema = "exemplaire")
public class OrdreEtatsEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "UUID")
    private String id;
    @Column(name = "description")
    private String description;
    @Column(name = "datecreation", updatable = false)
    private Date dateCreation;
    @Column(name = "datemodification")
    private Date dateModification;
    @Column(name = "datefinvote")
    private Date dateFinVote;
    @Column(name = "ordre")
    private int ordre ;
    @Column(name = "etats_id")
    private String etatsId;
    @ManyToOne
    @JoinColumn(name = "exemplaire_id")
    @Mapping("exemplairesInterne")
    private ExemplairesEntity exemplaireEntity;
    @OneToMany(mappedBy = "ordreEtatEntity")
    @Mapping("etatsValidationsInternes")
    private List<EtatsValidationsEntity> etatsValidationsEntities;
}
