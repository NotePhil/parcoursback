package cmr.notep.dao;

import lombok.*;
import org.dozer.Mapping;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "exemplaires", schema = "exemplaire")
public class ExemplairesEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "UUID")
    private String id;
    @Column(name = "code")
    private String code;
    @Column(name = "codebarre")
    private String codeBarre;
    @Column(name = "intitule")
    private String intitule;
    @Column(name = "personnebeneficiaire")
    private String personneBeneficiaireId;
    @Column(name = "personnerattachee")
    private String personneRattacheeId;
    @Column(name="document_id")
    private String documentId;
    @Column(name = "datecreation", updatable = false,nullable = false)
    private Date dateCreation;
    @Column(name = "datemodification", columnDefinition = "TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private Date dateModification;
    @ElementCollection
    @CollectionTable(name = "correspondants", joinColumns = @JoinColumn(name = "exemplaires_id"), schema = "exemplaire")
    @Mapping("personneEmettriceInterne")
    private List<CorrespondantsEntity>  personneEmettriceEntity;
    //liste des id des exemplaires qui ont servi à la creation de cet exemplaire
    @ElementCollection
    @CollectionTable(name = "exemplairesparents", joinColumns = @JoinColumn(name = "exemplaires_id"), schema = "exemplaire")
    @Column(name = "parent_id")
    private List<String> idExemplairesParents;
    @OneToMany(mappedBy = "exemplaireEntity")
    @Mapping("ordreEtatsInternes")
    private List<OrdreEtatsEntity> ordreEtatsId;
    @OneToMany(mappedBy = "exemplaireEntity")
    @Mapping("mouvementsInternes")
    private List<MouvementsEntity> mouvementsEntities;
    @OneToMany(mappedBy = "exemplaireEntity")
    @Mapping("mouvementSoldeCaissesInternes")
    private List<MouvementSoldeCaissesEntity> mouvementSoldeCaissesEntities;
    @ElementCollection
    @CollectionTable(name = "correspondants", joinColumns = @JoinColumn(name = "exemplaires_id"), schema = "exemplaire")
    @Mapping("personnesDestinatairesInternes")
    private List<CorrespondantsEntity> personnesDestinatairesEntities;
    @ElementCollection
    @CollectionTable(name = "exemplairesattributs", joinColumns = @JoinColumn(name = "exemplaires_id"), schema = "exemplaire")
    @Mapping("exemplaireAttributsInternes")
    private List<ExemplaireAttributsEntity> exemplaireAttributsEntities;
}
