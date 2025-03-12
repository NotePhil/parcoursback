package cmr.notep.wstools.modeles;

import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class GenericWsResponse implements Serializable {
    String reponse;
    Integer code;
    Map<String, List<String>> headers;
}
