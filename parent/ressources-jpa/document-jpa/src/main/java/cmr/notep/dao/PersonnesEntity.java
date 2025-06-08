package cmr.notep.dao;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.dozer.Mapping;
import org.hibernate.annotations.GenericGenerator;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "personnes", schema = "document")
public class PersonnesEntity
{
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "UUID")
    private String id;
    @Column(name = "adresse")
    private String adresse ;

    @Column(name = "mail")
    private String mail ;

    @Column(name = "telephone")
    private  String telephone ;

    @Column(name = "qrcodevalue")
    private  String qrcodevalue ;
    @Column(name = "datecreation")
    private Date dateCreation;
    @Column(name = "datemodification")
    private Date dateModification;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "rattacher" ,schema = "document",
            joinColumns = @JoinColumn(name = "personnes_id"),
            inverseJoinColumns = @JoinColumn(name = "rattacher_id"))
    @Mapping("personnesRatachees")
    private List<PersonnesEntity> personnesRatachees = new ArrayList<>();

    @OneToOne(mappedBy = "personnesEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "comptes_id" , referencedColumnName = "id")
    @Mapping("compte")
    private ComptesEntity comptesEntity;

    @OneToMany(mappedBy = "personnesEntity", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @Mapping("exemplaires")
    private List<ExemplairesEntity> exemplaireEntities;
}
