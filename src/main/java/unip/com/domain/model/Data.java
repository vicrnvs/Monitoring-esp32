package unip.com.domain.model;


import lombok.*;

import java.time.ZonedDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Data {

    private Integer id;
    private Esp32 esp32;
    private ZonedDateTime coleta;
    private SensorData sensorData;

}
