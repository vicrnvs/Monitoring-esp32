package unip.com.outbound.adapter.mysql.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "esp32ConfigParams")
@Table(name = "esp32_config_params")
public class Esp32ConfigParamsEntity {

    @Id
    @Column(name = "id", columnDefinition = "INTEGER")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_esp32", referencedColumnName = "id", foreignKey = @ForeignKey(name = "esp32_config_params_esp32_fk"))
    private Esp32Entity esp32;

    @Column(name = "param")
    private String param;

    @Column(name = "value")
    private String value;

    @Column(name = "active")
    private boolean active;

}
