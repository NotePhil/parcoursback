package cmr.notep.dao;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.dozer.Mapping;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "comptes", schema = "document")
public class ComptesEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "UUID")
    private String id;
    @Column(name = "libelle", nullable = false)
    private String libelle;
    @Column(name = "montantdecouvertmax")
    private int montantDecouvertMax;
    @Column(name = "solde")
    private int solde;
    @Column(name = "etat")
    private Boolean etat;
    @Column(name = "datecreation", columnDefinition = "TIMESTAMP", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date dateCreation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "personnes_id", nullable = false)
    @Mapping("beneficiaire")
    private PersonnesEntity personnesEntity;


}
