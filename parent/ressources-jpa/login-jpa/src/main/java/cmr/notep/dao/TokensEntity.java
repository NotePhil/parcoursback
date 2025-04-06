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
@Table(name = "token",schema = "login")
public class TokensEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "UUID")
    private String id;

    @Column(name = "valeur")
    private String valeur;

    @ManyToOne
    @JoinColumn(name = "utilisateurs_id")
    @Mapping("utilisateur")
    private UtilisateursEntity utilisateursEntity ;

    @Column(name = "datecreation")
    private Date dateCreation;

    @Column(name = "dateexpiration")
    private Date dateexpiration;

    @OneToOne(mappedBy = "tokensEntity",fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @PrimaryKeyJoinColumn
    @Mapping("session")
    private  SessionsEntity sessionsEntity ;

}
