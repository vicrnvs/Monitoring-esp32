package unip.com.domain.model;

import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Setter
public class SensorData {

    private Integer id;
    private String erros;
    private Integer moisture;
    private Double temperatura;
    private Integer airHumidity;

}
