package unip.com.inbound.adapter.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
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

    @NotNull(message = "identificador não encontrado")
    private String identificador;

    @NotNull(message = "nomeRua não encontrado")
    private String nomeRua;

    @NotNull(message = "numero não encontrado")
    private String numero;

    @NotNull(message = "cidade não encontrado")
    private String cidade;

    @NotNull(message = "bairro não encontrado")
    private String bairro;

    @NotNull(message = "cep não encontrado")
    private String cep;

    @NotNull(message = "estado não encontrado")
    private String estado;

    @NotNull(message = "pais não encontrado")
    private String pais;

    @NotNull(message = "latitude não encontrado")
    private String latitude;

    @NotNull(message = "longitude não encontrado")
    private String longitude;

    @NotNull(message = "altura não encontrado")
    private Integer altura;

    @NotNull(message = "proxima Manutenção não encontrado")
    private LocalDate proximaManutencao;

    private ZonedDateTime criadoEm;

    List<Esp32ConfigParamsDto> configParams;
}
