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
@Table(name = "exemplaires")
public class ExemplairesEntity{

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "UUID")
    private String id;

    @OneToMany(mappedBy = "exemplaireEntity", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @Mapping("deltasoldes")
    private List<DeltaSoldesEntity> deltaSoldeEntities;

    @OneToMany(mappedBy = "exemplaireEntity", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @Mapping("mouvementcaisses")
    private List<MouvementCaissesEntity> mouvementCaissesEntities;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "personnes_id")
    @Mapping("personne")
    private PersonnesEntity personnesEntity ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "documents_id")
    @Mapping("document")
    private DocumentsEntity documentsEntity ;
}
