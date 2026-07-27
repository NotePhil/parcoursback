package cmr.notep.login.dao;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Getter
@Setter
@Entity
@Table(name = "actions", schema = "login")
public class ActionEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "UUID")
    private String id;

    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "lien")
    private String lien;

    @Column(name = "bouton")
    private String bouton;

    @Column(name = "type")
    private String type;

    @Column(name = "ordre", nullable = false)
    private Integer ordre = 0;

    @Column(name = "element_id", nullable = false)
    private String elementId;
}
