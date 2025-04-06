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
@Table(name = "sessions",schema = "login")
public class SessionsEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "UUID")
    private String id;

    @Column(name = "datecreation")
    private Date dateCreation;

    @Column(name = "dateexpiration")
    private Date dateexpiration;

    @Column(name = "nomsessions")
    private String nomsessions;

    @OneToOne
    @MapsId
    @JoinColumn(name = "token_id")
    @Mapping("token")
    private TokensEntity tokensEntity ;
}
