package unip.com.outbound.adapter.mysql.entities;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "sensorData")
@Table(name = "sensor_data")
public class SensorDataEntity {

    @Id
    @Column(name = "id", columnDefinition = "INTEGER")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "erros")
    private String erros;

    @Column(name = "moisture", columnDefinition = "integer")
    private Integer moisture;

    @Column(name = "temperatura", columnDefinition = "decimal(5,2)")
    private Double temperatura;

    @Column(name = "airHumidity", columnDefinition = "integer")
    private Integer airHumidity;

}
