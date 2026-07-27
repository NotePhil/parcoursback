package cmr.notep.login.dao;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Getter
@Setter
@Entity
@Table(name = "fonctionnalites", schema = "login")
public class FonctionnaliteEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "UUID")
    private String id;

    @Column(name = "fonction", nullable = false)
    private String fonction;

    @Column(name = "icone")
    private String icone;

    @Column(name = "actif")
    private String actif;

    @Column(name = "ordre", nullable = false)
    private Integer ordre = 0;

    @Column(name = "groupe_id", nullable = false)
    private String groupeId;
}
