package cmr.notep.modele;

import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class RemplirId implements Serializable {

    private static final long serialVersionUID = 1L;
    private String roleId;
    private String missionId;

}
