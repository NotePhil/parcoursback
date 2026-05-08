package cmr.notep.dao;

import lombok.Getter;
import lombok.Setter;
import org.dozer.Mapping;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;

import java.util.*;

@Getter
@Setter
@Entity
@Table(name = "personnes", schema = "document")
@Inheritance(strategy = InheritanceType.JOINED)
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

    @Column(name = "datecreation", columnDefinition = "TIMESTAMP", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date dateCreation;

    @Column(name = "datemodification", columnDefinition = "TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private Date dateModification;
    @OneToMany(mappedBy = "personnesEntity",  fetch = FetchType.LAZY)
    private List<ComptesEntity> comptesEntities;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "rattacher" ,schema = "document",
            joinColumns = @JoinColumn(name = "personnes_id"),
            inverseJoinColumns = @JoinColumn(name = "rattacher_id"))
    @Mapping("personnesRatachees")
    private Set<PersonnesEntity> personnesRatachees = new HashSet<>();

    @PrePersist
    protected void onCreate() {
        if (dateCreation == null) {
            dateCreation = new Date();
        }
    }

    @PreUpdate
    protected void onUpdate() {
        dateModification = new Date();
    }
}
