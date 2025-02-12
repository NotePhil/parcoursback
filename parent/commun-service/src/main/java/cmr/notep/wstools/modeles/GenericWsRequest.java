package cmr.notep.wstools.modeles;

import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class GenericWsRequest implements Serializable {
    String url;
    String method;
    String body;
}
