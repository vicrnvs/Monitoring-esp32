package unip.com.model;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Esp32Dto {

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

    private LocalDate proximaManutencao;

    private ZonedDateTime criadoEm;

    List<Esp32ConfigParamsDto> configParams;
}
