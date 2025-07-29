package cmr.notep.dao;

import lombok.Getter;
import lombok.Setter;
import org.dozer.Mapping;
import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "groupes",schema = "login")
public class GroupesEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "UUID")
    private String id;

    @Column(name = "libelle")
    private String libelle;

    @Column(name = "etat")
    private boolean etat;

    @Column(name = "datecreation")
    private Date dateCreation;

    @Column(name = "datemodification")
    private Date dateModification;

    @OneToMany(mappedBy = "groupesEntity")
    @Mapping("utilisateurs")
    private List<UtilisateursEntity> utilisateursEntities ;

    @OneToMany(mappedBy = "groupesEntity")
    @Mapping("menus")
    private List<MenusEntity> menusEntities;
}
