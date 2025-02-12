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
@Table(name = "familles", schema = "document")
public class FamillesEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "UUID")
    private String id;
    @Column(name = "libelle")
    private String libelle;
    @Column(name = "description")
    private String description;
    @Column(name = "etat")
    private Boolean etat ;
    @Column(name = "datecreation", updatable = false)
    private Date dateCreation;
    @Column(name = "datemodification")
    private Date dateModification;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "sapplique",schema = "document",
            joinColumns = @JoinColumn(name = "familles_id"),
            inverseJoinColumns = @JoinColumn(name = "precomouvementsqtes_id"))
    @Mapping("precoMouvementsQtes")
    private List<PrecoMouvementsQtesEntity> precoMouvementsQtesEntities;

    @OneToMany(mappedBy = "famillesEntity", fetch = FetchType.LAZY)
    @Mapping("ressources")
    private List<RessourcesEntity> ressourceEntities;

    @ManyToMany(mappedBy = "famillesEntities", fetch = FetchType.LAZY)
    @Mapping("promotions")
    private List<PromotionsEntity> promotionsEntities;
}