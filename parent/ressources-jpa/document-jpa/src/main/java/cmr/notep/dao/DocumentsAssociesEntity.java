package cmr.notep.dao;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dozer.Mapping;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "documents_associes", schema = "document")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class DocumentsAssociesEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "UUID")
    private String id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "documents_id", nullable = false)
    private DocumentsEntity documentsEntity;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "document_associe_id", nullable = false)
    @Mapping("document")
    private DocumentsEntity documentAssocie;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "etat_id", nullable = false)
    private EtatsEntity etat;

    @Column(name = "datecreation", updatable = false)
    @Temporal(TemporalType.DATE)
    private Date dateCreation;
}

