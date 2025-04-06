package cmr.notep.dao;

import lombok.Getter;
import lombok.Setter;
import org.dozer.Mapping;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "historique",schema = "login")
public class HistoriquesEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "UUID")
    private String id;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "historiqueEntity",fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @Mapping("actions")
    private List<ActionsEntity> actionsEntityList ;
}
