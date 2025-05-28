package cmr.notep.exemplaire.dao;

import jakarta.persistence.Table;
import lombok.*;
import org.dozer.Mapping;
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
    @Column(name = "titre")
    private String titre;
    @Column(name = "personnebeneficiaire")
    private String personneBeneficiaireId;
    @Column(name = "personnerattachee")
    private String personneRattacheeId;
    @Column(name="document_id")
    private String documentId;
    @Column(name = "datecreation", updatable = false,nullable = false)
    private Date dateCreation;
    @Column(name = "datemodification")
    private Date dateModification;
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
    @ElementCollection
    @CollectionTable(name = "personnesdestinataires", joinColumns = @JoinColumn(name = "exemplaires_id"), schema = "exemplaire")
    @Column(name = "personneid")
    @Mapping("personnesDestinatairesInternes")
    private List<PersonnesDestinatairesEntity> personnesDestinatairesEntities;
    @ElementCollection
    @CollectionTable(name = "exemplairesattributs", joinColumns = @JoinColumn(name = "exemplaires_id"), schema = "exemplaire")
    @Column(name = "attributsid")
    @Mapping("exemplaireAttributsInternes")
    private List<ExemplaireAttributsEntity> exemplaireAttributsEntities;
}
