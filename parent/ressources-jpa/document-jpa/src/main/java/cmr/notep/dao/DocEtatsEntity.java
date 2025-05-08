package cmr.notep.dao;

import lombok.Getter;
import lombok.Setter;
import org.dozer.Mapping;
import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "docetats", schema = "document")
public class DocEtatsEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "UUID")
    private String id;
    @Column(name = "ordre")
    private int ordre;
    @Column(name = "datecreation", updatable = false)
    private Date dateCreation ;
    @Column(name = "datemodification")
    private Date dateModification;

    @ManyToOne
    @JoinColumn(name = "validations_id")
    @Mapping("validation")
    private ValidationsEntity validationsEntity;

    @ManyToMany
    @JoinTable(name = "docetats_predecesseur", schema = "document",
            joinColumns = @JoinColumn(name = "docetats_id"),
            inverseJoinColumns = @JoinColumn(name = "predecesseur_id"))
    @Mapping("predecesseurDocEtat")
    private List<DocEtatsEntity> predecesseursDocEtat = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "etats_id")
    @Mapping("etat")
    private EtatsEntity etatsEntity;

    @ManyToOne
    @JoinColumn(name = "documents_id")
    @Mapping("document")
    private DocumentsEntity documentsEntity;

    @ManyToOne
    @JoinColumn(name = "etapes_id")
    @Mapping("etape")
    private EtapesEntity etapesEntity;

}
