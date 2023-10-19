package unip.com.domain.model;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Setter
public class Esp32 {

    private Integer id;
    private String identificador;
    private String nomeRua;
    private String numero;
    private String cidade;
    private String bairro;
    private String cep;
    private String estado;
    private String pais;
    private String latitude;
    private String longitude;
    private Integer altura;
    private ZonedDateTime criadoEm;
    private LocalDate proximaManutencao;

    private List<Esp32ConfigParams> configParams;

    public void generateDefaultConfigParams(){
        if(Objects.isNull(configParams)) configParams = new ArrayList<>();
    }

}
