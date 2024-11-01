package cmr.notep.exemplaire.dao;

import lombok.*;
import org.dozer.Mapping;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "etatsvalidations", schema = "exemplaire")
public class EtatsValidationsEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "UUID")
    private String id;
    @Column(name = "methode")
    String methode;
    @Column(name = "personnel_id")
    String personnelId;
    @Column(name = "validation_id")
    String validationId;
    @Column(name = "datecreation", updatable = false,nullable = false)
    Date dateCreation;
    @ManyToOne
    @JoinColumn(name = "ordreetat_id")
    @Mapping("ordreEtatsInterne")
    private OrdreEtatsEntity ordreEtatEntity;
}
