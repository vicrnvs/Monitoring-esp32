package unip.com.outbound.adapter.mysql.entities;

import lombok.*;
import org.hibernate.annotations.GeneratorType;
import org.jboss.resteasy.spi.touri.MappedBy;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "esp32")
@Table(name = "esp32")
public class Esp32Entity implements Serializable {

    @Id
    @Column(name = "id", columnDefinition = "INTEGER")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "identificador", length = 10, columnDefinition = "CHAR")
    private String identificador;

    @Column(name = "nome_rua")
    private String nomeRua;

    @Column(name = "numero")
    private String numero;

    @Column(name = "cidade")
    private String cidade;

    @Column(name = "bairro")
    private String bairro;

    @Column(name = "cep")
    private String cep;

    @Column(name = "estado")
    private String estado;

    @Column(name = "pais")
    private String pais;

    @Column(name = "latitude")
    private String latitude;

    @Column(name = "longitude")
    private String longitude;

    @Column(name = "altura")
    private Integer altura;

    @Column(name = "criado_em")
    private ZonedDateTime criadoEm;

    @Column(name = "proxima_manutencao")
    private LocalDate proximaManutencao;

    @OneToMany(mappedBy = "esp32", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Esp32ConfigParamsEntity> configParams;

    public void addConfig(Esp32ConfigParamsEntity esp32ConfigParamsEntity){
        if(Objects.isNull(configParams)) configParams = new ArrayList<>();
        configParams.add(esp32ConfigParamsEntity);
        esp32ConfigParamsEntity.setEsp32(this);
    }
}
