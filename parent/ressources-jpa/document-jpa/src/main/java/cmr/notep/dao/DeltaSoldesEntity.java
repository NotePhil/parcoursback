package cmr.notep.dao;

import lombok.Getter;
import lombok.Setter;
import org.dozer.Mapping;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "deltasoldes")
public class DeltaSoldesEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "UUID")
    private String id;

    @Column(name = "montantavant")
    private double montantAvant ;

    @Column(name = "montantapres")
    private double montantApres ;

    @Column(name = "datecreation", updatable = false,nullable = false)
    private Date dateCreation ;

    @Column(name = "typemouvement", updatable = false,nullable = false)
    private String typeMvt ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comptes_id")
    @Mapping("compte")
    private ComptesEntity comptesEntity ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exemplaires_id")
    @Mapping("exemplaire")
    private ExemplairesEntity exemplaireEntity ;
}
