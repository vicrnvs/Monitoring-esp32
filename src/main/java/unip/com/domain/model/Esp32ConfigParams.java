package unip.com.domain.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Esp32ConfigParams {

    private Integer id;

    private String param;

    private String value;

    private boolean active;

}
