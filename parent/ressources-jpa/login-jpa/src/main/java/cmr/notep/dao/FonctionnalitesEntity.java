package cmr.notep.dao;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.dozer.Mapping;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "fonctionnalites",schema = "login")
public class FonctionnalitesEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "UUID")
    private String id;

    @Column(name = "fonction")
    private String fonction;

    @Column(name = "icone")
    private String icone;

    @Column(name = "actif")
    private boolean actif;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menus_id", referencedColumnName = "id")
    @Mapping("menus")
    private MenusEntity menusEntity ;

    @OneToMany(mappedBy = "fonctionnalitesEntity")
    @Mapping("elements")
    private List<ElementsEntity> elementsEntities;
}
