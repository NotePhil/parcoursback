package cmr.notep.login.dao;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "utilisateurs", schema = "login")
public class UtilisateurEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "UUID")
    private String id;

    @Column(name = "login", nullable = false, unique = true)
    private String login;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "personnel_id")
    private String personnelId;

    @Column(name = "groupe_id")
    private String groupeId;

    @Column(name = "nom")
    private String nom;

    @Column(name = "prenom")
    private String prenom;

    @Column(name = "telephone")
    private String telephone;

    @Column(name = "mail")
    private String mail;

    @Column(name = "sexe")
    private String sexe;

    @Column(name = "datenaissance")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateNaissance;

    @Column(name = "dateentree")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateEntree;

    @Column(name = "type")
    private String type;

    @Column(name = "qrcodevalue")
    private String qrCodeValue;
}
