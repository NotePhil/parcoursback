package cmr.notep.exemplaire.dao;

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
@Table(name = "mouvements", schema = "exemplaire")
public class MouvementsEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "UUID")
    private String id;

    @Column(name = "description" , nullable = false)
    private  String description ;

    @Column(name = "quantite")
    private int quantite ;

    @Column(name = "prix")
    private double prix ;

    @Column(name = "datecreation", updatable = false,nullable = false)
    private Date dateCreation ;

    @Column(name = "dateperemption")
    private Date datePeremption ;
    @Column(name = "datemodification")
    private Date dateModification;
    //PrecoMouvementsEntity
    @ElementCollection
    @CollectionTable(name = "respecterprecomouvement", joinColumns = @JoinColumn(name = "mouvements_id"), schema = "exemplaire")
    @Column(name = "precomouvements_id")
    private List<String> precoMouvementsRespecterIds;

    @ElementCollection
    @CollectionTable(name = "violerprecomouvement", joinColumns = @JoinColumn(name = "mouvements_id"), schema = "exemplaire")
    @Column(name = "precomouvements_id")
    private List<String> precoMouvementsViolerIds;
    @Column(name = "ressources_id")
    private String ressourcesId;
    @Column(name = "distributeurs_id")
    private String distributeursId;
    @ManyToOne
    @JoinColumn(name = "exemplaires_id")
    @Mapping("exemplaireinterne")
    ExemplairesEntity exemplaireEntity;
}
