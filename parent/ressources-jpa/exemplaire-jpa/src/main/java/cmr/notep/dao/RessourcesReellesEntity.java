package cmr.notep.dao;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "ressourcesreelles", schema = "exemplaire")
public class RessourcesReellesEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "UUID")
    private String id;
    @Version
    @Column(name = "version")
    private Integer version;
    @Column(name = "datecreation", columnDefinition = "TIMESTAMP", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date dateCreation;
    @Column(name = "datemodification", columnDefinition = "TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private Date dateModification;
    @Column(name = "quantite")
    private Integer quantite;
    @Column(name = "seuilalerte")
    private Integer seuilAlerte;
    @Column(name = "prixentree")
    private Double prixEntree;
    @Column(name = "prixsortie")
    private Double prixSortie;
    @Column(name = "unite")
    private String unite ;
    @Column(name = "scanbarcode")
    private String scanBarCode ;
    @Column(name = "ressource_id")
    private String ressourceId;
    @OneToMany
    @JoinColumn(name = "ressourcesreelles_id")
    private List<MouvementsEntity> mouvementsEntities;

}
