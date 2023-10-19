package unip.com.inbound.adapter.dto;

import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class DataDto {

    private Integer id;

    @NotNull(message = "epoch não encontrado!")
    private Integer epoch;

    @NotNull(message = "identifier não encontrado!")
    private Integer identificador;

    @NotNull(message = "sensorData não encontrado!")
    @Valid
    private SensorDataDto sensorData;


}
